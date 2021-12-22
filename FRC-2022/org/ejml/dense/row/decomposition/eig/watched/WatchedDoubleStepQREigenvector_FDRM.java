/*
 * Decompiled with CFR 0.150.
 */
package org.ejml.dense.row.decomposition.eig.watched;

import java.util.Arrays;
import org.ejml.UtilEjml;
import org.ejml.data.Complex_F32;
import org.ejml.data.FMatrixRMaj;
import org.ejml.dense.row.CommonOps_FDRM;
import org.ejml.dense.row.NormOps_FDRM;
import org.ejml.dense.row.SpecializedOps_FDRM;
import org.ejml.dense.row.decomposition.TriangularSolver_FDRM;
import org.ejml.dense.row.decomposition.eig.watched.WatchedDoubleStepQREigen_FDRM;
import org.ejml.dense.row.factory.LinearSolverFactory_FDRM;
import org.ejml.interfaces.linsol.LinearSolverDense;

public class WatchedDoubleStepQREigenvector_FDRM {
    WatchedDoubleStepQREigen_FDRM implicit;
    FMatrixRMaj Q;
    FMatrixRMaj[] eigenvectors;
    FMatrixRMaj eigenvectorTemp;
    LinearSolverDense solver;
    Complex_F32[] origEigenvalues;
    int N;
    int[] splits;
    int numSplits;
    int x1;
    int x2;
    int indexVal;
    boolean onscript;

    public boolean process(WatchedDoubleStepQREigen_FDRM implicit, FMatrixRMaj A, FMatrixRMaj Q_h) {
        this.implicit = implicit;
        if (this.N != A.numRows) {
            this.N = A.numRows;
            this.Q = new FMatrixRMaj(this.N, this.N);
            this.splits = new int[this.N];
            this.origEigenvalues = new Complex_F32[this.N];
            this.eigenvectors = new FMatrixRMaj[this.N];
            this.eigenvectorTemp = new FMatrixRMaj(this.N, 1);
            this.solver = LinearSolverFactory_FDRM.linear(0);
        } else {
            this.eigenvectors = new FMatrixRMaj[this.N];
        }
        System.arraycopy(implicit.eigenvalues, 0, this.origEigenvalues, 0, this.N);
        implicit.setup(A);
        implicit.setQ(this.Q);
        this.numSplits = 0;
        this.onscript = true;
        if (!this.findQandR()) {
            return false;
        }
        return this.extractVectors(Q_h);
    }

    public boolean extractVectors(FMatrixRMaj Q_h) {
        Arrays.fill(this.eigenvectorTemp.data, 0.0f);
        boolean triangular = true;
        for (int i = 0; i < this.N; ++i) {
            Complex_F32 c = this.implicit.eigenvalues[this.N - i - 1];
            if (triangular && !c.isReal()) {
                triangular = false;
            }
            if (!c.isReal() || this.eigenvectors[this.N - i - 1] != null) continue;
            this.solveEigenvectorDuplicateEigenvalue(c.real, i, triangular);
        }
        if (Q_h != null) {
            FMatrixRMaj temp = new FMatrixRMaj(this.N, 1);
            for (int i = 0; i < this.N; ++i) {
                FMatrixRMaj v = this.eigenvectors[i];
                if (v == null) continue;
                CommonOps_FDRM.mult(Q_h, v, temp);
                this.eigenvectors[i] = temp;
                temp = v;
            }
        }
        return true;
    }

    private void solveEigenvectorDuplicateEigenvalue(float real, int first, boolean isTriangle) {
        float scale = Math.abs(real);
        if (scale == 0.0f) {
            scale = 1.0f;
        }
        this.eigenvectorTemp.reshape(this.N, 1, false);
        this.eigenvectorTemp.zero();
        if (first > 0) {
            if (isTriangle) {
                this.solveUsingTriangle(real, first, this.eigenvectorTemp);
            } else {
                this.solveWithLU(real, first, this.eigenvectorTemp);
            }
        }
        this.eigenvectorTemp.reshape(this.N, 1, false);
        for (int i = first; i < this.N; ++i) {
            Complex_F32 c = this.implicit.eigenvalues[this.N - i - 1];
            if (!c.isReal() || !(Math.abs(c.real - real) / scale < 100.0f * UtilEjml.F_EPS)) continue;
            this.eigenvectorTemp.data[i] = 1.0f;
            FMatrixRMaj v = new FMatrixRMaj(this.N, 1);
            CommonOps_FDRM.multTransA(this.Q, this.eigenvectorTemp, v);
            this.eigenvectors[this.N - i - 1] = v;
            NormOps_FDRM.normalizeF(v);
            this.eigenvectorTemp.data[i] = 0.0f;
        }
    }

