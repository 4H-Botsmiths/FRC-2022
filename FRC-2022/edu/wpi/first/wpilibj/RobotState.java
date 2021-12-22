/*
 * Decompiled with CFR 0.150.
 */
package edu.wpi.first.wpilibj;

import edu.wpi.first.wpilibj.DriverStation;

public final class RobotState {
    public static boolean isDisabled() {
        return DriverStation.isDisabled();
    }

    public static boolean isEnabled() {
        return DriverStation.isEnabled();
    }

    public static boolean isEStopped() {
        return DriverStation.isEStopped();
    }

    @Deprecated
    public static boolean isOperatorControl() {
        return RobotState.isTeleop();
    }

    public static boolean isTeleop() {
        return DriverStation.isTeleop();
    }

    public static boolean isAutonomous() {
        return DriverStation.isAutonomous();
    }

    public static boolean isTest() {
        return DriverStation.isTest();
    }

    private RobotState() {
    }
}

