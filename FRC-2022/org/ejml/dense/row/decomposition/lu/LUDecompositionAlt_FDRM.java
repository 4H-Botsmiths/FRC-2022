/*
 * Decompiled with CFR 0.150.
 */
package org.ejml.dense.row.decomposition.lu;

import org.ejml.data.FMatrixRMaj;
import org.ejml.dense.row.decomposition.lu.LUDecompositionBase_FDRM;

public class LUDecompositionAlt_FDRM
extends LUDecompositionBase_FDRM {
    @Override
    public boolean decompose(FMatrixRMaj a) {
        this.decomposeCommonInit(a);
        float[] LUcolj = this.vv;
        for (int j = 0; j < this.n; ++j) {
            float lujj;
            int i;
            for (i = 0; i < this.m; ++i) {
                LUcolj[i] = this.dataLU[i * this.n + j];
            }
            i = 0;
            while (i < this.m) {
                int rowIndex = i * this.n;
                int kmax = i < j ? i : j;
                float s = 0.0f;
                for (int k = 0; k < kmax; ++k) {
                    s += this.dataLU[rowIndex + k] * LUcolj[k];
                }
                int n = i++;
                float f = LUcolj[n] - s;
                LUcolj[n] = f;
                this.dataLU[rowIndex + j] = f;
            }
            int p = j;
            float max = Math.abs(LUcolj[p]);
            for (int i2 = j + 1; i2 < this.m; ++i2) {
                float v = Math.abs(LUcolj[i2]);
                if (!(v > max)) continue;
                p = i2;
                max = v;
            }
            if (p != j) {
                int rowP = p * this.n;
                int rowJ = j * this.n;
                int endP = rowP + this.n;
                while (rowP < endP) {
                    float t = this.dataLU[rowP];
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
            if (j >= this.m || (lujj = this.dataLU[j * this.n + j]) == 0.0f) continue;
            for (int i3 = j + 1; i3 < this.m; ++i3) {
                int n = i3 * this.n + j;
                this.dataLU[n] = this.dataLU[n] / lujj;
            }
        }
        return true;
    }
}

