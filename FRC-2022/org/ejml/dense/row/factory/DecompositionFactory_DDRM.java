/*
 * Decompiled with CFR 0.150.
 */
package org.ejml.dense.row.factory;

import org.ejml.EjmlParameters;
import org.ejml.UtilEjml;
import org.ejml.data.DMatrix;
import org.ejml.data.DMatrixRMaj;
import org.ejml.dense.row.CommonOps_DDRM;
import org.ejml.dense.row.EigenOps_DDRM;
import org.ejml.dense.row.NormOps_DDRM;
import org.ejml.dense.row.SpecializedOps_DDRM;
import org.ejml.dense.row.decomposition.chol.CholeskyDecompositionBlock_DDRM;
import org.ejml.dense.row.decomposition.chol.CholeskyDecompositionInner_DDRM;
import org.ejml.dense.row.decomposition.chol.CholeskyDecompositionLDL_DDRM;
import org.ejml.dense.row.decomposition.chol.CholeskyDecomposition_DDRB_to_DDRM;
import org.ejml.dense.row.decomposition.eig.SwitchingEigenDecomposition_DDRM;
import org.ejml.dense.row.decomposition.eig.SymmetricQRAlgorithmDecomposition_DDRM;
import org.ejml.dense.row.decomposition.eig.WatchedDoubleStepQRDecomposition_DDRM;
import org.ejml.dense.row.decomposition.hessenberg.TridiagonalDecompositionHouseholder_DDRM;
import org.ejml.dense.row.decomposition.hessenberg.TridiagonalDecomposition_DDRB_to_DDRM;
import org.ejml.dense.row.decomposition.lu.LUDecompositionAlt_DDRM;
import org.ejml.dense.row.decomposition.qr.QRColPivDecompositionHouseholderColumn_DDRM;
import org.ejml.dense.row.decomposition.qr.QRDecompositionHouseholderColumn_DDRM;
import org.ejml.dense.row.decomposition.svd.SvdImplicitQrDecompose_DDRM;
import org.ejml.interfaces.decomposition.CholeskyDecomposition_F64;
import org.ejml.interfaces.decomposition.CholeskyLDLDecomposition_F64;
import org.ejml.interfaces.decomposition.DecompositionInterface;
import org.ejml.interfaces.decomposition.EigenDecomposition_F64;
import org.ejml.interfaces.decomposition.LUDecomposition_F64;
import org.ejml.interfaces.decomposition.QRDecomposition;
import org.ejml.interfaces.decomposition.QRPDecomposition_F64;
import org.ejml.interfaces.decomposition.SingularValueDecomposition;
import org.ejml.interfaces.decomposition.SingularValueDecomposition_F64;
import org.ejml.interfaces.decomposition.TridiagonalSimilarDecomposition_F64;

public class DecompositionFactory_DDRM {
    public static CholeskyDecomposition_F64<DMatrixRMaj> chol(int matrixSize, boolean lower) {
        if (matrixSize < EjmlParameters.SWITCH_BLOCK64_CHOLESKY) {
            return new CholeskyDecompositionInner_DDRM(lower);
        }
        if (EjmlParameters.MEMORY == EjmlParameters.MemoryUsage.FASTER) {
            return new CholeskyDecomposition_DDRB_to_DDRM(lower);
        }
        return new CholeskyDecompositionBlock_DDRM(EjmlParameters.BLOCK_WIDTH_CHOL);
    }

    public static CholeskyDecomposition_F64<DMatrixRMaj> chol(boolean lower) {
        return DecompositionFactory_DDRM.chol(100, lower);
    }

    public static CholeskyLDLDecomposition_F64<DMatrixRMaj> cholLDL(int matrixSize) {
        return new CholeskyDecompositionLDL_DDRM();
    }

    public static CholeskyLDLDecomposition_F64<DMatrixRMaj> cholLDL() {
        return new CholeskyDecompositionLDL_DDRM();
    }

    public static LUDecomposition_F64<DMatrixRMaj> lu(int numRows, int numCol) {
        return new LUDecompositionAlt_DDRM();
    }

    public static LUDecomposition_F64<DMatrixRMaj> lu() {
        return new LUDecompositionAlt_DDRM();
    }

    public static SingularValueDecomposition_F64<DMatrixRMaj> svd(int numRows, int numCols, boolean needU, boolean needV, boolean compact) {
        return new SvdImplicitQrDecompose_DDRM(compact, needU, needV, false);
    }

