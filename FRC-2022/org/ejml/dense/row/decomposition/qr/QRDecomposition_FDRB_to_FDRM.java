/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  org.jetbrains.annotations.Nullable
 */
package org.ejml.dense.row.decomposition.qr;

import org.ejml.EjmlParameters;
import org.ejml.data.FMatrixRBlock;
import org.ejml.data.FMatrixRMaj;
import org.ejml.dense.block.MatrixOps_FDRB;
import org.ejml.dense.block.decomposition.qr.QRDecompositionHouseholder_FDRB;
import org.ejml.dense.row.decomposition.BaseDecomposition_FDRB_to_FDRM;
import org.ejml.dense.row.decomposition.UtilDecompositons_FDRM;
import org.ejml.interfaces.decomposition.QRDecomposition;
import org.jetbrains.annotations.Nullable;

public class QRDecomposition_FDRB_to_FDRM
extends BaseDecomposition_FDRB_to_FDRM
implements QRDecomposition<FMatrixRMaj> {
    public QRDecomposition_FDRB_to_FDRM() {
        super(new QRDecompositionHouseholder_FDRB(), EjmlParameters.BLOCK_WIDTH);
    }

    @Override
    public FMatrixRMaj getQ(@Nullable FMatrixRMaj Q, boolean compact) {
        int minLength = Math.min(this.Ablock.numRows, this.Ablock.numCols);
        Q = compact ? UtilDecompositons_FDRM.ensureIdentity(Q, this.Ablock.numRows, minLength) : UtilDecompositons_FDRM.ensureIdentity(Q, this.Ablock.numRows, this.Ablock.numRows);
        FMatrixRBlock Qblock = new FMatrixRBlock();
        Qblock.numRows = Q.numRows;
        Qblock.numCols = Q.numCols;
        Qblock.blockLength = this.blockLength;
        Qblock.data = Q.data;
        ((QRDecompositionHouseholder_FDRB)this.alg).getQ(Qblock, compact);
        this.convertBlockToRow(Q.numRows, Q.numCols, Q.data);
        return Q;
    }

    @Override
    public FMatrixRMaj getR(@Nullable FMatrixRMaj R, boolean compact) {
        FMatrixRBlock Rblock = ((QRDecompositionHouseholder_FDRB)this.alg).getR((FMatrixRBlock)null, compact);
        if (R == null) {
            R = new FMatrixRMaj(Rblock.numRows, Rblock.numCols);
        }
        MatrixOps_FDRB.convert(Rblock, R);
        return R;
    }
}

