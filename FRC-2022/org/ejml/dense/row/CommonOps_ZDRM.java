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
import org.ejml.data.Complex_F64;
import org.ejml.data.DMatrixD1;
import org.ejml.data.DMatrixRMaj;
import org.ejml.data.Matrix;
import org.ejml.data.ZMatrixD1;
import org.ejml.data.ZMatrixRMaj;
import org.ejml.dense.row.MatrixFeatures_ZDRM;
import org.ejml.dense.row.decompose.lu.LUDecompositionAlt_ZDRM;
import org.ejml.dense.row.factory.LinearSolverFactory_ZDRM;
import org.ejml.dense.row.misc.TransposeAlgs_ZDRM;
import org.ejml.dense.row.mult.MatrixMatrixMult_ZDRM;
import org.ejml.interfaces.linsol.LinearSolverDense;
import org.jetbrains.annotations.Nullable;

public class CommonOps_ZDRM {
    public static ZMatrixRMaj identity(int width) {
        ZMatrixRMaj A = new ZMatrixRMaj(width, width);
        for (int i = 0; i < width; ++i) {
            A.set(i, i, 1.0, 0.0);
        }
        return A;
    }

    public static ZMatrixRMaj identity(int width, int height) {
        ZMatrixRMaj A = new ZMatrixRMaj(width, height);
        int m = Math.min(width, height);
        for (int i = 0; i < m; ++i) {
            A.set(i, i, 1.0, 0.0);
        }
        return A;
    }

    public static ZMatrixRMaj diag(double ... data) {
        if (data.length % 2 == 1) {
            throw new IllegalArgumentException("must be an even number of arguments");
        }
        return CommonOps_ZDRM.diag(new ZMatrixRMaj(1, 1), data.length / 2, data);
    }

    public static ZMatrixRMaj diag(@Nullable ZMatrixRMaj output, int N, double ... data) {
        output = UtilEjml.reshapeOrDeclare(output, N, N);
        int index = 0;
        for (int i = 0; i < N; ++i) {
            output.set(i, i, data[index++], data[index++]);
        }
        return output;
    }

    public static void extractDiag(ZMatrixRMaj src, ZMatrixRMaj dst) {
        int N = Math.min(src.numRows, src.numCols);
        if (!MatrixFeatures_ZDRM.isVector(dst) || dst.numCols * dst.numCols != N) {
            dst.reshape(N, 1);
        }
        for (int i = 0; i < N; ++i) {
            int index = src.getIndex(i, i);
            dst.data[i * 2] = src.data[index];
            dst.data[i * 2 + 1] = src.data[index + 1];
        }
    }

    public static void convert(DMatrixD1 input, ZMatrixD1 output) {
        if (input.numCols != output.numCols || input.numRows != output.numRows) {
            throw new IllegalArgumentException("The matrices are not all the same dimension.");
        }
        Arrays.fill(output.data, 0, output.getDataLength(), 0.0);
        int length = output.getDataLength();
        for (int i = 0; i < length; i += 2) {
            output.data[i] = input.data[i / 2];
        }
    }

    public static DMatrixRMaj stripReal(ZMatrixD1 input, @Nullable DMatrixRMaj output) {
        output = UtilEjml.reshapeOrDeclare(output, input.numRows, input.numCols);
        int length = input.getDataLength();
        for (int i = 0; i < length; i += 2) {
            output.data[i / 2] = input.data[i];
        }
        return output;
    }

    public static DMatrixRMaj stripImaginary(ZMatrixD1 input, @Nullable DMatrixRMaj output) {
        output = UtilEjml.reshapeOrDeclare(output, input.numRows, input.numCols);
        int length = input.getDataLength();
        for (int i = 1; i < length; i += 2) {
            output.data[i / 2] = input.data[i];
        }
        return output;
    }

    public static void magnitude(ZMatrixD1 input, DMatrixD1 output) {
        output.reshape(input.numRows, input.numCols);
        int length = input.getDataLength();
        for (int i = 0; i < length; i += 2) {
            double real = input.data[i];
            double imaginary = input.data[i + 1];
            output.data[i / 2] = Math.sqrt(real * real + imaginary * imaginary);
        }
    }

    public static ZMatrixD1 conjugate(ZMatrixD1 input, @Nullable ZMatrixRMaj output) {
        output = UtilEjml.reshapeOrDeclare(output, input.numRows, input.numCols);
        int length = input.getDataLength();
        for (int i = 0; i < length; i += 2) {
            output.data[i] = input.data[i];
            output.data[i + 1] = -input.data[i + 1];
        }
        return output;
    }

