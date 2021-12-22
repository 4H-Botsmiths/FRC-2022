/*
 * Decompiled with CFR 0.150.
 */
package org.ejml.dense.fixed;

import org.ejml.data.FMatrix5;
import org.ejml.data.FMatrix5x5;
import org.ejml.dense.fixed.CommonOps_FDF5;

public class NormOps_FDF5 {
    public static void normalizeF(FMatrix5x5 M) {
        float val = NormOps_FDF5.normF(M);
        CommonOps_FDF5.divide(M, val);
    }

    public static void normalizeF(FMatrix5 M) {
        float val = NormOps_FDF5.normF(M);
        CommonOps_FDF5.divide(M, val);
    }

    public static float fastNormF(FMatrix5x5 M) {
        float sum = 0.0f;
        sum += M.a11 * M.a11 + M.a12 * M.a12 + M.a13 * M.a13 + M.a14 * M.a14 + M.a15 * M.a15;
        sum += M.a21 * M.a21 + M.a22 * M.a22 + M.a23 * M.a23 + M.a24 * M.a24 + M.a25 * M.a25;
        sum += M.a31 * M.a31 + M.a32 * M.a32 + M.a33 * M.a33 + M.a34 * M.a34 + M.a35 * M.a35;
        sum += M.a41 * M.a41 + M.a42 * M.a42 + M.a43 * M.a43 + M.a44 * M.a44 + M.a45 * M.a45;
        return (float)Math.sqrt(sum += M.a51 * M.a51 + M.a52 * M.a52 + M.a53 * M.a53 + M.a54 * M.a54 + M.a55 * M.a55);
    }

    public static float fastNormF(FMatrix5 M) {
        float sum = M.a1 * M.a1 + M.a2 * M.a2 + M.a3 * M.a3 + M.a4 * M.a4 + M.a5 * M.a5;
        return (float)Math.sqrt(sum);
    }

    public static float normF(FMatrix5x5 M) {
        float scale = CommonOps_FDF5.elementMaxAbs(M);
        if (scale == 0.0f) {
            return 0.0f;
        }
        float a11 = M.a11 / scale;
        float a12 = M.a12 / scale;
        float a13 = M.a13 / scale;
        float a14 = M.a14 / scale;
        float a15 = M.a15 / scale;
        float a21 = M.a21 / scale;
        float a22 = M.a22 / scale;
        float a23 = M.a23 / scale;
        float a24 = M.a24 / scale;
        float a25 = M.a25 / scale;
        float a31 = M.a31 / scale;
        float a32 = M.a32 / scale;
        float a33 = M.a33 / scale;
        float a34 = M.a34 / scale;
        float a35 = M.a35 / scale;
        float a41 = M.a41 / scale;
        float a42 = M.a42 / scale;
        float a43 = M.a43 / scale;
        float a44 = M.a44 / scale;
        float a45 = M.a45 / scale;
        float a51 = M.a51 / scale;
        float a52 = M.a52 / scale;
        float a53 = M.a53 / scale;
        float a54 = M.a54 / scale;
        float a55 = M.a55 / scale;
        float sum = 0.0f;
        sum += a11 * a11 + a12 * a12 + a13 * a13 + a14 * a14 + a15 * a15;
        sum += a21 * a21 + a22 * a22 + a23 * a23 + a24 * a24 + a25 * a25;
        sum += a31 * a31 + a32 * a32 + a33 * a33 + a34 * a34 + a35 * a35;
        sum += a41 * a41 + a42 * a42 + a43 * a43 + a44 * a44 + a45 * a45;
        return scale * (float)Math.sqrt(sum += a51 * a51 + a52 * a52 + a53 * a53 + a54 * a54 + a55 * a55);
    }

    public static float normF(FMatrix5 M) {
        float scale = CommonOps_FDF5.elementMaxAbs(M);
        if (scale == 0.0f) {
            return 0.0f;
        }
        float a1 = M.a1 / scale;
        float a2 = M.a2 / scale;
        float a3 = M.a3 / scale;
        float a4 = M.a4 / scale;
        float a5 = M.a5 / scale;
        float sum = a1 * a1 + a2 * a2 + a3 * a3 + a4 * a4 + a5 * a5;
        return scale * (float)Math.sqrt(sum);
    }
}

