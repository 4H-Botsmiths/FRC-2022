/*
 * Decompiled with CFR 0.150.
 */
package edu.wpi.first.wpilibj;

import edu.wpi.first.hal.HAL;
import edu.wpi.first.hal.HALUtil;
import edu.wpi.first.hal.PowerJNI;
import edu.wpi.first.hal.can.CANJNI;
import edu.wpi.first.hal.can.CANStatus;

public final class RobotController {
    private RobotController() {
        throw new UnsupportedOperationException("This is a utility class!");
    }

    public static int getFPGAVersion() {
        return HALUtil.getFPGAVersion();
    }

    public static long getFPGARevision() {
        return HALUtil.getFPGARevision();
    }

    public static long getFPGATime() {
        return HALUtil.getFPGATime();
    }

    public static boolean getUserButton() {
        return HALUtil.getFPGAButton();
    }

    public static double getBatteryVoltage() {
        return PowerJNI.getVinVoltage();
    }

    public static boolean isSysActive() {
        return HAL.getSystemActive();
    }

    public static boolean isBrownedOut() {
        return HAL.getBrownedOut();
    }

    public static double getInputVoltage() {
        return PowerJNI.getVinVoltage();
    }

    public static double getInputCurrent() {
        return PowerJNI.getVinCurrent();
    }

    public static double getVoltage3V3() {
        return PowerJNI.getUserVoltage3V3();
    }

    public static double getCurrent3V3() {
        return PowerJNI.getUserCurrent3V3();
    }

    public static boolean getEnabled3V3() {
        return PowerJNI.getUserActive3V3();
    }

    public static int getFaultCount3V3() {
        return PowerJNI.getUserCurrentFaults3V3();
    }

    public static double getVoltage5V() {
        return PowerJNI.getUserVoltage5V();
    }

    public static double getCurrent5V() {
        return PowerJNI.getUserCurrent5V();
    }

    public static boolean getEnabled5V() {
        return PowerJNI.getUserActive5V();
    }

    public static int getFaultCount5V() {
        return PowerJNI.getUserCurrentFaults5V();
    }

    public static double getVoltage6V() {
        return PowerJNI.getUserVoltage6V();
    }

    public static double getCurrent6V() {
        return PowerJNI.getUserCurrent6V();
    }

    public static boolean getEnabled6V() {
        return PowerJNI.getUserActive6V();
    }

    public static int getFaultCount6V() {
        return PowerJNI.getUserCurrentFaults6V();
    }

    public static double getBrownoutVoltage() {
        return PowerJNI.getBrownoutVoltage();
    }

    public static void setBrownoutVoltage(double brownoutVoltage) {
        PowerJNI.setBrownoutVoltage(brownoutVoltage);
    }

    public static CANStatus getCANStatus() {
        CANStatus status = new CANStatus();
        CANJNI.getCANStatus(status);
        return status;
    }
}

