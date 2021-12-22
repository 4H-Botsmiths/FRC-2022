/*
 * Decompiled with CFR 0.150.
 */
package org.ejml.data;

import java.util.Iterator;
import org.ejml.data.DMatrix;
import org.ejml.data.MatrixSparse;

public interface DMatrixSparse
extends DMatrix,
MatrixSparse {
    public double get(int var1, int var2, double var3);

    public double unsafe_get(int var1, int var2, double var3);

    public Iterator<CoordinateRealValue> createCoordinateIterator();

    public static class CoordinateRealValue {
        public int row;
        public int col;
        public double value;
    }
}

