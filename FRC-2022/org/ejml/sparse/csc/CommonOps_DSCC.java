/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  org.jetbrains.annotations.Nullable
 */
package org.ejml.sparse.csc;

import java.util.Arrays;
import org.ejml.MatrixDimensionException;
import org.ejml.UtilEjml;
import org.ejml.data.DGrowArray;
import org.ejml.data.DMatrixRMaj;
import org.ejml.data.DMatrixSparseCSC;
import org.ejml.data.IGrowArray;
import org.ejml.dense.row.CommonOps_DDRM;
import org.ejml.interfaces.decomposition.LUSparseDecomposition_F64;
import org.ejml.interfaces.linsol.LinearSolverSparse;
import org.ejml.masks.Mask;
import org.ejml.ops.DOperatorBinary;
import org.ejml.ops.DOperatorBinaryIdx;
import org.ejml.ops.DOperatorUnary;
import org.ejml.ops.IPredicateBinary;
import org.ejml.sparse.FillReducing;
import org.ejml.sparse.csc.MatrixFeatures_DSCC;
import org.ejml.sparse.csc.factory.DecompositionFactory_DSCC;
import org.ejml.sparse.csc.factory.LinearSolverFactory_DSCC;
import org.ejml.sparse.csc.misc.ImplCommonOps_DSCC;
import org.ejml.sparse.csc.mult.ImplMultiplication_DSCC;
import org.jetbrains.annotations.Nullable;

public class CommonOps_DSCC {
    public static boolean checkIndicesSorted(DMatrixSparseCSC A) {
        for (int j = 0; j < A.numCols; ++j) {
            int idx0 = A.col_idx[j];
            int idx1 = A.col_idx[j + 1];
            if (idx0 != idx1 && A.nz_rows[idx0] >= A.numRows) {
                return false;
            }
            for (int i = idx0 + 1; i < idx1; ++i) {
                int row = A.nz_rows[i];
                if (A.nz_rows[i - 1] >= row) {
                    return false;
                }
                if (row < A.numRows) continue;
                return false;
            }
        }
        return true;
    }

    public static boolean checkStructure(DMatrixSparseCSC A) {
        if (A.col_idx.length < A.numCols + 1) {
            return false;
        }
        if (A.col_idx[A.numCols] != A.nz_length) {
            return false;
        }
        if (A.nz_rows.length < A.nz_length) {
            return false;
        }
        if (A.nz_values.length < A.nz_length) {
            return false;
        }
        if (A.col_idx[0] != 0) {
            return false;
        }
        for (int i = 0; i < A.numCols; ++i) {
            if (A.col_idx[i] > A.col_idx[i + 1]) {
                return false;
            }
            if (A.col_idx[i + 1] - A.col_idx[i] <= A.numRows) continue;
            return false;
        }
        if (!CommonOps_DSCC.checkSortedFlag(A)) {
            return false;
        }
        return !CommonOps_DSCC.checkDuplicateElements(A);
    }

    public static boolean checkSortedFlag(DMatrixSparseCSC A) {
        if (A.indicesSorted) {
            return CommonOps_DSCC.checkIndicesSorted(A);
        }
        return true;
    }

    public static boolean checkDuplicateElements(DMatrixSparseCSC A) {
        A = A.copy();
        A.sortIndices(null);
        return !CommonOps_DSCC.checkSortedFlag(A);
    }

    public static DMatrixSparseCSC transpose(DMatrixSparseCSC A, @Nullable DMatrixSparseCSC A_t, @Nullable IGrowArray gw) {
        A_t = UtilEjml.reshapeOrDeclare(A_t, A.numCols, A.numRows, A.nz_length);
        ImplCommonOps_DSCC.transpose(A, A_t, gw);
        return A_t;
    }

    public static DMatrixSparseCSC mult(DMatrixSparseCSC A, DMatrixSparseCSC B, @Nullable DMatrixSparseCSC outputC) {
        return CommonOps_DSCC.mult(A, B, outputC, null, null);
    }

    public static DMatrixSparseCSC mult(DMatrixSparseCSC A, DMatrixSparseCSC B, @Nullable DMatrixSparseCSC outputC, @Nullable IGrowArray gw, @Nullable DGrowArray gx) {
        if (A.numCols != B.numRows) {
            throw new MatrixDimensionException("Inconsistent matrix shapes. " + UtilEjml.stringShapes(A, B));
        }
        outputC = UtilEjml.reshapeOrDeclare(outputC, A, A.numRows, B.numCols);
        ImplMultiplication_DSCC.mult(A, B, outputC, gw, gx);
        return outputC;
    }

    public static DMatrixRMaj mult(DMatrixSparseCSC A, DMatrixRMaj B, @Nullable DMatrixRMaj outputC) {
        if (A.numCols != B.numRows) {
            throw new MatrixDimensionException("Inconsistent matrix shapes. " + UtilEjml.stringShapes(A, B));
        }
        outputC = UtilEjml.reshapeOrDeclare(outputC, A.numRows, B.numCols);
        ImplMultiplication_DSCC.mult(A, B, outputC);
        return outputC;
    }

    public static void multAdd(DMatrixSparseCSC A, DMatrixRMaj B, DMatrixRMaj outputC) {
        if (A.numCols != B.numRows) {
            throw new MatrixDimensionException("Inconsistent matrix shapes. " + UtilEjml.stringShapes(A, B));
        }
        if (A.numRows != outputC.numRows || B.numCols != outputC.numCols) {
            throw new MatrixDimensionException("Inconsistent matrix shapes. " + UtilEjml.stringShapes(A, B, outputC));
        }
        ImplMultiplication_DSCC.multAdd(A, B, outputC);
    }

    public static DMatrixRMaj multTransA(DMatrixSparseCSC A, DMatrixRMaj B, @Nullable DMatrixRMaj outputC, @Nullable DGrowArray work) {
        if (A.numRows != B.numRows) {
            throw new MatrixDimensionException("Inconsistent matrix shapes. " + UtilEjml.stringShapes(A, B));
        }
        if (work == null) {
            work = new DGrowArray();
        }
        outputC = UtilEjml.reshapeOrDeclare(outputC, A.numCols, B.numCols);
        ImplMultiplication_DSCC.multTransA(A, B, outputC, work);
        return outputC;
    }

