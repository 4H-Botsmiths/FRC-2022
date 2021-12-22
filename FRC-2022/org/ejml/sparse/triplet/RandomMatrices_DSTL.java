/*
 * Decompiled with CFR 0.150.
 */
package org.ejml.sparse.triplet;

import java.util.Random;
import org.ejml.data.DMatrixSparseTriplet;

public class RandomMatrices_DSTL {
    public static DMatrixSparseTriplet uniform(int numRows, int numCols, int nz_total, double min, double max, Random rand) {
        int i;
        int N = numCols * numRows;
        if (N < 0) {
            throw new IllegalArgumentException("matrix size is too large");
        }
        nz_total = Math.min(N, nz_total);
        int[] selected = new int[N];
        for (i = 0; i < N; ++i) {
            selected[i] = i;
        }
        for (i = 0; i < nz_total; ++i) {
            int s = rand.nextInt(N);
            int tmp = selected[s];
            selected[s] = selected[i];
            selected[i] = tmp;
        }
        DMatrixSparseTriplet ret = new DMatrixSparseTriplet(numRows, numCols, nz_total);
        for (int i2 = 0; i2 < nz_total; ++i2) {
            int row = selected[i2] / numCols;
            int col = selected[i2] % numCols;
            double value = rand.nextDouble() * (max - min) + min;
            ret.addItem(row, col, value);
        }
        return ret;
    }
}

