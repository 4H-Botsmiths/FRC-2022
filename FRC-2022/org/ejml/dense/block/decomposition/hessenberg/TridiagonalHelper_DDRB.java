/*
 * Decompiled with CFR 0.150.
 */
package org.ejml.dense.block.decomposition.hessenberg;

import org.ejml.data.DMatrixD1;
import org.ejml.data.DSubmatrixD1;
import org.ejml.dense.block.VectorOps_DDRB;
import org.ejml.dense.block.decomposition.qr.BlockHouseHolder_DDRB;
import org.ejml.dense.row.CommonOps_DDRM;

public class TridiagonalHelper_DDRB {
    public static void tridiagUpperRow(int blockLength, DSubmatrixD1 A, double[] gammas, DSubmatrixD1 V) {
        int blockHeight = Math.min(blockLength, A.row1 - A.row0);
        if (blockHeight <= 1) {
            return;
        }
        int width = A.col1 - A.col0;
        int num = Math.min(width - 1, blockHeight);
        int applyIndex = Math.min(width, blockHeight);
        for (int i = 0; i < num; ++i) {
            BlockHouseHolder_DDRB.computeHouseHolderRow(blockLength, A, gammas, i);
            double gamma = gammas[A.row0 + i];
            TridiagonalHelper_DDRB.computeY(blockLength, A, V, i, gamma);
            TridiagonalHelper_DDRB.computeRowOfV(blockLength, A, V, i, gamma);
            if (i + 1 >= applyIndex) continue;
            TridiagonalHelper_DDRB.applyReflectorsToRow(blockLength, A, V, i + 1);
        }
    }

    public static void computeW_row(int blockLength, DSubmatrixD1 Y, DSubmatrixD1 W, double[] beta, int betaIndex) {
        int heightY = Y.row1 - Y.row0;
        CommonOps_DDRM.fill((DMatrixD1)W.original, 0.0);
        BlockHouseHolder_DDRB.scale_row(blockLength, Y, W, 0, 1, -beta[betaIndex++]);
        int min = Math.min(heightY, W.col1 - W.col0);
        for (int i = 1; i < min; ++i) {
            double b = -beta[betaIndex++];
            for (int j = 0; j < i; ++j) {
                double yv = BlockHouseHolder_DDRB.innerProdRow(blockLength, Y, i, Y, j, 1);
                VectorOps_DDRB.add_row(blockLength, W, i, 1.0, W, j, b * yv, W, i, 1, Y.col1 - Y.col0);
            }
            BlockHouseHolder_DDRB.add_row(blockLength, Y, i, b, W, i, 1.0, W, i, 1, Y.col1 - Y.col0);
        }
    }

    public static void computeV_blockVector(int blockLength, DSubmatrixD1 A, double[] gammas, DSubmatrixD1 V) {
        int blockHeight = Math.min(blockLength, A.row1 - A.row0);
        if (blockHeight <= 1) {
            return;
        }
        int width = A.col1 - A.col0;
        int num = Math.min(width - 1, blockHeight);
        for (int i = 0; i < num; ++i) {
            double gamma = gammas[A.row0 + i];
            TridiagonalHelper_DDRB.computeY(blockLength, A, V, i, gamma);
            TridiagonalHelper_DDRB.computeRowOfV(blockLength, A, V, i, gamma);
        }
    }

    public static void applyReflectorsToRow(int blockLength, DSubmatrixD1 A, DSubmatrixD1 V, int row) {
        int height = Math.min(blockLength, A.row1 - A.row0);
        double[] dataA = ((DMatrixD1)A.original).data;
        double[] dataV = ((DMatrixD1)V.original).data;
        for (int i = 0; i < row; ++i) {
            int width = Math.min(blockLength, A.col1 - A.col0);
            int indexU = ((DMatrixD1)A.original).numCols * A.row0 + height * A.col0 + i * width + row;
            int indexV = ((DMatrixD1)V.original).numCols * V.row0 + height * V.col0 + i * width + row;
            double u_row = i + 1 == row ? 1.0 : dataA[indexU];
            double v_row = dataV[indexV];
            double before = A.get(i, i + 1);
            A.set(i, i + 1, 1.0);
            VectorOps_DDRB.add_row(blockLength, A, row, 1.0, V, i, u_row, A, row, row, A.col1 - A.col0);
            VectorOps_DDRB.add_row(blockLength, A, row, 1.0, A, i, v_row, A, row, row, A.col1 - A.col0);
            A.set(i, i + 1, before);
        }
    }

    public static void computeY(int blockLength, DSubmatrixD1 A, DSubmatrixD1 V, int row, double gamma) {
        TridiagonalHelper_DDRB.multA_u(blockLength, A, V, row);
        for (int i = 0; i < row; ++i) {
            double dot_v_u = BlockHouseHolder_DDRB.innerProdRow(blockLength, A, row, V, i, 1);
            double dot_u_u = BlockHouseHolder_DDRB.innerProdRow(blockLength, A, row, A, i, 1);
            VectorOps_DDRB.add_row(blockLength, V, row, 1.0, A, i, dot_v_u, V, row, row + 1, A.col1 - A.col0);
            VectorOps_DDRB.add_row(blockLength, V, row, 1.0, V, i, dot_u_u, V, row, row + 1, A.col1 - A.col0);
        }
        VectorOps_DDRB.scale_row(blockLength, V, row, -gamma, V, row, row + 1, V.col1 - V.col0);
    }

    public static void multA_u(int blockLength, DSubmatrixD1 A, DSubmatrixD1 V, int row) {
        int heightMatA = A.row1 - A.row0;
        for (int i = row + 1; i < heightMatA; ++i) {
            double val = TridiagonalHelper_DDRB.innerProdRowSymm(blockLength, A, row, A, i, 1);
            V.set(row, i, val);
        }
    }

    public static double innerProdRowSymm(int blockLength, DSubmatrixD1 A, int rowA, DSubmatrixD1 B, int rowB, int zeroOffset) {
        int offset = rowA + zeroOffset;
        if (offset + B.col0 >= B.col1) {
            return 0.0;
        }
        if (offset < rowB) {
            double total = B.get(offset, rowB);
            total += VectorOps_DDRB.dot_row_col(blockLength, A, rowA, B, rowB, offset + 1, rowB);
            return total += VectorOps_DDRB.dot_row(blockLength, A, rowA, B, rowB, rowB, A.col1 - A.col0);
        }
        double total = B.get(rowB, offset);
        return total += VectorOps_DDRB.dot_row(blockLength, A, rowA, B, rowB, offset + 1, A.col1 - A.col0);
    }

    public static void computeRowOfV(int blockLength, DSubmatrixD1 A, DSubmatrixD1 V, int row, double gamma) {
        double val = BlockHouseHolder_DDRB.innerProdRow(blockLength, A, row, V, row, 1);
        double before = A.get(row, row + 1);
        A.set(row, row + 1, 1.0);
        VectorOps_DDRB.add_row(blockLength, V, row, 1.0, A, row, -0.5 * gamma * val, V, row, row + 1, A.col1 - A.col0);
        A.set(row, row + 1, before);
    }
}

