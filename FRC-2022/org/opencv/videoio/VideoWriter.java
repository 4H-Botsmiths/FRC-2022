/*
 * Decompiled with CFR 0.150.
 */
package org.opencv.videoio;

import org.opencv.core.Mat;
import org.opencv.core.MatOfInt;
import org.opencv.core.Size;

public class VideoWriter {
    protected final long nativeObj;

    protected VideoWriter(long addr) {
        this.nativeObj = addr;
    }

    public long getNativeObjAddr() {
        return this.nativeObj;
    }

    public static VideoWriter __fromPtr__(long addr) {
        return new VideoWriter(addr);
    }

    public VideoWriter() {
        this.nativeObj = VideoWriter.VideoWriter_0();
    }

    public VideoWriter(String filename, int fourcc, double fps, Size frameSize, boolean isColor) {
        this.nativeObj = VideoWriter.VideoWriter_1(filename, fourcc, fps, frameSize.width, frameSize.height, isColor);
    }

    public VideoWriter(String filename, int fourcc, double fps, Size frameSize) {
        this.nativeObj = VideoWriter.VideoWriter_2(filename, fourcc, fps, frameSize.width, frameSize.height);
    }

    public VideoWriter(String filename, int apiPreference, int fourcc, double fps, Size frameSize, boolean isColor) {
        this.nativeObj = VideoWriter.VideoWriter_3(filename, apiPreference, fourcc, fps, frameSize.width, frameSize.height, isColor);
    }

    public VideoWriter(String filename, int apiPreference, int fourcc, double fps, Size frameSize) {
        this.nativeObj = VideoWriter.VideoWriter_4(filename, apiPreference, fourcc, fps, frameSize.width, frameSize.height);
    }

    public VideoWriter(String filename, int fourcc, double fps, Size frameSize, MatOfInt params) {
        MatOfInt params_mat = params;
        this.nativeObj = VideoWriter.VideoWriter_5(filename, fourcc, fps, frameSize.width, frameSize.height, params_mat.nativeObj);
    }

    public VideoWriter(String filename, int apiPreference, int fourcc, double fps, Size frameSize, MatOfInt params) {
        MatOfInt params_mat = params;
        this.nativeObj = VideoWriter.VideoWriter_6(filename, apiPreference, fourcc, fps, frameSize.width, frameSize.height, params_mat.nativeObj);
    }

    public boolean open(String filename, int fourcc, double fps, Size frameSize, boolean isColor) {
        return VideoWriter.open_0(this.nativeObj, filename, fourcc, fps, frameSize.width, frameSize.height, isColor);
    }

    public boolean open(String filename, int fourcc, double fps, Size frameSize) {
        return VideoWriter.open_1(this.nativeObj, filename, fourcc, fps, frameSize.width, frameSize.height);
    }

    public boolean open(String filename, int apiPreference, int fourcc, double fps, Size frameSize, boolean isColor) {
        return VideoWriter.open_2(this.nativeObj, filename, apiPreference, fourcc, fps, frameSize.width, frameSize.height, isColor);
    }

    public boolean open(String filename, int apiPreference, int fourcc, double fps, Size frameSize) {
        return VideoWriter.open_3(this.nativeObj, filename, apiPreference, fourcc, fps, frameSize.width, frameSize.height);
    }

    public boolean open(String filename, int fourcc, double fps, Size frameSize, MatOfInt params) {
        MatOfInt params_mat = params;
        return VideoWriter.open_4(this.nativeObj, filename, fourcc, fps, frameSize.width, frameSize.height, params_mat.nativeObj);
    }

    public boolean open(String filename, int apiPreference, int fourcc, double fps, Size frameSize, MatOfInt params) {
        MatOfInt params_mat = params;
        return VideoWriter.open_5(this.nativeObj, filename, apiPreference, fourcc, fps, frameSize.width, frameSize.height, params_mat.nativeObj);
    }

    public boolean isOpened() {
        return VideoWriter.isOpened_0(this.nativeObj);
    }

    public void release() {
        VideoWriter.release_0(this.nativeObj);
    }

    public void write(Mat image) {
        VideoWriter.write_0(this.nativeObj, image.nativeObj);
    }

    public boolean set(int propId, double value) {
        return VideoWriter.set_0(this.nativeObj, propId, value);
    }

    public double get(int propId) {
        return VideoWriter.get_0(this.nativeObj, propId);
    }

    public static int fourcc(char c1, char c2, char c3, char c4) {
        return VideoWriter.fourcc_0(c1, c2, c3, c4);
    }

    public String getBackendName() {
        return VideoWriter.getBackendName_0(this.nativeObj);
    }

    protected void finalize() throws Throwable {
        VideoWriter.delete(this.nativeObj);
    }

    private static native long VideoWriter_0();

    private static native long VideoWriter_1(String var0, int var1, double var2, double var4, double var6, boolean var8);

    private static native long VideoWriter_2(String var0, int var1, double var2, double var4, double var6);

    private static native long VideoWriter_3(String var0, int var1, int var2, double var3, double var5, double var7, boolean var9);

    private static native long VideoWriter_4(String var0, int var1, int var2, double var3, double var5, double var7);

    private static native long VideoWriter_5(String var0, int var1, double var2, double var4, double var6, long var8);

    private static native long VideoWriter_6(String var0, int var1, int var2, double var3, double var5, double var7, long var9);

    private static native boolean open_0(long var0, String var2, int var3, double var4, double var6, double var8, boolean var10);

    private static native boolean open_1(long var0, String var2, int var3, double var4, double var6, double var8);

    private static native boolean open_2(long var0, String var2, int var3, int var4, double var5, double var7, double var9, boolean var11);

    private static native boolean open_3(long var0, String var2, int var3, int var4, double var5, double var7, double var9);

    private static native boolean open_4(long var0, String var2, int var3, double var4, double var6, double var8, long var10);

    private static native boolean open_5(long var0, String var2, int var3, int var4, double var5, double var7, double var9, long var11);

    private static native boolean isOpened_0(long var0);

    private static native void release_0(long var0);

    private static native void write_0(long var0, long var2);

    private static native boolean set_0(long var0, int var2, double var3);

    private static native double get_0(long var0, int var2);

    private static native int fourcc_0(char var0, char var1, char var2, char var3);

    private static native String getBackendName_0(long var0);

    private static native void delete(long var0);
}

