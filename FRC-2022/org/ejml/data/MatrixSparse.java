/*
 * Decompiled with CFR 0.150.
 */
package org.ejml.data;

import org.ejml.data.ReshapeMatrix;

public interface MatrixSparse
extends ReshapeMatrix {
    public void printNonZero();

    public void reshape(int var1, int var2, int var3);

    @Override
    public void reshape(int var1, int var2);

    public void shrinkArrays();

    public void remove(int var1, int var2);

    public boolean isAssigned(int var1, int var2);

    @Override
    public void zero();

    public int getNonZeroLength();
}

