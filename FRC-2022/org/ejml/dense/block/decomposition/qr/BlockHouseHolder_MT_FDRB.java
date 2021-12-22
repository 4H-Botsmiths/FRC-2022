/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  org.jetbrains.annotations.Nullable
 */
package org.ejml.dense.block.decomposition.qr;

import org.ejml.UtilEjml;
import org.ejml.concurrency.EjmlConcurrency;
import org.ejml.data.FGrowArray;
import org.ejml.data.FMatrixD1;
import org.ejml.data.FSubmatrixD1;
import org.ejml.dense.block.InnerMultiplication_FDRB;
import org.ejml.dense.block.VectorOps_FDRB;
import org.ejml.dense.block.decomposition.qr.BlockHouseHolder_FDRB;
import org.jetbrains.annotations.Nullable;
import pabeles.concurrency.GrowArray;

public class BlockHouseHolder_MT_FDRB {
    public static boolean decomposeQR_block_col(int blockLength, FSubmatrixD1 Y, float[] gamma) {
        int width = Y.col1 - Y.col0;
        int height = Y.row1 - Y.row0;
        int min = Math.min(width, height);
        for (int i = 0; i < min; ++i) {
            if (!BlockHouseHolder_MT_FDRB.computeHouseHolderCol(blockLength, Y, gamma, i)) {
                return false;
            }
            BlockHouseHolder_MT_FDRB.rank1UpdateMultR_Col(blockLength, Y, i, gamma[Y.col0 + i]);
        }
        return true;
    }

    public static boolean computeHouseHolderCol(int blockLength, FSubmatrixD1 Y, float[] gamma, int i) {
        float max = BlockHouseHolder_FDRB.findMaxCol(blockLength, Y, i);
        if (max == 0.0f) {
            return false;
        }
        float tau = BlockHouseHolder_MT_FDRB.computeTauAndDivideCol(blockLength, Y, i, max);
        float u_0 = Y.get(i, i) + tau;
        BlockHouseHolder_MT_FDRB.divideElementsCol(blockLength, Y, i, u_0);
        gamma[Y.col0 + i] = u_0 / tau;
        Y.set(i, i, -(tau *= max));
        return true;
    }

    public static boolean computeHouseHolderRow(int blockLength, FSubmatrixD1 Y, float[] gamma, int i) {
        float max = BlockHouseHolder_FDRB.findMaxRow(blockLength, Y, i, i + 1);
        if (max == 0.0f) {
            return false;
        }
        float tau = BlockHouseHolder_MT_FDRB.computeTauAndDivideRow(blockLength, Y, i, i + 1, max);
        float u_0 = Y.get(i, i + 1) + tau;
        VectorOps_FDRB.div_row(blockLength, Y, i, u_0, Y, i, i + 1, Y.col1 - Y.col0);
        gamma[Y.row0 + i] = u_0 / tau;
        Y.set(i, i + 1, -tau * max);
        return true;
    }

    public static void rank1UpdateMultR_Col(int blockLength, FSubmatrixD1 A, int col, float gamma) {
        int width = Math.min(blockLength, A.col1 - A.col0);
        float[] dataA = ((FMatrixD1)A.original).data;
        EjmlConcurrency.loopFor(col + 1, width, j -> {
            float total = BlockHouseHolder_MT_FDRB.innerProdCol(blockLength, A, col, width, j, width);
            total *= gamma;
            for (int i = A.row0; i < A.row1; i += blockLength) {
                int height = Math.min(blockLength, A.row1 - i);
                int indexU = i * ((FMatrixD1)A.original).numCols + height * A.col0 + col;
                int indexA = i * ((FMatrixD1)A.original).numCols + height * A.col0 + j;
                if (i == A.row0) {
                    indexU += width * (col + 1);
                    int n = indexA += width * col;
                    dataA[n] = dataA[n] - total;
                    indexA += width;
                    int k = col + 1;
                    while (k < height) {
                        int n2 = indexA;
                        dataA[n2] = dataA[n2] - total * dataA[indexU];
                        ++k;
                        indexU += width;
                        indexA += width;
                    }
                    continue;
                }
                int endU = indexU + width * height;
                while (indexU != endU) {
                    int n = indexA;
                    dataA[n] = dataA[n] - total * dataA[indexU];
                    indexU += width;
                    indexA += width;
                }
            }
        });
    }

