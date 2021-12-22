/*
 * Decompiled with CFR 0.150.
 */
package org.ejml.dense.row.decompose.chol;

import org.ejml.UtilEjml;
import org.ejml.dense.row.decompose.chol.CholeskyDecompositionCommon_ZDRM;

public class CholeskyDecompositionInner_ZDRM
extends CholeskyDecompositionCommon_ZDRM {
    double tolerance = UtilEjml.EPS;

    public CholeskyDecompositionInner_ZDRM() {
        super(true);
    }

    public CholeskyDecompositionInner_ZDRM(boolean lower) {
        super(lower);
    }

    public void setTolerance(double tolerance) {
        this.tolerance = tolerance;
    }

    @Override
    protected boolean decomposeLower() {
        int j;
        int i;
        if (this.n == 0) {
            throw new IllegalArgumentException("Cholesky is undefined for 0 by 0 matrix");
        }
        double real_el_ii = 0.0;
        int stride = this.n * 2;
        for (i = 0; i < this.n; ++i) {
            for (j = i; j < this.n; ++j) {
                double realSum = this.t[i * stride + j * 2];
                double imagSum = this.t[i * stride + j * 2 + 1];
                if (i == j) {
                    if (Math.abs(imagSum) > this.tolerance * Math.abs(realSum)) {
                        return false;
                    }
                    int end = i * stride + i * 2;
                    int index = i * stride;
                    while (index < end) {
                        double real = this.t[index++];
                        double imag = this.t[index++];
                        realSum -= real * real + imag * imag;
                    }
                    if (realSum <= 0.0) {
                        return false;
                    }
                    this.t[i * stride + i * 2] = real_el_ii = Math.sqrt(realSum);
                    this.t[i * stride + i * 2 + 1] = 0.0;
                    continue;
                }
                int iEl = i * stride;
                int jEl = j * stride;
                int end = iEl + i * 2;
                while (iEl < end) {
                    double realI = this.t[iEl++];
                    double imagI = this.t[iEl++];
                    double realJ = this.t[jEl++];
                    double imagJ = this.t[jEl++];
                    realSum -= realI * realJ + imagI * imagJ;
                    imagSum -= realI * imagJ - realJ * imagI;
                }
                this.t[j * stride + i * 2] = realSum / real_el_ii;
                this.t[j * stride + i * 2 + 1] = imagSum / real_el_ii;
            }
        }
        for (i = 1; i < this.n; ++i) {
            for (j = 0; j < i; ++j) {
                this.t[i * stride + j * 2 + 1] = -this.t[i * stride + j * 2 + 1];
            }
        }
        return true;
    }

    @Override
    protected boolean decomposeUpper() {
        if (this.n == 0) {
            throw new IllegalArgumentException("Cholesky is undefined for 0 by 0 matrix");
        }
        double real_el_ii = 0.0;
        int stride = this.n * 2;
        for (int i = 0; i < this.n; ++i) {
            for (int j = i; j < this.n; ++j) {
                int k;
                double realSum = this.t[i * stride + j * 2];
                double imagSum = this.t[i * stride + j * 2 + 1];
                if (i == j) {
                    if (Math.abs(imagSum) > this.tolerance * Math.abs(realSum)) {
                        return false;
                    }
                    for (k = 0; k < i; ++k) {
                        double real = this.t[k * stride + i * 2];
                        double imag = this.t[k * stride + i * 2 + 1];
                        realSum -= real * real + imag * imag;
                    }
                    if (realSum <= 0.0) {
                        return false;
                    }
                    this.t[i * stride + i * 2] = real_el_ii = Math.sqrt(realSum);
                    this.t[i * stride + i * 2 + 1] = 0.0;
                    continue;
                }
                for (k = 0; k < i; ++k) {
                    double realI = this.t[k * stride + i * 2];
                    double imagI = this.t[k * stride + i * 2 + 1];
                    double realJ = this.t[k * stride + j * 2];
                    double imagJ = this.t[k * stride + j * 2 + 1];
                    realSum -= realI * realJ + imagI * imagJ;
                    imagSum -= realI * imagJ - realJ * imagI;
                }
                this.t[i * stride + j * 2] = realSum / real_el_ii;
                this.t[i * stride + j * 2 + 1] = imagSum / real_el_ii;
            }
        }
        return true;
    }
}

