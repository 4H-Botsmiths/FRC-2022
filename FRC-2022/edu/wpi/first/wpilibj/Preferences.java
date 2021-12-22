/*
 * Decompiled with CFR 0.150.
 */
package edu.wpi.first.wpilibj;

import edu.wpi.first.hal.HAL;
import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.util.ErrorMessages;
import java.util.Collection;

public final class Preferences {
    private static final String TABLE_NAME = "Preferences";
    private static Preferences instance;
    private static final NetworkTable m_table;

    @Deprecated
    public static synchronized Preferences getInstance() {
        if (instance == null) {
            instance = new Preferences();
        }
        return instance;
    }

    private Preferences() {
    }

    public static Collection<String> getKeys() {
        return m_table.getKeys();
    }

    public static void setString(String key, String value) {
        ErrorMessages.requireNonNullParam(value, "value", "setString");
        NetworkTableEntry entry = m_table.getEntry(key);
        entry.setString(value);
        entry.setPersistent();
    }

    @Deprecated
    public static void putString(String key, String value) {
        Preferences.setString(key, value);
    }

    public static void initString(String key, String value) {
        NetworkTableEntry entry = m_table.getEntry(key);
        entry.setDefaultString(value);
    }

    public static void setInt(String key, int value) {
        NetworkTableEntry entry = m_table.getEntry(key);
        entry.setDouble(value);
        entry.setPersistent();
    }

    @Deprecated
    public static void putInt(String key, int value) {
        Preferences.setInt(key, value);
    }

    public static void initInt(String key, int value) {
        NetworkTableEntry entry = m_table.getEntry(key);
        entry.setDefaultDouble(value);
    }

    public static void setDouble(String key, double value) {
        NetworkTableEntry entry = m_table.getEntry(key);
        entry.setDouble(value);
        entry.setPersistent();
    }

    @Deprecated
    public static void putDouble(String key, double value) {
        Preferences.setDouble(key, value);
    }

    public static void initDouble(String key, double value) {
        NetworkTableEntry entry = m_table.getEntry(key);
        entry.setDefaultDouble(value);
    }

    public static void setFloat(String key, float value) {
        NetworkTableEntry entry = m_table.getEntry(key);
        entry.setDouble(value);
        entry.setPersistent();
    }

    @Deprecated
    public static void putFloat(String key, float value) {
        Preferences.setFloat(key, value);
    }

    public static void initFloat(String key, float value) {
        NetworkTableEntry entry = m_table.getEntry(key);
        entry.setDefaultDouble(value);
    }

    public static void setBoolean(String key, boolean value) {
        NetworkTableEntry entry = m_table.getEntry(key);
        entry.setBoolean(value);
        entry.setPersistent();
    }

    @Deprecated
    public static void putBoolean(String key, boolean value) {
        Preferences.setBoolean(key, value);
    }

    public static void initBoolean(String key, boolean value) {
        NetworkTableEntry entry = m_table.getEntry(key);
        entry.setDefaultBoolean(value);
    }

    public static void setLong(String key, long value) {
        NetworkTableEntry entry = m_table.getEntry(key);
        entry.setDouble(value);
        entry.setPersistent();
    }

    @Deprecated
    public static void putLong(String key, long value) {
        Preferences.setLong(key, value);
    }

    public static void initLong(String key, long value) {
        NetworkTableEntry entry = m_table.getEntry(key);
        entry.setDefaultDouble(value);
    }

    public static boolean containsKey(String key) {
        return m_table.containsKey(key);
    }

    public static void remove(String key) {
        m_table.delete(key);
    }

    public static void removeAll() {
        for (String key : m_table.getKeys()) {
            if (".type".equals(key)) continue;
            Preferences.remove(key);
        }
    }

    public static String getString(String key, String backup) {
        return m_table.getEntry(key).getString(backup);
    }

    public static int getInt(String key, int backup) {
        return (int)m_table.getEntry(key).getDouble(backup);
    }

    public static double getDouble(String key, double backup) {
        return m_table.getEntry(key).getDouble(backup);
    }

    public static boolean getBoolean(String key, boolean backup) {
        return m_table.getEntry(key).getBoolean(backup);
    }

    public static float getFloat(String key, float backup) {
        return (float)m_table.getEntry(key).getDouble(backup);
    }

    public static long getLong(String key, long backup) {
        return (long)m_table.getEntry(key).getDouble(backup);
    }

    static {
        m_table = NetworkTableInstance.getDefault().getTable(TABLE_NAME);
        m_table.getEntry(".type").setString("RobotPreferences");
        m_table.addEntryListener((table, key, entry, value, flags) -> entry.setPersistent(), 5);
        HAL.report(28, 0);
    }
}

