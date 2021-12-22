/*
 * Decompiled with CFR 0.150.
 */
package org.ejml.interfaces.decomposition;

import org.ejml.data.Matrix;
import org.ejml.interfaces.decomposition.DecompositionSparseInterface;
import org.ejml.interfaces.decomposition.LUDecomposition;

public interface LUSparseDecomposition<MatrixType extends Matrix>
extends LUDecomposition<MatrixType>,
DecompositionSparseInterface<MatrixType> {
}

