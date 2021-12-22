/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  org.jetbrains.annotations.Nullable
 */
package org.ejml.interfaces.decomposition;

import org.ejml.data.Matrix;
import org.ejml.interfaces.decomposition.DecompositionInterface;
import org.jetbrains.annotations.Nullable;

public interface TridiagonalSimilarDecomposition<MatrixType extends Matrix>
extends DecompositionInterface<MatrixType> {
    public MatrixType getT(@Nullable MatrixType var1);

    public MatrixType getQ(@Nullable MatrixType var1, boolean var2);
}

