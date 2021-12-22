/*
 * Decompiled with CFR 0.150.
 */
package org.ejml.dense.row;

import org.ejml.data.CMatrix;
import org.ejml.data.CMatrixD1;
import org.ejml.data.CMatrixRMaj;
import org.ejml.data.Complex_F32;
import org.ejml.data.Matrix;
import org.ejml.dense.row.CommonOps_CDRM;
import org.ejml.dense.row.decompose.chol.CholeskyDecompositionInner_CDRM;
import org.ejml.dense.row.mult.VectorVectorMult_CDRM;

public class MatrixFeatures_CDRM {
    public static boolean isVector(Matrix mat) {
        return mat.getNumCols() == 1 || mat.getNumRows() == 1;
    }

    public static boolean isNegative(CMatrixD1 a, CMatrixD1 b, float tol) {
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

    public static boolean hasNaN(CMatrixD1 m) {
        int length = m.getDataLength();
        for (int i = 0; i < length; ++i) {
            if (!Float.isNaN(m.data[i])) continue;
            return true;
        }
        return false;
    }

    public static boolean hasUncountable(CMatrixD1 m) {
        int length = m.getDataLength();
        for (int i = 0; i < length; ++i) {
            float a = m.data[i];
            if (!Float.isNaN(a) && !Float.isInfinite(a)) continue;
            return true;
        }
        return false;
    }

    public static boolean isEquals(CMatrixD1 a, CMatrixD1 b) {
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

    public static boolean isEquals(CMatrixD1 a, CMatrixD1 b, float tol) {
        if (a.numRows != b.numRows || a.numCols != b.numCols) {
            return false;
        }
        if (tol == 0.0f) {
            return MatrixFeatures_CDRM.isEquals(a, b);
        }
        int length = a.getDataLength();
        for (int i = 0; i < length; ++i) {
            if (tol >= Math.abs(a.data[i] - b.data[i])) continue;
            return false;
        }
        return true;
    }

    public static boolean isIdentical(CMatrixD1 a, CMatrixD1 b, float tol) {
        if (a.numRows != b.numRows || a.numCols != b.numCols) {
            return false;
        }
        if (tol < 0.0f) {
            throw new IllegalArgumentException("Tolerance must be greater than or equal to zero.");
        }
        int length = a.getDataLength();
        for (int i = 0; i < length; ++i) {
            float valA = a.data[i];
            float valB = b.data[i];
            float diff = Math.abs(valA - valB);
            if (tol >= diff) continue;
            if (Float.isNaN(valA)) {
                return Float.isNaN(valB);
            }
            if (Float.isInfinite(valA)) {
                return valA == valB;
            }
            return false;
        }
        return true;
    }

    public static boolean isIdentity(CMatrix mat, float tol) {
        Complex_F32 c = new Complex_F32();
        for (int i = 0; i < mat.getNumRows(); ++i) {
            for (int j = 0; j < mat.getNumCols(); ++j) {
                mat.get(i, j, c);
                if (i == j) {
                    if (!(Math.abs(c.real - 1.0f) <= tol)) {
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

    public static boolean isHermitian(CMatrixRMaj Q, float tol) {
        if (Q.numCols != Q.numRows) {
            return false;
        }
        Complex_F32 a = new Complex_F32();
        Complex_F32 b = new Complex_F32();
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

    public static boolean isUnitary(CMatrixRMaj Q, float tol) {
        if (Q.numRows < Q.numCols) {
            throw new IllegalArgumentException("The number of rows must be more than or equal to the number of columns");
        }
        Complex_F32 prod = new Complex_F32();
        CMatrixRMaj[] u = CommonOps_CDRM.columnsToVector(Q, null);
        for (int i = 0; i < u.length; ++i) {
            CMatrixRMaj a = u[i];
            VectorVectorMult_CDRM.innerProdH(a, a, prod);
            if (Math.abs(prod.real - 1.0f) > tol) {
                return false;
            }
            if (Math.abs(prod.imaginary) > tol) {
                return false;
            }
            for (int j = i + 1; j < u.length; ++j) {
                VectorVectorMult_CDRM.innerProdH(a, u[j], prod);
                if (prod.getMagnitude2() <= tol * tol) continue;
                return false;
            }
        }
        return true;
    }

    public static boolean isPositiveDefinite(CMatrixRMaj A) {
        if (A.numCols != A.numRows) {
            return false;
        }
        CholeskyDecompositionInner_CDRM chol = new CholeskyDecompositionInner_CDRM(true);
        if (chol.inputModified()) {
            A = A.copy();
        }
        return chol.decompose(A);
    }

    public static boolean isUpperTriangle(CMatrixRMaj A, int hessenberg, float tol) {
        tol *= tol;
        for (int i = hessenberg + 1; i < A.numRows; ++i) {
            int maxCol = Math.min(i - hessenberg, A.numCols);
            for (int j = 0; j < maxCol; ++j) {
                int index = (i * A.numCols + j) * 2;
                float real = A.data[index];
                float imag = A.data[index + 1];
                float mag = real * real + imag * imag;
                if (mag <= tol) continue;
                return false;
            }
        }
        return true;
    }

    public static boolean isLowerTriangle(CMatrixRMaj A, int hessenberg, float tol) {
        tol *= tol;
        for (int i = 0; i < A.numRows - hessenberg - 1; ++i) {
            for (int j = i + hessenberg + 1; j < A.numCols; ++j) {
                int index = (i * A.numCols + j) * 2;
                float real = A.data[index];
                float imag = A.data[index + 1];
                float mag = real * real + imag * imag;
                if (mag <= tol) continue;
                return false;
            }
        }
        return true;
    }

    public static boolean isZeros(CMatrixD1 m, float tol) {
        int length = m.getNumElements() * 2;
        for (int i = 0; i < length; ++i) {
            if (!(Math.abs(m.data[i]) > tol)) continue;
            return false;
        }
        return true;
    }
}

