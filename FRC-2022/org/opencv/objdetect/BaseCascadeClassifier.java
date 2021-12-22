/*
 * Decompiled with CFR 0.150.
 */
package org.opencv.objdetect;

import org.opencv.core.Algorithm;

public class BaseCascadeClassifier
extends Algorithm {
    protected BaseCascadeClassifier(long addr) {
        super(addr);
    }

    public static BaseCascadeClassifier __fromPtr__(long addr) {
        return new BaseCascadeClassifier(addr);
    }

    @Override
    protected void finalize() throws Throwable {
        BaseCascadeClassifier.delete(this.nativeObj);
    }

    private static native void delete(long var0);
}

