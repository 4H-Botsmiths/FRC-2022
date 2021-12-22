/*
 * Decompiled with CFR 0.150.
 */
package org.ejml.data;

public class IGrowArray {
    public int[] data;
    public int length;

    public IGrowArray(int length) {
        this.data = new int[length];
        this.length = length;
    }

    public IGrowArray() {
        this(0);
    }

    public int length() {
        return this.length;
    }

    public void reshape(int length) {
        if (this.data.length < length) {
            this.data = new int[length];
        }
        this.length = length;
    }

    public void growInternal(int amount) {
        int[] tmp = new int[this.data.length + amount];
        System.arraycopy(this.data, 0, tmp, 0, this.data.length);
        this.data = tmp;
    }

    public void setTo(IGrowArray original) {
        this.reshape(original.length);
        System.arraycopy(original.data, 0, this.data, 0, original.length);
    }

    public int get(int index) {
        if (index < 0 || index >= this.length) {
            throw new IllegalArgumentException("Out of bounds");
        }
        return this.data[index];
    }

    public void set(int index, int value) {
        if (index < 0 || index >= this.length) {
            throw new IllegalArgumentException("Out of bounds");
        }
        this.data[index] = value;
    }

    public void add(int value) {
        if (this.length == this.data.length) {
            this.growInternal(Math.min(10000, 1 + this.data.length));
        }
        this.data[this.length++] = value;
    }

    public void clear() {
        this.length = 0;
    }

    public void free() {
        this.data = new int[0];
        this.length = 0;
    }
}

