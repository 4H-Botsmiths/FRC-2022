/*
 * Decompiled with CFR 0.150.
 */
package edu.wpi.first.math.filter;

import edu.wpi.first.util.CircularBuffer;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MedianFilter {
    private final CircularBuffer m_valueBuffer;
    private final List<Double> m_orderedValues;
    private final int m_size;

    public MedianFilter(int size) {
        this.m_valueBuffer = new CircularBuffer(size);
        this.m_orderedValues = new ArrayList<Double>(size);
        this.m_size = size;
    }

    public double calculate(double next) {
        int index = Collections.binarySearch(this.m_orderedValues, next);
        if (index < 0) {
            index = Math.abs(index + 1);
        }
        this.m_orderedValues.add(index, next);
        int curSize = this.m_orderedValues.size();
        if (curSize > this.m_size) {
            this.m_orderedValues.remove(this.m_valueBuffer.removeLast());
            --curSize;
        }
        this.m_valueBuffer.addFirst(next);
        if (curSize % 2 != 0) {
            return this.m_orderedValues.get(curSize / 2);
        }
        return (this.m_orderedValues.get(curSize / 2 - 1) + this.m_orderedValues.get(curSize / 2)) / 2.0;
    }

    public void reset() {
        this.m_orderedValues.clear();
        this.m_valueBuffer.clear();
    }
}

