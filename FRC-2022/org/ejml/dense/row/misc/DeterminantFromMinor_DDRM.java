/*
 * Decompiled with CFR 0.150.
 */
package org.ejml.dense.row.misc;

import org.ejml.data.DMatrix1Row;
import org.ejml.data.DMatrixRMaj;
import org.ejml.dense.row.misc.UnrolledDeterminantFromMinor_DDRM;

public class DeterminantFromMinor_DDRM {
    private final int width;
    private final int minWidth;
    private final int[] levelIndexes;
    private final double[] levelResults;
    private final int[] levelRemoved;
    private final int[] open;
    private int numOpen;
    private final DMatrixRMaj tempMat;
    private boolean dirty = false;

    public DeterminantFromMinor_DDRM(int width) {
        this(width, 5);
    }

    public DeterminantFromMinor_DDRM(int width, int minWidth) {
        if (minWidth > 5 || minWidth < 2) {
            throw new IllegalArgumentException("No direct function for that width");
        }
        if (width < minWidth) {
            minWidth = width;
        }
        this.minWidth = minWidth;
        this.width = width;
        int numLevels = width - (minWidth - 2);
        this.levelResults = new double[numLevels];
        this.levelRemoved = new int[numLevels];
        this.levelIndexes = new int[numLevels];
        this.open = new int[width];
        this.tempMat = new DMatrixRMaj(minWidth - 1, minWidth - 1);
    }

    public double compute(DMatrix1Row mat) {
        if (this.width != mat.numCols || this.width != mat.numRows) {
            throw new RuntimeException("Unexpected matrix dimension");
        }
        this.initStructures();
        int level = 0;
        while (true) {
            int excluded;
            int levelWidth;
            int levelIndex;
            if ((levelIndex = this.levelIndexes[level]) == (levelWidth = this.width - level)) {
                int prevLevelIndex;
                if (level == 0) {
                    return this.levelResults[0];
                }
                int n = level - 1;
                this.levelIndexes[n] = this.levelIndexes[n] + 1;
                double val = mat.get((level - 1) * this.width + this.levelRemoved[level - 1]);
                if (prevLevelIndex % 2 == 0) {
                    int n2 = level - 1;
                    this.levelResults[n2] = this.levelResults[n2] + val * this.levelResults[level];
                } else {
                    int n3 = level - 1;
                    this.levelResults[n3] = this.levelResults[n3] - val * this.levelResults[level];
                }
                this.putIntoOpen(level - 1);
                this.levelResults[level] = 0.0;
                this.levelIndexes[level] = 0;
                --level;
                continue;
            }
            this.levelRemoved[level] = excluded = this.openRemove(levelIndex);
            if (levelWidth == this.minWidth) {
                this.createMinor(mat);
                double subresult = mat.get(level * this.width + this.levelRemoved[level]);
                subresult *= UnrolledDeterminantFromMinor_DDRM.det(this.tempMat);
                if (levelIndex % 2 == 0) {
                    int n = level;
                    this.levelResults[n] = this.levelResults[n] + subresult;
                } else {
                    int n = level;
                    this.levelResults[n] = this.levelResults[n] - subresult;
                }
                this.putIntoOpen(level);
                int n = level;
                this.levelIndexes[n] = this.levelIndexes[n] + 1;
                continue;
            }
            ++level;
        }
    }

    private void initStructures() {
        int i;
        for (i = 0; i < this.width; ++i) {
            this.open[i] = i;
        }
        this.numOpen = this.width;
        if (this.dirty) {
            for (i = 0; i < this.levelIndexes.length; ++i) {
                this.levelIndexes[i] = 0;
                this.levelResults[i] = 0.0;
                this.levelRemoved[i] = 0;
            }
        }
        this.dirty = true;
    }

    private int openRemove(int where) {
        int val = this.open[where];
        System.arraycopy(this.open, where + 1, this.open, where, this.numOpen - where - 1);
        --this.numOpen;
        return val;
    }

    private void openAdd(int where, int val) {
        for (int i = this.numOpen; i > where; --i) {
            this.open[i] = this.open[i - 1];
        }
        ++this.numOpen;
        this.open[where] = val;
    }

    private void openAdd(int val) {
        this.open[this.numOpen++] = val;
    }

    private void putIntoOpen(int level) {
        boolean added = false;
        for (int i = 0; i < this.numOpen; ++i) {
            if (this.open[i] <= this.levelRemoved[level]) continue;
            added = true;
            this.openAdd(i, this.levelRemoved[level]);
            break;
        }
        if (!added) {
            this.openAdd(this.levelRemoved[level]);
        }
    }

    private void createMinor(DMatrix1Row mat) {
        int w = this.minWidth - 1;
        int firstRow = (this.width - w) * this.width;
        for (int i = 0; i < this.numOpen; ++i) {
            int col = this.open[i];
            int srcIndex = firstRow + col;
            int dstIndex = i;
            for (int j = 0; j < w; ++j) {
                this.tempMat.set(dstIndex, mat.get(srcIndex));
                dstIndex += w;
                srcIndex += this.width;
            }
        }
    }
}

