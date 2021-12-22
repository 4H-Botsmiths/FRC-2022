/*
 * Decompiled with CFR 0.150.
 */
package edu.wpi.first.wpilibj.smartdashboard;

import edu.wpi.first.hal.HAL;
import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.util.sendable.Sendable;
import edu.wpi.first.util.sendable.SendableRegistry;
import edu.wpi.first.wpilibj.smartdashboard.ListenerExecutor;
import edu.wpi.first.wpilibj.smartdashboard.SendableBuilderImpl;
import java.nio.ByteBuffer;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public final class SmartDashboard {
    private static final NetworkTable table = NetworkTableInstance.getDefault().getTable("SmartDashboard");
    private static final Map<String, Sendable> tablesToData = new HashMap<String, Sendable>();
    private static final ListenerExecutor listenerExecutor = new ListenerExecutor();

    private SmartDashboard() {
        throw new UnsupportedOperationException("This is a utility class!");
    }

    public static synchronized void putData(String key, Sendable data) {
        Sendable sddata = tablesToData.get(key);
        if (sddata == null || sddata != data) {
            tablesToData.put(key, data);
            NetworkTable dataTable = table.getSubTable(key);
            SendableBuilderImpl builder = new SendableBuilderImpl();
            builder.setTable(dataTable);
            SendableRegistry.publish(data, builder);
            builder.startListeners();
            dataTable.getEntry(".name").setString(key);
        }
    }

    public static void putData(Sendable value) {
        String name = SendableRegistry.getName(value);
        if (!name.isEmpty()) {
            SmartDashboard.putData(name, value);
        }
    }

    public static synchronized Sendable getData(String key) {
        Sendable data = tablesToData.get(key);
        if (data == null) {
            throw new IllegalArgumentException("SmartDashboard data does not exist: " + key);
        }
        return data;
    }

    public static NetworkTableEntry getEntry(String key) {
        return table.getEntry(key);
    }

    public static boolean containsKey(String key) {
        return table.containsKey(key);
    }

    public static Set<String> getKeys(int types) {
        return table.getKeys(types);
    }

    public static Set<String> getKeys() {
        return table.getKeys();
    }

    public static void setPersistent(String key) {
        SmartDashboard.getEntry(key).setPersistent();
    }

    public static void clearPersistent(String key) {
        SmartDashboard.getEntry(key).clearPersistent();
    }

    public static boolean isPersistent(String key) {
        return SmartDashboard.getEntry(key).isPersistent();
    }

    public static void setFlags(String key, int flags) {
        SmartDashboard.getEntry(key).setFlags(flags);
    }

    public static void clearFlags(String key, int flags) {
        SmartDashboard.getEntry(key).clearFlags(flags);
    }

    public static int getFlags(String key) {
        return SmartDashboard.getEntry(key).getFlags();
    }

    public static void delete(String key) {
        table.delete(key);
    }

    public static boolean putBoolean(String key, boolean value) {
        return SmartDashboard.getEntry(key).setBoolean(value);
    }

    public static boolean setDefaultBoolean(String key, boolean defaultValue) {
        return SmartDashboard.getEntry(key).setDefaultBoolean(defaultValue);
    }

    public static boolean getBoolean(String key, boolean defaultValue) {
        return SmartDashboard.getEntry(key).getBoolean(defaultValue);
    }

    public static boolean putNumber(String key, double value) {
        return SmartDashboard.getEntry(key).setDouble(value);
    }

    public static boolean setDefaultNumber(String key, double defaultValue) {
        return SmartDashboard.getEntry(key).setDefaultDouble(defaultValue);
    }

    public static double getNumber(String key, double defaultValue) {
        return SmartDashboard.getEntry(key).getDouble(defaultValue);
    }

    public static boolean putString(String key, String value) {
        return SmartDashboard.getEntry(key).setString(value);
    }

    public static boolean setDefaultString(String key, String defaultValue) {
        return SmartDashboard.getEntry(key).setDefaultString(defaultValue);
    }

    public static String getString(String key, String defaultValue) {
        return SmartDashboard.getEntry(key).getString(defaultValue);
    }

    public static boolean putBooleanArray(String key, boolean[] value) {
        return SmartDashboard.getEntry(key).setBooleanArray(value);
    }

    public static boolean putBooleanArray(String key, Boolean[] value) {
        return SmartDashboard.getEntry(key).setBooleanArray(value);
    }

    public static boolean setDefaultBooleanArray(String key, boolean[] defaultValue) {
        return SmartDashboard.getEntry(key).setDefaultBooleanArray(defaultValue);
    }

    public static boolean setDefaultBooleanArray(String key, Boolean[] defaultValue) {
        return SmartDashboard.getEntry(key).setDefaultBooleanArray(defaultValue);
    }

    public static boolean[] getBooleanArray(String key, boolean[] defaultValue) {
        return SmartDashboard.getEntry(key).getBooleanArray(defaultValue);
    }

    public static Boolean[] getBooleanArray(String key, Boolean[] defaultValue) {
        return SmartDashboard.getEntry(key).getBooleanArray(defaultValue);
    }

    public static boolean putNumberArray(String key, double[] value) {
        return SmartDashboard.getEntry(key).setDoubleArray(value);
    }

    public static boolean putNumberArray(String key, Double[] value) {
        return SmartDashboard.getEntry(key).setNumberArray(value);
    }

    public static boolean setDefaultNumberArray(String key, double[] defaultValue) {
        return SmartDashboard.getEntry(key).setDefaultDoubleArray(defaultValue);
    }

    public static boolean setDefaultNumberArray(String key, Double[] defaultValue) {
        return SmartDashboard.getEntry(key).setDefaultNumberArray(defaultValue);
    }

    public static double[] getNumberArray(String key, double[] defaultValue) {
        return SmartDashboard.getEntry(key).getDoubleArray(defaultValue);
    }

    public static Double[] getNumberArray(String key, Double[] defaultValue) {
        return SmartDashboard.getEntry(key).getDoubleArray(defaultValue);
    }

    public static boolean putStringArray(String key, String[] value) {
        return SmartDashboard.getEntry(key).setStringArray(value);
    }

    public static boolean setDefaultStringArray(String key, String[] defaultValue) {
        return SmartDashboard.getEntry(key).setDefaultStringArray(defaultValue);
    }

    public static String[] getStringArray(String key, String[] defaultValue) {
        return SmartDashboard.getEntry(key).getStringArray(defaultValue);
    }

    public static boolean putRaw(String key, byte[] value) {
        return SmartDashboard.getEntry(key).setRaw(value);
    }

    public static boolean putRaw(String key, ByteBuffer value, int len) {
        return SmartDashboard.getEntry(key).setRaw(value, len);
    }

    public static boolean setDefaultRaw(String key, byte[] defaultValue) {
        return SmartDashboard.getEntry(key).setDefaultRaw(defaultValue);
    }

    public static byte[] getRaw(String key, byte[] defaultValue) {
        return SmartDashboard.getEntry(key).getRaw(defaultValue);
    }

    public static void postListenerTask(Runnable task) {
        listenerExecutor.execute(task);
    }

    public static synchronized void updateValues() {
        listenerExecutor.runListenerTasks();
        for (Sendable data : tablesToData.values()) {
            SendableRegistry.update(data);
        }
    }

    static {
        HAL.report(43, 0);
    }
}

