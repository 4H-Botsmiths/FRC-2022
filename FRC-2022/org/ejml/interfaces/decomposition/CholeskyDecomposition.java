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

public interface CholeskyDecomposition<MatrixType extends Matrix>
extends DecompositionInterface<MatrixType> {
    public boolean isLower();

    public MatrixType getT(@Nullable MatrixType var1);
}