    public static void fill(ZMatrixD1 a, double real, double imaginary) {
        int N = a.getDataLength();
        for (int i = 0; i < N; i += 2) {
            a.data[i] = real;
            a.data[i + 1] = imaginary;
        }
    }

    public static void add(ZMatrixD1 a, ZMatrixD1 b, ZMatrixD1 c) {
        UtilEjml.checkSameShape((Matrix)a, (Matrix)b, true);
        c.reshape(a.numRows, b.numCols);
        int length = a.getDataLength();
        for (int i = 0; i < length; ++i) {
            c.data[i] = a.data[i] + b.data[i];
        }
    }

    public static void subtract(ZMatrixD1 a, ZMatrixD1 b, ZMatrixD1 c) {
        UtilEjml.checkSameShape((Matrix)a, (Matrix)b, true);
        c.reshape(a.numRows, b.numCols);
        int length = a.getDataLength();
        for (int i = 0; i < length; ++i) {
            c.data[i] = a.data[i] - b.data[i];
        }
    }

    public static void scale(double alphaReal, double alphaImag, ZMatrixD1 a) {
        int size = a.getNumElements() * 2;
        for (int i = 0; i < size; i += 2) {
            double real = a.data[i];
            double imag = a.data[i + 1];
            a.data[i] = real * alphaReal - imag * alphaImag;
            a.data[i + 1] = real * alphaImag + imag * alphaReal;
        }
    }

    public static void mult(ZMatrixRMaj a, ZMatrixRMaj b, ZMatrixRMaj c) {
        if (b.numCols >= EjmlParameters.CMULT_COLUMN_SWITCH) {
            MatrixMatrixMult_ZDRM.mult_reorder(a, b, c);
        } else {
            MatrixMatrixMult_ZDRM.mult_small(a, b, c);
        }
    }

    public static void mult(double realAlpha, double imgAlpha, ZMatrixRMaj a, ZMatrixRMaj b, ZMatrixRMaj c) {
        if (b.numCols >= EjmlParameters.CMULT_COLUMN_SWITCH) {
            MatrixMatrixMult_ZDRM.mult_reorder(realAlpha, imgAlpha, a, b, c);
        } else {
            MatrixMatrixMult_ZDRM.mult_small(realAlpha, imgAlpha, a, b, c);
        }
    }

    public static void multAdd(ZMatrixRMaj a, ZMatrixRMaj b, ZMatrixRMaj c) {
        if (b.numCols >= EjmlParameters.MULT_COLUMN_SWITCH) {
            MatrixMatrixMult_ZDRM.multAdd_reorder(a, b, c);
        } else {
            MatrixMatrixMult_ZDRM.multAdd_small(a, b, c);
        }
    }

    public static void multAdd(double realAlpha, double imgAlpha, ZMatrixRMaj a, ZMatrixRMaj b, ZMatrixRMaj c) {
        if (b.numCols >= EjmlParameters.CMULT_COLUMN_SWITCH) {
            MatrixMatrixMult_ZDRM.multAdd_reorder(realAlpha, imgAlpha, a, b, c);
        } else {
            MatrixMatrixMult_ZDRM.multAdd_small(realAlpha, imgAlpha, a, b, c);
        }
    }

    public static void multTransA(ZMatrixRMaj a, ZMatrixRMaj b, ZMatrixRMaj c) {
        if (a.numCols >= EjmlParameters.CMULT_COLUMN_SWITCH || b.numCols >= EjmlParameters.CMULT_COLUMN_SWITCH) {
            MatrixMatrixMult_ZDRM.multTransA_reorder(a, b, c);
        } else {
            MatrixMatrixMult_ZDRM.multTransA_small(a, b, c);
        }
    }

    public static void multTransA(double realAlpha, double imagAlpha, ZMatrixRMaj a, ZMatrixRMaj b, ZMatrixRMaj c) {
        if (a.numCols >= EjmlParameters.CMULT_COLUMN_SWITCH || b.numCols >= EjmlParameters.CMULT_COLUMN_SWITCH) {
            MatrixMatrixMult_ZDRM.multTransA_reorder(realAlpha, imagAlpha, a, b, c);
        } else {
            MatrixMatrixMult_ZDRM.multTransA_small(realAlpha, imagAlpha, a, b, c);
        }
    }

    public static void multTransB(ZMatrixRMaj a, ZMatrixRMaj b, ZMatrixRMaj c) {
        MatrixMatrixMult_ZDRM.multTransB(a, b, c);
    }

