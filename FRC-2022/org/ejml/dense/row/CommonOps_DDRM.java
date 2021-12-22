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
import org.ejml.data.DMatrix;
import org.ejml.data.DMatrix1Row;
import org.ejml.data.DMatrixD1;
import org.ejml.data.DMatrixRMaj;
import org.ejml.data.ElementLocation;
import org.ejml.data.Matrix;
import org.ejml.data.ReshapeMatrix;
import org.ejml.dense.row.MatrixFeatures_DDRM;
import org.ejml.dense.row.SpecializedOps_DDRM;
import org.ejml.dense.row.decomposition.TriangularSolver_DDRM;
import org.ejml.dense.row.decomposition.lu.LUDecompositionAlt_DDRM;
import org.ejml.dense.row.factory.LinearSolverFactory_DDRM;
import org.ejml.dense.row.linsol.chol.LinearSolverChol_DDRM;
import org.ejml.dense.row.linsol.lu.LinearSolverLu_DDRM;
import org.ejml.dense.row.misc.ImplCommonOps_DDMA;
import org.ejml.dense.row.misc.ImplCommonOps_DDRM;
import org.ejml.dense.row.misc.RrefGaussJordanRowPivot_DDRM;
import org.ejml.dense.row.misc.TransposeAlgs_DDRM;
import org.ejml.dense.row.misc.UnrolledCholesky_DDRM;
import org.ejml.dense.row.misc.UnrolledDeterminantFromMinor_DDRM;
import org.ejml.dense.row.misc.UnrolledInverseFromMinor_DDRM;
import org.ejml.dense.row.mult.MatrixMatrixMult_DDRM;
import org.ejml.dense.row.mult.MatrixMultProduct_DDRM;
import org.ejml.dense.row.mult.MatrixVectorMult_DDRM;
import org.ejml.dense.row.mult.VectorVectorMult_DDRM;
import org.ejml.interfaces.linsol.LinearSolverDense;
import org.ejml.ops.DOperatorUnary;
import org.jetbrains.annotations.Nullable;

public class CommonOps_DDRM {
    public static <T extends DMatrix1Row> T mult(T a, T b, @Nullable T output) {
        output = UtilEjml.reshapeOrDeclare(output, a, a.numRows, b.numCols);
        UtilEjml.checkSameInstance(a, output);
        UtilEjml.checkSameInstance(b, output);
        if (b.numCols == 1) {
            MatrixVectorMult_DDRM.mult(a, b, output);
        } else if (b.numCols >= EjmlParameters.MULT_COLUMN_SWITCH) {
            MatrixMatrixMult_DDRM.mult_reorder(a, b, output);
        } else {
            MatrixMatrixMult_DDRM.mult_small(a, b, output);
        }
        return output;
    }

    public static <T extends DMatrix1Row> T mult(double alpha, T a, T b, @Nullable T output) {
        output = UtilEjml.reshapeOrDeclare(output, a, a.numRows, b.numCols);
        UtilEjml.checkSameInstance(a, output);
        UtilEjml.checkSameInstance(b, output);
        if (b.numCols >= EjmlParameters.MULT_COLUMN_SWITCH) {
            MatrixMatrixMult_DDRM.mult_reorder(alpha, a, b, output);
        } else {
            MatrixMatrixMult_DDRM.mult_small(alpha, a, b, output);
        }
        return output;
    }

    public static <T extends DMatrix1Row> T multTransA(T a, T b, @Nullable T output) {
        output = UtilEjml.reshapeOrDeclare(output, a, a.numCols, b.numCols);
        UtilEjml.checkSameInstance(a, output);
        UtilEjml.checkSameInstance(b, output);
        if (b.numCols == 1) {
            if (a.numCols >= EjmlParameters.MULT_COLUMN_SWITCH) {
                MatrixVectorMult_DDRM.multTransA_reorder(a, b, output);
            } else {
                MatrixVectorMult_DDRM.multTransA_small(a, b, output);
            }
        } else if (a.numCols >= EjmlParameters.MULT_COLUMN_SWITCH || b.numCols >= EjmlParameters.MULT_COLUMN_SWITCH) {
            MatrixMatrixMult_DDRM.multTransA_reorder(a, b, output);
        } else {
            MatrixMatrixMult_DDRM.multTransA_small(a, b, output);
        }
        return output;
    }

    public static <T extends DMatrix1Row> T multTransA(double alpha, T a, T b, @Nullable T output) {
        output = UtilEjml.reshapeOrDeclare(output, a, a.numCols, b.numCols);
        UtilEjml.checkSameInstance(a, output);
        UtilEjml.checkSameInstance(b, output);
        if (a.numCols >= EjmlParameters.MULT_COLUMN_SWITCH || b.numCols >= EjmlParameters.MULT_COLUMN_SWITCH) {
            MatrixMatrixMult_DDRM.multTransA_reorder(alpha, a, b, output);
        } else {
            MatrixMatrixMult_DDRM.multTransA_small(alpha, a, b, output);
        }
        return output;
    }

    public static <T extends DMatrix1Row> T multTransB(T a, T b, @Nullable T output) {
        output = UtilEjml.reshapeOrDeclare(output, a, a.numRows, b.numRows);
        UtilEjml.checkSameInstance(a, output);
        UtilEjml.checkSameInstance(b, output);
        if (b.numRows == 1) {
            MatrixVectorMult_DDRM.mult(a, b, output);
        } else {
            MatrixMatrixMult_DDRM.multTransB(a, b, output);
        }
        return output;
    }

    public static <T extends DMatrix1Row> T multTransB(double alpha, T a, T b, @Nullable T output) {
        output = UtilEjml.reshapeOrDeclare(output, a, a.numRows, b.numRows);
        UtilEjml.checkSameInstance(a, output);
        UtilEjml.checkSameInstance(b, output);
        MatrixMatrixMult_DDRM.multTransB(alpha, a, b, output);
        return output;
    }

