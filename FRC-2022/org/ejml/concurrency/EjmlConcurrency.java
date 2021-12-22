/*
 * Decompiled with CFR 0.150.
 */
package org.ejml.concurrency;

import org.ejml.data.Matrix;
import org.ejml.data.MatrixSparse;
import pabeles.concurrency.ConcurrencyOps;

public class EjmlConcurrency
extends ConcurrencyOps {
    public static boolean USE_CONCURRENT = true;
    public static int ELEMENT_THRESHOLD = 50000;

    public static void setMaxThreads(int maxThreads) {
        ConcurrencyOps.setMaxThreads(maxThreads);
        USE_CONCURRENT = maxThreads > 1;
    }

    public static boolean isUseConcurrent() {
        return USE_CONCURRENT;
    }

    public static boolean useConcurrent(MatrixSparse mat) {
        if (!USE_CONCURRENT) {
            return false;
        }
        return mat.getNonZeroLength() > ELEMENT_THRESHOLD;
    }

    public static boolean useConcurrent(Matrix mat) {
        if (!USE_CONCURRENT) {
            return false;
        }
        return mat.getNumRows() * mat.getNumCols() > ELEMENT_THRESHOLD;
    }
}