    public static void rank1UpdateMultR_TopRow(int blockLength, FSubmatrixD1 A, int col, float gamma) {
        float[] dataA = ((FMatrixD1)A.original).data;
        int widthCol = Math.min(blockLength, A.col1 - col);
        for (int colStartJ = A.col0 + blockLength; colStartJ < A.col1; colStartJ += blockLength) {
            int widthJ = Math.min(blockLength, A.col1 - colStartJ);
            for (int j = 0; j < widthJ; ++j) {
                float total = BlockHouseHolder_MT_FDRB.innerProdCol(blockLength, A, col, widthCol, colStartJ - A.col0 + j, widthJ) * gamma;
                int i = A.row0;
                int height = Math.min(blockLength, A.row1 - i);
                int indexU = i * ((FMatrixD1)A.original).numCols + height * A.col0 + col;
                int indexA = i * ((FMatrixD1)A.original).numCols + height * colStartJ + j;
                indexU += widthCol * (col + 1);
                int n = indexA += widthJ * col;
                dataA[n] = dataA[n] - total;
                indexA += widthJ;
                int k = col + 1;
                while (k < height) {
                    int n2 = indexA;
                    dataA[n2] = dataA[n2] - total * dataA[indexU];
                    ++k;
                    indexU += widthCol;
                    indexA += widthJ;
                }
            }
        }
    }

    public static void rank1UpdateMultL_Row(int blockLength, FSubmatrixD1 A, int row, int colStart, float gamma) {
        int height = Math.min(blockLength, A.row1 - A.row0);
        float[] dataA = ((FMatrixD1)A.original).data;
        int zeroOffset = colStart - row;
        for (int i = row + 1; i < height; ++i) {
            float total = BlockHouseHolder_MT_FDRB.innerProdRow(blockLength, A, row, A, i, zeroOffset);
            total *= gamma;
            for (int j = A.col0; j < A.col1; j += blockLength) {
                int k;
                int width = Math.min(blockLength, A.col1 - j);
                int indexU = A.row0 * ((FMatrixD1)A.original).numCols + height * j + row * width;
                int indexA = A.row0 * ((FMatrixD1)A.original).numCols + height * j + i * width;
                if (j == A.col0) {
                    indexU += colStart + 1;
                    indexA += colStart;
                    int n = indexA++;
                    dataA[n] = dataA[n] - total;
                    for (k = colStart + 1; k < width; ++k) {
                        int n2 = indexA++;
                        dataA[n2] = dataA[n2] - total * dataA[indexU++];
                    }
                    continue;
                }
                for (k = 0; k < width; ++k) {
                    int n = indexA++;
                    dataA[n] = dataA[n] - total * dataA[indexU++];
                }
            }
        }
    }

    public static void rank1UpdateMultL_LeftCol(int blockLength, FSubmatrixD1 A, int row, float gamma, int zeroOffset) {
        int heightU = Math.min(blockLength, A.row1 - A.row0);
        int width = Math.min(blockLength, A.col1 - A.col0);
        float[] data = ((FMatrixD1)A.original).data;
        for (int blockStart = A.row0 + blockLength; blockStart < A.row1; blockStart += blockLength) {
            int heightA = Math.min(blockLength, A.row1 - blockStart);
            for (int i = 0; i < heightA; ++i) {
                float total = BlockHouseHolder_MT_FDRB.innerProdRow(blockLength, A, row, A, i + (blockStart - A.row0), zeroOffset);
                int indexU = A.row0 * ((FMatrixD1)A.original).numCols + heightU * A.col0 + row * width;
                int indexA = blockStart * ((FMatrixD1)A.original).numCols + heightA * A.col0 + i * width;
                indexU += zeroOffset + 1;
                indexA += zeroOffset;
                int n = indexA++;
                data[n] = data[n] - (total *= gamma);
                for (int k = zeroOffset + 1; k < width; ++k) {
                    int n2 = indexA++;
                    data[n2] = data[n2] - total * data[indexU++];
                }
            }
        }
    }

