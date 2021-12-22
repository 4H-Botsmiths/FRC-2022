/*
 * Decompiled with CFR 0.150.
 */
package org.ejml.dense.row;

import java.util.Random;
import org.ejml.LinearSolverSafe;
import org.ejml.UtilEjml;
import org.ejml.data.DMatrixRMaj;
import org.ejml.dense.row.CovarianceRandomDraw_DDRM;
import org.ejml.dense.row.MatrixFeatures_DDRM;
import org.ejml.dense.row.factory.LinearSolverFactory_DDRM;
import org.ejml.dense.row.misc.UnrolledInverseFromMinor_DDRM;
import org.ejml.interfaces.linsol.LinearSolverDense;

public class CovarianceOps_DDRM {
    public static double TOL = UtilEjml.TESTP_F64;

    public static boolean isValidFast(DMatrixRMaj cov) {
        return MatrixFeatures_DDRM.isDiagonalPositive(cov);
    }

    public static int isValid(DMatrixRMaj cov) {
        if (!MatrixFeatures_DDRM.isDiagonalPositive(cov)) {
            return 1;
        }
        if (!MatrixFeatures_DDRM.isSymmetric(cov, TOL)) {
            return 2;
        }
        if (!MatrixFeatures_DDRM.isPositiveSemidefinite(cov)) {
            return 3;
        }
        return 0;
    }

    public static boolean invert(DMatrixRMaj cov) {
        return CovarianceOps_DDRM.invert(cov, cov);
    }

    public static boolean invert(DMatrixRMaj cov, DMatrixRMaj cov_inv) {
        if (cov.numCols <= 4) {
            if (cov.numCols != cov.numRows) {
                throw new IllegalArgumentException("Must be a square matrix.");
            }
            if (cov.numCols >= 2) {
                UnrolledInverseFromMinor_DDRM.inv(cov, cov_inv);
            } else {
                cov_inv.data[0] = 1.0 / cov.data[0];
            }
        } else {
            LinearSolverDense<DMatrixRMaj> solver = LinearSolverFactory_DDRM.symmPosDef(cov.numRows);
            if (!(solver = new LinearSolverSafe<DMatrixRMaj>(solver)).setA(cov)) {
                return false;
            }
            solver.invert(cov_inv);
        }
        return true;
    }

    public static void randomVector(DMatrixRMaj cov, DMatrixRMaj vector, Random rand) {
        CovarianceRandomDraw_DDRM rng = new CovarianceRandomDraw_DDRM(rand, cov);
        rng.next(vector);
    }
}

