/*
 * Decompiled with CFR 0.150.
 */
package org.ejml.dense.row.linsol.svd;

import org.ejml.data.FMatrixRMaj;
import org.ejml.dense.row.CommonOps_FDRM;
import org.ejml.dense.row.SingularOps_FDRM;
import org.ejml.dense.row.factory.DecompositionFactory_FDRM;
import org.ejml.interfaces.SolveNullSpace;
import org.ejml.interfaces.decomposition.SingularValueDecomposition_F32;

public class SolveNullSpaceSvd_FDRM
implements SolveNullSpace<FMatrixRMaj> {
    boolean compact = true;
    SingularValueDecomposition_F32<FMatrixRMaj> svd = DecompositionFactory_FDRM.svd(1, 1, false, true, this.compact);
    FMatrixRMaj V;

    @Override
    public boolean process(FMatrixRMaj input, int numberOfSingular, FMatrixRMaj nullspace) {
        if (input.numCols > input.numRows) {
            if (this.compact) {
                this.svd = DecompositionFactory_FDRM.svd(1, 1, false, true, false);
                this.compact = false;
            }
        } else if (!this.compact) {
            this.svd = DecompositionFactory_FDRM.svd(1, 1, false, true, true);
            this.compact = true;
        }
        if (!this.svd.decompose(input)) {
            return false;
        }
        float[] singularValues = this.svd.getSingularValues();
        this.V = this.svd.getV(this.V, false);
        SingularOps_FDRM.descendingOrder(null, false, singularValues, this.svd.numberOfSingularValues(), this.V, false);
        nullspace.reshape(this.V.numRows, numberOfSingular);
        CommonOps_FDRM.extract(this.V, 0, this.V.numRows, this.V.numCols - numberOfSingular, this.V.numCols, nullspace, 0, 0);
        return true;
    }

    @Override
    public boolean inputModified() {
        return this.svd.inputModified();
    }

    public SingularValueDecomposition_F32<FMatrixRMaj> getSvd() {
        return this.svd;
    }

    public float[] getSingularValues() {
        return this.svd.getSingularValues();
    }
}

