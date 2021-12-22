/*
 * Decompiled with CFR 0.150.
 */
package org.ejml.data;

public class DGrowArray {
    public double[] data;
    public int length;

    public DGrowArray(int length) {
        this.data = new double[length];
        this.length = length;
    }

    public DGrowArray() {
        this(0);
    }

    public int length() {
        return this.length;
    }

    public void reset() {
        this.reshape(0);
    }

    public DGrowArray reshape(int length) {
        if (this.data.length < length) {
            this.data = new double[length];
        }
        this.length = length;
        return this;
    }

    public void growInternal(int amount) {
        double[] tmp = new double[this.data.length + amount];
        System.arraycopy(this.data, 0, tmp, 0, this.data.length);
        this.data = tmp;
    }

    public void setTo(DGrowArray original) {
        this.reshape(original.length);
        System.arraycopy(original.data, 0, this.data, 0, original.length);
    }

    public void add(double value) {
        if (this.length >= this.data.length) {
            this.growInternal(Math.min(500000, this.data.length + 10));
        }
        this.data[this.length++] = value;
    }

    public double get(int index) {
        if (index < 0 || index >= this.length) {
            throw new IllegalArgumentException("Out of bounds");
        }
        return this.data[index];
    }

    public void set(int index, double value) {
        if (index < 0 || index >= this.length) {
            throw new IllegalArgumentException("Out of bounds");
        }
        this.data[index] = value;
    }

    public void free() {
        this.data = new double[0];
        this.length = 0;
    }
}

