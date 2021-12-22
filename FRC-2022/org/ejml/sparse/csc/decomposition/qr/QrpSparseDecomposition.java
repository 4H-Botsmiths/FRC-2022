/*
 * Decompiled with CFR 0.150.
 */
package org.ejml.sparse.csc.decomposition.qr;

import org.ejml.data.Matrix;
import org.ejml.interfaces.decomposition.QRDecomposition;

public interface QrpSparseDecomposition<T extends Matrix>
extends QRDecomposition<T> {
    public int getRank();

    public int[] getColPivots();

    public T getColPivotMatrix(T var1);

    public int[] getRowPivots();

    public T getRowPivotMatrix(T var1);

    public boolean isColumnPivot();

    public boolean isRowPivot();
}

