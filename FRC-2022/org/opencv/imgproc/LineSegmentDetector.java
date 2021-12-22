/*
 * Decompiled with CFR 0.150.
 */
package org.opencv.imgproc;

import org.opencv.core.Algorithm;
import org.opencv.core.Mat;
import org.opencv.core.Size;

public class LineSegmentDetector
extends Algorithm {
    protected LineSegmentDetector(long addr) {
        super(addr);
    }

    public static LineSegmentDetector __fromPtr__(long addr) {
        return new LineSegmentDetector(addr);
    }

    public void detect(Mat _image, Mat _lines, Mat width, Mat prec, Mat nfa) {
        LineSegmentDetector.detect_0(this.nativeObj, _image.nativeObj, _lines.nativeObj, width.nativeObj, prec.nativeObj, nfa.nativeObj);
    }

    public void detect(Mat _image, Mat _lines, Mat width, Mat prec) {
        LineSegmentDetector.detect_1(this.nativeObj, _image.nativeObj, _lines.nativeObj, width.nativeObj, prec.nativeObj);
    }

    public void detect(Mat _image, Mat _lines, Mat width) {
        LineSegmentDetector.detect_2(this.nativeObj, _image.nativeObj, _lines.nativeObj, width.nativeObj);
    }

    public void detect(Mat _image, Mat _lines) {
        LineSegmentDetector.detect_3(this.nativeObj, _image.nativeObj, _lines.nativeObj);
    }

    public void drawSegments(Mat _image, Mat lines) {
        LineSegmentDetector.drawSegments_0(this.nativeObj, _image.nativeObj, lines.nativeObj);
    }

    public int compareSegments(Size size, Mat lines1, Mat lines2, Mat _image) {
        return LineSegmentDetector.compareSegments_0(this.nativeObj, size.width, size.height, lines1.nativeObj, lines2.nativeObj, _image.nativeObj);
    }

    public int compareSegments(Size size, Mat lines1, Mat lines2) {
        return LineSegmentDetector.compareSegments_1(this.nativeObj, size.width, size.height, lines1.nativeObj, lines2.nativeObj);
    }

    @Override
    protected void finalize() throws Throwable {
        LineSegmentDetector.delete(this.nativeObj);
    }

    private static native void detect_0(long var0, long var2, long var4, long var6, long var8, long var10);

    private static native void detect_1(long var0, long var2, long var4, long var6, long var8);

    private static native void detect_2(long var0, long var2, long var4, long var6);

    private static native void detect_3(long var0, long var2, long var4);

    private static native void drawSegments_0(long var0, long var2, long var4);

    private static native int compareSegments_0(long var0, double var2, double var4, long var6, long var8, long var10);

    private static native int compareSegments_1(long var0, double var2, double var4, long var6, long var8);

    private static native void delete(long var0);
}

