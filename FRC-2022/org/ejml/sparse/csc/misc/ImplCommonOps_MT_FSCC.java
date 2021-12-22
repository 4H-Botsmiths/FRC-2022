/*
 * Decompiled with CFR 0.150.
 */
package org.ejml.sparse.csc.misc;

import org.ejml.UtilEjml;
import org.ejml.concurrency.EjmlConcurrency;
import org.ejml.data.FMatrixSparseCSC;
import org.ejml.sparse.csc.mult.ImplMultiplication_FSCC;
import org.ejml.sparse.csc.mult.ImplMultiplication_MT_FSCC;
import org.ejml.sparse.csc.mult.Workspace_MT_FSCC;
import pabeles.concurrency.GrowArray;

public class ImplCommonOps_MT_FSCC {
    public static void add(float alpha, FMatrixSparseCSC A, float beta, FMatrixSparseCSC B, FMatrixSparseCSC C, GrowArray<Workspace_MT_FSCC> listWork) {
        EjmlConcurrency.loopBlocks(0, A.numCols, listWork, (workspace, col0, col1) -> {
            FMatrixSparseCSC workC = workspace.mat;
            workC.reshape(A.numRows, col1 - col0, col1 - col0);
            workC.col_idx[0] = 0;
            float[] x = UtilEjml.adjust(workspace.gx, A.numRows);
            int[] w = UtilEjml.adjust(workspace.gw, A.numRows, A.numRows);
            for (int col = col0; col < col1; ++col) {
                int colC = col - col0;
                workC.col_idx[colC] = workC.nz_length;
                ImplMultiplication_FSCC.multAddColA(A, col, alpha, workC, colC + 1, x, w);
                ImplMultiplication_FSCC.multAddColA(B, col, beta, workC, colC + 1, x, w);
                int idxC0 = workC.col_idx[colC];
                int idxC1 = workC.col_idx[colC + 1];
                for (int i = idxC0; i < idxC1; ++i) {
                    workC.nz_values[i] = x[workC.nz_rows[i]];
                }
            }
            workC.col_idx[col1 - col0] = workC.nz_length;
        });
        ImplMultiplication_MT_FSCC.stitchMatrix(C, A.numRows, A.numCols, listWork);
    }
}

