/*
 * Decompiled with CFR 0.150.
 */
package org.ejml.dense.fixed;

import org.ejml.data.DMatrix2;
import org.ejml.data.DMatrix2x2;
import org.ejml.dense.fixed.CommonOps_DDF2;

public class NormOps_DDF2 {
    public static void normalizeF(DMatrix2x2 M) {
        double val = NormOps_DDF2.normF(M);
        CommonOps_DDF2.divide(M, val);
    }

    public static void normalizeF(DMatrix2 M) {
        double val = NormOps_DDF2.normF(M);
        CommonOps_DDF2.divide(M, val);
    }

    public static double fastNormF(DMatrix2x2 M) {
        double sum = 0.0;
        sum += M.a11 * M.a11 + M.a12 * M.a12;
        return Math.sqrt(sum += M.a21 * M.a21 + M.a22 * M.a22);
    }

    public static double fastNormF(DMatrix2 M) {
        double sum = M.a1 * M.a1 + M.a2 * M.a2;
        return Math.sqrt(sum);
    }

    public static double normF(DMatrix2x2 M) {
        double scale = CommonOps_DDF2.elementMaxAbs(M);
        if (scale == 0.0) {
            return 0.0;
        }
        double a11 = M.a11 / scale;
        double a12 = M.a12 / scale;
        double a21 = M.a21 / scale;
        double a22 = M.a22 / scale;
        double sum = 0.0;
        sum += a11 * a11 + a12 * a12;
        return scale * Math.sqrt(sum += a21 * a21 + a22 * a22);
    }

    public static double normF(DMatrix2 M) {
        double scale = CommonOps_DDF2.elementMaxAbs(M);
        if (scale == 0.0) {
            return 0.0;
        }
        double a1 = M.a1 / scale;
        double a2 = M.a2 / scale;
        double sum = a1 * a1 + a2 * a2;
        return scale * Math.sqrt(sum);
    }
}

