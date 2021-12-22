/*
 * Decompiled with CFR 0.150.
 */
package org.ejml.dense.block;

import org.ejml.data.FMatrixD1;
import org.ejml.data.FSubmatrixD1;
import org.ejml.dense.block.InnerMultiplication_FDRB;
import org.ejml.dense.block.MatrixOps_FDRB;

public class MatrixMult_FDRB {
    public static void mult(int blockLength, FSubmatrixD1 A, FSubmatrixD1 B, FSubmatrixD1 C) {
        MatrixOps_FDRB.checkShapeMult(blockLength, A, B, C);
        for (int i = A.row0; i < A.row1; i += blockLength) {
            int heightA = Math.min(blockLength, A.row1 - i);
            for (int j = B.col0; j < B.col1; j += blockLength) {
                int widthB = Math.min(blockLength, B.col1 - j);
                int indexC = (i - A.row0 + C.row0) * ((FMatrixD1)C.original).numCols + (j - B.col0 + C.col0) * heightA;
                for (int k = A.col0; k < A.col1; k += blockLength) {
                    int widthA = Math.min(blockLength, A.col1 - k);
                    int indexA = i * ((FMatrixD1)A.original).numCols + k * heightA;
                    int indexB = (k - A.col0 + B.row0) * ((FMatrixD1)B.original).numCols + j * widthA;
                    if (k == A.col0) {
                        InnerMultiplication_FDRB.blockMultSet(((FMatrixD1)A.original).data, ((FMatrixD1)B.original).data, ((FMatrixD1)C.original).data, indexA, indexB, indexC, heightA, widthA, widthB);
                        continue;
                    }
                    InnerMultiplication_FDRB.blockMultPlus(((FMatrixD1)A.original).data, ((FMatrixD1)B.original).data, ((FMatrixD1)C.original).data, indexA, indexB, indexC, heightA, widthA, widthB);
                }
            }
        }
    }

    public static void multPlus(int blockLength, FSubmatrixD1 A, FSubmatrixD1 B, FSubmatrixD1 C) {
        for (int i = A.row0; i < A.row1; i += blockLength) {
            int heightA = Math.min(blockLength, A.row1 - i);
            for (int j = B.col0; j < B.col1; j += blockLength) {
                int widthB = Math.min(blockLength, B.col1 - j);
                int indexC = (i - A.row0 + C.row0) * ((FMatrixD1)C.original).numCols + (j - B.col0 + C.col0) * heightA;
                for (int k = A.col0; k < A.col1; k += blockLength) {
                    int widthA = Math.min(blockLength, A.col1 - k);
                    int indexA = i * ((FMatrixD1)A.original).numCols + k * heightA;
                    int indexB = (k - A.col0 + B.row0) * ((FMatrixD1)B.original).numCols + j * widthA;
                    InnerMultiplication_FDRB.blockMultPlus(((FMatrixD1)A.original).data, ((FMatrixD1)B.original).data, ((FMatrixD1)C.original).data, indexA, indexB, indexC, heightA, widthA, widthB);
                }
            }
        }
    }

    public static void multMinus(int blockLength, FSubmatrixD1 A, FSubmatrixD1 B, FSubmatrixD1 C) {
        for (int i = A.row0; i < A.row1; i += blockLength) {
            int heightA = Math.min(blockLength, A.row1 - i);
            for (int j = B.col0; j < B.col1; j += blockLength) {
                int widthB = Math.min(blockLength, B.col1 - j);
                int indexC = (i - A.row0 + C.row0) * ((FMatrixD1)C.original).numCols + (j - B.col0 + C.col0) * heightA;
                for (int k = A.col0; k < A.col1; k += blockLength) {
                    int widthA = Math.min(blockLength, A.col1 - k);
                    int indexA = i * ((FMatrixD1)A.original).numCols + k * heightA;
                    int indexB = (k - A.col0 + B.row0) * ((FMatrixD1)B.original).numCols + j * widthA;
                    InnerMultiplication_FDRB.blockMultMinus(((FMatrixD1)A.original).data, ((FMatrixD1)B.original).data, ((FMatrixD1)C.original).data, indexA, indexB, indexC, heightA, widthA, widthB);
                }
            }
        }
    }

