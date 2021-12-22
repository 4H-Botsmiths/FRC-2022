/*
 * Decompiled with CFR 0.150.
 */
package edu.wpi.first.networktables;

import edu.wpi.first.networktables.ConnectionInfo;
import edu.wpi.first.networktables.ConnectionNotification;
import edu.wpi.first.networktables.EntryInfo;
import edu.wpi.first.networktables.EntryNotification;
import edu.wpi.first.networktables.LogMessage;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.networktables.NetworkTableValue;
import edu.wpi.first.networktables.PersistentException;
import edu.wpi.first.networktables.RpcAnswer;
import edu.wpi.first.util.RuntimeLoader;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.concurrent.atomic.AtomicBoolean;

public final class NetworkTablesJNI {
    static boolean libraryLoaded = false;
    static RuntimeLoader<NetworkTablesJNI> loader = null;

    public static synchronized void forceLoad() throws IOException {
        if (libraryLoaded) {
            return;
        }
        loader = new RuntimeLoader<NetworkTablesJNI>("ntcorejni", RuntimeLoader.getDefaultExtractionRoot(), NetworkTablesJNI.class);
        loader.loadLibrary();
        libraryLoaded = true;
    }

    public static native int getDefaultInstance();

    public static native int createInstance();

    public static native void destroyInstance(int var0);

    public static native int getInstanceFromHandle(int var0);

    public static native int getEntry(int var0, String var1);

    public static native int[] getEntries(int var0, String var1, int var2);

    public static native String getEntryName(int var0);

    public static native long getEntryLastChange(int var0);

    public static native int getType(int var0);

    public static native boolean setBoolean(int var0, long var1, boolean var3, boolean var4);

    public static native boolean setDouble(int var0, long var1, double var3, boolean var5);

    public static native boolean setString(int var0, long var1, String var3, boolean var4);

    public static native boolean setRaw(int var0, long var1, byte[] var3, boolean var4);

    public static native boolean setRaw(int var0, long var1, ByteBuffer var3, int var4, boolean var5);

    public static native boolean setBooleanArray(int var0, long var1, boolean[] var3, boolean var4);

    public static native boolean setDoubleArray(int var0, long var1, double[] var3, boolean var4);

    public static native boolean setStringArray(int var0, long var1, String[] var3, boolean var4);

    public static native NetworkTableValue getValue(int var0);

    public static native boolean getBoolean(int var0, boolean var1);

    public static native double getDouble(int var0, double var1);

    public static native String getString(int var0, String var1);

    public static native byte[] getRaw(int var0, byte[] var1);

    public static native boolean[] getBooleanArray(int var0, boolean[] var1);

    public static native double[] getDoubleArray(int var0, double[] var1);

    public static native String[] getStringArray(int var0, String[] var1);

    public static native boolean setDefaultBoolean(int var0, long var1, boolean var3);

    public static native boolean setDefaultDouble(int var0, long var1, double var3);

    public static native boolean setDefaultString(int var0, long var1, String var3);

    public static native boolean setDefaultRaw(int var0, long var1, byte[] var3);

    public static native boolean setDefaultBooleanArray(int var0, long var1, boolean[] var3);

    public static native boolean setDefaultDoubleArray(int var0, long var1, double[] var3);

    public static native boolean setDefaultStringArray(int var0, long var1, String[] var3);

    public static native void setEntryFlags(int var0, int var1);

    public static native int getEntryFlags(int var0);

    public static native void deleteEntry(int var0);

    public static native void deleteAllEntries(int var0);

    public static native EntryInfo getEntryInfoHandle(NetworkTableInstance var0, int var1);

    public static native EntryInfo[] getEntryInfo(NetworkTableInstance var0, int var1, String var2, int var3);

    public static native int createEntryListenerPoller(int var0);

    public static native void destroyEntryListenerPoller(int var0);

    public static native int addPolledEntryListener(int var0, String var1, int var2);

    public static native int addPolledEntryListener(int var0, int var1, int var2);

    public static native EntryNotification[] pollEntryListener(NetworkTableInstance var0, int var1) throws InterruptedException;

    public static native EntryNotification[] pollEntryListenerTimeout(NetworkTableInstance var0, int var1, double var2) throws InterruptedException;

    public static native void cancelPollEntryListener(int var0);

    public static native void removeEntryListener(int var0);

