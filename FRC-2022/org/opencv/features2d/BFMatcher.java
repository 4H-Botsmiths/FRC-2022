/*
 * Decompiled with CFR 0.150.
 */
package org.opencv.features2d;

import org.opencv.features2d.DescriptorMatcher;

public class BFMatcher
extends DescriptorMatcher {
    protected BFMatcher(long addr) {
        super(addr);
    }

    public static BFMatcher __fromPtr__(long addr) {
        return new BFMatcher(addr);
    }

    public BFMatcher(int normType, boolean crossCheck) {
        super(BFMatcher.BFMatcher_0(normType, crossCheck));
    }

    public BFMatcher(int normType) {
        super(BFMatcher.BFMatcher_1(normType));
    }

    public BFMatcher() {
        super(BFMatcher.BFMatcher_2());
    }

    public static BFMatcher create(int normType, boolean crossCheck) {
        return BFMatcher.__fromPtr__(BFMatcher.create_0(normType, crossCheck));
    }

    public static BFMatcher create(int normType) {
        return BFMatcher.__fromPtr__(BFMatcher.create_1(normType));
    }

    public static BFMatcher create() {
        return BFMatcher.__fromPtr__(BFMatcher.create_2());
    }

    @Override
    protected void finalize() throws Throwable {
        BFMatcher.delete(this.nativeObj);
    }

    private static native long BFMatcher_0(int var0, boolean var1);

    private static native long BFMatcher_1(int var0);

    private static native long BFMatcher_2();

    private static native long create_0(int var0, boolean var1);

    private static native long create_1(int var0);

    private static native long create_2();

    private static native void delete(long var0);
}

