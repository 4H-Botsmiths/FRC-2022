/*
 * Decompiled with CFR 0.150.
 */
package edu.wpi.first.wpilibj;

import edu.wpi.first.hal.HAL;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.DSControlWord;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.RobotBase;
import edu.wpi.first.wpilibj.Watchdog;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public abstract class IterativeRobotBase
extends RobotBase {
    private final DSControlWord m_word = new DSControlWord();
    private Mode m_lastMode = Mode.kNone;
    private final double m_period;
    private final Watchdog m_watchdog;
    private boolean m_ntFlushEnabled;
    private boolean m_rpFirstRun = true;
    private boolean m_spFirstRun = true;
    private boolean m_dpFirstRun = true;
    private boolean m_apFirstRun = true;
    private boolean m_tpFirstRun = true;
    private boolean m_tmpFirstRun = true;

    protected IterativeRobotBase(double period) {
        this.m_period = period;
        this.m_watchdog = new Watchdog(period, this::printLoopOverrunMessage);
    }

    @Override
    public abstract void startCompetition();

    public void robotInit() {
    }

    public void simulationInit() {
    }

    public void disabledInit() {
    }

    public void autonomousInit() {
    }

    public void teleopInit() {
    }

    public void testInit() {
    }

    public void robotPeriodic() {
        if (this.m_rpFirstRun) {
            System.out.println("Default robotPeriodic() method... Override me!");
            this.m_rpFirstRun = false;
        }
    }

    public void simulationPeriodic() {
        if (this.m_spFirstRun) {
            System.out.println("Default simulationPeriodic() method... Override me!");
            this.m_spFirstRun = false;
        }
    }

    public void disabledPeriodic() {
        if (this.m_dpFirstRun) {
            System.out.println("Default disabledPeriodic() method... Override me!");
            this.m_dpFirstRun = false;
        }
    }

    public void autonomousPeriodic() {
        if (this.m_apFirstRun) {
            System.out.println("Default autonomousPeriodic() method... Override me!");
            this.m_apFirstRun = false;
        }
    }

    public void teleopPeriodic() {
        if (this.m_tpFirstRun) {
            System.out.println("Default teleopPeriodic() method... Override me!");
            this.m_tpFirstRun = false;
        }
    }

    public void testPeriodic() {
        if (this.m_tmpFirstRun) {
            System.out.println("Default testPeriodic() method... Override me!");
            this.m_tmpFirstRun = false;
        }
    }

    public void disabledExit() {
    }

    public void autonomousExit() {
    }

    public void teleopExit() {
    }

    public void testExit() {
    }

    public void setNetworkTablesFlushEnabled(boolean enabled) {
        this.m_ntFlushEnabled = enabled;
    }

    public double getPeriod() {
        return this.m_period;
    }

    protected void loopFunc() {
        this.m_watchdog.reset();
        this.m_word.update();
        Mode mode = Mode.kNone;
        if (this.m_word.isDisabled()) {
            mode = Mode.kDisabled;
        } else if (this.m_word.isAutonomous()) {
            mode = Mode.kAutonomous;
        } else if (this.m_word.isTeleop()) {
            mode = Mode.kTeleop;
        } else if (this.m_word.isTest()) {
            mode = Mode.kTest;
        }
        if (this.m_lastMode != mode) {
            if (this.m_lastMode == Mode.kDisabled) {
                this.disabledExit();
            } else if (this.m_lastMode == Mode.kAutonomous) {
                this.autonomousExit();
            } else if (this.m_lastMode == Mode.kTeleop) {
                this.teleopExit();
            } else if (this.m_lastMode == Mode.kTest) {
                LiveWindow.setEnabled(false);
                Shuffleboard.disableActuatorWidgets();
                this.testExit();
            }
            if (mode == Mode.kDisabled) {
                this.disabledInit();
                this.m_watchdog.addEpoch("disabledInit()");
            } else if (mode == Mode.kAutonomous) {
                this.autonomousInit();
                this.m_watchdog.addEpoch("autonomousInit()");
            } else if (mode == Mode.kTeleop) {
                this.teleopInit();
                this.m_watchdog.addEpoch("teleopInit()");
            } else if (mode == Mode.kTest) {
                LiveWindow.setEnabled(true);
                Shuffleboard.enableActuatorWidgets();
                this.testInit();
                this.m_watchdog.addEpoch("testInit()");
            }
            this.m_lastMode = mode;
        }
        if (mode == Mode.kDisabled) {
            HAL.observeUserProgramDisabled();
            this.disabledPeriodic();
            this.m_watchdog.addEpoch("disabledPeriodic()");
        } else if (mode == Mode.kAutonomous) {
            HAL.observeUserProgramAutonomous();
            this.autonomousPeriodic();
            this.m_watchdog.addEpoch("autonomousPeriodic()");
        } else if (mode == Mode.kTeleop) {
            HAL.observeUserProgramTeleop();
            this.teleopPeriodic();
            this.m_watchdog.addEpoch("teleopPeriodic()");
        } else {
            HAL.observeUserProgramTest();
            this.testPeriodic();
            this.m_watchdog.addEpoch("testPeriodic()");
        }
        this.robotPeriodic();
        this.m_watchdog.addEpoch("robotPeriodic()");
        SmartDashboard.updateValues();
        this.m_watchdog.addEpoch("SmartDashboard.updateValues()");
        LiveWindow.updateValues();
        this.m_watchdog.addEpoch("LiveWindow.updateValues()");
        Shuffleboard.update();
        this.m_watchdog.addEpoch("Shuffleboard.update()");
        if (IterativeRobotBase.isSimulation()) {
            HAL.simPeriodicBefore();
            this.simulationPeriodic();
            HAL.simPeriodicAfter();
            this.m_watchdog.addEpoch("simulationPeriodic()");
        }
        this.m_watchdog.disable();
        if (this.m_ntFlushEnabled) {
            NetworkTableInstance.getDefault().flush();
        }
        if (this.m_watchdog.isExpired()) {
            this.m_watchdog.printEpochs();
        }
    }

    private void printLoopOverrunMessage() {
        DriverStation.reportWarning("Loop time of " + this.m_period + "s overrun\n", false);
    }

    private static enum Mode {
        kNone,
        kDisabled,
        kAutonomous,
        kTeleop,
        kTest;

    }
}

