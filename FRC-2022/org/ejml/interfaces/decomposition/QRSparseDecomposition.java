/*
 * Decompiled with CFR 0.150.
 */
package org.ejml.interfaces.decomposition;

import org.ejml.data.Matrix;
import org.ejml.interfaces.decomposition.DecompositionSparseInterface;
import org.ejml.interfaces.decomposition.QRDecomposition;

public interface QRSparseDecomposition<T extends Matrix>
extends QRDecomposition<T>,
DecompositionSparseInterface<T> {
}

