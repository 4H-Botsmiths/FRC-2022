/*
 * Decompiled with CFR 0.150.
 */
package org.ejml.data;

import org.ejml.data.CMatrixRMaj;
import org.ejml.data.DMatrixRMaj;
import org.ejml.data.DMatrixSparseCSC;
import org.ejml.data.DMatrixSparseTriplet;
import org.ejml.data.FMatrixRMaj;
import org.ejml.data.FMatrixSparseCSC;
import org.ejml.data.FMatrixSparseTriplet;
import org.ejml.data.Matrix;
import org.ejml.data.ZMatrixRMaj;

public final class MatrixType
extends Enum<MatrixType> {
    public static final /* enum */ MatrixType DDRM = new MatrixType(true, true, 64, DMatrixRMaj.class);
    public static final /* enum */ MatrixType FDRM = new MatrixType(true, true, 32, FMatrixRMaj.class);
    public static final /* enum */ MatrixType ZDRM = new MatrixType(false, true, 64, ZMatrixRMaj.class);
    public static final /* enum */ MatrixType CDRM = new MatrixType(false, true, 32, CMatrixRMaj.class);
    public static final /* enum */ MatrixType DSCC = new MatrixType(true, false, 64, DMatrixSparseCSC.class);
    public static final /* enum */ MatrixType FSCC = new MatrixType(true, false, 32, FMatrixSparseCSC.class);
    public static final /* enum */ MatrixType ZSCC = new MatrixType(false, false, 64, Object.class);
    public static final /* enum */ MatrixType CSCC = new MatrixType(false, false, 32, Object.class);
    public static final /* enum */ MatrixType DTRIPLET = new MatrixType(false, false, 64, DMatrixSparseTriplet.class);
    public static final /* enum */ MatrixType FTRIPLET = new MatrixType(false, false, 64, FMatrixSparseTriplet.class);
    public static final /* enum */ MatrixType UNSPECIFIED = new MatrixType(false, false, 0, Object.class);
    final boolean fixed;
    final boolean dense;
    final boolean real;
    final int bits;
    final Class<?> classType;
    private static final /* synthetic */ MatrixType[] $VALUES;

    public static MatrixType[] values() {
        return (MatrixType[])$VALUES.clone();
    }

    public static MatrixType valueOf(String name) {
        return Enum.valueOf(MatrixType.class, name);
    }

    private MatrixType(boolean real, boolean dense, int bits, Class<?> type) {
        this(false, real, dense, bits, type);
    }

    private MatrixType(boolean fixed, boolean real, boolean dense, int bits, Class<?> type) {
        this.real = real;
        this.fixed = fixed;
        this.dense = dense;
        this.bits = bits;
        this.classType = type;
    }

    public static MatrixType lookup(Class<?> type) {
        if (type == DMatrixRMaj.class) {
            return DDRM;
        }
        if (type == FMatrixRMaj.class) {
            return FDRM;
        }
        if (type == ZMatrixRMaj.class) {
            return ZDRM;
        }
        if (type == CMatrixRMaj.class) {
            return CDRM;
        }
        if (type == DMatrixSparseCSC.class) {
            return DSCC;
        }
        if (type == FMatrixSparseCSC.class) {
            return FSCC;
        }
        throw new IllegalArgumentException("Unknown class");
    }

    public static MatrixType lookup(boolean dense, boolean real, int bits) {
        if (dense) {
            if (real) {
                if (bits == 64) {
                    return DDRM;
                }
                return FDRM;
            }
            if (bits == 64) {
                return ZDRM;
            }
            return CDRM;
        }
        if (real) {
            if (bits == 64) {
                return DSCC;
            }
            return FSCC;
        }
        throw new IllegalArgumentException("Complex sparse not yet supported");
    }

    public boolean isReal() {
        return this.real;
    }

    public boolean isFixed() {
        return this.fixed;
    }

    public boolean isDense() {
        return this.dense;
    }

    public int getBits() {
        return this.bits;
    }

    public Class getClassType() {
        return this.classType;
    }

    public Matrix create(int rows, int cols) {
        switch (this) {
            case DDRM: {
                return new DMatrixRMaj(rows, cols);
            }
            case FDRM: {
                return new FMatrixRMaj(rows, cols);
            }
            case ZDRM: {
                return new ZMatrixRMaj(rows, cols);
            }
            case CDRM: {
                return new CMatrixRMaj(rows, cols);
            }
            case DSCC: {
                return new DMatrixSparseCSC(rows, cols);
            }
            case FSCC: {
                return new FMatrixSparseCSC(rows, cols);
            }
        }
        throw new RuntimeException("Unknown Matrix Type " + (Object)((Object)this));
    }

    private static /* synthetic */ MatrixType[] $values() {
        return new MatrixType[]{DDRM, FDRM, ZDRM, CDRM, DSCC, FSCC, ZSCC, CSCC, DTRIPLET, FTRIPLET, UNSPECIFIED};
    }

    static {
        $VALUES = MatrixType.$values();
    }
}

