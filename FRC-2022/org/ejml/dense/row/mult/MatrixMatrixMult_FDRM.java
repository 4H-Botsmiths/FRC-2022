/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  org.jetbrains.annotations.Nullable
 */
package org.ejml.dense.row.mult;

import org.ejml.UtilEjml;
import org.ejml.data.FMatrix1Row;
import org.ejml.dense.row.CommonOps_FDRM;
import org.jetbrains.annotations.Nullable;

public class MatrixMatrixMult_FDRM {
    public static void mult_reorder(FMatrix1Row A, FMatrix1Row B, FMatrix1Row C) {
        UtilEjml.assertTrue(A != C && B != C, "Neither 'A' or 'B' can be the same matrix as 'C'");
        UtilEjml.assertShape(A.numCols, B.numRows, "The 'A' and 'B' matrices do not have compatible dimensions");
        C.reshape(A.numRows, B.numCols);
        if (A.numCols == 0 || A.numRows == 0) {
            CommonOps_FDRM.fill(C, 0.0f);
            return;
        }
        int endOfKLoop = B.numRows * B.numCols;
        for (int i = 0; i < A.numRows; ++i) {
            int indexCbase = i * C.numCols;
            int indexA = i * A.numCols;
            int indexB = 0;
            int indexC = indexCbase;
            int end = indexB + B.numCols;
            float valA = A.data[indexA++];
            while (indexB < end) {
                C.set(indexC++, valA * B.data[indexB++]);
            }
            while (indexB != endOfKLoop) {
                indexC = indexCbase;
                end = indexB + B.numCols;
                valA = A.data[indexA++];
                while (indexB < end) {
                    int n = indexC++;
                    C.data[n] = C.data[n] + valA * B.data[indexB++];
                }
            }
        }
    }

    public static void mult_small(FMatrix1Row A, FMatrix1Row B, FMatrix1Row C) {
        UtilEjml.assertTrue(A != C && B != C, "Neither 'A' or 'B' can be the same matrix as 'C'");
        UtilEjml.assertShape(A.numCols, B.numRows, "The 'A' and 'B' matrices do not have compatible dimensions");
        C.reshape(A.numRows, B.numCols);
        for (int i = 0; i < A.numRows; ++i) {
            int cIndex = i * B.numCols;
            int aIndexStart = i * A.numCols;
            for (int j = 0; j < B.numCols; ++j) {
                float total = 0.0f;
                int indexA = aIndexStart;
                int indexB = j;
                int end = indexA + B.numRows;
                while (indexA < end) {
                    total += A.data[indexA++] * B.data[indexB];
                    indexB += B.numCols;
                }
                C.set(cIndex++, total);
            }
        }
    }

    public static void mult_aux(FMatrix1Row A, FMatrix1Row B, FMatrix1Row C, @Nullable float[] aux) {
        UtilEjml.assertTrue(A != C && B != C, "Neither 'A' or 'B' can be the same matrix as 'C'");
        UtilEjml.assertShape(A.numCols, B.numRows, "The 'A' and 'B' matrices do not have compatible dimensions");
        C.reshape(A.numRows, B.numCols);
        if (aux == null) {
            aux = new float[B.numRows];
        }
        for (int j = 0; j < B.numCols; ++j) {
            for (int k = 0; k < B.numRows; ++k) {
                aux[k] = B.unsafe_get(k, j);
            }
            int indexA = 0;
            for (int i = 0; i < A.numRows; ++i) {
                float total = 0.0f;
                int k = 0;
                while (k < B.numRows) {
                    total += A.data[indexA++] * aux[k++];
                }
                C.set(i * C.numCols + j, total);
            }
        }
    }

    public static void multTransA_reorder(FMatrix1Row A, FMatrix1Row B, FMatrix1Row C) {
        UtilEjml.assertTrue(A != C && B != C, "Neither 'A' or 'B' can be the same matrix as 'C'");
        UtilEjml.assertShape(A.numRows, B.numRows, "The 'A' and 'B' matrices do not have compatible dimensions");
        C.reshape(A.numCols, B.numCols);
        if (A.numCols == 0 || A.numRows == 0) {
            CommonOps_FDRM.fill(C, 0.0f);
            return;
        }
        for (int i = 0; i < A.numCols; ++i) {
            int indexC_start = i * C.numCols;
            float valA = A.data[i];
            int indexB = 0;
            int end = indexB + B.numCols;
            int indexC = indexC_start;
            while (indexB < end) {
                C.set(indexC++, valA * B.data[indexB++]);
            }
            for (int k = 1; k < A.numRows; ++k) {
                valA = A.unsafe_get(k, i);
                end = indexB + B.numCols;
                indexC = indexC_start;
                while (indexB < end) {
                    int n = indexC++;
                    C.data[n] = C.data[n] + valA * B.data[indexB++];
                }
            }
        }
    }

