/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  org.jetbrains.annotations.Nullable
 */
package org.ejml.interfaces.decomposition;

import org.ejml.data.IGrowArray;
import org.ejml.data.Matrix;
import org.ejml.interfaces.decomposition.DecompositionInterface;
import org.jetbrains.annotations.Nullable;

public interface LUDecomposition<T extends Matrix>
extends DecompositionInterface<T> {
    public T getLower(@Nullable T var1);

    public T getUpper(@Nullable T var1);

    public T getRowPivot(@Nullable T var1);

    public int[] getRowPivotV(@Nullable IGrowArray var1);

    public boolean isSingular();
}

