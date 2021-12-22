/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  org.jetbrains.annotations.Nullable
 */
package org.ejml.sparse.csc.misc;

import org.ejml.UtilEjml;
import org.ejml.data.FGrowArray;
import org.ejml.data.FMatrixSparseCSC;
import org.ejml.data.IGrowArray;
import org.jetbrains.annotations.Nullable;

public class TriangularSolver_FSCC {
    public static void solveL(FMatrixSparseCSC L, float[] x) {
        int N = L.numCols;
        int idx0 = L.col_idx[0];
        for (int col = 0; col < N; ++col) {
            int idx1 = L.col_idx[col + 1];
            int n = col;
            float f = x[n] / L.nz_values[idx0];
            x[n] = f;
            float x_j = f;
            for (int i = idx0 + 1; i < idx1; ++i) {
                int row;
                int n2 = row = L.nz_rows[i];
                x[n2] = x[n2] - L.nz_values[i] * x_j;
            }
            idx0 = idx1;
        }
    }

    public static void solveTranL(FMatrixSparseCSC L, float[] x) {
        int N = L.numCols;
        int j = N - 1;
        while (j >= 0) {
            int idx0 = L.col_idx[j];
            int idx1 = L.col_idx[j + 1];
            for (int p = idx0 + 1; p < idx1; ++p) {
                int n = j;
                x[n] = x[n] - L.nz_values[p] * x[L.nz_rows[p]];
            }
            int n = j--;
            x[n] = x[n] / L.nz_values[idx0];
        }
    }

    public static void solveU(FMatrixSparseCSC U, float[] x) {
        int N = U.numCols;
        int idx1 = U.col_idx[N];
        for (int col = N - 1; col >= 0; --col) {
            int idx0 = U.col_idx[col];
            int n = col;
            float f = x[n] / U.nz_values[idx1 - 1];
            x[n] = f;
            float x_j = f;
            for (int i = idx0; i < idx1 - 1; ++i) {
                int row;
                int n2 = row = U.nz_rows[i];
                x[n2] = x[n2] - U.nz_values[i] * x_j;
            }
            idx1 = idx0;
        }
    }

    public static void solveTran(FMatrixSparseCSC G, boolean lower, FMatrixSparseCSC B, FMatrixSparseCSC X, @Nullable int[] pinv, @Nullable FGrowArray g_x, @Nullable IGrowArray g_xi, @Nullable IGrowArray g_w) {
        float[] x = UtilEjml.adjust(g_x, G.numRows);
        X.zero();
        X.indicesSorted = false;
        int[] xi = UtilEjml.adjust(g_xi, G.numRows);
        int[] w = UtilEjml.adjust(g_w, G.numCols, G.numCols);
        for (int colB = 0; colB < B.numCols; ++colB) {
            int col;
            int i;
            int idx0 = B.col_idx[colB];
            int idx1 = B.col_idx[colB + 1];
            int X_nz_count = 0;
            for (i = idx0; i < idx1; ++i) {
                int row = B.nz_rows[i];
                x[row] = B.nz_values[i];
                w[row] = 1;
                xi[X_nz_count++] = row;
            }
            if (lower) {
                for (col = G.numRows - 1; col >= 0; --col) {
                    X_nz_count = TriangularSolver_FSCC.solveTranColumn(G, x, xi, w, pinv, X_nz_count, col);
                }
            } else {
                for (col = 0; col < G.numRows; ++col) {
                    X_nz_count = TriangularSolver_FSCC.solveTranColumn(G, x, xi, w, pinv, X_nz_count, col);
                }
            }
            if (colB + 1 < B.numCols) {
                for (i = 0; i < X_nz_count; ++i) {
                    w[xi[i]] = 0;
                }
            }
            if (X.nz_values.length < X.nz_length + X_nz_count) {
                X.growMaxLength(X.nz_length * 2 + X_nz_count, true);
            }
            int p = 0;
            while (p < X_nz_count) {
                X.nz_rows[X.nz_length] = xi[p];
                X.nz_values[X.nz_length] = x[xi[p]];
                ++p;
                ++X.nz_length;
            }
            X.col_idx[colB + 1] = X.nz_length;
        }
    }

    private static int solveTranColumn(FMatrixSparseCSC G, float[] x, int[] xi, int[] w, @Nullable int[] pinv, int x_nz_count, int col) {
        int idxG0 = G.col_idx[col];
        int idxG1 = G.col_idx[col + 1];
        int indexDiagonal = -1;
        float total = 0.0f;
        for (int j = idxG0; j < idxG1; ++j) {
            int J = pinv != null ? pinv[j] : j;
            int row = G.nz_rows[J];
            if (row == col) {
                indexDiagonal = j;
                continue;
            }
            if (w[row] != 1) continue;
            total += G.nz_values[J] * x[row];
        }
        if (w[col] == 1) {
            x[col] = (x[col] - total) / G.nz_values[indexDiagonal];
        } else if (total != 0.0f) {
            w[col] = 1;
            x[col] = -total / G.nz_values[indexDiagonal];
            xi[x_nz_count++] = col;
        }
        return x_nz_count;
    }