    public static void multTransA_small(FMatrix1Row A, FMatrix1Row B, FMatrix1Row C) {
        UtilEjml.assertTrue(A != C && B != C, "Neither 'A' or 'B' can be the same matrix as 'C'");
        UtilEjml.assertShape(A.numRows, B.numRows, "The 'A' and 'B' matrices do not have compatible dimensions");
        C.reshape(A.numCols, B.numCols);
        for (int i = 0; i < A.numCols; ++i) {
            int cIndex = i * B.numCols;
            for (int j = 0; j < B.numCols; ++j) {
                int indexB;
                int indexA = i;
                int end = indexB + B.numRows * B.numCols;
                float total = 0.0f;
                for (indexB = j; indexB < end; indexB += B.numCols) {
                    total += A.data[indexA] * B.data[indexB];
                    indexA += A.numCols;
                }
                C.set(cIndex++, total);
            }
        }
    }

    public static void multTransAB(FMatrix1Row A, FMatrix1Row B, FMatrix1Row C) {
        UtilEjml.assertTrue(A != C && B != C, "Neither 'A' or 'B' can be the same matrix as 'C'");
        UtilEjml.assertShape(A.numRows, B.numCols, "The 'A' and 'B' matrices do not have compatible dimensions");
        C.reshape(A.numCols, B.numRows);
        for (int i = 0; i < A.numCols; ++i) {
            int cIndex = i * B.numRows;
            int indexB = 0;
            for (int j = 0; j < B.numRows; ++j) {
                int indexA = i;
                int end = indexB + B.numCols;
                float total = 0.0f;
                while (indexB < end) {
                    total += A.data[indexA] * B.data[indexB++];
                    indexA += A.numCols;
                }
                C.set(cIndex++, total);
            }
        }
    }

    public static void multTransAB_aux(FMatrix1Row A, FMatrix1Row B, FMatrix1Row C, @Nullable float[] aux) {
        UtilEjml.assertTrue(A != C && B != C, "Neither 'A' or 'B' can be the same matrix as 'C'");
        UtilEjml.assertShape(A.numRows, B.numCols, "The 'A' and 'B' matrices do not have compatible dimensions");
        C.reshape(A.numCols, B.numRows);
        if (aux == null) {
            aux = new float[A.numRows];
        }
        if (A.numCols == 0 || A.numRows == 0) {
            CommonOps_FDRM.fill(C, 0.0f);
            return;
        }
        int indexC = 0;
        for (int i = 0; i < A.numCols; ++i) {
            for (int k = 0; k < B.numCols; ++k) {
                aux[k] = A.unsafe_get(k, i);
            }
            for (int j = 0; j < B.numRows; ++j) {
                float total = 0.0f;
                for (int k = 0; k < B.numCols; ++k) {
                    total += aux[k] * B.unsafe_get(j, k);
                }
                C.set(indexC++, total);
            }
        }
    }

    public static void multTransB(FMatrix1Row A, FMatrix1Row B, FMatrix1Row C) {
        UtilEjml.assertTrue(A != C && B != C, "Neither 'A' or 'B' can be the same matrix as 'C'");
        UtilEjml.assertShape(A.numCols, B.numCols, "The 'A' and 'B' matrices do not have compatible dimensions");
        C.reshape(A.numRows, B.numRows);
        for (int xA = 0; xA < A.numRows; ++xA) {
            int cIndex = xA * B.numRows;
            int aIndexStart = xA * B.numCols;
            int end = aIndexStart + B.numCols;
            int indexB = 0;
            for (int xB = 0; xB < B.numRows; ++xB) {
                int indexA = aIndexStart;
                float total = 0.0f;
                while (indexA < end) {
                    total += A.data[indexA++] * B.data[indexB++];
                }
                C.set(cIndex++, total);
            }
        }
    }

