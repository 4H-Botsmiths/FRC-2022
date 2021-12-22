/*
 * Decompiled with CFR 0.150.
 */
package org.ejml.dense.row;

import org.ejml.UtilEjml;
import org.ejml.data.BMatrixRMaj;
import org.ejml.data.Complex_F32;
import org.ejml.data.FMatrix;
import org.ejml.data.FMatrixD1;
import org.ejml.data.FMatrixRMaj;
import org.ejml.data.Matrix;
import org.ejml.dense.row.CommonOps_FDRM;
import org.ejml.dense.row.SingularOps_FDRM;
import org.ejml.dense.row.decomposition.chol.CholeskyDecompositionInner_FDRM;
import org.ejml.dense.row.factory.DecompositionFactory_FDRM;
import org.ejml.dense.row.mult.VectorVectorMult_FDRM;
import org.ejml.interfaces.decomposition.EigenDecomposition_F32;
import org.ejml.interfaces.decomposition.LUDecomposition_F32;
import org.ejml.interfaces.decomposition.SingularValueDecomposition_F32;

public class MatrixFeatures_FDRM {
    public static boolean hasNaN(FMatrixD1 m) {
        int length = m.getNumElements();
        for (int i = 0; i < length; ++i) {
            if (!Float.isNaN(m.get(i))) continue;
            return true;
        }
        return false;
    }

    public static boolean hasUncountable(FMatrixD1 m) {
        int length = m.getNumElements();
        for (int i = 0; i < length; ++i) {
            float a = m.get(i);
            if (!Float.isNaN(a) && !Float.isInfinite(a)) continue;
            return true;
        }
        return false;
    }

    public static boolean isZeros(FMatrixD1 m, float tol) {
        int length = m.getNumElements();
        for (int i = 0; i < length; ++i) {
            if (!(Math.abs(m.get(i)) > tol)) continue;
            return false;
        }
        return true;
    }

    public static boolean isVector(Matrix mat) {
        return mat.getNumCols() == 1 || mat.getNumRows() == 1;
    }

    public static boolean isPositiveDefinite(FMatrixRMaj A) {
        if (!MatrixFeatures_FDRM.isSquare(A)) {
            return false;
        }
        CholeskyDecompositionInner_FDRM chol = new CholeskyDecompositionInner_FDRM(true);
        if (chol.inputModified()) {
            A = A.copy();
        }
        return chol.decompose(A);
    }

    public static boolean isPositiveSemidefinite(FMatrixRMaj A) {
        if (!MatrixFeatures_FDRM.isSquare(A)) {
            return false;
        }
        EigenDecomposition_F32<FMatrixRMaj> eig = DecompositionFactory_FDRM.eig(A.numCols, false);
        if (eig.inputModified()) {
            A = A.copy();
        }
        eig.decompose(A);
        for (int i = 0; i < A.numRows; ++i) {
            Complex_F32 v = eig.getEigenvalue(i);
            if (!(v.getReal() < 0.0f)) continue;
            return false;
        }
        return true;
    }

    public static boolean isSquare(FMatrixD1 mat) {
        return mat.numCols == mat.numRows;
    }

    public static boolean isSymmetric(FMatrixRMaj m, float tol) {
        if (m.numCols != m.numRows) {
            return false;
        }
        float max = CommonOps_FDRM.elementMaxAbs(m);
        for (int i = 0; i < m.numRows; ++i) {
            for (int j = 0; j < i; ++j) {
                float b;
                float a = m.get(i, j) / max;
                float diff = Math.abs(a - (b = m.get(j, i) / max));
                if (diff <= tol) continue;
                return false;
            }
        }
        return true;
    }

    public static boolean isSymmetric(FMatrixRMaj m) {
        return MatrixFeatures_FDRM.isSymmetric(m, 0.0f);
    }

    public static boolean isSkewSymmetric(FMatrixRMaj A, float tol) {
        if (A.numCols != A.numRows) {
            return false;
        }
        for (int i = 0; i < A.numRows; ++i) {
            for (int j = 0; j < i; ++j) {
                float b;
                float a = A.get(i, j);
                float diff = Math.abs(a + (b = A.get(j, i)));
                if (diff <= tol) continue;
                return false;
            }
        }
        return true;
    }

