/*
 * Decompiled with CFR 0.150.
 */
package org.ejml.sparse.csc.factory;

import org.ejml.data.FMatrixRMaj;
import org.ejml.data.FMatrixSparseCSC;
import org.ejml.interfaces.linsol.LinearSolverSparse;
import org.ejml.sparse.ComputePermutation;
import org.ejml.sparse.FillReducing;
import org.ejml.sparse.csc.decomposition.chol.CholeskyUpLooking_FSCC;
import org.ejml.sparse.csc.decomposition.lu.LuUpLooking_FSCC;
import org.ejml.sparse.csc.decomposition.qr.QrLeftLookingDecomposition_FSCC;
import org.ejml.sparse.csc.factory.DecompositionFactory_FSCC;
import org.ejml.sparse.csc.factory.FillReductionFactory_FSCC;
import org.ejml.sparse.csc.linsol.chol.LinearSolverCholesky_FSCC;
import org.ejml.sparse.csc.linsol.lu.LinearSolverLu_FSCC;
import org.ejml.sparse.csc.linsol.qr.LinearSolverQrLeftLooking_FSCC;

public class LinearSolverFactory_FSCC {
    public static LinearSolverSparse<FMatrixSparseCSC, FMatrixRMaj> cholesky(FillReducing permutation) {
        ComputePermutation<FMatrixSparseCSC> cp = FillReductionFactory_FSCC.create(permutation);
        CholeskyUpLooking_FSCC chol = (CholeskyUpLooking_FSCC)DecompositionFactory_FSCC.cholesky();
        return new LinearSolverCholesky_FSCC(chol, cp);
    }

    public static LinearSolverSparse<FMatrixSparseCSC, FMatrixRMaj> qr(FillReducing permutation) {
        ComputePermutation<FMatrixSparseCSC> cp = FillReductionFactory_FSCC.create(permutation);
        QrLeftLookingDecomposition_FSCC qr = new QrLeftLookingDecomposition_FSCC(cp);
        return new LinearSolverQrLeftLooking_FSCC(qr);
    }

    public static LinearSolverSparse<FMatrixSparseCSC, FMatrixRMaj> lu(FillReducing permutation) {
        ComputePermutation<FMatrixSparseCSC> cp = FillReductionFactory_FSCC.create(permutation);
        LuUpLooking_FSCC lu = new LuUpLooking_FSCC(cp);
        return new LinearSolverLu_FSCC(lu);
    }
}

