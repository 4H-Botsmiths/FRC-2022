/*
 * Decompiled with CFR 0.150.
 */
package org.opencv.objdetect;

import org.opencv.core.Mat;
import org.opencv.core.MatOfDouble;
import org.opencv.core.MatOfFloat;
import org.opencv.core.MatOfPoint;
import org.opencv.core.MatOfRect;
import org.opencv.core.Size;

public class HOGDescriptor {
    protected final long nativeObj;
    public static final int DEFAULT_NLEVELS = 64;
    public static final int DESCR_FORMAT_COL_BY_COL = 0;
    public static final int DESCR_FORMAT_ROW_BY_ROW = 1;
    public static final int L2Hys = 0;

    protected HOGDescriptor(long addr) {
        this.nativeObj = addr;
    }

    public long getNativeObjAddr() {
        return this.nativeObj;
    }

    public static HOGDescriptor __fromPtr__(long addr) {
        return new HOGDescriptor(addr);
    }

    public HOGDescriptor() {
        this.nativeObj = HOGDescriptor.HOGDescriptor_0();
    }

    public HOGDescriptor(Size _winSize, Size _blockSize, Size _blockStride, Size _cellSize, int _nbins, int _derivAperture, double _winSigma, int _histogramNormType, double _L2HysThreshold, boolean _gammaCorrection, int _nlevels, boolean _signedGradient) {
        this.nativeObj = HOGDescriptor.HOGDescriptor_1(_winSize.width, _winSize.height, _blockSize.width, _blockSize.height, _blockStride.width, _blockStride.height, _cellSize.width, _cellSize.height, _nbins, _derivAperture, _winSigma, _histogramNormType, _L2HysThreshold, _gammaCorrection, _nlevels, _signedGradient);
    }

    public HOGDescriptor(Size _winSize, Size _blockSize, Size _blockStride, Size _cellSize, int _nbins, int _derivAperture, double _winSigma, int _histogramNormType, double _L2HysThreshold, boolean _gammaCorrection, int _nlevels) {
        this.nativeObj = HOGDescriptor.HOGDescriptor_2(_winSize.width, _winSize.height, _blockSize.width, _blockSize.height, _blockStride.width, _blockStride.height, _cellSize.width, _cellSize.height, _nbins, _derivAperture, _winSigma, _histogramNormType, _L2HysThreshold, _gammaCorrection, _nlevels);
    }

    public HOGDescriptor(Size _winSize, Size _blockSize, Size _blockStride, Size _cellSize, int _nbins, int _derivAperture, double _winSigma, int _histogramNormType, double _L2HysThreshold, boolean _gammaCorrection) {
        this.nativeObj = HOGDescriptor.HOGDescriptor_3(_winSize.width, _winSize.height, _blockSize.width, _blockSize.height, _blockStride.width, _blockStride.height, _cellSize.width, _cellSize.height, _nbins, _derivAperture, _winSigma, _histogramNormType, _L2HysThreshold, _gammaCorrection);
    }

    public HOGDescriptor(Size _winSize, Size _blockSize, Size _blockStride, Size _cellSize, int _nbins, int _derivAperture, double _winSigma, int _histogramNormType, double _L2HysThreshold) {
        this.nativeObj = HOGDescriptor.HOGDescriptor_4(_winSize.width, _winSize.height, _blockSize.width, _blockSize.height, _blockStride.width, _blockStride.height, _cellSize.width, _cellSize.height, _nbins, _derivAperture, _winSigma, _histogramNormType, _L2HysThreshold);
    }

    public HOGDescriptor(Size _winSize, Size _blockSize, Size _blockStride, Size _cellSize, int _nbins, int _derivAperture, double _winSigma, int _histogramNormType) {
        this.nativeObj = HOGDescriptor.HOGDescriptor_5(_winSize.width, _winSize.height, _blockSize.width, _blockSize.height, _blockStride.width, _blockStride.height, _cellSize.width, _cellSize.height, _nbins, _derivAperture, _winSigma, _histogramNormType);
    }

