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
import pabeles.concurrency.IntObjectConsumer;

public class IntObjectTask<T>
extends ForkJoinTask<Void> {
    final int idx0;
    final int idx1;
    final int step;
    final int maxThreads;
    final int whichThread;
    final IntObjectConsumer<T> consumer;
    final GrowArray<T> workspace;
    @Nullable
    final T data;
    @Nullable
    IntObjectTask<T> next;

    public IntObjectTask(int idx0, int idx1, int step, int maxThreads, int whichThread, GrowArray<T> workspace, IntObjectConsumer<T> consumer) {
        this.idx0 = idx0;
        this.idx1 = idx1;
        this.step = step;
        this.maxThreads = maxThreads;
        this.whichThread = whichThread;
        this.consumer = consumer;
        this.workspace = workspace;
        this.data = null;
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
        if (this.whichThread == -1) {
            int numIterations = (this.idx1 - this.idx0) / this.step + ((this.idx1 - this.idx0) % this.step != 0 ? 1 : 0);
            int numThreads = Math.min(numIterations, this.maxThreads);
            this.workspace.reset();
            this.workspace.resize(numThreads);
            IntObjectTask<T> root = null;
            IntObjectTask<T> previous = null;
            for (int threadId = 0; threadId < numThreads - 1; ++threadId) {
                int segment0 = this.computeIndex(threadId, numThreads, numIterations);
                int segment1 = this.computeIndex(threadId + 1, numThreads, numIterations);
                IntObjectTask<T> task = new IntObjectTask<T>(segment0, segment1, this.step, -1, threadId, this.workspace, this.consumer);
                if (root == null) {
                    previous = task;
                    root = previous;
                } else {
                    ((IntObjectTask)Objects.requireNonNull(previous)).next = task;
                    previous = task;
                }
                task.fork();
            }
            for (int index = this.computeIndex(numThreads - 1, numThreads, numIterations); index < this.idx1; index += this.step) {
                this.consumer.accept(this.workspace.get(numThreads - 1), index);
            }
            while (root != null) {
                root.join();
                root = root.next;
            }
        } else {
            T work = this.workspace.get(this.whichThread);
            for (int index = this.idx0; index < this.idx1; index += this.step) {
                this.consumer.accept(work, index);
            }
        }
        return true;
    }

    private int computeIndex(int threadId, int numThreads, int numIterations) {
        return this.idx0 + threadId * numIterations / numThreads * this.step;
    }
}

