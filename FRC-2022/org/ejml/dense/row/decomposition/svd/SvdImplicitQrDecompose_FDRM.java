/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  org.jetbrains.annotations.NotNull
 *  org.jetbrains.annotations.Nullable
 */
package org.ejml.dense.row.decomposition.svd;

import org.ejml.UtilEjml;
import org.ejml.data.FMatrixRMaj;
import org.ejml.dense.row.CommonOps_FDRM;
import org.ejml.dense.row.decomposition.bidiagonal.BidiagonalDecompositionRow_FDRM;
import org.ejml.dense.row.decomposition.bidiagonal.BidiagonalDecompositionTall_FDRM;
import org.ejml.dense.row.decomposition.svd.implicitqr.SvdImplicitQrAlgorithm_FDRM;
import org.ejml.interfaces.decomposition.BidiagonalDecomposition_F32;
import org.ejml.interfaces.decomposition.SingularValueDecomposition_F32;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class SvdImplicitQrDecompose_FDRM
implements SingularValueDecomposition_F32<FMatrixRMaj> {
    protected int numRows;
    protected int numCols;
    protected int numRowsT;
    protected int numColsT;
    protected boolean canUseTallBidiagonal;
    protected BidiagonalDecomposition_F32<FMatrixRMaj> bidiag;
    protected SvdImplicitQrAlgorithm_FDRM qralg = new SvdImplicitQrAlgorithm_FDRM();
    float[] diag;
    float[] off;
    private FMatrixRMaj Ut;
    private FMatrixRMaj Vt;
    private float[] singularValues;
    private int numSingular;
    protected boolean compact;
    protected boolean computeU;
    protected boolean computeV;
    protected boolean prefComputeU;
    protected boolean prefComputeV;
    protected boolean transposed;
    private FMatrixRMaj A_mod = new FMatrixRMaj(1, 1);

    public SvdImplicitQrDecompose_FDRM(boolean compact, boolean computeU, boolean computeV, boolean canUseTallBidiagonal) {
        this.compact = compact;
        this.prefComputeU = computeU;
        this.prefComputeV = computeV;
        this.canUseTallBidiagonal = canUseTallBidiagonal;
    }

    @Override
    public float[] getSingularValues() {
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
    public FMatrixRMaj getU(@Nullable FMatrixRMaj U, boolean transpose) {
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
    public FMatrixRMaj getV(@Nullable FMatrixRMaj V, boolean transpose) {
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

    protected void transpose(@NotNull FMatrixRMaj V, FMatrixRMaj Vt) {
        CommonOps_FDRM.transpose(Vt, V);
    }

    @Override
    public FMatrixRMaj getW(@Nullable FMatrixRMaj W) {
        int n;
        int m = this.compact ? this.numSingular : this.numRows;
        int n2 = n = this.compact ? this.numSingular : this.numCols;
        if (W == null) {
            W = new FMatrixRMaj(m, n);
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
    public boolean decompose(FMatrixRMaj orig) {
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

    private boolean bidiagonalization(FMatrixRMaj orig) {
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
            FMatrixRMaj temp = this.Vt;
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

    private boolean setup(FMatrixRMaj orig) {
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
            this.diag = new float[this.numColsT];
            this.off = new float[this.numColsT - 1];
        }
        this.declareBidiagonalDecomposition();
        return true;
    }

    protected void declareBidiagonalDecomposition() {
        if (this.canUseTallBidiagonal && this.numRows > this.numCols * 2 && !this.computeU) {
            if (this.bidiag == null || !(this.bidiag instanceof BidiagonalDecompositionTall_FDRM)) {
                this.bidiag = new BidiagonalDecompositionTall_FDRM();
            }
        } else if (this.bidiag == null || !(this.bidiag instanceof BidiagonalDecompositionRow_FDRM)) {
            this.bidiag = new BidiagonalDecompositionRow_FDRM();
        }
    }

    private void makeSingularPositive() {
        this.numSingular = this.qralg.getNumberOfSingularValues();
        this.singularValues = this.qralg.getSingularValues();
        for (int i = 0; i < this.numSingular; ++i) {
            float val = this.qralg.getSingularValue(i);
            if (val < 0.0f) {
                this.singularValues[i] = 0.0f - val;
                if (!this.computeU) continue;
                int start = i * this.Ut.numCols;
                int stop = start + this.Ut.numCols;
                for (int j = start; j < stop; ++j) {
                    this.Ut.set(j, 0.0f - this.Ut.get(j));
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

