/*
 * Decompiled with CFR 0.150.
 */
package org.ejml.dense.row.mult;

import org.ejml.UtilEjml;
import org.ejml.concurrency.EjmlConcurrency;
import org.ejml.data.DMatrix1Row;
import org.ejml.dense.row.CommonOps_DDRM;

public class MatrixMatrixMult_MT_DDRM {
    public static void mult_reorder(DMatrix1Row A, DMatrix1Row B, DMatrix1Row C) {
        UtilEjml.assertTrue(A != C && B != C, "Neither 'A' or 'B' can be the same matrix as 'C'");
        UtilEjml.assertShape(A.numCols, B.numRows, "The 'A' and 'B' matrices do not have compatible dimensions");
        C.reshape(A.numRows, B.numCols);
        if (A.numCols == 0 || A.numRows == 0) {
            CommonOps_DDRM.fill(C, 0.0);
            return;
        }
        int endOfKLoop = B.numRows * B.numCols;
        EjmlConcurrency.loopFor(0, A.numRows, i -> {
            int indexCbase = i * C.numCols;
            int indexA = i * A.numCols;
            int indexB = 0;
            int indexC = indexCbase;
            int end = indexB + B.numCols;
            double valA = A.data[indexA++];
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
        });
    }

    public static void mult_small(DMatrix1Row A, DMatrix1Row B, DMatrix1Row C) {
        UtilEjml.assertTrue(A != C && B != C, "Neither 'A' or 'B' can be the same matrix as 'C'");
        UtilEjml.assertShape(A.numCols, B.numRows, "The 'A' and 'B' matrices do not have compatible dimensions");
        C.reshape(A.numRows, B.numCols);
        EjmlConcurrency.loopFor(0, A.numRows, i -> {
            int cIndex = i * B.numCols;
            int aIndexStart = i * A.numCols;
            for (int j = 0; j < B.numCols; ++j) {
                double total = 0.0;
                int indexA = aIndexStart;
                int indexB = j;
                int end = indexA + B.numRows;
                while (indexA < end) {
                    total += A.data[indexA++] * B.data[indexB];
                    indexB += B.numCols;
                }
                C.set(cIndex++, total);
            }
        });
    }

    public static void multTransA_reorder(DMatrix1Row A, DMatrix1Row B, DMatrix1Row C) {
        UtilEjml.assertTrue(A != C && B != C, "Neither 'A' or 'B' can be the same matrix as 'C'");
        UtilEjml.assertShape(A.numRows, B.numRows, "The 'A' and 'B' matrices do not have compatible dimensions");
        C.reshape(A.numCols, B.numCols);
        if (A.numCols == 0 || A.numRows == 0) {
            CommonOps_DDRM.fill(C, 0.0);
            return;
        }
        EjmlConcurrency.loopFor(0, A.numRows, i -> {
            int indexC_start = i * C.numCols;
            double valA = A.data[i];
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
        });
    }

    public static void multTransA_small(DMatrix1Row A, DMatrix1Row B, DMatrix1Row C) {
        UtilEjml.assertTrue(A != C && B != C, "Neither 'A' or 'B' can be the same matrix as 'C'");
        UtilEjml.assertShape(A.numRows, B.numRows, "The 'A' and 'B' matrices do not have compatible dimensions");
        C.reshape(A.numCols, B.numCols);
        EjmlConcurrency.loopFor(0, A.numCols, i -> {
            int cIndex = i * B.numCols;
            for (int j = 0; j < B.numCols; ++j) {
                int indexB;
                int indexA = i;
                int end = indexB + B.numRows * B.numCols;
                double total = 0.0;
                for (indexB = j; indexB < end; indexB += B.numCols) {
                    total += A.data[indexA] * B.data[indexB];
                    indexA += A.numCols;
                }
                C.set(cIndex++, total);
            }
        });
    }

    public static void multTransAB(DMatrix1Row A, DMatrix1Row B, DMatrix1Row C) {
        UtilEjml.assertTrue(A != C && B != C, "Neither 'A' or 'B' can be the same matrix as 'C'");
        UtilEjml.assertShape(A.numRows, B.numCols, "The 'A' and 'B' matrices do not have compatible dimensions");
        C.reshape(A.numCols, B.numRows);
        EjmlConcurrency.loopFor(0, A.numCols, i -> {
            int cIndex = i * B.numRows;
            int indexB = 0;
            for (int j = 0; j < B.numRows; ++j) {
                int indexA = i;
                int end = indexB + B.numCols;
                double total = 0.0;
                while (indexB < end) {
                    total += A.data[indexA] * B.data[indexB++];
                    indexA += A.numCols;
                }
                C.set(cIndex++, total);
            }
        });
    }

