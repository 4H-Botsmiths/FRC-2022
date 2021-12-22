/*
 * Decompiled with CFR 0.150.
 */
package org.opencv.core;

import java.nio.ByteBuffer;
import org.opencv.core.CvType;
import org.opencv.core.Point;
import org.opencv.core.Range;
import org.opencv.core.Rect;
import org.opencv.core.Scalar;
import org.opencv.core.Size;

public class Mat {
    public final long nativeObj;

    public Mat(long addr) {
        if (addr == 0L) {
            throw new UnsupportedOperationException("Native object address is NULL");
        }
        this.nativeObj = addr;
    }

    public Mat() {
        this.nativeObj = Mat.n_Mat();
    }

    public Mat(int rows, int cols, int type) {
        this.nativeObj = Mat.n_Mat(rows, cols, type);
    }

    public Mat(int rows, int cols, int type, ByteBuffer data) {
        this.nativeObj = Mat.n_Mat(rows, cols, type, data);
    }

    public Mat(int rows, int cols, int type, ByteBuffer data, long step) {
        this.nativeObj = Mat.n_Mat(rows, cols, type, data, step);
    }

    public Mat(Size size, int type) {
        this.nativeObj = Mat.n_Mat(size.width, size.height, type);
    }

    public Mat(int[] sizes, int type) {
        this.nativeObj = Mat.n_Mat(sizes.length, sizes, type);
    }

    public Mat(int rows, int cols, int type, Scalar s) {
        this.nativeObj = Mat.n_Mat(rows, cols, type, s.val[0], s.val[1], s.val[2], s.val[3]);
    }

    public Mat(Size size, int type, Scalar s) {
        this.nativeObj = Mat.n_Mat(size.width, size.height, type, s.val[0], s.val[1], s.val[2], s.val[3]);
    }

    public Mat(int[] sizes, int type, Scalar s) {
        this.nativeObj = Mat.n_Mat(sizes.length, sizes, type, s.val[0], s.val[1], s.val[2], s.val[3]);
    }

    public Mat(Mat m, Range rowRange, Range colRange) {
        this.nativeObj = Mat.n_Mat(m.nativeObj, rowRange.start, rowRange.end, colRange.start, colRange.end);
    }

    public Mat(Mat m, Range rowRange) {
        this.nativeObj = Mat.n_Mat(m.nativeObj, rowRange.start, rowRange.end);
    }

    public Mat(Mat m, Range[] ranges) {
        this.nativeObj = Mat.n_Mat(m.nativeObj, ranges);
    }

    public Mat(Mat m, Rect roi) {
        this.nativeObj = Mat.n_Mat(m.nativeObj, roi.y, roi.y + roi.height, roi.x, roi.x + roi.width);
    }

    public Mat adjustROI(int dtop, int dbottom, int dleft, int dright) {
        return new Mat(Mat.n_adjustROI(this.nativeObj, dtop, dbottom, dleft, dright));
    }

    public void assignTo(Mat m, int type) {
        Mat.n_assignTo(this.nativeObj, m.nativeObj, type);
    }

    public void assignTo(Mat m) {
        Mat.n_assignTo(this.nativeObj, m.nativeObj);
    }

    public int channels() {
        return Mat.n_channels(this.nativeObj);
    }

    public int checkVector(int elemChannels, int depth, boolean requireContinuous) {
        return Mat.n_checkVector(this.nativeObj, elemChannels, depth, requireContinuous);
    }

    public int checkVector(int elemChannels, int depth) {
        return Mat.n_checkVector(this.nativeObj, elemChannels, depth);
    }

    public int checkVector(int elemChannels) {
        return Mat.n_checkVector(this.nativeObj, elemChannels);
    }

    public Mat clone() {
        return new Mat(Mat.n_clone(this.nativeObj));
    }

    public Mat col(int x) {
        return new Mat(Mat.n_col(this.nativeObj, x));
    }

    public Mat colRange(int startcol, int endcol) {
        return new Mat(Mat.n_colRange(this.nativeObj, startcol, endcol));
    }

    public Mat colRange(Range r) {
        return new Mat(Mat.n_colRange(this.nativeObj, r.start, r.end));
    }

