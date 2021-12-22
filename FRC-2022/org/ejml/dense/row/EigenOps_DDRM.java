/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  org.jetbrains.annotations.Nullable
 */
package org.ejml.dense.row;

import org.ejml.UtilEjml;
import org.ejml.data.Complex_F64;
import org.ejml.data.DEigenpair;
import org.ejml.data.DMatrixRMaj;
import org.ejml.dense.row.CommonOps_DDRM;
import org.ejml.dense.row.MatrixFeatures_DDRM;
import org.ejml.dense.row.NormOps_DDRM;
import org.ejml.dense.row.SpecializedOps_DDRM;
import org.ejml.dense.row.decomposition.eig.EigenPowerMethod_DDRM;
import org.ejml.dense.row.factory.LinearSolverFactory_DDRM;
import org.ejml.dense.row.mult.VectorVectorMult_DDRM;
import org.ejml.interfaces.decomposition.EigenDecomposition_F64;
import org.ejml.interfaces.linsol.LinearSolverDense;
import org.jetbrains.annotations.Nullable;

public class EigenOps_DDRM {
    public static double computeEigenValue(DMatrixRMaj A, DMatrixRMaj eigenVector) {
        double bottom = VectorVectorMult_DDRM.innerProd(eigenVector, eigenVector);
        double top = VectorVectorMult_DDRM.innerProdA(eigenVector, A, eigenVector);
        return top / bottom;
    }

    @Nullable
    public static DEigenpair computeEigenVector(DMatrixRMaj A, double eigenvalue) {
        if (A.numRows != A.numCols) {
            throw new IllegalArgumentException("Must be a square matrix.");
        }
        DMatrixRMaj M = new DMatrixRMaj(A.numRows, A.numCols);
        DMatrixRMaj x = new DMatrixRMaj(A.numRows, 1);
        DMatrixRMaj b = new DMatrixRMaj(A.numRows, 1);
        CommonOps_DDRM.fill(b, 1.0);
        double origEigenvalue = eigenvalue;
        SpecializedOps_DDRM.addIdentity(A, M, -eigenvalue);
        double threshold = NormOps_DDRM.normPInf(A) * UtilEjml.EPS;
        double prevError = Double.MAX_VALUE;
        boolean hasWorked = false;
        LinearSolverDense<DMatrixRMaj> solver = LinearSolverFactory_DDRM.linear(M.numRows);
        double perp = 1.0E-4;
        for (int i = 0; i < 200; ++i) {
            boolean failed = false;
            if (!solver.setA(M)) {
                failed = true;
            } else {
                solver.solve(b, x);
            }
            if (MatrixFeatures_DDRM.hasUncountable(x)) {
                failed = true;
            }
            if (failed) {
                if (!hasWorked) {
                    double val = i % 2 == 0 ? 1.0 - perp : 1.0 + perp;
                    eigenvalue = origEigenvalue * Math.pow(val, i / 2 + 1);
                    SpecializedOps_DDRM.addIdentity(A, M, -eigenvalue);
                    continue;
                }
                return new DEigenpair(eigenvalue, b);
            }
            hasWorked = true;
            b.setTo(x);
            NormOps_DDRM.normalizeF(b);
            CommonOps_DDRM.mult(M, b, x);
            double error = NormOps_DDRM.normPInf(x);
            if (error - prevError > UtilEjml.EPS * 10.0) {
                prevError = Double.MAX_VALUE;
                hasWorked = false;
                double val = i % 2 == 0 ? 1.0 - perp : 1.0 + perp;
                eigenvalue = origEigenvalue * Math.pow(val, 1.0);
            } else {
                if (error <= threshold || Math.abs(prevError - error) <= UtilEjml.EPS) {
                    return new DEigenpair(eigenvalue, b);
                }
                prevError = error;
                eigenvalue = VectorVectorMult_DDRM.innerProdA(b, A, b);
            }
            SpecializedOps_DDRM.addIdentity(A, M, -eigenvalue);
        }
        return null;
    }

    @Nullable
    public static DEigenpair dominantEigenpair(DMatrixRMaj A) {
        EigenPowerMethod_DDRM power = new EigenPowerMethod_DDRM(A.numRows);
        if (!power.computeShiftInvert(A, 0.1)) {
            return null;
        }
        throw new RuntimeException("Not yet implemented");
    }

    public static double[] boundLargestEigenValue(DMatrixRMaj A, @Nullable double[] bound) {
        if (A.numRows != A.numCols) {
            throw new IllegalArgumentException("A must be a square matrix.");
        }
        double min = Double.MAX_VALUE;
        double max = 0.0;
        int n = A.numRows;
        for (int i = 0; i < n; ++i) {
            double total = 0.0;
            for (int j = 0; j < n; ++j) {
                double v = A.get(i, j);
                if (v < 0.0) {
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
            bound = new double[]{min, max};
        }
        return bound;
    }

    public static DMatrixRMaj createMatrixD(EigenDecomposition_F64<?> eig) {
        int N = eig.getNumberOfEigenvalues();
        DMatrixRMaj D = new DMatrixRMaj(N, N);
        for (int i = 0; i < N; ++i) {
            Complex_F64 c = eig.getEigenvalue(i);
            if (!c.isReal()) continue;
            D.set(i, i, c.real);
        }
        return D;
    }

    public static DMatrixRMaj createMatrixV(EigenDecomposition_F64<DMatrixRMaj> eig) {
        int N = eig.getNumberOfEigenvalues();
        DMatrixRMaj V = new DMatrixRMaj(N, N);
        for (int i = 0; i < N; ++i) {
            DMatrixRMaj v;
            Complex_F64 c = eig.getEigenvalue(i);
            if (!c.isReal() || (v = (DMatrixRMaj)eig.getEigenVector(i)) == null) continue;
            for (int j = 0; j < N; ++j) {
                V.set(j, i, v.get(j, 0));
            }
        }
        return V;
    }
}

