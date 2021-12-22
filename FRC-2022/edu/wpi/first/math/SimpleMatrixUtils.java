/*
 * Decompiled with CFR 0.150.
 */
package edu.wpi.first.math;

import edu.wpi.first.math.Pair;
import edu.wpi.first.math.WPIMathJNI;
import java.util.function.BiFunction;
import org.ejml.data.DMatrixRMaj;
import org.ejml.dense.row.NormOps_DDRM;
import org.ejml.dense.row.factory.DecompositionFactory_DDRM;
import org.ejml.interfaces.decomposition.CholeskyDecomposition_F64;
import org.ejml.simple.SimpleBase;
import org.ejml.simple.SimpleMatrix;

public final class SimpleMatrixUtils {
    private SimpleMatrixUtils() {
    }

    public static SimpleMatrix expm(SimpleMatrix matrix) {
        BiFunction<SimpleMatrix, SimpleMatrix, SimpleMatrix> solveProvider = SimpleBase::solve;
        SimpleMatrix A = matrix;
        double A_L1 = NormOps_DDRM.inducedP1(matrix.getDDRM());
        int n_squarings = 0;
        if (A_L1 < 0.01495585217958292) {
            Pair<SimpleMatrix, SimpleMatrix> pair = SimpleMatrixUtils._pade3(A);
            return SimpleMatrixUtils.dispatchPade(pair.getFirst(), pair.getSecond(), n_squarings, solveProvider);
        }
        if (A_L1 < 0.253939833006323) {
            Pair<SimpleMatrix, SimpleMatrix> pair = SimpleMatrixUtils._pade5(A);
            return SimpleMatrixUtils.dispatchPade(pair.getFirst(), pair.getSecond(), n_squarings, solveProvider);
        }
        if (A_L1 < 0.9504178996162932) {
            Pair<SimpleMatrix, SimpleMatrix> pair = SimpleMatrixUtils._pade7(A);
            return SimpleMatrixUtils.dispatchPade(pair.getFirst(), pair.getSecond(), n_squarings, solveProvider);
        }
        if (A_L1 < 2.097847961257068) {
            Pair<SimpleMatrix, SimpleMatrix> pair = SimpleMatrixUtils._pade9(A);
            return SimpleMatrixUtils.dispatchPade(pair.getFirst(), pair.getSecond(), n_squarings, solveProvider);
        }
        double maxNorm = 5.371920351148152;
        double log = Math.log(A_L1 / maxNorm) / Math.log(2.0);
        n_squarings = (int)Math.max(0.0, Math.ceil(log));
        A = (SimpleMatrix)A.divide(Math.pow(2.0, n_squarings));
        Pair<SimpleMatrix, SimpleMatrix> pair = SimpleMatrixUtils._pade13(A);
        return SimpleMatrixUtils.dispatchPade(pair.getFirst(), pair.getSecond(), n_squarings, solveProvider);
    }

    private static SimpleMatrix dispatchPade(SimpleMatrix U, SimpleMatrix V, int nSquarings, BiFunction<SimpleMatrix, SimpleMatrix, SimpleMatrix> solveProvider) {
        SimpleMatrix P = U.plus(V);
        SimpleMatrix Q = ((SimpleMatrix)U.negative()).plus(V);
        SimpleMatrix R = solveProvider.apply(Q, P);
        for (int i = 0; i < nSquarings; ++i) {
            R = R.mult(R);
        }
        return R;
    }

    private static Pair<SimpleMatrix, SimpleMatrix> _pade3(SimpleMatrix A) {
        double[] b = new double[]{120.0, 60.0, 12.0, 1.0};
        SimpleMatrix ident = SimpleMatrixUtils.eye(A.numRows(), A.numCols());
        SimpleMatrix A2 = A.mult(A);
        SimpleMatrix U = A.mult(A2.mult((SimpleMatrix)((SimpleMatrix)ident.scale(b[1])).plus(b[3])));
        SimpleMatrix V = ((SimpleMatrix)A2.scale(b[2])).plus((SimpleMatrix)ident.scale(b[0]));
        return new Pair<SimpleMatrix, SimpleMatrix>(U, V);
    }

    private static Pair<SimpleMatrix, SimpleMatrix> _pade5(SimpleMatrix A) {
        double[] b = new double[]{30240.0, 15120.0, 3360.0, 420.0, 30.0, 1.0};
        SimpleMatrix ident = SimpleMatrixUtils.eye(A.numRows(), A.numCols());
        SimpleMatrix A2 = A.mult(A);
        SimpleMatrix A4 = A2.mult(A2);
        SimpleMatrix U = A.mult(((SimpleMatrix)A4.scale(b[5])).plus((SimpleMatrix)A2.scale(b[3])).plus((SimpleMatrix)ident.scale(b[1])));
        SimpleMatrix V = ((SimpleMatrix)A4.scale(b[4])).plus((SimpleMatrix)A2.scale(b[2])).plus((SimpleMatrix)ident.scale(b[0]));
        return new Pair<SimpleMatrix, SimpleMatrix>(U, V);
    }

