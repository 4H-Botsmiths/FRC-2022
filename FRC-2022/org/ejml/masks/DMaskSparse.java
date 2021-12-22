/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  org.jetbrains.annotations.Nullable
 */
package org.ejml.masks;

import org.ejml.UtilEjml;
import org.ejml.data.DMatrixSparseCSC;
import org.ejml.data.IGrowArray;
import org.ejml.masks.Mask;
import org.ejml.masks.MaskBuilder;
import org.jetbrains.annotations.Nullable;

public class DMaskSparse
extends Mask {
    protected final DMatrixSparseCSC matrix;
    public final double zeroElement;
    private int indexedColumn = -1;
    private int[] rowIndicesInIndexedColumn;

    public DMaskSparse(DMatrixSparseCSC matrix, boolean negated, double zeroElement, @Nullable IGrowArray gw, boolean indexFirstColumn) {
        super(negated);
        this.matrix = matrix;
        this.zeroElement = zeroElement;
        this.rowIndicesInIndexedColumn = UtilEjml.adjust(gw, matrix.numRows);
        if (indexFirstColumn) {
            this.setIndexColumn(0);
        }
    }

    @Override
    public boolean isSet(int row, int col) {
        if (col != this.indexedColumn) {
            return this.negated ^ this.matrix.unsafe_get(row, col) != this.zeroElement;
        }
        return this.negated ^ this.rowIndicesInIndexedColumn[row] - 1 == col;
    }

    @Override
    public boolean isSet(int idx) {
        return this.isSet(idx, 0);
    }

    @Override
    public int getNumCols() {
        return this.matrix.numCols;
    }

    @Override
    public int getNumRows() {
        return this.matrix.numRows;
    }

    @Override
    public void setIndexColumn(int col) {
        if (this.indexedColumn != col) {
            this.indexedColumn = col;
            for (int i = this.matrix.col_idx[col]; i < this.matrix.col_idx[col + 1]; ++i) {
                if (this.matrix.nz_values[i] == this.zeroElement) continue;
                this.rowIndicesInIndexedColumn[this.matrix.nz_rows[i]] = col + 1;
            }
        }
    }

    @Override
    public int maxMaskedEntries() {
        if (this.negated) {
            return this.matrix.getNumCols() * this.matrix.getNumRows() - this.matrix.nz_length;
        }
        return this.matrix.nz_length;
    }

    public static class Builder
    extends MaskBuilder<DMaskSparse> {
        private DMatrixSparseCSC matrix;
        private double zeroElement = 0.0;
        private boolean indexFirstColumn = false;
        @Nullable
        private IGrowArray gw;

        public Builder(DMatrixSparseCSC matrix) {
            this.matrix = matrix;
        }

        public Builder withZeroElement(double zeroElement) {
            this.zeroElement = zeroElement;
            return this;
        }

        public Builder withIndexFirstColumn(boolean indexFirstColumn) {
            this.indexFirstColumn = indexFirstColumn;
            return this;
        }

        public Builder withWorkArray(IGrowArray gw) {
            this.gw = gw;
            return this;
        }

        @Override
        public DMaskSparse build() {
            return new DMaskSparse(this.matrix, this.negated, this.zeroElement, this.gw, this.indexFirstColumn);
        }
    }
}

