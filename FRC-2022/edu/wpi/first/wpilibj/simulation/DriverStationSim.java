/*
 * Decompiled with CFR 0.150.
 */
package edu.wpi.first.wpilibj.simulation;

import edu.wpi.first.hal.AllianceStationID;
import edu.wpi.first.hal.simulation.DriverStationDataJNI;
import edu.wpi.first.hal.simulation.NotifyCallback;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.simulation.CallbackStore;

public final class DriverStationSim {
    private DriverStationSim() {
        throw new UnsupportedOperationException("This is a utility class!");
    }

    public static CallbackStore registerEnabledCallback(NotifyCallback callback, boolean initialNotify) {
        int uid = DriverStationDataJNI.registerEnabledCallback(callback, initialNotify);
        return new CallbackStore(uid, DriverStationDataJNI::cancelEnabledCallback);
    }

    public static boolean getEnabled() {
        return DriverStationDataJNI.getEnabled();
    }

    public static void setEnabled(boolean enabled) {
        DriverStationDataJNI.setEnabled(enabled);
    }

    public static CallbackStore registerAutonomousCallback(NotifyCallback callback, boolean initialNotify) {
        int uid = DriverStationDataJNI.registerAutonomousCallback(callback, initialNotify);
        return new CallbackStore(uid, DriverStationDataJNI::cancelAutonomousCallback);
    }

    public static boolean getAutonomous() {
        return DriverStationDataJNI.getAutonomous();
    }

    public static void setAutonomous(boolean autonomous) {
        DriverStationDataJNI.setAutonomous(autonomous);
    }

    public static CallbackStore registerTestCallback(NotifyCallback callback, boolean initialNotify) {
        int uid = DriverStationDataJNI.registerTestCallback(callback, initialNotify);
        return new CallbackStore(uid, DriverStationDataJNI::cancelTestCallback);
    }

    public static boolean getTest() {
        return DriverStationDataJNI.getTest();
    }

    public static void setTest(boolean test) {
        DriverStationDataJNI.setTest(test);
    }

    public static CallbackStore registerEStopCallback(NotifyCallback callback, boolean initialNotify) {
        int uid = DriverStationDataJNI.registerEStopCallback(callback, initialNotify);
        return new CallbackStore(uid, DriverStationDataJNI::cancelEStopCallback);
    }

    public static boolean getEStop() {
        return DriverStationDataJNI.getEStop();
    }

    public static void setEStop(boolean eStop) {
        DriverStationDataJNI.setEStop(eStop);
    }

    public static CallbackStore registerFmsAttachedCallback(NotifyCallback callback, boolean initialNotify) {
        int uid = DriverStationDataJNI.registerFmsAttachedCallback(callback, initialNotify);
        return new CallbackStore(uid, DriverStationDataJNI::cancelFmsAttachedCallback);
    }

    public static boolean getFmsAttached() {
        return DriverStationDataJNI.getFmsAttached();
    }

    public static void setFmsAttached(boolean fmsAttached) {
        DriverStationDataJNI.setFmsAttached(fmsAttached);
    }

    public static CallbackStore registerDsAttachedCallback(NotifyCallback callback, boolean initialNotify) {
        int uid = DriverStationDataJNI.registerDsAttachedCallback(callback, initialNotify);
        return new CallbackStore(uid, DriverStationDataJNI::cancelDsAttachedCallback);
    }

    public static boolean getDsAttached() {
        return DriverStationDataJNI.getDsAttached();
    }

    public static void setDsAttached(boolean dsAttached) {
        DriverStationDataJNI.setDsAttached(dsAttached);
    }

    public static CallbackStore registerAllianceStationIdCallback(NotifyCallback callback, boolean initialNotify) {
        int uid = DriverStationDataJNI.registerAllianceStationIdCallback(callback, initialNotify);
        return new CallbackStore(uid, DriverStationDataJNI::cancelAllianceStationIdCallback);
    }

