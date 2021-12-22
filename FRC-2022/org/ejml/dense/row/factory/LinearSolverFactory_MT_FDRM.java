/*
 * Decompiled with CFR 0.150.
 */
package org.ejml.dense.row.factory;

import org.ejml.EjmlParameters;
import org.ejml.data.FMatrixRMaj;
import org.ejml.dense.block.linsol.chol.CholeskyOuterSolver_MT_FDRB;
import org.ejml.dense.block.linsol.qr.QrHouseHolderSolver_MT_FDRB;
import org.ejml.dense.row.linsol.chol.LinearSolverChol_FDRB;
import org.ejml.dense.row.linsol.qr.LinearSolverQrBlock64_FDRM;
import org.ejml.dense.row.linsol.qr.LinearSolverQrHouseCol_MT_FDRM;
import org.ejml.interfaces.linsol.LinearSolverDense;

public class LinearSolverFactory_MT_FDRM {
    public static LinearSolverDense<FMatrixRMaj> chol(int numRows) {
        return LinearSolverFactory_MT_FDRM.symmPosDef(numRows);
    }

    public static LinearSolverDense<FMatrixRMaj> qr(int numRows, int numCols) {
        return LinearSolverFactory_MT_FDRM.leastSquares(numRows, numCols);
    }

    public static LinearSolverDense<FMatrixRMaj> leastSquares(int numRows, int numCols) {
        if (numCols < EjmlParameters.SWITCH_BLOCK64_QR) {
            return new LinearSolverQrHouseCol_MT_FDRM();
        }
        if (EjmlParameters.MEMORY == EjmlParameters.MemoryUsage.FASTER) {
            return new LinearSolverQrBlock64_FDRM(new QrHouseHolderSolver_MT_FDRB());
        }
        return new LinearSolverQrHouseCol_MT_FDRM();
    }

    public static LinearSolverDense<FMatrixRMaj> symmPosDef(int matrixWidth) {
        return new LinearSolverChol_FDRB(new CholeskyOuterSolver_MT_FDRB());
    }
}

