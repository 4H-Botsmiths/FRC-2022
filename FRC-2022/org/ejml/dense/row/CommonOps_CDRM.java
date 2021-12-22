/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  org.jetbrains.annotations.Nullable
 */
package org.ejml.dense.row;

import java.util.Arrays;
import org.ejml.EjmlParameters;
import org.ejml.LinearSolverSafe;
import org.ejml.UtilEjml;
import org.ejml.data.CMatrixD1;
import org.ejml.data.CMatrixRMaj;
import org.ejml.data.Complex_F32;
import org.ejml.data.FMatrixD1;
import org.ejml.data.FMatrixRMaj;
import org.ejml.data.Matrix;
import org.ejml.dense.row.MatrixFeatures_CDRM;
import org.ejml.dense.row.decompose.lu.LUDecompositionAlt_CDRM;
import org.ejml.dense.row.factory.LinearSolverFactory_CDRM;
import org.ejml.dense.row.misc.TransposeAlgs_CDRM;
import org.ejml.dense.row.mult.MatrixMatrixMult_CDRM;
import org.ejml.interfaces.linsol.LinearSolverDense;
import org.jetbrains.annotations.Nullable;

public class CommonOps_CDRM {
    public static CMatrixRMaj identity(int width) {
        CMatrixRMaj A = new CMatrixRMaj(width, width);
        for (int i = 0; i < width; ++i) {
            A.set(i, i, 1.0f, 0.0f);
        }
        return A;
    }

    public static CMatrixRMaj identity(int width, int height) {
        CMatrixRMaj A = new CMatrixRMaj(width, height);
        int m = Math.min(width, height);
        for (int i = 0; i < m; ++i) {
            A.set(i, i, 1.0f, 0.0f);
        }
        return A;
    }

    public static CMatrixRMaj diag(float ... data) {
        if (data.length % 2 == 1) {
            throw new IllegalArgumentException("must be an even number of arguments");
        }
        return CommonOps_CDRM.diag(new CMatrixRMaj(1, 1), data.length / 2, data);
    }

    public static CMatrixRMaj diag(@Nullable CMatrixRMaj output, int N, float ... data) {
        output = UtilEjml.reshapeOrDeclare(output, N, N);
        int index = 0;
        for (int i = 0; i < N; ++i) {
            output.set(i, i, data[index++], data[index++]);
        }
        return output;
    }

    public static void extractDiag(CMatrixRMaj src, CMatrixRMaj dst) {
        int N = Math.min(src.numRows, src.numCols);
        if (!MatrixFeatures_CDRM.isVector(dst) || dst.numCols * dst.numCols != N) {
            dst.reshape(N, 1);
        }
        for (int i = 0; i < N; ++i) {
            int index = src.getIndex(i, i);
            dst.data[i * 2] = src.data[index];
            dst.data[i * 2 + 1] = src.data[index + 1];
        }
    }

    public static void convert(FMatrixD1 input, CMatrixD1 output) {
        if (input.numCols != output.numCols || input.numRows != output.numRows) {
            throw new IllegalArgumentException("The matrices are not all the same dimension.");
        }
        Arrays.fill(output.data, 0, output.getDataLength(), 0.0f);
        int length = output.getDataLength();
        for (int i = 0; i < length; i += 2) {
            output.data[i] = input.data[i / 2];
        }
    }

    public static FMatrixRMaj stripReal(CMatrixD1 input, @Nullable FMatrixRMaj output) {
        output = UtilEjml.reshapeOrDeclare(output, input.numRows, input.numCols);
        int length = input.getDataLength();
        for (int i = 0; i < length; i += 2) {
            output.data[i / 2] = input.data[i];
        }
        return output;
    }

    public static FMatrixRMaj stripImaginary(CMatrixD1 input, @Nullable FMatrixRMaj output) {
        output = UtilEjml.reshapeOrDeclare(output, input.numRows, input.numCols);
        int length = input.getDataLength();
        for (int i = 1; i < length; i += 2) {
            output.data[i / 2] = input.data[i];
        }
        return output;
    }

    public static void magnitude(CMatrixD1 input, FMatrixD1 output) {
        output.reshape(input.numRows, input.numCols);
        int length = input.getDataLength();
        for (int i = 0; i < length; i += 2) {
            float real = input.data[i];
            float imaginary = input.data[i + 1];
            output.data[i / 2] = (float)Math.sqrt(real * real + imaginary * imaginary);
        }
    }

