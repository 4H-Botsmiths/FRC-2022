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
import org.ejml.data.FGrowArray;
import org.ejml.data.FMatrixD1;
import org.ejml.data.FMatrixRBlock;
import org.ejml.data.FMatrixRMaj;
import org.ejml.data.FSubmatrixD1;
import org.ejml.dense.block.MatrixMult_FDRB;
import org.ejml.dense.row.CommonOps_FDRM;
import org.ejml.dense.row.MatrixFeatures_FDRM;
import org.ejml.dense.row.RandomMatrices_FDRM;
import org.ejml.ops.FConvertMatrixStruct;
import org.jetbrains.annotations.Nullable;

public class MatrixOps_FDRB {
    public static void convert(FMatrixRMaj src, FMatrixRBlock dst) {
        FConvertMatrixStruct.convert(src, dst);
    }

    public static FMatrixRBlock convertInplace(FMatrixRMaj src, @Nullable FMatrixRBlock dst, @Nullable FGrowArray workspace) {
        if (dst == null) {
            dst = new FMatrixRBlock();
        }
        dst.data = src.data;
        dst.blockLength = EjmlParameters.BLOCK_WIDTH;
        dst.numRows = src.numRows;
        dst.numCols = src.numCols;
        MatrixOps_FDRB.convertRowToBlock(src.numRows, src.numCols, dst.blockLength, src.data, workspace);
        return dst;
    }

