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

public interface BidiagonalDecomposition<T extends Matrix>
extends DecompositionInterface<T> {
    public T getB(@Nullable T var1, boolean var2);

    public T getU(@Nullable T var1, boolean var2, boolean var3);

    public T getV(@Nullable T var1, boolean var2, boolean var3);
}