    public static void multAdd_reorder(FMatrix1Row A, FMatrix1Row B, FMatrix1Row C) {
        UtilEjml.assertTrue(A != C && B != C, "Neither 'A' or 'B' can be the same matrix as 'C'");
        UtilEjml.assertShape(A.numCols, B.numRows, "The 'A' and 'B' matrices do not have compatible dimensions");
        UtilEjml.assertShape(A.numRows == C.numRows && B.numCols == C.numCols, "C is not compatible with A and B");
        if (A.numCols == 0 || A.numRows == 0) {
            return;
        }
        int endOfKLoop = B.numRows * B.numCols;
        for (int i = 0; i < A.numRows; ++i) {
            int indexCbase = i * C.numCols;
            int indexA = i * A.numCols;
            int indexB = 0;
            int indexC = indexCbase;
            int end = indexB + B.numCols;
            float valA = A.data[indexA++];
            while (indexB < end) {
                C.plus(indexC++, valA * B.data[indexB++]);
            }
            while (indexB != endOfKLoop) {
                indexC = indexCbase;
                end = indexB + B.numCols;
                valA = A.data[indexA++];
                while (indexB < end) {
                    int n = indexC++;
                    C.data[n] = C.data[n] + valA * B.data[indexB++];
                }
            }
        }
    }

    public static void multAdd_small(FMatrix1Row A, FMatrix1Row B, FMatrix1Row C) {
        UtilEjml.assertTrue(A != C && B != C, "Neither 'A' or 'B' can be the same matrix as 'C'");
        UtilEjml.assertShape(A.numCols, B.numRows, "The 'A' and 'B' matrices do not have compatible dimensions");
        UtilEjml.assertShape(A.numRows == C.numRows && B.numCols == C.numCols, "C is not compatible with A and B");
        for (int i = 0; i < A.numRows; ++i) {
            int cIndex = i * B.numCols;
            int aIndexStart = i * A.numCols;
            for (int j = 0; j < B.numCols; ++j) {
                float total = 0.0f;
                int indexA = aIndexStart;
                int indexB = j;
                int end = indexA + B.numRows;
                while (indexA < end) {
                    total += A.data[indexA++] * B.data[indexB];
                    indexB += B.numCols;
                }
                C.plus(cIndex++, total);
            }
        }
    }

    public static void multAdd_aux(FMatrix1Row A, FMatrix1Row B, FMatrix1Row C, @Nullable float[] aux) {
        UtilEjml.assertTrue(A != C && B != C, "Neither 'A' or 'B' can be the same matrix as 'C'");
        UtilEjml.assertShape(A.numCols, B.numRows, "The 'A' and 'B' matrices do not have compatible dimensions");
        UtilEjml.assertShape(A.numRows == C.numRows && B.numCols == C.numCols, "C is not compatible with A and B");
        if (aux == null) {
            aux = new float[B.numRows];
        }
        for (int j = 0; j < B.numCols; ++j) {
            for (int k = 0; k < B.numRows; ++k) {
                aux[k] = B.unsafe_get(k, j);
            }
            int indexA = 0;
            for (int i = 0; i < A.numRows; ++i) {
                float total = 0.0f;
                int k = 0;
                while (k < B.numRows) {
                    total += A.data[indexA++] * aux[k++];
                }
                C.plus(i * C.numCols + j, total);
            }
        }
    }

    public static void multAddTransA_reorder(FMatrix1Row A, FMatrix1Row B, FMatrix1Row C) {
        UtilEjml.assertTrue(A != C && B != C, "Neither 'A' or 'B' can be the same matrix as 'C'");
        UtilEjml.assertShape(A.numRows, B.numRows, "The 'A' and 'B' matrices do not have compatible dimensions");
        UtilEjml.assertShape(A.numCols == C.numRows && B.numCols == C.numCols, "C is not compatible with A and B");
        if (A.numCols == 0 || A.numRows == 0) {
            return;
        }
        for (int i = 0; i < A.numCols; ++i) {
            int indexC_start = i * C.numCols;
            float valA = A.data[i];
            int indexB = 0;
            int end = indexB + B.numCols;
            int indexC = indexC_start;
            while (indexB < end) {
                C.plus(indexC++, valA * B.data[indexB++]);
            }
            for (int k = 1; k < A.numRows; ++k) {
                valA = A.unsafe_get(k, i);
                end = indexB + B.numCols;
                indexC = indexC_start;
                while (indexB < end) {
                    int n = indexC++;
                    C.data[n] = C.data[n] + valA * B.data[indexB++];
                }
            }
        }
    }