    public static float innerProdCol(int blockLength, FSubmatrixD1 A, int colA, int widthA, int colB, int widthB) {
        float total = 0.0f;
        float[] data = ((FMatrixD1)A.original).data;
        int colBlockA = A.col0 + colA - colA % blockLength;
        int colBlockB = A.col0 + colB - colB % blockLength;
        colA %= blockLength;
        colB %= blockLength;
        for (int i = A.row0; i < A.row1; i += blockLength) {
            int endA;
            int height = Math.min(blockLength, A.row1 - i);
            int indexA = i * ((FMatrixD1)A.original).numCols + height * colBlockA + colA;
            int indexB = i * ((FMatrixD1)A.original).numCols + height * colBlockB + colB;
            if (i == A.row0) {
                total = data[indexB += widthB * colA];
                indexB += widthB;
                endA = (indexA += widthA * (colA + 1)) + (height - colA - 1) * widthA;
                while (indexA != endA) {
                    total += data[indexA] * data[indexB];
                    indexA += widthA;
                    indexB += widthB;
                }
                continue;
            }
            endA = indexA + widthA * height;
            while (indexA != endA) {
                total += data[indexA] * data[indexB];
                indexA += widthA;
                indexB += widthB;
            }
        }
        return total;
    }

    public static float innerProdRow(int blockLength, FSubmatrixD1 A, int rowA, FSubmatrixD1 B, int rowB, int zeroOffset) {
        int offset = rowA + zeroOffset;
        if (offset + B.col0 >= B.col1) {
            return 0.0f;
        }
        float total = B.get(rowB, offset);
        return total += VectorOps_FDRB.dot_row(blockLength, A, rowA, B, rowB, offset + 1, A.col1 - A.col0);
    }

    public static void add_row(int blockLength, FSubmatrixD1 A, int rowA, float alpha, FSubmatrixD1 B, int rowB, float beta, FSubmatrixD1 C, int rowC, int zeroOffset, int end) {
        int offset = rowA + zeroOffset;
        if (C.col0 + offset >= C.col1) {
            return;
        }
        C.set(rowC, offset, alpha + B.get(rowB, offset) * beta);
        VectorOps_FDRB.add_row(blockLength, A, rowA, alpha, B, rowB, beta, C, rowC, offset + 1, end);
    }

    public static void divideElementsCol(int blockLength, FSubmatrixD1 Y, int col, float val) {
        int width = Math.min(blockLength, Y.col1 - Y.col0);
        float[] dataY = ((FMatrixD1)Y.original).data;
        EjmlConcurrency.loopFor(Y.row0, Y.row1, blockLength, i -> {
            int index;
            int height = Math.min(blockLength, Y.row1 - i);
            if (i == Y.row0) {
                index += width * (col + 1);
                int k = col + 1;
                while (k < height) {
                    int n = index;
                    dataY[n] = dataY[n] / val;
                    ++k;
                    index += width;
                }
            } else {
                int endIndex = index + width * height;
                for (index = i * ((FMatrixD1)Y.original).numCols + height * Y.col0 + col; index != endIndex; index += width) {
                    int n = index;
                    dataY[n] = dataY[n] / val;
                }
            }
        });
    }

    public static void scale_row(int blockLength, FSubmatrixD1 Y, FSubmatrixD1 W, int row, int zeroOffset, float val) {
        int offset = row + zeroOffset;
        if (offset >= W.col1 - W.col0) {
            return;
        }
        W.set(row, offset, val);
        VectorOps_FDRB.scale_row(blockLength, Y, row, val, W, row, offset + 1, Y.col1 - Y.col0);
    }

