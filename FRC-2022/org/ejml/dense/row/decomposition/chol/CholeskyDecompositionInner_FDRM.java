/*
 * Decompiled with CFR 0.150.
 */
package org.ejml.dense.row.decomposition.chol;

import org.ejml.dense.row.decomposition.chol.CholeskyDecompositionCommon_FDRM;

public class CholeskyDecompositionInner_FDRM
extends CholeskyDecompositionCommon_FDRM {
    public CholeskyDecompositionInner_FDRM() {
        super(true);
    }

    public CholeskyDecompositionInner_FDRM(boolean lower) {
        super(lower);
    }

    @Override
    protected boolean decomposeLower() {
        int j;
        int i;
        float div_el_ii = 0.0f;
        for (i = 0; i < this.n; ++i) {
            for (j = i; j < this.n; ++j) {
                float sum = this.t[i * this.n + j];
                int iEl = i * this.n;
                int jEl = j * this.n;
                int end = iEl + i;
                while (iEl < end) {
                    sum -= this.t[iEl] * this.t[jEl];
                    ++iEl;
                    ++jEl;
                }
                if (i == j) {
                    float el_ii;
                    if (sum <= 0.0f) {
                        return false;
                    }
                    this.t[i * this.n + i] = el_ii = (float)Math.sqrt(sum);
                    div_el_ii = 1.0f / el_ii;
                    continue;
                }
                this.t[j * this.n + i] = sum * div_el_ii;
            }
        }
        for (i = 0; i < this.n; ++i) {
            for (j = i + 1; j < this.n; ++j) {
                this.t[i * this.n + j] = 0.0f;
            }
        }
        return true;
    }

    @Override
    protected boolean decomposeUpper() {
        int j;
        int i;
        float div_el_ii = 0.0f;
        for (i = 0; i < this.n; ++i) {
            for (j = i; j < this.n; ++j) {
                float sum = this.t[i * this.n + j];
                for (int k = 0; k < i; ++k) {
                    sum -= this.t[k * this.n + i] * this.t[k * this.n + j];
                }
                if (i == j) {
                    float el_ii;
                    if (sum <= 0.0f) {
                        return false;
                    }
                    this.t[i * this.n + i] = el_ii = (float)Math.sqrt(sum);
                    div_el_ii = 1.0f / el_ii;
                    continue;
                }
                this.t[i * this.n + j] = sum * div_el_ii;
            }
        }
        for (i = 0; i < this.n; ++i) {
            for (j = 0; j < i; ++j) {
                this.t[i * this.n + j] = 0.0f;
            }
        }
        return true;
    }
}

