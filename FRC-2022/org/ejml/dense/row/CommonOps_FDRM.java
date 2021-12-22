/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  org.jetbrains.annotations.Nullable
 */
package org.ejml.dense.row;

import java.util.Arrays;
import org.ejml.EjmlParameters;
import org.ejml.LinearSolverSafe;
import org.ejml.MatrixDimensionException;
import org.ejml.UtilEjml;
import org.ejml.data.BMatrixRMaj;
import org.ejml.data.ElementLocation;
import org.ejml.data.FMatrix;
import org.ejml.data.FMatrix1Row;
import org.ejml.data.FMatrixD1;
import org.ejml.data.FMatrixRMaj;
import org.ejml.data.Matrix;
import org.ejml.data.ReshapeMatrix;
import org.ejml.dense.row.MatrixFeatures_FDRM;
import org.ejml.dense.row.SpecializedOps_FDRM;
import org.ejml.dense.row.decomposition.TriangularSolver_FDRM;
import org.ejml.dense.row.decomposition.lu.LUDecompositionAlt_FDRM;
import org.ejml.dense.row.factory.LinearSolverFactory_FDRM;
import org.ejml.dense.row.linsol.chol.LinearSolverChol_FDRM;
import org.ejml.dense.row.linsol.lu.LinearSolverLu_FDRM;
import org.ejml.dense.row.misc.ImplCommonOps_FDMA;
import org.ejml.dense.row.misc.ImplCommonOps_FDRM;
import org.ejml.dense.row.misc.RrefGaussJordanRowPivot_FDRM;
import org.ejml.dense.row.misc.TransposeAlgs_FDRM;
import org.ejml.dense.row.misc.UnrolledCholesky_FDRM;
import org.ejml.dense.row.misc.UnrolledDeterminantFromMinor_FDRM;
import org.ejml.dense.row.misc.UnrolledInverseFromMinor_FDRM;
import org.ejml.dense.row.mult.MatrixMatrixMult_FDRM;
import org.ejml.dense.row.mult.MatrixMultProduct_FDRM;
import org.ejml.dense.row.mult.MatrixVectorMult_FDRM;
import org.ejml.dense.row.mult.VectorVectorMult_FDRM;
import org.ejml.interfaces.linsol.LinearSolverDense;
import org.ejml.ops.FOperatorUnary;
import org.jetbrains.annotations.Nullable;

public class CommonOps_FDRM {
    public static <T extends FMatrix1Row> T mult(T a, T b, @Nullable T output) {
        output = UtilEjml.reshapeOrDeclare(output, a, a.numRows, b.numCols);
        UtilEjml.checkSameInstance(a, output);
        UtilEjml.checkSameInstance(b, output);
        if (b.numCols == 1) {
            MatrixVectorMult_FDRM.mult(a, b, output);
        } else if (b.numCols >= EjmlParameters.MULT_COLUMN_SWITCH) {
            MatrixMatrixMult_FDRM.mult_reorder(a, b, output);
        } else {
            MatrixMatrixMult_FDRM.mult_small(a, b, output);
        }
        return output;
    }

    public static <T extends FMatrix1Row> T mult(float alpha, T a, T b, @Nullable T output) {
        output = UtilEjml.reshapeOrDeclare(output, a, a.numRows, b.numCols);
        UtilEjml.checkSameInstance(a, output);
        UtilEjml.checkSameInstance(b, output);
        if (b.numCols >= EjmlParameters.MULT_COLUMN_SWITCH) {
            MatrixMatrixMult_FDRM.mult_reorder(alpha, a, b, output);
        } else {
            MatrixMatrixMult_FDRM.mult_small(alpha, a, b, output);
        }
        return output;
    }

    public static <T extends FMatrix1Row> T multTransA(T a, T b, @Nullable T output) {
        output = UtilEjml.reshapeOrDeclare(output, a, a.numCols, b.numCols);
        UtilEjml.checkSameInstance(a, output);
        UtilEjml.checkSameInstance(b, output);
        if (b.numCols == 1) {
            if (a.numCols >= EjmlParameters.MULT_COLUMN_SWITCH) {
                MatrixVectorMult_FDRM.multTransA_reorder(a, b, output);
            } else {
                MatrixVectorMult_FDRM.multTransA_small(a, b, output);
            }
        } else if (a.numCols >= EjmlParameters.MULT_COLUMN_SWITCH || b.numCols >= EjmlParameters.MULT_COLUMN_SWITCH) {
            MatrixMatrixMult_FDRM.multTransA_reorder(a, b, output);
        } else {
            MatrixMatrixMult_FDRM.multTransA_small(a, b, output);
        }
        return output;
    }

    public static <T extends FMatrix1Row> T multTransA(float alpha, T a, T b, @Nullable T output) {
        output = UtilEjml.reshapeOrDeclare(output, a, a.numCols, b.numCols);
        UtilEjml.checkSameInstance(a, output);
        UtilEjml.checkSameInstance(b, output);
        if (a.numCols >= EjmlParameters.MULT_COLUMN_SWITCH || b.numCols >= EjmlParameters.MULT_COLUMN_SWITCH) {
            MatrixMatrixMult_FDRM.multTransA_reorder(alpha, a, b, output);
        } else {
            MatrixMatrixMult_FDRM.multTransA_small(alpha, a, b, output);
        }
        return output;
    }

    public static <T extends FMatrix1Row> T multTransB(T a, T b, @Nullable T output) {
        output = UtilEjml.reshapeOrDeclare(output, a, a.numRows, b.numRows);
        UtilEjml.checkSameInstance(a, output);
        UtilEjml.checkSameInstance(b, output);
        if (b.numRows == 1) {
            MatrixVectorMult_FDRM.mult(a, b, output);
        } else {
            MatrixMatrixMult_FDRM.multTransB(a, b, output);
        }
        return output;
    }

    public static <T extends FMatrix1Row> T multTransB(float alpha, T a, T b, @Nullable T output) {
        output = UtilEjml.reshapeOrDeclare(output, a, a.numRows, b.numRows);
        UtilEjml.checkSameInstance(a, output);
        UtilEjml.checkSameInstance(b, output);
        MatrixMatrixMult_FDRM.multTransB(alpha, a, b, output);
        return output;
    }