    public int dims() {
        return Mat.n_dims(this.nativeObj);
    }

    public int cols() {
        return Mat.n_cols(this.nativeObj);
    }

    public void convertTo(Mat m, int rtype, double alpha, double beta) {
        Mat.n_convertTo(this.nativeObj, m.nativeObj, rtype, alpha, beta);
    }

    public void convertTo(Mat m, int rtype, double alpha) {
        Mat.n_convertTo(this.nativeObj, m.nativeObj, rtype, alpha);
    }

    public void convertTo(Mat m, int rtype) {
        Mat.n_convertTo(this.nativeObj, m.nativeObj, rtype);
    }

    public void copyTo(Mat m) {
        Mat.n_copyTo(this.nativeObj, m.nativeObj);
    }

    public void copyTo(Mat m, Mat mask) {
        Mat.n_copyTo(this.nativeObj, m.nativeObj, mask.nativeObj);
    }

    public void create(int rows, int cols, int type) {
        Mat.n_create(this.nativeObj, rows, cols, type);
    }

    public void create(Size size, int type) {
        Mat.n_create(this.nativeObj, size.width, size.height, type);
    }

    public void create(int[] sizes, int type) {
        Mat.n_create(this.nativeObj, sizes.length, sizes, type);
    }

    public void copySize(Mat m) {
        Mat.n_copySize(this.nativeObj, m.nativeObj);
    }

    public Mat cross(Mat m) {
        return new Mat(Mat.n_cross(this.nativeObj, m.nativeObj));
    }

    public long dataAddr() {
        return Mat.n_dataAddr(this.nativeObj);
    }

    public int depth() {
        return Mat.n_depth(this.nativeObj);
    }

    public Mat diag(int d) {
        return new Mat(Mat.n_diag(this.nativeObj, d));
    }

    public Mat diag() {
        return new Mat(Mat.n_diag(this.nativeObj, 0));
    }

    public static Mat diag(Mat d) {
        return new Mat(Mat.n_diag(d.nativeObj));
    }

    public double dot(Mat m) {
        return Mat.n_dot(this.nativeObj, m.nativeObj);
    }

    public long elemSize() {
        return Mat.n_elemSize(this.nativeObj);
    }

    public long elemSize1() {
        return Mat.n_elemSize1(this.nativeObj);
    }

    public boolean empty() {
        return Mat.n_empty(this.nativeObj);
    }

    public static Mat eye(int rows, int cols, int type) {
        return new Mat(Mat.n_eye(rows, cols, type));
    }

    public static Mat eye(Size size, int type) {
        return new Mat(Mat.n_eye(size.width, size.height, type));
    }

    public Mat inv(int method) {
        return new Mat(Mat.n_inv(this.nativeObj, method));
    }

    public Mat inv() {
        return new Mat(Mat.n_inv(this.nativeObj));
    }

    public boolean isContinuous() {
        return Mat.n_isContinuous(this.nativeObj);
    }

    public boolean isSubmatrix() {
        return Mat.n_isSubmatrix(this.nativeObj);
    }

    public void locateROI(Size wholeSize, Point ofs) {
        double[] wholeSize_out = new double[2];
        double[] ofs_out = new double[2];
        Mat.locateROI_0(this.nativeObj, wholeSize_out, ofs_out);
        if (wholeSize != null) {
            wholeSize.width = wholeSize_out[0];
            wholeSize.height = wholeSize_out[1];
        }
        if (ofs != null) {
            ofs.x = ofs_out[0];
            ofs.y = ofs_out[1];
        }
    }

    public Mat mul(Mat m, double scale) {
        return new Mat(Mat.n_mul(this.nativeObj, m.nativeObj, scale));
    }

    public Mat mul(Mat m) {
        return new Mat(Mat.n_mul(this.nativeObj, m.nativeObj));
    }

    public static Mat ones(int rows, int cols, int type) {
        return new Mat(Mat.n_ones(rows, cols, type));
    }

    public static Mat ones(Size size, int type) {
        return new Mat(Mat.n_ones(size.width, size.height, type));
    }

