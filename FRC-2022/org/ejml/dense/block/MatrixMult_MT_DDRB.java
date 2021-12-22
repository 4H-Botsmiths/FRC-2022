/*
 * Decompiled with CFR 0.150.
 */
package org.ejml.dense.block;

import org.ejml.concurrency.EjmlConcurrency;
import org.ejml.data.DMatrixD1;
import org.ejml.data.DSubmatrixD1;
import org.ejml.dense.block.InnerMultiplication_DDRB;
import org.ejml.dense.block.MatrixOps_DDRB;

public class MatrixMult_MT_DDRB {
    public static void mult(int blockLength, DSubmatrixD1 A, DSubmatrixD1 B, DSubmatrixD1 C) {
        MatrixOps_DDRB.checkShapeMult(blockLength, A, B, C);
        EjmlConcurrency.loopFor(A.row0, A.row1, blockLength, i -> {
            int heightA = Math.min(blockLength, A.row1 - i);
            for (int j = B.col0; j < B.col1; j += blockLength) {
                int widthB = Math.min(blockLength, B.col1 - j);
                int indexC = (i - A.row0 + C.row0) * ((DMatrixD1)C.original).numCols + (j - B.col0 + C.col0) * heightA;
                for (int k = A.col0; k < A.col1; k += blockLength) {
                    int widthA = Math.min(blockLength, A.col1 - k);
                    int indexA = i * ((DMatrixD1)A.original).numCols + k * heightA;
                    int indexB = (k - A.col0 + B.row0) * ((DMatrixD1)B.original).numCols + j * widthA;
                    if (k == A.col0) {
                        InnerMultiplication_DDRB.blockMultSet(((DMatrixD1)A.original).data, ((DMatrixD1)B.original).data, ((DMatrixD1)C.original).data, indexA, indexB, indexC, heightA, widthA, widthB);
                        continue;
                    }
                    InnerMultiplication_DDRB.blockMultPlus(((DMatrixD1)A.original).data, ((DMatrixD1)B.original).data, ((DMatrixD1)C.original).data, indexA, indexB, indexC, heightA, widthA, widthB);
                }
            }
        });
    }

    public static void multPlus(int blockLength, DSubmatrixD1 A, DSubmatrixD1 B, DSubmatrixD1 C) {
        EjmlConcurrency.loopFor(A.row0, A.row1, blockLength, i -> {
            int heightA = Math.min(blockLength, A.row1 - i);
            for (int j = B.col0; j < B.col1; j += blockLength) {
                int widthB = Math.min(blockLength, B.col1 - j);
                int indexC = (i - A.row0 + C.row0) * ((DMatrixD1)C.original).numCols + (j - B.col0 + C.col0) * heightA;
                for (int k = A.col0; k < A.col1; k += blockLength) {
                    int widthA = Math.min(blockLength, A.col1 - k);
                    int indexA = i * ((DMatrixD1)A.original).numCols + k * heightA;
                    int indexB = (k - A.col0 + B.row0) * ((DMatrixD1)B.original).numCols + j * widthA;
                    InnerMultiplication_DDRB.blockMultPlus(((DMatrixD1)A.original).data, ((DMatrixD1)B.original).data, ((DMatrixD1)C.original).data, indexA, indexB, indexC, heightA, widthA, widthB);
                }
            }
        });
    }

    public static void multMinus(int blockLength, DSubmatrixD1 A, DSubmatrixD1 B, DSubmatrixD1 C) {
        EjmlConcurrency.loopFor(A.row0, A.row1, blockLength, i -> {
            int heightA = Math.min(blockLength, A.row1 - i);
            for (int j = B.col0; j < B.col1; j += blockLength) {
                int widthB = Math.min(blockLength, B.col1 - j);
                int indexC = (i - A.row0 + C.row0) * ((DMatrixD1)C.original).numCols + (j - B.col0 + C.col0) * heightA;
                for (int k = A.col0; k < A.col1; k += blockLength) {
                    int widthA = Math.min(blockLength, A.col1 - k);
                    int indexA = i * ((DMatrixD1)A.original).numCols + k * heightA;
                    int indexB = (k - A.col0 + B.row0) * ((DMatrixD1)B.original).numCols + j * widthA;
                    InnerMultiplication_DDRB.blockMultMinus(((DMatrixD1)A.original).data, ((DMatrixD1)B.original).data, ((DMatrixD1)C.original).data, indexA, indexB, indexC, heightA, widthA, widthB);
                }
            }
        });
    }