    public static SingularValueDecomposition_F64<DMatrixRMaj> svd(boolean needU, boolean needV, boolean compact) {
        return DecompositionFactory_DDRM.svd(100, 100, needU, needV, compact);
    }

    public static QRDecomposition<DMatrixRMaj> qr(int numRows, int numCols) {
        return new QRDecompositionHouseholderColumn_DDRM();
    }

    public static QRDecomposition<DMatrixRMaj> qr() {
        return new QRDecompositionHouseholderColumn_DDRM();
    }

    public static QRPDecomposition_F64<DMatrixRMaj> qrp(int numRows, int numCols) {
        return new QRColPivDecompositionHouseholderColumn_DDRM();
    }

    public static QRPDecomposition_F64<DMatrixRMaj> qrp() {
        return new QRColPivDecompositionHouseholderColumn_DDRM();
    }

    public static EigenDecomposition_F64<DMatrixRMaj> eig(int matrixSize, boolean needVectors) {
        return new SwitchingEigenDecomposition_DDRM(matrixSize, needVectors, UtilEjml.TEST_F64);
    }

    public static EigenDecomposition_F64<DMatrixRMaj> eig(boolean needVectors) {
        return DecompositionFactory_DDRM.eig(100, needVectors);
    }

    public static EigenDecomposition_F64<DMatrixRMaj> eig(int matrixSize, boolean computeVectors, boolean isSymmetric) {
        if (isSymmetric) {
            TridiagonalSimilarDecomposition_F64<DMatrixRMaj> decomp = DecompositionFactory_DDRM.tridiagonal(matrixSize);
            return new SymmetricQRAlgorithmDecomposition_DDRM(decomp, computeVectors);
        }
        return new WatchedDoubleStepQRDecomposition_DDRM(computeVectors);
    }

    public static EigenDecomposition_F64<DMatrixRMaj> eig(boolean computeVectors, boolean isSymmetric) {
        return DecompositionFactory_DDRM.eig(100, computeVectors, isSymmetric);
    }

    public static double quality(DMatrixRMaj orig, SingularValueDecomposition<DMatrixRMaj> svd) {
        return DecompositionFactory_DDRM.quality(orig, svd.getU(null, false), svd.getW(null), svd.getV(null, true));
    }

    public static double quality(DMatrixRMaj orig, DMatrixRMaj U, DMatrixRMaj W, DMatrixRMaj Vt) {
        DMatrixRMaj UW = new DMatrixRMaj(U.numRows, W.numCols);
        CommonOps_DDRM.mult(U, W, UW);
        DMatrixRMaj foundA = new DMatrixRMaj(UW.numRows, Vt.numCols);
        CommonOps_DDRM.mult(UW, Vt, foundA);
        double normA = NormOps_DDRM.normF(foundA);
        return SpecializedOps_DDRM.diffNormF(orig, foundA) / normA;
    }

    public static double quality(DMatrixRMaj orig, EigenDecomposition_F64<DMatrixRMaj> eig) {
        DMatrixRMaj A = orig;
        DMatrixRMaj V = EigenOps_DDRM.createMatrixV(eig);
        DMatrixRMaj D = EigenOps_DDRM.createMatrixD(eig);
        DMatrixRMaj L = new DMatrixRMaj(A.numRows, V.numCols);
        CommonOps_DDRM.mult(A, V, L);
        DMatrixRMaj R = new DMatrixRMaj(V.numRows, D.numCols);
        CommonOps_DDRM.mult(V, D, R);
        DMatrixRMaj diff = new DMatrixRMaj(L.numRows, L.numCols);
        CommonOps_DDRM.subtract(L, R, diff);
        double top = NormOps_DDRM.normF(diff);
        double bottom = NormOps_DDRM.normF(L);
        double error = top / bottom;
        return error;
    }

    public static TridiagonalSimilarDecomposition_F64<DMatrixRMaj> tridiagonal(int matrixSize) {
        if (matrixSize >= 1800) {
            return new TridiagonalDecomposition_DDRB_to_DDRM();
        }
        return new TridiagonalDecompositionHouseholder_DDRM();
    }

    public static <T extends DMatrix> boolean decomposeSafe(DecompositionInterface<T> decomp, T M) {
        if (decomp.inputModified()) {
            return decomp.decompose((DMatrix)M.copy());
        }
        return decomp.decompose(M);
    }
}

