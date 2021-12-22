/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  org.jetbrains.annotations.Nullable
 */
package org.ejml.dense.row.mult;

import org.ejml.data.CMatrixRMaj;
import org.ejml.data.Complex_F32;
import org.jetbrains.annotations.Nullable;

public class VectorVectorMult_CDRM {
    public static Complex_F32 innerProd(CMatrixRMaj x, CMatrixRMaj y, @Nullable Complex_F32 output) {
        if (output == null) {
            output = new Complex_F32();
        } else {
            output.imaginary = 0.0f;
            output.real = 0.0f;
        }
        int m = x.getDataLength();
        for (int i = 0; i < m; i += 2) {
            float realX = x.data[i];
            float imagX = x.data[i + 1];
            float realY = y.data[i];
            float imagY = y.data[i + 1];
            output.real += realX * realY - imagX * imagY;
            output.imaginary += realX * imagY + imagX * realY;
        }
        return output;
    }

    public static Complex_F32 innerProdH(CMatrixRMaj x, CMatrixRMaj y, @Nullable Complex_F32 output) {
        if (output == null) {
            output = new Complex_F32();
        } else {
            output.imaginary = 0.0f;
            output.real = 0.0f;
        }
        int m = x.getDataLength();
        for (int i = 0; i < m; i += 2) {
            float realX = x.data[i];
            float imagX = x.data[i + 1];
            float realY = y.data[i];
            float imagY = -y.data[i + 1];
            output.real += realX * realY - imagX * imagY;
            output.imaginary += realX * imagY + imagX * realY;
        }
        return output;
    }

    public static void outerProd(CMatrixRMaj x, CMatrixRMaj y, CMatrixRMaj A) {
        int m = A.numRows;
        int n = A.numCols;
        int index = 0;
        for (int i = 0; i < m; ++i) {
            float realX = x.data[i * 2];
            float imagX = x.data[i * 2 + 1];
            int indexY = 0;
            for (int j = 0; j < n; ++j) {
                float realY = y.data[indexY++];
                float imagY = y.data[indexY++];
                A.data[index++] = realX * realY - imagX * imagY;
                A.data[index++] = realX * imagY + imagX * realY;
            }
        }
    }

    public static void outerProdH(CMatrixRMaj x, CMatrixRMaj y, CMatrixRMaj A) {
        int m = A.numRows;
        int n = A.numCols;
        int index = 0;
        for (int i = 0; i < m; ++i) {
            float realX = x.data[i * 2];
            float imagX = x.data[i * 2 + 1];
            int indexY = 0;
            for (int j = 0; j < n; ++j) {
                float realY = y.data[indexY++];
                float imagY = -y.data[indexY++];
                A.data[index++] = realX * realY - imagX * imagY;
                A.data[index++] = realX * imagY + imagX * realY;
            }
        }
    }
}

