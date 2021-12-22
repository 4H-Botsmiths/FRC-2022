/*
 * Decompiled with CFR 0.150.
 */
package org.ejml.dense.row.decomposition.chol;

import org.ejml.data.DMatrixRMaj;

class CholeskyBlockHelper_DDRM {
    private final DMatrixRMaj L;
    private final double[] el;

    public CholeskyBlockHelper_DDRM(int widthMax) {
        this.L = new DMatrixRMaj(widthMax, widthMax);
        this.el = this.L.data;
    }

    public boolean decompose(DMatrixRMaj mat, int indexStart, int n) {
        double[] m = mat.data;
        double div_el_ii = 0.0;
        for (int i = 0; i < n; ++i) {
            for (int j = i; j < n; ++j) {
                double v;
                double sum = m[indexStart + i * mat.numCols + j];
                int iEl = i * n;
                int jEl = j * n;
                int end = iEl + i;
                while (iEl < end) {
                    sum -= this.el[iEl] * this.el[jEl];
                    ++iEl;
                    ++jEl;
                }
                if (i == j) {
                    double el_ii;
                    if (sum <= 0.0) {
                        return false;
                    }
                    this.el[i * n + i] = el_ii = Math.sqrt(sum);
                    m[indexStart + i * mat.numCols + i] = el_ii;
                    div_el_ii = 1.0 / el_ii;
                    continue;
                }
                this.el[j * n + i] = v = sum * div_el_ii;
                m[indexStart + j * mat.numCols + i] = v;
            }
        }
        return true;
    }

    public DMatrixRMaj getL() {
        return this.L;
    }
}