    public static <T extends FMatrix1Row> T multTransAB(T a, T b, @Nullable T output) {
        output = UtilEjml.reshapeOrDeclare(output, a, a.numCols, b.numRows);
        UtilEjml.checkSameInstance(a, output);
        UtilEjml.checkSameInstance(b, output);
        if (b.numRows == 1) {
            if (a.numCols >= EjmlParameters.MULT_COLUMN_SWITCH) {
                MatrixVectorMult_FDRM.multTransA_reorder(a, b, output);
            } else {
                MatrixVectorMult_FDRM.multTransA_small(a, b, output);
            }
        } else if (a.numCols >= EjmlParameters.MULT_TRANAB_COLUMN_SWITCH) {
            MatrixMatrixMult_FDRM.multTransAB_aux(a, b, output, null);
        } else {
            MatrixMatrixMult_FDRM.multTransAB(a, b, output);
        }
        return output;
    }

    public static <T extends FMatrix1Row> T multTransAB(float alpha, T a, T b, @Nullable T output) {
        output = UtilEjml.reshapeOrDeclare(output, a, a.numCols, b.numRows);
        UtilEjml.checkSameInstance(a, output);
        UtilEjml.checkSameInstance(b, output);
        if (a.numCols >= EjmlParameters.MULT_TRANAB_COLUMN_SWITCH) {
            MatrixMatrixMult_FDRM.multTransAB_aux(alpha, a, b, output, null);
        } else {
            MatrixMatrixMult_FDRM.multTransAB(alpha, a, b, output);
        }
        return output;
    }

    public static float dot(FMatrixD1 a, FMatrixD1 b) {
        if (!MatrixFeatures_FDRM.isVector(a) || !MatrixFeatures_FDRM.isVector(b)) {
            throw new RuntimeException("Both inputs must be vectors");
        }
        return VectorVectorMult_FDRM.innerProd(a, b);
    }

    public static <T extends FMatrix1Row> T multInner(T a, @Nullable T output) {
        output = UtilEjml.reshapeOrDeclare(output, a, a.numCols, a.numCols);
        if (a.numCols >= EjmlParameters.MULT_INNER_SWITCH) {
            MatrixMultProduct_FDRM.inner_small(a, output);
        } else {
            MatrixMultProduct_FDRM.inner_reorder(a, output);
        }
        return output;
    }

    public static <T extends FMatrix1Row> T multOuter(T a, @Nullable T output) {
        output = UtilEjml.reshapeOrDeclare(output, a, a.numRows, a.numRows);
        MatrixMultProduct_FDRM.outer(a, output);
        return output;
    }

    public static void multAdd(FMatrix1Row a, FMatrix1Row b, FMatrix1Row c) {
        if (b.numCols == 1) {
            MatrixVectorMult_FDRM.multAdd(a, b, c);
        } else if (b.numCols >= EjmlParameters.MULT_COLUMN_SWITCH) {
            MatrixMatrixMult_FDRM.multAdd_reorder(a, b, c);
        } else {
            MatrixMatrixMult_FDRM.multAdd_small(a, b, c);
        }
    }

    public static void multAdd(float alpha, FMatrix1Row a, FMatrix1Row b, FMatrix1Row c) {
        if (b.numCols >= EjmlParameters.MULT_COLUMN_SWITCH) {
            MatrixMatrixMult_FDRM.multAdd_reorder(alpha, a, b, c);
        } else {
            MatrixMatrixMult_FDRM.multAdd_small(alpha, a, b, c);
        }
    }

    public static void multAddTransA(FMatrix1Row a, FMatrix1Row b, FMatrix1Row c) {
        if (b.numCols == 1) {
            if (a.numCols >= EjmlParameters.MULT_COLUMN_SWITCH) {
                MatrixVectorMult_FDRM.multAddTransA_reorder(a, b, c);
            } else {
                MatrixVectorMult_FDRM.multAddTransA_small(a, b, c);
            }
        } else if (a.numCols >= EjmlParameters.MULT_COLUMN_SWITCH || b.numCols >= EjmlParameters.MULT_COLUMN_SWITCH) {
            MatrixMatrixMult_FDRM.multAddTransA_reorder(a, b, c);
        } else {
            MatrixMatrixMult_FDRM.multAddTransA_small(a, b, c);
        }
    }

    public static void multAddTransA(float alpha, FMatrix1Row a, FMatrix1Row b, FMatrix1Row c) {
        if (a.numCols >= EjmlParameters.MULT_COLUMN_SWITCH || b.numCols >= EjmlParameters.MULT_COLUMN_SWITCH) {
            MatrixMatrixMult_FDRM.multAddTransA_reorder(alpha, a, b, c);
        } else {
            MatrixMatrixMult_FDRM.multAddTransA_small(alpha, a, b, c);
        }
    }

    public static void multAddTransB(FMatrix1Row a, FMatrix1Row b, FMatrix1Row c) {
        MatrixMatrixMult_FDRM.multAddTransB(a, b, c);
    }

    public static void multAddTransB(float alpha, FMatrix1Row a, FMatrix1Row b, FMatrix1Row c) {
        MatrixMatrixMult_FDRM.multAddTransB(alpha, a, b, c);
    }

    public static void multAddTransAB(FMatrix1Row a, FMatrix1Row b, FMatrix1Row c) {
        if (a.numCols >= EjmlParameters.MULT_TRANAB_COLUMN_SWITCH) {
            MatrixMatrixMult_FDRM.multAddTransAB_aux(a, b, c, null);
        } else {
            MatrixMatrixMult_FDRM.multAddTransAB(a, b, c);
        }
    }

    public static void multAddTransAB(float alpha, FMatrix1Row a, FMatrix1Row b, FMatrix1Row c) {
        if (a.numCols >= EjmlParameters.MULT_TRANAB_COLUMN_SWITCH) {
            MatrixMatrixMult_FDRM.multAddTransAB_aux(alpha, a, b, c, null);
        } else {
            MatrixMatrixMult_FDRM.multAddTransAB(alpha, a, b, c);
        }
    }

    public static boolean solve(FMatrixRMaj a, FMatrixRMaj b, FMatrixRMaj x) {
        x.reshape(a.numCols, b.numCols);
        LinearSolverDense<FMatrixRMaj> solver = LinearSolverFactory_FDRM.general(a.numRows, a.numCols);
        solver = new LinearSolverSafe<FMatrixRMaj>(solver);
        if (!solver.setA(a)) {
            return false;
        }
        solver.solve(b, x);
        return true;
    }

    public static boolean solveSPD(FMatrixRMaj A, FMatrixRMaj b, FMatrixRMaj x) {
        if (A.numRows != A.numCols) {
            throw new IllegalArgumentException("Must be a square matrix");
        }
        x.reshape(A.numCols, b.numCols);
        if (A.numRows <= 7) {
            FMatrixRMaj L = A.createLike();
            if (!UnrolledCholesky_FDRM.lower(A, L)) {
                return false;
            }
            if (x.numCols == 1) {
                x.setTo(b);
                TriangularSolver_FDRM.solveL(L.data, x.data, L.numCols);
                TriangularSolver_FDRM.solveTranL(L.data, x.data, L.numCols);
            } else {
                float[] vv = new float[A.numCols];
                LinearSolverChol_FDRM.solveLower(L, b, x, vv);
            }
        } else {
            LinearSolverDense<FMatrixRMaj> solver = LinearSolverFactory_FDRM.chol(A.numCols);
            if (!(solver = new LinearSolverSafe<FMatrixRMaj>(solver)).setA(A)) {
                return false;
            }
            solver.solve(b, x);
            return true;
        }
        return true;
    }

