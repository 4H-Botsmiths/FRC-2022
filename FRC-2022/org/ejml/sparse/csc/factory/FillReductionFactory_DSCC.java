/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  org.jetbrains.annotations.Nullable
 */
package org.ejml.sparse.csc.factory;

import java.util.Random;
import org.ejml.UtilEjml;
import org.ejml.data.DMatrixSparseCSC;
import org.ejml.data.IGrowArray;
import org.ejml.sparse.ComputePermutation;
import org.ejml.sparse.FillReducing;
import org.jetbrains.annotations.Nullable;

public class FillReductionFactory_DSCC {
    public static final Random rand = new Random(234234L);

    @Nullable
    public static ComputePermutation<DMatrixSparseCSC> create(FillReducing type) {
        switch (type) {
            case NONE: {
                return null;
            }
            case RANDOM: {
                return new ComputePermutation<DMatrixSparseCSC>(true, true){

                    /*
                     * WARNING - Removed try catching itself - possible behaviour change.
                     */
                    @Override
                    public void process(DMatrixSparseCSC m) {
                        Random _rand;
                        this.prow.reshape(m.numRows);
                        this.pcol.reshape(m.numCols);
                        FillReductionFactory_DSCC.fillSequence(this.prow);
                        FillReductionFactory_DSCC.fillSequence(this.pcol);
                        Random random = rand;
                        synchronized (random) {
                            _rand = new Random(rand.nextInt());
                        }
                        UtilEjml.shuffle(this.prow.data, this.prow.length, 0, this.prow.length, _rand);
                        UtilEjml.shuffle(this.pcol.data, this.pcol.length, 0, this.pcol.length, _rand);
                    }
                };
            }
            case IDENTITY: {
                return new ComputePermutation<DMatrixSparseCSC>(true, true){

                    @Override
                    public void process(DMatrixSparseCSC m) {
                        this.prow.reshape(m.numRows);
                        this.pcol.reshape(m.numCols);
                        FillReductionFactory_DSCC.fillSequence(this.prow);
                        FillReductionFactory_DSCC.fillSequence(this.pcol);
                    }
                };
            }
        }
        throw new RuntimeException("Unknown " + (Object)((Object)type));
    }

    private static void fillSequence(IGrowArray perm) {
        for (int i = 0; i < perm.length; ++i) {
            perm.data[i] = i;
        }
    }
}

