/*
 * Decompiled with CFR 0.150.
 */
package org.ejml.interfaces.decomposition;

import org.ejml.data.Matrix;
import org.ejml.interfaces.decomposition.BidiagonalDecomposition;

public interface BidiagonalDecomposition_F64<T extends Matrix>
extends BidiagonalDecomposition<T> {
    public void getDiagonal(double[] var1, double[] var2);
}

