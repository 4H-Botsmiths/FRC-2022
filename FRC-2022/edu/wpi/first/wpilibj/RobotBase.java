/*
 * Decompiled with CFR 0.150.
 */
package edu.wpi.first.wpilibj;

import edu.wpi.first.cameraserver.CameraServerShared;
import edu.wpi.first.cameraserver.CameraServerSharedStore;
import edu.wpi.first.cscore.CameraServerJNI;
import edu.wpi.first.hal.HAL;
import edu.wpi.first.hal.HALUtil;
import edu.wpi.first.math.MathShared;
import edu.wpi.first.math.MathSharedStore;
import edu.wpi.first.math.MathUsageId;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.Notifier;
import edu.wpi.first.wpilibj.RuntimeType;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.OpenOption;
import java.util.concurrent.locks.ReentrantLock;
import java.util.function.Supplier;

public abstract class RobotBase
implements AutoCloseable {
    private static long m_threadId = -1L;
    private static final ReentrantLock m_runMutex = new ReentrantLock();
    private static RobotBase m_robotCopy;
    private static boolean m_suppressExitWarning;

    private static void setupCameraServerShared() {
        CameraServerShared shared = new CameraServerShared(){

            @Override
            public void reportVideoServer(int id) {
                HAL.report(42, id + 1);
            }

            @Override
            public void reportUsbCamera(int id) {
                HAL.report(73, id + 1);
            }

            @Override
            public void reportDriverStationError(String error) {
                DriverStation.reportError(error, true);
            }

            @Override
            public void reportAxisCamera(int id) {
                HAL.report(41, id + 1);
            }

            @Override
            public Long getRobotMainThreadId() {
                return RobotBase.getMainThreadId();
            }

            @Override
            public boolean isRoboRIO() {
                return RobotBase.isReal();
            }
        };
        CameraServerSharedStore.setCameraServerShared(shared);
    }

    private static void setupMathShared() {
        MathSharedStore.setMathShared(new MathShared(){

            @Override
            public void reportError(String error, StackTraceElement[] stackTrace) {
                DriverStation.reportError(error, stackTrace);
            }

            @Override
            public void reportUsage(MathUsageId id, int count) {
                switch (id) {
                    case kKinematics_DifferentialDrive: {
                        HAL.report(87, 1);
                        break;
                    }
                    case kKinematics_MecanumDrive: {
                        HAL.report(87, 2);
                        break;
                    }
                    case kKinematics_SwerveDrive: {
                        HAL.report(87, 3);
                        break;
                    }
                    case kTrajectory_TrapezoidProfile: {
                        HAL.report(90, count);
                        break;
                    }
                    case kFilter_Linear: {
                        HAL.report(71, count);
                        break;
                    }
                    case kOdometry_DifferentialDrive: {
                        HAL.report(88, 1);
                        break;
                    }
                    case kOdometry_SwerveDrive: {
                        HAL.report(88, 3);
                        break;
                    }
                    case kOdometry_MecanumDrive: {
                        HAL.report(88, 2);
                        break;
                    }
                    case kController_PIDController2: {
                        HAL.report(85, count);
                        break;
                    }
                    case kController_ProfiledPIDController: {
                        HAL.report(86, count);
                        break;
                    }
                }
            }
        });
    }

    protected RobotBase() {
        NetworkTableInstance inst = NetworkTableInstance.getDefault();
        m_threadId = Thread.currentThread().getId();
        RobotBase.setupCameraServerShared();
        RobotBase.setupMathShared();
        inst.setNetworkIdentity("Robot");
        if (RobotBase.isReal()) {
            inst.startServer("/home/lvuser/networktables.ini");
        } else {
            inst.startServer();
        }
        inst.getTable("LiveWindow").getSubTable(".status").getEntry("LW Enabled").setBoolean(false);
        LiveWindow.setEnabled(false);
        Shuffleboard.disableActuatorWidgets();
    }

    public static long getMainThreadId() {
        return m_threadId;
    }

    @Override
    public void close() {
    }

    public static RuntimeType getRuntimeType() {
        return RuntimeType.getValue(HALUtil.getHALRuntimeType());
    }

    public static boolean isSimulation() {
        return !RobotBase.isReal();
    }

    public static boolean isReal() {
        RuntimeType runtimeType = RobotBase.getRuntimeType();
        return runtimeType == RuntimeType.kRoboRIO || runtimeType == RuntimeType.kRoboRIO2;
    }

    public boolean isDisabled() {
        return DriverStation.isDisabled();
    }

    public boolean isEnabled() {
        return DriverStation.isEnabled();
    }

    public boolean isAutonomous() {
        return DriverStation.isAutonomous();
    }

    public boolean isAutonomousEnabled() {
        return DriverStation.isAutonomousEnabled();
    }

    public boolean isTest() {
        return DriverStation.isTest();
    }

    @Deprecated(since="2022", forRemoval=true)
    public boolean isOperatorControl() {
        return DriverStation.isTeleop();
    }

    public boolean isTeleop() {
        return DriverStation.isTeleop();
    }

    @Deprecated(since="2022", forRemoval=true)
    public boolean isOperatorControlEnabled() {
        return DriverStation.isTeleopEnabled();
    }

    public boolean isTeleopEnabled() {
        return DriverStation.isTeleopEnabled();
    }

    public boolean isNewDataAvailable() {
        return DriverStation.isNewControlData();
    }

    public abstract void startCompetition();

    public abstract void endCompetition();

    public static boolean getBooleanProperty(String name, boolean defaultValue) {
        String propVal = System.getProperty(name);
        if (propVal == null) {
            return defaultValue;
        }
        if ("false".equalsIgnoreCase(propVal)) {
            return false;
        }
        if ("true".equalsIgnoreCase(propVal)) {
            return true;
        }
        throw new IllegalStateException(propVal);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private static <T extends RobotBase> void runRobot(Supplier<T> robotSupplier) {
        RobotBase robot;
        System.out.println("********** Robot program starting **********");
        try {
            robot = (RobotBase)robotSupplier.get();
        }
        catch (Throwable throwable) {
            Throwable cause = throwable.getCause();
            if (cause != null) {
                throwable = cause;
            }
            String robotName = "Unknown";
            StackTraceElement[] elements = throwable.getStackTrace();
            if (elements.length > 0) {
                robotName = elements[0].getClassName();
            }
            DriverStation.reportError("Unhandled exception instantiating robot " + robotName + " " + throwable.toString(), elements);
            DriverStation.reportError("The robot program quit unexpectedly. This is usually due to a code error.\n  The above stacktrace can help determine where the error occurred.\n  See https://wpilib.org/stacktrace for more information.\n", false);
            DriverStation.reportError("Could not instantiate robot " + robotName + "!", false);
            return;
        }
        m_runMutex.lock();
        m_robotCopy = robot;
        m_runMutex.unlock();
        if (RobotBase.isReal()) {
            File file = new File("/tmp/frc_versions/FRC_Lib_Version.ini");
            try {
                if (file.exists() && !file.delete()) {
                    throw new IOException("Failed to delete FRC_Lib_Version.ini");
                }
                if (!file.createNewFile()) {
                    throw new IOException("Failed to create new FRC_Lib_Version.ini");
                }
                try (OutputStream output = Files.newOutputStream(file.toPath(), new OpenOption[0]);){
                    output.write("Java ".getBytes(StandardCharsets.UTF_8));
                    output.write("2022.1.1-beta-4".getBytes(StandardCharsets.UTF_8));
                }
            }
            catch (IOException ex) {
                DriverStation.reportError("Could not write FRC_Lib_Version.ini: " + ex.toString(), ex.getStackTrace());
            }
        }
        boolean errorOnExit = false;
        try {
            robot.startCompetition();
        }
        catch (Throwable throwable) {
            Throwable cause = throwable.getCause();
            if (cause != null) {
                throwable = cause;
            }
            DriverStation.reportError("Unhandled exception: " + throwable.toString(), throwable.getStackTrace());
            errorOnExit = true;
        }
        finally {
            m_runMutex.lock();
            boolean suppressExitWarning = m_suppressExitWarning;
            m_runMutex.unlock();
            if (!suppressExitWarning) {
                DriverStation.reportWarning("The robot program quit unexpectedly. This is usually due to a code error.\n  The above stacktrace can help determine where the error occurred.\n  See https://wpilib.org/stacktrace for more information.", false);
                if (errorOnExit) {
                    DriverStation.reportError("The startCompetition() method (or methods called by it) should have handled the exception above.", false);
                } else {
                    DriverStation.reportError("Unexpected return from startCompetition() method.", false);
                }
            }
        }
    }

    public static void suppressExitWarning(boolean value) {
        m_runMutex.lock();
        m_suppressExitWarning = value;
        m_runMutex.unlock();
    }

    public static <T extends RobotBase> void startRobot(Supplier<T> robotSupplier) {
        if (!HAL.initialize(500, 0)) {
            throw new IllegalStateException("Failed to initialize. Terminating");
        }
        CameraServerJNI.enumerateSinks();
        HAL.report(2, 3, 0, "2022.1.1-beta-4");
        if (!Notifier.setHALThreadPriority(true, 40)) {
            DriverStation.reportWarning("Setting HAL Notifier RT priority to 40 failed", false);
        }
        if (HAL.hasMain()) {
            Thread thread = new Thread(() -> {
                RobotBase.runRobot(robotSupplier);
                HAL.exitMain();
            }, "robot main");
            thread.setDaemon(true);
            thread.start();
            HAL.runMain();
            RobotBase.suppressExitWarning(true);
            m_runMutex.lock();
            RobotBase robot = m_robotCopy;
            m_runMutex.unlock();
            if (robot != null) {
                robot.endCompetition();
            }
            try {
                thread.join(1000L);
            }
            catch (InterruptedException ex) {
                Thread.currentThread().interrupt();
            }
        } else {
            RobotBase.runRobot(robotSupplier);
        }
        HAL.shutdown();
        System.exit(0);
    }
}

