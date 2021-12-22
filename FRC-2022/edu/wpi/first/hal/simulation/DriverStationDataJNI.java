/*
 * Decompiled with CFR 0.150.
 */
package edu.wpi.first.hal.simulation;

import edu.wpi.first.hal.JNIWrapper;
import edu.wpi.first.hal.simulation.NotifyCallback;

public class DriverStationDataJNI
extends JNIWrapper {
    public static native int registerEnabledCallback(NotifyCallback var0, boolean var1);

    public static native void cancelEnabledCallback(int var0);

    public static native boolean getEnabled();

    public static native void setEnabled(boolean var0);

    public static native int registerAutonomousCallback(NotifyCallback var0, boolean var1);

    public static native void cancelAutonomousCallback(int var0);

    public static native boolean getAutonomous();

    public static native void setAutonomous(boolean var0);

    public static native int registerTestCallback(NotifyCallback var0, boolean var1);

    public static native void cancelTestCallback(int var0);

    public static native boolean getTest();

    public static native void setTest(boolean var0);

    public static native int registerEStopCallback(NotifyCallback var0, boolean var1);

    public static native void cancelEStopCallback(int var0);

    public static native boolean getEStop();

    public static native void setEStop(boolean var0);

    public static native int registerFmsAttachedCallback(NotifyCallback var0, boolean var1);

    public static native void cancelFmsAttachedCallback(int var0);

    public static native boolean getFmsAttached();

    public static native void setFmsAttached(boolean var0);

    public static native int registerDsAttachedCallback(NotifyCallback var0, boolean var1);

    public static native void cancelDsAttachedCallback(int var0);

    public static native boolean getDsAttached();

    public static native void setDsAttached(boolean var0);

    public static native int registerAllianceStationIdCallback(NotifyCallback var0, boolean var1);

    public static native void cancelAllianceStationIdCallback(int var0);

    public static native int getAllianceStationId();

    public static native void setAllianceStationId(int var0);

    public static native int registerMatchTimeCallback(NotifyCallback var0, boolean var1);

    public static native void cancelMatchTimeCallback(int var0);

    public static native double getMatchTime();

    public static native void setMatchTime(double var0);

    public static native void setJoystickAxes(byte var0, float[] var1);

    public static native void setJoystickPOVs(byte var0, short[] var1);

    public static native void setJoystickButtons(byte var0, int var1, int var2);

    public static native long getJoystickOutputs(int var0);

    public static native int getJoystickRumble(int var0, int var1);

    public static native void setMatchInfo(String var0, String var1, int var2, int var3, int var4);

    public static native void registerAllCallbacks(NotifyCallback var0, boolean var1);

    public static native void notifyNewData();

    public static native void setSendError(boolean var0);

    public static native void setSendConsoleLine(boolean var0);

    public static native void setJoystickButton(int var0, int var1, boolean var2);

    public static native void setJoystickAxis(int var0, int var1, double var2);

    public static native void setJoystickPOV(int var0, int var1, int var2);

    public static native void setJoystickButtonsValue(int var0, int var1);

    public static native void setJoystickAxisCount(int var0, int var1);

    public static native void setJoystickPOVCount(int var0, int var1);

    public static native void setJoystickButtonCount(int var0, int var1);

    public static native void setJoystickIsXbox(int var0, boolean var1);

    public static native void setJoystickType(int var0, int var1);

    public static native void setJoystickName(int var0, String var1);

    public static native void setJoystickAxisType(int var0, int var1, int var2);

    public static native void setGameSpecificMessage(String var0);

    public static native void setEventName(String var0);

    public static native void setMatchType(int var0);

    public static native void setMatchNumber(int var0);

    public static native void setReplayNumber(int var0);

    public static native void resetData();
}

