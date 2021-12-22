/*
 * Decompiled with CFR 0.150.
 */
package org.ejml.dense.row;

import java.util.Random;
import org.ejml.data.BMatrixRMaj;
import org.ejml.data.DMatrixD1;
import org.ejml.data.DMatrixRMaj;
import org.ejml.dense.row.CommonOps_DDRM;
import org.ejml.dense.row.NormOps_DDRM;
import org.ejml.dense.row.mult.SubmatrixOps_DDRM;
import org.ejml.dense.row.mult.VectorVectorMult_DDRM;

public class RandomMatrices_DDRM {
    public static DMatrixRMaj[] span(int dimen, int numVectors, Random rand) {
        if (dimen < numVectors) {
            throw new IllegalArgumentException("The number of vectors must be less than or equal to the dimension");
        }
        DMatrixRMaj[] u = new DMatrixRMaj[numVectors];
        u[0] = RandomMatrices_DDRM.rectangle(dimen, 1, -1.0, 1.0, rand);
        NormOps_DDRM.normalizeF(u[0]);
        for (int i = 1; i < numVectors; ++i) {
            DMatrixRMaj a = new DMatrixRMaj(dimen, 1);
            DMatrixRMaj r = RandomMatrices_DDRM.rectangle(dimen, 1, -1.0, 1.0, rand);
            for (int j = 0; j < i; ++j) {
                a.setTo(r);
                VectorVectorMult_DDRM.householder(-2.0, u[j], r, a);
                CommonOps_DDRM.add(r, a, a);
                CommonOps_DDRM.scale(0.5, a);
                DMatrixRMaj t = a;
                a = r;
                r = t;
                double val = NormOps_DDRM.normF(r);
                if (val == 0.0 || Double.isNaN(val) || Double.isInfinite(val)) {
                    throw new RuntimeException("Failed sanity check");
                }
                CommonOps_DDRM.divide(r, val);
            }
            u[i] = r;
        }
        return u;
    }

    public static DMatrixRMaj insideSpan(DMatrixRMaj[] span, double min, double max, Random rand) {
        DMatrixRMaj A = new DMatrixRMaj(span.length, 1);
        DMatrixRMaj B = new DMatrixRMaj(span[0].getNumElements(), 1);
        for (int i = 0; i < span.length; ++i) {
            B.setTo(span[i]);
            double val = rand.nextDouble() * (max - min) + min;
            CommonOps_DDRM.scale(val, B);
            CommonOps_DDRM.add(A, B, A);
        }
        return A;
    }

    public static DMatrixRMaj orthogonal(int numRows, int numCols, Random rand) {
        if (numRows < numCols) {
            throw new IllegalArgumentException("The number of rows must be more than or equal to the number of columns");
        }
        DMatrixRMaj[] u = RandomMatrices_DDRM.span(numRows, numCols, rand);
        DMatrixRMaj ret = new DMatrixRMaj(numRows, numCols);
        for (int i = 0; i < numCols; ++i) {
            SubmatrixOps_DDRM.setSubMatrix(u[i], ret, 0, 0, 0, i, numRows, 1);
        }
        return ret;
    }

    public static DMatrixRMaj diagonal(int N, double min, double max, Random rand) {
        return RandomMatrices_DDRM.diagonal(N, N, min, max, rand);
    }

    public static DMatrixRMaj diagonal(int numRows, int numCols, double min, double max, Random rand) {
        if (max < min) {
            throw new IllegalArgumentException("The max must be >= the min");
        }
        DMatrixRMaj ret = new DMatrixRMaj(numRows, numCols);
        int N = Math.min(numRows, numCols);
        double r = max - min;
        for (int i = 0; i < N; ++i) {
            ret.set(i, i, rand.nextDouble() * r + min);
        }
        return ret;
    }

    public static DMatrixRMaj singular(int numRows, int numCols, Random rand, double ... sv) {
        DMatrixRMaj S;
        DMatrixRMaj V;
        DMatrixRMaj U;
        if (numRows > numCols) {
            U = RandomMatrices_DDRM.orthogonal(numRows, numCols, rand);
            V = RandomMatrices_DDRM.orthogonal(numCols, numCols, rand);
            S = new DMatrixRMaj(numCols, numCols);
        } else {
            U = RandomMatrices_DDRM.orthogonal(numRows, numRows, rand);
            V = RandomMatrices_DDRM.orthogonal(numCols, numCols, rand);
            S = new DMatrixRMaj(numRows, numCols);
        }
        int min = Math.min(numRows, numCols);
        min = Math.min(min, sv.length);
        for (int i = 0; i < min; ++i) {
            S.set(i, i, sv[i]);
        }
        DMatrixRMaj tmp = new DMatrixRMaj(numRows, numCols);
        CommonOps_DDRM.mult(U, S, tmp);
        S.reshape(numRows, numCols);
        CommonOps_DDRM.multTransB(tmp, V, S);
        return S;
    }

