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
import org.ejml.data.FGrowArray;
import org.ejml.data.FMatrixRMaj;
import org.ejml.data.FMatrixSparseCSC;
import org.ejml.data.IGrowArray;
import org.ejml.dense.row.CommonOps_FDRM;
import org.ejml.interfaces.decomposition.LUSparseDecomposition_F32;
import org.ejml.interfaces.linsol.LinearSolverSparse;
import org.ejml.masks.Mask;
import org.ejml.ops.FOperatorBinary;
import org.ejml.ops.FOperatorBinaryIdx;
import org.ejml.ops.FOperatorUnary;
import org.ejml.ops.IPredicateBinary;
import org.ejml.sparse.FillReducing;
import org.ejml.sparse.csc.MatrixFeatures_FSCC;
import org.ejml.sparse.csc.factory.DecompositionFactory_FSCC;
import org.ejml.sparse.csc.factory.LinearSolverFactory_FSCC;
import org.ejml.sparse.csc.misc.ImplCommonOps_FSCC;
import org.ejml.sparse.csc.mult.ImplMultiplication_FSCC;
import org.jetbrains.annotations.Nullable;

public class CommonOps_FSCC {
    public static boolean checkIndicesSorted(FMatrixSparseCSC A) {
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

    public static boolean checkStructure(FMatrixSparseCSC A) {
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
        if (!CommonOps_FSCC.checkSortedFlag(A)) {
            return false;
        }
        return !CommonOps_FSCC.checkDuplicateElements(A);
    }

    public static boolean checkSortedFlag(FMatrixSparseCSC A) {
        if (A.indicesSorted) {
            return CommonOps_FSCC.checkIndicesSorted(A);
        }
        return true;
    }

    public static boolean checkDuplicateElements(FMatrixSparseCSC A) {
        A = A.copy();
        A.sortIndices(null);
        return !CommonOps_FSCC.checkSortedFlag(A);
    }

    public static FMatrixSparseCSC transpose(FMatrixSparseCSC A, @Nullable FMatrixSparseCSC A_t, @Nullable IGrowArray gw) {
        A_t = UtilEjml.reshapeOrDeclare(A_t, A.numCols, A.numRows, A.nz_length);
        ImplCommonOps_FSCC.transpose(A, A_t, gw);
        return A_t;
    }

    public static FMatrixSparseCSC mult(FMatrixSparseCSC A, FMatrixSparseCSC B, @Nullable FMatrixSparseCSC outputC) {
        return CommonOps_FSCC.mult(A, B, outputC, null, null);
    }

    public static FMatrixSparseCSC mult(FMatrixSparseCSC A, FMatrixSparseCSC B, @Nullable FMatrixSparseCSC outputC, @Nullable IGrowArray gw, @Nullable FGrowArray gx) {
        if (A.numCols != B.numRows) {
            throw new MatrixDimensionException("Inconsistent matrix shapes. " + UtilEjml.stringShapes(A, B));
        }
        outputC = UtilEjml.reshapeOrDeclare(outputC, A, A.numRows, B.numCols);
        ImplMultiplication_FSCC.mult(A, B, outputC, gw, gx);
        return outputC;
    }

    public static FMatrixRMaj mult(FMatrixSparseCSC A, FMatrixRMaj B, @Nullable FMatrixRMaj outputC) {
        if (A.numCols != B.numRows) {
            throw new MatrixDimensionException("Inconsistent matrix shapes. " + UtilEjml.stringShapes(A, B));
        }
        outputC = UtilEjml.reshapeOrDeclare(outputC, A.numRows, B.numCols);
        ImplMultiplication_FSCC.mult(A, B, outputC);
        return outputC;
    }

    public static void multAdd(FMatrixSparseCSC A, FMatrixRMaj B, FMatrixRMaj outputC) {
        if (A.numCols != B.numRows) {
            throw new MatrixDimensionException("Inconsistent matrix shapes. " + UtilEjml.stringShapes(A, B));
        }
        if (A.numRows != outputC.numRows || B.numCols != outputC.numCols) {
            throw new MatrixDimensionException("Inconsistent matrix shapes. " + UtilEjml.stringShapes(A, B, outputC));
        }
        ImplMultiplication_FSCC.multAdd(A, B, outputC);
    }

    public static FMatrixRMaj multTransA(FMatrixSparseCSC A, FMatrixRMaj B, @Nullable FMatrixRMaj outputC, @Nullable FGrowArray work) {
        if (A.numRows != B.numRows) {
            throw new MatrixDimensionException("Inconsistent matrix shapes. " + UtilEjml.stringShapes(A, B));
        }
        if (work == null) {
            work = new FGrowArray();
        }
        outputC = UtilEjml.reshapeOrDeclare(outputC, A.numCols, B.numCols);
        ImplMultiplication_FSCC.multTransA(A, B, outputC, work);
        return outputC;
    }

    public static void multAddTransA(FMatrixSparseCSC A, FMatrixRMaj B, FMatrixRMaj outputC, @Nullable FGrowArray work) {
        if (A.numRows != B.numRows) {
            throw new MatrixDimensionException("Inconsistent matrix shapes. " + UtilEjml.stringShapes(A, B));
        }
        if (A.numCols != outputC.numRows || B.numCols != outputC.numCols) {
            throw new MatrixDimensionException("Inconsistent matrix shapes. " + UtilEjml.stringShapes(A, B, outputC));
        }
        if (work == null) {
            work = new FGrowArray();
        }
        ImplMultiplication_FSCC.multAddTransA(A, B, outputC, work);
    }

    public static FMatrixRMaj multTransB(FMatrixSparseCSC A, FMatrixRMaj B, @Nullable FMatrixRMaj outputC, @Nullable FGrowArray work) {
        if (A.numCols != B.numCols) {
            throw new MatrixDimensionException("Inconsistent matrix shapes. " + UtilEjml.stringShapes(A, B));
        }
        outputC = UtilEjml.reshapeOrDeclare(outputC, A.numRows, B.numRows);
        if (work == null) {
            work = new FGrowArray();
        }
        ImplMultiplication_FSCC.multTransB(A, B, outputC, work);
        return outputC;
    }

    public static void multAddTransB(FMatrixSparseCSC A, FMatrixRMaj B, FMatrixRMaj outputC, @Nullable FGrowArray work) {
        if (A.numCols != B.numCols) {
            throw new MatrixDimensionException("Inconsistent matrix shapes. " + UtilEjml.stringShapes(A, B));
        }
        if (A.numRows != outputC.numRows || B.numRows != outputC.numCols) {
            throw new MatrixDimensionException("Inconsistent matrix shapes. " + UtilEjml.stringShapes(A, B, outputC));
        }
        if (work == null) {
            work = new FGrowArray();
        }
        ImplMultiplication_FSCC.multAddTransB(A, B, outputC, work);
    }

    public static FMatrixRMaj multTransAB(FMatrixSparseCSC A, FMatrixRMaj B, FMatrixRMaj outputC) {
        if (A.numRows != B.numCols) {
            throw new MatrixDimensionException("Inconsistent matrix shapes. " + UtilEjml.stringShapes(A, B));
        }
        outputC = UtilEjml.reshapeOrDeclare(outputC, A.numCols, B.numRows);
        ImplMultiplication_FSCC.multTransAB(A, B, outputC);
        return outputC;
    }

    public static void multAddTransAB(FMatrixSparseCSC A, FMatrixRMaj B, FMatrixRMaj outputC) {
        if (A.numRows != B.numCols) {
            throw new MatrixDimensionException("Inconsistent matrix shapes. " + UtilEjml.stringShapes(A, B));
        }
        if (A.numCols != outputC.numRows || B.numRows != outputC.numCols) {
            throw new MatrixDimensionException("Inconsistent matrix shapes. " + UtilEjml.stringShapes(A, B, outputC));
        }
        ImplMultiplication_FSCC.multAddTransAB(A, B, outputC);
    }

    public static void symmLowerToFull(FMatrixSparseCSC A, FMatrixSparseCSC outputB, @Nullable IGrowArray gw) {
        ImplCommonOps_FSCC.symmLowerToFull(A, outputB, gw);
    }

    public static FMatrixSparseCSC add(float alpha, FMatrixSparseCSC A, float beta, FMatrixSparseCSC B, @Nullable FMatrixSparseCSC outputC, @Nullable IGrowArray gw, @Nullable FGrowArray gx) {
        if (A.numRows != B.numRows || A.numCols != B.numCols) {
            throw new MatrixDimensionException("Inconsistent matrix shapes. " + UtilEjml.stringShapes(A, B));
        }
        outputC = UtilEjml.reshapeOrDeclare(outputC, A, A.numRows, A.numCols);
        ImplCommonOps_FSCC.add(alpha, A, beta, B, outputC, gw, gx);
        return outputC;
    }

    public static FMatrixSparseCSC identity(int length) {
        return CommonOps_FSCC.identity(length, length);
    }

    public static FMatrixSparseCSC identity(int numRows, int numCols) {
        int min = Math.min(numRows, numCols);
        FMatrixSparseCSC A = new FMatrixSparseCSC(numRows, numCols, min);
        CommonOps_FSCC.setIdentity(A);
        return A;
    }

    public static void setIdentity(FMatrixSparseCSC A) {
        int i;
        int min = Math.min(A.numRows, A.numCols);
        A.growMaxLength(min, false);
        A.nz_length = min;
        Arrays.fill(A.nz_values, 0, min, 1.0f);
        for (i = 1; i <= min; ++i) {
            A.col_idx[i] = i;
            A.nz_rows[i - 1] = i - 1;
        }
        for (i = min + 1; i <= A.numCols; ++i) {
            A.col_idx[i] = min;
        }
    }

    public static void scale(float scalar, FMatrixSparseCSC A, FMatrixSparseCSC outputB) {
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

    public static void divide(FMatrixSparseCSC A, float scalar, FMatrixSparseCSC outputB) {
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

    public static void divide(float scalar, FMatrixSparseCSC A, FMatrixSparseCSC outputB) {
        if (A != outputB) {
            outputB.copyStructure(A);
        }
        for (int i = 0; i < A.nz_length; ++i) {
            outputB.nz_values[i] = scalar / A.nz_values[i];
        }
    }

    public static void changeSign(FMatrixSparseCSC A, FMatrixSparseCSC outputB) {
        if (A != outputB) {
            outputB.copyStructure(A);
        }
        for (int i = 0; i < A.nz_length; ++i) {
            outputB.nz_values[i] = -A.nz_values[i];
        }
    }

    public static float elementMinAbs(FMatrixSparseCSC A) {
        if (A.nz_length == 0) {
            return 0.0f;
        }
        float min = A.isFull() ? Math.abs(A.nz_values[0]) : 0.0f;
        for (int i = 0; i < A.nz_length; ++i) {
            float val = Math.abs(A.nz_values[i]);
            if (!(val < min)) continue;
            min = val;
        }
        return min;
    }

    public static float elementMaxAbs(FMatrixSparseCSC A) {
        if (A.nz_length == 0) {
            return 0.0f;
        }
        float max = A.isFull() ? Math.abs(A.nz_values[0]) : 0.0f;
        for (int i = 0; i < A.nz_length; ++i) {
            float val = Math.abs(A.nz_values[i]);
            if (!(val > max)) continue;
            max = val;
        }
        return max;
    }

    public static float elementMin(FMatrixSparseCSC A) {
        if (A.nz_length == 0) {
            return 0.0f;
        }
        float min = A.isFull() ? A.nz_values[0] : 0.0f;
        for (int i = 0; i < A.nz_length; ++i) {
            float val = A.nz_values[i];
            if (!(val < min)) continue;
            min = val;
        }
        return min;
    }

    public static float elementMax(FMatrixSparseCSC A) {
        if (A.nz_length == 0) {
            return 0.0f;
        }
        float max = A.isFull() ? A.nz_values[0] : 0.0f;
        for (int i = 0; i < A.nz_length; ++i) {
            float val = A.nz_values[i];
            if (!(val > max)) continue;
            max = val;
        }
        return max;
    }

    public static float elementSum(FMatrixSparseCSC A) {
        if (A.nz_length == 0) {
            return 0.0f;
        }
        float sum = 0.0f;
        for (int i = 0; i < A.nz_length; ++i) {
            sum += A.nz_values[i];
        }
        return sum;
    }

    public static FMatrixSparseCSC elementMult(FMatrixSparseCSC A, FMatrixSparseCSC B, @Nullable FMatrixSparseCSC output, @Nullable IGrowArray gw, @Nullable FGrowArray gx) {
        if (A.numCols != B.numCols || A.numRows != B.numRows) {
            throw new MatrixDimensionException("All inputs must have the same number of rows and columns. " + UtilEjml.stringShapes(A, B));
        }
        output = UtilEjml.reshapeOrDeclare(output, A, A.numRows, A.numCols);
        ImplCommonOps_FSCC.elementMult(A, B, output, gw, gx);
        return output;
    }

    public static void maxAbsCols(FMatrixSparseCSC A, @Nullable FMatrixRMaj outputB) {
        outputB = UtilEjml.reshapeOrDeclare(outputB, 1, A.numCols);
        for (int i = 0; i < A.numCols; ++i) {
            int idx0 = A.col_idx[i];
            int idx1 = A.col_idx[i + 1];
            float maxabs = 0.0f;
            for (int j = idx0; j < idx1; ++j) {
                float v = Math.abs(A.nz_values[j]);
                if (!(v > maxabs)) continue;
                maxabs = v;
            }
            outputB.data[i] = maxabs;
        }
    }

    public static void multColumns(FMatrixSparseCSC A, float[] values, int offset) {
        if (values.length + offset < A.numCols) {
            throw new IllegalArgumentException("Array is too small. " + values.length + " < " + A.numCols);
        }
        for (int i = 0; i < A.numCols; ++i) {
            int idx0 = A.col_idx[i];
            int idx1 = A.col_idx[i + 1];
            float v = values[offset + i];
            int j = idx0;
            while (j < idx1) {
                int n = j++;
                A.nz_values[n] = A.nz_values[n] * v;
            }
        }
    }

    public static void divideColumns(FMatrixSparseCSC A, float[] values, int offset) {
        if (values.length + offset < A.numCols) {
            throw new IllegalArgumentException("Array is too small. " + values.length + " < " + A.numCols);
        }
        for (int i = 0; i < A.numCols; ++i) {
            int idx0 = A.col_idx[i];
            int idx1 = A.col_idx[i + 1];
            float v = values[offset + i];
            int j = idx0;
            while (j < idx1) {
                int n = j++;
                A.nz_values[n] = A.nz_values[n] / v;
            }
        }
    }

    public static void multRowsCols(float[] diagA, int offsetA, FMatrixSparseCSC B, float[] diagC, int offsetC) {
        if (diagA.length + offsetA < B.numRows) {
            throw new IllegalArgumentException("diagA is too small.");
        }
        if (diagC.length + offsetC < B.numCols) {
            throw new IllegalArgumentException("diagA is too small.");
        }
        for (int i = 0; i < B.numCols; ++i) {
            int idx0 = B.col_idx[i];
            int idx1 = B.col_idx[i + 1];
            float c = diagC[offsetC + i];
            for (int j = idx0; j < idx1; ++j) {
                int n = j;
                B.nz_values[n] = B.nz_values[n] * (c * diagA[offsetA + B.nz_rows[j]]);
            }
        }
    }

    public static void divideRowsCols(float[] diagA, int offsetA, FMatrixSparseCSC B, float[] diagC, int offsetC) {
        if (diagA.length + offsetA < B.numRows) {
            throw new IllegalArgumentException("diagA is too small.");
        }
        if (diagC.length + offsetC < B.numCols) {
            throw new IllegalArgumentException("diagA is too small.");
        }
        for (int i = 0; i < B.numCols; ++i) {
            int idx0 = B.col_idx[i];
            int idx1 = B.col_idx[i + 1];
            float c = diagC[offsetC + i];
            for (int j = idx0; j < idx1; ++j) {
                int n = j;
                B.nz_values[n] = B.nz_values[n] / (c * diagA[offsetA + B.nz_rows[j]]);
            }
        }
    }

    public static FMatrixSparseCSC diag(float ... values) {
        int N = values.length;
        return CommonOps_FSCC.diag(new FMatrixSparseCSC(N, N, N), values, 0, N);
    }

    public static FMatrixSparseCSC diag(@Nullable FMatrixSparseCSC A, float[] values, int offset, int length) {
        int N = length;
        if (A == null) {
            A = new FMatrixSparseCSC(N, N, N);
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

    public static void extractDiag(FMatrixSparseCSC A, FMatrixSparseCSC outputB) {
        int N = Math.min(A.numRows, A.numCols);
        if (!MatrixFeatures_FSCC.isVector(outputB)) {
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

    public static void extractDiag(FMatrixSparseCSC A, FMatrixRMaj outputB) {
        int N = Math.min(A.numRows, A.numCols);
        if (outputB.getNumElements() != N || outputB.numRows != 1 && outputB.numCols != 1) {
            outputB.reshape(N, 1);
        }
        for (int i = 0; i < N; ++i) {
            outputB.data[i] = A.unsafe_get(i, i);
        }
    }

    public static FMatrixSparseCSC permutationMatrix(int[] p, boolean inverse, int N, @Nullable FMatrixSparseCSC P) {
        if (P == null) {
            P = new FMatrixSparseCSC(N, N, N);
        } else {
            P.reshape(N, N, N);
        }
        P.indicesSorted = true;
        P.nz_length = N;
        if (!inverse) {
            for (int i = 0; i < N; ++i) {
                P.col_idx[i + 1] = i + 1;
                P.nz_rows[p[i]] = i;
                P.nz_values[i] = 1.0f;
            }
        } else {
            for (int i = 0; i < N; ++i) {
                P.col_idx[i + 1] = i + 1;
                P.nz_rows[i] = p[i];
                P.nz_values[i] = 1.0f;
            }
        }
        return P;
    }

    public static void permutationVector(FMatrixSparseCSC P, int[] vector) {
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
        CommonOps_FSCC.permutationInverse(original, inverse, length);
        return inverse;
    }

    public static void permuteRowInv(int[] permInv, FMatrixSparseCSC input, FMatrixSparseCSC output) {
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

    public static void permute(@Nullable int[] permRowInv, FMatrixSparseCSC input, @Nullable int[] permCol, FMatrixSparseCSC output) {
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

    public static void permute(int[] perm, float[] input, float[] output, int N) {
        for (int k = 0; k < N; ++k) {
            output[k] = input[perm[k]];
        }
    }

    public static void permuteInv(int[] perm, float[] input, float[] output, int N) {
        for (int k = 0; k < N; ++k) {
            output[perm[k]] = input[k];
        }
    }

    public static void permuteSymmetric(FMatrixSparseCSC input, int[] permInv, FMatrixSparseCSC output, @Nullable IGrowArray gw) {
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

    public static FMatrixSparseCSC concatRows(FMatrixSparseCSC top, FMatrixSparseCSC bottom, @Nullable FMatrixSparseCSC out) {
        if (top.numCols != bottom.numCols) {
            throw new MatrixDimensionException("Number of columns must match. " + UtilEjml.stringShapes(top, bottom));
        }
        if (out == null) {
            out = new FMatrixSparseCSC(0, 0, 0);
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

    public static FMatrixSparseCSC concatColumns(FMatrixSparseCSC left, FMatrixSparseCSC right, @Nullable FMatrixSparseCSC out) {
        if (left.numRows != right.numRows) {
            throw new MatrixDimensionException("Number of rows must match. " + UtilEjml.stringShapes(left, right));
        }
        if (out == null) {
            out = new FMatrixSparseCSC(0, 0, 0);
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

    public static FMatrixSparseCSC extractColumn(FMatrixSparseCSC A, int column, @Nullable FMatrixSparseCSC out) {
        if (out == null) {
            out = new FMatrixSparseCSC(1, 1, 1);
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

    public static FMatrixSparseCSC extractRows(FMatrixSparseCSC A, int row0, int row1, @Nullable FMatrixSparseCSC out) {
        if (out == null) {
            out = new FMatrixSparseCSC(1, 1, 1);
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

    public static void extract(FMatrixSparseCSC src, int srcY0, int srcY1, int srcX0, int srcX1, FMatrixSparseCSC dst, int dstY0, int dstX0) {
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
        CommonOps_FSCC.zero(dst, dstY0, dstY0 + h, dstX0, dstX0 + w);
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

    public static FMatrixSparseCSC select(FMatrixSparseCSC A, IPredicateBinary selector, @Nullable FMatrixSparseCSC output) {
        if (output != A) {
            output = UtilEjml.reshapeOrDeclare(output, A);
        }
        ImplCommonOps_FSCC.select(A, output, selector);
        return output;
    }

    public static void fill(FMatrixSparseCSC A, float value) {
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

    public static FMatrixRMaj sumCols(FMatrixSparseCSC input, @Nullable FMatrixRMaj output) {
        if (output == null) {
            output = new FMatrixRMaj(1, input.numCols);
        } else {
            output.reshape(1, input.numCols);
        }
        for (int col = 0; col < input.numCols; ++col) {
            int idx0 = input.col_idx[col];
            int idx1 = input.col_idx[col + 1];
            float sum = 0.0f;
            for (int i = idx0; i < idx1; ++i) {
                sum += input.nz_values[i];
            }
            output.data[col] = sum;
        }
        return output;
    }

    public static FMatrixRMaj minCols(FMatrixSparseCSC input, @Nullable FMatrixRMaj output) {
        output = UtilEjml.reshapeOrDeclare(output, 1, input.numCols);
        for (int col = 0; col < input.numCols; ++col) {
            int idx1 = input.col_idx[col + 1];
            int idx0 = input.col_idx[col];
            float min = idx1 - idx0 == input.numRows ? Float.MAX_VALUE : 0.0f;
            for (int i = idx0; i < idx1; ++i) {
                float v = input.nz_values[i];
                if (!(min > v)) continue;
                min = v;
            }
            output.data[col] = min;
        }
        return output;
    }

    public static FMatrixRMaj maxCols(FMatrixSparseCSC input, @Nullable FMatrixRMaj output) {
        output = UtilEjml.reshapeOrDeclare(output, 1, input.numCols);
        for (int col = 0; col < input.numCols; ++col) {
            int idx1 = input.col_idx[col + 1];
            int idx0 = input.col_idx[col];
            float max = idx1 - idx0 == input.numRows ? -3.4028235E38f : 0.0f;
            for (int i = idx0; i < idx1; ++i) {
                float v = input.nz_values[i];
                if (!(max < v)) continue;
                max = v;
            }
            output.data[col] = max;
        }
        return output;
    }

    public static FMatrixRMaj sumRows(FMatrixSparseCSC input, @Nullable FMatrixRMaj output) {
        output = UtilEjml.reshapeOrDeclare(output, input.numRows, 1);
        Arrays.fill(output.data, 0, input.numRows, 0.0f);
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

    public static FMatrixRMaj minRows(FMatrixSparseCSC input, @Nullable FMatrixRMaj output, @Nullable IGrowArray gw) {
        output = UtilEjml.reshapeOrDeclare(output, input.numRows, 1);
        int[] w = UtilEjml.adjust(gw, input.numRows, input.numRows);
        Arrays.fill(output.data, 0, input.numRows, Float.MAX_VALUE);
        for (int col = 0; col < input.numCols; ++col) {
            int idx0 = input.col_idx[col];
            int idx1 = input.col_idx[col + 1];
            for (int i = idx0; i < idx1; ++i) {
                int row = input.nz_rows[i];
                float v = input.nz_values[i];
                if (output.data[row] > v) {
                    output.data[row] = v;
                }
                int n = row;
                w[n] = w[n] + 1;
            }
        }
        for (int row = 0; row < input.numRows; ++row) {
            if (w[row] == input.numCols || !(output.data[row] > 0.0f)) continue;
            output.data[row] = 0.0f;
        }
        return output;
    }

    public static FMatrixRMaj maxRows(FMatrixSparseCSC input, @Nullable FMatrixRMaj output, @Nullable IGrowArray gw) {
        output = UtilEjml.reshapeOrDeclare(output, input.numRows, 1);
        int[] w = UtilEjml.adjust(gw, input.numRows, input.numRows);
        Arrays.fill(output.data, 0, input.numRows, -3.4028235E38f);
        for (int col = 0; col < input.numCols; ++col) {
            int idx0 = input.col_idx[col];
            int idx1 = input.col_idx[col + 1];
            for (int i = idx0; i < idx1; ++i) {
                int row = input.nz_rows[i];
                float v = input.nz_values[i];
                if (output.data[row] < v) {
                    output.data[row] = v;
                }
                int n = row;
                w[n] = w[n] + 1;
            }
        }
        for (int row = 0; row < input.numRows; ++row) {
            if (w[row] == input.numCols || !(output.data[row] < 0.0f)) continue;
            output.data[row] = 0.0f;
        }
        return output;
    }

    public static void zero(FMatrixSparseCSC A, int row0, int row1, int col0, int col1) {
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

    public static float dotInnerColumns(FMatrixSparseCSC A, int colA, FMatrixSparseCSC B, int colB, @Nullable IGrowArray gw, @Nullable FGrowArray gx) {
        return ImplMultiplication_FSCC.dotInnerColumns(A, colA, B, colB, gw, gx);
    }

    public static boolean solve(FMatrixSparseCSC a, FMatrixRMaj b, FMatrixRMaj x) {
        x.reshape(a.numCols, b.numCols);
        LinearSolverSparse<FMatrixSparseCSC, FMatrixRMaj> solver = a.numRows > a.numCols ? LinearSolverFactory_FSCC.qr(FillReducing.NONE) : LinearSolverFactory_FSCC.lu(FillReducing.NONE);
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

    public static boolean solve(FMatrixSparseCSC a, FMatrixSparseCSC b, FMatrixSparseCSC x) {
        x.reshape(a.numCols, b.numCols);
        LinearSolverSparse<FMatrixSparseCSC, FMatrixRMaj> solver = a.numRows > a.numCols ? LinearSolverFactory_FSCC.qr(FillReducing.NONE) : LinearSolverFactory_FSCC.lu(FillReducing.NONE);
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

    public static boolean invert(FMatrixSparseCSC A, FMatrixRMaj inverse) {
        if (A.numRows != A.numCols) {
            throw new IllegalArgumentException("A must be a square matrix");
        }
        inverse.reshape(A.numRows, A.numCols);
        LinearSolverSparse<FMatrixSparseCSC, FMatrixRMaj> solver = LinearSolverFactory_FSCC.lu(FillReducing.NONE);
        if (solver.modifiesA()) {
            A = A.copy();
        }
        FMatrixRMaj I = CommonOps_FDRM.identity(A.numRows);
        if (!solver.setA(A)) {
            return false;
        }
        solver.solve(I, inverse);
        return true;
    }

    public static float det(FMatrixSparseCSC A) {
        LUSparseDecomposition_F32<FMatrixSparseCSC> alg = DecompositionFactory_FSCC.lu(FillReducing.NONE);
        if (alg.inputModified()) {
            A = A.copy();
        }
        if (!alg.decompose(A)) {
            return 0.0f;
        }
        return alg.computeDeterminant().real;
    }

    public static void removeZeros(FMatrixSparseCSC input, FMatrixSparseCSC output, float tol) {
        ImplCommonOps_FSCC.removeZeros(input, output, tol);
    }

    public static void removeZeros(FMatrixSparseCSC A, float tol) {
        ImplCommonOps_FSCC.removeZeros(A, tol);
    }

    public static void duplicatesAdd(FMatrixSparseCSC A, @Nullable IGrowArray work) {
        ImplCommonOps_FSCC.duplicatesAdd(A, work);
    }

    public static void multRows(float[] diag, int offset, FMatrixSparseCSC A) {
        if (diag.length < A.numRows) {
            throw new IllegalArgumentException("Array is too small. " + diag.length + " < " + A.numCols);
        }
        for (int i = 0; i < A.nz_length; ++i) {
            int n = i;
            A.nz_values[n] = A.nz_values[n] * diag[A.nz_rows[i + offset]];
        }
    }

    public static void divideRows(float[] diag, int offset, FMatrixSparseCSC A) {
        if (diag.length < A.numRows) {
            throw new IllegalArgumentException("Array is too small. " + diag.length + " < " + A.numCols);
        }
        for (int i = 0; i < A.nz_length; ++i) {
            int n = i;
            A.nz_values[n] = A.nz_values[n] / diag[A.nz_rows[i + offset]];
        }
    }

    public static float trace(FMatrixSparseCSC A) {
        float output = 0.0f;
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

    public static FMatrixSparseCSC apply(FMatrixSparseCSC input, FOperatorUnary func, @Nullable FMatrixSparseCSC output) {
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

    public static FMatrixSparseCSC apply(FMatrixSparseCSC input, FOperatorUnary func) {
        return CommonOps_FSCC.apply(input, func, input);
    }

    public static FMatrixSparseCSC applyRowIdx(FMatrixSparseCSC input, FOperatorBinaryIdx func, @Nullable FMatrixSparseCSC output) {
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

    public static FMatrixSparseCSC applyColumnIdx(FMatrixSparseCSC input, FOperatorBinaryIdx func, @Nullable FMatrixSparseCSC output) {
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

    public static float reduceScalar(FMatrixSparseCSC input, float initValue, FOperatorBinary func) {
        float result = initValue;
        for (int i = 0; i < input.nz_length; ++i) {
            result = func.apply(result, input.nz_values[i]);
        }
        return result;
    }

    public static float reduceScalar(FMatrixSparseCSC input, FOperatorBinary func) {
        return CommonOps_FSCC.reduceScalar(input, 0.0f, func);
    }

    public static FMatrixRMaj reduceColumnWise(FMatrixSparseCSC input, float initValue, FOperatorBinary func, @Nullable FMatrixRMaj output, @Nullable Mask mask) {
        if (output == null) {
            output = new FMatrixRMaj(1, input.numCols);
        } else {
            output.reshape(1, input.numCols);
        }
        for (int col = 0; col < input.numCols; ++col) {
            int start = input.col_idx[col];
            int end = input.col_idx[col + 1];
            float acc = initValue;
            if (mask == null || mask.isSet(col)) {
                for (int i = start; i < end; ++i) {
                    acc = func.apply(acc, input.nz_values[i]);
                }
            }
            output.data[col] = acc;
        }
        return output;
    }

    public static FMatrixRMaj reduceRowWise(FMatrixSparseCSC input, float initValue, FOperatorBinary func, @Nullable FMatrixRMaj output) {
        if (output == null) {
            output = new FMatrixRMaj(1, input.numRows);
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