    public static void transpose(FMatrixRMaj mat) {
        if (mat.numCols == mat.numRows) {
            TransposeAlgs_FDRM.square(mat);
        } else {
            FMatrixRMaj b = new FMatrixRMaj(mat.numCols, mat.numRows);
            CommonOps_FDRM.transpose(mat, b);
            mat.setTo(b);
        }
    }

    public static FMatrixRMaj transpose(FMatrixRMaj A, @Nullable FMatrixRMaj A_tran) {
        A_tran = UtilEjml.reshapeOrDeclare(A_tran, A.numCols, A.numRows);
        if (A.numRows > EjmlParameters.TRANSPOSE_SWITCH && A.numCols > EjmlParameters.TRANSPOSE_SWITCH) {
            TransposeAlgs_FDRM.block(A, A_tran, EjmlParameters.BLOCK_WIDTH);
        } else {
            TransposeAlgs_FDRM.standard(A, A_tran);
        }
        return A_tran;
    }

    public static float trace(FMatrix1Row a) {
        int N = Math.min(a.numRows, a.numCols);
        float sum = 0.0f;
        int index = 0;
        for (int i = 0; i < N; ++i) {
            sum += a.get(index);
            index += 1 + a.numCols;
        }
        return sum;
    }

    public static float det(FMatrixRMaj mat) {
        int numRow;
        int numCol = mat.getNumCols();
        if (numCol != (numRow = mat.getNumRows())) {
            throw new MatrixDimensionException("Must be a square matrix.");
        }
        if (numCol <= 6) {
            if (numCol >= 2) {
                return UnrolledDeterminantFromMinor_FDRM.det(mat);
            }
            return mat.get(0);
        }
        LUDecompositionAlt_FDRM alg = new LUDecompositionAlt_FDRM();
        if (alg.inputModified()) {
            mat = mat.copy();
        }
        if (!alg.decompose(mat)) {
            return 0.0f;
        }
        return alg.computeDeterminant().real;
    }

    public static boolean invert(FMatrixRMaj mat) {
        if (mat.numCols <= 5) {
            if (mat.numCols != mat.numRows) {
                throw new MatrixDimensionException("Must be a square matrix.");
            }
            if (mat.numCols >= 2) {
                UnrolledInverseFromMinor_FDRM.inv(mat, mat);
            } else {
                mat.set(0, 1.0f / mat.get(0));
            }
        } else {
            LUDecompositionAlt_FDRM alg = new LUDecompositionAlt_FDRM();
            LinearSolverLu_FDRM solver = new LinearSolverLu_FDRM(alg);
            if (solver.setA(mat)) {
                solver.invert(mat);
            } else {
                return false;
            }
        }
        return true;
    }

    public static boolean invert(FMatrixRMaj mat, FMatrixRMaj result) {
        result.reshape(mat.numRows, mat.numCols);
        if (mat.numCols <= 5) {
            if (mat.numCols != mat.numRows) {
                throw new MatrixDimensionException("Must be a square matrix.");
            }
            if (result.numCols >= 2) {
                UnrolledInverseFromMinor_FDRM.inv(mat, result);
            } else {
                result.set(0, 1.0f / mat.get(0));
            }
        } else {
            LUDecompositionAlt_FDRM alg = new LUDecompositionAlt_FDRM();
            LinearSolverLu_FDRM solver = new LinearSolverLu_FDRM(alg);
            if (solver.modifiesA()) {
                mat = mat.copy();
            }
            if (!solver.setA(mat)) {
                return false;
            }
            solver.invert(result);
        }
        return true;
    }

    public static boolean invertSPD(FMatrixRMaj mat, FMatrixRMaj result) {
        if (mat.numRows != mat.numCols) {
            throw new IllegalArgumentException("Must be a square matrix");
        }
        result.reshape(mat.numRows, mat.numRows);
        if (mat.numRows <= 7) {
            if (!UnrolledCholesky_FDRM.lower(mat, result)) {
                return false;
            }
            TriangularSolver_FDRM.invertLower(result.data, result.numCols);
            SpecializedOps_FDRM.multLowerTranA(result);
        } else {
            LinearSolverDense<FMatrixRMaj> solver = LinearSolverFactory_FDRM.chol(mat.numCols);
            if (solver.modifiesA()) {
                mat = mat.copy();
            }
            if (!solver.setA(mat)) {
                return false;
            }
            solver.invert(result);
        }
        return true;
    }

    public static void pinv(FMatrixRMaj A, FMatrixRMaj invA) {
        LinearSolverDense<FMatrixRMaj> solver = LinearSolverFactory_FDRM.pseudoInverse(true);
        if (solver.modifiesA()) {
            A = A.copy();
        }
        if (!solver.setA(A)) {
            throw new IllegalArgumentException("Invert failed, maybe a bug?");
        }
        solver.invert(invA);
    }

    public static FMatrixRMaj[] columnsToVector(FMatrixRMaj A, @Nullable FMatrixRMaj[] v) {
        FMatrixRMaj[] ret = v == null || v.length < A.numCols ? new FMatrixRMaj[A.numCols] : v;
        for (int i = 0; i < ret.length; ++i) {
            if (ret[i] == null) {
                ret[i] = new FMatrixRMaj(A.numRows, 1);
            } else {
                ret[i].reshape(A.numRows, 1, false);
            }
            FMatrixRMaj u = ret[i];
            for (int j = 0; j < A.numRows; ++j) {
                u.set(j, 0, A.get(j, i));
            }
        }
        return ret;
    }

    public static FMatrixRMaj[] rowsToVector(FMatrixRMaj A, @Nullable FMatrixRMaj[] v) {
        FMatrixRMaj[] ret = v == null || v.length < A.numRows ? new FMatrixRMaj[A.numRows] : v;
        for (int i = 0; i < ret.length; ++i) {
            if (ret[i] == null) {
                ret[i] = new FMatrixRMaj(A.numCols, 1);
            } else {
                ret[i].reshape(A.numCols, 1, false);
            }
            FMatrixRMaj u = ret[i];
            for (int j = 0; j < A.numCols; ++j) {
                u.set(j, 0, A.get(i, j));
            }
        }
        return ret;
    }