    public static Mat ones(int[] sizes, int type) {
        return new Mat(Mat.n_ones(sizes.length, sizes, type));
    }

    public void push_back(Mat m) {
        Mat.n_push_back(this.nativeObj, m.nativeObj);
    }

    public void release() {
        Mat.n_release(this.nativeObj);
    }

    public Mat reshape(int cn, int rows) {
        return new Mat(Mat.n_reshape(this.nativeObj, cn, rows));
    }

    public Mat reshape(int cn) {
        return new Mat(Mat.n_reshape(this.nativeObj, cn));
    }

    public Mat reshape(int cn, int[] newshape) {
        return new Mat(Mat.n_reshape_1(this.nativeObj, cn, newshape.length, newshape));
    }

    public Mat row(int y) {
        return new Mat(Mat.n_row(this.nativeObj, y));
    }

    public Mat rowRange(int startrow, int endrow) {
        return new Mat(Mat.n_rowRange(this.nativeObj, startrow, endrow));
    }

    public Mat rowRange(Range r) {
        return new Mat(Mat.n_rowRange(this.nativeObj, r.start, r.end));
    }

    public int rows() {
        return Mat.n_rows(this.nativeObj);
    }

    public Mat setTo(Scalar s) {
        return new Mat(Mat.n_setTo(this.nativeObj, s.val[0], s.val[1], s.val[2], s.val[3]));
    }

    public Mat setTo(Scalar value, Mat mask) {
        return new Mat(Mat.n_setTo(this.nativeObj, value.val[0], value.val[1], value.val[2], value.val[3], mask.nativeObj));
    }

    public Mat setTo(Mat value, Mat mask) {
        return new Mat(Mat.n_setTo(this.nativeObj, value.nativeObj, mask.nativeObj));
    }

    public Mat setTo(Mat value) {
        return new Mat(Mat.n_setTo(this.nativeObj, value.nativeObj));
    }

    public Size size() {
        return new Size(Mat.n_size(this.nativeObj));
    }

    public int size(int i) {
        return Mat.n_size_i(this.nativeObj, i);
    }

    public long step1(int i) {
        return Mat.n_step1(this.nativeObj, i);
    }

    public long step1() {
        return Mat.n_step1(this.nativeObj);
    }

    public Mat submat(int rowStart, int rowEnd, int colStart, int colEnd) {
        return new Mat(Mat.n_submat_rr(this.nativeObj, rowStart, rowEnd, colStart, colEnd));
    }

    public Mat submat(Range rowRange, Range colRange) {
        return new Mat(Mat.n_submat_rr(this.nativeObj, rowRange.start, rowRange.end, colRange.start, colRange.end));
    }

    public Mat submat(Range[] ranges) {
        return new Mat(Mat.n_submat_ranges(this.nativeObj, ranges));
    }

    public Mat submat(Rect roi) {
        return new Mat(Mat.n_submat(this.nativeObj, roi.x, roi.y, roi.width, roi.height));
    }

    public Mat t() {
        return new Mat(Mat.n_t(this.nativeObj));
    }

    public long total() {
        return Mat.n_total(this.nativeObj);
    }

    public int type() {
        return Mat.n_type(this.nativeObj);
    }

    public static Mat zeros(int rows, int cols, int type) {
        return new Mat(Mat.n_zeros(rows, cols, type));
    }

    public static Mat zeros(Size size, int type) {
        return new Mat(Mat.n_zeros(size.width, size.height, type));
    }

    public static Mat zeros(int[] sizes, int type) {
        return new Mat(Mat.n_zeros(sizes.length, sizes, type));
    }

    protected void finalize() throws Throwable {
        Mat.n_delete(this.nativeObj);
        super.finalize();
    }

    public String toString() {
        String _dims = this.dims() > 0 ? "" : "-1*-1*";
        for (int i = 0; i < this.dims(); ++i) {
            _dims = _dims + this.size(i) + "*";
        }
        return "Mat [ " + _dims + CvType.typeToString(this.type()) + ", isCont=" + this.isContinuous() + ", isSubmat=" + this.isSubmatrix() + ", nativeObj=0x" + Long.toHexString(this.nativeObj) + ", dataAddr=0x" + Long.toHexString(this.dataAddr()) + " ]";
    }

