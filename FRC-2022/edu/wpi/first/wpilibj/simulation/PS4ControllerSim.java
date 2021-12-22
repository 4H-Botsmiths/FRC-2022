/*
 * Decompiled with CFR 0.150.
 */
package edu.wpi.first.wpilibj.simulation;

import edu.wpi.first.wpilibj.PS4Controller;
import edu.wpi.first.wpilibj.simulation.GenericHIDSim;

public class PS4ControllerSim
extends GenericHIDSim {
    public PS4ControllerSim(PS4Controller joystick) {
        super(joystick);
        this.setAxisCount(6);
        this.setButtonCount(14);
    }

    public PS4ControllerSim(int port) {
        super(port);
        this.setAxisCount(6);
        this.setButtonCount(14);
    }

    public void setLeftX(double value) {
        this.setRawAxis(PS4Controller.Axis.kLeftX.value, value);
    }

    public void setRightX(double value) {
        this.setRawAxis(PS4Controller.Axis.kRightX.value, value);
    }

    public void setLeftY(double value) {
        this.setRawAxis(PS4Controller.Axis.kLeftY.value, value);
    }

    public void setRightY(double value) {
        this.setRawAxis(PS4Controller.Axis.kRightY.value, value);
    }

    public void setL2Axis(double value) {
        this.setRawAxis(PS4Controller.Axis.kL2.value, value);
    }

    public void setR2Axis(double value) {
        this.setRawAxis(PS4Controller.Axis.kR2.value, value);
    }

    public void setSquareButton(boolean value) {
        this.setRawButton(PS4Controller.Button.kSquare.value, value);
    }

    public void setCrossButton(boolean value) {
        this.setRawButton(PS4Controller.Button.kCross.value, value);
    }

    public void setCircleButton(boolean value) {
        this.setRawButton(PS4Controller.Button.kCircle.value, value);
    }

    public void setTriangleButton(boolean value) {
        this.setRawButton(PS4Controller.Button.kTriangle.value, value);
    }

    public void setL1Button(boolean value) {
        this.setRawButton(PS4Controller.Button.kL1.value, value);
    }

    public void setR1Button(boolean value) {
        this.setRawButton(PS4Controller.Button.kR1.value, value);
    }

    public void setL2Button(boolean value) {
        this.setRawButton(PS4Controller.Button.kL2.value, value);
    }

    public void setR2Button(boolean value) {
        this.setRawButton(PS4Controller.Button.kR2.value, value);
    }

    public void setShareButton(boolean value) {
        this.setRawButton(PS4Controller.Button.kShare.value, value);
    }

    public void setOptionsButton(boolean value) {
        this.setRawButton(PS4Controller.Button.kOptions.value, value);
    }

    public void setL3Button(boolean value) {
        this.setRawButton(PS4Controller.Button.kL3.value, value);
    }

    public void setR3Button(boolean value) {
        this.setRawButton(PS4Controller.Button.kR3.value, value);
    }

    public void setPSButton(boolean value) {
        this.setRawButton(PS4Controller.Button.kPS.value, value);
    }

    public void setTouchpad(boolean value) {
        this.setRawButton(PS4Controller.Button.kTouchpad.value, value);
    }
}