    public static void setIdentity(FMatrix1Row mat) {
        int width = mat.numRows < mat.numCols ? mat.numRows : mat.numCols;
        Arrays.fill(mat.data, 0, mat.getNumElements(), 0.0f);
        int index = 0;
        int i = 0;
        while (i < width) {
            mat.data[index] = 1.0f;
            ++i;
            index += mat.numCols + 1;
        }
    }

    public static FMatrixRMaj identity(int width) {
        FMatrixRMaj ret = new FMatrixRMaj(width, width);
        for (int i = 0; i < width; ++i) {
            ret.set(i, i, 1.0f);
        }
        return ret;
    }

    public static FMatrixRMaj identity(int numRows, int numCols) {
        FMatrixRMaj ret = new FMatrixRMaj(numRows, numCols);
        int small = numRows < numCols ? numRows : numCols;
        for (int i = 0; i < small; ++i) {
            ret.set(i, i, 1.0f);
        }
        return ret;
    }

    public static FMatrixRMaj diag(float ... diagEl) {
        return CommonOps_FDRM.diag(null, diagEl.length, diagEl);
    }

    public static FMatrixRMaj diag(@Nullable FMatrixRMaj ret, int width, float ... diagEl) {
        if (ret == null) {
            ret = new FMatrixRMaj(width, width);
        } else {
            if (ret.numRows != width || ret.numCols != width) {
                throw new IllegalArgumentException("Unexpected matrix size");
            }
            CommonOps_FDRM.fill(ret, 0.0f);
        }
        for (int i = 0; i < width; ++i) {
            ret.unsafe_set(i, i, diagEl[i]);
        }
        return ret;
    }

    public static FMatrixRMaj diagR(int numRows, int numCols, float ... diagEl) {
        FMatrixRMaj ret = new FMatrixRMaj(numRows, numCols);
        int o = Math.min(numRows, numCols);
        for (int i = 0; i < o; ++i) {
            ret.set(i, i, diagEl[i]);
        }
        return ret;
    }

    public static FMatrixRMaj kron(FMatrixRMaj A, FMatrixRMaj B, @Nullable FMatrixRMaj C) {
        int numColsC = A.numCols * B.numCols;
        int numRowsC = A.numRows * B.numRows;
        C = UtilEjml.reshapeOrDeclare(C, numRowsC, numColsC);
        for (int i = 0; i < A.numRows; ++i) {
            for (int j = 0; j < A.numCols; ++j) {
                float a = A.get(i, j);
                for (int rowB = 0; rowB < B.numRows; ++rowB) {
                    for (int colB = 0; colB < B.numCols; ++colB) {
                        float val = a * B.get(rowB, colB);
                        C.unsafe_set(i * B.numRows + rowB, j * B.numCols + colB, val);
                    }
                }
            }
        }
        return C;
    }

    public static void extract(FMatrix src, int srcY0, int srcY1, int srcX0, int srcX1, FMatrix dst, int dstY0, int dstX0) {
        if (srcY1 < srcY0 || srcY0 < 0 || srcY1 > src.getNumRows()) {
            throw new MatrixDimensionException("srcY1 < srcY0 || srcY0 < 0 || srcY1 > src.numRows. " + UtilEjml.stringShapes(src, dst));
        }
        if (srcX1 < srcX0 || srcX0 < 0 || srcX1 > src.getNumCols()) {
            throw new MatrixDimensionException("srcX1 < srcX0 || srcX0 < 0 || srcX1 > src.numCols. " + UtilEjml.stringShapes(src, dst));
        }
        int w = srcX1 - srcX0;
        int h = srcY1 - srcY0;
        if (dstY0 + h > dst.getNumRows()) {
            throw new MatrixDimensionException("dst is too small in rows. " + dst.getNumRows() + " < " + (dstY0 + h));
        }
        if (dstX0 + w > dst.getNumCols()) {
            throw new MatrixDimensionException("dst is too small in columns. " + dst.getNumCols() + " < " + (dstX0 + w));
        }
        if (src instanceof FMatrixRMaj && dst instanceof FMatrixRMaj) {
            ImplCommonOps_FDRM.extract((FMatrixRMaj)src, srcY0, srcX0, (FMatrixRMaj)dst, dstY0, dstX0, h, w);
        } else {
            ImplCommonOps_FDMA.extract(src, srcY0, srcX0, dst, dstY0, dstX0, h, w);
        }
    }

    public static void extract(FMatrix src, int srcY0, int srcY1, int srcX0, int srcX1, FMatrix dst) {
        ((ReshapeMatrix)((Object)dst)).reshape(srcY1 - srcY0, srcX1 - srcX0);
        CommonOps_FDRM.extract(src, srcY0, srcY1, srcX0, srcX1, dst, 0, 0);
    }

    public static void extract(FMatrix src, int srcY0, int srcX0, FMatrix dst) {
        CommonOps_FDRM.extract(src, srcY0, srcY0 + dst.getNumRows(), srcX0, srcX0 + dst.getNumCols(), dst, 0, 0);
    }

    public static FMatrixRMaj extract(FMatrixRMaj src, int srcY0, int srcY1, int srcX0, int srcX1) {
        if (srcY1 <= srcY0 || srcY0 < 0 || srcY1 > src.numRows) {
            throw new MatrixDimensionException("srcY1 <= srcY0 || srcY0 < 0 || srcY1 > src.numRows");
        }
        if (srcX1 <= srcX0 || srcX0 < 0 || srcX1 > src.numCols) {
            throw new MatrixDimensionException("srcX1 <= srcX0 || srcX0 < 0 || srcX1 > src.numCols");
        }
        int w = srcX1 - srcX0;
        int h = srcY1 - srcY0;
        FMatrixRMaj dst = new FMatrixRMaj(h, w);
        ImplCommonOps_FDRM.extract(src, srcY0, srcX0, dst, 0, 0, h, w);
        return dst;
    }

    public static FMatrixRMaj extract(FMatrixRMaj src, int[] rows, int rowsSize, int[] cols, int colsSize, @Nullable FMatrixRMaj dst) {
        dst = UtilEjml.reshapeOrDeclare(dst, rowsSize, colsSize);
        int indexDst = 0;
        for (int i = 0; i < rowsSize; ++i) {
            int indexSrcRow = src.numCols * rows[i];
            for (int j = 0; j < colsSize; ++j) {
                dst.data[indexDst++] = src.data[indexSrcRow + cols[j]];
            }
        }
        return dst;
    }

