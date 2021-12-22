/*
 * Decompiled with CFR 0.150.
 */
package edu.wpi.first.math;

import edu.wpi.first.math.MatBuilder;
import edu.wpi.first.math.Nat;
import edu.wpi.first.math.Num;
import edu.wpi.first.math.WPIMathJNI;
import edu.wpi.first.math.numbers.N1;
import java.util.Objects;
import org.ejml.MatrixDimensionException;
import org.ejml.data.DMatrix;
import org.ejml.data.DMatrixRMaj;
import org.ejml.dense.row.CommonOps_DDRM;
import org.ejml.dense.row.MatrixFeatures_DDRM;
import org.ejml.dense.row.NormOps_DDRM;
import org.ejml.dense.row.factory.DecompositionFactory_DDRM;
import org.ejml.interfaces.decomposition.CholeskyDecomposition_F64;
import org.ejml.simple.SimpleMatrix;

public class Matrix<R extends Num, C extends Num> {
    protected final SimpleMatrix m_storage;

    public Matrix(Nat<R> rows, Nat<C> columns) {
        this.m_storage = new SimpleMatrix(Objects.requireNonNull(rows).getNum(), Objects.requireNonNull(columns).getNum());
    }

    public Matrix(SimpleMatrix storage) {
        this.m_storage = Objects.requireNonNull(storage);
    }

    public Matrix(Matrix<R, C> other) {
        this.m_storage = (SimpleMatrix)Objects.requireNonNull(other).getStorage().copy();
    }

    public SimpleMatrix getStorage() {
        return this.m_storage;
    }

    public final int getNumCols() {
        return this.m_storage.numCols();
    }

    public final int getNumRows() {
        return this.m_storage.numRows();
    }

    public final double get(int row, int col) {
        return this.m_storage.get(row, col);
    }

    public final void set(int row, int col, double value) {
        this.m_storage.set(row, col, value);
    }

    public final void setRow(int row, Matrix<N1, C> val) {
        this.m_storage.setRow(row, 0, Objects.requireNonNull(val).m_storage.getDDRM().getData());
    }

    public final void setColumn(int column, Matrix<R, N1> val) {
        this.m_storage.setColumn(column, 0, Objects.requireNonNull(val).m_storage.getDDRM().getData());
    }

    public void fill(double value) {
        this.m_storage.fill(value);
    }

    public final Matrix<R, C> diag() {
        return new Matrix<R, C>((SimpleMatrix)this.m_storage.diag());
    }

    public final double max() {
        return CommonOps_DDRM.elementMax(this.m_storage.getDDRM());
    }

    public final double maxAbs() {
        return CommonOps_DDRM.elementMaxAbs(this.m_storage.getDDRM());
    }

    public final double minInternal() {
        return CommonOps_DDRM.elementMin(this.m_storage.getDDRM());
    }

    public final double mean() {
        return this.elementSum() / (double)this.m_storage.getNumElements();
    }

    public final <C2 extends Num> Matrix<R, C2> times(Matrix<C, C2> other) {
        return new Matrix<R, C>(this.m_storage.mult(Objects.requireNonNull(other).m_storage));
    }

    public Matrix<R, C> times(double value) {
        return new Matrix<R, C>((SimpleMatrix)this.m_storage.scale(value));
    }

    public final Matrix<R, C> elementTimes(Matrix<R, C> other) {
        return new Matrix<R, C>(this.m_storage.elementMult(Objects.requireNonNull(other).m_storage));
    }

    public final Matrix<R, C> minus(double value) {
        return new Matrix<R, C>((SimpleMatrix)this.m_storage.minus(value));
    }

    public final Matrix<R, C> minus(Matrix<R, C> value) {
        return new Matrix<R, C>(this.m_storage.minus(Objects.requireNonNull(value).m_storage));
    }

    public final Matrix<R, C> plus(double value) {
        return new Matrix<R, C>((SimpleMatrix)this.m_storage.plus(value));
    }

    public final Matrix<R, C> plus(Matrix<R, C> value) {
        return new Matrix<R, C>(this.m_storage.plus(Objects.requireNonNull(value).m_storage));
    }

    public Matrix<R, C> div(int value) {
        return new Matrix<R, C>((SimpleMatrix)this.m_storage.divide(value));
    }

    public Matrix<R, C> div(double value) {
        return new Matrix<R, C>((SimpleMatrix)this.m_storage.divide(value));
    }

    public final Matrix<C, R> transpose() {
        return new Matrix<R, C>((SimpleMatrix)this.m_storage.transpose());
    }

    public final Matrix<R, C> copy() {
        return new Matrix<R, C>((SimpleMatrix)this.m_storage.copy());
    }

    public final Matrix<R, C> inv() {
        return new Matrix<R, C>((SimpleMatrix)this.m_storage.invert());
    }

    public final <C2 extends Num> Matrix<C, C2> solve(Matrix<R, C2> b) {
        return new Matrix<R, C>(this.m_storage.solve(Objects.requireNonNull(b).m_storage));
    }

    public final Matrix<R, C> exp() {
        if (this.getNumRows() != this.getNumCols()) {
            throw new MatrixDimensionException("Non-square matrices cannot be exponentiated! This matrix is " + this.getNumRows() + " x " + this.getNumCols());
        }
        Matrix<R, C> toReturn = new Matrix<R, C>(new SimpleMatrix(this.getNumRows(), this.getNumCols()));
        WPIMathJNI.exp(this.m_storage.getDDRM().getData(), this.getNumRows(), toReturn.m_storage.getDDRM().getData());
        return toReturn;
    }

