/*
 * Decompiled with CFR 0.150.
 */
package edu.wpi.first.wpilibj;

import edu.wpi.first.hal.NotifierJNI;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.RobotController;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.ReentrantLock;

public class Notifier
implements AutoCloseable {
    private Thread m_thread;
    private final ReentrantLock m_processLock = new ReentrantLock();
    private final AtomicInteger m_notifier = new AtomicInteger();
    private double m_expirationTimeSeconds;
    private Runnable m_handler;
    private boolean m_periodic;
    private double m_periodSeconds;

    protected void finalize() {
        this.close();
    }

    @Override
    public void close() {
        int handle = this.m_notifier.getAndSet(0);
        if (handle == 0) {
            return;
        }
        NotifierJNI.stopNotifier(handle);
        if (this.m_thread.isAlive()) {
            try {
                this.m_thread.interrupt();
                this.m_thread.join();
            }
            catch (InterruptedException ex) {
                Thread.currentThread().interrupt();
            }
        }
        NotifierJNI.cleanNotifier(handle);
        this.m_thread = null;
    }

    private void updateAlarm(long triggerTimeMicroS) {
        int notifier = this.m_notifier.get();
        if (notifier == 0) {
            return;
        }
        NotifierJNI.updateNotifierAlarm(notifier, triggerTimeMicroS);
    }

    private void updateAlarm() {
        this.updateAlarm((long)(this.m_expirationTimeSeconds * 1000000.0));
    }

    public Notifier(Runnable run) {
        Objects.requireNonNull(run);
        this.m_handler = run;
        this.m_notifier.set(NotifierJNI.initializeNotifier());
        this.m_thread = new Thread(() -> {
            long curTime;
            int notifier;
            while (!Thread.interrupted() && (notifier = this.m_notifier.get()) != 0 && (curTime = NotifierJNI.waitForNotifierAlarm(notifier)) != 0L) {
                Runnable handler;
                this.m_processLock.lock();
                try {
                    handler = this.m_handler;
                    if (this.m_periodic) {
                        this.m_expirationTimeSeconds += this.m_periodSeconds;
                        this.updateAlarm();
                    } else {
                        this.updateAlarm(-1L);
                    }
                }
                finally {
                    this.m_processLock.unlock();
                }
                if (handler == null) continue;
                handler.run();
            }
        });
        this.m_thread.setName("Notifier");
        this.m_thread.setDaemon(true);
        this.m_thread.setUncaughtExceptionHandler((thread, error) -> {
            Throwable cause = error.getCause();
            if (cause != null) {
                error = cause;
            }
            DriverStation.reportError("Unhandled exception: " + error.toString(), error.getStackTrace());
            DriverStation.reportError("The loopFunc() method (or methods called by it) should have handled the exception above.", false);
        });
        this.m_thread.start();
    }

    public void setName(String name) {
        this.m_thread.setName(name);
        NotifierJNI.setNotifierName(this.m_notifier.get(), name);
    }

    public void setHandler(Runnable handler) {
        this.m_processLock.lock();
        try {
            this.m_handler = handler;
        }
        finally {
            this.m_processLock.unlock();
        }
    }

    public void startSingle(double delaySeconds) {
        this.m_processLock.lock();
        try {
            this.m_periodic = false;
            this.m_periodSeconds = delaySeconds;
            this.m_expirationTimeSeconds = (double)RobotController.getFPGATime() * 1.0E-6 + delaySeconds;
            this.updateAlarm();
        }
        finally {
            this.m_processLock.unlock();
        }
    }

    public void startPeriodic(double periodSeconds) {
        this.m_processLock.lock();
        try {
            this.m_periodic = true;
            this.m_periodSeconds = periodSeconds;
            this.m_expirationTimeSeconds = (double)RobotController.getFPGATime() * 1.0E-6 + periodSeconds;
            this.updateAlarm();
        }
        finally {
            this.m_processLock.unlock();
        }
    }

    public void stop() {
        this.m_processLock.lock();
        try {
            this.m_periodic = false;
            NotifierJNI.cancelNotifierAlarm(this.m_notifier.get());
        }
        finally {
            this.m_processLock.unlock();
        }
    }

    public static boolean setHALThreadPriority(boolean realTime, int priority) {
        return NotifierJNI.setHALThreadPriority(realTime, priority);
    }
}