    public static void solve(FMatrixSparseCSC G, boolean lower, FMatrixSparseCSC B, FMatrixSparseCSC X, @Nullable int[] pinv, @Nullable FGrowArray g_x, @Nullable IGrowArray g_xi, @Nullable IGrowArray g_w) {
        float[] x = UtilEjml.adjust(g_x, G.numRows);
        if (g_xi == null) {
            g_xi = new IGrowArray();
        }
        int[] xi = UtilEjml.adjust(g_xi, G.numRows);
        int[] w = UtilEjml.adjust(g_w, G.numCols * 2, G.numCols);
        X.nz_length = 0;
        X.col_idx[0] = 0;
        X.indicesSorted = false;
        for (int colB = 0; colB < B.numCols; ++colB) {
            int top = TriangularSolver_FSCC.solveColB(G, lower, B, colB, x, pinv, g_xi, w);
            int nz_count = X.numRows - top;
            if (X.nz_values.length < X.nz_length + nz_count) {
                X.growMaxLength(X.nz_length * 2 + nz_count, true);
            }
            int p = top;
            while (p < X.numRows) {
                X.nz_rows[X.nz_length] = xi[p];
                X.nz_values[X.nz_length] = x[xi[p]];
                ++p;
                ++X.nz_length;
            }
            X.col_idx[colB + 1] = X.nz_length;
        }
    }

    public static int solveColB(FMatrixSparseCSC G, boolean lower, FMatrixSparseCSC B, int colB, float[] x, @Nullable int[] pinv, @Nullable IGrowArray g_xi, int[] w) {
        int top;
        int X_rows = G.numCols;
        int[] xi = UtilEjml.adjust(g_xi, X_rows);
        for (int p = top = TriangularSolver_FSCC.searchNzRowsInX(G, B, colB, pinv, xi, w); p < X_rows; ++p) {
            x[xi[p]] = 0.0f;
        }
        int idxB0 = B.col_idx[colB];
        int idxB1 = B.col_idx[colB + 1];
        for (int p = idxB0; p < idxB1; ++p) {
            x[B.nz_rows[p]] = B.nz_values[p];
        }
        for (int px = top; px < X_rows; ++px) {
            int q;
            int p;
            int J;
            int j = xi[px];
            int n = J = pinv != null ? pinv[j] : j;
            if (J < 0) continue;
            if (lower) {
                int n2 = j;
                x[n2] = x[n2] / G.nz_values[G.col_idx[J]];
                p = G.col_idx[J] + 1;
                q = G.col_idx[J + 1];
            } else {
                int n3 = j;
                x[n3] = x[n3] / G.nz_values[G.col_idx[J + 1] - 1];
                p = G.col_idx[J];
                q = G.col_idx[J + 1] - 1;
            }
            while (p < q) {
                int n4 = G.nz_rows[p];
                x[n4] = x[n4] - G.nz_values[p] * x[j];
                ++p;
            }
        }
        return top;
    }

    public static int searchNzRowsInX(FMatrixSparseCSC G, FMatrixSparseCSC B, int colB, @Nullable int[] pinv, int[] xi, int[] w) {
        int i;
        int X_rows = G.numCols;
        if (xi.length < X_rows) {
            throw new IllegalArgumentException("xi must be at least G.numCols=" + G.numCols);
        }
        if (w.length < 2 * X_rows) {
            throw new IllegalArgumentException("w must be at least 2*G.numCols in length (2*number of rows in X) and first N elements must be zero");
        }
        int idx0 = B.col_idx[colB];
        int idx1 = B.col_idx[colB + 1];
        int top = X_rows;
        for (i = idx0; i < idx1; ++i) {
            int rowB = B.nz_rows[i];
            if (rowB >= X_rows || w[rowB] != 0) continue;
            top = TriangularSolver_FSCC.searchNzRowsInX_DFS(rowB, G, top, pinv, xi, w);
        }
        for (i = top; i < X_rows; ++i) {
            w[xi[i]] = 0;
        }
        return top;
    }

