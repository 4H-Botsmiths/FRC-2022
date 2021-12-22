/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  org.jetbrains.annotations.Nullable
 */
package org.ejml.ops;

import org.ejml.UtilEjml;
import org.ejml.data.FMatrix;
import org.ejml.data.FMatrix2;
import org.ejml.data.FMatrix2x2;
import org.ejml.data.FMatrix3;
import org.ejml.data.FMatrix3x3;
import org.ejml.data.FMatrix4;
import org.ejml.data.FMatrix4x4;
import org.ejml.data.FMatrix5;
import org.ejml.data.FMatrix5x5;
import org.ejml.data.FMatrix6;
import org.ejml.data.FMatrix6x6;
import org.ejml.data.FMatrixRBlock;
import org.ejml.data.FMatrixRMaj;
import org.ejml.data.FMatrixSparseCSC;
import org.ejml.data.FMatrixSparseTriplet;
import org.ejml.data.IGrowArray;
import org.ejml.data.ReshapeMatrix;
import org.jetbrains.annotations.Nullable;

public class FConvertMatrixStruct {
    public static void convert(FMatrix input, FMatrix output) {
        if (output instanceof ReshapeMatrix) {
            ((ReshapeMatrix)((Object)output)).reshape(input.getNumRows(), input.getNumCols());
        } else {
            if (input.getNumRows() != output.getNumRows()) {
                throw new IllegalArgumentException("Number of rows do not match");
            }
            if (input.getNumCols() != output.getNumCols()) {
                throw new IllegalArgumentException("Number of columns do not match");
            }
        }
        for (int i = 0; i < input.getNumRows(); ++i) {
            for (int j = 0; j < input.getNumCols(); ++j) {
                output.unsafe_set(i, j, input.unsafe_get(i, j));
            }
        }
    }

    public static FMatrixRMaj convert(FMatrix2x2 input, @Nullable FMatrixRMaj output) {
        if (output == null) {
            output = new FMatrixRMaj(2, 2);
        }
        output.reshape(input.getNumRows(), input.getNumCols());
        output.data[0] = input.a11;
        output.data[1] = input.a12;
        output.data[2] = input.a21;
        output.data[3] = input.a22;
        return output;
    }

    public static FMatrixRMaj convert(FMatrix3x3 input, @Nullable FMatrixRMaj output) {
        if (output == null) {
            output = new FMatrixRMaj(3, 3);
        }
        output.reshape(input.getNumRows(), input.getNumCols());
        output.data[0] = input.a11;
        output.data[1] = input.a12;
        output.data[2] = input.a13;
        output.data[3] = input.a21;
        output.data[4] = input.a22;
        output.data[5] = input.a23;
        output.data[6] = input.a31;
        output.data[7] = input.a32;
        output.data[8] = input.a33;
        return output;
    }

    public static FMatrixRMaj convert(FMatrix4x4 input, @Nullable FMatrixRMaj output) {
        if (output == null) {
            output = new FMatrixRMaj(4, 4);
        }
        output.reshape(input.getNumRows(), input.getNumCols());
        output.data[0] = input.a11;
        output.data[1] = input.a12;
        output.data[2] = input.a13;
        output.data[3] = input.a14;
        output.data[4] = input.a21;
        output.data[5] = input.a22;
        output.data[6] = input.a23;
        output.data[7] = input.a24;
        output.data[8] = input.a31;
        output.data[9] = input.a32;
        output.data[10] = input.a33;
        output.data[11] = input.a34;
        output.data[12] = input.a41;
        output.data[13] = input.a42;
        output.data[14] = input.a43;
        output.data[15] = input.a44;
        return output;
    }

    public static FMatrixRMaj convert(FMatrix5x5 input, @Nullable FMatrixRMaj output) {
        if (output == null) {
            output = new FMatrixRMaj(5, 5);
        }
        output.reshape(input.getNumRows(), input.getNumCols());
        output.data[0] = input.a11;
        output.data[1] = input.a12;
        output.data[2] = input.a13;
        output.data[3] = input.a14;
        output.data[4] = input.a15;
        output.data[5] = input.a21;
        output.data[6] = input.a22;
        output.data[7] = input.a23;
        output.data[8] = input.a24;
        output.data[9] = input.a25;
        output.data[10] = input.a31;
        output.data[11] = input.a32;
        output.data[12] = input.a33;
        output.data[13] = input.a34;
        output.data[14] = input.a35;
        output.data[15] = input.a41;
        output.data[16] = input.a42;
        output.data[17] = input.a43;
        output.data[18] = input.a44;
        output.data[19] = input.a45;
        output.data[20] = input.a51;
        output.data[21] = input.a52;
        output.data[22] = input.a53;
        output.data[23] = input.a54;
        output.data[24] = input.a55;
        return output;
    }

