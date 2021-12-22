/*
 * Decompiled with CFR 0.150.
 */
package org.ejml.simple;

import java.util.Random;
import org.ejml.data.CMatrixRMaj;
import org.ejml.data.DMatrixRBlock;
import org.ejml.data.DMatrixRMaj;
import org.ejml.data.DMatrixSparseCSC;
import org.ejml.data.FMatrixRBlock;
import org.ejml.data.FMatrixRMaj;
import org.ejml.data.FMatrixSparseCSC;
import org.ejml.data.Matrix;
import org.ejml.data.MatrixType;
import org.ejml.data.ZMatrixRMaj;
import org.ejml.dense.row.CommonOps_DDRM;
import org.ejml.dense.row.CovarianceRandomDraw_DDRM;
import org.ejml.dense.row.CovarianceRandomDraw_FDRM;
import org.ejml.dense.row.RandomMatrices_DDRM;
import org.ejml.dense.row.RandomMatrices_FDRM;
import org.ejml.ops.DConvertMatrixStruct;
import org.ejml.ops.FConvertMatrixStruct;
import org.ejml.simple.SimpleBase;

public class SimpleMatrix
extends SimpleBase<SimpleMatrix> {
    public static final int END = Integer.MAX_VALUE;

    public SimpleMatrix(int numRows, int numCols, boolean rowMajor, double[] data) {
        this.setMatrix(new DMatrixRMaj(numRows, numCols, rowMajor, data));
    }

    public SimpleMatrix(int numRows, int numCols, boolean rowMajor, float[] data) {
        this.setMatrix(new FMatrixRMaj(numRows, numCols, rowMajor, data));
    }

    public SimpleMatrix(double[][] data) {
        this.setMatrix(new DMatrixRMaj(data));
    }

    public SimpleMatrix(float[][] data) {
        this.setMatrix(new FMatrixRMaj(data));
    }

    public SimpleMatrix(int numRows, int numCols) {
        this.setMatrix(new DMatrixRMaj(numRows, numCols));
    }

    public SimpleMatrix(int numRows, int numCols, Class type) {
        this(numRows, numCols, MatrixType.lookup(type));
    }

    public SimpleMatrix(int numRows, int numCols, MatrixType type) {
        switch (type) {
            case DDRM: {
                this.setMatrix(new DMatrixRMaj(numRows, numCols));
                break;
            }
            case FDRM: {
                this.setMatrix(new FMatrixRMaj(numRows, numCols));
                break;
            }
            case ZDRM: {
                this.setMatrix(new ZMatrixRMaj(numRows, numCols));
                break;
            }
            case CDRM: {
                this.setMatrix(new CMatrixRMaj(numRows, numCols));
                break;
            }
            case DSCC: {
                this.setMatrix(new DMatrixSparseCSC(numRows, numCols));
                break;
            }
            case FSCC: {
                this.setMatrix(new FMatrixSparseCSC(numRows, numCols));
                break;
            }
            default: {
                throw new RuntimeException("Unknown matrix type");
            }
        }
    }

    public SimpleMatrix(SimpleMatrix orig) {
        this.setMatrix((Matrix)orig.mat.copy());
    }

    public SimpleMatrix(Matrix orig) {
        Object mat;
        if (orig instanceof DMatrixRBlock) {
            DMatrixRMaj a = new DMatrixRMaj(orig.getNumRows(), orig.getNumCols());
            DConvertMatrixStruct.convert((DMatrixRBlock)orig, a);
            mat = a;
        } else if (orig instanceof FMatrixRBlock) {
            FMatrixRMaj a = new FMatrixRMaj(orig.getNumRows(), orig.getNumCols());
            FConvertMatrixStruct.convert((FMatrixRBlock)orig, a);
            mat = a;
        } else {
            mat = orig.copy();
        }
        this.setMatrix((Matrix)mat);
    }

    protected SimpleMatrix() {
    }

    public static SimpleMatrix wrap(Matrix internalMat) {
        SimpleMatrix ret = new SimpleMatrix();
        ret.setMatrix(internalMat);
        return ret;
    }

    public static SimpleMatrix identity(int width) {
        return SimpleMatrix.identity(width, DMatrixRMaj.class);
    }

    public static SimpleMatrix identity(int width, Class type) {
        SimpleMatrix ret = new SimpleMatrix(width, width, type);
        ret.ops.setIdentity(ret.mat);
        return ret;
    }

    public static SimpleMatrix diag(double ... vals) {
        DMatrixRMaj m = CommonOps_DDRM.diag(vals);
        SimpleMatrix ret = SimpleMatrix.wrap(m);
        return ret;
    }

    public static SimpleMatrix diag(Class type, double ... vals) {
        SimpleMatrix M = new SimpleMatrix(vals.length, vals.length, type);
        for (int i = 0; i < vals.length; ++i) {
            M.set(i, i, vals[i]);
        }
        return M;
    }

    public static SimpleMatrix random_DDRM(int numRows, int numCols, double minValue, double maxValue, Random rand) {
        SimpleMatrix ret = new SimpleMatrix(numRows, numCols);
        RandomMatrices_DDRM.fillUniform((DMatrixRMaj)ret.mat, minValue, maxValue, rand);
        return ret;
    }

    public static SimpleMatrix random_FDRM(int numRows, int numCols, float minValue, float maxValue, Random rand) {
        SimpleMatrix ret = new SimpleMatrix(numRows, numCols, FMatrixRMaj.class);
        RandomMatrices_FDRM.fillUniform((FMatrixRMaj)ret.mat, minValue, maxValue, rand);
        return ret;
    }

    public static SimpleMatrix randomNormal(SimpleMatrix covariance, Random random) {
        SimpleMatrix found = new SimpleMatrix(covariance.numRows(), 1, covariance.getType());
        switch (found.getType()) {
            case DDRM: {
                CovarianceRandomDraw_DDRM draw = new CovarianceRandomDraw_DDRM(random, (DMatrixRMaj)covariance.getMatrix());
                draw.next((DMatrixRMaj)found.getMatrix());
                break;
            }
            case FDRM: {
                CovarianceRandomDraw_FDRM draw = new CovarianceRandomDraw_FDRM(random, (FMatrixRMaj)covariance.getMatrix());
                draw.next((FMatrixRMaj)found.getMatrix());
                break;
            }
            default: {
                throw new IllegalArgumentException("Matrix type is currently not supported");
            }
        }
        return found;
    }

    @Override
    protected SimpleMatrix createMatrix(int numRows, int numCols, MatrixType type) {
        return new SimpleMatrix(numRows, numCols, type);
    }

    @Override
    protected SimpleMatrix wrapMatrix(Matrix m) {
        return new SimpleMatrix(m);
    }
}

