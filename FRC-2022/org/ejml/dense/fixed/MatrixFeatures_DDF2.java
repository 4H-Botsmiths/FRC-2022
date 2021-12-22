/*
 * Decompiled with CFR 0.150.
 */
package org.ejml.dense.fixed;

import org.ejml.UtilEjml;
import org.ejml.data.DMatrix2;
import org.ejml.data.DMatrix2x2;

public class MatrixFeatures_DDF2 {
    public static boolean isIdentical(DMatrix2x2 a, DMatrix2x2 b, double tol) {
        if (!UtilEjml.isIdentical(a.a11, b.a11, tol)) {
            return false;
        }
        if (!UtilEjml.isIdentical(a.a12, b.a12, tol)) {
            return false;
        }
        if (!UtilEjml.isIdentical(a.a21, b.a21, tol)) {
            return false;
        }
        return UtilEjml.isIdentical(a.a22, b.a22, tol);
    }

    public static boolean isIdentical(DMatrix2 a, DMatrix2 b, double tol) {
        if (!UtilEjml.isIdentical(a.a1, b.a1, tol)) {
            return false;
        }
        return UtilEjml.isIdentical(a.a2, b.a2, tol);
    }

    public static boolean hasUncountable(DMatrix2x2 a) {
        if (UtilEjml.isUncountable(a.a11 + a.a12)) {
            return true;
        }
        return UtilEjml.isUncountable(a.a21 + a.a22);
    }

    public static boolean hasUncountable(DMatrix2 a) {
        if (UtilEjml.isUncountable(a.a1)) {
            return true;
        }
        return UtilEjml.isUncountable(a.a2);
    }
}

