/*
 * Decompiled with CFR 0.150.
 */
package org.ejml.dense.row.decompose.lu;

import org.ejml.data.CMatrixRMaj;
import org.ejml.dense.row.decompose.lu.LUDecompositionBase_CDRM;

public class LUDecompositionAlt_CDRM
extends LUDecompositionBase_CDRM {
    @Override
    public boolean decompose(CMatrixRMaj a) {
        this.decomposeCommonInit(a);
        float[] LUcolj = this.vv;
        for (int j = 0; j < this.n; ++j) {
            int k;
            int i;
            for (i = 0; i < this.m; ++i) {
                LUcolj[i * 2] = this.dataLU[i * this.stride + j * 2];
                LUcolj[i * 2 + 1] = this.dataLU[i * this.stride + j * 2 + 1];
            }
            for (i = 0; i < this.m; ++i) {
                int rowIndex = i * this.stride;
                int kmax = i < j ? i : j;
                float realS = 0.0f;
                float imgS = 0.0f;
                for (k = 0; k < kmax; ++k) {
                    float realD = this.dataLU[rowIndex + k * 2];
                    float imgD = this.dataLU[rowIndex + k * 2 + 1];
                    float realCol = LUcolj[k * 2];
                    float imgCol = LUcolj[k * 2 + 1];
                    realS += realD * realCol - imgD * imgCol;
                    imgS += realD * imgCol + imgD * realCol;
                }
                int n = i * 2;
                float f = LUcolj[n] - realS;
                LUcolj[n] = f;
                this.dataLU[rowIndex + j * 2] = f;
                int n2 = i * 2 + 1;
                float f2 = LUcolj[n2] - imgS;
                LUcolj[n2] = f2;
                this.dataLU[rowIndex + j * 2 + 1] = f2;
            }
            int p = j;
            float max = LUDecompositionAlt_CDRM.mag(LUcolj, p * 2);
            for (int i2 = j + 1; i2 < this.m; ++i2) {
                float v = LUDecompositionAlt_CDRM.mag(LUcolj, i2 * 2);
                if (!(v > max)) continue;
                p = i2;
                max = v;
            }
            if (p != j) {
                int rowP = p * this.stride;
                int rowJ = j * this.stride;
                int endP = rowP + this.stride;
                while (rowP < endP) {
                    float t = this.dataLU[rowP];
                    this.dataLU[rowP] = this.dataLU[rowJ];
                    this.dataLU[rowJ] = t;
                    ++rowP;
                    ++rowJ;
                }
                k = this.pivot[p];
                this.pivot[p] = this.pivot[j];
                this.pivot[j] = k;
                this.pivsign = -this.pivsign;
            }
            this.indx[j] = p;
            if (j >= this.m) continue;
            float realLujj = this.dataLU[j * this.stride + j * 2];
            float imgLujj = this.dataLU[j * this.stride + j * 2 + 1];
            float magLujj = realLujj * realLujj + imgLujj * imgLujj;
            if (realLujj == 0.0f && imgLujj == 0.0f) continue;
            for (int i3 = j + 1; i3 < this.m; ++i3) {
                float realLU = this.dataLU[i3 * this.stride + j * 2];
                float imagLU = this.dataLU[i3 * this.stride + j * 2 + 1];
                this.dataLU[i3 * this.stride + j * 2] = (realLU * realLujj + imagLU * imgLujj) / magLujj;
                this.dataLU[i3 * this.stride + j * 2 + 1] = (imagLU * realLujj - realLU * imgLujj) / magLujj;
            }
        }
        return true;
    }

    private static float mag(float[] d, int index) {
        float r = d[index];
        float i = d[index + 1];
        return r * r + i * i;
    }
}

