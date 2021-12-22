/*
 * Decompiled with CFR 0.150.
 */
package org.ejml.dense.row;

import org.ejml.data.ZMatrixRMaj;
import org.ejml.dense.row.CommonOps_ZDRM;

public class NormOps_ZDRM {
    public static double normF(ZMatrixRMaj a) {
        double total = 0.0;
        double scale = CommonOps_ZDRM.elementMaxAbs(a);
        if (scale == 0.0) {
            return 0.0;
        }
        int size = a.getDataLength();
        for (int i = 0; i < size; i += 2) {
            double real = a.data[i] / scale;
            double imag = a.data[i + 1] / scale;
            total += real * real + imag * imag;
        }
        return scale * Math.sqrt(total);
    }
}

