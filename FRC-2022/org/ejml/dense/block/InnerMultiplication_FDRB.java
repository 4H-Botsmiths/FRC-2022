/*
 * Decompiled with CFR 0.150.
 */
package org.ejml.dense.block;

public class InnerMultiplication_FDRB {
    public static void blockMultPlus(float[] dataA, float[] dataB, float[] dataC, int indexA, int indexB, int indexC, int heightA, int widthA, int widthC) {
        int a = indexA;
        int rowC = indexC;
        int i = 0;
        while (i < heightA) {
            int b = indexB;
            int endC = rowC + widthC;
            int endA = a + widthA;
            while (a != endA) {
                float valA = dataA[a++];
                int c = rowC;
                while (c != endC) {
                    int n = c++;
                    dataC[n] = dataC[n] + valA * dataB[b++];
                }
            }
            ++i;
            rowC += widthC;
        }
    }

    public static void blockMultPlusTransA(float[] dataA, float[] dataB, float[] dataC, int indexA, int indexB, int indexC, int heightA, int widthA, int widthC) {
        int rowC = indexC;
        int i = 0;
        while (i < widthA) {
            int colA;
            int endA = colA + widthA * heightA;
            int b = indexB;
            for (colA = i + indexA; colA != endA; colA += widthA) {
                float valA = dataA[colA];
                int c = rowC;
                int endB = b + widthC;
                while (b != endB) {
                    int n = c++;
                    dataC[n] = dataC[n] + valA * dataB[b++];
                }
            }
            ++i;
            rowC += widthC;
        }
    }

    public static void blockMultPlusTransB(float[] dataA, float[] dataB, float[] dataC, int indexA, int indexB, int indexC, int heightA, int widthA, int widthC) {
        for (int i = 0; i < heightA; ++i) {
            for (int j = 0; j < widthC; ++j) {
                float val = 0.0f;
                for (int k = 0; k < widthA; ++k) {
                    val += dataA[i * widthA + k + indexA] * dataB[j * widthA + k + indexB];
                }
                int n = i * widthC + j + indexC;
                dataC[n] = dataC[n] + val;
            }
        }
    }

    public static void blockMultMinus(float[] dataA, float[] dataB, float[] dataC, int indexA, int indexB, int indexC, int heightA, int widthA, int widthC) {
        int a = indexA;
        int rowC = indexC;
        int i = 0;
        while (i < heightA) {
            int b = indexB;
            int endC = rowC + widthC;
            int endA = a + widthA;
            while (a != endA) {
                float valA = dataA[a++];
                int c = rowC;
                while (c != endC) {
                    int n = c++;
                    dataC[n] = dataC[n] - valA * dataB[b++];
                }
            }
            ++i;
            rowC += widthC;
        }
    }

    public static void blockMultMinusTransA(float[] dataA, float[] dataB, float[] dataC, int indexA, int indexB, int indexC, int heightA, int widthA, int widthC) {
        int rowC = indexC;
        int i = 0;
        while (i < widthA) {
            int colA;
            int endA = colA + widthA * heightA;
            int b = indexB;
            for (colA = i + indexA; colA != endA; colA += widthA) {
                float valA = dataA[colA];
                int c = rowC;
                int endB = b + widthC;
                while (b != endB) {
                    int n = c++;
                    dataC[n] = dataC[n] - valA * dataB[b++];
                }
            }
            ++i;
            rowC += widthC;
        }
    }

    public static void blockMultMinusTransB(float[] dataA, float[] dataB, float[] dataC, int indexA, int indexB, int indexC, int heightA, int widthA, int widthC) {
        for (int i = 0; i < heightA; ++i) {
            for (int j = 0; j < widthC; ++j) {
                float val = 0.0f;
                for (int k = 0; k < widthA; ++k) {
                    val += dataA[i * widthA + k + indexA] * dataB[j * widthA + k + indexB];
                }
                int n = i * widthC + j + indexC;
                dataC[n] = dataC[n] - val;
            }
        }
    }

    public static void blockMultSet(float[] dataA, float[] dataB, float[] dataC, int indexA, int indexB, int indexC, int heightA, int widthA, int widthC) {
        int a = indexA;
        int rowC = indexC;
        int i = 0;
        while (i < heightA) {
            int b = indexB;
            int endC = rowC + widthC;
            int endA = a + widthA;
            while (a != endA) {
                float valA = dataA[a++];
                int c = rowC;
                if (b == indexB) {
                    while (c != endC) {
                        dataC[c++] = valA * dataB[b++];
                    }
                    continue;
                }
                while (c != endC) {
                    int n = c++;
                    dataC[n] = dataC[n] + valA * dataB[b++];
                }
            }
            ++i;
            rowC += widthC;
        }
    }

