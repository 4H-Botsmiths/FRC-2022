/*
 * Decompiled with CFR 0.150.
 */
package org.ejml.sparse.csc.factory;

import org.ejml.data.DMatrixRMaj;
import org.ejml.data.DMatrixSparseCSC;
import org.ejml.interfaces.linsol.LinearSolverSparse;
import org.ejml.sparse.ComputePermutation;
import org.ejml.sparse.FillReducing;
import org.ejml.sparse.csc.decomposition.chol.CholeskyUpLooking_DSCC;
import org.ejml.sparse.csc.decomposition.lu.LuUpLooking_DSCC;
import org.ejml.sparse.csc.decomposition.qr.QrLeftLookingDecomposition_DSCC;
import org.ejml.sparse.csc.factory.DecompositionFactory_DSCC;
import org.ejml.sparse.csc.factory.FillReductionFactory_DSCC;
import org.ejml.sparse.csc.linsol.chol.LinearSolverCholesky_DSCC;
import org.ejml.sparse.csc.linsol.lu.LinearSolverLu_DSCC;
import org.ejml.sparse.csc.linsol.qr.LinearSolverQrLeftLooking_DSCC;

public class LinearSolverFactory_DSCC {
    public static LinearSolverSparse<DMatrixSparseCSC, DMatrixRMaj> cholesky(FillReducing permutation) {
        ComputePermutation<DMatrixSparseCSC> cp = FillReductionFactory_DSCC.create(permutation);
        CholeskyUpLooking_DSCC chol = (CholeskyUpLooking_DSCC)DecompositionFactory_DSCC.cholesky();
        return new LinearSolverCholesky_DSCC(chol, cp);
    }

    public static LinearSolverSparse<DMatrixSparseCSC, DMatrixRMaj> qr(FillReducing permutation) {
        ComputePermutation<DMatrixSparseCSC> cp = FillReductionFactory_DSCC.create(permutation);
        QrLeftLookingDecomposition_DSCC qr = new QrLeftLookingDecomposition_DSCC(cp);
        return new LinearSolverQrLeftLooking_DSCC(qr);
    }

    public static LinearSolverSparse<DMatrixSparseCSC, DMatrixRMaj> lu(FillReducing permutation) {
        ComputePermutation<DMatrixSparseCSC> cp = FillReductionFactory_DSCC.create(permutation);
        LuUpLooking_DSCC lu = new LuUpLooking_DSCC(cp);
        return new LinearSolverLu_DSCC(lu);
    }
}