    public static void multTransB(DMatrix1Row A, DMatrix1Row B, DMatrix1Row C) {
        UtilEjml.assertTrue(A != C && B != C, "Neither 'A' or 'B' can be the same matrix as 'C'");
        UtilEjml.assertShape(A.numCols, B.numCols, "The 'A' and 'B' matrices do not have compatible dimensions");
        C.reshape(A.numRows, B.numRows);
        EjmlConcurrency.loopFor(0, A.numRows, xA -> {
            int cIndex = xA * B.numRows;
            int aIndexStart = xA * B.numCols;
            int end = aIndexStart + B.numCols;
            int indexB = 0;
            for (int xB = 0; xB < B.numRows; ++xB) {
                int indexA = aIndexStart;
                double total = 0.0;
                while (indexA < end) {
                    total += A.data[indexA++] * B.data[indexB++];
                }
                C.set(cIndex++, total);
            }
        });
    }

    public static void multAdd_reorder(DMatrix1Row A, DMatrix1Row B, DMatrix1Row C) {
        UtilEjml.assertTrue(A != C && B != C, "Neither 'A' or 'B' can be the same matrix as 'C'");
        UtilEjml.assertShape(A.numCols, B.numRows, "The 'A' and 'B' matrices do not have compatible dimensions");
        UtilEjml.assertShape(A.numRows == C.numRows && B.numCols == C.numCols, "C is not compatible with A and B");
        if (A.numCols == 0 || A.numRows == 0) {
            return;
        }
        int endOfKLoop = B.numRows * B.numCols;
        EjmlConcurrency.loopFor(0, A.numRows, i -> {
            int indexCbase = i * C.numCols;
            int indexA = i * A.numCols;
            int indexB = 0;
            int indexC = indexCbase;
            int end = indexB + B.numCols;
            double valA = A.data[indexA++];
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
        });
    }

    public static void multAdd_small(DMatrix1Row A, DMatrix1Row B, DMatrix1Row C) {
        UtilEjml.assertTrue(A != C && B != C, "Neither 'A' or 'B' can be the same matrix as 'C'");
        UtilEjml.assertShape(A.numCols, B.numRows, "The 'A' and 'B' matrices do not have compatible dimensions");
        UtilEjml.assertShape(A.numRows == C.numRows && B.numCols == C.numCols, "C is not compatible with A and B");
        EjmlConcurrency.loopFor(0, A.numRows, i -> {
            int cIndex = i * B.numCols;
            int aIndexStart = i * A.numCols;
            for (int j = 0; j < B.numCols; ++j) {
                double total = 0.0;
                int indexA = aIndexStart;
                int indexB = j;
                int end = indexA + B.numRows;
                while (indexA < end) {
                    total += A.data[indexA++] * B.data[indexB];
                    indexB += B.numCols;
                }
                C.plus(cIndex++, total);
            }
        });
    }

    public static void multAddTransA_reorder(DMatrix1Row A, DMatrix1Row B, DMatrix1Row C) {
        UtilEjml.assertTrue(A != C && B != C, "Neither 'A' or 'B' can be the same matrix as 'C'");
        UtilEjml.assertShape(A.numRows, B.numRows, "The 'A' and 'B' matrices do not have compatible dimensions");
        UtilEjml.assertShape(A.numCols == C.numRows && B.numCols == C.numCols, "C is not compatible with A and B");
        if (A.numCols == 0 || A.numRows == 0) {
            return;
        }
        EjmlConcurrency.loopFor(0, A.numRows, i -> {
            int indexC_start = i * C.numCols;
            double valA = A.data[i];
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
        });
    }

    public static void multAddTransA_small(DMatrix1Row A, DMatrix1Row B, DMatrix1Row C) {
        UtilEjml.assertTrue(A != C && B != C, "Neither 'A' or 'B' can be the same matrix as 'C'");
        UtilEjml.assertShape(A.numRows, B.numRows, "The 'A' and 'B' matrices do not have compatible dimensions");
        UtilEjml.assertShape(A.numCols == C.numRows && B.numCols == C.numCols, "C is not compatible with A and B");
        EjmlConcurrency.loopFor(0, A.numCols, i -> {
            int cIndex = i * B.numCols;
            for (int j = 0; j < B.numCols; ++j) {
                int indexB;
                int indexA = i;
                int end = indexB + B.numRows * B.numCols;
                double total = 0.0;
                for (indexB = j; indexB < end; indexB += B.numCols) {
                    total += A.data[indexA] * B.data[indexB];
                    indexA += A.numCols;
                }
                C.plus(cIndex++, total);
            }
        });
    }

