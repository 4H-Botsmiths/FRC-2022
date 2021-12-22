/*
 * Decompiled with CFR 0.150.
 */
package org.ejml.data;

import org.ejml.MatrixDimensionException;
import org.ejml.UtilEjml;
import org.ejml.data.CMatrix;
import org.ejml.data.ReshapeMatrix;

public abstract class CMatrixD1
implements CMatrix,
ReshapeMatrix {
    public float[] data = UtilEjml.ZERO_LENGTH_F32;
    public int numRows;
    public int numCols;

    public float[] getData() {
        return this.data;
    }

    public void setData(float[] data) {
        this.data = data;
    }

    public abstract int getIndex(int var1, int var2);

    public void setTo(CMatrixD1 b) {
        if (this.numRows != b.numRows || this.numCols != b.numCols) {
            throw new MatrixDimensionException("The two matrices do not have compatible shapes.");
        }
        int dataLength = b.getDataLength();
        System.arraycopy(b.data, 0, this.data, 0, dataLength);
    }

    @Override
    public int getNumRows() {
        return this.numRows;
    }

    @Override
    public int getNumCols() {
        return this.numCols;
    }

    public void setNumRows(int numRows) {
        this.numRows = numRows;
    }

    public void setNumCols(int numCols) {
        this.numCols = numCols;
    }

    public int getNumElements() {
        return this.numRows * this.numCols;
    }
}