    public static DMatrixRMaj symmetricWithEigenvalues(int num, Random rand, double ... eigenvalues) {
        DMatrixRMaj V = RandomMatrices_DDRM.orthogonal(num, num, rand);
        DMatrixRMaj D = CommonOps_DDRM.diag(eigenvalues);
        DMatrixRMaj temp = new DMatrixRMaj(num, num);
        CommonOps_DDRM.mult(V, D, temp);
        CommonOps_DDRM.multTransB(temp, V, D);
        return D;
    }

    public static DMatrixRMaj rectangle(int numRow, int numCol, Random rand) {
        DMatrixRMaj mat = new DMatrixRMaj(numRow, numCol);
        RandomMatrices_DDRM.fillUniform(mat, 0.0, 1.0, rand);
        return mat;
    }

    public static BMatrixRMaj randomBinary(int numRow, int numCol, Random rand) {
        BMatrixRMaj mat = new BMatrixRMaj(numRow, numCol);
        RandomMatrices_DDRM.setRandomB(mat, rand);
        return mat;
    }

    public static void addUniform(DMatrixRMaj A, double min, double max, Random rand) {
        double[] d = A.getData();
        int size = A.getNumElements();
        double r = max - min;
        int i = 0;
        while (i < size) {
            int n = i++;
            d[n] = d[n] + (r * rand.nextDouble() + min);
        }
    }

    public static DMatrixRMaj rectangle(int numRow, int numCol, double min, double max, Random rand) {
        DMatrixRMaj mat = new DMatrixRMaj(numRow, numCol);
        RandomMatrices_DDRM.fillUniform(mat, min, max, rand);
        return mat;
    }

    public static void fillUniform(DMatrixRMaj mat, Random rand) {
        RandomMatrices_DDRM.fillUniform(mat, 0.0, 1.0, rand);
    }

    public static void fillUniform(DMatrixD1 mat, double min, double max, Random rand) {
        double[] d = mat.getData();
        int size = mat.getNumElements();
        double r = max - min;
        for (int i = 0; i < size; ++i) {
            d[i] = r * rand.nextDouble() + min;
        }
    }

    public static void setRandomB(BMatrixRMaj mat, Random rand) {
        boolean[] d = mat.data;
        int size = mat.getNumElements();
        for (int i = 0; i < size; ++i) {
            d[i] = rand.nextBoolean();
        }
    }

    public static DMatrixRMaj rectangleGaussian(int numRow, int numCol, double mean, double stdev, Random rand) {
        DMatrixRMaj m = new DMatrixRMaj(numRow, numCol);
        RandomMatrices_DDRM.fillGaussian(m, mean, stdev, rand);
        return m;
    }

    public static void fillGaussian(DMatrixD1 mat, double mean, double stdev, Random rand) {
        double[] d = mat.getData();
        int size = mat.getNumElements();
        for (int i = 0; i < size; ++i) {
            d[i] = mean + stdev * rand.nextGaussian();
        }
    }

    public static DMatrixRMaj symmetricPosDef(int width, Random rand) {
        int i;
        DMatrixRMaj a = new DMatrixRMaj(width, 1);
        DMatrixRMaj b = new DMatrixRMaj(width, width);
        for (i = 0; i < width; ++i) {
            a.set(i, 0, rand.nextDouble());
        }
        CommonOps_DDRM.multTransB(a, a, b);
        for (i = 0; i < width; ++i) {
            b.add(i, i, 1.0);
        }
        return b;
    }

    public static DMatrixRMaj symmetric(int length, double min, double max, Random rand) {
        DMatrixRMaj A = new DMatrixRMaj(length, length);
        RandomMatrices_DDRM.symmetric(A, min, max, rand);
        return A;
    }

    public static void symmetric(DMatrixRMaj A, double min, double max, Random rand) {
        if (A.numRows != A.numCols) {
            throw new IllegalArgumentException("A must be a square matrix");
        }
        double range = max - min;
        int length = A.numRows;
        for (int i = 0; i < length; ++i) {
            for (int j = i; j < length; ++j) {
                double val = rand.nextDouble() * range + min;
                A.set(i, j, val);
                A.set(j, i, val);
            }
        }
    }

    public static DMatrixRMaj triangularUpper(int dimen, int hessenberg, double min, double max, Random rand) {
        if (hessenberg < 0) {
            throw new RuntimeException("hessenberg must be more than or equal to 0");
        }
        double range = max - min;
        DMatrixRMaj A = new DMatrixRMaj(dimen, dimen);
        for (int i = 0; i < dimen; ++i) {
            int start;
            for (int j = start = i <= hessenberg ? 0 : i - hessenberg; j < dimen; ++j) {
                A.set(i, j, rand.nextDouble() * range + min);
            }
        }
        return A;
    }

    public static DMatrixRMaj triangularLower(int dimen, int hessenberg, double min, double max, Random rand) {
        if (hessenberg < 0) {
            throw new RuntimeException("hessenberg must be more than or equal to 0");
        }
        double range = max - min;
        DMatrixRMaj A = new DMatrixRMaj(dimen, dimen);
        for (int i = 0; i < dimen; ++i) {
            int end = Math.min(dimen, i + hessenberg + 1);
            for (int j = 0; j < end; ++j) {
                A.set(i, j, rand.nextDouble() * range + min);
            }
        }
        return A;
    }
}