    public static void multTransB(double realAlpha, double imagAlpha, ZMatrixRMaj a, ZMatrixRMaj b, ZMatrixRMaj c) {
        MatrixMatrixMult_ZDRM.multTransB(realAlpha, imagAlpha, a, b, c);
    }

    public static void multTransAB(ZMatrixRMaj a, ZMatrixRMaj b, ZMatrixRMaj c) {
        if (a.numCols >= EjmlParameters.CMULT_TRANAB_COLUMN_SWITCH) {
            MatrixMatrixMult_ZDRM.multTransAB_aux(a, b, c, null);
        } else {
            MatrixMatrixMult_ZDRM.multTransAB(a, b, c);
        }
    }

    public static void multTransAB(double realAlpha, double imagAlpha, ZMatrixRMaj a, ZMatrixRMaj b, ZMatrixRMaj c) {
        if (a.numCols >= EjmlParameters.CMULT_TRANAB_COLUMN_SWITCH) {
            MatrixMatrixMult_ZDRM.multTransAB_aux(realAlpha, imagAlpha, a, b, c, null);
        } else {
            MatrixMatrixMult_ZDRM.multTransAB(realAlpha, imagAlpha, a, b, c);
        }
    }

    public static void multAddTransA(ZMatrixRMaj a, ZMatrixRMaj b, ZMatrixRMaj c) {
        if (a.numCols >= EjmlParameters.CMULT_COLUMN_SWITCH || b.numCols >= EjmlParameters.CMULT_COLUMN_SWITCH) {
            MatrixMatrixMult_ZDRM.multAddTransA_reorder(a, b, c);
        } else {
            MatrixMatrixMult_ZDRM.multAddTransA_small(a, b, c);
        }
    }

    public static void multAddTransA(double realAlpha, double imagAlpha, ZMatrixRMaj a, ZMatrixRMaj b, ZMatrixRMaj c) {
        if (a.numCols >= EjmlParameters.CMULT_COLUMN_SWITCH || b.numCols >= EjmlParameters.CMULT_COLUMN_SWITCH) {
            MatrixMatrixMult_ZDRM.multAddTransA_reorder(realAlpha, imagAlpha, a, b, c);
        } else {
            MatrixMatrixMult_ZDRM.multAddTransA_small(realAlpha, imagAlpha, a, b, c);
        }
    }

    public static void multAddTransB(ZMatrixRMaj a, ZMatrixRMaj b, ZMatrixRMaj c) {
        MatrixMatrixMult_ZDRM.multAddTransB(a, b, c);
    }

    public static void multAddTransB(double realAlpha, double imagAlpha, ZMatrixRMaj a, ZMatrixRMaj b, ZMatrixRMaj c) {
        MatrixMatrixMult_ZDRM.multAddTransB(realAlpha, imagAlpha, a, b, c);
    }

    public static void multAddTransAB(ZMatrixRMaj a, ZMatrixRMaj b, ZMatrixRMaj c) {
        if (a.numCols >= EjmlParameters.CMULT_TRANAB_COLUMN_SWITCH) {
            MatrixMatrixMult_ZDRM.multAddTransAB_aux(a, b, c, null);
        } else {
            MatrixMatrixMult_ZDRM.multAddTransAB(a, b, c);
        }
    }

    public static void multAddTransAB(double realAlpha, double imagAlpha, ZMatrixRMaj a, ZMatrixRMaj b, ZMatrixRMaj c) {
        if (a.numCols >= EjmlParameters.CMULT_TRANAB_COLUMN_SWITCH) {
            MatrixMatrixMult_ZDRM.multAddTransAB_aux(realAlpha, imagAlpha, a, b, c, null);
        } else {
            MatrixMatrixMult_ZDRM.multAddTransAB(realAlpha, imagAlpha, a, b, c);
        }
    }

    public static void transpose(ZMatrixRMaj mat) {
        if (mat.numCols == mat.numRows) {
            TransposeAlgs_ZDRM.square(mat);
        } else {
            ZMatrixRMaj b = new ZMatrixRMaj(mat.numCols, mat.numRows);
            CommonOps_ZDRM.transpose(mat, b);
            mat.reshape(b.numRows, b.numCols);
            mat.setTo(b);
        }
    }

    public static void transposeConjugate(ZMatrixRMaj mat) {
        if (mat.numCols == mat.numRows) {
            TransposeAlgs_ZDRM.squareConjugate(mat);
        } else {
            ZMatrixRMaj b = new ZMatrixRMaj(mat.numCols, mat.numRows);
            CommonOps_ZDRM.transposeConjugate(mat, b);
            mat.reshape(b.numRows, b.numCols);
            mat.setTo(b);
        }
    }