    public static void multAddTransA_small(FMatrix1Row A, FMatrix1Row B, FMatrix1Row C) {
        UtilEjml.assertTrue(A != C && B != C, "Neither 'A' or 'B' can be the same matrix as 'C'");
        UtilEjml.assertShape(A.numRows, B.numRows, "The 'A' and 'B' matrices do not have compatible dimensions");
        UtilEjml.assertShape(A.numCols == C.numRows && B.numCols == C.numCols, "C is not compatible with A and B");
        for (int i = 0; i < A.numCols; ++i) {
            int cIndex = i * B.numCols;
            for (int j = 0; j < B.numCols; ++j) {
                int indexB;
                int indexA = i;
                int end = indexB + B.numRows * B.numCols;
                float total = 0.0f;
                for (indexB = j; indexB < end; indexB += B.numCols) {
                    total += A.data[indexA] * B.data[indexB];
                    indexA += A.numCols;
                }
                C.plus(cIndex++, total);
            }
        }
    }

    public static void multAddTransAB(FMatrix1Row A, FMatrix1Row B, FMatrix1Row C) {
        UtilEjml.assertTrue(A != C && B != C, "Neither 'A' or 'B' can be the same matrix as 'C'");
        UtilEjml.assertShape(A.numRows, B.numCols, "The 'A' and 'B' matrices do not have compatible dimensions");
        UtilEjml.assertShape(A.numCols == C.numRows && B.numRows == C.numCols, "C is not compatible with A and B");
        for (int i = 0; i < A.numCols; ++i) {
            int cIndex = i * B.numRows;
            int indexB = 0;
            for (int j = 0; j < B.numRows; ++j) {
                int indexA = i;
                int end = indexB + B.numCols;
                float total = 0.0f;
                while (indexB < end) {
                    total += A.data[indexA] * B.data[indexB++];
                    indexA += A.numCols;
                }
                C.plus(cIndex++, total);
            }
        }
    }

    public static void multAddTransAB_aux(FMatrix1Row A, FMatrix1Row B, FMatrix1Row C, @Nullable float[] aux) {
        UtilEjml.assertTrue(A != C && B != C, "Neither 'A' or 'B' can be the same matrix as 'C'");
        UtilEjml.assertShape(A.numRows, B.numCols, "The 'A' and 'B' matrices do not have compatible dimensions");
        UtilEjml.assertShape(A.numCols == C.numRows && B.numRows == C.numCols, "C is not compatible with A and B");
        if (aux == null) {
            aux = new float[A.numRows];
        }
        if (A.numCols == 0 || A.numRows == 0) {
            return;
        }
        int indexC = 0;
        for (int i = 0; i < A.numCols; ++i) {
            for (int k = 0; k < B.numCols; ++k) {
                aux[k] = A.unsafe_get(k, i);
            }
            for (int j = 0; j < B.numRows; ++j) {
                float total = 0.0f;
                for (int k = 0; k < B.numCols; ++k) {
                    total += aux[k] * B.unsafe_get(j, k);
                }
                C.plus(indexC++, total);
            }
        }
    }

    public static void multAddTransB(FMatrix1Row A, FMatrix1Row B, FMatrix1Row C) {
        UtilEjml.assertTrue(A != C && B != C, "Neither 'A' or 'B' can be the same matrix as 'C'");
        UtilEjml.assertShape(A.numCols, B.numCols, "The 'A' and 'B' matrices do not have compatible dimensions");
        UtilEjml.assertShape(A.numRows == C.numRows && B.numRows == C.numCols, "C is not compatible with A and B");
        for (int xA = 0; xA < A.numRows; ++xA) {
            int cIndex = xA * B.numRows;
            int aIndexStart = xA * B.numCols;
            int end = aIndexStart + B.numCols;
            int indexB = 0;
            for (int xB = 0; xB < B.numRows; ++xB) {
                int indexA = aIndexStart;
                float total = 0.0f;
                while (indexA < end) {
                    total += A.data[indexA++] * B.data[indexB++];
                }
                C.plus(cIndex++, total);
            }
        }
    }

