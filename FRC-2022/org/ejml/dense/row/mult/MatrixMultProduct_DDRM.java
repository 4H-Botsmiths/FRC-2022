/*
 * Decompiled with CFR 0.150.
 */
package org.ejml.dense.row.mult;

import java.util.Arrays;
import org.ejml.data.DMatrix1Row;

public class MatrixMultProduct_DDRM {
    public static void outer(DMatrix1Row a, DMatrix1Row c) {
        for (int i = 0; i < a.numRows; ++i) {
            int indexC1;
            int indexC2 = indexC1 = i * c.numCols + i;
            int j = i;
            while (j < a.numRows) {
                int indexA = i * a.numCols;
                int indexB = j * a.numCols;
                double sum = 0.0;
                int end = indexA + a.numCols;
                while (indexA < end) {
                    sum += a.data[indexA] * a.data[indexB];
                    ++indexA;
                    ++indexB;
                }
                int n = indexC1++;
                double d = sum;
                c.data[n] = d;
                c.data[indexC2] = d;
                ++j;
                indexC2 += c.numCols;
            }
        }
    }

    public static void inner_small(DMatrix1Row a, DMatrix1Row c) {
        for (int i = 0; i < a.numCols; ++i) {
            for (int j = i; j < a.numCols; ++j) {
                int indexC1 = i * c.numCols + j;
                int indexC2 = j * c.numCols + i;
                int indexA = i;
                int indexB = j;
                double sum = 0.0;
                int end = indexA + a.numRows * a.numCols;
                while (indexA < end) {
                    sum += a.data[indexA] * a.data[indexB];
                    indexA += a.numCols;
                    indexB += a.numCols;
                }
                c.data[indexC1] = c.data[indexC2] = sum;
            }
        }
    }

    public static void inner_reorder(DMatrix1Row a, DMatrix1Row c) {
        for (int i = 0; i < a.numCols; ++i) {
            int indexC = i * c.numCols + i;
            double valAi = a.data[i];
            for (int j = i; j < a.numCols; ++j) {
                c.data[indexC++] = valAi * a.data[j];
            }
            for (int k = 1; k < a.numRows; ++k) {
                indexC = i * c.numCols + i;
                int indexB = k * a.numCols + i;
                valAi = a.data[indexB];
                for (int j = i; j < a.numCols; ++j) {
                    int n = indexC++;
                    c.data[n] = c.data[n] + valAi * a.data[indexB++];
                }
            }
            int indexC2 = indexC = i * c.numCols + i;
            int j = i;
            while (j < a.numCols) {
                c.data[indexC2] = c.data[indexC++];
                ++j;
                indexC2 += c.numCols;
            }
        }
    }

    public static void inner_reorder_upper(DMatrix1Row a, DMatrix1Row c) {
        for (int i = 0; i < a.numCols; ++i) {
            int indexC = i * c.numCols + i;
            double valAi = a.data[i];
            for (int j = i; j < a.numCols; ++j) {
                c.data[indexC++] = valAi * a.data[j];
            }
            for (int k = 1; k < a.numRows; ++k) {
                indexC = i * c.numCols + i;
                int indexB = k * a.numCols + i;
                valAi = a.data[indexB];
                for (int j = i; j < a.numCols; ++j) {
                    int n = indexC++;
                    c.data[n] = c.data[n] + valAi * a.data[indexB++];
                }
            }
        }
    }

    public static void inner_reorder_lower(DMatrix1Row A, DMatrix1Row B) {
        int cols = A.numCols;
        B.reshape(cols, cols);
        Arrays.fill(B.data, 0.0);
        for (int i = 0; i < cols; ++i) {
            for (int j = 0; j <= i; ++j) {
                int n = i * cols + j;
                B.data[n] = B.data[n] + A.data[i] * A.data[j];
            }
            for (int k = 1; k < A.numRows; ++k) {
                int indexRow = k * cols;
                double valI = A.data[i + indexRow];
                int indexB = i * cols;
                for (int j = 0; j <= i; ++j) {
                    int n = indexB++;
                    B.data[n] = B.data[n] + valI * A.data[indexRow++];
                }
            }
        }
    }
}

