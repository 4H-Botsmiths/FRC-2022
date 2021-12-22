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

public interface QRDecomposition<T extends Matrix>
extends DecompositionInterface<T> {
    public T getQ(@Nullable T var1, boolean var2);

    public T getR(@Nullable T var1, boolean var2);
}

