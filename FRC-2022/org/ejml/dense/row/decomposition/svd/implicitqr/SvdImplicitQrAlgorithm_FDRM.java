/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  org.jetbrains.annotations.Nullable
 */
package org.ejml.dense.row.decomposition.svd.implicitqr;

import java.util.Random;
import org.ejml.UtilEjml;
import org.ejml.data.FMatrixRMaj;
import org.ejml.dense.row.decomposition.eig.EigenvalueSmall_F32;
import org.jetbrains.annotations.Nullable;

public class SvdImplicitQrAlgorithm_FDRM {
    protected Random rand = new Random(3434270L);
    @Nullable
    protected FMatrixRMaj Ut;
    @Nullable
    protected FMatrixRMaj Vt;
    protected int totalSteps;
    protected float maxValue;
    protected int N;
    protected EigenvalueSmall_F32 eigenSmall = new EigenvalueSmall_F32();
    protected int numExceptional;
    protected int nextExceptional;
    protected float[] diag;
    protected float[] off;
    float bulge;
    protected int x1;
    protected int x2;
    int steps;
    protected int[] splits;
    protected int numSplits;
    private int exceptionalThresh = 15;
    private int maxIterations = this.exceptionalThresh * 100;
    boolean followScript;
    private static final int giveUpOnKnown = 10;
    private float[] values;
    private boolean fastValues = false;
    private boolean findingZeros;
    float c;
    float s;

    public SvdImplicitQrAlgorithm_FDRM(boolean fastValues) {
        this.fastValues = fastValues;
    }

    public SvdImplicitQrAlgorithm_FDRM() {
    }

    @Nullable
    public FMatrixRMaj getUt() {
        return this.Ut;
    }

    public void setUt(@Nullable FMatrixRMaj ut) {
        this.Ut = ut;
    }

    @Nullable
    public FMatrixRMaj getVt() {
        return this.Vt;
    }

    public void setVt(@Nullable FMatrixRMaj vt) {
        this.Vt = vt;
    }

    public void setMatrix(int numRows, int numCols, float[] diag, float[] off) {
        this.initParam(numRows, numCols);
        this.diag = diag;
        this.off = off;
        this.maxValue = Math.abs(diag[0]);
        for (int i = 1; i < this.N; ++i) {
            float a = Math.abs(diag[i]);
            float b = Math.abs(off[i - 1]);
            if (a > this.maxValue) {
                this.maxValue = Math.abs(a);
            }
            if (!(b > this.maxValue)) continue;
            this.maxValue = Math.abs(b);
        }
    }

    public float[] swapDiag(float[] diag) {
        float[] ret = this.diag;
        this.diag = diag;
        return ret;
    }

    public float[] swapOff(float[] off) {
        float[] ret = this.off;
        this.off = off;
        return ret;
    }

    public void setMaxValue(float maxValue) {
        this.maxValue = maxValue;
    }

    public void initParam(int M, int N) {
        if (N > M) {
            throw new RuntimeException("Must be a square or tall matrix");
        }
        this.N = N;
        if (this.splits == null || this.splits.length < N) {
            this.splits = new int[N];
        }
        this.x1 = 0;
        this.x2 = this.N - 1;
        this.steps = 0;
        this.totalSteps = 0;
        this.numSplits = 0;
        this.numExceptional = 0;
        this.nextExceptional = this.exceptionalThresh;
    }

    public boolean process() {
        this.followScript = false;
        this.findingZeros = true;
        return this._process();
    }

    public boolean process(float[] values) {
        this.followScript = true;
        this.values = values;
        this.findingZeros = false;
        return this._process();
    }

