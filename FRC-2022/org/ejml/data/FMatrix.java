/*
 * Decompiled with CFR 0.150.
 */
package org.ejml.data;

import org.ejml.data.Matrix;

public interface FMatrix
extends Matrix {
    public float get(int var1, int var2);

    public float unsafe_get(int var1, int var2);

    public void set(int var1, int var2, float var3);

    public void unsafe_set(int var1, int var2, float var3);

    default public int getNumElements() {
        return this.getNumRows() * this.getNumCols();
    }
}