    public static FMatrixRMaj extract(FMatrixRMaj src, int[] indexes, int length, @Nullable FMatrixRMaj dst) {
        if (dst == null) {
            dst = new FMatrixRMaj(length, 1);
        } else if (!MatrixFeatures_FDRM.isVector(dst) || dst.getNumElements() != length) {
            dst.reshape(length, 1);
        }
        for (int i = 0; i < length; ++i) {
            dst.data[i] = src.data[indexes[i]];
        }
        return dst;
    }

    public static void insert(FMatrixRMaj src, FMatrixRMaj dst, int[] rows, int rowsSize, int[] cols, int colsSize) {
        UtilEjml.assertEq(rowsSize, src.numRows, "src's rows don't match rowsSize");
        UtilEjml.assertEq(colsSize, src.numCols, "src's columns don't match colsSize");
        int indexSrc = 0;
        for (int i = 0; i < rowsSize; ++i) {
            int indexDstRow = dst.numCols * rows[i];
            for (int j = 0; j < colsSize; ++j) {
                dst.data[indexDstRow + cols[j]] = src.data[indexSrc++];
            }
        }
    }

    public static FMatrixRMaj extractDiag(FMatrixRMaj src, @Nullable FMatrixRMaj dst) {
        int N = Math.min(src.numRows, src.numCols);
        if (dst == null) {
            dst = new FMatrixRMaj(N, 1);
        } else if (!MatrixFeatures_FDRM.isVector(dst) || dst.numCols * dst.numCols != N) {
            dst.reshape(N, 1);
        }
        for (int i = 0; i < N; ++i) {
            dst.set(i, src.unsafe_get(i, i));
        }
        return dst;
    }

    public static FMatrixRMaj extractRow(FMatrixRMaj a, int row, @Nullable FMatrixRMaj out) {
        if (out == null) {
            out = new FMatrixRMaj(1, a.numCols);
        } else if (!MatrixFeatures_FDRM.isVector(out) || out.getNumElements() != a.numCols) {
            out.reshape(1, a.numCols);
        }
        System.arraycopy(a.data, a.getIndex(row, 0), out.data, 0, a.numCols);
        return out;
    }

    public static FMatrixRMaj extractColumn(FMatrixRMaj a, int column, @Nullable FMatrixRMaj out) {
        if (out == null) {
            out = new FMatrixRMaj(a.numRows, 1);
        } else if (!MatrixFeatures_FDRM.isVector(out) || out.getNumElements() != a.numRows) {
            out.reshape(a.numRows, 1);
        }
        int index = column;
        int i = 0;
        while (i < a.numRows) {
            out.data[i] = a.data[index];
            ++i;
            index += a.numCols;
        }
        return out;
    }

    public static void removeColumns(FMatrixRMaj A, int col0, int col1) {
        UtilEjml.assertTrue(col0 < col1, "col1 must be >= col0");
        UtilEjml.assertTrue(col0 >= 0 && col1 <= A.numCols, "Columns which are to be removed must be in bounds");
        int step = col1 - col0 + 1;
        int offset = 0;
        int idx = 0;
        for (int row = 0; row < A.numRows; ++row) {
            int i = 0;
            while (i < col0) {
                A.data[idx] = A.data[idx + offset];
                ++i;
                ++idx;
            }
            offset += step;
            i = col1 + 1;
            while (i < A.numCols) {
                A.data[idx] = A.data[idx + offset];
                ++i;
                ++idx;
            }
        }
        A.numCols -= step;
    }

    public static void insert(FMatrix src, FMatrix dest, int destY0, int destX0) {
        CommonOps_FDRM.extract(src, 0, src.getNumRows(), 0, src.getNumCols(), dest, destY0, destX0);
    }

    public static float elementMax(FMatrixD1 a) {
        return ImplCommonOps_FDRM.elementMax(a, null);
    }

    public static float elementMax(FMatrixD1 a, ElementLocation loc) {
        return ImplCommonOps_FDRM.elementMax(a, loc);
    }

    public static float elementMaxAbs(FMatrixD1 a) {
        return ImplCommonOps_FDRM.elementMaxAbs(a, null);
    }

    public static float elementMaxAbs(FMatrixD1 a, ElementLocation loc) {
        return ImplCommonOps_FDRM.elementMaxAbs(a, loc);
    }

    public static float elementMin(FMatrixD1 a) {
        return ImplCommonOps_FDRM.elementMin(a, null);
    }

    public static float elementMin(FMatrixD1 a, ElementLocation loc) {
        return ImplCommonOps_FDRM.elementMin(a, loc);
    }

    public static float elementMinAbs(FMatrixD1 a) {
        return ImplCommonOps_FDRM.elementMinAbs(a, null);
    }

    public static float elementMinAbs(FMatrixD1 a, ElementLocation loc) {
        return ImplCommonOps_FDRM.elementMinAbs(a, loc);
    }

    public static void elementMult(FMatrixD1 A, FMatrixD1 B) {
        ImplCommonOps_FDRM.elementMult(A, B);
    }

    public static <T extends FMatrixD1> T elementMult(T A, T B, @Nullable T output) {
        return ImplCommonOps_FDRM.elementMult(A, B, output);
    }

    public static void elementDiv(FMatrixD1 A, FMatrixD1 B) {
        ImplCommonOps_FDRM.elementDiv(A, B);
    }

    public static <T extends FMatrixD1> T elementDiv(T A, T B, @Nullable T output) {
        return ImplCommonOps_FDRM.elementDiv(A, B, output);
    }

    public static float elementSum(FMatrixD1 mat) {
        return ImplCommonOps_FDRM.elementSum(mat);
    }

    public static float elementSumAbs(FMatrixD1 mat) {
        return ImplCommonOps_FDRM.elementSumAbs(mat);
    }

    public static <T extends FMatrixD1> T elementPower(T A, T B, @Nullable T output) {
        return ImplCommonOps_FDRM.elementPower(A, B, output);
    }

    public static <T extends FMatrixD1> T elementPower(float a, T B, @Nullable T output) {
        return ImplCommonOps_FDRM.elementPower(a, B, output);
    }

    public static <T extends FMatrixD1> T elementPower(T A, float b, @Nullable T output) {
        return ImplCommonOps_FDRM.elementPower(A, b, output);
    }

    public static <T extends FMatrixD1> T elementLog(T A, @Nullable T output) {
        return ImplCommonOps_FDRM.elementLog(A, output);
    }

    public static <T extends FMatrixD1> T elementExp(T A, @Nullable T output) {
        return ImplCommonOps_FDRM.elementExp(A, output);
    }

    public static void multRows(float[] values, FMatrixRMaj A) {
        if (values.length < A.numRows) {
            throw new IllegalArgumentException("Not enough elements in values.");
        }
        int index = 0;
        for (int row = 0; row < A.numRows; ++row) {
            float v = values[row];
            for (int col = 0; col < A.numCols; ++col) {
                int n = index++;
                A.data[n] = A.data[n] * v;
            }
        }
    }