    public static CMatrixD1 conjugate(CMatrixD1 input, @Nullable CMatrixRMaj output) {
        output = UtilEjml.reshapeOrDeclare(output, input.numRows, input.numCols);
        int length = input.getDataLength();
        for (int i = 0; i < length; i += 2) {
            output.data[i] = input.data[i];
            output.data[i + 1] = -input.data[i + 1];
        }
        return output;
    }

    public static void fill(CMatrixD1 a, float real, float imaginary) {
        int N = a.getDataLength();
        for (int i = 0; i < N; i += 2) {
            a.data[i] = real;
            a.data[i + 1] = imaginary;
        }
    }

    public static void add(CMatrixD1 a, CMatrixD1 b, CMatrixD1 c) {
        UtilEjml.checkSameShape((Matrix)a, (Matrix)b, true);
        c.reshape(a.numRows, b.numCols);
        int length = a.getDataLength();
        for (int i = 0; i < length; ++i) {
            c.data[i] = a.data[i] + b.data[i];
        }
    }

    public static void subtract(CMatrixD1 a, CMatrixD1 b, CMatrixD1 c) {
        UtilEjml.checkSameShape((Matrix)a, (Matrix)b, true);
        c.reshape(a.numRows, b.numCols);
        int length = a.getDataLength();
        for (int i = 0; i < length; ++i) {
            c.data[i] = a.data[i] - b.data[i];
        }
    }

    public static void scale(float alphaReal, float alphaImag, CMatrixD1 a) {
        int size = a.getNumElements() * 2;
        for (int i = 0; i < size; i += 2) {
            float real = a.data[i];
            float imag = a.data[i + 1];
            a.data[i] = real * alphaReal - imag * alphaImag;
            a.data[i + 1] = real * alphaImag + imag * alphaReal;
        }
    }

    public static void mult(CMatrixRMaj a, CMatrixRMaj b, CMatrixRMaj c) {
        if (b.numCols >= EjmlParameters.CMULT_COLUMN_SWITCH) {
            MatrixMatrixMult_CDRM.mult_reorder(a, b, c);
        } else {
            MatrixMatrixMult_CDRM.mult_small(a, b, c);
        }
    }

    public static void mult(float realAlpha, float imgAlpha, CMatrixRMaj a, CMatrixRMaj b, CMatrixRMaj c) {
        if (b.numCols >= EjmlParameters.CMULT_COLUMN_SWITCH) {
            MatrixMatrixMult_CDRM.mult_reorder(realAlpha, imgAlpha, a, b, c);
        } else {
            MatrixMatrixMult_CDRM.mult_small(realAlpha, imgAlpha, a, b, c);
        }
    }

    public static void multAdd(CMatrixRMaj a, CMatrixRMaj b, CMatrixRMaj c) {
        if (b.numCols >= EjmlParameters.MULT_COLUMN_SWITCH) {
            MatrixMatrixMult_CDRM.multAdd_reorder(a, b, c);
        } else {
            MatrixMatrixMult_CDRM.multAdd_small(a, b, c);
        }
    }

    public static void multAdd(float realAlpha, float imgAlpha, CMatrixRMaj a, CMatrixRMaj b, CMatrixRMaj c) {
        if (b.numCols >= EjmlParameters.CMULT_COLUMN_SWITCH) {
            MatrixMatrixMult_CDRM.multAdd_reorder(realAlpha, imgAlpha, a, b, c);
        } else {
            MatrixMatrixMult_CDRM.multAdd_small(realAlpha, imgAlpha, a, b, c);
        }
    }

    public static void multTransA(CMatrixRMaj a, CMatrixRMaj b, CMatrixRMaj c) {
        if (a.numCols >= EjmlParameters.CMULT_COLUMN_SWITCH || b.numCols >= EjmlParameters.CMULT_COLUMN_SWITCH) {
            MatrixMatrixMult_CDRM.multTransA_reorder(a, b, c);
        } else {
            MatrixMatrixMult_CDRM.multTransA_small(a, b, c);
        }
    }

    public static void multTransA(float realAlpha, float imagAlpha, CMatrixRMaj a, CMatrixRMaj b, CMatrixRMaj c) {
        if (a.numCols >= EjmlParameters.CMULT_COLUMN_SWITCH || b.numCols >= EjmlParameters.CMULT_COLUMN_SWITCH) {
            MatrixMatrixMult_CDRM.multTransA_reorder(realAlpha, imagAlpha, a, b, c);
        } else {
            MatrixMatrixMult_CDRM.multTransA_small(realAlpha, imagAlpha, a, b, c);
        }
    }

