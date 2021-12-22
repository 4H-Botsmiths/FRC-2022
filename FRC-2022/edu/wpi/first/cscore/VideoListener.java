/*
 * Decompiled with CFR 0.150.
 */
package edu.wpi.first.cscore;

import edu.wpi.first.cscore.CameraServerJNI;
import edu.wpi.first.cscore.VideoEvent;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;
import java.util.function.Consumer;

public class VideoListener
implements AutoCloseable {
    private int m_handle;
    private static final ReentrantLock s_lock = new ReentrantLock();
    private static final Map<Integer, Consumer<VideoEvent>> s_listeners = new HashMap<Integer, Consumer<VideoEvent>>();
    private static Thread s_thread;
    private static int s_poller;
    private static boolean s_waitQueue;
    private static final Condition s_waitQueueCond;

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public VideoListener(Consumer<VideoEvent> listener, int eventMask, boolean immediateNotify) {
        s_lock.lock();
        try {
            if (s_poller == 0) {
                s_poller = CameraServerJNI.createListenerPoller();
                VideoListener.startThread();
            }
            this.m_handle = CameraServerJNI.addPolledListener(s_poller, eventMask, immediateNotify);
            s_listeners.put(this.m_handle, listener);
        }
        finally {
            s_lock.unlock();
        }
    }

    @Override
    public synchronized void close() {
        if (this.m_handle != 0) {
            s_lock.lock();
            try {
                s_listeners.remove(this.m_handle);
            }
            finally {
                s_lock.unlock();
            }
            CameraServerJNI.removeListener(this.m_handle);
            this.m_handle = 0;
        }
    }

    public boolean isValid() {
        return this.m_handle != 0;
    }

    private static void startThread() {
        s_thread = new Thread(() -> {
            boolean wasInterrupted = false;
            while (!Thread.interrupted()) {
                VideoEvent[] events;
                try {
                    events = CameraServerJNI.pollListener(s_poller);
                }
                catch (InterruptedException ex) {
                    s_lock.lock();
                    try {
                        if (s_waitQueue) {
                            s_waitQueue = false;
                            s_waitQueueCond.signalAll();
                            continue;
                        }
                    }
                    finally {
                        s_lock.unlock();
                        continue;
                    }
                    Thread.currentThread().interrupt();
                    wasInterrupted = true;
                    break;
                }
                for (VideoEvent event : events) {
                    Consumer<VideoEvent> listener;
                    s_lock.lock();
                    try {
                        listener = s_listeners.get(event.listener);
                    }
                    finally {
                        s_lock.unlock();
                    }
                    if (listener == null) continue;
                    try {
                        listener.accept(event);
                    }
                    catch (Throwable throwable) {
                        System.err.println("Unhandled exception during listener callback: " + throwable.toString());
                        throwable.printStackTrace();
                    }
                }
            }
            s_lock.lock();
            try {
                if (!wasInterrupted) {
                    CameraServerJNI.destroyListenerPoller(s_poller);
                }
                s_poller = 0;
            }
            finally {
                s_lock.unlock();
            }
        }, "VideoListener");
        s_thread.setDaemon(true);
        s_thread.start();
    }

    static {
        s_waitQueueCond = s_lock.newCondition();
    }
}