    public static <T extends DMatrix1Row> T multTransAB(T a, T b, @Nullable T output) {
        output = UtilEjml.reshapeOrDeclare(output, a, a.numCols, b.numRows);
        UtilEjml.checkSameInstance(a, output);
        UtilEjml.checkSameInstance(b, output);
        if (b.numRows == 1) {
            if (a.numCols >= EjmlParameters.MULT_COLUMN_SWITCH) {
                MatrixVectorMult_DDRM.multTransA_reorder(a, b, output);
            } else {
                MatrixVectorMult_DDRM.multTransA_small(a, b, output);
            }
        } else if (a.numCols >= EjmlParameters.MULT_TRANAB_COLUMN_SWITCH) {
            MatrixMatrixMult_DDRM.multTransAB_aux(a, b, output, null);
        } else {
            MatrixMatrixMult_DDRM.multTransAB(a, b, output);
        }
        return output;
    }

    public static <T extends DMatrix1Row> T multTransAB(double alpha, T a, T b, @Nullable T output) {
        output = UtilEjml.reshapeOrDeclare(output, a, a.numCols, b.numRows);
        UtilEjml.checkSameInstance(a, output);
        UtilEjml.checkSameInstance(b, output);
        if (a.numCols >= EjmlParameters.MULT_TRANAB_COLUMN_SWITCH) {
            MatrixMatrixMult_DDRM.multTransAB_aux(alpha, a, b, output, null);
        } else {
            MatrixMatrixMult_DDRM.multTransAB(alpha, a, b, output);
        }
        return output;
    }

    public static double dot(DMatrixD1 a, DMatrixD1 b) {
        if (!MatrixFeatures_DDRM.isVector(a) || !MatrixFeatures_DDRM.isVector(b)) {
            throw new RuntimeException("Both inputs must be vectors");
        }
        return VectorVectorMult_DDRM.innerProd(a, b);
    }

    public static <T extends DMatrix1Row> T multInner(T a, @Nullable T output) {
        output = UtilEjml.reshapeOrDeclare(output, a, a.numCols, a.numCols);
        if (a.numCols >= EjmlParameters.MULT_INNER_SWITCH) {
            MatrixMultProduct_DDRM.inner_small(a, output);
        } else {
            MatrixMultProduct_DDRM.inner_reorder(a, output);
        }
        return output;
    }

    public static <T extends DMatrix1Row> T multOuter(T a, @Nullable T output) {
        output = UtilEjml.reshapeOrDeclare(output, a, a.numRows, a.numRows);
        MatrixMultProduct_DDRM.outer(a, output);
        return output;
    }

    public static void multAdd(DMatrix1Row a, DMatrix1Row b, DMatrix1Row c) {
        if (b.numCols == 1) {
            MatrixVectorMult_DDRM.multAdd(a, b, c);
        } else if (b.numCols >= EjmlParameters.MULT_COLUMN_SWITCH) {
            MatrixMatrixMult_DDRM.multAdd_reorder(a, b, c);
        } else {
            MatrixMatrixMult_DDRM.multAdd_small(a, b, c);
        }
    }

    public static void multAdd(double alpha, DMatrix1Row a, DMatrix1Row b, DMatrix1Row c) {
        if (b.numCols >= EjmlParameters.MULT_COLUMN_SWITCH) {
            MatrixMatrixMult_DDRM.multAdd_reorder(alpha, a, b, c);
        } else {
            MatrixMatrixMult_DDRM.multAdd_small(alpha, a, b, c);
        }
    }

    public static void multAddTransA(DMatrix1Row a, DMatrix1Row b, DMatrix1Row c) {
        if (b.numCols == 1) {
            if (a.numCols >= EjmlParameters.MULT_COLUMN_SWITCH) {
                MatrixVectorMult_DDRM.multAddTransA_reorder(a, b, c);
            } else {
                MatrixVectorMult_DDRM.multAddTransA_small(a, b, c);
            }
        } else if (a.numCols >= EjmlParameters.MULT_COLUMN_SWITCH || b.numCols >= EjmlParameters.MULT_COLUMN_SWITCH) {
            MatrixMatrixMult_DDRM.multAddTransA_reorder(a, b, c);
        } else {
            MatrixMatrixMult_DDRM.multAddTransA_small(a, b, c);
        }
    }

    public static void multAddTransA(double alpha, DMatrix1Row a, DMatrix1Row b, DMatrix1Row c) {
        if (a.numCols >= EjmlParameters.MULT_COLUMN_SWITCH || b.numCols >= EjmlParameters.MULT_COLUMN_SWITCH) {
            MatrixMatrixMult_DDRM.multAddTransA_reorder(alpha, a, b, c);
        } else {
            MatrixMatrixMult_DDRM.multAddTransA_small(alpha, a, b, c);
        }
    }

    public static void multAddTransB(DMatrix1Row a, DMatrix1Row b, DMatrix1Row c) {
        MatrixMatrixMult_DDRM.multAddTransB(a, b, c);
    }

    public static void multAddTransB(double alpha, DMatrix1Row a, DMatrix1Row b, DMatrix1Row c) {
        MatrixMatrixMult_DDRM.multAddTransB(alpha, a, b, c);
    }

    public static void multAddTransAB(DMatrix1Row a, DMatrix1Row b, DMatrix1Row c) {
        if (a.numCols >= EjmlParameters.MULT_TRANAB_COLUMN_SWITCH) {
            MatrixMatrixMult_DDRM.multAddTransAB_aux(a, b, c, null);
        } else {
            MatrixMatrixMult_DDRM.multAddTransAB(a, b, c);
        }
    }

    public static void multAddTransAB(double alpha, DMatrix1Row a, DMatrix1Row b, DMatrix1Row c) {
        if (a.numCols >= EjmlParameters.MULT_TRANAB_COLUMN_SWITCH) {
            MatrixMatrixMult_DDRM.multAddTransAB_aux(alpha, a, b, c, null);
        } else {
            MatrixMatrixMult_DDRM.multAddTransAB(alpha, a, b, c);
        }
    }

