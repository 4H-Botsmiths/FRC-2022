/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  org.jetbrains.annotations.Nullable
 */
package org.ejml.dense.block.decomposition.hessenberg;

import org.ejml.data.DMatrixD1;
import org.ejml.data.DMatrixRBlock;
import org.ejml.data.DMatrixRMaj;
import org.ejml.data.DSubmatrixD1;
import org.ejml.dense.block.InnerMultiplication_DDRB;
import org.ejml.dense.block.MatrixMult_DDRB;
import org.ejml.dense.block.decomposition.hessenberg.TridiagonalHelper_DDRB;
import org.ejml.dense.block.decomposition.qr.QRDecompositionHouseholder_DDRB;
import org.ejml.dense.row.CommonOps_DDRM;
import org.ejml.interfaces.decomposition.TridiagonalSimilarDecomposition_F64;
import org.jetbrains.annotations.Nullable;

public class TridiagonalDecompositionHouseholder_DDRB
implements TridiagonalSimilarDecomposition_F64<DMatrixRBlock> {
    protected DMatrixRBlock A;
    protected DMatrixRBlock V = new DMatrixRBlock(1, 1);
    protected DMatrixRBlock tmp = new DMatrixRBlock(1, 1);
    protected double[] gammas = new double[1];
    protected DMatrixRMaj zerosM = new DMatrixRMaj(1, 1);

    @Override
    public DMatrixRBlock getT(@Nullable DMatrixRBlock T) {
        if (T == null) {
            T = new DMatrixRBlock(this.A.numRows, this.A.numCols, this.A.blockLength);
        } else {
            if (T.numRows != this.A.numRows || T.numCols != this.A.numCols) {
                throw new IllegalArgumentException("T must have the same dimensions as the input matrix");
            }
            CommonOps_DDRM.fill(T, 0.0);
        }
        T.set(0, 0, this.A.data[0]);
        for (int i = 1; i < this.A.numRows; ++i) {
            double d = this.A.get(i - 1, i);
            T.set(i, i, this.A.get(i, i));
            T.set(i - 1, i, d);
            T.set(i, i - 1, d);
        }
        return T;
    }

    @Override
    public DMatrixRBlock getQ(@Nullable DMatrixRBlock Q, boolean transposed) {
        Q = QRDecompositionHouseholder_DDRB.initializeQ(Q, this.A.numRows, this.A.numCols, this.A.blockLength, false);
        int height = Math.min(this.A.blockLength, this.A.numRows);
        this.V.reshape(height, this.A.numCols, false);
        this.tmp.reshape(height, this.A.numCols, false);
        DSubmatrixD1 subQ = new DSubmatrixD1(Q);
        DSubmatrixD1 subU = new DSubmatrixD1(this.A);
        DSubmatrixD1 subW = new DSubmatrixD1(this.V);
        DSubmatrixD1 tmp = new DSubmatrixD1(this.tmp);
        int N = this.A.numRows;
        int start = N - N % this.A.blockLength;
        if (start == N) {
            start -= this.A.blockLength;
        }
        if (start < 0) {
            start = 0;
        }
        for (int i = start; i >= 0; i -= this.A.blockLength) {
            int blockSize = Math.min(this.A.blockLength, N - i);
            subW.col0 = i;
            subW.row1 = blockSize;
            ((DMatrixD1)subW.original).reshape(subW.row1, subW.col1, false);
            if (transposed) {
                tmp.row0 = i;
                tmp.row1 = this.A.numCols;
                tmp.col0 = 0;
                tmp.col1 = blockSize;
            } else {
                tmp.col0 = i;
                tmp.row1 = blockSize;
            }
            ((DMatrixD1)tmp.original).reshape(tmp.row1, tmp.col1, false);
            subU.col0 = i;
            subU.row0 = i;
            subU.row1 = subU.row0 + blockSize;
            this.copyZeros(subU);
            TridiagonalHelper_DDRB.computeW_row(this.A.blockLength, subU, subW, this.gammas, i);
            subQ.col0 = i;
            subQ.row0 = i;
            if (transposed) {
                MatrixMult_DDRB.multTransB(this.A.blockLength, subQ, subU, tmp);
            } else {
                MatrixMult_DDRB.mult(this.A.blockLength, subU, subQ, tmp);
            }
            if (transposed) {
                MatrixMult_DDRB.multPlus(this.A.blockLength, tmp, subW, subQ);
            } else {
                MatrixMult_DDRB.multPlusTransA(this.A.blockLength, subW, tmp, subQ);
            }
            this.replaceZeros(subU);
        }
        return Q;
    }

    private void copyZeros(DSubmatrixD1 subU) {
        int N = Math.min(this.A.blockLength, subU.col1 - subU.col0);
        for (int i = 0; i < N; ++i) {
            for (int j = 0; j <= i; ++j) {
                this.zerosM.unsafe_set(i, j, subU.get(i, j));
                subU.set(i, j, 0.0);
            }
            if (subU.col0 + i + 1 >= ((DMatrixD1)subU.original).numCols) continue;
            this.zerosM.unsafe_set(i, i + 1, subU.get(i, i + 1));
            subU.set(i, i + 1, 1.0);
        }
    }

    private void replaceZeros(DSubmatrixD1 subU) {
        int N = Math.min(this.A.blockLength, subU.col1 - subU.col0);
        for (int i = 0; i < N; ++i) {
            for (int j = 0; j <= i; ++j) {
                subU.set(i, j, this.zerosM.get(i, j));
            }
            if (subU.col0 + i + 1 >= ((DMatrixD1)subU.original).numCols) continue;
            subU.set(i, i + 1, this.zerosM.get(i, i + 1));
        }
    }

    @Override
    public void getDiagonal(double[] diag, double[] off) {
        diag[0] = this.A.data[0];
        for (int i = 1; i < this.A.numRows; ++i) {
            diag[i] = this.A.get(i, i);
            off[i - 1] = this.A.get(i - 1, i);
        }
    }

    @Override
    public boolean decompose(DMatrixRBlock orig) {
        if (orig.numCols != orig.numRows) {
            throw new IllegalArgumentException("Input matrix must be square.");
        }
        this.init(orig);
        DSubmatrixD1 subA = new DSubmatrixD1(this.A);
        DSubmatrixD1 subV = new DSubmatrixD1(this.V);
        DSubmatrixD1 subU = new DSubmatrixD1(this.A);
        int N = orig.numCols;
        for (int i = 0; i < N; i += this.A.blockLength) {
            int height = Math.min(this.A.blockLength, this.A.numRows - i);
            subA.col0 = subU.col0 = i;
            subA.row0 = subU.row0 = i;
            subU.row1 = subU.row0 + height;
            subV.col0 = i;
            subV.row1 = height;
            ((DMatrixD1)subV.original).reshape(subV.row1, subV.col1, false);
            TridiagonalHelper_DDRB.tridiagUpperRow(this.A.blockLength, subA, this.gammas, subV);
            if (subU.row1 >= orig.numCols) continue;
            double before = subU.get(this.A.blockLength - 1, this.A.blockLength);
            subU.set(this.A.blockLength - 1, this.A.blockLength, 1.0);
            TridiagonalDecompositionHouseholder_DDRB.multPlusTransA(this.A.blockLength, subU, subV, subA);
            TridiagonalDecompositionHouseholder_DDRB.multPlusTransA(this.A.blockLength, subV, subU, subA);
            subU.set(this.A.blockLength - 1, this.A.blockLength, before);
        }
        return true;
    }

    public static void multPlusTransA(int blockLength, DSubmatrixD1 A, DSubmatrixD1 B, DSubmatrixD1 C) {
        int heightA = Math.min(blockLength, A.row1 - A.row0);
        for (int i = C.row0 + blockLength; i < C.row1; i += blockLength) {
            int heightC = Math.min(blockLength, C.row1 - i);
            int indexA = A.row0 * ((DMatrixD1)A.original).numCols + (i - C.row0 + A.col0) * heightA;
            for (int j = i; j < C.col1; j += blockLength) {
                int widthC = Math.min(blockLength, C.col1 - j);
                int indexC = i * ((DMatrixD1)C.original).numCols + j * heightC;
                int indexB = B.row0 * ((DMatrixD1)B.original).numCols + (j - C.col0 + B.col0) * heightA;
                InnerMultiplication_DDRB.blockMultPlusTransA(((DMatrixD1)A.original).data, ((DMatrixD1)B.original).data, ((DMatrixD1)C.original).data, indexA, indexB, indexC, heightA, heightC, widthC);
            }
        }
    }

    private void init(DMatrixRBlock orig) {
        this.A = orig;
        int height = Math.min(this.A.blockLength, this.A.numRows);
        this.V.reshape(height, this.A.numCols, this.A.blockLength, false);
        this.tmp.reshape(height, this.A.numCols, this.A.blockLength, false);
        if (this.gammas.length < this.A.numCols) {
            this.gammas = new double[this.A.numCols];
        }
        this.zerosM.reshape(this.A.blockLength, this.A.blockLength + 1, false);
    }

    @Override
    public boolean inputModified() {
        return true;
    }
}