    public static FMatrixRMaj convert(FMatrix6x6 input, @Nullable FMatrixRMaj output) {
        if (output == null) {
            output = new FMatrixRMaj(6, 6);
        }
        output.reshape(input.getNumRows(), input.getNumCols());
        output.data[0] = input.a11;
        output.data[1] = input.a12;
        output.data[2] = input.a13;
        output.data[3] = input.a14;
        output.data[4] = input.a15;
        output.data[5] = input.a16;
        output.data[6] = input.a21;
        output.data[7] = input.a22;
        output.data[8] = input.a23;
        output.data[9] = input.a24;
        output.data[10] = input.a25;
        output.data[11] = input.a26;
        output.data[12] = input.a31;
        output.data[13] = input.a32;
        output.data[14] = input.a33;
        output.data[15] = input.a34;
        output.data[16] = input.a35;
        output.data[17] = input.a36;
        output.data[18] = input.a41;
        output.data[19] = input.a42;
        output.data[20] = input.a43;
        output.data[21] = input.a44;
        output.data[22] = input.a45;
        output.data[23] = input.a46;
        output.data[24] = input.a51;
        output.data[25] = input.a52;
        output.data[26] = input.a53;
        output.data[27] = input.a54;
        output.data[28] = input.a55;
        output.data[29] = input.a56;
        output.data[30] = input.a61;
        output.data[31] = input.a62;
        output.data[32] = input.a63;
        output.data[33] = input.a64;
        output.data[34] = input.a65;
        output.data[35] = input.a66;
        return output;
    }

    public static FMatrix2x2 convert(FMatrixRMaj input, @Nullable FMatrix2x2 output) {
        if (output == null) {
            output = new FMatrix2x2();
        }
        if (input.getNumRows() != output.getNumRows()) {
            throw new IllegalArgumentException("Number of rows do not match");
        }
        if (input.getNumCols() != output.getNumCols()) {
            throw new IllegalArgumentException("Number of columns do not match");
        }
        output.a11 = input.data[0];
        output.a12 = input.data[1];
        output.a21 = input.data[2];
        output.a22 = input.data[3];
        return output;
    }

    public static FMatrix3x3 convert(FMatrixRMaj input, @Nullable FMatrix3x3 output) {
        if (output == null) {
            output = new FMatrix3x3();
        }
        if (input.getNumRows() != output.getNumRows()) {
            throw new IllegalArgumentException("Number of rows do not match");
        }
        if (input.getNumCols() != output.getNumCols()) {
            throw new IllegalArgumentException("Number of columns do not match");
        }
        output.a11 = input.data[0];
        output.a12 = input.data[1];
        output.a13 = input.data[2];
        output.a21 = input.data[3];
        output.a22 = input.data[4];
        output.a23 = input.data[5];
        output.a31 = input.data[6];
        output.a32 = input.data[7];
        output.a33 = input.data[8];
        return output;
    }

    public static FMatrix4x4 convert(FMatrixRMaj input, @Nullable FMatrix4x4 output) {
        if (output == null) {
            output = new FMatrix4x4();
        }
        if (input.getNumRows() != output.getNumRows()) {
            throw new IllegalArgumentException("Number of rows do not match");
        }
        if (input.getNumCols() != output.getNumCols()) {
            throw new IllegalArgumentException("Number of columns do not match");
        }
        output.a11 = input.data[0];
        output.a12 = input.data[1];
        output.a13 = input.data[2];
        output.a14 = input.data[3];
        output.a21 = input.data[4];
        output.a22 = input.data[5];
        output.a23 = input.data[6];
        output.a24 = input.data[7];
        output.a31 = input.data[8];
        output.a32 = input.data[9];
        output.a33 = input.data[10];
        output.a34 = input.data[11];
        output.a41 = input.data[12];
        output.a42 = input.data[13];
        output.a43 = input.data[14];
        output.a44 = input.data[15];
        return output;
    }

