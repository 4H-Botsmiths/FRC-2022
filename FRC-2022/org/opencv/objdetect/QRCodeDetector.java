/*
 * Decompiled with CFR 0.150.
 */
package org.opencv.objdetect;

import java.util.List;
import org.opencv.core.Mat;
import org.opencv.utils.Converters;

public class QRCodeDetector {
    protected final long nativeObj;

    protected QRCodeDetector(long addr) {
        this.nativeObj = addr;
    }

    public long getNativeObjAddr() {
        return this.nativeObj;
    }

    public static QRCodeDetector __fromPtr__(long addr) {
        return new QRCodeDetector(addr);
    }

    public QRCodeDetector() {
        this.nativeObj = QRCodeDetector.QRCodeDetector_0();
    }

    public void setEpsX(double epsX) {
        QRCodeDetector.setEpsX_0(this.nativeObj, epsX);
    }

    public void setEpsY(double epsY) {
        QRCodeDetector.setEpsY_0(this.nativeObj, epsY);
    }

    public boolean detect(Mat img, Mat points) {
        return QRCodeDetector.detect_0(this.nativeObj, img.nativeObj, points.nativeObj);
    }

    public String decode(Mat img, Mat points, Mat straight_qrcode) {
        return QRCodeDetector.decode_0(this.nativeObj, img.nativeObj, points.nativeObj, straight_qrcode.nativeObj);
    }

    public String decode(Mat img, Mat points) {
        return QRCodeDetector.decode_1(this.nativeObj, img.nativeObj, points.nativeObj);
    }

    public String decodeCurved(Mat img, Mat points, Mat straight_qrcode) {
        return QRCodeDetector.decodeCurved_0(this.nativeObj, img.nativeObj, points.nativeObj, straight_qrcode.nativeObj);
    }

    public String decodeCurved(Mat img, Mat points) {
        return QRCodeDetector.decodeCurved_1(this.nativeObj, img.nativeObj, points.nativeObj);
    }

    public String detectAndDecode(Mat img, Mat points, Mat straight_qrcode) {
        return QRCodeDetector.detectAndDecode_0(this.nativeObj, img.nativeObj, points.nativeObj, straight_qrcode.nativeObj);
    }

    public String detectAndDecode(Mat img, Mat points) {
        return QRCodeDetector.detectAndDecode_1(this.nativeObj, img.nativeObj, points.nativeObj);
    }

    public String detectAndDecode(Mat img) {
        return QRCodeDetector.detectAndDecode_2(this.nativeObj, img.nativeObj);
    }

    public String detectAndDecodeCurved(Mat img, Mat points, Mat straight_qrcode) {
        return QRCodeDetector.detectAndDecodeCurved_0(this.nativeObj, img.nativeObj, points.nativeObj, straight_qrcode.nativeObj);
    }

    public String detectAndDecodeCurved(Mat img, Mat points) {
        return QRCodeDetector.detectAndDecodeCurved_1(this.nativeObj, img.nativeObj, points.nativeObj);
    }

    public String detectAndDecodeCurved(Mat img) {
        return QRCodeDetector.detectAndDecodeCurved_2(this.nativeObj, img.nativeObj);
    }

    public boolean detectMulti(Mat img, Mat points) {
        return QRCodeDetector.detectMulti_0(this.nativeObj, img.nativeObj, points.nativeObj);
    }

    public boolean decodeMulti(Mat img, Mat points, List<String> decoded_info, List<Mat> straight_qrcode) {
        Mat straight_qrcode_mat = new Mat();
        boolean retVal = QRCodeDetector.decodeMulti_0(this.nativeObj, img.nativeObj, points.nativeObj, decoded_info, straight_qrcode_mat.nativeObj);
        Converters.Mat_to_vector_Mat(straight_qrcode_mat, straight_qrcode);
        straight_qrcode_mat.release();
        return retVal;
    }

    public boolean decodeMulti(Mat img, Mat points, List<String> decoded_info) {
        return QRCodeDetector.decodeMulti_1(this.nativeObj, img.nativeObj, points.nativeObj, decoded_info);
    }

    public boolean detectAndDecodeMulti(Mat img, List<String> decoded_info, Mat points, List<Mat> straight_qrcode) {
        Mat straight_qrcode_mat = new Mat();
        boolean retVal = QRCodeDetector.detectAndDecodeMulti_0(this.nativeObj, img.nativeObj, decoded_info, points.nativeObj, straight_qrcode_mat.nativeObj);
        Converters.Mat_to_vector_Mat(straight_qrcode_mat, straight_qrcode);
        straight_qrcode_mat.release();
        return retVal;
    }

    public boolean detectAndDecodeMulti(Mat img, List<String> decoded_info, Mat points) {
        return QRCodeDetector.detectAndDecodeMulti_1(this.nativeObj, img.nativeObj, decoded_info, points.nativeObj);
    }

    public boolean detectAndDecodeMulti(Mat img, List<String> decoded_info) {
        return QRCodeDetector.detectAndDecodeMulti_2(this.nativeObj, img.nativeObj, decoded_info);
    }

    protected void finalize() throws Throwable {
        QRCodeDetector.delete(this.nativeObj);
    }

    private static native long QRCodeDetector_0();

    private static native void setEpsX_0(long var0, double var2);

    private static native void setEpsY_0(long var0, double var2);

    private static native boolean detect_0(long var0, long var2, long var4);

    private static native String decode_0(long var0, long var2, long var4, long var6);

    private static native String decode_1(long var0, long var2, long var4);

    private static native String decodeCurved_0(long var0, long var2, long var4, long var6);

    private static native String decodeCurved_1(long var0, long var2, long var4);

    private static native String detectAndDecode_0(long var0, long var2, long var4, long var6);

    private static native String detectAndDecode_1(long var0, long var2, long var4);

    private static native String detectAndDecode_2(long var0, long var2);

    private static native String detectAndDecodeCurved_0(long var0, long var2, long var4, long var6);

    private static native String detectAndDecodeCurved_1(long var0, long var2, long var4);

    private static native String detectAndDecodeCurved_2(long var0, long var2);

    private static native boolean detectMulti_0(long var0, long var2, long var4);

    private static native boolean decodeMulti_0(long var0, long var2, long var4, List<String> var6, long var7);

    private static native boolean decodeMulti_1(long var0, long var2, long var4, List<String> var6);

    private static native boolean detectAndDecodeMulti_0(long var0, long var2, List<String> var4, long var5, long var7);

    private static native boolean detectAndDecodeMulti_1(long var0, long var2, List<String> var4, long var5);

    private static native boolean detectAndDecodeMulti_2(long var0, long var2, List<String> var4);

    private static native void delete(long var0);
}

