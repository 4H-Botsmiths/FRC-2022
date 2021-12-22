/*
 * Decompiled with CFR 0.150.
 */
package org.ejml.sparse.csc;

import java.util.Arrays;
import java.util.Random;
import org.ejml.UtilEjml;
import org.ejml.data.DMatrixRMaj;
import org.ejml.data.DMatrixSparseCSC;
import org.ejml.data.DMatrixSparseTriplet;
import org.ejml.dense.row.CommonOps_DDRM;
import org.ejml.ops.DConvertMatrixStruct;
import org.ejml.sparse.csc.CommonOps_DSCC;

public class RandomMatrices_DSCC {
    public static DMatrixSparseCSC rectangle(int numRows, int numCols, int nz_total, double min, double max, Random rand) {
        int i;
        if (UtilEjml.exceedsMaxMatrixSize(numRows, numCols)) {
            throw new IllegalArgumentException("Due to how a random matrix is created, rows*cols < Integer.MAX_VALUE");
        }
        nz_total = Math.min(numRows * numCols, nz_total);
        int[] selected = UtilEjml.shuffled(numRows * numCols, nz_total, rand);
        Arrays.sort(selected, 0, nz_total);
        DMatrixSparseCSC ret = new DMatrixSparseCSC(numRows, numCols, nz_total);
        ret.indicesSorted = true;
        int[] hist = new int[numCols];
        for (i = 0; i < nz_total; ++i) {
            int n = selected[i] / numRows;
            hist[n] = hist[n] + 1;
        }
        ret.histogramToStructure(hist);
        for (i = 0; i < nz_total; ++i) {
            int row;
            ret.nz_rows[i] = row = selected[i] % numRows;
            ret.nz_values[i] = rand.nextDouble() * (max - min) + min;
        }
        return ret;
    }

    public static DMatrixSparseCSC rectangle(int numRows, int numCols, int nz_total, Random rand) {
        return RandomMatrices_DSCC.rectangle(numRows, numCols, nz_total, -1.0, 1.0, rand);
    }

    public static DMatrixSparseCSC symmetric(int N, int nz_total, double min, double max, Random rand) {
        if (UtilEjml.exceedsMaxMatrixSize(N, N)) {
            throw new IllegalArgumentException("Due to how a random matrix is created, N*N < Integer.MAX_VALUE");
        }
        if (N < 0) {
            throw new IllegalArgumentException("Matrix must have a positive size. N=" + N);
        }
        int Ntriagle = (N * N + N) / 2;
        int[] open = new int[Ntriagle];
        int index = 0;
        for (int row = 0; row < N; ++row) {
            int col = row;
            while (col < N) {
                open[index] = row * N + col;
                ++col;
                ++index;
            }
        }
        UtilEjml.shuffle(open, open.length, 0, nz_total, rand);
        Arrays.sort(open, 0, nz_total);
        DMatrixSparseTriplet A = new DMatrixSparseTriplet(N, N, nz_total * 2);
        for (int i = 0; i < nz_total; ++i) {
            int index2 = open[i];
            int row = index2 / N;
            int col = index2 % N;
            double value = rand.nextDouble() * (max - min) + min;
            if (row == col) {
                A.addItem(row, col, value);
                continue;
            }
            A.addItem(row, col, value);
            A.addItem(col, row, value);
        }
        DMatrixSparseCSC B = new DMatrixSparseCSC(N, N, A.nz_length);
        DConvertMatrixStruct.convert(A, B);
        return B;
    }

    public static DMatrixSparseCSC triangleLower(int dimen, int hessenberg, int nz_total, double min, double max, Random rand) {
        int diag_total = dimen - hessenberg;
        int[] rowStart = new int[dimen];
        int[] rowEnd = new int[dimen];
        int N = 0;
        for (int i = 0; i < dimen; ++i) {
            if (i < dimen - 1 + hessenberg) {
                rowStart[i] = N;
            }
            N += i < hessenberg ? dimen : dimen - 1 - i + hessenberg;
            if (i >= dimen - 1 + hessenberg) continue;
            rowEnd[i] = N;
        }
        nz_total = Math.min(N += dimen - hessenberg, nz_total);
        nz_total = Math.max(diag_total, nz_total);
        int off_total = nz_total - diag_total;
        int[] selected = UtilEjml.shuffled(N - diag_total, off_total, rand);
        Arrays.sort(selected, 0, off_total);
        DMatrixSparseCSC L = new DMatrixSparseCSC(dimen, dimen, nz_total);
        int[] hist = new int[dimen];
        int s_index = 0;
        for (int col = 0; col < dimen; ++col) {
            if (col >= hessenberg) {
                int n = col;
                hist[n] = hist[n] + 1;
            }
            while (s_index < off_total && selected[s_index] < rowEnd[col]) {
                int n = col;
                hist[n] = hist[n] + 1;
                ++s_index;
            }
        }
        L.histogramToStructure(hist);
        int nz_index = 0;
        s_index = 0;
        for (int col = 0; col < dimen; ++col) {
            int offset;
            int n = offset = col >= hessenberg ? col - hessenberg + 1 : 0;
            if (col >= hessenberg) {
                L.nz_rows[nz_index] = col - hessenberg;
                L.nz_values[nz_index++] = rand.nextDouble() * (max - min) + min;
            }
            while (s_index < off_total && selected[s_index] < rowEnd[col]) {
                int row;
                L.nz_rows[nz_index] = row = selected[s_index++] - rowStart[col] + offset;
                L.nz_values[nz_index++] = rand.nextDouble() * (max - min) + min;
            }
        }
        return L;
    }

