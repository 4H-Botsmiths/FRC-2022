/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  org.jetbrains.annotations.Nullable
 */
package org.ejml.dense.row;

import org.ejml.EjmlParameters;
import org.ejml.UtilEjml;
import org.ejml.data.DMatrix1Row;
import org.ejml.data.DMatrixRMaj;
import org.ejml.dense.row.misc.TransposeAlgs_MT_DDRM;
import org.ejml.dense.row.mult.MatrixMatrixMult_MT_DDRM;
import org.jetbrains.annotations.Nullable;

public class CommonOps_MT_DDRM {
    public static <T extends DMatrix1Row> T mult(T a, T b, @Nullable T output) {
        output = UtilEjml.reshapeOrDeclare(output, a, a.numRows, b.numCols);
        UtilEjml.checkSameInstance(a, output);
        UtilEjml.checkSameInstance(b, output);
        MatrixMatrixMult_MT_DDRM.mult_reorder(a, b, output);
        return output;
    }

    public static <T extends DMatrix1Row> T mult(double alpha, T a, T b, @Nullable T output) {
        output = UtilEjml.reshapeOrDeclare(output, a, a.numRows, b.numCols);
        UtilEjml.checkSameInstance(a, output);
        UtilEjml.checkSameInstance(b, output);
        MatrixMatrixMult_MT_DDRM.mult_reorder(alpha, a, b, output);
        return output;
    }

    public static <T extends DMatrix1Row> T multTransA(T a, T b, @Nullable T output) {
        output = UtilEjml.reshapeOrDeclare(output, a, a.numCols, b.numCols);
        UtilEjml.checkSameInstance(a, output);
        UtilEjml.checkSameInstance(b, output);
        MatrixMatrixMult_MT_DDRM.multTransA_reorder(a, b, output);
        return output;
    }

    public static <T extends DMatrix1Row> T multTransA(double alpha, T a, T b, @Nullable T output) {
        output = UtilEjml.reshapeOrDeclare(output, a, a.numCols, b.numCols);
        UtilEjml.checkSameInstance(a, output);
        UtilEjml.checkSameInstance(b, output);
        MatrixMatrixMult_MT_DDRM.multTransA_reorder(alpha, a, b, output);
        return output;
    }

    public static <T extends DMatrix1Row> T multTransB(T a, T b, @Nullable T output) {
        output = UtilEjml.reshapeOrDeclare(output, a, a.numRows, b.numRows);
        UtilEjml.checkSameInstance(a, output);
        UtilEjml.checkSameInstance(b, output);
        MatrixMatrixMult_MT_DDRM.multTransB(a, b, output);
        return output;
    }

    public static <T extends DMatrix1Row> T multTransB(double alpha, T a, T b, @Nullable T output) {
        output = UtilEjml.reshapeOrDeclare(output, a, a.numRows, b.numRows);
        UtilEjml.checkSameInstance(a, output);
        UtilEjml.checkSameInstance(b, output);
        MatrixMatrixMult_MT_DDRM.multTransB(alpha, a, b, output);
        return output;
    }

    public static <T extends DMatrix1Row> T multTransAB(T a, T b, @Nullable T output) {
        output = UtilEjml.reshapeOrDeclare(output, a, a.numCols, b.numRows);
        UtilEjml.checkSameInstance(a, output);
        UtilEjml.checkSameInstance(b, output);
        MatrixMatrixMult_MT_DDRM.multTransAB(a, b, output);
        return output;
    }

    public static <T extends DMatrix1Row> T multTransAB(double alpha, T a, T b, @Nullable T output) {
        output = UtilEjml.reshapeOrDeclare(output, a, a.numCols, b.numRows);
        UtilEjml.checkSameInstance(a, output);
        UtilEjml.checkSameInstance(b, output);
        MatrixMatrixMult_MT_DDRM.multTransAB(alpha, a, b, output);
        return output;
    }

    public static void multAdd(DMatrix1Row a, DMatrix1Row b, DMatrix1Row c) {
        MatrixMatrixMult_MT_DDRM.multAdd_reorder(a, b, c);
    }

    public static void multAdd(double alpha, DMatrix1Row a, DMatrix1Row b, DMatrix1Row c) {
        MatrixMatrixMult_MT_DDRM.multAdd_reorder(alpha, a, b, c);
    }

    public static void multAddTransA(DMatrix1Row a, DMatrix1Row b, DMatrix1Row c) {
        MatrixMatrixMult_MT_DDRM.multAddTransA_reorder(a, b, c);
    }

    public static void multAddTransA(double alpha, DMatrix1Row a, DMatrix1Row b, DMatrix1Row c) {
        MatrixMatrixMult_MT_DDRM.multAddTransA_reorder(alpha, a, b, c);
    }

    public static void multAddTransB(DMatrix1Row a, DMatrix1Row b, DMatrix1Row c) {
        MatrixMatrixMult_MT_DDRM.multAddTransB(a, b, c);
    }

    public static void multAddTransB(double alpha, DMatrix1Row a, DMatrix1Row b, DMatrix1Row c) {
        MatrixMatrixMult_MT_DDRM.multAddTransB(alpha, a, b, c);
    }

    public static void multAddTransAB(DMatrix1Row a, DMatrix1Row b, DMatrix1Row c) {
        MatrixMatrixMult_MT_DDRM.multAddTransAB(a, b, c);
    }

    public static void multAddTransAB(double alpha, DMatrix1Row a, DMatrix1Row b, DMatrix1Row c) {
        MatrixMatrixMult_MT_DDRM.multAddTransAB(alpha, a, b, c);
    }

    public static void transpose(DMatrixRMaj mat) {
        if (mat.numCols == mat.numRows) {
            TransposeAlgs_MT_DDRM.square(mat);
        } else {
            DMatrixRMaj b = new DMatrixRMaj(mat.numCols, mat.numRows);
            CommonOps_MT_DDRM.transpose(mat, b);
            mat.setTo(b);
        }
    }

    public static DMatrixRMaj transpose(DMatrixRMaj A, @Nullable DMatrixRMaj A_tran) {
        A_tran = UtilEjml.reshapeOrDeclare(A_tran, A.numCols, A.numRows);
        if (A.numRows > EjmlParameters.TRANSPOSE_SWITCH && A.numCols > EjmlParameters.TRANSPOSE_SWITCH) {
            TransposeAlgs_MT_DDRM.block(A, A_tran, EjmlParameters.BLOCK_WIDTH);
        } else {
            TransposeAlgs_MT_DDRM.standard(A, A_tran);
        }
        return A_tran;
    }
}

