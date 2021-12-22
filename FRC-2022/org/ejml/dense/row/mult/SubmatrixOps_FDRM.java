/*
 * Decompiled with CFR 0.150.
 */
package org.ejml.dense.row.mult;

import org.ejml.data.FMatrix1Row;

public class SubmatrixOps_FDRM {
    public static void setSubMatrix(FMatrix1Row src, FMatrix1Row dst, int srcRow, int srcCol, int dstRow, int dstCol, int numSubRows, int numSubCols) {
        for (int i = 0; i < numSubRows; ++i) {
            for (int j = 0; j < numSubCols; ++j) {
                float val = src.get(i + srcRow, j + srcCol);
                dst.set(i + dstRow, j + dstCol, val);
            }
        }
    }
}

