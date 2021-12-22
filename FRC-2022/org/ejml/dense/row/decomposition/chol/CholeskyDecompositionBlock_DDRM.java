/*
 * Decompiled with CFR 0.150.
 */
package org.ejml.dense.row.decomposition.chol;

import org.ejml.data.DMatrixRMaj;
import org.ejml.dense.row.decomposition.chol.CholeskyBlockHelper_DDRM;
import org.ejml.dense.row.decomposition.chol.CholeskyDecompositionCommon_DDRM;

public class CholeskyDecompositionBlock_DDRM
extends CholeskyDecompositionCommon_DDRM {
    private final int blockWidth;
    private DMatrixRMaj B;
    private final CholeskyBlockHelper_DDRM chol;

    public CholeskyDecompositionBlock_DDRM(int blockWidth) {
        super(true);
        this.blockWidth = blockWidth;
        this.chol = new CholeskyBlockHelper_DDRM(blockWidth);
    }

    @Override
    public void setExpectedMaxSize(int numRows, int numCols) {
        super.setExpectedMaxSize(numRows, numCols);
        this.B = numRows < this.blockWidth ? new DMatrixRMaj(0, 0) : new DMatrixRMaj(this.blockWidth, this.maxWidth);
    }

    @Override
    protected boolean decomposeLower() {
        int i;
        if (this.n < this.blockWidth) {
            this.B.reshape(0, 0, false);
        } else {
            this.B.reshape(this.blockWidth, this.n - this.blockWidth, false);
        }
        int numBlocks = this.n / this.blockWidth;
        int remainder = this.n % this.blockWidth;
        if (remainder > 0) {
            ++numBlocks;
        }
        this.B.numCols = this.n;
        for (i = 0; i < numBlocks; ++i) {
            int width;
            this.B.numCols -= this.blockWidth;
            if (this.B.numCols > 0) {
                if (!this.chol.decompose(this.T, i * this.blockWidth * this.T.numCols + i * this.blockWidth, this.blockWidth)) {
                    return false;
                }
                int indexSrc = i * this.blockWidth * this.T.numCols + (i + 1) * this.blockWidth;
                int indexDst = (i + 1) * this.blockWidth * this.T.numCols + i * this.blockWidth;
                this.solveL_special(this.chol.getL().data, this.T, indexSrc, indexDst, this.B);
                int indexL = (i + 1) * this.blockWidth * this.n + (i + 1) * this.blockWidth;
                this.symmRankTranA_sub(this.B, this.T, indexL);
                continue;
            }
            int n = width = remainder > 0 ? remainder : this.blockWidth;
            if (this.chol.decompose(this.T, i * this.blockWidth * this.T.numCols + i * this.blockWidth, width)) continue;
            return false;
        }
        for (i = 0; i < this.n; ++i) {
            for (int j = i + 1; j < this.n; ++j) {
                this.t[i * this.n + j] = 0.0;
            }
        }
        return true;
    }

    @Override
    protected boolean decomposeUpper() {
        throw new RuntimeException("Not implemented. Do a lower decomposition and transpose it...");
    }

    public void solveL_special(double[] L, DMatrixRMaj b_src, int indexSrc, int indexDst, DMatrixRMaj B) {
        double[] dataSrc = b_src.data;
        double[] b = B.data;
        int m = B.numRows;
        int n = B.numCols;
        int widthL = m;
        for (int j = 0; j < n; ++j) {
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
        }
    }

    public void symmRankTranA_sub(DMatrixRMaj a, DMatrixRMaj c, int startIndexC) {
        double[] dataA = a.data;
        double[] dataC = c.data;
        int strideC = c.numCols + 1;
        for (int i = 0; i < a.numCols; ++i) {
            int indexA = i;
            int endR = a.numCols;
            int k = 0;
            while (k < a.numRows) {
                int indexC = startIndexC;
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
            startIndexC += strideC;
        }
    }
}

