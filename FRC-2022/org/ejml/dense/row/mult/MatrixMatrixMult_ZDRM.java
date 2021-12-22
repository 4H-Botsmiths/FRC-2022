/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  org.jetbrains.annotations.Nullable
 */
package org.ejml.dense.row.mult;

import org.ejml.MatrixDimensionException;
import org.ejml.data.ZMatrixRMaj;
import org.ejml.dense.row.CommonOps_ZDRM;
import org.jetbrains.annotations.Nullable;

public class MatrixMatrixMult_ZDRM {
    public static void mult_reorder(ZMatrixRMaj a, ZMatrixRMaj b, ZMatrixRMaj c) {
        if (a == c || b == c) {
            throw new IllegalArgumentException("Neither 'a' or 'b' can be the same matrix as 'c'");
        }
        if (a.numCols != b.numRows) {
            throw new MatrixDimensionException("The 'a' and 'b' matrices do not have compatible dimensions");
        }
        if (a.numRows != c.numRows || b.numCols != c.numCols) {
            throw new MatrixDimensionException("The results matrix does not have the desired dimensions");
        }
        if (a.numCols == 0 || a.numRows == 0) {
            CommonOps_ZDRM.fill(c, 0.0, 0.0);
            return;
        }
        int indexCbase = 0;
        int strideA = a.getRowStride();
        int strideB = b.getRowStride();
        int strideC = c.getRowStride();
        int endOfKLoop = b.numRows * strideB;
        for (int i = 0; i < a.numRows; ++i) {
            double imgB;
            double realB;
            int indexA = i * strideA;
            int indexB = 0;
            int indexC = indexCbase;
            int end = indexB + strideB;
            double realA = a.data[indexA++];
            double imagA = a.data[indexA++];
            while (indexB < end) {
                realB = b.data[indexB++];
                imgB = b.data[indexB++];
                c.data[indexC++] = realA * realB - imagA * imgB;
                c.data[indexC++] = realA * imgB + imagA * realB;
            }
            while (indexB != endOfKLoop) {
                indexC = indexCbase;
                end = indexB + strideB;
                realA = a.data[indexA++];
                imagA = a.data[indexA++];
                while (indexB < end) {
                    realB = b.data[indexB++];
                    imgB = b.data[indexB++];
                    int n = indexC++;
                    c.data[n] = c.data[n] + (realA * realB - imagA * imgB);
                    int n2 = indexC++;
                    c.data[n2] = c.data[n2] + (realA * imgB + imagA * realB);
                }
            }
            indexCbase += strideC;
        }
    }

    public static void mult_small(ZMatrixRMaj a, ZMatrixRMaj b, ZMatrixRMaj c) {
        if (a == c || b == c) {
            throw new IllegalArgumentException("Neither 'a' or 'b' can be the same matrix as 'c'");
        }
        if (a.numCols != b.numRows) {
            throw new MatrixDimensionException("The 'a' and 'b' matrices do not have compatible dimensions");
        }
        if (a.numRows != c.numRows || b.numCols != c.numCols) {
            throw new MatrixDimensionException("The results matrix does not have the desired dimensions");
        }
        int aIndexStart = 0;
        int indexC = 0;
        int strideA = a.getRowStride();
        int strideB = b.getRowStride();
        for (int i = 0; i < a.numRows; ++i) {
            for (int j = 0; j < b.numCols; ++j) {
                double realTotal = 0.0;
                double imgTotal = 0.0;
                int indexA = aIndexStart;
                int indexB = j * 2;
                int end = indexA + strideA;
                while (indexA < end) {
                    double realA = a.data[indexA++];
                    double imagA = a.data[indexA++];
                    double realB = b.data[indexB];
                    double imgB = b.data[indexB + 1];
                    realTotal += realA * realB - imagA * imgB;
                    imgTotal += realA * imgB + imagA * realB;
                    indexB += strideB;
                }
                c.data[indexC++] = realTotal;
                c.data[indexC++] = imgTotal;
            }
            aIndexStart += strideA;
        }
    }

    public static void multTransA_reorder(ZMatrixRMaj a, ZMatrixRMaj b, ZMatrixRMaj c) {
        if (a == c || b == c) {
            throw new IllegalArgumentException("Neither 'a' or 'b' can be the same matrix as 'c'");
        }
        if (a.numRows != b.numRows) {
            throw new MatrixDimensionException("The 'a' and 'b' matrices do not have compatible dimensions");
        }
        if (a.numCols != c.numRows || b.numCols != c.numCols) {
            throw new MatrixDimensionException("The results matrix does not have the desired dimensions");
        }
        if (a.numCols == 0 || a.numRows == 0) {
            CommonOps_ZDRM.fill(c, 0.0, 0.0);
            return;
        }
        for (int i = 0; i < a.numCols; ++i) {
            int indexC_start = i * c.numCols * 2;
            double realA = a.data[i * 2];
            double imagA = a.data[i * 2 + 1];
            int indexB = 0;
            int end = indexB + b.numCols * 2;
            int indexC = indexC_start;
            while (indexB < end) {
                double realB = b.data[indexB++];
                double imagB = b.data[indexB++];
                c.data[indexC++] = realA * realB + imagA * imagB;
                c.data[indexC++] = realA * imagB - imagA * realB;
            }
            for (int k = 1; k < a.numRows; ++k) {
                realA = a.getReal(k, i);
                imagA = a.getImag(k, i);
                end = indexB + b.numCols * 2;
                indexC = indexC_start;
                while (indexB < end) {
                    double realB = b.data[indexB++];
                    double imagB = b.data[indexB++];
                    int n = indexC++;
                    c.data[n] = c.data[n] + (realA * realB + imagA * imagB);
                    int n2 = indexC++;
                    c.data[n2] = c.data[n2] + (realA * imagB - imagA * realB);
                }
            }
        }
    }

