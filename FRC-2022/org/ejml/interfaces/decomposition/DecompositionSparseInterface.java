/*
 * Decompiled with CFR 0.150.
 */
package org.ejml.interfaces.decomposition;

import org.ejml.data.Matrix;
import org.ejml.interfaces.decomposition.DecompositionInterface;

public interface DecompositionSparseInterface<T extends Matrix>
extends DecompositionInterface<T> {
    public void setStructureLocked(boolean var1);

    public boolean isStructureLocked();
}

