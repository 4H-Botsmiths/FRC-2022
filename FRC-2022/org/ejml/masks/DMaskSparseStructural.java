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

public class DMaskSparseStructural
extends Mask {
    private final DMatrixSparseCSC matrix;
    private int indexedColumn = -1;
    private int[] rowIndicesInIndexedColumn;

    public DMaskSparseStructural(DMatrixSparseCSC matrix, boolean negated, @Nullable IGrowArray gw, boolean indexFirstColumn) {
        super(negated);
        this.matrix = matrix;
        this.rowIndicesInIndexedColumn = UtilEjml.adjust(gw, matrix.numRows);
        if (indexFirstColumn) {
            this.setIndexColumn(0);
        }
    }

    @Override
    public boolean isSet(int row, int col) {
        if (col != this.indexedColumn) {
            return this.negated ^ this.matrix.isAssigned(row, col);
        }
        return this.negated ^ this.rowIndicesInIndexedColumn[row] - 1 == col;
    }

    @Override
    public boolean isSet(int idx) {
        return this.isSet(idx, 0);
    }

    @Override
    public int getNumCols() {
        return this.matrix.getNumCols();
    }

    @Override
    public int getNumRows() {
        return this.matrix.getNumRows();
    }

    @Override
    public void setIndexColumn(int col) {
        if (this.indexedColumn != col) {
            this.indexedColumn = col;
            for (int i = this.matrix.col_idx[col]; i < this.matrix.col_idx[col + 1]; ++i) {
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
    extends MaskBuilder<DMaskSparseStructural> {
        private DMatrixSparseCSC matrix;
        private boolean indexFirstColumn;
        @Nullable
        private IGrowArray gw;

        public Builder(DMatrixSparseCSC matrix) {
            this.matrix = matrix;
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
        public DMaskSparseStructural build() {
            return new DMaskSparseStructural(this.matrix, this.negated, this.gw, this.indexFirstColumn);
        }
    }
}

