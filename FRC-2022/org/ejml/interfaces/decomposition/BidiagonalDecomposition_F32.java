/*
 * Decompiled with CFR 0.150.
 */
package org.ejml.interfaces.decomposition;

import org.ejml.data.Matrix;
import org.ejml.interfaces.decomposition.BidiagonalDecomposition;

public interface BidiagonalDecomposition_F32<T extends Matrix>
extends BidiagonalDecomposition<T> {
    public void getDiagonal(float[] var1, float[] var2);
}

