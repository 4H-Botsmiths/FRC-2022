/*
 * Decompiled with CFR 0.150.
 */
package org.ejml.simple.ops;

import java.io.PrintStream;
import org.ejml.concurrency.EjmlConcurrency;
import org.ejml.data.Complex_F64;
import org.ejml.data.DMatrixRMaj;
import org.ejml.data.Matrix;
import org.ejml.dense.row.CommonOps_DDRM;
import org.ejml.dense.row.CommonOps_MT_DDRM;
import org.ejml.dense.row.MatrixFeatures_DDRM;
import org.ejml.dense.row.NormOps_DDRM;
import org.ejml.dense.row.mult.VectorVectorMult_DDRM;
import org.ejml.ops.MatrixIO;
import org.ejml.simple.SimpleOperations;

public class SimpleOperations_DDRM
implements SimpleOperations<DMatrixRMaj> {
    @Override
    public void set(DMatrixRMaj A, int row, int column, double value) {
        A.set(row, column, value);
    }

    @Override
    public void set(DMatrixRMaj A, int row, int column, double real, double imaginary) {
        throw new IllegalArgumentException("Does not support imaginary values");
    }

    @Override
    public double get(DMatrixRMaj A, int row, int column) {
        return A.get(row, column);
    }

    @Override
    public void get(DMatrixRMaj A, int row, int column, Complex_F64 value) {
        value.real = A.get(row, column);
        value.imaginary = 0.0;
    }

    @Override
    public void fill(DMatrixRMaj A, double value) {
        CommonOps_DDRM.fill(A, value);
    }

    @Override
    public void transpose(DMatrixRMaj input, DMatrixRMaj output) {
        if (EjmlConcurrency.useConcurrent(input)) {
            CommonOps_MT_DDRM.transpose(input, output);
        } else {
            CommonOps_DDRM.transpose(input, output);
        }
    }

    @Override
    public void mult(DMatrixRMaj A, DMatrixRMaj B, DMatrixRMaj output) {
        if (EjmlConcurrency.useConcurrent(A)) {
            CommonOps_MT_DDRM.mult(A, B, output);
        } else {
            CommonOps_DDRM.mult(A, B, output);
        }
    }

    @Override
    public void multTransA(DMatrixRMaj A, DMatrixRMaj B, DMatrixRMaj output) {
        if (EjmlConcurrency.useConcurrent(A)) {
            CommonOps_MT_DDRM.multTransA(A, B, output);
        } else {
            CommonOps_DDRM.multTransA(A, B, output);
        }
    }

    @Override
    public void kron(DMatrixRMaj A, DMatrixRMaj B, DMatrixRMaj output) {
        CommonOps_DDRM.kron(A, B, output);
    }

    @Override
    public void plus(DMatrixRMaj A, DMatrixRMaj B, DMatrixRMaj output) {
        CommonOps_DDRM.add(A, B, output);
    }

    @Override
    public void minus(DMatrixRMaj A, DMatrixRMaj B, DMatrixRMaj output) {
        CommonOps_DDRM.subtract(A, B, output);
    }

    @Override
    public void minus(DMatrixRMaj A, double b, DMatrixRMaj output) {
        CommonOps_DDRM.subtract(A, b, output);
    }

    @Override
    public void plus(DMatrixRMaj A, double b, DMatrixRMaj output) {
        CommonOps_DDRM.add(A, b, output);
    }

    @Override
    public void plus(DMatrixRMaj A, double beta, DMatrixRMaj b, DMatrixRMaj output) {
        CommonOps_DDRM.add(A, beta, b, output);
    }

    @Override
    public void plus(double alpha, DMatrixRMaj A, double beta, DMatrixRMaj b, DMatrixRMaj output) {
        CommonOps_DDRM.add(alpha, A, beta, b, output);
    }

    @Override
    public double dot(DMatrixRMaj A, DMatrixRMaj v) {
        return VectorVectorMult_DDRM.innerProd(A, v);
    }

    @Override
    public void scale(DMatrixRMaj A, double val, DMatrixRMaj output) {
        CommonOps_DDRM.scale(val, A, output);
    }

    @Override
    public void divide(DMatrixRMaj A, double val, DMatrixRMaj output) {
        CommonOps_DDRM.divide(A, val, output);
    }

    @Override
    public boolean invert(DMatrixRMaj A, DMatrixRMaj output) {
        return CommonOps_DDRM.invert(A, output);
    }

    @Override
    public void setIdentity(DMatrixRMaj A) {
        CommonOps_DDRM.setIdentity(A);
    }

    @Override
    public void pseudoInverse(DMatrixRMaj A, DMatrixRMaj output) {
        CommonOps_DDRM.pinv(A, output);
    }

    @Override
    public boolean solve(DMatrixRMaj A, DMatrixRMaj X, DMatrixRMaj B) {
        return CommonOps_DDRM.solve(A, B, X);
    }

    @Override
    public void zero(DMatrixRMaj A) {
        A.zero();
    }

    @Override
    public double normF(DMatrixRMaj A) {
        return NormOps_DDRM.normF(A);
    }

    @Override
    public double conditionP2(DMatrixRMaj A) {
        return NormOps_DDRM.conditionP2(A);
    }

    @Override
    public double determinant(DMatrixRMaj A) {
        return CommonOps_DDRM.det(A);
    }

    @Override
    public double trace(DMatrixRMaj A) {
        return CommonOps_DDRM.trace(A);
    }

    @Override
    public void setRow(DMatrixRMaj A, int row, int startColumn, double ... values) {
        for (int i = 0; i < values.length; ++i) {
            A.set(row, startColumn + i, values[i]);
        }
    }

    @Override
    public void setColumn(DMatrixRMaj A, int column, int startRow, double ... values) {
        for (int i = 0; i < values.length; ++i) {
            A.set(startRow + i, column, values[i]);
        }
    }

    @Override
    public void extract(DMatrixRMaj src, int srcY0, int srcY1, int srcX0, int srcX1, DMatrixRMaj dst, int dstY0, int dstX0) {
        CommonOps_DDRM.extract(src, srcY0, srcY1, srcX0, srcX1, dst, dstY0, dstX0);
    }

    @Override
    public DMatrixRMaj diag(DMatrixRMaj A) {
        DMatrixRMaj output;
        if (MatrixFeatures_DDRM.isVector(A)) {
            int N = Math.max(A.numCols, A.numRows);
            output = new DMatrixRMaj(N, N);
            CommonOps_DDRM.diag(output, N, A.data);
        } else {
            int N = Math.min(A.numCols, A.numRows);
            output = new DMatrixRMaj(N, 1);
            CommonOps_DDRM.extractDiag(A, output);
        }
        return output;
    }

    @Override
    public boolean hasUncountable(DMatrixRMaj M) {
        return MatrixFeatures_DDRM.hasUncountable(M);
    }

    @Override
    public void changeSign(DMatrixRMaj a) {
        CommonOps_DDRM.changeSign(a);
    }

    @Override
    public double elementMaxAbs(DMatrixRMaj A) {
        return CommonOps_DDRM.elementMaxAbs(A);
    }

    @Override
    public double elementMinAbs(DMatrixRMaj A) {
        return CommonOps_DDRM.elementMinAbs(A);
    }

    @Override
    public double elementSum(DMatrixRMaj A) {
        return CommonOps_DDRM.elementSum(A);
    }

    @Override
    public void elementMult(DMatrixRMaj A, DMatrixRMaj B, DMatrixRMaj output) {
        CommonOps_DDRM.elementMult(A, B, output);
    }

    @Override
    public void elementDiv(DMatrixRMaj A, DMatrixRMaj B, DMatrixRMaj output) {
        CommonOps_DDRM.elementDiv(A, B, output);
    }

    @Override
    public void elementPower(DMatrixRMaj A, DMatrixRMaj B, DMatrixRMaj output) {
        CommonOps_DDRM.elementPower(A, B, output);
    }

    @Override
    public void elementPower(DMatrixRMaj A, double b, DMatrixRMaj output) {
        CommonOps_DDRM.elementPower(A, b, output);
    }

    @Override
    public void elementExp(DMatrixRMaj A, DMatrixRMaj output) {
        CommonOps_DDRM.elementExp(A, output);
    }

    @Override
    public void elementLog(DMatrixRMaj A, DMatrixRMaj output) {
        CommonOps_DDRM.elementLog(A, output);
    }

    @Override
    public boolean isIdentical(DMatrixRMaj A, DMatrixRMaj B, double tol) {
        return MatrixFeatures_DDRM.isIdentical(A, B, tol);
    }

    @Override
    public void print(PrintStream out, Matrix mat, String format) {
        MatrixIO.print(out, (DMatrixRMaj)mat, format);
    }
}

