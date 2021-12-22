/*
 * Decompiled with CFR 0.150.
 */
package org.ejml.dense.row;

import java.util.Random;
import org.ejml.data.BMatrixRMaj;
import org.ejml.data.FMatrixD1;
import org.ejml.data.FMatrixRMaj;
import org.ejml.dense.row.CommonOps_FDRM;
import org.ejml.dense.row.NormOps_FDRM;
import org.ejml.dense.row.mult.SubmatrixOps_FDRM;
import org.ejml.dense.row.mult.VectorVectorMult_FDRM;

public class RandomMatrices_FDRM {
    public static FMatrixRMaj[] span(int dimen, int numVectors, Random rand) {
        if (dimen < numVectors) {
            throw new IllegalArgumentException("The number of vectors must be less than or equal to the dimension");
        }
        FMatrixRMaj[] u = new FMatrixRMaj[numVectors];
        u[0] = RandomMatrices_FDRM.rectangle(dimen, 1, -1.0f, 1.0f, rand);
        NormOps_FDRM.normalizeF(u[0]);
        for (int i = 1; i < numVectors; ++i) {
            FMatrixRMaj a = new FMatrixRMaj(dimen, 1);
            FMatrixRMaj r = RandomMatrices_FDRM.rectangle(dimen, 1, -1.0f, 1.0f, rand);
            for (int j = 0; j < i; ++j) {
                a.setTo(r);
                VectorVectorMult_FDRM.householder(-2.0f, u[j], r, a);
                CommonOps_FDRM.add(r, a, a);
                CommonOps_FDRM.scale(0.5f, a);
                FMatrixRMaj t = a;
                a = r;
                r = t;
                float val = NormOps_FDRM.normF(r);
                if (val == 0.0f || Float.isNaN(val) || Float.isInfinite(val)) {
                    throw new RuntimeException("Failed sanity check");
                }
                CommonOps_FDRM.divide(r, val);
            }
            u[i] = r;
        }
        return u;
    }

    public static FMatrixRMaj insideSpan(FMatrixRMaj[] span, float min, float max, Random rand) {
        FMatrixRMaj A = new FMatrixRMaj(span.length, 1);
        FMatrixRMaj B = new FMatrixRMaj(span[0].getNumElements(), 1);
        for (int i = 0; i < span.length; ++i) {
            B.setTo(span[i]);
            float val = rand.nextFloat() * (max - min) + min;
            CommonOps_FDRM.scale(val, B);
            CommonOps_FDRM.add(A, B, A);
        }
        return A;
    }

    public static FMatrixRMaj orthogonal(int numRows, int numCols, Random rand) {
        if (numRows < numCols) {
            throw new IllegalArgumentException("The number of rows must be more than or equal to the number of columns");
        }
        FMatrixRMaj[] u = RandomMatrices_FDRM.span(numRows, numCols, rand);
        FMatrixRMaj ret = new FMatrixRMaj(numRows, numCols);
        for (int i = 0; i < numCols; ++i) {
            SubmatrixOps_FDRM.setSubMatrix(u[i], ret, 0, 0, 0, i, numRows, 1);
        }
        return ret;
    }

    public static FMatrixRMaj diagonal(int N, float min, float max, Random rand) {
        return RandomMatrices_FDRM.diagonal(N, N, min, max, rand);
    }

    public static FMatrixRMaj diagonal(int numRows, int numCols, float min, float max, Random rand) {
        if (max < min) {
            throw new IllegalArgumentException("The max must be >= the min");
        }
        FMatrixRMaj ret = new FMatrixRMaj(numRows, numCols);
        int N = Math.min(numRows, numCols);
        float r = max - min;
        for (int i = 0; i < N; ++i) {
            ret.set(i, i, rand.nextFloat() * r + min);
        }
        return ret;
    }

    public static FMatrixRMaj singular(int numRows, int numCols, Random rand, float ... sv) {
        FMatrixRMaj S;
        FMatrixRMaj V;
        FMatrixRMaj U;
        if (numRows > numCols) {
            U = RandomMatrices_FDRM.orthogonal(numRows, numCols, rand);
            V = RandomMatrices_FDRM.orthogonal(numCols, numCols, rand);
            S = new FMatrixRMaj(numCols, numCols);
        } else {
            U = RandomMatrices_FDRM.orthogonal(numRows, numRows, rand);
            V = RandomMatrices_FDRM.orthogonal(numCols, numCols, rand);
            S = new FMatrixRMaj(numRows, numCols);
        }
        int min = Math.min(numRows, numCols);
        min = Math.min(min, sv.length);
        for (int i = 0; i < min; ++i) {
            S.set(i, i, sv[i]);
        }
        FMatrixRMaj tmp = new FMatrixRMaj(numRows, numCols);
        CommonOps_FDRM.mult(U, S, tmp);
        S.reshape(numRows, numCols);
        CommonOps_FDRM.multTransB(tmp, V, S);
        return S;
    }