    public static void multAddTransAB(DMatrix1Row A, DMatrix1Row B, DMatrix1Row C) {
        UtilEjml.assertTrue(A != C && B != C, "Neither 'A' or 'B' can be the same matrix as 'C'");
        UtilEjml.assertShape(A.numRows, B.numCols, "The 'A' and 'B' matrices do not have compatible dimensions");
        UtilEjml.assertShape(A.numCols == C.numRows && B.numRows == C.numCols, "C is not compatible with A and B");
        EjmlConcurrency.loopFor(0, A.numCols, i -> {
            int cIndex = i * B.numRows;
            int indexB = 0;
            for (int j = 0; j < B.numRows; ++j) {
                int indexA = i;
                int end = indexB + B.numCols;
                double total = 0.0;
                while (indexB < end) {
                    total += A.data[indexA] * B.data[indexB++];
                    indexA += A.numCols;
                }
                C.plus(cIndex++, total);
            }
        });
    }

    public static void multAddTransB(DMatrix1Row A, DMatrix1Row B, DMatrix1Row C) {
        UtilEjml.assertTrue(A != C && B != C, "Neither 'A' or 'B' can be the same matrix as 'C'");
        UtilEjml.assertShape(A.numCols, B.numCols, "The 'A' and 'B' matrices do not have compatible dimensions");
        UtilEjml.assertShape(A.numRows == C.numRows && B.numRows == C.numCols, "C is not compatible with A and B");
        EjmlConcurrency.loopFor(0, A.numRows, xA -> {
            int cIndex = xA * B.numRows;
            int aIndexStart = xA * B.numCols;
            int end = aIndexStart + B.numCols;
            int indexB = 0;
            for (int xB = 0; xB < B.numRows; ++xB) {
                int indexA = aIndexStart;
                double total = 0.0;
                while (indexA < end) {
                    total += A.data[indexA++] * B.data[indexB++];
                }
                C.plus(cIndex++, total);
            }
        });
    }

    public static void mult_reorder(double alpha, DMatrix1Row A, DMatrix1Row B, DMatrix1Row C) {
        UtilEjml.assertTrue(A != C && B != C, "Neither 'A' or 'B' can be the same matrix as 'C'");
        UtilEjml.assertShape(A.numCols, B.numRows, "The 'A' and 'B' matrices do not have compatible dimensions");
        C.reshape(A.numRows, B.numCols);
        if (A.numCols == 0 || A.numRows == 0) {
            CommonOps_DDRM.fill(C, 0.0);
            return;
        }
        int endOfKLoop = B.numRows * B.numCols;
        EjmlConcurrency.loopFor(0, A.numRows, i -> {
            int indexCbase = i * C.numCols;
            int indexA = i * A.numCols;
            int indexB = 0;
            int indexC = indexCbase;
            int end = indexB + B.numCols;
            double valA = alpha * A.data[indexA++];
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
        });
    }

    public static void mult_small(double alpha, DMatrix1Row A, DMatrix1Row B, DMatrix1Row C) {
        UtilEjml.assertTrue(A != C && B != C, "Neither 'A' or 'B' can be the same matrix as 'C'");
        UtilEjml.assertShape(A.numCols, B.numRows, "The 'A' and 'B' matrices do not have compatible dimensions");
        C.reshape(A.numRows, B.numCols);
        EjmlConcurrency.loopFor(0, A.numRows, i -> {
            int cIndex = i * B.numCols;
            int aIndexStart = i * A.numCols;
            for (int j = 0; j < B.numCols; ++j) {
                double total = 0.0;
                int indexA = aIndexStart;
                int indexB = j;
                int end = indexA + B.numRows;
                while (indexA < end) {
                    total += A.data[indexA++] * B.data[indexB];
                    indexB += B.numCols;
                }
                C.set(cIndex++, alpha * total);
            }
        });
    }

