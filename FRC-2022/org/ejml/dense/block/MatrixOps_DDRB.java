/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  org.jetbrains.annotations.Nullable
 */
package org.ejml.dense.block;

import java.util.Random;
import org.ejml.EjmlParameters;
import org.ejml.UtilEjml;
import org.ejml.data.DGrowArray;
import org.ejml.data.DMatrixD1;
import org.ejml.data.DMatrixRBlock;
import org.ejml.data.DMatrixRMaj;
import org.ejml.data.DSubmatrixD1;
import org.ejml.dense.block.MatrixMult_DDRB;
import org.ejml.dense.row.CommonOps_DDRM;
import org.ejml.dense.row.MatrixFeatures_DDRM;
import org.ejml.dense.row.RandomMatrices_DDRM;
import org.ejml.ops.DConvertMatrixStruct;
import org.jetbrains.annotations.Nullable;

public class MatrixOps_DDRB {
    public static void convert(DMatrixRMaj src, DMatrixRBlock dst) {
        DConvertMatrixStruct.convert(src, dst);
    }

    public static DMatrixRBlock convertInplace(DMatrixRMaj src, @Nullable DMatrixRBlock dst, @Nullable DGrowArray workspace) {
        if (dst == null) {
            dst = new DMatrixRBlock();
        }
        dst.data = src.data;
        dst.blockLength = EjmlParameters.BLOCK_WIDTH;
        dst.numRows = src.numRows;
        dst.numCols = src.numCols;
        MatrixOps_DDRB.convertRowToBlock(src.numRows, src.numCols, dst.blockLength, src.data, workspace);
        return dst;
    }

    public static void convertRowToBlock(int numRows, int numCols, int blockLength, double[] data, @Nullable DGrowArray workspace) {
        int minLength = Math.min(blockLength, numRows) * numCols;
        double[] tmp = UtilEjml.adjust(workspace, minLength);
        for (int i = 0; i < numRows; i += blockLength) {
            int blockHeight = Math.min(blockLength, numRows - i);
            System.arraycopy(data, i * numCols, tmp, 0, blockHeight * numCols);
            for (int j = 0; j < numCols; j += blockLength) {
                int blockWidth = Math.min(blockLength, numCols - j);
                int indexDst = i * numCols + blockHeight * j;
                int indexSrcRow = j;
                for (int k = 0; k < blockHeight; ++k) {
                    System.arraycopy(tmp, indexSrcRow, data, indexDst, blockWidth);
                    indexDst += blockWidth;
                    indexSrcRow += numCols;
                }
            }
        }
    }

    public static DMatrixRMaj convert(DMatrixRBlock src, DMatrixRMaj dst) {
        return DConvertMatrixStruct.convert(src, dst);
    }

    public static DMatrixRMaj convertInplace(DMatrixRBlock src, @Nullable DMatrixRMaj dst, @Nullable DGrowArray workspace) {
        if (dst == null) {
            dst = new DMatrixRMaj();
        }
        dst.data = src.data;
        dst.numRows = src.numRows;
        dst.numCols = src.numCols;
        MatrixOps_DDRB.convertBlockToRow(src.numRows, src.numCols, src.blockLength, src.data, workspace);
        return dst;
    }

    public static void convertBlockToRow(int numRows, int numCols, int blockLength, double[] data, @Nullable DGrowArray workspace) {
        int minLength = Math.min(blockLength, numRows) * numCols;
        double[] tmp = UtilEjml.adjust(workspace, minLength);
        for (int i = 0; i < numRows; i += blockLength) {
            int blockHeight = Math.min(blockLength, numRows - i);
            System.arraycopy(data, i * numCols, tmp, 0, blockHeight * numCols);
            for (int j = 0; j < numCols; j += blockLength) {
                int blockWidth = Math.min(blockLength, numCols - j);
                int indexSrc = blockHeight * j;
                int indexDstRow = i * numCols + j;
                for (int k = 0; k < blockHeight; ++k) {
                    System.arraycopy(tmp, indexSrc, data, indexDstRow, blockWidth);
                    indexSrc += blockWidth;
                    indexDstRow += numCols;
                }
            }
        }
    }

