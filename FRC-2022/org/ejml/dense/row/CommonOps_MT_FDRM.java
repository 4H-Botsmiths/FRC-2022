/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  org.jetbrains.annotations.Nullable
 */
package org.ejml.dense.row;

import org.ejml.EjmlParameters;
import org.ejml.UtilEjml;
import org.ejml.data.FMatrix1Row;
import org.ejml.data.FMatrixRMaj;
import org.ejml.dense.row.misc.TransposeAlgs_MT_FDRM;
import org.ejml.dense.row.mult.MatrixMatrixMult_MT_FDRM;
import org.jetbrains.annotations.Nullable;

public class CommonOps_MT_FDRM {
    public static <T extends FMatrix1Row> T mult(T a, T b, @Nullable T output) {
        output = UtilEjml.reshapeOrDeclare(output, a, a.numRows, b.numCols);
        UtilEjml.checkSameInstance(a, output);
        UtilEjml.checkSameInstance(b, output);
        MatrixMatrixMult_MT_FDRM.mult_reorder(a, b, output);
        return output;
    }

    public static <T extends FMatrix1Row> T mult(float alpha, T a, T b, @Nullable T output) {
        output = UtilEjml.reshapeOrDeclare(output, a, a.numRows, b.numCols);
        UtilEjml.checkSameInstance(a, output);
        UtilEjml.checkSameInstance(b, output);
        MatrixMatrixMult_MT_FDRM.mult_reorder(alpha, a, b, output);
        return output;
    }

    public static <T extends FMatrix1Row> T multTransA(T a, T b, @Nullable T output) {
        output = UtilEjml.reshapeOrDeclare(output, a, a.numCols, b.numCols);
        UtilEjml.checkSameInstance(a, output);
        UtilEjml.checkSameInstance(b, output);
        MatrixMatrixMult_MT_FDRM.multTransA_reorder(a, b, output);
        return output;
    }

    public static <T extends FMatrix1Row> T multTransA(float alpha, T a, T b, @Nullable T output) {
        output = UtilEjml.reshapeOrDeclare(output, a, a.numCols, b.numCols);
        UtilEjml.checkSameInstance(a, output);
        UtilEjml.checkSameInstance(b, output);
        MatrixMatrixMult_MT_FDRM.multTransA_reorder(alpha, a, b, output);
        return output;
    }

    public static <T extends FMatrix1Row> T multTransB(T a, T b, @Nullable T output) {
        output = UtilEjml.reshapeOrDeclare(output, a, a.numRows, b.numRows);
        UtilEjml.checkSameInstance(a, output);
        UtilEjml.checkSameInstance(b, output);
        MatrixMatrixMult_MT_FDRM.multTransB(a, b, output);
        return output;
    }

    public static <T extends FMatrix1Row> T multTransB(float alpha, T a, T b, @Nullable T output) {
        output = UtilEjml.reshapeOrDeclare(output, a, a.numRows, b.numRows);
        UtilEjml.checkSameInstance(a, output);
        UtilEjml.checkSameInstance(b, output);
        MatrixMatrixMult_MT_FDRM.multTransB(alpha, a, b, output);
        return output;
    }

    public static <T extends FMatrix1Row> T multTransAB(T a, T b, @Nullable T output) {
        output = UtilEjml.reshapeOrDeclare(output, a, a.numCols, b.numRows);
        UtilEjml.checkSameInstance(a, output);
        UtilEjml.checkSameInstance(b, output);
        MatrixMatrixMult_MT_FDRM.multTransAB(a, b, output);
        return output;
    }

    public static <T extends FMatrix1Row> T multTransAB(float alpha, T a, T b, @Nullable T output) {
        output = UtilEjml.reshapeOrDeclare(output, a, a.numCols, b.numRows);
        UtilEjml.checkSameInstance(a, output);
        UtilEjml.checkSameInstance(b, output);
        MatrixMatrixMult_MT_FDRM.multTransAB(alpha, a, b, output);
        return output;
    }

    public static void multAdd(FMatrix1Row a, FMatrix1Row b, FMatrix1Row c) {
        MatrixMatrixMult_MT_FDRM.multAdd_reorder(a, b, c);
    }

    public static void multAdd(float alpha, FMatrix1Row a, FMatrix1Row b, FMatrix1Row c) {
        MatrixMatrixMult_MT_FDRM.multAdd_reorder(alpha, a, b, c);
    }

    public static void multAddTransA(FMatrix1Row a, FMatrix1Row b, FMatrix1Row c) {
        MatrixMatrixMult_MT_FDRM.multAddTransA_reorder(a, b, c);
    }

    public static void multAddTransA(float alpha, FMatrix1Row a, FMatrix1Row b, FMatrix1Row c) {
        MatrixMatrixMult_MT_FDRM.multAddTransA_reorder(alpha, a, b, c);
    }

    public static void multAddTransB(FMatrix1Row a, FMatrix1Row b, FMatrix1Row c) {
        MatrixMatrixMult_MT_FDRM.multAddTransB(a, b, c);
    }

    public static void multAddTransB(float alpha, FMatrix1Row a, FMatrix1Row b, FMatrix1Row c) {
        MatrixMatrixMult_MT_FDRM.multAddTransB(alpha, a, b, c);
    }

    public static void multAddTransAB(FMatrix1Row a, FMatrix1Row b, FMatrix1Row c) {
        MatrixMatrixMult_MT_FDRM.multAddTransAB(a, b, c);
    }

    public static void multAddTransAB(float alpha, FMatrix1Row a, FMatrix1Row b, FMatrix1Row c) {
        MatrixMatrixMult_MT_FDRM.multAddTransAB(alpha, a, b, c);
    }

    public static void transpose(FMatrixRMaj mat) {
        if (mat.numCols == mat.numRows) {
            TransposeAlgs_MT_FDRM.square(mat);
        } else {
            FMatrixRMaj b = new FMatrixRMaj(mat.numCols, mat.numRows);
            CommonOps_MT_FDRM.transpose(mat, b);
            mat.setTo(b);
        }
    }

    public static FMatrixRMaj transpose(FMatrixRMaj A, @Nullable FMatrixRMaj A_tran) {
        A_tran = UtilEjml.reshapeOrDeclare(A_tran, A.numCols, A.numRows);
        if (A.numRows > EjmlParameters.TRANSPOSE_SWITCH && A.numCols > EjmlParameters.TRANSPOSE_SWITCH) {
            TransposeAlgs_MT_FDRM.block(A, A_tran, EjmlParameters.BLOCK_WIDTH);
        } else {
            TransposeAlgs_MT_FDRM.standard(A, A_tran);
        }
        return A_tran;
    }
}

