/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  org.jetbrains.annotations.Nullable
 */
package org.ejml.dense.row.decomposition.eig.watched;

import java.util.Random;
import org.ejml.UtilEjml;
import org.ejml.data.Complex_F64;
import org.ejml.data.DMatrixRMaj;
import org.ejml.dense.row.MatrixFeatures_DDRM;
import org.ejml.dense.row.decomposition.eig.EigenvalueSmall_F64;
import org.ejml.dense.row.decomposition.qr.QrHelperFunctions_DDRM;
import org.jetbrains.annotations.Nullable;

public class WatchedDoubleStepQREigen_DDRM {
    private Random rand = new Random(9026L);
    private int N;
    DMatrixRMaj A;
    protected DMatrixRMaj u;
    protected double gamma;
    protected DMatrixRMaj _temp;
    int[] numStepsFind;
    int steps;
    Complex_F64[] eigenvalues;
    int numEigen;
    private EigenvalueSmall_F64 valueSmall = new EigenvalueSmall_F64();
    private final double[] temp = new double[9];
    private boolean printHumps = false;
    boolean checkHessenberg = false;
    private boolean checkOrthogonal = false;
    private boolean checkUncountable = false;
    private boolean useStandardEq = false;
    private boolean useCareful2x2 = true;
    private boolean normalize = true;
    int lastExceptional;
    int numExceptional;
    int exceptionalThreshold = 20;
    int maxIterations = this.exceptionalThreshold * 20;
    public boolean createR = true;
    @Nullable
    public DMatrixRMaj Q;

    public void incrementSteps() {
        ++this.steps;
    }

    public void setQ(@Nullable DMatrixRMaj Q) {
        this.Q = Q;
    }

    private void addEigenvalue(double v) {
        this.numStepsFind[this.numEigen] = this.steps;
        this.eigenvalues[this.numEigen].setTo(v, 0.0);
        ++this.numEigen;
        this.steps = 0;
        this.lastExceptional = 0;
    }

    private void addEigenvalue(double v, double i) {
        this.numStepsFind[this.numEigen] = this.steps;
        this.eigenvalues[this.numEigen].setTo(v, i);
        ++this.numEigen;
        this.steps = 0;
        this.lastExceptional = 0;
    }

    public void setChecks(boolean hessenberg, boolean orthogonal, boolean uncountable) {
        this.checkHessenberg = hessenberg;
        this.checkOrthogonal = orthogonal;
        this.checkUncountable = uncountable;
    }

    public boolean isZero(int x1, int x2) {
        double right;
        double above;
        double target = Math.abs(this.A.get(x1, x2));
        return target <= 0.5 * UtilEjml.EPS * ((above = Math.abs(this.A.get(x1 - 1, x2))) + (right = Math.abs(this.A.get(x1, x2 + 1))));
    }

    public void setup(DMatrixRMaj A) {
        int i;
        if (A.numRows != A.numCols) {
            throw new RuntimeException("Must be square");
        }
        if (this.N != A.numRows) {
            this.N = A.numRows;
            this.A = A.copy();
            this.u = new DMatrixRMaj(A.numRows, 1);
            this._temp = new DMatrixRMaj(A.numRows, 1);
            this.numStepsFind = new int[A.numRows];
        } else {
            this.A.setTo(A);
            UtilEjml.memset(this.numStepsFind, 0, this.numStepsFind.length);
        }
        for (i = 2; i < this.N; ++i) {
            for (int j = 0; j < i - 1; ++j) {
                this.A.set(i, j, 0.0);
            }
        }
        this.eigenvalues = new Complex_F64[A.numRows];
        for (i = 0; i < this.eigenvalues.length; ++i) {
            this.eigenvalues[i] = new Complex_F64();
        }
        this.numEigen = 0;
        this.lastExceptional = 0;
        this.numExceptional = 0;
        this.steps = 0;
    }

    public void exceptionalShift(int x1, int x2) {
        double val;
        if (this.printHumps) {
            System.out.println("Performing exceptional implicit double step");
        }
        if ((val = Math.abs(this.A.get(x2, x2))) == 0.0) {
            val = 1.0;
        }
        ++this.numExceptional;
        double p = 1.0 - Math.pow(0.1, this.numExceptional);
        val *= p + 2.0 * (1.0 - p) * (this.rand.nextDouble() - 0.5);
        if (this.rand.nextBoolean()) {
            val = -val;
        }
        this.performImplicitSingleStep(x1, x2, val);
        this.lastExceptional = this.steps;
    }

