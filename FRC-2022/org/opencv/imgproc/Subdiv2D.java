/*
 * Decompiled with CFR 0.150.
 */
package org.opencv.imgproc;

import java.util.List;
import org.opencv.core.Mat;
import org.opencv.core.MatOfFloat4;
import org.opencv.core.MatOfFloat6;
import org.opencv.core.MatOfInt;
import org.opencv.core.MatOfPoint2f;
import org.opencv.core.Point;
import org.opencv.core.Rect;
import org.opencv.utils.Converters;

public class Subdiv2D {
    protected final long nativeObj;
    public static final int PTLOC_ERROR = -2;
    public static final int PTLOC_OUTSIDE_RECT = -1;
    public static final int PTLOC_INSIDE = 0;
    public static final int PTLOC_VERTEX = 1;
    public static final int PTLOC_ON_EDGE = 2;
    public static final int NEXT_AROUND_ORG = 0;
    public static final int NEXT_AROUND_DST = 34;
    public static final int PREV_AROUND_ORG = 17;
    public static final int PREV_AROUND_DST = 51;
    public static final int NEXT_AROUND_LEFT = 19;
    public static final int NEXT_AROUND_RIGHT = 49;
    public static final int PREV_AROUND_LEFT = 32;
    public static final int PREV_AROUND_RIGHT = 2;

    protected Subdiv2D(long addr) {
        this.nativeObj = addr;
    }

    public long getNativeObjAddr() {
        return this.nativeObj;
    }

    public static Subdiv2D __fromPtr__(long addr) {
        return new Subdiv2D(addr);
    }

    public Subdiv2D() {
        this.nativeObj = Subdiv2D.Subdiv2D_0();
    }

    public Subdiv2D(Rect rect) {
        this.nativeObj = Subdiv2D.Subdiv2D_1(rect.x, rect.y, rect.width, rect.height);
    }

    public void initDelaunay(Rect rect) {
        Subdiv2D.initDelaunay_0(this.nativeObj, rect.x, rect.y, rect.width, rect.height);
    }

    public int insert(Point pt) {
        return Subdiv2D.insert_0(this.nativeObj, pt.x, pt.y);
    }

    public void insert(MatOfPoint2f ptvec) {
        MatOfPoint2f ptvec_mat = ptvec;
        Subdiv2D.insert_1(this.nativeObj, ptvec_mat.nativeObj);
    }

    public int locate(Point pt, int[] edge, int[] vertex) {
        double[] edge_out = new double[1];
        double[] vertex_out = new double[1];
        int retVal = Subdiv2D.locate_0(this.nativeObj, pt.x, pt.y, edge_out, vertex_out);
        if (edge != null) {
            edge[0] = (int)edge_out[0];
        }
        if (vertex != null) {
            vertex[0] = (int)vertex_out[0];
        }
        return retVal;
    }

    public int findNearest(Point pt, Point nearestPt) {
        double[] nearestPt_out = new double[2];
        int retVal = Subdiv2D.findNearest_0(this.nativeObj, pt.x, pt.y, nearestPt_out);
        if (nearestPt != null) {
            nearestPt.x = nearestPt_out[0];
            nearestPt.y = nearestPt_out[1];
        }
        return retVal;
    }

    public int findNearest(Point pt) {
        return Subdiv2D.findNearest_1(this.nativeObj, pt.x, pt.y);
    }

    public void getEdgeList(MatOfFloat4 edgeList) {
        MatOfFloat4 edgeList_mat = edgeList;
        Subdiv2D.getEdgeList_0(this.nativeObj, edgeList_mat.nativeObj);
    }

    public void getLeadingEdgeList(MatOfInt leadingEdgeList) {
        MatOfInt leadingEdgeList_mat = leadingEdgeList;
        Subdiv2D.getLeadingEdgeList_0(this.nativeObj, leadingEdgeList_mat.nativeObj);
    }

    public void getTriangleList(MatOfFloat6 triangleList) {
        MatOfFloat6 triangleList_mat = triangleList;
        Subdiv2D.getTriangleList_0(this.nativeObj, triangleList_mat.nativeObj);
    }

