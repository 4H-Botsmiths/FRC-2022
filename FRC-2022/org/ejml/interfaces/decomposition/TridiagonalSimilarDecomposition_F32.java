/*
 * Decompiled with CFR 0.150.
 */
package org.ejml.interfaces.decomposition;

import org.ejml.data.Matrix;
import org.ejml.interfaces.decomposition.TridiagonalSimilarDecomposition;

public interface TridiagonalSimilarDecomposition_F32<MatrixType extends Matrix>
extends TridiagonalSimilarDecomposition<MatrixType> {
    public void getDiagonal(float[] var1, float[] var2);
}