    public static void divideRows(float[] values, FMatrixRMaj A) {
        if (values.length < A.numRows) {
            throw new IllegalArgumentException("Not enough elements in values.");
        }
        int index = 0;
        for (int row = 0; row < A.numRows; ++row) {
            float v = values[row];
            for (int col = 0; col < A.numCols; ++col) {
                int n = index++;
                A.data[n] = A.data[n] / v;
            }
        }
    }

    public static void multCols(FMatrixRMaj A, float[] values) {
        if (values.length < A.numCols) {
            throw new IllegalArgumentException("Not enough elements in values.");
        }
        int index = 0;
        for (int row = 0; row < A.numRows; ++row) {
            for (int col = 0; col < A.numCols; ++col) {
                int n = index++;
                A.data[n] = A.data[n] * values[col];
            }
        }
    }

    public static void divideCols(FMatrixRMaj A, float[] values) {
        if (values.length < A.numCols) {
            throw new IllegalArgumentException("Not enough elements in values.");
        }
        int index = 0;
        for (int row = 0; row < A.numRows; ++row) {
            for (int col = 0; col < A.numCols; ++col) {
                int n = index++;
                A.data[n] = A.data[n] / values[col];
            }
        }
    }

    public static void divideRowsCols(float[] diagA, int offsetA, FMatrixRMaj B, float[] diagC, int offsetC) {
        if (diagA.length - offsetA < B.numRows) {
            throw new IllegalArgumentException("Not enough elements in diagA.");
        }
        if (diagC.length - offsetC < B.numCols) {
            throw new IllegalArgumentException("Not enough elements in diagC.");
        }
        int rows = B.numRows;
        int cols = B.numCols;
        int index = 0;
        for (int row = 0; row < rows; ++row) {
            float va = diagA[offsetA + row];
            for (int col = 0; col < cols; ++col) {
                int n = index++;
                B.data[n] = B.data[n] / (va * diagC[offsetC + col]);
            }
        }
    }

    public static FMatrixRMaj sumRows(FMatrixRMaj input, @Nullable FMatrixRMaj output) {
        output = UtilEjml.reshapeOrDeclare(output, input.numRows, 1);
        for (int row = 0; row < input.numRows; ++row) {
            float total = 0.0f;
            int end = (row + 1) * input.numCols;
            for (int index = row * input.numCols; index < end; ++index) {
                total += input.data[index];
            }
            output.set(row, total);
        }
        return output;
    }

    public static FMatrixRMaj minRows(FMatrixRMaj input, @Nullable FMatrixRMaj output) {
        output = UtilEjml.reshapeOrDeclare(output, input.numRows, 1);
        for (int row = 0; row < input.numRows; ++row) {
            float min = Float.MAX_VALUE;
            int end = (row + 1) * input.numCols;
            for (int index = row * input.numCols; index < end; ++index) {
                float v = input.data[index];
                if (!(v < min)) continue;
                min = v;
            }
            output.set(row, min);
        }
        return output;
    }

    public static FMatrixRMaj maxRows(FMatrixRMaj input, @Nullable FMatrixRMaj output) {
        output = UtilEjml.reshapeOrDeclare(output, input.numRows, 1);
        for (int row = 0; row < input.numRows; ++row) {
            float max = -3.4028235E38f;
            int end = (row + 1) * input.numCols;
            for (int index = row * input.numCols; index < end; ++index) {
                float v = input.data[index];
                if (!(v > max)) continue;
                max = v;
            }
            output.set(row, max);
        }
        return output;
    }

    public static FMatrixRMaj sumCols(FMatrixRMaj input, @Nullable FMatrixRMaj output) {
        output = UtilEjml.reshapeOrDeclare(output, 1, input.numCols);
        for (int cols = 0; cols < input.numCols; ++cols) {
            int index;
            float total = 0.0f;
            int end = index + input.numCols * input.numRows;
            for (index = cols; index < end; index += input.numCols) {
                total += input.data[index];
            }
            output.set(cols, total);
        }
        return output;
    }

    public static FMatrixRMaj minCols(FMatrixRMaj input, @Nullable FMatrixRMaj output) {
        output = UtilEjml.reshapeOrDeclare(output, 1, input.numCols);
        for (int cols = 0; cols < input.numCols; ++cols) {
            int index;
            float minimum = Float.MAX_VALUE;
            int end = index + input.numCols * input.numRows;
            for (index = cols; index < end; index += input.numCols) {
                float v = input.data[index];
                if (!(v < minimum)) continue;
                minimum = v;
            }
            output.set(cols, minimum);
        }
        return output;
    }

    public static FMatrixRMaj maxCols(FMatrixRMaj input, @Nullable FMatrixRMaj output) {
        output = UtilEjml.reshapeOrDeclare(output, 1, input.numCols);
        for (int cols = 0; cols < input.numCols; ++cols) {
            int index;
            float maximum = -3.4028235E38f;
            int end = index + input.numCols * input.numRows;
            for (index = cols; index < end; index += input.numCols) {
                float v = input.data[index];
                if (!(v > maximum)) continue;
                maximum = v;
            }
            output.set(cols, maximum);
        }
        return output;
    }

    public static void addEquals(FMatrixD1 a, FMatrixD1 b) {
        UtilEjml.checkSameShape((Matrix)a, (Matrix)b, true);
        int length = a.getNumElements();
        for (int i = 0; i < length; ++i) {
            a.plus(i, b.get(i));
        }
    }

    public static void addEquals(FMatrixD1 a, float beta, FMatrixD1 b) {
        UtilEjml.checkSameShape((Matrix)a, (Matrix)b, true);
        int length = a.getNumElements();
        for (int i = 0; i < length; ++i) {
            a.plus(i, beta * b.get(i));
        }
    }

    public static <T extends FMatrixD1> T add(T a, T b, @Nullable T output) {
        UtilEjml.checkSameShape(a, b, true);
        output = UtilEjml.reshapeOrDeclare(output, a);
        int length = a.getNumElements();
        for (int i = 0; i < length; ++i) {
            output.set(i, a.get(i) + b.get(i));
        }
        return output;
    }

    public static <T extends FMatrixD1> T add(T a, float beta, T b, @Nullable T output) {
        UtilEjml.checkSameShape(a, b, true);
        output = UtilEjml.reshapeOrDeclare(output, a);
        int length = a.getNumElements();
        for (int i = 0; i < length; ++i) {
            output.set(i, a.get(i) + beta * b.get(i));
        }
        return output;
    }