    public void getVoronoiFacetList(MatOfInt idx, List<MatOfPoint2f> facetList, MatOfPoint2f facetCenters) {
        MatOfInt idx_mat = idx;
        Mat facetList_mat = new Mat();
        MatOfPoint2f facetCenters_mat = facetCenters;
        Subdiv2D.getVoronoiFacetList_0(this.nativeObj, idx_mat.nativeObj, facetList_mat.nativeObj, facetCenters_mat.nativeObj);
        Converters.Mat_to_vector_vector_Point2f(facetList_mat, facetList);
        facetList_mat.release();
    }

    public Point getVertex(int vertex, int[] firstEdge) {
        double[] firstEdge_out = new double[1];
        Point retVal = new Point(Subdiv2D.getVertex_0(this.nativeObj, vertex, firstEdge_out));
        if (firstEdge != null) {
            firstEdge[0] = (int)firstEdge_out[0];
        }
        return retVal;
    }

    public Point getVertex(int vertex) {
        return new Point(Subdiv2D.getVertex_1(this.nativeObj, vertex));
    }

    public int getEdge(int edge, int nextEdgeType) {
        return Subdiv2D.getEdge_0(this.nativeObj, edge, nextEdgeType);
    }

    public int nextEdge(int edge) {
        return Subdiv2D.nextEdge_0(this.nativeObj, edge);
    }

    public int rotateEdge(int edge, int rotate) {
        return Subdiv2D.rotateEdge_0(this.nativeObj, edge, rotate);
    }

    public int symEdge(int edge) {
        return Subdiv2D.symEdge_0(this.nativeObj, edge);
    }

    public int edgeOrg(int edge, Point orgpt) {
        double[] orgpt_out = new double[2];
        int retVal = Subdiv2D.edgeOrg_0(this.nativeObj, edge, orgpt_out);
        if (orgpt != null) {
            orgpt.x = orgpt_out[0];
            orgpt.y = orgpt_out[1];
        }
        return retVal;
    }

    public int edgeOrg(int edge) {
        return Subdiv2D.edgeOrg_1(this.nativeObj, edge);
    }

    public int edgeDst(int edge, Point dstpt) {
        double[] dstpt_out = new double[2];
        int retVal = Subdiv2D.edgeDst_0(this.nativeObj, edge, dstpt_out);
        if (dstpt != null) {
            dstpt.x = dstpt_out[0];
            dstpt.y = dstpt_out[1];
        }
        return retVal;
    }

    public int edgeDst(int edge) {
        return Subdiv2D.edgeDst_1(this.nativeObj, edge);
    }

    protected void finalize() throws Throwable {
        Subdiv2D.delete(this.nativeObj);
    }

    private static native long Subdiv2D_0();

    private static native long Subdiv2D_1(int var0, int var1, int var2, int var3);

    private static native void initDelaunay_0(long var0, int var2, int var3, int var4, int var5);

    private static native int insert_0(long var0, double var2, double var4);

    private static native void insert_1(long var0, long var2);

    private static native int locate_0(long var0, double var2, double var4, double[] var6, double[] var7);

    private static native int findNearest_0(long var0, double var2, double var4, double[] var6);

    private static native int findNearest_1(long var0, double var2, double var4);

    private static native void getEdgeList_0(long var0, long var2);

    private static native void getLeadingEdgeList_0(long var0, long var2);

    private static native void getTriangleList_0(long var0, long var2);

    private static native void getVoronoiFacetList_0(long var0, long var2, long var4, long var6);

    private static native double[] getVertex_0(long var0, int var2, double[] var3);

    private static native double[] getVertex_1(long var0, int var2);

    private static native int getEdge_0(long var0, int var2, int var3);

    private static native int nextEdge_0(long var0, int var2);

    private static native int rotateEdge_0(long var0, int var2, int var3);

    private static native int symEdge_0(long var0, int var2);

    private static native int edgeOrg_0(long var0, int var2, double[] var3);

    private static native int edgeOrg_1(long var0, int var2);

    private static native int edgeDst_0(long var0, int var2, double[] var3);

    private static native int edgeDst_1(long var0, int var2);

    private static native void delete(long var0);
}