    public String dump() {
        return Mat.nDump(this.nativeObj);
    }

    public int put(int row, int col, double ... data) {
        int t = this.type();
        if (data == null || data.length % CvType.channels(t) != 0) {
            throw new UnsupportedOperationException("Provided data element number (" + (data == null ? 0 : data.length) + ") should be multiple of the Mat channels count (" + CvType.channels(t) + ")");
        }
        return Mat.nPutD(this.nativeObj, row, col, data.length, data);
    }

    public int put(int[] idx, double ... data) {
        int t = this.type();
        if (data == null || data.length % CvType.channels(t) != 0) {
            throw new UnsupportedOperationException("Provided data element number (" + (data == null ? 0 : data.length) + ") should be multiple of the Mat channels count (" + CvType.channels(t) + ")");
        }
        if (idx.length != this.dims()) {
            throw new IllegalArgumentException("Incorrect number of indices");
        }
        return Mat.nPutDIdx(this.nativeObj, idx, data.length, data);
    }

    public int put(int row, int col, float[] data) {
        int t = this.type();
        if (data == null || data.length % CvType.channels(t) != 0) {
            throw new UnsupportedOperationException("Provided data element number (" + (data == null ? 0 : data.length) + ") should be multiple of the Mat channels count (" + CvType.channels(t) + ")");
        }
        if (CvType.depth(t) == 5) {
            return Mat.nPutF(this.nativeObj, row, col, data.length, data);
        }
        throw new UnsupportedOperationException("Mat data type is not compatible: " + t);
    }

    public int put(int[] idx, float[] data) {
        int t = this.type();
        if (data == null || data.length % CvType.channels(t) != 0) {
            throw new UnsupportedOperationException("Provided data element number (" + (data == null ? 0 : data.length) + ") should be multiple of the Mat channels count (" + CvType.channels(t) + ")");
        }
        if (idx.length != this.dims()) {
            throw new IllegalArgumentException("Incorrect number of indices");
        }
        if (CvType.depth(t) == 5) {
            return Mat.nPutFIdx(this.nativeObj, idx, data.length, data);
        }
        throw new UnsupportedOperationException("Mat data type is not compatible: " + t);
    }

    public int put(int row, int col, int[] data) {
        int t = this.type();
        if (data == null || data.length % CvType.channels(t) != 0) {
            throw new UnsupportedOperationException("Provided data element number (" + (data == null ? 0 : data.length) + ") should be multiple of the Mat channels count (" + CvType.channels(t) + ")");
        }
        if (CvType.depth(t) == 4) {
            return Mat.nPutI(this.nativeObj, row, col, data.length, data);
        }
        throw new UnsupportedOperationException("Mat data type is not compatible: " + t);
    }

    public int put(int[] idx, int[] data) {
        int t = this.type();
        if (data == null || data.length % CvType.channels(t) != 0) {
            throw new UnsupportedOperationException("Provided data element number (" + (data == null ? 0 : data.length) + ") should be multiple of the Mat channels count (" + CvType.channels(t) + ")");
        }
        if (idx.length != this.dims()) {
            throw new IllegalArgumentException("Incorrect number of indices");
        }
        if (CvType.depth(t) == 4) {
            return Mat.nPutIIdx(this.nativeObj, idx, data.length, data);
        }
        throw new UnsupportedOperationException("Mat data type is not compatible: " + t);
    }

    public int put(int row, int col, short[] data) {
        int t = this.type();
        if (data == null || data.length % CvType.channels(t) != 0) {
            throw new UnsupportedOperationException("Provided data element number (" + (data == null ? 0 : data.length) + ") should be multiple of the Mat channels count (" + CvType.channels(t) + ")");
        }
        if (CvType.depth(t) == 2 || CvType.depth(t) == 3) {
            return Mat.nPutS(this.nativeObj, row, col, data.length, data);
        }
        throw new UnsupportedOperationException("Mat data type is not compatible: " + t);
    }