    public static void multTransA_small(ZMatrixRMaj a, ZMatrixRMaj b, ZMatrixRMaj c) {
        if (a == c || b == c) {
            throw new IllegalArgumentException("Neither 'a' or 'b' can be the same matrix as 'c'");
        }
        if (a.numRows != b.numRows) {
            throw new MatrixDimensionException("The 'a' and 'b' matrices do not have compatible dimensions");
        }
        if (a.numCols != c.numRows || b.numCols != c.numCols) {
            throw new MatrixDimensionException("The results matrix does not have the desired dimensions");
        }
        int indexC = 0;
        for (int i = 0; i < a.numCols; ++i) {
            for (int j = 0; j < b.numCols; ++j) {
                int indexB;
                int indexA = i * 2;
                int end = indexB + b.numRows * b.numCols * 2;
                double realTotal = 0.0;
                double imagTotal = 0.0;
                for (indexB = j * 2; indexB < end; indexB += b.numCols * 2) {
                    double realA = a.data[indexA];
                    double imagA = a.data[indexA + 1];
                    double realB = b.data[indexB];
                    double imagB = b.data[indexB + 1];
                    realTotal += realA * realB + imagA * imagB;
                    imagTotal += realA * imagB - imagA * realB;
                    indexA += a.numCols * 2;
                }
                c.data[indexC++] = realTotal;
                c.data[indexC++] = imagTotal;
            }
        }
    }

    public static void multTransB(ZMatrixRMaj a, ZMatrixRMaj b, ZMatrixRMaj c) {
        if (a == c || b == c) {
            throw new IllegalArgumentException("Neither 'a' or 'b' can be the same matrix as 'c'");
        }
        if (a.numCols != b.numCols) {
            throw new MatrixDimensionException("The 'a' and 'b' matrices do not have compatible dimensions");
        }
        if (a.numRows != c.numRows || b.numRows != c.numCols) {
            throw new MatrixDimensionException("The results matrix does not have the desired dimensions");
        }
        int indexC = 0;
        int aIndexStart = 0;
        for (int xA = 0; xA < a.numRows; ++xA) {
            int end = aIndexStart + b.numCols * 2;
            int indexB = 0;
            for (int xB = 0; xB < b.numRows; ++xB) {
                int indexA = aIndexStart;
                double realTotal = 0.0;
                double imagTotal = 0.0;
                while (indexA < end) {
                    double realA = a.data[indexA++];
                    double imagA = a.data[indexA++];
                    double realB = b.data[indexB++];
                    double imagB = b.data[indexB++];
                    realTotal += realA * realB + imagA * imagB;
                    imagTotal += imagA * realB - realA * imagB;
                }
                c.data[indexC++] = realTotal;
                c.data[indexC++] = imagTotal;
            }
            aIndexStart += a.numCols * 2;
        }
    }

    public static void multTransAB(ZMatrixRMaj a, ZMatrixRMaj b, ZMatrixRMaj c) {
        if (a == c || b == c) {
            throw new IllegalArgumentException("Neither 'a' or 'b' can be the same matrix as 'c'");
        }
        if (a.numRows != b.numCols) {
            throw new MatrixDimensionException("The 'a' and 'b' matrices do not have compatible dimensions");
        }
        if (a.numCols != c.numRows || b.numRows != c.numCols) {
            throw new MatrixDimensionException("The results matrix does not have the desired dimensions");
        }
        int indexC = 0;
        for (int i = 0; i < a.numCols; ++i) {
            int indexB = 0;
            for (int j = 0; j < b.numRows; ++j) {
                int indexA = i * 2;
                int end = indexB + b.numCols * 2;
                double realTotal = 0.0;
                double imagTotal = 0.0;
                while (indexB < end) {
                    double realA = a.data[indexA];
                    double imagA = -a.data[indexA + 1];
                    double realB = b.data[indexB++];
                    double imagB = -b.data[indexB++];
                    realTotal += realA * realB - imagA * imagB;
                    imagTotal += realA * imagB + imagA * realB;
                    indexA += a.numCols * 2;
                }
                c.data[indexC++] = realTotal;
                c.data[indexC++] = imagTotal;
            }
        }
    }

    public static void multTransAB_aux(ZMatrixRMaj a, ZMatrixRMaj b, ZMatrixRMaj c, @Nullable double[] aux) {
        if (a == c || b == c) {
            throw new IllegalArgumentException("Neither 'a' or 'b' can be the same matrix as 'c'");
        }
        if (a.numRows != b.numCols) {
            throw new MatrixDimensionException("The 'a' and 'b' matrices do not have compatible dimensions");
        }
        if (a.numCols != c.numRows || b.numRows != c.numCols) {
            throw new MatrixDimensionException("The results matrix does not have the desired dimensions");
        }
        if (aux == null) {
            aux = new double[a.numRows * 2];
        }
        if (a.numCols == 0 || a.numRows == 0) {
            CommonOps_ZDRM.fill(c, 0.0, 0.0);
            return;
        }
        int indexC = 0;
        for (int i = 0; i < a.numCols; ++i) {
            int indexA = i * 2;
            for (int k = 0; k < b.numCols; ++k) {
                aux[k * 2] = a.data[indexA];
                aux[k * 2 + 1] = a.data[indexA + 1];
                indexA += a.numCols * 2;
            }
            for (int j = 0; j < b.numRows; ++j) {
                int indexAux = 0;
                int indexB = j * b.numCols * 2;
                double realTotal = 0.0;
                double imagTotal = 0.0;
                for (int k = 0; k < b.numCols; ++k) {
                    double realA = aux[indexAux++];
                    double imagA = -aux[indexAux++];
                    double realB = b.data[indexB++];
                    double imagB = -b.data[indexB++];
                    realTotal += realA * realB - imagA * imagB;
                    imagTotal += realA * imagB + imagA * realB;
                }
                c.data[indexC++] = realTotal;
                c.data[indexC++] = imagTotal;
            }
        }
    }

