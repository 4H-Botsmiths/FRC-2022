/*
 * Decompiled with CFR 0.150.
 */
package org.ejml.interfaces.decomposition;

import org.ejml.data.Matrix;
import org.ejml.interfaces.decomposition.CholeskyDecomposition;
import org.ejml.interfaces.decomposition.DecompositionSparseInterface;

public interface CholeskySparseDecomposition<MatrixType extends Matrix>
extends CholeskyDecomposition<MatrixType>,
DecompositionSparseInterface<MatrixType> {
}