    public static ZMatrixRMaj transpose(ZMatrixRMaj input, @Nullable ZMatrixRMaj output) {
        output = UtilEjml.reshapeOrDeclare(output, input.numCols, input.numRows);
        TransposeAlgs_ZDRM.standard(input, output);
        return output;
    }

    public static ZMatrixRMaj transposeConjugate(ZMatrixRMaj input, @Nullable ZMatrixRMaj output) {
        output = UtilEjml.reshapeOrDeclare(output, input.numCols, input.numRows);
        TransposeAlgs_ZDRM.standardConjugate(input, output);
        return output;
    }

    public static boolean invert(ZMatrixRMaj A) {
        LinearSolverDense<ZMatrixRMaj> solver = LinearSolverFactory_ZDRM.lu(A.numRows);
        if (!solver.setA(A)) {
            return false;
        }
        solver.invert(A);
        return true;
    }

    public static boolean invert(ZMatrixRMaj input, ZMatrixRMaj output) {
        LinearSolverDense<ZMatrixRMaj> solver = LinearSolverFactory_ZDRM.lu(input.numRows);
        if (solver.modifiesA()) {
            input = input.copy();
        }
        if (!solver.setA(input)) {
            return false;
        }
        solver.invert(output);
        return true;
    }

    public static boolean solve(ZMatrixRMaj a, ZMatrixRMaj b, ZMatrixRMaj x) {
        LinearSolverDense<ZMatrixRMaj> solver = a.numCols == a.numRows ? LinearSolverFactory_ZDRM.lu(a.numRows) : LinearSolverFactory_ZDRM.qr(a.numRows, a.numCols);
        if (!(solver = new LinearSolverSafe<ZMatrixRMaj>(solver)).setA(a)) {
            return false;
        }
        solver.solve(b, x);
        return true;
    }

    public static Complex_F64 det(ZMatrixRMaj mat) {
        LUDecompositionAlt_ZDRM alg = new LUDecompositionAlt_ZDRM();
        if (alg.inputModified()) {
            mat = mat.copy();
        }
        if (!alg.decompose(mat)) {
            return new Complex_F64();
        }
        return alg.computeDeterminant();
    }

    public static ZMatrixRMaj elementMultiply(ZMatrixD1 input, double real, double imaginary, @Nullable ZMatrixRMaj output) {
        output = UtilEjml.reshapeOrDeclare(output, input.numRows, input.numCols);
        int N = input.getDataLength();
        for (int i = 0; i < N; i += 2) {
            double inReal = input.data[i];
            double intImag = input.data[i + 1];
            output.data[i] = inReal * real - intImag * imaginary;
            output.data[i + 1] = inReal * imaginary + intImag * real;
        }
        return output;
    }

    public static ZMatrixRMaj elementDivide(ZMatrixD1 input, double real, double imaginary, @Nullable ZMatrixRMaj output) {
        output = UtilEjml.reshapeOrDeclare(output, input.numRows, input.numCols);
        double norm = real * real + imaginary * imaginary;
        int N = input.getDataLength();
        for (int i = 0; i < N; i += 2) {
            double inReal = input.data[i];
            double inImag = input.data[i + 1];
            output.data[i] = (inReal * real + inImag * imaginary) / norm;
            output.data[i + 1] = (inImag * real - inReal * imaginary) / norm;
        }
        return output;
    }

    public static ZMatrixRMaj elementDivide(double real, double imaginary, ZMatrixD1 input, @Nullable ZMatrixRMaj output) {
        output = UtilEjml.reshapeOrDeclare(output, input.numRows, input.numCols);
        int N = input.getDataLength();
        for (int i = 0; i < N; i += 2) {
            double inReal = input.data[i];
            double inImag = input.data[i + 1];
            double norm = inReal * inReal + inImag * inImag;
            output.data[i] = (real * inReal + imaginary * inImag) / norm;
            output.data[i + 1] = (imaginary * inReal - real * inImag) / norm;
        }
        return output;
    }

    public static double elementMinReal(ZMatrixD1 a) {
        int size = a.getDataLength();
        double min = a.data[0];
        for (int i = 2; i < size; i += 2) {
            double val = a.data[i];
            if (!(val < min)) continue;
            min = val;
        }
        return min;
    }

    public static double elementMinImaginary(ZMatrixD1 a) {
        int size = a.getDataLength();
        double min = a.data[1];
        for (int i = 3; i < size; i += 2) {
            double val = a.data[i];
            if (!(val < min)) continue;
            min = val;
        }
        return min;
    }

