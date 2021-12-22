/*
 * Decompiled with CFR 0.150.
 */
package org.opencv.objdetect;

import org.opencv.core.MatOfInt;
import org.opencv.core.MatOfRect;

public class Objdetect {
    public static final int CASCADE_DO_CANNY_PRUNING = 1;
    public static final int CASCADE_SCALE_IMAGE = 2;
    public static final int CASCADE_FIND_BIGGEST_OBJECT = 4;
    public static final int CASCADE_DO_ROUGH_SEARCH = 8;
    public static final int DetectionBasedTracker_DETECTED_NOT_SHOWN_YET = 0;
    public static final int DetectionBasedTracker_DETECTED = 1;
    public static final int DetectionBasedTracker_DETECTED_TEMPORARY_LOST = 2;
    public static final int DetectionBasedTracker_WRONG_OBJECT = 3;

    public static void groupRectangles(MatOfRect rectList, MatOfInt weights, int groupThreshold, double eps) {
        MatOfRect rectList_mat = rectList;
        MatOfInt weights_mat = weights;
        Objdetect.groupRectangles_0(rectList_mat.nativeObj, weights_mat.nativeObj, groupThreshold, eps);
    }

    public static void groupRectangles(MatOfRect rectList, MatOfInt weights, int groupThreshold) {
        MatOfRect rectList_mat = rectList;
        MatOfInt weights_mat = weights;
        Objdetect.groupRectangles_1(rectList_mat.nativeObj, weights_mat.nativeObj, groupThreshold);
    }

    private static native void groupRectangles_0(long var0, long var2, int var4, double var5);

    private static native void groupRectangles_1(long var0, long var2, int var4);
}

