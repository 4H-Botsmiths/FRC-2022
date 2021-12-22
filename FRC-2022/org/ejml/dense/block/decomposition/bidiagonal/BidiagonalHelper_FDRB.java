/*
 * Decompiled with CFR 0.150.
 */
package org.ejml.dense.block.decomposition.bidiagonal;

import org.ejml.data.FMatrixD1;
import org.ejml.data.FSubmatrixD1;
import org.ejml.dense.block.decomposition.qr.BlockHouseHolder_FDRB;

public class BidiagonalHelper_FDRB {
    public static boolean bidiagOuterBlocks(int blockLength, FSubmatrixD1 A, float[] gammasU, float[] gammasV) {
        int width = Math.min(blockLength, A.col1 - A.col0);
        int height = Math.min(blockLength, A.row1 - A.row0);
        int min = Math.min(width, height);
        for (int i = 0; i < min; ++i) {
            if (!BlockHouseHolder_FDRB.computeHouseHolderCol(blockLength, A, gammasU, i)) {
                return false;
            }
            BlockHouseHolder_FDRB.rank1UpdateMultR_Col(blockLength, A, i, gammasU[A.col0 + i]);
            BlockHouseHolder_FDRB.rank1UpdateMultR_TopRow(blockLength, A, i, gammasU[A.col0 + i]);
            System.out.println("After column stuff");
            ((FMatrixD1)A.original).print();
            if (!BlockHouseHolder_FDRB.computeHouseHolderRow(blockLength, A, gammasV, i)) {
                return false;
            }
            BlockHouseHolder_FDRB.rank1UpdateMultL_Row(blockLength, A, i, i + 1, gammasV[A.row0 + i]);
            System.out.println("After update row");
            ((FMatrixD1)A.original).print();
            System.out.println("After row stuff");
            ((FMatrixD1)A.original).print();
        }
        return true;
    }
}