    public static void multTransA(int blockLength, FSubmatrixD1 A, FSubmatrixD1 B, FSubmatrixD1 C) {
        for (int i = A.col0; i < A.col1; i += blockLength) {
            int widthA = Math.min(blockLength, A.col1 - i);
            for (int j = B.col0; j < B.col1; j += blockLength) {
                int widthB = Math.min(blockLength, B.col1 - j);
                int indexC = (i - A.col0 + C.row0) * ((FMatrixD1)C.original).numCols + (j - B.col0 + C.col0) * widthA;
                for (int k = A.row0; k < A.row1; k += blockLength) {
                    int heightA = Math.min(blockLength, A.row1 - k);
                    int indexA = k * ((FMatrixD1)A.original).numCols + i * heightA;
                    int indexB = (k - A.row0 + B.row0) * ((FMatrixD1)B.original).numCols + j * heightA;
                    if (k == A.row0) {
                        InnerMultiplication_FDRB.blockMultSetTransA(((FMatrixD1)A.original).data, ((FMatrixD1)B.original).data, ((FMatrixD1)C.original).data, indexA, indexB, indexC, heightA, widthA, widthB);
                        continue;
                    }
                    InnerMultiplication_FDRB.blockMultPlusTransA(((FMatrixD1)A.original).data, ((FMatrixD1)B.original).data, ((FMatrixD1)C.original).data, indexA, indexB, indexC, heightA, widthA, widthB);
                }
            }
        }
    }

    public static void multPlusTransA(int blockLength, FSubmatrixD1 A, FSubmatrixD1 B, FSubmatrixD1 C) {
        for (int i = A.col0; i < A.col1; i += blockLength) {
            int widthA = Math.min(blockLength, A.col1 - i);
            for (int j = B.col0; j < B.col1; j += blockLength) {
                int widthB = Math.min(blockLength, B.col1 - j);
                int indexC = (i - A.col0 + C.row0) * ((FMatrixD1)C.original).numCols + (j - B.col0 + C.col0) * widthA;
                for (int k = A.row0; k < A.row1; k += blockLength) {
                    int heightA = Math.min(blockLength, A.row1 - k);
                    int indexA = k * ((FMatrixD1)A.original).numCols + i * heightA;
                    int indexB = (k - A.row0 + B.row0) * ((FMatrixD1)B.original).numCols + j * heightA;
                    InnerMultiplication_FDRB.blockMultPlusTransA(((FMatrixD1)A.original).data, ((FMatrixD1)B.original).data, ((FMatrixD1)C.original).data, indexA, indexB, indexC, heightA, widthA, widthB);
                }
            }
        }
    }

    public static void multMinusTransA(int blockLength, FSubmatrixD1 A, FSubmatrixD1 B, FSubmatrixD1 C) {
        for (int i = A.col0; i < A.col1; i += blockLength) {
            int widthA = Math.min(blockLength, A.col1 - i);
            for (int j = B.col0; j < B.col1; j += blockLength) {
                int widthB = Math.min(blockLength, B.col1 - j);
                int indexC = (i - A.col0 + C.row0) * ((FMatrixD1)C.original).numCols + (j - B.col0 + C.col0) * widthA;
                for (int k = A.row0; k < A.row1; k += blockLength) {
                    int heightA = Math.min(blockLength, A.row1 - k);
                    int indexA = k * ((FMatrixD1)A.original).numCols + i * heightA;
                    int indexB = (k - A.row0 + B.row0) * ((FMatrixD1)B.original).numCols + j * heightA;
                    InnerMultiplication_FDRB.blockMultMinusTransA(((FMatrixD1)A.original).data, ((FMatrixD1)B.original).data, ((FMatrixD1)C.original).data, indexA, indexB, indexC, heightA, widthA, widthB);
                }
            }
        }
    }

    public static void multTransB(int blockLength, FSubmatrixD1 A, FSubmatrixD1 B, FSubmatrixD1 C) {
        for (int i = A.row0; i < A.row1; i += blockLength) {
            int heightA = Math.min(blockLength, A.row1 - i);
            for (int j = B.row0; j < B.row1; j += blockLength) {
                int widthC = Math.min(blockLength, B.row1 - j);
                int indexC = (i - A.row0 + C.row0) * ((FMatrixD1)C.original).numCols + (j - B.row0 + C.col0) * heightA;
                for (int k = A.col0; k < A.col1; k += blockLength) {
                    int widthA = Math.min(blockLength, A.col1 - k);
                    int indexA = i * ((FMatrixD1)A.original).numCols + k * heightA;
                    int indexB = j * ((FMatrixD1)B.original).numCols + (k - A.col0 + B.col0) * widthC;
                    if (k == A.col0) {
                        InnerMultiplication_FDRB.blockMultSetTransB(((FMatrixD1)A.original).data, ((FMatrixD1)B.original).data, ((FMatrixD1)C.original).data, indexA, indexB, indexC, heightA, widthA, widthC);
                        continue;
                    }
                    InnerMultiplication_FDRB.blockMultPlusTransB(((FMatrixD1)A.original).data, ((FMatrixD1)B.original).data, ((FMatrixD1)C.original).data, indexA, indexB, indexC, heightA, widthA, widthC);
                }
            }
        }
    }
}