    private void solveUsingTriangle(float real, int index, FMatrixRMaj r) {
        int i;
        for (i = 0; i < index; ++i) {
            this.implicit.A.add(i, i, -real);
        }
        SpecializedOps_FDRM.subvector(this.implicit.A, 0, index, index, false, 0, r);
        CommonOps_FDRM.changeSign(r);
        TriangularSolver_FDRM.solveU(this.implicit.A.data, r.data, this.implicit.A.numRows, 0, index);
        for (i = 0; i < index; ++i) {
            this.implicit.A.add(i, i, real);
        }
    }

    private void solveWithLU(float real, int index, FMatrixRMaj r) {
        FMatrixRMaj A = new FMatrixRMaj(index, index);
        CommonOps_FDRM.extract(this.implicit.A, 0, index, 0, index, A, 0, 0);
        for (int i = 0; i < index; ++i) {
            A.add(i, i, -real);
        }
        r.reshape(index, 1, false);
        SpecializedOps_FDRM.subvector(this.implicit.A, 0, index, index, false, 0, r);
        CommonOps_FDRM.changeSign(r);
        if (!this.solver.setA(A)) {
            throw new RuntimeException("Solve failed");
        }
        this.solver.solve(r, r);
    }

    public boolean findQandR() {
        CommonOps_FDRM.setIdentity(this.Q);
        this.x1 = 0;
        this.x2 = this.N - 1;
        this.indexVal = 0;
        while (this.indexVal < this.N) {
            if (this.findNextEigenvalue()) continue;
            return false;
        }
        return true;
    }

    private boolean findNextEigenvalue() {
        boolean foundEigen = false;
        while (!foundEigen && this.implicit.steps < this.implicit.maxIterations) {
            this.implicit.incrementSteps();
            if (this.x2 < this.x1) {
                this.moveToNextSplit();
                continue;
            }
            if (this.x2 - this.x1 == 0) {
                this.implicit.addEigenAt(this.x1);
                --this.x2;
                ++this.indexVal;
                foundEigen = true;
                continue;
            }
            if (this.x2 - this.x1 == 1 && !this.implicit.isReal2x2(this.x1, this.x2)) {
                this.implicit.addComputedEigen2x2(this.x1, this.x2);
                this.x2 -= 2;
                this.indexVal += 2;
                foundEigen = true;
                continue;
            }
            if (this.implicit.steps - this.implicit.lastExceptional > this.implicit.exceptionalThreshold) {
                this.implicit.exceptionalShift(this.x1, this.x2);
                this.implicit.lastExceptional = this.implicit.steps;
                continue;
            }
            if (this.implicit.isZero(this.x2, this.x2 - 1)) {
                this.implicit.addEigenAt(this.x2);
                foundEigen = true;
                --this.x2;
                ++this.indexVal;
                continue;
            }
            this.checkSplitPerformImplicit();
        }
        return foundEigen;
    }

    private void checkSplitPerformImplicit() {
        for (int i = this.x2; i > this.x1; --i) {
            if (!this.implicit.isZero(i, i - 1)) continue;
            this.x1 = i;
            this.splits[this.numSplits++] = i - 1;
            return;
        }
        if (this.onscript) {
            if (this.implicit.steps > this.implicit.exceptionalThreshold / 2) {
                this.onscript = false;
            } else {
                Complex_F32 a = this.origEigenvalues[this.indexVal];
                if (a.isReal()) {
                    this.implicit.performImplicitSingleStep(this.x1, this.x2, a.getReal());
                } else if (this.x2 - this.x1 >= 1 && this.x1 + 2 < this.N) {
                    this.implicit.performImplicitDoubleStep(this.x1, this.x2, a.real, a.imaginary);
                } else {
                    this.onscript = false;
                }
            }
        } else if (this.x2 - this.x1 >= 1 && this.x1 + 2 < this.N) {
            this.implicit.implicitDoubleStep(this.x1, this.x2);
        } else {
            this.implicit.performImplicitSingleStep(this.x1, this.x2, this.implicit.A.get(this.x2, this.x2));
        }
    }

    private void moveToNextSplit() {
        if (this.numSplits <= 0) {
            throw new RuntimeException("bad");
        }
        this.x2 = this.splits[--this.numSplits];
        this.x1 = this.numSplits > 0 ? this.splits[this.numSplits - 1] + 1 : 0;
    }

    public FMatrixRMaj getQ() {
        return this.Q;
    }

    public WatchedDoubleStepQREigen_FDRM getImplicit() {
        return this.implicit;
    }

    public FMatrixRMaj[] getEigenvectors() {
        return this.eigenvectors;
    }

    public Complex_F32[] getEigenvalues() {
        return this.implicit.eigenvalues;
    }
}

