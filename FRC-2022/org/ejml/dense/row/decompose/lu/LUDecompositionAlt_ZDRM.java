/*
 * Decompiled with CFR 0.150.
 */
package org.ejml.dense.row.decompose.lu;

import org.ejml.data.ZMatrixRMaj;
import org.ejml.dense.row.decompose.lu.LUDecompositionBase_ZDRM;

public class LUDecompositionAlt_ZDRM
extends LUDecompositionBase_ZDRM {
    @Override
    public boolean decompose(ZMatrixRMaj a) {
        this.decomposeCommonInit(a);
        double[] LUcolj = this.vv;
        for (int j = 0; j < this.n; ++j) {
            int i;
            for (i = 0; i < this.m; ++i) {
                LUcolj[i * 2] = this.dataLU[i * this.stride + j * 2];
                LUcolj[i * 2 + 1] = this.dataLU[i * this.stride + j * 2 + 1];
            }
            for (i = 0; i < this.m; ++i) {
                int rowIndex = i * this.stride;
                int kmax = i < j ? i : j;
                double realS = 0.0;
                double imgS = 0.0;
                for (int k = 0; k < kmax; ++k) {
                    double realD = this.dataLU[rowIndex + k * 2];
                    double imgD = this.dataLU[rowIndex + k * 2 + 1];
                    double realCol = LUcolj[k * 2];
                    double imgCol = LUcolj[k * 2 + 1];
                    realS += realD * realCol - imgD * imgCol;
                    imgS += realD * imgCol + imgD * realCol;
                }
                int n = i * 2;
                double d = LUcolj[n] - realS;
                LUcolj[n] = d;
                this.dataLU[rowIndex + j * 2] = d;
                int n2 = i * 2 + 1;
                double d2 = LUcolj[n2] - imgS;
                LUcolj[n2] = d2;
                this.dataLU[rowIndex + j * 2 + 1] = d2;
            }
            int p = j;
            double max = LUDecompositionAlt_ZDRM.mag(LUcolj, p * 2);
            for (int i2 = j + 1; i2 < this.m; ++i2) {
                double v = LUDecompositionAlt_ZDRM.mag(LUcolj, i2 * 2);
                if (!(v > max)) continue;
                p = i2;
                max = v;
            }
            if (p != j) {
                int rowP = p * this.stride;
                int rowJ = j * this.stride;
                int endP = rowP + this.stride;
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
            if (j >= this.m) continue;
            double realLujj = this.dataLU[j * this.stride + j * 2];
            double imgLujj = this.dataLU[j * this.stride + j * 2 + 1];
            double magLujj = realLujj * realLujj + imgLujj * imgLujj;
            if (realLujj == 0.0 && imgLujj == 0.0) continue;
            for (int i3 = j + 1; i3 < this.m; ++i3) {
                double realLU = this.dataLU[i3 * this.stride + j * 2];
                double imagLU = this.dataLU[i3 * this.stride + j * 2 + 1];
                this.dataLU[i3 * this.stride + j * 2] = (realLU * realLujj + imagLU * imgLujj) / magLujj;
                this.dataLU[i3 * this.stride + j * 2 + 1] = (imagLU * realLujj - realLU * imgLujj) / magLujj;
            }
        }
        return true;
    }

    private static double mag(double[] d, int index) {
        double r = d[index];
        double i = d[index + 1];
        return r * r + i * i;
    }
}

