/*
 * Decompiled with CFR 0.150.
 */
package edu.wpi.first.hal;

import edu.wpi.first.hal.AllianceStationID;
import edu.wpi.first.hal.ControlWord;
import edu.wpi.first.hal.JNIWrapper;
import edu.wpi.first.hal.MatchInfoData;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;

public final class HAL
extends JNIWrapper {
    private static final List<Runnable> s_simPeriodicBefore = new ArrayList<Runnable>();
    private static final List<Runnable> s_simPeriodicAfter = new ArrayList<Runnable>();
    public static final int kMaxJoystickAxes = 12;
    public static final int kMaxJoystickPOVs = 12;

    public static native void waitForDSData();

    public static native boolean initialize(int var0, int var1);

    public static native void shutdown();

    public static native boolean hasMain();

    public static native void runMain();

    public static native void exitMain();

    private static native void simPeriodicBeforeNative();

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public static SimPeriodicBeforeCallback registerSimPeriodicBeforeCallback(Runnable r) {
        List<Runnable> list = s_simPeriodicBefore;
        synchronized (list) {
            s_simPeriodicBefore.add(r);
        }
        return new SimPeriodicBeforeCallback(r);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public static void simPeriodicBefore() {
        HAL.simPeriodicBeforeNative();
        List<Runnable> list = s_simPeriodicBefore;
        synchronized (list) {
            for (Runnable r : s_simPeriodicBefore) {
                r.run();
            }
        }
    }

    private static native void simPeriodicAfterNative();

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public static SimPeriodicAfterCallback registerSimPeriodicAfterCallback(Runnable r) {
        List<Runnable> list = s_simPeriodicAfter;
        synchronized (list) {
            s_simPeriodicAfter.add(r);
        }
        return new SimPeriodicAfterCallback(r);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public static void simPeriodicAfter() {
        HAL.simPeriodicAfterNative();
        List<Runnable> list = s_simPeriodicAfter;
        synchronized (list) {
            for (Runnable r : s_simPeriodicAfter) {
                r.run();
            }
        }
    }

    public static native void observeUserProgramStarting();

    public static native void observeUserProgramDisabled();

    public static native void observeUserProgramAutonomous();

    public static native void observeUserProgramTeleop();

    public static native void observeUserProgramTest();

    public static void report(int resource, int instanceNumber) {
        HAL.report(resource, instanceNumber, 0, "");
    }

    public static void report(int resource, int instanceNumber, int context) {
        HAL.report(resource, instanceNumber, context, "");
    }

    public static native int report(int var0, int var1, int var2, String var3);

    public static native int nativeGetControlWord();

    public static void getControlWord(ControlWord controlWord) {
        int word = HAL.nativeGetControlWord();
        controlWord.update((word & 1) != 0, (word >> 1 & 1) != 0, (word >> 2 & 1) != 0, (word >> 3 & 1) != 0, (word >> 4 & 1) != 0, (word >> 5 & 1) != 0);
    }

    private static native int nativeGetAllianceStation();

    public static AllianceStationID getAllianceStation() {
        switch (HAL.nativeGetAllianceStation()) {
            case 0: {
                return AllianceStationID.Red1;
            }
            case 1: {
                return AllianceStationID.Red2;
            }
            case 2: {
                return AllianceStationID.Red3;
            }
            case 3: {
                return AllianceStationID.Blue1;
            }
            case 4: {
                return AllianceStationID.Blue2;
            }
            case 5: {
                return AllianceStationID.Blue3;
            }
        }
        return null;
    }

    public static native boolean isNewControlData();

    public static native void releaseDSMutex();

    public static native boolean waitForDSDataTimeout(double var0);

    public static native short getJoystickAxes(byte var0, float[] var1);

    public static native short getJoystickPOVs(byte var0, short[] var1);

    public static native int getJoystickButtons(byte var0, ByteBuffer var1);

    public static native int setJoystickOutputs(byte var0, int var1, short var2, short var3);

    public static native int getJoystickIsXbox(byte var0);

    public static native int getJoystickType(byte var0);

    public static native String getJoystickName(byte var0);

    public static native int getJoystickAxisType(byte var0, byte var1);

    public static native double getMatchTime();

    public static native boolean getSystemActive();

    public static native boolean getBrownedOut();

    public static native int getMatchInfo(MatchInfoData var0);

    public static native int sendError(boolean var0, int var1, boolean var2, String var3, String var4, String var5, boolean var6);

    public static native int sendConsoleLine(String var0);

    public static native int getPortWithModule(byte var0, byte var1);

    public static native int getPort(byte var0);

    private HAL() {
    }

    public static class SimPeriodicAfterCallback
    implements AutoCloseable {
        private final Runnable m_run;

        private SimPeriodicAfterCallback(Runnable r) {
            this.m_run = r;
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public void close() {
            List<Runnable> list = s_simPeriodicAfter;
            synchronized (list) {
                s_simPeriodicAfter.remove(this.m_run);
            }
        }
    }

    public static class SimPeriodicBeforeCallback
    implements AutoCloseable {
        private final Runnable m_run;

        private SimPeriodicBeforeCallback(Runnable r) {
            this.m_run = r;
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public void close() {
            List<Runnable> list = s_simPeriodicBefore;
            synchronized (list) {
                s_simPeriodicBefore.remove(this.m_run);
            }
        }
    }
}

