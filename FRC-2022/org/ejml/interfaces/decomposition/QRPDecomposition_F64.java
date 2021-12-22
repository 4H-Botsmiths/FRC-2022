/*
 * Decompiled with CFR 0.150.
 */
package org.ejml.interfaces.decomposition;

import org.ejml.data.Matrix;
import org.ejml.interfaces.decomposition.QRPDecomposition;

public interface QRPDecomposition_F64<T extends Matrix>
extends QRPDecomposition<T> {
    public void setSingularThreshold(double var1);
}