    public static void multTransA_reorder(double alpha, DMatrix1Row A, DMatrix1Row B, DMatrix1Row C) {
        UtilEjml.assertTrue(A != C && B != C, "Neither 'A' or 'B' can be the same matrix as 'C'");
        UtilEjml.assertShape(A.numRows, B.numRows, "The 'A' and 'B' matrices do not have compatible dimensions");
        C.reshape(A.numCols, B.numCols);
        if (A.numCols == 0 || A.numRows == 0) {
            CommonOps_DDRM.fill(C, 0.0);
            return;
        }
        EjmlConcurrency.loopFor(0, A.numRows, i -> {
            int indexC_start = i * C.numCols;
            double valA = alpha * A.data[i];
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
        });
    }

    public static void multTransA_small(double alpha, DMatrix1Row A, DMatrix1Row B, DMatrix1Row C) {
        UtilEjml.assertTrue(A != C && B != C, "Neither 'A' or 'B' can be the same matrix as 'C'");
        UtilEjml.assertShape(A.numRows, B.numRows, "The 'A' and 'B' matrices do not have compatible dimensions");
        C.reshape(A.numCols, B.numCols);
        EjmlConcurrency.loopFor(0, A.numCols, i -> {
            int cIndex = i * B.numCols;
            for (int j = 0; j < B.numCols; ++j) {
                int indexB;
                int indexA = i;
                int end = indexB + B.numRows * B.numCols;
                double total = 0.0;
                for (indexB = j; indexB < end; indexB += B.numCols) {
                    total += A.data[indexA] * B.data[indexB];
                    indexA += A.numCols;
                }
                C.set(cIndex++, alpha * total);
            }
        });
    }

    public static void multTransAB(double alpha, DMatrix1Row A, DMatrix1Row B, DMatrix1Row C) {
        UtilEjml.assertTrue(A != C && B != C, "Neither 'A' or 'B' can be the same matrix as 'C'");
        UtilEjml.assertShape(A.numRows, B.numCols, "The 'A' and 'B' matrices do not have compatible dimensions");
        C.reshape(A.numCols, B.numRows);
        EjmlConcurrency.loopFor(0, A.numCols, i -> {
            int cIndex = i * B.numRows;
            int indexB = 0;
            for (int j = 0; j < B.numRows; ++j) {
                int indexA = i;
                int end = indexB + B.numCols;
                double total = 0.0;
                while (indexB < end) {
                    total += A.data[indexA] * B.data[indexB++];
                    indexA += A.numCols;
                }
                C.set(cIndex++, alpha * total);
            }
        });
    }

    public static void multTransB(double alpha, DMatrix1Row A, DMatrix1Row B, DMatrix1Row C) {
        UtilEjml.assertTrue(A != C && B != C, "Neither 'A' or 'B' can be the same matrix as 'C'");
        UtilEjml.assertShape(A.numCols, B.numCols, "The 'A' and 'B' matrices do not have compatible dimensions");
        C.reshape(A.numRows, B.numRows);
        EjmlConcurrency.loopFor(0, A.numRows, xA -> {
            int cIndex = xA * B.numRows;
            int aIndexStart = xA * B.numCols;
            int end = aIndexStart + B.numCols;
            int indexB = 0;
            for (int xB = 0; xB < B.numRows; ++xB) {
                int indexA = aIndexStart;
                double total = 0.0;
                while (indexA < end) {
                    total += A.data[indexA++] * B.data[indexB++];
                }
                C.set(cIndex++, alpha * total);
            }
        });
    }

    public static void multAdd_reorder(double alpha, DMatrix1Row A, DMatrix1Row B, DMatrix1Row C) {
        UtilEjml.assertTrue(A != C && B != C, "Neither 'A' or 'B' can be the same matrix as 'C'");
        UtilEjml.assertShape(A.numCols, B.numRows, "The 'A' and 'B' matrices do not have compatible dimensions");
        UtilEjml.assertShape(A.numRows == C.numRows && B.numCols == C.numCols, "C is not compatible with A and B");
        if (A.numCols == 0 || A.numRows == 0) {
            return;
        }
        int endOfKLoop = B.numRows * B.numCols;
        EjmlConcurrency.loopFor(0, A.numRows, i -> {
            int indexCbase = i * C.numCols;
            int indexA = i * A.numCols;
            int indexB = 0;
            int indexC = indexCbase;
            int end = indexB + B.numCols;
            double valA = alpha * A.data[indexA++];
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
        });
    }

