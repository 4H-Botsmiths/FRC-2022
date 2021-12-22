/*
 * Decompiled with CFR 0.150.
 */
package org.ejml.dense.row.factory;

import org.ejml.EjmlParameters;
import org.ejml.data.FMatrixRMaj;
import org.ejml.dense.row.decomposition.chol.CholeskyDecompositionInner_FDRM;
import org.ejml.dense.row.decomposition.lu.LUDecompositionAlt_FDRM;
import org.ejml.dense.row.decomposition.qr.QRColPivDecompositionHouseholderColumn_FDRM;
import org.ejml.dense.row.linsol.AdjustableLinearSolver_FDRM;
import org.ejml.dense.row.linsol.chol.LinearSolverChol_FDRB;
import org.ejml.dense.row.linsol.chol.LinearSolverChol_FDRM;
import org.ejml.dense.row.linsol.lu.LinearSolverLu_FDRM;
import org.ejml.dense.row.linsol.qr.AdjLinearSolverQr_FDRM;
import org.ejml.dense.row.linsol.qr.LinearSolverQrBlock64_FDRM;
import org.ejml.dense.row.linsol.qr.LinearSolverQrHouseCol_FDRM;
import org.ejml.dense.row.linsol.qr.LinearSolverQrpHouseCol_FDRM;
import org.ejml.dense.row.linsol.qr.SolvePseudoInverseQrp_FDRM;
import org.ejml.dense.row.linsol.svd.SolvePseudoInverseSvd_FDRM;
import org.ejml.interfaces.linsol.LinearSolverDense;

public class LinearSolverFactory_FDRM {
    public static LinearSolverDense<FMatrixRMaj> lu(int numRows) {
        return LinearSolverFactory_FDRM.linear(numRows);
    }

    public static LinearSolverDense<FMatrixRMaj> chol(int numRows) {
        return LinearSolverFactory_FDRM.symmPosDef(numRows);
    }

    public static LinearSolverDense<FMatrixRMaj> qr(int numRows, int numCols) {
        return LinearSolverFactory_FDRM.leastSquares(numRows, numCols);
    }

    public static LinearSolverDense<FMatrixRMaj> qrp(boolean computeNorm2, boolean computeQ) {
        return LinearSolverFactory_FDRM.leastSquaresQrPivot(computeNorm2, computeQ);
    }

    public static LinearSolverDense<FMatrixRMaj> general(int numRows, int numCols) {
        if (numRows == numCols) {
            return LinearSolverFactory_FDRM.linear(numRows);
        }
        return LinearSolverFactory_FDRM.leastSquares(numRows, numCols);
    }

    public static LinearSolverDense<FMatrixRMaj> linear(int matrixSize) {
        return new LinearSolverLu_FDRM(new LUDecompositionAlt_FDRM());
    }

    public static LinearSolverDense<FMatrixRMaj> leastSquares(int numRows, int numCols) {
        if (numCols < EjmlParameters.SWITCH_BLOCK64_QR) {
            return new LinearSolverQrHouseCol_FDRM();
        }
        if (EjmlParameters.MEMORY == EjmlParameters.MemoryUsage.FASTER) {
            return new LinearSolverQrBlock64_FDRM();
        }
        return new LinearSolverQrHouseCol_FDRM();
    }

    public static LinearSolverDense<FMatrixRMaj> symmPosDef(int matrixWidth) {
        if (matrixWidth < EjmlParameters.SWITCH_BLOCK64_CHOLESKY) {
            CholeskyDecompositionInner_FDRM decomp = new CholeskyDecompositionInner_FDRM(true);
            return new LinearSolverChol_FDRM(decomp);
        }
        if (EjmlParameters.MEMORY == EjmlParameters.MemoryUsage.FASTER) {
            return new LinearSolverChol_FDRB();
        }
        CholeskyDecompositionInner_FDRM decomp = new CholeskyDecompositionInner_FDRM(true);
        return new LinearSolverChol_FDRM(decomp);
    }

    public static LinearSolverDense<FMatrixRMaj> leastSquaresQrPivot(boolean computeNorm2, boolean computeQ) {
        QRColPivDecompositionHouseholderColumn_FDRM decomposition = new QRColPivDecompositionHouseholderColumn_FDRM();
        if (computeQ) {
            return new SolvePseudoInverseQrp_FDRM(decomposition, computeNorm2);
        }
        return new LinearSolverQrpHouseCol_FDRM(decomposition, computeNorm2);
    }

    public static LinearSolverDense<FMatrixRMaj> pseudoInverse(boolean useSVD) {
        if (useSVD) {
            return new SolvePseudoInverseSvd_FDRM();
        }
        return LinearSolverFactory_FDRM.leastSquaresQrPivot(true, false);
    }

    public static AdjustableLinearSolver_FDRM adjustable() {
        return new AdjLinearSolverQr_FDRM();
    }
}

