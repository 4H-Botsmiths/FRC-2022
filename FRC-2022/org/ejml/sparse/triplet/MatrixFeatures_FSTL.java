/*
 * Decompiled with CFR 0.150.
 */
package org.ejml.sparse.triplet;

import org.ejml.data.FMatrixSparseTriplet;

public class MatrixFeatures_FSTL {
    public static boolean isEquals(FMatrixSparseTriplet a, FMatrixSparseTriplet b) {
        if (!MatrixFeatures_FSTL.isSameShape(a, b)) {
            return false;
        }
        for (int i = 0; i < a.nz_length; ++i) {
            int arow = a.nz_rowcol.data[i * 2];
            int acol = a.nz_rowcol.data[i * 2 + 1];
            float avalue = a.nz_value.data[i];
            int bindex = b.nz_index(arow, acol);
            if (bindex < 0) {
                return false;
            }
            float bvalue = b.nz_value.data[bindex];
            if (avalue == bvalue) continue;
            return false;
        }
        return true;
    }

    public static boolean isEquals(FMatrixSparseTriplet a, FMatrixSparseTriplet b, float tol) {
        if (!MatrixFeatures_FSTL.isSameShape(a, b)) {
            return false;
        }
        for (int i = 0; i < a.nz_length; ++i) {
            int arow = a.nz_rowcol.data[i * 2];
            int acol = a.nz_rowcol.data[i * 2 + 1];
            float avalue = a.nz_value.data[i];
            int bindex = b.nz_index(arow, acol);
            if (bindex < 0) {
                return false;
            }
            float bvalue = b.nz_value.data[bindex];
            if (!(Math.abs(avalue - bvalue) > tol)) continue;
            return false;
        }
        return true;
    }

    public static boolean isSameShape(FMatrixSparseTriplet a, FMatrixSparseTriplet b) {
        return a.numRows == b.numRows && a.numCols == b.numCols && a.nz_length == b.nz_length;
    }
}

