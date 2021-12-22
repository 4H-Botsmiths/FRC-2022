/*
 * Decompiled with CFR 0.150.
 */
package org.ejml.dense.row.misc;

import org.ejml.data.ZMatrixRMaj;

public class TransposeAlgs_ZDRM {
    public static void square(ZMatrixRMaj mat) {
        int rowStride;
        int index = 2;
        int indexEnd = rowStride = mat.getRowStride();
        int i = 0;
        while (i < mat.numRows) {
            int indexOther = (i + 1) * rowStride + i * 2;
            while (index < indexEnd) {
                double real = mat.data[index];
                double img = mat.data[index + 1];
                mat.data[index] = mat.data[indexOther];
                mat.data[index + 1] = mat.data[indexOther + 1];
                mat.data[indexOther] = real;
                mat.data[indexOther + 1] = img;
                index += 2;
                indexOther += rowStride;
            }
            index += (++i + 1) * 2;
            indexEnd += rowStride;
        }
    }

    public static void squareConjugate(ZMatrixRMaj mat) {
        int rowStride;
        int index = 2;
        int indexEnd = rowStride = mat.getRowStride();
        int i = 0;
        while (i < mat.numRows) {
            mat.data[index - 1] = -mat.data[index - 1];
            int indexOther = (i + 1) * rowStride + i * 2;
            while (index < indexEnd) {
                double real = mat.data[index];
                double img = mat.data[index + 1];
                mat.data[index] = mat.data[indexOther];
                mat.data[index + 1] = -mat.data[indexOther + 1];
                mat.data[indexOther] = real;
                mat.data[indexOther + 1] = -img;
                index += 2;
                indexOther += rowStride;
            }
            index += (++i + 1) * 2;
            indexEnd += rowStride;
        }
    }

    public static void standard(ZMatrixRMaj A, ZMatrixRMaj A_tran) {
        int index = 0;
        int rowStrideTran = A_tran.getRowStride();
        int rowStride = A.getRowStride();
        for (int i = 0; i < A_tran.numRows; ++i) {
            int index2 = i * 2;
            int end = index + rowStrideTran;
            while (index < end) {
                A_tran.data[index++] = A.data[index2];
                A_tran.data[index++] = A.data[index2 + 1];
                index2 += rowStride;
            }
        }
    }

    public static void standardConjugate(ZMatrixRMaj A, ZMatrixRMaj A_tran) {
        int index = 0;
        int rowStrideTran = A_tran.getRowStride();
        int rowStride = A.getRowStride();
        for (int i = 0; i < A_tran.numRows; ++i) {
            int index2 = i * 2;
            int end = index + rowStrideTran;
            while (index < end) {
                A_tran.data[index++] = A.data[index2];
                A_tran.data[index++] = -A.data[index2 + 1];
                index2 += rowStride;
            }
        }
    }
}