    public static void convertTranSrc(DMatrixRMaj src, DMatrixRBlock dst) {
        if (src.numRows != dst.numCols || src.numCols != dst.numRows) {
            throw new IllegalArgumentException("Incompatible matrix shapes.");
        }
        for (int i = 0; i < dst.numRows; i += dst.blockLength) {
            int blockHeight = Math.min(dst.blockLength, dst.numRows - i);
            for (int j = 0; j < dst.numCols; j += dst.blockLength) {
                int blockWidth = Math.min(dst.blockLength, dst.numCols - j);
                int indexDst = i * dst.numCols + blockHeight * j;
                int indexSrc = j * src.numCols + i;
                for (int l = 0; l < blockWidth; ++l) {
                    int rowSrc = indexSrc + l * src.numCols;
                    int rowDst = indexDst + l;
                    int k = 0;
                    while (k < blockHeight) {
                        dst.data[rowDst] = src.data[rowSrc++];
                        ++k;
                        rowDst += blockWidth;
                    }
                }
            }
        }
    }

    public static void mult(DMatrixRBlock A, DMatrixRBlock B, DMatrixRBlock C) {
        if (A.numCols != B.numRows) {
            throw new IllegalArgumentException("Columns in A are incompatible with rows in B");
        }
        if (A.numRows != C.numRows) {
            throw new IllegalArgumentException("Rows in A are incompatible with rows in C");
        }
        if (B.numCols != C.numCols) {
            throw new IllegalArgumentException("Columns in B are incompatible with columns in C");
        }
        if (A.blockLength != B.blockLength || A.blockLength != C.blockLength) {
            throw new IllegalArgumentException("Block lengths are not all the same.");
        }
        int blockLength = A.blockLength;
        DSubmatrixD1 Asub = new DSubmatrixD1(A, 0, A.numRows, 0, A.numCols);
        DSubmatrixD1 Bsub = new DSubmatrixD1(B, 0, B.numRows, 0, B.numCols);
        DSubmatrixD1 Csub = new DSubmatrixD1(C, 0, C.numRows, 0, C.numCols);
        MatrixMult_DDRB.mult(blockLength, Asub, Bsub, Csub);
    }

    public static void multTransA(DMatrixRBlock A, DMatrixRBlock B, DMatrixRBlock C) {
        if (A.numRows != B.numRows) {
            throw new IllegalArgumentException("Rows in A are incompatible with rows in B");
        }
        if (A.numCols != C.numRows) {
            throw new IllegalArgumentException("Columns in A are incompatible with rows in C");
        }
        if (B.numCols != C.numCols) {
            throw new IllegalArgumentException("Columns in B are incompatible with columns in C");
        }
        if (A.blockLength != B.blockLength || A.blockLength != C.blockLength) {
            throw new IllegalArgumentException("Block lengths are not all the same.");
        }
        int blockLength = A.blockLength;
        DSubmatrixD1 Asub = new DSubmatrixD1(A, 0, A.numRows, 0, A.numCols);
        DSubmatrixD1 Bsub = new DSubmatrixD1(B, 0, B.numRows, 0, B.numCols);
        DSubmatrixD1 Csub = new DSubmatrixD1(C, 0, C.numRows, 0, C.numCols);
        MatrixMult_DDRB.multTransA(blockLength, Asub, Bsub, Csub);
    }

    public static void multTransB(DMatrixRBlock A, DMatrixRBlock B, DMatrixRBlock C) {
        if (A.numCols != B.numCols) {
            throw new IllegalArgumentException("Columns in A are incompatible with columns in B");
        }
        if (A.numRows != C.numRows) {
            throw new IllegalArgumentException("Rows in A are incompatible with rows in C");
        }
        if (B.numRows != C.numCols) {
            throw new IllegalArgumentException("Rows in B are incompatible with columns in C");
        }
        if (A.blockLength != B.blockLength || A.blockLength != C.blockLength) {
            throw new IllegalArgumentException("Block lengths are not all the same.");
        }
        int blockLength = A.blockLength;
        DSubmatrixD1 Asub = new DSubmatrixD1(A, 0, A.numRows, 0, A.numCols);
        DSubmatrixD1 Bsub = new DSubmatrixD1(B, 0, B.numRows, 0, B.numCols);
        DSubmatrixD1 Csub = new DSubmatrixD1(C, 0, C.numRows, 0, C.numCols);
        MatrixMult_DDRB.multTransB(blockLength, Asub, Bsub, Csub);
    }

