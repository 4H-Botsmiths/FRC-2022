/*
 * Decompiled with CFR 0.150.
 */
package org.ejml.dense.block;

import org.ejml.data.FMatrixD1;
import org.ejml.data.FSubmatrixD1;
import org.ejml.dense.block.InnerMultiplication_FDRB;

public class InnerRankUpdate_FDRB {
    public static void rankNUpdate(int blockLength, float alpha, FSubmatrixD1 A, FSubmatrixD1 B) {
        int heightB = B.row1 - B.row0;
        if (heightB > blockLength) {
            throw new IllegalArgumentException("Height of B cannot be greater than the block length");
        }
        int N = B.col1 - B.col0;
        if (A.col1 - A.col0 != N) {
            throw new IllegalArgumentException("A does not have the expected number of columns based on B's width");
        }
        if (A.row1 - A.row0 != N) {
            throw new IllegalArgumentException("A does not have the expected number of rows based on B's width");
        }
        for (int i = B.col0; i < B.col1; i += blockLength) {
            int indexB_i = B.row0 * ((FMatrixD1)B.original).numCols + i * heightB;
            int widthB_i = Math.min(blockLength, B.col1 - i);
            int rowA = i - B.col0 + A.row0;
            int heightA = Math.min(blockLength, A.row1 - rowA);
            for (int j = B.col0; j < B.col1; j += blockLength) {
                int widthB_j = Math.min(blockLength, B.col1 - j);
                int indexA = rowA * ((FMatrixD1)A.original).numCols + (j - B.col0 + A.col0) * heightA;
                int indexB_j = B.row0 * ((FMatrixD1)B.original).numCols + j * heightB;
                InnerMultiplication_FDRB.blockMultPlusTransA(alpha, ((FMatrixD1)B.original).data, ((FMatrixD1)B.original).data, ((FMatrixD1)A.original).data, indexB_i, indexB_j, indexA, heightB, widthB_i, widthB_j);
            }
        }
    }

    public static void symmRankNMinus_U(int blockLength, FSubmatrixD1 A, FSubmatrixD1 B) {
        int heightB = B.row1 - B.row0;
        if (heightB > blockLength) {
            throw new IllegalArgumentException("Height of B cannot be greater than the block length");
        }
        int N = B.col1 - B.col0;
        if (A.col1 - A.col0 != N) {
            throw new IllegalArgumentException("A does not have the expected number of columns based on B's width");
        }
        if (A.row1 - A.row0 != N) {
            throw new IllegalArgumentException("A does not have the expected number of rows based on B's width");
        }
        for (int i = B.col0; i < B.col1; i += blockLength) {
            int indexB_i = B.row0 * ((FMatrixD1)B.original).numCols + i * heightB;
            int widthB_i = Math.min(blockLength, B.col1 - i);
            int rowA = i - B.col0 + A.row0;
            int heightA = Math.min(blockLength, A.row1 - rowA);
            for (int j = i; j < B.col1; j += blockLength) {
                int widthB_j = Math.min(blockLength, B.col1 - j);
                int indexA = rowA * ((FMatrixD1)A.original).numCols + (j - B.col0 + A.col0) * heightA;
                int indexB_j = B.row0 * ((FMatrixD1)B.original).numCols + j * heightB;
                if (i == j) {
                    InnerRankUpdate_FDRB.multTransABlockMinus_U(((FMatrixD1)B.original).data, ((FMatrixD1)A.original).data, indexB_i, indexB_j, indexA, heightB, widthB_i, widthB_j);
                    continue;
                }
                InnerRankUpdate_FDRB.multTransABlockMinus(((FMatrixD1)B.original).data, ((FMatrixD1)A.original).data, indexB_i, indexB_j, indexA, heightB, widthB_i, widthB_j);
            }
        }
    }

