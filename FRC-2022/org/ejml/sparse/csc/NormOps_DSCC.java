/*
 * Decompiled with CFR 0.150.
 */
package org.ejml.sparse.csc;

import org.ejml.data.DMatrixSparseCSC;
import org.ejml.sparse.csc.CommonOps_DSCC;

public class NormOps_DSCC {
    public static double fastNormF(DMatrixSparseCSC A) {
        double total = 0.0;
        for (int i = 0; i < A.nz_length; ++i) {
            double x = A.nz_values[i];
            total += x * x;
        }
        return Math.sqrt(total);
    }

    public static double normF(DMatrixSparseCSC A) {
        double total = 0.0;
        double max = CommonOps_DSCC.elementMaxAbs(A);
        for (int i = 0; i < A.nz_length; ++i) {
            double x = A.nz_values[i] / max;
            total += x * x;
        }
        return max * Math.sqrt(total);
    }
}

