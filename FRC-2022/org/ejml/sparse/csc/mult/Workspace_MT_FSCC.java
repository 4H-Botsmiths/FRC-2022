/*
 * Decompiled with CFR 0.150.
 */
package org.ejml.sparse.csc.mult;

import org.ejml.data.FGrowArray;
import org.ejml.data.FMatrixSparseCSC;
import org.ejml.data.IGrowArray;

public class Workspace_MT_FSCC {
    public final IGrowArray gw = new IGrowArray();
    public final FGrowArray gx = new FGrowArray();
    public final FMatrixSparseCSC mat = new FMatrixSparseCSC(1, 1);
}