    public static void multAddTransA(DMatrixSparseCSC A, DMatrixRMaj B, DMatrixRMaj outputC, @Nullable DGrowArray work) {
        if (A.numRows != B.numRows) {
            throw new MatrixDimensionException("Inconsistent matrix shapes. " + UtilEjml.stringShapes(A, B));
        }
        if (A.numCols != outputC.numRows || B.numCols != outputC.numCols) {
            throw new MatrixDimensionException("Inconsistent matrix shapes. " + UtilEjml.stringShapes(A, B, outputC));
        }
        if (work == null) {
            work = new DGrowArray();
        }
        ImplMultiplication_DSCC.multAddTransA(A, B, outputC, work);
    }

    public static DMatrixRMaj multTransB(DMatrixSparseCSC A, DMatrixRMaj B, @Nullable DMatrixRMaj outputC, @Nullable DGrowArray work) {
        if (A.numCols != B.numCols) {
            throw new MatrixDimensionException("Inconsistent matrix shapes. " + UtilEjml.stringShapes(A, B));
        }
        outputC = UtilEjml.reshapeOrDeclare(outputC, A.numRows, B.numRows);
        if (work == null) {
            work = new DGrowArray();
        }
        ImplMultiplication_DSCC.multTransB(A, B, outputC, work);
        return outputC;
    }

    public static void multAddTransB(DMatrixSparseCSC A, DMatrixRMaj B, DMatrixRMaj outputC, @Nullable DGrowArray work) {
        if (A.numCols != B.numCols) {
            throw new MatrixDimensionException("Inconsistent matrix shapes. " + UtilEjml.stringShapes(A, B));
        }
        if (A.numRows != outputC.numRows || B.numRows != outputC.numCols) {
            throw new MatrixDimensionException("Inconsistent matrix shapes. " + UtilEjml.stringShapes(A, B, outputC));
        }
        if (work == null) {
            work = new DGrowArray();
        }
        ImplMultiplication_DSCC.multAddTransB(A, B, outputC, work);
    }

    public static DMatrixRMaj multTransAB(DMatrixSparseCSC A, DMatrixRMaj B, DMatrixRMaj outputC) {
        if (A.numRows != B.numCols) {
            throw new MatrixDimensionException("Inconsistent matrix shapes. " + UtilEjml.stringShapes(A, B));
        }
        outputC = UtilEjml.reshapeOrDeclare(outputC, A.numCols, B.numRows);
        ImplMultiplication_DSCC.multTransAB(A, B, outputC);
        return outputC;
    }

    public static void multAddTransAB(DMatrixSparseCSC A, DMatrixRMaj B, DMatrixRMaj outputC) {
        if (A.numRows != B.numCols) {
            throw new MatrixDimensionException("Inconsistent matrix shapes. " + UtilEjml.stringShapes(A, B));
        }
        if (A.numCols != outputC.numRows || B.numRows != outputC.numCols) {
            throw new MatrixDimensionException("Inconsistent matrix shapes. " + UtilEjml.stringShapes(A, B, outputC));
        }
        ImplMultiplication_DSCC.multAddTransAB(A, B, outputC);
    }

    public static void symmLowerToFull(DMatrixSparseCSC A, DMatrixSparseCSC outputB, @Nullable IGrowArray gw) {
        ImplCommonOps_DSCC.symmLowerToFull(A, outputB, gw);
    }

    public static DMatrixSparseCSC add(double alpha, DMatrixSparseCSC A, double beta, DMatrixSparseCSC B, @Nullable DMatrixSparseCSC outputC, @Nullable IGrowArray gw, @Nullable DGrowArray gx) {
        if (A.numRows != B.numRows || A.numCols != B.numCols) {
            throw new MatrixDimensionException("Inconsistent matrix shapes. " + UtilEjml.stringShapes(A, B));
        }
        outputC = UtilEjml.reshapeOrDeclare(outputC, A, A.numRows, A.numCols);
        ImplCommonOps_DSCC.add(alpha, A, beta, B, outputC, gw, gx);
        return outputC;
    }

    public static DMatrixSparseCSC identity(int length) {
        return CommonOps_DSCC.identity(length, length);
    }

    public static DMatrixSparseCSC identity(int numRows, int numCols) {
        int min = Math.min(numRows, numCols);
        DMatrixSparseCSC A = new DMatrixSparseCSC(numRows, numCols, min);
        CommonOps_DSCC.setIdentity(A);
        return A;
    }

    public static void setIdentity(DMatrixSparseCSC A) {
        int i;
        int min = Math.min(A.numRows, A.numCols);
        A.growMaxLength(min, false);
        A.nz_length = min;
        Arrays.fill(A.nz_values, 0, min, 1.0);
        for (i = 1; i <= min; ++i) {
            A.col_idx[i] = i;
            A.nz_rows[i - 1] = i - 1;
        }
        for (i = min + 1; i <= A.numCols; ++i) {
            A.col_idx[i] = min;
        }
    }

    public static void scale(double scalar, DMatrixSparseCSC A, DMatrixSparseCSC outputB) {
        if (A != outputB) {
            outputB.copyStructure(A);
            for (int i = 0; i < A.nz_length; ++i) {
                outputB.nz_values[i] = A.nz_values[i] * scalar;
            }
        } else {
            int i = 0;
            while (i < A.nz_length) {
                int n = i++;
                outputB.nz_values[n] = outputB.nz_values[n] * scalar;
            }
        }
    }

    public static void divide(DMatrixSparseCSC A, double scalar, DMatrixSparseCSC outputB) {
        if (A != outputB) {
            outputB.copyStructure(A);
            for (int i = 0; i < A.nz_length; ++i) {
                outputB.nz_values[i] = A.nz_values[i] / scalar;
            }
        } else {
            int i = 0;
            while (i < A.nz_length) {
                int n = i++;
                A.nz_values[n] = A.nz_values[n] / scalar;
            }
        }
    }

