/*
 * Decompiled with CFR 0.150.
 */
package org.ejml.dense.row.linsol.svd;

import org.ejml.data.DMatrixRMaj;
import org.ejml.dense.row.CommonOps_DDRM;
import org.ejml.dense.row.SingularOps_DDRM;
import org.ejml.dense.row.factory.DecompositionFactory_DDRM;
import org.ejml.interfaces.SolveNullSpace;
import org.ejml.interfaces.decomposition.SingularValueDecomposition_F64;

public class SolveNullSpaceSvd_DDRM
implements SolveNullSpace<DMatrixRMaj> {
    boolean compact = true;
    SingularValueDecomposition_F64<DMatrixRMaj> svd = DecompositionFactory_DDRM.svd(1, 1, false, true, this.compact);
    DMatrixRMaj V;

    @Override
    public boolean process(DMatrixRMaj input, int numberOfSingular, DMatrixRMaj nullspace) {
        if (input.numCols > input.numRows) {
            if (this.compact) {
                this.svd = DecompositionFactory_DDRM.svd(1, 1, false, true, false);
                this.compact = false;
            }
        } else if (!this.compact) {
            this.svd = DecompositionFactory_DDRM.svd(1, 1, false, true, true);
            this.compact = true;
        }
        if (!this.svd.decompose(input)) {
            return false;
        }
        double[] singularValues = this.svd.getSingularValues();
        this.V = this.svd.getV(this.V, false);
        SingularOps_DDRM.descendingOrder(null, false, singularValues, this.svd.numberOfSingularValues(), this.V, false);
        nullspace.reshape(this.V.numRows, numberOfSingular);
        CommonOps_DDRM.extract(this.V, 0, this.V.numRows, this.V.numCols - numberOfSingular, this.V.numCols, nullspace, 0, 0);
        return true;
    }

    @Override
    public boolean inputModified() {
        return this.svd.inputModified();
    }

    public SingularValueDecomposition_F64<DMatrixRMaj> getSvd() {
        return this.svd;
    }

    public double[] getSingularValues() {
        return this.svd.getSingularValues();
    }
}