    public static void blockMultSetTransA(float[] dataA, float[] dataB, float[] dataC, int indexA, int indexB, int indexC, int heightA, int widthA, int widthC) {
        int rowC = indexC;
        int i = 0;
        while (i < widthA) {
            int colA;
            int endA = colA + widthA * heightA;
            int b = indexB;
            for (colA = i + indexA; colA != endA; colA += widthA) {
                float valA = dataA[colA];
                int c = rowC;
                int endB = b + widthC;
                if (b == indexB) {
                    while (b != endB) {
                        dataC[c++] = valA * dataB[b++];
                    }
                    continue;
                }
                while (b != endB) {
                    int n = c++;
                    dataC[n] = dataC[n] + valA * dataB[b++];
                }
            }
            ++i;
            rowC += widthC;
        }
    }

    public static void blockMultSetTransB(float[] dataA, float[] dataB, float[] dataC, int indexA, int indexB, int indexC, int heightA, int widthA, int widthC) {
        for (int i = 0; i < heightA; ++i) {
            for (int j = 0; j < widthC; ++j) {
                float val = 0.0f;
                for (int k = 0; k < widthA; ++k) {
                    val += dataA[i * widthA + k + indexA] * dataB[j * widthA + k + indexB];
                }
                dataC[i * widthC + j + indexC] = val;
            }
        }
    }

    public static void blockMultPlus(float alpha, float[] dataA, float[] dataB, float[] dataC, int indexA, int indexB, int indexC, int heightA, int widthA, int widthC) {
        int a = indexA;
        int rowC = indexC;
        int i = 0;
        while (i < heightA) {
            int b = indexB;
            int endC = rowC + widthC;
            int endA = a + widthA;
            while (a != endA) {
                float valA = alpha * dataA[a++];
                int c = rowC;
                while (c != endC) {
                    int n = c++;
                    dataC[n] = dataC[n] + valA * dataB[b++];
                }
            }
            ++i;
            rowC += widthC;
        }
    }

    public static void blockMultPlusTransA(float alpha, float[] dataA, float[] dataB, float[] dataC, int indexA, int indexB, int indexC, int heightA, int widthA, int widthC) {
        int rowC = indexC;
        int i = 0;
        while (i < widthA) {
            int colA;
            int endA = colA + widthA * heightA;
            int b = indexB;
            for (colA = i + indexA; colA != endA; colA += widthA) {
                float valA = alpha * dataA[colA];
                int c = rowC;
                int endB = b + widthC;
                while (b != endB) {
                    int n = c++;
                    dataC[n] = dataC[n] + valA * dataB[b++];
                }
            }
            ++i;
            rowC += widthC;
        }
    }

    public static void blockMultPlusTransB(float alpha, float[] dataA, float[] dataB, float[] dataC, int indexA, int indexB, int indexC, int heightA, int widthA, int widthC) {
        for (int i = 0; i < heightA; ++i) {
            for (int j = 0; j < widthC; ++j) {
                float val = 0.0f;
                for (int k = 0; k < widthA; ++k) {
                    val += dataA[i * widthA + k + indexA] * dataB[j * widthA + k + indexB];
                }
                int n = i * widthC + j + indexC;
                dataC[n] = dataC[n] + alpha * val;
            }
        }
    }

    public static void blockMultSet(float alpha, float[] dataA, float[] dataB, float[] dataC, int indexA, int indexB, int indexC, int heightA, int widthA, int widthC) {
        int a = indexA;
        int rowC = indexC;
        int i = 0;
        while (i < heightA) {
            int b = indexB;
            int endC = rowC + widthC;
            int endA = a + widthA;
            while (a != endA) {
                float valA = alpha * dataA[a++];
                int c = rowC;
                if (b == indexB) {
                    while (c != endC) {
                        dataC[c++] = valA * dataB[b++];
                    }
                    continue;
                }
                while (c != endC) {
                    int n = c++;
                    dataC[n] = dataC[n] + valA * dataB[b++];
                }
            }
            ++i;
            rowC += widthC;
        }
    }

    public static void blockMultSetTransA(float alpha, float[] dataA, float[] dataB, float[] dataC, int indexA, int indexB, int indexC, int heightA, int widthA, int widthC) {
        int rowC = indexC;
        int i = 0;
        while (i < widthA) {
            int colA;
            int endA = colA + widthA * heightA;
            int b = indexB;
            for (colA = i + indexA; colA != endA; colA += widthA) {
                float valA = alpha * dataA[colA];
                int c = rowC;
                int endB = b + widthC;
                if (b == indexB) {
                    while (b != endB) {
                        dataC[c++] = valA * dataB[b++];
                    }
                    continue;
                }
                while (b != endB) {
                    int n = c++;
                    dataC[n] = dataC[n] + valA * dataB[b++];
                }
            }
            ++i;
            rowC += widthC;
        }
    }

    public static void blockMultSetTransB(float alpha, float[] dataA, float[] dataB, float[] dataC, int indexA, int indexB, int indexC, int heightA, int widthA, int widthC) {
        for (int i = 0; i < heightA; ++i) {
            for (int j = 0; j < widthC; ++j) {
                float val = 0.0f;
                for (int k = 0; k < widthA; ++k) {
                    val += dataA[i * widthA + k + indexA] * dataB[j * widthA + k + indexB];
                }
                dataC[i * widthC + j + indexC] = alpha * val;
            }
        }
    }
}