    public static float computeTauAndDivideCol(int blockLength, FSubmatrixD1 Y, int col, float max) {
        int width = Math.min(blockLength, Y.col1 - Y.col0);
        float[] dataY = ((FMatrixD1)Y.original).data;
        float top = 0.0f;
        float norm2 = 0.0f;
        for (int i = Y.row0; i < Y.row1; i += blockLength) {
            float val;
            int k;
            int height = Math.min(blockLength, Y.row1 - i);
            int index = i * ((FMatrixD1)Y.original).numCols + height * Y.col0 + col;
            if (i == Y.row0) {
                int n = index += width * col;
                float f = dataY[n] / max;
                dataY[n] = f;
                top = f;
                norm2 += top * top;
                index += width;
                k = col + 1;
                while (k < height) {
                    int n2 = index;
                    float f2 = dataY[n2] / max;
                    dataY[n2] = f2;
                    val = f2;
                    norm2 += val * val;
                    ++k;
                    index += width;
                }
                continue;
            }
            k = 0;
            while (k < height) {
                int n = index;
                float f = dataY[n] / max;
                dataY[n] = f;
                val = f;
                norm2 += val * val;
                ++k;
                index += width;
            }
        }
        norm2 = (float)Math.sqrt(norm2);
        if (top < 0.0f) {
            norm2 = -norm2;
        }
        return norm2;
    }

    public static float computeTauAndDivideRow(int blockLength, FSubmatrixD1 Y, int row, int colStart, float max) {
        int height = Math.min(blockLength, Y.row1 - Y.row0);
        float[] dataY = ((FMatrixD1)Y.original).data;
        float top = 0.0f;
        float norm2 = 0.0f;
        int startJ = Y.col0 + colStart - colStart % blockLength;
        colStart %= blockLength;
        for (int j = startJ; j < Y.col1; j += blockLength) {
            float val;
            int k;
            int width = Math.min(blockLength, Y.col1 - j);
            int index = Y.row0 * ((FMatrixD1)Y.original).numCols + height * j + row * width;
            if (j == startJ) {
                index += colStart;
                int n = index++;
                float f = dataY[n] / max;
                dataY[n] = f;
                top = f;
                norm2 += top * top;
                for (k = colStart + 1; k < width; ++k) {
                    int n2 = index++;
                    float f2 = dataY[n2] / max;
                    dataY[n2] = f2;
                    val = f2;
                    norm2 += val * val;
                }
                continue;
            }
            for (k = 0; k < width; ++k) {
                int n = index++;
                float f = dataY[n] / max;
                dataY[n] = f;
                val = f;
                norm2 += val * val;
            }
        }
        norm2 = (float)Math.sqrt(norm2);
        if (top < 0.0f) {
            norm2 = -norm2;
        }
        return norm2;
    }

    public static float findMaxCol(int blockLength, FSubmatrixD1 Y, int col) {
        int width = Math.min(blockLength, Y.col1 - Y.col0);
        float[] dataY = ((FMatrixD1)Y.original).data;
        float max = 0.0f;
        for (int i = Y.row0; i < Y.row1; i += blockLength) {
            float v;
            int k;
            int height = Math.min(blockLength, Y.row1 - i);
            int index = i * ((FMatrixD1)Y.original).numCols + height * Y.col0 + col;
            if (i == Y.row0) {
                index += width * col;
                k = col;
                while (k < height) {
                    v = Math.abs(dataY[index]);
                    if (v > max) {
                        max = v;
                    }
                    ++k;
                    index += width;
                }
                continue;
            }
            k = 0;
            while (k < height) {
                v = Math.abs(dataY[index]);
                if (v > max) {
                    max = v;
                }
                ++k;
                index += width;
            }
        }
        return max;
    }

    public static float findMaxRow(int blockLength, FSubmatrixD1 Y, int row, int colStart) {
        int height = Math.min(blockLength, Y.row1 - Y.row0);
        float[] dataY = ((FMatrixD1)Y.original).data;
        float max = 0.0f;
        for (int j = Y.col0; j < Y.col1; j += blockLength) {
            float v;
            int k;
            int width = Math.min(blockLength, Y.col1 - j);
            int index = Y.row0 * ((FMatrixD1)Y.original).numCols + height * j + row * width;
            if (j == Y.col0) {
                index += colStart;
                for (k = colStart; k < width; ++k) {
                    int n = index++;
                    v = Math.abs(dataY[n]);
                    if (!(v > max)) continue;
                    max = v;
                }
                continue;
            }
            for (k = 0; k < width; ++k) {
                int n = index++;
                v = Math.abs(dataY[n]);
                if (!(v > max)) continue;
                max = v;
            }
        }
        return max;
    }

