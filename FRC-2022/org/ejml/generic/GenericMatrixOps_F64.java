/*
 * Decompiled with CFR 0.150.
 */
package org.ejml.generic;

import java.util.Random;
import org.ejml.data.DMatrix;

public class GenericMatrixOps_F64 {
    public static boolean isEquivalent(DMatrix a, DMatrix b, double tol) {
        if (a.getNumRows() != b.getNumRows() || a.getNumCols() != b.getNumCols()) {
            return false;
        }
        for (int i = 0; i < a.getNumRows(); ++i) {
            for (int j = 0; j < a.getNumCols(); ++j) {
                double diff = Math.abs(a.get(i, j) - b.get(i, j));
                if (!(diff > tol)) continue;
                return false;
            }
        }
        return true;
    }

    public static boolean isIdentity(DMatrix a, double tol) {
        for (int i = 0; i < a.getNumRows(); ++i) {
            for (int j = 0; j < a.getNumCols(); ++j) {
                if (!(i == j ? Math.abs(a.get(i, j) - 1.0) > tol : Math.abs(a.get(i, j)) > tol)) continue;
                return false;
            }
        }
        return true;
    }

    public static boolean isEquivalentTriangle(boolean upper, DMatrix a, DMatrix b, double tol) {
        if (a.getNumRows() != b.getNumRows() || a.getNumCols() != b.getNumCols()) {
            return false;
        }
        if (upper) {
            for (int i = 0; i < a.getNumRows(); ++i) {
                for (int j = i; j < a.getNumCols(); ++j) {
                    double diff = Math.abs(a.get(i, j) - b.get(i, j));
                    if (!(diff > tol)) continue;
                    return false;
                }
            }
        } else {
            for (int j = 0; j < a.getNumCols(); ++j) {
                for (int i = j; i < a.getNumRows(); ++i) {
                    double diff = Math.abs(a.get(i, j) - b.get(i, j));
                    if (!(diff > tol)) continue;
                    return false;
                }
            }
        }
        return true;
    }

    public static void copy(DMatrix from, DMatrix to) {
        int numCols = from.getNumCols();
        int numRows = from.getNumRows();
        for (int i = 0; i < numRows; ++i) {
            for (int j = 0; j < numCols; ++j) {
                to.set(i, j, from.get(i, j));
            }
        }
    }

    public static void setRandom(DMatrix a, double min, double max, Random rand) {
        for (int i = 0; i < a.getNumRows(); ++i) {
            for (int j = 0; j < a.getNumCols(); ++j) {
                double val = rand.nextDouble() * (max - min) + min;
                a.set(i, j, val);
            }
        }
    }
}

