/*
 * Decompiled with CFR 0.150.
 */
package org.ejml.dense.fixed;

import org.ejml.data.FMatrix2;
import org.ejml.data.FMatrix2x2;
import org.ejml.dense.fixed.CommonOps_FDF2;

public class NormOps_FDF2 {
    public static void normalizeF(FMatrix2x2 M) {
        float val = NormOps_FDF2.normF(M);
        CommonOps_FDF2.divide(M, val);
    }

    public static void normalizeF(FMatrix2 M) {
        float val = NormOps_FDF2.normF(M);
        CommonOps_FDF2.divide(M, val);
    }

    public static float fastNormF(FMatrix2x2 M) {
        float sum = 0.0f;
        sum += M.a11 * M.a11 + M.a12 * M.a12;
        return (float)Math.sqrt(sum += M.a21 * M.a21 + M.a22 * M.a22);
    }

    public static float fastNormF(FMatrix2 M) {
        float sum = M.a1 * M.a1 + M.a2 * M.a2;
        return (float)Math.sqrt(sum);
    }

    public static float normF(FMatrix2x2 M) {
        float scale = CommonOps_FDF2.elementMaxAbs(M);
        if (scale == 0.0f) {
            return 0.0f;
        }
        float a11 = M.a11 / scale;
        float a12 = M.a12 / scale;
        float a21 = M.a21 / scale;
        float a22 = M.a22 / scale;
        float sum = 0.0f;
        sum += a11 * a11 + a12 * a12;
        return scale * (float)Math.sqrt(sum += a21 * a21 + a22 * a22);
    }

    public static float normF(FMatrix2 M) {
        float scale = CommonOps_FDF2.elementMaxAbs(M);
        if (scale == 0.0f) {
            return 0.0f;
        }
        float a1 = M.a1 / scale;
        float a2 = M.a2 / scale;
        float sum = a1 * a1 + a2 * a2;
        return scale * (float)Math.sqrt(sum);
    }
}

