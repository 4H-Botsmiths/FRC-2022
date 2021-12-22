/*
 * Decompiled with CFR 0.150.
 */
package org.ejml.dense.row.misc;

import org.ejml.data.FMatrixRMaj;
import org.ejml.interfaces.linsol.ReducedRowEchelonForm_F32;

public class RrefGaussJordanRowPivot_FDRM
implements ReducedRowEchelonForm_F32<FMatrixRMaj> {
    float tol;

    @Override
    public void setTolerance(float tol) {
        this.tol = tol;
    }

    @Override
    public void reduce(FMatrixRMaj A, int coefficientColumns) {
        if (A.numCols < coefficientColumns) {
            throw new IllegalArgumentException("The system must be at least as wide as A");
        }
        int leadIndex = 0;
        for (int i = 0; i < coefficientColumns; ++i) {
            int row;
            int pivotRow = -1;
            float maxValue = this.tol;
            for (row = leadIndex; row < A.numRows; ++row) {
                float v = Math.abs(A.data[row * A.numCols + i]);
                if (!(v > maxValue)) continue;
                maxValue = v;
                pivotRow = row;
            }
            if (pivotRow == -1) continue;
            if (leadIndex != pivotRow) {
                RrefGaussJordanRowPivot_FDRM.swapRows(A, leadIndex, pivotRow);
            }
            for (row = 0; row < A.numRows; ++row) {
                if (row == leadIndex) continue;
                int indexPivot = leadIndex * A.numCols + i;
                int indexTarget = row * A.numCols + i;
                float alpha = A.data[indexTarget] / A.data[indexPivot++];
                A.data[indexTarget++] = 0.0f;
                for (int col = i + 1; col < A.numCols; ++col) {
                    int n = indexTarget++;
                    A.data[n] = A.data[n] - A.data[indexPivot++] * alpha;
                }
            }
            int indexPivot = leadIndex * A.numCols + i;
            float alpha = 1.0f / A.data[indexPivot];
            A.data[indexPivot++] = 1.0f;
            for (int col = i + 1; col < A.numCols; ++col) {
                int n = indexPivot++;
                A.data[n] = A.data[n] * alpha;
            }
            ++leadIndex;
        }
    }

    protected static void swapRows(FMatrixRMaj A, int rowA, int rowB) {
        int indexA = rowA * A.numCols;
        int indexB = rowB * A.numCols;
        int i = 0;
        while (i < A.numCols) {
            float temp = A.data[indexA];
            A.data[indexA] = A.data[indexB];
            A.data[indexB] = temp;
            ++i;
            ++indexA;
            ++indexB;
        }
    }
}