    public static void multAdd_reorder(ZMatrixRMaj a, ZMatrixRMaj b, ZMatrixRMaj c) {
        if (a == c || b == c) {
            throw new IllegalArgumentException("Neither 'a' or 'b' can be the same matrix as 'c'");
        }
        if (a.numCols != b.numRows) {
            throw new MatrixDimensionException("The 'a' and 'b' matrices do not have compatible dimensions");
        }
        if (a.numRows != c.numRows || b.numCols != c.numCols) {
            throw new MatrixDimensionException("The results matrix does not have the desired dimensions");
        }
        if (a.numCols == 0 || a.numRows == 0) {
            return;
        }
        int indexCbase = 0;
        int strideA = a.getRowStride();
        int strideB = b.getRowStride();
        int strideC = c.getRowStride();
        int endOfKLoop = b.numRows * strideB;
        for (int i = 0; i < a.numRows; ++i) {
            double imgB;
            double realB;
            int indexA = i * strideA;
            int indexB = 0;
            int indexC = indexCbase;
            int end = indexB + strideB;
            double realA = a.data[indexA++];
            double imagA = a.data[indexA++];
            while (indexB < end) {
                realB = b.data[indexB++];
                imgB = b.data[indexB++];
                int n = indexC++;
                c.data[n] = c.data[n] + (realA * realB - imagA * imgB);
                int n2 = indexC++;
                c.data[n2] = c.data[n2] + (realA * imgB + imagA * realB);
            }
            while (indexB != endOfKLoop) {
                indexC = indexCbase;
                end = indexB + strideB;
                realA = a.data[indexA++];
                imagA = a.data[indexA++];
                while (indexB < end) {
                    realB = b.data[indexB++];
                    imgB = b.data[indexB++];
                    int n = indexC++;
                    c.data[n] = c.data[n] + (realA * realB - imagA * imgB);
                    int n3 = indexC++;
                    c.data[n3] = c.data[n3] + (realA * imgB + imagA * realB);
                }
            }
            indexCbase += strideC;
        }
    }

    public static void multAdd_small(ZMatrixRMaj a, ZMatrixRMaj b, ZMatrixRMaj c) {
        if (a == c || b == c) {
            throw new IllegalArgumentException("Neither 'a' or 'b' can be the same matrix as 'c'");
        }
        if (a.numCols != b.numRows) {
            throw new MatrixDimensionException("The 'a' and 'b' matrices do not have compatible dimensions");
        }
        if (a.numRows != c.numRows || b.numCols != c.numCols) {
            throw new MatrixDimensionException("The results matrix does not have the desired dimensions");
        }
        int aIndexStart = 0;
        int indexC = 0;
        int strideA = a.getRowStride();
        int strideB = b.getRowStride();
        for (int i = 0; i < a.numRows; ++i) {
            for (int j = 0; j < b.numCols; ++j) {
                double realTotal = 0.0;
                double imgTotal = 0.0;
                int indexA = aIndexStart;
                int indexB = j * 2;
                int end = indexA + strideA;
                while (indexA < end) {
                    double realA = a.data[indexA++];
                    double imagA = a.data[indexA++];
                    double realB = b.data[indexB];
                    double imgB = b.data[indexB + 1];
                    realTotal += realA * realB - imagA * imgB;
                    imgTotal += realA * imgB + imagA * realB;
                    indexB += strideB;
                }
                int n = indexC++;
                c.data[n] = c.data[n] + realTotal;
                int n2 = indexC++;
                c.data[n2] = c.data[n2] + imgTotal;
            }
            aIndexStart += strideA;
        }
    }

    public static void multAddTransA_reorder(ZMatrixRMaj a, ZMatrixRMaj b, ZMatrixRMaj c) {
        if (a == c || b == c) {
            throw new IllegalArgumentException("Neither 'a' or 'b' can be the same matrix as 'c'");
        }
        if (a.numRows != b.numRows) {
            throw new MatrixDimensionException("The 'a' and 'b' matrices do not have compatible dimensions");
        }
        if (a.numCols != c.numRows || b.numCols != c.numCols) {
            throw new MatrixDimensionException("The results matrix does not have the desired dimensions");
        }
        if (a.numCols == 0 || a.numRows == 0) {
            return;
        }
        for (int i = 0; i < a.numCols; ++i) {
            int indexC_start = i * c.numCols * 2;
            double realA = a.data[i * 2];
            double imagA = a.data[i * 2 + 1];
            int indexB = 0;
            int end = indexB + b.numCols * 2;
            int indexC = indexC_start;
            while (indexB < end) {
                double realB = b.data[indexB++];
                double imagB = b.data[indexB++];
                int n = indexC++;
                c.data[n] = c.data[n] + (realA * realB + imagA * imagB);
                int n2 = indexC++;
                c.data[n2] = c.data[n2] + (realA * imagB - imagA * realB);
            }
            for (int k = 1; k < a.numRows; ++k) {
                realA = a.getReal(k, i);
                imagA = a.getImag(k, i);
                end = indexB + b.numCols * 2;
                indexC = indexC_start;
                while (indexB < end) {
                    double realB = b.data[indexB++];
                    double imagB = b.data[indexB++];
                    int n = indexC++;
                    c.data[n] = c.data[n] + (realA * realB + imagA * imagB);
                    int n3 = indexC++;
                    c.data[n3] = c.data[n3] + (realA * imagB - imagA * realB);
                }
            }
        }
    }

    public static void multAddTransA_small(ZMatrixRMaj a, ZMatrixRMaj b, ZMatrixRMaj c) {
        if (a == c || b == c) {
            throw new IllegalArgumentException("Neither 'a' or 'b' can be the same matrix as 'c'");
        }
        if (a.numRows != b.numRows) {
            throw new MatrixDimensionException("The 'a' and 'b' matrices do not have compatible dimensions");
        }
        if (a.numCols != c.numRows || b.numCols != c.numCols) {
            throw new MatrixDimensionException("The results matrix does not have the desired dimensions");
        }
        int indexC = 0;
        for (int i = 0; i < a.numCols; ++i) {
            for (int j = 0; j < b.numCols; ++j) {
                int indexB;
                int indexA = i * 2;
                int end = indexB + b.numRows * b.numCols * 2;
                double realTotal = 0.0;
                double imagTotal = 0.0;
                for (indexB = j * 2; indexB < end; indexB += b.numCols * 2) {
                    double realA = a.data[indexA];
                    double imagA = a.data[indexA + 1];
                    double realB = b.data[indexB];
                    double imagB = b.data[indexB + 1];
                    realTotal += realA * realB + imagA * imagB;
                    imagTotal += realA * imagB - imagA * realB;
                    indexA += a.numCols * 2;
                }
                int n = indexC++;
                c.data[n] = c.data[n] + realTotal;
                int n2 = indexC++;
                c.data[n2] = c.data[n2] + imagTotal;
            }
        }
    }

