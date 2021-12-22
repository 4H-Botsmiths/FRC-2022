/*
 * Decompiled with CFR 0.150.
 */
package edu.wpi.first.wpilibj;

import edu.wpi.first.hal.AllianceStationID;
import edu.wpi.first.hal.ControlWord;
import edu.wpi.first.hal.HAL;
import edu.wpi.first.hal.MatchInfoData;
import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.MotorSafety;
import edu.wpi.first.wpilibj.RobotController;
import edu.wpi.first.wpilibj.Timer;
import java.nio.ByteBuffer;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class DriverStation {
    public static final int kJoystickPorts = 6;
    private static final double JOYSTICK_UNPLUGGED_MESSAGE_INTERVAL = 1.0;
    private static double m_nextMessageTime;
    private static DriverStation instance;
    private static HALJoystickAxes[] m_joystickAxes;
    private static HALJoystickPOVs[] m_joystickPOVs;
    private static HALJoystickButtons[] m_joystickButtons;
    private static MatchInfoData m_matchInfo;
    private static HALJoystickAxes[] m_joystickAxesCache;
    private static HALJoystickPOVs[] m_joystickPOVsCache;
    private static HALJoystickButtons[] m_joystickButtonsCache;
    private static MatchInfoData m_matchInfoCache;
    private static int[] m_joystickButtonsPressed;
    private static int[] m_joystickButtonsReleased;
    private static final ByteBuffer m_buttonCountBuffer;
    private static final MatchDataSender m_matchDataSender;
    private static Thread m_thread;
    private static volatile boolean m_threadKeepAlive;
    private static final ReentrantLock m_cacheDataMutex;
    private static final Lock m_waitForDataMutex;
    private static final Condition m_waitForDataCond;
    private static int m_waitForDataCount;
    private static final ThreadLocal<Integer> m_lastCount;
    private static boolean m_silenceJoystickWarning;
    private static boolean m_userInDisabled;
    private static boolean m_userInAutonomous;
    private static boolean m_userInTeleop;
    private static boolean m_userInTest;
    private static final Object m_controlWordMutex;
    private static final ControlWord m_controlWordCache;
    private static long m_lastControlWordUpdate;

    @Deprecated
    public static DriverStation getInstance() {
        return instance;
    }

    private DriverStation() {
    }

    public static synchronized void release() {
        m_threadKeepAlive = false;
        if (m_thread != null) {
            try {
                m_thread.join();
            }
            catch (InterruptedException ex) {
                Thread.currentThread().interrupt();
            }
            m_thread = null;
        }
    }

    public static void reportError(String error, boolean printTrace) {
        DriverStation.reportErrorImpl(true, 1, error, printTrace);
    }

    public static void reportError(String error, StackTraceElement[] stackTrace) {
        DriverStation.reportErrorImpl(true, 1, error, stackTrace);
    }

    public static void reportWarning(String warning, boolean printTrace) {
        DriverStation.reportErrorImpl(false, 1, warning, printTrace);
    }

    public static void reportWarning(String warning, StackTraceElement[] stackTrace) {
        DriverStation.reportErrorImpl(false, 1, warning, stackTrace);
    }

    private static void reportErrorImpl(boolean isError, int code, String error, boolean printTrace) {
        DriverStation.reportErrorImpl(isError, code, error, printTrace, Thread.currentThread().getStackTrace(), 3);
    }

    private static void reportErrorImpl(boolean isError, int code, String error, StackTraceElement[] stackTrace) {
        DriverStation.reportErrorImpl(isError, code, error, true, stackTrace, 0);
    }

    private static void reportErrorImpl(boolean isError, int code, String error, boolean printTrace, StackTraceElement[] stackTrace, int stackTraceFirst) {
        String locString = stackTrace.length >= stackTraceFirst + 1 ? stackTrace[stackTraceFirst].toString() : "";
        StringBuilder traceString = new StringBuilder();
        if (printTrace) {
            boolean haveLoc = false;
            for (int i = stackTraceFirst; i < stackTrace.length; ++i) {
                String loc = stackTrace[i].toString();
                traceString.append("\tat ").append(loc).append('\n');
                if (haveLoc || loc.startsWith("edu.wpi.first")) continue;
                locString = loc;
                haveLoc = true;
            }
        }
        HAL.sendError(isError, code, false, error, locString, traceString.toString(), true);
    }

    public static boolean getStickButton(int stick, int button) {
        if (stick < 0 || stick >= 6) {
            throw new IllegalArgumentException("Joystick index is out of range, should be 0-3");
        }
        if (button <= 0) {
            DriverStation.reportJoystickUnpluggedError("Button indexes begin at 1 in WPILib for C++ and Java\n");
            return false;
        }
        m_cacheDataMutex.lock();
        try {
            if (button <= DriverStation.m_joystickButtons[stick].m_count) {
                boolean bl = (DriverStation.m_joystickButtons[stick].m_buttons & 1 << button - 1) != 0;
                return bl;
            }
        }
        finally {
            m_cacheDataMutex.unlock();
        }
        DriverStation.reportJoystickUnpluggedWarning("Joystick Button " + button + " on port " + stick + " not available, check if controller is plugged in");
        return false;
    }

    public static boolean getStickButtonPressed(int stick, int button) {
        if (button <= 0) {
            DriverStation.reportJoystickUnpluggedError("Button indexes begin at 1 in WPILib for C++ and Java\n");
            return false;
        }
        if (stick < 0 || stick >= 6) {
            throw new IllegalArgumentException("Joystick index is out of range, should be 0-3");
        }
        m_cacheDataMutex.lock();
        try {
            if (button <= DriverStation.m_joystickButtons[stick].m_count) {
                if ((m_joystickButtonsPressed[stick] & 1 << button - 1) != 0) {
                    int n = stick;
                    m_joystickButtonsPressed[n] = m_joystickButtonsPressed[n] & ~(1 << button - 1);
                    boolean bl = true;
                    return bl;
                }
                boolean bl = false;
                return bl;
            }
        }
        finally {
            m_cacheDataMutex.unlock();
        }
        DriverStation.reportJoystickUnpluggedWarning("Joystick Button " + button + " on port " + stick + " not available, check if controller is plugged in");
        return false;
    }

    public static boolean getStickButtonReleased(int stick, int button) {
        if (button <= 0) {
            DriverStation.reportJoystickUnpluggedError("Button indexes begin at 1 in WPILib for C++ and Java\n");
            return false;
        }
        if (stick < 0 || stick >= 6) {
            throw new IllegalArgumentException("Joystick index is out of range, should be 0-3");
        }
        m_cacheDataMutex.lock();
        try {
            if (button <= DriverStation.m_joystickButtons[stick].m_count) {
                if ((m_joystickButtonsReleased[stick] & 1 << button - 1) != 0) {
                    int n = stick;
                    m_joystickButtonsReleased[n] = m_joystickButtonsReleased[n] & ~(1 << button - 1);
                    boolean bl = true;
                    return bl;
                }
                boolean bl = false;
                return bl;
            }
        }
        finally {
            m_cacheDataMutex.unlock();
        }
        DriverStation.reportJoystickUnpluggedWarning("Joystick Button " + button + " on port " + stick + " not available, check if controller is plugged in");
        return false;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public static double getStickAxis(int stick, int axis) {
        if (stick < 0 || stick >= 6) {
            throw new IllegalArgumentException("Joystick index is out of range, should be 0-5");
        }
        if (axis < 0 || axis >= 12) {
            throw new IllegalArgumentException("Joystick axis is out of range");
        }
        m_cacheDataMutex.lock();
        try {
            if (axis < DriverStation.m_joystickAxes[stick].m_count) {
                double d = DriverStation.m_joystickAxes[stick].m_axes[axis];
                return d;
            }
        }
        finally {
            m_cacheDataMutex.unlock();
        }
        DriverStation.reportJoystickUnpluggedWarning("Joystick axis " + axis + " on port " + stick + " not available, check if controller is plugged in");
        return 0.0;
    }

    public static int getStickPOV(int stick, int pov) {
        if (stick < 0 || stick >= 6) {
            throw new IllegalArgumentException("Joystick index is out of range, should be 0-5");
        }
        if (pov < 0 || pov >= 12) {
            throw new IllegalArgumentException("Joystick POV is out of range");
        }
        m_cacheDataMutex.lock();
        try {
            if (pov < DriverStation.m_joystickPOVs[stick].m_count) {
                short s = DriverStation.m_joystickPOVs[stick].m_povs[pov];
                return s;
            }
        }
        finally {
            m_cacheDataMutex.unlock();
        }
        DriverStation.reportJoystickUnpluggedWarning("Joystick POV " + pov + " on port " + stick + " not available, check if controller is plugged in");
        return -1;
    }

    public static int getStickButtons(int stick) {
        if (stick < 0 || stick >= 6) {
            throw new IllegalArgumentException("Joystick index is out of range, should be 0-3");
        }
        m_cacheDataMutex.lock();
        try {
            int n = DriverStation.m_joystickButtons[stick].m_buttons;
            return n;
        }
        finally {
            m_cacheDataMutex.unlock();
        }
    }

    public static int getStickAxisCount(int stick) {
        if (stick < 0 || stick >= 6) {
            throw new IllegalArgumentException("Joystick index is out of range, should be 0-5");
        }
        m_cacheDataMutex.lock();
        try {
            short s = DriverStation.m_joystickAxes[stick].m_count;
            return s;
        }
        finally {
            m_cacheDataMutex.unlock();
        }
    }

    public static int getStickPOVCount(int stick) {
        if (stick < 0 || stick >= 6) {
            throw new IllegalArgumentException("Joystick index is out of range, should be 0-5");
        }
        m_cacheDataMutex.lock();
        try {
            short s = DriverStation.m_joystickPOVs[stick].m_count;
            return s;
        }
        finally {
            m_cacheDataMutex.unlock();
        }
    }

    public static int getStickButtonCount(int stick) {
        if (stick < 0 || stick >= 6) {
            throw new IllegalArgumentException("Joystick index is out of range, should be 0-5");
        }
        m_cacheDataMutex.lock();
        try {
            byte by = DriverStation.m_joystickButtons[stick].m_count;
            return by;
        }
        finally {
            m_cacheDataMutex.unlock();
        }
    }

    public static boolean getJoystickIsXbox(int stick) {
        if (stick < 0 || stick >= 6) {
            throw new IllegalArgumentException("Joystick index is out of range, should be 0-5");
        }
        return HAL.getJoystickIsXbox((byte)stick) == 1;
    }

    public static int getJoystickType(int stick) {
        if (stick < 0 || stick >= 6) {
            throw new IllegalArgumentException("Joystick index is out of range, should be 0-5");
        }
        return HAL.getJoystickType((byte)stick);
    }

    public static String getJoystickName(int stick) {
        if (stick < 0 || stick >= 6) {
            throw new IllegalArgumentException("Joystick index is out of range, should be 0-5");
        }
        return HAL.getJoystickName((byte)stick);
    }

    public static int getJoystickAxisType(int stick, int axis) {
        if (stick < 0 || stick >= 6) {
            throw new IllegalArgumentException("Joystick index is out of range, should be 0-5");
        }
        return HAL.getJoystickAxisType((byte)stick, (byte)axis);
    }

    public static boolean isJoystickConnected(int stick) {
        return DriverStation.getStickAxisCount(stick) > 0 || DriverStation.getStickButtonCount(stick) > 0 || DriverStation.getStickPOVCount(stick) > 0;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public static boolean isEnabled() {
        Object object = m_controlWordMutex;
        synchronized (object) {
            DriverStation.updateControlWord(false);
            return m_controlWordCache.getEnabled() && m_controlWordCache.getDSAttached();
        }
    }

    public static boolean isDisabled() {
        return !DriverStation.isEnabled();
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public static boolean isEStopped() {
        Object object = m_controlWordMutex;
        synchronized (object) {
            DriverStation.updateControlWord(false);
            return m_controlWordCache.getEStop();
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public static boolean isAutonomous() {
        Object object = m_controlWordMutex;
        synchronized (object) {
            DriverStation.updateControlWord(false);
            return m_controlWordCache.getAutonomous();
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public static boolean isAutonomousEnabled() {
        Object object = m_controlWordMutex;
        synchronized (object) {
            DriverStation.updateControlWord(false);
            return m_controlWordCache.getAutonomous() && m_controlWordCache.getEnabled();
        }
    }

    @Deprecated(since="2022", forRemoval=true)
    public static boolean isOperatorControl() {
        return DriverStation.isTeleop();
    }

    public static boolean isTeleop() {
        return !DriverStation.isAutonomous() && !DriverStation.isTest();
    }

    @Deprecated(since="2022", forRemoval=true)
    public static boolean isOperatorControlEnabled() {
        return DriverStation.isTeleopEnabled();
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public static boolean isTeleopEnabled() {
        Object object = m_controlWordMutex;
        synchronized (object) {
            DriverStation.updateControlWord(false);
            return !m_controlWordCache.getAutonomous() && !m_controlWordCache.getTest() && m_controlWordCache.getEnabled();
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public static boolean isTest() {
        Object object = m_controlWordMutex;
        synchronized (object) {
            DriverStation.updateControlWord(false);
            return m_controlWordCache.getTest();
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public static boolean isDSAttached() {
        Object object = m_controlWordMutex;
        synchronized (object) {
            DriverStation.updateControlWord(false);
            return m_controlWordCache.getDSAttached();
        }
    }

    public static boolean isNewControlData() {
        m_waitForDataMutex.lock();
        try {
            int currentCount = m_waitForDataCount;
            if (m_lastCount.get() != currentCount) {
                m_lastCount.set(currentCount);
                boolean bl = true;
                return bl;
            }
        }
        finally {
            m_waitForDataMutex.unlock();
        }
        return false;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public static boolean isFMSAttached() {
        Object object = m_controlWordMutex;
        synchronized (object) {
            DriverStation.updateControlWord(false);
            return m_controlWordCache.getFMSAttached();
        }
    }

    public static String getGameSpecificMessage() {
        m_cacheDataMutex.lock();
        try {
            String string = DriverStation.m_matchInfo.gameSpecificMessage;
            return string;
        }
        finally {
            m_cacheDataMutex.unlock();
        }
    }

    public static String getEventName() {
        m_cacheDataMutex.lock();
        try {
            String string = DriverStation.m_matchInfo.eventName;
            return string;
        }
        finally {
            m_cacheDataMutex.unlock();
        }
    }

    public static MatchType getMatchType() {
        int matchType;
        m_cacheDataMutex.lock();
        try {
            matchType = DriverStation.m_matchInfo.matchType;
        }
        finally {
            m_cacheDataMutex.unlock();
        }
        switch (matchType) {
            case 1: {
                return MatchType.Practice;
            }
            case 2: {
                return MatchType.Qualification;
            }
            case 3: {
                return MatchType.Elimination;
            }
        }
        return MatchType.None;
    }

    public static int getMatchNumber() {
        m_cacheDataMutex.lock();
        try {
            int n = DriverStation.m_matchInfo.matchNumber;
            return n;
        }
        finally {
            m_cacheDataMutex.unlock();
        }
    }

    public static int getReplayNumber() {
        m_cacheDataMutex.lock();
        try {
            int n = DriverStation.m_matchInfo.replayNumber;
            return n;
        }
        finally {
            m_cacheDataMutex.unlock();
        }
    }

    public static Alliance getAlliance() {
        AllianceStationID allianceStationID = HAL.getAllianceStation();
        if (allianceStationID == null) {
            return Alliance.Invalid;
        }
        switch (allianceStationID) {
            case Red1: 
            case Red2: 
            case Red3: {
                return Alliance.Red;
            }
            case Blue1: 
            case Blue2: 
            case Blue3: {
                return Alliance.Blue;
            }
        }
        return Alliance.Invalid;
    }

    public static int getLocation() {
        AllianceStationID allianceStationID = HAL.getAllianceStation();
        if (allianceStationID == null) {
            return 0;
        }
        switch (allianceStationID) {
            case Blue1: 
            case Red1: {
                return 1;
            }
            case Blue2: 
            case Red2: {
                return 2;
            }
            case Blue3: 
            case Red3: {
                return 3;
            }
        }
        return 0;
    }

    public static void waitForData() {
        DriverStation.waitForData(0.0);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public static boolean waitForData(double timeoutSeconds) {
        long startTimeMicroS = RobotController.getFPGATime();
        long timeoutMicroS = (long)(timeoutSeconds * 1000000.0);
        m_waitForDataMutex.lock();
        try {
            int currentCount = m_waitForDataCount;
            if (m_lastCount.get() != currentCount) {
                m_lastCount.set(currentCount);
                boolean bl = true;
                return bl;
            }
            while (m_waitForDataCount == currentCount) {
                if (timeoutMicroS > 0L) {
                    long nowMicroS = RobotController.getFPGATime();
                    if (nowMicroS < startTimeMicroS + timeoutMicroS) {
                        boolean signaled = m_waitForDataCond.await(startTimeMicroS + timeoutMicroS - nowMicroS, TimeUnit.MICROSECONDS);
                        if (signaled) continue;
                        boolean bl = false;
                        return bl;
                    }
                    boolean bl = false;
                    return bl;
                }
                m_waitForDataCond.await();
            }
            m_lastCount.set(m_waitForDataCount);
            boolean bl = true;
            return bl;
        }
        catch (InterruptedException ex) {
            boolean bl = false;
            return bl;
        }
        finally {
            m_waitForDataMutex.unlock();
        }
    }

    public static double getMatchTime() {
        return HAL.getMatchTime();
    }

    public static void inDisabled(boolean entering) {
        m_userInDisabled = entering;
    }

    public static void inAutonomous(boolean entering) {
        m_userInAutonomous = entering;
    }

    @Deprecated(since="2022", forRemoval=true)
    public static void inOperatorControl(boolean entering) {
        m_userInTeleop = entering;
    }

    public static void inTeleop(boolean entering) {
        m_userInTeleop = entering;
    }

    public static void inTest(boolean entering) {
        m_userInTest = entering;
    }

    public static void wakeupWaitForData() {
        m_waitForDataMutex.lock();
        try {
            ++m_waitForDataCount;
            m_waitForDataCond.signalAll();
        }
        finally {
            m_waitForDataMutex.unlock();
        }
    }

    public static void silenceJoystickConnectionWarning(boolean silence) {
        m_silenceJoystickWarning = silence;
    }

    public static boolean isJoystickConnectionWarningSilenced() {
        return !DriverStation.isFMSAttached() && m_silenceJoystickWarning;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    protected static void getData() {
        for (byte stick = 0; stick < 6; stick = (byte)(stick + 1)) {
            DriverStation.m_joystickAxesCache[stick].m_count = HAL.getJoystickAxes(stick, DriverStation.m_joystickAxesCache[stick].m_axes);
            DriverStation.m_joystickPOVsCache[stick].m_count = HAL.getJoystickPOVs(stick, DriverStation.m_joystickPOVsCache[stick].m_povs);
            DriverStation.m_joystickButtonsCache[stick].m_buttons = HAL.getJoystickButtons(stick, m_buttonCountBuffer);
            DriverStation.m_joystickButtonsCache[stick].m_count = m_buttonCountBuffer.get(0);
        }
        HAL.getMatchInfo(m_matchInfoCache);
        DriverStation.updateControlWord(true);
        m_cacheDataMutex.lock();
        try {
            int i = 0;
            while (i < 6) {
                int n = i;
                m_joystickButtonsPressed[n] = m_joystickButtonsPressed[n] | ~DriverStation.m_joystickButtons[i].m_buttons & DriverStation.m_joystickButtonsCache[i].m_buttons;
                int n2 = i++;
                m_joystickButtonsReleased[n2] = m_joystickButtonsReleased[n2] | DriverStation.m_joystickButtons[i].m_buttons & ~DriverStation.m_joystickButtonsCache[i].m_buttons;
            }
            HALJoystickAxes[] currentAxes = m_joystickAxes;
            m_joystickAxes = m_joystickAxesCache;
            m_joystickAxesCache = currentAxes;
            HALJoystickButtons[] currentButtons = m_joystickButtons;
            m_joystickButtons = m_joystickButtonsCache;
            m_joystickButtonsCache = currentButtons;
            HALJoystickPOVs[] currentPOVs = m_joystickPOVs;
            m_joystickPOVs = m_joystickPOVsCache;
            m_joystickPOVsCache = currentPOVs;
            MatchInfoData currentInfo = m_matchInfo;
            m_matchInfo = m_matchInfoCache;
            m_matchInfoCache = currentInfo;
        }
        finally {
            m_cacheDataMutex.unlock();
        }
        DriverStation.wakeupWaitForData();
        m_matchDataSender.sendMatchData();
    }

    private static void reportJoystickUnpluggedError(String message) {
        double currentTime = Timer.getFPGATimestamp();
        if (currentTime > m_nextMessageTime) {
            DriverStation.reportError(message, false);
            m_nextMessageTime = currentTime + 1.0;
        }
    }

    private static void reportJoystickUnpluggedWarning(String message) {
        double currentTime;
        if ((DriverStation.isFMSAttached() || !m_silenceJoystickWarning) && (currentTime = Timer.getFPGATimestamp()) > m_nextMessageTime) {
            DriverStation.reportWarning(message, false);
            m_nextMessageTime = currentTime + 1.0;
        }
    }

    private static void run() {
        int safetyCounter = 0;
        while (m_threadKeepAlive) {
            HAL.waitForDSData();
            DriverStation.getData();
            if (DriverStation.isDisabled()) {
                safetyCounter = 0;
            }
            if (++safetyCounter >= 4) {
                MotorSafety.checkMotors();
                safetyCounter = 0;
            }
            if (m_userInDisabled) {
                HAL.observeUserProgramDisabled();
            }
            if (m_userInAutonomous) {
                HAL.observeUserProgramAutonomous();
            }
            if (m_userInTeleop) {
                HAL.observeUserProgramTeleop();
            }
            if (!m_userInTest) continue;
            HAL.observeUserProgramTest();
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public static void updateControlWordFromCache(ControlWord word) {
        Object object = m_controlWordMutex;
        synchronized (object) {
            DriverStation.updateControlWord(true);
            word.update(m_controlWordCache);
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private static void updateControlWord(boolean force) {
        long now = System.currentTimeMillis();
        Object object = m_controlWordMutex;
        synchronized (object) {
            if (now - m_lastControlWordUpdate > 50L || force) {
                HAL.getControlWord(m_controlWordCache);
                m_lastControlWordUpdate = now;
            }
        }
    }

    static {
        instance = new DriverStation();
        m_joystickAxes = new HALJoystickAxes[6];
        m_joystickPOVs = new HALJoystickPOVs[6];
        m_joystickButtons = new HALJoystickButtons[6];
        m_matchInfo = new MatchInfoData();
        m_joystickAxesCache = new HALJoystickAxes[6];
        m_joystickPOVsCache = new HALJoystickPOVs[6];
        m_joystickButtonsCache = new HALJoystickButtons[6];
        m_matchInfoCache = new MatchInfoData();
        m_joystickButtonsPressed = new int[6];
        m_joystickButtonsReleased = new int[6];
        m_buttonCountBuffer = ByteBuffer.allocateDirect(1);
        m_threadKeepAlive = true;
        m_cacheDataMutex = new ReentrantLock();
        m_lastCount = ThreadLocal.withInitial(() -> 0);
        HAL.initialize(500, 0);
        m_waitForDataCount = 0;
        m_waitForDataMutex = new ReentrantLock();
        m_waitForDataCond = m_waitForDataMutex.newCondition();
        for (int i = 0; i < 6; ++i) {
            DriverStation.m_joystickButtons[i] = new HALJoystickButtons();
            DriverStation.m_joystickAxes[i] = new HALJoystickAxes(12);
            DriverStation.m_joystickPOVs[i] = new HALJoystickPOVs(12);
            DriverStation.m_joystickButtonsCache[i] = new HALJoystickButtons();
            DriverStation.m_joystickAxesCache[i] = new HALJoystickAxes(12);
            DriverStation.m_joystickPOVsCache[i] = new HALJoystickPOVs(12);
        }
        m_controlWordMutex = new Object();
        m_controlWordCache = new ControlWord();
        m_lastControlWordUpdate = 0L;
        m_matchDataSender = new MatchDataSender();
        m_thread = new Thread((Runnable)new DriverStationTask(), "FRCDriverStation");
        m_thread.setPriority(7);
        m_thread.start();
    }

    private static class MatchDataSender {
        NetworkTable table = NetworkTableInstance.getDefault().getTable("FMSInfo");
        NetworkTableEntry typeMetadata = this.table.getEntry(".type");
        NetworkTableEntry gameSpecificMessage;
        NetworkTableEntry eventName;
        NetworkTableEntry matchNumber;
        NetworkTableEntry replayNumber;
        NetworkTableEntry matchType;
        NetworkTableEntry alliance;
        NetworkTableEntry station;
        NetworkTableEntry controlWord;
        boolean oldIsRedAlliance = true;
        int oldStationNumber = 1;
        String oldEventName = "";
        String oldGameSpecificMessage = "";
        int oldMatchNumber;
        int oldReplayNumber;
        int oldMatchType;
        int oldControlWord;

        MatchDataSender() {
            this.typeMetadata.forceSetString("FMSInfo");
            this.gameSpecificMessage = this.table.getEntry("GameSpecificMessage");
            this.gameSpecificMessage.forceSetString("");
            this.eventName = this.table.getEntry("EventName");
            this.eventName.forceSetString("");
            this.matchNumber = this.table.getEntry("MatchNumber");
            this.matchNumber.forceSetDouble(0.0);
            this.replayNumber = this.table.getEntry("ReplayNumber");
            this.replayNumber.forceSetDouble(0.0);
            this.matchType = this.table.getEntry("MatchType");
            this.matchType.forceSetDouble(0.0);
            this.alliance = this.table.getEntry("IsRedAlliance");
            this.alliance.forceSetBoolean(true);
            this.station = this.table.getEntry("StationNumber");
            this.station.forceSetDouble(1.0);
            this.controlWord = this.table.getEntry("FMSControlData");
            this.controlWord.forceSetDouble(0.0);
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        private void sendMatchData() {
            int currentMatchType;
            int currentReplayNumber;
            int currentMatchNumber;
            String currentGameSpecificMessage;
            String currentEventName;
            AllianceStationID allianceID = HAL.getAllianceStation();
            boolean isRedAlliance = false;
            int stationNumber = 1;
            switch (allianceID) {
                case Blue1: {
                    isRedAlliance = false;
                    stationNumber = 1;
                    break;
                }
                case Blue2: {
                    isRedAlliance = false;
                    stationNumber = 2;
                    break;
                }
                case Blue3: {
                    isRedAlliance = false;
                    stationNumber = 3;
                    break;
                }
                case Red1: {
                    isRedAlliance = true;
                    stationNumber = 1;
                    break;
                }
                case Red2: {
                    isRedAlliance = true;
                    stationNumber = 2;
                    break;
                }
                default: {
                    isRedAlliance = true;
                    stationNumber = 3;
                }
            }
            m_cacheDataMutex.lock();
            try {
                currentEventName = DriverStation.m_matchInfo.eventName;
                currentGameSpecificMessage = DriverStation.m_matchInfo.gameSpecificMessage;
                currentMatchNumber = DriverStation.m_matchInfo.matchNumber;
                currentReplayNumber = DriverStation.m_matchInfo.replayNumber;
                currentMatchType = DriverStation.m_matchInfo.matchType;
            }
            finally {
                m_cacheDataMutex.unlock();
            }
            int currentControlWord = HAL.nativeGetControlWord();
            if (this.oldIsRedAlliance != isRedAlliance) {
                this.alliance.setBoolean(isRedAlliance);
                this.oldIsRedAlliance = isRedAlliance;
            }
            if (this.oldStationNumber != stationNumber) {
                this.station.setDouble(stationNumber);
                this.oldStationNumber = stationNumber;
            }
            if (!this.oldEventName.equals(currentEventName)) {
                this.eventName.setString(currentEventName);
                this.oldEventName = currentEventName;
            }
            if (!this.oldGameSpecificMessage.equals(currentGameSpecificMessage)) {
                this.gameSpecificMessage.setString(currentGameSpecificMessage);
                this.oldGameSpecificMessage = currentGameSpecificMessage;
            }
            if (currentMatchNumber != this.oldMatchNumber) {
                this.matchNumber.setDouble(currentMatchNumber);
                this.oldMatchNumber = currentMatchNumber;
            }
            if (currentReplayNumber != this.oldReplayNumber) {
                this.replayNumber.setDouble(currentReplayNumber);
                this.oldReplayNumber = currentReplayNumber;
            }
            if (currentMatchType != this.oldMatchType) {
                this.matchType.setDouble(currentMatchType);
                this.oldMatchType = currentMatchType;
            }
            if (currentControlWord != this.oldControlWord) {
                this.controlWord.setDouble(currentControlWord);
                this.oldControlWord = currentControlWord;
            }
        }
    }

    private static class DriverStationTask
    implements Runnable {
        DriverStationTask() {
        }

        @Override
        public void run() {
            DriverStation.run();
        }
    }

    public static enum MatchType {
        None,
        Practice,
        Qualification,
        Elimination;

    }

    public static enum Alliance {
        Red,
        Blue,
        Invalid;

    }

    private static class HALJoystickPOVs {
        public short[] m_povs;
        public short m_count;

        HALJoystickPOVs(int count) {
            this.m_povs = new short[count];
            for (int i = 0; i < count; ++i) {
                this.m_povs[i] = -1;
            }
        }
    }

    private static class HALJoystickAxes {
        public float[] m_axes;
        public short m_count;

        HALJoystickAxes(int count) {
            this.m_axes = new float[count];
        }
    }

    private static class HALJoystickButtons {
        public int m_buttons;
        public byte m_count;

        private HALJoystickButtons() {
        }
    }
}

