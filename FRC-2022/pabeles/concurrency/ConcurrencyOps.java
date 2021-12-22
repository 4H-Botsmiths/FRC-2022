/*
 * Decompiled with CFR 0.150.
 */
package pabeles.concurrency;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinTask;
import java.util.function.IntConsumer;
import java.util.stream.IntStream;
import pabeles.concurrency.GrowArray;
import pabeles.concurrency.IntObjectConsumer;
import pabeles.concurrency.IntObjectTask;
import pabeles.concurrency.IntOperatorTask;
import pabeles.concurrency.IntProducerNumber;
import pabeles.concurrency.IntRangeConsumer;
import pabeles.concurrency.IntRangeObjectConsumer;
import pabeles.concurrency.IntRangeObjectTask;
import pabeles.concurrency.IntRangeTask;

public class ConcurrencyOps {
    private static ForkJoinPool pool = new ForkJoinPool();

    public static void setMaxThreads(int maxThreads) {
        pool = new ForkJoinPool(Math.max(1, maxThreads));
    }

    public static int getMaxThreads() {
        return pool.getParallelism();
    }

    public static void loopFor(int start, int endExclusive, IntConsumer consumer) {
        try {
            ((ForkJoinTask)pool.submit(() -> IntStream.range(start, endExclusive).parallel().forEach(consumer))).get();
        }
        catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
    }

    public static void loopFor(int start, int endExclusive, int step, IntConsumer consumer) {
        if (step <= 0) {
            throw new IllegalArgumentException("Step must be a positive number.");
        }
        if (start >= endExclusive) {
            return;
        }
        try {
            int range = endExclusive - start;
            int iterations = range / step + (range % step == 0 ? 0 : 1);
            ((ForkJoinTask)pool.submit(() -> IntStream.range(0, iterations).parallel().forEach(i -> consumer.accept(start + i * step)))).get();
        }
        catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
    }

    public static <T> void loopFor(int start, int endExclusive, int step, GrowArray<T> workspace, IntObjectConsumer<T> consumer) {
        if (step <= 0) {
            throw new IllegalArgumentException("Step must be a positive number.");
        }
        if (start >= endExclusive) {
            return;
        }
        try {
            pool.submit(new IntObjectTask<T>(start, endExclusive, step, pool.getParallelism(), -1, workspace, consumer)).get();
        }
        catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
    }

    public static void loopBlocks(int start, int endExclusive, int minBlock, IntRangeConsumer consumer) {
        ForkJoinPool pool = ConcurrencyOps.pool;
        int numThreads = pool.getParallelism();
        int range = endExclusive - start;
        if (range == 0) {
            return;
        }
        if (range < 0) {
            throw new IllegalArgumentException("end must be more than start. " + start + " -> " + endExclusive);
        }
        int block = ConcurrencyOps.selectBlockSize(range, minBlock, numThreads);
        try {
            pool.submit(new IntRangeTask(start, endExclusive, block, consumer)).get();
        }
        catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
    }

    static int selectBlockSize(int range, int minBlock, int numThreads) {
        int block = Math.max(minBlock, range / numThreads);
        int N = Math.max(1, range / block);
        return range / N;
    }

    public static void loopBlocks(int start, int endExclusive, IntRangeConsumer consumer) {
        ForkJoinPool pool = ConcurrencyOps.pool;
        int numThreads = pool.getParallelism();
        int range = endExclusive - start;
        if (range == 0) {
            return;
        }
        if (range < 0) {
            throw new IllegalArgumentException("end must be more than start. " + start + " -> " + endExclusive);
        }
        int blockSize = Math.max(1, range / numThreads);
        try {
            pool.submit(new IntRangeTask(start, endExclusive, blockSize, consumer)).get();
        }
        catch (InterruptedException | ExecutionException e) {
            throw new RuntimeException(e);
        }
    }

    public static <T> void loopBlocks(int start, int endExclusive, GrowArray<T> workspace, IntRangeObjectConsumer<T> consumer) {
        ForkJoinPool pool = ConcurrencyOps.pool;
        int numThreads = pool.getParallelism();
        int range = endExclusive - start;
        if (range == 0) {
            return;
        }
        if (range < 0) {
            throw new IllegalArgumentException("end must be more than start. " + start + " -> " + endExclusive);
        }
        int blockSize = Math.max(1, range / numThreads);
        ConcurrencyOps.runLoopBlocks(start, endExclusive, workspace, consumer, pool, blockSize);
    }

    public static <T> void loopBlocks(int start, int endExclusive, int minBlock, GrowArray<T> workspace, IntRangeObjectConsumer<T> consumer) {
        ForkJoinPool pool = ConcurrencyOps.pool;
        int numThreads = pool.getParallelism();
        int range = endExclusive - start;
        if (range == 0) {
            return;
        }
        if (range < 0) {
            throw new IllegalArgumentException("end must be more than start. " + start + " -> " + endExclusive);
        }
        int blockSize = ConcurrencyOps.selectBlockSize(range, minBlock, numThreads);
        ConcurrencyOps.runLoopBlocks(start, endExclusive, workspace, consumer, pool, blockSize);
    }

    private static <T> void runLoopBlocks(int start, int endExclusive, GrowArray<T> workspace, IntRangeObjectConsumer<T> consumer, ForkJoinPool pool, int blockSize) {
        workspace.reset();
        try {
            pool.submit(new IntRangeObjectTask<T>(start, endExclusive, blockSize, workspace, consumer)).get();
        }
        catch (InterruptedException | ExecutionException e) {
            throw new RuntimeException(e);
        }
    }

    public static Number sum(int start, int endExclusive, Class type, IntProducerNumber producer) {
        try {
            return pool.submit(new IntOperatorTask.Sum(start, endExclusive, type, producer)).get();
        }
        catch (InterruptedException | ExecutionException e) {
            throw new RuntimeException(e);
        }
    }

    public static Number max(int start, int endExclusive, Class type, IntProducerNumber producer) {
        try {
            return pool.submit(new IntOperatorTask.Max(start, endExclusive, type, producer)).get();
        }
        catch (InterruptedException | ExecutionException e) {
            throw new RuntimeException(e);
        }
    }

    public static Number min(int start, int endExclusive, Class type, IntProducerNumber producer) {
        try {
            return pool.submit(new IntOperatorTask.Min(start, endExclusive, type, producer)).get();
        }
        catch (InterruptedException | ExecutionException e) {
            throw new RuntimeException(e);
        }
    }

    public static ForkJoinPool getThreadPool() {
        return pool;
    }

    public static interface Reset<D> {
        public void reset(D var1);
    }

    public static interface NewInstance<D> {
        public D newInstance();
    }
}

