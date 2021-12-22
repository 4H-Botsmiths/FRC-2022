/*
 * Decompiled with CFR 0.150.
 */
package edu.wpi.first.networktables;

import edu.wpi.first.networktables.EntryInfo;
import edu.wpi.first.networktables.EntryNotification;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.networktables.NetworkTableValue;
import edu.wpi.first.networktables.NetworkTablesJNI;
import edu.wpi.first.networktables.PersistentException;
import edu.wpi.first.networktables.TableEntryListener;
import edu.wpi.first.networktables.TableListener;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.function.Consumer;

public final class NetworkTable {
    public static final char PATH_SEPARATOR = '/';
    private final String m_path;
    private final String m_pathWithSep;
    private final NetworkTableInstance m_inst;
    private final ConcurrentMap<String, NetworkTableEntry> m_entries = new ConcurrentHashMap<String, NetworkTableEntry>();

    public static String basenameKey(String key) {
        int slash = key.lastIndexOf(47);
        if (slash == -1) {
            return key;
        }
        return key.substring(slash + 1);
    }

    public static String normalizeKey(String key, boolean withLeadingSlash) {
        Object normalized = withLeadingSlash ? "/" + key : key;
        normalized = ((String)normalized).replaceAll("/{2,}", String.valueOf('/'));
        if (!withLeadingSlash && ((String)normalized).charAt(0) == '/') {
            normalized = ((String)normalized).substring(1);
        }
        return normalized;
    }

    public static String normalizeKey(String key) {
        return NetworkTable.normalizeKey(key, true);
    }

    public static List<String> getHierarchy(String key) {
        String normal = NetworkTable.normalizeKey(key, true);
        ArrayList<String> hierarchy = new ArrayList<String>();
        if (normal.length() == 1) {
            hierarchy.add(normal);
            return hierarchy;
        }
        int i = 1;
        while (true) {
            if (i == -1) break;
            hierarchy.add(normal.substring(0, i));
            i = normal.indexOf(47, i + 1);
        }
        hierarchy.add(normal);
        return hierarchy;
    }

    NetworkTable(NetworkTableInstance inst, String path) {
        this.m_path = path;
        this.m_pathWithSep = path + "/";
        this.m_inst = inst;
    }

    public NetworkTableInstance getInstance() {
        return this.m_inst;
    }

    public String toString() {
        return "NetworkTable: " + this.m_path;
    }

    public NetworkTableEntry getEntry(String key) {
        NetworkTableEntry entry = (NetworkTableEntry)this.m_entries.get(key);
        if (entry == null) {
            entry = this.m_inst.getEntry(this.m_pathWithSep + key);
            this.m_entries.putIfAbsent(key, entry);
        }
        return entry;
    }

    public int addEntryListener(TableEntryListener listener, int flags) {
        int prefixLen = this.m_path.length() + 1;
        return this.m_inst.addEntryListener(this.m_pathWithSep, (EntryNotification event) -> {
            String relativeKey = event.name.substring(prefixLen);
            if (relativeKey.indexOf(47) != -1) {
                return;
            }
            listener.valueChanged(this, relativeKey, event.getEntry(), event.value, event.flags);
        }, flags);
    }

    public int addEntryListener(String key, TableEntryListener listener, int flags) {
        NetworkTableEntry entry = this.getEntry(key);
        return this.m_inst.addEntryListener(entry, (EntryNotification event) -> listener.valueChanged(this, key, entry, event.value, event.flags), flags);
    }

    public void removeEntryListener(int listener) {
        this.m_inst.removeEntryListener(listener);
    }

    public int addSubTableListener(final TableListener listener, boolean localNotify) {
        int flags = 5;
        if (localNotify) {
            flags |= 2;
        }
        final int prefixLen = this.m_path.length() + 1;
        final NetworkTable parent = this;
        return this.m_inst.addEntryListener(this.m_pathWithSep, new Consumer<EntryNotification>(){
            final Set<String> m_notifiedTables = new HashSet<String>();

            @Override
            public void accept(EntryNotification event) {
                String relativeKey = event.name.substring(prefixLen);
                int endSubTable = relativeKey.indexOf(47);
                if (endSubTable == -1) {
                    return;
                }
                String subTableKey = relativeKey.substring(0, endSubTable);
                if (this.m_notifiedTables.contains(subTableKey)) {
                    return;
                }
                this.m_notifiedTables.add(subTableKey);
                listener.tableCreated(parent, subTableKey, parent.getSubTable(subTableKey));
            }
        }, flags);
    }

    public void removeTableListener(int listener) {
        this.m_inst.removeEntryListener(listener);
    }

    public NetworkTable getSubTable(String key) {
        return new NetworkTable(this.m_inst, this.m_pathWithSep + key);
    }

    public boolean containsKey(String key) {
        return !"".equals(key) && this.getEntry(key).exists();
    }

    public boolean containsSubTable(String key) {
        int[] handles = NetworkTablesJNI.getEntries(this.m_inst.getHandle(), this.m_pathWithSep + key + "/", 0);
        return handles.length != 0;
    }

    public Set<String> getKeys(int types) {
        HashSet<String> keys = new HashSet<String>();
        int prefixLen = this.m_path.length() + 1;
        for (EntryInfo info : this.m_inst.getEntryInfo(this.m_pathWithSep, types)) {
            String relativeKey = info.name.substring(prefixLen);
            if (relativeKey.indexOf(47) != -1) continue;
            keys.add(relativeKey);
            if (this.m_entries.get(relativeKey) != null) continue;
            this.m_entries.putIfAbsent(relativeKey, new NetworkTableEntry(this.m_inst, info.entry));
        }
        return keys;
    }

    public Set<String> getKeys() {
        return this.getKeys(0);
    }

    public Set<String> getSubTables() {
        HashSet<String> keys = new HashSet<String>();
        int prefixLen = this.m_path.length() + 1;
        for (EntryInfo info : this.m_inst.getEntryInfo(this.m_pathWithSep, 0)) {
            String relativeKey = info.name.substring(prefixLen);
            int endSubTable = relativeKey.indexOf(47);
            if (endSubTable == -1) continue;
            keys.add(relativeKey.substring(0, endSubTable));
        }
        return keys;
    }

    public void delete(String key) {
        this.getEntry(key).delete();
    }

    boolean putValue(String key, NetworkTableValue value) {
        return this.getEntry(key).setValue(value);
    }

    boolean setDefaultValue(String key, NetworkTableValue defaultValue) {
        return this.getEntry(key).setDefaultValue(defaultValue);
    }

    NetworkTableValue getValue(String key) {
        return this.getEntry(key).getValue();
    }

    public String getPath() {
        return this.m_path;
    }

    public void saveEntries(String filename) throws PersistentException {
        this.m_inst.saveEntries(filename, this.m_pathWithSep);
    }

    public String[] loadEntries(String filename) throws PersistentException {
        return this.m_inst.loadEntries(filename, this.m_pathWithSep);
    }

    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if (!(other instanceof NetworkTable)) {
            return false;
        }
        NetworkTable ntOther = (NetworkTable)other;
        return this.m_inst.equals(ntOther.m_inst) && this.m_path.equals(ntOther.m_path);
    }

    public int hashCode() {
        return Objects.hash(this.m_inst, this.m_path);
    }
}

