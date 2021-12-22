/*
 * Decompiled with CFR 0.150.
 */
package org.ejml.interfaces.decomposition;

import org.ejml.data.Complex_F64;
import org.ejml.data.Matrix;
import org.ejml.interfaces.decomposition.LUDecomposition;

public interface LUDecomposition_F64<T extends Matrix>
extends LUDecomposition<T> {
    public Complex_F64 computeDeterminant();
}

