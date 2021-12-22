/*
 * Decompiled with CFR 0.150.
 */
package org.ejml.simple;

import org.ejml.UtilEjml;
import org.ejml.data.DMatrixRMaj;
import org.ejml.data.FMatrixRMaj;
import org.ejml.data.Matrix;
import org.ejml.dense.row.SingularOps_DDRM;
import org.ejml.dense.row.SingularOps_FDRM;
import org.ejml.dense.row.factory.DecompositionFactory_DDRM;
import org.ejml.dense.row.factory.DecompositionFactory_FDRM;
import org.ejml.interfaces.decomposition.SingularValueDecomposition;
import org.ejml.interfaces.decomposition.SingularValueDecomposition_F32;
import org.ejml.interfaces.decomposition.SingularValueDecomposition_F64;
import org.ejml.simple.SimpleBase;
import org.ejml.simple.SimpleMatrix;

public class SimpleSVD<T extends SimpleBase> {
    private SingularValueDecomposition svd;
    private T U;
    private T W;
    private T V;
    private Matrix mat;
    final boolean is64;
    double tol;

    public SimpleSVD(Matrix mat, boolean compact) {
        this.mat = mat;
        this.is64 = mat instanceof DMatrixRMaj;
        if (this.is64) {
            DMatrixRMaj m = (DMatrixRMaj)mat;
            this.svd = DecompositionFactory_DDRM.svd(m.numRows, m.numCols, true, true, compact);
        } else {
            FMatrixRMaj m = (FMatrixRMaj)mat;
            this.svd = DecompositionFactory_FDRM.svd(m.numRows, m.numCols, true, true, compact);
        }
        if (!this.svd.decompose(mat)) {
            throw new RuntimeException("Decomposition failed");
        }
        this.U = SimpleMatrix.wrap(this.svd.getU(null, false));
        this.W = SimpleMatrix.wrap(this.svd.getW(null));
        this.V = SimpleMatrix.wrap(this.svd.getV(null, false));
        if (this.is64) {
            SingularOps_DDRM.descendingOrder((DMatrixRMaj)((SimpleBase)this.U).getMatrix(), false, (DMatrixRMaj)((SimpleBase)this.W).getMatrix(), (DMatrixRMaj)((SimpleBase)this.V).getMatrix(), false);
            this.tol = SingularOps_DDRM.singularThreshold((SingularValueDecomposition_F64)this.svd);
        } else {
            SingularOps_FDRM.descendingOrder((FMatrixRMaj)((SimpleBase)this.U).getMatrix(), false, (FMatrixRMaj)((SimpleBase)this.W).getMatrix(), (FMatrixRMaj)((SimpleBase)this.V).getMatrix(), false);
            this.tol = SingularOps_FDRM.singularThreshold((SingularValueDecomposition_F32)this.svd);
        }
    }

    public T getU() {
        return this.U;
    }

    public T getW() {
        return this.W;
    }

    public T getV() {
        return this.V;
    }

    public double quality() {
        if (this.is64) {
            return DecompositionFactory_DDRM.quality((DMatrixRMaj)this.mat, (DMatrixRMaj)((SimpleBase)this.U).getMatrix(), (DMatrixRMaj)((SimpleBase)this.W).getMatrix(), (DMatrixRMaj)((SimpleBase)((SimpleBase)this.V).transpose()).getMatrix());
        }
        return DecompositionFactory_FDRM.quality((FMatrixRMaj)this.mat, (FMatrixRMaj)((SimpleBase)this.U).getMatrix(), (FMatrixRMaj)((SimpleBase)this.W).getMatrix(), (FMatrixRMaj)((SimpleBase)((SimpleBase)this.V).transpose()).getMatrix());
    }

    public SimpleMatrix nullSpace() {
        if (this.is64) {
            return SimpleMatrix.wrap(SingularOps_DDRM.nullSpace((SingularValueDecomposition_F64)this.svd, null, this.tol));
        }
        return SimpleMatrix.wrap(SingularOps_FDRM.nullSpace((SingularValueDecomposition_F32)this.svd, null, (float)this.tol));
    }

    public double getSingleValue(int index) {
        return ((SimpleBase)this.W).get(index, index);
    }

    public double[] getSingularValues() {
        double[] ret = new double[((SimpleBase)this.W).numCols()];
        for (int i = 0; i < ret.length; ++i) {
            ret[i] = this.getSingleValue(i);
        }
        return ret;
    }

    public int rank() {
        if (this.is64) {
            return SingularOps_DDRM.rank((SingularValueDecomposition_F64)this.svd, this.tol);
        }
        return SingularOps_FDRM.rank((SingularValueDecomposition_F32)this.svd, (float)this.tol);
    }

    public int nullity() {
        if (this.is64) {
            return SingularOps_DDRM.nullity((SingularValueDecomposition_F64)this.svd, 10.0 * UtilEjml.EPS);
        }
        return SingularOps_FDRM.nullity((SingularValueDecomposition_F32)this.svd, 5.0f * UtilEjml.F_EPS);
    }

    public SingularValueDecomposition getSVD() {
        return this.svd;
    }
}