    public void implicitDoubleStep(int x1, int x2) {
        double b31;
        double b21;
        double b11;
        if (this.printHumps) {
            System.out.println("Performing implicit double step");
        }
        double z11 = this.A.get(x2 - 1, x2 - 1);
        double z12 = this.A.get(x2 - 1, x2);
        double z21 = this.A.get(x2, x2 - 1);
        double z22 = this.A.get(x2, x2);
        double a11 = this.A.get(x1, x1);
        double a21 = this.A.get(x1 + 1, x1);
        double a12 = this.A.get(x1, x1 + 1);
        double a22 = this.A.get(x1 + 1, x1 + 1);
        double a32 = this.A.get(x1 + 2, x1 + 1);
        if (this.normalize) {
            this.temp[0] = a11;
            this.temp[1] = a21;
            this.temp[2] = a12;
            this.temp[3] = a22;
            this.temp[4] = a32;
            this.temp[5] = z11;
            this.temp[6] = z22;
            this.temp[7] = z12;
            this.temp[8] = z21;
            double max = Math.abs(this.temp[0]);
            for (int j = 1; j < this.temp.length; ++j) {
                if (!(Math.abs(this.temp[j]) > max)) continue;
                max = Math.abs(this.temp[j]);
            }
            a11 /= max;
            a21 /= max;
            a12 /= max;
            a22 /= max;
            a32 /= max;
            z11 /= max;
            z22 /= max;
            z12 /= max;
            z21 /= max;
        }
        if (this.useStandardEq) {
            b11 = ((a11 - z11) * (a11 - z22) - z21 * z12) / a21 + a12;
            b21 = a11 + a22 - z11 - z22;
            b31 = a32;
        } else {
            b11 = (a11 - z11) * (a11 - z22) - z21 * z12 + a12 * a21;
            b21 = (a11 + a22 - z11 - z22) * a21;
            b31 = a32 * a21;
        }
        this.performImplicitDoubleStep(x1, x2, b11, b21, b31);
    }

    public void performImplicitDoubleStep(int x1, int x2, double real, double img) {
        double b31;
        double b21;
        double b11;
        double a11 = this.A.get(x1, x1);
        double a21 = this.A.get(x1 + 1, x1);
        double a12 = this.A.get(x1, x1 + 1);
        double a22 = this.A.get(x1 + 1, x1 + 1);
        double a32 = this.A.get(x1 + 2, x1 + 1);
        double p_plus_t = 2.0 * real;
        double p_times_t = real * real + img * img;
        if (this.useStandardEq) {
            b11 = (a11 * a11 - p_plus_t * a11 + p_times_t) / a21 + a12;
            b21 = a11 + a22 - p_plus_t;
            b31 = a32;
        } else {
            b11 = a11 * a11 - p_plus_t * a11 + p_times_t + a12 * a21;
            b21 = (a11 + a22 - p_plus_t) * a21;
            b31 = a32 * a21;
        }
        this.performImplicitDoubleStep(x1, x2, b11, b21, b31);
    }

    private void performImplicitDoubleStep(int x1, int x2, double b11, double b21, double b31) {
        if (!this.bulgeDoubleStepQn(x1, b11, b21, b31, 0.0, false)) {
            return;
        }
        if (this.Q != null) {
            this.rank1UpdateMultR(this.Q, this.gamma, 0, x1, x1 + 3);
            if (this.checkOrthogonal && !MatrixFeatures_DDRM.isOrthogonal(this.Q, UtilEjml.TEST_F64)) {
                this.u.print();
                this.Q.print();
                throw new RuntimeException("Bad");
            }
        }
        if (this.printHumps) {
            System.out.println("Applied first Q matrix, it should be humped now. A = ");
            this.A.print("%12.3e");
            System.out.println("Pushing the hump off the matrix.");
        }
        for (int i = x1; i < x2 - 2; ++i) {
            if (this.bulgeDoubleStepQn(i) && this.Q != null) {
                this.rank1UpdateMultR(this.Q, this.gamma, 0, i + 1, i + 4);
                if (this.checkOrthogonal && !MatrixFeatures_DDRM.isOrthogonal(this.Q, UtilEjml.TEST_F64)) {
                    throw new RuntimeException("Bad");
                }
            }
            if (!this.printHumps) continue;
            System.out.println("i = " + i + " A = ");
            this.A.print("%12.3e");
        }
        if (this.printHumps) {
            System.out.println("removing last bump");
        }
        if (x2 - 2 >= 0 && this.bulgeSingleStepQn(x2 - 2) && this.Q != null) {
            this.rank1UpdateMultR(this.Q, this.gamma, 0, x2 - 1, x2 + 1);
            if (this.checkOrthogonal && !MatrixFeatures_DDRM.isOrthogonal(this.Q, UtilEjml.TEST_F64)) {
                throw new RuntimeException("Bad");
            }
        }
        if (this.printHumps) {
            System.out.println(" A = ");
            this.A.print("%12.3e");
        }
        if (this.checkHessenberg && !MatrixFeatures_DDRM.isUpperTriangle(this.A, 1, UtilEjml.TEST_F64)) {
            this.A.print("%12.3e");
            throw new RuntimeException("Bad matrix");
        }
    }