    public static void convertRowToBlock(int numRows, int numCols, int blockLength, float[] data, @Nullable FGrowArray workspace) {
        int minLength = Math.min(blockLength, numRows) * numCols;
        float[] tmp = UtilEjml.adjust(workspace, minLength);
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

    public static FMatrixRMaj convert(FMatrixRBlock src, FMatrixRMaj dst) {
        return FConvertMatrixStruct.convert(src, dst);
    }

    public static FMatrixRMaj convertInplace(FMatrixRBlock src, @Nullable FMatrixRMaj dst, @Nullable FGrowArray workspace) {
        if (dst == null) {
            dst = new FMatrixRMaj();
        }
        dst.data = src.data;
        dst.numRows = src.numRows;
        dst.numCols = src.numCols;
        MatrixOps_FDRB.convertBlockToRow(src.numRows, src.numCols, src.blockLength, src.data, workspace);
        return dst;
    }

    public static void convertBlockToRow(int numRows, int numCols, int blockLength, float[] data, @Nullable FGrowArray workspace) {
        int minLength = Math.min(blockLength, numRows) * numCols;
        float[] tmp = UtilEjml.adjust(workspace, minLength);
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

    public static void convertTranSrc(FMatrixRMaj src, FMatrixRBlock dst) {
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

    public static void mult(FMatrixRBlock A, FMatrixRBlock B, FMatrixRBlock C) {
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
        FSubmatrixD1 Asub = new FSubmatrixD1(A, 0, A.numRows, 0, A.numCols);
        FSubmatrixD1 Bsub = new FSubmatrixD1(B, 0, B.numRows, 0, B.numCols);
        FSubmatrixD1 Csub = new FSubmatrixD1(C, 0, C.numRows, 0, C.numCols);
        MatrixMult_FDRB.mult(blockLength, Asub, Bsub, Csub);
    }

    public static void multTransA(FMatrixRBlock A, FMatrixRBlock B, FMatrixRBlock C) {
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
        FSubmatrixD1 Asub = new FSubmatrixD1(A, 0, A.numRows, 0, A.numCols);
        FSubmatrixD1 Bsub = new FSubmatrixD1(B, 0, B.numRows, 0, B.numCols);
        FSubmatrixD1 Csub = new FSubmatrixD1(C, 0, C.numRows, 0, C.numCols);
        MatrixMult_FDRB.multTransA(blockLength, Asub, Bsub, Csub);
    }

    public static void multTransB(FMatrixRBlock A, FMatrixRBlock B, FMatrixRBlock C) {
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
        FSubmatrixD1 Asub = new FSubmatrixD1(A, 0, A.numRows, 0, A.numCols);
        FSubmatrixD1 Bsub = new FSubmatrixD1(B, 0, B.numRows, 0, B.numCols);
        FSubmatrixD1 Csub = new FSubmatrixD1(C, 0, C.numRows, 0, C.numCols);
        MatrixMult_FDRB.multTransB(blockLength, Asub, Bsub, Csub);
    }

    public static FMatrixRBlock transpose(FMatrixRBlock A, @Nullable FMatrixRBlock A_tran) {
        if (A_tran != null) {
            if (A.numRows != A_tran.numCols || A.numCols != A_tran.numRows) {
                throw new IllegalArgumentException("Incompatible dimensions.");
            }
            if (A.blockLength != A_tran.blockLength) {
                throw new IllegalArgumentException("Incompatible block size.");
            }
        } else {
            A_tran = new FMatrixRBlock(A.numCols, A.numRows, A.blockLength);
        }
        for (int i = 0; i < A.numRows; i += A.blockLength) {
            int blockHeight = Math.min(A.blockLength, A.numRows - i);
            for (int j = 0; j < A.numCols; j += A.blockLength) {
                int blockWidth = Math.min(A.blockLength, A.numCols - j);
                int indexA = i * A.numCols + blockHeight * j;
                int indexC = j * A_tran.numCols + blockWidth * i;
                MatrixOps_FDRB.transposeBlock(A, A_tran, indexA, indexC, blockWidth, blockHeight);
            }
        }
        return A_tran;
    }

    private static void transposeBlock(FMatrixRBlock A, FMatrixRBlock A_tran, int indexA, int indexC, int width, int height) {
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

    public static FMatrixRBlock createRandom(int numRows, int numCols, float min, float max, Random rand) {
        FMatrixRBlock ret = new FMatrixRBlock(numRows, numCols);
        RandomMatrices_FDRM.fillUniform(ret, min, max, rand);
        return ret;
    }

    public static FMatrixRBlock createRandom(int numRows, int numCols, float min, float max, Random rand, int blockLength) {
        FMatrixRBlock ret = new FMatrixRBlock(numRows, numCols, blockLength);
        RandomMatrices_FDRM.fillUniform(ret, min, max, rand);
        return ret;
    }

    public static FMatrixRBlock convert(FMatrixRMaj A, int blockLength) {
        FMatrixRBlock ret = new FMatrixRBlock(A.numRows, A.numCols, blockLength);
        MatrixOps_FDRB.convert(A, ret);
        return ret;
    }

    public static FMatrixRBlock convert(FMatrixRMaj A) {
        FMatrixRBlock ret = new FMatrixRBlock(A.numRows, A.numCols);
        MatrixOps_FDRB.convert(A, ret);
        return ret;
    }

    public static boolean isEquals(FMatrixRBlock A, FMatrixRBlock B) {
        if (A.blockLength != B.blockLength) {
            return false;
        }
        return MatrixFeatures_FDRM.isEquals(A, B);
    }

    public static boolean isEquals(FMatrixRBlock A, FMatrixRBlock B, float tol) {
        if (A.blockLength != B.blockLength) {
            return false;
        }
        return MatrixFeatures_FDRM.isEquals(A, B, tol);
    }

    public static void zeroTriangle(boolean upper, FMatrixRBlock A) {
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
                                A.data[index + w * k + l] = 0.0f;
                            }
                        }
                        continue;
                    }
                    for (k = 0; k < h; ++k) {
                        for (l = 0; l < w; ++l) {
                            A.data[index + w * k + l] = 0.0f;
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
                                A.data[index + w * k + l] = 0.0f;
                            }
                        }
                        continue;
                    }
                    for (k = 0; k < h; ++k) {
                        for (int l = 0; l < w; ++l) {
                            A.data[index + w * k + l] = 0.0f;
                        }
                    }
                }
            }
        }
    }

    public static void copyTriangle(boolean upper, FMatrixRBlock src, FMatrixRBlock dst) {
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

    public static void set(FMatrixRBlock A, float value) {
        CommonOps_FDRM.fill(A, value);
    }

    public static void setIdentity(FMatrixRBlock A) {
        int minLength = Math.min(A.numRows, A.numCols);
        CommonOps_FDRM.fill(A, 0.0f);
        int blockLength = A.blockLength;
        for (int i = 0; i < minLength; i += blockLength) {
            int h = Math.min(blockLength, A.numRows - i);
            int w = Math.min(blockLength, A.numCols - i);
            int index = i * A.numCols + h * i;
            int m = Math.min(h, w);
            for (int k = 0; k < m; ++k) {
                A.data[index + k * w + k] = 1.0f;
            }
        }
    }

    public static FMatrixRBlock identity(int numRows, int numCols, int blockLength) {
        FMatrixRBlock A = new FMatrixRBlock(numRows, numCols, blockLength);
        int minLength = Math.min(numRows, numCols);
        for (int i = 0; i < minLength; i += blockLength) {
            int h = Math.min(blockLength, A.numRows - i);
            int w = Math.min(blockLength, A.numCols - i);
            int index = i * A.numCols + h * i;
            int m = Math.min(h, w);
            for (int k = 0; k < m; ++k) {
                A.data[index + k * w + k] = 1.0f;
            }
        }
        return A;
    }

    public static void checkIdenticalShape(FMatrixRBlock A, FMatrixRBlock B) {
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

    public static void extractAligned(FMatrixRBlock src, FMatrixRBlock dst) {
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

    public static boolean blockAligned(int blockLength, FSubmatrixD1 A) {
        if (A.col0 % blockLength != 0) {
            return false;
        }
        if (A.row0 % blockLength != 0) {
            return false;
        }
        if (A.col1 % blockLength != 0 && A.col1 != ((FMatrixD1)A.original).numCols) {
            return false;
        }
        return A.row1 % blockLength == 0 || A.row1 == ((FMatrixD1)A.original).numRows;
    }

    static void checkShapeMult(int blockLength, FSubmatrixD1 A, FSubmatrixD1 B, FSubmatrixD1 C) {
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
        if (!MatrixOps_FDRB.blockAligned(blockLength, A)) {
            throw new RuntimeException("Sub-Matrix A is not block aligned");
        }
        if (!MatrixOps_FDRB.blockAligned(blockLength, B)) {
            throw new RuntimeException("Sub-Matrix B is not block aligned");
        }
        if (!MatrixOps_FDRB.blockAligned(blockLength, C)) {
            throw new RuntimeException("Sub-Matrix C is not block aligned");
        }
    }
}

