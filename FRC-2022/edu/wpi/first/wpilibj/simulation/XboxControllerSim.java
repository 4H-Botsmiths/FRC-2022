/*
 * Decompiled with CFR 0.150.
 */
package edu.wpi.first.wpilibj.simulation;

import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.simulation.GenericHIDSim;

public class XboxControllerSim
extends GenericHIDSim {
    public XboxControllerSim(XboxController joystick) {
        super(joystick);
        this.setAxisCount(6);
        this.setButtonCount(10);
    }

    public XboxControllerSim(int port) {
        super(port);
        this.setAxisCount(6);
        this.setButtonCount(10);
    }

    public void setLeftX(double value) {
        this.setRawAxis(XboxController.Axis.kLeftX.value, value);
    }

    public void setRightX(double value) {
        this.setRawAxis(XboxController.Axis.kRightX.value, value);
    }

    public void setLeftY(double value) {
        this.setRawAxis(XboxController.Axis.kLeftY.value, value);
    }

    public void setRightY(double value) {
        this.setRawAxis(XboxController.Axis.kRightY.value, value);
    }

    public void setLeftTriggerAxis(double value) {
        this.setRawAxis(XboxController.Axis.kLeftTrigger.value, value);
    }

    public void setRightTriggerAxis(double value) {
        this.setRawAxis(XboxController.Axis.kRightTrigger.value, value);
    }

    public void setLeftBumper(boolean state) {
        this.setRawButton(XboxController.Button.kLeftBumper.value, state);
    }

    public void setRightBumper(boolean state) {
        this.setRawButton(XboxController.Button.kRightBumper.value, state);
    }

    public void setLeftStickButton(boolean state) {
        this.setRawButton(XboxController.Button.kLeftStick.value, state);
    }

    public void setRightStickButton(boolean state) {
        this.setRawButton(XboxController.Button.kRightStick.value, state);
    }

    public void setAButton(boolean state) {
        this.setRawButton(XboxController.Button.kA.value, state);
    }

    public void setBButton(boolean state) {
        this.setRawButton(XboxController.Button.kB.value, state);
    }

    public void setXButton(boolean state) {
        this.setRawButton(XboxController.Button.kX.value, state);
    }

    public void setYButton(boolean state) {
        this.setRawButton(XboxController.Button.kY.value, state);
    }

    public void setBackButton(boolean state) {
        this.setRawButton(XboxController.Button.kBack.value, state);
    }

    public void setStartButton(boolean state) {
        this.setRawButton(XboxController.Button.kStart.value, state);
    }
}

