/*
 * Decompiled with CFR 0.150.
 */
package org.ejml.dense.row;

import java.util.Random;
import org.ejml.data.ZMatrixD1;
import org.ejml.data.ZMatrixRMaj;
import org.ejml.dense.row.CommonOps_ZDRM;

public class RandomMatrices_ZDRM {
    public static ZMatrixRMaj rectangle(int numRow, int numCol, Random rand) {
        return RandomMatrices_ZDRM.rectangle(numRow, numCol, -1.0, 1.0, rand);
    }

    public static ZMatrixRMaj rectangle(int numRow, int numCol, double min, double max, Random rand) {
        ZMatrixRMaj mat = new ZMatrixRMaj(numRow, numCol);
        RandomMatrices_ZDRM.fillUniform(mat, min, max, rand);
        return mat;
    }

    public static void fillUniform(ZMatrixRMaj mat, Random rand) {
        RandomMatrices_ZDRM.fillUniform(mat, 0.0, 1.0, rand);
    }

    public static void fillUniform(ZMatrixD1 mat, double min, double max, Random rand) {
        double[] d = mat.getData();
        int size = mat.getDataLength();
        double r = max - min;
        for (int i = 0; i < size; ++i) {
            d[i] = r * rand.nextDouble() + min;
        }
    }

    public static ZMatrixRMaj hermitianPosDef(int width, Random rand) {
        ZMatrixRMaj a = RandomMatrices_ZDRM.rectangle(width, 1, rand);
        ZMatrixRMaj b = new ZMatrixRMaj(1, width);
        ZMatrixRMaj c = new ZMatrixRMaj(width, width);
        CommonOps_ZDRM.transposeConjugate(a, b);
        CommonOps_ZDRM.mult(a, b, c);
        for (int i = 0; i < width; ++i) {
            int n = 2 * (i * width + i);
            c.data[n] = c.data[n] + 1.0;
        }
        return c;
    }

    public static ZMatrixRMaj hermitian(int length, double min, double max, Random rand) {
        ZMatrixRMaj A = new ZMatrixRMaj(length, length);
        RandomMatrices_ZDRM.fillHermitian(A, min, max, rand);
        return A;
    }

    public static void fillHermitian(ZMatrixRMaj A, double min, double max, Random rand) {
        if (A.numRows != A.numCols) {
            throw new IllegalArgumentException("A must be a square matrix");
        }
        double range = max - min;
        int length = A.numRows;
        for (int i = 0; i < length; ++i) {
            A.set(i, i, rand.nextDouble() * range + min, 0.0);
            for (int j = i + 1; j < length; ++j) {
                double real = rand.nextDouble() * range + min;
                double imaginary = rand.nextDouble() * range + min;
                A.set(i, j, real, imaginary);
                A.set(j, i, real, -imaginary);
            }
        }
    }
}

