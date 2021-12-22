/*
 * Decompiled with CFR 0.150.
 */
package org.ejml.data;

import java.util.Arrays;
import org.ejml.EjmlParameters;
import org.ejml.UtilEjml;
import org.ejml.data.FMatrix;
import org.ejml.data.FMatrixD1;
import org.ejml.data.Matrix;
import org.ejml.data.MatrixType;

public class FMatrixRBlock
extends FMatrixD1 {
    public int blockLength;

    public FMatrixRBlock(int numRows, int numCols, int blockLength) {
        UtilEjml.checkTooLarge(numRows, numCols);
        this.data = new float[numRows * numCols];
        this.blockLength = blockLength;
        this.numRows = numRows;
        this.numCols = numCols;
    }

    public FMatrixRBlock(int numRows, int numCols) {
        this(numRows, numCols, EjmlParameters.BLOCK_WIDTH);
    }

    public FMatrixRBlock() {
    }

    public void set(FMatrixRBlock A) {
        this.blockLength = A.blockLength;
        this.numRows = A.numRows;
        this.numCols = A.numCols;
        int N = this.numCols * this.numRows;
        if (this.data.length < N) {
            this.data = new float[N];
        }
        System.arraycopy(A.data, 0, this.data, 0, N);
    }

    public static FMatrixRBlock wrap(float[] data, int numRows, int numCols, int blockLength) {
        FMatrixRBlock ret = new FMatrixRBlock();
        ret.data = data;
        ret.numRows = numRows;
        ret.numCols = numCols;
        ret.blockLength = blockLength;
        return ret;
    }

    @Override
    public float[] getData() {
        return this.data;
    }

    @Override
    public void reshape(int numRows, int numCols, boolean saveValues) {
        UtilEjml.checkTooLarge(numRows, numCols);
        if (numRows * numCols <= this.data.length) {
            this.numRows = numRows;
            this.numCols = numCols;
        } else {
            float[] data = new float[numRows * numCols];
            if (saveValues) {
                System.arraycopy(this.data, 0, data, 0, this.getNumElements());
            }
            this.numRows = numRows;
            this.numCols = numCols;
            this.data = data;
        }
    }

    public void reshape(int numRows, int numCols, int blockLength, boolean saveValues) {
        this.blockLength = blockLength;
        this.reshape(numRows, numCols, saveValues);
    }

    @Override
    public int getIndex(int row, int col) {
        int blockRow = row / this.blockLength;
        int blockCol = col / this.blockLength;
        int localHeight = Math.min(this.numRows - blockRow * this.blockLength, this.blockLength);
        int index = blockRow * this.blockLength * this.numCols + blockCol * localHeight * this.blockLength;
        int localLength = Math.min(this.numCols - this.blockLength * blockCol, this.blockLength);
        return index + localLength * (row %= this.blockLength) + (col %= this.blockLength);
    }

    @Override
    public float get(int row, int col) {
        return this.data[this.getIndex(row, col)];
    }

    @Override
    public float unsafe_get(int row, int col) {
        return this.data[this.getIndex(row, col)];
    }

    @Override
    public void set(int row, int col, float val) {
        this.data[this.getIndex((int)row, (int)col)] = val;
    }

    @Override
    public void unsafe_set(int row, int col, float val) {
        this.data[this.getIndex((int)row, (int)col)] = val;
    }

    @Override
    public int getNumRows() {
        return this.numRows;
    }

    @Override
    public int getNumCols() {
        return this.numCols;
    }

    @Override
    public void zero() {
        Arrays.fill(this.data, 0, this.getNumElements(), 0.0f);
    }

    @Override
    public <T extends Matrix> T createLike() {
        return (T)new FMatrixRBlock(this.numRows, this.numCols, this.blockLength);
    }

    @Override
    public <T extends Matrix> T create(int numRows, int numCols) {
        return (T)new FMatrixRBlock(numRows, numCols, this.blockLength);
    }

    @Override
    public void setTo(Matrix original) {
        if (original instanceof FMatrixRBlock) {
            this.set((FMatrixRBlock)original);
        } else {
            FMatrix m = (FMatrix)original;
            for (int i = 0; i < this.numRows; ++i) {
                for (int j = 0; j < this.numCols; ++j) {
                    this.set(i, j, m.get(i, j));
                }
            }
        }
    }

    public FMatrixRBlock copy() {
        FMatrixRBlock A = new FMatrixRBlock(this.numRows, this.numCols, this.blockLength);
        A.set(this);
        return A;
    }

    @Override
    public MatrixType getType() {
        return MatrixType.UNSPECIFIED;
    }
}