    public boolean _process() {
        if (this.maxValue == 0.0f) {
            return true;
        }
        while (this.x2 >= 0) {
            if (this.steps > this.maxIterations) {
                return false;
            }
            if (this.x1 == this.x2) {
                this.resetSteps();
                if (this.nextSplit()) continue;
                break;
            }
            if (this.fastValues && this.x2 - this.x1 == 1) {
                this.resetSteps();
                this.eigenBB_2x2(this.x1);
                this.setSubmatrix(this.x2, this.x2);
                continue;
            }
            if (this.steps >= this.nextExceptional) {
                this.exceptionShift();
                continue;
            }
            if (this.checkForAndHandleZeros()) continue;
            if (this.followScript) {
                this.performScriptedStep();
                continue;
            }
            this.performDynamicStep();
        }
        return true;
    }

    private void performDynamicStep() {
        if (this.findingZeros) {
            if (this.steps > 6) {
                this.findingZeros = false;
            } else {
                float scale = this.computeBulgeScale();
                this.performImplicitSingleStep(scale, 0.0f, false);
            }
        } else {
            float scale = this.computeBulgeScale();
            float lambda = this.selectWilkinsonShift(scale);
            this.performImplicitSingleStep(scale, lambda, false);
        }
    }

    private void performScriptedStep() {
        float scale = this.computeBulgeScale();
        if (this.steps > 10) {
            this.followScript = false;
        } else {
            float s = this.values[this.x2] / scale;
            this.performImplicitSingleStep(scale, s * s, false);
        }
    }

    public void incrementSteps() {
        ++this.steps;
        ++this.totalSteps;
    }

    public boolean isOffZero(int i) {
        float bottom = Math.abs(this.diag[i]) + Math.abs(this.diag[i + 1]);
        return Math.abs(this.off[i]) <= bottom * UtilEjml.F_EPS;
    }

    public boolean isDiagonalZero(int i) {
        float bottom = Math.abs(this.diag[i + 1]) + Math.abs(this.off[i]);
        return Math.abs(this.diag[i]) <= bottom * UtilEjml.F_EPS;
    }

    public void resetSteps() {
        this.steps = 0;
        this.nextExceptional = this.exceptionalThresh;
        this.numExceptional = 0;
    }

    public boolean nextSplit() {
        if (this.numSplits == 0) {
            return false;
        }
        this.x2 = this.splits[--this.numSplits];
        this.x1 = this.numSplits > 0 ? this.splits[this.numSplits - 1] + 1 : 0;
        return true;
    }

    public void performImplicitSingleStep(float scale, float lambda, boolean byAngle) {
        this.createBulge(this.x1, lambda, scale, byAngle);
        for (int i = this.x1; i < this.x2 - 1 && this.bulge != 0.0f; ++i) {
            this.removeBulgeLeft(i, true);
            if (this.bulge == 0.0f) break;
            this.removeBulgeRight(i);
        }
        if (this.bulge != 0.0f) {
            this.removeBulgeLeft(this.x2 - 1, false);
        }
        this.incrementSteps();
    }

    protected void updateRotator(FMatrixRMaj Q, int m, int n, float c, float s) {
        int rowA = m * Q.numCols;
        int rowB = n * Q.numCols;
        int endA = rowA + Q.numCols;
        while (rowA != endA) {
            float a = Q.get(rowA);
            float b = Q.get(rowB);
            Q.set(rowA, c * a + s * b);
            Q.set(rowB, -s * a + c * b);
            ++rowA;
            ++rowB;
        }
    }

    private float computeBulgeScale() {
        float b11 = this.diag[this.x1];
        float b12 = this.off[this.x1];
        return Math.max(Math.abs(b11), Math.abs(b12));
    }

    protected void createBulge(int x1, float p, float scale, boolean byAngle) {
        float b11 = this.diag[x1];
        float b12 = this.off[x1];
        float b22 = this.diag[x1 + 1];
        if (byAngle) {
            this.c = (float)Math.cos(p);
            this.s = (float)Math.sin(p);
        } else {
            float u1 = b11 / scale * (b11 / scale) - p;
            float u2 = b12 / scale * (b11 / scale);
            float gamma = (float)Math.sqrt(u1 * u1 + u2 * u2);
            this.c = u1 / gamma;
            this.s = u2 / gamma;
        }
        this.diag[x1] = b11 * this.c + b12 * this.s;
        this.off[x1] = b12 * this.c - b11 * this.s;
        this.diag[x1 + 1] = b22 * this.c;
        this.bulge = b22 * this.s;
        if (this.Vt != null) {
            this.updateRotator(this.Vt, x1, x1 + 1, this.c, this.s);
        }
    }

