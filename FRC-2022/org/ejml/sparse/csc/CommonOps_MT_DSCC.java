/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  org.jetbrains.annotations.Nullable
 */
package org.ejml.sparse.csc;

import org.ejml.MatrixDimensionException;
import org.ejml.UtilEjml;
import org.ejml.data.DGrowArray;
import org.ejml.data.DMatrixRMaj;
import org.ejml.data.DMatrixSparseCSC;
import org.ejml.sparse.csc.misc.ImplCommonOps_MT_DSCC;
import org.ejml.sparse.csc.mult.ImplMultiplication_MT_DSCC;
import org.ejml.sparse.csc.mult.Workspace_MT_DSCC;
import org.jetbrains.annotations.Nullable;
import pabeles.concurrency.GrowArray;

public class CommonOps_MT_DSCC {
    public static DMatrixSparseCSC mult(DMatrixSparseCSC A, DMatrixSparseCSC B, @Nullable DMatrixSparseCSC outputC) {
        return CommonOps_MT_DSCC.mult(A, B, outputC, null);
    }

    public static DMatrixSparseCSC mult(DMatrixSparseCSC A, DMatrixSparseCSC B, @Nullable DMatrixSparseCSC outputC, @Nullable GrowArray<Workspace_MT_DSCC> listWork) {
        if (A.numCols != B.numRows) {
            throw new MatrixDimensionException("Inconsistent matrix shapes. " + UtilEjml.stringShapes(A, B));
        }
        outputC = UtilEjml.reshapeOrDeclare(outputC, A, A.numRows, B.numCols);
        if (listWork == null) {
            listWork = new GrowArray<Workspace_MT_DSCC>(Workspace_MT_DSCC::new);
        }
        ImplMultiplication_MT_DSCC.mult(A, B, outputC, listWork);
        return outputC;
    }

    public static DMatrixSparseCSC add(double alpha, DMatrixSparseCSC A, double beta, DMatrixSparseCSC B, @Nullable DMatrixSparseCSC outputC, @Nullable GrowArray<Workspace_MT_DSCC> listWork) {
        if (A.numRows != B.numRows || A.numCols != B.numCols) {
            throw new MatrixDimensionException("Inconsistent matrix shapes. " + UtilEjml.stringShapes(A, B));
        }
        outputC = UtilEjml.reshapeOrDeclare(outputC, A, A.numRows, A.numCols);
        if (listWork == null) {
            listWork = new GrowArray<Workspace_MT_DSCC>(Workspace_MT_DSCC::new);
        }
        ImplCommonOps_MT_DSCC.add(alpha, A, beta, B, outputC, listWork);
        return outputC;
    }

    public static DMatrixRMaj mult(DMatrixSparseCSC A, DMatrixRMaj B, @Nullable DMatrixRMaj outputC, @Nullable GrowArray<DGrowArray> workArrays) {
        if (A.numCols != B.numRows) {
            throw new MatrixDimensionException("Inconsistent matrix shapes. " + UtilEjml.stringShapes(A, B));
        }
        outputC = UtilEjml.reshapeOrDeclare(outputC, A.numRows, B.numCols);
        if (workArrays == null) {
            workArrays = new GrowArray<DGrowArray>(DGrowArray::new);
        }
        ImplMultiplication_MT_DSCC.mult(A, B, outputC, workArrays);
        return outputC;
    }

    public static void multAdd(DMatrixSparseCSC A, DMatrixRMaj B, DMatrixRMaj outputC, @Nullable GrowArray<DGrowArray> workArrays) {
        if (A.numCols != B.numRows) {
            throw new MatrixDimensionException("Inconsistent matrix shapes. " + UtilEjml.stringShapes(A, B));
        }
        if (A.numRows != outputC.numRows || B.numCols != outputC.numCols) {
            throw new MatrixDimensionException("Inconsistent matrix shapes. " + UtilEjml.stringShapes(A, B, outputC));
        }
        if (workArrays == null) {
            workArrays = new GrowArray<DGrowArray>(DGrowArray::new);
        }
        ImplMultiplication_MT_DSCC.multAdd(A, B, outputC, workArrays);
    }