    public static DMatrixSparseCSC triangleUpper(int dimen, int hessenberg, int nz_total, double min, double max, Random rand) {
        DMatrixSparseCSC L = RandomMatrices_DSCC.triangleLower(dimen, hessenberg, nz_total, min, max, rand);
        DMatrixSparseCSC U = L.createLike();
        CommonOps_DSCC.transpose(L, U, null);
        return U;
    }

    public static int nonzero(int numRows, int numCols, double minFill, double maxFill, Random rand) {
        int N = numRows * numCols;
        return (int)((double)N * (rand.nextDouble() * (maxFill - minFill) + minFill) + 0.5);
    }

    public static DMatrixSparseCSC triangle(boolean upper, int N, double minFill, double maxFill, Random rand) {
        int nz = (int)((double)((N - 1) * (N - 1) / 2) * (rand.nextDouble() * (maxFill - minFill) + minFill)) + N;
        if (upper) {
            return RandomMatrices_DSCC.triangleUpper(N, 0, nz, -1.0, 1.0, rand);
        }
        return RandomMatrices_DSCC.triangleLower(N, 0, nz, -1.0, 1.0, rand);
    }

    public static DMatrixSparseCSC symmetricPosDef(int width, double probabilityZero, Random rand) {
        int i;
        if (UtilEjml.exceedsMaxMatrixSize(width, width)) {
            throw new IllegalArgumentException("Due to how a random matrix is created, width*width < Integer.MAX_VALUE");
        }
        if (probabilityZero < 0.0 || probabilityZero > 1.0) {
            throw new IllegalArgumentException("Invalid value for probabilityZero");
        }
        DMatrixRMaj a = new DMatrixRMaj(width, 1);
        DMatrixRMaj b = new DMatrixRMaj(width, width);
        for (i = 1; i < width; ++i) {
            if (!(rand.nextDouble() >= probabilityZero)) continue;
            a.set(i, 0, rand.nextDouble() * 2.0 - 1.0);
        }
        CommonOps_DDRM.multTransB(a, a, b);
        for (i = 0; i < width; ++i) {
            b.add(i, i, 1.0 + rand.nextDouble() * 0.1);
        }
        DMatrixSparseCSC out = new DMatrixSparseCSC(width, width, width);
        DConvertMatrixStruct.convert(b, out, UtilEjml.TEST_F64);
        return out;
    }

    public static DMatrixSparseCSC generateUniform(int numRows, int numCols, int nzEntriesPerColumn, double min, double max, Random rand) {
        if (nzEntriesPerColumn > numRows) {
            throw new IllegalArgumentException("numRows must be greater than nzEntriesPerColumn");
        }
        int nz_total = Math.toIntExact(nzEntriesPerColumn * numCols);
        DMatrixSparseCSC matrix = new DMatrixSparseCSC(numRows, numCols, nz_total);
        matrix.indicesSorted = true;
        if (nzEntriesPerColumn == 0) {
            return matrix;
        }
        int[] nz_hist = new int[numCols];
        Arrays.fill(nz_hist, nzEntriesPerColumn);
        matrix.histogramToStructure(nz_hist);
        boolean[] selectedRows = new boolean[numRows];
        boolean dropRows = (double)((float)nzEntriesPerColumn / (float)numRows) > 0.5;
        for (int col = 0; col < numCols; ++col) {
            int row;
            int colEntry;
            if (dropRows) {
                Arrays.fill(selectedRows, true);
            } else {
                Arrays.fill(selectedRows, false);
            }
            int nz_index = col * nzEntriesPerColumn;
            if (dropRows) {
                for (colEntry = 0; colEntry < numRows - nzEntriesPerColumn; ++colEntry) {
                    row = rand.nextInt(numRows);
                    while (!selectedRows[row]) {
                        row = rand.nextInt(numRows);
                    }
                    selectedRows[row] = false;
                }
                for (int row2 = 0; row2 < selectedRows.length; ++row2) {
                    if (!selectedRows[row2]) continue;
                    matrix.nz_rows[nz_index] = row2;
                    matrix.nz_values[nz_index] = rand.nextDouble() * (max - min) + min;
                    ++nz_index;
                }
                continue;
            }
            for (colEntry = 0; colEntry < nzEntriesPerColumn; ++colEntry) {
                row = rand.nextInt(numRows);
                while (selectedRows[row]) {
                    row = rand.nextInt(numRows);
                }
                selectedRows[row] = true;
                matrix.nz_rows[nz_index] = row;
                matrix.nz_values[nz_index] = rand.nextDouble() * (max - min) + min;
                ++nz_index;
            }
            Arrays.sort(matrix.nz_rows, nz_index - nzEntriesPerColumn, nz_index);
        }
        return matrix;
    }

    public static void ensureNotSingular(DMatrixSparseCSC A, Random rand) {
        int[] s = UtilEjml.shuffled(A.numRows, rand);
        Arrays.sort(s);
        int N = Math.min(A.numCols, A.numRows);
        for (int col = 0; col < N; ++col) {
            A.set(s[col], col, rand.nextDouble() + 0.5);
        }
    }
}

