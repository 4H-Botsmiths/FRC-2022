/*
 * Decompiled with CFR 0.150.
 */
package org.ejml.data;

import org.ejml.data.Matrix;

public interface DMatrix
extends Matrix {
    public double get(int var1, int var2);

    public double unsafe_get(int var1, int var2);

    public void set(int var1, int var2, double var3);

    public void unsafe_set(int var1, int var2, double var3);

    default public int getNumElements() {
        return this.getNumRows() * this.getNumCols();
    }
}

