/*
 * Decompiled with CFR 0.150.
 */
package org.ejml.sparse.csc.mult;

import java.util.Arrays;
import org.ejml.UtilEjml;
import org.ejml.concurrency.EjmlConcurrency;
import org.ejml.data.FGrowArray;
import org.ejml.data.FMatrixRMaj;
import org.ejml.data.FMatrixSparseCSC;
import org.ejml.sparse.csc.mult.ImplMultiplication_FSCC;
import org.ejml.sparse.csc.mult.Workspace_MT_FSCC;
import pabeles.concurrency.GrowArray;

public class ImplMultiplication_MT_FSCC {
    public static void mult(FMatrixSparseCSC A, FMatrixSparseCSC B, FMatrixSparseCSC C, GrowArray<Workspace_MT_FSCC> listWork) {
        EjmlConcurrency.loopBlocks(0, B.numCols, listWork, (workspace, bj0, bj1) -> {
            FMatrixSparseCSC workC = workspace.mat;
            workC.reshape(A.numRows, bj1 - bj0, bj1 - bj0);
            workC.col_idx[0] = 0;
            float[] x = UtilEjml.adjust(workspace.gx, A.numRows);
            int[] w = UtilEjml.adjust(workspace.gw, A.numRows, A.numRows);
            for (int bj = bj0; bj < bj1; ++bj) {
                int colC = bj - bj0;
                int idx0 = B.col_idx[bj];
                int idx1 = B.col_idx[bj + 1];
                workC.col_idx[colC + 1] = workC.nz_length;
                if (idx0 == idx1) continue;
                for (int bi = idx0; bi < idx1; ++bi) {
                    int rowB = B.nz_rows[bi];
                    float valB = B.nz_values[bi];
                    ImplMultiplication_FSCC.multAddColA(A, rowB, valB, workC, colC + 1, x, w);
                }
                int idxC0 = workC.col_idx[colC];
                int idxC1 = workC.col_idx[colC + 1];
                for (int i = idxC0; i < idxC1; ++i) {
                    workC.nz_values[i] = x[workC.nz_rows[i]];
                }
            }
        });
        ImplMultiplication_MT_FSCC.stitchMatrix(C, A.numRows, B.numCols, listWork);
    }

    public static void stitchMatrix(FMatrixSparseCSC out, int numRows, int numCols, GrowArray<Workspace_MT_FSCC> listWork) {
        int i;
        out.reshape(numRows, numCols);
        out.indicesSorted = false;
        out.nz_length = 0;
        for (i = 0; i < listWork.size(); ++i) {
            out.nz_length += listWork.get((int)i).mat.nz_length;
        }
        out.growMaxLength(out.nz_length, false);
        out.nz_length = 0;
        out.numCols = 0;
        out.col_idx[0] = 0;
        for (i = 0; i < listWork.size(); ++i) {
            Workspace_MT_FSCC workspace = listWork.get(i);
            System.arraycopy(workspace.mat.nz_rows, 0, out.nz_rows, out.nz_length, workspace.mat.nz_length);
            System.arraycopy(workspace.mat.nz_values, 0, out.nz_values, out.nz_length, workspace.mat.nz_length);
            for (int col = 1; col <= workspace.mat.numCols; ++col) {
                out.col_idx[++out.numCols] = out.nz_length + workspace.mat.col_idx[col];
            }
            out.nz_length += workspace.mat.nz_length;
        }
        UtilEjml.assertEq(out.numCols, numCols);
        UtilEjml.assertEq(out.col_idx[numCols], out.nz_length);
    }

    public static void mult(FMatrixSparseCSC A, FMatrixRMaj B, FMatrixRMaj C, GrowArray<FGrowArray> listWork) {
        ImplMultiplication_MT_FSCC.mult(A, B, C, false, listWork);
    }

    public static void multAdd(FMatrixSparseCSC A, FMatrixRMaj B, FMatrixRMaj C, GrowArray<FGrowArray> listWork) {
        ImplMultiplication_MT_FSCC.mult(A, B, C, true, listWork);
    }

    public static void mult(FMatrixSparseCSC A, FMatrixRMaj B, FMatrixRMaj C, boolean add, GrowArray<FGrowArray> listWork) {
        EjmlConcurrency.loopBlocks(0, B.numCols, listWork, (gwork, bj0, bj1) -> {
            float[] work = gwork.reshape((int)(A.numRows + B.numRows)).data;
            for (int bj = bj0; bj < bj1; ++bj) {
                int rowC;
                int k;
                Arrays.fill(work, 0, A.numRows, 0.0f);
                for (k = 0; k < B.numRows; ++k) {
                    work[A.numRows + k] = B.data[k * B.numCols + bj];
                }
                for (k = 0; k < A.numCols; ++k) {
                    int idx0 = A.col_idx[k];
                    int idx1 = A.col_idx[k + 1];
                    if (idx0 == idx1) continue;
                    for (int i = idx0; i < idx1; ++i) {
                        int ai;
                        int n = ai = A.nz_rows[i];
                        work[n] = work[n] + A.nz_values[i] * work[A.numRows + k];
                    }
                }
                if (add) {
                    for (rowC = 0; rowC < C.numRows; ++rowC) {
                        int n = rowC * C.numCols + bj;
                        C.data[n] = C.data[n] + work[rowC];
                    }
                    continue;
                }
                for (rowC = 0; rowC < C.numRows; ++rowC) {
                    C.data[rowC * C.numCols + bj] = work[rowC];
                }
            }
        });
    }

