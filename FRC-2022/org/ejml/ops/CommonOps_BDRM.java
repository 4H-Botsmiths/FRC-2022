/*
 * Decompiled with CFR 0.150.
 */
package org.ejml.ops;

import org.ejml.data.BMatrixRMaj;

public class CommonOps_BDRM {
    public static void transposeSquare(BMatrixRMaj mat) {
        if (mat.numCols != mat.numRows) {
            throw new IllegalArgumentException("Must be sqare");
        }
        int index = 1;
        int indexEnd = mat.numCols;
        int i = 0;
        while (i < mat.numRows) {
            int indexOther = (i + 1) * mat.numCols + i;
            while (index < indexEnd) {
                boolean val = mat.data[index];
                mat.data[index] = mat.data[indexOther];
                mat.data[indexOther] = val;
                ++index;
                indexOther += mat.numCols;
            }
            index += ++i + 1;
            indexEnd += mat.numCols;
        }
    }
}

