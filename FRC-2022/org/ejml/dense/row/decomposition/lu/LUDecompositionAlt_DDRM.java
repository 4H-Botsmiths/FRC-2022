/*
 * Decompiled with CFR 0.150.
 */
package org.ejml.dense.row.decomposition.lu;

import org.ejml.data.DMatrixRMaj;
import org.ejml.dense.row.decomposition.lu.LUDecompositionBase_DDRM;

public class LUDecompositionAlt_DDRM
extends LUDecompositionBase_DDRM {
    @Override
    public boolean decompose(DMatrixRMaj a) {
        this.decomposeCommonInit(a);
        double[] LUcolj = this.vv;
        for (int j = 0; j < this.n; ++j) {
            double lujj;
            int i;
            for (i = 0; i < this.m; ++i) {
                LUcolj[i] = this.dataLU[i * this.n + j];
            }
            i = 0;
            while (i < this.m) {
                int rowIndex = i * this.n;
                int kmax = i < j ? i : j;
                double s = 0.0;
                for (int k = 0; k < kmax; ++k) {
                    s += this.dataLU[rowIndex + k] * LUcolj[k];
                }
                int n = i++;
                double d = LUcolj[n] - s;
                LUcolj[n] = d;
                this.dataLU[rowIndex + j] = d;
            }
            int p = j;
            double max = Math.abs(LUcolj[p]);
            for (int i2 = j + 1; i2 < this.m; ++i2) {
                double v = Math.abs(LUcolj[i2]);
                if (!(v > max)) continue;
                p = i2;
                max = v;
            }
            if (p != j) {
                int rowP = p * this.n;
                int rowJ = j * this.n;
                int endP = rowP + this.n;
                while (rowP < endP) {
                    double t = this.dataLU[rowP];
                    this.dataLU[rowP] = this.dataLU[rowJ];
                    this.dataLU[rowJ] = t;
                    ++rowP;
                    ++rowJ;
                }
                int k = this.pivot[p];
                this.pivot[p] = this.pivot[j];
                this.pivot[j] = k;
                this.pivsign = -this.pivsign;
            }
            this.indx[j] = p;
            if (j >= this.m || (lujj = this.dataLU[j * this.n + j]) == 0.0) continue;
            for (int i3 = j + 1; i3 < this.m; ++i3) {
                int n = i3 * this.n + j;
                this.dataLU[n] = this.dataLU[n] / lujj;
            }
        }
        return true;
    }
}