    public static boolean isInverse(FMatrixRMaj a, FMatrixRMaj b, float tol) {
        if (a.numRows != b.numRows || a.numCols != b.numCols) {
            return false;
        }
        int numRows = a.numRows;
        int numCols = a.numCols;
        for (int i = 0; i < numRows; ++i) {
            for (int j = 0; j < numCols; ++j) {
                float total = 0.0f;
                for (int k = 0; k < numCols; ++k) {
                    total += a.get(i, k) * b.get(k, j);
                }
                if (!(i == j ? !(Math.abs(total - 1.0f) <= tol) : !(Math.abs(total) <= tol))) continue;
                return false;
            }
        }
        return true;
    }

    public static boolean isEquals(FMatrixD1 a, FMatrixD1 b, float tol) {
        if (a.numRows != b.numRows || a.numCols != b.numCols) {
            return false;
        }
        if (tol == 0.0f) {
            return MatrixFeatures_FDRM.isEquals(a, b);
        }
        int length = a.getNumElements();
        for (int i = 0; i < length; ++i) {
            if (tol >= Math.abs(a.get(i) - b.get(i))) continue;
            return false;
        }
        return true;
    }

    public static boolean isEqualsTriangle(FMatrix a, FMatrix b, boolean upper, float tol) {
        if (a.getNumRows() != b.getNumRows() || a.getNumCols() != b.getNumCols()) {
            return false;
        }
        if (upper) {
            for (int i = 0; i < a.getNumRows(); ++i) {
                for (int j = i; j < a.getNumCols(); ++j) {
                    if (!(Math.abs(a.get(i, j) - b.get(i, j)) > tol)) continue;
                    return false;
                }
            }
        } else {
            for (int i = 0; i < a.getNumRows(); ++i) {
                int end = Math.min(i, a.getNumCols() - 1);
                for (int j = 0; j <= end; ++j) {
                    if (!(Math.abs(a.get(i, j) - b.get(i, j)) > tol)) continue;
                    return false;
                }
            }
        }
        return true;
    }

    public static boolean isEquals(FMatrixD1 a, FMatrixD1 b) {
        if (a.numRows != b.numRows || a.numCols != b.numCols) {
            return false;
        }
        int length = a.getNumElements();
        for (int i = 0; i < length; ++i) {
            if (a.get(i) == b.get(i)) continue;
            return false;
        }
        return true;
    }

    public static boolean isEquals(BMatrixRMaj a, BMatrixRMaj b) {
        if (a.numRows != b.numRows || a.numCols != b.numCols) {
            return false;
        }
        int length = a.getNumElements();
        for (int i = 0; i < length; ++i) {
            if (a.get(i) == b.get(i)) continue;
            return false;
        }
        return true;
    }

    public static boolean isIdentical(FMatrixD1 a, FMatrixD1 b, float tol) {
        if (a.numRows != b.numRows || a.numCols != b.numCols) {
            return false;
        }
        if (tol < 0.0f) {
            throw new IllegalArgumentException("Tolerance must be greater than or equal to zero.");
        }
        int length = a.getNumElements();
        for (int i = 0; i < length; ++i) {
            if (UtilEjml.isIdentical(a.get(i), b.get(i), tol)) continue;
            return false;
        }
        return true;
    }

    public static boolean isOrthogonal(FMatrixRMaj Q, float tol) {
        if (Q.numRows < Q.numCols) {
            throw new IllegalArgumentException("The number of rows must be more than or equal to the number of columns");
        }
        FMatrixRMaj[] u = CommonOps_FDRM.columnsToVector(Q, null);
        for (int i = 0; i < u.length; ++i) {
            FMatrixRMaj a = u[i];
            for (int j = i + 1; j < u.length; ++j) {
                float val = VectorVectorMult_FDRM.innerProd(a, u[j]);
                if (Math.abs(val) <= tol) continue;
                return false;
            }
        }
        return true;
    }

