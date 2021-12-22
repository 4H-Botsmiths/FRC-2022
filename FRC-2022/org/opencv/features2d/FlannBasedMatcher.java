/*
 * Decompiled with CFR 0.150.
 */
package org.opencv.features2d;

import org.opencv.features2d.DescriptorMatcher;

public class FlannBasedMatcher
extends DescriptorMatcher {
    protected FlannBasedMatcher(long addr) {
        super(addr);
    }

    public static FlannBasedMatcher __fromPtr__(long addr) {
        return new FlannBasedMatcher(addr);
    }

    public FlannBasedMatcher() {
        super(FlannBasedMatcher.FlannBasedMatcher_0());
    }

    public static FlannBasedMatcher create() {
        return FlannBasedMatcher.__fromPtr__(FlannBasedMatcher.create_0());
    }

    @Override
    protected void finalize() throws Throwable {
        FlannBasedMatcher.delete(this.nativeObj);
    }

    private static native long FlannBasedMatcher_0();

    private static native long create_0();

    private static native void delete(long var0);
}

