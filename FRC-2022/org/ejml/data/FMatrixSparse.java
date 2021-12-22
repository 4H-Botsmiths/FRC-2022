/*
 * Decompiled with CFR 0.150.
 */
package org.ejml.data;

import java.util.Iterator;
import org.ejml.data.FMatrix;
import org.ejml.data.MatrixSparse;

public interface FMatrixSparse
extends FMatrix,
MatrixSparse {
    public float get(int var1, int var2, float var3);

    public float unsafe_get(int var1, int var2, float var3);

    public Iterator<CoordinateRealValue> createCoordinateIterator();

    public static class CoordinateRealValue {
        public int row;
        public int col;
        public float value;
    }
}