    public static void multAddTransB(ZMatrixRMaj a, ZMatrixRMaj b, ZMatrixRMaj c) {
        if (a == c || b == c) {
            throw new IllegalArgumentException("Neither 'a' or 'b' can be the same matrix as 'c'");
        }
        if (a.numCols != b.numCols) {
            throw new MatrixDimensionException("The 'a' and 'b' matrices do not have compatible dimensions");
        }
        if (a.numRows != c.numRows || b.numRows != c.numCols) {
            throw new MatrixDimensionException("The results matrix does not have the desired dimensions");
        }
        int indexC = 0;
        int aIndexStart = 0;
        for (int xA = 0; xA < a.numRows; ++xA) {
            int end = aIndexStart + b.numCols * 2;
            int indexB = 0;
            for (int xB = 0; xB < b.numRows; ++xB) {
                int indexA = aIndexStart;
                double realTotal = 0.0;
                double imagTotal = 0.0;
                while (indexA < end) {
                    double realA = a.data[indexA++];
                    double imagA = a.data[indexA++];
                    double realB = b.data[indexB++];
                    double imagB = b.data[indexB++];
                    realTotal += realA * realB + imagA * imagB;
                    imagTotal += imagA * realB - realA * imagB;
                }
                int n = indexC++;
                c.data[n] = c.data[n] + realTotal;
                int n2 = indexC++;
                c.data[n2] = c.data[n2] + imagTotal;
            }
            aIndexStart += a.numCols * 2;
        }
    }

    public static void multAddTransAB(ZMatrixRMaj a, ZMatrixRMaj b, ZMatrixRMaj c) {
        if (a == c || b == c) {
            throw new IllegalArgumentException("Neither 'a' or 'b' can be the same matrix as 'c'");
        }
        if (a.numRows != b.numCols) {
            throw new MatrixDimensionException("The 'a' and 'b' matrices do not have compatible dimensions");
        }
        if (a.numCols != c.numRows || b.numRows != c.numCols) {
            throw new MatrixDimensionException("The results matrix does not have the desired dimensions");
        }
        int indexC = 0;
        for (int i = 0; i < a.numCols; ++i) {
            int indexB = 0;
            for (int j = 0; j < b.numRows; ++j) {
                int indexA = i * 2;
                int end = indexB + b.numCols * 2;
                double realTotal = 0.0;
                double imagTotal = 0.0;
                while (indexB < end) {
                    double realA = a.data[indexA];
                    double imagA = -a.data[indexA + 1];
                    double realB = b.data[indexB++];
                    double imagB = -b.data[indexB++];
                    realTotal += realA * realB - imagA * imagB;
                    imagTotal += realA * imagB + imagA * realB;
                    indexA += a.numCols * 2;
                }
                int n = indexC++;
                c.data[n] = c.data[n] + realTotal;
                int n2 = indexC++;
                c.data[n2] = c.data[n2] + imagTotal;
            }
        }
    }

    public static void multAddTransAB_aux(ZMatrixRMaj a, ZMatrixRMaj b, ZMatrixRMaj c, @Nullable double[] aux) {
        if (a == c || b == c) {
            throw new IllegalArgumentException("Neither 'a' or 'b' can be the same matrix as 'c'");
        }
        if (a.numRows != b.numCols) {
            throw new MatrixDimensionException("The 'a' and 'b' matrices do not have compatible dimensions");
        }
        if (a.numCols != c.numRows || b.numRows != c.numCols) {
            throw new MatrixDimensionException("The results matrix does not have the desired dimensions");
        }
        if (aux == null) {
            aux = new double[a.numRows * 2];
        }
        if (a.numCols == 0 || a.numRows == 0) {
            return;
        }
        int indexC = 0;
        for (int i = 0; i < a.numCols; ++i) {
            int indexA = i * 2;
            for (int k = 0; k < b.numCols; ++k) {
                aux[k * 2] = a.data[indexA];
                aux[k * 2 + 1] = a.data[indexA + 1];
                indexA += a.numCols * 2;
            }
            for (int j = 0; j < b.numRows; ++j) {
                int indexAux = 0;
                int indexB = j * b.numCols * 2;
                double realTotal = 0.0;
                double imagTotal = 0.0;
                for (int k = 0; k < b.numCols; ++k) {
                    double realA = aux[indexAux++];
                    double imagA = -aux[indexAux++];
                    double realB = b.data[indexB++];
                    double imagB = -b.data[indexB++];
                    realTotal += realA * realB - imagA * imagB;
                    imagTotal += realA * imagB + imagA * realB;
                }
                int n = indexC++;
                c.data[n] = c.data[n] + realTotal;
                int n2 = indexC++;
                c.data[n2] = c.data[n2] + imagTotal;
            }
        }
    }

