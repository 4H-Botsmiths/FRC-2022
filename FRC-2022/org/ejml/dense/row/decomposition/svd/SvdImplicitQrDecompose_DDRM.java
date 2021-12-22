/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  org.jetbrains.annotations.NotNull
 *  org.jetbrains.annotations.Nullable
 */
package org.ejml.dense.row.decomposition.svd;

import org.ejml.UtilEjml;
import org.ejml.data.DMatrixRMaj;
import org.ejml.dense.row.CommonOps_DDRM;
import org.ejml.dense.row.decomposition.bidiagonal.BidiagonalDecompositionRow_DDRM;
import org.ejml.dense.row.decomposition.bidiagonal.BidiagonalDecompositionTall_DDRM;
import org.ejml.dense.row.decomposition.svd.implicitqr.SvdImplicitQrAlgorithm_DDRM;
import org.ejml.interfaces.decomposition.BidiagonalDecomposition_F64;
import org.ejml.interfaces.decomposition.SingularValueDecomposition_F64;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class SvdImplicitQrDecompose_DDRM
implements SingularValueDecomposition_F64<DMatrixRMaj> {
    protected int numRows;
    protected int numCols;
    protected int numRowsT;
    protected int numColsT;
    protected boolean canUseTallBidiagonal;
    protected BidiagonalDecomposition_F64<DMatrixRMaj> bidiag;
    protected SvdImplicitQrAlgorithm_DDRM qralg = new SvdImplicitQrAlgorithm_DDRM();
    double[] diag;
    double[] off;
    private DMatrixRMaj Ut;
    private DMatrixRMaj Vt;
    private double[] singularValues;
    private int numSingular;
    protected boolean compact;
    protected boolean computeU;
    protected boolean computeV;
    protected boolean prefComputeU;
    protected boolean prefComputeV;
    protected boolean transposed;
    private DMatrixRMaj A_mod = new DMatrixRMaj(1, 1);

    public SvdImplicitQrDecompose_DDRM(boolean compact, boolean computeU, boolean computeV, boolean canUseTallBidiagonal) {
        this.compact = compact;
        this.prefComputeU = computeU;
        this.prefComputeV = computeV;
        this.canUseTallBidiagonal = canUseTallBidiagonal;
    }

    @Override
    public double[] getSingularValues() {
        return this.singularValues;
    }

    @Override
    public int numberOfSingularValues() {
        return this.numSingular;
    }

    @Override
    public boolean isCompact() {
        return this.compact;
    }

    @Override
    public DMatrixRMaj getU(@Nullable DMatrixRMaj U, boolean transpose) {
        if (!this.prefComputeU) {
            throw new IllegalArgumentException("As requested U was not computed.");
        }
        if (transpose) {
            if (U == null) {
                return this.Ut;
            }
            U.setTo(this.Ut);
        } else {
            U = UtilEjml.reshapeOrDeclare(U, this.Ut.numCols, this.Ut.numRows);
            this.transpose(U, this.Ut);
        }
        return U;
    }

    @Override
    public DMatrixRMaj getV(@Nullable DMatrixRMaj V, boolean transpose) {
        if (!this.prefComputeV) {
            throw new IllegalArgumentException("As requested V was not computed.");
        }
        if (transpose) {
            if (V == null) {
                return this.Vt;
            }
            V.setTo(this.Vt);
        } else {
            V = UtilEjml.reshapeOrDeclare(V, this.Vt.numCols, this.Vt.numRows);
            this.transpose(V, this.Vt);
        }
        return V;
    }

    protected void transpose(@NotNull DMatrixRMaj V, DMatrixRMaj Vt) {
        CommonOps_DDRM.transpose(Vt, V);
    }

    @Override
    public DMatrixRMaj getW(@Nullable DMatrixRMaj W) {
        int n;
        int m = this.compact ? this.numSingular : this.numRows;
        int n2 = n = this.compact ? this.numSingular : this.numCols;
        if (W == null) {
            W = new DMatrixRMaj(m, n);
        } else {
            W.reshape(m, n, false);
            W.zero();
        }
        for (int i = 0; i < this.numSingular; ++i) {
            W.unsafe_set(i, i, this.singularValues[i]);
        }
        return W;
    }

    @Override
    public boolean decompose(DMatrixRMaj orig) {
        if (!this.setup(orig)) {
            return false;
        }
        if (this.bidiagonalization(orig)) {
            return false;
        }
        if (this.computeUWV()) {
            return false;
        }
        this.makeSingularPositive();
        this.undoTranspose();
        return true;
    }

    @Override
    public boolean inputModified() {
        return false;
    }

    private boolean bidiagonalization(DMatrixRMaj orig) {
        if (this.transposed) {
            this.A_mod.reshape(orig.numCols, orig.numRows, false);
            this.transpose(this.A_mod, orig);
        } else {
            this.A_mod.reshape(orig.numRows, orig.numCols, false);
            this.A_mod.setTo(orig);
        }
        return !this.bidiag.decompose(this.A_mod);
    }

    private void undoTranspose() {
        if (this.transposed) {
            DMatrixRMaj temp = this.Vt;
            this.Vt = this.Ut;
            this.Ut = temp;
        }
    }

    private boolean computeUWV() {
        this.bidiag.getDiagonal(this.diag, this.off);
        this.qralg.setMatrix(this.numRowsT, this.numColsT, this.diag, this.off);
        if (this.computeU) {
            this.Ut = this.bidiag.getU(this.Ut, true, this.compact);
        }
        if (this.computeV) {
            this.Vt = this.bidiag.getV(this.Vt, true, this.compact);
        }
        this.qralg.setFastValues(false);
        if (this.computeU) {
            this.qralg.setUt(this.Ut);
        } else {
            this.qralg.setUt(null);
        }
        if (this.computeV) {
            this.qralg.setVt(this.Vt);
        } else {
            this.qralg.setVt(null);
        }
        boolean ret = !this.qralg.process();
        return ret;
    }

    private boolean setup(DMatrixRMaj orig) {
        boolean bl = this.transposed = orig.numCols > orig.numRows;
        if (this.transposed) {
            this.computeU = this.prefComputeV;
            this.computeV = this.prefComputeU;
            this.numRowsT = orig.numCols;
            this.numColsT = orig.numRows;
        } else {
            this.computeU = this.prefComputeU;
            this.computeV = this.prefComputeV;
            this.numRowsT = orig.numRows;
            this.numColsT = orig.numCols;
        }
        this.numRows = orig.numRows;
        this.numCols = orig.numCols;
        if (this.numRows == 0 || this.numCols == 0) {
            return false;
        }
        if (this.diag == null || this.diag.length < this.numColsT) {
            this.diag = new double[this.numColsT];
            this.off = new double[this.numColsT - 1];
        }
        this.declareBidiagonalDecomposition();
        return true;
    }

    protected void declareBidiagonalDecomposition() {
        if (this.canUseTallBidiagonal && this.numRows > this.numCols * 2 && !this.computeU) {
            if (this.bidiag == null || !(this.bidiag instanceof BidiagonalDecompositionTall_DDRM)) {
                this.bidiag = new BidiagonalDecompositionTall_DDRM();
            }
        } else if (this.bidiag == null || !(this.bidiag instanceof BidiagonalDecompositionRow_DDRM)) {
            this.bidiag = new BidiagonalDecompositionRow_DDRM();
        }
    }

    private void makeSingularPositive() {
        this.numSingular = this.qralg.getNumberOfSingularValues();
        this.singularValues = this.qralg.getSingularValues();
        for (int i = 0; i < this.numSingular; ++i) {
            double val = this.qralg.getSingularValue(i);
            if (val < 0.0) {
                this.singularValues[i] = 0.0 - val;
                if (!this.computeU) continue;
                int start = i * this.Ut.numCols;
                int stop = start + this.Ut.numCols;
                for (int j = start; j < stop; ++j) {
                    this.Ut.set(j, 0.0 - this.Ut.get(j));
                }
                continue;
            }
            this.singularValues[i] = val;
        }
    }

    @Override
    public int numRows() {
        return this.numRows;
    }

    @Override
    public int numCols() {
        return this.numCols;
    }
}