    protected void computeRotator(float rise, float run) {
        if (Math.abs(rise) < Math.abs(run)) {
            float k = rise / run;
            float bottom = (float)Math.sqrt(1.0f + k * k);
            this.s = 1.0f / bottom;
            this.c = k / bottom;
        } else {
            float t = run / rise;
            float bottom = (float)Math.sqrt(1.0f + t * t);
            this.c = 1.0f / bottom;
            this.s = t / bottom;
        }
    }

    protected void removeBulgeLeft(int x1, boolean notLast) {
        float b11 = this.diag[x1];
        float b12 = this.off[x1];
        float b22 = this.diag[x1 + 1];
        this.computeRotator(b11, this.bulge);
        this.diag[x1] = this.c * b11 + this.s * this.bulge;
        this.off[x1] = this.c * b12 + this.s * b22;
        this.diag[x1 + 1] = this.c * b22 - this.s * b12;
        if (notLast) {
            float b23 = this.off[x1 + 1];
            this.bulge = this.s * b23;
            this.off[x1 + 1] = this.c * b23;
        }
        if (this.Ut != null) {
            this.updateRotator(this.Ut, x1, x1 + 1, this.c, this.s);
        }
    }

    protected void removeBulgeRight(int x1) {
        float b12 = this.off[x1];
        float b22 = this.diag[x1 + 1];
        float b23 = this.off[x1 + 1];
        this.computeRotator(b12, this.bulge);
        this.off[x1] = b12 * this.c + this.bulge * this.s;
        this.diag[x1 + 1] = b22 * this.c + b23 * this.s;
        this.off[x1 + 1] = -b22 * this.s + b23 * this.c;
        float b33 = this.diag[x1 + 2];
        this.diag[x1 + 2] = b33 * this.c;
        this.bulge = b33 * this.s;
        if (this.Vt != null) {
            this.updateRotator(this.Vt, x1 + 1, x1 + 2, this.c, this.s);
        }
    }

    public void setSubmatrix(int x1, int x2) {
        this.x1 = x1;
        this.x2 = x2;
    }

    public float selectWilkinsonShift(float scale) {
        float a22;
        if (this.x2 - this.x1 > 1) {
            float d1 = this.diag[this.x2 - 1] / scale;
            float o1 = this.off[this.x2 - 2] / scale;
            float d2 = this.diag[this.x2] / scale;
            float o2 = this.off[this.x2 - 1] / scale;
            float a11 = o1 * o1 + d1 * d1;
            a22 = o2 * o2 + d2 * d2;
            this.eigenSmall.symm2x2_fast(a11, o2 * d1, a22);
        } else {
            float a = this.diag[this.x2 - 1] / scale;
            float b = this.off[this.x2 - 1] / scale;
            float c = this.diag[this.x2] / scale;
            float a11 = a * a;
            a22 = b * b + c * c;
            this.eigenSmall.symm2x2_fast(a11, a * b, a22);
        }
        float diff0 = Math.abs(this.eigenSmall.value0.real - a22);
        float diff1 = Math.abs(this.eigenSmall.value1.real - a22);
        return diff0 < diff1 ? this.eigenSmall.value0.real : this.eigenSmall.value1.real;
    }

