/*
 * Decompiled with CFR 0.150.
 */
package org.ejml.ops;

import org.ejml.data.CMatrix;
import org.ejml.data.CMatrixRMaj;
import org.ejml.data.DMatrix;
import org.ejml.data.DMatrix2x2;
import org.ejml.data.DMatrix3x3;
import org.ejml.data.DMatrix4x4;
import org.ejml.data.DMatrixRMaj;
import org.ejml.data.FMatrix;
import org.ejml.data.FMatrix2x2;
import org.ejml.data.FMatrix3x3;
import org.ejml.data.FMatrix4x4;
import org.ejml.data.FMatrixRMaj;
import org.ejml.data.ZMatrix;
import org.ejml.data.ZMatrixRMaj;

public class ConvertMatrixData {
    public static void convert(DMatrix input, FMatrix output) {
        if (input.getNumRows() != output.getNumRows()) {
            throw new IllegalArgumentException("Number of rows do not match");
        }
        if (input.getNumCols() != output.getNumCols()) {
            throw new IllegalArgumentException("Number of columns do not match");
        }
        for (int i = 0; i < input.getNumRows(); ++i) {
            for (int j = 0; j < input.getNumCols(); ++j) {
                output.unsafe_set(i, j, (float)input.unsafe_get(i, j));
            }
        }
    }

    public static void convert(DMatrix input, ZMatrix output) {
        if (input.getNumRows() != output.getNumRows()) {
            throw new IllegalArgumentException("Number of rows do not match");
        }
        if (input.getNumCols() != output.getNumCols()) {
            throw new IllegalArgumentException("Number of columns do not match");
        }
        for (int i = 0; i < input.getNumRows(); ++i) {
            for (int j = 0; j < input.getNumCols(); ++j) {
                output.set(i, j, input.unsafe_get(i, j), 0.0);
            }
        }
    }

    public static void convert(DMatrix input, CMatrix output) {
        if (input.getNumRows() != output.getNumRows()) {
            throw new IllegalArgumentException("Number of rows do not match");
        }
        if (input.getNumCols() != output.getNumCols()) {
            throw new IllegalArgumentException("Number of columns do not match");
        }
        for (int i = 0; i < input.getNumRows(); ++i) {
            for (int j = 0; j < input.getNumCols(); ++j) {
                output.set(i, j, (float)input.unsafe_get(i, j), 0.0f);
            }
        }
    }

    public static void convert(FMatrix input, DMatrix output) {
        if (input.getNumRows() != output.getNumRows()) {
            throw new IllegalArgumentException("Number of rows do not match");
        }
        if (input.getNumCols() != output.getNumCols()) {
            throw new IllegalArgumentException("Number of columns do not match");
        }
        for (int i = 0; i < input.getNumRows(); ++i) {
            for (int j = 0; j < input.getNumCols(); ++j) {
                output.unsafe_set(i, j, input.unsafe_get(i, j));
            }
        }
    }

    public static void convert(FMatrix input, ZMatrix output) {
        if (input.getNumRows() != output.getNumRows()) {
            throw new IllegalArgumentException("Number of rows do not match");
        }
        if (input.getNumCols() != output.getNumCols()) {
            throw new IllegalArgumentException("Number of columns do not match");
        }
        for (int i = 0; i < input.getNumRows(); ++i) {
            for (int j = 0; j < input.getNumCols(); ++j) {
                output.set(i, j, input.unsafe_get(i, j), 0.0);
            }
        }
    }

    public static void convert(FMatrix input, CMatrix output) {
        if (input.getNumRows() != output.getNumRows()) {
            throw new IllegalArgumentException("Number of rows do not match");
        }
        if (input.getNumCols() != output.getNumCols()) {
            throw new IllegalArgumentException("Number of columns do not match");
        }
        for (int i = 0; i < input.getNumRows(); ++i) {
            for (int j = 0; j < input.getNumCols(); ++j) {
                output.set(i, j, input.unsafe_get(i, j), 0.0f);
            }
        }
    }

    public static void convert(ZMatrix input, CMatrix output) {
        if (input.getNumRows() != output.getNumRows()) {
            throw new IllegalArgumentException("Number of rows do not match");
        }
        if (input.getNumCols() != output.getNumCols()) {
            throw new IllegalArgumentException("Number of columns do not match");
        }
        for (int i = 0; i < input.getNumRows(); ++i) {
            for (int j = 0; j < input.getNumCols(); ++j) {
                output.set(i, j, (float)input.getReal(i, j), (float)input.getImag(i, j));
            }
        }
    }

