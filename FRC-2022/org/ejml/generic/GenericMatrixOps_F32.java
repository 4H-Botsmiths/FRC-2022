/*
 * Decompiled with CFR 0.150.
 */
package org.ejml.generic;

import java.util.Random;
import org.ejml.data.FMatrix;

public class GenericMatrixOps_F32 {
    public static boolean isEquivalent(FMatrix a, FMatrix b, float tol) {
        if (a.getNumRows() != b.getNumRows() || a.getNumCols() != b.getNumCols()) {
            return false;
        }
        for (int i = 0; i < a.getNumRows(); ++i) {
            for (int j = 0; j < a.getNumCols(); ++j) {
                float diff = Math.abs(a.get(i, j) - b.get(i, j));
                if (!(diff > tol)) continue;
                return false;
            }
        }
        return true;
    }

    public static boolean isIdentity(FMatrix a, float tol) {
        for (int i = 0; i < a.getNumRows(); ++i) {
            for (int j = 0; j < a.getNumCols(); ++j) {
                if (!(i == j ? Math.abs(a.get(i, j) - 1.0f) > tol : Math.abs(a.get(i, j)) > tol)) continue;
                return false;
            }
        }
        return true;
    }

    public static boolean isEquivalentTriangle(boolean upper, FMatrix a, FMatrix b, float tol) {
        if (a.getNumRows() != b.getNumRows() || a.getNumCols() != b.getNumCols()) {
            return false;
        }
        if (upper) {
            for (int i = 0; i < a.getNumRows(); ++i) {
                for (int j = i; j < a.getNumCols(); ++j) {
                    float diff = Math.abs(a.get(i, j) - b.get(i, j));
                    if (!(diff > tol)) continue;
                    return false;
                }
            }
        } else {
            for (int j = 0; j < a.getNumCols(); ++j) {
                for (int i = j; i < a.getNumRows(); ++i) {
                    float diff = Math.abs(a.get(i, j) - b.get(i, j));
                    if (!(diff > tol)) continue;
                    return false;
                }
            }
        }
        return true;
    }

    public static void copy(FMatrix from, FMatrix to) {
        int numCols = from.getNumCols();
        int numRows = from.getNumRows();
        for (int i = 0; i < numRows; ++i) {
            for (int j = 0; j < numCols; ++j) {
                to.set(i, j, from.get(i, j));
            }
        }
    }

    public static void setRandom(FMatrix a, float min, float max, Random rand) {
        for (int i = 0; i < a.getNumRows(); ++i) {
            for (int j = 0; j < a.getNumCols(); ++j) {
                float val = rand.nextFloat() * (max - min) + min;
                a.set(i, j, val);
            }
        }
    }
}

