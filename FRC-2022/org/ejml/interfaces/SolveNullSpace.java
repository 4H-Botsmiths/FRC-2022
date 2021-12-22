/*
 * Decompiled with CFR 0.150.
 */
package org.ejml.interfaces;

import org.ejml.data.Matrix;

public interface SolveNullSpace<T extends Matrix> {
    public boolean process(T var1, int var2, T var3);

    public boolean inputModified();
}

