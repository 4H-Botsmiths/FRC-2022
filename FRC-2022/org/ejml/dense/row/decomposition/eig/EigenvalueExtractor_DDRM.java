/*
 * Decompiled with CFR 0.150.
 */
package org.ejml.dense.row.decomposition.eig;

import org.ejml.data.Complex_F64;
import org.ejml.data.DMatrixRMaj;

public interface EigenvalueExtractor_DDRM {
    public boolean process(DMatrixRMaj var1);

    public int getNumberOfEigenvalues();

    public Complex_F64[] getEigenvalues();
}

