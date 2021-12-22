/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  org.jetbrains.annotations.Nullable
 */
package org.ejml.dense.row.decomposition.qr;

import org.ejml.EjmlParameters;
import org.ejml.data.DMatrixRBlock;
import org.ejml.data.DMatrixRMaj;
import org.ejml.dense.block.MatrixOps_DDRB;
import org.ejml.dense.block.decomposition.qr.QRDecompositionHouseholder_DDRB;
import org.ejml.dense.row.decomposition.BaseDecomposition_DDRB_to_DDRM;
import org.ejml.dense.row.decomposition.UtilDecompositons_DDRM;
import org.ejml.interfaces.decomposition.QRDecomposition;
import org.jetbrains.annotations.Nullable;

public class QRDecomposition_DDRB_to_DDRM
extends BaseDecomposition_DDRB_to_DDRM
implements QRDecomposition<DMatrixRMaj> {
    public QRDecomposition_DDRB_to_DDRM() {
        super(new QRDecompositionHouseholder_DDRB(), EjmlParameters.BLOCK_WIDTH);
    }

    @Override
    public DMatrixRMaj getQ(@Nullable DMatrixRMaj Q, boolean compact) {
        int minLength = Math.min(this.Ablock.numRows, this.Ablock.numCols);
        Q = compact ? UtilDecompositons_DDRM.ensureIdentity(Q, this.Ablock.numRows, minLength) : UtilDecompositons_DDRM.ensureIdentity(Q, this.Ablock.numRows, this.Ablock.numRows);
        DMatrixRBlock Qblock = new DMatrixRBlock();
        Qblock.numRows = Q.numRows;
        Qblock.numCols = Q.numCols;
        Qblock.blockLength = this.blockLength;
        Qblock.data = Q.data;
        ((QRDecompositionHouseholder_DDRB)this.alg).getQ(Qblock, compact);
        this.convertBlockToRow(Q.numRows, Q.numCols, Q.data);
        return Q;
    }

    @Override
    public DMatrixRMaj getR(@Nullable DMatrixRMaj R, boolean compact) {
        DMatrixRBlock Rblock = ((QRDecompositionHouseholder_DDRB)this.alg).getR((DMatrixRBlock)null, compact);
        if (R == null) {
            R = new DMatrixRMaj(Rblock.numRows, Rblock.numCols);
        }
        MatrixOps_DDRB.convert(Rblock, R);
        return R;
    }
}

