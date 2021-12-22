/*
 * Decompiled with CFR 0.150.
 */
package org.ejml.dense.row.decomposition.eig;

import org.ejml.UtilEjml;
import org.ejml.data.DMatrixRMaj;
import org.ejml.dense.row.CommonOps_DDRM;
import org.ejml.dense.row.NormOps_DDRM;
import org.ejml.dense.row.SpecializedOps_DDRM;
import org.ejml.dense.row.factory.LinearSolverFactory_DDRM;
import org.ejml.interfaces.linsol.LinearSolverDense;

public class EigenPowerMethod_DDRM {
    private double tol = UtilEjml.TESTP_F64;
    private DMatrixRMaj q0;
    private DMatrixRMaj q1;
    private DMatrixRMaj q2;
    private int maxIterations = 20;
    private DMatrixRMaj B;
    private DMatrixRMaj seed;

    public EigenPowerMethod_DDRM(int size) {
        this.q0 = new DMatrixRMaj(size, 1);
        this.q1 = new DMatrixRMaj(size, 1);
        this.q2 = new DMatrixRMaj(size, 1);
        this.B = new DMatrixRMaj(size, size);
    }

    public void setSeed(DMatrixRMaj seed) {
        this.seed = seed;
    }

    public void setOptions(int maxIterations, double tolerance) {
        this.maxIterations = maxIterations;
        this.tol = tolerance;
    }

    public boolean computeDirect(DMatrixRMaj A) {
        this.initPower(A);
        boolean converged = false;
        for (int i = 0; i < this.maxIterations && !converged; ++i) {
            CommonOps_DDRM.mult(A, this.q0, this.q1);
            double s = NormOps_DDRM.normPInf(this.q1);
            CommonOps_DDRM.divide(this.q1, s, this.q2);
            converged = this.checkConverged(A);
        }
        return converged;
    }

    private void initPower(DMatrixRMaj A) {
        if (A.numRows != A.numCols) {
            throw new IllegalArgumentException("A must be a square matrix.");
        }
        if (this.seed != null) {
            this.q0.setTo(this.seed);
        } else {
            for (int i = 0; i < A.numRows; ++i) {
                this.q0.data[i] = 1.0;
            }
        }
    }

    private boolean checkConverged(DMatrixRMaj A) {
        double worst = 0.0;
        double worst2 = 0.0;
        for (int j = 0; j < A.numRows; ++j) {
            double val = Math.abs(this.q2.data[j] - this.q0.data[j]);
            if (val > worst) {
                worst = val;
            }
            if (!((val = Math.abs(this.q2.data[j] + this.q0.data[j])) > worst2)) continue;
            worst2 = val;
        }
        DMatrixRMaj temp = this.q0;
        this.q0 = this.q2;
        this.q2 = temp;
        if (worst < this.tol) {
            return true;
        }
        return worst2 < this.tol;
    }

    public boolean computeShiftDirect(DMatrixRMaj A, double alpha) {
        SpecializedOps_DDRM.addIdentity(A, this.B, -alpha);
        return this.computeDirect(this.B);
    }

    public boolean computeShiftInvert(DMatrixRMaj A, double alpha) {
        this.initPower(A);
        LinearSolverDense<DMatrixRMaj> solver = LinearSolverFactory_DDRM.linear(A.numCols);
        SpecializedOps_DDRM.addIdentity(A, this.B, -alpha);
        solver.setA(this.B);
        boolean converged = false;
        for (int i = 0; i < this.maxIterations && !converged; ++i) {
            solver.solve(this.q0, this.q1);
            double s = NormOps_DDRM.normPInf(this.q1);
            CommonOps_DDRM.divide(this.q1, s, this.q2);
            converged = this.checkConverged(A);
        }
        return converged;
    }

    public DMatrixRMaj getEigenVector() {
        return this.q0;
    }
}

