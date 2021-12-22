/*
 * Decompiled with CFR 0.150.
 */
package org.ejml.interfaces.decomposition;

import org.ejml.data.Complex_F64;
import org.ejml.data.Matrix;
import org.ejml.interfaces.decomposition.LUSparseDecomposition;

public interface LUSparseDecomposition_F64<T extends Matrix>
extends LUSparseDecomposition<T> {
    public Complex_F64 computeDeterminant();
}