    public static void computeW_Column(int blockLength, FSubmatrixD1 Y, FSubmatrixD1 W, @Nullable GrowArray<FGrowArray> workspace, float[] beta, int betaIndex) {
        workspace = UtilEjml.checkDeclare_F32(workspace);
        int widthB = W.col1 - W.col0;
        BlockHouseHolder_MT_FDRB.initializeW(blockLength, W, Y, widthB, beta[betaIndex]);
        int min = Math.min(widthB, W.row1 - W.row0);
        float[] temp = workspace.grow().reshape((int)(Y.col1 - Y.col0)).data;
        for (int j = 1; j < min; ++j) {
            BlockHouseHolder_MT_FDRB.computeY_t_V(blockLength, Y, j, temp);
            BlockHouseHolder_MT_FDRB.computeZ(blockLength, Y, W, j, temp, beta[betaIndex + j]);
        }
    }

    public static void initializeW(int blockLength, FSubmatrixD1 W, FSubmatrixD1 Y, int widthB, float b) {
        float[] dataW = ((FMatrixD1)W.original).data;
        float[] dataY = ((FMatrixD1)Y.original).data;
        EjmlConcurrency.loopFor(W.row0, W.row1, blockLength, i -> {
            int heightW = Math.min(blockLength, W.row1 - i);
            int indexW = i * ((FMatrixD1)W.original).numCols + heightW * W.col0;
            int indexY = i * ((FMatrixD1)Y.original).numCols + heightW * Y.col0;
            if (i == W.row0) {
                dataW[indexW] = -b;
                indexW += widthB;
                indexY += widthB;
                int k = 1;
                while (k < heightW) {
                    dataW[indexW] = -b * dataY[indexY];
                    ++k;
                    indexW += widthB;
                    indexY += widthB;
                }
            } else {
                int k = 0;
                while (k < heightW) {
                    dataW[indexW] = -b * dataY[indexY];
                    ++k;
                    indexW += widthB;
                    indexY += widthB;
                }
            }
        });
    }

    public static void computeZ(int blockLength, FSubmatrixD1 Y, FSubmatrixD1 W, int col, float[] temp, float beta) {
        int width = Y.col1 - Y.col0;
        float[] dataW = ((FMatrixD1)W.original).data;
        float[] dataY = ((FMatrixD1)Y.original).data;
        int colsW = ((FMatrixD1)W.original).numCols;
        float beta_neg = -beta;
        EjmlConcurrency.loopFor(Y.row0, Y.row1, blockLength, i -> {
            int heightW = Math.min(blockLength, Y.row1 - i);
            int indexW = i * colsW + heightW * W.col0;
            int indexZ = i * colsW + heightW * W.col0 + col;
            int indexV = i * ((FMatrixD1)Y.original).numCols + heightW * Y.col0 + col;
            if (i == Y.row0) {
                int k = 0;
                while (k < heightW) {
                    float total = 0.0f;
                    for (int j = 0; j < col; ++j) {
                        total += dataW[indexW + j] * temp[j];
                    }
                    dataW[indexZ] = k < col ? -beta * total : (k == col ? beta_neg * (1.0f + total) : beta_neg * (dataY[indexV] + total));
                    ++k;
                    indexZ += width;
                    indexW += width;
                    indexV += width;
                }
            } else {
                int endZ = indexZ + width * heightW;
                while (indexZ != endZ) {
                    float total = 0.0f;
                    for (int j = 0; j < col; ++j) {
                        total += dataW[indexW + j] * temp[j];
                    }
                    dataW[indexZ] = beta_neg * (dataY[indexV] + total);
                    indexZ += width;
                    indexW += width;
                    indexV += width;
                }
            }
        });
    }

    public static void computeY_t_V(int blockLength, FSubmatrixD1 Y, int col, float[] temp) {
        int widthB = Y.col1 - Y.col0;
        EjmlConcurrency.loopFor(0, col, j -> {
            temp[j] = BlockHouseHolder_MT_FDRB.innerProdCol(blockLength, Y, col, widthB, j, widthB);
        });
    }

