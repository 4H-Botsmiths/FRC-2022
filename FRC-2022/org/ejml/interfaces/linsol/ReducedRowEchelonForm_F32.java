/*
 * Decompiled with CFR 0.150.
 */
package org.ejml.interfaces.linsol;

import org.ejml.data.Matrix;
import org.ejml.interfaces.linsol.ReducedRowEchelonForm;

public interface ReducedRowEchelonForm_F32<T extends Matrix>
extends ReducedRowEchelonForm<T> {
    public void setTolerance(float var1);
}

