/*
 * Decompiled with CFR 0.150.
 */
package edu.wpi.first.wpilibj.smartdashboard;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.concurrent.Executor;

class ListenerExecutor
implements Executor {
    private final Collection<Runnable> m_tasks = new ArrayList<Runnable>();
    private final Object m_lock = new Object();

    ListenerExecutor() {
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public void execute(Runnable task) {
        Object object = this.m_lock;
        synchronized (object) {
            this.m_tasks.add(task);
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public void runListenerTasks() {
        ArrayList<Runnable> tasks = new ArrayList<Runnable>();
        Iterator iterator = this.m_lock;
        synchronized (iterator) {
            tasks.addAll(this.m_tasks);
            this.m_tasks.clear();
        }
        for (Runnable task : tasks) {
            task.run();
        }
    }
}

