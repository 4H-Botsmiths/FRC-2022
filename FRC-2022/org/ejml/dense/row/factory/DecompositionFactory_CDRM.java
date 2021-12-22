/*
 * Decompiled with CFR 0.150.
 */
package org.ejml.dense.row.factory;

import org.ejml.data.CMatrixRMaj;
import org.ejml.dense.row.decompose.chol.CholeskyDecompositionInner_CDRM;
import org.ejml.dense.row.decompose.lu.LUDecompositionAlt_CDRM;
import org.ejml.dense.row.decompose.qr.QRDecompositionHouseholderColumn_CDRM;
import org.ejml.interfaces.decomposition.CholeskyDecomposition_F32;
import org.ejml.interfaces.decomposition.DecompositionInterface;
import org.ejml.interfaces.decomposition.LUDecomposition_F32;
import org.ejml.interfaces.decomposition.QRDecomposition;

public class DecompositionFactory_CDRM {
    public static LUDecomposition_F32<CMatrixRMaj> lu(int numRows, int numCols) {
        return new LUDecompositionAlt_CDRM();
    }

    public static QRDecomposition<CMatrixRMaj> qr(int numRows, int numCols) {
        return new QRDecompositionHouseholderColumn_CDRM();
    }

    public static CholeskyDecomposition_F32<CMatrixRMaj> chol(int size, boolean lower) {
        return new CholeskyDecompositionInner_CDRM(lower);
    }

    public static boolean decomposeSafe(DecompositionInterface<CMatrixRMaj> decomposition, CMatrixRMaj a) {
        if (decomposition.inputModified()) {
            a = a.copy();
        }
        return decomposition.decompose(a);
    }
}

