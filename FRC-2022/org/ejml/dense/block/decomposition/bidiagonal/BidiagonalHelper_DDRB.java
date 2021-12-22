/*
 * Decompiled with CFR 0.150.
 */
package org.ejml.dense.block.decomposition.bidiagonal;

import org.ejml.data.DMatrixD1;
import org.ejml.data.DSubmatrixD1;
import org.ejml.dense.block.decomposition.qr.BlockHouseHolder_DDRB;

public class BidiagonalHelper_DDRB {
    public static boolean bidiagOuterBlocks(int blockLength, DSubmatrixD1 A, double[] gammasU, double[] gammasV) {
        int width = Math.min(blockLength, A.col1 - A.col0);
        int height = Math.min(blockLength, A.row1 - A.row0);
        int min = Math.min(width, height);
        for (int i = 0; i < min; ++i) {
            if (!BlockHouseHolder_DDRB.computeHouseHolderCol(blockLength, A, gammasU, i)) {
                return false;
            }
            BlockHouseHolder_DDRB.rank1UpdateMultR_Col(blockLength, A, i, gammasU[A.col0 + i]);
            BlockHouseHolder_DDRB.rank1UpdateMultR_TopRow(blockLength, A, i, gammasU[A.col0 + i]);
            System.out.println("After column stuff");
            ((DMatrixD1)A.original).print();
            if (!BlockHouseHolder_DDRB.computeHouseHolderRow(blockLength, A, gammasV, i)) {
                return false;
            }
            BlockHouseHolder_DDRB.rank1UpdateMultL_Row(blockLength, A, i, i + 1, gammasV[A.row0 + i]);
            System.out.println("After update row");
            ((DMatrixD1)A.original).print();
            System.out.println("After row stuff");
            ((DMatrixD1)A.original).print();
        }
        return true;
    }
}

