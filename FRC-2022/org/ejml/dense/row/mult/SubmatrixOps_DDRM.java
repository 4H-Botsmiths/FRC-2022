/*
 * Decompiled with CFR 0.150.
 */
package org.ejml.dense.row.mult;

import org.ejml.data.DMatrix1Row;

public class SubmatrixOps_DDRM {
    public static void setSubMatrix(DMatrix1Row src, DMatrix1Row dst, int srcRow, int srcCol, int dstRow, int dstCol, int numSubRows, int numSubCols) {
        for (int i = 0; i < numSubRows; ++i) {
            for (int j = 0; j < numSubCols; ++j) {
                double val = src.get(i + srcRow, j + srcCol);
                dst.set(i + dstRow, j + dstCol, val);
            }
        }
    }
}

