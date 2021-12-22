/*
 * Decompiled with CFR 0.150.
 */
package org.ejml.masks;

import org.ejml.data.DMatrixD1;
import org.ejml.data.DMatrixSparseCSC;
import org.ejml.masks.DMaskPrimitive;
import org.ejml.masks.DMaskSparse;
import org.ejml.masks.DMaskSparseStructural;
import org.ejml.masks.MaskBuilder;

public class DMaskFactory {
    public static DMaskPrimitive.Builder builder(double[] values) {
        return new DMaskPrimitive.Builder(values);
    }

    public static DMaskPrimitive.Builder builder(DMatrixD1 matrix) {
        return new DMaskPrimitive.Builder(matrix.data).withNumCols(matrix.numCols);
    }

    public static MaskBuilder builder(DMatrixSparseCSC matrix, boolean structural) {
        if (structural) {
            return new DMaskSparseStructural.Builder(matrix);
        }
        return new DMaskSparse.Builder(matrix);
    }
}

