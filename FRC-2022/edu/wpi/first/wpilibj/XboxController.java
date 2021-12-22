/*
 * Decompiled with CFR 0.150.
 */
package edu.wpi.first.wpilibj;

import edu.wpi.first.hal.HAL;
import edu.wpi.first.wpilibj.GenericHID;

public class XboxController
extends GenericHID {
    public XboxController(int port) {
        super(port);
        HAL.report(72, port + 1);
    }

    public double getLeftX() {
        return this.getRawAxis(Axis.kLeftX.value);
    }

    public double getRightX() {
        return this.getRawAxis(Axis.kRightX.value);
    }

    public double getLeftY() {
        return this.getRawAxis(Axis.kLeftY.value);
    }

    public double getRightY() {
        return this.getRawAxis(Axis.kRightY.value);
    }

    public double getLeftTriggerAxis() {
        return this.getRawAxis(Axis.kLeftTrigger.value);
    }

    public double getRightTriggerAxis() {
        return this.getRawAxis(Axis.kRightTrigger.value);
    }

    public boolean getLeftBumper() {
        return this.getRawButton(Button.kLeftBumper.value);
    }

    public boolean getRightBumper() {
        return this.getRawButton(Button.kRightBumper.value);
    }

    public boolean getLeftBumperPressed() {
        return this.getRawButtonPressed(Button.kLeftBumper.value);
    }

    public boolean getRightBumperPressed() {
        return this.getRawButtonPressed(Button.kRightBumper.value);
    }

    public boolean getLeftBumperReleased() {
        return this.getRawButtonReleased(Button.kLeftBumper.value);
    }

    public boolean getRightBumperReleased() {
        return this.getRawButtonReleased(Button.kRightBumper.value);
    }

    public boolean getLeftStickButton() {
        return this.getRawButton(Button.kLeftStick.value);
    }

    public boolean getRightStickButton() {
        return this.getRawButton(Button.kRightStick.value);
    }

    public boolean getLeftStickButtonPressed() {
        return this.getRawButtonPressed(Button.kLeftStick.value);
    }

    public boolean getRightStickButtonPressed() {
        return this.getRawButtonPressed(Button.kRightStick.value);
    }

    public boolean getLeftStickButtonReleased() {
        return this.getRawButtonReleased(Button.kLeftStick.value);
    }

    public boolean getRightStickButtonReleased() {
        return this.getRawButtonReleased(Button.kRightStick.value);
    }

    public boolean getAButton() {
        return this.getRawButton(Button.kA.value);
    }

    public boolean getAButtonPressed() {
        return this.getRawButtonPressed(Button.kA.value);
    }

    public boolean getAButtonReleased() {
        return this.getRawButtonReleased(Button.kA.value);
    }

    public boolean getBButton() {
        return this.getRawButton(Button.kB.value);
    }

    public boolean getBButtonPressed() {
        return this.getRawButtonPressed(Button.kB.value);
    }

    public boolean getBButtonReleased() {
        return this.getRawButtonReleased(Button.kB.value);
    }

    public boolean getXButton() {
        return this.getRawButton(Button.kX.value);
    }

    public boolean getXButtonPressed() {
        return this.getRawButtonPressed(Button.kX.value);
    }

    public boolean getXButtonReleased() {
        return this.getRawButtonReleased(Button.kX.value);
    }

    public boolean getYButton() {
        return this.getRawButton(Button.kY.value);
    }

    public boolean getYButtonPressed() {
        return this.getRawButtonPressed(Button.kY.value);
    }

    public boolean getYButtonReleased() {
        return this.getRawButtonReleased(Button.kY.value);
    }

    public boolean getBackButton() {
        return this.getRawButton(Button.kBack.value);
    }

    public boolean getBackButtonPressed() {
        return this.getRawButtonPressed(Button.kBack.value);
    }

    public boolean getBackButtonReleased() {
        return this.getRawButtonReleased(Button.kBack.value);
    }

    public boolean getStartButton() {
        return this.getRawButton(Button.kStart.value);
    }

    public boolean getStartButtonPressed() {
        return this.getRawButtonPressed(Button.kStart.value);
    }

    public boolean getStartButtonReleased() {
        return this.getRawButtonReleased(Button.kStart.value);
    }

    public static enum Axis {
        kLeftX(0),
        kRightX(4),
        kLeftY(1),
        kRightY(5),
        kLeftTrigger(2),
        kRightTrigger(3);

        public final int value;

        private Axis(int value) {
            this.value = value;
        }

        public String toString() {
            String name = this.name().substring(1);
            if (name.endsWith("Trigger")) {
                return name + "Axis";
            }
            return name;
        }
    }

    public static enum Button {
        kLeftBumper(5),
        kRightBumper(6),
        kLeftStick(9),
        kRightStick(10),
        kA(1),
        kB(2),
        kX(3),
        kY(4),
        kBack(7),
        kStart(8);

        public final int value;

        private Button(int value) {
            this.value = value;
        }

        public String toString() {
            String name = this.name().substring(1);
            if (name.endsWith("Bumper")) {
                return name;
            }
            return name + "Button";
        }
    }
}

