/*
 * Decompiled with CFR 0.150.
 */
package org.opencv.features2d;

import org.opencv.features2d.Feature2D;
import org.opencv.features2d.SimpleBlobDetector_Params;

public class SimpleBlobDetector
extends Feature2D {
    protected SimpleBlobDetector(long addr) {
        super(addr);
    }

    public static SimpleBlobDetector __fromPtr__(long addr) {
        return new SimpleBlobDetector(addr);
    }

    public static SimpleBlobDetector create(SimpleBlobDetector_Params parameters) {
        return SimpleBlobDetector.__fromPtr__(SimpleBlobDetector.create_0(parameters.nativeObj));
    }

    public static SimpleBlobDetector create() {
        return SimpleBlobDetector.__fromPtr__(SimpleBlobDetector.create_1());
    }

    @Override
    public String getDefaultName() {
        return SimpleBlobDetector.getDefaultName_0(this.nativeObj);
    }

    @Override
    protected void finalize() throws Throwable {
        SimpleBlobDetector.delete(this.nativeObj);
    }

    private static native long create_0(long var0);

    private static native long create_1();

    private static native String getDefaultName_0(long var0);

    private static native void delete(long var0);
}