    public static void multTransA(int blockLength, DSubmatrixD1 A, DSubmatrixD1 B, DSubmatrixD1 C) {
        EjmlConcurrency.loopFor(A.col0, A.col1, blockLength, i -> {
            int widthA = Math.min(blockLength, A.col1 - i);
            for (int j = B.col0; j < B.col1; j += blockLength) {
                int widthB = Math.min(blockLength, B.col1 - j);
                int indexC = (i - A.col0 + C.row0) * ((DMatrixD1)C.original).numCols + (j - B.col0 + C.col0) * widthA;
                for (int k = A.row0; k < A.row1; k += blockLength) {
                    int heightA = Math.min(blockLength, A.row1 - k);
                    int indexA = k * ((DMatrixD1)A.original).numCols + i * heightA;
                    int indexB = (k - A.row0 + B.row0) * ((DMatrixD1)B.original).numCols + j * heightA;
                    if (k == A.row0) {
                        InnerMultiplication_DDRB.blockMultSetTransA(((DMatrixD1)A.original).data, ((DMatrixD1)B.original).data, ((DMatrixD1)C.original).data, indexA, indexB, indexC, heightA, widthA, widthB);
                        continue;
                    }
                    InnerMultiplication_DDRB.blockMultPlusTransA(((DMatrixD1)A.original).data, ((DMatrixD1)B.original).data, ((DMatrixD1)C.original).data, indexA, indexB, indexC, heightA, widthA, widthB);
                }
            }
        });
    }

    public static void multPlusTransA(int blockLength, DSubmatrixD1 A, DSubmatrixD1 B, DSubmatrixD1 C) {
        EjmlConcurrency.loopFor(A.col0, A.col1, blockLength, i -> {
            int widthA = Math.min(blockLength, A.col1 - i);
            for (int j = B.col0; j < B.col1; j += blockLength) {
                int widthB = Math.min(blockLength, B.col1 - j);
                int indexC = (i - A.col0 + C.row0) * ((DMatrixD1)C.original).numCols + (j - B.col0 + C.col0) * widthA;
                for (int k = A.row0; k < A.row1; k += blockLength) {
                    int heightA = Math.min(blockLength, A.row1 - k);
                    int indexA = k * ((DMatrixD1)A.original).numCols + i * heightA;
                    int indexB = (k - A.row0 + B.row0) * ((DMatrixD1)B.original).numCols + j * heightA;
                    InnerMultiplication_DDRB.blockMultPlusTransA(((DMatrixD1)A.original).data, ((DMatrixD1)B.original).data, ((DMatrixD1)C.original).data, indexA, indexB, indexC, heightA, widthA, widthB);
                }
            }
        });
    }

    public static void multMinusTransA(int blockLength, DSubmatrixD1 A, DSubmatrixD1 B, DSubmatrixD1 C) {
        EjmlConcurrency.loopFor(A.col0, A.col1, blockLength, i -> {
            int widthA = Math.min(blockLength, A.col1 - i);
            for (int j = B.col0; j < B.col1; j += blockLength) {
                int widthB = Math.min(blockLength, B.col1 - j);
                int indexC = (i - A.col0 + C.row0) * ((DMatrixD1)C.original).numCols + (j - B.col0 + C.col0) * widthA;
                for (int k = A.row0; k < A.row1; k += blockLength) {
                    int heightA = Math.min(blockLength, A.row1 - k);
                    int indexA = k * ((DMatrixD1)A.original).numCols + i * heightA;
                    int indexB = (k - A.row0 + B.row0) * ((DMatrixD1)B.original).numCols + j * heightA;
                    InnerMultiplication_DDRB.blockMultMinusTransA(((DMatrixD1)A.original).data, ((DMatrixD1)B.original).data, ((DMatrixD1)C.original).data, indexA, indexB, indexC, heightA, widthA, widthB);
                }
            }
        });
    }

    public static void multTransB(int blockLength, DSubmatrixD1 A, DSubmatrixD1 B, DSubmatrixD1 C) {
        EjmlConcurrency.loopFor(A.row0, A.row1, blockLength, i -> {
            int heightA = Math.min(blockLength, A.row1 - i);
            for (int j = B.row0; j < B.row1; j += blockLength) {
                int widthC = Math.min(blockLength, B.row1 - j);
                int indexC = (i - A.row0 + C.row0) * ((DMatrixD1)C.original).numCols + (j - B.row0 + C.col0) * heightA;
                for (int k = A.col0; k < A.col1; k += blockLength) {
                    int widthA = Math.min(blockLength, A.col1 - k);
                    int indexA = i * ((DMatrixD1)A.original).numCols + k * heightA;
                    int indexB = j * ((DMatrixD1)B.original).numCols + (k - A.col0 + B.col0) * widthC;
                    if (k == A.col0) {
                        InnerMultiplication_DDRB.blockMultSetTransB(((DMatrixD1)A.original).data, ((DMatrixD1)B.original).data, ((DMatrixD1)C.original).data, indexA, indexB, indexC, heightA, widthA, widthC);
                        continue;
                    }
                    InnerMultiplication_DDRB.blockMultPlusTransB(((DMatrixD1)A.original).data, ((DMatrixD1)B.original).data, ((DMatrixD1)C.original).data, indexA, indexB, indexC, heightA, widthA, widthC);
                }
            }
        });
    }
}