    public static FMatrix5x5 convert(FMatrixRMaj input, @Nullable FMatrix5x5 output) {
        if (output == null) {
            output = new FMatrix5x5();
        }
        if (input.getNumRows() != output.getNumRows()) {
            throw new IllegalArgumentException("Number of rows do not match");
        }
        if (input.getNumCols() != output.getNumCols()) {
            throw new IllegalArgumentException("Number of columns do not match");
        }
        output.a11 = input.data[0];
        output.a12 = input.data[1];
        output.a13 = input.data[2];
        output.a14 = input.data[3];
        output.a15 = input.data[4];
        output.a21 = input.data[5];
        output.a22 = input.data[6];
        output.a23 = input.data[7];
        output.a24 = input.data[8];
        output.a25 = input.data[9];
        output.a31 = input.data[10];
        output.a32 = input.data[11];
        output.a33 = input.data[12];
        output.a34 = input.data[13];
        output.a35 = input.data[14];
        output.a41 = input.data[15];
        output.a42 = input.data[16];
        output.a43 = input.data[17];
        output.a44 = input.data[18];
        output.a45 = input.data[19];
        output.a51 = input.data[20];
        output.a52 = input.data[21];
        output.a53 = input.data[22];
        output.a54 = input.data[23];
        output.a55 = input.data[24];
        return output;
    }

    public static FMatrix6x6 convert(FMatrixRMaj input, @Nullable FMatrix6x6 output) {
        if (output == null) {
            output = new FMatrix6x6();
        }
        if (input.getNumRows() != output.getNumRows()) {
            throw new IllegalArgumentException("Number of rows do not match");
        }
        if (input.getNumCols() != output.getNumCols()) {
            throw new IllegalArgumentException("Number of columns do not match");
        }
        output.a11 = input.data[0];
        output.a12 = input.data[1];
        output.a13 = input.data[2];
        output.a14 = input.data[3];
        output.a15 = input.data[4];
        output.a16 = input.data[5];
        output.a21 = input.data[6];
        output.a22 = input.data[7];
        output.a23 = input.data[8];
        output.a24 = input.data[9];
        output.a25 = input.data[10];
        output.a26 = input.data[11];
        output.a31 = input.data[12];
        output.a32 = input.data[13];
        output.a33 = input.data[14];
        output.a34 = input.data[15];
        output.a35 = input.data[16];
        output.a36 = input.data[17];
        output.a41 = input.data[18];
        output.a42 = input.data[19];
        output.a43 = input.data[20];
        output.a44 = input.data[21];
        output.a45 = input.data[22];
        output.a46 = input.data[23];
        output.a51 = input.data[24];
        output.a52 = input.data[25];
        output.a53 = input.data[26];
        output.a54 = input.data[27];
        output.a55 = input.data[28];
        output.a56 = input.data[29];
        output.a61 = input.data[30];
        output.a62 = input.data[31];
        output.a63 = input.data[32];
        output.a64 = input.data[33];
        output.a65 = input.data[34];
        output.a66 = input.data[35];
        return output;
    }

    public static FMatrixRMaj convert(FMatrix2 input, @Nullable FMatrixRMaj output) {
        if (output == null) {
            output = new FMatrixRMaj(2, 1);
        }
        if (output.getNumRows() != 1 && output.getNumCols() != 1) {
            throw new IllegalArgumentException("One row or column must have a length of 1 for it to be a vector");
        }
        int length = Math.max(output.getNumRows(), output.getNumCols());
        if (length != 2) {
            throw new IllegalArgumentException("Length of input vector is not 2.  It is " + length);
        }
        output.data[0] = input.a1;
        output.data[1] = input.a2;
        return output;
    }

    public static FMatrixRMaj convert(FMatrix3 input, @Nullable FMatrixRMaj output) {
        if (output == null) {
            output = new FMatrixRMaj(3, 1);
        }
        if (output.getNumRows() != 1 && output.getNumCols() != 1) {
            throw new IllegalArgumentException("One row or column must have a length of 1 for it to be a vector");
        }
        int length = Math.max(output.getNumRows(), output.getNumCols());
        if (length != 3) {
            throw new IllegalArgumentException("Length of input vector is not 3.  It is " + length);
        }
        output.data[0] = input.a1;
        output.data[1] = input.a2;
        output.data[2] = input.a3;
        return output;
    }

