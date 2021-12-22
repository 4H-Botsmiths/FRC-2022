/*
 * Decompiled with CFR 0.150.
 */
package org.ejml.interfaces.decomposition;

import org.ejml.data.Complex_F32;
import org.ejml.data.Matrix;
import org.ejml.interfaces.decomposition.CholeskySparseDecomposition;

public interface CholeskySparseDecomposition_F32<MatrixType extends Matrix>
extends CholeskySparseDecomposition<MatrixType> {
    public Complex_F32 computeDeterminant();
}

