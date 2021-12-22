/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  org.jetbrains.annotations.Nullable
 */
package org.ejml.dense.row.decomposition.eig.symm;

import java.util.Random;
import org.ejml.UtilEjml;
import org.ejml.data.FMatrixRMaj;
import org.ejml.dense.row.decomposition.eig.EigenvalueSmall_F32;
import org.jetbrains.annotations.Nullable;

public class SymmetricQREigenHelper_FDRM {
    protected Random rand = new Random(3434270L);
    protected int steps;
    protected int numExceptional;
    protected int lastExceptional;
    protected EigenvalueSmall_F32 eigenSmall = new EigenvalueSmall_F32();
    @Nullable
    protected FMatrixRMaj Q;
    protected int N;
    protected float[] diag = UtilEjml.ZERO_LENGTH_F32;
    protected float[] off = UtilEjml.ZERO_LENGTH_F32;
    protected int x1;
    protected int x2;
    protected int[] splits = new int[1];
    protected int numSplits;
    private float bulge;
    private float c;
    private float s;
    private float c2;
    private float s2;
    private float cs;

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

    public void setQ(FMatrixRMaj q) {
        this.Q = q;
    }

    public void incrementSteps() {
        ++this.steps;
    }

    public void init(float[] diag, float[] off, int numCols) {
        this.reset(numCols);
        this.diag = diag;
        this.off = off;
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

    public void reset(int N) {
        this.N = N;
        this.diag = UtilEjml.ZERO_LENGTH_F32;
        this.off = UtilEjml.ZERO_LENGTH_F32;
        if (this.splits.length < N) {
            this.splits = new int[N];
        }
        this.numSplits = 0;
        this.x1 = 0;
        this.x2 = N - 1;
        this.lastExceptional = 0;
        this.numExceptional = 0;
        this.steps = 0;
        this.Q = null;
    }

    public float[] copyDiag(float[] ret) {
        if (ret == null || ret.length < this.N) {
            ret = new float[this.N];
        }
        System.arraycopy(this.diag, 0, ret, 0, this.N);
        return ret;
    }

    public float[] copyOff(float[] ret) {
        if (ret == null || ret.length < this.N - 1) {
            ret = new float[this.N - 1];
        }
        System.arraycopy(this.off, 0, ret, 0, this.N - 1);
        return ret;
    }

    public float[] copyEigenvalues(float[] ret) {
        if (ret == null || ret.length < this.N) {
            ret = new float[this.N];
        }
        System.arraycopy(this.diag, 0, ret, 0, this.N);
        return ret;
    }

    public void setSubmatrix(int x1, int x2) {
        this.x1 = x1;
        this.x2 = x2;
    }

    protected boolean isZero(int index) {
        float bottom = Math.abs(this.diag[index]) + Math.abs(this.diag[index + 1]);
        return Math.abs(this.off[index]) <= bottom * UtilEjml.F_EPS;
    }

    protected void performImplicitSingleStep(float lambda, boolean byAngle) {
        if (this.x2 - this.x1 == 1) {
            this.createBulge2by2(this.x1, lambda, byAngle);
        } else {
            this.createBulge(this.x1, lambda, byAngle);
            for (int i = this.x1; i < this.x2 - 2 && this.bulge != 0.0f; ++i) {
                this.removeBulge(i);
            }
            if (this.bulge != 0.0f) {
                this.removeBulgeEnd(this.x2 - 2);
            }
        }
    }

    protected void updateQ(int m, int n, float c, float s) {
        int rowA = m * this.N;
        int rowB = n * this.N;
        int endA = rowA + this.N;
        while (rowA < endA) {
            float a = this.Q.data[rowA];
            float b = this.Q.data[rowB];
            this.Q.data[rowA++] = c * a + s * b;
            this.Q.data[rowB++] = -s * a + c * b;
        }
    }

    protected void createBulge(int x1, float p, boolean byAngle) {
        float a11 = this.diag[x1];
        float a22 = this.diag[x1 + 1];
        float a12 = this.off[x1];
        float a23 = this.off[x1 + 1];
        if (byAngle) {
            this.c = (float)Math.cos(p);
            this.s = (float)Math.sin(p);
            this.c2 = this.c * this.c;
            this.s2 = this.s * this.s;
            this.cs = this.c * this.s;
        } else {
            this.computeRotation(a11 - p, a12);
        }
        this.diag[x1] = this.c2 * a11 + 2.0f * this.cs * a12 + this.s2 * a22;
        this.diag[x1 + 1] = this.c2 * a22 - 2.0f * this.cs * a12 + this.s2 * a11;
        this.off[x1] = a12 * (this.c2 - this.s2) + this.cs * (a22 - a11);
        this.off[x1 + 1] = this.c * a23;
        this.bulge = this.s * a23;
        if (this.Q != null) {
            this.updateQ(x1, x1 + 1, this.c, this.s);
        }
    }

    protected void createBulge2by2(int x1, float p, boolean byAngle) {
        float a11 = this.diag[x1];
        float a22 = this.diag[x1 + 1];
        float a12 = this.off[x1];
        if (byAngle) {
            this.c = (float)Math.cos(p);
            this.s = (float)Math.sin(p);
            this.c2 = this.c * this.c;
            this.s2 = this.s * this.s;
            this.cs = this.c * this.s;
        } else {
            this.computeRotation(a11 - p, a12);
        }
        this.diag[x1] = this.c2 * a11 + 2.0f * this.cs * a12 + this.s2 * a22;
        this.diag[x1 + 1] = this.c2 * a22 - 2.0f * this.cs * a12 + this.s2 * a11;
        this.off[x1] = a12 * (this.c2 - this.s2) + this.cs * (a22 - a11);
        if (this.Q != null) {
            this.updateQ(x1, x1 + 1, this.c, this.s);
        }
    }

    private void computeRotation(float run, float rise) {
        if (Math.abs(rise) > Math.abs(run)) {
            float k = run / rise;
            float bottom = 1.0f + k * k;
            float bottom_sq = (float)Math.sqrt(bottom);
            this.s2 = 1.0f / bottom;
            this.c2 = k * k / bottom;
            this.cs = k / bottom;
            this.s = 1.0f / bottom_sq;
            this.c = k / bottom_sq;
        } else {
            float t = rise / run;
            float bottom = 1.0f + t * t;
            float bottom_sq = (float)Math.sqrt(bottom);
            this.c2 = 1.0f / bottom;
            this.s2 = t * t / bottom;
            this.cs = t / bottom;
            this.c = 1.0f / bottom_sq;
            this.s = t / bottom_sq;
        }
    }

    protected void removeBulge(int x1) {
        float a22 = this.diag[x1 + 1];
        float a33 = this.diag[x1 + 2];
        float a12 = this.off[x1];
        float a23 = this.off[x1 + 1];
        float a34 = this.off[x1 + 2];
        this.computeRotation(a12, this.bulge);
        this.diag[x1 + 1] = this.c2 * a22 + 2.0f * this.cs * a23 + this.s2 * a33;
        this.diag[x1 + 2] = this.c2 * a33 - 2.0f * this.cs * a23 + this.s2 * a22;
        this.off[x1] = this.c * a12 + this.s * this.bulge;
        this.off[x1 + 1] = a23 * (this.c2 - this.s2) + this.cs * (a33 - a22);
        this.off[x1 + 2] = this.c * a34;
        this.bulge = this.s * a34;
        if (this.Q != null) {
            this.updateQ(x1 + 1, x1 + 2, this.c, this.s);
        }
    }

    protected void removeBulgeEnd(int x1) {
        float a22 = this.diag[x1 + 1];
        float a12 = this.off[x1];
        float a23 = this.off[x1 + 1];
        float a33 = this.diag[x1 + 2];
        this.computeRotation(a12, this.bulge);
        this.diag[x1 + 1] = this.c2 * a22 + 2.0f * this.cs * a23 + this.s2 * a33;
        this.diag[x1 + 2] = this.c2 * a33 - 2.0f * this.cs * a23 + this.s2 * a22;
        this.off[x1] = this.c * a12 + this.s * this.bulge;
        this.off[x1 + 1] = a23 * (this.c2 - this.s2) + this.cs * (a33 - a22);
        if (this.Q != null) {
            this.updateQ(x1 + 1, x1 + 2, this.c, this.s);
        }
    }

    protected void eigenvalue2by2(int x1) {
        float scale;
        float a = this.diag[x1];
        float b = this.off[x1];
        float c = this.diag[x1 + 1];
        float absA = Math.abs(a);
        float absB = Math.abs(b);
        float absC = Math.abs(c);
        float f = scale = absA > absB ? absA : absB;
        if (absC > scale) {
            scale = absC;
        }
        if (scale == 0.0f) {
            this.off[x1] = 0.0f;
            this.diag[x1] = 0.0f;
            this.diag[x1 + 1] = 0.0f;
            return;
        }
        this.eigenSmall.symm2x2_fast(a /= scale, b /= scale, c /= scale);
        this.off[x1] = 0.0f;
        this.diag[x1] = scale * this.eigenSmall.value0.real;
        this.diag[x1 + 1] = scale * this.eigenSmall.value1.real;
    }

    public void exceptionalShift() {
        ++this.numExceptional;
        float mag = 0.05f * (float)this.numExceptional;
        if (mag > 1.0f) {
            mag = 1.0f;
        }
        float theta = 2.0f * (this.rand.nextFloat() - 0.5f) * mag;
        this.performImplicitSingleStep(theta, true);
        this.lastExceptional = this.steps;
    }

    public boolean nextSplit() {
        if (this.numSplits == 0) {
            return false;
        }
        this.x2 = this.splits[--this.numSplits];
        this.x1 = this.numSplits > 0 ? this.splits[this.numSplits - 1] + 1 : 0;
        return true;
    }

    public float computeShift() {
        if (this.x2 - this.x1 >= 1) {
            return this.computeWilkinsonShift();
        }
        return this.diag[this.x2];
    }

    public float computeWilkinsonShift() {
        float scale;
        float a = this.diag[this.x2 - 1];
        float b = this.off[this.x2 - 1];
        float c = this.diag[this.x2];
        float absA = Math.abs(a);
        float absB = Math.abs(b);
        float absC = Math.abs(c);
        float f = scale = absA > absB ? absA : absB;
        if (absC > scale) {
            scale = absC;
        }
        if (scale == 0.0f) {
            throw new RuntimeException("this should never happen");
        }
        this.eigenSmall.symm2x2_fast(a /= scale, b /= scale, c /= scale);
        float diff0 = Math.abs(this.eigenSmall.value0.real - c);
        float diff1 = Math.abs(this.eigenSmall.value1.real - c);
        if (diff0 < diff1) {
            return scale * this.eigenSmall.value0.real;
        }
        return scale * this.eigenSmall.value1.real;
    }

    public int getMatrixSize() {
        return this.N;
    }

    public void resetSteps() {
        this.steps = 0;
        this.lastExceptional = 0;
    }
}

