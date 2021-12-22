/*
 * Decompiled with CFR 0.150.
 */
package org.ejml.data;

import org.ejml.data.DMatrix;
import org.ejml.data.DMatrixD1;
import org.ejml.data.DMatrixRMaj;
import org.ejml.data.Submatrix;
import org.ejml.ops.MatrixIO;

public class DSubmatrixD1
extends Submatrix<DMatrixD1> {
    public DSubmatrixD1() {
    }

    public DSubmatrixD1(DMatrixD1 original) {
        this.set(original);
    }

    public DSubmatrixD1(DMatrixD1 original, int row0, int row1, int col0, int col1) {
        this.set(original, row0, row1, col0, col1);
    }

    public double get(int row, int col) {
        return ((DMatrixD1)this.original).get(row + this.row0, col + this.col0);
    }

    public void set(int row, int col, double value) {
        ((DMatrixD1)this.original).set(row + this.row0, col + this.col0, value);
    }

    public DMatrixRMaj extract() {
        DMatrixRMaj ret = new DMatrixRMaj(this.row1 - this.row0, this.col1 - this.col0);
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
        MatrixIO.print(System.out, (DMatrix)this.original, "%6.3f", this.row0, this.row1, this.col0, this.col1);
    }
}

