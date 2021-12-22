/*
 * Decompiled with CFR 0.150.
 */
package org.ejml.dense.block.decomposition.hessenberg;

import org.ejml.data.FMatrixD1;
import org.ejml.data.FSubmatrixD1;
import org.ejml.dense.block.VectorOps_FDRB;
import org.ejml.dense.block.decomposition.qr.BlockHouseHolder_FDRB;
import org.ejml.dense.row.CommonOps_FDRM;

public class TridiagonalHelper_FDRB {
    public static void tridiagUpperRow(int blockLength, FSubmatrixD1 A, float[] gammas, FSubmatrixD1 V) {
        int blockHeight = Math.min(blockLength, A.row1 - A.row0);
        if (blockHeight <= 1) {
            return;
        }
        int width = A.col1 - A.col0;
        int num = Math.min(width - 1, blockHeight);
        int applyIndex = Math.min(width, blockHeight);
        for (int i = 0; i < num; ++i) {
            BlockHouseHolder_FDRB.computeHouseHolderRow(blockLength, A, gammas, i);
            float gamma = gammas[A.row0 + i];
            TridiagonalHelper_FDRB.computeY(blockLength, A, V, i, gamma);
            TridiagonalHelper_FDRB.computeRowOfV(blockLength, A, V, i, gamma);
            if (i + 1 >= applyIndex) continue;
            TridiagonalHelper_FDRB.applyReflectorsToRow(blockLength, A, V, i + 1);
        }
    }

    public static void computeW_row(int blockLength, FSubmatrixD1 Y, FSubmatrixD1 W, float[] beta, int betaIndex) {
        int heightY = Y.row1 - Y.row0;
        CommonOps_FDRM.fill((FMatrixD1)W.original, 0.0f);
        BlockHouseHolder_FDRB.scale_row(blockLength, Y, W, 0, 1, -beta[betaIndex++]);
        int min = Math.min(heightY, W.col1 - W.col0);
        for (int i = 1; i < min; ++i) {
            float b = -beta[betaIndex++];
            for (int j = 0; j < i; ++j) {
                float yv = BlockHouseHolder_FDRB.innerProdRow(blockLength, Y, i, Y, j, 1);
                VectorOps_FDRB.add_row(blockLength, W, i, 1.0f, W, j, b * yv, W, i, 1, Y.col1 - Y.col0);
            }
            BlockHouseHolder_FDRB.add_row(blockLength, Y, i, b, W, i, 1.0f, W, i, 1, Y.col1 - Y.col0);
        }
    }

    public static void computeV_blockVector(int blockLength, FSubmatrixD1 A, float[] gammas, FSubmatrixD1 V) {
        int blockHeight = Math.min(blockLength, A.row1 - A.row0);
        if (blockHeight <= 1) {
            return;
        }
        int width = A.col1 - A.col0;
        int num = Math.min(width - 1, blockHeight);
        for (int i = 0; i < num; ++i) {
            float gamma = gammas[A.row0 + i];
            TridiagonalHelper_FDRB.computeY(blockLength, A, V, i, gamma);
            TridiagonalHelper_FDRB.computeRowOfV(blockLength, A, V, i, gamma);
        }
    }

    public static void applyReflectorsToRow(int blockLength, FSubmatrixD1 A, FSubmatrixD1 V, int row) {
        int height = Math.min(blockLength, A.row1 - A.row0);
        float[] dataA = ((FMatrixD1)A.original).data;
        float[] dataV = ((FMatrixD1)V.original).data;
        for (int i = 0; i < row; ++i) {
            int width = Math.min(blockLength, A.col1 - A.col0);
            int indexU = ((FMatrixD1)A.original).numCols * A.row0 + height * A.col0 + i * width + row;
            int indexV = ((FMatrixD1)V.original).numCols * V.row0 + height * V.col0 + i * width + row;
            float u_row = i + 1 == row ? 1.0f : dataA[indexU];
            float v_row = dataV[indexV];
            float before = A.get(i, i + 1);
            A.set(i, i + 1, 1.0f);
            VectorOps_FDRB.add_row(blockLength, A, row, 1.0f, V, i, u_row, A, row, row, A.col1 - A.col0);
            VectorOps_FDRB.add_row(blockLength, A, row, 1.0f, A, i, v_row, A, row, row, A.col1 - A.col0);
            A.set(i, i + 1, before);
        }
    }

    public static void computeY(int blockLength, FSubmatrixD1 A, FSubmatrixD1 V, int row, float gamma) {
        TridiagonalHelper_FDRB.multA_u(blockLength, A, V, row);
        for (int i = 0; i < row; ++i) {
            float dot_v_u = BlockHouseHolder_FDRB.innerProdRow(blockLength, A, row, V, i, 1);
            float dot_u_u = BlockHouseHolder_FDRB.innerProdRow(blockLength, A, row, A, i, 1);
            VectorOps_FDRB.add_row(blockLength, V, row, 1.0f, A, i, dot_v_u, V, row, row + 1, A.col1 - A.col0);
            VectorOps_FDRB.add_row(blockLength, V, row, 1.0f, V, i, dot_u_u, V, row, row + 1, A.col1 - A.col0);
        }
        VectorOps_FDRB.scale_row(blockLength, V, row, -gamma, V, row, row + 1, V.col1 - V.col0);
    }

    public static void multA_u(int blockLength, FSubmatrixD1 A, FSubmatrixD1 V, int row) {
        int heightMatA = A.row1 - A.row0;
        for (int i = row + 1; i < heightMatA; ++i) {
            float val = TridiagonalHelper_FDRB.innerProdRowSymm(blockLength, A, row, A, i, 1);
            V.set(row, i, val);
        }
    }

    public static float innerProdRowSymm(int blockLength, FSubmatrixD1 A, int rowA, FSubmatrixD1 B, int rowB, int zeroOffset) {
        int offset = rowA + zeroOffset;
        if (offset + B.col0 >= B.col1) {
            return 0.0f;
        }
        if (offset < rowB) {
            float total = B.get(offset, rowB);
            total += VectorOps_FDRB.dot_row_col(blockLength, A, rowA, B, rowB, offset + 1, rowB);
            return total += VectorOps_FDRB.dot_row(blockLength, A, rowA, B, rowB, rowB, A.col1 - A.col0);
        }
        float total = B.get(rowB, offset);
        return total += VectorOps_FDRB.dot_row(blockLength, A, rowA, B, rowB, offset + 1, A.col1 - A.col0);
    }

    public static void computeRowOfV(int blockLength, FSubmatrixD1 A, FSubmatrixD1 V, int row, float gamma) {
        float val = BlockHouseHolder_FDRB.innerProdRow(blockLength, A, row, V, row, 1);
        float before = A.get(row, row + 1);
        A.set(row, row + 1, 1.0f);
        VectorOps_FDRB.add_row(blockLength, V, row, 1.0f, A, row, -0.5f * gamma * val, V, row, row + 1, A.col1 - A.col0);
        A.set(row, row + 1, before);
    }
}