    public static void divide(double scalar, DMatrixSparseCSC A, DMatrixSparseCSC outputB) {
        if (A != outputB) {
            outputB.copyStructure(A);
        }
        for (int i = 0; i < A.nz_length; ++i) {
            outputB.nz_values[i] = scalar / A.nz_values[i];
        }
    }

    public static void changeSign(DMatrixSparseCSC A, DMatrixSparseCSC outputB) {
        if (A != outputB) {
            outputB.copyStructure(A);
        }
        for (int i = 0; i < A.nz_length; ++i) {
            outputB.nz_values[i] = -A.nz_values[i];
        }
    }

    public static double elementMinAbs(DMatrixSparseCSC A) {
        if (A.nz_length == 0) {
            return 0.0;
        }
        double min = A.isFull() ? Math.abs(A.nz_values[0]) : 0.0;
        for (int i = 0; i < A.nz_length; ++i) {
            double val = Math.abs(A.nz_values[i]);
            if (!(val < min)) continue;
            min = val;
        }
        return min;
    }

    public static double elementMaxAbs(DMatrixSparseCSC A) {
        if (A.nz_length == 0) {
            return 0.0;
        }
        double max = A.isFull() ? Math.abs(A.nz_values[0]) : 0.0;
        for (int i = 0; i < A.nz_length; ++i) {
            double val = Math.abs(A.nz_values[i]);
            if (!(val > max)) continue;
            max = val;
        }
        return max;
    }

    public static double elementMin(DMatrixSparseCSC A) {
        if (A.nz_length == 0) {
            return 0.0;
        }
        double min = A.isFull() ? A.nz_values[0] : 0.0;
        for (int i = 0; i < A.nz_length; ++i) {
            double val = A.nz_values[i];
            if (!(val < min)) continue;
            min = val;
        }
        return min;
    }

    public static double elementMax(DMatrixSparseCSC A) {
        if (A.nz_length == 0) {
            return 0.0;
        }
        double max = A.isFull() ? A.nz_values[0] : 0.0;
        for (int i = 0; i < A.nz_length; ++i) {
            double val = A.nz_values[i];
            if (!(val > max)) continue;
            max = val;
        }
        return max;
    }

    public static double elementSum(DMatrixSparseCSC A) {
        if (A.nz_length == 0) {
            return 0.0;
        }
        double sum = 0.0;
        for (int i = 0; i < A.nz_length; ++i) {
            sum += A.nz_values[i];
        }
        return sum;
    }

    public static DMatrixSparseCSC elementMult(DMatrixSparseCSC A, DMatrixSparseCSC B, @Nullable DMatrixSparseCSC output, @Nullable IGrowArray gw, @Nullable DGrowArray gx) {
        if (A.numCols != B.numCols || A.numRows != B.numRows) {
            throw new MatrixDimensionException("All inputs must have the same number of rows and columns. " + UtilEjml.stringShapes(A, B));
        }
        output = UtilEjml.reshapeOrDeclare(output, A, A.numRows, A.numCols);
        ImplCommonOps_DSCC.elementMult(A, B, output, gw, gx);
        return output;
    }

    public static void maxAbsCols(DMatrixSparseCSC A, @Nullable DMatrixRMaj outputB) {
        outputB = UtilEjml.reshapeOrDeclare(outputB, 1, A.numCols);
        for (int i = 0; i < A.numCols; ++i) {
            int idx0 = A.col_idx[i];
            int idx1 = A.col_idx[i + 1];
            double maxabs = 0.0;
            for (int j = idx0; j < idx1; ++j) {
                double v = Math.abs(A.nz_values[j]);
                if (!(v > maxabs)) continue;
                maxabs = v;
            }
            outputB.data[i] = maxabs;
        }
    }

    public static void multColumns(DMatrixSparseCSC A, double[] values, int offset) {
        if (values.length + offset < A.numCols) {
            throw new IllegalArgumentException("Array is too small. " + values.length + " < " + A.numCols);
        }
        for (int i = 0; i < A.numCols; ++i) {
            int idx0 = A.col_idx[i];
            int idx1 = A.col_idx[i + 1];
            double v = values[offset + i];
            int j = idx0;
            while (j < idx1) {
                int n = j++;
                A.nz_values[n] = A.nz_values[n] * v;
            }
        }
    }

    public static void divideColumns(DMatrixSparseCSC A, double[] values, int offset) {
        if (values.length + offset < A.numCols) {
            throw new IllegalArgumentException("Array is too small. " + values.length + " < " + A.numCols);
        }
        for (int i = 0; i < A.numCols; ++i) {
            int idx0 = A.col_idx[i];
            int idx1 = A.col_idx[i + 1];
            double v = values[offset + i];
            int j = idx0;
            while (j < idx1) {
                int n = j++;
                A.nz_values[n] = A.nz_values[n] / v;
            }
        }
    }

    public static void multRowsCols(double[] diagA, int offsetA, DMatrixSparseCSC B, double[] diagC, int offsetC) {
        if (diagA.length + offsetA < B.numRows) {
            throw new IllegalArgumentException("diagA is too small.");
        }
        if (diagC.length + offsetC < B.numCols) {
            throw new IllegalArgumentException("diagA is too small.");
        }
        for (int i = 0; i < B.numCols; ++i) {
            int idx0 = B.col_idx[i];
            int idx1 = B.col_idx[i + 1];
            double c = diagC[offsetC + i];
            for (int j = idx0; j < idx1; ++j) {
                int n = j;
                B.nz_values[n] = B.nz_values[n] * (c * diagA[offsetA + B.nz_rows[j]]);
            }
        }
    }