    public final Matrix<R, C> pow(double exponent) {
        if (this.getNumRows() != this.getNumCols()) {
            throw new MatrixDimensionException("Non-square matrices cannot be raised to a power! This matrix is " + this.getNumRows() + " x " + this.getNumCols());
        }
        Matrix<R, C> toReturn = new Matrix<R, C>(new SimpleMatrix(this.getNumRows(), this.getNumCols()));
        WPIMathJNI.pow(this.m_storage.getDDRM().getData(), this.getNumRows(), exponent, toReturn.m_storage.getDDRM().getData());
        return toReturn;
    }

    public final double det() {
        return this.m_storage.determinant();
    }

    public final double normF() {
        return this.m_storage.normF();
    }

    public final double normIndP1() {
        return NormOps_DDRM.inducedP1(this.m_storage.getDDRM());
    }

    public final double elementSum() {
        return this.m_storage.elementSum();
    }

    public final double trace() {
        return this.m_storage.trace();
    }

    public final Matrix<R, C> elementPower(double b) {
        return new Matrix<R, C>((SimpleMatrix)this.m_storage.elementPower(b));
    }

    public final Matrix<R, C> elementPower(int b) {
        return new Matrix<R, C>((SimpleMatrix)this.m_storage.elementPower(b));
    }

    public final Matrix<N1, C> extractRowVector(int row) {
        return new Matrix<R, C>((SimpleMatrix)this.m_storage.extractVector(true, row));
    }

    public final Matrix<R, N1> extractColumnVector(int column) {
        return new Matrix<R, C>((SimpleMatrix)this.m_storage.extractVector(false, column));
    }

    public final <R2 extends Num, C2 extends Num> Matrix<R2, C2> block(Nat<R2> height, Nat<C2> width, int startingRow, int startingCol) {
        return new Matrix<R, C>((SimpleMatrix)this.m_storage.extractMatrix(startingRow, startingRow + Objects.requireNonNull(height).getNum(), startingCol, startingCol + Objects.requireNonNull(width).getNum()));
    }

    public final <R2 extends Num, C2 extends Num> Matrix<R2, C2> block(int height, int width, int startingRow, int startingCol) {
        return new Matrix<R, C>((SimpleMatrix)this.m_storage.extractMatrix(startingRow, startingRow + height, startingCol, startingCol + width));
    }

    public <R2 extends Num, C2 extends Num> void assignBlock(int startingRow, int startingCol, Matrix<R2, C2> other) {
        this.m_storage.insertIntoThis(startingRow, startingCol, Objects.requireNonNull(other).m_storage);
    }

    public <R2 extends Num, C2 extends Num> void extractFrom(int startingRow, int startingCol, Matrix<R2, C2> other) {
        CommonOps_DDRM.extract((DMatrix)other.m_storage.getDDRM(), startingRow, startingCol, (DMatrix)this.m_storage.getDDRM());
    }

    public Matrix<R, C> lltDecompose(boolean lowerTriangular) {
        SimpleMatrix temp = (SimpleMatrix)this.m_storage.copy();
        CholeskyDecomposition_F64<DMatrixRMaj> chol = DecompositionFactory_DDRM.chol(temp.numRows(), lowerTriangular);
        if (!chol.decompose((DMatrixRMaj)temp.getMatrix())) {
            double[] matData = temp.getDDRM().data;
            boolean isZeros = true;
            for (double matDatum : matData) {
                isZeros &= Math.abs(matDatum) < 1.0E-6;
            }
            if (isZeros) {
                return new Matrix<R, C>(new SimpleMatrix(temp.numRows(), temp.numCols()));
            }
            throw new RuntimeException("Cholesky decomposition failed! Input matrix:\n" + this.m_storage.toString());
        }
        return new Matrix<R, C>(SimpleMatrix.wrap(chol.getT(null)));
    }

    public double[] getData() {
        return this.m_storage.getDDRM().getData();
    }

    public static <D extends Num> Matrix<D, D> eye(Nat<D> dim) {
        return new Matrix(SimpleMatrix.identity(Objects.requireNonNull(dim).getNum()));
    }

    public static <D extends Num> Matrix<D, D> eye(D dim) {
        return new Matrix(SimpleMatrix.identity(Objects.requireNonNull(dim).getNum()));
    }

    public static <R extends Num, C extends Num> MatBuilder<R, C> mat(Nat<R> rows, Nat<C> cols) {
        return new MatBuilder<R, C>(Objects.requireNonNull(rows), Objects.requireNonNull(cols));
    }

    public static <R1 extends Num, C1 extends Num> Matrix<R1, C1> changeBoundsUnchecked(Matrix<?, ?> mat) {
        return new Matrix(mat.m_storage);
    }

    public boolean isIdentical(Matrix<?, ?> other, double tolerance) {
        return MatrixFeatures_DDRM.isIdentical(this.m_storage.getDDRM(), other.m_storage.getDDRM(), tolerance);
    }

    public boolean isEqual(Matrix<?, ?> other, double tolerance) {
        return MatrixFeatures_DDRM.isEquals(this.m_storage.getDDRM(), other.m_storage.getDDRM(), tolerance);
    }

    public String toString() {
        return this.m_storage.toString();
    }

    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof Matrix)) {
            return false;
        }
        Matrix matrix = (Matrix)other;
        if (MatrixFeatures_DDRM.hasUncountable(matrix.m_storage.getDDRM())) {
            return false;
        }
        return MatrixFeatures_DDRM.isEquals(this.m_storage.getDDRM(), matrix.m_storage.getDDRM());
    }

    public int hashCode() {
        return Objects.hash(this.m_storage);
    }
}

