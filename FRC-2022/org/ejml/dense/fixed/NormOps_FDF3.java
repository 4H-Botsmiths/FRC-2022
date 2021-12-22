/*
 * Decompiled with CFR 0.150.
 */
package org.ejml.dense.fixed;

import org.ejml.data.FMatrix3;
import org.ejml.data.FMatrix3x3;
import org.ejml.dense.fixed.CommonOps_FDF3;

public class NormOps_FDF3 {
    public static void normalizeF(FMatrix3x3 M) {
        float val = NormOps_FDF3.normF(M);
        CommonOps_FDF3.divide(M, val);
    }

    public static void normalizeF(FMatrix3 M) {
        float val = NormOps_FDF3.normF(M);
        CommonOps_FDF3.divide(M, val);
    }

    public static float fastNormF(FMatrix3x3 M) {
        float sum = 0.0f;
        sum += M.a11 * M.a11 + M.a12 * M.a12 + M.a13 * M.a13;
        sum += M.a21 * M.a21 + M.a22 * M.a22 + M.a23 * M.a23;
        return (float)Math.sqrt(sum += M.a31 * M.a31 + M.a32 * M.a32 + M.a33 * M.a33);
    }

    public static float fastNormF(FMatrix3 M) {
        float sum = M.a1 * M.a1 + M.a2 * M.a2 + M.a3 * M.a3;
        return (float)Math.sqrt(sum);
    }

    public static float normF(FMatrix3x3 M) {
        float scale = CommonOps_FDF3.elementMaxAbs(M);
        if (scale == 0.0f) {
            return 0.0f;
        }
        float a11 = M.a11 / scale;
        float a12 = M.a12 / scale;
        float a13 = M.a13 / scale;
        float a21 = M.a21 / scale;
        float a22 = M.a22 / scale;
        float a23 = M.a23 / scale;
        float a31 = M.a31 / scale;
        float a32 = M.a32 / scale;
        float a33 = M.a33 / scale;
        float sum = 0.0f;
        sum += a11 * a11 + a12 * a12 + a13 * a13;
        sum += a21 * a21 + a22 * a22 + a23 * a23;
        return scale * (float)Math.sqrt(sum += a31 * a31 + a32 * a32 + a33 * a33);
    }

    public static float normF(FMatrix3 M) {
        float scale = CommonOps_FDF3.elementMaxAbs(M);
        if (scale == 0.0f) {
            return 0.0f;
        }
        float a1 = M.a1 / scale;
        float a2 = M.a2 / scale;
        float a3 = M.a3 / scale;
        float sum = a1 * a1 + a2 * a2 + a3 * a3;
        return scale * (float)Math.sqrt(sum);
    }
}

