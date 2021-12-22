/*
 * Decompiled with CFR 0.150.
 */
package org.ejml.interfaces.decomposition;

import org.ejml.data.Complex_F64;
import org.ejml.data.Matrix;
import org.ejml.interfaces.decomposition.CholeskyDecomposition;

public interface CholeskyDecomposition_F64<MatrixType extends Matrix>
extends CholeskyDecomposition<MatrixType> {
    public Complex_F64 computeDeterminant();
}

