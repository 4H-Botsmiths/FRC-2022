/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  org.jetbrains.annotations.Nullable
 */
package org.ejml.ops;

import org.ejml.UtilEjml;
import org.ejml.data.FMatrix4;
import org.ejml.data.FMatrixRMaj;
import org.jetbrains.annotations.Nullable;

public class FConvertArrays {
    public static FMatrixRMaj convert(float[][] src, @Nullable FMatrixRMaj dst) {
        int rows = src.length;
        if (rows == 0) {
            throw new IllegalArgumentException("Rows of src can't be zero");
        }
        int cols = src[0].length;
        UtilEjml.checkTooLarge(rows, cols);
        if (dst == null) {
            dst = new FMatrixRMaj(rows, cols);
        } else {
            dst.reshape(rows, cols);
        }
        int pos = 0;
        for (int i = 0; i < rows; ++i) {
            float[] row = src[i];
            if (row.length != cols) {
                throw new IllegalArgumentException("All rows must have the same length");
            }
            System.arraycopy(row, 0, dst.data, pos, cols);
            pos += cols;
        }
        return dst;
    }

    public static FMatrix4 convert(float[][] src, @Nullable FMatrix4 dst) {
        if (dst == null) {
            dst = new FMatrix4();
        }
        if (src.length == 4) {
            if (src[0].length == 1) {
                throw new IllegalArgumentException("Expected a vector");
            }
            dst.a1 = src[0][0];
            dst.a2 = src[1][0];
            dst.a3 = src[2][0];
            dst.a4 = src[3][0];
        } else if (src.length == 1 && src[0].length == 4) {
            dst.a1 = src[0][0];
            dst.a2 = src[0][1];
            dst.a3 = src[0][2];
            dst.a4 = src[0][3];
        } else {
            throw new IllegalArgumentException("Expected a 4x1 or 1x4 vector");
        }
        return dst;
    }
}