    public static FMatrixRMaj convert(FMatrix4 input, @Nullable FMatrixRMaj output) {
        if (output == null) {
            output = new FMatrixRMaj(4, 1);
        }
        if (output.getNumRows() != 1 && output.getNumCols() != 1) {
            throw new IllegalArgumentException("One row or column must have a length of 1 for it to be a vector");
        }
        int length = Math.max(output.getNumRows(), output.getNumCols());
        if (length != 4) {
            throw new IllegalArgumentException("Length of input vector is not 4.  It is " + length);
        }
        output.data[0] = input.a1;
        output.data[1] = input.a2;
        output.data[2] = input.a3;
        output.data[3] = input.a4;
        return output;
    }

    public static FMatrixRMaj convert(FMatrix5 input, @Nullable FMatrixRMaj output) {
        if (output == null) {
            output = new FMatrixRMaj(5, 1);
        }
        if (output.getNumRows() != 1 && output.getNumCols() != 1) {
            throw new IllegalArgumentException("One row or column must have a length of 1 for it to be a vector");
        }
        int length = Math.max(output.getNumRows(), output.getNumCols());
        if (length != 5) {
            throw new IllegalArgumentException("Length of input vector is not 5.  It is " + length);
        }
        output.data[0] = input.a1;
        output.data[1] = input.a2;
        output.data[2] = input.a3;
        output.data[3] = input.a4;
        output.data[4] = input.a5;
        return output;
    }

    public static FMatrixRMaj convert(FMatrix6 input, @Nullable FMatrixRMaj output) {
        if (output == null) {
            output = new FMatrixRMaj(6, 1);
        }
        if (output.getNumRows() != 1 && output.getNumCols() != 1) {
            throw new IllegalArgumentException("One row or column must have a length of 1 for it to be a vector");
        }
        int length = Math.max(output.getNumRows(), output.getNumCols());
        if (length != 6) {
            throw new IllegalArgumentException("Length of input vector is not 6.  It is " + length);
        }
        output.data[0] = input.a1;
        output.data[1] = input.a2;
        output.data[2] = input.a3;
        output.data[3] = input.a4;
        output.data[4] = input.a5;
        output.data[5] = input.a6;
        return output;
    }

    public static FMatrix2 convert(FMatrixRMaj input, @Nullable FMatrix2 output) {
        if (output == null) {
            output = new FMatrix2();
        }
        if (input.getNumRows() != 1 && input.getNumCols() != 1) {
            throw new IllegalArgumentException("One row or column must have a length of 1 for it to be a vector");
        }
        int length = Math.max(input.getNumRows(), input.getNumCols());
        if (length != 2) {
            throw new IllegalArgumentException("Length of input vector is not 2.  It is " + length);
        }
        output.a1 = input.data[0];
        output.a2 = input.data[1];
        return output;
    }

    public static FMatrix3 convert(FMatrixRMaj input, @Nullable FMatrix3 output) {
        if (output == null) {
            output = new FMatrix3();
        }
        if (input.getNumRows() != 1 && input.getNumCols() != 1) {
            throw new IllegalArgumentException("One row or column must have a length of 1 for it to be a vector");
        }
        int length = Math.max(input.getNumRows(), input.getNumCols());
        if (length != 3) {
            throw new IllegalArgumentException("Length of input vector is not 3.  It is " + length);
        }
        output.a1 = input.data[0];
        output.a2 = input.data[1];
        output.a3 = input.data[2];
        return output;
    }

    public static FMatrix4 convert(FMatrixRMaj input, @Nullable FMatrix4 output) {
        if (output == null) {
            output = new FMatrix4();
        }
        if (input.getNumRows() != 1 && input.getNumCols() != 1) {
            throw new IllegalArgumentException("One row or column must have a length of 1 for it to be a vector");
        }
        int length = Math.max(input.getNumRows(), input.getNumCols());
        if (length != 4) {
            throw new IllegalArgumentException("Length of input vector is not 4.  It is " + length);
        }
        output.a1 = input.data[0];
        output.a2 = input.data[1];
        output.a3 = input.data[2];
        output.a4 = input.data[3];
        return output;
    }

