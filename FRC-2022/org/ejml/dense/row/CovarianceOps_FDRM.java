/*
 * Decompiled with CFR 0.150.
 */
package org.ejml.dense.row;

import java.util.Random;
import org.ejml.LinearSolverSafe;
import org.ejml.UtilEjml;
import org.ejml.data.FMatrixRMaj;
import org.ejml.dense.row.CovarianceRandomDraw_FDRM;
import org.ejml.dense.row.MatrixFeatures_FDRM;
import org.ejml.dense.row.factory.LinearSolverFactory_FDRM;
import org.ejml.dense.row.misc.UnrolledInverseFromMinor_FDRM;
import org.ejml.interfaces.linsol.LinearSolverDense;

public class CovarianceOps_FDRM {
    public static float TOL = UtilEjml.TESTP_F32;

    public static boolean isValidFast(FMatrixRMaj cov) {
        return MatrixFeatures_FDRM.isDiagonalPositive(cov);
    }

    public static int isValid(FMatrixRMaj cov) {
        if (!MatrixFeatures_FDRM.isDiagonalPositive(cov)) {
            return 1;
        }
        if (!MatrixFeatures_FDRM.isSymmetric(cov, TOL)) {
            return 2;
        }
        if (!MatrixFeatures_FDRM.isPositiveSemidefinite(cov)) {
            return 3;
        }
        return 0;
    }

    public static boolean invert(FMatrixRMaj cov) {
        return CovarianceOps_FDRM.invert(cov, cov);
    }

    public static boolean invert(FMatrixRMaj cov, FMatrixRMaj cov_inv) {
        if (cov.numCols <= 4) {
            if (cov.numCols != cov.numRows) {
                throw new IllegalArgumentException("Must be a square matrix.");
            }
            if (cov.numCols >= 2) {
                UnrolledInverseFromMinor_FDRM.inv(cov, cov_inv);
            } else {
                cov_inv.data[0] = 1.0f / cov.data[0];
            }
        } else {
            LinearSolverDense<FMatrixRMaj> solver = LinearSolverFactory_FDRM.symmPosDef(cov.numRows);
            if (!(solver = new LinearSolverSafe<FMatrixRMaj>(solver)).setA(cov)) {
                return false;
            }
            solver.invert(cov_inv);
        }
        return true;
    }

    public static void randomVector(FMatrixRMaj cov, FMatrixRMaj vector, Random rand) {
        CovarianceRandomDraw_FDRM rng = new CovarianceRandomDraw_FDRM(rand, cov);
        rng.next(vector);
    }
}

