/*
 * Decompiled with CFR 0.150.
 */
package edu.wpi.first.wpilibj;

import edu.wpi.first.hal.NotifierJNI;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.Tracer;
import java.io.Closeable;
import java.util.PriorityQueue;
import java.util.concurrent.locks.ReentrantLock;

public class Watchdog
implements Closeable,
Comparable<Watchdog> {
    private static final long kMinPrintPeriodMicroS = 1000000L;
    private double m_startTimeSeconds;
    private double m_timeoutSeconds;
    private double m_expirationTimeSeconds;
    private final Runnable m_callback;
    private double m_lastTimeoutPrintSeconds;
    boolean m_isExpired;
    boolean m_suppressTimeoutMessage;
    private final Tracer m_tracer;
    private static final PriorityQueue<Watchdog> m_watchdogs = new PriorityQueue();
    private static ReentrantLock m_queueMutex = new ReentrantLock();
    private static int m_notifier = NotifierJNI.initializeNotifier();

    public Watchdog(double timeoutSeconds, Runnable callback) {
        this.m_timeoutSeconds = timeoutSeconds;
        this.m_callback = callback;
        this.m_tracer = new Tracer();
    }

    @Override
    public void close() {
        this.disable();
    }

    public boolean equals(Object obj) {
        if (obj instanceof Watchdog) {
            return Double.compare(this.m_expirationTimeSeconds, ((Watchdog)obj).m_expirationTimeSeconds) == 0;
        }
        return false;
    }

    public int hashCode() {
        return Double.hashCode(this.m_expirationTimeSeconds);
    }

    @Override
    public int compareTo(Watchdog rhs) {
        return Double.compare(this.m_expirationTimeSeconds, rhs.m_expirationTimeSeconds);
    }

    public double getTime() {
        return Timer.getFPGATimestamp() - this.m_startTimeSeconds;
    }

    public void setTimeout(double timeoutSeconds) {
        this.m_startTimeSeconds = Timer.getFPGATimestamp();
        this.m_tracer.clearEpochs();
        m_queueMutex.lock();
        try {
            this.m_timeoutSeconds = timeoutSeconds;
            this.m_isExpired = false;
            m_watchdogs.remove(this);
            this.m_expirationTimeSeconds = this.m_startTimeSeconds + this.m_timeoutSeconds;
            m_watchdogs.add(this);
            Watchdog.updateAlarm();
        }
        finally {
            m_queueMutex.unlock();
        }
    }

    public double getTimeout() {
        m_queueMutex.lock();
        try {
            double d = this.m_timeoutSeconds;
            return d;
        }
        finally {
            m_queueMutex.unlock();
        }
    }

    public boolean isExpired() {
        m_queueMutex.lock();
        try {
            boolean bl = this.m_isExpired;
            return bl;
        }
        finally {
            m_queueMutex.unlock();
        }
    }

    public void addEpoch(String epochName) {
        this.m_tracer.addEpoch(epochName);
    }

    public void printEpochs() {
        this.m_tracer.printEpochs();
    }

    public void reset() {
        this.enable();
    }

    public void enable() {
        this.m_startTimeSeconds = Timer.getFPGATimestamp();
        this.m_tracer.clearEpochs();
        m_queueMutex.lock();
        try {
            this.m_isExpired = false;
            m_watchdogs.remove(this);
            this.m_expirationTimeSeconds = this.m_startTimeSeconds + this.m_timeoutSeconds;
            m_watchdogs.add(this);
            Watchdog.updateAlarm();
        }
        finally {
            m_queueMutex.unlock();
        }
    }

    public void disable() {
        m_queueMutex.lock();
        try {
            m_watchdogs.remove(this);
            Watchdog.updateAlarm();
        }
        finally {
            m_queueMutex.unlock();
        }
    }

    public void suppressTimeoutMessage(boolean suppress) {
        this.m_suppressTimeoutMessage = suppress;
    }

    private static void updateAlarm() {
        if (m_watchdogs.size() == 0) {
            NotifierJNI.cancelNotifierAlarm(m_notifier);
        } else {
            NotifierJNI.updateNotifierAlarm(m_notifier, (long)(Watchdog.m_watchdogs.peek().m_expirationTimeSeconds * 1000000.0));
        }
    }

    private static Thread startDaemonThread(Runnable target) {
        Thread inst = new Thread(target);
        inst.setDaemon(true);
        inst.start();
        return inst;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private static void schedulerFunc() {
        long curTime;
        while (!Thread.currentThread().isInterrupted() && (curTime = NotifierJNI.waitForNotifierAlarm(m_notifier)) != 0L) {
            m_queueMutex.lock();
            try {
                if (m_watchdogs.size() == 0) continue;
                Watchdog watchdog = m_watchdogs.poll();
                double now = (double)curTime * 1.0E-6;
                if (now - watchdog.m_lastTimeoutPrintSeconds > 1000000.0) {
                    watchdog.m_lastTimeoutPrintSeconds = now;
                    if (!watchdog.m_suppressTimeoutMessage) {
                        DriverStation.reportWarning(String.format("Watchdog not fed within %.6fs\n", watchdog.m_timeoutSeconds), false);
                    }
                }
                watchdog.m_isExpired = true;
                m_queueMutex.unlock();
                watchdog.m_callback.run();
                m_queueMutex.lock();
                Watchdog.updateAlarm();
            }
            finally {
                m_queueMutex.unlock();
            }
        }
    }

    static {
        NotifierJNI.setNotifierName(m_notifier, "Watchdog");
        Watchdog.startDaemonThread(Watchdog::schedulerFunc);
    }
}

