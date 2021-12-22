/*
 * Decompiled with CFR 0.150.
 */
package org.ejml.dense.row.decomposition.chol;

import org.ejml.data.FMatrixRMaj;

class CholeskyBlockHelper_FDRM {
    private final FMatrixRMaj L;
    private final float[] el;

    public CholeskyBlockHelper_FDRM(int widthMax) {
        this.L = new FMatrixRMaj(widthMax, widthMax);
        this.el = this.L.data;
    }

    public boolean decompose(FMatrixRMaj mat, int indexStart, int n) {
        float[] m = mat.data;
        float div_el_ii = 0.0f;
        for (int i = 0; i < n; ++i) {
            for (int j = i; j < n; ++j) {
                float v;
                float sum = m[indexStart + i * mat.numCols + j];
                int iEl = i * n;
                int jEl = j * n;
                int end = iEl + i;
                while (iEl < end) {
                    sum -= this.el[iEl] * this.el[jEl];
                    ++iEl;
                    ++jEl;
                }
                if (i == j) {
                    float el_ii;
                    if (sum <= 0.0f) {
                        return false;
                    }
                    this.el[i * n + i] = el_ii = (float)Math.sqrt(sum);
                    m[indexStart + i * mat.numCols + i] = el_ii;
                    div_el_ii = 1.0f / el_ii;
                    continue;
                }
                this.el[j * n + i] = v = sum * div_el_ii;
                m[indexStart + j * mat.numCols + i] = v;
            }
        }
        return true;
    }

    public FMatrixRMaj getL() {
        return this.L;
    }
}

