/*
 * Decompiled with CFR 0.150.
 */
package org.ejml.dense.fixed;

import org.ejml.data.DMatrix3;
import org.ejml.data.DMatrix3x3;
import org.ejml.dense.fixed.CommonOps_DDF3;

public class NormOps_DDF3 {
    public static void normalizeF(DMatrix3x3 M) {
        double val = NormOps_DDF3.normF(M);
        CommonOps_DDF3.divide(M, val);
    }

    public static void normalizeF(DMatrix3 M) {
        double val = NormOps_DDF3.normF(M);
        CommonOps_DDF3.divide(M, val);
    }

    public static double fastNormF(DMatrix3x3 M) {
        double sum = 0.0;
        sum += M.a11 * M.a11 + M.a12 * M.a12 + M.a13 * M.a13;
        sum += M.a21 * M.a21 + M.a22 * M.a22 + M.a23 * M.a23;
        return Math.sqrt(sum += M.a31 * M.a31 + M.a32 * M.a32 + M.a33 * M.a33);
    }

    public static double fastNormF(DMatrix3 M) {
        double sum = M.a1 * M.a1 + M.a2 * M.a2 + M.a3 * M.a3;
        return Math.sqrt(sum);
    }

    public static double normF(DMatrix3x3 M) {
        double scale = CommonOps_DDF3.elementMaxAbs(M);
        if (scale == 0.0) {
            return 0.0;
        }
        double a11 = M.a11 / scale;
        double a12 = M.a12 / scale;
        double a13 = M.a13 / scale;
        double a21 = M.a21 / scale;
        double a22 = M.a22 / scale;
        double a23 = M.a23 / scale;
        double a31 = M.a31 / scale;
        double a32 = M.a32 / scale;
        double a33 = M.a33 / scale;
        double sum = 0.0;
        sum += a11 * a11 + a12 * a12 + a13 * a13;
        sum += a21 * a21 + a22 * a22 + a23 * a23;
        return scale * Math.sqrt(sum += a31 * a31 + a32 * a32 + a33 * a33);
    }

    public static double normF(DMatrix3 M) {
        double scale = CommonOps_DDF3.elementMaxAbs(M);
        if (scale == 0.0) {
            return 0.0;
        }
        double a1 = M.a1 / scale;
        double a2 = M.a2 / scale;
        double a3 = M.a3 / scale;
        double sum = a1 * a1 + a2 * a2 + a3 * a3;
        return scale * Math.sqrt(sum);
    }
}

