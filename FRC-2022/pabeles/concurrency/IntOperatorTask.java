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
import pabeles.concurrency.IntProducerNumber;

public abstract class IntOperatorTask
extends ForkJoinTask<Number> {
    final int value;
    final int max;
    final IntProducerNumber consumer;
    boolean master = true;
    Number result;
    Class primitiveType;
    @Nullable
    IntOperatorTask next = null;

    protected IntOperatorTask(int value, int max, Class primitiveType, IntProducerNumber consumer) {
        this.value = value;
        this.max = max;
        this.primitiveType = primitiveType;
        this.consumer = consumer;
    }

    @Override
    public Number getRawResult() {
        return this.result;
    }

    @Override
    protected void setRawResult(Number value) {
        this.result = value;
    }

    @Override
    protected boolean exec() {
        ForkJoinTask root = null;
        IntOperatorTask previous = null;
        if (this.master) {
            int value = this.value;
            while (value + 1 < this.max) {
                IntOperatorTask child = this.newInstance(value, this.max, this.primitiveType, this.consumer);
                child.master = false;
                if (root == null) {
                    root = child;
                    previous = child;
                } else {
                    ((IntOperatorTask)Objects.requireNonNull(previous)).next = child;
                    previous = child;
                }
                child.fork();
                ++value;
            }
            this.result = this.consumer.accept(value);
        } else {
            this.result = this.consumer.accept(this.value);
        }
        while (root != null) {
            root.join();
            this.operator(((IntOperatorTask)root).result);
            IntOperatorTask next = ((IntOperatorTask)root).next;
            ((IntOperatorTask)root).next = null;
            root = next;
        }
        return true;
    }

    protected abstract IntOperatorTask newInstance(int var1, int var2, Class var3, IntProducerNumber var4);

    protected abstract void operator(Number var1);

    public static class Min
    extends IntOperatorTask {
        public Min(int value, int max, Class primitiveType, IntProducerNumber consumer) {
            super(value, max, primitiveType, consumer);
        }

        @Override
        protected IntOperatorTask newInstance(int value, int max, Class primitiveType, IntProducerNumber consumer) {
            return new Min(value, max, primitiveType, consumer);
        }

        @Override
        protected void operator(Number next) {
            if (this.primitiveType == Byte.TYPE) {
                this.result = Math.min(this.result.byteValue(), next.byteValue());
            } else if (this.primitiveType == Short.TYPE) {
                this.result = Math.min(this.result.shortValue(), next.shortValue());
            } else if (this.primitiveType == Integer.TYPE) {
                this.result = Math.min(this.result.intValue(), next.intValue());
            } else if (this.primitiveType == Long.TYPE) {
                this.result = Math.min(this.result.longValue(), next.longValue());
            } else if (this.primitiveType == Float.TYPE) {
                this.result = Float.valueOf(Math.min(this.result.floatValue(), next.floatValue()));
            } else if (this.primitiveType == Double.TYPE) {
                this.result = Math.min(this.result.doubleValue(), next.doubleValue());
            } else {
                throw new RuntimeException("Unknown primitive type " + this.primitiveType);
            }
        }
    }

    public static class Max
    extends IntOperatorTask {
        public Max(int value, int max, Class primitiveType, IntProducerNumber consumer) {
            super(value, max, primitiveType, consumer);
        }

        @Override
        protected IntOperatorTask newInstance(int value, int max, Class primitiveType, IntProducerNumber consumer) {
            return new Max(value, max, primitiveType, consumer);
        }

        @Override
        protected void operator(Number next) {
            if (this.primitiveType == Byte.TYPE) {
                this.result = Math.max(this.result.byteValue(), next.byteValue());
            } else if (this.primitiveType == Short.TYPE) {
                this.result = Math.max(this.result.shortValue(), next.shortValue());
            } else if (this.primitiveType == Integer.TYPE) {
                this.result = Math.max(this.result.intValue(), next.intValue());
            } else if (this.primitiveType == Long.TYPE) {
                this.result = Math.max(this.result.longValue(), next.longValue());
            } else if (this.primitiveType == Float.TYPE) {
                this.result = Float.valueOf(Math.max(this.result.floatValue(), next.floatValue()));
            } else if (this.primitiveType == Double.TYPE) {
                this.result = Math.max(this.result.doubleValue(), next.doubleValue());
            } else {
                throw new RuntimeException("Unknown primitive type " + this.primitiveType);
            }
        }
    }

    public static class Sum
    extends IntOperatorTask {
        public Sum(int value, int max, Class primitiveType, IntProducerNumber consumer) {
            super(value, max, primitiveType, consumer);
        }

        @Override
        protected IntOperatorTask newInstance(int value, int max, Class primitiveType, IntProducerNumber consumer) {
            return new Sum(value, max, primitiveType, consumer);
        }

        @Override
        protected void operator(Number next) {
            if (this.primitiveType == Byte.TYPE) {
                this.result = this.result.byteValue() + next.byteValue();
            } else if (this.primitiveType == Short.TYPE) {
                this.result = this.result.shortValue() + next.shortValue();
            } else if (this.primitiveType == Integer.TYPE) {
                this.result = this.result.intValue() + next.intValue();
            } else if (this.primitiveType == Long.TYPE) {
                this.result = this.result.longValue() + next.longValue();
            } else if (this.primitiveType == Float.TYPE) {
                this.result = Float.valueOf(this.result.floatValue() + next.floatValue());
            } else if (this.primitiveType == Double.TYPE) {
                this.result = this.result.doubleValue() + next.doubleValue();
            } else {
                throw new RuntimeException("Unknown primitive type " + this.primitiveType);
            }
        }
    }
}

