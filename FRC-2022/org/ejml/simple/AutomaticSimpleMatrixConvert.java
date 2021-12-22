/*
 * Decompiled with CFR 0.150.
 */
package org.ejml.simple;

import org.ejml.data.Matrix;
import org.ejml.data.MatrixType;
import org.ejml.ops.ConvertMatrixType;
import org.ejml.simple.SimpleBase;

public class AutomaticSimpleMatrixConvert {
    MatrixType commonType;

    public void specify0(SimpleBase a, SimpleBase ... inputs) {
        SimpleBase[] array = new SimpleBase[inputs.length + 1];
        System.arraycopy(inputs, 0, array, 0, inputs.length);
        array[inputs.length] = a;
        this.specify(inputs);
    }

    public void specify(SimpleBase ... inputs) {
        boolean dense = false;
        boolean real = true;
        int bits = 32;
        for (SimpleBase s : inputs) {
            MatrixType t = s.mat.getType();
            if (t.isDense()) {
                dense = true;
            }
            if (!t.isReal()) {
                real = false;
            }
            if (t.getBits() != 64) continue;
            bits = 64;
        }
        this.commonType = MatrixType.lookup(dense, real, bits);
    }

    public <T extends SimpleBase<T>> T convert(SimpleBase matrix) {
        Matrix m;
        if (matrix.getType() == this.commonType) {
            return (T)matrix;
        }
        if (!matrix.getType().isDense() && this.commonType.isDense()) {
            System.err.println("\n***** WARNING *****\n");
            System.err.println("Converting a sparse to dense matrix automatically.");
            System.err.println("Current auto convert code isn't that smart and this might have been available");
        }
        if ((m = ConvertMatrixType.convert(matrix.mat, this.commonType)) == null) {
            throw new IllegalArgumentException("Conversion from " + (Object)((Object)matrix.getType()) + " to " + (Object)((Object)this.commonType) + " not possible");
        }
        return matrix.wrapMatrix(m);
    }
}

