/*
 * Decompiled with CFR 0.150.
 */
package org.ejml.dense.row;

import org.ejml.data.Complex_F64;
import org.ejml.data.Matrix;
import org.ejml.data.ZMatrix;
import org.ejml.data.ZMatrixD1;
import org.ejml.data.ZMatrixRMaj;
import org.ejml.dense.row.CommonOps_ZDRM;
import org.ejml.dense.row.decompose.chol.CholeskyDecompositionInner_ZDRM;
import org.ejml.dense.row.mult.VectorVectorMult_ZDRM;

public class MatrixFeatures_ZDRM {
    public static boolean isVector(Matrix mat) {
        return mat.getNumCols() == 1 || mat.getNumRows() == 1;
    }

    public static boolean isNegative(ZMatrixD1 a, ZMatrixD1 b, double tol) {
        if (a.numRows != b.numRows || a.numCols != b.numCols) {
            throw new IllegalArgumentException("Matrix dimensions must match");
        }
        int length = a.getNumElements() * 2;
        for (int i = 0; i < length; ++i) {
            if (Math.abs(a.data[i] + b.data[i]) <= tol) continue;
            return false;
        }
        return true;
    }

    public static boolean hasNaN(ZMatrixD1 m) {
        int length = m.getDataLength();
        for (int i = 0; i < length; ++i) {
            if (!Double.isNaN(m.data[i])) continue;
            return true;
        }
        return false;
    }

    public static boolean hasUncountable(ZMatrixD1 m) {
        int length = m.getDataLength();
        for (int i = 0; i < length; ++i) {
            double a = m.data[i];
            if (!Double.isNaN(a) && !Double.isInfinite(a)) continue;
            return true;
        }
        return false;
    }

    public static boolean isEquals(ZMatrixD1 a, ZMatrixD1 b) {
        if (a.numRows != b.numRows || a.numCols != b.numCols) {
            return false;
        }
        int length = a.getDataLength();
        for (int i = 0; i < length; ++i) {
            if (a.data[i] == b.data[i]) continue;
            return false;
        }
        return true;
    }

    public static boolean isEquals(ZMatrixD1 a, ZMatrixD1 b, double tol) {
        if (a.numRows != b.numRows || a.numCols != b.numCols) {
            return false;
        }
        if (tol == 0.0) {
            return MatrixFeatures_ZDRM.isEquals(a, b);
        }
        int length = a.getDataLength();
        for (int i = 0; i < length; ++i) {
            if (tol >= Math.abs(a.data[i] - b.data[i])) continue;
            return false;
        }
        return true;
    }

    public static boolean isIdentical(ZMatrixD1 a, ZMatrixD1 b, double tol) {
        if (a.numRows != b.numRows || a.numCols != b.numCols) {
            return false;
        }
        if (tol < 0.0) {
            throw new IllegalArgumentException("Tolerance must be greater than or equal to zero.");
        }
        int length = a.getDataLength();
        for (int i = 0; i < length; ++i) {
            double valA = a.data[i];
            double valB = b.data[i];
            double diff = Math.abs(valA - valB);
            if (tol >= diff) continue;
            if (Double.isNaN(valA)) {
                return Double.isNaN(valB);
            }
            if (Double.isInfinite(valA)) {
                return valA == valB;
            }
            return false;
        }
        return true;
    }

    public static boolean isIdentity(ZMatrix mat, double tol) {
        Complex_F64 c = new Complex_F64();
        for (int i = 0; i < mat.getNumRows(); ++i) {
            for (int j = 0; j < mat.getNumCols(); ++j) {
                mat.get(i, j, c);
                if (i == j) {
                    if (!(Math.abs(c.real - 1.0) <= tol)) {
                        return false;
                    }
                    if (Math.abs(c.imaginary) <= tol) continue;
                    return false;
                }
                if (!(Math.abs(c.real) <= tol)) {
                    return false;
                }
                if (Math.abs(c.imaginary) <= tol) continue;
                return false;
            }
        }
        return true;
    }

    public static boolean isHermitian(ZMatrixRMaj Q, double tol) {
        if (Q.numCols != Q.numRows) {
            return false;
        }
        Complex_F64 a = new Complex_F64();
        Complex_F64 b = new Complex_F64();
        for (int i = 0; i < Q.numCols; ++i) {
            for (int j = i; j < Q.numCols; ++j) {
                Q.get(i, j, a);
                Q.get(j, i, b);
                if (Math.abs(a.real - b.real) > tol) {
                    return false;
                }
                if (!(Math.abs(a.imaginary + b.imaginary) > tol)) continue;
                return false;
            }
        }
        return true;
    }

    public static boolean isUnitary(ZMatrixRMaj Q, double tol) {
        if (Q.numRows < Q.numCols) {
            throw new IllegalArgumentException("The number of rows must be more than or equal to the number of columns");
        }
        Complex_F64 prod = new Complex_F64();
        ZMatrixRMaj[] u = CommonOps_ZDRM.columnsToVector(Q, null);
        for (int i = 0; i < u.length; ++i) {
            ZMatrixRMaj a = u[i];
            VectorVectorMult_ZDRM.innerProdH(a, a, prod);
            if (Math.abs(prod.real - 1.0) > tol) {
                return false;
            }
            if (Math.abs(prod.imaginary) > tol) {
                return false;
            }
            for (int j = i + 1; j < u.length; ++j) {
                VectorVectorMult_ZDRM.innerProdH(a, u[j], prod);
                if (prod.getMagnitude2() <= tol * tol) continue;
                return false;
            }
        }
        return true;
    }

    public static boolean isPositiveDefinite(ZMatrixRMaj A) {
        if (A.numCols != A.numRows) {
            return false;
        }
        CholeskyDecompositionInner_ZDRM chol = new CholeskyDecompositionInner_ZDRM(true);
        if (chol.inputModified()) {
            A = A.copy();
        }
        return chol.decompose(A);
    }

    public static boolean isUpperTriangle(ZMatrixRMaj A, int hessenberg, double tol) {
        tol *= tol;
        for (int i = hessenberg + 1; i < A.numRows; ++i) {
            int maxCol = Math.min(i - hessenberg, A.numCols);
            for (int j = 0; j < maxCol; ++j) {
                int index = (i * A.numCols + j) * 2;
                double real = A.data[index];
                double imag = A.data[index + 1];
                double mag = real * real + imag * imag;
                if (mag <= tol) continue;
                return false;
            }
        }
        return true;
    }

    public static boolean isLowerTriangle(ZMatrixRMaj A, int hessenberg, double tol) {
        tol *= tol;
        for (int i = 0; i < A.numRows - hessenberg - 1; ++i) {
            for (int j = i + hessenberg + 1; j < A.numCols; ++j) {
                int index = (i * A.numCols + j) * 2;
                double real = A.data[index];
                double imag = A.data[index + 1];
                double mag = real * real + imag * imag;
                if (mag <= tol) continue;
                return false;
            }
        }
        return true;
    }

    public static boolean isZeros(ZMatrixD1 m, double tol) {
        int length = m.getNumElements() * 2;
        for (int i = 0; i < length; ++i) {
            if (!(Math.abs(m.data[i]) > tol)) continue;
            return false;
        }
        return true;
    }
}

