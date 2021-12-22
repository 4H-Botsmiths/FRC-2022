/*
 * Decompiled with CFR 0.150.
 */
package org.ejml.dense.row.mult;

import org.ejml.data.FMatrix1Row;
import org.ejml.data.FMatrixD1;
import org.ejml.data.FMatrixRMaj;

public class VectorVectorMult_FDRM {
    public static float innerProd(FMatrixD1 x, FMatrixD1 y) {
        int m = x.getNumElements();
        float total = 0.0f;
        for (int i = 0; i < m; ++i) {
            total += x.get(i) * y.get(i);
        }
        return total;
    }

    public static float innerProdA(FMatrixD1 x, FMatrixD1 A, FMatrixD1 y) {
        int n = A.numRows;
        int m = A.numCols;
        if (x.getNumElements() != n) {
            throw new IllegalArgumentException("Unexpected number of elements in x");
        }
        if (y.getNumElements() != m) {
            throw new IllegalArgumentException("Unexpected number of elements in y");
        }
        float result = 0.0f;
        for (int i = 0; i < m; ++i) {
            float total = 0.0f;
            for (int j = 0; j < n; ++j) {
                total += x.get(j) * A.unsafe_get(j, i);
            }
            result += total * y.get(i);
        }
        return result;
    }

    public static float innerProdTranA(FMatrixD1 x, FMatrixD1 A, FMatrixD1 y) {
        int n = A.numRows;
        if (n != A.numCols) {
            throw new IllegalArgumentException("A must be square");
        }
        if (x.getNumElements() != n) {
            throw new IllegalArgumentException("Unexpected number of elements in x");
        }
        if (y.getNumElements() != n) {
            throw new IllegalArgumentException("Unexpected number of elements in y");
        }
        float result = 0.0f;
        for (int i = 0; i < n; ++i) {
            float total = 0.0f;
            for (int j = 0; j < n; ++j) {
                total += x.get(j) * A.unsafe_get(i, j);
            }
            result += total * y.get(i);
        }
        return result;
    }

    public static void outerProd(FMatrixD1 x, FMatrixD1 y, FMatrix1Row A) {
        int m = A.numRows;
        int n = A.numCols;
        int index = 0;
        for (int i = 0; i < m; ++i) {
            float xdat = x.get(i);
            for (int j = 0; j < n; ++j) {
                A.set(index++, xdat * y.get(j));
            }
        }
    }

    public static void addOuterProd(float gamma, FMatrixD1 x, FMatrixD1 y, FMatrix1Row A) {
        int m = A.numRows;
        int n = A.numCols;
        int index = 0;
        if (gamma == 1.0f) {
            for (int i = 0; i < m; ++i) {
                float xdat = x.get(i);
                for (int j = 0; j < n; ++j) {
                    A.plus(index++, xdat * y.get(j));
                }
            }
        } else {
            for (int i = 0; i < m; ++i) {
                float xdat = x.get(i);
                for (int j = 0; j < n; ++j) {
                    A.plus(index++, gamma * xdat * y.get(j));
                }
            }
        }
    }

    public static void householder(float gamma, FMatrixD1 u, FMatrixD1 x, FMatrixD1 y) {
        int i;
        int n = u.getNumElements();
        float sum = 0.0f;
        for (i = 0; i < n; ++i) {
            sum += u.get(i) * x.get(i);
        }
        for (i = 0; i < n; ++i) {
            y.set(i, x.get(i) + gamma * u.get(i) * sum);
        }
    }

    public static void rank1Update(float gamma, FMatrixRMaj A, FMatrixRMaj u, FMatrixRMaj w, FMatrixRMaj B) {
        int n = u.getNumElements();
        int matrixIndex = 0;
        for (int i = 0; i < n; ++i) {
            float elementU = u.data[i];
            int j = 0;
            while (j < n) {
                B.data[matrixIndex] = A.data[matrixIndex] + gamma * elementU * w.data[j];
                ++j;
                ++matrixIndex;
            }
        }
    }

    public static void rank1Update(float gamma, FMatrixRMaj A, FMatrixRMaj u, FMatrixRMaj w) {
        int n = u.getNumElements();
        int matrixIndex = 0;
        for (int i = 0; i < n; ++i) {
            float elementU = u.data[i];
            for (int j = 0; j < n; ++j) {
                int n2 = matrixIndex++;
                A.data[n2] = A.data[n2] + gamma * elementU * w.data[j];
            }
        }
    }
}