    public static native boolean waitForEntryListenerQueue(int var0, double var1);

    public static native int createConnectionListenerPoller(int var0);

    public static native void destroyConnectionListenerPoller(int var0);

    public static native int addPolledConnectionListener(int var0, boolean var1);

    public static native ConnectionNotification[] pollConnectionListener(NetworkTableInstance var0, int var1) throws InterruptedException;

    public static native ConnectionNotification[] pollConnectionListenerTimeout(NetworkTableInstance var0, int var1, double var2) throws InterruptedException;

    public static native void cancelPollConnectionListener(int var0);

    public static native void removeConnectionListener(int var0);

    public static native boolean waitForConnectionListenerQueue(int var0, double var1);

    public static native int createRpcCallPoller(int var0);

    public static native void destroyRpcCallPoller(int var0);

    public static native void createPolledRpc(int var0, byte[] var1, int var2);

    public static native RpcAnswer[] pollRpc(NetworkTableInstance var0, int var1) throws InterruptedException;

    public static native RpcAnswer[] pollRpcTimeout(NetworkTableInstance var0, int var1, double var2) throws InterruptedException;

    public static native void cancelPollRpc(int var0);

    public static native boolean waitForRpcCallQueue(int var0, double var1);

    public static native boolean postRpcResponse(int var0, int var1, byte[] var2);

    public static native int callRpc(int var0, byte[] var1);

    public static native byte[] getRpcResult(int var0, int var1);

    public static native byte[] getRpcResult(int var0, int var1, double var2);

    public static native void cancelRpcResult(int var0, int var1);

    public static native byte[] getRpc(int var0, byte[] var1);

    public static native void setNetworkIdentity(int var0, String var1);

    public static native int getNetworkMode(int var0);

    public static native void startLocal(int var0);

    public static native void stopLocal(int var0);

    public static native void startServer(int var0, String var1, String var2, int var3);

    public static native void stopServer(int var0);

    public static native void startClient(int var0);

    public static native void startClient(int var0, String var1, int var2);

    public static native void startClient(int var0, String[] var1, int[] var2);

    public static native void startClientTeam(int var0, int var1, int var2);

    public static native void stopClient(int var0);

    public static native void setServer(int var0, String var1, int var2);

    public static native void setServer(int var0, String[] var1, int[] var2);

    public static native void setServerTeam(int var0, int var1, int var2);

    public static native void startDSClient(int var0, int var1);

    public static native void stopDSClient(int var0);

    public static native void setUpdateRate(int var0, double var1);

    public static native void flush(int var0);

    public static native ConnectionInfo[] getConnections(int var0);

    public static native boolean isConnected(int var0);

    public static native void savePersistent(int var0, String var1) throws PersistentException;

    public static native String[] loadPersistent(int var0, String var1) throws PersistentException;

    public static native void saveEntries(int var0, String var1, String var2) throws PersistentException;

    public static native String[] loadEntries(int var0, String var1, String var2) throws PersistentException;

    public static native long now();

    public static native int createLoggerPoller(int var0);

    public static native void destroyLoggerPoller(int var0);

    public static native int addPolledLogger(int var0, int var1, int var2);

    public static native LogMessage[] pollLogger(NetworkTableInstance var0, int var1) throws InterruptedException;

    public static native LogMessage[] pollLoggerTimeout(NetworkTableInstance var0, int var1, double var2) throws InterruptedException;

    public static native void cancelPollLogger(int var0);

    public static native void removeLogger(int var0);

    public static native boolean waitForLoggerQueue(int var0, double var1);

    static {
        if (Helper.getExtractOnStaticLoad()) {
            try {
                loader = new RuntimeLoader<NetworkTablesJNI>("ntcorejni", RuntimeLoader.getDefaultExtractionRoot(), NetworkTablesJNI.class);
                loader.loadLibrary();
            }
            catch (IOException ex) {
                ex.printStackTrace();
                System.exit(1);
            }
            libraryLoaded = true;
        }
    }

    public static class Helper {
        private static AtomicBoolean extractOnStaticLoad = new AtomicBoolean(true);

        public static boolean getExtractOnStaticLoad() {
            return extractOnStaticLoad.get();
        }

        public static void setExtractOnStaticLoad(boolean load) {
            extractOnStaticLoad.set(load);
        }
    }
}

