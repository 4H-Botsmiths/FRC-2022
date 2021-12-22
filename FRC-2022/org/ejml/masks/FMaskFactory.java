/*
 * Decompiled with CFR 0.150.
 */
package org.ejml.masks;

import org.ejml.data.FMatrixD1;
import org.ejml.data.FMatrixSparseCSC;
import org.ejml.masks.FMaskPrimitive;
import org.ejml.masks.FMaskSparse;
import org.ejml.masks.FMaskSparseStructural;
import org.ejml.masks.MaskBuilder;

public class FMaskFactory {
    public static FMaskPrimitive.Builder builder(float[] values) {
        return new FMaskPrimitive.Builder(values);
    }

    public static FMaskPrimitive.Builder builder(FMatrixD1 matrix) {
        return new FMaskPrimitive.Builder(matrix.data).withNumCols(matrix.numCols);
    }

    public static MaskBuilder builder(FMatrixSparseCSC matrix, boolean structural) {
        if (structural) {
            return new FMaskSparseStructural.Builder(matrix);
        }
        return new FMaskSparse.Builder(matrix);
    }
}