    public static AllianceStationID getAllianceStationId() {
        switch (DriverStationDataJNI.getAllianceStationId()) {
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

    public static void setAllianceStationId(AllianceStationID allianceStationId) {
        int allianceStation;
        switch (allianceStationId) {
            case Red1: {
                allianceStation = 0;
                break;
            }
            case Red2: {
                allianceStation = 1;
                break;
            }
            case Red3: {
                allianceStation = 2;
                break;
            }
            case Blue1: {
                allianceStation = 3;
                break;
            }
            case Blue2: {
                allianceStation = 4;
                break;
            }
            case Blue3: {
                allianceStation = 5;
                break;
            }
            default: {
                return;
            }
        }
        DriverStationDataJNI.setAllianceStationId(allianceStation);
    }

    public static CallbackStore registerMatchTimeCallback(NotifyCallback callback, boolean initialNotify) {
        int uid = DriverStationDataJNI.registerMatchTimeCallback(callback, initialNotify);
        return new CallbackStore(uid, DriverStationDataJNI::cancelMatchTimeCallback);
    }

    public static double getMatchTime() {
        return DriverStationDataJNI.getMatchTime();
    }

    public static void setMatchTime(double matchTime) {
        DriverStationDataJNI.setMatchTime(matchTime);
    }

    public static void notifyNewData() {
        DriverStationDataJNI.notifyNewData();
        DriverStation.waitForData();
    }

    public static void setSendError(boolean shouldSend) {
        DriverStationDataJNI.setSendError(shouldSend);
    }

    public static void setSendConsoleLine(boolean shouldSend) {
        DriverStationDataJNI.setSendConsoleLine(shouldSend);
    }

    public static long getJoystickOutputs(int stick) {
        return DriverStationDataJNI.getJoystickOutputs(stick);
    }

    public static int getJoystickRumble(int stick, int rumbleNum) {
        return DriverStationDataJNI.getJoystickRumble(stick, rumbleNum);
    }

    public static void setJoystickButton(int stick, int button, boolean state) {
        DriverStationDataJNI.setJoystickButton(stick, button, state);
    }

    public static void setJoystickAxis(int stick, int axis, double value) {
        DriverStationDataJNI.setJoystickAxis(stick, axis, value);
    }

    public static void setJoystickPOV(int stick, int pov, int value) {
        DriverStationDataJNI.setJoystickPOV(stick, pov, value);
    }

    public static void setJoystickButtons(int stick, int buttons) {
        DriverStationDataJNI.setJoystickButtonsValue(stick, buttons);
    }

    public static void setJoystickAxisCount(int stick, int count) {
        DriverStationDataJNI.setJoystickAxisCount(stick, count);
    }

    public static void setJoystickPOVCount(int stick, int count) {
        DriverStationDataJNI.setJoystickPOVCount(stick, count);
    }

    public static void setJoystickButtonCount(int stick, int count) {
        DriverStationDataJNI.setJoystickButtonCount(stick, count);
    }

    public static void setJoystickIsXbox(int stick, boolean isXbox) {
        DriverStationDataJNI.setJoystickIsXbox(stick, isXbox);
    }

    public static void setJoystickType(int stick, int type) {
        DriverStationDataJNI.setJoystickType(stick, type);
    }

    public static void setJoystickName(int stick, String name) {
        DriverStationDataJNI.setJoystickName(stick, name);
    }

    public static void setJoystickAxisType(int stick, int axis, int type) {
        DriverStationDataJNI.setJoystickAxisType(stick, axis, type);
    }

    public static void setGameSpecificMessage(String message) {
        DriverStationDataJNI.setGameSpecificMessage(message);
    }

    public static void setEventName(String name) {
        DriverStationDataJNI.setEventName(name);
    }

    public static void setMatchType(DriverStation.MatchType type) {
        int matchType;
        switch (type) {
            case Practice: {
                matchType = 1;
                break;
            }
            case Qualification: {
                matchType = 2;
                break;
            }
            case Elimination: {
                matchType = 3;
                break;
            }
            case None: {
                matchType = 0;
                break;
            }
            default: {
                return;
            }
        }
        DriverStationDataJNI.setMatchType(matchType);
    }

    public static void setMatchNumber(int matchNumber) {
        DriverStationDataJNI.setMatchNumber(matchNumber);
    }

    public static void setReplayNumber(int replayNumber) {
        DriverStationDataJNI.setReplayNumber(replayNumber);
    }

    public static void resetData() {
        DriverStationDataJNI.resetData();
    }
}

