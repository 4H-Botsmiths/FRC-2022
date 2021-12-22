/*
 * Decompiled with CFR 0.150.
 */
package edu.wpi.first.wpilibj.shuffleboard;

import edu.wpi.first.wpilibj.shuffleboard.WidgetType;

public enum BuiltInWidgets implements WidgetType
{
    kTextView("Text View"),
    kNumberSlider("Number Slider"),
    kNumberBar("Number Bar"),
    kDial("Simple Dial"),
    kGraph("Graph"),
    kBooleanBox("Boolean Box"),
    kToggleButton("Toggle Button"),
    kToggleSwitch("Toggle Switch"),
    kVoltageView("Voltage View"),
    kPowerDistribution("PDP"),
    kComboBoxChooser("ComboBox Chooser"),
    kSplitButtonChooser("Split Button Chooser"),
    kEncoder("Encoder"),
    kMotorController("Motor Controller"),
    kCommand("Command"),
    kPIDCommand("PID Command"),
    kPIDController("PID Controller"),
    kAccelerometer("Accelerometer"),
    k3AxisAccelerometer("3-Axis Accelerometer"),
    kGyro("Gyro"),
    kRelay("Relay"),
    kDifferentialDrive("Differential Drivebase"),
    kMecanumDrive("Mecanum Drivebase"),
    kCameraStream("Camera Stream");

    private final String m_widgetName;

    private BuiltInWidgets(String widgetName) {
        this.m_widgetName = widgetName;
    }

    @Override
    public String getWidgetName() {
        return this.m_widgetName;
    }
}