    public static FMatrix5 convert(FMatrixRMaj input, @Nullable FMatrix5 output) {
        if (output == null) {
            output = new FMatrix5();
        }
        if (input.getNumRows() != 1 && input.getNumCols() != 1) {
            throw new IllegalArgumentException("One row or column must have a length of 1 for it to be a vector");
        }
        int length = Math.max(input.getNumRows(), input.getNumCols());
        if (length != 5) {
            throw new IllegalArgumentException("Length of input vector is not 5.  It is " + length);
        }
        output.a1 = input.data[0];
        output.a2 = input.data[1];
        output.a3 = input.data[2];
        output.a4 = input.data[3];
        output.a5 = input.data[4];
        return output;
    }

    public static FMatrix6 convert(FMatrixRMaj input, @Nullable FMatrix6 output) {
        if (output == null) {
            output = new FMatrix6();
        }
        if (input.getNumRows() != 1 && input.getNumCols() != 1) {
            throw new IllegalArgumentException("One row or column must have a length of 1 for it to be a vector");
        }
        int length = Math.max(input.getNumRows(), input.getNumCols());
        if (length != 6) {
            throw new IllegalArgumentException("Length of input vector is not 6.  It is " + length);
        }
        output.a1 = input.data[0];
        output.a2 = input.data[1];
        output.a3 = input.data[2];
        output.a4 = input.data[3];
        output.a5 = input.data[4];
        output.a6 = input.data[5];
        return output;
    }

    public static void convert(FMatrixRMaj src, FMatrixRBlock dst) {
        if (src.numRows != dst.numRows || src.numCols != dst.numCols) {
            throw new IllegalArgumentException("Must be the same size.");
        }
        for (int i = 0; i < dst.numRows; i += dst.blockLength) {
            int blockHeight = Math.min(dst.blockLength, dst.numRows - i);
            for (int j = 0; j < dst.numCols; j += dst.blockLength) {
                int blockWidth = Math.min(dst.blockLength, dst.numCols - j);
                int indexDst = i * dst.numCols + blockHeight * j;
                int indexSrcRow = i * dst.numCols + j;
                for (int k = 0; k < blockHeight; ++k) {
                    System.arraycopy(src.data, indexSrcRow, dst.data, indexDst, blockWidth);
                    indexDst += blockWidth;
                    indexSrcRow += dst.numCols;
                }
            }
        }
    }

    public static FMatrixRMaj convert(FMatrixRBlock src, @Nullable FMatrixRMaj dst) {
        if (dst != null) {
            dst.reshape(src.getNumRows(), src.getNumCols());
        } else {
            dst = new FMatrixRMaj(src.numRows, src.numCols);
        }
        for (int i = 0; i < src.numRows; i += src.blockLength) {
            int blockHeight = Math.min(src.blockLength, src.numRows - i);
            for (int j = 0; j < src.numCols; j += src.blockLength) {
                int blockWidth = Math.min(src.blockLength, src.numCols - j);
                int indexSrc = i * src.numCols + blockHeight * j;
                int indexDstRow = i * dst.numCols + j;
                for (int k = 0; k < blockHeight; ++k) {
                    System.arraycopy(src.data, indexSrc, dst.data, indexDstRow, blockWidth);
                    indexSrc += blockWidth;
                    indexDstRow += dst.numCols;
                }
            }
        }
        return dst;
    }

    public static FMatrixSparseTriplet convert(FMatrix src, @Nullable FMatrixSparseTriplet dst, float tol) {
        if (dst == null) {
            dst = new FMatrixSparseTriplet(src.getNumRows(), src.getNumCols(), 1);
        } else {
            dst.reshape(src.getNumRows(), src.getNumCols());
        }
        for (int row = 0; row < src.getNumRows(); ++row) {
            for (int col = 0; col < src.getNumCols(); ++col) {
                float value = src.unsafe_get(row, col);
                if (!(Math.abs(value) > tol)) continue;
                dst.addItem(row, col, value);
            }
        }
        return dst;
    }

    public static FMatrixSparseTriplet convert(FMatrixRMaj src, @Nullable FMatrixSparseTriplet dst, float tol) {
        if (dst == null) {
            dst = new FMatrixSparseTriplet(src.numRows, src.numCols, src.numRows * src.numCols);
        } else {
            dst.reshape(src.numRows, src.numCols);
        }
        int index = 0;
        for (int row = 0; row < src.numRows; ++row) {
            for (int col = 0; col < src.numCols; ++col) {
                int n = index++;
                float value = src.data[n];
                if (!(Math.abs(value) > tol)) continue;
                dst.addItem(row, col, value);
            }
        }
        return dst;
    }

