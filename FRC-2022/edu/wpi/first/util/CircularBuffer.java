/*
 * Decompiled with CFR 0.150.
 */
package edu.wpi.first.util;

public class CircularBuffer {
    private double[] m_data;
    private int m_front;
    private int m_length;

    public CircularBuffer(int size) {
        this.m_data = new double[size];
        for (int i = 0; i < this.m_data.length; ++i) {
            this.m_data[i] = 0.0;
        }
    }

    double size() {
        return this.m_length;
    }

    double getFirst() {
        return this.m_data[this.m_front];
    }

    double getLast() {
        if (this.m_length == 0) {
            return 0.0;
        }
        return this.m_data[(this.m_front + this.m_length - 1) % this.m_data.length];
    }

    public void addFirst(double value) {
        if (this.m_data.length == 0) {
            return;
        }
        this.m_front = this.moduloDec(this.m_front);
        this.m_data[this.m_front] = value;
        if (this.m_length < this.m_data.length) {
            ++this.m_length;
        }
    }

    public void addLast(double value) {
        if (this.m_data.length == 0) {
            return;
        }
        this.m_data[(this.m_front + this.m_length) % this.m_data.length] = value;
        if (this.m_length < this.m_data.length) {
            ++this.m_length;
        } else {
            this.m_front = this.moduloInc(this.m_front);
        }
    }

    public double removeFirst() {
        if (this.m_length == 0) {
            return 0.0;
        }
        double temp = this.m_data[this.m_front];
        this.m_front = this.moduloInc(this.m_front);
        --this.m_length;
        return temp;
    }

    public double removeLast() {
        if (this.m_length == 0) {
            return 0.0;
        }
        --this.m_length;
        return this.m_data[(this.m_front + this.m_length) % this.m_data.length];
    }

    void resize(int size) {
        double[] newBuffer = new double[size];
        this.m_length = Math.min(this.m_length, size);
        for (int i = 0; i < this.m_length; ++i) {
            newBuffer[i] = this.m_data[(this.m_front + i) % this.m_data.length];
        }
        this.m_data = newBuffer;
        this.m_front = 0;
    }

    public void clear() {
        for (int i = 0; i < this.m_data.length; ++i) {
            this.m_data[i] = 0.0;
        }
        this.m_front = 0;
        this.m_length = 0;
    }

    public double get(int index) {
        return this.m_data[(this.m_front + index) % this.m_data.length];
    }

    private int moduloInc(int index) {
        return (index + 1) % this.m_data.length;
    }

    private int moduloDec(int index) {
        if (index == 0) {
            return this.m_data.length - 1;
        }
        return index - 1;
    }
}