    public static void mult_reorder(float alpha, FMatrix1Row A, FMatrix1Row B, FMatrix1Row C) {
        UtilEjml.assertTrue(A != C && B != C, "Neither 'A' or 'B' can be the same matrix as 'C'");
        UtilEjml.assertShape(A.numCols, B.numRows, "The 'A' and 'B' matrices do not have compatible dimensions");
        C.reshape(A.numRows, B.numCols);
        if (A.numCols == 0 || A.numRows == 0) {
            CommonOps_FDRM.fill(C, 0.0f);
            return;
        }
        int endOfKLoop = B.numRows * B.numCols;
        for (int i = 0; i < A.numRows; ++i) {
            int indexCbase = i * C.numCols;
            int indexA = i * A.numCols;
            int indexB = 0;
            int indexC = indexCbase;
            int end = indexB + B.numCols;
            float valA = alpha * A.data[indexA++];
            while (indexB < end) {
                C.set(indexC++, valA * B.data[indexB++]);
            }
            while (indexB != endOfKLoop) {
                indexC = indexCbase;
                end = indexB + B.numCols;
                valA = alpha * A.data[indexA++];
                while (indexB < end) {
                    int n = indexC++;
                    C.data[n] = C.data[n] + valA * B.data[indexB++];
                }
            }
        }
    }

    public static void mult_small(float alpha, FMatrix1Row A, FMatrix1Row B, FMatrix1Row C) {
        UtilEjml.assertTrue(A != C && B != C, "Neither 'A' or 'B' can be the same matrix as 'C'");
        UtilEjml.assertShape(A.numCols, B.numRows, "The 'A' and 'B' matrices do not have compatible dimensions");
        C.reshape(A.numRows, B.numCols);
        for (int i = 0; i < A.numRows; ++i) {
            int cIndex = i * B.numCols;
            int aIndexStart = i * A.numCols;
            for (int j = 0; j < B.numCols; ++j) {
                float total = 0.0f;
                int indexA = aIndexStart;
                int indexB = j;
                int end = indexA + B.numRows;
                while (indexA < end) {
                    total += A.data[indexA++] * B.data[indexB];
                    indexB += B.numCols;
                }
                C.set(cIndex++, alpha * total);
            }
        }
    }

    public static void mult_aux(float alpha, FMatrix1Row A, FMatrix1Row B, FMatrix1Row C, @Nullable float[] aux) {
        UtilEjml.assertTrue(A != C && B != C, "Neither 'A' or 'B' can be the same matrix as 'C'");
        UtilEjml.assertShape(A.numCols, B.numRows, "The 'A' and 'B' matrices do not have compatible dimensions");
        C.reshape(A.numRows, B.numCols);
        if (aux == null) {
            aux = new float[B.numRows];
        }
        for (int j = 0; j < B.numCols; ++j) {
            for (int k = 0; k < B.numRows; ++k) {
                aux[k] = B.unsafe_get(k, j);
            }
            int indexA = 0;
            for (int i = 0; i < A.numRows; ++i) {
                float total = 0.0f;
                int k = 0;
                while (k < B.numRows) {
                    total += A.data[indexA++] * aux[k++];
                }
                C.set(i * C.numCols + j, alpha * total);
            }
        }
    }

    public static void multTransA_reorder(float alpha, FMatrix1Row A, FMatrix1Row B, FMatrix1Row C) {
        UtilEjml.assertTrue(A != C && B != C, "Neither 'A' or 'B' can be the same matrix as 'C'");
        UtilEjml.assertShape(A.numRows, B.numRows, "The 'A' and 'B' matrices do not have compatible dimensions");
        C.reshape(A.numCols, B.numCols);
        if (A.numCols == 0 || A.numRows == 0) {
            CommonOps_FDRM.fill(C, 0.0f);
            return;
        }
        for (int i = 0; i < A.numCols; ++i) {
            int indexC_start = i * C.numCols;
            float valA = alpha * A.data[i];
            int indexB = 0;
            int end = indexB + B.numCols;
            int indexC = indexC_start;
            while (indexB < end) {
                C.set(indexC++, valA * B.data[indexB++]);
            }
            for (int k = 1; k < A.numRows; ++k) {
                valA = alpha * A.unsafe_get(k, i);
                end = indexB + B.numCols;
                indexC = indexC_start;
                while (indexB < end) {
                    int n = indexC++;
                    C.data[n] = C.data[n] + valA * B.data[indexB++];
                }
            }
        }
    }

