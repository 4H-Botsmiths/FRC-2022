/*
 * Decompiled with CFR 0.150.
 */
package org.ejml.dense.fixed;

import org.ejml.UtilEjml;
import org.ejml.data.DMatrix3;
import org.ejml.data.DMatrix3x3;

public class MatrixFeatures_DDF3 {
    public static boolean isIdentical(DMatrix3x3 a, DMatrix3x3 b, double tol) {
        if (!UtilEjml.isIdentical(a.a11, b.a11, tol)) {
            return false;
        }
        if (!UtilEjml.isIdentical(a.a12, b.a12, tol)) {
            return false;
        }
        if (!UtilEjml.isIdentical(a.a13, b.a13, tol)) {
            return false;
        }
        if (!UtilEjml.isIdentical(a.a21, b.a21, tol)) {
            return false;
        }
        if (!UtilEjml.isIdentical(a.a22, b.a22, tol)) {
            return false;
        }
        if (!UtilEjml.isIdentical(a.a23, b.a23, tol)) {
            return false;
        }
        if (!UtilEjml.isIdentical(a.a31, b.a31, tol)) {
            return false;
        }
        if (!UtilEjml.isIdentical(a.a32, b.a32, tol)) {
            return false;
        }
        return UtilEjml.isIdentical(a.a33, b.a33, tol);
    }

    public static boolean isIdentical(DMatrix3 a, DMatrix3 b, double tol) {
        if (!UtilEjml.isIdentical(a.a1, b.a1, tol)) {
            return false;
        }
        if (!UtilEjml.isIdentical(a.a2, b.a2, tol)) {
            return false;
        }
        return UtilEjml.isIdentical(a.a3, b.a3, tol);
    }

    public static boolean hasUncountable(DMatrix3x3 a) {
        if (UtilEjml.isUncountable(a.a11 + a.a12 + a.a13)) {
            return true;
        }
        if (UtilEjml.isUncountable(a.a21 + a.a22 + a.a23)) {
            return true;
        }
        return UtilEjml.isUncountable(a.a31 + a.a32 + a.a33);
    }

    public static boolean hasUncountable(DMatrix3 a) {
        if (UtilEjml.isUncountable(a.a1)) {
            return true;
        }
        if (UtilEjml.isUncountable(a.a2)) {
            return true;
        }
        return UtilEjml.isUncountable(a.a3);
    }
}

