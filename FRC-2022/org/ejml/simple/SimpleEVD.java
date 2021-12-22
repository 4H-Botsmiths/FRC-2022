/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  org.jetbrains.annotations.Nullable
 */
package org.ejml.simple;

import java.util.ArrayList;
import java.util.List;
import org.ejml.data.Complex_F32;
import org.ejml.data.Complex_F64;
import org.ejml.data.DMatrixRMaj;
import org.ejml.data.FMatrixRMaj;
import org.ejml.data.Matrix;
import org.ejml.dense.row.factory.DecompositionFactory_DDRM;
import org.ejml.dense.row.factory.DecompositionFactory_FDRM;
import org.ejml.interfaces.decomposition.EigenDecomposition;
import org.ejml.interfaces.decomposition.EigenDecomposition_F32;
import org.ejml.interfaces.decomposition.EigenDecomposition_F64;
import org.ejml.simple.SimpleBase;
import org.ejml.simple.SimpleMatrix;
import org.jetbrains.annotations.Nullable;

public class SimpleEVD<T extends SimpleBase> {
    private EigenDecomposition eig;
    Matrix mat;

    public SimpleEVD(Matrix mat) {
        this.mat = mat;
        switch (mat.getType()) {
            case DDRM: {
                this.eig = DecompositionFactory_DDRM.eig(mat.getNumCols(), true);
                break;
            }
            case FDRM: {
                this.eig = DecompositionFactory_FDRM.eig(mat.getNumCols(), true);
                break;
            }
            default: {
                throw new IllegalArgumentException("Matrix type not yet supported. " + (Object)((Object)mat.getType()));
            }
        }
        if (!this.eig.decompose(mat)) {
            throw new RuntimeException("Eigenvalue Decomposition failed");
        }
    }

    public List<Complex_F64> getEigenvalues() {
        ArrayList<Complex_F64> ret = new ArrayList<Complex_F64>();
        if (this.mat.getType().getBits() == 64) {
            EigenDecomposition_F64 d = (EigenDecomposition_F64)this.eig;
            for (int i = 0; i < this.eig.getNumberOfEigenvalues(); ++i) {
                ret.add(d.getEigenvalue(i));
            }
        } else {
            EigenDecomposition_F32 d = (EigenDecomposition_F32)this.eig;
            for (int i = 0; i < this.eig.getNumberOfEigenvalues(); ++i) {
                Complex_F32 c = d.getEigenvalue(i);
                ret.add(new Complex_F64(c.real, c.imaginary));
            }
        }
        return ret;
    }

    public int getNumberOfEigenvalues() {
        return this.eig.getNumberOfEigenvalues();
    }

    public Complex_F64 getEigenvalue(int index) {
        if (this.mat.getType().getBits() == 64) {
            return ((EigenDecomposition_F64)this.eig).getEigenvalue(index);
        }
        Complex_F64 c = ((EigenDecomposition_F64)this.eig).getEigenvalue(index);
        return new Complex_F64(c.real, c.imaginary);
    }

    @Nullable
    public T getEigenVector(int index) {
        Object v = this.eig.getEigenVector(index);
        if (v == null) {
            return null;
        }
        return (T)SimpleMatrix.wrap(v);
    }

    public double quality() {
        if (this.mat.getType().getBits() == 64) {
            return DecompositionFactory_DDRM.quality((DMatrixRMaj)this.mat, (EigenDecomposition_F64)this.eig);
        }
        return DecompositionFactory_FDRM.quality((FMatrixRMaj)this.mat, (EigenDecomposition_F32)this.eig);
    }

    public EigenDecomposition getEVD() {
        return this.eig;
    }

    public int getIndexMax() {
        int indexMax = 0;
        double max = this.getEigenvalue(0).getMagnitude2();
        int N = this.getNumberOfEigenvalues();
        for (int i = 1; i < N; ++i) {
            double m = this.getEigenvalue(i).getMagnitude2();
            if (!(m > max)) continue;
            max = m;
            indexMax = i;
        }
        return indexMax;
    }

    public int getIndexMin() {
        int indexMin = 0;
        double min = this.getEigenvalue(0).getMagnitude2();
        int N = this.getNumberOfEigenvalues();
        for (int i = 1; i < N; ++i) {
            double m = this.getEigenvalue(i).getMagnitude2();
            if (!(m < min)) continue;
            min = m;
            indexMin = i;
        }
        return indexMin;
    }
}

