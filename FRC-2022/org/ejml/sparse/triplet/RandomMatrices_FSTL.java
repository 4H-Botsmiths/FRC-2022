/*
 * Decompiled with CFR 0.150.
 */
package org.ejml.sparse.triplet;

import java.util.Random;
import org.ejml.data.FMatrixSparseTriplet;

public class RandomMatrices_FSTL {
    public static FMatrixSparseTriplet uniform(int numRows, int numCols, int nz_total, float min, float max, Random rand) {
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
        FMatrixSparseTriplet ret = new FMatrixSparseTriplet(numRows, numCols, nz_total);
        for (int i2 = 0; i2 < nz_total; ++i2) {
            int row = selected[i2] / numCols;
            int col = selected[i2] % numCols;
            float value = rand.nextFloat() * (max - min) + min;
            ret.addItem(row, col, value);
        }
        return ret;
    }
}

