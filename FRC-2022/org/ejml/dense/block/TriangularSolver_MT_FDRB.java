/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  org.jetbrains.annotations.Nullable
 */
package org.ejml.dense.block;

import java.util.Arrays;
import org.ejml.concurrency.EjmlConcurrency;
import org.ejml.data.FGrowArray;
import org.ejml.data.FMatrixD1;
import org.ejml.data.FSubmatrixD1;
import org.ejml.dense.block.InnerMultiplication_FDRB;
import org.ejml.dense.block.InnerTriangularSolver_FDRB;
import org.ejml.dense.block.MatrixMult_MT_FDRB;
import org.jetbrains.annotations.Nullable;
import pabeles.concurrency.GrowArray;

public class TriangularSolver_MT_FDRB {
    public static void invert(int blockLength, boolean upper, FSubmatrixD1 T, FSubmatrixD1 T_inv, @Nullable GrowArray<FGrowArray> workspace) {
        if (upper) {
            throw new IllegalArgumentException("Upper triangular matrices not supported yet");
        }
        if (T.original == T_inv.original) {
            throw new IllegalArgumentException("Same instance not allowed for concurrent");
        }
        if (workspace == null) {
            workspace = new GrowArray<FGrowArray>(FGrowArray::new);
        } else {
            workspace.reset();
        }
        if (T.row0 != T_inv.row0 || T.row1 != T_inv.row1 || T.col0 != T_inv.col0 || T.col1 != T_inv.col1) {
            throw new IllegalArgumentException("T and T_inv must be at the same elements in the matrix");
        }
        int blockSize = blockLength * blockLength;
        int M = T.row1 - T.row0;
        float[] dataT = ((FMatrixD1)T.original).data;
        float[] dataX = ((FMatrixD1)T_inv.original).data;
        int offsetT = T.row0 * ((FMatrixD1)T.original).numCols + M * T.col0;
        for (int rowT = 0; rowT < M; rowT += blockLength) {
            int _rowT = rowT;
            int heightT = Math.min(T.row1 - (rowT + T.row0), blockLength);
            int indexII = offsetT + ((FMatrixD1)T.original).numCols * (rowT + T.row0) + heightT * (rowT + T.col0);
            EjmlConcurrency.loopFor(0, rowT, blockLength, workspace, (work, colT) -> {
                float[] temp = work.reshape((int)blockSize).data;
                int widthX = Math.min(T.col1 - (colT + T.col0), blockLength);
                Arrays.fill(temp, 0.0f);
                for (int k = colT; k < _rowT; k += blockLength) {
                    int widthT = Math.min(T.col1 - (k + T.col0), blockLength);
                    int indexL = offsetT + ((FMatrixD1)T.original).numCols * (_rowT + T.row0) + heightT * (k + T.col0);
                    int indexX = offsetT + ((FMatrixD1)T.original).numCols * (k + T.row0) + widthT * (colT + T.col0);
                    InnerMultiplication_FDRB.blockMultMinus(dataT, dataX, temp, indexL, indexX, 0, heightT, widthT, widthX);
                }
                int indexX = offsetT + ((FMatrixD1)T.original).numCols * (_rowT + T.row0) + heightT * (colT + T.col0);
                InnerTriangularSolver_FDRB.solveL(dataT, temp, heightT, widthX, heightT, indexII, 0);
                System.arraycopy(temp, 0, dataX, indexX, widthX * heightT);
            });
            InnerTriangularSolver_FDRB.invertLower(dataT, dataX, heightT, indexII, indexII);
        }
    }

    public static void solve(int blockLength, boolean upper, FSubmatrixD1 T, FSubmatrixD1 B, boolean transT) {
        if (upper) {
            TriangularSolver_MT_FDRB.solveR(blockLength, T, B, transT);
        } else {
            TriangularSolver_MT_FDRB.solveL(blockLength, T, B, transT);
        }
    }