    public static void mult_reorder(double realAlpha, double imagAlpha, ZMatrixRMaj a, ZMatrixRMaj b, ZMatrixRMaj c) {
        if (a == c || b == c) {
            throw new IllegalArgumentException("Neither 'a' or 'b' can be the same matrix as 'c'");
        }
        if (a.numCols != b.numRows) {
            throw new MatrixDimensionException("The 'a' and 'b' matrices do not have compatible dimensions");
        }
        if (a.numRows != c.numRows || b.numCols != c.numCols) {
            throw new MatrixDimensionException("The results matrix does not have the desired dimensions");
        }
        if (a.numCols == 0 || a.numRows == 0) {
            CommonOps_ZDRM.fill(c, 0.0, 0.0);
            return;
        }
        int indexCbase = 0;
        int strideA = a.getRowStride();
        int strideB = b.getRowStride();
        int strideC = c.getRowStride();
        int endOfKLoop = b.numRows * strideB;
        for (int i = 0; i < a.numRows; ++i) {
            double imgB;
            double realB;
            int indexA = i * strideA;
            int indexB = 0;
            int indexC = indexCbase;
            int end = indexB + strideB;
            double realTmp = a.data[indexA++];
            double imagTmp = a.data[indexA++];
            double realA = realAlpha * realTmp - imagAlpha * imagTmp;
            double imagA = realAlpha * imagTmp + imagAlpha * realTmp;
            while (indexB < end) {
                realB = b.data[indexB++];
                imgB = b.data[indexB++];
                c.data[indexC++] = realA * realB - imagA * imgB;
                c.data[indexC++] = realA * imgB + imagA * realB;
            }
            while (indexB != endOfKLoop) {
                indexC = indexCbase;
                end = indexB + strideB;
                realTmp = a.data[indexA++];
                imagTmp = a.data[indexA++];
                realA = realAlpha * realTmp - imagAlpha * imagTmp;
                imagA = realAlpha * imagTmp + imagAlpha * realTmp;
                while (indexB < end) {
                    realB = b.data[indexB++];
                    imgB = b.data[indexB++];
                    int n = indexC++;
                    c.data[n] = c.data[n] + (realA * realB - imagA * imgB);
                    int n2 = indexC++;
                    c.data[n2] = c.data[n2] + (realA * imgB + imagA * realB);
                }
            }
            indexCbase += strideC;
        }
    }

    public static void mult_small(double realAlpha, double imagAlpha, ZMatrixRMaj a, ZMatrixRMaj b, ZMatrixRMaj c) {
        if (a == c || b == c) {
            throw new IllegalArgumentException("Neither 'a' or 'b' can be the same matrix as 'c'");
        }
        if (a.numCols != b.numRows) {
            throw new MatrixDimensionException("The 'a' and 'b' matrices do not have compatible dimensions");
        }
        if (a.numRows != c.numRows || b.numCols != c.numCols) {
            throw new MatrixDimensionException("The results matrix does not have the desired dimensions");
        }
        int aIndexStart = 0;
        int indexC = 0;
        int strideA = a.getRowStride();
        int strideB = b.getRowStride();
        for (int i = 0; i < a.numRows; ++i) {
            for (int j = 0; j < b.numCols; ++j) {
                double realTotal = 0.0;
                double imgTotal = 0.0;
                int indexA = aIndexStart;
                int indexB = j * 2;
                int end = indexA + strideA;
                while (indexA < end) {
                    double realA = a.data[indexA++];
                    double imagA = a.data[indexA++];
                    double realB = b.data[indexB];
                    double imgB = b.data[indexB + 1];
                    realTotal += realA * realB - imagA * imgB;
                    imgTotal += realA * imgB + imagA * realB;
                    indexB += strideB;
                }
                c.data[indexC++] = realAlpha * realTotal - imagAlpha * imgTotal;
                c.data[indexC++] = realAlpha * imgTotal + imagAlpha * realTotal;
            }
            aIndexStart += strideA;
        }
    }

    public static void multTransA_reorder(double realAlpha, double imagAlpha, ZMatrixRMaj a, ZMatrixRMaj b, ZMatrixRMaj c) {
        if (a == c || b == c) {
            throw new IllegalArgumentException("Neither 'a' or 'b' can be the same matrix as 'c'");
        }
        if (a.numRows != b.numRows) {
            throw new MatrixDimensionException("The 'a' and 'b' matrices do not have compatible dimensions");
        }
        if (a.numCols != c.numRows || b.numCols != c.numCols) {
            throw new MatrixDimensionException("The results matrix does not have the desired dimensions");
        }
        if (a.numCols == 0 || a.numRows == 0) {
            CommonOps_ZDRM.fill(c, 0.0, 0.0);
            return;
        }
        for (int i = 0; i < a.numCols; ++i) {
            int indexC_start = i * c.numCols * 2;
            double realTmp = a.data[i * 2];
            double imagTmp = a.data[i * 2 + 1];
            double realA = realAlpha * realTmp + imagAlpha * imagTmp;
            double imagA = realAlpha * imagTmp - imagAlpha * realTmp;
            int indexB = 0;
            int end = indexB + b.numCols * 2;
            int indexC = indexC_start;
            while (indexB < end) {
                double realB = b.data[indexB++];
                double imagB = b.data[indexB++];
                c.data[indexC++] = realA * realB + imagA * imagB;
                c.data[indexC++] = realA * imagB - imagA * realB;
            }
            for (int k = 1; k < a.numRows; ++k) {
                realTmp = a.getReal(k, i);
                imagTmp = a.getImag(k, i);
                realA = realAlpha * realTmp + imagAlpha * imagTmp;
                imagA = realAlpha * imagTmp - imagAlpha * realTmp;
                end = indexB + b.numCols * 2;
                indexC = indexC_start;
                while (indexB < end) {
                    double realB = b.data[indexB++];
                    double imagB = b.data[indexB++];
                    int n = indexC++;
                    c.data[n] = c.data[n] + (realA * realB + imagA * imagB);
                    int n2 = indexC++;
                    c.data[n2] = c.data[n2] + (realA * imagB - imagA * realB);
                }
            }
        }
    }

    public static void multTransA_small(double realAlpha, double imagAlpha, ZMatrixRMaj a, ZMatrixRMaj b, ZMatrixRMaj c) {
        if (a == c || b == c) {
            throw new IllegalArgumentException("Neither 'a' or 'b' can be the same matrix as 'c'");
        }
        if (a.numRows != b.numRows) {
            throw new MatrixDimensionException("The 'a' and 'b' matrices do not have compatible dimensions");
        }
        if (a.numCols != c.numRows || b.numCols != c.numCols) {
            throw new MatrixDimensionException("The results matrix does not have the desired dimensions");
        }
        int indexC = 0;
        for (int i = 0; i < a.numCols; ++i) {
            for (int j = 0; j < b.numCols; ++j) {
                int indexB;
                int indexA = i * 2;
                int end = indexB + b.numRows * b.numCols * 2;
                double realTotal = 0.0;
                double imagTotal = 0.0;
                for (indexB = j * 2; indexB < end; indexB += b.numCols * 2) {
                    double realA = a.data[indexA];
                    double imagA = a.data[indexA + 1];
                    double realB = b.data[indexB];
                    double imagB = b.data[indexB + 1];
                    realTotal += realA * realB + imagA * imagB;
                    imagTotal += realA * imagB - imagA * realB;
                    indexA += a.numCols * 2;
                }
                c.data[indexC++] = realAlpha * realTotal - imagAlpha * imagTotal;
                c.data[indexC++] = realAlpha * imagTotal + imagAlpha * realTotal;
            }
        }
    }