    public static double elementMaxReal(ZMatrixD1 a) {
        int size = a.getDataLength();
        double max = a.data[0];
        for (int i = 2; i < size; i += 2) {
            double val = a.data[i];
            if (!(val > max)) continue;
            max = val;
        }
        return max;
    }

    public static double elementMaxImaginary(ZMatrixD1 a) {
        int size = a.getDataLength();
        double max = a.data[1];
        for (int i = 3; i < size; i += 2) {
            double val = a.data[i];
            if (!(val > max)) continue;
            max = val;
        }
        return max;
    }

    public static double elementMaxMagnitude2(ZMatrixD1 a) {
        int size = a.getDataLength();
        double max = 0.0;
        int i = 0;
        while (i < size) {
            double real = a.data[i++];
            int n = i++;
            double imaginary = a.data[n];
            double m = real * real + imaginary * imaginary;
            if (!(m > max)) continue;
            max = m;
        }
        return max;
    }

    public static void setIdentity(ZMatrixRMaj mat) {
        int width = mat.numRows < mat.numCols ? mat.numRows : mat.numCols;
        Arrays.fill(mat.data, 0, mat.getDataLength(), 0.0);
        int index = 0;
        int stride = mat.getRowStride();
        int i = 0;
        while (i < width) {
            mat.data[index] = 1.0;
            ++i;
            index += stride + 2;
        }
    }

    public static ZMatrixRMaj extract(ZMatrixRMaj src, int srcY0, int srcY1, int srcX0, int srcX1) {
        if (srcY1 <= srcY0 || srcY0 < 0 || srcY1 > src.numRows) {
            throw new IllegalArgumentException("srcY1 <= srcY0 || srcY0 < 0 || srcY1 > src.numRows");
        }
        if (srcX1 <= srcX0 || srcX0 < 0 || srcX1 > src.numCols) {
            throw new IllegalArgumentException("srcX1 <= srcX0 || srcX0 < 0 || srcX1 > src.numCols");
        }
        int w = srcX1 - srcX0;
        int h = srcY1 - srcY0;
        ZMatrixRMaj dst = new ZMatrixRMaj(h, w);
        CommonOps_ZDRM.extract(src, srcY0, srcY1, srcX0, srcX1, dst, 0, 0);
        return dst;
    }

    public static void extract(ZMatrixRMaj src, int srcY0, int srcY1, int srcX0, int srcX1, ZMatrixRMaj dst, int dstY0, int dstX0) {
        int numRows = srcY1 - srcY0;
        int stride = (srcX1 - srcX0) * 2;
        for (int y = 0; y < numRows; ++y) {
            int indexSrc = src.getIndex(y + srcY0, srcX0);
            int indexDst = dst.getIndex(y + dstY0, dstX0);
            System.arraycopy(src.data, indexSrc, dst.data, indexDst, stride);
        }
    }

    public static ZMatrixRMaj[] columnsToVector(ZMatrixRMaj A, @Nullable ZMatrixRMaj[] v) {
        ZMatrixRMaj[] ret = v == null || v.length < A.numCols ? new ZMatrixRMaj[A.numCols] : v;
        for (int i = 0; i < ret.length; ++i) {
            if (ret[i] == null) {
                ret[i] = new ZMatrixRMaj(A.numRows, 1);
            } else {
                ret[i].reshape(A.numRows, 1);
            }
            ZMatrixRMaj u = ret[i];
            int indexU = 0;
            for (int j = 0; j < A.numRows; ++j) {
                int indexA = A.getIndex(j, i);
                u.data[indexU++] = A.data[indexA++];
                u.data[indexU++] = A.data[indexA];
            }
        }
        return ret;
    }

    public static double elementMaxAbs(ZMatrixRMaj a) {
        int size = a.getDataLength();
        double max = 0.0;
        for (int i = 0; i < size; i += 2) {
            double real = a.data[i];
            double imag = a.data[i + 1];
            double val = real * real + imag * imag;
            if (!(val > max)) continue;
            max = val;
        }
        return Math.sqrt(max);
    }

    public static double elementMinAbs(ZMatrixRMaj a) {
        int size = a.getDataLength();
        double min = Double.MAX_VALUE;
        for (int i = 0; i < size; i += 2) {
            double real = a.data[i];
            double imag = a.data[i + 1];
            double val = real * real + imag * imag;
            if (!(val < min)) continue;
            min = val;
        }
        return Math.sqrt(min);
    }
}