    public void performImplicitSingleStep(int x1, int x2, double eigenvalue) {
        if (!this.createBulgeSingleStep(x1, eigenvalue)) {
            return;
        }
        if (this.Q != null) {
            this.rank1UpdateMultR(this.Q, this.gamma, 0, x1, x1 + 2);
            if (this.checkOrthogonal && !MatrixFeatures_DDRM.isOrthogonal(this.Q, UtilEjml.TEST_F64)) {
                throw new RuntimeException("Bad");
            }
        }
        if (this.printHumps) {
            System.out.println("Applied first Q matrix, it should be humped now. A = ");
            this.A.print("%12.3e");
            System.out.println("Pushing the hump off the matrix.");
        }
        for (int i = x1; i < x2 - 1; ++i) {
            if (this.bulgeSingleStepQn(i) && this.Q != null) {
                this.rank1UpdateMultR(this.Q, this.gamma, 0, i + 1, i + 3);
                if (this.checkOrthogonal && !MatrixFeatures_DDRM.isOrthogonal(this.Q, UtilEjml.TESTP_F64)) {
                    throw new RuntimeException("Bad");
                }
            }
            if (!this.printHumps) continue;
            System.out.println("i = " + i + " A = ");
            this.A.print("%12.3e");
        }
        if (this.checkHessenberg && !MatrixFeatures_DDRM.isUpperTriangle(this.A, 1, UtilEjml.TESTP_F64)) {
            this.A.print("%12.3e");
            throw new RuntimeException("Bad matrix");
        }
    }

    public boolean createBulgeSingleStep(int x1, double eigenvalue) {
        double b11 = this.A.get(x1, x1) - eigenvalue;
        double b21 = this.A.get(x1 + 1, x1);
        double threshold = Math.abs(this.A.get(x1, x1)) * UtilEjml.EPS;
        return this.bulgeSingleStepQn(x1, b11, b21, threshold, false);
    }

    public boolean bulgeDoubleStepQn(int i) {
        double a11 = this.A.get(i + 1, i);
        double a21 = this.A.get(i + 2, i);
        double a31 = this.A.get(i + 3, i);
        double threshold = Math.abs(this.A.get(i, i)) * UtilEjml.EPS;
        return this.bulgeDoubleStepQn(i + 1, a11, a21, a31, threshold, true);
    }

    public boolean bulgeDoubleStepQn(int i, double a11, double a21, double a31, double threshold, boolean set) {
        double max;
        if (this.normalize) {
            double absA11 = Math.abs(a11);
            double absA21 = Math.abs(a21);
            double absA31 = Math.abs(a31);
            double d = max = absA11 > absA21 ? absA11 : absA21;
            if (absA31 > max) {
                max = absA31;
            }
            if (max <= threshold) {
                if (set) {
                    this.A.set(i, i - 1, 0.0);
                    this.A.set(i + 1, i - 1, 0.0);
                    this.A.set(i + 2, i - 1, 0.0);
                }
                return false;
            }
            a11 /= max;
            a21 /= max;
            a31 /= max;
        } else {
            max = 1.0;
        }
        double tau = Math.sqrt(a11 * a11 + a21 * a21 + a31 * a31);
        if (a11 < 0.0) {
            tau = -tau;
        }
        double div = a11 + tau;
        this.u.set(i, 0, 1.0);
        this.u.set(i + 1, 0, a21 / div);
        this.u.set(i + 2, 0, a31 / div);
        this.gamma = div / tau;
        this.rank1UpdateMultR(this.A, this.gamma, 0, i, i + 3);
        if (set) {
            this.A.set(i, i - 1, -max * tau);
            this.A.set(i + 1, i - 1, 0.0);
            this.A.set(i + 2, i - 1, 0.0);
        }
        if (this.printHumps) {
            System.out.println("  After Q.   A =");
            this.A.print();
        }
        this.rank1UpdateMultL(this.A, this.gamma, 0, i, i + 3);
        if (this.checkUncountable && MatrixFeatures_DDRM.hasUncountable(this.A)) {
            throw new RuntimeException("bad matrix");
        }
        return true;
    }

