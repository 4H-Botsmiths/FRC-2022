/*
 * Decompiled with CFR 0.150.
 */
package org.ejml.dense.row.factory;

import org.ejml.EjmlParameters;
import org.ejml.UtilEjml;
import org.ejml.data.FMatrix;
import org.ejml.data.FMatrixRMaj;
import org.ejml.dense.row.decomposition.chol.CholeskyDecompositionBlock_MT_FDRM;
import org.ejml.dense.row.decomposition.eig.SwitchingEigenDecomposition_FDRM;
import org.ejml.dense.row.decomposition.eig.SymmetricQRAlgorithmDecomposition_FDRM;
import org.ejml.dense.row.decomposition.eig.WatchedDoubleStepQRDecomposition_FDRM;
import org.ejml.dense.row.decomposition.eig.watched.WatchedDoubleStepQREigen_MT_FDRM;
import org.ejml.dense.row.decomposition.hessenberg.HessenbergSimilarDecomposition_MT_FDRM;
import org.ejml.dense.row.decomposition.hessenberg.TridiagonalDecompositionHouseholder_MT_FDRM;
import org.ejml.dense.row.decomposition.qr.QRDecompositionHouseholderColumn_MT_FDRM;
import org.ejml.dense.row.decomposition.svd.SvdImplicitQrDecompose_MT_FDRM;
import org.ejml.interfaces.decomposition.CholeskyDecomposition_F32;
import org.ejml.interfaces.decomposition.DecompositionInterface;
import org.ejml.interfaces.decomposition.EigenDecomposition_F32;
import org.ejml.interfaces.decomposition.QRDecomposition;
import org.ejml.interfaces.decomposition.SingularValueDecomposition_F32;
import org.ejml.interfaces.decomposition.TridiagonalSimilarDecomposition_F32;

public class DecompositionFactory_MT_FDRM {
    public static CholeskyDecomposition_F32<FMatrixRMaj> chol(int matrixSize, boolean lower) {
        return new CholeskyDecompositionBlock_MT_FDRM(EjmlParameters.BLOCK_WIDTH_CHOL);
    }

    public static CholeskyDecomposition_F32<FMatrixRMaj> chol(boolean lower) {
        return DecompositionFactory_MT_FDRM.chol(100, lower);
    }

    public static SingularValueDecomposition_F32<FMatrixRMaj> svd(int numRows, int numCols, boolean needU, boolean needV, boolean compact) {
        return new SvdImplicitQrDecompose_MT_FDRM(compact, needU, needV, false);
    }

    public static SingularValueDecomposition_F32<FMatrixRMaj> svd(boolean needU, boolean needV, boolean compact) {
        return DecompositionFactory_MT_FDRM.svd(100, 100, needU, needV, compact);
    }

    public static QRDecomposition<FMatrixRMaj> qr(int numRows, int numCols) {
        return new QRDecompositionHouseholderColumn_MT_FDRM();
    }

    public static QRDecomposition<FMatrixRMaj> qr() {
        return new QRDecompositionHouseholderColumn_MT_FDRM();
    }

    public static EigenDecomposition_F32<FMatrixRMaj> eig(int matrixSize, boolean needVectors) {
        EigenDecomposition_F32<FMatrixRMaj> symm = DecompositionFactory_MT_FDRM.eig(matrixSize, needVectors, true);
        EigenDecomposition_F32<FMatrixRMaj> general = DecompositionFactory_MT_FDRM.eig(matrixSize, needVectors, false);
        return new SwitchingEigenDecomposition_FDRM(symm, general, UtilEjml.TEST_F32);
    }

    public static EigenDecomposition_F32<FMatrixRMaj> eig(boolean needVectors) {
        return DecompositionFactory_MT_FDRM.eig(100, needVectors);
    }

    public static EigenDecomposition_F32<FMatrixRMaj> eig(int matrixSize, boolean computeVectors, boolean isSymmetric) {
        if (isSymmetric) {
            TridiagonalSimilarDecomposition_F32<FMatrixRMaj> decomp = DecompositionFactory_MT_FDRM.tridiagonal(matrixSize);
            return new SymmetricQRAlgorithmDecomposition_FDRM(decomp, computeVectors);
        }
        HessenbergSimilarDecomposition_MT_FDRM hessenberg = new HessenbergSimilarDecomposition_MT_FDRM();
        WatchedDoubleStepQREigen_MT_FDRM eigenQR = new WatchedDoubleStepQREigen_MT_FDRM();
        return new WatchedDoubleStepQRDecomposition_FDRM(hessenberg, eigenQR, computeVectors);
    }

    public static EigenDecomposition_F32<FMatrixRMaj> eig(boolean computeVectors, boolean isSymmetric) {
        return DecompositionFactory_MT_FDRM.eig(100, computeVectors, isSymmetric);
    }

    public static TridiagonalSimilarDecomposition_F32<FMatrixRMaj> tridiagonal(int matrixSize) {
        return new TridiagonalDecompositionHouseholder_MT_FDRM();
    }

    public static <T extends FMatrix> boolean decomposeSafe(DecompositionInterface<T> decomp, T M) {
        if (decomp.inputModified()) {
            return decomp.decompose((FMatrix)M.copy());
        }
        return decomp.decompose(M);
    }
}

