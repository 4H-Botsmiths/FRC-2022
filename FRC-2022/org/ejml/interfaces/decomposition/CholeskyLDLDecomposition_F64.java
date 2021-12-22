/*
 * Decompiled with CFR 0.150.
 */
package org.ejml.interfaces.decomposition;

import org.ejml.data.Matrix;
import org.ejml.interfaces.decomposition.CholeskyLDLDecomposition;

public interface CholeskyLDLDecomposition_F64<MatrixType extends Matrix>
extends CholeskyLDLDecomposition<MatrixType> {
    public double[] getDiagonal();
}

