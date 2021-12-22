/*
 * Decompiled with CFR 0.150.
 */
package org.ejml.sparse.csc.factory;

import org.ejml.data.DMatrixSparseCSC;
import org.ejml.interfaces.decomposition.CholeskySparseDecomposition_F64;
import org.ejml.interfaces.decomposition.LUSparseDecomposition_F64;
import org.ejml.interfaces.decomposition.QRSparseDecomposition;
import org.ejml.sparse.ComputePermutation;
import org.ejml.sparse.FillReducing;
import org.ejml.sparse.csc.decomposition.chol.CholeskyUpLooking_DSCC;
import org.ejml.sparse.csc.decomposition.lu.LuUpLooking_DSCC;
import org.ejml.sparse.csc.decomposition.qr.QrLeftLookingDecomposition_DSCC;
import org.ejml.sparse.csc.factory.FillReductionFactory_DSCC;

public class DecompositionFactory_DSCC {
    public static CholeskySparseDecomposition_F64 cholesky() {
        return new CholeskyUpLooking_DSCC();
    }

    public static QRSparseDecomposition<DMatrixSparseCSC> qr(FillReducing permutation) {
        ComputePermutation<DMatrixSparseCSC> cp = FillReductionFactory_DSCC.create(permutation);
        return new QrLeftLookingDecomposition_DSCC(cp);
    }

    public static LUSparseDecomposition_F64<DMatrixSparseCSC> lu(FillReducing permutation) {
        ComputePermutation<DMatrixSparseCSC> cp = FillReductionFactory_DSCC.create(permutation);
        return new LuUpLooking_DSCC(cp);
    }
}

