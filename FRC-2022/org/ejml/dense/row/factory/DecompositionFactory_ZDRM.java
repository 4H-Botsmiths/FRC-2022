/*
 * Decompiled with CFR 0.150.
 */
package org.ejml.dense.row.factory;

import org.ejml.data.ZMatrixRMaj;
import org.ejml.dense.row.decompose.chol.CholeskyDecompositionInner_ZDRM;
import org.ejml.dense.row.decompose.lu.LUDecompositionAlt_ZDRM;
import org.ejml.dense.row.decompose.qr.QRDecompositionHouseholderColumn_ZDRM;
import org.ejml.interfaces.decomposition.CholeskyDecomposition_F64;
import org.ejml.interfaces.decomposition.DecompositionInterface;
import org.ejml.interfaces.decomposition.LUDecomposition_F64;
import org.ejml.interfaces.decomposition.QRDecomposition;

public class DecompositionFactory_ZDRM {
    public static LUDecomposition_F64<ZMatrixRMaj> lu(int numRows, int numCols) {
        return new LUDecompositionAlt_ZDRM();
    }

    public static QRDecomposition<ZMatrixRMaj> qr(int numRows, int numCols) {
        return new QRDecompositionHouseholderColumn_ZDRM();
    }

    public static CholeskyDecomposition_F64<ZMatrixRMaj> chol(int size, boolean lower) {
        return new CholeskyDecompositionInner_ZDRM(lower);
    }

    public static boolean decomposeSafe(DecompositionInterface<ZMatrixRMaj> decomposition, ZMatrixRMaj a) {
        if (decomposition.inputModified()) {
            a = a.copy();
        }
        return decomposition.decompose(a);
    }
}

