/*
 * Decompiled with CFR 0.150.
 */
package edu.wpi.first.wpilibj.smartdashboard;

import edu.wpi.first.networktables.NTSendableBuilder;
import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableValue;
import edu.wpi.first.util.function.BooleanConsumer;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import java.util.ArrayList;
import java.util.List;
import java.util.function.BooleanSupplier;
import java.util.function.Consumer;
import java.util.function.DoubleConsumer;
import java.util.function.DoubleSupplier;
import java.util.function.Function;
import java.util.function.Supplier;

public class SendableBuilderImpl
implements NTSendableBuilder {
    private final List<Property> m_properties = new ArrayList<Property>();
    private Runnable m_safeState;
    private final List<Runnable> m_updateTables = new ArrayList<Runnable>();
    private NetworkTable m_table;
    private NetworkTableEntry m_controllableEntry;
    private boolean m_actuator;

    public void setTable(NetworkTable table) {
        this.m_table = table;
        this.m_controllableEntry = table.getEntry(".controllable");
    }

    @Override
    public NetworkTable getTable() {
        return this.m_table;
    }

    @Override
    public boolean isPublished() {
        return this.m_table != null;
    }

    public boolean isActuator() {
        return this.m_actuator;
    }

    @Override
    public void update() {
        for (Property property : this.m_properties) {
            if (property.m_update == null) continue;
            property.m_update.accept(property.m_entry);
        }
        for (Runnable updateTable : this.m_updateTables) {
            updateTable.run();
        }
    }

    public void startListeners() {
        for (Property property : this.m_properties) {
            property.startListener();
        }
        if (this.m_controllableEntry != null) {
            this.m_controllableEntry.setBoolean(true);
        }
    }

    public void stopListeners() {
        for (Property property : this.m_properties) {
            property.stopListener();
        }
        if (this.m_controllableEntry != null) {
            this.m_controllableEntry.setBoolean(false);
        }
    }

    public void startLiveWindowMode() {
        if (this.m_safeState != null) {
            this.m_safeState.run();
        }
        this.startListeners();
    }

    public void stopLiveWindowMode() {
        this.stopListeners();
        if (this.m_safeState != null) {
            this.m_safeState.run();
        }
    }

    @Override
    public void clearProperties() {
        this.stopListeners();
        this.m_properties.clear();
    }

    @Override
    public void setSmartDashboardType(String type) {
        this.m_table.getEntry(".type").setString(type);
    }

    @Override
    public void setActuator(boolean value) {
        this.m_table.getEntry(".actuator").setBoolean(value);
        this.m_actuator = value;
    }

    @Override
    public void setSafeState(Runnable func) {
        this.m_safeState = func;
    }

    @Override
    public void setUpdateTable(Runnable func) {
        this.m_updateTables.add(func);
    }

    @Override
    public NetworkTableEntry getEntry(String key) {
        return this.m_table.getEntry(key);
    }

    @Override
    public void addBooleanProperty(String key, BooleanSupplier getter, BooleanConsumer setter) {
        Property property = new Property(this.m_table, key);
        if (getter != null) {
            property.m_update = entry -> entry.setBoolean(getter.getAsBoolean());
        }
        if (setter != null) {
            property.m_createListener = entry -> entry.addListener(event -> {
                if (event.value.isBoolean()) {
                    SmartDashboard.postListenerTask(() -> setter.accept(event.value.getBoolean()));
                }
            }, 21);
        }
        this.m_properties.add(property);
    }

    @Override
    public void addDoubleProperty(String key, DoubleSupplier getter, DoubleConsumer setter) {
        Property property = new Property(this.m_table, key);
        if (getter != null) {
            property.m_update = entry -> entry.setDouble(getter.getAsDouble());
        }
        if (setter != null) {
            property.m_createListener = entry -> entry.addListener(event -> {
                if (event.value.isDouble()) {
                    SmartDashboard.postListenerTask(() -> setter.accept(event.value.getDouble()));
                }
            }, 21);
        }
        this.m_properties.add(property);
    }

    @Override
    public void addStringProperty(String key, Supplier<String> getter, Consumer<String> setter) {
        Property property = new Property(this.m_table, key);
        if (getter != null) {
            property.m_update = entry -> entry.setString((String)getter.get());
        }
        if (setter != null) {
            property.m_createListener = entry -> entry.addListener(event -> {
                if (event.value.isString()) {
                    SmartDashboard.postListenerTask(() -> setter.accept(event.value.getString()));
                }
            }, 21);
        }
        this.m_properties.add(property);
    }

    @Override
    public void addBooleanArrayProperty(String key, Supplier<boolean[]> getter, Consumer<boolean[]> setter) {
        Property property = new Property(this.m_table, key);
        if (getter != null) {
            property.m_update = entry -> entry.setBooleanArray((boolean[])getter.get());
        }
        if (setter != null) {
            property.m_createListener = entry -> entry.addListener(event -> {
                if (event.value.isBooleanArray()) {
                    SmartDashboard.postListenerTask(() -> setter.accept(event.value.getBooleanArray()));
                }
            }, 21);
        }
        this.m_properties.add(property);
    }

    @Override
    public void addDoubleArrayProperty(String key, Supplier<double[]> getter, Consumer<double[]> setter) {
        Property property = new Property(this.m_table, key);
        if (getter != null) {
            property.m_update = entry -> entry.setDoubleArray((double[])getter.get());
        }
        if (setter != null) {
            property.m_createListener = entry -> entry.addListener(event -> {
                if (event.value.isDoubleArray()) {
                    SmartDashboard.postListenerTask(() -> setter.accept(event.value.getDoubleArray()));
                }
            }, 21);
        }
        this.m_properties.add(property);
    }

    @Override
    public void addStringArrayProperty(String key, Supplier<String[]> getter, Consumer<String[]> setter) {
        Property property = new Property(this.m_table, key);
        if (getter != null) {
            property.m_update = entry -> entry.setStringArray((String[])getter.get());
        }
        if (setter != null) {
            property.m_createListener = entry -> entry.addListener(event -> {
                if (event.value.isStringArray()) {
                    SmartDashboard.postListenerTask(() -> setter.accept(event.value.getStringArray()));
                }
            }, 21);
        }
        this.m_properties.add(property);
    }

    @Override
    public void addRawProperty(String key, Supplier<byte[]> getter, Consumer<byte[]> setter) {
        Property property = new Property(this.m_table, key);
        if (getter != null) {
            property.m_update = entry -> entry.setRaw((byte[])getter.get());
        }
        if (setter != null) {
            property.m_createListener = entry -> entry.addListener(event -> {
                if (event.value.isRaw()) {
                    SmartDashboard.postListenerTask(() -> setter.accept(event.value.getRaw()));
                }
            }, 21);
        }
        this.m_properties.add(property);
    }

    @Override
    public void addValueProperty(String key, Supplier<NetworkTableValue> getter, Consumer<NetworkTableValue> setter) {
        Property property = new Property(this.m_table, key);
        if (getter != null) {
            property.m_update = entry -> entry.setValue(getter.get());
        }
        if (setter != null) {
            property.m_createListener = entry -> entry.addListener(event -> SmartDashboard.postListenerTask(() -> setter.accept(event.value)), 21);
        }
        this.m_properties.add(property);
    }

    private static class Property {
        final NetworkTableEntry m_entry;
        int m_listener;
        Consumer<NetworkTableEntry> m_update;
        Function<NetworkTableEntry, Integer> m_createListener;

        Property(NetworkTable table, String key) {
            this.m_entry = table.getEntry(key);
        }

        protected synchronized void finalize() {
            this.stopListener();
        }

        void startListener() {
            if (this.m_entry.isValid() && this.m_listener == 0 && this.m_createListener != null) {
                this.m_listener = this.m_createListener.apply(this.m_entry);
            }
        }

        void stopListener() {
            if (this.m_entry.isValid() && this.m_listener != 0) {
                this.m_entry.removeListener(this.m_listener);
                this.m_listener = 0;
            }
        }
    }
}