    public static void symmRankNMinus_L(int blockLength, FSubmatrixD1 A, FSubmatrixD1 B) {
        int widthB = B.col1 - B.col0;
        if (widthB > blockLength) {
            throw new IllegalArgumentException("Width of B cannot be greater than the block length");
        }
        int N = B.row1 - B.row0;
        if (A.col1 - A.col0 != N) {
            throw new IllegalArgumentException("A does not have the expected number of columns based on B's height");
        }
        if (A.row1 - A.row0 != N) {
            throw new IllegalArgumentException("A does not have the expected number of rows based on B's height");
        }
        for (int i = B.row0; i < B.row1; i += blockLength) {
            int heightB_i = Math.min(blockLength, B.row1 - i);
            int indexB_i = i * ((FMatrixD1)B.original).numCols + heightB_i * B.col0;
            int rowA = i - B.row0 + A.row0;
            int heightA = Math.min(blockLength, A.row1 - rowA);
            for (int j = B.row0; j <= i; j += blockLength) {
                int widthB_j = Math.min(blockLength, B.row1 - j);
                int indexA = rowA * ((FMatrixD1)A.original).numCols + (j - B.row0 + A.col0) * heightA;
                int indexB_j = j * ((FMatrixD1)B.original).numCols + widthB_j * B.col0;
                if (i == j) {
                    InnerRankUpdate_FDRB.multTransBBlockMinus_L(((FMatrixD1)B.original).data, ((FMatrixD1)A.original).data, indexB_i, indexB_j, indexA, widthB, heightB_i, widthB_j);
                    continue;
                }
                InnerRankUpdate_FDRB.multTransBBlockMinus(((FMatrixD1)B.original).data, ((FMatrixD1)A.original).data, indexB_i, indexB_j, indexA, widthB, heightB_i, widthB_j);
            }
        }
    }

    protected static void multTransABlockMinus(float[] dataA, float[] dataC, int indexA, int indexB, int indexC, int heightA, int widthA, int widthC) {
        int rowB = indexB;
        int endLoopK = rowB + heightA * widthC;
        int startA = indexA;
        while (rowB != endLoopK) {
            int a = startA;
            int c = indexC;
            int endA = a + widthA;
            int endB = rowB + widthC;
            while (a != endA) {
                float valA = dataA[a++];
                int b = rowB;
                while (b != endB) {
                    int n = c++;
                    dataC[n] = dataC[n] - valA * dataA[b++];
                }
            }
            rowB += widthC;
            startA += widthA;
        }
    }

    protected static void multTransABlockMinus_U(float[] dataA, float[] dataC, int indexA, int indexB, int indexC, int heightA, int widthA, int widthC) {
        for (int i = 0; i < widthA; ++i) {
            for (int k = 0; k < heightA; ++k) {
                float valA = dataA[k * widthA + i + indexA];
                int b = k * widthC + indexB + i;
                int c = i * widthC + indexC + i;
                int endC = c - i + widthC;
                while (c != endC) {
                    int n = c++;
                    dataC[n] = dataC[n] - valA * dataA[b++];
                }
            }
        }
    }

    protected static void multTransBBlockMinus(float[] dataA, float[] dataC, int indexA, int indexB, int indexC, int widthA, int heightA, int widthC) {
        int rowA = indexA;
        int c = indexC;
        int i = 0;
        while (i < heightA) {
            int endA = rowA + widthA;
            int rowB = indexB;
            int endLoopJ = c + widthC;
            while (c != endLoopJ) {
                int a = rowA;
                int b = rowB;
                float sum = 0.0f;
                while (a != endA) {
                    sum += dataA[a++] * dataA[b++];
                }
                int n = c++;
                dataC[n] = dataC[n] - sum;
                rowB += widthA;
            }
            ++i;
            rowA += widthA;
        }
    }

    protected static void multTransBBlockMinus_L(float[] dataA, float[] dataC, int indexA, int indexB, int indexC, int widthA, int heightA, int widthC) {
        for (int i = 0; i < heightA; ++i) {
            int rowA = i * widthA + indexA;
            int endA = rowA + widthA;
            int rowB = indexB;
            int rowC = i * widthC + indexC;
            int j = 0;
            while (j <= i) {
                float sum = 0.0f;
                int a = rowA;
                int b = rowB;
                while (a != endA) {
                    sum += dataA[a++] * dataA[b++];
                }
                int n = rowC + j;
                dataC[n] = dataC[n] - sum;
                ++j;
                rowB += widthA;
            }
        }
    }
}

