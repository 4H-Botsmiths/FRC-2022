/*
 * Decompiled with CFR 0.150.
 */
package org.ejml.interfaces.decomposition;

import org.ejml.data.Matrix;
import org.ejml.interfaces.decomposition.DecompositionInterface;

public interface EigenDecomposition<T extends Matrix>
extends DecompositionInterface<T> {
    public int getNumberOfEigenvalues();

    public T getEigenVector(int var1);
}