    public static FMatrixRMaj convert(FMatrixSparseTriplet src, @Nullable FMatrixRMaj dst) {
        if (dst == null) {
            dst = new FMatrixRMaj(src.numRows, src.numCols);
        } else {
            dst.reshape(src.numRows, src.numCols);
            dst.zero();
        }
        for (int i = 0; i < src.nz_length; ++i) {
            int row = src.nz_rowcol.data[i * 2];
            int col = src.nz_rowcol.data[i * 2 + 1];
            float value = src.nz_value.data[i];
            dst.unsafe_set(row, col, value);
        }
        return dst;
    }

    public static FMatrixRMaj convert(FMatrixSparseCSC src, @Nullable FMatrixRMaj dst) {
        if (dst == null) {
            dst = new FMatrixRMaj(src.numRows, src.numCols);
        } else {
            dst.reshape(src.numRows, src.numCols);
            dst.zero();
        }
        int idx0 = src.col_idx[0];
        for (int j = 1; j <= src.numCols; ++j) {
            int idx1 = src.col_idx[j];
            for (int i = idx0; i < idx1; ++i) {
                int row = src.nz_rows[i];
                float val = src.nz_values[i];
                dst.unsafe_set(row, j - 1, val);
            }
            idx0 = idx1;
        }
        return dst;
    }

    public static FMatrixSparseCSC convert(FMatrixRMaj src, @Nullable FMatrixSparseCSC dst, float tol) {
        int nonzero = 0;
        int N = src.numRows * src.numCols;
        for (int i = 0; i < N; ++i) {
            if (src.data[i] == 0.0f) continue;
            ++nonzero;
        }
        dst = UtilEjml.reshapeOrDeclare(dst, src.numRows, src.numCols, nonzero);
        dst.nz_length = 0;
        dst.col_idx[0] = 0;
        for (int col = 0; col < src.numCols; ++col) {
            for (int row = 0; row < src.numRows; ++row) {
                float value = src.data[row * src.numCols + col];
                if (Math.abs(value) <= tol) continue;
                dst.nz_rows[dst.nz_length] = row;
                dst.nz_values[dst.nz_length] = value;
                ++dst.nz_length;
            }
            dst.col_idx[col + 1] = dst.nz_length;
        }
        return dst;
    }

    public static FMatrixSparseCSC convert(FMatrixSparseTriplet src, @Nullable FMatrixSparseCSC dst, @Nullable IGrowArray histStorage) {
        int i;
        dst = UtilEjml.reshapeOrDeclare(dst, src.numRows, src.numCols, src.nz_length);
        int[] hist = UtilEjml.adjustClear(histStorage, src.numCols);
        for (i = 0; i < src.nz_length; ++i) {
            int n = src.nz_rowcol.data[i * 2 + 1];
            hist[n] = hist[n] + 1;
        }
        dst.histogramToStructure(hist);
        System.arraycopy(dst.col_idx, 0, hist, 0, dst.numCols);
        for (i = 0; i < src.nz_length; ++i) {
            int row = src.nz_rowcol.data[i * 2];
            int col = src.nz_rowcol.data[i * 2 + 1];
            float value = src.nz_value.data[i];
            int n = col;
            hist[n] = hist[n] + 1;
            dst.nz_rows[index] = row;
            dst.nz_values[index] = value;
        }
        dst.indicesSorted = false;
        return dst;
    }

    public static FMatrixSparseCSC convert(FMatrixSparseTriplet src, @Nullable FMatrixSparseCSC dst) {
        return FConvertMatrixStruct.convert(src, dst, null);
    }

    public static FMatrixSparseTriplet convert(FMatrixSparseCSC src, @Nullable FMatrixSparseTriplet dst) {
        if (dst == null) {
            dst = new FMatrixSparseTriplet(src.numRows, src.numCols, src.nz_length);
        } else {
            dst.reshape(src.numRows, src.numCols);
        }
        int i0 = src.col_idx[0];
        for (int col = 0; col < src.numCols; ++col) {
            int i1 = src.col_idx[col + 1];
            for (int i = i0; i < i1; ++i) {
                int row = src.nz_rows[i];
                dst.addItem(row, col, src.nz_values[i]);
            }
            i0 = i1;
        }
        return dst;
    }
}

