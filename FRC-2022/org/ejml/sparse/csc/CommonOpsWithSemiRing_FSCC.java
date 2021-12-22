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
import org.ejml.data.IGrowArray;
import org.ejml.masks.Mask;
import org.ejml.ops.FSemiRing;
import org.ejml.sparse.csc.misc.ImplCommonOpsWithSemiRing_FSCC;
import org.ejml.sparse.csc.mult.ImplMultiplicationWithSemiRing_FSCC;
import org.jetbrains.annotations.Nullable;

public class CommonOpsWithSemiRing_FSCC {
    public static FMatrixSparseCSC mult(FMatrixSparseCSC A, FMatrixSparseCSC B, @Nullable FMatrixSparseCSC output, FSemiRing semiRing) {
        return CommonOpsWithSemiRing_FSCC.mult(A, B, output, semiRing, null, null, null);
    }

    public static FMatrixSparseCSC mult(FMatrixSparseCSC A, FMatrixSparseCSC B, @Nullable FMatrixSparseCSC output, FSemiRing semiRing, @Nullable Mask mask, @Nullable IGrowArray gw, @Nullable FGrowArray gx) {
        if (A.numCols != B.numRows) {
            throw new MatrixDimensionException("Inconsistent matrix shapes. " + UtilEjml.stringShapes(A, B));
        }
        output = UtilEjml.reshapeOrDeclare(output, A, A.numRows, B.numCols);
        ImplMultiplicationWithSemiRing_FSCC.mult(A, B, output, semiRing, mask, gw, gx);
        return output;
    }

    public static FMatrixRMaj mult(FMatrixSparseCSC A, FMatrixRMaj B, @Nullable FMatrixRMaj output, FSemiRing semiRing) {
        if (A.numCols != B.numRows) {
            throw new MatrixDimensionException("Inconsistent matrix shapes. " + UtilEjml.stringShapes(A, B));
        }
        output = UtilEjml.reshapeOrDeclare(output, A.numRows, B.numCols);
        ImplMultiplicationWithSemiRing_FSCC.mult(A, B, output, semiRing);
        return output;
    }

    public static void multAdd(FMatrixSparseCSC A, FMatrixRMaj B, FMatrixRMaj output, FSemiRing semiRing) {
        if (A.numRows != output.numRows || B.numCols != output.numCols) {
            throw new IllegalArgumentException("Inconsistent matrix shapes. " + UtilEjml.stringShapes(A, B, output));
        }
        ImplMultiplicationWithSemiRing_FSCC.multAdd(A, B, output, semiRing);
    }

    public static FMatrixRMaj multTransA(FMatrixSparseCSC A, FMatrixRMaj B, @Nullable FMatrixRMaj output, FSemiRing semiRing) {
        if (A.numRows != B.numRows) {
            throw new MatrixDimensionException("Inconsistent matrix shapes. " + UtilEjml.stringShapes(A, B));
        }
        output = UtilEjml.reshapeOrDeclare(output, A.numCols, B.numCols);
        ImplMultiplicationWithSemiRing_FSCC.multTransA(A, B, output, semiRing);
        return output;
    }

    public static void multAddTransA(FMatrixSparseCSC A, FMatrixRMaj B, FMatrixRMaj output, FSemiRing semiRing) {
        if (A.numCols != output.numRows || B.numCols != output.numCols) {
            throw new IllegalArgumentException("Inconsistent matrix shapes. " + UtilEjml.stringShapes(A, B, output));
        }
        ImplMultiplicationWithSemiRing_FSCC.multAddTransA(A, B, output, semiRing);
    }

    public static FMatrixRMaj multTransB(FMatrixSparseCSC A, FMatrixRMaj B, @Nullable FMatrixRMaj output, FSemiRing semiRing) {
        if (A.numCols != B.numCols) {
            throw new MatrixDimensionException("Inconsistent matrix shapes. " + UtilEjml.stringShapes(A, B));
        }
        output = UtilEjml.reshapeOrDeclare(output, A.numRows, B.numRows);
        ImplMultiplicationWithSemiRing_FSCC.multTransB(A, B, output, semiRing);
        return output;
    }

    public static void multAddTransB(FMatrixSparseCSC A, FMatrixRMaj B, FMatrixRMaj output, FSemiRing semiRing) {
        if (A.numRows != output.numRows || B.numRows != output.numCols) {
            throw new IllegalArgumentException("Inconsistent matrix shapes. " + UtilEjml.stringShapes(A, B, output));
        }
        ImplMultiplicationWithSemiRing_FSCC.multAddTransB(A, B, output, semiRing);
    }

    public static FMatrixRMaj multTransAB(FMatrixSparseCSC A, FMatrixRMaj B, FMatrixRMaj output, FSemiRing semiRing) {
        if (A.numRows != B.numCols) {
            throw new MatrixDimensionException("Inconsistent matrix shapes. " + UtilEjml.stringShapes(A, B));
        }
        output = UtilEjml.reshapeOrDeclare(output, A.numCols, B.numRows);
        ImplMultiplicationWithSemiRing_FSCC.multTransAB(A, B, output, semiRing);
        return output;
    }

    public static void multAddTransAB(FMatrixSparseCSC A, FMatrixRMaj B, FMatrixRMaj C, FSemiRing semiRing) {
        if (A.numCols != C.numRows || B.numRows != C.numCols) {
            throw new IllegalArgumentException("Inconsistent matrix shapes. " + UtilEjml.stringShapes(A, B, C));
        }
        ImplMultiplicationWithSemiRing_FSCC.multAddTransAB(A, B, C, semiRing);
    }

    public static FMatrixSparseCSC add(float alpha, FMatrixSparseCSC A, float beta, FMatrixSparseCSC B, @Nullable FMatrixSparseCSC output, FSemiRing semiRing, @Nullable Mask mask, @Nullable IGrowArray gw, @Nullable FGrowArray gx) {
        if (A.numRows != B.numRows || A.numCols != B.numCols) {
            throw new MatrixDimensionException("Inconsistent matrix shapes. " + UtilEjml.stringShapes(A, B));
        }
        output = UtilEjml.reshapeOrDeclare(output, A, A.numRows, A.numCols);
        if (mask != null) {
            mask.compatible(output);
        }
        ImplCommonOpsWithSemiRing_FSCC.add(alpha, A, beta, B, output, semiRing, mask, gw, gx);
        return output;
    }

    public static FMatrixSparseCSC elementMult(FMatrixSparseCSC A, FMatrixSparseCSC B, @Nullable FMatrixSparseCSC output, FSemiRing semiRing, @Nullable Mask mask, @Nullable IGrowArray gw, @Nullable FGrowArray gx) {
        if (A.numCols != B.numCols || A.numRows != B.numRows) {
            throw new MatrixDimensionException("All inputs must have the same number of rows and columns. " + UtilEjml.stringShapes(A, B));
        }
        output = UtilEjml.reshapeOrDeclare(output, A, A.numRows, A.numCols);
        ImplCommonOpsWithSemiRing_FSCC.elementMult(A, B, output, semiRing, mask, gw, gx);
        return output;
    }
}

