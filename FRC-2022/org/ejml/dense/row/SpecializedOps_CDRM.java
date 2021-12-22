/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  org.jetbrains.annotations.Nullable
 */
package org.ejml.dense.row;

import org.ejml.data.CMatrixRMaj;
import org.ejml.data.Complex_F32;
import org.ejml.dense.row.CommonOps_CDRM;
import org.ejml.dense.row.MatrixFeatures_CDRM;
import org.ejml.dense.row.NormOps_CDRM;
import org.ejml.dense.row.mult.VectorVectorMult_CDRM;
import org.jetbrains.annotations.Nullable;

public class SpecializedOps_CDRM {
    public static CMatrixRMaj createReflector(CMatrixRMaj u) {
        if (!MatrixFeatures_CDRM.isVector(u)) {
            throw new IllegalArgumentException("u must be a vector");
        }
        float norm = NormOps_CDRM.normF(u);
        float gamma = -2.0f / (norm * norm);
        CMatrixRMaj Q = CommonOps_CDRM.identity(u.getNumElements());
        CommonOps_CDRM.multAddTransB(gamma, 0.0f, u, u, Q);
        return Q;
    }

    public static CMatrixRMaj createReflector(CMatrixRMaj u, float gamma) {
        if (!MatrixFeatures_CDRM.isVector(u)) {
            throw new IllegalArgumentException("u must be a vector");
        }
        CMatrixRMaj Q = CommonOps_CDRM.identity(u.getNumElements());
        CommonOps_CDRM.multAddTransB(-gamma, 0.0f, u, u, Q);
        return Q;
    }

    public static CMatrixRMaj pivotMatrix(@Nullable CMatrixRMaj ret, int[] pivots, int numPivots, boolean transposed) {
        if (ret == null) {
            ret = new CMatrixRMaj(numPivots, numPivots);
        } else {
            if (ret.numCols != numPivots || ret.numRows != numPivots) {
                throw new IllegalArgumentException("Unexpected matrix dimension");
            }
            CommonOps_CDRM.fill(ret, 0.0f, 0.0f);
        }
        if (transposed) {
            for (int i = 0; i < numPivots; ++i) {
                ret.set(pivots[i], i, 1.0f, 0.0f);
            }
        } else {
            for (int i = 0; i < numPivots; ++i) {
                ret.set(i, pivots[i], 1.0f, 0.0f);
            }
        }
        return ret;
    }

    public static float elementDiagMaxMagnitude2(CMatrixRMaj a) {
        int size = Math.min(a.numRows, a.numCols);
        int rowStride = a.getRowStride();
        float max = 0.0f;
        for (int i = 0; i < size; ++i) {
            int index = i * rowStride + i * 2;
            float real = a.data[index];
            float imaginary = a.data[index + 1];
            float m = real * real + imaginary * imaginary;
            if (!(m > max)) continue;
            max = m;
        }
        return max;
    }

    public static float qualityTriangular(CMatrixRMaj T) {
        int N = Math.min(T.numRows, T.numCols);
        float max = SpecializedOps_CDRM.elementDiagMaxMagnitude2(T);
        if (max == 0.0f) {
            return 0.0f;
        }
        max = (float)Math.sqrt(max);
        int rowStride = T.getRowStride();
        float qualityR = 1.0f;
        float qualityI = 0.0f;
        for (int i = 0; i < N; ++i) {
            int index = i * rowStride + i * 2;
            float real = T.data[index] / max;
            float imaginary = T.data[index] / max;
            float r = qualityR * real - qualityI * imaginary;
            float img = qualityR * imaginary + real * qualityI;
            qualityR = r;
            qualityI = img;
        }
        return (float)Math.sqrt(qualityR * qualityR + qualityI * qualityI);
    }

    public static CMatrixRMaj householder(CMatrixRMaj u, float gamma) {
        int N = u.getDataLength() / 2;
        CMatrixRMaj uut = new CMatrixRMaj(N, N);
        VectorVectorMult_CDRM.outerProdH(u, u, uut);
        CommonOps_CDRM.elementMultiply(uut, -gamma, 0.0f, uut);
        for (int i = 0; i < N; ++i) {
            int index = (i * uut.numCols + i) * 2;
            uut.data[index] = 1.0f + uut.data[index];
        }
        return uut;
    }

    public static CMatrixRMaj householderVector(CMatrixRMaj x) {
        float imagTau;
        float realTau;
        CMatrixRMaj u = x.copy();
        float max = CommonOps_CDRM.elementMaxAbs(u);
        CommonOps_CDRM.elementDivide(u, max, 0.0f, u);
        float nx = NormOps_CDRM.normF(u);
        Complex_F32 c = new Complex_F32();
        u.get(0, 0, c);
        if (c.getMagnitude() == 0.0f) {
            realTau = nx;
            imagTau = 0.0f;
        } else {
            realTau = c.real / c.getMagnitude() * nx;
            imagTau = c.imaginary / c.getMagnitude() * nx;
        }
        u.set(0, 0, c.real + realTau, c.imaginary + imagTau);
        CommonOps_CDRM.elementDivide(u, u.getReal(0, 0), u.getImag(0, 0), u);
        return u;
    }
}