    public static void multAdd_zeros(int blockLength, FSubmatrixD1 Y, FSubmatrixD1 B, FSubmatrixD1 C) {
        int widthY = Y.col1 - Y.col0;
        EjmlConcurrency.loopFor(Y.row0, Y.row1, blockLength, i -> {
            int heightY = Math.min(blockLength, Y.row1 - i);
            for (int j = B.col0; j < B.col1; j += blockLength) {
                int widthB = Math.min(blockLength, B.col1 - j);
                int indexC = (i - Y.row0 + C.row0) * ((FMatrixD1)C.original).numCols + (j - B.col0 + C.col0) * heightY;
                for (int k = Y.col0; k < Y.col1; k += blockLength) {
                    int indexY = i * ((FMatrixD1)Y.original).numCols + k * heightY;
                    int indexB = (k - Y.col0 + B.row0) * ((FMatrixD1)B.original).numCols + j * widthY;
                    if (i == Y.row0) {
                        BlockHouseHolder_MT_FDRB.multBlockAdd_zerosone(((FMatrixD1)Y.original).data, ((FMatrixD1)B.original).data, ((FMatrixD1)C.original).data, indexY, indexB, indexC, heightY, widthY, widthB);
                        continue;
                    }
                    InnerMultiplication_FDRB.blockMultPlus(((FMatrixD1)Y.original).data, ((FMatrixD1)B.original).data, ((FMatrixD1)C.original).data, indexY, indexB, indexC, heightY, widthY, widthB);
                }
            }
        });
    }

    public static void multBlockAdd_zerosone(float[] dataA, float[] dataB, float[] dataC, int indexA, int indexB, int indexC, int heightA, int widthA, int widthC) {
        for (int i = 0; i < heightA; ++i) {
            for (int j = 0; j < widthC; ++j) {
                float val = i < widthA ? dataB[i * widthC + j + indexB] : 0.0f;
                int end = Math.min(i, widthA);
                int innerIndexA = i * widthA + indexA;
                int innerOffsetB = j + indexB;
                int endA = innerIndexA + end;
                while (innerIndexA != endA) {
                    val += dataA[innerIndexA++] * dataB[innerOffsetB];
                    innerOffsetB += widthC;
                }
                int n = i * widthC + j + indexC;
                dataC[n] = dataC[n] + val;
            }
        }
    }

    public static void multTransA_vecCol(int blockLength, FSubmatrixD1 A, FSubmatrixD1 B, FSubmatrixD1 C) {
        int widthA = A.col1 - A.col0;
        if (widthA > blockLength) {
            throw new IllegalArgumentException("A is expected to be at most one block wide.");
        }
        EjmlConcurrency.loopFor(B.col0, B.col1, blockLength, j -> {
            int widthB = Math.min(blockLength, B.col1 - j);
            int indexC = C.row0 * ((FMatrixD1)C.original).numCols + (j - B.col0 + C.col0) * widthA;
            for (int k = A.row0; k < A.row1; k += blockLength) {
                int heightA = Math.min(blockLength, A.row1 - k);
                int indexA = k * ((FMatrixD1)A.original).numCols + A.col0 * heightA;
                int indexB = (k - A.row0 + B.row0) * ((FMatrixD1)B.original).numCols + j * heightA;
                if (k == A.row0) {
                    BlockHouseHolder_MT_FDRB.multTransABlockSet_lowerTriag(((FMatrixD1)A.original).data, ((FMatrixD1)B.original).data, ((FMatrixD1)C.original).data, indexA, indexB, indexC, heightA, widthA, widthB);
                    continue;
                }
                InnerMultiplication_FDRB.blockMultPlusTransA(((FMatrixD1)A.original).data, ((FMatrixD1)B.original).data, ((FMatrixD1)C.original).data, indexA, indexB, indexC, heightA, widthA, widthB);
            }
        });
    }

    protected static void multTransABlockSet_lowerTriag(float[] dataA, float[] dataB, float[] dataC, int indexA, int indexB, int indexC, int heightA, int widthA, int widthC) {
        for (int i = 0; i < widthA; ++i) {
            for (int j = 0; j < widthC; ++j) {
                float val = i < heightA ? dataB[i * widthC + j + indexB] : 0.0f;
                for (int k = i + 1; k < heightA; ++k) {
                    val += dataA[k * widthA + i + indexA] * dataB[k * widthC + j + indexB];
                }
                dataC[i * widthC + j + indexC] = val;
            }
        }
    }
}

