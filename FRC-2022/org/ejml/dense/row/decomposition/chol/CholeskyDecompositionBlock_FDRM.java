/*
 * Decompiled with CFR 0.150.
 */
package org.ejml.dense.row.decomposition.chol;

import org.ejml.data.FMatrixRMaj;
import org.ejml.dense.row.decomposition.chol.CholeskyBlockHelper_FDRM;
import org.ejml.dense.row.decomposition.chol.CholeskyDecompositionCommon_FDRM;

public class CholeskyDecompositionBlock_FDRM
extends CholeskyDecompositionCommon_FDRM {
    private final int blockWidth;
    private FMatrixRMaj B;
    private final CholeskyBlockHelper_FDRM chol;

    public CholeskyDecompositionBlock_FDRM(int blockWidth) {
        super(true);
        this.blockWidth = blockWidth;
        this.chol = new CholeskyBlockHelper_FDRM(blockWidth);
    }

    @Override
    public void setExpectedMaxSize(int numRows, int numCols) {
        super.setExpectedMaxSize(numRows, numCols);
        this.B = numRows < this.blockWidth ? new FMatrixRMaj(0, 0) : new FMatrixRMaj(this.blockWidth, this.maxWidth);
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
                this.t[i * this.n + j] = 0.0f;
            }
        }
        return true;
    }

    @Override
    protected boolean decomposeUpper() {
        throw new RuntimeException("Not implemented. Do a lower decomposition and transpose it...");
    }

    public void solveL_special(float[] L, FMatrixRMaj b_src, int indexSrc, int indexDst, FMatrixRMaj B) {
        float[] dataSrc = b_src.data;
        float[] b = B.data;
        int m = B.numRows;
        int n = B.numCols;
        int widthL = m;
        for (int j = 0; j < n; ++j) {
            int indexb = j;
            int rowL = 0;
            int i = 0;
            while (i < widthL) {
                float val;
                float sum = dataSrc[indexSrc + i * b_src.numCols + j];
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

    public void symmRankTranA_sub(FMatrixRMaj a, FMatrixRMaj c, int startIndexC) {
        float[] dataA = a.data;
        float[] dataC = c.data;
        int strideC = c.numCols + 1;
        for (int i = 0; i < a.numCols; ++i) {
            int indexA = i;
            int endR = a.numCols;
            int k = 0;
            while (k < a.numRows) {
                int indexC = startIndexC;
                float valA = dataA[indexA];
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

