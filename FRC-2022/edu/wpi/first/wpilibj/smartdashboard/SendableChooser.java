/*
 * Decompiled with CFR 0.150.
 */
package edu.wpi.first.wpilibj.smartdashboard;

import edu.wpi.first.networktables.NTSendable;
import edu.wpi.first.networktables.NTSendableBuilder;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.util.sendable.Sendable;
import edu.wpi.first.util.sendable.SendableRegistry;
import edu.wpi.first.wpilibj.util.ErrorMessages;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.ReentrantLock;

public class SendableChooser<V>
implements NTSendable,
AutoCloseable {
    private static final String DEFAULT = "default";
    private static final String SELECTED = "selected";
    private static final String ACTIVE = "active";
    private static final String OPTIONS = "options";
    private static final String INSTANCE = ".instance";
    private final Map<String, V> m_map = new LinkedHashMap<String, V>();
    private String m_defaultChoice = "";
    private final int m_instance;
    private static final AtomicInteger s_instances = new AtomicInteger();
    private String m_selected;
    private final List<NetworkTableEntry> m_activeEntries = new ArrayList<NetworkTableEntry>();
    private final ReentrantLock m_mutex = new ReentrantLock();

    public SendableChooser() {
        this.m_instance = s_instances.getAndIncrement();
        SendableRegistry.add((Sendable)this, "SendableChooser", this.m_instance);
    }

    @Override
    public void close() {
        SendableRegistry.remove(this);
    }

    public void addOption(String name, V object) {
        this.m_map.put(name, object);
    }

    @Deprecated
    public void addObject(String name, V object) {
        this.addOption(name, object);
    }

    public void setDefaultOption(String name, V object) {
        ErrorMessages.requireNonNullParam(name, "name", "setDefaultOption");
        this.m_defaultChoice = name;
        this.addOption(name, object);
    }

    @Deprecated
    public void addDefault(String name, V object) {
        this.setDefaultOption(name, object);
    }

    public V getSelected() {
        this.m_mutex.lock();
        try {
            if (this.m_selected != null) {
                V v = this.m_map.get(this.m_selected);
                return v;
            }
            V v = this.m_map.get(this.m_defaultChoice);
            return v;
        }
        finally {
            this.m_mutex.unlock();
        }
    }

    @Override
    public void initSendable(NTSendableBuilder builder) {
        builder.setSmartDashboardType("String Chooser");
        builder.getEntry(INSTANCE).setDouble(this.m_instance);
        builder.addStringProperty(DEFAULT, () -> this.m_defaultChoice, null);
        builder.addStringArrayProperty(OPTIONS, () -> this.m_map.keySet().toArray(new String[0]), null);
        builder.addStringProperty(ACTIVE, () -> {
            this.m_mutex.lock();
            try {
                if (this.m_selected != null) {
                    String string = this.m_selected;
                    return string;
                }
                String string = this.m_defaultChoice;
                return string;
            }
            finally {
                this.m_mutex.unlock();
            }
        }, null);
        this.m_mutex.lock();
        try {
            this.m_activeEntries.add(builder.getEntry(ACTIVE));
        }
        finally {
            this.m_mutex.unlock();
        }
        builder.addStringProperty(SELECTED, null, val -> {
            this.m_mutex.lock();
            try {
                this.m_selected = val;
                for (NetworkTableEntry entry : this.m_activeEntries) {
                    entry.setString((String)val);
                }
            }
            finally {
                this.m_mutex.unlock();
            }
        });
    }
}