    public int put(int[] idx, short[] data) {
        int t = this.type();
        if (data == null || data.length % CvType.channels(t) != 0) {
            throw new UnsupportedOperationException("Provided data element number (" + (data == null ? 0 : data.length) + ") should be multiple of the Mat channels count (" + CvType.channels(t) + ")");
        }
        if (idx.length != this.dims()) {
            throw new IllegalArgumentException("Incorrect number of indices");
        }
        if (CvType.depth(t) == 2 || CvType.depth(t) == 3) {
            return Mat.nPutSIdx(this.nativeObj, idx, data.length, data);
        }
        throw new UnsupportedOperationException("Mat data type is not compatible: " + t);
    }

    public int put(int row, int col, byte[] data) {
        int t = this.type();
        if (data == null || data.length % CvType.channels(t) != 0) {
            throw new UnsupportedOperationException("Provided data element number (" + (data == null ? 0 : data.length) + ") should be multiple of the Mat channels count (" + CvType.channels(t) + ")");
        }
        if (CvType.depth(t) == 0 || CvType.depth(t) == 1) {
            return Mat.nPutB(this.nativeObj, row, col, data.length, data);
        }
        throw new UnsupportedOperationException("Mat data type is not compatible: " + t);
    }

    public int put(int[] idx, byte[] data) {
        int t = this.type();
        if (data == null || data.length % CvType.channels(t) != 0) {
            throw new UnsupportedOperationException("Provided data element number (" + (data == null ? 0 : data.length) + ") should be multiple of the Mat channels count (" + CvType.channels(t) + ")");
        }
        if (idx.length != this.dims()) {
            throw new IllegalArgumentException("Incorrect number of indices");
        }
        if (CvType.depth(t) == 0 || CvType.depth(t) == 1) {
            return Mat.nPutBIdx(this.nativeObj, idx, data.length, data);
        }
        throw new UnsupportedOperationException("Mat data type is not compatible: " + t);
    }

    public int put(int row, int col, byte[] data, int offset, int length) {
        int t = this.type();
        if (data == null || length % CvType.channels(t) != 0) {
            throw new UnsupportedOperationException("Provided data element number (" + (data == null ? 0 : data.length) + ") should be multiple of the Mat channels count (" + CvType.channels(t) + ")");
        }
        if (CvType.depth(t) == 0 || CvType.depth(t) == 1) {
            return Mat.nPutBwOffset(this.nativeObj, row, col, length, offset, data);
        }
        throw new UnsupportedOperationException("Mat data type is not compatible: " + t);
    }

    public int put(int[] idx, byte[] data, int offset, int length) {
        int t = this.type();
        if (data == null || length % CvType.channels(t) != 0) {
            throw new UnsupportedOperationException("Provided data element number (" + (data == null ? 0 : data.length) + ") should be multiple of the Mat channels count (" + CvType.channels(t) + ")");
        }
        if (idx.length != this.dims()) {
            throw new IllegalArgumentException("Incorrect number of indices");
        }
        if (CvType.depth(t) == 0 || CvType.depth(t) == 1) {
            return Mat.nPutBwIdxOffset(this.nativeObj, idx, length, offset, data);
        }
        throw new UnsupportedOperationException("Mat data type is not compatible: " + t);
    }

    public int get(int row, int col, byte[] data) {
        int t = this.type();
        if (data == null || data.length % CvType.channels(t) != 0) {
            throw new UnsupportedOperationException("Provided data element number (" + (data == null ? 0 : data.length) + ") should be multiple of the Mat channels count (" + CvType.channels(t) + ")");
        }
        if (CvType.depth(t) == 0 || CvType.depth(t) == 1) {
            return Mat.nGetB(this.nativeObj, row, col, data.length, data);
        }
        throw new UnsupportedOperationException("Mat data type is not compatible: " + t);
    }

