/*
 * Decompiled with CFR 0.150.
 */
package org.opencv.core;

import java.util.Arrays;

public class Scalar {
    public double[] val;

    public Scalar(double v0, double v1, double v2, double v3) {
        this.val = new double[]{v0, v1, v2, v3};
    }

    public Scalar(double v0, double v1, double v2) {
        this.val = new double[]{v0, v1, v2, 0.0};
    }

    public Scalar(double v0, double v1) {
        this.val = new double[]{v0, v1, 0.0, 0.0};
    }

    public Scalar(double v0) {
        this.val = new double[]{v0, 0.0, 0.0, 0.0};
    }

    public Scalar(double[] vals) {
        if (vals != null && vals.length == 4) {
            this.val = (double[])vals.clone();
        } else {
            this.val = new double[4];
            this.set(vals);
        }
    }

    public void set(double[] vals) {
        if (vals != null) {
            this.val[0] = vals.length > 0 ? vals[0] : 0.0;
            this.val[1] = vals.length > 1 ? vals[1] : 0.0;
            this.val[2] = vals.length > 2 ? vals[2] : 0.0;
            this.val[3] = vals.length > 3 ? vals[3] : 0.0;
        } else {
            this.val[3] = 0.0;
            this.val[2] = 0.0;
            this.val[1] = 0.0;
            this.val[0] = 0.0;
        }
    }

    public static Scalar all(double v) {
        return new Scalar(v, v, v, v);
    }

    public Scalar clone() {
        return new Scalar(this.val);
    }

    public Scalar mul(Scalar it, double scale) {
        return new Scalar(this.val[0] * it.val[0] * scale, this.val[1] * it.val[1] * scale, this.val[2] * it.val[2] * scale, this.val[3] * it.val[3] * scale);
    }

    public Scalar mul(Scalar it) {
        return this.mul(it, 1.0);
    }

    public Scalar conj() {
        return new Scalar(this.val[0], -this.val[1], -this.val[2], -this.val[3]);
    }

    public boolean isReal() {
        return this.val[1] == 0.0 && this.val[2] == 0.0 && this.val[3] == 0.0;
    }

    public int hashCode() {
        int prime = 31;
        int result = 1;
        result = 31 * result + Arrays.hashCode(this.val);
        return result;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof Scalar)) {
            return false;
        }
        Scalar it = (Scalar)obj;
        return Arrays.equals(this.val, it.val);
    }

    public String toString() {
        return "[" + this.val[0] + ", " + this.val[1] + ", " + this.val[2] + ", " + this.val[3] + "]";
    }
}

