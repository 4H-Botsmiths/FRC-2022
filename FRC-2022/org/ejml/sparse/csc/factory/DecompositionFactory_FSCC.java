/*
 * Decompiled with CFR 0.150.
 */
package org.ejml.sparse.csc.factory;

import org.ejml.data.FMatrixSparseCSC;
import org.ejml.interfaces.decomposition.CholeskySparseDecomposition_F32;
import org.ejml.interfaces.decomposition.LUSparseDecomposition_F32;
import org.ejml.interfaces.decomposition.QRSparseDecomposition;
import org.ejml.sparse.ComputePermutation;
import org.ejml.sparse.FillReducing;
import org.ejml.sparse.csc.decomposition.chol.CholeskyUpLooking_FSCC;
import org.ejml.sparse.csc.decomposition.lu.LuUpLooking_FSCC;
import org.ejml.sparse.csc.decomposition.qr.QrLeftLookingDecomposition_FSCC;
import org.ejml.sparse.csc.factory.FillReductionFactory_FSCC;

public class DecompositionFactory_FSCC {
    public static CholeskySparseDecomposition_F32 cholesky() {
        return new CholeskyUpLooking_FSCC();
    }

    public static QRSparseDecomposition<FMatrixSparseCSC> qr(FillReducing permutation) {
        ComputePermutation<FMatrixSparseCSC> cp = FillReductionFactory_FSCC.create(permutation);
        return new QrLeftLookingDecomposition_FSCC(cp);
    }

    public static LUSparseDecomposition_F32<FMatrixSparseCSC> lu(FillReducing permutation) {
        ComputePermutation<FMatrixSparseCSC> cp = FillReductionFactory_FSCC.create(permutation);
        return new LuUpLooking_FSCC(cp);
    }
}

