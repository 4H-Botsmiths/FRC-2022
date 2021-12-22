/*
 * Decompiled with CFR 0.150.
 */
package org.ejml.dense.row.factory;

import org.ejml.EjmlParameters;
import org.ejml.UtilEjml;
import org.ejml.data.FMatrix;
import org.ejml.data.FMatrixRMaj;
import org.ejml.dense.row.CommonOps_FDRM;
import org.ejml.dense.row.EigenOps_FDRM;
import org.ejml.dense.row.NormOps_FDRM;
import org.ejml.dense.row.SpecializedOps_FDRM;
import org.ejml.dense.row.decomposition.chol.CholeskyDecompositionBlock_FDRM;
import org.ejml.dense.row.decomposition.chol.CholeskyDecompositionInner_FDRM;
import org.ejml.dense.row.decomposition.chol.CholeskyDecompositionLDL_FDRM;
import org.ejml.dense.row.decomposition.chol.CholeskyDecomposition_FDRB_to_FDRM;
import org.ejml.dense.row.decomposition.eig.SwitchingEigenDecomposition_FDRM;
import org.ejml.dense.row.decomposition.eig.SymmetricQRAlgorithmDecomposition_FDRM;
import org.ejml.dense.row.decomposition.eig.WatchedDoubleStepQRDecomposition_FDRM;
import org.ejml.dense.row.decomposition.hessenberg.TridiagonalDecompositionHouseholder_FDRM;
import org.ejml.dense.row.decomposition.hessenberg.TridiagonalDecomposition_FDRB_to_FDRM;
import org.ejml.dense.row.decomposition.lu.LUDecompositionAlt_FDRM;
import org.ejml.dense.row.decomposition.qr.QRColPivDecompositionHouseholderColumn_FDRM;
import org.ejml.dense.row.decomposition.qr.QRDecompositionHouseholderColumn_FDRM;
import org.ejml.dense.row.decomposition.svd.SvdImplicitQrDecompose_FDRM;
import org.ejml.interfaces.decomposition.CholeskyDecomposition_F32;
import org.ejml.interfaces.decomposition.CholeskyLDLDecomposition_F32;
import org.ejml.interfaces.decomposition.DecompositionInterface;
import org.ejml.interfaces.decomposition.EigenDecomposition_F32;
import org.ejml.interfaces.decomposition.LUDecomposition_F32;
import org.ejml.interfaces.decomposition.QRDecomposition;
import org.ejml.interfaces.decomposition.QRPDecomposition_F32;
import org.ejml.interfaces.decomposition.SingularValueDecomposition;
import org.ejml.interfaces.decomposition.SingularValueDecomposition_F32;
import org.ejml.interfaces.decomposition.TridiagonalSimilarDecomposition_F32;

public class DecompositionFactory_FDRM {
    public static CholeskyDecomposition_F32<FMatrixRMaj> chol(int matrixSize, boolean lower) {
        if (matrixSize < EjmlParameters.SWITCH_BLOCK64_CHOLESKY) {
            return new CholeskyDecompositionInner_FDRM(lower);
        }
        if (EjmlParameters.MEMORY == EjmlParameters.MemoryUsage.FASTER) {
            return new CholeskyDecomposition_FDRB_to_FDRM(lower);
        }
        return new CholeskyDecompositionBlock_FDRM(EjmlParameters.BLOCK_WIDTH_CHOL);
    }

    public static CholeskyDecomposition_F32<FMatrixRMaj> chol(boolean lower) {
        return DecompositionFactory_FDRM.chol(100, lower);
    }

    public static CholeskyLDLDecomposition_F32<FMatrixRMaj> cholLDL(int matrixSize) {
        return new CholeskyDecompositionLDL_FDRM();
    }

    public static CholeskyLDLDecomposition_F32<FMatrixRMaj> cholLDL() {
        return new CholeskyDecompositionLDL_FDRM();
    }

    public static LUDecomposition_F32<FMatrixRMaj> lu(int numRows, int numCol) {
        return new LUDecompositionAlt_FDRM();
    }

    public static LUDecomposition_F32<FMatrixRMaj> lu() {
        return new LUDecompositionAlt_FDRM();
    }

    public static SingularValueDecomposition_F32<FMatrixRMaj> svd(int numRows, int numCols, boolean needU, boolean needV, boolean compact) {
        return new SvdImplicitQrDecompose_FDRM(compact, needU, needV, false);
    }

