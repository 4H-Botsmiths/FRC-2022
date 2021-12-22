/*
 * Decompiled with CFR 0.150.
 */
package edu.wpi.first.wpilibj;

import edu.wpi.first.hal.HAL;
import edu.wpi.first.wpilibj.GenericHID;

public class PS4Controller
extends GenericHID {
    public PS4Controller(int port) {
        super(port);
        HAL.report(94, port + 1);
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

    public double getL2Axis() {
        return this.getRawAxis(Axis.kL2.value);
    }

    public double getR2Axis() {
        return this.getRawAxis(Axis.kR2.value);
    }

    public boolean getL2Button() {
        return this.getRawButton(Button.kL2.value);
    }

    public boolean getR2Button() {
        return this.getRawButton(Button.kR2.value);
    }

    public boolean getL2ButtonPressed() {
        return this.getRawButtonPressed(Button.kL2.value);
    }

    public boolean getR2ButtonPressed() {
        return this.getRawButtonPressed(Button.kR2.value);
    }

    public boolean getL2ButtonReleased() {
        return this.getRawButtonReleased(Button.kL2.value);
    }

    public boolean getR2ButtonReleased() {
        return this.getRawButtonReleased(Button.kR2.value);
    }

    public boolean getL1Button() {
        return this.getRawButton(Button.kL1.value);
    }

    public boolean getR1Button() {
        return this.getRawButton(Button.kR1.value);
    }

    public boolean getL1ButtonPressed() {
        return this.getRawButtonPressed(Button.kL1.value);
    }

    public boolean getR1ButtonPressed() {
        return this.getRawButtonPressed(Button.kR1.value);
    }

    public boolean getL1ButtonReleased() {
        return this.getRawButtonReleased(Button.kL1.value);
    }

    public boolean getR1ButtonReleased() {
        return this.getRawButtonReleased(Button.kR1.value);
    }

    public boolean getL3Button() {
        return this.getRawButton(Button.kL3.value);
    }

    public boolean getR3Button() {
        return this.getRawButton(Button.kR3.value);
    }

    public boolean getL3ButtonPressed() {
        return this.getRawButtonPressed(Button.kL3.value);
    }

    public boolean getR3ButtonPressed() {
        return this.getRawButtonPressed(Button.kR3.value);
    }

    public boolean getL3ButtonReleased() {
        return this.getRawButtonReleased(Button.kL3.value);
    }

    public boolean getR3ButtonReleased() {
        return this.getRawButtonReleased(Button.kR3.value);
    }

    public boolean getSquareButton() {
        return this.getRawButton(Button.kSquare.value);
    }

    public boolean getSquareButtonPressed() {
        return this.getRawButtonPressed(Button.kSquare.value);
    }

    public boolean getSquareButtonReleased() {
        return this.getRawButtonReleased(Button.kSquare.value);
    }

    public boolean getCrossButton() {
        return this.getRawButton(Button.kCross.value);
    }

    public boolean getCrossButtonPressed() {
        return this.getRawButtonPressed(Button.kCross.value);
    }

    public boolean getCrossButtonReleased() {
        return this.getRawButtonReleased(Button.kCross.value);
    }

    public boolean getTriangleButton() {
        return this.getRawButton(Button.kTriangle.value);
    }

    public boolean getTriangleButtonPressed() {
        return this.getRawButtonPressed(Button.kTriangle.value);
    }

    public boolean getTriangleButtonReleased() {
        return this.getRawButtonReleased(Button.kTriangle.value);
    }

    public boolean getCircleButton() {
        return this.getRawButton(Button.kCircle.value);
    }

    public boolean getCircleButtonPressed() {
        return this.getRawButtonPressed(Button.kCircle.value);
    }

    public boolean getCircleButtonReleased() {
        return this.getRawButtonReleased(Button.kCircle.value);
    }

    public boolean getShareButton() {
        return this.getRawButton(Button.kShare.value);
    }

    public boolean getShareButtonPressed() {
        return this.getRawButtonPressed(Button.kShare.value);
    }

    public boolean getShareButtonReleased() {
        return this.getRawButtonReleased(Button.kShare.value);
    }

    public boolean getPSButton() {
        return this.getRawButton(Button.kPS.value);
    }

    public boolean getPSButtonPressed() {
        return this.getRawButtonPressed(Button.kPS.value);
    }

    public boolean getPSButtonReleased() {
        return this.getRawButtonReleased(Button.kPS.value);
    }

    public boolean getOptionsButton() {
        return this.getRawButton(Button.kOptions.value);
    }

    public boolean getOptionsButtonPressed() {
        return this.getRawButtonPressed(Button.kOptions.value);
    }

    public boolean getOptionsButtonReleased() {
        return this.getRawButtonReleased(Button.kOptions.value);
    }

    public boolean getTouchpad() {
        return this.getRawButton(Button.kTouchpad.value);
    }

    public boolean getTouchpadPressed() {
        return this.getRawButtonPressed(Button.kTouchpad.value);
    }

    public boolean getTouchpadReleased() {
        return this.getRawButtonReleased(Button.kTouchpad.value);
    }

    public static enum Axis {
        kLeftX(0),
        kLeftY(1),
        kRightX(2),
        kRightY(5),
        kL2(3),
        kR2(4);

        public final int value;

        private Axis(int index) {
            this.value = index;
        }

        public String toString() {
            String name = this.name().substring(1);
            if (name.endsWith("2")) {
                return name + "Axis";
            }
            return name;
        }
    }

    public static enum Button {
        kSquare(1),
        kCross(2),
        kCircle(3),
        kTriangle(4),
        kL1(5),
        kR1(6),
        kL2(7),
        kR2(8),
        kShare(9),
        kOptions(10),
        kL3(11),
        kR3(12),
        kPS(13),
        kTouchpad(14);

        public final int value;

        private Button(int index) {
            this.value = index;
        }

        public String toString() {
            String name = this.name().substring(1);
            if (this == kTouchpad) {
                return name;
            }
            return name + "Button";
        }
    }
}