    public static DMatrixRMaj multTransA(DMatrixSparseCSC A, DMatrixRMaj B, @Nullable DMatrixRMaj outputC, @Nullable GrowArray<DGrowArray> workArray) {
        if (A.numRows != B.numRows) {
            throw new MatrixDimensionException("Inconsistent matrix shapes. " + UtilEjml.stringShapes(A, B));
        }
        outputC = UtilEjml.reshapeOrDeclare(outputC, A.numCols, B.numCols);
        if (workArray == null) {
            workArray = new GrowArray<DGrowArray>(DGrowArray::new);
        }
        ImplMultiplication_MT_DSCC.multTransA(A, B, outputC, workArray);
        return outputC;
    }

    public static void multAddTransA(DMatrixSparseCSC A, DMatrixRMaj B, DMatrixRMaj outputC, @Nullable GrowArray<DGrowArray> workArray) {
        if (A.numRows != B.numRows) {
            throw new MatrixDimensionException("Inconsistent matrix shapes. " + UtilEjml.stringShapes(A, B));
        }
        if (A.numCols != outputC.numRows || B.numCols != outputC.numCols) {
            throw new MatrixDimensionException("Inconsistent matrix shapes. " + UtilEjml.stringShapes(A, B, outputC));
        }
        if (workArray == null) {
            workArray = new GrowArray<DGrowArray>(DGrowArray::new);
        }
        ImplMultiplication_MT_DSCC.multAddTransA(A, B, outputC, workArray);
    }

    public static DMatrixRMaj multTransB(DMatrixSparseCSC A, DMatrixRMaj B, @Nullable DMatrixRMaj outputC, @Nullable GrowArray<DGrowArray> workArrays) {
        if (A.numCols != B.numCols) {
            throw new MatrixDimensionException("Inconsistent matrix shapes. " + UtilEjml.stringShapes(A, B));
        }
        outputC = UtilEjml.reshapeOrDeclare(outputC, A.numRows, B.numRows);
        if (workArrays == null) {
            workArrays = new GrowArray<DGrowArray>(DGrowArray::new);
        }
        ImplMultiplication_MT_DSCC.multTransB(A, B, outputC, workArrays);
        return outputC;
    }

    public static void multAddTransB(DMatrixSparseCSC A, DMatrixRMaj B, DMatrixRMaj outputC, @Nullable GrowArray<DGrowArray> workArrays) {
        if (A.numCols != B.numCols) {
            throw new MatrixDimensionException("Inconsistent matrix shapes. " + UtilEjml.stringShapes(A, B));
        }
        if (A.numRows != outputC.numRows || B.numRows != outputC.numCols) {
            throw new MatrixDimensionException("Inconsistent matrix shapes. " + UtilEjml.stringShapes(A, B, outputC));
        }
        if (workArrays == null) {
            workArrays = new GrowArray<DGrowArray>(DGrowArray::new);
        }
        ImplMultiplication_MT_DSCC.multAddTransB(A, B, outputC, workArrays);
    }

    public static DMatrixRMaj multTransAB(DMatrixSparseCSC A, DMatrixRMaj B, DMatrixRMaj outputC) {
        if (A.numRows != B.numCols) {
            throw new MatrixDimensionException("Inconsistent matrix shapes. " + UtilEjml.stringShapes(A, B));
        }
        outputC = UtilEjml.reshapeOrDeclare(outputC, A.numCols, B.numRows);
        ImplMultiplication_MT_DSCC.multTransAB(A, B, outputC);
        return outputC;
    }

    public static void multAddTransAB(DMatrixSparseCSC A, DMatrixRMaj B, DMatrixRMaj outputC) {
        if (A.numRows != B.numCols) {
            throw new MatrixDimensionException("Inconsistent matrix shapes. " + UtilEjml.stringShapes(A, B));
        }
        if (A.numCols != outputC.numRows || B.numRows != outputC.numCols) {
            throw new MatrixDimensionException("Inconsistent matrix shapes. " + UtilEjml.stringShapes(A, B, outputC));
        }
        ImplMultiplication_MT_DSCC.multAddTransAB(A, B, outputC);
    }
}

