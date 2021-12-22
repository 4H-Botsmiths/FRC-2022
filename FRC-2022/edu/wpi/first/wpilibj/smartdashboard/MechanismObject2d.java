/*
 * Decompiled with CFR 0.150.
 */
package edu.wpi.first.wpilibj.smartdashboard;

import edu.wpi.first.networktables.NetworkTable;
import java.util.HashMap;
import java.util.Map;

public abstract class MechanismObject2d {
    private final String m_name;
    private NetworkTable m_table;
    private final Map<String, MechanismObject2d> m_objects = new HashMap<String, MechanismObject2d>(1);

    protected MechanismObject2d(String name) {
        this.m_name = name;
    }

    public final synchronized <T extends MechanismObject2d> T append(T object) {
        if (this.m_objects.containsKey(object.getName())) {
            throw new UnsupportedOperationException("Mechanism object names must be unique!");
        }
        this.m_objects.put(object.getName(), object);
        if (this.m_table != null) {
            object.update(this.m_table.getSubTable(object.getName()));
        }
        return object;
    }

    final synchronized void update(NetworkTable table) {
        this.m_table = table;
        this.updateEntries(this.m_table);
        for (MechanismObject2d obj : this.m_objects.values()) {
            obj.update(this.m_table.getSubTable(obj.m_name));
        }
    }

    protected abstract void updateEntries(NetworkTable var1);

    public final String getName() {
        return this.m_name;
    }
}

