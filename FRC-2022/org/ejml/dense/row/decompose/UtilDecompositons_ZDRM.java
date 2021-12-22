/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  org.jetbrains.annotations.Nullable
 */
package org.ejml.dense.row.decompose;

import org.ejml.data.ZMatrixRMaj;
import org.ejml.dense.row.CommonOps_ZDRM;
import org.jetbrains.annotations.Nullable;

public class UtilDecompositons_ZDRM {
    public static ZMatrixRMaj checkIdentity(@Nullable ZMatrixRMaj A, int numRows, int numCols) {
        if (A == null) {
            return CommonOps_ZDRM.identity(numRows, numCols);
        }
        if (numRows != A.numRows || numCols != A.numCols) {
            throw new IllegalArgumentException("Input is not " + numRows + " x " + numCols + " matrix");
        }
        CommonOps_ZDRM.setIdentity(A);
        return A;
    }

    public static ZMatrixRMaj checkZeros(@Nullable ZMatrixRMaj A, int numRows, int numCols) {
        if (A == null) {
            return new ZMatrixRMaj(numRows, numCols);
        }
        if (numRows != A.numRows || numCols != A.numCols) {
            throw new IllegalArgumentException("Input is not " + numRows + " x " + numCols + " matrix");
        }
        A.zero();
        return A;
    }

    public static ZMatrixRMaj checkZerosLT(@Nullable ZMatrixRMaj A, int numRows, int numCols) {
        if (A == null) {
            return new ZMatrixRMaj(numRows, numCols);
        }
        if (numRows != A.numRows || numCols != A.numCols) {
            throw new IllegalArgumentException("Input is not " + numRows + " x " + numCols + " matrix");
        }
        for (int i = 0; i < A.numRows; ++i) {
            int index = i * A.numCols * 2;
            int end = index + Math.min(i, A.numCols) * 2;
            while (index < end) {
                A.data[index++] = 0.0;
            }
        }
        return A;
    }

    public static ZMatrixRMaj checkZerosUT(@Nullable ZMatrixRMaj A, int numRows, int numCols) {
        if (A == null) {
            return new ZMatrixRMaj(numRows, numCols);
        }
        if (numRows != A.numRows || numCols != A.numCols) {
            throw new IllegalArgumentException("Input is not " + numRows + " x " + numCols + " matrix");
        }
        int maxRows = Math.min(A.numRows, A.numCols);
        for (int i = 0; i < maxRows; ++i) {
            int index = (i * A.numCols + i + 1) * 2;
            int end = (i * A.numCols + A.numCols) * 2;
            while (index < end) {
                A.data[index++] = 0.0;
            }
        }
        return A;
    }
}

