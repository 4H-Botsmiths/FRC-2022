/*
 * Decompiled with CFR 0.150.
 */
package org.ejml.dense.row.linsol.qr;

import org.ejml.data.DMatrixRBlock;
import org.ejml.dense.block.linsol.qr.QrHouseHolderSolver_DDRB;
import org.ejml.dense.row.linsol.LinearSolver_DDRB_to_DDRM;
import org.ejml.interfaces.linsol.LinearSolverDense;

public class LinearSolverQrBlock64_DDRM
extends LinearSolver_DDRB_to_DDRM {
    public LinearSolverQrBlock64_DDRM() {
        super(new QrHouseHolderSolver_DDRB());
    }

    public LinearSolverQrBlock64_DDRM(LinearSolverDense<DMatrixRBlock> alg) {
        super(alg);
    }
}

