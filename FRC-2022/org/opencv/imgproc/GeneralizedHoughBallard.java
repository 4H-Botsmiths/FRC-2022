/*
 * Decompiled with CFR 0.150.
 */
package org.opencv.imgproc;

import org.opencv.imgproc.GeneralizedHough;

public class GeneralizedHoughBallard
extends GeneralizedHough {
    protected GeneralizedHoughBallard(long addr) {
        super(addr);
    }

    public static GeneralizedHoughBallard __fromPtr__(long addr) {
        return new GeneralizedHoughBallard(addr);
    }

    public void setLevels(int levels) {
        GeneralizedHoughBallard.setLevels_0(this.nativeObj, levels);
    }

    public int getLevels() {
        return GeneralizedHoughBallard.getLevels_0(this.nativeObj);
    }

    public void setVotesThreshold(int votesThreshold) {
        GeneralizedHoughBallard.setVotesThreshold_0(this.nativeObj, votesThreshold);
    }

    public int getVotesThreshold() {
        return GeneralizedHoughBallard.getVotesThreshold_0(this.nativeObj);
    }

    @Override
    protected void finalize() throws Throwable {
        GeneralizedHoughBallard.delete(this.nativeObj);
    }

    private static native void setLevels_0(long var0, int var2);

    private static native int getLevels_0(long var0);

    private static native void setVotesThreshold_0(long var0, int var2);

    private static native int getVotesThreshold_0(long var0);

    private static native void delete(long var0);
}