    public static void divideRowsCols(double[] diagA, int offsetA, DMatrixSparseCSC B, double[] diagC, int offsetC) {
        if (diagA.length + offsetA < B.numRows) {
            throw new IllegalArgumentException("diagA is too small.");
        }
        if (diagC.length + offsetC < B.numCols) {
            throw new IllegalArgumentException("diagA is too small.");
        }
        for (int i = 0; i < B.numCols; ++i) {
            int idx0 = B.col_idx[i];
            int idx1 = B.col_idx[i + 1];
            double c = diagC[offsetC + i];
            for (int j = idx0; j < idx1; ++j) {
                int n = j;
                B.nz_values[n] = B.nz_values[n] / (c * diagA[offsetA + B.nz_rows[j]]);
            }
        }
    }

    public static DMatrixSparseCSC diag(double ... values) {
        int N = values.length;
        return CommonOps_DSCC.diag(new DMatrixSparseCSC(N, N, N), values, 0, N);
    }

    public static DMatrixSparseCSC diag(@Nullable DMatrixSparseCSC A, double[] values, int offset, int length) {
        int N = length;
        if (A == null) {
            A = new DMatrixSparseCSC(N, N, N);
        } else {
            A.reshape(N, N, N);
        }
        A.nz_length = N;
        for (int i = 0; i < N; ++i) {
            A.col_idx[i + 1] = i + 1;
            A.nz_rows[i] = i;
            A.nz_values[i] = values[offset + i];
        }
        return A;
    }

    public static void extractDiag(DMatrixSparseCSC A, DMatrixSparseCSC outputB) {
        int N = Math.min(A.numRows, A.numCols);
        if (!MatrixFeatures_DSCC.isVector(outputB)) {
            outputB.reshape(N, 1, N);
        } else if (outputB.numRows * outputB.numCols != N) {
            outputB.reshape(N, 1, N);
        } else {
            outputB.growMaxLength(N, false);
        }
        outputB.nz_length = N;
        outputB.indicesSorted = true;
        if (outputB.numRows != 1) {
            outputB.col_idx[0] = 0;
            outputB.col_idx[1] = N;
            for (int i = 0; i < N; ++i) {
                outputB.nz_values[i] = A.unsafe_get(i, i);
                outputB.nz_rows[i] = i;
            }
        } else {
            outputB.col_idx[0] = 0;
            for (int i = 0; i < N; ++i) {
                outputB.nz_values[i] = A.unsafe_get(i, i);
                outputB.nz_rows[i] = 0;
                outputB.col_idx[i + 1] = i + 1;
            }
        }
    }

    public static void extractDiag(DMatrixSparseCSC A, DMatrixRMaj outputB) {
        int N = Math.min(A.numRows, A.numCols);
        if (outputB.getNumElements() != N || outputB.numRows != 1 && outputB.numCols != 1) {
            outputB.reshape(N, 1);
        }
        for (int i = 0; i < N; ++i) {
            outputB.data[i] = A.unsafe_get(i, i);
        }
    }

    public static DMatrixSparseCSC permutationMatrix(int[] p, boolean inverse, int N, @Nullable DMatrixSparseCSC P) {
        if (P == null) {
            P = new DMatrixSparseCSC(N, N, N);
        } else {
            P.reshape(N, N, N);
        }
        P.indicesSorted = true;
        P.nz_length = N;
        if (!inverse) {
            for (int i = 0; i < N; ++i) {
                P.col_idx[i + 1] = i + 1;
                P.nz_rows[p[i]] = i;
                P.nz_values[i] = 1.0;
            }
        } else {
            for (int i = 0; i < N; ++i) {
                P.col_idx[i + 1] = i + 1;
                P.nz_rows[i] = p[i];
                P.nz_values[i] = 1.0;
            }
        }
        return P;
    }

    public static void permutationVector(DMatrixSparseCSC P, int[] vector) {
        if (P.numCols != P.numRows) {
            throw new MatrixDimensionException("Expected a square matrix");
        }
        if (P.nz_length != P.numCols) {
            throw new IllegalArgumentException("Expected N non-zero elements in permutation matrix");
        }
        if (vector.length < P.numCols) {
            throw new IllegalArgumentException("vector is too short");
        }
        int M = P.numCols;
        for (int i = 0; i < M; ++i) {
            if (P.col_idx[i + 1] != i + 1) {
                throw new IllegalArgumentException("Unexpected number of elements in a column");
            }
            vector[P.nz_rows[i]] = i;
        }
    }

    public static void permutationInverse(int[] original, int[] inverse, int length) {
        for (int i = 0; i < length; ++i) {
            inverse[original[i]] = i;
        }
    }

    public static int[] permutationInverse(int[] original, int length) {
        int[] inverse = new int[length];
        CommonOps_DSCC.permutationInverse(original, inverse, length);
        return inverse;
    }

    public static void permuteRowInv(int[] permInv, DMatrixSparseCSC input, DMatrixSparseCSC output) {
        if (input.numRows > permInv.length) {
            throw new IllegalArgumentException("permutation vector must have at least as many elements as input has rows");
        }
        output.reshape(input.numRows, input.numCols, input.nz_length);
        output.nz_length = input.nz_length;
        output.indicesSorted = false;
        System.arraycopy(input.nz_values, 0, output.nz_values, 0, input.nz_length);
        System.arraycopy(input.col_idx, 0, output.col_idx, 0, input.numCols + 1);
        int idx0 = 0;
        for (int i = 0; i < input.numCols; ++i) {
            int idx1 = output.col_idx[i + 1];
            for (int j = idx0; j < idx1; ++j) {
                output.nz_rows[j] = permInv[input.nz_rows[j]];
            }
            idx0 = idx1;
        }
    }

