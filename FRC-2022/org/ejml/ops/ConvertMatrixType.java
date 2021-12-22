/*
 * Decompiled with CFR 0.150.
 */
package org.ejml.ops;

import org.ejml.data.CMatrix;
import org.ejml.data.CMatrixRMaj;
import org.ejml.data.DMatrix;
import org.ejml.data.DMatrixRMaj;
import org.ejml.data.DMatrixSparseCSC;
import org.ejml.data.FMatrix;
import org.ejml.data.FMatrixRMaj;
import org.ejml.data.FMatrixSparseCSC;
import org.ejml.data.Matrix;
import org.ejml.data.MatrixType;
import org.ejml.data.ReshapeMatrix;
import org.ejml.data.ZMatrix;
import org.ejml.data.ZMatrixRMaj;
import org.ejml.ops.ConvertMatrixData;
import org.ejml.ops.DConvertMatrixStruct;
import org.ejml.ops.FConvertMatrixStruct;

public class ConvertMatrixType {
    public static Matrix convert(Matrix matrix, MatrixType desired) {
        ReshapeMatrix m = null;
        block0 : switch (matrix.getType()) {
            case DDRM: {
                switch (desired) {
                    case DDRM: {
                        m = (ReshapeMatrix)matrix.copy();
                        break block0;
                    }
                    case FDRM: {
                        m = new FMatrixRMaj(matrix.getNumRows(), matrix.getNumCols());
                        ConvertMatrixData.convert((DMatrixRMaj)matrix, m);
                        break block0;
                    }
                    case ZDRM: {
                        m = new ZMatrixRMaj(matrix.getNumRows(), matrix.getNumCols());
                        ConvertMatrixData.convert((DMatrixRMaj)matrix, (ZMatrixRMaj)m);
                        break block0;
                    }
                    case CDRM: {
                        m = new CMatrixRMaj(matrix.getNumRows(), matrix.getNumCols());
                        ConvertMatrixData.convert((DMatrixRMaj)matrix, (CMatrixRMaj)m);
                        break block0;
                    }
                    case DSCC: {
                        m = new DMatrixSparseCSC(matrix.getNumRows(), matrix.getNumCols());
                        DConvertMatrixStruct.convert((DMatrix)((DMatrixRMaj)matrix), (DMatrix)((DMatrixSparseCSC)m));
                        break block0;
                    }
                    case FSCC: {
                        m = new FMatrixSparseCSC(matrix.getNumRows(), matrix.getNumCols());
                        ConvertMatrixData.convert((DMatrix)((DMatrixRMaj)matrix), (FMatrixSparseCSC)m);
                    }
                }
                break;
            }
            case FDRM: {
                switch (desired) {
                    case FDRM: {
                        m = (ReshapeMatrix)matrix.copy();
                        break block0;
                    }
                    case DDRM: {
                        m = new DMatrixRMaj(matrix.getNumRows(), matrix.getNumCols());
                        ConvertMatrixData.convert((FMatrixRMaj)matrix, (DMatrixRMaj)m);
                        break block0;
                    }
                    case ZDRM: {
                        m = new ZMatrixRMaj(matrix.getNumRows(), matrix.getNumCols());
                        ConvertMatrixData.convert((FMatrixRMaj)matrix, (ZMatrixRMaj)m);
                        break block0;
                    }
                    case CDRM: {
                        m = new CMatrixRMaj(matrix.getNumRows(), matrix.getNumCols());
                        ConvertMatrixData.convert((FMatrixRMaj)matrix, (CMatrixRMaj)m);
                        break block0;
                    }
                    case DSCC: {
                        m = new DMatrixSparseCSC(matrix.getNumRows(), matrix.getNumCols());
                        ConvertMatrixData.convert((FMatrix)((FMatrixRMaj)matrix), (DMatrixSparseCSC)m);
                        break block0;
                    }
                    case FSCC: {
                        m = new FMatrixSparseCSC(matrix.getNumRows(), matrix.getNumCols());
                        FConvertMatrixStruct.convert((FMatrix)((FMatrixRMaj)matrix), (FMatrix)((FMatrixSparseCSC)m));
                    }
                }
                break;
            }
            case ZDRM: {
                switch (desired) {
                    case ZDRM: {
                        m = (ReshapeMatrix)matrix.copy();
                        break block0;
                    }
                    case CDRM: {
                        m = new CMatrixRMaj(matrix.getNumRows(), matrix.getNumCols());
                        ConvertMatrixData.convert((ZMatrixRMaj)matrix, (CMatrix)((CMatrixRMaj)m));
                    }
                }
                break;
            }
            case CDRM: {
                switch (desired) {
                    case CDRM: {
                        m = (ReshapeMatrix)matrix.copy();
                        break block0;
                    }
                    case ZDRM: {
                        m = new ZMatrixRMaj(matrix.getNumRows(), matrix.getNumCols());
                        ConvertMatrixData.convert((CMatrixRMaj)matrix, (ZMatrix)((ZMatrixRMaj)m));
                    }
                }
                break;
            }
            case DSCC: {
                switch (desired) {
                    case DSCC: {
                        m = (ReshapeMatrix)matrix.copy();
                        break block0;
                    }
                    case DDRM: {
                        m = new DMatrixRMaj(matrix.getNumRows(), matrix.getNumCols());
                        DConvertMatrixStruct.convert((DMatrixSparseCSC)matrix, (DMatrixRMaj)m);
                        break block0;
                    }
                    case FDRM: {
                        m = new FMatrixRMaj(matrix.getNumRows(), matrix.getNumCols());
                        ConvertMatrixData.convert((DMatrix)((DMatrixSparseCSC)matrix), (FMatrix)((Object)m));
                        break block0;
                    }
                    case ZDRM: {
                        m = new ZMatrixRMaj(matrix.getNumRows(), matrix.getNumCols());
                        ConvertMatrixData.convert((DMatrix)((DMatrixSparseCSC)matrix), (ZMatrix)((ZMatrixRMaj)m));
                        break block0;
                    }
                    case CDRM: {
                        m = new CMatrixRMaj(matrix.getNumRows(), matrix.getNumCols());
                        ConvertMatrixData.convert((DMatrix)((DMatrixSparseCSC)matrix), (CMatrix)((CMatrixRMaj)m));
                        break block0;
                    }
                    case FSCC: {
                        m = new FMatrixSparseCSC(matrix.getNumRows(), matrix.getNumCols());
                        ConvertMatrixData.convert((DMatrix)((DMatrixSparseCSC)matrix), (FMatrixSparseCSC)m);
                    }
                }
                break;
            }
            case FSCC: {
                switch (desired) {
                    case FSCC: {
                        m = (ReshapeMatrix)matrix.copy();
                        break block0;
                    }
                    case DDRM: {
                        m = new DMatrixRMaj(matrix.getNumRows(), matrix.getNumCols());
                        ConvertMatrixData.convert((FMatrix)((FMatrixSparseCSC)matrix), (DMatrix)((DMatrixRMaj)m));
                        break block0;
                    }
                    case FDRM: {
                        m = new FMatrixRMaj(matrix.getNumRows(), matrix.getNumCols());
                        FConvertMatrixStruct.convert((FMatrixSparseCSC)matrix, m);
                        break block0;
                    }
                    case ZDRM: {
                        m = new ZMatrixRMaj(matrix.getNumRows(), matrix.getNumCols());
                        ConvertMatrixData.convert((FMatrix)((FMatrixSparseCSC)matrix), (ZMatrix)((ZMatrixRMaj)m));
                        break block0;
                    }
                    case CDRM: {
                        m = new CMatrixRMaj(matrix.getNumRows(), matrix.getNumCols());
                        ConvertMatrixData.convert((FMatrix)((FMatrixSparseCSC)matrix), (CMatrix)((CMatrixRMaj)m));
                        break block0;
                    }
                    case DSCC: {
                        m = new DMatrixSparseCSC(matrix.getNumRows(), matrix.getNumCols());
                        ConvertMatrixData.convert((FMatrix)((FMatrixSparseCSC)matrix), (DMatrixSparseCSC)m);
                    }
                }
            }
        }
        if (m == null) {
            throw new IllegalArgumentException("Unknown " + (Object)((Object)matrix.getType()) + " " + (Object)((Object)desired));
        }
        return m;
    }
}

