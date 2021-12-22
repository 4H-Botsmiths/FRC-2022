/*
 * Decompiled with CFR 0.150.
 */
package org.ejml.dense.row.decomposition.eig;

import org.ejml.data.Complex_F32;
import org.ejml.data.FMatrixRMaj;

public interface EigenvalueExtractor_FDRM {
    public boolean process(FMatrixRMaj var1);

    public int getNumberOfEigenvalues();

    public Complex_F32[] getEigenvalues();
}

