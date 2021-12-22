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
import org.ejml.data.IGrowArray;
import org.ejml.masks.Mask;
import org.ejml.ops.DSemiRing;
import org.ejml.sparse.csc.misc.ImplCommonOpsWithSemiRing_DSCC;
import org.ejml.sparse.csc.mult.ImplMultiplicationWithSemiRing_DSCC;
import org.jetbrains.annotations.Nullable;

public class CommonOpsWithSemiRing_DSCC {
    public static DMatrixSparseCSC mult(DMatrixSparseCSC A, DMatrixSparseCSC B, @Nullable DMatrixSparseCSC output, DSemiRing semiRing) {
        return CommonOpsWithSemiRing_DSCC.mult(A, B, output, semiRing, null, null, null);
    }

    public static DMatrixSparseCSC mult(DMatrixSparseCSC A, DMatrixSparseCSC B, @Nullable DMatrixSparseCSC output, DSemiRing semiRing, @Nullable Mask mask, @Nullable IGrowArray gw, @Nullable DGrowArray gx) {
        if (A.numCols != B.numRows) {
            throw new MatrixDimensionException("Inconsistent matrix shapes. " + UtilEjml.stringShapes(A, B));
        }
        output = UtilEjml.reshapeOrDeclare(output, A, A.numRows, B.numCols);
        ImplMultiplicationWithSemiRing_DSCC.mult(A, B, output, semiRing, mask, gw, gx);
        return output;
    }

    public static DMatrixRMaj mult(DMatrixSparseCSC A, DMatrixRMaj B, @Nullable DMatrixRMaj output, DSemiRing semiRing) {
        if (A.numCols != B.numRows) {
            throw new MatrixDimensionException("Inconsistent matrix shapes. " + UtilEjml.stringShapes(A, B));
        }
        output = UtilEjml.reshapeOrDeclare(output, A.numRows, B.numCols);
        ImplMultiplicationWithSemiRing_DSCC.mult(A, B, output, semiRing);
        return output;
    }

    public static void multAdd(DMatrixSparseCSC A, DMatrixRMaj B, DMatrixRMaj output, DSemiRing semiRing) {
        if (A.numRows != output.numRows || B.numCols != output.numCols) {
            throw new IllegalArgumentException("Inconsistent matrix shapes. " + UtilEjml.stringShapes(A, B, output));
        }
        ImplMultiplicationWithSemiRing_DSCC.multAdd(A, B, output, semiRing);
    }

    public static DMatrixRMaj multTransA(DMatrixSparseCSC A, DMatrixRMaj B, @Nullable DMatrixRMaj output, DSemiRing semiRing) {
        if (A.numRows != B.numRows) {
            throw new MatrixDimensionException("Inconsistent matrix shapes. " + UtilEjml.stringShapes(A, B));
        }
        output = UtilEjml.reshapeOrDeclare(output, A.numCols, B.numCols);
        ImplMultiplicationWithSemiRing_DSCC.multTransA(A, B, output, semiRing);
        return output;
    }

    public static void multAddTransA(DMatrixSparseCSC A, DMatrixRMaj B, DMatrixRMaj output, DSemiRing semiRing) {
        if (A.numCols != output.numRows || B.numCols != output.numCols) {
            throw new IllegalArgumentException("Inconsistent matrix shapes. " + UtilEjml.stringShapes(A, B, output));
        }
        ImplMultiplicationWithSemiRing_DSCC.multAddTransA(A, B, output, semiRing);
    }

    public static DMatrixRMaj multTransB(DMatrixSparseCSC A, DMatrixRMaj B, @Nullable DMatrixRMaj output, DSemiRing semiRing) {
        if (A.numCols != B.numCols) {
            throw new MatrixDimensionException("Inconsistent matrix shapes. " + UtilEjml.stringShapes(A, B));
        }
        output = UtilEjml.reshapeOrDeclare(output, A.numRows, B.numRows);
        ImplMultiplicationWithSemiRing_DSCC.multTransB(A, B, output, semiRing);
        return output;
    }

    public static void multAddTransB(DMatrixSparseCSC A, DMatrixRMaj B, DMatrixRMaj output, DSemiRing semiRing) {
        if (A.numRows != output.numRows || B.numRows != output.numCols) {
            throw new IllegalArgumentException("Inconsistent matrix shapes. " + UtilEjml.stringShapes(A, B, output));
        }
        ImplMultiplicationWithSemiRing_DSCC.multAddTransB(A, B, output, semiRing);
    }

    public static DMatrixRMaj multTransAB(DMatrixSparseCSC A, DMatrixRMaj B, DMatrixRMaj output, DSemiRing semiRing) {
        if (A.numRows != B.numCols) {
            throw new MatrixDimensionException("Inconsistent matrix shapes. " + UtilEjml.stringShapes(A, B));
        }
        output = UtilEjml.reshapeOrDeclare(output, A.numCols, B.numRows);
        ImplMultiplicationWithSemiRing_DSCC.multTransAB(A, B, output, semiRing);
        return output;
    }

    public static void multAddTransAB(DMatrixSparseCSC A, DMatrixRMaj B, DMatrixRMaj C, DSemiRing semiRing) {
        if (A.numCols != C.numRows || B.numRows != C.numCols) {
            throw new IllegalArgumentException("Inconsistent matrix shapes. " + UtilEjml.stringShapes(A, B, C));
        }
        ImplMultiplicationWithSemiRing_DSCC.multAddTransAB(A, B, C, semiRing);
    }

    public static DMatrixSparseCSC add(double alpha, DMatrixSparseCSC A, double beta, DMatrixSparseCSC B, @Nullable DMatrixSparseCSC output, DSemiRing semiRing, @Nullable Mask mask, @Nullable IGrowArray gw, @Nullable DGrowArray gx) {
        if (A.numRows != B.numRows || A.numCols != B.numCols) {
            throw new MatrixDimensionException("Inconsistent matrix shapes. " + UtilEjml.stringShapes(A, B));
        }
        output = UtilEjml.reshapeOrDeclare(output, A, A.numRows, A.numCols);
        if (mask != null) {
            mask.compatible(output);
        }
        ImplCommonOpsWithSemiRing_DSCC.add(alpha, A, beta, B, output, semiRing, mask, gw, gx);
        return output;
    }

    public static DMatrixSparseCSC elementMult(DMatrixSparseCSC A, DMatrixSparseCSC B, @Nullable DMatrixSparseCSC output, DSemiRing semiRing, @Nullable Mask mask, @Nullable IGrowArray gw, @Nullable DGrowArray gx) {
        if (A.numCols != B.numCols || A.numRows != B.numRows) {
            throw new MatrixDimensionException("All inputs must have the same number of rows and columns. " + UtilEjml.stringShapes(A, B));
        }
        output = UtilEjml.reshapeOrDeclare(output, A, A.numRows, A.numCols);
        ImplCommonOpsWithSemiRing_DSCC.elementMult(A, B, output, semiRing, mask, gw, gx);
        return output;
    }
}

