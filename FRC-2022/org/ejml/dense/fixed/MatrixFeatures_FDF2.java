/*
 * Decompiled with CFR 0.150.
 */
package org.ejml.dense.fixed;

import org.ejml.UtilEjml;
import org.ejml.data.FMatrix2;
import org.ejml.data.FMatrix2x2;

public class MatrixFeatures_FDF2 {
    public static boolean isIdentical(FMatrix2x2 a, FMatrix2x2 b, float tol) {
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

    public static boolean isIdentical(FMatrix2 a, FMatrix2 b, float tol) {
        if (!UtilEjml.isIdentical(a.a1, b.a1, tol)) {
            return false;
        }
        return UtilEjml.isIdentical(a.a2, b.a2, tol);
    }

    public static boolean hasUncountable(FMatrix2x2 a) {
        if (UtilEjml.isUncountable(a.a11 + a.a12)) {
            return true;
        }
        return UtilEjml.isUncountable(a.a21 + a.a22);
    }

    public static boolean hasUncountable(FMatrix2 a) {
        if (UtilEjml.isUncountable(a.a1)) {
            return true;
        }
        return UtilEjml.isUncountable(a.a2);
    }
}