    public boolean bulgeSingleStepQn(int i) {
        double a11 = this.A.get(i + 1, i);
        double a21 = this.A.get(i + 2, i);
        double threshold = Math.abs(this.A.get(i, i)) * UtilEjml.EPS;
        return this.bulgeSingleStepQn(i + 1, a11, a21, threshold, true);
    }

    public boolean bulgeSingleStepQn(int i, double a11, double a21, double threshold, boolean set) {
        double max;
        if (this.normalize) {
            max = Math.abs(a11);
            if (max < Math.abs(a21)) {
                max = Math.abs(a21);
            }
            if (max <= threshold) {
                if (set) {
                    this.A.set(i, i - 1, 0.0);
                    this.A.set(i + 1, i - 1, 0.0);
                }
                return false;
            }
            a11 /= max;
            a21 /= max;
        } else {
            max = 1.0;
        }
        double tau = Math.sqrt(a11 * a11 + a21 * a21);
        if (a11 < 0.0) {
            tau = -tau;
        }
        double div = a11 + tau;
        this.u.set(i, 0, 1.0);
        this.u.set(i + 1, 0, a21 / div);
        this.gamma = div / tau;
        this.rank1UpdateMultR(this.A, this.gamma, 0, i, i + 2);
        if (set) {
            this.A.set(i, i - 1, -max * tau);
            this.A.set(i + 1, i - 1, 0.0);
        }
        this.rank1UpdateMultL(this.A, this.gamma, 0, i, i + 2);
        if (this.checkUncountable && MatrixFeatures_DDRM.hasUncountable(this.A)) {
            throw new RuntimeException("bad matrix");
        }
        return true;
    }

    public void eigen2by2_scale(double a11, double a12, double a21, double a22) {
        double max;
        double abs11 = Math.abs(a11);
        double abs22 = Math.abs(a22);
        double abs12 = Math.abs(a12);
        double abs21 = Math.abs(a21);
        double d = max = abs11 > abs22 ? abs11 : abs22;
        if (max < abs12) {
            max = abs12;
        }
        if (max < abs21) {
            max = abs21;
        }
        if (max == 0.0) {
            this.valueSmall.value0.real = 0.0;
            this.valueSmall.value0.imaginary = 0.0;
            this.valueSmall.value1.real = 0.0;
            this.valueSmall.value1.imaginary = 0.0;
        } else {
            a12 /= max;
            a21 /= max;
            a11 /= max;
            a22 /= max;
            if (this.useCareful2x2) {
                this.valueSmall.value2x2(a11, a12, a21, a22);
            } else {
                this.valueSmall.value2x2_fast(a11, a12, a21, a22);
            }
            this.valueSmall.value0.real *= max;
            this.valueSmall.value0.imaginary *= max;
            this.valueSmall.value1.real *= max;
            this.valueSmall.value1.imaginary *= max;
        }
    }

    public int getNumberOfEigenvalues() {
        return this.numEigen;
    }

    public Complex_F64[] getEigenvalues() {
        return this.eigenvalues;
    }

    public void addComputedEigen2x2(int x1, int x2) {
        this.eigen2by2_scale(this.A.get(x1, x1), this.A.get(x1, x2), this.A.get(x2, x1), this.A.get(x2, x2));
        if (this.checkUncountable && (Double.isNaN(this.valueSmall.value0.real) || Double.isNaN(this.valueSmall.value1.real))) {
            throw new RuntimeException("Uncountable");
        }
        this.addEigenvalue(this.valueSmall.value0.real, this.valueSmall.value0.imaginary);
        this.addEigenvalue(this.valueSmall.value1.real, this.valueSmall.value1.imaginary);
    }

    public boolean isReal2x2(int x1, int x2) {
        this.eigen2by2_scale(this.A.get(x1, x1), this.A.get(x1, x2), this.A.get(x2, x1), this.A.get(x2, x2));
        return this.valueSmall.value0.isReal();
    }

    public void addEigenAt(int x1) {
        this.addEigenvalue(this.A.get(x1, x1));
    }

    protected void rank1UpdateMultL(DMatrixRMaj A, double gamma, int colA0, int w0, int w1) {
        QrHelperFunctions_DDRM.rank1UpdateMultL(A, this.u.data, gamma, colA0, w0, w1);
    }

    protected void rank1UpdateMultR(DMatrixRMaj A, double gamma, int colA0, int w0, int w1) {
        QrHelperFunctions_DDRM.rank1UpdateMultR(A, this.u.data, gamma, colA0, w0, w1, this._temp.data);
    }

    public void printSteps() {
        for (int i = 0; i < this.N; ++i) {
            System.out.println("Step[" + i + "] = " + this.numStepsFind[i]);
        }
    }
}