    public static DMatrixRBlock transpose(DMatrixRBlock A, @Nullable DMatrixRBlock A_tran) {
        if (A_tran != null) {
            if (A.numRows != A_tran.numCols || A.numCols != A_tran.numRows) {
                throw new IllegalArgumentException("Incompatible dimensions.");
            }
            if (A.blockLength != A_tran.blockLength) {
                throw new IllegalArgumentException("Incompatible block size.");
            }
        } else {
            A_tran = new DMatrixRBlock(A.numCols, A.numRows, A.blockLength);
        }
        for (int i = 0; i < A.numRows; i += A.blockLength) {
            int blockHeight = Math.min(A.blockLength, A.numRows - i);
            for (int j = 0; j < A.numCols; j += A.blockLength) {
                int blockWidth = Math.min(A.blockLength, A.numCols - j);
                int indexA = i * A.numCols + blockHeight * j;
                int indexC = j * A_tran.numCols + blockWidth * i;
                MatrixOps_DDRB.transposeBlock(A, A_tran, indexA, indexC, blockWidth, blockHeight);
            }
        }
        return A_tran;
    }

    private static void transposeBlock(DMatrixRBlock A, DMatrixRBlock A_tran, int indexA, int indexC, int width, int height) {
        for (int i = 0; i < height; ++i) {
            int rowIndexA;
            int rowIndexC = indexC + i;
            int end = rowIndexA + width;
            for (rowIndexA = indexA + width * i; rowIndexA < end; ++rowIndexA) {
                A_tran.data[rowIndexC] = A.data[rowIndexA];
                rowIndexC += height;
            }
        }
    }

    public static DMatrixRBlock createRandom(int numRows, int numCols, double min, double max, Random rand) {
        DMatrixRBlock ret = new DMatrixRBlock(numRows, numCols);
        RandomMatrices_DDRM.fillUniform(ret, min, max, rand);
        return ret;
    }

    public static DMatrixRBlock createRandom(int numRows, int numCols, double min, double max, Random rand, int blockLength) {
        DMatrixRBlock ret = new DMatrixRBlock(numRows, numCols, blockLength);
        RandomMatrices_DDRM.fillUniform(ret, min, max, rand);
        return ret;
    }

    public static DMatrixRBlock convert(DMatrixRMaj A, int blockLength) {
        DMatrixRBlock ret = new DMatrixRBlock(A.numRows, A.numCols, blockLength);
        MatrixOps_DDRB.convert(A, ret);
        return ret;
    }

    public static DMatrixRBlock convert(DMatrixRMaj A) {
        DMatrixRBlock ret = new DMatrixRBlock(A.numRows, A.numCols);
        MatrixOps_DDRB.convert(A, ret);
        return ret;
    }

    public static boolean isEquals(DMatrixRBlock A, DMatrixRBlock B) {
        if (A.blockLength != B.blockLength) {
            return false;
        }
        return MatrixFeatures_DDRM.isEquals(A, B);
    }

    public static boolean isEquals(DMatrixRBlock A, DMatrixRBlock B, double tol) {
        if (A.blockLength != B.blockLength) {
            return false;
        }
        return MatrixFeatures_DDRM.isEquals(A, B, tol);
    }

