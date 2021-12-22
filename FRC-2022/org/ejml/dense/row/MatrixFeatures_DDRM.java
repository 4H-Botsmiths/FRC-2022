/*
 * Decompiled with CFR 0.150.
 */
package org.ejml.dense.row;

import org.ejml.UtilEjml;
import org.ejml.data.BMatrixRMaj;
import org.ejml.data.Complex_F64;
import org.ejml.data.DMatrix;
import org.ejml.data.DMatrixD1;
import org.ejml.data.DMatrixRMaj;
import org.ejml.data.Matrix;
import org.ejml.dense.row.CommonOps_DDRM;
import org.ejml.dense.row.SingularOps_DDRM;
import org.ejml.dense.row.decomposition.chol.CholeskyDecompositionInner_DDRM;
import org.ejml.dense.row.factory.DecompositionFactory_DDRM;
import org.ejml.dense.row.mult.VectorVectorMult_DDRM;
import org.ejml.interfaces.decomposition.EigenDecomposition_F64;
import org.ejml.interfaces.decomposition.LUDecomposition_F64;
import org.ejml.interfaces.decomposition.SingularValueDecomposition_F64;

public class MatrixFeatures_DDRM {
    public static boolean hasNaN(DMatrixD1 m) {
        int length = m.getNumElements();
        for (int i = 0; i < length; ++i) {
            if (!Double.isNaN(m.get(i))) continue;
            return true;
        }
        return false;
    }

    public static boolean hasUncountable(DMatrixD1 m) {
        int length = m.getNumElements();
        for (int i = 0; i < length; ++i) {
            double a = m.get(i);
            if (!Double.isNaN(a) && !Double.isInfinite(a)) continue;
            return true;
        }
        return false;
    }

