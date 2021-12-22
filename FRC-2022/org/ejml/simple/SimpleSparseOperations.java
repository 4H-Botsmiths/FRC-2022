/*
 * Decompiled with CFR 0.150.
 */
package org.ejml.simple;

import java.io.Serializable;
import org.ejml.data.Matrix;
import org.ejml.data.MatrixSparse;
import org.ejml.simple.SimpleOperations;

public interface SimpleSparseOperations<S extends MatrixSparse, D extends Matrix>
extends SimpleOperations<S>,
Serializable {
    public void extractDiag(S var1, D var2);

    @Override
    public void multTransA(S var1, D var2, D var3);

    @Override
    public void mult(S var1, D var2, D var3);
}