    public static void multTransA(FMatrixSparseCSC A, FMatrixRMaj B, FMatrixRMaj C, GrowArray<FGrowArray> listWork) {
        EjmlConcurrency.loopBlocks(0, B.numCols, listWork, (gwork, j0, j1) -> {
            float[] work = gwork.reshape((int)B.numRows).data;
            for (int j = j0; j < j1; ++j) {
                for (int k = 0; k < B.numRows; ++k) {
                    work[k] = B.data[k * B.numCols + j];
                }
                for (int i = 0; i < A.numCols; ++i) {
                    int idx0 = A.col_idx[i];
                    int idx1 = A.col_idx[i + 1];
                    float sum = 0.0f;
                    for (int indexA = idx0; indexA < idx1; ++indexA) {
                        int k = A.nz_rows[indexA];
                        sum += A.nz_values[indexA] * work[k];
                    }
                    C.data[i * C.numCols + j] = sum;
                }
            }
        });
    }

    public static void multAddTransA(FMatrixSparseCSC A, FMatrixRMaj B, FMatrixRMaj C, GrowArray<FGrowArray> listWork) {
        EjmlConcurrency.loopBlocks(0, B.numCols, listWork, (gwork, j0, j1) -> {
            float[] work = gwork.reshape((int)B.numRows).data;
            for (int j = j0; j < j1; ++j) {
                for (int k = 0; k < B.numRows; ++k) {
                    work[k] = B.data[k * B.numCols + j];
                }
                for (int i = 0; i < A.numCols; ++i) {
                    int idx0 = A.col_idx[i];
                    int idx1 = A.col_idx[i + 1];
                    float sum = 0.0f;
                    for (int indexA = idx0; indexA < idx1; ++indexA) {
                        int k = A.nz_rows[indexA];
                        sum += A.nz_values[indexA] * work[k];
                    }
                    int n = i * C.numCols + j;
                    C.data[n] = C.data[n] + sum;
                }
            }
        });
    }

    public static void multTransB(FMatrixSparseCSC A, FMatrixRMaj B, FMatrixRMaj C, GrowArray<FGrowArray> listWork) {
        ImplMultiplication_MT_FSCC.multTransB(A, B, C, false, listWork);
    }

    public static void multAddTransB(FMatrixSparseCSC A, FMatrixRMaj B, FMatrixRMaj C, GrowArray<FGrowArray> listWork) {
        ImplMultiplication_MT_FSCC.multTransB(A, B, C, true, listWork);
    }

    public static void multTransB(FMatrixSparseCSC A, FMatrixRMaj B, FMatrixRMaj C, boolean add, GrowArray<FGrowArray> listWork) {
        EjmlConcurrency.loopBlocks(0, B.numRows, listWork, (gwork, bj0, bj1) -> {
            float[] work = gwork.reshape((int)A.numRows).data;
            for (int bj = bj0; bj < bj1; ++bj) {
                int rowC;
                Arrays.fill(work, 0, A.numRows, 0.0f);
                for (int k = 0; k < A.numCols; ++k) {
                    int idx0 = A.col_idx[k];
                    int idx1 = A.col_idx[k + 1];
                    if (idx0 == idx1) continue;
                    for (int i = idx0; i < idx1; ++i) {
                        int ai;
                        int n = ai = A.nz_rows[i];
                        work[n] = work[n] + A.nz_values[i] * B.data[bj * B.numCols + k];
                    }
                }
                if (add) {
                    for (rowC = 0; rowC < C.numRows; ++rowC) {
                        int n = rowC * C.numCols + bj;
                        C.data[n] = C.data[n] + work[rowC];
                    }
                    continue;
                }
                for (rowC = 0; rowC < C.numRows; ++rowC) {
                    C.data[rowC * C.numCols + bj] = work[rowC];
                }
            }
        });
    }

    public static void multTransAB(FMatrixSparseCSC A, FMatrixRMaj B, FMatrixRMaj C) {
        EjmlConcurrency.loopFor(0, B.numRows, j -> {
            for (int i = 0; i < A.numCols; ++i) {
                int idx0 = A.col_idx[i];
                int idx1 = A.col_idx[i + 1];
                int indexRowB = j * B.numCols;
                float sum = 0.0f;
                for (int indexA = idx0; indexA < idx1; ++indexA) {
                    int k = A.nz_rows[indexA];
                    sum += A.nz_values[indexA] * B.data[indexRowB + k];
                }
                C.data[i * C.numCols + j] = sum;
            }
        });
    }

    public static void multAddTransAB(FMatrixSparseCSC A, FMatrixRMaj B, FMatrixRMaj C) {
        EjmlConcurrency.loopFor(0, B.numRows, j -> {
            for (int i = 0; i < A.numCols; ++i) {
                int idx0 = A.col_idx[i];
                int idx1 = A.col_idx[i + 1];
                int indexRowB = j * B.numCols;
                float sum = 0.0f;
                for (int indexA = idx0; indexA < idx1; ++indexA) {
                    int k = A.nz_rows[indexA];
                    sum += A.nz_values[indexA] * B.data[indexRowB + k];
                }
                int n = i * C.numCols + j;
                C.data[n] = C.data[n] + sum;
            }
        });
    }
}