    private static Pair<SimpleMatrix, SimpleMatrix> _pade7(SimpleMatrix A) {
        double[] b = new double[]{1.729728E7, 8648640.0, 1995840.0, 277200.0, 25200.0, 1512.0, 56.0, 1.0};
        SimpleMatrix ident = SimpleMatrixUtils.eye(A.numRows(), A.numCols());
        SimpleMatrix A2 = A.mult(A);
        SimpleMatrix A4 = A2.mult(A2);
        SimpleMatrix A6 = A4.mult(A2);
        SimpleMatrix U = A.mult(((SimpleMatrix)A6.scale(b[7])).plus((SimpleMatrix)A4.scale(b[5])).plus((SimpleMatrix)A2.scale(b[3])).plus((SimpleMatrix)ident.scale(b[1])));
        SimpleMatrix V = ((SimpleMatrix)A6.scale(b[6])).plus((SimpleMatrix)A4.scale(b[4])).plus((SimpleMatrix)A2.scale(b[2])).plus((SimpleMatrix)ident.scale(b[0]));
        return new Pair<SimpleMatrix, SimpleMatrix>(U, V);
    }

    private static Pair<SimpleMatrix, SimpleMatrix> _pade9(SimpleMatrix A) {
        double[] b = new double[]{1.76432256E10, 8.8216128E9, 2.0756736E9, 3.027024E8, 3.027024E7, 2162160.0, 110880.0, 3960.0, 90.0, 1.0};
        SimpleMatrix ident = SimpleMatrixUtils.eye(A.numRows(), A.numCols());
        SimpleMatrix A2 = A.mult(A);
        SimpleMatrix A4 = A2.mult(A2);
        SimpleMatrix A6 = A4.mult(A2);
        SimpleMatrix A8 = A6.mult(A2);
        SimpleMatrix U = A.mult(((SimpleMatrix)A8.scale(b[9])).plus((SimpleMatrix)A6.scale(b[7])).plus((SimpleMatrix)A4.scale(b[5])).plus((SimpleMatrix)A2.scale(b[3])).plus((SimpleMatrix)ident.scale(b[1])));
        SimpleMatrix V = ((SimpleMatrix)A8.scale(b[8])).plus((SimpleMatrix)A6.scale(b[6])).plus((SimpleMatrix)A4.scale(b[4])).plus((SimpleMatrix)A2.scale(b[2])).plus((SimpleMatrix)ident.scale(b[0]));
        return new Pair<SimpleMatrix, SimpleMatrix>(U, V);
    }

    private static Pair<SimpleMatrix, SimpleMatrix> _pade13(SimpleMatrix A) {
        double[] b = new double[]{6.476475253248E16, 3.238237626624E16, 7.7717703038976E15, 1.1873537964288E15, 1.29060195264E14, 1.05594705216E13, 6.704425728E11, 3.352212864E10, 1.32324192E9, 4.08408E7, 960960.0, 16380.0, 182.0, 1.0};
        SimpleMatrix ident = SimpleMatrixUtils.eye(A.numRows(), A.numCols());
        SimpleMatrix A2 = A.mult(A);
        SimpleMatrix A4 = A2.mult(A2);
        SimpleMatrix A6 = A4.mult(A2);
        SimpleMatrix U = A.mult(((SimpleMatrix)A6.scale(b[13])).plus((SimpleMatrix)A4.scale(b[11])).plus((SimpleMatrix)A2.scale(b[9])).plus((SimpleMatrix)A6.scale(b[7])).plus((SimpleMatrix)A4.scale(b[5])).plus((SimpleMatrix)A2.scale(b[3])).plus((SimpleMatrix)ident.scale(b[1])));
        SimpleMatrix V = A6.mult(((SimpleMatrix)A6.scale(b[12])).plus((SimpleMatrix)A4.scale(b[10])).plus((SimpleMatrix)A2.scale(b[8]))).plus(((SimpleMatrix)A6.scale(b[6])).plus((SimpleMatrix)A4.scale(b[4])).plus((SimpleMatrix)A2.scale(b[2])).plus((SimpleMatrix)ident.scale(b[0])));
        return new Pair<SimpleMatrix, SimpleMatrix>(U, V);
    }

    private static SimpleMatrix eye(int rows, int cols) {
        return SimpleMatrix.identity(Math.min(rows, cols));
    }

    public static SimpleMatrix eye(int rows) {
        return SimpleMatrix.identity(rows);
    }

    public static SimpleMatrix lltDecompose(SimpleMatrix src) {
        return SimpleMatrixUtils.lltDecompose(src, false);
    }

    public static SimpleMatrix lltDecompose(SimpleMatrix src, boolean lowerTriangular) {
        SimpleMatrix temp = (SimpleMatrix)src.copy();
        CholeskyDecomposition_F64<DMatrixRMaj> chol = DecompositionFactory_DDRM.chol(temp.numRows(), lowerTriangular);
        if (!chol.decompose((DMatrixRMaj)temp.getMatrix())) {
            double[] matData = temp.getDDRM().data;
            boolean isZeros = true;
            for (double matDatum : matData) {
                isZeros &= Math.abs(matDatum) < 1.0E-6;
            }
            if (isZeros) {
                return new SimpleMatrix(temp.numRows(), temp.numCols());
            }
            throw new RuntimeException("Cholesky decomposition failed! Input matrix:\n" + src.toString());
        }
        return SimpleMatrix.wrap(chol.getT(null));
    }

    public static SimpleMatrix exp(SimpleMatrix A) {
        SimpleMatrix toReturn = new SimpleMatrix(A.numRows(), A.numRows());
        WPIMathJNI.exp(A.getDDRM().getData(), A.numRows(), toReturn.getDDRM().getData());
        return toReturn;
    }
}

