/*
 * Decompiled with CFR 0.150.
 */
package edu.wpi.first.wpilibj.smartdashboard;

import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.wpilibj.smartdashboard.MechanismObject2d;
import edu.wpi.first.wpilibj.util.Color8Bit;

public class MechanismLigament2d
extends MechanismObject2d {
    private double m_angle;
    private NetworkTableEntry m_angleEntry;
    private String m_color;
    private NetworkTableEntry m_colorEntry;
    private double m_length;
    private NetworkTableEntry m_lengthEntry;
    private double m_weight;
    private NetworkTableEntry m_weightEntry;

    public MechanismLigament2d(String name, double length, double angle, double lineWidth, Color8Bit color) {
        super(name);
        this.setColor(color);
        this.setLength(length);
        this.setAngle(angle);
        this.setLineWeight(lineWidth);
    }

    public MechanismLigament2d(String name, double length, double angle) {
        this(name, length, angle, 10.0, new Color8Bit(235, 137, 52));
    }

    public synchronized void setAngle(double degrees) {
        this.m_angle = degrees;
        this.flush();
    }

    public synchronized void setAngle(Rotation2d angle) {
        this.setAngle(angle.getDegrees());
    }

    public synchronized double getAngle() {
        if (this.m_angleEntry != null) {
            this.m_angle = this.m_angleEntry.getDouble(0.0);
        }
        return this.m_angle;
    }

    public synchronized void setLength(double length) {
        this.m_length = length;
        this.flush();
    }

    public synchronized double getLength() {
        if (this.m_lengthEntry != null) {
            this.m_length = this.m_lengthEntry.getDouble(0.0);
        }
        return this.m_length;
    }

    public synchronized void setColor(Color8Bit color) {
        this.m_color = String.format("#%02X%02X%02X", color.red, color.green, color.blue);
        this.flush();
    }

    public synchronized void setLineWeight(double weight) {
        this.m_weight = weight;
        this.flush();
    }

    @Override
    protected void updateEntries(NetworkTable table) {
        table.getEntry(".type").setString("line");
        this.m_angleEntry = table.getEntry("angle");
        this.m_lengthEntry = table.getEntry("length");
        this.m_colorEntry = table.getEntry("color");
        this.m_weightEntry = table.getEntry("weight");
        this.flush();
    }

    private void flush() {
        if (this.m_angleEntry != null) {
            this.m_angleEntry.setDouble(this.m_angle);
        }
        if (this.m_lengthEntry != null) {
            this.m_lengthEntry.setDouble(this.m_length);
        }
        if (this.m_colorEntry != null) {
            this.m_colorEntry.setString(this.m_color);
        }
        if (this.m_weightEntry != null) {
            this.m_weightEntry.setDouble(this.m_weight);
        }
    }
}

