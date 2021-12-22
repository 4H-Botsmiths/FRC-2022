/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  org.jetbrains.annotations.Nullable
 */
package org.ejml.interfaces.decomposition;

import org.ejml.data.Matrix;
import org.ejml.interfaces.decomposition.QRDecomposition;
import org.jetbrains.annotations.Nullable;

public interface QRPDecomposition<T extends Matrix>
extends QRDecomposition<T> {
    public int getRank();

    public int[] getColPivots();

    public T getColPivotMatrix(@Nullable T var1);
}

