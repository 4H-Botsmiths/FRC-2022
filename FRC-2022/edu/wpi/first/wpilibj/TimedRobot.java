/*
 * Decompiled with CFR 0.150.
 */
package edu.wpi.first.wpilibj;

import edu.wpi.first.hal.HAL;
import edu.wpi.first.hal.NotifierJNI;
import edu.wpi.first.wpilibj.IterativeRobotBase;
import edu.wpi.first.wpilibj.Timer;
import java.util.PriorityQueue;

public class TimedRobot
extends IterativeRobotBase {
    public static final double kDefaultPeriod = 0.02;
    private final int m_notifier = NotifierJNI.initializeNotifier();
    private double m_startTime;
    private final PriorityQueue<Callback> m_callbacks = new PriorityQueue();

    protected TimedRobot() {
        this(0.02);
    }

    protected TimedRobot(double period) {
        super(period);
        this.m_startTime = Timer.getFPGATimestamp();
        this.addPeriodic(this::loopFunc, period);
        NotifierJNI.setNotifierName(this.m_notifier, "TimedRobot");
        HAL.report(22, 4);
    }

    protected void finalize() {
        NotifierJNI.stopNotifier(this.m_notifier);
        NotifierJNI.cleanNotifier(this.m_notifier);
    }

    @Override
    public void startCompetition() {
        this.robotInit();
        if (TimedRobot.isSimulation()) {
            this.simulationInit();
        }
        System.out.println("********** Robot program startup complete **********");
        HAL.observeUserProgramStarting();
        block0: while (true) {
            Callback callback = this.m_callbacks.poll();
            NotifierJNI.updateNotifierAlarm(this.m_notifier, (long)(callback.expirationTime * 1000000.0));
            long curTime = NotifierJNI.waitForNotifierAlarm(this.m_notifier);
            if (curTime == 0L) break;
            callback.func.run();
            callback.expirationTime += callback.period;
            this.m_callbacks.add(callback);
            while (true) {
                if ((long)(this.m_callbacks.peek().expirationTime * 1000000.0) > curTime) continue block0;
                callback = this.m_callbacks.poll();
                callback.func.run();
                callback.expirationTime += callback.period;
                this.m_callbacks.add(callback);
            }
            break;
        }
    }

    @Override
    public void endCompetition() {
        NotifierJNI.stopNotifier(this.m_notifier);
    }

    public void addPeriodic(Runnable callback, double periodSeconds) {
        this.m_callbacks.add(new Callback(callback, this.m_startTime, periodSeconds, 0.0));
    }

    public void addPeriodic(Runnable callback, double periodSeconds, double offsetSeconds) {
        this.m_callbacks.add(new Callback(callback, this.m_startTime, periodSeconds, offsetSeconds));
    }

    static class Callback
    implements Comparable<Callback> {
        public Runnable func;
        public double period;
        public double expirationTime;

        Callback(Runnable func, double startTimeSeconds, double periodSeconds, double offsetSeconds) {
            this.func = func;
            this.period = periodSeconds;
            this.expirationTime = startTimeSeconds + offsetSeconds + Math.floor((Timer.getFPGATimestamp() - startTimeSeconds) / this.period) * this.period + this.period;
        }

        public boolean equals(Object rhs) {
            if (rhs instanceof Callback) {
                return Double.compare(this.expirationTime, ((Callback)rhs).expirationTime) == 0;
            }
            return false;
        }

        public int hashCode() {
            return Double.hashCode(this.expirationTime);
        }

        @Override
        public int compareTo(Callback rhs) {
            return Double.compare(this.expirationTime, rhs.expirationTime);
        }
    }
}