    public static void multTransA_small(float alpha, FMatrix1Row A, FMatrix1Row B, FMatrix1Row C) {
        UtilEjml.assertTrue(A != C && B != C, "Neither 'A' or 'B' can be the same matrix as 'C'");
        UtilEjml.assertShape(A.numRows, B.numRows, "The 'A' and 'B' matrices do not have compatible dimensions");
        C.reshape(A.numCols, B.numCols);
        for (int i = 0; i < A.numCols; ++i) {
            int cIndex = i * B.numCols;
            for (int j = 0; j < B.numCols; ++j) {
                int indexB;
                int indexA = i;
                int end = indexB + B.numRows * B.numCols;
                float total = 0.0f;
                for (indexB = j; indexB < end; indexB += B.numCols) {
                    total += A.data[indexA] * B.data[indexB];
                    indexA += A.numCols;
                }
                C.set(cIndex++, alpha * total);
            }
        }
    }

    public static void multTransAB(float alpha, FMatrix1Row A, FMatrix1Row B, FMatrix1Row C) {
        UtilEjml.assertTrue(A != C && B != C, "Neither 'A' or 'B' can be the same matrix as 'C'");
        UtilEjml.assertShape(A.numRows, B.numCols, "The 'A' and 'B' matrices do not have compatible dimensions");
        C.reshape(A.numCols, B.numRows);
        for (int i = 0; i < A.numCols; ++i) {
            int cIndex = i * B.numRows;
            int indexB = 0;
            for (int j = 0; j < B.numRows; ++j) {
                int indexA = i;
                int end = indexB + B.numCols;
                float total = 0.0f;
                while (indexB < end) {
                    total += A.data[indexA] * B.data[indexB++];
                    indexA += A.numCols;
                }
                C.set(cIndex++, alpha * total);
            }
        }
    }

    public static void multTransAB_aux(float alpha, FMatrix1Row A, FMatrix1Row B, FMatrix1Row C, @Nullable float[] aux) {
        UtilEjml.assertTrue(A != C && B != C, "Neither 'A' or 'B' can be the same matrix as 'C'");
        UtilEjml.assertShape(A.numRows, B.numCols, "The 'A' and 'B' matrices do not have compatible dimensions");
        C.reshape(A.numCols, B.numRows);
        if (aux == null) {
            aux = new float[A.numRows];
        }
        if (A.numCols == 0 || A.numRows == 0) {
            CommonOps_FDRM.fill(C, 0.0f);
            return;
        }
        int indexC = 0;
        for (int i = 0; i < A.numCols; ++i) {
            for (int k = 0; k < B.numCols; ++k) {
                aux[k] = A.unsafe_get(k, i);
            }
            for (int j = 0; j < B.numRows; ++j) {
                float total = 0.0f;
                for (int k = 0; k < B.numCols; ++k) {
                    total += aux[k] * B.unsafe_get(j, k);
                }
                C.set(indexC++, alpha * total);
            }
        }
    }

    public static void multTransB(float alpha, FMatrix1Row A, FMatrix1Row B, FMatrix1Row C) {
        UtilEjml.assertTrue(A != C && B != C, "Neither 'A' or 'B' can be the same matrix as 'C'");
        UtilEjml.assertShape(A.numCols, B.numCols, "The 'A' and 'B' matrices do not have compatible dimensions");
        C.reshape(A.numRows, B.numRows);
        for (int xA = 0; xA < A.numRows; ++xA) {
            int cIndex = xA * B.numRows;
            int aIndexStart = xA * B.numCols;
            int end = aIndexStart + B.numCols;
            int indexB = 0;
            for (int xB = 0; xB < B.numRows; ++xB) {
                int indexA = aIndexStart;
                float total = 0.0f;
                while (indexA < end) {
                    total += A.data[indexA++] * B.data[indexB++];
                }
                C.set(cIndex++, alpha * total);
            }
        }
    }