    public static void multTransB(double realAlpha, double imagAlpha, ZMatrixRMaj a, ZMatrixRMaj b, ZMatrixRMaj c) {
        if (a == c || b == c) {
            throw new IllegalArgumentException("Neither 'a' or 'b' can be the same matrix as 'c'");
        }
        if (a.numCols != b.numCols) {
            throw new MatrixDimensionException("The 'a' and 'b' matrices do not have compatible dimensions");
        }
        if (a.numRows != c.numRows || b.numRows != c.numCols) {
            throw new MatrixDimensionException("The results matrix does not have the desired dimensions");
        }
        int indexC = 0;
        int aIndexStart = 0;
        for (int xA = 0; xA < a.numRows; ++xA) {
            int end = aIndexStart + b.numCols * 2;
            int indexB = 0;
            for (int xB = 0; xB < b.numRows; ++xB) {
                int indexA = aIndexStart;
                double realTotal = 0.0;
                double imagTotal = 0.0;
                while (indexA < end) {
                    double realA = a.data[indexA++];
                    double imagA = a.data[indexA++];
                    double realB = b.data[indexB++];
                    double imagB = b.data[indexB++];
                    realTotal += realA * realB + imagA * imagB;
                    imagTotal += imagA * realB - realA * imagB;
                }
                c.data[indexC++] = realAlpha * realTotal - imagAlpha * imagTotal;
                c.data[indexC++] = realAlpha * imagTotal + imagAlpha * realTotal;
            }
            aIndexStart += a.numCols * 2;
        }
    }

    public static void multTransAB(double realAlpha, double imagAlpha, ZMatrixRMaj a, ZMatrixRMaj b, ZMatrixRMaj c) {
        if (a == c || b == c) {
            throw new IllegalArgumentException("Neither 'a' or 'b' can be the same matrix as 'c'");
        }
        if (a.numRows != b.numCols) {
            throw new MatrixDimensionException("The 'a' and 'b' matrices do not have compatible dimensions");
        }
        if (a.numCols != c.numRows || b.numRows != c.numCols) {
            throw new MatrixDimensionException("The results matrix does not have the desired dimensions");
        }
        int indexC = 0;
        for (int i = 0; i < a.numCols; ++i) {
            int indexB = 0;
            for (int j = 0; j < b.numRows; ++j) {
                int indexA = i * 2;
                int end = indexB + b.numCols * 2;
                double realTotal = 0.0;
                double imagTotal = 0.0;
                while (indexB < end) {
                    double realA = a.data[indexA];
                    double imagA = -a.data[indexA + 1];
                    double realB = b.data[indexB++];
                    double imagB = -b.data[indexB++];
                    realTotal += realA * realB - imagA * imagB;
                    imagTotal += realA * imagB + imagA * realB;
                    indexA += a.numCols * 2;
                }
                c.data[indexC++] = realAlpha * realTotal - imagAlpha * imagTotal;
                c.data[indexC++] = realAlpha * imagTotal + imagAlpha * realTotal;
            }
        }
    }

    public static void multTransAB_aux(double realAlpha, double imagAlpha, ZMatrixRMaj a, ZMatrixRMaj b, ZMatrixRMaj c, @Nullable double[] aux) {
        if (a == c || b == c) {
            throw new IllegalArgumentException("Neither 'a' or 'b' can be the same matrix as 'c'");
        }
        if (a.numRows != b.numCols) {
            throw new MatrixDimensionException("The 'a' and 'b' matrices do not have compatible dimensions");
        }
        if (a.numCols != c.numRows || b.numRows != c.numCols) {
            throw new MatrixDimensionException("The results matrix does not have the desired dimensions");
        }
        if (aux == null) {
            aux = new double[a.numRows * 2];
        }
        if (a.numCols == 0 || a.numRows == 0) {
            CommonOps_ZDRM.fill(c, 0.0, 0.0);
            return;
        }
        int indexC = 0;
        for (int i = 0; i < a.numCols; ++i) {
            int indexA = i * 2;
            for (int k = 0; k < b.numCols; ++k) {
                aux[k * 2] = a.data[indexA];
                aux[k * 2 + 1] = a.data[indexA + 1];
                indexA += a.numCols * 2;
            }
            for (int j = 0; j < b.numRows; ++j) {
                int indexAux = 0;
                int indexB = j * b.numCols * 2;
                double realTotal = 0.0;
                double imagTotal = 0.0;
                for (int k = 0; k < b.numCols; ++k) {
                    double realA = aux[indexAux++];
                    double imagA = -aux[indexAux++];
                    double realB = b.data[indexB++];
                    double imagB = -b.data[indexB++];
                    realTotal += realA * realB - imagA * imagB;
                    imagTotal += realA * imagB + imagA * realB;
                }
                c.data[indexC++] = realAlpha * realTotal - imagAlpha * imagTotal;
                c.data[indexC++] = realAlpha * imagTotal + imagAlpha * realTotal;
            }
        }
    }