    public static boolean isZeros(DMatrixD1 m, double tol) {
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

    public static boolean isPositiveDefinite(DMatrixRMaj A) {
        if (!MatrixFeatures_DDRM.isSquare(A)) {
            return false;
        }
        CholeskyDecompositionInner_DDRM chol = new CholeskyDecompositionInner_DDRM(true);
        if (chol.inputModified()) {
            A = A.copy();
        }
        return chol.decompose(A);
    }

    public static boolean isPositiveSemidefinite(DMatrixRMaj A) {
        if (!MatrixFeatures_DDRM.isSquare(A)) {
            return false;
        }
        EigenDecomposition_F64<DMatrixRMaj> eig = DecompositionFactory_DDRM.eig(A.numCols, false);
        if (eig.inputModified()) {
            A = A.copy();
        }
        eig.decompose(A);
        for (int i = 0; i < A.numRows; ++i) {
            Complex_F64 v = eig.getEigenvalue(i);
            if (!(v.getReal() < 0.0)) continue;
            return false;
        }
        return true;
    }

    public static boolean isSquare(DMatrixD1 mat) {
        return mat.numCols == mat.numRows;
    }

    public static boolean isSymmetric(DMatrixRMaj m, double tol) {
        if (m.numCols != m.numRows) {
            return false;
        }
        double max = CommonOps_DDRM.elementMaxAbs(m);
        for (int i = 0; i < m.numRows; ++i) {
            for (int j = 0; j < i; ++j) {
                double b;
                double a = m.get(i, j) / max;
                double diff = Math.abs(a - (b = m.get(j, i) / max));
                if (diff <= tol) continue;
                return false;
            }
        }
        return true;
    }

    public static boolean isSymmetric(DMatrixRMaj m) {
        return MatrixFeatures_DDRM.isSymmetric(m, 0.0);
    }

    public static boolean isSkewSymmetric(DMatrixRMaj A, double tol) {
        if (A.numCols != A.numRows) {
            return false;
        }
        for (int i = 0; i < A.numRows; ++i) {
            for (int j = 0; j < i; ++j) {
                double b;
                double a = A.get(i, j);
                double diff = Math.abs(a + (b = A.get(j, i)));
                if (diff <= tol) continue;
                return false;
            }
        }
        return true;
    }

    public static boolean isInverse(DMatrixRMaj a, DMatrixRMaj b, double tol) {
        if (a.numRows != b.numRows || a.numCols != b.numCols) {
            return false;
        }
        int numRows = a.numRows;
        int numCols = a.numCols;
        for (int i = 0; i < numRows; ++i) {
            for (int j = 0; j < numCols; ++j) {
                double total = 0.0;
                for (int k = 0; k < numCols; ++k) {
                    total += a.get(i, k) * b.get(k, j);
                }
                if (!(i == j ? !(Math.abs(total - 1.0) <= tol) : !(Math.abs(total) <= tol))) continue;
                return false;
            }
        }
        return true;
    }

    public static boolean isEquals(DMatrixD1 a, DMatrixD1 b, double tol) {
        if (a.numRows != b.numRows || a.numCols != b.numCols) {
            return false;
        }
        if (tol == 0.0) {
            return MatrixFeatures_DDRM.isEquals(a, b);
        }
        int length = a.getNumElements();
        for (int i = 0; i < length; ++i) {
            if (tol >= Math.abs(a.get(i) - b.get(i))) continue;
            return false;
        }
        return true;
    }

    public static boolean isEqualsTriangle(DMatrix a, DMatrix b, boolean upper, double tol) {
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

    public static boolean isEquals(DMatrixD1 a, DMatrixD1 b) {
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

    public static boolean isIdentical(DMatrixD1 a, DMatrixD1 b, double tol) {
        if (a.numRows != b.numRows || a.numCols != b.numCols) {
            return false;
        }
        if (tol < 0.0) {
            throw new IllegalArgumentException("Tolerance must be greater than or equal to zero.");
        }
        int length = a.getNumElements();
        for (int i = 0; i < length; ++i) {
            if (UtilEjml.isIdentical(a.get(i), b.get(i), tol)) continue;
            return false;
        }
        return true;
    }

    public static boolean isOrthogonal(DMatrixRMaj Q, double tol) {
        if (Q.numRows < Q.numCols) {
            throw new IllegalArgumentException("The number of rows must be more than or equal to the number of columns");
        }
        DMatrixRMaj[] u = CommonOps_DDRM.columnsToVector(Q, null);
        for (int i = 0; i < u.length; ++i) {
            DMatrixRMaj a = u[i];
            for (int j = i + 1; j < u.length; ++j) {
                double val = VectorVectorMult_DDRM.innerProd(a, u[j]);
                if (Math.abs(val) <= tol) continue;
                return false;
            }
        }
        return true;
    }

    public static boolean isRowsLinearIndependent(DMatrixRMaj A) {
        LUDecomposition_F64<DMatrixRMaj> lu = DecompositionFactory_DDRM.lu(A.numRows, A.numCols);
        if (lu.inputModified()) {
            A = A.copy();
        }
        if (!lu.decompose(A)) {
            throw new RuntimeException("Decompositon failed?");
        }
        return !lu.isSingular();
    }

    public static boolean isIdentity(DMatrixRMaj mat, double tol) {
        int index = 0;
        for (int i = 0; i < mat.numRows; ++i) {
            for (int j = 0; j < mat.numCols; ++j) {
                if (!(i == j ? !(Math.abs(mat.get(index++) - 1.0) <= tol) : !(Math.abs(mat.get(index++)) <= tol))) continue;
                return false;
            }
        }
        return true;
    }

    public static boolean isConstantVal(DMatrixRMaj mat, double val, double tol) {
        int index = 0;
        for (int i = 0; i < mat.numRows; ++i) {
            for (int j = 0; j < mat.numCols; ++j) {
                if (Math.abs(mat.get(index++) - val) <= tol) continue;
                return false;
            }
        }
        return true;
    }

    public static boolean isDiagonalPositive(DMatrixRMaj a) {
        for (int i = 0; i < a.numRows; ++i) {
            if (a.get(i, i) >= 0.0) continue;
            return false;
        }
        return true;
    }

    public static boolean isFullRank(DMatrixRMaj a) {
        throw new RuntimeException("Implement");
    }

    public static boolean isNegative(DMatrixD1 a, DMatrixD1 b, double tol) {
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

    public static boolean isUpperTriangle(DMatrixRMaj A, int hessenberg, double tol) {
        for (int i = hessenberg + 1; i < A.numRows; ++i) {
            int maxCol = Math.min(i - hessenberg, A.numCols);
            for (int j = 0; j < maxCol; ++j) {
                if (Math.abs(A.unsafe_get(i, j)) <= tol) continue;
                return false;
            }
        }
        return true;
    }

    public static boolean isLowerTriangle(DMatrixRMaj A, int hessenberg, double tol) {
        for (int i = 0; i < A.numRows - hessenberg - 1; ++i) {
            for (int j = i + hessenberg + 1; j < A.numCols; ++j) {
                if (Math.abs(A.unsafe_get(i, j)) <= tol) continue;
                return false;
            }
        }
        return true;
    }

    public static int rank(DMatrixRMaj A) {
        return MatrixFeatures_DDRM.rank(A, UtilEjml.EPS * 100.0);
    }

    public static int rank(DMatrixRMaj A, double threshold) {
        SingularValueDecomposition_F64<DMatrixRMaj> svd = DecompositionFactory_DDRM.svd(A.numRows, A.numCols, false, false, true);
        if (svd.inputModified()) {
            A = A.copy();
        }
        if (!svd.decompose(A)) {
            throw new RuntimeException("Decomposition failed");
        }
        return SingularOps_DDRM.rank(svd, threshold);
    }

    public static int nullity(DMatrixRMaj A) {
        return MatrixFeatures_DDRM.nullity(A, UtilEjml.EPS * 100.0);
    }

    public static int nullity(DMatrixRMaj A, double threshold) {
        SingularValueDecomposition_F64<DMatrixRMaj> svd = DecompositionFactory_DDRM.svd(A.numRows, A.numCols, false, false, true);
        if (svd.inputModified()) {
            A = A.copy();
        }
        if (!svd.decompose(A)) {
            throw new RuntimeException("Decomposition failed");
        }
        return SingularOps_DDRM.nullity(svd, threshold);
    }

    public static int countNonZero(DMatrixRMaj A) {
        int total = 0;
        int index = 0;
        for (int row = 0; row < A.numRows; ++row) {
            int col = 0;
            while (col < A.numCols) {
                if (A.data[index] != 0.0) {
                    ++total;
                }
                ++col;
                ++index;
            }
        }
        return total;
    }
}

