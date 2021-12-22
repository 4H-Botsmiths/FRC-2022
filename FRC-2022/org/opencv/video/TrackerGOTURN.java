/*
 * Decompiled with CFR 0.150.
 */
package org.opencv.video;

import org.opencv.video.Tracker;
import org.opencv.video.TrackerGOTURN_Params;

public class TrackerGOTURN
extends Tracker {
    protected TrackerGOTURN(long addr) {
        super(addr);
    }

    public static TrackerGOTURN __fromPtr__(long addr) {
        return new TrackerGOTURN(addr);
    }

    public static TrackerGOTURN create(TrackerGOTURN_Params parameters) {
        return TrackerGOTURN.__fromPtr__(TrackerGOTURN.create_0(parameters.nativeObj));
    }

    public static TrackerGOTURN create() {
        return TrackerGOTURN.__fromPtr__(TrackerGOTURN.create_1());
    }

    @Override
    protected void finalize() throws Throwable {
        TrackerGOTURN.delete(this.nativeObj);
    }

    private static native long create_0(long var0);

    private static native long create_1();

    private static native void delete(long var0);
}