    public static boolean solve(DMatrixRMaj a, DMatrixRMaj b, DMatrixRMaj x) {
        x.reshape(a.numCols, b.numCols);
        LinearSolverDense<DMatrixRMaj> solver = LinearSolverFactory_DDRM.general(a.numRows, a.numCols);
        solver = new LinearSolverSafe<DMatrixRMaj>(solver);
        if (!solver.setA(a)) {
            return false;
        }
        solver.solve(b, x);
        return true;
    }

    public static boolean solveSPD(DMatrixRMaj A, DMatrixRMaj b, DMatrixRMaj x) {
        if (A.numRows != A.numCols) {
            throw new IllegalArgumentException("Must be a square matrix");
        }
        x.reshape(A.numCols, b.numCols);
        if (A.numRows <= 7) {
            DMatrixRMaj L = A.createLike();
            if (!UnrolledCholesky_DDRM.lower(A, L)) {
                return false;
            }
            if (x.numCols == 1) {
                x.setTo(b);
                TriangularSolver_DDRM.solveL(L.data, x.data, L.numCols);
                TriangularSolver_DDRM.solveTranL(L.data, x.data, L.numCols);
            } else {
                double[] vv = new double[A.numCols];
                LinearSolverChol_DDRM.solveLower(L, b, x, vv);
            }
        } else {
            LinearSolverDense<DMatrixRMaj> solver = LinearSolverFactory_DDRM.chol(A.numCols);
            if (!(solver = new LinearSolverSafe<DMatrixRMaj>(solver)).setA(A)) {
                return false;
            }
            solver.solve(b, x);
            return true;
        }
        return true;
    }

    public static void transpose(DMatrixRMaj mat) {
        if (mat.numCols == mat.numRows) {
            TransposeAlgs_DDRM.square(mat);
        } else {
            DMatrixRMaj b = new DMatrixRMaj(mat.numCols, mat.numRows);
            CommonOps_DDRM.transpose(mat, b);
            mat.setTo(b);
        }
    }

    public static DMatrixRMaj transpose(DMatrixRMaj A, @Nullable DMatrixRMaj A_tran) {
        A_tran = UtilEjml.reshapeOrDeclare(A_tran, A.numCols, A.numRows);
        if (A.numRows > EjmlParameters.TRANSPOSE_SWITCH && A.numCols > EjmlParameters.TRANSPOSE_SWITCH) {
            TransposeAlgs_DDRM.block(A, A_tran, EjmlParameters.BLOCK_WIDTH);
        } else {
            TransposeAlgs_DDRM.standard(A, A_tran);
        }
        return A_tran;
    }

    public static double trace(DMatrix1Row a) {
        int N = Math.min(a.numRows, a.numCols);
        double sum = 0.0;
        int index = 0;
        for (int i = 0; i < N; ++i) {
            sum += a.get(index);
            index += 1 + a.numCols;
        }
        return sum;
    }

    public static double det(DMatrixRMaj mat) {
        int numRow;
        int numCol = mat.getNumCols();
        if (numCol != (numRow = mat.getNumRows())) {
            throw new MatrixDimensionException("Must be a square matrix.");
        }
        if (numCol <= 6) {
            if (numCol >= 2) {
                return UnrolledDeterminantFromMinor_DDRM.det(mat);
            }
            return mat.get(0);
        }
        LUDecompositionAlt_DDRM alg = new LUDecompositionAlt_DDRM();
        if (alg.inputModified()) {
            mat = mat.copy();
        }
        if (!alg.decompose(mat)) {
            return 0.0;
        }
        return alg.computeDeterminant().real;
    }

    public static boolean invert(DMatrixRMaj mat) {
        if (mat.numCols <= 5) {
            if (mat.numCols != mat.numRows) {
                throw new MatrixDimensionException("Must be a square matrix.");
            }
            if (mat.numCols >= 2) {
                UnrolledInverseFromMinor_DDRM.inv(mat, mat);
            } else {
                mat.set(0, 1.0 / mat.get(0));
            }
        } else {
            LUDecompositionAlt_DDRM alg = new LUDecompositionAlt_DDRM();
            LinearSolverLu_DDRM solver = new LinearSolverLu_DDRM(alg);
            if (solver.setA(mat)) {
                solver.invert(mat);
            } else {
                return false;
            }
        }
        return true;
    }