    public static void multTransB(CMatrixRMaj a, CMatrixRMaj b, CMatrixRMaj c) {
        MatrixMatrixMult_CDRM.multTransB(a, b, c);
    }

    public static void multTransB(float realAlpha, float imagAlpha, CMatrixRMaj a, CMatrixRMaj b, CMatrixRMaj c) {
        MatrixMatrixMult_CDRM.multTransB(realAlpha, imagAlpha, a, b, c);
    }

    public static void multTransAB(CMatrixRMaj a, CMatrixRMaj b, CMatrixRMaj c) {
        if (a.numCols >= EjmlParameters.CMULT_TRANAB_COLUMN_SWITCH) {
            MatrixMatrixMult_CDRM.multTransAB_aux(a, b, c, null);
        } else {
            MatrixMatrixMult_CDRM.multTransAB(a, b, c);
        }
    }

    public static void multTransAB(float realAlpha, float imagAlpha, CMatrixRMaj a, CMatrixRMaj b, CMatrixRMaj c) {
        if (a.numCols >= EjmlParameters.CMULT_TRANAB_COLUMN_SWITCH) {
            MatrixMatrixMult_CDRM.multTransAB_aux(realAlpha, imagAlpha, a, b, c, null);
        } else {
            MatrixMatrixMult_CDRM.multTransAB(realAlpha, imagAlpha, a, b, c);
        }
    }

    public static void multAddTransA(CMatrixRMaj a, CMatrixRMaj b, CMatrixRMaj c) {
        if (a.numCols >= EjmlParameters.CMULT_COLUMN_SWITCH || b.numCols >= EjmlParameters.CMULT_COLUMN_SWITCH) {
            MatrixMatrixMult_CDRM.multAddTransA_reorder(a, b, c);
        } else {
            MatrixMatrixMult_CDRM.multAddTransA_small(a, b, c);
        }
    }

    public static void multAddTransA(float realAlpha, float imagAlpha, CMatrixRMaj a, CMatrixRMaj b, CMatrixRMaj c) {
        if (a.numCols >= EjmlParameters.CMULT_COLUMN_SWITCH || b.numCols >= EjmlParameters.CMULT_COLUMN_SWITCH) {
            MatrixMatrixMult_CDRM.multAddTransA_reorder(realAlpha, imagAlpha, a, b, c);
        } else {
            MatrixMatrixMult_CDRM.multAddTransA_small(realAlpha, imagAlpha, a, b, c);
        }
    }

    public static void multAddTransB(CMatrixRMaj a, CMatrixRMaj b, CMatrixRMaj c) {
        MatrixMatrixMult_CDRM.multAddTransB(a, b, c);
    }

    public static void multAddTransB(float realAlpha, float imagAlpha, CMatrixRMaj a, CMatrixRMaj b, CMatrixRMaj c) {
        MatrixMatrixMult_CDRM.multAddTransB(realAlpha, imagAlpha, a, b, c);
    }

    public static void multAddTransAB(CMatrixRMaj a, CMatrixRMaj b, CMatrixRMaj c) {
        if (a.numCols >= EjmlParameters.CMULT_TRANAB_COLUMN_SWITCH) {
            MatrixMatrixMult_CDRM.multAddTransAB_aux(a, b, c, null);
        } else {
            MatrixMatrixMult_CDRM.multAddTransAB(a, b, c);
        }
    }

    public static void multAddTransAB(float realAlpha, float imagAlpha, CMatrixRMaj a, CMatrixRMaj b, CMatrixRMaj c) {
        if (a.numCols >= EjmlParameters.CMULT_TRANAB_COLUMN_SWITCH) {
            MatrixMatrixMult_CDRM.multAddTransAB_aux(realAlpha, imagAlpha, a, b, c, null);
        } else {
            MatrixMatrixMult_CDRM.multAddTransAB(realAlpha, imagAlpha, a, b, c);
        }
    }

    public static void transpose(CMatrixRMaj mat) {
        if (mat.numCols == mat.numRows) {
            TransposeAlgs_CDRM.square(mat);
        } else {
            CMatrixRMaj b = new CMatrixRMaj(mat.numCols, mat.numRows);
            CommonOps_CDRM.transpose(mat, b);
            mat.reshape(b.numRows, b.numCols);
            mat.setTo(b);
        }
    }

    public static void transposeConjugate(CMatrixRMaj mat) {
        if (mat.numCols == mat.numRows) {
            TransposeAlgs_CDRM.squareConjugate(mat);
        } else {
            CMatrixRMaj b = new CMatrixRMaj(mat.numCols, mat.numRows);
            CommonOps_CDRM.transposeConjugate(mat, b);
            mat.reshape(b.numRows, b.numCols);
            mat.setTo(b);
        }
    }