    public int get(int[] idx, byte[] data) {
        int t = this.type();
        if (data == null || data.length % CvType.channels(t) != 0) {
            throw new UnsupportedOperationException("Provided data element number (" + (data == null ? 0 : data.length) + ") should be multiple of the Mat channels count (" + CvType.channels(t) + ")");
        }
        if (idx.length != this.dims()) {
            throw new IllegalArgumentException("Incorrect number of indices");
        }
        if (CvType.depth(t) == 0 || CvType.depth(t) == 1) {
            return Mat.nGetBIdx(this.nativeObj, idx, data.length, data);
        }
        throw new UnsupportedOperationException("Mat data type is not compatible: " + t);
    }

    public int get(int row, int col, short[] data) {
        int t = this.type();
        if (data == null || data.length % CvType.channels(t) != 0) {
            throw new UnsupportedOperationException("Provided data element number (" + (data == null ? 0 : data.length) + ") should be multiple of the Mat channels count (" + CvType.channels(t) + ")");
        }
        if (CvType.depth(t) == 2 || CvType.depth(t) == 3) {
            return Mat.nGetS(this.nativeObj, row, col, data.length, data);
        }
        throw new UnsupportedOperationException("Mat data type is not compatible: " + t);
    }

    public int get(int[] idx, short[] data) {
        int t = this.type();
        if (data == null || data.length % CvType.channels(t) != 0) {
            throw new UnsupportedOperationException("Provided data element number (" + (data == null ? 0 : data.length) + ") should be multiple of the Mat channels count (" + CvType.channels(t) + ")");
        }
        if (idx.length != this.dims()) {
            throw new IllegalArgumentException("Incorrect number of indices");
        }
        if (CvType.depth(t) == 2 || CvType.depth(t) == 3) {
            return Mat.nGetSIdx(this.nativeObj, idx, data.length, data);
        }
        throw new UnsupportedOperationException("Mat data type is not compatible: " + t);
    }

    public int get(int row, int col, int[] data) {
        int t = this.type();
        if (data == null || data.length % CvType.channels(t) != 0) {
            throw new UnsupportedOperationException("Provided data element number (" + (data == null ? 0 : data.length) + ") should be multiple of the Mat channels count (" + CvType.channels(t) + ")");
        }
        if (CvType.depth(t) == 4) {
            return Mat.nGetI(this.nativeObj, row, col, data.length, data);
        }
        throw new UnsupportedOperationException("Mat data type is not compatible: " + t);
    }

    public int get(int[] idx, int[] data) {
        int t = this.type();
        if (data == null || data.length % CvType.channels(t) != 0) {
            throw new UnsupportedOperationException("Provided data element number (" + (data == null ? 0 : data.length) + ") should be multiple of the Mat channels count (" + CvType.channels(t) + ")");
        }
        if (idx.length != this.dims()) {
            throw new IllegalArgumentException("Incorrect number of indices");
        }
        if (CvType.depth(t) == 4) {
            return Mat.nGetIIdx(this.nativeObj, idx, data.length, data);
        }
        throw new UnsupportedOperationException("Mat data type is not compatible: " + t);
    }

    public int get(int row, int col, float[] data) {
        int t = this.type();
        if (data == null || data.length % CvType.channels(t) != 0) {
            throw new UnsupportedOperationException("Provided data element number (" + (data == null ? 0 : data.length) + ") should be multiple of the Mat channels count (" + CvType.channels(t) + ")");
        }
        if (CvType.depth(t) == 5) {
            return Mat.nGetF(this.nativeObj, row, col, data.length, data);
        }
        throw new UnsupportedOperationException("Mat data type is not compatible: " + t);
    }

    public int get(int[] idx, float[] data) {
        int t = this.type();
        if (data == null || data.length % CvType.channels(t) != 0) {
            throw new UnsupportedOperationException("Provided data element number (" + (data == null ? 0 : data.length) + ") should be multiple of the Mat channels count (" + CvType.channels(t) + ")");
        }
        if (idx.length != this.dims()) {
            throw new IllegalArgumentException("Incorrect number of indices");
        }
        if (CvType.depth(t) == 5) {
            return Mat.nGetFIdx(this.nativeObj, idx, data.length, data);
        }
        throw new UnsupportedOperationException("Mat data type is not compatible: " + t);
    }