    public static void multAdd_reorder(float alpha, FMatrix1Row A, FMatrix1Row B, FMatrix1Row C) {
        UtilEjml.assertTrue(A != C && B != C, "Neither 'A' or 'B' can be the same matrix as 'C'");
        UtilEjml.assertShape(A.numCols, B.numRows, "The 'A' and 'B' matrices do not have compatible dimensions");
        UtilEjml.assertShape(A.numRows == C.numRows && B.numCols == C.numCols, "C is not compatible with A and B");
        if (A.numCols == 0 || A.numRows == 0) {
            return;
        }
        int endOfKLoop = B.numRows * B.numCols;
        for (int i = 0; i < A.numRows; ++i) {
            int indexCbase = i * C.numCols;
            int indexA = i * A.numCols;
            int indexB = 0;
            int indexC = indexCbase;
            int end = indexB + B.numCols;
            float valA = alpha * A.data[indexA++];
            while (indexB < end) {
                C.plus(indexC++, valA * B.data[indexB++]);
            }
            while (indexB != endOfKLoop) {
                indexC = indexCbase;
                end = indexB + B.numCols;
                valA = alpha * A.data[indexA++];
                while (indexB < end) {
                    int n = indexC++;
                    C.data[n] = C.data[n] + valA * B.data[indexB++];
                }
            }
        }
    }

    public static void multAdd_small(float alpha, FMatrix1Row A, FMatrix1Row B, FMatrix1Row C) {
        UtilEjml.assertTrue(A != C && B != C, "Neither 'A' or 'B' can be the same matrix as 'C'");
        UtilEjml.assertShape(A.numCols, B.numRows, "The 'A' and 'B' matrices do not have compatible dimensions");
        UtilEjml.assertShape(A.numRows == C.numRows && B.numCols == C.numCols, "C is not compatible with A and B");
        for (int i = 0; i < A.numRows; ++i) {
            int cIndex = i * B.numCols;
            int aIndexStart = i * A.numCols;
            for (int j = 0; j < B.numCols; ++j) {
                float total = 0.0f;
                int indexA = aIndexStart;
                int indexB = j;
                int end = indexA + B.numRows;
                while (indexA < end) {
                    total += A.data[indexA++] * B.data[indexB];
                    indexB += B.numCols;
                }
                C.plus(cIndex++, alpha * total);
            }
        }
    }

    public static void multAdd_aux(float alpha, FMatrix1Row A, FMatrix1Row B, FMatrix1Row C, @Nullable float[] aux) {
        UtilEjml.assertTrue(A != C && B != C, "Neither 'A' or 'B' can be the same matrix as 'C'");
        UtilEjml.assertShape(A.numCols, B.numRows, "The 'A' and 'B' matrices do not have compatible dimensions");
        UtilEjml.assertShape(A.numRows == C.numRows && B.numCols == C.numCols, "C is not compatible with A and B");
        if (aux == null) {
            aux = new float[B.numRows];
        }
        for (int j = 0; j < B.numCols; ++j) {
            for (int k = 0; k < B.numRows; ++k) {
                aux[k] = B.unsafe_get(k, j);
            }
            int indexA = 0;
            for (int i = 0; i < A.numRows; ++i) {
                float total = 0.0f;
                int k = 0;
                while (k < B.numRows) {
                    total += A.data[indexA++] * aux[k++];
                }
                C.plus(i * C.numCols + j, alpha * total);
            }
        }
    }

    public static void multAddTransA_reorder(float alpha, FMatrix1Row A, FMatrix1Row B, FMatrix1Row C) {
        UtilEjml.assertTrue(A != C && B != C, "Neither 'A' or 'B' can be the same matrix as 'C'");
        UtilEjml.assertShape(A.numRows, B.numRows, "The 'A' and 'B' matrices do not have compatible dimensions");
        UtilEjml.assertShape(A.numCols == C.numRows && B.numCols == C.numCols, "C is not compatible with A and B");
        if (A.numCols == 0 || A.numRows == 0) {
            return;
        }
        for (int i = 0; i < A.numCols; ++i) {
            int indexC_start = i * C.numCols;
            float valA = alpha * A.data[i];
            int indexB = 0;
            int end = indexB + B.numCols;
            int indexC = indexC_start;
            while (indexB < end) {
                C.plus(indexC++, valA * B.data[indexB++]);
            }
            for (int k = 1; k < A.numRows; ++k) {
                valA = alpha * A.unsafe_get(k, i);
                end = indexB + B.numCols;
                indexC = indexC_start;
                while (indexB < end) {
                    int n = indexC++;
                    C.data[n] = C.data[n] + valA * B.data[indexB++];
                }
            }
        }
    }

