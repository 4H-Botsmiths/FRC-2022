/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  org.jetbrains.annotations.Nullable
 */
package pabeles.concurrency;

import java.util.Objects;
import java.util.concurrent.ForkJoinTask;
import org.jetbrains.annotations.Nullable;
import pabeles.concurrency.GrowArray;
import pabeles.concurrency.IntRangeObjectConsumer;

public class IntRangeObjectTask<T>
extends ForkJoinTask<Void> {
    final int min;
    final int max;
    final int stepLength;
    final int step;
    final IntRangeObjectConsumer<T> consumer;
    final GrowArray<T> workspace;
    @Nullable
    IntRangeObjectTask<T> next;

    public IntRangeObjectTask(int step, int min, int max, int stepLength, GrowArray<T> workspace, IntRangeObjectConsumer<T> consumer) {
        this.step = step;
        this.min = min;
        this.max = max;
        this.stepLength = stepLength;
        this.consumer = consumer;
        this.workspace = workspace;
    }

    public IntRangeObjectTask(int min, int max, int stepLength, GrowArray<T> workspace, IntRangeObjectConsumer<T> consumer) {
        this(-1, min, max, stepLength, workspace, consumer);
    }

    @Override
    public Void getRawResult() {
        return null;
    }

    @Override
    protected void setRawResult(Void value) {
    }

    @Override
    protected boolean exec() {
        int N = (this.max - this.min) / this.stepLength;
        if (this.step == -1) {
            int step;
            this.workspace.resize(N);
            IntRangeObjectTask<T> root = null;
            IntRangeObjectTask<T> previous = null;
            for (step = 0; step < N - 1; ++step) {
                IntRangeObjectTask<T> task = new IntRangeObjectTask<T>(step, this.min, this.max, this.stepLength, this.workspace, this.consumer);
                if (root == null) {
                    root = previous = task;
                } else {
                    ((IntRangeObjectTask)Objects.requireNonNull(previous)).next = task;
                    previous = task;
                }
                task.fork();
            }
            int index0 = step * this.stepLength + this.min;
            this.consumer.accept(this.workspace.get(N - 1), index0, this.max);
            while (root != null) {
                root.join();
                root = root.next;
            }
        } else {
            int index0 = this.step * this.stepLength + this.min;
            int index1 = index0 + this.stepLength;
            this.consumer.accept(this.workspace.get(this.step), index0, index1);
        }
        return true;
    }
}