    public static FMatrixRMaj symmetricWithEigenvalues(int num, Random rand, float ... eigenvalues) {
        FMatrixRMaj V = RandomMatrices_FDRM.orthogonal(num, num, rand);
        FMatrixRMaj D = CommonOps_FDRM.diag(eigenvalues);
        FMatrixRMaj temp = new FMatrixRMaj(num, num);
        CommonOps_FDRM.mult(V, D, temp);
        CommonOps_FDRM.multTransB(temp, V, D);
        return D;
    }

    public static FMatrixRMaj rectangle(int numRow, int numCol, Random rand) {
        FMatrixRMaj mat = new FMatrixRMaj(numRow, numCol);
        RandomMatrices_FDRM.fillUniform(mat, 0.0f, 1.0f, rand);
        return mat;
    }

    public static BMatrixRMaj randomBinary(int numRow, int numCol, Random rand) {
        BMatrixRMaj mat = new BMatrixRMaj(numRow, numCol);
        RandomMatrices_FDRM.setRandomB(mat, rand);
        return mat;
    }

    public static void addUniform(FMatrixRMaj A, float min, float max, Random rand) {
        float[] d = A.getData();
        int size = A.getNumElements();
        float r = max - min;
        int i = 0;
        while (i < size) {
            int n = i++;
            d[n] = d[n] + (r * rand.nextFloat() + min);
        }
    }

    public static FMatrixRMaj rectangle(int numRow, int numCol, float min, float max, Random rand) {
        FMatrixRMaj mat = new FMatrixRMaj(numRow, numCol);
        RandomMatrices_FDRM.fillUniform(mat, min, max, rand);
        return mat;
    }

    public static void fillUniform(FMatrixRMaj mat, Random rand) {
        RandomMatrices_FDRM.fillUniform(mat, 0.0f, 1.0f, rand);
    }

    public static void fillUniform(FMatrixD1 mat, float min, float max, Random rand) {
        float[] d = mat.getData();
        int size = mat.getNumElements();
        float r = max - min;
        for (int i = 0; i < size; ++i) {
            d[i] = r * rand.nextFloat() + min;
        }
    }

    public static void setRandomB(BMatrixRMaj mat, Random rand) {
        boolean[] d = mat.data;
        int size = mat.getNumElements();
        for (int i = 0; i < size; ++i) {
            d[i] = rand.nextBoolean();
        }
    }

    public static FMatrixRMaj rectangleGaussian(int numRow, int numCol, float mean, float stdev, Random rand) {
        FMatrixRMaj m = new FMatrixRMaj(numRow, numCol);
        RandomMatrices_FDRM.fillGaussian(m, mean, stdev, rand);
        return m;
    }

    public static void fillGaussian(FMatrixD1 mat, float mean, float stdev, Random rand) {
        float[] d = mat.getData();
        int size = mat.getNumElements();
        for (int i = 0; i < size; ++i) {
            d[i] = mean + stdev * (float)rand.nextGaussian();
        }
    }

    public static FMatrixRMaj symmetricPosDef(int width, Random rand) {
        int i;
        FMatrixRMaj a = new FMatrixRMaj(width, 1);
        FMatrixRMaj b = new FMatrixRMaj(width, width);
        for (i = 0; i < width; ++i) {
            a.set(i, 0, rand.nextFloat());
        }
        CommonOps_FDRM.multTransB(a, a, b);
        for (i = 0; i < width; ++i) {
            b.add(i, i, 1.0f);
        }
        return b;
    }

    public static FMatrixRMaj symmetric(int length, float min, float max, Random rand) {
        FMatrixRMaj A = new FMatrixRMaj(length, length);
        RandomMatrices_FDRM.symmetric(A, min, max, rand);
        return A;
    }

    public static void symmetric(FMatrixRMaj A, float min, float max, Random rand) {
        if (A.numRows != A.numCols) {
            throw new IllegalArgumentException("A must be a square matrix");
        }
        float range = max - min;
        int length = A.numRows;
        for (int i = 0; i < length; ++i) {
            for (int j = i; j < length; ++j) {
                float val = rand.nextFloat() * range + min;
                A.set(i, j, val);
                A.set(j, i, val);
            }
        }
    }

    public static FMatrixRMaj triangularUpper(int dimen, int hessenberg, float min, float max, Random rand) {
        if (hessenberg < 0) {
            throw new RuntimeException("hessenberg must be more than or equal to 0");
        }
        float range = max - min;
        FMatrixRMaj A = new FMatrixRMaj(dimen, dimen);
        for (int i = 0; i < dimen; ++i) {
            int start;
            for (int j = start = i <= hessenberg ? 0 : i - hessenberg; j < dimen; ++j) {
                A.set(i, j, rand.nextFloat() * range + min);
            }
        }
        return A;
    }

    public static FMatrixRMaj triangularLower(int dimen, int hessenberg, float min, float max, Random rand) {
        if (hessenberg < 0) {
            throw new RuntimeException("hessenberg must be more than or equal to 0");
        }
        float range = max - min;
        FMatrixRMaj A = new FMatrixRMaj(dimen, dimen);
        for (int i = 0; i < dimen; ++i) {
            int end = Math.min(dimen, i + hessenberg + 1);
            for (int j = 0; j < end; ++j) {
                A.set(i, j, rand.nextFloat() * range + min);
            }
        }
        return A;
    }
}

