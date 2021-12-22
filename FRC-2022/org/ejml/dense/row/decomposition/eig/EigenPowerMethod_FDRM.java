/*
 * Decompiled with CFR 0.150.
 */
package org.ejml.dense.row.decomposition.eig;

import org.ejml.UtilEjml;
import org.ejml.data.FMatrixRMaj;
import org.ejml.dense.row.CommonOps_FDRM;
import org.ejml.dense.row.NormOps_FDRM;
import org.ejml.dense.row.SpecializedOps_FDRM;
import org.ejml.dense.row.factory.LinearSolverFactory_FDRM;
import org.ejml.interfaces.linsol.LinearSolverDense;

public class EigenPowerMethod_FDRM {
    private float tol = UtilEjml.TESTP_F32;
    private FMatrixRMaj q0;
    private FMatrixRMaj q1;
    private FMatrixRMaj q2;
    private int maxIterations = 20;
    private FMatrixRMaj B;
    private FMatrixRMaj seed;

    public EigenPowerMethod_FDRM(int size) {
        this.q0 = new FMatrixRMaj(size, 1);
        this.q1 = new FMatrixRMaj(size, 1);
        this.q2 = new FMatrixRMaj(size, 1);
        this.B = new FMatrixRMaj(size, size);
    }

    public void setSeed(FMatrixRMaj seed) {
        this.seed = seed;
    }

    public void setOptions(int maxIterations, float tolerance) {
        this.maxIterations = maxIterations;
        this.tol = tolerance;
    }

    public boolean computeDirect(FMatrixRMaj A) {
        this.initPower(A);
        boolean converged = false;
        for (int i = 0; i < this.maxIterations && !converged; ++i) {
            CommonOps_FDRM.mult(A, this.q0, this.q1);
            float s = NormOps_FDRM.normPInf(this.q1);
            CommonOps_FDRM.divide(this.q1, s, this.q2);
            converged = this.checkConverged(A);
        }
        return converged;
    }

    private void initPower(FMatrixRMaj A) {
        if (A.numRows != A.numCols) {
            throw new IllegalArgumentException("A must be a square matrix.");
        }
        if (this.seed != null) {
            this.q0.setTo(this.seed);
        } else {
            for (int i = 0; i < A.numRows; ++i) {
                this.q0.data[i] = 1.0f;
            }
        }
    }

    private boolean checkConverged(FMatrixRMaj A) {
        float worst = 0.0f;
        float worst2 = 0.0f;
        for (int j = 0; j < A.numRows; ++j) {
            float val = Math.abs(this.q2.data[j] - this.q0.data[j]);
            if (val > worst) {
                worst = val;
            }
            if (!((val = Math.abs(this.q2.data[j] + this.q0.data[j])) > worst2)) continue;
            worst2 = val;
        }
        FMatrixRMaj temp = this.q0;
        this.q0 = this.q2;
        this.q2 = temp;
        if (worst < this.tol) {
            return true;
        }
        return worst2 < this.tol;
    }

    public boolean computeShiftDirect(FMatrixRMaj A, float alpha) {
        SpecializedOps_FDRM.addIdentity(A, this.B, -alpha);
        return this.computeDirect(this.B);
    }

    public boolean computeShiftInvert(FMatrixRMaj A, float alpha) {
        this.initPower(A);
        LinearSolverDense<FMatrixRMaj> solver = LinearSolverFactory_FDRM.linear(A.numCols);
        SpecializedOps_FDRM.addIdentity(A, this.B, -alpha);
        solver.setA(this.B);
        boolean converged = false;
        for (int i = 0; i < this.maxIterations && !converged; ++i) {
            solver.solve(this.q0, this.q1);
            float s = NormOps_FDRM.normPInf(this.q1);
            CommonOps_FDRM.divide(this.q1, s, this.q2);
            converged = this.checkConverged(A);
        }
        return converged;
    }

    public FMatrixRMaj getEigenVector() {
        return this.q0;
    }
}

