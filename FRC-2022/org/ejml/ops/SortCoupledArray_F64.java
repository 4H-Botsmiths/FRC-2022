/*
 * Decompiled with CFR 0.150.
 */
package org.ejml.ops;

import org.ejml.ops.QuickSort_S32;

public class SortCoupledArray_F64 {
    int[] tmp = new int[0];
    int[] copyA = new int[0];
    double[] copyB = new double[0];
    QuickSort_S32 quicksort = new QuickSort_S32();

    public void quick(int[] segments, int length, int[] valuesA, double[] valuesB) {
        for (int i = 1; i < length; ++i) {
            int x0 = segments[i - 1];
            int x1 = segments[i];
            this.quick(x0, x1 - x0, valuesA, valuesB);
        }
    }

    private void quick(int offset, int length, int[] valuesA, double[] valuesB) {
        if (length <= 1) {
            return;
        }
        if (this.tmp.length < length) {
            int l = length * 2 + 1;
            this.tmp = new int[l];
            this.copyA = new int[l];
            this.copyB = new double[l];
        }
        System.arraycopy(valuesA, offset, this.copyA, 0, length);
        System.arraycopy(valuesB, offset, this.copyB, 0, length);
        if (length > 50) {
            this.quicksort.sort(this.copyA, length, this.tmp);
        } else {
            SortCoupledArray_F64.shellSort(this.copyA, 0, length, this.tmp);
        }
        for (int i = 0; i < length; ++i) {
            valuesA[offset + i] = this.copyA[this.tmp[i]];
            valuesB[offset + i] = this.copyB[this.tmp[i]];
        }
    }

    public static void shellSort(int[] data, int offset, int length, int[] indexes) {
        int i;
        for (i = 0; i < length; ++i) {
            indexes[i] = offset + i;
        }
        int inc = 1;
        do {
            inc *= 3;
        } while (++inc <= length);
        do {
            for (i = inc /= 3; i < length; ++i) {
                int v = data[indexes[i]];
                int idx_i = indexes[i];
                int j = i;
                while (data[indexes[j - inc]] > v) {
                    indexes[j] = indexes[j - inc];
                    if ((j -= inc) >= inc) continue;
                }
                indexes[j] = idx_i;
            }
        } while (inc > 1);
    }
}

