/*
 * Decompiled with CFR 0.150.
 */
package org.ejml.dense.row.decomposition.chol;

import org.ejml.concurrency.EjmlConcurrency;
import org.ejml.data.DMatrixRMaj;
import org.ejml.dense.row.decomposition.chol.CholeskyDecompositionBlock_DDRM;

public class CholeskyDecompositionBlock_MT_DDRM
extends CholeskyDecompositionBlock_DDRM {
    public CholeskyDecompositionBlock_MT_DDRM(int blockWidth) {
        super(blockWidth);
    }

    @Override
    public void solveL_special(double[] L, DMatrixRMaj b_src, int indexSrc, int indexDst, DMatrixRMaj B) {
        double[] dataSrc = b_src.data;
        double[] b = B.data;
        int m = B.numRows;
        int n = B.numCols;
        int widthL = m;
        EjmlConcurrency.loopFor(0, n, j -> {
            int indexb = j;
            int rowL = 0;
            int i = 0;
            while (i < widthL) {
                double val;
                double sum = dataSrc[indexSrc + i * b_src.numCols + j];
                int indexL = rowL;
                int endL = indexL + i;
                int indexB = j;
                while (indexL != endL) {
                    sum -= L[indexL++] * b[indexB];
                    indexB += n;
                }
                dataSrc[indexDst + j * b_src.numCols + i] = val = sum / L[i * widthL + i];
                b[indexb] = val;
                ++i;
                indexb += n;
                rowL += widthL;
            }
        });
    }

    @Override
    public void symmRankTranA_sub(DMatrixRMaj a, DMatrixRMaj c, int startIndexC) {
        double[] dataA = a.data;
        double[] dataC = c.data;
        int strideC = c.numCols + 1;
        EjmlConcurrency.loopFor(0, a.numCols, i -> {
            int indexA = i;
            int endR = a.numCols;
            int k = 0;
            while (k < a.numRows) {
                int indexC = startIndexC + i * strideC;
                double valA = dataA[indexA];
                int indexR = indexA;
                while (indexR < endR) {
                    int n = indexC++;
                    dataC[n] = dataC[n] - valA * dataA[indexR++];
                }
                ++k;
                indexA += a.numCols;
                endR += a.numCols;
            }
        });
    }
}

