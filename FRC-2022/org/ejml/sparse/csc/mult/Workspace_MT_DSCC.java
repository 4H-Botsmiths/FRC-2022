/*
 * Decompiled with CFR 0.150.
 */
package org.ejml.sparse.csc.mult;

import org.ejml.data.DGrowArray;
import org.ejml.data.DMatrixSparseCSC;
import org.ejml.data.IGrowArray;

public class Workspace_MT_DSCC {
    public final IGrowArray gw = new IGrowArray();
    public final DGrowArray gx = new DGrowArray();
    public final DMatrixSparseCSC mat = new DMatrixSparseCSC(1, 1);
}