    public static boolean invert(DMatrixRMaj mat, DMatrixRMaj result) {
        result.reshape(mat.numRows, mat.numCols);
        if (mat.numCols <= 5) {
            if (mat.numCols != mat.numRows) {
                throw new MatrixDimensionException("Must be a square matrix.");
            }
            if (result.numCols >= 2) {
                UnrolledInverseFromMinor_DDRM.inv(mat, result);
            } else {
                result.set(0, 1.0 / mat.get(0));
            }
        } else {
            LUDecompositionAlt_DDRM alg = new LUDecompositionAlt_DDRM();
            LinearSolverLu_DDRM solver = new LinearSolverLu_DDRM(alg);
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

    public static boolean invertSPD(DMatrixRMaj mat, DMatrixRMaj result) {
        if (mat.numRows != mat.numCols) {
            throw new IllegalArgumentException("Must be a square matrix");
        }
        result.reshape(mat.numRows, mat.numRows);
        if (mat.numRows <= 7) {
            if (!UnrolledCholesky_DDRM.lower(mat, result)) {
                return false;
            }
            TriangularSolver_DDRM.invertLower(result.data, result.numCols);
            SpecializedOps_DDRM.multLowerTranA(result);
        } else {
            LinearSolverDense<DMatrixRMaj> solver = LinearSolverFactory_DDRM.chol(mat.numCols);
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

    public static void pinv(DMatrixRMaj A, DMatrixRMaj invA) {
        LinearSolverDense<DMatrixRMaj> solver = LinearSolverFactory_DDRM.pseudoInverse(true);
        if (solver.modifiesA()) {
            A = A.copy();
        }
        if (!solver.setA(A)) {
            throw new IllegalArgumentException("Invert failed, maybe a bug?");
        }
        solver.invert(invA);
    }

    public static DMatrixRMaj[] columnsToVector(DMatrixRMaj A, @Nullable DMatrixRMaj[] v) {
        DMatrixRMaj[] ret = v == null || v.length < A.numCols ? new DMatrixRMaj[A.numCols] : v;
        for (int i = 0; i < ret.length; ++i) {
            if (ret[i] == null) {
                ret[i] = new DMatrixRMaj(A.numRows, 1);
            } else {
                ret[i].reshape(A.numRows, 1, false);
            }
            DMatrixRMaj u = ret[i];
            for (int j = 0; j < A.numRows; ++j) {
                u.set(j, 0, A.get(j, i));
            }
        }
        return ret;
    }

    public static DMatrixRMaj[] rowsToVector(DMatrixRMaj A, @Nullable DMatrixRMaj[] v) {
        DMatrixRMaj[] ret = v == null || v.length < A.numRows ? new DMatrixRMaj[A.numRows] : v;
        for (int i = 0; i < ret.length; ++i) {
            if (ret[i] == null) {
                ret[i] = new DMatrixRMaj(A.numCols, 1);
            } else {
                ret[i].reshape(A.numCols, 1, false);
            }
            DMatrixRMaj u = ret[i];
            for (int j = 0; j < A.numCols; ++j) {
                u.set(j, 0, A.get(i, j));
            }
        }
        return ret;
    }

    public static void setIdentity(DMatrix1Row mat) {
        int width = mat.numRows < mat.numCols ? mat.numRows : mat.numCols;
        Arrays.fill(mat.data, 0, mat.getNumElements(), 0.0);
        int index = 0;
        int i = 0;
        while (i < width) {
            mat.data[index] = 1.0;
            ++i;
            index += mat.numCols + 1;
        }
    }

    public static DMatrixRMaj identity(int width) {
        DMatrixRMaj ret = new DMatrixRMaj(width, width);
        for (int i = 0; i < width; ++i) {
            ret.set(i, i, 1.0);
        }
        return ret;
    }

    public static DMatrixRMaj identity(int numRows, int numCols) {
        DMatrixRMaj ret = new DMatrixRMaj(numRows, numCols);
        int small = numRows < numCols ? numRows : numCols;
        for (int i = 0; i < small; ++i) {
            ret.set(i, i, 1.0);
        }
        return ret;
    }

    public static DMatrixRMaj diag(double ... diagEl) {
        return CommonOps_DDRM.diag(null, diagEl.length, diagEl);
    }

    public static DMatrixRMaj diag(@Nullable DMatrixRMaj ret, int width, double ... diagEl) {
        if (ret == null) {
            ret = new DMatrixRMaj(width, width);
        } else {
            if (ret.numRows != width || ret.numCols != width) {
                throw new IllegalArgumentException("Unexpected matrix size");
            }
            CommonOps_DDRM.fill(ret, 0.0);
        }
        for (int i = 0; i < width; ++i) {
            ret.unsafe_set(i, i, diagEl[i]);
        }
        return ret;
    }

    public static DMatrixRMaj diagR(int numRows, int numCols, double ... diagEl) {
        DMatrixRMaj ret = new DMatrixRMaj(numRows, numCols);
        int o = Math.min(numRows, numCols);
        for (int i = 0; i < o; ++i) {
            ret.set(i, i, diagEl[i]);
        }
        return ret;
    }

    public static DMatrixRMaj kron(DMatrixRMaj A, DMatrixRMaj B, @Nullable DMatrixRMaj C) {
        int numColsC = A.numCols * B.numCols;
        int numRowsC = A.numRows * B.numRows;
        C = UtilEjml.reshapeOrDeclare(C, numRowsC, numColsC);
        for (int i = 0; i < A.numRows; ++i) {
            for (int j = 0; j < A.numCols; ++j) {
                double a = A.get(i, j);
                for (int rowB = 0; rowB < B.numRows; ++rowB) {
                    for (int colB = 0; colB < B.numCols; ++colB) {
                        double val = a * B.get(rowB, colB);
                        C.unsafe_set(i * B.numRows + rowB, j * B.numCols + colB, val);
                    }
                }
            }
        }
        return C;
    }

    public static void extract(DMatrix src, int srcY0, int srcY1, int srcX0, int srcX1, DMatrix dst, int dstY0, int dstX0) {
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
        if (src instanceof DMatrixRMaj && dst instanceof DMatrixRMaj) {
            ImplCommonOps_DDRM.extract((DMatrixRMaj)src, srcY0, srcX0, (DMatrixRMaj)dst, dstY0, dstX0, h, w);
        } else {
            ImplCommonOps_DDMA.extract(src, srcY0, srcX0, dst, dstY0, dstX0, h, w);
        }
    }

    public static void extract(DMatrix src, int srcY0, int srcY1, int srcX0, int srcX1, DMatrix dst) {
        ((ReshapeMatrix)((Object)dst)).reshape(srcY1 - srcY0, srcX1 - srcX0);
        CommonOps_DDRM.extract(src, srcY0, srcY1, srcX0, srcX1, dst, 0, 0);
    }

    public static void extract(DMatrix src, int srcY0, int srcX0, DMatrix dst) {
        CommonOps_DDRM.extract(src, srcY0, srcY0 + dst.getNumRows(), srcX0, srcX0 + dst.getNumCols(), dst, 0, 0);
    }

    public static DMatrixRMaj extract(DMatrixRMaj src, int srcY0, int srcY1, int srcX0, int srcX1) {
        if (srcY1 <= srcY0 || srcY0 < 0 || srcY1 > src.numRows) {
            throw new MatrixDimensionException("srcY1 <= srcY0 || srcY0 < 0 || srcY1 > src.numRows");
        }
        if (srcX1 <= srcX0 || srcX0 < 0 || srcX1 > src.numCols) {
            throw new MatrixDimensionException("srcX1 <= srcX0 || srcX0 < 0 || srcX1 > src.numCols");
        }
        int w = srcX1 - srcX0;
        int h = srcY1 - srcY0;
        DMatrixRMaj dst = new DMatrixRMaj(h, w);
        ImplCommonOps_DDRM.extract(src, srcY0, srcX0, dst, 0, 0, h, w);
        return dst;
    }

    public static DMatrixRMaj extract(DMatrixRMaj src, int[] rows, int rowsSize, int[] cols, int colsSize, @Nullable DMatrixRMaj dst) {
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

    public static DMatrixRMaj extract(DMatrixRMaj src, int[] indexes, int length, @Nullable DMatrixRMaj dst) {
        if (dst == null) {
            dst = new DMatrixRMaj(length, 1);
        } else if (!MatrixFeatures_DDRM.isVector(dst) || dst.getNumElements() != length) {
            dst.reshape(length, 1);
        }
        for (int i = 0; i < length; ++i) {
            dst.data[i] = src.data[indexes[i]];
        }
        return dst;
    }

    public static void insert(DMatrixRMaj src, DMatrixRMaj dst, int[] rows, int rowsSize, int[] cols, int colsSize) {
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

    public static DMatrixRMaj extractDiag(DMatrixRMaj src, @Nullable DMatrixRMaj dst) {
        int N = Math.min(src.numRows, src.numCols);
        if (dst == null) {
            dst = new DMatrixRMaj(N, 1);
        } else if (!MatrixFeatures_DDRM.isVector(dst) || dst.numCols * dst.numCols != N) {
            dst.reshape(N, 1);
        }
        for (int i = 0; i < N; ++i) {
            dst.set(i, src.unsafe_get(i, i));
        }
        return dst;
    }

    public static DMatrixRMaj extractRow(DMatrixRMaj a, int row, @Nullable DMatrixRMaj out) {
        if (out == null) {
            out = new DMatrixRMaj(1, a.numCols);
        } else if (!MatrixFeatures_DDRM.isVector(out) || out.getNumElements() != a.numCols) {
            out.reshape(1, a.numCols);
        }
        System.arraycopy(a.data, a.getIndex(row, 0), out.data, 0, a.numCols);
        return out;
    }

    public static DMatrixRMaj extractColumn(DMatrixRMaj a, int column, @Nullable DMatrixRMaj out) {
        if (out == null) {
            out = new DMatrixRMaj(a.numRows, 1);
        } else if (!MatrixFeatures_DDRM.isVector(out) || out.getNumElements() != a.numRows) {
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

    public static void removeColumns(DMatrixRMaj A, int col0, int col1) {
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

    public static void insert(DMatrix src, DMatrix dest, int destY0, int destX0) {
        CommonOps_DDRM.extract(src, 0, src.getNumRows(), 0, src.getNumCols(), dest, destY0, destX0);
    }

    public static double elementMax(DMatrixD1 a) {
        return ImplCommonOps_DDRM.elementMax(a, null);
    }

    public static double elementMax(DMatrixD1 a, ElementLocation loc) {
        return ImplCommonOps_DDRM.elementMax(a, loc);
    }

    public static double elementMaxAbs(DMatrixD1 a) {
        return ImplCommonOps_DDRM.elementMaxAbs(a, null);
    }

    public static double elementMaxAbs(DMatrixD1 a, ElementLocation loc) {
        return ImplCommonOps_DDRM.elementMaxAbs(a, loc);
    }

    public static double elementMin(DMatrixD1 a) {
        return ImplCommonOps_DDRM.elementMin(a, null);
    }

    public static double elementMin(DMatrixD1 a, ElementLocation loc) {
        return ImplCommonOps_DDRM.elementMin(a, loc);
    }

    public static double elementMinAbs(DMatrixD1 a) {
        return ImplCommonOps_DDRM.elementMinAbs(a, null);
    }

    public static double elementMinAbs(DMatrixD1 a, ElementLocation loc) {
        return ImplCommonOps_DDRM.elementMinAbs(a, loc);
    }

    public static void elementMult(DMatrixD1 A, DMatrixD1 B) {
        ImplCommonOps_DDRM.elementMult(A, B);
    }

    public static <T extends DMatrixD1> T elementMult(T A, T B, @Nullable T output) {
        return ImplCommonOps_DDRM.elementMult(A, B, output);
    }

    public static void elementDiv(DMatrixD1 A, DMatrixD1 B) {
        ImplCommonOps_DDRM.elementDiv(A, B);
    }

    public static <T extends DMatrixD1> T elementDiv(T A, T B, @Nullable T output) {
        return ImplCommonOps_DDRM.elementDiv(A, B, output);
    }

    public static double elementSum(DMatrixD1 mat) {
        return ImplCommonOps_DDRM.elementSum(mat);
    }

    public static double elementSumAbs(DMatrixD1 mat) {
        return ImplCommonOps_DDRM.elementSumAbs(mat);
    }

    public static <T extends DMatrixD1> T elementPower(T A, T B, @Nullable T output) {
        return ImplCommonOps_DDRM.elementPower(A, B, output);
    }

    public static <T extends DMatrixD1> T elementPower(double a, T B, @Nullable T output) {
        return ImplCommonOps_DDRM.elementPower(a, B, output);
    }

    public static <T extends DMatrixD1> T elementPower(T A, double b, @Nullable T output) {
        return ImplCommonOps_DDRM.elementPower(A, b, output);
    }

    public static <T extends DMatrixD1> T elementLog(T A, @Nullable T output) {
        return ImplCommonOps_DDRM.elementLog(A, output);
    }

    public static <T extends DMatrixD1> T elementExp(T A, @Nullable T output) {
        return ImplCommonOps_DDRM.elementExp(A, output);
    }

    public static void multRows(double[] values, DMatrixRMaj A) {
        if (values.length < A.numRows) {
            throw new IllegalArgumentException("Not enough elements in values.");
        }
        int index = 0;
        for (int row = 0; row < A.numRows; ++row) {
            double v = values[row];
            for (int col = 0; col < A.numCols; ++col) {
                int n = index++;
                A.data[n] = A.data[n] * v;
            }
        }
    }

    public static void divideRows(double[] values, DMatrixRMaj A) {
        if (values.length < A.numRows) {
            throw new IllegalArgumentException("Not enough elements in values.");
        }
        int index = 0;
        for (int row = 0; row < A.numRows; ++row) {
            double v = values[row];
            for (int col = 0; col < A.numCols; ++col) {
                int n = index++;
                A.data[n] = A.data[n] / v;
            }
        }
    }

    public static void multCols(DMatrixRMaj A, double[] values) {
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

    public static void divideCols(DMatrixRMaj A, double[] values) {
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

    public static void divideRowsCols(double[] diagA, int offsetA, DMatrixRMaj B, double[] diagC, int offsetC) {
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
            double va = diagA[offsetA + row];
            for (int col = 0; col < cols; ++col) {
                int n = index++;
                B.data[n] = B.data[n] / (va * diagC[offsetC + col]);
            }
        }
    }

    public static DMatrixRMaj sumRows(DMatrixRMaj input, @Nullable DMatrixRMaj output) {
        output = UtilEjml.reshapeOrDeclare(output, input.numRows, 1);
        for (int row = 0; row < input.numRows; ++row) {
            double total = 0.0;
            int end = (row + 1) * input.numCols;
            for (int index = row * input.numCols; index < end; ++index) {
                total += input.data[index];
            }
            output.set(row, total);
        }
        return output;
    }

    public static DMatrixRMaj minRows(DMatrixRMaj input, @Nullable DMatrixRMaj output) {
        output = UtilEjml.reshapeOrDeclare(output, input.numRows, 1);
        for (int row = 0; row < input.numRows; ++row) {
            double min = Double.MAX_VALUE;
            int end = (row + 1) * input.numCols;
            for (int index = row * input.numCols; index < end; ++index) {
                double v = input.data[index];
                if (!(v < min)) continue;
                min = v;
            }
            output.set(row, min);
        }
        return output;
    }

    public static DMatrixRMaj maxRows(DMatrixRMaj input, @Nullable DMatrixRMaj output) {
        output = UtilEjml.reshapeOrDeclare(output, input.numRows, 1);
        for (int row = 0; row < input.numRows; ++row) {
            double max = -1.7976931348623157E308;
            int end = (row + 1) * input.numCols;
            for (int index = row * input.numCols; index < end; ++index) {
                double v = input.data[index];
                if (!(v > max)) continue;
                max = v;
            }
            output.set(row, max);
        }
        return output;
    }

    public static DMatrixRMaj sumCols(DMatrixRMaj input, @Nullable DMatrixRMaj output) {
        output = UtilEjml.reshapeOrDeclare(output, 1, input.numCols);
        for (int cols = 0; cols < input.numCols; ++cols) {
            int index;
            double total = 0.0;
            int end = index + input.numCols * input.numRows;
            for (index = cols; index < end; index += input.numCols) {
                total += input.data[index];
            }
            output.set(cols, total);
        }
        return output;
    }

    public static DMatrixRMaj minCols(DMatrixRMaj input, @Nullable DMatrixRMaj output) {
        output = UtilEjml.reshapeOrDeclare(output, 1, input.numCols);
        for (int cols = 0; cols < input.numCols; ++cols) {
            int index;
            double minimum = Double.MAX_VALUE;
            int end = index + input.numCols * input.numRows;
            for (index = cols; index < end; index += input.numCols) {
                double v = input.data[index];
                if (!(v < minimum)) continue;
                minimum = v;
            }
            output.set(cols, minimum);
        }
        return output;
    }

    public static DMatrixRMaj maxCols(DMatrixRMaj input, @Nullable DMatrixRMaj output) {
        output = UtilEjml.reshapeOrDeclare(output, 1, input.numCols);
        for (int cols = 0; cols < input.numCols; ++cols) {
            int index;
            double maximum = -1.7976931348623157E308;
            int end = index + input.numCols * input.numRows;
            for (index = cols; index < end; index += input.numCols) {
                double v = input.data[index];
                if (!(v > maximum)) continue;
                maximum = v;
            }
            output.set(cols, maximum);
        }
        return output;
    }

    public static void addEquals(DMatrixD1 a, DMatrixD1 b) {
        UtilEjml.checkSameShape((Matrix)a, (Matrix)b, true);
        int length = a.getNumElements();
        for (int i = 0; i < length; ++i) {
            a.plus(i, b.get(i));
        }
    }

    public static void addEquals(DMatrixD1 a, double beta, DMatrixD1 b) {
        UtilEjml.checkSameShape((Matrix)a, (Matrix)b, true);
        int length = a.getNumElements();
        for (int i = 0; i < length; ++i) {
            a.plus(i, beta * b.get(i));
        }
    }

    public static <T extends DMatrixD1> T add(T a, T b, @Nullable T output) {
        UtilEjml.checkSameShape(a, b, true);
        output = UtilEjml.reshapeOrDeclare(output, a);
        int length = a.getNumElements();
        for (int i = 0; i < length; ++i) {
            output.set(i, a.get(i) + b.get(i));
        }
        return output;
    }

    public static <T extends DMatrixD1> T add(T a, double beta, T b, @Nullable T output) {
        UtilEjml.checkSameShape(a, b, true);
        output = UtilEjml.reshapeOrDeclare(output, a);
        int length = a.getNumElements();
        for (int i = 0; i < length; ++i) {
            output.set(i, a.get(i) + beta * b.get(i));
        }
        return output;
    }

    public static <T extends DMatrixD1> T add(double alpha, T a, double beta, T b, @Nullable T output) {
        UtilEjml.checkSameShape(a, b, true);
        output = UtilEjml.reshapeOrDeclare(output, a);
        int length = a.getNumElements();
        for (int i = 0; i < length; ++i) {
            output.set(i, alpha * a.get(i) + beta * b.get(i));
        }
        return output;
    }

    public static <T extends DMatrixD1> T add(double alpha, T a, T b, T output) {
        UtilEjml.checkSameShape(a, b, true);
        output = UtilEjml.reshapeOrDeclare(output, a);
        int length = a.getNumElements();
        for (int i = 0; i < length; ++i) {
            output.set(i, alpha * a.get(i) + b.get(i));
        }
        return output;
    }

    public static void add(DMatrixD1 a, double val) {
        int length = a.getNumElements();
        for (int i = 0; i < length; ++i) {
            a.plus(i, val);
        }
    }

    public static <T extends DMatrixD1> T add(T a, double val, T output) {
        output = UtilEjml.reshapeOrDeclare(output, a);
        int length = a.getNumElements();
        for (int i = 0; i < length; ++i) {
            output.data[i] = a.data[i] + val;
        }
        return output;
    }

    public static <T extends DMatrixD1> T subtract(T a, double val, @Nullable T output) {
        output = UtilEjml.reshapeOrDeclare(output, a);
        int length = a.getNumElements();
        for (int i = 0; i < length; ++i) {
            output.data[i] = a.data[i] - val;
        }
        return output;
    }

    public static <T extends DMatrixD1> T subtract(double val, T a, @Nullable T output) {
        output = UtilEjml.reshapeOrDeclare(output, a);
        int length = a.getNumElements();
        for (int i = 0; i < length; ++i) {
            output.data[i] = val - a.data[i];
        }
        return output;
    }

    public static void subtractEquals(DMatrixD1 a, DMatrixD1 b) {
        UtilEjml.checkSameShape((Matrix)a, (Matrix)b, true);
        int length = a.getNumElements();
        for (int i = 0; i < length; ++i) {
            int n = i;
            a.data[n] = a.data[n] - b.data[i];
        }
    }

    public static <T extends DMatrixD1> T subtract(T a, T b, @Nullable T output) {
        UtilEjml.checkSameShape(a, b, true);
        output = UtilEjml.reshapeOrDeclare(output, a);
        int length = a.getNumElements();
        for (int i = 0; i < length; ++i) {
            output.data[i] = a.data[i] - b.data[i];
        }
        return output;
    }

    public static void scale(double alpha, DMatrixD1 a) {
        int size = a.getNumElements();
        int i = 0;
        while (i < size) {
            int n = i++;
            a.data[n] = a.data[n] * alpha;
        }
    }

    public static void scale(double alpha, DMatrixD1 a, DMatrixD1 b) {
        b.reshape(a.numRows, a.numCols);
        int size = a.getNumElements();
        for (int i = 0; i < size; ++i) {
            b.data[i] = a.data[i] * alpha;
        }
    }

    public static void scaleRow(double alpha, DMatrixRMaj A, int row) {
        int idx = row * A.numCols;
        for (int col = 0; col < A.numCols; ++col) {
            int n = idx++;
            A.data[n] = A.data[n] * alpha;
        }
    }

    public static void scaleCol(double alpha, DMatrixRMaj A, int col) {
        int idx = col;
        int row = 0;
        while (row < A.numRows) {
            int n = idx;
            A.data[n] = A.data[n] * alpha;
            ++row;
            idx += A.numCols;
        }
    }

    public static void divide(double alpha, DMatrixD1 a) {
        int size = a.getNumElements();
        for (int i = 0; i < size; ++i) {
            a.data[i] = alpha / a.data[i];
        }
    }

    public static void divide(DMatrixD1 a, double alpha) {
        int size = a.getNumElements();
        int i = 0;
        while (i < size) {
            int n = i++;
            a.data[n] = a.data[n] / alpha;
        }
    }

    public static <T extends DMatrixD1> T divide(double alpha, T input, T output) {
        output = UtilEjml.reshapeOrDeclare(output, input);
        int size = input.getNumElements();
        for (int i = 0; i < size; ++i) {
            output.data[i] = alpha / input.data[i];
        }
        return output;
    }

    public static <T extends DMatrixD1> T divide(T input, double alpha, @Nullable T output) {
        output = UtilEjml.reshapeOrDeclare(output, input);
        int size = input.getNumElements();
        for (int i = 0; i < size; ++i) {
            output.data[i] = input.data[i] / alpha;
        }
        return output;
    }

    public static void changeSign(DMatrixD1 a) {
        int size = a.getNumElements();
        for (int i = 0; i < size; ++i) {
            a.data[i] = -a.data[i];
        }
    }

    public static <T extends DMatrixD1> T changeSign(T input, @Nullable T output) {
        output = UtilEjml.reshapeOrDeclare(output, input);
        int size = input.getNumElements();
        for (int i = 0; i < size; ++i) {
            output.data[i] = -input.data[i];
        }
        return output;
    }

    public static void fill(DMatrixD1 a, double value) {
        Arrays.fill(a.data, 0, a.getNumElements(), value);
    }

    public static DMatrixRMaj rref(DMatrixRMaj A, int numUnknowns, @Nullable DMatrixRMaj reduced) {
        reduced = UtilEjml.reshapeOrDeclare(reduced, A);
        if (numUnknowns <= 0) {
            numUnknowns = A.numCols;
        }
        RrefGaussJordanRowPivot_DDRM alg = new RrefGaussJordanRowPivot_DDRM();
        alg.setTolerance(CommonOps_DDRM.elementMaxAbs(A) * UtilEjml.EPS * (double)Math.max(A.numRows, A.numCols));
        reduced.setTo(A);
        alg.reduce(reduced, numUnknowns);
        return reduced;
    }

    public static BMatrixRMaj elementLessThan(DMatrixRMaj A, double value, BMatrixRMaj output) {
        output = UtilEjml.reshapeOrDeclare(output, A.numRows, A.numCols);
        int N = A.getNumElements();
        for (int i = 0; i < N; ++i) {
            output.data[i] = A.data[i] < value;
        }
        return output;
    }

    public static BMatrixRMaj elementLessThanOrEqual(DMatrixRMaj A, double value, BMatrixRMaj output) {
        output = UtilEjml.reshapeOrDeclare(output, A.numRows, A.numCols);
        int N = A.getNumElements();
        for (int i = 0; i < N; ++i) {
            output.data[i] = A.data[i] <= value;
        }
        return output;
    }

    public static BMatrixRMaj elementMoreThan(DMatrixRMaj A, double value, BMatrixRMaj output) {
        output = UtilEjml.reshapeOrDeclare(output, A.numRows, A.numCols);
        int N = A.getNumElements();
        for (int i = 0; i < N; ++i) {
            output.data[i] = A.data[i] > value;
        }
        return output;
    }

    public static BMatrixRMaj elementMoreThanOrEqual(DMatrixRMaj A, double value, BMatrixRMaj output) {
        output = UtilEjml.reshapeOrDeclare(output, A.numRows, A.numCols);
        int N = A.getNumElements();
        for (int i = 0; i < N; ++i) {
            output.data[i] = A.data[i] >= value;
        }
        return output;
    }

    public static BMatrixRMaj elementLessThan(DMatrixRMaj A, DMatrixRMaj B, BMatrixRMaj output) {
        output = UtilEjml.reshapeOrDeclare(output, A.numRows, A.numCols);
        int N = A.getNumElements();
        for (int i = 0; i < N; ++i) {
            output.data[i] = A.data[i] < B.data[i];
        }
        return output;
    }

    public static BMatrixRMaj elementLessThanOrEqual(DMatrixRMaj A, DMatrixRMaj B, BMatrixRMaj output) {
        output = UtilEjml.reshapeOrDeclare(output, A.numRows, A.numCols);
        int N = A.getNumElements();
        for (int i = 0; i < N; ++i) {
            output.data[i] = A.data[i] <= B.data[i];
        }
        return output;
    }

    public static DMatrixRMaj elements(DMatrixRMaj A, BMatrixRMaj marked, @Nullable DMatrixRMaj output) {
        UtilEjml.checkSameShape((Matrix)A, (Matrix)marked, false);
        if (output == null) {
            output = new DMatrixRMaj(1, 1);
        }
        output.reshape(CommonOps_DDRM.countTrue(marked), 1);
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

    public static DMatrixRMaj concatColumns(DMatrixRMaj a, DMatrixRMaj b, @Nullable DMatrixRMaj output) {
        int rows = Math.max(a.numRows, b.numRows);
        int cols = a.numCols + b.numCols;
        output = UtilEjml.reshapeOrDeclare(output, rows, cols);
        output.zero();
        CommonOps_DDRM.insert(a, output, 0, 0);
        CommonOps_DDRM.insert(b, output, 0, a.numCols);
        return output;
    }

    public static DMatrixRMaj concatColumnsMulti(DMatrixRMaj ... m) {
        int rows = 0;
        int cols = 0;
        for (int i = 0; i < m.length; ++i) {
            rows = Math.max(rows, m[i].numRows);
            cols += m[i].numCols;
        }
        DMatrixRMaj R = new DMatrixRMaj(rows, cols);
        int col = 0;
        for (int i = 0; i < m.length; ++i) {
            CommonOps_DDRM.insert(m[i], R, 0, col);
            col += m[i].numCols;
        }
        return R;
    }

    public static void concatRows(DMatrixRMaj a, DMatrixRMaj b, DMatrixRMaj output) {
        int rows = a.numRows + b.numRows;
        int cols = Math.max(a.numCols, b.numCols);
        output.reshape(rows, cols);
        output.zero();
        CommonOps_DDRM.insert(a, output, 0, 0);
        CommonOps_DDRM.insert(b, output, a.numRows, 0);
    }

    public static DMatrixRMaj concatRowsMulti(DMatrixRMaj ... m) {
        int rows = 0;
        int cols = 0;
        for (int i = 0; i < m.length; ++i) {
            rows += m[i].numRows;
            cols = Math.max(cols, m[i].numCols);
        }
        DMatrixRMaj R = new DMatrixRMaj(rows, cols);
        int row = 0;
        for (int i = 0; i < m.length; ++i) {
            CommonOps_DDRM.insert(m[i], R, row, 0);
            row += m[i].numRows;
        }
        return R;
    }

    public static DMatrixRMaj permuteRowInv(int[] pinv, DMatrixRMaj input, DMatrixRMaj output) {
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

    public static void abs(DMatrixD1 a, DMatrixD1 c) {
        c.reshape(a.numRows, a.numCols);
        int length = a.getNumElements();
        for (int i = 0; i < length; ++i) {
            c.data[i] = Math.abs(a.data[i]);
        }
    }

    public static void abs(DMatrixD1 a) {
        int length = a.getNumElements();
        for (int i = 0; i < length; ++i) {
            a.data[i] = Math.abs(a.data[i]);
        }
    }

    public static void symmLowerToFull(DMatrixRMaj A) {
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

    public static void symmUpperToFull(DMatrixRMaj A) {
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

    public static DMatrixRMaj apply(DMatrixRMaj input, DOperatorUnary func, @Nullable DMatrixRMaj output) {
        output = UtilEjml.reshapeOrDeclare(output, input.numRows, input.numCols);
        for (int i = 0; i < input.data.length; ++i) {
            output.data[i] = func.apply(input.data[i]);
        }
        return output;
    }

    public static DMatrixRMaj apply(DMatrixRMaj input, DOperatorUnary func) {
        return CommonOps_DDRM.apply(input, func, input);
    }
}