    public static <T extends FMatrixD1> T add(float alpha, T a, float beta, T b, @Nullable T output) {
        UtilEjml.checkSameShape(a, b, true);
        output = UtilEjml.reshapeOrDeclare(output, a);
        int length = a.getNumElements();
        for (int i = 0; i < length; ++i) {
            output.set(i, alpha * a.get(i) + beta * b.get(i));
        }
        return output;
    }

    public static <T extends FMatrixD1> T add(float alpha, T a, T b, T output) {
        UtilEjml.checkSameShape(a, b, true);
        output = UtilEjml.reshapeOrDeclare(output, a);
        int length = a.getNumElements();
        for (int i = 0; i < length; ++i) {
            output.set(i, alpha * a.get(i) + b.get(i));
        }
        return output;
    }

    public static void add(FMatrixD1 a, float val) {
        int length = a.getNumElements();
        for (int i = 0; i < length; ++i) {
            a.plus(i, val);
        }
    }

    public static <T extends FMatrixD1> T add(T a, float val, T output) {
        output = UtilEjml.reshapeOrDeclare(output, a);
        int length = a.getNumElements();
        for (int i = 0; i < length; ++i) {
            output.data[i] = a.data[i] + val;
        }
        return output;
    }

    public static <T extends FMatrixD1> T subtract(T a, float val, @Nullable T output) {
        output = UtilEjml.reshapeOrDeclare(output, a);
        int length = a.getNumElements();
        for (int i = 0; i < length; ++i) {
            output.data[i] = a.data[i] - val;
        }
        return output;
    }

    public static <T extends FMatrixD1> T subtract(float val, T a, @Nullable T output) {
        output = UtilEjml.reshapeOrDeclare(output, a);
        int length = a.getNumElements();
        for (int i = 0; i < length; ++i) {
            output.data[i] = val - a.data[i];
        }
        return output;
    }

    public static void subtractEquals(FMatrixD1 a, FMatrixD1 b) {
        UtilEjml.checkSameShape((Matrix)a, (Matrix)b, true);
        int length = a.getNumElements();
        for (int i = 0; i < length; ++i) {
            int n = i;
            a.data[n] = a.data[n] - b.data[i];
        }
    }

    public static <T extends FMatrixD1> T subtract(T a, T b, @Nullable T output) {
        UtilEjml.checkSameShape(a, b, true);
        output = UtilEjml.reshapeOrDeclare(output, a);
        int length = a.getNumElements();
        for (int i = 0; i < length; ++i) {
            output.data[i] = a.data[i] - b.data[i];
        }
        return output;
    }

    public static void scale(float alpha, FMatrixD1 a) {
        int size = a.getNumElements();
        int i = 0;
        while (i < size) {
            int n = i++;
            a.data[n] = a.data[n] * alpha;
        }
    }

    public static void scale(float alpha, FMatrixD1 a, FMatrixD1 b) {
        b.reshape(a.numRows, a.numCols);
        int size = a.getNumElements();
        for (int i = 0; i < size; ++i) {
            b.data[i] = a.data[i] * alpha;
        }
    }

    public static void scaleRow(float alpha, FMatrixRMaj A, int row) {
        int idx = row * A.numCols;
        for (int col = 0; col < A.numCols; ++col) {
            int n = idx++;
            A.data[n] = A.data[n] * alpha;
        }
    }

    public static void scaleCol(float alpha, FMatrixRMaj A, int col) {
        int idx = col;
        int row = 0;
        while (row < A.numRows) {
            int n = idx;
            A.data[n] = A.data[n] * alpha;
            ++row;
            idx += A.numCols;
        }
    }

    public static void divide(float alpha, FMatrixD1 a) {
        int size = a.getNumElements();
        for (int i = 0; i < size; ++i) {
            a.data[i] = alpha / a.data[i];
        }
    }

    public static void divide(FMatrixD1 a, float alpha) {
        int size = a.getNumElements();
        int i = 0;
        while (i < size) {
            int n = i++;
            a.data[n] = a.data[n] / alpha;
        }
    }

    public static <T extends FMatrixD1> T divide(float alpha, T input, T output) {
        output = UtilEjml.reshapeOrDeclare(output, input);
        int size = input.getNumElements();
        for (int i = 0; i < size; ++i) {
            output.data[i] = alpha / input.data[i];
        }
        return output;
    }

    public static <T extends FMatrixD1> T divide(T input, float alpha, @Nullable T output) {
        output = UtilEjml.reshapeOrDeclare(output, input);
        int size = input.getNumElements();
        for (int i = 0; i < size; ++i) {
            output.data[i] = input.data[i] / alpha;
        }
        return output;
    }

    public static void changeSign(FMatrixD1 a) {
        int size = a.getNumElements();
        for (int i = 0; i < size; ++i) {
            a.data[i] = -a.data[i];
        }
    }

    public static <T extends FMatrixD1> T changeSign(T input, @Nullable T output) {
        output = UtilEjml.reshapeOrDeclare(output, input);
        int size = input.getNumElements();
        for (int i = 0; i < size; ++i) {
            output.data[i] = -input.data[i];
        }
        return output;
    }

    public static void fill(FMatrixD1 a, float value) {
        Arrays.fill(a.data, 0, a.getNumElements(), value);
    }

    public static FMatrixRMaj rref(FMatrixRMaj A, int numUnknowns, @Nullable FMatrixRMaj reduced) {
        reduced = UtilEjml.reshapeOrDeclare(reduced, A);
        if (numUnknowns <= 0) {
            numUnknowns = A.numCols;
        }
        RrefGaussJordanRowPivot_FDRM alg = new RrefGaussJordanRowPivot_FDRM();
        alg.setTolerance(CommonOps_FDRM.elementMaxAbs(A) * UtilEjml.F_EPS * (float)Math.max(A.numRows, A.numCols));
        reduced.setTo(A);
        alg.reduce(reduced, numUnknowns);
        return reduced;
    }

    public static BMatrixRMaj elementLessThan(FMatrixRMaj A, float value, BMatrixRMaj output) {
        output = UtilEjml.reshapeOrDeclare(output, A.numRows, A.numCols);
        int N = A.getNumElements();
        for (int i = 0; i < N; ++i) {
            output.data[i] = A.data[i] < value;
        }
        return output;
    }

    public static BMatrixRMaj elementLessThanOrEqual(FMatrixRMaj A, float value, BMatrixRMaj output) {
        output = UtilEjml.reshapeOrDeclare(output, A.numRows, A.numCols);
        int N = A.getNumElements();
        for (int i = 0; i < N; ++i) {
            output.data[i] = A.data[i] <= value;
        }
        return output;
    }

