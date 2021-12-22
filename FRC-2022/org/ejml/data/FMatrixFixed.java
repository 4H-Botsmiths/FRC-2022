/*
 * Decompiled with CFR 0.150.
 */
package org.ejml.data;

import org.ejml.data.FMatrix;
import org.ejml.data.Matrix;

public interface FMatrixFixed
extends FMatrix {
    @Override
    default public <T extends Matrix> T create(int numRows, int numCols) {
        if (numRows == this.getNumRows() && numCols == this.getNumCols()) {
            return this.createLike();
        }
        throw new RuntimeException("Fixed sized matrices can't be used to create matrices of arbitrary shape");
    }
}

