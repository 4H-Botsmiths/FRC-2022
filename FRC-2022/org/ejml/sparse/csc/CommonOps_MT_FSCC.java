/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  org.jetbrains.annotations.Nullable
 */
package org.ejml.sparse.csc;

import org.ejml.MatrixDimensionException;
import org.ejml.UtilEjml;
import org.ejml.data.FGrowArray;
import org.ejml.data.FMatrixRMaj;
import org.ejml.data.FMatrixSparseCSC;
import org.ejml.sparse.csc.misc.ImplCommonOps_MT_FSCC;
import org.ejml.sparse.csc.mult.ImplMultiplication_MT_FSCC;
import org.ejml.sparse.csc.mult.Workspace_MT_FSCC;
import org.jetbrains.annotations.Nullable;
import pabeles.concurrency.GrowArray;

public class CommonOps_MT_FSCC {
    public static FMatrixSparseCSC mult(FMatrixSparseCSC A, FMatrixSparseCSC B, @Nullable FMatrixSparseCSC outputC) {
        return CommonOps_MT_FSCC.mult(A, B, outputC, null);
    }

    public static FMatrixSparseCSC mult(FMatrixSparseCSC A, FMatrixSparseCSC B, @Nullable FMatrixSparseCSC outputC, @Nullable GrowArray<Workspace_MT_FSCC> listWork) {
        if (A.numCols != B.numRows) {
            throw new MatrixDimensionException("Inconsistent matrix shapes. " + UtilEjml.stringShapes(A, B));
        }
        outputC = UtilEjml.reshapeOrDeclare(outputC, A, A.numRows, B.numCols);
        if (listWork == null) {
            listWork = new GrowArray<Workspace_MT_FSCC>(Workspace_MT_FSCC::new);
        }
        ImplMultiplication_MT_FSCC.mult(A, B, outputC, listWork);
        return outputC;
    }

    public static FMatrixSparseCSC add(float alpha, FMatrixSparseCSC A, float beta, FMatrixSparseCSC B, @Nullable FMatrixSparseCSC outputC, @Nullable GrowArray<Workspace_MT_FSCC> listWork) {
        if (A.numRows != B.numRows || A.numCols != B.numCols) {
            throw new MatrixDimensionException("Inconsistent matrix shapes. " + UtilEjml.stringShapes(A, B));
        }
        outputC = UtilEjml.reshapeOrDeclare(outputC, A, A.numRows, A.numCols);
        if (listWork == null) {
            listWork = new GrowArray<Workspace_MT_FSCC>(Workspace_MT_FSCC::new);
        }
        ImplCommonOps_MT_FSCC.add(alpha, A, beta, B, outputC, listWork);
        return outputC;
    }

    public static FMatrixRMaj mult(FMatrixSparseCSC A, FMatrixRMaj B, @Nullable FMatrixRMaj outputC, @Nullable GrowArray<FGrowArray> workArrays) {
        if (A.numCols != B.numRows) {
            throw new MatrixDimensionException("Inconsistent matrix shapes. " + UtilEjml.stringShapes(A, B));
        }
        outputC = UtilEjml.reshapeOrDeclare(outputC, A.numRows, B.numCols);
        if (workArrays == null) {
            workArrays = new GrowArray<FGrowArray>(FGrowArray::new);
        }
        ImplMultiplication_MT_FSCC.mult(A, B, outputC, workArrays);
        return outputC;
    }

    public static void multAdd(FMatrixSparseCSC A, FMatrixRMaj B, FMatrixRMaj outputC, @Nullable GrowArray<FGrowArray> workArrays) {
        if (A.numCols != B.numRows) {
            throw new MatrixDimensionException("Inconsistent matrix shapes. " + UtilEjml.stringShapes(A, B));
        }
        if (A.numRows != outputC.numRows || B.numCols != outputC.numCols) {
            throw new MatrixDimensionException("Inconsistent matrix shapes. " + UtilEjml.stringShapes(A, B, outputC));
        }
        if (workArrays == null) {
            workArrays = new GrowArray<FGrowArray>(FGrowArray::new);
        }
        ImplMultiplication_MT_FSCC.multAdd(A, B, outputC, workArrays);
    }

