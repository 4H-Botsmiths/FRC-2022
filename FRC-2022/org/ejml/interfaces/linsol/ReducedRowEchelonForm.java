/*
 * Decompiled with CFR 0.150.
 */
package org.ejml.interfaces.linsol;

import org.ejml.data.Matrix;

public interface ReducedRowEchelonForm<T extends Matrix> {
    public void reduce(T var1, int var2);
}

