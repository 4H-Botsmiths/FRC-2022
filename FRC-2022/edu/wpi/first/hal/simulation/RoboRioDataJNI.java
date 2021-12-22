/*
 * Decompiled with CFR 0.150.
 */
package edu.wpi.first.hal.simulation;

import edu.wpi.first.hal.JNIWrapper;
import edu.wpi.first.hal.simulation.NotifyCallback;

public class RoboRioDataJNI
extends JNIWrapper {
    public static native int registerFPGAButtonCallback(NotifyCallback var0, boolean var1);

    public static native void cancelFPGAButtonCallback(int var0);

    public static native boolean getFPGAButton();

    public static native void setFPGAButton(boolean var0);

    public static native int registerVInVoltageCallback(NotifyCallback var0, boolean var1);

    public static native void cancelVInVoltageCallback(int var0);

    public static native double getVInVoltage();

    public static native void setVInVoltage(double var0);

    public static native int registerVInCurrentCallback(NotifyCallback var0, boolean var1);

    public static native void cancelVInCurrentCallback(int var0);

    public static native double getVInCurrent();

    public static native void setVInCurrent(double var0);

    public static native int registerUserVoltage6VCallback(NotifyCallback var0, boolean var1);

    public static native void cancelUserVoltage6VCallback(int var0);

    public static native double getUserVoltage6V();

    public static native void setUserVoltage6V(double var0);

    public static native int registerUserCurrent6VCallback(NotifyCallback var0, boolean var1);

    public static native void cancelUserCurrent6VCallback(int var0);

    public static native double getUserCurrent6V();

    public static native void setUserCurrent6V(double var0);

    public static native int registerUserActive6VCallback(NotifyCallback var0, boolean var1);

    public static native void cancelUserActive6VCallback(int var0);

    public static native boolean getUserActive6V();

    public static native void setUserActive6V(boolean var0);

    public static native int registerUserVoltage5VCallback(NotifyCallback var0, boolean var1);

    public static native void cancelUserVoltage5VCallback(int var0);

    public static native double getUserVoltage5V();

    public static native void setUserVoltage5V(double var0);

    public static native int registerUserCurrent5VCallback(NotifyCallback var0, boolean var1);

    public static native void cancelUserCurrent5VCallback(int var0);

    public static native double getUserCurrent5V();

    public static native void setUserCurrent5V(double var0);

    public static native int registerUserActive5VCallback(NotifyCallback var0, boolean var1);

    public static native void cancelUserActive5VCallback(int var0);

    public static native boolean getUserActive5V();

    public static native void setUserActive5V(boolean var0);

    public static native int registerUserVoltage3V3Callback(NotifyCallback var0, boolean var1);

    public static native void cancelUserVoltage3V3Callback(int var0);

    public static native double getUserVoltage3V3();

    public static native void setUserVoltage3V3(double var0);

    public static native int registerUserCurrent3V3Callback(NotifyCallback var0, boolean var1);

    public static native void cancelUserCurrent3V3Callback(int var0);

    public static native double getUserCurrent3V3();

    public static native void setUserCurrent3V3(double var0);

    public static native int registerUserActive3V3Callback(NotifyCallback var0, boolean var1);

    public static native void cancelUserActive3V3Callback(int var0);

    public static native boolean getUserActive3V3();

    public static native void setUserActive3V3(boolean var0);

    public static native int registerUserFaults6VCallback(NotifyCallback var0, boolean var1);

    public static native void cancelUserFaults6VCallback(int var0);

    public static native int getUserFaults6V();

    public static native void setUserFaults6V(int var0);

    public static native int registerUserFaults5VCallback(NotifyCallback var0, boolean var1);

    public static native void cancelUserFaults5VCallback(int var0);

    public static native int getUserFaults5V();

    public static native void setUserFaults5V(int var0);

    public static native int registerUserFaults3V3Callback(NotifyCallback var0, boolean var1);

    public static native void cancelUserFaults3V3Callback(int var0);

    public static native int getUserFaults3V3();

    public static native void setUserFaults3V3(int var0);

    public static native int registerBrownoutVoltageCallback(NotifyCallback var0, boolean var1);

    public static native void cancelBrownoutVoltageCallback(int var0);

    public static native double getBrownoutVoltage();

    public static native void setBrownoutVoltage(double var0);

    public static native void resetData();
}