    public int get(int row, int col, double[] data) {
        int t = this.type();
        if (data == null || data.length % CvType.channels(t) != 0) {
            throw new UnsupportedOperationException("Provided data element number (" + (data == null ? 0 : data.length) + ") should be multiple of the Mat channels count (" + CvType.channels(t) + ")");
        }
        if (CvType.depth(t) == 6) {
            return Mat.nGetD(this.nativeObj, row, col, data.length, data);
        }
        throw new UnsupportedOperationException("Mat data type is not compatible: " + t);
    }

    public int get(int[] idx, double[] data) {
        int t = this.type();
        if (data == null || data.length % CvType.channels(t) != 0) {
            throw new UnsupportedOperationException("Provided data element number (" + (data == null ? 0 : data.length) + ") should be multiple of the Mat channels count (" + CvType.channels(t) + ")");
        }
        if (idx.length != this.dims()) {
            throw new IllegalArgumentException("Incorrect number of indices");
        }
        if (CvType.depth(t) == 6) {
            return Mat.nGetDIdx(this.nativeObj, idx, data.length, data);
        }
        throw new UnsupportedOperationException("Mat data type is not compatible: " + t);
    }

    public double[] get(int row, int col) {
        return Mat.nGet(this.nativeObj, row, col);
    }

    public double[] get(int[] idx) {
        if (idx.length != this.dims()) {
            throw new IllegalArgumentException("Incorrect number of indices");
        }
        return Mat.nGetIdx(this.nativeObj, idx);
    }

    public int height() {
        return this.rows();
    }

    public int width() {
        return this.cols();
    }

    public long getNativeObjAddr() {
        return this.nativeObj;
    }

    private static native long n_Mat();

    private static native long n_Mat(int var0, int var1, int var2);

    private static native long n_Mat(int var0, int[] var1, int var2);

    private static native long n_Mat(int var0, int var1, int var2, ByteBuffer var3);

    private static native long n_Mat(int var0, int var1, int var2, ByteBuffer var3, long var4);

    private static native long n_Mat(double var0, double var2, int var4);

    private static native long n_Mat(int var0, int var1, int var2, double var3, double var5, double var7, double var9);

    private static native long n_Mat(double var0, double var2, int var4, double var5, double var7, double var9, double var11);

    private static native long n_Mat(int var0, int[] var1, int var2, double var3, double var5, double var7, double var9);

    private static native long n_Mat(long var0, int var2, int var3, int var4, int var5);

    private static native long n_Mat(long var0, int var2, int var3);

    private static native long n_Mat(long var0, Range[] var2);

    private static native long n_adjustROI(long var0, int var2, int var3, int var4, int var5);

    private static native void n_assignTo(long var0, long var2, int var4);

    private static native void n_assignTo(long var0, long var2);

    private static native int n_channels(long var0);

    private static native int n_checkVector(long var0, int var2, int var3, boolean var4);

    private static native int n_checkVector(long var0, int var2, int var3);

    private static native int n_checkVector(long var0, int var2);

    private static native long n_clone(long var0);

    private static native long n_col(long var0, int var2);

    private static native long n_colRange(long var0, int var2, int var3);

    private static native int n_dims(long var0);

    private static native int n_cols(long var0);

    private static native void n_convertTo(long var0, long var2, int var4, double var5, double var7);

    private static native void n_convertTo(long var0, long var2, int var4, double var5);

    private static native void n_convertTo(long var0, long var2, int var4);

    private static native void n_copyTo(long var0, long var2);

    private static native void n_copyTo(long var0, long var2, long var4);

    private static native void n_create(long var0, int var2, int var3, int var4);

    private static native void n_create(long var0, double var2, double var4, int var6);

    private static native void n_create(long var0, int var2, int[] var3, int var4);

    private static native void n_copySize(long var0, long var2);

    private static native long n_cross(long var0, long var2);

    private static native long n_dataAddr(long var0);

    private static native int n_depth(long var0);

    private static native long n_diag(long var0, int var2);

    private static native long n_diag(long var0);

    private static native double n_dot(long var0, long var2);

    private static native long n_elemSize(long var0);

    private static native long n_elemSize1(long var0);

    private static native boolean n_empty(long var0);

    private static native long n_eye(int var0, int var1, int var2);