    protected void eigenBB_2x2(int x1) {
        float scale;
        float b11 = this.diag[x1];
        float b12 = this.off[x1];
        float b22 = this.diag[x1 + 1];
        float absA = Math.abs(b11);
        float absB = Math.abs(b12);
        float absC = Math.abs(b22);
        float f = scale = absA > absB ? absA : absB;
        if (absC > scale) {
            scale = absC;
        }
        if (scale == 0.0f) {
            return;
        }
        this.eigenSmall.symm2x2_fast((b11 /= scale) * b11, b11 * (b12 /= scale), b12 * b12 + (b22 /= scale) * b22);
        this.off[x1] = 0.0f;
        this.diag[x1] = scale * (float)Math.sqrt(this.eigenSmall.value0.real);
        float sgn = Math.signum(this.eigenSmall.value1.real);
        this.diag[x1 + 1] = sgn * scale * (float)Math.sqrt(Math.abs(this.eigenSmall.value1.real));
    }

    protected boolean checkForAndHandleZeros() {
        int i;
        for (i = this.x2 - 1; i >= this.x1; --i) {
            if (!this.isOffZero(i)) continue;
            this.resetSteps();
            this.splits[this.numSplits++] = i;
            this.x1 = i + 1;
            return true;
        }
        for (i = this.x2 - 1; i >= this.x1; --i) {
            if (!this.isDiagonalZero(i)) continue;
            this.pushRight(i);
            this.resetSteps();
            this.splits[this.numSplits++] = i;
            this.x1 = i + 1;
            return true;
        }
        return false;
    }

    private void pushRight(int row) {
        if (this.isOffZero(row)) {
            return;
        }
        this.rotatorPushRight(row);
        int end = this.N - 2 - row;
        for (int i = 0; i < end && this.bulge != 0.0f; ++i) {
            this.rotatorPushRight2(row, i + 2);
        }
    }

    private void rotatorPushRight(int m) {
        float b11 = this.off[m];
        float b21 = this.diag[m + 1];
        this.computeRotator(b21, -b11);
        this.off[m] = 0.0f;
        this.diag[m + 1] = b21 * this.c - b11 * this.s;
        if (m + 2 < this.N) {
            float b22 = this.off[m + 1];
            this.off[m + 1] = b22 * this.c;
            this.bulge = b22 * this.s;
        } else {
            this.bulge = 0.0f;
        }
        if (this.Ut != null) {
            this.updateRotator(this.Ut, m, m + 1, this.c, this.s);
        }
    }

    private void rotatorPushRight2(int m, int offset) {
        float b11 = this.bulge;
        float b12 = this.diag[m + offset];
        this.computeRotator(b12, -b11);
        this.diag[m + offset] = b12 * this.c - b11 * this.s;
        if (m + offset < this.N - 1) {
            float b22 = this.off[m + offset];
            this.off[m + offset] = b22 * this.c;
            this.bulge = b22 * this.s;
        }
        if (this.Ut != null) {
            this.updateRotator(this.Ut, m, m + offset, this.c, this.s);
        }
    }

    public void exceptionShift() {
        ++this.numExceptional;
        float mag = 0.05f * (float)this.numExceptional;
        if (mag > 1.0f) {
            mag = 1.0f;
        }
        float angle = 2.0f * UtilEjml.F_PI * (this.rand.nextFloat() - 0.5f) * mag;
        this.performImplicitSingleStep(0.0f, angle, true);
        this.nextExceptional = this.steps + this.exceptionalThresh;
    }

    public void printMatrix() {
        int j;
        System.out.print("Off Diag[ ");
        for (j = 0; j < this.N - 1; ++j) {
            System.out.printf("%5.2ff ", Float.valueOf(this.off[j]));
        }
        System.out.println();
        System.out.print("    Diag[ ");
        for (j = 0; j < this.N; ++j) {
            System.out.printf("%5.2ff ", Float.valueOf(this.diag[j]));
        }
        System.out.println();
    }

    public int getNumberOfSingularValues() {
        return this.N;
    }

    public float getSingularValue(int index) {
        return this.diag[index];
    }

    public void setFastValues(boolean b) {
        this.fastValues = b;
    }

    public float[] getSingularValues() {
        return this.diag;
    }

    public float[] getDiag() {
        return this.diag;
    }

    public float[] getOff() {
        return this.off;
    }

    public float getMaxValue() {
        return this.maxValue;
    }
}

