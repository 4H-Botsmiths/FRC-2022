/*
 * Decompiled with CFR 0.150.
 */
package org.ejml.ops;

import org.ejml.UtilEjml;
import org.ejml.data.DMatrix;

public class MatrixFeatures_D {
    public static boolean isEquals(DMatrix a, DMatrix b) {
        if (a.getNumRows() != b.getNumRows() || a.getNumCols() != b.getNumCols()) {
            return false;
        }
        int numRows = a.getNumRows();
        int numCols = a.getNumCols();
        for (int row = 0; row < numRows; ++row) {
            for (int col = 0; col < numCols; ++col) {
                if (a.unsafe_get(row, col) == b.unsafe_get(row, col)) continue;
                return false;
            }
        }
        return true;
    }

    public static boolean isIdentical(DMatrix a, DMatrix b, double tol) {
        if (a.getNumRows() != b.getNumRows() || a.getNumCols() != b.getNumCols()) {
            return false;
        }
        if (tol < 0.0) {
            throw new IllegalArgumentException("Tolerance must be greater than or equal to zero.");
        }
        int numRows = a.getNumRows();
        int numCols = a.getNumCols();
        for (int row = 0; row < numRows; ++row) {
            for (int col = 0; col < numCols; ++col) {
                if (UtilEjml.isIdentical(a.unsafe_get(row, col), b.unsafe_get(row, col), tol)) continue;
                return false;
            }
        }
        return true;
    }
}

