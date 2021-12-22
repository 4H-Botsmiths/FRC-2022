/*
 * Decompiled with CFR 0.150.
 */
package edu.wpi.first.wpilibj.smartdashboard;

import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.wpilibj.smartdashboard.MechanismObject2d;
import java.util.HashMap;
import java.util.Map;

public final class MechanismRoot2d {
    private final String m_name;
    private NetworkTable m_table;
    private final Map<String, MechanismObject2d> m_objects = new HashMap<String, MechanismObject2d>(1);
    private double m_x;
    private NetworkTableEntry m_xEntry;
    private double m_y;
    private NetworkTableEntry m_yEntry;

    MechanismRoot2d(String name, double x, double y) {
        this.m_name = name;
        this.m_x = x;
        this.m_y = y;
    }

    public synchronized <T extends MechanismObject2d> T append(T object) {
        if (this.m_objects.containsKey(object.getName())) {
            throw new UnsupportedOperationException("Mechanism object names must be unique!");
        }
        this.m_objects.put(object.getName(), object);
        if (this.m_table != null) {
            object.update(this.m_table.getSubTable(object.getName()));
        }
        return object;
    }

    public synchronized void setPosition(double x, double y) {
        this.m_x = x;
        this.m_y = y;
    }

    synchronized void update(NetworkTable table) {
        this.m_table = table;
        this.m_xEntry = this.m_table.getEntry("x");
        this.m_yEntry = this.m_table.getEntry("y");
        this.flush();
        for (MechanismObject2d obj : this.m_objects.values()) {
            obj.update(this.m_table.getSubTable(obj.getName()));
        }
    }

    public String getName() {
        return this.m_name;
    }

    private void flush() {
        if (this.m_xEntry != null) {
            this.m_xEntry.setDouble(this.m_x);
        }
        if (this.m_yEntry != null) {
            this.m_yEntry.setDouble(this.m_y);
        }
    }
}

