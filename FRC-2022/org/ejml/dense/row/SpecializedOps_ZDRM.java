/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  org.jetbrains.annotations.Nullable
 */
package org.ejml.dense.row;

import org.ejml.data.Complex_F64;
import org.ejml.data.ZMatrixRMaj;
import org.ejml.dense.row.CommonOps_ZDRM;
import org.ejml.dense.row.MatrixFeatures_ZDRM;
import org.ejml.dense.row.NormOps_ZDRM;
import org.ejml.dense.row.mult.VectorVectorMult_ZDRM;
import org.jetbrains.annotations.Nullable;

public class SpecializedOps_ZDRM {
    public static ZMatrixRMaj createReflector(ZMatrixRMaj u) {
        if (!MatrixFeatures_ZDRM.isVector(u)) {
            throw new IllegalArgumentException("u must be a vector");
        }
        double norm = NormOps_ZDRM.normF(u);
        double gamma = -2.0 / (norm * norm);
        ZMatrixRMaj Q = CommonOps_ZDRM.identity(u.getNumElements());
        CommonOps_ZDRM.multAddTransB(gamma, 0.0, u, u, Q);
        return Q;
    }

    public static ZMatrixRMaj createReflector(ZMatrixRMaj u, double gamma) {
        if (!MatrixFeatures_ZDRM.isVector(u)) {
            throw new IllegalArgumentException("u must be a vector");
        }
        ZMatrixRMaj Q = CommonOps_ZDRM.identity(u.getNumElements());
        CommonOps_ZDRM.multAddTransB(-gamma, 0.0, u, u, Q);
        return Q;
    }

    public static ZMatrixRMaj pivotMatrix(@Nullable ZMatrixRMaj ret, int[] pivots, int numPivots, boolean transposed) {
        if (ret == null) {
            ret = new ZMatrixRMaj(numPivots, numPivots);
        } else {
            if (ret.numCols != numPivots || ret.numRows != numPivots) {
                throw new IllegalArgumentException("Unexpected matrix dimension");
            }
            CommonOps_ZDRM.fill(ret, 0.0, 0.0);
        }
        if (transposed) {
            for (int i = 0; i < numPivots; ++i) {
                ret.set(pivots[i], i, 1.0, 0.0);
            }
        } else {
            for (int i = 0; i < numPivots; ++i) {
                ret.set(i, pivots[i], 1.0, 0.0);
            }
        }
        return ret;
    }

    public static double elementDiagMaxMagnitude2(ZMatrixRMaj a) {
        int size = Math.min(a.numRows, a.numCols);
        int rowStride = a.getRowStride();
        double max = 0.0;
        for (int i = 0; i < size; ++i) {
            int index = i * rowStride + i * 2;
            double real = a.data[index];
            double imaginary = a.data[index + 1];
            double m = real * real + imaginary * imaginary;
            if (!(m > max)) continue;
            max = m;
        }
        return max;
    }

    public static double qualityTriangular(ZMatrixRMaj T) {
        int N = Math.min(T.numRows, T.numCols);
        double max = SpecializedOps_ZDRM.elementDiagMaxMagnitude2(T);
        if (max == 0.0) {
            return 0.0;
        }
        max = Math.sqrt(max);
        int rowStride = T.getRowStride();
        double qualityR = 1.0;
        double qualityI = 0.0;
        for (int i = 0; i < N; ++i) {
            int index = i * rowStride + i * 2;
            double real = T.data[index] / max;
            double imaginary = T.data[index] / max;
            double r = qualityR * real - qualityI * imaginary;
            double img = qualityR * imaginary + real * qualityI;
            qualityR = r;
            qualityI = img;
        }
        return Math.sqrt(qualityR * qualityR + qualityI * qualityI);
    }

    public static ZMatrixRMaj householder(ZMatrixRMaj u, double gamma) {
        int N = u.getDataLength() / 2;
        ZMatrixRMaj uut = new ZMatrixRMaj(N, N);
        VectorVectorMult_ZDRM.outerProdH(u, u, uut);
        CommonOps_ZDRM.elementMultiply(uut, -gamma, 0.0, uut);
        for (int i = 0; i < N; ++i) {
            int index = (i * uut.numCols + i) * 2;
            uut.data[index] = 1.0 + uut.data[index];
        }
        return uut;
    }

    public static ZMatrixRMaj householderVector(ZMatrixRMaj x) {
        double imagTau;
        double realTau;
        ZMatrixRMaj u = x.copy();
        double max = CommonOps_ZDRM.elementMaxAbs(u);
        CommonOps_ZDRM.elementDivide(u, max, 0.0, u);
        double nx = NormOps_ZDRM.normF(u);
        Complex_F64 c = new Complex_F64();
        u.get(0, 0, c);
        if (c.getMagnitude() == 0.0) {
            realTau = nx;
            imagTau = 0.0;
        } else {
            realTau = c.real / c.getMagnitude() * nx;
            imagTau = c.imaginary / c.getMagnitude() * nx;
        }
        u.set(0, 0, c.real + realTau, c.imaginary + imagTau);
        CommonOps_ZDRM.elementDivide(u, u.getReal(0, 0), u.getImag(0, 0), u);
        return u;
    }
}

