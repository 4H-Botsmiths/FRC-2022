/*
 * Decompiled with CFR 0.150.
 */
package edu.wpi.first.networktables;

import edu.wpi.first.networktables.ConnectionInfo;
import edu.wpi.first.networktables.ConnectionNotification;
import edu.wpi.first.networktables.EntryInfo;
import edu.wpi.first.networktables.EntryNotification;
import edu.wpi.first.networktables.LogMessage;
import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTablesJNI;
import edu.wpi.first.networktables.PersistentException;
import edu.wpi.first.networktables.RpcAnswer;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;
import java.util.function.Consumer;

public final class NetworkTableInstance
implements AutoCloseable {
    public static final int kNetModeNone = 0;
    public static final int kNetModeServer = 1;
    public static final int kNetModeClient = 2;
    public static final int kNetModeStarting = 4;
    public static final int kNetModeFailure = 8;
    public static final int kNetModeLocal = 16;
    public static final int kDefaultPort = 1735;
    private static NetworkTableInstance s_defaultInstance;
    private final ConcurrentMap<String, NetworkTable> m_tables = new ConcurrentHashMap<String, NetworkTable>();
    private final ReentrantLock m_entryListenerLock = new ReentrantLock();
    private final Map<Integer, EntryConsumer<EntryNotification>> m_entryListeners = new HashMap<Integer, EntryConsumer<EntryNotification>>();
    private int m_entryListenerPoller;
    private boolean m_entryListenerWaitQueue;
    private final Condition m_entryListenerWaitQueueCond = this.m_entryListenerLock.newCondition();
    private final ReentrantLock m_connectionListenerLock = new ReentrantLock();
    private final Map<Integer, Consumer<ConnectionNotification>> m_connectionListeners = new HashMap<Integer, Consumer<ConnectionNotification>>();
    private int m_connectionListenerPoller;
    private boolean m_connectionListenerWaitQueue;
    private final Condition m_connectionListenerWaitQueueCond = this.m_connectionListenerLock.newCondition();
    private final ReentrantLock m_rpcCallLock = new ReentrantLock();
    private final Map<Integer, EntryConsumer<RpcAnswer>> m_rpcCalls = new HashMap<Integer, EntryConsumer<RpcAnswer>>();
    private int m_rpcCallPoller;
    private boolean m_rpcCallWaitQueue;
    private final Condition m_rpcCallWaitQueueCond = this.m_rpcCallLock.newCondition();
    private static final byte[] rev0def;
    private final ReentrantLock m_loggerLock = new ReentrantLock();
    private final Map<Integer, Consumer<LogMessage>> m_loggers = new HashMap<Integer, Consumer<LogMessage>>();
    private int m_loggerPoller;
    private boolean m_loggerWaitQueue;
    private final Condition m_loggerWaitQueueCond = this.m_loggerLock.newCondition();
    private boolean m_owned = false;
    private final int m_handle;

    private NetworkTableInstance(int handle) {
        this.m_handle = handle;
    }

    @Override
    public synchronized void close() {
        if (this.m_owned && this.m_handle != 0) {
            NetworkTablesJNI.destroyInstance(this.m_handle);
        }
    }

    public boolean isValid() {
        return this.m_handle != 0;
    }

    public static synchronized NetworkTableInstance getDefault() {
        if (s_defaultInstance == null) {
            s_defaultInstance = new NetworkTableInstance(NetworkTablesJNI.getDefaultInstance());
        }
        return s_defaultInstance;
    }

    public static NetworkTableInstance create() {
        NetworkTableInstance inst = new NetworkTableInstance(NetworkTablesJNI.createInstance());
        inst.m_owned = true;
        return inst;
    }

    public int getHandle() {
        return this.m_handle;
    }

    public NetworkTableEntry getEntry(String name) {
        return new NetworkTableEntry(this, NetworkTablesJNI.getEntry(this.m_handle, name));
    }

    public NetworkTableEntry[] getEntries(String prefix, int types) {
        int[] handles = NetworkTablesJNI.getEntries(this.m_handle, prefix, types);
        NetworkTableEntry[] entries = new NetworkTableEntry[handles.length];
        for (int i = 0; i < handles.length; ++i) {
            entries[i] = new NetworkTableEntry(this, handles[i]);
        }
        return entries;
    }

    public EntryInfo[] getEntryInfo(String prefix, int types) {
        return NetworkTablesJNI.getEntryInfo(this, this.m_handle, prefix, types);
    }

    public NetworkTable getTable(String key) {
        NetworkTable oldTable;
        Object theKey = key.isEmpty() || "/".equals(key) ? "" : (key.charAt(0) == '/' ? key : "/" + key);
        NetworkTable table = (NetworkTable)this.m_tables.get(theKey);
        if (table == null && (oldTable = this.m_tables.putIfAbsent((String)theKey, table = new NetworkTable(this, (String)theKey))) != null) {
            table = oldTable;
        }
        return table;
    }

    public void deleteAllEntries() {
        NetworkTablesJNI.deleteAllEntries(this.m_handle);
    }

    private void startEntryListenerThread() {
        Thread entryListenerThread = new Thread(() -> {
            boolean wasInterrupted = false;
            while (!Thread.interrupted()) {
                EntryNotification[] events;
                try {
                    events = NetworkTablesJNI.pollEntryListener(this, this.m_entryListenerPoller);
                }
                catch (InterruptedException ex) {
                    this.m_entryListenerLock.lock();
                    try {
                        if (this.m_entryListenerWaitQueue) {
                            this.m_entryListenerWaitQueue = false;
                            this.m_entryListenerWaitQueueCond.signalAll();
                            continue;
                        }
                    }
                    finally {
                        this.m_entryListenerLock.unlock();
                        continue;
                    }
                    Thread.currentThread().interrupt();
                    wasInterrupted = true;
                    break;
                }
                for (EntryNotification event : events) {
                    EntryConsumer<EntryNotification> listener;
                    this.m_entryListenerLock.lock();
                    try {
                        listener = this.m_entryListeners.get(event.listener);
                    }
                    finally {
                        this.m_entryListenerLock.unlock();
                    }
                    if (listener == null) continue;
                    event.m_entryObject = listener.m_entry;
                    try {
                        listener.m_consumer.accept(event);
                    }
                    catch (Throwable throwable) {
                        System.err.println("Unhandled exception during entry listener callback: " + throwable.toString());
                        throwable.printStackTrace();
                    }
                }
            }
            this.m_entryListenerLock.lock();
            try {
                if (!wasInterrupted) {
                    NetworkTablesJNI.destroyEntryListenerPoller(this.m_entryListenerPoller);
                }
                this.m_entryListenerPoller = 0;
            }
            finally {
                this.m_entryListenerLock.unlock();
            }
        }, "NTEntryListener");
        entryListenerThread.setDaemon(true);
        entryListenerThread.start();
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public int addEntryListener(String prefix, Consumer<EntryNotification> listener, int flags) {
        this.m_entryListenerLock.lock();
        try {
            if (this.m_entryListenerPoller == 0) {
                this.m_entryListenerPoller = NetworkTablesJNI.createEntryListenerPoller(this.m_handle);
                this.startEntryListenerThread();
            }
            int handle = NetworkTablesJNI.addPolledEntryListener(this.m_entryListenerPoller, prefix, flags);
            this.m_entryListeners.put(handle, new EntryConsumer<EntryNotification>(null, listener));
            int n = handle;
            return n;
        }
        finally {
            this.m_entryListenerLock.unlock();
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public int addEntryListener(NetworkTableEntry entry, Consumer<EntryNotification> listener, int flags) {
        if (!this.equals(entry.getInstance())) {
            throw new IllegalArgumentException("entry does not belong to this instance");
        }
        this.m_entryListenerLock.lock();
        try {
            if (this.m_entryListenerPoller == 0) {
                this.m_entryListenerPoller = NetworkTablesJNI.createEntryListenerPoller(this.m_handle);
                this.startEntryListenerThread();
            }
            int handle = NetworkTablesJNI.addPolledEntryListener(this.m_entryListenerPoller, entry.getHandle(), flags);
            this.m_entryListeners.put(handle, new EntryConsumer<EntryNotification>(entry, listener));
            int n = handle;
            return n;
        }
        finally {
            this.m_entryListenerLock.unlock();
        }
    }

    public void removeEntryListener(int listener) {
        NetworkTablesJNI.removeEntryListener(listener);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public boolean waitForEntryListenerQueue(double timeout) {
        if (!NetworkTablesJNI.waitForEntryListenerQueue(this.m_handle, timeout)) {
            return false;
        }
        this.m_entryListenerLock.lock();
        try {
            if (this.m_entryListenerPoller == 0) return true;
            this.m_entryListenerWaitQueue = true;
            NetworkTablesJNI.cancelPollEntryListener(this.m_entryListenerPoller);
            while (this.m_entryListenerWaitQueue) {
                try {
                    if (!(timeout < 0.0)) {
                        boolean bl = this.m_entryListenerWaitQueueCond.await((long)(timeout * 1.0E9), TimeUnit.NANOSECONDS);
                        return bl;
                    }
                    this.m_entryListenerWaitQueueCond.await();
                }
                catch (InterruptedException ex) {
                    Thread.currentThread().interrupt();
                    boolean bl = true;
                    return bl;
                }
            }
            return true;
        }
        finally {
            this.m_entryListenerLock.unlock();
        }
    }

    private void startConnectionListenerThread() {
        Thread connectionListenerThread = new Thread(() -> {
            boolean wasInterrupted = false;
            while (!Thread.interrupted()) {
                ConnectionNotification[] events;
                try {
                    events = NetworkTablesJNI.pollConnectionListener(this, this.m_connectionListenerPoller);
                }
                catch (InterruptedException ex) {
                    this.m_connectionListenerLock.lock();
                    try {
                        if (this.m_connectionListenerWaitQueue) {
                            this.m_connectionListenerWaitQueue = false;
                            this.m_connectionListenerWaitQueueCond.signalAll();
                            continue;
                        }
                    }
                    finally {
                        this.m_connectionListenerLock.unlock();
                        continue;
                    }
                    Thread.currentThread().interrupt();
                    wasInterrupted = true;
                    break;
                }
                for (ConnectionNotification event : events) {
                    Consumer<ConnectionNotification> listener;
                    this.m_connectionListenerLock.lock();
                    try {
                        listener = this.m_connectionListeners.get(event.listener);
                    }
                    finally {
                        this.m_connectionListenerLock.unlock();
                    }
                    if (listener == null) continue;
                    try {
                        listener.accept(event);
                    }
                    catch (Throwable throwable) {
                        System.err.println("Unhandled exception during connection listener callback: " + throwable.toString());
                        throwable.printStackTrace();
                    }
                }
            }
            this.m_connectionListenerLock.lock();
            try {
                if (!wasInterrupted) {
                    NetworkTablesJNI.destroyConnectionListenerPoller(this.m_connectionListenerPoller);
                }
                this.m_connectionListenerPoller = 0;
            }
            finally {
                this.m_connectionListenerLock.unlock();
            }
        }, "NTConnectionListener");
        connectionListenerThread.setDaemon(true);
        connectionListenerThread.start();
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public int addConnectionListener(Consumer<ConnectionNotification> listener, boolean immediateNotify) {
        this.m_connectionListenerLock.lock();
        try {
            if (this.m_connectionListenerPoller == 0) {
                this.m_connectionListenerPoller = NetworkTablesJNI.createConnectionListenerPoller(this.m_handle);
                this.startConnectionListenerThread();
            }
            int handle = NetworkTablesJNI.addPolledConnectionListener(this.m_connectionListenerPoller, immediateNotify);
            this.m_connectionListeners.put(handle, listener);
            int n = handle;
            return n;
        }
        finally {
            this.m_connectionListenerLock.unlock();
        }
    }

    public void removeConnectionListener(int listener) {
        this.m_connectionListenerLock.lock();
        try {
            this.m_connectionListeners.remove(listener);
        }
        finally {
            this.m_connectionListenerLock.unlock();
        }
        NetworkTablesJNI.removeConnectionListener(listener);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public boolean waitForConnectionListenerQueue(double timeout) {
        if (!NetworkTablesJNI.waitForConnectionListenerQueue(this.m_handle, timeout)) {
            return false;
        }
        this.m_connectionListenerLock.lock();
        try {
            if (this.m_connectionListenerPoller == 0) return true;
            this.m_connectionListenerWaitQueue = true;
            NetworkTablesJNI.cancelPollConnectionListener(this.m_connectionListenerPoller);
            while (this.m_connectionListenerWaitQueue) {
                try {
                    if (!(timeout < 0.0)) {
                        boolean bl = this.m_connectionListenerWaitQueueCond.await((long)(timeout * 1.0E9), TimeUnit.NANOSECONDS);
                        return bl;
                    }
                    this.m_connectionListenerWaitQueueCond.await();
                }
                catch (InterruptedException ex) {
                    Thread.currentThread().interrupt();
                    boolean bl = true;
                    return bl;
                }
            }
            return true;
        }
        finally {
            this.m_connectionListenerLock.unlock();
        }
    }

    private void startRpcCallThread() {
        Thread rpcCallThread = new Thread(() -> {
            boolean wasInterrupted = false;
            while (!Thread.interrupted()) {
                RpcAnswer[] events;
                try {
                    events = NetworkTablesJNI.pollRpc(this, this.m_rpcCallPoller);
                }
                catch (InterruptedException ex) {
                    this.m_rpcCallLock.lock();
                    try {
                        if (this.m_rpcCallWaitQueue) {
                            this.m_rpcCallWaitQueue = false;
                            this.m_rpcCallWaitQueueCond.signalAll();
                            continue;
                        }
                    }
                    finally {
                        this.m_rpcCallLock.unlock();
                        continue;
                    }
                    Thread.currentThread().interrupt();
                    wasInterrupted = true;
                    break;
                }
                for (RpcAnswer event : events) {
                    EntryConsumer<RpcAnswer> listener;
                    this.m_rpcCallLock.lock();
                    try {
                        listener = this.m_rpcCalls.get(event.entry);
                    }
                    finally {
                        this.m_rpcCallLock.unlock();
                    }
                    if (listener == null) continue;
                    event.m_entryObject = listener.m_entry;
                    try {
                        listener.m_consumer.accept(event);
                    }
                    catch (Throwable throwable) {
                        System.err.println("Unhandled exception during RPC callback: " + throwable.toString());
                        throwable.printStackTrace();
                    }
                    event.finish();
                }
            }
            this.m_rpcCallLock.lock();
            try {
                if (!wasInterrupted) {
                    NetworkTablesJNI.destroyRpcCallPoller(this.m_rpcCallPoller);
                }
                this.m_rpcCallPoller = 0;
            }
            finally {
                this.m_rpcCallLock.unlock();
            }
        }, "NTRpcCall");
        rpcCallThread.setDaemon(true);
        rpcCallThread.start();
    }

    public void createRpc(NetworkTableEntry entry, Consumer<RpcAnswer> callback) {
        this.m_rpcCallLock.lock();
        try {
            if (this.m_rpcCallPoller == 0) {
                this.m_rpcCallPoller = NetworkTablesJNI.createRpcCallPoller(this.m_handle);
                this.startRpcCallThread();
            }
            NetworkTablesJNI.createPolledRpc(entry.getHandle(), rev0def, this.m_rpcCallPoller);
            this.m_rpcCalls.put(entry.getHandle(), new EntryConsumer<RpcAnswer>(entry, callback));
        }
        finally {
            this.m_rpcCallLock.unlock();
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public boolean waitForRpcCallQueue(double timeout) {
        if (!NetworkTablesJNI.waitForRpcCallQueue(this.m_handle, timeout)) {
            return false;
        }
        this.m_rpcCallLock.lock();
        try {
            if (this.m_rpcCallPoller == 0) return true;
            this.m_rpcCallWaitQueue = true;
            NetworkTablesJNI.cancelPollRpc(this.m_rpcCallPoller);
            while (this.m_rpcCallWaitQueue) {
                try {
                    if (!(timeout < 0.0)) {
                        boolean bl = this.m_rpcCallWaitQueueCond.await((long)(timeout * 1.0E9), TimeUnit.NANOSECONDS);
                        return bl;
                    }
                    this.m_rpcCallWaitQueueCond.await();
                }
                catch (InterruptedException ex) {
                    Thread.currentThread().interrupt();
                    boolean bl = true;
                    return bl;
                }
            }
            return true;
        }
        finally {
            this.m_rpcCallLock.unlock();
        }
    }

    public void setNetworkIdentity(String name) {
        NetworkTablesJNI.setNetworkIdentity(this.m_handle, name);
    }

    public int getNetworkMode() {
        return NetworkTablesJNI.getNetworkMode(this.m_handle);
    }

    public void startLocal() {
        NetworkTablesJNI.startLocal(this.m_handle);
    }

    public void stopLocal() {
        NetworkTablesJNI.stopLocal(this.m_handle);
    }

    public void startServer() {
        this.startServer("networktables.ini");
    }

    public void startServer(String persistFilename) {
        this.startServer(persistFilename, "");
    }

    public void startServer(String persistFilename, String listenAddress) {
        this.startServer(persistFilename, listenAddress, 1735);
    }

    public void startServer(String persistFilename, String listenAddress, int port) {
        NetworkTablesJNI.startServer(this.m_handle, persistFilename, listenAddress, port);
    }

    public void stopServer() {
        NetworkTablesJNI.stopServer(this.m_handle);
    }

    public void startClient() {
        NetworkTablesJNI.startClient(this.m_handle);
    }

    public void startClient(String serverName) {
        this.startClient(serverName, 1735);
    }

    public void startClient(String serverName, int port) {
        NetworkTablesJNI.startClient(this.m_handle, serverName, port);
    }

    public void startClient(String[] serverNames) {
        this.startClient(serverNames, 1735);
    }

    public void startClient(String[] serverNames, int port) {
        int[] ports = new int[serverNames.length];
        for (int i = 0; i < serverNames.length; ++i) {
            ports[i] = port;
        }
        this.startClient(serverNames, ports);
    }

    public void startClient(String[] serverNames, int[] ports) {
        NetworkTablesJNI.startClient(this.m_handle, serverNames, ports);
    }

    public void startClientTeam(int team) {
        this.startClientTeam(team, 1735);
    }

    public void startClientTeam(int team, int port) {
        NetworkTablesJNI.startClientTeam(this.m_handle, team, port);
    }

    public void stopClient() {
        NetworkTablesJNI.stopClient(this.m_handle);
    }

    public void setServer(String serverName) {
        this.setServer(serverName, 1735);
    }

    public void setServer(String serverName, int port) {
        NetworkTablesJNI.setServer(this.m_handle, serverName, port);
    }

    public void setServer(String[] serverNames) {
        this.setServer(serverNames, 1735);
    }

    public void setServer(String[] serverNames, int port) {
        int[] ports = new int[serverNames.length];
        for (int i = 0; i < serverNames.length; ++i) {
            ports[i] = port;
        }
        this.setServer(serverNames, ports);
    }

    public void setServer(String[] serverNames, int[] ports) {
        NetworkTablesJNI.setServer(this.m_handle, serverNames, ports);
    }

    public void setServerTeam(int team) {
        this.setServerTeam(team, 1735);
    }

    public void setServerTeam(int team, int port) {
        NetworkTablesJNI.setServerTeam(this.m_handle, team, port);
    }

    public void startDSClient() {
        this.startDSClient(1735);
    }

    public void startDSClient(int port) {
        NetworkTablesJNI.startDSClient(this.m_handle, port);
    }

    public void stopDSClient() {
        NetworkTablesJNI.stopDSClient(this.m_handle);
    }

    public void setUpdateRate(double interval) {
        NetworkTablesJNI.setUpdateRate(this.m_handle, interval);
    }

    public void flush() {
        NetworkTablesJNI.flush(this.m_handle);
    }

    public ConnectionInfo[] getConnections() {
        return NetworkTablesJNI.getConnections(this.m_handle);
    }

    public boolean isConnected() {
        return NetworkTablesJNI.isConnected(this.m_handle);
    }

    public void savePersistent(String filename) throws PersistentException {
        NetworkTablesJNI.savePersistent(this.m_handle, filename);
    }

    public String[] loadPersistent(String filename) throws PersistentException {
        return NetworkTablesJNI.loadPersistent(this.m_handle, filename);
    }

    public void saveEntries(String filename, String prefix) throws PersistentException {
        NetworkTablesJNI.saveEntries(this.m_handle, filename, prefix);
    }

    public String[] loadEntries(String filename, String prefix) throws PersistentException {
        return NetworkTablesJNI.loadEntries(this.m_handle, filename, prefix);
    }

    private void startLogThread() {
        Thread loggerThread = new Thread(() -> {
            boolean wasInterrupted = false;
            while (!Thread.interrupted()) {
                LogMessage[] events;
                try {
                    events = NetworkTablesJNI.pollLogger(this, this.m_loggerPoller);
                }
                catch (InterruptedException ex) {
                    Thread.currentThread().interrupt();
                    wasInterrupted = true;
                    break;
                }
                for (LogMessage event : events) {
                    Consumer<LogMessage> logger;
                    this.m_loggerLock.lock();
                    try {
                        logger = this.m_loggers.get(event.logger);
                    }
                    finally {
                        this.m_loggerLock.unlock();
                    }
                    if (logger == null) continue;
                    try {
                        logger.accept(event);
                    }
                    catch (Throwable throwable) {
                        System.err.println("Unhandled exception during logger callback: " + throwable.toString());
                        throwable.printStackTrace();
                    }
                }
            }
            this.m_loggerLock.lock();
            try {
                if (!wasInterrupted) {
                    NetworkTablesJNI.destroyLoggerPoller(this.m_loggerPoller);
                }
                this.m_rpcCallPoller = 0;
            }
            finally {
                this.m_loggerLock.unlock();
            }
        }, "NTLogger");
        loggerThread.setDaemon(true);
        loggerThread.start();
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public int addLogger(Consumer<LogMessage> func, int minLevel, int maxLevel) {
        this.m_loggerLock.lock();
        try {
            if (this.m_loggerPoller == 0) {
                this.m_loggerPoller = NetworkTablesJNI.createLoggerPoller(this.m_handle);
                this.startLogThread();
            }
            int handle = NetworkTablesJNI.addPolledLogger(this.m_loggerPoller, minLevel, maxLevel);
            this.m_loggers.put(handle, func);
            int n = handle;
            return n;
        }
        finally {
            this.m_loggerLock.unlock();
        }
    }

    public void removeLogger(int logger) {
        this.m_loggerLock.lock();
        try {
            this.m_loggers.remove(logger);
        }
        finally {
            this.m_loggerLock.unlock();
        }
        NetworkTablesJNI.removeLogger(logger);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public boolean waitForLoggerQueue(double timeout) {
        if (!NetworkTablesJNI.waitForLoggerQueue(this.m_handle, timeout)) {
            return false;
        }
        this.m_loggerLock.lock();
        try {
            if (this.m_loggerPoller == 0) return true;
            this.m_loggerWaitQueue = true;
            NetworkTablesJNI.cancelPollLogger(this.m_loggerPoller);
            while (this.m_loggerWaitQueue) {
                try {
                    if (!(timeout < 0.0)) {
                        boolean bl = this.m_loggerWaitQueueCond.await((long)(timeout * 1.0E9), TimeUnit.NANOSECONDS);
                        return bl;
                    }
                    this.m_loggerWaitQueueCond.await();
                }
                catch (InterruptedException ex) {
                    Thread.currentThread().interrupt();
                    boolean bl = true;
                    return bl;
                }
            }
            return true;
        }
        finally {
            this.m_loggerLock.unlock();
        }
    }

    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if (!(other instanceof NetworkTableInstance)) {
            return false;
        }
        return this.m_handle == ((NetworkTableInstance)other).m_handle;
    }

    public int hashCode() {
        return this.m_handle;
    }

    static {
        rev0def = new byte[]{0};
    }

    private static class EntryConsumer<T> {
        final NetworkTableEntry m_entry;
        final Consumer<T> m_consumer;

        EntryConsumer(NetworkTableEntry entry, Consumer<T> consumer) {
            this.m_entry = entry;
            this.m_consumer = consumer;
        }
    }
}

