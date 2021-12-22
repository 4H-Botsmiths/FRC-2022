/*
 * Decompiled with CFR 0.150.
 */
package org.ejml.dense.row.misc;

import org.ejml.data.FMatrix1Row;

public class TransposeAlgs_FDRM {
    public static void square(FMatrix1Row A) {
        for (int i = 0; i < A.numRows; ++i) {
            int index = i * A.numCols + i + 1;
            int indexEnd = (i + 1) * A.numCols;
            int indexOther = (i + 1) * A.numCols + i;
            while (index < indexEnd) {
                float val = A.data[index];
                A.data[index] = A.data[indexOther];
                A.data[indexOther] = val;
                ++index;
                indexOther += A.numCols;
            }
        }
    }

    public static void block(FMatrix1Row A, FMatrix1Row A_tran, int blockLength) {
        for (int idx0 = 0; idx0 < A.numRows; idx0 += blockLength) {
            int idx1 = Math.min(A.numRows, idx0 + blockLength);
            int blockHeight = idx1 - idx0;
            int indexSrc = idx0 * A.numCols;
            int indexDst = idx0;
            for (int j = 0; j < A.numCols; j += blockLength) {
                int blockWidth = Math.min(blockLength, A.numCols - j);
                int indexSrcEnd = indexSrc + blockWidth;
                while (indexSrc < indexSrcEnd) {
                    int rowSrc = indexSrc;
                    int rowDst = indexDst;
                    int end = rowDst + blockHeight;
                    while (rowDst < end) {
                        A_tran.data[rowDst++] = A.data[rowSrc];
                        rowSrc += A.numCols;
                    }
                    indexDst += A_tran.numCols;
                    ++indexSrc;
                }
            }
        }
    }

    public static void standard(FMatrix1Row A, FMatrix1Row A_tran) {
        for (int i = 0; i < A_tran.numRows; ++i) {
            int index = i * A_tran.numCols;
            int index2 = i;
            int end = index + A_tran.numCols;
            while (index < end) {
                A_tran.data[index++] = A.data[index2];
                index2 += A.numCols;
            }
        }
    }
}

