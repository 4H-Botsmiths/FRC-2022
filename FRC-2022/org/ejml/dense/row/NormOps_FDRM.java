/*
 * Decompiled with CFR 0.150.
 */
package org.ejml.dense.row;

import org.ejml.UtilEjml;
import org.ejml.data.FMatrix1Row;
import org.ejml.data.FMatrixD1;
import org.ejml.data.FMatrixRMaj;
import org.ejml.dense.row.CommonOps_FDRM;
import org.ejml.dense.row.MatrixFeatures_FDRM;
import org.ejml.dense.row.SingularOps_FDRM;
import org.ejml.dense.row.factory.DecompositionFactory_FDRM;
import org.ejml.interfaces.decomposition.SingularValueDecomposition_F32;

public class NormOps_FDRM {
    public static void normalizeF(FMatrixRMaj A) {
        float val = NormOps_FDRM.normF(A);
        if (val == 0.0f) {
            return;
        }
        int size = A.getNumElements();
        for (int i = 0; i < size; ++i) {
            A.div(i, val);
        }
    }

    public static float conditionP(FMatrixRMaj A, float p) {
        if (p == 2.0f) {
            return NormOps_FDRM.conditionP2(A);
        }
        if (A.numRows == A.numCols) {
            FMatrixRMaj A_inv = new FMatrixRMaj(A.numRows, A.numCols);
            if (!CommonOps_FDRM.invert(A, A_inv)) {
                throw new IllegalArgumentException("A can't be inverted.");
            }
            return NormOps_FDRM.normP(A, p) * NormOps_FDRM.normP(A_inv, p);
        }
        FMatrixRMaj pinv = new FMatrixRMaj(A.numCols, A.numRows);
        CommonOps_FDRM.pinv(A, pinv);
        return NormOps_FDRM.normP(A, p) * NormOps_FDRM.normP(pinv, p);
    }

    public static float conditionP2(FMatrixRMaj A) {
        SingularValueDecomposition_F32<FMatrixRMaj> svd = DecompositionFactory_FDRM.svd(A.numRows, A.numCols, false, false, true);
        svd.decompose(A);
        float[] singularValues = svd.getSingularValues();
        int n = SingularOps_FDRM.rank(svd, UtilEjml.TEST_F32);
        if (n == 0) {
            return 0.0f;
        }
        float smallest = Float.MAX_VALUE;
        float largest = Float.MIN_VALUE;
        for (float s : singularValues) {
            if (s < smallest) {
                smallest = s;
            }
            if (!(s > largest)) continue;
            largest = s;
        }
        return largest / smallest;
    }

    public static float fastNormF(FMatrixD1 a) {
        float total = 0.0f;
        int size = a.getNumElements();
        for (int i = 0; i < size; ++i) {
            float val = a.get(i);
            total += val * val;
        }
        return (float)Math.sqrt(total);
    }

    public static float normF(FMatrixD1 a) {
        float total = 0.0f;
        float scale = CommonOps_FDRM.elementMaxAbs(a);
        if (scale == 0.0f) {
            return 0.0f;
        }
        int size = a.getNumElements();
        for (int i = 0; i < size; ++i) {
            float val = a.get(i) / scale;
            total += val * val;
        }
        return scale * (float)Math.sqrt(total);
    }

    public static float elementP(FMatrix1Row A, float p) {
        if (p == 1.0f) {
            return CommonOps_FDRM.elementSumAbs(A);
        }
        if (p == 2.0f) {
            return NormOps_FDRM.normF(A);
        }
        float max = CommonOps_FDRM.elementMaxAbs(A);
        if (max == 0.0f) {
            return 0.0f;
        }
        float total = 0.0f;
        int size = A.getNumElements();
        for (int i = 0; i < size; ++i) {
            float a = A.get(i) / max;
            total += (float)Math.pow(Math.abs(a), p);
        }
        return max * (float)Math.pow(total, 1.0f / p);
    }

    public static float fastElementP(FMatrixD1 A, float p) {
        if (p == 2.0f) {
            return NormOps_FDRM.fastNormF(A);
        }
        float total = 0.0f;
        int size = A.getNumElements();
        for (int i = 0; i < size; ++i) {
            float a = A.get(i);
            total += (float)Math.pow(Math.abs(a), p);
        }
        return (float)Math.pow(total, 1.0f / p);
    }

    public static float normP(FMatrixRMaj A, float p) {
        if (p == 1.0f) {
            return NormOps_FDRM.normP1(A);
        }
        if (p == 2.0f) {
            return NormOps_FDRM.normP2(A);
        }
        if (Float.isInfinite(p)) {
            return NormOps_FDRM.normPInf(A);
        }
        if (MatrixFeatures_FDRM.isVector(A)) {
            return NormOps_FDRM.elementP(A, p);
        }
        throw new IllegalArgumentException("Doesn't support induced norms yet.");
    }

    public static float fastNormP(FMatrixRMaj A, float p) {
        if (p == 1.0f) {
            return NormOps_FDRM.normP1(A);
        }
        if (p == 2.0f) {
            return NormOps_FDRM.fastNormP2(A);
        }
        if (Float.isInfinite(p)) {
            return NormOps_FDRM.normPInf(A);
        }
        if (MatrixFeatures_FDRM.isVector(A)) {
            return NormOps_FDRM.fastElementP(A, p);
        }
        throw new IllegalArgumentException("Doesn't support induced norms yet.");
    }

    public static float normP1(FMatrixRMaj A) {
        if (MatrixFeatures_FDRM.isVector(A)) {
            return CommonOps_FDRM.elementSumAbs(A);
        }
        return NormOps_FDRM.inducedP1(A);
    }

    public static float normP2(FMatrixRMaj A) {
        if (MatrixFeatures_FDRM.isVector(A)) {
            return NormOps_FDRM.normF(A);
        }
        return NormOps_FDRM.inducedP2(A);
    }

    public static float fastNormP2(FMatrixRMaj A) {
        if (MatrixFeatures_FDRM.isVector(A)) {
            return NormOps_FDRM.fastNormF(A);
        }
        return NormOps_FDRM.inducedP2(A);
    }

    public static float normPInf(FMatrixRMaj A) {
        if (MatrixFeatures_FDRM.isVector(A)) {
            return CommonOps_FDRM.elementMaxAbs(A);
        }
        return NormOps_FDRM.inducedPInf(A);
    }

    public static float inducedP1(FMatrixRMaj A) {
        float max = 0.0f;
        int m = A.numRows;
        int n = A.numCols;
        for (int j = 0; j < n; ++j) {
            float total = 0.0f;
            for (int i = 0; i < m; ++i) {
                total += Math.abs(A.get(i, j));
            }
            if (!(total > max)) continue;
            max = total;
        }
        return max;
    }

    public static float inducedP2(FMatrixRMaj A) {
        SingularValueDecomposition_F32<FMatrixRMaj> svd = DecompositionFactory_FDRM.svd(A.numRows, A.numCols, false, false, true);
        if (!svd.decompose(A)) {
            throw new RuntimeException("Decomposition failed");
        }
        float[] singularValues = svd.getSingularValues();
        return UtilEjml.max(singularValues, 0, singularValues.length);
    }

    public static float inducedPInf(FMatrixRMaj A) {
        float max = 0.0f;
        int m = A.numRows;
        int n = A.numCols;
        for (int i = 0; i < m; ++i) {
            float total = 0.0f;
            for (int j = 0; j < n; ++j) {
                total += Math.abs(A.get(i, j));
            }
            if (!(total > max)) continue;
            max = total;
        }
        return max;
    }
}

