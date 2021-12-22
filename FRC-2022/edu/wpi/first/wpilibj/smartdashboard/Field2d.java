/*
 * Decompiled with CFR 0.150.
 */
package edu.wpi.first.wpilibj.smartdashboard;

import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.networktables.NTSendable;
import edu.wpi.first.networktables.NTSendableBuilder;
import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.util.sendable.SendableRegistry;
import edu.wpi.first.wpilibj.smartdashboard.FieldObject2d;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Field2d
implements NTSendable {
    private NetworkTable m_table;
    private final List<FieldObject2d> m_objects = new ArrayList<FieldObject2d>();

    public Field2d() {
        FieldObject2d obj = new FieldObject2d("Robot");
        obj.setPose(new Pose2d());
        this.m_objects.add(obj);
        SendableRegistry.add(this, "Field");
    }

    public synchronized void setRobotPose(Pose2d pose) {
        this.m_objects.get(0).setPose(pose);
    }

    public synchronized void setRobotPose(double xMeters, double yMeters, Rotation2d rotation) {
        this.m_objects.get(0).setPose(xMeters, yMeters, rotation);
    }

    public synchronized Pose2d getRobotPose() {
        return this.m_objects.get(0).getPose();
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public synchronized FieldObject2d getObject(String name) {
        for (FieldObject2d obj : this.m_objects) {
            if (!obj.m_name.equals(name)) continue;
            return obj;
        }
        FieldObject2d obj = new FieldObject2d(name);
        this.m_objects.add(obj);
        if (this.m_table != null) {
            FieldObject2d fieldObject2d = obj;
            synchronized (fieldObject2d) {
                obj.m_entry = this.m_table.getEntry(name);
            }
        }
        return obj;
    }

    public synchronized FieldObject2d getRobotObject() {
        return this.m_objects.get(0);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public void initSendable(NTSendableBuilder builder) {
        builder.setSmartDashboardType("Field2d");
        Field2d field2d = this;
        synchronized (field2d) {
            this.m_table = builder.getTable();
            Iterator<FieldObject2d> iterator = this.m_objects.iterator();
            while (iterator.hasNext()) {
                FieldObject2d obj;
                FieldObject2d fieldObject2d = obj = iterator.next();
                synchronized (fieldObject2d) {
                    obj.m_entry = this.m_table.getEntry(obj.m_name);
                    obj.updateEntry(true);
                }
            }
        }
    }
}

