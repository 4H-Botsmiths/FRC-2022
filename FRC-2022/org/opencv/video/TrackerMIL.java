/*
 * Decompiled with CFR 0.150.
 */
package org.opencv.video;

import org.opencv.video.Tracker;
import org.opencv.video.TrackerMIL_Params;

public class TrackerMIL
extends Tracker {
    protected TrackerMIL(long addr) {
        super(addr);
    }

    public static TrackerMIL __fromPtr__(long addr) {
        return new TrackerMIL(addr);
    }

    public static TrackerMIL create(TrackerMIL_Params parameters) {
        return TrackerMIL.__fromPtr__(TrackerMIL.create_0(parameters.nativeObj));
    }

    public static TrackerMIL create() {
        return TrackerMIL.__fromPtr__(TrackerMIL.create_1());
    }

    @Override
    protected void finalize() throws Throwable {
        TrackerMIL.delete(this.nativeObj);
    }

    private static native long create_0(long var0);

    private static native long create_1();

    private static native void delete(long var0);
}

