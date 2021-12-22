/*
 * Decompiled with CFR 0.150.
 */
package org.ejml.simple.ops;

import java.io.PrintStream;
import org.ejml.concurrency.EjmlConcurrency;
import org.ejml.data.Complex_F64;
import org.ejml.data.FGrowArray;
import org.ejml.data.FMatrixRMaj;
import org.ejml.data.FMatrixSparseCSC;
import org.ejml.data.IGrowArray;
import org.ejml.data.Matrix;
import org.ejml.ops.MatrixIO;
import org.ejml.simple.ConvertToDenseException;
import org.ejml.simple.ConvertToImaginaryException;
import org.ejml.simple.SimpleSparseOperations;
import org.ejml.sparse.csc.CommonOps_FSCC;
import org.ejml.sparse.csc.CommonOps_MT_FSCC;
import org.ejml.sparse.csc.MatrixFeatures_FSCC;
import org.ejml.sparse.csc.NormOps_FSCC;
import org.ejml.sparse.csc.mult.Workspace_MT_FSCC;
import pabeles.concurrency.GrowArray;

public class SimpleOperations_FSCC
implements SimpleSparseOperations<FMatrixSparseCSC, FMatrixRMaj> {
    public transient IGrowArray gw = new IGrowArray();
    public transient FGrowArray gx = new FGrowArray();
    public transient GrowArray<Workspace_MT_FSCC> workspaceMT = new GrowArray<Workspace_MT_FSCC>(Workspace_MT_FSCC::new);
    public transient GrowArray<FGrowArray> workspaceA = new GrowArray<FGrowArray>(FGrowArray::new);

    @Override
    public void set(FMatrixSparseCSC A, int row, int column, double value) {
        A.set(row, column, (float)value);
    }

    @Override
    public void set(FMatrixSparseCSC A, int row, int column, double real, double imaginary) {
        throw new ConvertToImaginaryException();
    }

    @Override
    public double get(FMatrixSparseCSC A, int row, int column) {
        return A.get(row, column);
    }

    @Override
    public void get(FMatrixSparseCSC A, int row, int column, Complex_F64 value) {
        value.real = A.get(row, column);
        value.imaginary = 0.0;
    }

    @Override
    public void fill(FMatrixSparseCSC A, double value) {
        if (value != 0.0) {
            throw new ConvertToDenseException();
        }
        A.zero();
    }

    @Override
    public void transpose(FMatrixSparseCSC input, FMatrixSparseCSC output) {
        CommonOps_FSCC.transpose(input, output, this.gw);
    }

    @Override
    public void mult(FMatrixSparseCSC A, FMatrixSparseCSC B, FMatrixSparseCSC output) {
        if (EjmlConcurrency.useConcurrent(A)) {
            CommonOps_MT_FSCC.mult(A, B, output, this.workspaceMT);
        } else {
            CommonOps_FSCC.mult(A, B, output);
        }
    }

    @Override
    public void multTransA(FMatrixSparseCSC A, FMatrixSparseCSC B, FMatrixSparseCSC output) {
        FMatrixSparseCSC At = new FMatrixSparseCSC(1, 1);
        CommonOps_FSCC.transpose(A, At, this.gw);
        if (EjmlConcurrency.useConcurrent(A)) {
            CommonOps_MT_FSCC.mult(A, B, output, this.workspaceMT);
        } else {
            CommonOps_FSCC.mult(At, B, output, this.gw, this.gx);
        }
    }

    @Override
    public void extractDiag(FMatrixSparseCSC input, FMatrixRMaj output) {
        CommonOps_FSCC.extractDiag(input, output);
    }

    @Override
    public void multTransA(FMatrixSparseCSC A, FMatrixRMaj B, FMatrixRMaj output) {
        if (EjmlConcurrency.useConcurrent(A)) {
            CommonOps_MT_FSCC.multTransA(A, B, output, this.workspaceA);
        } else {
            CommonOps_FSCC.multTransA(A, B, output, null);
        }
    }

    @Override
    public void mult(FMatrixSparseCSC A, FMatrixRMaj B, FMatrixRMaj output) {
        if (EjmlConcurrency.useConcurrent(A)) {
            CommonOps_MT_FSCC.mult(A, B, output, this.workspaceA);
        } else {
            CommonOps_FSCC.mult(A, B, output);
        }
    }

    @Override
    public void kron(FMatrixSparseCSC A, FMatrixSparseCSC B, FMatrixSparseCSC output) {
        throw new RuntimeException("Unsupported. Make a feature request if you need this!");
    }

    @Override
    public void plus(FMatrixSparseCSC A, FMatrixSparseCSC B, FMatrixSparseCSC output) {
        CommonOps_FSCC.add(1.0f, A, 1.0f, B, output, null, null);
    }

    @Override
    public void minus(FMatrixSparseCSC A, FMatrixSparseCSC B, FMatrixSparseCSC output) {
        CommonOps_FSCC.add(1.0f, A, -1.0f, B, output, null, null);
    }

    @Override
    public void minus(FMatrixSparseCSC A, double b, FMatrixSparseCSC output) {
        throw new ConvertToDenseException();
    }

    @Override
    public void plus(FMatrixSparseCSC A, double b, FMatrixSparseCSC output) {
        throw new ConvertToDenseException();
    }

    @Override
    public void plus(FMatrixSparseCSC A, double beta, FMatrixSparseCSC b, FMatrixSparseCSC output) {
        if (EjmlConcurrency.useConcurrent(A)) {
            CommonOps_MT_FSCC.add(1.0f, A, (float)beta, b, output, this.workspaceMT);
        } else {
            CommonOps_FSCC.add(1.0f, A, (float)beta, b, output, this.gw, this.gx);
        }
    }

    @Override
    public void plus(double alpha, FMatrixSparseCSC A, double beta, FMatrixSparseCSC b, FMatrixSparseCSC output) {
        if (EjmlConcurrency.useConcurrent(A)) {
            CommonOps_MT_FSCC.add((float)alpha, A, (float)beta, b, output, this.workspaceMT);
        } else {
            CommonOps_FSCC.add((float)alpha, A, (float)beta, b, output, this.gw, this.gx);
        }
    }

    @Override
    public double dot(FMatrixSparseCSC A, FMatrixSparseCSC v) {
        return CommonOps_FSCC.dotInnerColumns(A, 0, v, 0, this.gw, this.gx);
    }

    @Override
    public void scale(FMatrixSparseCSC A, double val, FMatrixSparseCSC output) {
        CommonOps_FSCC.scale((float)val, A, output);
    }

    @Override
    public void divide(FMatrixSparseCSC A, double val, FMatrixSparseCSC output) {
        CommonOps_FSCC.divide(A, (float)val, output);
    }

    @Override
    public boolean invert(FMatrixSparseCSC A, FMatrixSparseCSC output) {
        return this.solve(A, output, CommonOps_FSCC.identity(A.numRows, A.numCols));
    }

    @Override
    public void setIdentity(FMatrixSparseCSC A) {
        CommonOps_FSCC.setIdentity(A);
    }

    @Override
    public void pseudoInverse(FMatrixSparseCSC A, FMatrixSparseCSC output) {
        throw new RuntimeException("Unsupported");
    }

    @Override
    public boolean solve(FMatrixSparseCSC A, FMatrixSparseCSC X, FMatrixSparseCSC B) {
        return CommonOps_FSCC.solve(A, X, B);
    }

    @Override
    public boolean solve(FMatrixSparseCSC A, FMatrixRMaj X, FMatrixRMaj B) {
        return CommonOps_FSCC.solve(A, X, B);
    }

    @Override
    public void zero(FMatrixSparseCSC A) {
        A.zero();
    }

    @Override
    public double normF(FMatrixSparseCSC A) {
        return NormOps_FSCC.normF(A);
    }

    @Override
    public double conditionP2(FMatrixSparseCSC A) {
        throw new RuntimeException("Unsupported");
    }

    @Override
    public double determinant(FMatrixSparseCSC A) {
        return CommonOps_FSCC.det(A);
    }

    @Override
    public double trace(FMatrixSparseCSC A) {
        return CommonOps_FSCC.trace(A);
    }

    @Override
    public void setRow(FMatrixSparseCSC A, int row, int startColumn, double ... values) {
        for (int i = 0; i < values.length; ++i) {
            A.set(row, startColumn + i, (float)values[i]);
        }
    }

    @Override
    public void setColumn(FMatrixSparseCSC A, int column, int startRow, double ... values) {
        for (int i = 0; i < values.length; ++i) {
            A.set(startRow + i, column, (float)values[i]);
        }
    }

    @Override
    public void extract(FMatrixSparseCSC src, int srcY0, int srcY1, int srcX0, int srcX1, FMatrixSparseCSC dst, int dstY0, int dstX0) {
        CommonOps_FSCC.extract(src, srcY0, srcY1, srcX0, srcX1, dst, dstY0, dstX0);
    }

    @Override
    public FMatrixSparseCSC diag(FMatrixSparseCSC A) {
        FMatrixSparseCSC output;
        if (MatrixFeatures_FSCC.isVector(A)) {
            int N = Math.max(A.numCols, A.numRows);
            output = new FMatrixSparseCSC(N, N);
            CommonOps_FSCC.diag(output, A.nz_values, 0, N);
        } else {
            int N = Math.min(A.numCols, A.numRows);
            output = new FMatrixSparseCSC(N, 1);
            CommonOps_FSCC.extractDiag(A, output);
        }
        return output;
    }

    @Override
    public boolean hasUncountable(FMatrixSparseCSC M) {
        return MatrixFeatures_FSCC.hasUncountable(M);
    }

    @Override
    public void changeSign(FMatrixSparseCSC a) {
        CommonOps_FSCC.changeSign(a, a);
    }

    @Override
    public double elementMaxAbs(FMatrixSparseCSC A) {
        return CommonOps_FSCC.elementMaxAbs(A);
    }

    @Override
    public double elementMinAbs(FMatrixSparseCSC A) {
        return CommonOps_FSCC.elementMinAbs(A);
    }

    @Override
    public double elementSum(FMatrixSparseCSC A) {
        return CommonOps_FSCC.elementSum(A);
    }

    @Override
    public void elementMult(FMatrixSparseCSC A, FMatrixSparseCSC B, FMatrixSparseCSC output) {
        CommonOps_FSCC.elementMult(A, B, output, null, null);
    }

    @Override
    public void elementDiv(FMatrixSparseCSC A, FMatrixSparseCSC B, FMatrixSparseCSC output) {
        throw new ConvertToDenseException();
    }

    @Override
    public void elementPower(FMatrixSparseCSC A, FMatrixSparseCSC B, FMatrixSparseCSC output) {
        throw new ConvertToDenseException();
    }

    @Override
    public void elementPower(FMatrixSparseCSC A, double b, FMatrixSparseCSC output) {
        throw new ConvertToDenseException();
    }

    @Override
    public void elementExp(FMatrixSparseCSC A, FMatrixSparseCSC output) {
        throw new ConvertToDenseException();
    }

    @Override
    public void elementLog(FMatrixSparseCSC A, FMatrixSparseCSC output) {
        throw new ConvertToDenseException();
    }

    @Override
    public boolean isIdentical(FMatrixSparseCSC A, FMatrixSparseCSC B, double tol) {
        return MatrixFeatures_FSCC.isEqualsSort(A, B, (float)tol);
    }

    @Override
    public void print(PrintStream out, Matrix mat, String format) {
        MatrixIO.print(out, (FMatrixSparseCSC)mat, format);
    }
}