    public static boolean isRowsLinearIndependent(FMatrixRMaj A) {
        LUDecomposition_F32<FMatrixRMaj> lu = DecompositionFactory_FDRM.lu(A.numRows, A.numCols);
        if (lu.inputModified()) {
            A = A.copy();
        }
        if (!lu.decompose(A)) {
            throw new RuntimeException("Decompositon failed?");
        }
        return !lu.isSingular();
    }

    public static boolean isIdentity(FMatrixRMaj mat, float tol) {
        int index = 0;
        for (int i = 0; i < mat.numRows; ++i) {
            for (int j = 0; j < mat.numCols; ++j) {
                if (!(i == j ? !(Math.abs(mat.get(index++) - 1.0f) <= tol) : !(Math.abs(mat.get(index++)) <= tol))) continue;
                return false;
            }
        }
        return true;
    }

    public static boolean isConstantVal(FMatrixRMaj mat, float val, float tol) {
        int index = 0;
        for (int i = 0; i < mat.numRows; ++i) {
            for (int j = 0; j < mat.numCols; ++j) {
                if (Math.abs(mat.get(index++) - val) <= tol) continue;
                return false;
            }
        }
        return true;
    }

    public static boolean isDiagonalPositive(FMatrixRMaj a) {
        for (int i = 0; i < a.numRows; ++i) {
            if (a.get(i, i) >= 0.0f) continue;
            return false;
        }
        return true;
    }

    public static boolean isFullRank(FMatrixRMaj a) {
        throw new RuntimeException("Implement");
    }

    public static boolean isNegative(FMatrixD1 a, FMatrixD1 b, float tol) {
        if (a.numRows != b.numRows || a.numCols != b.numCols) {
            throw new IllegalArgumentException("Matrix dimensions must match");
        }
        int length = a.getNumElements();
        for (int i = 0; i < length; ++i) {
            if (Math.abs(a.get(i) + b.get(i)) <= tol) continue;
            return false;
        }
        return true;
    }

    public static boolean isUpperTriangle(FMatrixRMaj A, int hessenberg, float tol) {
        for (int i = hessenberg + 1; i < A.numRows; ++i) {
            int maxCol = Math.min(i - hessenberg, A.numCols);
            for (int j = 0; j < maxCol; ++j) {
                if (Math.abs(A.unsafe_get(i, j)) <= tol) continue;
                return false;
            }
        }
        return true;
    }

    public static boolean isLowerTriangle(FMatrixRMaj A, int hessenberg, float tol) {
        for (int i = 0; i < A.numRows - hessenberg - 1; ++i) {
            for (int j = i + hessenberg + 1; j < A.numCols; ++j) {
                if (Math.abs(A.unsafe_get(i, j)) <= tol) continue;
                return false;
            }
        }
        return true;
    }

    public static int rank(FMatrixRMaj A) {
        return MatrixFeatures_FDRM.rank(A, UtilEjml.F_EPS * 100.0f);
    }

    public static int rank(FMatrixRMaj A, float threshold) {
        SingularValueDecomposition_F32<FMatrixRMaj> svd = DecompositionFactory_FDRM.svd(A.numRows, A.numCols, false, false, true);
        if (svd.inputModified()) {
            A = A.copy();
        }
        if (!svd.decompose(A)) {
            throw new RuntimeException("Decomposition failed");
        }
        return SingularOps_FDRM.rank(svd, threshold);
    }

    public static int nullity(FMatrixRMaj A) {
        return MatrixFeatures_FDRM.nullity(A, UtilEjml.F_EPS * 100.0f);
    }

    public static int nullity(FMatrixRMaj A, float threshold) {
        SingularValueDecomposition_F32<FMatrixRMaj> svd = DecompositionFactory_FDRM.svd(A.numRows, A.numCols, false, false, true);
        if (svd.inputModified()) {
            A = A.copy();
        }
        if (!svd.decompose(A)) {
            throw new RuntimeException("Decomposition failed");
        }
        return SingularOps_FDRM.nullity(svd, threshold);
    }

    public static int countNonZero(FMatrixRMaj A) {
        int total = 0;
        int index = 0;
        for (int row = 0; row < A.numRows; ++row) {
            int col = 0;
            while (col < A.numCols) {
                if (A.data[index] != 0.0f) {
                    ++total;
                }
                ++col;
                ++index;
            }
        }
        return total;
    }
}

