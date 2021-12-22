/*
 * Decompiled with CFR 0.150.
 */
package org.ejml.dense.row.factory;

import org.ejml.data.CMatrixRMaj;
import org.ejml.dense.row.decompose.chol.CholeskyDecompositionInner_CDRM;
import org.ejml.dense.row.decompose.lu.LUDecompositionAlt_CDRM;
import org.ejml.dense.row.linsol.chol.LinearSolverChol_CDRM;
import org.ejml.dense.row.linsol.lu.LinearSolverLu_CDRM;
import org.ejml.dense.row.linsol.qr.LinearSolverQrHouseCol_CDRM;
import org.ejml.interfaces.linsol.LinearSolverDense;

public class LinearSolverFactory_CDRM {
    public static LinearSolverDense<CMatrixRMaj> lu(int matrixSize) {
        return new LinearSolverLu_CDRM(new LUDecompositionAlt_CDRM());
    }

    public static LinearSolverDense<CMatrixRMaj> chol(int matrixSize) {
        return new LinearSolverChol_CDRM(new CholeskyDecompositionInner_CDRM());
    }

    public static LinearSolverDense<CMatrixRMaj> qr(int numRows, int numCols) {
        return new LinearSolverQrHouseCol_CDRM();
    }
}

