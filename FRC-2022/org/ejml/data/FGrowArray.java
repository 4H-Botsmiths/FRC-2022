/*
 * Decompiled with CFR 0.150.
 */
package org.ejml.data;

public class FGrowArray {
    public float[] data;
    public int length;

    public FGrowArray(int length) {
        this.data = new float[length];
        this.length = length;
    }

    public FGrowArray() {
        this(0);
    }

    public int length() {
        return this.length;
    }

    public void reset() {
        this.reshape(0);
    }

    public FGrowArray reshape(int length) {
        if (this.data.length < length) {
            this.data = new float[length];
        }
        this.length = length;
        return this;
    }

    public void growInternal(int amount) {
        float[] tmp = new float[this.data.length + amount];
        System.arraycopy(this.data, 0, tmp, 0, this.data.length);
        this.data = tmp;
    }

    public void setTo(FGrowArray original) {
        this.reshape(original.length);
        System.arraycopy(original.data, 0, this.data, 0, original.length);
    }

    public void add(float value) {
        if (this.length >= this.data.length) {
            this.growInternal(Math.min(500000, this.data.length + 10));
        }
        this.data[this.length++] = value;
    }

    public float get(int index) {
        if (index < 0 || index >= this.length) {
            throw new IllegalArgumentException("Out of bounds");
        }
        return this.data[index];
    }

    public void set(int index, float value) {
        if (index < 0 || index >= this.length) {
            throw new IllegalArgumentException("Out of bounds");
        }
        this.data[index] = value;
    }

    public void free() {
        this.data = new float[0];
        this.length = 0;
    }
}

