/*
 * Decompiled with CFR 0.150.
 */
package org.ejml.masks;

import org.ejml.masks.Mask;
import org.ejml.masks.MaskBuilder;

public class DMaskPrimitive
extends Mask {
    private final double[] values;
    public final int numCols;
    public final double zeroElement;

    public DMaskPrimitive(double[] values, int numCols, boolean negated, double zeroElement) {
        super(negated);
        this.values = values;
        this.numCols = numCols;
        this.zeroElement = zeroElement;
    }

    @Override
    public boolean isSet(int row, int col) {
        return this.negated ^ this.values[row * this.numCols + col] != this.zeroElement;
    }

    @Override
    public int getNumCols() {
        return this.numCols;
    }

    @Override
    public int getNumRows() {
        return this.values.length / this.numCols;
    }

    @Override
    public void setIndexColumn(int column) {
    }

    @Override
    public int maxMaskedEntries() {
        return this.values.length;
    }

    @Override
    public boolean isSet(int index) {
        return this.negated ^ this.values[index] != this.zeroElement;
    }

    public static class Builder
    extends MaskBuilder<DMaskPrimitive> {
        private double[] values;
        private int numCols = 1;
        private double zeroElement = 0.0;

        public Builder(double[] values) {
            this.values = values;
        }

        public Builder withNumCols(int numCols) {
            this.numCols = numCols;
            return this;
        }

        public Builder withZeroElement(double zeroElement) {
            this.zeroElement = zeroElement;
            return this;
        }

        @Override
        public DMaskPrimitive build() {
            return new DMaskPrimitive(this.values, this.numCols, this.negated, this.zeroElement);
        }
    }
}