    public static void convert(CMatrix input, ZMatrix output) {
        if (input.getNumRows() != output.getNumRows()) {
            throw new IllegalArgumentException("Number of rows do not match");
        }
        if (input.getNumCols() != output.getNumCols()) {
            throw new IllegalArgumentException("Number of columns do not match");
        }
        for (int i = 0; i < input.getNumRows(); ++i) {
            for (int j = 0; j < input.getNumCols(); ++j) {
                output.set(i, j, input.getReal(i, j), input.getImag(i, j));
            }
        }
    }

    public static void convert(DMatrixRMaj src, FMatrixRMaj dst) {
        int N = src.getNumElements();
        for (int i = 0; i < N; ++i) {
            dst.data[i] = (float)src.data[i];
        }
    }

    public static void convert(FMatrixRMaj src, DMatrixRMaj dst) {
        int N = src.getNumElements();
        for (int i = 0; i < N; ++i) {
            dst.data[i] = src.data[i];
        }
    }

    public static void convert(DMatrix2x2 src, FMatrix2x2 dst) {
        dst.a11 = (float)src.a11;
        dst.a12 = (float)src.a12;
        dst.a21 = (float)src.a21;
        dst.a22 = (float)src.a22;
    }

    public static void convert(DMatrix3x3 src, FMatrix3x3 dst) {
        dst.a11 = (float)src.a11;
        dst.a12 = (float)src.a12;
        dst.a13 = (float)src.a13;
        dst.a21 = (float)src.a21;
        dst.a22 = (float)src.a22;
        dst.a23 = (float)src.a23;
        dst.a31 = (float)src.a31;
        dst.a32 = (float)src.a32;
        dst.a33 = (float)src.a33;
    }

    public static void convert(DMatrix4x4 src, FMatrix4x4 dst) {
        dst.a11 = (float)src.a11;
        dst.a12 = (float)src.a12;
        dst.a13 = (float)src.a13;
        dst.a14 = (float)src.a14;
        dst.a21 = (float)src.a21;
        dst.a22 = (float)src.a22;
        dst.a23 = (float)src.a23;
        dst.a24 = (float)src.a24;
        dst.a31 = (float)src.a31;
        dst.a32 = (float)src.a32;
        dst.a33 = (float)src.a33;
        dst.a34 = (float)src.a34;
        dst.a41 = (float)src.a41;
        dst.a42 = (float)src.a42;
        dst.a43 = (float)src.a43;
        dst.a44 = (float)src.a44;
    }

    public static void convert(FMatrix2x2 src, DMatrix2x2 dst) {
        dst.a11 = src.a11;
        dst.a12 = src.a12;
        dst.a21 = src.a21;
        dst.a22 = src.a22;
    }

    public static void convert(FMatrix3x3 src, DMatrix3x3 dst) {
        dst.a11 = src.a11;
        dst.a12 = src.a12;
        dst.a13 = src.a13;
        dst.a21 = src.a21;
        dst.a22 = src.a22;
        dst.a23 = src.a23;
        dst.a31 = src.a31;
        dst.a32 = src.a32;
        dst.a33 = src.a33;
    }

    public static void convert(FMatrix4x4 src, DMatrix4x4 dst) {
        dst.a11 = src.a11;
        dst.a12 = src.a12;
        dst.a13 = src.a13;
        dst.a14 = src.a14;
        dst.a21 = src.a21;
        dst.a22 = src.a22;
        dst.a23 = src.a23;
        dst.a24 = src.a24;
        dst.a31 = src.a31;
        dst.a32 = src.a32;
        dst.a33 = src.a33;
        dst.a34 = src.a34;
        dst.a41 = src.a41;
        dst.a42 = src.a42;
        dst.a43 = src.a43;
        dst.a44 = src.a44;
    }

    public static void convert(DMatrixRMaj src, ZMatrixRMaj dst) {
        int N = src.getNumElements();
        int indexDst = 0;
        for (int i = 0; i < N; ++i) {
            dst.data[indexDst++] = src.data[i];
            dst.data[indexDst++] = 0.0;
        }
    }

    public static void convert(DMatrixRMaj src, CMatrixRMaj dst) {
        int N = src.getNumElements();
        int indexDst = 0;
        for (int i = 0; i < N; ++i) {
            dst.data[indexDst++] = (float)src.data[i];
            dst.data[indexDst++] = 0.0f;
        }
    }

    public static void convert(FMatrixRMaj src, ZMatrixRMaj dst) {
        int N = src.getNumElements();
        int indexDst = 0;
        for (int i = 0; i < N; ++i) {
            dst.data[indexDst++] = src.data[i];
            dst.data[indexDst++] = 0.0;
        }
    }

    public static void convert(FMatrixRMaj src, CMatrixRMaj dst) {
        int N = src.getNumElements();
        int indexDst = 0;
        for (int i = 0; i < N; ++i) {
            dst.data[indexDst++] = src.data[i];
            dst.data[indexDst++] = 0.0f;
        }
    }
}

