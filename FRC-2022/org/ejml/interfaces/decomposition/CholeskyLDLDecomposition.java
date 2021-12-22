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

public interface CholeskyLDLDecomposition<MatrixType extends Matrix>
extends DecompositionInterface<MatrixType> {
    public MatrixType getL(@Nullable MatrixType var1);

    public MatrixType getD(@Nullable MatrixType var1);
}