    public static void solveBlock(int blockLength, boolean upper, FSubmatrixD1 T, FSubmatrixD1 B, boolean transT, boolean transB) {
        int Trows = T.row1 - T.row0;
        if (Trows > blockLength) {
            throw new IllegalArgumentException("T can be at most the size of a block");
        }
        int blockT_rows = Math.min(blockLength, ((FMatrixD1)T.original).numRows - T.row0);
        int blockT_cols = Math.min(blockLength, ((FMatrixD1)T.original).numCols - T.col0);
        int offsetT = T.row0 * ((FMatrixD1)T.original).numCols + blockT_rows * T.col0;
        float[] dataT = ((FMatrixD1)T.original).data;
        float[] dataB = ((FMatrixD1)B.original).data;
        if (transB) {
            if (upper) {
                if (transT) {
                    throw new IllegalArgumentException("Operation not yet supported");
                }
                throw new IllegalArgumentException("Operation not yet supported");
            }
            if (transT) {
                throw new IllegalArgumentException("Operation not yet supported");
            }
            EjmlConcurrency.loopFor(B.row0, B.row1, blockLength, i -> {
                int N = Math.min(B.row1, i + blockLength) - i;
                int offsetB = i * ((FMatrixD1)B.original).numCols + N * B.col0;
                InnerTriangularSolver_FDRB.solveLTransB(dataT, dataB, blockT_rows, N, blockT_rows, offsetT, offsetB);
            });
        } else {
            if (Trows != B.row1 - B.row0) {
                throw new IllegalArgumentException("T and B must have the same number of rows.");
            }
            if (upper) {
                if (transT) {
                    EjmlConcurrency.loopFor(B.col0, B.col1, blockLength, i -> {
                        int offsetB = B.row0 * ((FMatrixD1)B.original).numCols + Trows * i;
                        int N = Math.min(B.col1, i + blockLength) - i;
                        InnerTriangularSolver_FDRB.solveTransU(dataT, dataB, Trows, N, Trows, offsetT, offsetB);
                    });
                } else {
                    EjmlConcurrency.loopFor(B.col0, B.col1, blockLength, i -> {
                        int offsetB = B.row0 * ((FMatrixD1)B.original).numCols + Trows * i;
                        int N = Math.min(B.col1, i + blockLength) - i;
                        InnerTriangularSolver_FDRB.solveU(dataT, dataB, Trows, N, Trows, offsetT, offsetB);
                    });
                }
            } else if (transT) {
                EjmlConcurrency.loopFor(B.col0, B.col1, blockLength, i -> {
                    int offsetB = B.row0 * ((FMatrixD1)B.original).numCols + Trows * i;
                    int N = Math.min(B.col1, i + blockLength) - i;
                    InnerTriangularSolver_FDRB.solveTransL(dataT, dataB, Trows, N, blockT_cols, offsetT, offsetB);
                });
            } else {
                EjmlConcurrency.loopFor(B.col0, B.col1, blockLength, i -> {
                    int offsetB = B.row0 * ((FMatrixD1)B.original).numCols + Trows * i;
                    int N = Math.min(B.col1, i + blockLength) - i;
                    InnerTriangularSolver_FDRB.solveL(dataT, dataB, Trows, N, blockT_cols, offsetT, offsetB);
                });
            }
        }
    }

    public static void solveL(int blockLength, FSubmatrixD1 L, FSubmatrixD1 B, boolean transL) {
        int stepI;
        int startI;
        FSubmatrixD1 Y = new FSubmatrixD1((FMatrixD1)B.original);
        FSubmatrixD1 Linner = new FSubmatrixD1((FMatrixD1)L.original);
        FSubmatrixD1 Binner = new FSubmatrixD1((FMatrixD1)B.original);
        int lengthL = B.row1 - B.row0;
        if (transL) {
            startI = lengthL - lengthL % blockLength;
            if (startI == lengthL && lengthL >= blockLength) {
                startI -= blockLength;
            }
            stepI = -blockLength;
        } else {
            startI = 0;
            stepI = blockLength;
        }
        int i = startI;
        while (!(transL ? i < 0 : i >= lengthL)) {
            boolean updateY;
            int widthT = Math.min(blockLength, lengthL - i);
            Linner.col0 = L.col0 + i;
            Linner.col1 = Linner.col0 + widthT;
            Linner.row0 = L.row0 + i;
            Linner.row1 = Linner.row0 + widthT;
            Binner.col0 = B.col0;
            Binner.col1 = B.col1;
            Binner.row0 = B.row0 + i;
            Binner.row1 = Binner.row0 + widthT;
            TriangularSolver_MT_FDRB.solveBlock(blockLength, false, Linner, Binner, transL, false);
            if (transL) {
                updateY = Linner.row0 > 0;
            } else {
                boolean bl = updateY = Linner.row1 < L.row1;
            }
            if (updateY) {
                if (transL) {
                    Linner.col1 = Linner.col0;
                    Linner.col0 = Linner.col1 - blockLength;
                    Linner.row1 = L.row1;
                    Binner.row1 = B.row1;
                    Y.row0 = Binner.row0 - blockLength;
                    Y.row1 = Binner.row0;
                } else {
                    Linner.row0 = Linner.row1;
                    Linner.row1 = Math.min(Linner.row0 + blockLength, L.row1);
                    Linner.col0 = L.col0;
                    Binner.row0 = B.row0;
                    Y.row0 = Binner.row1;
                    Y.row1 = Math.min(Y.row0 + blockLength, B.row1);
                }
                for (int k = B.col0; k < B.col1; k += blockLength) {
                    Binner.col0 = k;
                    Binner.col1 = Math.min(k + blockLength, B.col1);
                    Y.col0 = Binner.col0;
                    Y.col1 = Binner.col1;
                    if (transL) {
                        MatrixMult_MT_FDRB.multMinusTransA(blockLength, Linner, Binner, Y);
                        continue;
                    }
                    MatrixMult_MT_FDRB.multMinus(blockLength, Linner, Binner, Y);
                }
            }
            i += stepI;
        }
    }