    public static void zeroTriangle(boolean upper, DMatrixRBlock A) {
        int blockLength = A.blockLength;
        if (upper) {
            for (int i = 0; i < A.numRows; i += blockLength) {
                int h = Math.min(blockLength, A.numRows - i);
                for (int j = i; j < A.numCols; j += blockLength) {
                    int l;
                    int k;
                    int w = Math.min(blockLength, A.numCols - j);
                    int index = i * A.numCols + h * j;
                    if (j == i) {
                        for (k = 0; k < h; ++k) {
                            for (l = k + 1; l < w; ++l) {
                                A.data[index + w * k + l] = 0.0;
                            }
                        }
                        continue;
                    }
                    for (k = 0; k < h; ++k) {
                        for (l = 0; l < w; ++l) {
                            A.data[index + w * k + l] = 0.0;
                        }
                    }
                }
            }
        } else {
            for (int i = 0; i < A.numRows; i += blockLength) {
                int h = Math.min(blockLength, A.numRows - i);
                for (int j = 0; j <= i; j += blockLength) {
                    int k;
                    int w = Math.min(blockLength, A.numCols - j);
                    int index = i * A.numCols + h * j;
                    if (j == i) {
                        for (k = 0; k < h; ++k) {
                            int z = Math.min(k, w);
                            for (int l = 0; l < z; ++l) {
                                A.data[index + w * k + l] = 0.0;
                            }
                        }
                        continue;
                    }
                    for (k = 0; k < h; ++k) {
                        for (int l = 0; l < w; ++l) {
                            A.data[index + w * k + l] = 0.0;
                        }
                    }
                }
            }
        }
    }

    public static void copyTriangle(boolean upper, DMatrixRBlock src, DMatrixRBlock dst) {
        if (src.blockLength != dst.blockLength) {
            throw new IllegalArgumentException("Block size is different");
        }
        if (src.numRows < dst.numRows) {
            throw new IllegalArgumentException("The src has fewer rows than dst");
        }
        if (src.numCols < dst.numCols) {
            throw new IllegalArgumentException("The src has fewer columns than dst");
        }
        int blockLength = src.blockLength;
        int numRows = Math.min(src.numRows, dst.numRows);
        int numCols = Math.min(src.numCols, dst.numCols);
        if (upper) {
            for (int i = 0; i < numRows; i += blockLength) {
                int heightSrc = Math.min(blockLength, src.numRows - i);
                int heightDst = Math.min(blockLength, dst.numRows - i);
                for (int j = i; j < numCols; j += blockLength) {
                    int k;
                    int widthSrc = Math.min(blockLength, src.numCols - j);
                    int widthDst = Math.min(blockLength, dst.numCols - j);
                    int indexSrc = i * src.numCols + heightSrc * j;
                    int indexDst = i * dst.numCols + heightDst * j;
                    if (j == i) {
                        for (k = 0; k < heightDst; ++k) {
                            for (int l = k; l < widthDst; ++l) {
                                dst.data[indexDst + widthDst * k + l] = src.data[indexSrc + widthSrc * k + l];
                            }
                        }
                        continue;
                    }
                    for (k = 0; k < heightDst; ++k) {
                        System.arraycopy(src.data, indexSrc + widthSrc * k, dst.data, indexDst + widthDst * k, widthDst);
                    }
                }
            }
        } else {
            for (int i = 0; i < numRows; i += blockLength) {
                int heightSrc = Math.min(blockLength, src.numRows - i);
                int heightDst = Math.min(blockLength, dst.numRows - i);
                for (int j = 0; j <= i; j += blockLength) {
                    int k;
                    int widthSrc = Math.min(blockLength, src.numCols - j);
                    int widthDst = Math.min(blockLength, dst.numCols - j);
                    int indexSrc = i * src.numCols + heightSrc * j;
                    int indexDst = i * dst.numCols + heightDst * j;
                    if (j == i) {
                        for (k = 0; k < heightDst; ++k) {
                            int z = Math.min(k + 1, widthDst);
                            for (int l = 0; l < z; ++l) {
                                dst.data[indexDst + widthDst * k + l] = src.data[indexSrc + widthSrc * k + l];
                            }
                        }
                        continue;
                    }
                    for (k = 0; k < heightDst; ++k) {
                        System.arraycopy(src.data, indexSrc + widthSrc * k, dst.data, indexDst + widthDst * k, widthDst);
                    }
                }
            }
        }
    }

    public static void set(DMatrixRBlock A, double value) {
        CommonOps_DDRM.fill(A, value);
    }

    public static void setIdentity(DMatrixRBlock A) {
        int minLength = Math.min(A.numRows, A.numCols);
        CommonOps_DDRM.fill(A, 0.0);
        int blockLength = A.blockLength;
        for (int i = 0; i < minLength; i += blockLength) {
            int h = Math.min(blockLength, A.numRows - i);
            int w = Math.min(blockLength, A.numCols - i);
            int index = i * A.numCols + h * i;
            int m = Math.min(h, w);
            for (int k = 0; k < m; ++k) {
                A.data[index + k * w + k] = 1.0;
            }
        }
    }