    private static native long n_eye(double var0, double var2, int var4);

    private static native long n_inv(long var0, int var2);

    private static native long n_inv(long var0);

    private static native boolean n_isContinuous(long var0);

    private static native boolean n_isSubmatrix(long var0);

    private static native void locateROI_0(long var0, double[] var2, double[] var3);

    private static native long n_mul(long var0, long var2, double var4);

    private static native long n_mul(long var0, long var2);

    private static native long n_ones(int var0, int var1, int var2);

    private static native long n_ones(double var0, double var2, int var4);

    private static native long n_ones(int var0, int[] var1, int var2);

    private static native void n_push_back(long var0, long var2);

    private static native void n_release(long var0);

    private static native long n_reshape(long var0, int var2, int var3);

    private static native long n_reshape(long var0, int var2);

    private static native long n_reshape_1(long var0, int var2, int var3, int[] var4);

    private static native long n_row(long var0, int var2);

    private static native long n_rowRange(long var0, int var2, int var3);

    private static native int n_rows(long var0);

    private static native long n_setTo(long var0, double var2, double var4, double var6, double var8);

    private static native long n_setTo(long var0, double var2, double var4, double var6, double var8, long var10);

    private static native long n_setTo(long var0, long var2, long var4);

    private static native long n_setTo(long var0, long var2);

    private static native double[] n_size(long var0);

    private static native int n_size_i(long var0, int var2);

    private static native long n_step1(long var0, int var2);

    private static native long n_step1(long var0);

    private static native long n_submat_rr(long var0, int var2, int var3, int var4, int var5);

    private static native long n_submat_ranges(long var0, Range[] var2);

    private static native long n_submat(long var0, int var2, int var3, int var4, int var5);

    private static native long n_t(long var0);

    private static native long n_total(long var0);

    private static native int n_type(long var0);

    private static native long n_zeros(int var0, int var1, int var2);

    private static native long n_zeros(double var0, double var2, int var4);

    private static native long n_zeros(int var0, int[] var1, int var2);

    private static native void n_delete(long var0);

    private static native int nPutD(long var0, int var2, int var3, int var4, double[] var5);

    private static native int nPutDIdx(long var0, int[] var2, int var3, double[] var4);

    private static native int nPutF(long var0, int var2, int var3, int var4, float[] var5);

    private static native int nPutFIdx(long var0, int[] var2, int var3, float[] var4);

    private static native int nPutI(long var0, int var2, int var3, int var4, int[] var5);

    private static native int nPutIIdx(long var0, int[] var2, int var3, int[] var4);

    private static native int nPutS(long var0, int var2, int var3, int var4, short[] var5);

    private static native int nPutSIdx(long var0, int[] var2, int var3, short[] var4);

    private static native int nPutB(long var0, int var2, int var3, int var4, byte[] var5);

    private static native int nPutBIdx(long var0, int[] var2, int var3, byte[] var4);

    private static native int nPutBwOffset(long var0, int var2, int var3, int var4, int var5, byte[] var6);

    private static native int nPutBwIdxOffset(long var0, int[] var2, int var3, int var4, byte[] var5);

    private static native int nGetB(long var0, int var2, int var3, int var4, byte[] var5);

    private static native int nGetBIdx(long var0, int[] var2, int var3, byte[] var4);

    private static native int nGetS(long var0, int var2, int var3, int var4, short[] var5);

    private static native int nGetSIdx(long var0, int[] var2, int var3, short[] var4);

    private static native int nGetI(long var0, int var2, int var3, int var4, int[] var5);

    private static native int nGetIIdx(long var0, int[] var2, int var3, int[] var4);

    private static native int nGetF(long var0, int var2, int var3, int var4, float[] var5);

    private static native int nGetFIdx(long var0, int[] var2, int var3, float[] var4);

    private static native int nGetD(long var0, int var2, int var3, int var4, double[] var5);

    private static native int nGetDIdx(long var0, int[] var2, int var3, double[] var4);

    private static native double[] nGet(long var0, int var2, int var3);

    private static native double[] nGetIdx(long var0, int[] var2);

    private static native String nDump(long var0);
}