    public static void solveR(int blockLength, FSubmatrixD1 R, FSubmatrixD1 B, boolean transR) {
        int stepI;
        int startI;
        int lengthR = B.row1 - B.row0;
        if (R.getCols() != lengthR) {
            throw new IllegalArgumentException("Number of columns in R must be equal to the number of rows in B");
        }
        if (R.getRows() != lengthR) {
            throw new IllegalArgumentException("Number of rows in R must be equal to the number of rows in B");
        }
        FSubmatrixD1 Y = new FSubmatrixD1((FMatrixD1)B.original);
        FSubmatrixD1 Rinner = new FSubmatrixD1((FMatrixD1)R.original);
        FSubmatrixD1 Binner = new FSubmatrixD1((FMatrixD1)B.original);
        if (transR) {
            startI = 0;
            stepI = blockLength;
        } else {
            startI = lengthR - lengthR % blockLength;
            if (startI == lengthR && lengthR >= blockLength) {
                startI -= blockLength;
            }
            stepI = -blockLength;
        }
        int i = startI;
        while (!(transR ? i >= lengthR : i < 0)) {
            boolean updateY;
            int widthT = Math.min(blockLength, lengthR - i);
            Rinner.col0 = R.col0 + i;
            Rinner.col1 = Rinner.col0 + widthT;
            Rinner.row0 = R.row0 + i;
            Rinner.row1 = Rinner.row0 + widthT;
            Binner.col0 = B.col0;
            Binner.col1 = B.col1;
            Binner.row0 = B.row0 + i;
            Binner.row1 = Binner.row0 + widthT;
            TriangularSolver_MT_FDRB.solveBlock(blockLength, true, Rinner, Binner, transR, false);
            if (transR) {
                updateY = Rinner.row1 < R.row1;
            } else {
                boolean bl = updateY = Rinner.row0 > 0;
            }
            if (updateY) {
                if (transR) {
                    Rinner.col0 = Rinner.col1;
                    Rinner.col1 = Math.min(Rinner.col0 + blockLength, R.col1);
                    Rinner.row0 = R.row0;
                    Binner.row0 = B.row0;
                    Y.row0 = Binner.row1;
                    Y.row1 = Math.min(Y.row0 + blockLength, B.row1);
                } else {
                    Rinner.row1 = Rinner.row0;
                    Rinner.row0 = Rinner.row1 - blockLength;
                    Rinner.col1 = R.col1;
                    Binner.row1 = B.row1;
                    Y.row0 = Binner.row0 - blockLength;
                    Y.row1 = Binner.row0;
                }
                for (int k = B.col0; k < B.col1; k += blockLength) {
                    Binner.col0 = k;
                    Binner.col1 = Math.min(k + blockLength, B.col1);
                    Y.col0 = Binner.col0;
                    Y.col1 = Binner.col1;
                    if (transR) {
                        MatrixMult_MT_FDRB.multMinusTransA(blockLength, Rinner, Binner, Y);
                        continue;
                    }
                    MatrixMult_MT_FDRB.multMinus(blockLength, Rinner, Binner, Y);
                }
            }
            i += stepI;
        }
    }
}

