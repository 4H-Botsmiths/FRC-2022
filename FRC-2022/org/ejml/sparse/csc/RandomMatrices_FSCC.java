/*
 * Decompiled with CFR 0.150.
 */
package org.ejml.sparse.csc;

import java.util.Arrays;
import java.util.Random;
import org.ejml.UtilEjml;
import org.ejml.data.FMatrixRMaj;
import org.ejml.data.FMatrixSparseCSC;
import org.ejml.data.FMatrixSparseTriplet;
import org.ejml.dense.row.CommonOps_FDRM;
import org.ejml.ops.FConvertMatrixStruct;
import org.ejml.sparse.csc.CommonOps_FSCC;

public class RandomMatrices_FSCC {
    public static FMatrixSparseCSC rectangle(int numRows, int numCols, int nz_total, float min, float max, Random rand) {
        int i;
        if (UtilEjml.exceedsMaxMatrixSize(numRows, numCols)) {
            throw new IllegalArgumentException("Due to how a random matrix is created, rows*cols < Integer.MAX_VALUE");
        }
        nz_total = Math.min(numRows * numCols, nz_total);
        int[] selected = UtilEjml.shuffled(numRows * numCols, nz_total, rand);
        Arrays.sort(selected, 0, nz_total);
        FMatrixSparseCSC ret = new FMatrixSparseCSC(numRows, numCols, nz_total);
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
            ret.nz_values[i] = rand.nextFloat() * (max - min) + min;
        }
        return ret;
    }

    public static FMatrixSparseCSC rectangle(int numRows, int numCols, int nz_total, Random rand) {
        return RandomMatrices_FSCC.rectangle(numRows, numCols, nz_total, -1.0f, 1.0f, rand);
    }

    public static FMatrixSparseCSC symmetric(int N, int nz_total, float min, float max, Random rand) {
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
        FMatrixSparseTriplet A = new FMatrixSparseTriplet(N, N, nz_total * 2);
        for (int i = 0; i < nz_total; ++i) {
            int index2 = open[i];
            int row = index2 / N;
            int col = index2 % N;
            float value = rand.nextFloat() * (max - min) + min;
            if (row == col) {
                A.addItem(row, col, value);
                continue;
            }
            A.addItem(row, col, value);
            A.addItem(col, row, value);
        }
        FMatrixSparseCSC B = new FMatrixSparseCSC(N, N, A.nz_length);
        FConvertMatrixStruct.convert(A, B);
        return B;
    }

    public static FMatrixSparseCSC triangleLower(int dimen, int hessenberg, int nz_total, float min, float max, Random rand) {
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
        FMatrixSparseCSC L = new FMatrixSparseCSC(dimen, dimen, nz_total);
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
                L.nz_values[nz_index++] = rand.nextFloat() * (max - min) + min;
            }
            while (s_index < off_total && selected[s_index] < rowEnd[col]) {
                int row;
                L.nz_rows[nz_index] = row = selected[s_index++] - rowStart[col] + offset;
                L.nz_values[nz_index++] = rand.nextFloat() * (max - min) + min;
            }
        }
        return L;
    }

    public static FMatrixSparseCSC triangleUpper(int dimen, int hessenberg, int nz_total, float min, float max, Random rand) {
        FMatrixSparseCSC L = RandomMatrices_FSCC.triangleLower(dimen, hessenberg, nz_total, min, max, rand);
        FMatrixSparseCSC U = L.createLike();
        CommonOps_FSCC.transpose(L, U, null);
        return U;
    }

    public static int nonzero(int numRows, int numCols, float minFill, float maxFill, Random rand) {
        int N = numRows * numCols;
        return (int)((float)N * (rand.nextFloat() * (maxFill - minFill) + minFill) + 0.5f);
    }

    public static FMatrixSparseCSC triangle(boolean upper, int N, float minFill, float maxFill, Random rand) {
        int nz = (int)((float)((N - 1) * (N - 1) / 2) * (rand.nextFloat() * (maxFill - minFill) + minFill)) + N;
        if (upper) {
            return RandomMatrices_FSCC.triangleUpper(N, 0, nz, -1.0f, 1.0f, rand);
        }
        return RandomMatrices_FSCC.triangleLower(N, 0, nz, -1.0f, 1.0f, rand);
    }

    public static FMatrixSparseCSC symmetricPosDef(int width, float probabilityZero, Random rand) {
        int i;
        if (UtilEjml.exceedsMaxMatrixSize(width, width)) {
            throw new IllegalArgumentException("Due to how a random matrix is created, width*width < Integer.MAX_VALUE");
        }
        if (probabilityZero < 0.0f || probabilityZero > 1.0f) {
            throw new IllegalArgumentException("Invalid value for probabilityZero");
        }
        FMatrixRMaj a = new FMatrixRMaj(width, 1);
        FMatrixRMaj b = new FMatrixRMaj(width, width);
        for (i = 1; i < width; ++i) {
            if (!(rand.nextFloat() >= probabilityZero)) continue;
            a.set(i, 0, rand.nextFloat() * 2.0f - 1.0f);
        }
        CommonOps_FDRM.multTransB(a, a, b);
        for (i = 0; i < width; ++i) {
            b.add(i, i, 1.0f + rand.nextFloat() * 0.1f);
        }
        FMatrixSparseCSC out = new FMatrixSparseCSC(width, width, width);
        FConvertMatrixStruct.convert(b, out, UtilEjml.TEST_F32);
        return out;
    }

    public static FMatrixSparseCSC generateUniform(int numRows, int numCols, int nzEntriesPerColumn, float min, float max, Random rand) {
        if (nzEntriesPerColumn > numRows) {
            throw new IllegalArgumentException("numRows must be greater than nzEntriesPerColumn");
        }
        int nz_total = Math.toIntExact(nzEntriesPerColumn * numCols);
        FMatrixSparseCSC matrix = new FMatrixSparseCSC(numRows, numCols, nz_total);
        matrix.indicesSorted = true;
        if (nzEntriesPerColumn == 0) {
            return matrix;
        }
        int[] nz_hist = new int[numCols];
        Arrays.fill(nz_hist, nzEntriesPerColumn);
        matrix.histogramToStructure(nz_hist);
        boolean[] selectedRows = new boolean[numRows];
        boolean dropRows = (float)nzEntriesPerColumn / (float)numRows > 0.5f;
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
                    matrix.nz_values[nz_index] = rand.nextFloat() * (max - min) + min;
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
                matrix.nz_values[nz_index] = rand.nextFloat() * (max - min) + min;
                ++nz_index;
            }
            Arrays.sort(matrix.nz_rows, nz_index - nzEntriesPerColumn, nz_index);
        }
        return matrix;
    }

    public static void ensureNotSingular(FMatrixSparseCSC A, Random rand) {
        int[] s = UtilEjml.shuffled(A.numRows, rand);
        Arrays.sort(s);
        int N = Math.min(A.numCols, A.numRows);
        for (int col = 0; col < N; ++col) {
            A.set(s[col], col, rand.nextFloat() + 0.5f);
        }
    }
}

