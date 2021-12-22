/*
 * Decompiled with CFR 0.150.
 */
package org.ejml.dense.row.decomposition.eig;

import org.ejml.data.Complex_F32;

public class EigenvalueSmall_F32 {
    public Complex_F32 value0 = new Complex_F32();
    public Complex_F32 value1 = new Complex_F32();

    public void value2x2(float a11, float a12, float a21, float a22) {
        float c;
        float s;
        if (a12 + a21 == 0.0f) {
            c = s = 1.0f / (float)Math.sqrt(2.0);
        } else {
            float aa = a11 - a22;
            float bb = a12 + a21;
            float t_hat = aa / bb;
            float t = t_hat / (1.0f + (float)Math.sqrt(1.0f + t_hat * t_hat));
            c = 1.0f / (float)Math.sqrt(1.0f + t * t);
            s = c * t;
        }
        float c2 = c * c;
        float s2 = s * s;
        float cs = c * s;
        float b11 = c2 * a11 + s2 * a22 - cs * (a12 + a21);
        float b12 = c2 * a12 - s2 * a21 + cs * (a11 - a22);
        float b21 = c2 * a21 - s2 * a12 + cs * (a11 - a22);
        if (b21 * b12 >= 0.0f) {
            if (b12 == 0.0f) {
                c = 0.0f;
                s = 1.0f;
            } else {
                s = (float)Math.sqrt(b21 / (b12 + b21));
                c = (float)Math.sqrt(b12 / (b12 + b21));
            }
            cs = c * s;
            a11 = b11 - cs * (b12 + b21);
            a22 = b11 + cs * (b12 + b21);
            this.value0.real = a11;
            this.value1.real = a22;
            this.value1.imaginary = 0.0f;
            this.value0.imaginary = 0.0f;
        } else {
            this.value0.real = this.value1.real = b11;
            this.value0.imaginary = (float)Math.sqrt(-b21 * b12);
            this.value1.imaginary = -this.value0.imaginary;
        }
    }

    public void value2x2_fast(float a11, float a12, float a21, float a22) {
        float left = (a11 + a22) / 2.0f;
        float inside = 4.0f * a12 * a21 + (a11 - a22) * (a11 - a22);
        if (inside < 0.0f) {
            this.value0.real = this.value1.real = left;
            this.value0.imaginary = (float)Math.sqrt(-inside) / 2.0f;
            this.value1.imaginary = -this.value0.imaginary;
        } else {
            float right = (float)Math.sqrt(inside) / 2.0f;
            this.value0.real = left + right;
            this.value1.real = left - right;
            this.value1.imaginary = 0.0f;
            this.value0.imaginary = 0.0f;
        }
    }

    public void symm2x2_fast(float a11, float a12, float a22) {
        float left = (a11 + a22) * 0.5f;
        float b = (a11 - a22) * 0.5f;
        float right = (float)Math.sqrt(b * b + a12 * a12);
        this.value0.real = left + right;
        this.value1.real = left - right;
    }
}

