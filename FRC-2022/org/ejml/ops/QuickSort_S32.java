/*
 * Decompiled with CFR 0.150.
 */
package org.ejml.ops;

public class QuickSort_S32 {
    private int M = 7;
    private final int NSTACK;
    private final int[] istack;

    public QuickSort_S32() {
        this.NSTACK = 65;
        this.istack = new int[this.NSTACK];
    }

    public QuickSort_S32(int NSTACK, int M) {
        this.M = M;
        this.NSTACK = NSTACK;
        this.istack = new int[NSTACK];
    }

    public void sort(int[] arr, int length, int[] indexes) {
        int i;
        for (i = 0; i < length; ++i) {
            indexes[i] = i;
        }
        int jstack = -1;
        int l = 0;
        int ir = length - 1;
        while (true) {
            int temp;
            int a;
            int j;
            if (ir - l < this.M) {
                for (j = l + 1; j <= ir; ++j) {
                    a = arr[indexes[j]];
                    temp = indexes[j];
                    for (i = j - 1; i >= l && arr[indexes[i]] > a; --i) {
                        indexes[i + 1] = indexes[i];
                    }
                    indexes[i + 1] = temp;
                }
                if (jstack < 0) break;
                ir = this.istack[jstack--];
                l = this.istack[jstack--];
                continue;
            }
            int k = l + ir >>> 1;
            temp = indexes[k];
            indexes[k] = indexes[l + 1];
            indexes[l + 1] = temp;
            if (arr[indexes[l]] > arr[indexes[ir]]) {
                temp = indexes[l];
                indexes[l] = indexes[ir];
                indexes[ir] = temp;
            }
            if (arr[indexes[l + 1]] > arr[indexes[ir]]) {
                temp = indexes[l + 1];
                indexes[l + 1] = indexes[ir];
                indexes[ir] = temp;
            }
            if (arr[indexes[l]] > arr[indexes[l + 1]]) {
                temp = indexes[l];
                indexes[l] = indexes[l + 1];
                indexes[l + 1] = temp;
            }
            i = l + 1;
            j = ir;
            a = arr[indexes[l + 1]];
            while (true) {
                if (arr[indexes[++i]] < a) {
                    continue;
                }
                while (arr[indexes[--j]] > a) {
                }
                if (j < i) break;
                temp = indexes[i];
                indexes[i] = indexes[j];
                indexes[j] = temp;
            }
            temp = indexes[l + 1];
            indexes[l + 1] = indexes[j];
            indexes[j] = temp;
            if ((jstack += 2) >= this.NSTACK) {
                throw new RuntimeException("NSTACK too small");
            }
            if (ir - i + 1 >= j - l) {
                this.istack[jstack] = ir;
                this.istack[jstack - 1] = i;
                ir = j - 1;
                continue;
            }
            this.istack[jstack] = j - 1;
            this.istack[jstack - 1] = l;
            l = i;
        }
    }
}

