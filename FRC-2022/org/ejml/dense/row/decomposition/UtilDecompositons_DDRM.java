/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  org.jetbrains.annotations.Nullable
 */
package org.ejml.dense.row.decomposition;

import org.ejml.data.DMatrixRMaj;
import org.ejml.dense.row.CommonOps_DDRM;
import org.jetbrains.annotations.Nullable;

public class UtilDecompositons_DDRM {
    public static DMatrixRMaj ensureIdentity(@Nullable DMatrixRMaj A, int numRows, int numCols) {
        if (A == null) {
            return CommonOps_DDRM.identity(numRows, numCols);
        }
        A.reshape(numRows, numCols);
        CommonOps_DDRM.setIdentity(A);
        return A;
    }

    public static DMatrixRMaj ensureZeros(@Nullable DMatrixRMaj A, int numRows, int numCols) {
        if (A == null) {
            return new DMatrixRMaj(numRows, numCols);
        }
        A.reshape(numRows, numCols);
        A.zero();
        return A;
    }

    public static DMatrixRMaj checkZerosLT(@Nullable DMatrixRMaj A, int numRows, int numCols) {
        if (A == null) {
            return new DMatrixRMaj(numRows, numCols);
        }
        if (numRows != A.numRows || numCols != A.numCols) {
            A.reshape(numRows, numCols);
            A.zero();
        } else {
            for (int i = 0; i < A.numRows; ++i) {
                int index = i * A.numCols;
                int end = index + Math.min(i, A.numCols);
                while (index < end) {
                    A.data[index++] = 0.0;
                }
            }
        }
        return A;
    }

    public static DMatrixRMaj checkZerosUT(@Nullable DMatrixRMaj A, int numRows, int numCols) {
        if (A == null) {
            return new DMatrixRMaj(numRows, numCols);
        }
        if (numRows != A.numRows || numCols != A.numCols) {
            throw new IllegalArgumentException("Input is not " + numRows + " x " + numCols + " matrix");
        }
        int maxRows = Math.min(A.numRows, A.numCols);
        for (int i = 0; i < maxRows; ++i) {
            int index = i * A.numCols + i + 1;
            int end = i * A.numCols + A.numCols;
            while (index < end) {
                A.data[index++] = 0.0;
            }
        }
        return A;
    }
}