    public static FMatrixRMaj multTransA(FMatrixSparseCSC A, FMatrixRMaj B, @Nullable FMatrixRMaj outputC, @Nullable GrowArray<FGrowArray> workArray) {
        if (A.numRows != B.numRows) {
            throw new MatrixDimensionException("Inconsistent matrix shapes. " + UtilEjml.stringShapes(A, B));
        }
        outputC = UtilEjml.reshapeOrDeclare(outputC, A.numCols, B.numCols);
        if (workArray == null) {
            workArray = new GrowArray<FGrowArray>(FGrowArray::new);
        }
        ImplMultiplication_MT_FSCC.multTransA(A, B, outputC, workArray);
        return outputC;
    }

    public static void multAddTransA(FMatrixSparseCSC A, FMatrixRMaj B, FMatrixRMaj outputC, @Nullable GrowArray<FGrowArray> workArray) {
        if (A.numRows != B.numRows) {
            throw new MatrixDimensionException("Inconsistent matrix shapes. " + UtilEjml.stringShapes(A, B));
        }
        if (A.numCols != outputC.numRows || B.numCols != outputC.numCols) {
            throw new MatrixDimensionException("Inconsistent matrix shapes. " + UtilEjml.stringShapes(A, B, outputC));
        }
        if (workArray == null) {
            workArray = new GrowArray<FGrowArray>(FGrowArray::new);
        }
        ImplMultiplication_MT_FSCC.multAddTransA(A, B, outputC, workArray);
    }

    public static FMatrixRMaj multTransB(FMatrixSparseCSC A, FMatrixRMaj B, @Nullable FMatrixRMaj outputC, @Nullable GrowArray<FGrowArray> workArrays) {
        if (A.numCols != B.numCols) {
            throw new MatrixDimensionException("Inconsistent matrix shapes. " + UtilEjml.stringShapes(A, B));
        }
        outputC = UtilEjml.reshapeOrDeclare(outputC, A.numRows, B.numRows);
        if (workArrays == null) {
            workArrays = new GrowArray<FGrowArray>(FGrowArray::new);
        }
        ImplMultiplication_MT_FSCC.multTransB(A, B, outputC, workArrays);
        return outputC;
    }

    public static void multAddTransB(FMatrixSparseCSC A, FMatrixRMaj B, FMatrixRMaj outputC, @Nullable GrowArray<FGrowArray> workArrays) {
        if (A.numCols != B.numCols) {
            throw new MatrixDimensionException("Inconsistent matrix shapes. " + UtilEjml.stringShapes(A, B));
        }
        if (A.numRows != outputC.numRows || B.numRows != outputC.numCols) {
            throw new MatrixDimensionException("Inconsistent matrix shapes. " + UtilEjml.stringShapes(A, B, outputC));
        }
        if (workArrays == null) {
            workArrays = new GrowArray<FGrowArray>(FGrowArray::new);
        }
        ImplMultiplication_MT_FSCC.multAddTransB(A, B, outputC, workArrays);
    }

    public static FMatrixRMaj multTransAB(FMatrixSparseCSC A, FMatrixRMaj B, FMatrixRMaj outputC) {
        if (A.numRows != B.numCols) {
            throw new MatrixDimensionException("Inconsistent matrix shapes. " + UtilEjml.stringShapes(A, B));
        }
        outputC = UtilEjml.reshapeOrDeclare(outputC, A.numCols, B.numRows);
        ImplMultiplication_MT_FSCC.multTransAB(A, B, outputC);
        return outputC;
    }

    public static void multAddTransAB(FMatrixSparseCSC A, FMatrixRMaj B, FMatrixRMaj outputC) {
        if (A.numRows != B.numCols) {
            throw new MatrixDimensionException("Inconsistent matrix shapes. " + UtilEjml.stringShapes(A, B));
        }
        if (A.numCols != outputC.numRows || B.numRows != outputC.numCols) {
            throw new MatrixDimensionException("Inconsistent matrix shapes. " + UtilEjml.stringShapes(A, B, outputC));
        }
        ImplMultiplication_MT_FSCC.multAddTransAB(A, B, outputC);
    }
}