    public static void multAdd_reorder(double realAlpha, double imagAlpha, ZMatrixRMaj a, ZMatrixRMaj b, ZMatrixRMaj c) {
        if (a == c || b == c) {
            throw new IllegalArgumentException("Neither 'a' or 'b' can be the same matrix as 'c'");
        }
        if (a.numCols != b.numRows) {
            throw new MatrixDimensionException("The 'a' and 'b' matrices do not have compatible dimensions");
        }
        if (a.numRows != c.numRows || b.numCols != c.numCols) {
            throw new MatrixDimensionException("The results matrix does not have the desired dimensions");
        }
        if (a.numCols == 0 || a.numRows == 0) {
            return;
        }
        int indexCbase = 0;
        int strideA = a.getRowStride();
        int strideB = b.getRowStride();
        int strideC = c.getRowStride();
        int endOfKLoop = b.numRows * strideB;
        for (int i = 0; i < a.numRows; ++i) {
            double imgB;
            double realB;
            int indexA = i * strideA;
            int indexB = 0;
            int indexC = indexCbase;
            int end = indexB + strideB;
            double realTmp = a.data[indexA++];
            double imagTmp = a.data[indexA++];
            double realA = realAlpha * realTmp - imagAlpha * imagTmp;
            double imagA = realAlpha * imagTmp + imagAlpha * realTmp;
            while (indexB < end) {
                realB = b.data[indexB++];
                imgB = b.data[indexB++];
                int n = indexC++;
                c.data[n] = c.data[n] + (realA * realB - imagA * imgB);
                int n2 = indexC++;
                c.data[n2] = c.data[n2] + (realA * imgB + imagA * realB);
            }
            while (indexB != endOfKLoop) {
                indexC = indexCbase;
                end = indexB + strideB;
                realTmp = a.data[indexA++];
                imagTmp = a.data[indexA++];
                realA = realAlpha * realTmp - imagAlpha * imagTmp;
                imagA = realAlpha * imagTmp + imagAlpha * realTmp;
                while (indexB < end) {
                    realB = b.data[indexB++];
                    imgB = b.data[indexB++];
                    int n = indexC++;
                    c.data[n] = c.data[n] + (realA * realB - imagA * imgB);
                    int n3 = indexC++;
                    c.data[n3] = c.data[n3] + (realA * imgB + imagA * realB);
                }
            }
            indexCbase += strideC;
        }
    }

    public static void multAdd_small(double realAlpha, double imagAlpha, ZMatrixRMaj a, ZMatrixRMaj b, ZMatrixRMaj c) {
        if (a == c || b == c) {
            throw new IllegalArgumentException("Neither 'a' or 'b' can be the same matrix as 'c'");
        }
        if (a.numCols != b.numRows) {
            throw new MatrixDimensionException("The 'a' and 'b' matrices do not have compatible dimensions");
        }
        if (a.numRows != c.numRows || b.numCols != c.numCols) {
            throw new MatrixDimensionException("The results matrix does not have the desired dimensions");
        }
        int aIndexStart = 0;
        int indexC = 0;
        int strideA = a.getRowStride();
        int strideB = b.getRowStride();
        for (int i = 0; i < a.numRows; ++i) {
            for (int j = 0; j < b.numCols; ++j) {
                double realTotal = 0.0;
                double imgTotal = 0.0;
                int indexA = aIndexStart;
                int indexB = j * 2;
                int end = indexA + strideA;
                while (indexA < end) {
                    double realA = a.data[indexA++];
                    double imagA = a.data[indexA++];
                    double realB = b.data[indexB];
                    double imgB = b.data[indexB + 1];
                    realTotal += realA * realB - imagA * imgB;
                    imgTotal += realA * imgB + imagA * realB;
                    indexB += strideB;
                }
                int n = indexC++;
                c.data[n] = c.data[n] + (realAlpha * realTotal - imagAlpha * imgTotal);
                int n2 = indexC++;
                c.data[n2] = c.data[n2] + (realAlpha * imgTotal + imagAlpha * realTotal);
            }
            aIndexStart += strideA;
        }
    }

    public static void multAddTransA_reorder(double realAlpha, double imagAlpha, ZMatrixRMaj a, ZMatrixRMaj b, ZMatrixRMaj c) {
        if (a == c || b == c) {
            throw new IllegalArgumentException("Neither 'a' or 'b' can be the same matrix as 'c'");
        }
        if (a.numRows != b.numRows) {
            throw new MatrixDimensionException("The 'a' and 'b' matrices do not have compatible dimensions");
        }
        if (a.numCols != c.numRows || b.numCols != c.numCols) {
            throw new MatrixDimensionException("The results matrix does not have the desired dimensions");
        }
        if (a.numCols == 0 || a.numRows == 0) {
            return;
        }
        for (int i = 0; i < a.numCols; ++i) {
            int indexC_start = i * c.numCols * 2;
            double realTmp = a.data[i * 2];
            double imagTmp = a.data[i * 2 + 1];
            double realA = realAlpha * realTmp + imagAlpha * imagTmp;
            double imagA = realAlpha * imagTmp - imagAlpha * realTmp;
            int indexB = 0;
            int end = indexB + b.numCols * 2;
            int indexC = indexC_start;
            while (indexB < end) {
                double realB = b.data[indexB++];
                double imagB = b.data[indexB++];
                int n = indexC++;
                c.data[n] = c.data[n] + (realA * realB + imagA * imagB);
                int n2 = indexC++;
                c.data[n2] = c.data[n2] + (realA * imagB - imagA * realB);
            }
            for (int k = 1; k < a.numRows; ++k) {
                realTmp = a.getReal(k, i);
                imagTmp = a.getImag(k, i);
                realA = realAlpha * realTmp + imagAlpha * imagTmp;
                imagA = realAlpha * imagTmp - imagAlpha * realTmp;
                end = indexB + b.numCols * 2;
                indexC = indexC_start;
                while (indexB < end) {
                    double realB = b.data[indexB++];
                    double imagB = b.data[indexB++];
                    int n = indexC++;
                    c.data[n] = c.data[n] + (realA * realB + imagA * imagB);
                    int n3 = indexC++;
                    c.data[n3] = c.data[n3] + (realA * imagB - imagA * realB);
                }
            }
        }
    }

