/*
 * Decompiled with CFR 0.150.
 */
package edu.wpi.first.wpilibj.smartdashboard;

import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.math.trajectory.Trajectory;
import edu.wpi.first.networktables.NetworkTableEntry;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.ArrayList;
import java.util.List;

public class FieldObject2d {
    String m_name;
    NetworkTableEntry m_entry;
    private final List<Pose2d> m_poses = new ArrayList<Pose2d>();

    FieldObject2d(String name) {
        this.m_name = name;
    }

    public synchronized void setPose(Pose2d pose) {
        this.setPoses(pose);
    }

    public synchronized void setPose(double xMeters, double yMeters, Rotation2d rotation) {
        this.setPose(new Pose2d(xMeters, yMeters, rotation));
    }

    public synchronized Pose2d getPose() {
        this.updateFromEntry();
        if (this.m_poses.isEmpty()) {
            return new Pose2d();
        }
        return this.m_poses.get(0);
    }

    public synchronized void setPoses(List<Pose2d> poses) {
        this.m_poses.clear();
        for (Pose2d pose : poses) {
            this.m_poses.add(pose);
        }
        this.updateEntry();
    }

    public synchronized void setPoses(Pose2d ... poses) {
        this.m_poses.clear();
        for (Pose2d pose : poses) {
            this.m_poses.add(pose);
        }
        this.updateEntry();
    }

    public synchronized void setTrajectory(Trajectory trajectory) {
        this.m_poses.clear();
        for (Trajectory.State state : trajectory.getStates()) {
            this.m_poses.add(state.poseMeters);
        }
        this.updateEntry();
    }

    public synchronized List<Pose2d> getPoses() {
        this.updateFromEntry();
        return new ArrayList<Pose2d>(this.m_poses);
    }

    void updateEntry() {
        this.updateEntry(false);
    }

    synchronized void updateEntry(boolean setDefault) {
        if (this.m_entry == null) {
            return;
        }
        if (this.m_poses.size() < 85) {
            double[] arr = new double[this.m_poses.size() * 3];
            int ndx = 0;
            for (Pose2d pose : this.m_poses) {
                Translation2d translation = pose.getTranslation();
                arr[ndx + 0] = translation.getX();
                arr[ndx + 1] = translation.getY();
                arr[ndx + 2] = pose.getRotation().getDegrees();
                ndx += 3;
            }
            if (setDefault) {
                this.m_entry.setDefaultDoubleArray(arr);
            } else {
                this.m_entry.setDoubleArray(arr);
            }
        } else {
            ByteBuffer output = ByteBuffer.allocate(this.m_poses.size() * 3 * 8);
            output.order(ByteOrder.BIG_ENDIAN);
            for (Pose2d pose : this.m_poses) {
                Translation2d translation = pose.getTranslation();
                output.putDouble(translation.getX());
                output.putDouble(translation.getY());
                output.putDouble(pose.getRotation().getDegrees());
            }
            if (setDefault) {
                this.m_entry.setDefaultRaw(output.array());
            } else {
                this.m_entry.forceSetRaw(output.array());
            }
        }
    }

    private synchronized void updateFromEntry() {
        if (this.m_entry == null) {
            return;
        }
        double[] arr = this.m_entry.getDoubleArray((double[])null);
        if (arr != null) {
            if (arr.length % 3 != 0) {
                return;
            }
            this.m_poses.clear();
            for (int i = 0; i < arr.length; i += 3) {
                this.m_poses.add(new Pose2d(arr[i], arr[i + 1], Rotation2d.fromDegrees(arr[i + 2])));
            }
        } else {
            byte[] data = this.m_entry.getRaw(null);
            if (data == null) {
                return;
            }
            if (data.length % 24 != 0) {
                return;
            }
            ByteBuffer input = ByteBuffer.wrap(data);
            input.order(ByteOrder.BIG_ENDIAN);
            this.m_poses.clear();
            for (int i = 0; i < data.length / 24; ++i) {
                double x = input.getDouble();
                double y = input.getDouble();
                double rot = input.getDouble();
                this.m_poses.add(new Pose2d(x, y, Rotation2d.fromDegrees(rot)));
            }
        }
    }
}