    public static SingularValueDecomposition_F32<FMatrixRMaj> svd(boolean needU, boolean needV, boolean compact) {
        return DecompositionFactory_FDRM.svd(100, 100, needU, needV, compact);
    }

    public static QRDecomposition<FMatrixRMaj> qr(int numRows, int numCols) {
        return new QRDecompositionHouseholderColumn_FDRM();
    }

    public static QRDecomposition<FMatrixRMaj> qr() {
        return new QRDecompositionHouseholderColumn_FDRM();
    }

    public static QRPDecomposition_F32<FMatrixRMaj> qrp(int numRows, int numCols) {
        return new QRColPivDecompositionHouseholderColumn_FDRM();
    }

    public static QRPDecomposition_F32<FMatrixRMaj> qrp() {
        return new QRColPivDecompositionHouseholderColumn_FDRM();
    }

    public static EigenDecomposition_F32<FMatrixRMaj> eig(int matrixSize, boolean needVectors) {
        return new SwitchingEigenDecomposition_FDRM(matrixSize, needVectors, UtilEjml.TEST_F32);
    }

    public static EigenDecomposition_F32<FMatrixRMaj> eig(boolean needVectors) {
        return DecompositionFactory_FDRM.eig(100, needVectors);
    }

    public static EigenDecomposition_F32<FMatrixRMaj> eig(int matrixSize, boolean computeVectors, boolean isSymmetric) {
        if (isSymmetric) {
            TridiagonalSimilarDecomposition_F32<FMatrixRMaj> decomp = DecompositionFactory_FDRM.tridiagonal(matrixSize);
            return new SymmetricQRAlgorithmDecomposition_FDRM(decomp, computeVectors);
        }
        return new WatchedDoubleStepQRDecomposition_FDRM(computeVectors);
    }

    public static EigenDecomposition_F32<FMatrixRMaj> eig(boolean computeVectors, boolean isSymmetric) {
        return DecompositionFactory_FDRM.eig(100, computeVectors, isSymmetric);
    }

    public static float quality(FMatrixRMaj orig, SingularValueDecomposition<FMatrixRMaj> svd) {
        return DecompositionFactory_FDRM.quality(orig, svd.getU(null, false), svd.getW(null), svd.getV(null, true));
    }

    public static float quality(FMatrixRMaj orig, FMatrixRMaj U, FMatrixRMaj W, FMatrixRMaj Vt) {
        FMatrixRMaj UW = new FMatrixRMaj(U.numRows, W.numCols);
        CommonOps_FDRM.mult(U, W, UW);
        FMatrixRMaj foundA = new FMatrixRMaj(UW.numRows, Vt.numCols);
        CommonOps_FDRM.mult(UW, Vt, foundA);
        float normA = NormOps_FDRM.normF(foundA);
        return SpecializedOps_FDRM.diffNormF(orig, foundA) / normA;
    }

    public static float quality(FMatrixRMaj orig, EigenDecomposition_F32<FMatrixRMaj> eig) {
        FMatrixRMaj A = orig;
        FMatrixRMaj V = EigenOps_FDRM.createMatrixV(eig);
        FMatrixRMaj D = EigenOps_FDRM.createMatrixD(eig);
        FMatrixRMaj L = new FMatrixRMaj(A.numRows, V.numCols);
        CommonOps_FDRM.mult(A, V, L);
        FMatrixRMaj R = new FMatrixRMaj(V.numRows, D.numCols);
        CommonOps_FDRM.mult(V, D, R);
        FMatrixRMaj diff = new FMatrixRMaj(L.numRows, L.numCols);
        CommonOps_FDRM.subtract(L, R, diff);
        float top = NormOps_FDRM.normF(diff);
        float bottom = NormOps_FDRM.normF(L);
        float error = top / bottom;
        return error;
    }

    public static TridiagonalSimilarDecomposition_F32<FMatrixRMaj> tridiagonal(int matrixSize) {
        if (matrixSize >= 1800) {
            return new TridiagonalDecomposition_FDRB_to_FDRM();
        }
        return new TridiagonalDecompositionHouseholder_FDRM();
    }

    public static <T extends FMatrix> boolean decomposeSafe(DecompositionInterface<T> decomp, T M) {
        if (decomp.inputModified()) {
            return decomp.decompose((FMatrix)M.copy());
        }
        return decomp.decompose(M);
    }
}