    private static int searchNzRowsInX_DFS(int rowB, FMatrixSparseCSC G, int top, @Nullable int[] pinv, int[] xi, int[] w) {
        int N = G.numCols;
        int head = 0;
        xi[head] = rowB;
        while (head >= 0) {
            int G_col_new;
            int G_col = xi[head];
            int n = G_col_new = pinv != null ? pinv[G_col] : G_col;
            if (w[G_col] == 0) {
                w[G_col] = 1;
                w[N + head] = G_col_new < 0 || G_col_new >= N ? 0 : G.col_idx[G_col_new];
            }
            boolean done = true;
            int idx0 = w[N + head];
            int idx1 = G_col_new < 0 || G_col_new >= N ? 0 : G.col_idx[G_col_new + 1];
            for (int j = idx0; j < idx1; ++j) {
                int jrow = G.nz_rows[j];
                if (jrow >= N || w[jrow] != 0) continue;
                w[N + head] = j + 1;
                xi[++head] = jrow;
                done = false;
                break;
            }
            if (!done) continue;
            --head;
            xi[--top] = G_col;
        }
        return top;
    }

    public static void eliminationTree(FMatrixSparseCSC A, boolean ata, int[] parent, @Nullable IGrowArray gwork) {
        int m = A.numRows;
        int n = A.numCols;
        if (parent.length < n) {
            throw new IllegalArgumentException("parent must be of length N");
        }
        int[] work = UtilEjml.adjust(gwork, n + (ata ? m : 0));
        boolean ancestor = false;
        int previous = n;
        if (ata) {
            for (int i = 0; i < m; ++i) {
                work[previous + i] = -1;
            }
        }
        for (int k = 0; k < n; ++k) {
            parent[k] = -1;
            work[0 + k] = -1;
            int idx0 = A.col_idx[k];
            int idx1 = A.col_idx[k + 1];
            for (int p = idx0; p < idx1; ++p) {
                int i;
                int nz_row_p = A.nz_rows[p];
                int n2 = i = ata ? work[previous + nz_row_p] : nz_row_p;
                while (i != -1 && i < k) {
                    int inext = work[0 + i];
                    work[0 + i] = k;
                    if (inext == -1) {
                        parent[i] = k;
                        break;
                    }
                    i = inext;
                }
                if (!ata) continue;
                work[previous + nz_row_p] = k;
            }
        }
    }

    public static void postorder(int[] parent, int N, int[] post, @Nullable IGrowArray gwork) {
        int j;
        if (parent.length < N) {
            throw new IllegalArgumentException("parent must be at least of length N");
        }
        if (post.length < N) {
            throw new IllegalArgumentException("post must be at least of length N");
        }
        int[] w = UtilEjml.adjust(gwork, 3 * N);
        int next = N;
        for (j = 0; j < N; ++j) {
            w[j] = -1;
        }
        for (j = N - 1; j >= 0; --j) {
            if (parent[j] == -1) continue;
            w[next + j] = w[parent[j]];
            w[parent[j]] = j;
        }
        int k = 0;
        for (int j2 = 0; j2 < N; ++j2) {
            if (parent[j2] != -1) continue;
            k = TriangularSolver_FSCC.postorder_dfs(j2, k, w, post, N);
        }
    }

    protected static int postorder_dfs(int j, int k, int[] w, int[] post, int N) {
        int next = N;
        int stack = 2 * N;
        int top = 0;
        w[stack + top] = j;
        while (top >= 0) {
            int p = w[stack + top];
            int i = w[p];
            if (i == -1) {
                --top;
                post[k++] = p;
                continue;
            }
            w[p] = w[next + i];
            w[stack + ++top] = i;
        }
        return k;
    }

    public static int searchNzRowsElim(FMatrixSparseCSC A, int k, int[] parent, int[] s, int[] w) {
        int p;
        int top = A.numCols;
        int idx0 = A.col_idx[k];
        int idx1 = A.col_idx[k + 1];
        w[k] = -w[k] - 2;
        for (p = idx0; p < idx1; ++p) {
            int i = A.nz_rows[p];
            if (i > k) continue;
            int len = 0;
            while (w[i] >= 0) {
                s[len++] = i;
                w[i] = -w[i] - 2;
                i = parent[i];
            }
            while (len > 0) {
                s[--top] = s[--len];
            }
        }
        for (p = top; p < A.numCols; ++p) {
            w[s[p]] = -w[s[p]] - 2;
        }
        w[k] = -w[k] - 2;
        return top;
    }

    public static float qualityTriangular(FMatrixSparseCSC T) {
        int N = Math.min(T.numRows, T.numCols);
        float max = T.unsafe_get(0, 0);
        for (int i = 1; i < N; ++i) {
            max = Math.max(max, Math.abs(T.unsafe_get(i, i)));
        }
        if (max == 0.0f) {
            return 0.0f;
        }
        float quality = 1.0f;
        for (int i = 0; i < N; ++i) {
            quality *= T.unsafe_get(i, i) / max;
        }
        return Math.abs(quality);
    }
}