    public static DMatrixRBlock identity(int numRows, int numCols, int blockLength) {
        DMatrixRBlock A = new DMatrixRBlock(numRows, numCols, blockLength);
        int minLength = Math.min(numRows, numCols);
        for (int i = 0; i < minLength; i += blockLength) {
            int h = Math.min(blockLength, A.numRows - i);
            int w = Math.min(blockLength, A.numCols - i);
            int index = i * A.numCols + h * i;
            int m = Math.min(h, w);
            for (int k = 0; k < m; ++k) {
                A.data[index + k * w + k] = 1.0;
            }
        }
        return A;
    }

    public static void checkIdenticalShape(DMatrixRBlock A, DMatrixRBlock B) {
        if (A.blockLength != B.blockLength) {
            throw new IllegalArgumentException("Block size is different");
        }
        if (A.numRows != B.numRows) {
            throw new IllegalArgumentException("Number of rows is different");
        }
        if (A.numCols != B.numCols) {
            throw new IllegalArgumentException("NUmber of columns is different");
        }
    }

    public static void extractAligned(DMatrixRBlock src, DMatrixRBlock dst) {
        if (src.blockLength != dst.blockLength) {
            throw new IllegalArgumentException("Block size is different");
        }
        if (src.numRows < dst.numRows) {
            throw new IllegalArgumentException("The src has fewer rows than dst");
        }
        if (src.numCols < dst.numCols) {
            throw new IllegalArgumentException("The src has fewer columns than dst");
        }
        int blockLength = src.blockLength;
        int numRows = Math.min(src.numRows, dst.numRows);
        int numCols = Math.min(src.numCols, dst.numCols);
        for (int i = 0; i < numRows; i += blockLength) {
            int heightSrc = Math.min(blockLength, src.numRows - i);
            int heightDst = Math.min(blockLength, dst.numRows - i);
            for (int j = 0; j < numCols; j += blockLength) {
                int widthSrc = Math.min(blockLength, src.numCols - j);
                int widthDst = Math.min(blockLength, dst.numCols - j);
                int indexSrc = i * src.numCols + heightSrc * j;
                int indexDst = i * dst.numCols + heightDst * j;
                for (int k = 0; k < heightDst; ++k) {
                    System.arraycopy(src.data, indexSrc + widthSrc * k, dst.data, indexDst + widthDst * k, widthDst);
                }
            }
        }
    }

    public static boolean blockAligned(int blockLength, DSubmatrixD1 A) {
        if (A.col0 % blockLength != 0) {
            return false;
        }
        if (A.row0 % blockLength != 0) {
            return false;
        }
        if (A.col1 % blockLength != 0 && A.col1 != ((DMatrixD1)A.original).numCols) {
            return false;
        }
        return A.row1 % blockLength == 0 || A.row1 == ((DMatrixD1)A.original).numRows;
    }

    static void checkShapeMult(int blockLength, DSubmatrixD1 A, DSubmatrixD1 B, DSubmatrixD1 C) {
        int Arow = A.getRows();
        int Acol = A.getCols();
        int Brow = B.getRows();
        int Bcol = B.getCols();
        int Crow = C.getRows();
        int Ccol = C.getCols();
        if (Arow != Crow) {
            throw new RuntimeException("Mismatch A and C rows");
        }
        if (Bcol != Ccol) {
            throw new RuntimeException("Mismatch B and C columns");
        }
        if (Acol != Brow) {
            throw new RuntimeException("Mismatch A columns and B rows");
        }
        if (!MatrixOps_DDRB.blockAligned(blockLength, A)) {
            throw new RuntimeException("Sub-Matrix A is not block aligned");
        }
        if (!MatrixOps_DDRB.blockAligned(blockLength, B)) {
            throw new RuntimeException("Sub-Matrix B is not block aligned");
        }
        if (!MatrixOps_DDRB.blockAligned(blockLength, C)) {
            throw new RuntimeException("Sub-Matrix C is not block aligned");
        }
    }
}