    public HOGDescriptor(Size _winSize, Size _blockSize, Size _blockStride, Size _cellSize, int _nbins, int _derivAperture, double _winSigma) {
        this.nativeObj = HOGDescriptor.HOGDescriptor_6(_winSize.width, _winSize.height, _blockSize.width, _blockSize.height, _blockStride.width, _blockStride.height, _cellSize.width, _cellSize.height, _nbins, _derivAperture, _winSigma);
    }

    public HOGDescriptor(Size _winSize, Size _blockSize, Size _blockStride, Size _cellSize, int _nbins, int _derivAperture) {
        this.nativeObj = HOGDescriptor.HOGDescriptor_7(_winSize.width, _winSize.height, _blockSize.width, _blockSize.height, _blockStride.width, _blockStride.height, _cellSize.width, _cellSize.height, _nbins, _derivAperture);
    }

    public HOGDescriptor(Size _winSize, Size _blockSize, Size _blockStride, Size _cellSize, int _nbins) {
        this.nativeObj = HOGDescriptor.HOGDescriptor_8(_winSize.width, _winSize.height, _blockSize.width, _blockSize.height, _blockStride.width, _blockStride.height, _cellSize.width, _cellSize.height, _nbins);
    }

    public HOGDescriptor(String filename) {
        this.nativeObj = HOGDescriptor.HOGDescriptor_9(filename);
    }

    public long getDescriptorSize() {
        return HOGDescriptor.getDescriptorSize_0(this.nativeObj);
    }

    public boolean checkDetectorSize() {
        return HOGDescriptor.checkDetectorSize_0(this.nativeObj);
    }

    public double getWinSigma() {
        return HOGDescriptor.getWinSigma_0(this.nativeObj);
    }

    public void setSVMDetector(Mat svmdetector) {
        HOGDescriptor.setSVMDetector_0(this.nativeObj, svmdetector.nativeObj);
    }

    public boolean load(String filename, String objname) {
        return HOGDescriptor.load_0(this.nativeObj, filename, objname);
    }

    public boolean load(String filename) {
        return HOGDescriptor.load_1(this.nativeObj, filename);
    }

    public void save(String filename, String objname) {
        HOGDescriptor.save_0(this.nativeObj, filename, objname);
    }

    public void save(String filename) {
        HOGDescriptor.save_1(this.nativeObj, filename);
    }

    public void compute(Mat img, MatOfFloat descriptors, Size winStride, Size padding, MatOfPoint locations) {
        MatOfFloat descriptors_mat = descriptors;
        MatOfPoint locations_mat = locations;
        HOGDescriptor.compute_0(this.nativeObj, img.nativeObj, descriptors_mat.nativeObj, winStride.width, winStride.height, padding.width, padding.height, locations_mat.nativeObj);
    }

    public void compute(Mat img, MatOfFloat descriptors, Size winStride, Size padding) {
        MatOfFloat descriptors_mat = descriptors;
        HOGDescriptor.compute_1(this.nativeObj, img.nativeObj, descriptors_mat.nativeObj, winStride.width, winStride.height, padding.width, padding.height);
    }

    public void compute(Mat img, MatOfFloat descriptors, Size winStride) {
        MatOfFloat descriptors_mat = descriptors;
        HOGDescriptor.compute_2(this.nativeObj, img.nativeObj, descriptors_mat.nativeObj, winStride.width, winStride.height);
    }

    public void compute(Mat img, MatOfFloat descriptors) {
        MatOfFloat descriptors_mat = descriptors;
        HOGDescriptor.compute_3(this.nativeObj, img.nativeObj, descriptors_mat.nativeObj);
    }

    public void detect(Mat img, MatOfPoint foundLocations, MatOfDouble weights, double hitThreshold, Size winStride, Size padding, MatOfPoint searchLocations) {
        MatOfPoint foundLocations_mat = foundLocations;
        MatOfDouble weights_mat = weights;
        MatOfPoint searchLocations_mat = searchLocations;
        HOGDescriptor.detect_0(this.nativeObj, img.nativeObj, foundLocations_mat.nativeObj, weights_mat.nativeObj, hitThreshold, winStride.width, winStride.height, padding.width, padding.height, searchLocations_mat.nativeObj);
    }

