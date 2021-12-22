/*
 * Decompiled with CFR 0.150.
 */
package org.ejml.dense.row.factory;

import org.ejml.EjmlParameters;
import org.ejml.data.DMatrixRMaj;
import org.ejml.dense.block.linsol.chol.CholeskyOuterSolver_MT_DDRB;
import org.ejml.dense.block.linsol.qr.QrHouseHolderSolver_MT_DDRB;
import org.ejml.dense.row.linsol.chol.LinearSolverChol_DDRB;
import org.ejml.dense.row.linsol.qr.LinearSolverQrBlock64_DDRM;
import org.ejml.dense.row.linsol.qr.LinearSolverQrHouseCol_MT_DDRM;
import org.ejml.interfaces.linsol.LinearSolverDense;

public class LinearSolverFactory_MT_DDRM {
    public static LinearSolverDense<DMatrixRMaj> chol(int numRows) {
        return LinearSolverFactory_MT_DDRM.symmPosDef(numRows);
    }

    public static LinearSolverDense<DMatrixRMaj> qr(int numRows, int numCols) {
        return LinearSolverFactory_MT_DDRM.leastSquares(numRows, numCols);
    }

    public static LinearSolverDense<DMatrixRMaj> leastSquares(int numRows, int numCols) {
        if (numCols < EjmlParameters.SWITCH_BLOCK64_QR) {
            return new LinearSolverQrHouseCol_MT_DDRM();
        }
        if (EjmlParameters.MEMORY == EjmlParameters.MemoryUsage.FASTER) {
            return new LinearSolverQrBlock64_DDRM(new QrHouseHolderSolver_MT_DDRB());
        }
        return new LinearSolverQrHouseCol_MT_DDRM();
    }

    public static LinearSolverDense<DMatrixRMaj> symmPosDef(int matrixWidth) {
        return new LinearSolverChol_DDRB(new CholeskyOuterSolver_MT_DDRB());
    }
}