    public static void permute(@Nullable int[] permRowInv, DMatrixSparseCSC input, @Nullable int[] permCol, DMatrixSparseCSC output) {
        if (permRowInv != null && input.numRows > permRowInv.length) {
            throw new IllegalArgumentException("rowInv permutation vector must have at least as many elements as input has columns");
        }
        if (permCol != null && input.numCols > permCol.length) {
            throw new IllegalArgumentException("permCol permutation vector must have at least as many elements as input has rows");
        }
        output.reshape(input.numRows, input.numCols, input.nz_length);
        output.indicesSorted = false;
        output.nz_length = input.nz_length;
        int N = input.numCols;
        int outputNZ = 0;
        for (int i = 0; i < N; ++i) {
            int inputCol = permCol != null ? permCol[i] : i;
            int inputNZ = input.col_idx[inputCol];
            int total = input.col_idx[inputCol + 1] - inputNZ;
            output.col_idx[i + 1] = output.col_idx[i] + total;
            for (int j = 0; j < total; ++j) {
                int row = input.nz_rows[inputNZ];
                output.nz_rows[outputNZ] = permRowInv != null ? permRowInv[row] : row;
                output.nz_values[outputNZ++] = input.nz_values[inputNZ++];
            }
        }
    }

    public static void permute(int[] perm, double[] input, double[] output, int N) {
        for (int k = 0; k < N; ++k) {
            output[k] = input[perm[k]];
        }
    }

    public static void permuteInv(int[] perm, double[] input, double[] output, int N) {
        for (int k = 0; k < N; ++k) {
            output[perm[k]] = input[k];
        }
    }

    public static void permuteSymmetric(DMatrixSparseCSC input, int[] permInv, DMatrixSparseCSC output, @Nullable IGrowArray gw) {
        int i2;
        int i;
        int p;
        int idx1;
        int idx0;
        int j2;
        int j;
        if (input.numRows != input.numCols) {
            throw new MatrixDimensionException("Input must be a square matrix. " + UtilEjml.stringShapes(input, output));
        }
        if (input.numRows != permInv.length) {
            throw new MatrixDimensionException("Number of column in input must match length of permInv");
        }
        int N = input.numCols;
        int[] w = UtilEjml.adjustClear(gw, N);
        output.reshape(N, N, 0);
        output.indicesSorted = false;
        output.col_idx[0] = 0;
        for (j = 0; j < N; ++j) {
            j2 = permInv[j];
            idx0 = input.col_idx[j];
            idx1 = input.col_idx[j + 1];
            for (p = idx0; p < idx1; ++p) {
                i = input.nz_rows[p];
                if (i > j) continue;
                i2 = permInv[i];
                int n = i2 > j2 ? i2 : j2;
                w[n] = w[n] + 1;
            }
        }
        output.histogramToStructure(w);
        System.arraycopy(output.col_idx, 0, w, 0, output.numCols);
        for (j = 0; j < N; ++j) {
            j2 = permInv[j];
            idx0 = input.col_idx[j];
            idx1 = input.col_idx[j + 1];
            for (p = idx0; p < idx1; ++p) {
                i = input.nz_rows[p];
                if (i > j) continue;
                i2 = permInv[i];
                int n = i2 > j2 ? i2 : j2;
                w[n] = w[n] + 1;
                output.nz_rows[q] = i2 < j2 ? i2 : j2;
                output.nz_values[q] = input.nz_values[p];
            }
        }
    }

    public static DMatrixSparseCSC concatRows(DMatrixSparseCSC top, DMatrixSparseCSC bottom, @Nullable DMatrixSparseCSC out) {
        if (top.numCols != bottom.numCols) {
            throw new MatrixDimensionException("Number of columns must match. " + UtilEjml.stringShapes(top, bottom));
        }
        if (out == null) {
            out = new DMatrixSparseCSC(0, 0, 0);
        }
        out.reshape(top.numRows + bottom.numRows, top.numCols, top.nz_length + bottom.nz_length);
        out.nz_length = top.nz_length + bottom.nz_length;
        int index = 0;
        for (int i = 0; i < top.numCols; ++i) {
            int out1;
            int top0 = top.col_idx[i];
            int top1 = top.col_idx[i + 1];
            int bot0 = bottom.col_idx[i];
            int bot1 = bottom.col_idx[i + 1];
            int out0 = out.col_idx[i];
            out.col_idx[i + 1] = out1 = out0 + top1 - top0 + bot1 - bot0;
            int j = top0;
            while (j < top1) {
                out.nz_values[index] = top.nz_values[j];
                out.nz_rows[index] = top.nz_rows[j];
                ++j;
                ++index;
            }
            j = bot0;
            while (j < bot1) {
                out.nz_values[index] = bottom.nz_values[j];
                out.nz_rows[index] = top.numRows + bottom.nz_rows[j];
                ++j;
                ++index;
            }
        }
        out.indicesSorted = false;
        return out;
    }

    public static DMatrixSparseCSC concatColumns(DMatrixSparseCSC left, DMatrixSparseCSC right, @Nullable DMatrixSparseCSC out) {
        if (left.numRows != right.numRows) {
            throw new MatrixDimensionException("Number of rows must match. " + UtilEjml.stringShapes(left, right));
        }
        if (out == null) {
            out = new DMatrixSparseCSC(0, 0, 0);
        }
        out.reshape(left.numRows, left.numCols + right.numCols, left.nz_length + right.nz_length);
        out.nz_length = left.nz_length + right.nz_length;
        System.arraycopy(left.col_idx, 0, out.col_idx, 0, left.numCols + 1);
        System.arraycopy(left.nz_rows, 0, out.nz_rows, 0, left.nz_length);
        System.arraycopy(left.nz_values, 0, out.nz_values, 0, left.nz_length);
        int index = left.nz_length;
        for (int i = 0; i < right.numCols; ++i) {
            int r0 = right.col_idx[i];
            int r1 = right.col_idx[i + 1];
            out.col_idx[left.numCols + i] = index;
            out.col_idx[left.numCols + i + 1] = index + (r1 - r0);
            int j = r0;
            while (j < r1) {
                out.nz_rows[index] = right.nz_rows[j];
                out.nz_values[index] = right.nz_values[j];
                ++j;
                ++index;
            }
        }
        out.indicesSorted = left.indicesSorted && right.indicesSorted;
        return out;
    }