    public void detect(Mat img, MatOfPoint foundLocations, MatOfDouble weights, double hitThreshold, Size winStride, Size padding) {
        MatOfPoint foundLocations_mat = foundLocations;
        MatOfDouble weights_mat = weights;
        HOGDescriptor.detect_1(this.nativeObj, img.nativeObj, foundLocations_mat.nativeObj, weights_mat.nativeObj, hitThreshold, winStride.width, winStride.height, padding.width, padding.height);
    }

    public void detect(Mat img, MatOfPoint foundLocations, MatOfDouble weights, double hitThreshold, Size winStride) {
        MatOfPoint foundLocations_mat = foundLocations;
        MatOfDouble weights_mat = weights;
        HOGDescriptor.detect_2(this.nativeObj, img.nativeObj, foundLocations_mat.nativeObj, weights_mat.nativeObj, hitThreshold, winStride.width, winStride.height);
    }

    public void detect(Mat img, MatOfPoint foundLocations, MatOfDouble weights, double hitThreshold) {
        MatOfPoint foundLocations_mat = foundLocations;
        MatOfDouble weights_mat = weights;
        HOGDescriptor.detect_3(this.nativeObj, img.nativeObj, foundLocations_mat.nativeObj, weights_mat.nativeObj, hitThreshold);
    }

    public void detect(Mat img, MatOfPoint foundLocations, MatOfDouble weights) {
        MatOfPoint foundLocations_mat = foundLocations;
        MatOfDouble weights_mat = weights;
        HOGDescriptor.detect_4(this.nativeObj, img.nativeObj, foundLocations_mat.nativeObj, weights_mat.nativeObj);
    }

    public void detectMultiScale(Mat img, MatOfRect foundLocations, MatOfDouble foundWeights, double hitThreshold, Size winStride, Size padding, double scale, double finalThreshold, boolean useMeanshiftGrouping) {
        MatOfRect foundLocations_mat = foundLocations;
        MatOfDouble foundWeights_mat = foundWeights;
        HOGDescriptor.detectMultiScale_0(this.nativeObj, img.nativeObj, foundLocations_mat.nativeObj, foundWeights_mat.nativeObj, hitThreshold, winStride.width, winStride.height, padding.width, padding.height, scale, finalThreshold, useMeanshiftGrouping);
    }

    public void detectMultiScale(Mat img, MatOfRect foundLocations, MatOfDouble foundWeights, double hitThreshold, Size winStride, Size padding, double scale, double finalThreshold) {
        MatOfRect foundLocations_mat = foundLocations;
        MatOfDouble foundWeights_mat = foundWeights;
        HOGDescriptor.detectMultiScale_1(this.nativeObj, img.nativeObj, foundLocations_mat.nativeObj, foundWeights_mat.nativeObj, hitThreshold, winStride.width, winStride.height, padding.width, padding.height, scale, finalThreshold);
    }

    public void detectMultiScale(Mat img, MatOfRect foundLocations, MatOfDouble foundWeights, double hitThreshold, Size winStride, Size padding, double scale) {
        MatOfRect foundLocations_mat = foundLocations;
        MatOfDouble foundWeights_mat = foundWeights;
        HOGDescriptor.detectMultiScale_2(this.nativeObj, img.nativeObj, foundLocations_mat.nativeObj, foundWeights_mat.nativeObj, hitThreshold, winStride.width, winStride.height, padding.width, padding.height, scale);
    }

    public void detectMultiScale(Mat img, MatOfRect foundLocations, MatOfDouble foundWeights, double hitThreshold, Size winStride, Size padding) {
        MatOfRect foundLocations_mat = foundLocations;
        MatOfDouble foundWeights_mat = foundWeights;
        HOGDescriptor.detectMultiScale_3(this.nativeObj, img.nativeObj, foundLocations_mat.nativeObj, foundWeights_mat.nativeObj, hitThreshold, winStride.width, winStride.height, padding.width, padding.height);
    }

    public void detectMultiScale(Mat img, MatOfRect foundLocations, MatOfDouble foundWeights, double hitThreshold, Size winStride) {
        MatOfRect foundLocations_mat = foundLocations;
        MatOfDouble foundWeights_mat = foundWeights;
        HOGDescriptor.detectMultiScale_4(this.nativeObj, img.nativeObj, foundLocations_mat.nativeObj, foundWeights_mat.nativeObj, hitThreshold, winStride.width, winStride.height);
    }

