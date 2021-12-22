/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  org.jetbrains.annotations.Nullable
 */
package org.ejml.dense.row.decompose;

import org.ejml.data.CMatrixRMaj;
import org.ejml.dense.row.CommonOps_CDRM;
import org.jetbrains.annotations.Nullable;

public class UtilDecompositons_CDRM {
    public static CMatrixRMaj checkIdentity(@Nullable CMatrixRMaj A, int numRows, int numCols) {
        if (A == null) {
            return CommonOps_CDRM.identity(numRows, numCols);
        }
        if (numRows != A.numRows || numCols != A.numCols) {
            throw new IllegalArgumentException("Input is not " + numRows + " x " + numCols + " matrix");
        }
        CommonOps_CDRM.setIdentity(A);
        return A;
    }

    public static CMatrixRMaj checkZeros(@Nullable CMatrixRMaj A, int numRows, int numCols) {
        if (A == null) {
            return new CMatrixRMaj(numRows, numCols);
        }
        if (numRows != A.numRows || numCols != A.numCols) {
            throw new IllegalArgumentException("Input is not " + numRows + " x " + numCols + " matrix");
        }
        A.zero();
        return A;
    }

    public static CMatrixRMaj checkZerosLT(@Nullable CMatrixRMaj A, int numRows, int numCols) {
        if (A == null) {
            return new CMatrixRMaj(numRows, numCols);
        }
        if (numRows != A.numRows || numCols != A.numCols) {
            throw new IllegalArgumentException("Input is not " + numRows + " x " + numCols + " matrix");
        }
        for (int i = 0; i < A.numRows; ++i) {
            int index = i * A.numCols * 2;
            int end = index + Math.min(i, A.numCols) * 2;
            while (index < end) {
                A.data[index++] = 0.0f;
            }
        }
        return A;
    }

    public static CMatrixRMaj checkZerosUT(@Nullable CMatrixRMaj A, int numRows, int numCols) {
        if (A == null) {
            return new CMatrixRMaj(numRows, numCols);
        }
        if (numRows != A.numRows || numCols != A.numCols) {
            throw new IllegalArgumentException("Input is not " + numRows + " x " + numCols + " matrix");
        }
        int maxRows = Math.min(A.numRows, A.numCols);
        for (int i = 0; i < maxRows; ++i) {
            int index = (i * A.numCols + i + 1) * 2;
            int end = (i * A.numCols + A.numCols) * 2;
            while (index < end) {
                A.data[index++] = 0.0f;
            }
        }
        return A;
    }
}