    public static void multAdd_small(double alpha, DMatrix1Row A, DMatrix1Row B, DMatrix1Row C) {
        UtilEjml.assertTrue(A != C && B != C, "Neither 'A' or 'B' can be the same matrix as 'C'");
        UtilEjml.assertShape(A.numCols, B.numRows, "The 'A' and 'B' matrices do not have compatible dimensions");
        UtilEjml.assertShape(A.numRows == C.numRows && B.numCols == C.numCols, "C is not compatible with A and B");
        EjmlConcurrency.loopFor(0, A.numRows, i -> {
            int cIndex = i * B.numCols;
            int aIndexStart = i * A.numCols;
            for (int j = 0; j < B.numCols; ++j) {
                double total = 0.0;
                int indexA = aIndexStart;
                int indexB = j;
                int end = indexA + B.numRows;
                while (indexA < end) {
                    total += A.data[indexA++] * B.data[indexB];
                    indexB += B.numCols;
                }
                C.plus(cIndex++, alpha * total);
            }
        });
    }

    public static void multAddTransA_reorder(double alpha, DMatrix1Row A, DMatrix1Row B, DMatrix1Row C) {
        UtilEjml.assertTrue(A != C && B != C, "Neither 'A' or 'B' can be the same matrix as 'C'");
        UtilEjml.assertShape(A.numRows, B.numRows, "The 'A' and 'B' matrices do not have compatible dimensions");
        UtilEjml.assertShape(A.numCols == C.numRows && B.numCols == C.numCols, "C is not compatible with A and B");
        if (A.numCols == 0 || A.numRows == 0) {
            return;
        }
        EjmlConcurrency.loopFor(0, A.numRows, i -> {
            int indexC_start = i * C.numCols;
            double valA = alpha * A.data[i];
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
        });
    }

    public static void multAddTransA_small(double alpha, DMatrix1Row A, DMatrix1Row B, DMatrix1Row C) {
        UtilEjml.assertTrue(A != C && B != C, "Neither 'A' or 'B' can be the same matrix as 'C'");
        UtilEjml.assertShape(A.numRows, B.numRows, "The 'A' and 'B' matrices do not have compatible dimensions");
        UtilEjml.assertShape(A.numCols == C.numRows && B.numCols == C.numCols, "C is not compatible with A and B");
        EjmlConcurrency.loopFor(0, A.numCols, i -> {
            int cIndex = i * B.numCols;
            for (int j = 0; j < B.numCols; ++j) {
                int indexB;
                int indexA = i;
                int end = indexB + B.numRows * B.numCols;
                double total = 0.0;
                for (indexB = j; indexB < end; indexB += B.numCols) {
                    total += A.data[indexA] * B.data[indexB];
                    indexA += A.numCols;
                }
                C.plus(cIndex++, alpha * total);
            }
        });
    }

    public static void multAddTransAB(double alpha, DMatrix1Row A, DMatrix1Row B, DMatrix1Row C) {
        UtilEjml.assertTrue(A != C && B != C, "Neither 'A' or 'B' can be the same matrix as 'C'");
        UtilEjml.assertShape(A.numRows, B.numCols, "The 'A' and 'B' matrices do not have compatible dimensions");
        UtilEjml.assertShape(A.numCols == C.numRows && B.numRows == C.numCols, "C is not compatible with A and B");
        EjmlConcurrency.loopFor(0, A.numCols, i -> {
            int cIndex = i * B.numRows;
            int indexB = 0;
            for (int j = 0; j < B.numRows; ++j) {
                int indexA = i;
                int end = indexB + B.numCols;
                double total = 0.0;
                while (indexB < end) {
                    total += A.data[indexA] * B.data[indexB++];
                    indexA += A.numCols;
                }
                C.plus(cIndex++, alpha * total);
            }
        });
    }

    public static void multAddTransB(double alpha, DMatrix1Row A, DMatrix1Row B, DMatrix1Row C) {
        UtilEjml.assertTrue(A != C && B != C, "Neither 'A' or 'B' can be the same matrix as 'C'");
        UtilEjml.assertShape(A.numCols, B.numCols, "The 'A' and 'B' matrices do not have compatible dimensions");
        UtilEjml.assertShape(A.numRows == C.numRows && B.numRows == C.numCols, "C is not compatible with A and B");
        EjmlConcurrency.loopFor(0, A.numRows, xA -> {
            int cIndex = xA * B.numRows;
            int aIndexStart = xA * B.numCols;
            int end = aIndexStart + B.numCols;
            int indexB = 0;
            for (int xB = 0; xB < B.numRows; ++xB) {
                int indexA = aIndexStart;
                double total = 0.0;
                while (indexA < end) {
                    total += A.data[indexA++] * B.data[indexB++];
                }
                C.plus(cIndex++, alpha * total);
            }
        });
    }
}

