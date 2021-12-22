/*
 * Decompiled with CFR 0.150.
 */
package org.ejml.dense.row;

import org.ejml.UtilEjml;
import org.ejml.data.DMatrix1Row;
import org.ejml.data.DMatrixD1;
import org.ejml.data.DMatrixRMaj;
import org.ejml.dense.row.CommonOps_DDRM;
import org.ejml.dense.row.MatrixFeatures_DDRM;
import org.ejml.dense.row.SingularOps_DDRM;
import org.ejml.dense.row.factory.DecompositionFactory_DDRM;
import org.ejml.interfaces.decomposition.SingularValueDecomposition_F64;

public class NormOps_DDRM {
    public static void normalizeF(DMatrixRMaj A) {
        double val = NormOps_DDRM.normF(A);
        if (val == 0.0) {
            return;
        }
        int size = A.getNumElements();
        for (int i = 0; i < size; ++i) {
            A.div(i, val);
        }
    }

    public static double conditionP(DMatrixRMaj A, double p) {
        if (p == 2.0) {
            return NormOps_DDRM.conditionP2(A);
        }
        if (A.numRows == A.numCols) {
            DMatrixRMaj A_inv = new DMatrixRMaj(A.numRows, A.numCols);
            if (!CommonOps_DDRM.invert(A, A_inv)) {
                throw new IllegalArgumentException("A can't be inverted.");
            }
            return NormOps_DDRM.normP(A, p) * NormOps_DDRM.normP(A_inv, p);
        }
        DMatrixRMaj pinv = new DMatrixRMaj(A.numCols, A.numRows);
        CommonOps_DDRM.pinv(A, pinv);
        return NormOps_DDRM.normP(A, p) * NormOps_DDRM.normP(pinv, p);
    }

    public static double conditionP2(DMatrixRMaj A) {
        SingularValueDecomposition_F64<DMatrixRMaj> svd = DecompositionFactory_DDRM.svd(A.numRows, A.numCols, false, false, true);
        svd.decompose(A);
        double[] singularValues = svd.getSingularValues();
        int n = SingularOps_DDRM.rank(svd, UtilEjml.TEST_F64);
        if (n == 0) {
            return 0.0;
        }
        double smallest = Double.MAX_VALUE;
        double largest = Double.MIN_VALUE;
        for (double s : singularValues) {
            if (s < smallest) {
                smallest = s;
            }
            if (!(s > largest)) continue;
            largest = s;
        }
        return largest / smallest;
    }

    public static double fastNormF(DMatrixD1 a) {
        double total = 0.0;
        int size = a.getNumElements();
        for (int i = 0; i < size; ++i) {
            double val = a.get(i);
            total += val * val;
        }
        return Math.sqrt(total);
    }

    public static double normF(DMatrixD1 a) {
        double total = 0.0;
        double scale = CommonOps_DDRM.elementMaxAbs(a);
        if (scale == 0.0) {
            return 0.0;
        }
        int size = a.getNumElements();
        for (int i = 0; i < size; ++i) {
            double val = a.get(i) / scale;
            total += val * val;
        }
        return scale * Math.sqrt(total);
    }

    public static double elementP(DMatrix1Row A, double p) {
        if (p == 1.0) {
            return CommonOps_DDRM.elementSumAbs(A);
        }
        if (p == 2.0) {
            return NormOps_DDRM.normF(A);
        }
        double max = CommonOps_DDRM.elementMaxAbs(A);
        if (max == 0.0) {
            return 0.0;
        }
        double total = 0.0;
        int size = A.getNumElements();
        for (int i = 0; i < size; ++i) {
            double a = A.get(i) / max;
            total += Math.pow(Math.abs(a), p);
        }
        return max * Math.pow(total, 1.0 / p);
    }

    public static double fastElementP(DMatrixD1 A, double p) {
        if (p == 2.0) {
            return NormOps_DDRM.fastNormF(A);
        }
        double total = 0.0;
        int size = A.getNumElements();
        for (int i = 0; i < size; ++i) {
            double a = A.get(i);
            total += Math.pow(Math.abs(a), p);
        }
        return Math.pow(total, 1.0 / p);
    }

    public static double normP(DMatrixRMaj A, double p) {
        if (p == 1.0) {
            return NormOps_DDRM.normP1(A);
        }
        if (p == 2.0) {
            return NormOps_DDRM.normP2(A);
        }
        if (Double.isInfinite(p)) {
            return NormOps_DDRM.normPInf(A);
        }
        if (MatrixFeatures_DDRM.isVector(A)) {
            return NormOps_DDRM.elementP(A, p);
        }
        throw new IllegalArgumentException("Doesn't support induced norms yet.");
    }

    public static double fastNormP(DMatrixRMaj A, double p) {
        if (p == 1.0) {
            return NormOps_DDRM.normP1(A);
        }
        if (p == 2.0) {
            return NormOps_DDRM.fastNormP2(A);
        }
        if (Double.isInfinite(p)) {
            return NormOps_DDRM.normPInf(A);
        }
        if (MatrixFeatures_DDRM.isVector(A)) {
            return NormOps_DDRM.fastElementP(A, p);
        }
        throw new IllegalArgumentException("Doesn't support induced norms yet.");
    }

    public static double normP1(DMatrixRMaj A) {
        if (MatrixFeatures_DDRM.isVector(A)) {
            return CommonOps_DDRM.elementSumAbs(A);
        }
        return NormOps_DDRM.inducedP1(A);
    }

    public static double normP2(DMatrixRMaj A) {
        if (MatrixFeatures_DDRM.isVector(A)) {
            return NormOps_DDRM.normF(A);
        }
        return NormOps_DDRM.inducedP2(A);
    }

    public static double fastNormP2(DMatrixRMaj A) {
        if (MatrixFeatures_DDRM.isVector(A)) {
            return NormOps_DDRM.fastNormF(A);
        }
        return NormOps_DDRM.inducedP2(A);
    }

    public static double normPInf(DMatrixRMaj A) {
        if (MatrixFeatures_DDRM.isVector(A)) {
            return CommonOps_DDRM.elementMaxAbs(A);
        }
        return NormOps_DDRM.inducedPInf(A);
    }

    public static double inducedP1(DMatrixRMaj A) {
        double max = 0.0;
        int m = A.numRows;
        int n = A.numCols;
        for (int j = 0; j < n; ++j) {
            double total = 0.0;
            for (int i = 0; i < m; ++i) {
                total += Math.abs(A.get(i, j));
            }
            if (!(total > max)) continue;
            max = total;
        }
        return max;
    }

    public static double inducedP2(DMatrixRMaj A) {
        SingularValueDecomposition_F64<DMatrixRMaj> svd = DecompositionFactory_DDRM.svd(A.numRows, A.numCols, false, false, true);
        if (!svd.decompose(A)) {
            throw new RuntimeException("Decomposition failed");
        }
        double[] singularValues = svd.getSingularValues();
        return UtilEjml.max(singularValues, 0, singularValues.length);
    }

    public static double inducedPInf(DMatrixRMaj A) {
        double max = 0.0;
        int m = A.numRows;
        int n = A.numCols;
        for (int i = 0; i < m; ++i) {
            double total = 0.0;
            for (int j = 0; j < n; ++j) {
                total += Math.abs(A.get(i, j));
            }
            if (!(total > max)) continue;
            max = total;
        }
        return max;
    }
}