    public static BMatrixRMaj elementMoreThan(FMatrixRMaj A, float value, BMatrixRMaj output) {
        output = UtilEjml.reshapeOrDeclare(output, A.numRows, A.numCols);
        int N = A.getNumElements();
        for (int i = 0; i < N; ++i) {
            output.data[i] = A.data[i] > value;
        }
        return output;
    }

    public static BMatrixRMaj elementMoreThanOrEqual(FMatrixRMaj A, float value, BMatrixRMaj output) {
        output = UtilEjml.reshapeOrDeclare(output, A.numRows, A.numCols);
        int N = A.getNumElements();
        for (int i = 0; i < N; ++i) {
            output.data[i] = A.data[i] >= value;
        }
        return output;
    }

    public static BMatrixRMaj elementLessThan(FMatrixRMaj A, FMatrixRMaj B, BMatrixRMaj output) {
        output = UtilEjml.reshapeOrDeclare(output, A.numRows, A.numCols);
        int N = A.getNumElements();
        for (int i = 0; i < N; ++i) {
            output.data[i] = A.data[i] < B.data[i];
        }
        return output;
    }

    public static BMatrixRMaj elementLessThanOrEqual(FMatrixRMaj A, FMatrixRMaj B, BMatrixRMaj output) {
        output = UtilEjml.reshapeOrDeclare(output, A.numRows, A.numCols);
        int N = A.getNumElements();
        for (int i = 0; i < N; ++i) {
            output.data[i] = A.data[i] <= B.data[i];
        }
        return output;
    }

    public static FMatrixRMaj elements(FMatrixRMaj A, BMatrixRMaj marked, @Nullable FMatrixRMaj output) {
        UtilEjml.checkSameShape((Matrix)A, (Matrix)marked, false);
        if (output == null) {
            output = new FMatrixRMaj(1, 1);
        }
        output.reshape(CommonOps_FDRM.countTrue(marked), 1);
        int N = A.getNumElements();
        int index = 0;
        for (int i = 0; i < N; ++i) {
            if (!marked.data[i]) continue;
            output.data[index++] = A.data[i];
        }
        return output;
    }

    public static int countTrue(BMatrixRMaj A) {
        int total = 0;
        int N = A.getNumElements();
        for (int i = 0; i < N; ++i) {
            if (!A.data[i]) continue;
            ++total;
        }
        return total;
    }

    public static FMatrixRMaj concatColumns(FMatrixRMaj a, FMatrixRMaj b, @Nullable FMatrixRMaj output) {
        int rows = Math.max(a.numRows, b.numRows);
        int cols = a.numCols + b.numCols;
        output = UtilEjml.reshapeOrDeclare(output, rows, cols);
        output.zero();
        CommonOps_FDRM.insert(a, output, 0, 0);
        CommonOps_FDRM.insert(b, output, 0, a.numCols);
        return output;
    }

    public static FMatrixRMaj concatColumnsMulti(FMatrixRMaj ... m) {
        int rows = 0;
        int cols = 0;
        for (int i = 0; i < m.length; ++i) {
            rows = Math.max(rows, m[i].numRows);
            cols += m[i].numCols;
        }
        FMatrixRMaj R = new FMatrixRMaj(rows, cols);
        int col = 0;
        for (int i = 0; i < m.length; ++i) {
            CommonOps_FDRM.insert(m[i], R, 0, col);
            col += m[i].numCols;
        }
        return R;
    }

    public static void concatRows(FMatrixRMaj a, FMatrixRMaj b, FMatrixRMaj output) {
        int rows = a.numRows + b.numRows;
        int cols = Math.max(a.numCols, b.numCols);
        output.reshape(rows, cols);
        output.zero();
        CommonOps_FDRM.insert(a, output, 0, 0);
        CommonOps_FDRM.insert(b, output, a.numRows, 0);
    }

    public static FMatrixRMaj concatRowsMulti(FMatrixRMaj ... m) {
        int rows = 0;
        int cols = 0;
        for (int i = 0; i < m.length; ++i) {
            rows += m[i].numRows;
            cols = Math.max(cols, m[i].numCols);
        }
        FMatrixRMaj R = new FMatrixRMaj(rows, cols);
        int row = 0;
        for (int i = 0; i < m.length; ++i) {
            CommonOps_FDRM.insert(m[i], R, row, 0);
            row += m[i].numRows;
        }
        return R;
    }

    public static FMatrixRMaj permuteRowInv(int[] pinv, FMatrixRMaj input, FMatrixRMaj output) {
        if (input.numRows > pinv.length) {
            throw new MatrixDimensionException("permutation vector must have at least as many elements as input has rows");
        }
        output = UtilEjml.reshapeOrDeclare(output, input.numRows, input.numCols);
        int m = input.numCols;
        for (int row = 0; row < input.numRows; ++row) {
            System.arraycopy(input.data, row * m, output.data, pinv[row] * m, m);
        }
        return output;
    }

    public static void abs(FMatrixD1 a, FMatrixD1 c) {
        c.reshape(a.numRows, a.numCols);
        int length = a.getNumElements();
        for (int i = 0; i < length; ++i) {
            c.data[i] = Math.abs(a.data[i]);
        }
    }

    public static void abs(FMatrixD1 a) {
        int length = a.getNumElements();
        for (int i = 0; i < length; ++i) {
            a.data[i] = Math.abs(a.data[i]);
        }
    }

    public static void symmLowerToFull(FMatrixRMaj A) {
        if (A.numRows != A.numCols) {
            throw new MatrixDimensionException("Must be a square matrix");
        }
        int cols = A.numCols;
        for (int row = 0; row < A.numRows; ++row) {
            for (int col = row + 1; col < cols; ++col) {
                A.data[row * cols + col] = A.data[col * cols + row];
            }
        }
    }

    public static void symmUpperToFull(FMatrixRMaj A) {
        if (A.numRows != A.numCols) {
            throw new MatrixDimensionException("Must be a square matrix");
        }
        int cols = A.numCols;
        for (int row = 0; row < A.numRows; ++row) {
            for (int col = 0; col <= row; ++col) {
                A.data[row * cols + col] = A.data[col * cols + row];
            }
        }
    }

    public static FMatrixRMaj apply(FMatrixRMaj input, FOperatorUnary func, @Nullable FMatrixRMaj output) {
        output = UtilEjml.reshapeOrDeclare(output, input.numRows, input.numCols);
        for (int i = 0; i < input.data.length; ++i) {
            output.data[i] = func.apply(input.data[i]);
        }
        return output;
    }

    public static FMatrixRMaj apply(FMatrixRMaj input, FOperatorUnary func) {
        return CommonOps_FDRM.apply(input, func, input);
    }
}

