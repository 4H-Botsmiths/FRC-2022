/*
 * Decompiled with CFR 0.150.
 */
package org.opencv.video;

import org.opencv.core.Mat;
import org.opencv.core.Rect;

public class Tracker {
    protected final long nativeObj;

    protected Tracker(long addr) {
        this.nativeObj = addr;
    }

    public long getNativeObjAddr() {
        return this.nativeObj;
    }

    public static Tracker __fromPtr__(long addr) {
        return new Tracker(addr);
    }

    public void init(Mat image, Rect boundingBox) {
        Tracker.init_0(this.nativeObj, image.nativeObj, boundingBox.x, boundingBox.y, boundingBox.width, boundingBox.height);
    }

    public boolean update(Mat image, Rect boundingBox) {
        double[] boundingBox_out = new double[4];
        boolean retVal = Tracker.update_0(this.nativeObj, image.nativeObj, boundingBox_out);
        if (boundingBox != null) {
            boundingBox.x = (int)boundingBox_out[0];
            boundingBox.y = (int)boundingBox_out[1];
            boundingBox.width = (int)boundingBox_out[2];
            boundingBox.height = (int)boundingBox_out[3];
        }
        return retVal;
    }

    protected void finalize() throws Throwable {
        Tracker.delete(this.nativeObj);
    }

    private static native void init_0(long var0, long var2, int var4, int var5, int var6, int var7);

    private static native boolean update_0(long var0, long var2, double[] var4);

    private static native void delete(long var0);
}

