/*
 * Decompiled with CFR 0.150.
 */
package pabeles.concurrency;

import java.util.Objects;
import java.util.concurrent.ForkJoinTask;
import pabeles.concurrency.IntRangeConsumer;

public class IntRangeTask
extends ForkJoinTask<Void> {
    final int min;
    final int max;
    final int stepLength;
    final int step;
    final IntRangeConsumer consumer;
    IntRangeTask next;

    public IntRangeTask(int step, int min, int max, int stepLength, IntRangeConsumer consumer) {
        this.step = step;
        this.min = min;
        this.max = max;
        this.stepLength = stepLength;
        this.consumer = consumer;
    }

    public IntRangeTask(int min, int max, int stepLength, IntRangeConsumer consumer) {
        this(-1, min, max, stepLength, consumer);
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
            ForkJoinTask root = null;
            IntRangeTask previous = null;
            for (step = 0; step < N - 1; ++step) {
                IntRangeTask task = new IntRangeTask(step, this.min, this.max, this.stepLength, this.consumer);
                if (root == null) {
                    previous = task;
                    root = previous;
                } else {
                    ((IntRangeTask)Objects.requireNonNull(previous)).next = task;
                    previous = task;
                }
                task.fork();
            }
            int index0 = step * this.stepLength + this.min;
            this.consumer.accept(index0, this.max);
            while (root != null) {
                root.join();
                root = ((IntRangeTask)root).next;
            }
        } else {
            int index0 = this.step * this.stepLength + this.min;
            int index1 = index0 + this.stepLength;
            this.consumer.accept(index0, index1);
        }
        return true;
    }
}

