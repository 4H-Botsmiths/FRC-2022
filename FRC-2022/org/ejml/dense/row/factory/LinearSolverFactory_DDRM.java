/*
 * Decompiled with CFR 0.150.
 */
package org.ejml.dense.row.factory;

import org.ejml.EjmlParameters;
import org.ejml.data.DMatrixRMaj;
import org.ejml.dense.row.decomposition.chol.CholeskyDecompositionInner_DDRM;
import org.ejml.dense.row.decomposition.lu.LUDecompositionAlt_DDRM;
import org.ejml.dense.row.decomposition.qr.QRColPivDecompositionHouseholderColumn_DDRM;
import org.ejml.dense.row.linsol.AdjustableLinearSolver_DDRM;
import org.ejml.dense.row.linsol.chol.LinearSolverChol_DDRB;
import org.ejml.dense.row.linsol.chol.LinearSolverChol_DDRM;
import org.ejml.dense.row.linsol.lu.LinearSolverLu_DDRM;
import org.ejml.dense.row.linsol.qr.AdjLinearSolverQr_DDRM;
import org.ejml.dense.row.linsol.qr.LinearSolverQrBlock64_DDRM;
import org.ejml.dense.row.linsol.qr.LinearSolverQrHouseCol_DDRM;
import org.ejml.dense.row.linsol.qr.LinearSolverQrpHouseCol_DDRM;
import org.ejml.dense.row.linsol.qr.SolvePseudoInverseQrp_DDRM;
import org.ejml.dense.row.linsol.svd.SolvePseudoInverseSvd_DDRM;
import org.ejml.interfaces.linsol.LinearSolverDense;

public class LinearSolverFactory_DDRM {
    public static LinearSolverDense<DMatrixRMaj> lu(int numRows) {
        return LinearSolverFactory_DDRM.linear(numRows);
    }

    public static LinearSolverDense<DMatrixRMaj> chol(int numRows) {
        return LinearSolverFactory_DDRM.symmPosDef(numRows);
    }

    public static LinearSolverDense<DMatrixRMaj> qr(int numRows, int numCols) {
        return LinearSolverFactory_DDRM.leastSquares(numRows, numCols);
    }

    public static LinearSolverDense<DMatrixRMaj> qrp(boolean computeNorm2, boolean computeQ) {
        return LinearSolverFactory_DDRM.leastSquaresQrPivot(computeNorm2, computeQ);
    }

    public static LinearSolverDense<DMatrixRMaj> general(int numRows, int numCols) {
        if (numRows == numCols) {
            return LinearSolverFactory_DDRM.linear(numRows);
        }
        return LinearSolverFactory_DDRM.leastSquares(numRows, numCols);
    }

    public static LinearSolverDense<DMatrixRMaj> linear(int matrixSize) {
        return new LinearSolverLu_DDRM(new LUDecompositionAlt_DDRM());
    }

    public static LinearSolverDense<DMatrixRMaj> leastSquares(int numRows, int numCols) {
        if (numCols < EjmlParameters.SWITCH_BLOCK64_QR) {
            return new LinearSolverQrHouseCol_DDRM();
        }
        if (EjmlParameters.MEMORY == EjmlParameters.MemoryUsage.FASTER) {
            return new LinearSolverQrBlock64_DDRM();
        }
        return new LinearSolverQrHouseCol_DDRM();
    }

    public static LinearSolverDense<DMatrixRMaj> symmPosDef(int matrixWidth) {
        if (matrixWidth < EjmlParameters.SWITCH_BLOCK64_CHOLESKY) {
            CholeskyDecompositionInner_DDRM decomp = new CholeskyDecompositionInner_DDRM(true);
            return new LinearSolverChol_DDRM(decomp);
        }
        if (EjmlParameters.MEMORY == EjmlParameters.MemoryUsage.FASTER) {
            return new LinearSolverChol_DDRB();
        }
        CholeskyDecompositionInner_DDRM decomp = new CholeskyDecompositionInner_DDRM(true);
        return new LinearSolverChol_DDRM(decomp);
    }

    public static LinearSolverDense<DMatrixRMaj> leastSquaresQrPivot(boolean computeNorm2, boolean computeQ) {
        QRColPivDecompositionHouseholderColumn_DDRM decomposition = new QRColPivDecompositionHouseholderColumn_DDRM();
        if (computeQ) {
            return new SolvePseudoInverseQrp_DDRM(decomposition, computeNorm2);
        }
        return new LinearSolverQrpHouseCol_DDRM(decomposition, computeNorm2);
    }

    public static LinearSolverDense<DMatrixRMaj> pseudoInverse(boolean useSVD) {
        if (useSVD) {
            return new SolvePseudoInverseSvd_DDRM();
        }
        return LinearSolverFactory_DDRM.leastSquaresQrPivot(true, false);
    }

    public static AdjustableLinearSolver_DDRM adjustable() {
        return new AdjLinearSolverQr_DDRM();
    }
}

