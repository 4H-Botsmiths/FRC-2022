/*
 * Decompiled with CFR 0.150.
 */
package org.ejml.dense.row.factory;

import org.ejml.EjmlParameters;
import org.ejml.UtilEjml;
import org.ejml.data.DMatrix;
import org.ejml.data.DMatrixRMaj;
import org.ejml.dense.row.decomposition.chol.CholeskyDecompositionBlock_MT_DDRM;
import org.ejml.dense.row.decomposition.eig.SwitchingEigenDecomposition_DDRM;
import org.ejml.dense.row.decomposition.eig.SymmetricQRAlgorithmDecomposition_DDRM;
import org.ejml.dense.row.decomposition.eig.WatchedDoubleStepQRDecomposition_DDRM;
import org.ejml.dense.row.decomposition.eig.watched.WatchedDoubleStepQREigen_MT_DDRM;
import org.ejml.dense.row.decomposition.hessenberg.HessenbergSimilarDecomposition_MT_DDRM;
import org.ejml.dense.row.decomposition.hessenberg.TridiagonalDecompositionHouseholder_MT_DDRM;
import org.ejml.dense.row.decomposition.qr.QRDecompositionHouseholderColumn_MT_DDRM;
import org.ejml.dense.row.decomposition.svd.SvdImplicitQrDecompose_MT_DDRM;
import org.ejml.interfaces.decomposition.CholeskyDecomposition_F64;
import org.ejml.interfaces.decomposition.DecompositionInterface;
import org.ejml.interfaces.decomposition.EigenDecomposition_F64;
import org.ejml.interfaces.decomposition.QRDecomposition;
import org.ejml.interfaces.decomposition.SingularValueDecomposition_F64;
import org.ejml.interfaces.decomposition.TridiagonalSimilarDecomposition_F64;

public class DecompositionFactory_MT_DDRM {
    public static CholeskyDecomposition_F64<DMatrixRMaj> chol(int matrixSize, boolean lower) {
        return new CholeskyDecompositionBlock_MT_DDRM(EjmlParameters.BLOCK_WIDTH_CHOL);
    }

    public static CholeskyDecomposition_F64<DMatrixRMaj> chol(boolean lower) {
        return DecompositionFactory_MT_DDRM.chol(100, lower);
    }

    public static SingularValueDecomposition_F64<DMatrixRMaj> svd(int numRows, int numCols, boolean needU, boolean needV, boolean compact) {
        return new SvdImplicitQrDecompose_MT_DDRM(compact, needU, needV, false);
    }

    public static SingularValueDecomposition_F64<DMatrixRMaj> svd(boolean needU, boolean needV, boolean compact) {
        return DecompositionFactory_MT_DDRM.svd(100, 100, needU, needV, compact);
    }

    public static QRDecomposition<DMatrixRMaj> qr(int numRows, int numCols) {
        return new QRDecompositionHouseholderColumn_MT_DDRM();
    }

    public static QRDecomposition<DMatrixRMaj> qr() {
        return new QRDecompositionHouseholderColumn_MT_DDRM();
    }

    public static EigenDecomposition_F64<DMatrixRMaj> eig(int matrixSize, boolean needVectors) {
        EigenDecomposition_F64<DMatrixRMaj> symm = DecompositionFactory_MT_DDRM.eig(matrixSize, needVectors, true);
        EigenDecomposition_F64<DMatrixRMaj> general = DecompositionFactory_MT_DDRM.eig(matrixSize, needVectors, false);
        return new SwitchingEigenDecomposition_DDRM(symm, general, UtilEjml.TEST_F64);
    }

    public static EigenDecomposition_F64<DMatrixRMaj> eig(boolean needVectors) {
        return DecompositionFactory_MT_DDRM.eig(100, needVectors);
    }

    public static EigenDecomposition_F64<DMatrixRMaj> eig(int matrixSize, boolean computeVectors, boolean isSymmetric) {
        if (isSymmetric) {
            TridiagonalSimilarDecomposition_F64<DMatrixRMaj> decomp = DecompositionFactory_MT_DDRM.tridiagonal(matrixSize);
            return new SymmetricQRAlgorithmDecomposition_DDRM(decomp, computeVectors);
        }
        HessenbergSimilarDecomposition_MT_DDRM hessenberg = new HessenbergSimilarDecomposition_MT_DDRM();
        WatchedDoubleStepQREigen_MT_DDRM eigenQR = new WatchedDoubleStepQREigen_MT_DDRM();
        return new WatchedDoubleStepQRDecomposition_DDRM(hessenberg, eigenQR, computeVectors);
    }

    public static EigenDecomposition_F64<DMatrixRMaj> eig(boolean computeVectors, boolean isSymmetric) {
        return DecompositionFactory_MT_DDRM.eig(100, computeVectors, isSymmetric);
    }

    public static TridiagonalSimilarDecomposition_F64<DMatrixRMaj> tridiagonal(int matrixSize) {
        return new TridiagonalDecompositionHouseholder_MT_DDRM();
    }

    public static <T extends DMatrix> boolean decomposeSafe(DecompositionInterface<T> decomp, T M) {
        if (decomp.inputModified()) {
            return decomp.decompose((DMatrix)M.copy());
        }
        return decomp.decompose(M);
    }
}

