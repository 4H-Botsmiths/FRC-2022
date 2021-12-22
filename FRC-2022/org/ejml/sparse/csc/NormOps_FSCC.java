/*
 * Decompiled with CFR 0.150.
 */
package org.ejml.sparse.csc;

import org.ejml.data.FMatrixSparseCSC;
import org.ejml.sparse.csc.CommonOps_FSCC;

public class NormOps_FSCC {
    public static float fastNormF(FMatrixSparseCSC A) {
        float total = 0.0f;
        for (int i = 0; i < A.nz_length; ++i) {
            float x = A.nz_values[i];
            total += x * x;
        }
        return (float)Math.sqrt(total);
    }

    public static float normF(FMatrixSparseCSC A) {
        float total = 0.0f;
        float max = CommonOps_FSCC.elementMaxAbs(A);
        for (int i = 0; i < A.nz_length; ++i) {
            float x = A.nz_values[i] / max;
            total += x * x;
        }
        return max * (float)Math.sqrt(total);
    }
}

