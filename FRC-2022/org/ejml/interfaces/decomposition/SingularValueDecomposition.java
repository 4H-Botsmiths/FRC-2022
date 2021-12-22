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

public interface SingularValueDecomposition<T extends Matrix>
extends DecompositionInterface<T> {
    public int numberOfSingularValues();

    public boolean isCompact();

    public T getU(@Nullable T var1, boolean var2);

    public T getV(@Nullable T var1, boolean var2);

    public T getW(@Nullable T var1);

    public int numRows();

    public int numCols();
}

