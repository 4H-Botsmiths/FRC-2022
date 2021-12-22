/*
 * Decompiled with CFR 0.150.
 */
package org.ejml.dense.row;

import org.ejml.data.CMatrixRMaj;
import org.ejml.dense.row.CommonOps_CDRM;

public class NormOps_CDRM {
    public static float normF(CMatrixRMaj a) {
        float total = 0.0f;
        float scale = CommonOps_CDRM.elementMaxAbs(a);
        if (scale == 0.0f) {
            return 0.0f;
        }
        int size = a.getDataLength();
        for (int i = 0; i < size; i += 2) {
            float real = a.data[i] / scale;
            float imag = a.data[i + 1] / scale;
            total += real * real + imag * imag;
        }
        return scale * (float)Math.sqrt(total);
    }
}

