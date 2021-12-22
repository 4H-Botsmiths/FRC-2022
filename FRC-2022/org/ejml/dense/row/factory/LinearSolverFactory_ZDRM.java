/*
 * Decompiled with CFR 0.150.
 */
package org.ejml.dense.row.factory;

import org.ejml.data.ZMatrixRMaj;
import org.ejml.dense.row.decompose.chol.CholeskyDecompositionInner_ZDRM;
import org.ejml.dense.row.decompose.lu.LUDecompositionAlt_ZDRM;
import org.ejml.dense.row.linsol.chol.LinearSolverChol_ZDRM;
import org.ejml.dense.row.linsol.lu.LinearSolverLu_ZDRM;
import org.ejml.dense.row.linsol.qr.LinearSolverQrHouseCol_ZDRM;
import org.ejml.interfaces.linsol.LinearSolverDense;

public class LinearSolverFactory_ZDRM {
    public static LinearSolverDense<ZMatrixRMaj> lu(int matrixSize) {
        return new LinearSolverLu_ZDRM(new LUDecompositionAlt_ZDRM());
    }

    public static LinearSolverDense<ZMatrixRMaj> chol(int matrixSize) {
        return new LinearSolverChol_ZDRM(new CholeskyDecompositionInner_ZDRM());
    }

    public static LinearSolverDense<ZMatrixRMaj> qr(int numRows, int numCols) {
        return new LinearSolverQrHouseCol_ZDRM();
    }
}

