/*
 * Decompiled with CFR 0.150.
 */
package org.ejml.dense.row.misc;

import org.ejml.data.DMatrix;

public class ImplCommonOps_DDMA {
    public static void extract(DMatrix src, int srcY0, int srcX0, DMatrix dst, int dstY0, int dstX0, int numRows, int numCols) {
        for (int y = 0; y < numRows; ++y) {
            for (int x = 0; x < numCols; ++x) {
                double v = src.get(y + srcY0, x + srcX0);
                dst.set(dstY0 + y, dstX0 + x, v);
            }
        }
    }
}