    public void detectMultiScale(Mat img, MatOfRect foundLocations, MatOfDouble foundWeights, double hitThreshold) {
        MatOfRect foundLocations_mat = foundLocations;
        MatOfDouble foundWeights_mat = foundWeights;
        HOGDescriptor.detectMultiScale_5(this.nativeObj, img.nativeObj, foundLocations_mat.nativeObj, foundWeights_mat.nativeObj, hitThreshold);
    }

    public void detectMultiScale(Mat img, MatOfRect foundLocations, MatOfDouble foundWeights) {
        MatOfRect foundLocations_mat = foundLocations;
        MatOfDouble foundWeights_mat = foundWeights;
        HOGDescriptor.detectMultiScale_6(this.nativeObj, img.nativeObj, foundLocations_mat.nativeObj, foundWeights_mat.nativeObj);
    }

    public void computeGradient(Mat img, Mat grad, Mat angleOfs, Size paddingTL, Size paddingBR) {
        HOGDescriptor.computeGradient_0(this.nativeObj, img.nativeObj, grad.nativeObj, angleOfs.nativeObj, paddingTL.width, paddingTL.height, paddingBR.width, paddingBR.height);
    }

    public void computeGradient(Mat img, Mat grad, Mat angleOfs, Size paddingTL) {
        HOGDescriptor.computeGradient_1(this.nativeObj, img.nativeObj, grad.nativeObj, angleOfs.nativeObj, paddingTL.width, paddingTL.height);
    }

    public void computeGradient(Mat img, Mat grad, Mat angleOfs) {
        HOGDescriptor.computeGradient_2(this.nativeObj, img.nativeObj, grad.nativeObj, angleOfs.nativeObj);
    }

    public static MatOfFloat getDefaultPeopleDetector() {
        return MatOfFloat.fromNativeAddr(HOGDescriptor.getDefaultPeopleDetector_0());
    }

    public static MatOfFloat getDaimlerPeopleDetector() {
        return MatOfFloat.fromNativeAddr(HOGDescriptor.getDaimlerPeopleDetector_0());
    }

    public Size get_winSize() {
        return new Size(HOGDescriptor.get_winSize_0(this.nativeObj));
    }

    public Size get_blockSize() {
        return new Size(HOGDescriptor.get_blockSize_0(this.nativeObj));
    }

    public Size get_blockStride() {
        return new Size(HOGDescriptor.get_blockStride_0(this.nativeObj));
    }

    public Size get_cellSize() {
        return new Size(HOGDescriptor.get_cellSize_0(this.nativeObj));
    }

    public int get_nbins() {
        return HOGDescriptor.get_nbins_0(this.nativeObj);
    }

    public int get_derivAperture() {
        return HOGDescriptor.get_derivAperture_0(this.nativeObj);
    }

    public double get_winSigma() {
        return HOGDescriptor.get_winSigma_0(this.nativeObj);
    }

    public int get_histogramNormType() {
        return HOGDescriptor.get_histogramNormType_0(this.nativeObj);
    }

    public double get_L2HysThreshold() {
        return HOGDescriptor.get_L2HysThreshold_0(this.nativeObj);
    }

    public boolean get_gammaCorrection() {
        return HOGDescriptor.get_gammaCorrection_0(this.nativeObj);
    }

    public MatOfFloat get_svmDetector() {
        return MatOfFloat.fromNativeAddr(HOGDescriptor.get_svmDetector_0(this.nativeObj));
    }

    public int get_nlevels() {
        return HOGDescriptor.get_nlevels_0(this.nativeObj);
    }

    public boolean get_signedGradient() {
        return HOGDescriptor.get_signedGradient_0(this.nativeObj);
    }

    protected void finalize() throws Throwable {
        HOGDescriptor.delete(this.nativeObj);
    }

    private static native long HOGDescriptor_0();

    private static native long HOGDescriptor_1(double var0, double var2, double var4, double var6, double var8, double var10, double var12, double var14, int var16, int var17, double var18, int var20, double var21, boolean var23, int var24, boolean var25);

    private static native long HOGDescriptor_2(double var0, double var2, double var4, double var6, double var8, double var10, double var12, double var14, int var16, int var17, double var18, int var20, double var21, boolean var23, int var24);