    public static DMatrixSparseCSC extractColumn(DMatrixSparseCSC A, int column, @Nullable DMatrixSparseCSC out) {
        if (out == null) {
            out = new DMatrixSparseCSC(1, 1, 1);
        }
        int idx0 = A.col_idx[column];
        int idx1 = A.col_idx[column + 1];
        out.reshape(A.numRows, 1, idx1 - idx0);
        out.nz_length = idx1 - idx0;
        out.col_idx[0] = 0;
        out.col_idx[1] = out.nz_length;
        System.arraycopy(A.nz_values, idx0, out.nz_values, 0, out.nz_length);
        System.arraycopy(A.nz_rows, idx0, out.nz_rows, 0, out.nz_length);
        return out;
    }

    public static DMatrixSparseCSC extractRows(DMatrixSparseCSC A, int row0, int row1, @Nullable DMatrixSparseCSC out) {
        if (out == null) {
            out = new DMatrixSparseCSC(1, 1, 1);
        }
        out.reshape(row1 - row0, A.numCols, A.nz_length);
        for (int col = 0; col < A.numCols; ++col) {
            int idx0 = A.col_idx[col];
            int idx1 = A.col_idx[col + 1];
            for (int i = idx0; i < idx1; ++i) {
                int row = A.nz_rows[i];
                if (row < row0 || row >= row1) continue;
                out.nz_values[out.nz_length] = A.nz_values[i];
                out.nz_rows[out.nz_length++] = row - row0;
            }
            out.col_idx[col + 1] = out.nz_length;
        }
        return out;
    }

    public static void extract(DMatrixSparseCSC src, int srcY0, int srcY1, int srcX0, int srcX1, DMatrixSparseCSC dst, int dstY0, int dstX0) {
        if (srcY1 < srcY0 || srcY0 < 0 || srcY1 > src.getNumRows()) {
            throw new MatrixDimensionException("srcY1 < srcY0 || srcY0 < 0 || srcY1 > src.numRows. " + UtilEjml.stringShapes(src, dst));
        }
        if (srcX1 < srcX0 || srcX0 < 0 || srcX1 > src.getNumCols()) {
            throw new MatrixDimensionException("srcX1 < srcX0 || srcX0 < 0 || srcX1 > src.numCols. " + UtilEjml.stringShapes(src, dst));
        }
        int w = srcX1 - srcX0;
        int h = srcY1 - srcY0;
        if (dstY0 + h > dst.getNumRows()) {
            throw new IllegalArgumentException("dst is too small in rows. " + dst.getNumRows() + " < " + (dstY0 + h));
        }
        if (dstX0 + w > dst.getNumCols()) {
            throw new IllegalArgumentException("dst is too small in columns. " + dst.getNumCols() + " < " + (dstX0 + w));
        }
        CommonOps_DSCC.zero(dst, dstY0, dstY0 + h, dstX0, dstX0 + w);
        for (int colSrc = srcX0; colSrc < srcX1; ++colSrc) {
            int idxS0 = src.col_idx[colSrc];
            int idxS1 = src.col_idx[colSrc + 1];
            for (int i = idxS0; i < idxS1; ++i) {
                int row = src.nz_rows[i];
                if (row < srcY0 || row >= srcY1) continue;
                dst.set(row - srcY0 + dstY0, colSrc - srcX0 + dstX0, src.nz_values[i]);
            }
        }
    }

    public static DMatrixSparseCSC select(DMatrixSparseCSC A, IPredicateBinary selector, @Nullable DMatrixSparseCSC output) {
        if (output != A) {
            output = UtilEjml.reshapeOrDeclare(output, A);
        }
        ImplCommonOps_DSCC.select(A, output, selector);
        return output;
    }

    public static void fill(DMatrixSparseCSC A, double value) {
        int N = A.numCols * A.numRows;
        A.growMaxLength(N, false);
        A.col_idx[0] = 0;
        for (int col = 0; col < A.numCols; ++col) {
            int idx0 = A.col_idx[col];
            int n = idx0 + A.numRows;
            A.col_idx[col + 1] = n;
            int idx1 = n;
            for (int i = idx0; i < idx1; ++i) {
                A.nz_rows[i] = i - idx0;
                A.nz_values[i] = value;
            }
        }
        A.nz_length = N;
        A.indicesSorted = true;
    }

    public static DMatrixRMaj sumCols(DMatrixSparseCSC input, @Nullable DMatrixRMaj output) {
        if (output == null) {
            output = new DMatrixRMaj(1, input.numCols);
        } else {
            output.reshape(1, input.numCols);
        }
        for (int col = 0; col < input.numCols; ++col) {
            int idx0 = input.col_idx[col];
            int idx1 = input.col_idx[col + 1];
            double sum = 0.0;
            for (int i = idx0; i < idx1; ++i) {
                sum += input.nz_values[i];
            }
            output.data[col] = sum;
        }
        return output;
    }

    public static DMatrixRMaj minCols(DMatrixSparseCSC input, @Nullable DMatrixRMaj output) {
        output = UtilEjml.reshapeOrDeclare(output, 1, input.numCols);
        for (int col = 0; col < input.numCols; ++col) {
            int idx1 = input.col_idx[col + 1];
            int idx0 = input.col_idx[col];
            double min = idx1 - idx0 == input.numRows ? Double.MAX_VALUE : 0.0;
            for (int i = idx0; i < idx1; ++i) {
                double v = input.nz_values[i];
                if (!(min > v)) continue;
                min = v;
            }
            output.data[col] = min;
        }
        return output;
    }

    public static DMatrixRMaj maxCols(DMatrixSparseCSC input, @Nullable DMatrixRMaj output) {
        output = UtilEjml.reshapeOrDeclare(output, 1, input.numCols);
        for (int col = 0; col < input.numCols; ++col) {
            int idx1 = input.col_idx[col + 1];
            int idx0 = input.col_idx[col];
            double max = idx1 - idx0 == input.numRows ? -1.7976931348623157E308 : 0.0;
            for (int i = idx0; i < idx1; ++i) {
                double v = input.nz_values[i];
                if (!(max < v)) continue;
                max = v;
            }
            output.data[col] = max;
        }
        return output;
    }

