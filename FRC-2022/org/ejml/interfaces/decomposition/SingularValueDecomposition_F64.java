/*
 * Decompiled with CFR 0.150.
 */
package org.ejml.interfaces.decomposition;

import org.ejml.data.Matrix;
import org.ejml.interfaces.decomposition.SingularValueDecomposition;

public interface SingularValueDecomposition_F64<T extends Matrix>
extends SingularValueDecomposition<T> {
    public double[] getSingularValues();
}