    private static native long HOGDescriptor_3(double var0, double var2, double var4, double var6, double var8, double var10, double var12, double var14, int var16, int var17, double var18, int var20, double var21, boolean var23);

    private static native long HOGDescriptor_4(double var0, double var2, double var4, double var6, double var8, double var10, double var12, double var14, int var16, int var17, double var18, int var20, double var21);

    private static native long HOGDescriptor_5(double var0, double var2, double var4, double var6, double var8, double var10, double var12, double var14, int var16, int var17, double var18, int var20);

    private static native long HOGDescriptor_6(double var0, double var2, double var4, double var6, double var8, double var10, double var12, double var14, int var16, int var17, double var18);

    private static native long HOGDescriptor_7(double var0, double var2, double var4, double var6, double var8, double var10, double var12, double var14, int var16, int var17);

    private static native long HOGDescriptor_8(double var0, double var2, double var4, double var6, double var8, double var10, double var12, double var14, int var16);

    private static native long HOGDescriptor_9(String var0);

    private static native long getDescriptorSize_0(long var0);

    private static native boolean checkDetectorSize_0(long var0);

    private static native double getWinSigma_0(long var0);

    private static native void setSVMDetector_0(long var0, long var2);

    private static native boolean load_0(long var0, String var2, String var3);

    private static native boolean load_1(long var0, String var2);

    private static native void save_0(long var0, String var2, String var3);

    private static native void save_1(long var0, String var2);

    private static native void compute_0(long var0, long var2, long var4, double var6, double var8, double var10, double var12, long var14);

    private static native void compute_1(long var0, long var2, long var4, double var6, double var8, double var10, double var12);

    private static native void compute_2(long var0, long var2, long var4, double var6, double var8);

    private static native void compute_3(long var0, long var2, long var4);

    private static native void detect_0(long var0, long var2, long var4, long var6, double var8, double var10, double var12, double var14, double var16, long var18);

    private static native void detect_1(long var0, long var2, long var4, long var6, double var8, double var10, double var12, double var14, double var16);

    private static native void detect_2(long var0, long var2, long var4, long var6, double var8, double var10, double var12);

    private static native void detect_3(long var0, long var2, long var4, long var6, double var8);

    private static native void detect_4(long var0, long var2, long var4, long var6);

    private static native void detectMultiScale_0(long var0, long var2, long var4, long var6, double var8, double var10, double var12, double var14, double var16, double var18, double var20, boolean var22);

    private static native void detectMultiScale_1(long var0, long var2, long var4, long var6, double var8, double var10, double var12, double var14, double var16, double var18, double var20);

    private static native void detectMultiScale_2(long var0, long var2, long var4, long var6, double var8, double var10, double var12, double var14, double var16, double var18);

    private static native void detectMultiScale_3(long var0, long var2, long var4, long var6, double var8, double var10, double var12, double var14, double var16);

    private static native void detectMultiScale_4(long var0, long var2, long var4, long var6, double var8, double var10, double var12);

    private static native void detectMultiScale_5(long var0, long var2, long var4, long var6, double var8);

    private static native void detectMultiScale_6(long var0, long var2, long var4, long var6);

    private static native void computeGradient_0(long var0, long var2, long var4, long var6, double var8, double var10, double var12, double var14);

    private static native void computeGradient_1(long var0, long var2, long var4, long var6, double var8, double var10);

    private static native void computeGradient_2(long var0, long var2, long var4, long var6);

    private static native long getDefaultPeopleDetector_0();

    private static native long getDaimlerPeopleDetector_0();

    private static native double[] get_winSize_0(long var0);

    private static native double[] get_blockSize_0(long var0);

    private static native double[] get_blockStride_0(long var0);

    private static native double[] get_cellSize_0(long var0);

    private static native int get_nbins_0(long var0);

    private static native int get_derivAperture_0(long var0);

    private static native double get_winSigma_0(long var0);

    private static native int get_histogramNormType_0(long var0);

    private static native double get_L2HysThreshold_0(long var0);

    private static native boolean get_gammaCorrection_0(long var0);

    private static native long get_svmDetector_0(long var0);

    private static native int get_nlevels_0(long var0);

    private static native boolean get_signedGradient_0(long var0);

    private static native void delete(long var0);
}

