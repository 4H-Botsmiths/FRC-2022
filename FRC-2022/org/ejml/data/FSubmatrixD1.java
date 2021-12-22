/*
 * Decompiled with CFR 0.150.
 */
package org.ejml.data;

import org.ejml.data.FMatrix;
import org.ejml.data.FMatrixD1;
import org.ejml.data.FMatrixRMaj;
import org.ejml.data.Submatrix;
import org.ejml.ops.MatrixIO;

public class FSubmatrixD1
extends Submatrix<FMatrixD1> {
    public FSubmatrixD1() {
    }

    public FSubmatrixD1(FMatrixD1 original) {
        this.set(original);
    }

    public FSubmatrixD1(FMatrixD1 original, int row0, int row1, int col0, int col1) {
        this.set(original, row0, row1, col0, col1);
    }

    public float get(int row, int col) {
        return ((FMatrixD1)this.original).get(row + this.row0, col + this.col0);
    }

    public void set(int row, int col, float value) {
        ((FMatrixD1)this.original).set(row + this.row0, col + this.col0, value);
    }

    public FMatrixRMaj extract() {
        FMatrixRMaj ret = new FMatrixRMaj(this.row1 - this.row0, this.col1 - this.col0);
        for (int i = 0; i < ret.numRows; ++i) {
            for (int j = 0; j < ret.numCols; ++j) {
                ret.set(i, j, this.get(i, j));
            }
        }
        return ret;
    }

    @Override
    public void print() {
        if (this.original == null) {
            throw new RuntimeException("Uninitialized submatrix");
        }
        MatrixIO.print(System.out, (FMatrix)this.original, "%6.3ff", this.row0, this.row1, this.col0, this.col1);
    }
}

