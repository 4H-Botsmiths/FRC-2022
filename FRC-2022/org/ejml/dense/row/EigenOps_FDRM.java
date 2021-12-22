/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  org.jetbrains.annotations.Nullable
 */
package org.ejml.dense.row;

import org.ejml.UtilEjml;
import org.ejml.data.Complex_F32;
import org.ejml.data.FEigenpair;
import org.ejml.data.FMatrixRMaj;
import org.ejml.dense.row.CommonOps_FDRM;
import org.ejml.dense.row.MatrixFeatures_FDRM;
import org.ejml.dense.row.NormOps_FDRM;
import org.ejml.dense.row.SpecializedOps_FDRM;
import org.ejml.dense.row.decomposition.eig.EigenPowerMethod_FDRM;
import org.ejml.dense.row.factory.LinearSolverFactory_FDRM;
import org.ejml.dense.row.mult.VectorVectorMult_FDRM;
import org.ejml.interfaces.decomposition.EigenDecomposition_F32;
import org.ejml.interfaces.linsol.LinearSolverDense;
import org.jetbrains.annotations.Nullable;

public class EigenOps_FDRM {
    public static float computeEigenValue(FMatrixRMaj A, FMatrixRMaj eigenVector) {
        float bottom = VectorVectorMult_FDRM.innerProd(eigenVector, eigenVector);
        float top = VectorVectorMult_FDRM.innerProdA(eigenVector, A, eigenVector);
        return top / bottom;
    }

    @Nullable
    public static FEigenpair computeEigenVector(FMatrixRMaj A, float eigenvalue) {
        if (A.numRows != A.numCols) {
            throw new IllegalArgumentException("Must be a square matrix.");
        }
        FMatrixRMaj M = new FMatrixRMaj(A.numRows, A.numCols);
        FMatrixRMaj x = new FMatrixRMaj(A.numRows, 1);
        FMatrixRMaj b = new FMatrixRMaj(A.numRows, 1);
        CommonOps_FDRM.fill(b, 1.0f);
        float origEigenvalue = eigenvalue;
        SpecializedOps_FDRM.addIdentity(A, M, -eigenvalue);
        float threshold = NormOps_FDRM.normPInf(A) * UtilEjml.F_EPS;
        float prevError = Float.MAX_VALUE;
        boolean hasWorked = false;
        LinearSolverDense<FMatrixRMaj> solver = LinearSolverFactory_FDRM.linear(M.numRows);
        float perp = 1.0E-4f;
        for (int i = 0; i < 200; ++i) {
            boolean failed = false;
            if (!solver.setA(M)) {
                failed = true;
            } else {
                solver.solve(b, x);
            }
            if (MatrixFeatures_FDRM.hasUncountable(x)) {
                failed = true;
            }
            if (failed) {
                if (!hasWorked) {
                    float val = i % 2 == 0 ? 1.0f - perp : 1.0f + perp;
                    eigenvalue = origEigenvalue * (float)Math.pow(val, i / 2 + 1);
                    SpecializedOps_FDRM.addIdentity(A, M, -eigenvalue);
                    continue;
                }
                return new FEigenpair(eigenvalue, b);
            }
            hasWorked = true;
            b.setTo(x);
            NormOps_FDRM.normalizeF(b);
            CommonOps_FDRM.mult(M, b, x);
            float error = NormOps_FDRM.normPInf(x);
            if (error - prevError > UtilEjml.F_EPS * 10.0f) {
                prevError = Float.MAX_VALUE;
                hasWorked = false;
                float val = i % 2 == 0 ? 1.0f - perp : 1.0f + perp;
                eigenvalue = origEigenvalue * (float)Math.pow(val, 1.0);
            } else {
                if (error <= threshold || Math.abs(prevError - error) <= UtilEjml.F_EPS) {
                    return new FEigenpair(eigenvalue, b);
                }
                prevError = error;
                eigenvalue = VectorVectorMult_FDRM.innerProdA(b, A, b);
            }
            SpecializedOps_FDRM.addIdentity(A, M, -eigenvalue);
        }
        return null;
    }

    @Nullable
    public static FEigenpair dominantEigenpair(FMatrixRMaj A) {
        EigenPowerMethod_FDRM power = new EigenPowerMethod_FDRM(A.numRows);
        if (!power.computeShiftInvert(A, 0.1f)) {
            return null;
        }
        throw new RuntimeException("Not yet implemented");
    }

    public static float[] boundLargestEigenValue(FMatrixRMaj A, @Nullable float[] bound) {
        if (A.numRows != A.numCols) {
            throw new IllegalArgumentException("A must be a square matrix.");
        }
        float min = Float.MAX_VALUE;
        float max = 0.0f;
        int n = A.numRows;
        for (int i = 0; i < n; ++i) {
            float total = 0.0f;
            for (int j = 0; j < n; ++j) {
                float v = A.get(i, j);
                if (v < 0.0f) {
                    throw new IllegalArgumentException("Matrix must be positive");
                }
                total += v;
            }
            if (total < min) {
                min = total;
            }
            if (!(total > max)) continue;
            max = total;
        }
        if (bound == null) {
            bound = new float[]{min, max};
        }
        return bound;
    }

    public static FMatrixRMaj createMatrixD(EigenDecomposition_F32<?> eig) {
        int N = eig.getNumberOfEigenvalues();
        FMatrixRMaj D = new FMatrixRMaj(N, N);
        for (int i = 0; i < N; ++i) {
            Complex_F32 c = eig.getEigenvalue(i);
            if (!c.isReal()) continue;
            D.set(i, i, c.real);
        }
        return D;
    }

    public static FMatrixRMaj createMatrixV(EigenDecomposition_F32<FMatrixRMaj> eig) {
        int N = eig.getNumberOfEigenvalues();
        FMatrixRMaj V = new FMatrixRMaj(N, N);
        for (int i = 0; i < N; ++i) {
            FMatrixRMaj v;
            Complex_F32 c = eig.getEigenvalue(i);
            if (!c.isReal() || (v = (FMatrixRMaj)eig.getEigenVector(i)) == null) continue;
            for (int j = 0; j < N; ++j) {
                V.set(j, i, v.get(j, 0));
            }
        }
        return V;
    }
}

