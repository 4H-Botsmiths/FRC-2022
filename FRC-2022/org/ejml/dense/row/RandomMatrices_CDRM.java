/*
 * Decompiled with CFR 0.150.
 */
package org.ejml.dense.row;

import java.util.Random;
import org.ejml.data.CMatrixD1;
import org.ejml.data.CMatrixRMaj;
import org.ejml.dense.row.CommonOps_CDRM;

public class RandomMatrices_CDRM {
    public static CMatrixRMaj rectangle(int numRow, int numCol, Random rand) {
        return RandomMatrices_CDRM.rectangle(numRow, numCol, -1.0f, 1.0f, rand);
    }

    public static CMatrixRMaj rectangle(int numRow, int numCol, float min, float max, Random rand) {
        CMatrixRMaj mat = new CMatrixRMaj(numRow, numCol);
        RandomMatrices_CDRM.fillUniform(mat, min, max, rand);
        return mat;
    }

    public static void fillUniform(CMatrixRMaj mat, Random rand) {
        RandomMatrices_CDRM.fillUniform(mat, 0.0f, 1.0f, rand);
    }

    public static void fillUniform(CMatrixD1 mat, float min, float max, Random rand) {
        float[] d = mat.getData();
        int size = mat.getDataLength();
        float r = max - min;
        for (int i = 0; i < size; ++i) {
            d[i] = r * rand.nextFloat() + min;
        }
    }

    public static CMatrixRMaj hermitianPosDef(int width, Random rand) {
        CMatrixRMaj a = RandomMatrices_CDRM.rectangle(width, 1, rand);
        CMatrixRMaj b = new CMatrixRMaj(1, width);
        CMatrixRMaj c = new CMatrixRMaj(width, width);
        CommonOps_CDRM.transposeConjugate(a, b);
        CommonOps_CDRM.mult(a, b, c);
        for (int i = 0; i < width; ++i) {
            int n = 2 * (i * width + i);
            c.data[n] = c.data[n] + 1.0f;
        }
        return c;
    }

    public static CMatrixRMaj hermitian(int length, float min, float max, Random rand) {
        CMatrixRMaj A = new CMatrixRMaj(length, length);
        RandomMatrices_CDRM.fillHermitian(A, min, max, rand);
        return A;
    }

    public static void fillHermitian(CMatrixRMaj A, float min, float max, Random rand) {
        if (A.numRows != A.numCols) {
            throw new IllegalArgumentException("A must be a square matrix");
        }
        float range = max - min;
        int length = A.numRows;
        for (int i = 0; i < length; ++i) {
            A.set(i, i, rand.nextFloat() * range + min, 0.0f);
            for (int j = i + 1; j < length; ++j) {
                float real = rand.nextFloat() * range + min;
                float imaginary = rand.nextFloat() * range + min;
                A.set(i, j, real, imaginary);
                A.set(j, i, real, -imaginary);
            }
        }
    }
}

