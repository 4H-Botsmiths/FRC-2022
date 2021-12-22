/*
 * Decompiled with CFR 0.150.
 */
package org.ejml.interfaces.decomposition;

import org.ejml.data.Matrix;

public interface DecompositionInterface<T extends Matrix> {
    public boolean decompose(T var1);

    public boolean inputModified();
}