    public static void multAddTransA_small(float alpha, FMatrix1Row A, FMatrix1Row B, FMatrix1Row C) {
        UtilEjml.assertTrue(A != C && B != C, "Neither 'A' or 'B' can be the same matrix as 'C'");
        UtilEjml.assertShape(A.numRows, B.numRows, "The 'A' and 'B' matrices do not have compatible dimensions");
        UtilEjml.assertShape(A.numCols == C.numRows && B.numCols == C.numCols, "C is not compatible with A and B");
        for (int i = 0; i < A.numCols; ++i) {
            int cIndex = i * B.numCols;
            for (int j = 0; j < B.numCols; ++j) {
                int indexB;
                int indexA = i;
                int end = indexB + B.numRows * B.numCols;
                float total = 0.0f;
                for (indexB = j; indexB < end; indexB += B.numCols) {
                    total += A.data[indexA] * B.data[indexB];
                    indexA += A.numCols;
                }
                C.plus(cIndex++, alpha * total);
            }
        }
    }

    public static void multAddTransAB(float alpha, FMatrix1Row A, FMatrix1Row B, FMatrix1Row C) {
        UtilEjml.assertTrue(A != C && B != C, "Neither 'A' or 'B' can be the same matrix as 'C'");
        UtilEjml.assertShape(A.numRows, B.numCols, "The 'A' and 'B' matrices do not have compatible dimensions");
        UtilEjml.assertShape(A.numCols == C.numRows && B.numRows == C.numCols, "C is not compatible with A and B");
        for (int i = 0; i < A.numCols; ++i) {
            int cIndex = i * B.numRows;
            int indexB = 0;
            for (int j = 0; j < B.numRows; ++j) {
                int indexA = i;
                int end = indexB + B.numCols;
                float total = 0.0f;
                while (indexB < end) {
                    total += A.data[indexA] * B.data[indexB++];
                    indexA += A.numCols;
                }
                C.plus(cIndex++, alpha * total);
            }
        }
    }

    public static void multAddTransAB_aux(float alpha, FMatrix1Row A, FMatrix1Row B, FMatrix1Row C, @Nullable float[] aux) {
        UtilEjml.assertTrue(A != C && B != C, "Neither 'A' or 'B' can be the same matrix as 'C'");
        UtilEjml.assertShape(A.numRows, B.numCols, "The 'A' and 'B' matrices do not have compatible dimensions");
        UtilEjml.assertShape(A.numCols == C.numRows && B.numRows == C.numCols, "C is not compatible with A and B");
        if (aux == null) {
            aux = new float[A.numRows];
        }
        if (A.numCols == 0 || A.numRows == 0) {
            return;
        }
        int indexC = 0;
        for (int i = 0; i < A.numCols; ++i) {
            for (int k = 0; k < B.numCols; ++k) {
                aux[k] = A.unsafe_get(k, i);
            }
            for (int j = 0; j < B.numRows; ++j) {
                float total = 0.0f;
                for (int k = 0; k < B.numCols; ++k) {
                    total += aux[k] * B.unsafe_get(j, k);
                }
                C.plus(indexC++, alpha * total);
            }
        }
    }

    public static void multAddTransB(float alpha, FMatrix1Row A, FMatrix1Row B, FMatrix1Row C) {
        UtilEjml.assertTrue(A != C && B != C, "Neither 'A' or 'B' can be the same matrix as 'C'");
        UtilEjml.assertShape(A.numCols, B.numCols, "The 'A' and 'B' matrices do not have compatible dimensions");
        UtilEjml.assertShape(A.numRows == C.numRows && B.numRows == C.numCols, "C is not compatible with A and B");
        for (int xA = 0; xA < A.numRows; ++xA) {
            int cIndex = xA * B.numRows;
            int aIndexStart = xA * B.numCols;
            int end = aIndexStart + B.numCols;
            int indexB = 0;
            for (int xB = 0; xB < B.numRows; ++xB) {
                int indexA = aIndexStart;
                float total = 0.0f;
                while (indexA < end) {
                    total += A.data[indexA++] * B.data[indexB++];
                }
                C.plus(cIndex++, alpha * total);
            }
        }
    }
}