    public static void multAddTransA_small(double realAlpha, double imagAlpha, ZMatrixRMaj a, ZMatrixRMaj b, ZMatrixRMaj c) {
        if (a == c || b == c) {
            throw new IllegalArgumentException("Neither 'a' or 'b' can be the same matrix as 'c'");
        }
        if (a.numRows != b.numRows) {
            throw new MatrixDimensionException("The 'a' and 'b' matrices do not have compatible dimensions");
        }
        if (a.numCols != c.numRows || b.numCols != c.numCols) {
            throw new MatrixDimensionException("The results matrix does not have the desired dimensions");
        }
        int indexC = 0;
        for (int i = 0; i < a.numCols; ++i) {
            for (int j = 0; j < b.numCols; ++j) {
                int indexB;
                int indexA = i * 2;
                int end = indexB + b.numRows * b.numCols * 2;
                double realTotal = 0.0;
                double imagTotal = 0.0;
                for (indexB = j * 2; indexB < end; indexB += b.numCols * 2) {
                    double realA = a.data[indexA];
                    double imagA = a.data[indexA + 1];
                    double realB = b.data[indexB];
                    double imagB = b.data[indexB + 1];
                    realTotal += realA * realB + imagA * imagB;
                    imagTotal += realA * imagB - imagA * realB;
                    indexA += a.numCols * 2;
                }
                int n = indexC++;
                c.data[n] = c.data[n] + (realAlpha * realTotal - imagAlpha * imagTotal);
                int n2 = indexC++;
                c.data[n2] = c.data[n2] + (realAlpha * imagTotal + imagAlpha * realTotal);
            }
        }
    }

    public static void multAddTransB(double realAlpha, double imagAlpha, ZMatrixRMaj a, ZMatrixRMaj b, ZMatrixRMaj c) {
        if (a == c || b == c) {
            throw new IllegalArgumentException("Neither 'a' or 'b' can be the same matrix as 'c'");
        }
        if (a.numCols != b.numCols) {
            throw new MatrixDimensionException("The 'a' and 'b' matrices do not have compatible dimensions");
        }
        if (a.numRows != c.numRows || b.numRows != c.numCols) {
            throw new MatrixDimensionException("The results matrix does not have the desired dimensions");
        }
        int indexC = 0;
        int aIndexStart = 0;
        for (int xA = 0; xA < a.numRows; ++xA) {
            int end = aIndexStart + b.numCols * 2;
            int indexB = 0;
            for (int xB = 0; xB < b.numRows; ++xB) {
                int indexA = aIndexStart;
                double realTotal = 0.0;
                double imagTotal = 0.0;
                while (indexA < end) {
                    double realA = a.data[indexA++];
                    double imagA = a.data[indexA++];
                    double realB = b.data[indexB++];
                    double imagB = b.data[indexB++];
                    realTotal += realA * realB + imagA * imagB;
                    imagTotal += imagA * realB - realA * imagB;
                }
                int n = indexC++;
                c.data[n] = c.data[n] + (realAlpha * realTotal - imagAlpha * imagTotal);
                int n2 = indexC++;
                c.data[n2] = c.data[n2] + (realAlpha * imagTotal + imagAlpha * realTotal);
            }
            aIndexStart += a.numCols * 2;
        }
    }

    public static void multAddTransAB(double realAlpha, double imagAlpha, ZMatrixRMaj a, ZMatrixRMaj b, ZMatrixRMaj c) {
        if (a == c || b == c) {
            throw new IllegalArgumentException("Neither 'a' or 'b' can be the same matrix as 'c'");
        }
        if (a.numRows != b.numCols) {
            throw new MatrixDimensionException("The 'a' and 'b' matrices do not have compatible dimensions");
        }
        if (a.numCols != c.numRows || b.numRows != c.numCols) {
            throw new MatrixDimensionException("The results matrix does not have the desired dimensions");
        }
        int indexC = 0;
        for (int i = 0; i < a.numCols; ++i) {
            int indexB = 0;
            for (int j = 0; j < b.numRows; ++j) {
                int indexA = i * 2;
                int end = indexB + b.numCols * 2;
                double realTotal = 0.0;
                double imagTotal = 0.0;
                while (indexB < end) {
                    double realA = a.data[indexA];
                    double imagA = -a.data[indexA + 1];
                    double realB = b.data[indexB++];
                    double imagB = -b.data[indexB++];
                    realTotal += realA * realB - imagA * imagB;
                    imagTotal += realA * imagB + imagA * realB;
                    indexA += a.numCols * 2;
                }
                int n = indexC++;
                c.data[n] = c.data[n] + (realAlpha * realTotal - imagAlpha * imagTotal);
                int n2 = indexC++;
                c.data[n2] = c.data[n2] + (realAlpha * imagTotal + imagAlpha * realTotal);
            }
        }
    }

    public static void multAddTransAB_aux(double realAlpha, double imagAlpha, ZMatrixRMaj a, ZMatrixRMaj b, ZMatrixRMaj c, @Nullable double[] aux) {
        if (a == c || b == c) {
            throw new IllegalArgumentException("Neither 'a' or 'b' can be the same matrix as 'c'");
        }
        if (a.numRows != b.numCols) {
            throw new MatrixDimensionException("The 'a' and 'b' matrices do not have compatible dimensions");
        }
        if (a.numCols != c.numRows || b.numRows != c.numCols) {
            throw new MatrixDimensionException("The results matrix does not have the desired dimensions");
        }
        if (aux == null) {
            aux = new double[a.numRows * 2];
        }
        if (a.numCols == 0 || a.numRows == 0) {
            return;
        }
        int indexC = 0;
        for (int i = 0; i < a.numCols; ++i) {
            int indexA = i * 2;
            for (int k = 0; k < b.numCols; ++k) {
                aux[k * 2] = a.data[indexA];
                aux[k * 2 + 1] = a.data[indexA + 1];
                indexA += a.numCols * 2;
            }
            for (int j = 0; j < b.numRows; ++j) {
                int indexAux = 0;
                int indexB = j * b.numCols * 2;
                double realTotal = 0.0;
                double imagTotal = 0.0;
                for (int k = 0; k < b.numCols; ++k) {
                    double realA = aux[indexAux++];
                    double imagA = -aux[indexAux++];
                    double realB = b.data[indexB++];
                    double imagB = -b.data[indexB++];
                    realTotal += realA * realB - imagA * imagB;
                    imagTotal += realA * imagB + imagA * realB;
                }
                int n = indexC++;
                c.data[n] = c.data[n] + (realAlpha * realTotal - imagAlpha * imagTotal);
                int n2 = indexC++;
                c.data[n2] = c.data[n2] + (realAlpha * imagTotal + imagAlpha * realTotal);
            }
        }
    }
}

