/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  org.jetbrains.annotations.Nullable
 */
package org.ejml.sparse;

import org.ejml.data.IGrowArray;
import org.ejml.data.Matrix;
import org.jetbrains.annotations.Nullable;

public abstract class ComputePermutation<T extends Matrix> {
    @Nullable
    protected IGrowArray prow;
    @Nullable
    protected IGrowArray pcol;

    protected ComputePermutation(boolean hasRow, boolean hasCol) {
        if (hasRow) {
            this.prow = new IGrowArray();
        }
        if (hasCol) {
            this.pcol = new IGrowArray();
        }
    }

    public abstract void process(T var1);

    @Nullable
    public IGrowArray getRow() {
        return this.prow;
    }

    @Nullable
    public IGrowArray getColumn() {
        return this.pcol;
    }

    public boolean hasRowPermutation() {
        return this.prow != null;
    }

    public boolean hasColumnPermutation() {
        return this.pcol != null;
    }
}

