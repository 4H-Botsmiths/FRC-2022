/*
 * Decompiled with CFR 0.150.
 */
package org.ejml.dense.row.linsol.qr;

import org.ejml.data.FMatrixRBlock;
import org.ejml.dense.block.linsol.qr.QrHouseHolderSolver_FDRB;
import org.ejml.dense.row.linsol.LinearSolver_FDRB_to_FDRM;
import org.ejml.interfaces.linsol.LinearSolverDense;

public class LinearSolverQrBlock64_FDRM
extends LinearSolver_FDRB_to_FDRM {
    public LinearSolverQrBlock64_FDRM() {
        super(new QrHouseHolderSolver_FDRB());
    }

    public LinearSolverQrBlock64_FDRM(LinearSolverDense<FMatrixRBlock> alg) {
        super(alg);
    }
}

