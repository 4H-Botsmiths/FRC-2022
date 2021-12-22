/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  org.jetbrains.annotations.NotNull
 */
package pabeles.concurrency;

import java.lang.reflect.Array;
import org.jetbrains.annotations.NotNull;
import pabeles.concurrency.ConcurrencyOps;

public class GrowArray<D> {
    ConcurrencyOps.NewInstance<D> factory;
    ConcurrencyOps.Reset<D> reset;
    Class<D> elementType;
    D[] array;
    int size;

    public GrowArray(ConcurrencyOps.NewInstance<D> factory) {
        this(factory, o -> {});
    }

    public GrowArray(ConcurrencyOps.NewInstance<D> factory, ConcurrencyOps.Reset<D> reset) {
        this(factory, reset, factory.newInstance().getClass());
    }

    public GrowArray(ConcurrencyOps.NewInstance<D> factory, ConcurrencyOps.Reset<D> reset, Class<D> type) {
        this.factory = factory;
        this.reset = reset;
        this.elementType = type;
        this.array = this.createArray(0);
        this.size = 0;
    }

    @NotNull
    private D[] createArray(int length) {
        return (Object[])Array.newInstance(this.elementType, length);
    }

    public void reset() {
        this.size = 0;
    }

    public void resize(int length) {
        if (length >= this.array.length) {
            D[] tmp = this.createArray(length);
            System.arraycopy(this.array, 0, tmp, 0, this.array.length);
            for (int i = this.array.length; i < tmp.length; ++i) {
                tmp[i] = this.factory.newInstance();
            }
            this.array = tmp;
        }
        for (int i = this.size; i < length; ++i) {
            this.reset.reset(this.array[i]);
        }
        this.size = length;
    }

    public D grow() {
        if (this.size == this.array.length) {
            int length = Math.max(10, this.size < 1000 ? this.size * 2 : this.size * 5 / 3);
            D[] tmp = this.createArray(length);
            System.arraycopy(this.array, 0, tmp, 0, this.array.length);
            for (int i = this.array.length; i < tmp.length; ++i) {
                tmp[i] = this.factory.newInstance();
            }
            this.array = tmp;
        }
        D ret = this.array[this.size++];
        this.reset.reset(ret);
        return ret;
    }

    public void releaseInternalArray() {
        this.array = this.createArray(0);
        this.size = 0;
    }

    public D get(int index) {
        return this.array[index];
    }

    public int size() {
        return this.size;
    }
}