    public static CMatrixRMaj transpose(CMatrixRMaj input, @Nullable CMatrixRMaj output) {
        output = UtilEjml.reshapeOrDeclare(output, input.numCols, input.numRows);
        TransposeAlgs_CDRM.standard(input, output);
        return output;
    }

    public static CMatrixRMaj transposeConjugate(CMatrixRMaj input, @Nullable CMatrixRMaj output) {
        output = UtilEjml.reshapeOrDeclare(output, input.numCols, input.numRows);
        TransposeAlgs_CDRM.standardConjugate(input, output);
        return output;
    }

    public static boolean invert(CMatrixRMaj A) {
        LinearSolverDense<CMatrixRMaj> solver = LinearSolverFactory_CDRM.lu(A.numRows);
        if (!solver.setA(A)) {
            return false;
        }
        solver.invert(A);
        return true;
    }

    public static boolean invert(CMatrixRMaj input, CMatrixRMaj output) {
        LinearSolverDense<CMatrixRMaj> solver = LinearSolverFactory_CDRM.lu(input.numRows);
        if (solver.modifiesA()) {
            input = input.copy();
        }
        if (!solver.setA(input)) {
            return false;
        }
        solver.invert(output);
        return true;
    }

    public static boolean solve(CMatrixRMaj a, CMatrixRMaj b, CMatrixRMaj x) {
        LinearSolverDense<CMatrixRMaj> solver = a.numCols == a.numRows ? LinearSolverFactory_CDRM.lu(a.numRows) : LinearSolverFactory_CDRM.qr(a.numRows, a.numCols);
        if (!(solver = new LinearSolverSafe<CMatrixRMaj>(solver)).setA(a)) {
            return false;
        }
        solver.solve(b, x);
        return true;
    }

    public static Complex_F32 det(CMatrixRMaj mat) {
        LUDecompositionAlt_CDRM alg = new LUDecompositionAlt_CDRM();
        if (alg.inputModified()) {
            mat = mat.copy();
        }
        if (!alg.decompose(mat)) {
            return new Complex_F32();
        }
        return alg.computeDeterminant();
    }

    public static CMatrixRMaj elementMultiply(CMatrixD1 input, float real, float imaginary, @Nullable CMatrixRMaj output) {
        output = UtilEjml.reshapeOrDeclare(output, input.numRows, input.numCols);
        int N = input.getDataLength();
        for (int i = 0; i < N; i += 2) {
            float inReal = input.data[i];
            float intImag = input.data[i + 1];
            output.data[i] = inReal * real - intImag * imaginary;
            output.data[i + 1] = inReal * imaginary + intImag * real;
        }
        return output;
    }

    public static CMatrixRMaj elementDivide(CMatrixD1 input, float real, float imaginary, @Nullable CMatrixRMaj output) {
        output = UtilEjml.reshapeOrDeclare(output, input.numRows, input.numCols);
        float norm = real * real + imaginary * imaginary;
        int N = input.getDataLength();
        for (int i = 0; i < N; i += 2) {
            float inReal = input.data[i];
            float inImag = input.data[i + 1];
            output.data[i] = (inReal * real + inImag * imaginary) / norm;
            output.data[i + 1] = (inImag * real - inReal * imaginary) / norm;
        }
        return output;
    }

    public static CMatrixRMaj elementDivide(float real, float imaginary, CMatrixD1 input, @Nullable CMatrixRMaj output) {
        output = UtilEjml.reshapeOrDeclare(output, input.numRows, input.numCols);
        int N = input.getDataLength();
        for (int i = 0; i < N; i += 2) {
            float inReal = input.data[i];
            float inImag = input.data[i + 1];
            float norm = inReal * inReal + inImag * inImag;
            output.data[i] = (real * inReal + imaginary * inImag) / norm;
            output.data[i + 1] = (imaginary * inReal - real * inImag) / norm;
        }
        return output;
    }

    public static float elementMinReal(CMatrixD1 a) {
        int size = a.getDataLength();
        float min = a.data[0];
        for (int i = 2; i < size; i += 2) {
            float val = a.data[i];
            if (!(val < min)) continue;
            min = val;
        }
        return min;
    }

    public static float elementMinImaginary(CMatrixD1 a) {
        int size = a.getDataLength();
        float min = a.data[1];
        for (int i = 3; i < size; i += 2) {
            float val = a.data[i];
            if (!(val < min)) continue;
            min = val;
        }
        return min;
    }