    public static DMatrixRMaj sumRows(DMatrixSparseCSC input, @Nullable DMatrixRMaj output) {
        output = UtilEjml.reshapeOrDeclare(output, input.numRows, 1);
        Arrays.fill(output.data, 0, input.numRows, 0.0);
        for (int col = 0; col < input.numCols; ++col) {
            int idx0 = input.col_idx[col];
            int idx1 = input.col_idx[col + 1];
            for (int i = idx0; i < idx1; ++i) {
                int n = input.nz_rows[i];
                output.data[n] = output.data[n] + input.nz_values[i];
            }
        }
        return output;
    }

    public static DMatrixRMaj minRows(DMatrixSparseCSC input, @Nullable DMatrixRMaj output, @Nullable IGrowArray gw) {
        output = UtilEjml.reshapeOrDeclare(output, input.numRows, 1);
        int[] w = UtilEjml.adjust(gw, input.numRows, input.numRows);
        Arrays.fill(output.data, 0, input.numRows, Double.MAX_VALUE);
        for (int col = 0; col < input.numCols; ++col) {
            int idx0 = input.col_idx[col];
            int idx1 = input.col_idx[col + 1];
            for (int i = idx0; i < idx1; ++i) {
                int row = input.nz_rows[i];
                double v = input.nz_values[i];
                if (output.data[row] > v) {
                    output.data[row] = v;
                }
                int n = row;
                w[n] = w[n] + 1;
            }
        }
        for (int row = 0; row < input.numRows; ++row) {
            if (w[row] == input.numCols || !(output.data[row] > 0.0)) continue;
            output.data[row] = 0.0;
        }
        return output;
    }

    public static DMatrixRMaj maxRows(DMatrixSparseCSC input, @Nullable DMatrixRMaj output, @Nullable IGrowArray gw) {
        output = UtilEjml.reshapeOrDeclare(output, input.numRows, 1);
        int[] w = UtilEjml.adjust(gw, input.numRows, input.numRows);
        Arrays.fill(output.data, 0, input.numRows, -1.7976931348623157E308);
        for (int col = 0; col < input.numCols; ++col) {
            int idx0 = input.col_idx[col];
            int idx1 = input.col_idx[col + 1];
            for (int i = idx0; i < idx1; ++i) {
                int row = input.nz_rows[i];
                double v = input.nz_values[i];
                if (output.data[row] < v) {
                    output.data[row] = v;
                }
                int n = row;
                w[n] = w[n] + 1;
            }
        }
        for (int row = 0; row < input.numRows; ++row) {
            if (w[row] == input.numCols || !(output.data[row] < 0.0)) continue;
            output.data[row] = 0.0;
        }
        return output;
    }

    public static void zero(DMatrixSparseCSC A, int row0, int row1, int col0, int col1) {
        for (int col = col1 - 1; col >= col0; --col) {
            int i;
            int numRemoved = 0;
            int idx0 = A.col_idx[col];
            int idx1 = A.col_idx[col + 1];
            for (i = idx0; i < idx1; ++i) {
                int row = A.nz_rows[i];
                if (row >= row0 && row < row1) {
                    ++numRemoved;
                    continue;
                }
                if (numRemoved <= 0) continue;
                A.nz_rows[i - numRemoved] = row;
                A.nz_values[i - numRemoved] = A.nz_values[i];
            }
            if (numRemoved <= 0) continue;
            for (i = idx1; i < A.nz_length; ++i) {
                A.nz_rows[i - numRemoved] = A.nz_rows[i];
                A.nz_values[i - numRemoved] = A.nz_values[i];
            }
            A.nz_length -= numRemoved;
            i = col + 1;
            while (i <= A.numCols) {
                int n = i++;
                A.col_idx[n] = A.col_idx[n] - numRemoved;
            }
        }
    }

    public static double dotInnerColumns(DMatrixSparseCSC A, int colA, DMatrixSparseCSC B, int colB, @Nullable IGrowArray gw, @Nullable DGrowArray gx) {
        return ImplMultiplication_DSCC.dotInnerColumns(A, colA, B, colB, gw, gx);
    }

    public static boolean solve(DMatrixSparseCSC a, DMatrixRMaj b, DMatrixRMaj x) {
        x.reshape(a.numCols, b.numCols);
        LinearSolverSparse<DMatrixSparseCSC, DMatrixRMaj> solver = a.numRows > a.numCols ? LinearSolverFactory_DSCC.qr(FillReducing.NONE) : LinearSolverFactory_DSCC.lu(FillReducing.NONE);
        if (solver.modifiesA()) {
            a = a.copy();
        }
        if (solver.modifiesB()) {
            b = b.copy();
        }
        if (!solver.setA(a)) {
            return false;
        }
        solver.solve(b, x);
        return true;
    }

    public static boolean solve(DMatrixSparseCSC a, DMatrixSparseCSC b, DMatrixSparseCSC x) {
        x.reshape(a.numCols, b.numCols);
        LinearSolverSparse<DMatrixSparseCSC, DMatrixRMaj> solver = a.numRows > a.numCols ? LinearSolverFactory_DSCC.qr(FillReducing.NONE) : LinearSolverFactory_DSCC.lu(FillReducing.NONE);
        if (solver.modifiesA()) {
            a = a.copy();
        }
        if (solver.modifiesB()) {
            b = b.copy();
        }
        if (!solver.setA(a)) {
            return false;
        }
        solver.solveSparse(b, x);
        return true;
    }

    public static boolean invert(DMatrixSparseCSC A, DMatrixRMaj inverse) {
        if (A.numRows != A.numCols) {
            throw new IllegalArgumentException("A must be a square matrix");
        }
        inverse.reshape(A.numRows, A.numCols);
        LinearSolverSparse<DMatrixSparseCSC, DMatrixRMaj> solver = LinearSolverFactory_DSCC.lu(FillReducing.NONE);
        if (solver.modifiesA()) {
            A = A.copy();
        }
        DMatrixRMaj I = CommonOps_DDRM.identity(A.numRows);
        if (!solver.setA(A)) {
            return false;
        }
        solver.solve(I, inverse);
        return true;
    }

