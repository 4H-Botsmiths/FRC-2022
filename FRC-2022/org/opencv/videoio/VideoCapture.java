/*
 * Decompiled with CFR 0.150.
 */
package org.opencv.videoio;

import org.opencv.core.Mat;
import org.opencv.core.MatOfInt;

public class VideoCapture {
    protected final long nativeObj;

    protected VideoCapture(long addr) {
        this.nativeObj = addr;
    }

    public long getNativeObjAddr() {
        return this.nativeObj;
    }

    public static VideoCapture __fromPtr__(long addr) {
        return new VideoCapture(addr);
    }

    public VideoCapture() {
        this.nativeObj = VideoCapture.VideoCapture_0();
    }

    public VideoCapture(String filename, int apiPreference) {
        this.nativeObj = VideoCapture.VideoCapture_1(filename, apiPreference);
    }

    public VideoCapture(String filename) {
        this.nativeObj = VideoCapture.VideoCapture_2(filename);
    }

    public VideoCapture(String filename, int apiPreference, MatOfInt params) {
        MatOfInt params_mat = params;
        this.nativeObj = VideoCapture.VideoCapture_3(filename, apiPreference, params_mat.nativeObj);
    }

    public VideoCapture(int index, int apiPreference) {
        this.nativeObj = VideoCapture.VideoCapture_4(index, apiPreference);
    }

    public VideoCapture(int index) {
        this.nativeObj = VideoCapture.VideoCapture_5(index);
    }

    public VideoCapture(int index, int apiPreference, MatOfInt params) {
        MatOfInt params_mat = params;
        this.nativeObj = VideoCapture.VideoCapture_6(index, apiPreference, params_mat.nativeObj);
    }

    public boolean open(String filename, int apiPreference) {
        return VideoCapture.open_0(this.nativeObj, filename, apiPreference);
    }

    public boolean open(String filename) {
        return VideoCapture.open_1(this.nativeObj, filename);
    }

    public boolean open(String filename, int apiPreference, MatOfInt params) {
        MatOfInt params_mat = params;
        return VideoCapture.open_2(this.nativeObj, filename, apiPreference, params_mat.nativeObj);
    }

    public boolean open(int index, int apiPreference) {
        return VideoCapture.open_3(this.nativeObj, index, apiPreference);
    }

    public boolean open(int index) {
        return VideoCapture.open_4(this.nativeObj, index);
    }

    public boolean open(int index, int apiPreference, MatOfInt params) {
        MatOfInt params_mat = params;
        return VideoCapture.open_5(this.nativeObj, index, apiPreference, params_mat.nativeObj);
    }

    public boolean isOpened() {
        return VideoCapture.isOpened_0(this.nativeObj);
    }

    public void release() {
        VideoCapture.release_0(this.nativeObj);
    }

    public boolean grab() {
        return VideoCapture.grab_0(this.nativeObj);
    }

    public boolean retrieve(Mat image, int flag) {
        return VideoCapture.retrieve_0(this.nativeObj, image.nativeObj, flag);
    }

    public boolean retrieve(Mat image) {
        return VideoCapture.retrieve_1(this.nativeObj, image.nativeObj);
    }

    public boolean read(Mat image) {
        return VideoCapture.read_0(this.nativeObj, image.nativeObj);
    }

    public boolean set(int propId, double value) {
        return VideoCapture.set_0(this.nativeObj, propId, value);
    }

    public double get(int propId) {
        return VideoCapture.get_0(this.nativeObj, propId);
    }

    public String getBackendName() {
        return VideoCapture.getBackendName_0(this.nativeObj);
    }

    public void setExceptionMode(boolean enable) {
        VideoCapture.setExceptionMode_0(this.nativeObj, enable);
    }

    public boolean getExceptionMode() {
        return VideoCapture.getExceptionMode_0(this.nativeObj);
    }

    protected void finalize() throws Throwable {
        VideoCapture.delete(this.nativeObj);
    }

    private static native long VideoCapture_0();

    private static native long VideoCapture_1(String var0, int var1);

    private static native long VideoCapture_2(String var0);

    private static native long VideoCapture_3(String var0, int var1, long var2);

    private static native long VideoCapture_4(int var0, int var1);

    private static native long VideoCapture_5(int var0);

    private static native long VideoCapture_6(int var0, int var1, long var2);

    private static native boolean open_0(long var0, String var2, int var3);

    private static native boolean open_1(long var0, String var2);

    private static native boolean open_2(long var0, String var2, int var3, long var4);

    private static native boolean open_3(long var0, int var2, int var3);

    private static native boolean open_4(long var0, int var2);

    private static native boolean open_5(long var0, int var2, int var3, long var4);

    private static native boolean isOpened_0(long var0);

    private static native void release_0(long var0);

    private static native boolean grab_0(long var0);

    private static native boolean retrieve_0(long var0, long var2, int var4);

    private static native boolean retrieve_1(long var0, long var2);

    private static native boolean read_0(long var0, long var2);

    private static native boolean set_0(long var0, int var2, double var3);

    private static native double get_0(long var0, int var2);

    private static native String getBackendName_0(long var0);

    private static native void setExceptionMode_0(long var0, boolean var2);

    private static native boolean getExceptionMode_0(long var0);

    private static native void delete(long var0);
}