    public static float elementMaxReal(CMatrixD1 a) {
        int size = a.getDataLength();
        float max = a.data[0];
        for (int i = 2; i < size; i += 2) {
            float val = a.data[i];
            if (!(val > max)) continue;
            max = val;
        }
        return max;
    }

    public static float elementMaxImaginary(CMatrixD1 a) {
        int size = a.getDataLength();
        float max = a.data[1];
        for (int i = 3; i < size; i += 2) {
            float val = a.data[i];
            if (!(val > max)) continue;
            max = val;
        }
        return max;
    }

    public static float elementMaxMagnitude2(CMatrixD1 a) {
        int size = a.getDataLength();
        float max = 0.0f;
        int i = 0;
        while (i < size) {
            float real = a.data[i++];
            int n = i++;
            float imaginary = a.data[n];
            float m = real * real + imaginary * imaginary;
            if (!(m > max)) continue;
            max = m;
        }
        return max;
    }

    public static void setIdentity(CMatrixRMaj mat) {
        int width = mat.numRows < mat.numCols ? mat.numRows : mat.numCols;
        Arrays.fill(mat.data, 0, mat.getDataLength(), 0.0f);
        int index = 0;
        int stride = mat.getRowStride();
        int i = 0;
        while (i < width) {
            mat.data[index] = 1.0f;
            ++i;
            index += stride + 2;
        }
    }

    public static CMatrixRMaj extract(CMatrixRMaj src, int srcY0, int srcY1, int srcX0, int srcX1) {
        if (srcY1 <= srcY0 || srcY0 < 0 || srcY1 > src.numRows) {
            throw new IllegalArgumentException("srcY1 <= srcY0 || srcY0 < 0 || srcY1 > src.numRows");
        }
        if (srcX1 <= srcX0 || srcX0 < 0 || srcX1 > src.numCols) {
            throw new IllegalArgumentException("srcX1 <= srcX0 || srcX0 < 0 || srcX1 > src.numCols");
        }
        int w = srcX1 - srcX0;
        int h = srcY1 - srcY0;
        CMatrixRMaj dst = new CMatrixRMaj(h, w);
        CommonOps_CDRM.extract(src, srcY0, srcY1, srcX0, srcX1, dst, 0, 0);
        return dst;
    }

    public static void extract(CMatrixRMaj src, int srcY0, int srcY1, int srcX0, int srcX1, CMatrixRMaj dst, int dstY0, int dstX0) {
        int numRows = srcY1 - srcY0;
        int stride = (srcX1 - srcX0) * 2;
        for (int y = 0; y < numRows; ++y) {
            int indexSrc = src.getIndex(y + srcY0, srcX0);
            int indexDst = dst.getIndex(y + dstY0, dstX0);
            System.arraycopy(src.data, indexSrc, dst.data, indexDst, stride);
        }
    }

    public static CMatrixRMaj[] columnsToVector(CMatrixRMaj A, @Nullable CMatrixRMaj[] v) {
        CMatrixRMaj[] ret = v == null || v.length < A.numCols ? new CMatrixRMaj[A.numCols] : v;
        for (int i = 0; i < ret.length; ++i) {
            if (ret[i] == null) {
                ret[i] = new CMatrixRMaj(A.numRows, 1);
            } else {
                ret[i].reshape(A.numRows, 1);
            }
            CMatrixRMaj u = ret[i];
            int indexU = 0;
            for (int j = 0; j < A.numRows; ++j) {
                int indexA = A.getIndex(j, i);
                u.data[indexU++] = A.data[indexA++];
                u.data[indexU++] = A.data[indexA];
            }
        }
        return ret;
    }

    public static float elementMaxAbs(CMatrixRMaj a) {
        int size = a.getDataLength();
        float max = 0.0f;
        for (int i = 0; i < size; i += 2) {
            float real = a.data[i];
            float imag = a.data[i + 1];
            float val = real * real + imag * imag;
            if (!(val > max)) continue;
            max = val;
        }
        return (float)Math.sqrt(max);
    }

    public static float elementMinAbs(CMatrixRMaj a) {
        int size = a.getDataLength();
        float min = Float.MAX_VALUE;
        for (int i = 0; i < size; i += 2) {
            float real = a.data[i];
            float imag = a.data[i + 1];
            float val = real * real + imag * imag;
            if (!(val < min)) continue;
            min = val;
        }
        return (float)Math.sqrt(min);
    }
}