    public static double det(DMatrixSparseCSC A) {
        LUSparseDecomposition_F64<DMatrixSparseCSC> alg = DecompositionFactory_DSCC.lu(FillReducing.NONE);
        if (alg.inputModified()) {
            A = A.copy();
        }
        if (!alg.decompose(A)) {
            return 0.0;
        }
        return alg.computeDeterminant().real;
    }

    public static void removeZeros(DMatrixSparseCSC input, DMatrixSparseCSC output, double tol) {
        ImplCommonOps_DSCC.removeZeros(input, output, tol);
    }

    public static void removeZeros(DMatrixSparseCSC A, double tol) {
        ImplCommonOps_DSCC.removeZeros(A, tol);
    }

    public static void duplicatesAdd(DMatrixSparseCSC A, @Nullable IGrowArray work) {
        ImplCommonOps_DSCC.duplicatesAdd(A, work);
    }

    public static void multRows(double[] diag, int offset, DMatrixSparseCSC A) {
        if (diag.length < A.numRows) {
            throw new IllegalArgumentException("Array is too small. " + diag.length + " < " + A.numCols);
        }
        for (int i = 0; i < A.nz_length; ++i) {
            int n = i;
            A.nz_values[n] = A.nz_values[n] * diag[A.nz_rows[i + offset]];
        }
    }

    public static void divideRows(double[] diag, int offset, DMatrixSparseCSC A) {
        if (diag.length < A.numRows) {
            throw new IllegalArgumentException("Array is too small. " + diag.length + " < " + A.numCols);
        }
        for (int i = 0; i < A.nz_length; ++i) {
            int n = i;
            A.nz_values[n] = A.nz_values[n] / diag[A.nz_rows[i + offset]];
        }
    }

    public static double trace(DMatrixSparseCSC A) {
        double output = 0.0;
        int o = Math.min(A.numCols, A.numRows);
        block0: for (int col = 0; col < o; ++col) {
            int idx0 = A.col_idx[col];
            int idx1 = A.col_idx[col + 1];
            for (int i = idx0; i < idx1; ++i) {
                if (A.nz_rows[i] != col) continue;
                output += A.nz_values[i];
                continue block0;
            }
        }
        return output;
    }

    public static DMatrixSparseCSC apply(DMatrixSparseCSC input, DOperatorUnary func, @Nullable DMatrixSparseCSC output) {
        if (output == null) {
            output = input.createLike();
        }
        if (input != output) {
            output.copyStructure(input);
        }
        for (int i = 0; i < input.nz_length; ++i) {
            output.nz_values[i] = func.apply(input.nz_values[i]);
        }
        return output;
    }

    public static DMatrixSparseCSC apply(DMatrixSparseCSC input, DOperatorUnary func) {
        return CommonOps_DSCC.apply(input, func, input);
    }

    public static DMatrixSparseCSC applyRowIdx(DMatrixSparseCSC input, DOperatorBinaryIdx func, @Nullable DMatrixSparseCSC output) {
        if (output == null) {
            output = input.createLike();
        }
        if (input != output) {
            output.copyStructure(input);
        }
        for (int i = 0; i < input.nz_length; ++i) {
            output.nz_values[i] = func.apply(input.nz_rows[i], input.nz_values[i]);
        }
        return output;
    }

    public static DMatrixSparseCSC applyColumnIdx(DMatrixSparseCSC input, DOperatorBinaryIdx func, @Nullable DMatrixSparseCSC output) {
        if (output == null) {
            output = input.createLike();
        }
        if (input != output) {
            output.copyStructure(input);
        }
        for (int col = 0; col < input.numCols; ++col) {
            for (int i = input.col_idx[col]; i < input.col_idx[col + 1]; ++i) {
                output.nz_values[i] = func.apply(col, input.nz_values[i]);
            }
        }
        return output;
    }

    public static double reduceScalar(DMatrixSparseCSC input, double initValue, DOperatorBinary func) {
        double result = initValue;
        for (int i = 0; i < input.nz_length; ++i) {
            result = func.apply(result, input.nz_values[i]);
        }
        return result;
    }

    public static double reduceScalar(DMatrixSparseCSC input, DOperatorBinary func) {
        return CommonOps_DSCC.reduceScalar(input, 0.0, func);
    }

    public static DMatrixRMaj reduceColumnWise(DMatrixSparseCSC input, double initValue, DOperatorBinary func, @Nullable DMatrixRMaj output, @Nullable Mask mask) {
        if (output == null) {
            output = new DMatrixRMaj(1, input.numCols);
        } else {
            output.reshape(1, input.numCols);
        }
        for (int col = 0; col < input.numCols; ++col) {
            int start = input.col_idx[col];
            int end = input.col_idx[col + 1];
            double acc = initValue;
            if (mask == null || mask.isSet(col)) {
                for (int i = start; i < end; ++i) {
                    acc = func.apply(acc, input.nz_values[i]);
                }
            }
            output.data[col] = acc;
        }
        return output;
    }

    public static DMatrixRMaj reduceRowWise(DMatrixSparseCSC input, double initValue, DOperatorBinary func, @Nullable DMatrixRMaj output) {
        if (output == null) {
            output = new DMatrixRMaj(1, input.numRows);
        } else {
            output.reshape(1, input.numCols);
        }
        Arrays.fill(output.data, initValue);
        for (int col = 0; col < input.numCols; ++col) {
            int start = input.col_idx[col];
            int end = input.col_idx[col + 1];
            for (int i = start; i < end; ++i) {
                output.data[input.nz_rows[i]] = func.apply(output.data[input.nz_rows[i]], input.nz_values[i]);
            }
        }
        return output;
    }
}

