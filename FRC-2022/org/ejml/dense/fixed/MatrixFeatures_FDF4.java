/*
 * Decompiled with CFR 0.150.
 */
package org.ejml.dense.fixed;

import org.ejml.UtilEjml;
import org.ejml.data.FMatrix4;
import org.ejml.data.FMatrix4x4;

public class MatrixFeatures_FDF4 {
    public static boolean isIdentical(FMatrix4x4 a, FMatrix4x4 b, float tol) {
        if (!UtilEjml.isIdentical(a.a11, b.a11, tol)) {
            return false;
        }
        if (!UtilEjml.isIdentical(a.a12, b.a12, tol)) {
            return false;
        }
        if (!UtilEjml.isIdentical(a.a13, b.a13, tol)) {
            return false;
        }
        if (!UtilEjml.isIdentical(a.a14, b.a14, tol)) {
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
        if (!UtilEjml.isIdentical(a.a24, b.a24, tol)) {
            return false;
        }
        if (!UtilEjml.isIdentical(a.a31, b.a31, tol)) {
            return false;
        }
        if (!UtilEjml.isIdentical(a.a32, b.a32, tol)) {
            return false;
        }
        if (!UtilEjml.isIdentical(a.a33, b.a33, tol)) {
            return false;
        }
        if (!UtilEjml.isIdentical(a.a34, b.a34, tol)) {
            return false;
        }
        if (!UtilEjml.isIdentical(a.a41, b.a41, tol)) {
            return false;
        }
        if (!UtilEjml.isIdentical(a.a42, b.a42, tol)) {
            return false;
        }
        if (!UtilEjml.isIdentical(a.a43, b.a43, tol)) {
            return false;
        }
        return UtilEjml.isIdentical(a.a44, b.a44, tol);
    }

    public static boolean isIdentical(FMatrix4 a, FMatrix4 b, float tol) {
        if (!UtilEjml.isIdentical(a.a1, b.a1, tol)) {
            return false;
        }
        if (!UtilEjml.isIdentical(a.a2, b.a2, tol)) {
            return false;
        }
        if (!UtilEjml.isIdentical(a.a3, b.a3, tol)) {
            return false;
        }
        return UtilEjml.isIdentical(a.a4, b.a4, tol);
    }

    public static boolean hasUncountable(FMatrix4x4 a) {
        if (UtilEjml.isUncountable(a.a11 + a.a12 + a.a13 + a.a14)) {
            return true;
        }
        if (UtilEjml.isUncountable(a.a21 + a.a22 + a.a23 + a.a24)) {
            return true;
        }
        if (UtilEjml.isUncountable(a.a31 + a.a32 + a.a33 + a.a34)) {
            return true;
        }
        return UtilEjml.isUncountable(a.a41 + a.a42 + a.a43 + a.a44);
    }

    public static boolean hasUncountable(FMatrix4 a) {
        if (UtilEjml.isUncountable(a.a1)) {
            return true;
        }
        if (UtilEjml.isUncountable(a.a2)) {
            return true;
        }
        if (UtilEjml.isUncountable(a.a3)) {
            return true;
        }
        return UtilEjml.isUncountable(a.a4);
    }
}

