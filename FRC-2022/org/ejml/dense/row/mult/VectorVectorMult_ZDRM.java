/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  org.jetbrains.annotations.Nullable
 */
package org.ejml.dense.row.mult;

import org.ejml.data.Complex_F64;
import org.ejml.data.ZMatrixRMaj;
import org.jetbrains.annotations.Nullable;

public class VectorVectorMult_ZDRM {
    public static Complex_F64 innerProd(ZMatrixRMaj x, ZMatrixRMaj y, @Nullable Complex_F64 output) {
        if (output == null) {
            output = new Complex_F64();
        } else {
            output.imaginary = 0.0;
            output.real = 0.0;
        }
        int m = x.getDataLength();
        for (int i = 0; i < m; i += 2) {
            double realX = x.data[i];
            double imagX = x.data[i + 1];
            double realY = y.data[i];
            double imagY = y.data[i + 1];
            output.real += realX * realY - imagX * imagY;
            output.imaginary += realX * imagY + imagX * realY;
        }
        return output;
    }

    public static Complex_F64 innerProdH(ZMatrixRMaj x, ZMatrixRMaj y, @Nullable Complex_F64 output) {
        if (output == null) {
            output = new Complex_F64();
        } else {
            output.imaginary = 0.0;
            output.real = 0.0;
        }
        int m = x.getDataLength();
        for (int i = 0; i < m; i += 2) {
            double realX = x.data[i];
            double imagX = x.data[i + 1];
            double realY = y.data[i];
            double imagY = -y.data[i + 1];
            output.real += realX * realY - imagX * imagY;
            output.imaginary += realX * imagY + imagX * realY;
        }
        return output;
    }

    public static void outerProd(ZMatrixRMaj x, ZMatrixRMaj y, ZMatrixRMaj A) {
        int m = A.numRows;
        int n = A.numCols;
        int index = 0;
        for (int i = 0; i < m; ++i) {
            double realX = x.data[i * 2];
            double imagX = x.data[i * 2 + 1];
            int indexY = 0;
            for (int j = 0; j < n; ++j) {
                double realY = y.data[indexY++];
                double imagY = y.data[indexY++];
                A.data[index++] = realX * realY - imagX * imagY;
                A.data[index++] = realX * imagY + imagX * realY;
            }
        }
    }

    public static void outerProdH(ZMatrixRMaj x, ZMatrixRMaj y, ZMatrixRMaj A) {
        int m = A.numRows;
        int n = A.numCols;
        int index = 0;
        for (int i = 0; i < m; ++i) {
            double realX = x.data[i * 2];
            double imagX = x.data[i * 2 + 1];
            int indexY = 0;
            for (int j = 0; j < n; ++j) {
                double realY = y.data[indexY++];
                double imagY = -y.data[indexY++];
                A.data[index++] = realX * realY - imagX * imagY;
                A.data[index++] = realX * imagY + imagX * realY;
            }
        }
    }
}

