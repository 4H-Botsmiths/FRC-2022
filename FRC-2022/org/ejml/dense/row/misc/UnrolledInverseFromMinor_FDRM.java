/*
 * Decompiled with CFR 0.150.
 */
package org.ejml.dense.row.misc;

import org.ejml.data.FMatrixRMaj;

public class UnrolledInverseFromMinor_FDRM {
    public static final int MAX = 5;

    public static void inv(FMatrixRMaj mat, FMatrixRMaj inv) {
        float max = Math.abs(mat.data[0]);
        int N = mat.getNumElements();
        for (int i = 1; i < N; ++i) {
            float a = Math.abs(mat.data[i]);
            if (!(a > max)) continue;
            max = a;
        }
        switch (mat.numRows) {
            case 2: {
                UnrolledInverseFromMinor_FDRM.inv2(mat, inv, 1.0f / max);
                break;
            }
            case 3: {
                UnrolledInverseFromMinor_FDRM.inv3(mat, inv, 1.0f / max);
                break;
            }
            case 4: {
                UnrolledInverseFromMinor_FDRM.inv4(mat, inv, 1.0f / max);
                break;
            }
            case 5: {
                UnrolledInverseFromMinor_FDRM.inv5(mat, inv, 1.0f / max);
                break;
            }
            default: {
                throw new IllegalArgumentException("Not supported");
            }
        }
    }

    public static void inv2(FMatrixRMaj mat, FMatrixRMaj inv, float scale) {
        float a22;
        float[] data = mat.data;
        float a11 = data[0] * scale;
        float a12 = data[1] * scale;
        float a21 = data[2] * scale;
        float m11 = a22 = data[3] * scale;
        float m12 = -a21;
        float m21 = -a12;
        float m22 = a11;
        float det = (a11 * m11 + a12 * m12) / scale;
        data = inv.data;
        data[0] = m11 / det;
        data[1] = m21 / det;
        data[2] = m12 / det;
        data[3] = m22 / det;
    }

    public static void inv3(FMatrixRMaj mat, FMatrixRMaj inv, float scale) {
        float[] data = mat.data;
        float a11 = data[0] * scale;
        float a12 = data[1] * scale;
        float a13 = data[2] * scale;
        float a21 = data[3] * scale;
        float a22 = data[4] * scale;
        float a23 = data[5] * scale;
        float a31 = data[6] * scale;
        float a32 = data[7] * scale;
        float a33 = data[8] * scale;
        float m11 = a22 * a33 - a23 * a32;
        float m12 = -(a21 * a33 - a23 * a31);
        float m13 = a21 * a32 - a22 * a31;
        float m21 = -(a12 * a33 - a13 * a32);
        float m22 = a11 * a33 - a13 * a31;
        float m23 = -(a11 * a32 - a12 * a31);
        float m31 = a12 * a23 - a13 * a22;
        float m32 = -(a11 * a23 - a13 * a21);
        float m33 = a11 * a22 - a12 * a21;
        float det = (a11 * m11 + a12 * m12 + a13 * m13) / scale;
        data = inv.data;
        data[0] = m11 / det;
        data[1] = m21 / det;
        data[2] = m31 / det;
        data[3] = m12 / det;
        data[4] = m22 / det;
        data[5] = m32 / det;
        data[6] = m13 / det;
        data[7] = m23 / det;
        data[8] = m33 / det;
    }

    public static void inv4(FMatrixRMaj mat, FMatrixRMaj inv, float scale) {
        float[] data = mat.data;
        float a11 = data[0] * scale;
        float a12 = data[1] * scale;
        float a13 = data[2] * scale;
        float a14 = data[3] * scale;
        float a21 = data[4] * scale;
        float a22 = data[5] * scale;
        float a23 = data[6] * scale;
        float a24 = data[7] * scale;
        float a31 = data[8] * scale;
        float a32 = data[9] * scale;
        float a33 = data[10] * scale;
        float a34 = data[11] * scale;
        float a41 = data[12] * scale;
        float a42 = data[13] * scale;
        float a43 = data[14] * scale;
        float a44 = data[15] * scale;
        float m11 = a22 * (a33 * a44 - a34 * a43) - a23 * (a32 * a44 - a34 * a42) + a24 * (a32 * a43 - a33 * a42);
        float m12 = -(a21 * (a33 * a44 - a34 * a43) - a23 * (a31 * a44 - a34 * a41) + a24 * (a31 * a43 - a33 * a41));
        float m13 = a21 * (a32 * a44 - a34 * a42) - a22 * (a31 * a44 - a34 * a41) + a24 * (a31 * a42 - a32 * a41);
        float m14 = -(a21 * (a32 * a43 - a33 * a42) - a22 * (a31 * a43 - a33 * a41) + a23 * (a31 * a42 - a32 * a41));
        float m21 = -(a12 * (a33 * a44 - a34 * a43) - a13 * (a32 * a44 - a34 * a42) + a14 * (a32 * a43 - a33 * a42));
        float m22 = a11 * (a33 * a44 - a34 * a43) - a13 * (a31 * a44 - a34 * a41) + a14 * (a31 * a43 - a33 * a41);
        float m23 = -(a11 * (a32 * a44 - a34 * a42) - a12 * (a31 * a44 - a34 * a41) + a14 * (a31 * a42 - a32 * a41));
        float m24 = a11 * (a32 * a43 - a33 * a42) - a12 * (a31 * a43 - a33 * a41) + a13 * (a31 * a42 - a32 * a41);
        float m31 = a12 * (a23 * a44 - a24 * a43) - a13 * (a22 * a44 - a24 * a42) + a14 * (a22 * a43 - a23 * a42);
        float m32 = -(a11 * (a23 * a44 - a24 * a43) - a13 * (a21 * a44 - a24 * a41) + a14 * (a21 * a43 - a23 * a41));
        float m33 = a11 * (a22 * a44 - a24 * a42) - a12 * (a21 * a44 - a24 * a41) + a14 * (a21 * a42 - a22 * a41);
        float m34 = -(a11 * (a22 * a43 - a23 * a42) - a12 * (a21 * a43 - a23 * a41) + a13 * (a21 * a42 - a22 * a41));
        float m41 = -(a12 * (a23 * a34 - a24 * a33) - a13 * (a22 * a34 - a24 * a32) + a14 * (a22 * a33 - a23 * a32));
        float m42 = a11 * (a23 * a34 - a24 * a33) - a13 * (a21 * a34 - a24 * a31) + a14 * (a21 * a33 - a23 * a31);
        float m43 = -(a11 * (a22 * a34 - a24 * a32) - a12 * (a21 * a34 - a24 * a31) + a14 * (a21 * a32 - a22 * a31));
        float m44 = a11 * (a22 * a33 - a23 * a32) - a12 * (a21 * a33 - a23 * a31) + a13 * (a21 * a32 - a22 * a31);
        float det = (a11 * m11 + a12 * m12 + a13 * m13 + a14 * m14) / scale;
        data = inv.data;
        data[0] = m11 / det;
        data[1] = m21 / det;
        data[2] = m31 / det;
        data[3] = m41 / det;
        data[4] = m12 / det;
        data[5] = m22 / det;
        data[6] = m32 / det;
        data[7] = m42 / det;
        data[8] = m13 / det;
        data[9] = m23 / det;
        data[10] = m33 / det;
        data[11] = m43 / det;
        data[12] = m14 / det;
        data[13] = m24 / det;
        data[14] = m34 / det;
        data[15] = m44 / det;
    }

    public static void inv5(FMatrixRMaj mat, FMatrixRMaj inv, float scale) {
        float[] data = mat.data;
        float a11 = data[0] * scale;
        float a12 = data[1] * scale;
        float a13 = data[2] * scale;
        float a14 = data[3] * scale;
        float a15 = data[4] * scale;
        float a21 = data[5] * scale;
        float a22 = data[6] * scale;
        float a23 = data[7] * scale;
        float a24 = data[8] * scale;
        float a25 = data[9] * scale;
        float a31 = data[10] * scale;
        float a32 = data[11] * scale;
        float a33 = data[12] * scale;
        float a34 = data[13] * scale;
        float a35 = data[14] * scale;
        float a41 = data[15] * scale;
        float a42 = data[16] * scale;
        float a43 = data[17] * scale;
        float a44 = data[18] * scale;
        float a45 = data[19] * scale;
        float a51 = data[20] * scale;
        float a52 = data[21] * scale;
        float a53 = data[22] * scale;
        float a54 = data[23] * scale;
        float a55 = data[24] * scale;
        float m11 = a22 * (a33 * (a44 * a55 - a45 * a54) - a34 * (a43 * a55 - a45 * a53) + a35 * (a43 * a54 - a44 * a53)) - a23 * (a32 * (a44 * a55 - a45 * a54) - a34 * (a42 * a55 - a45 * a52) + a35 * (a42 * a54 - a44 * a52)) + a24 * (a32 * (a43 * a55 - a45 * a53) - a33 * (a42 * a55 - a45 * a52) + a35 * (a42 * a53 - a43 * a52)) - a25 * (a32 * (a43 * a54 - a44 * a53) - a33 * (a42 * a54 - a44 * a52) + a34 * (a42 * a53 - a43 * a52));
        float m12 = -(a21 * (a33 * (a44 * a55 - a45 * a54) - a34 * (a43 * a55 - a45 * a53) + a35 * (a43 * a54 - a44 * a53)) - a23 * (a31 * (a44 * a55 - a45 * a54) - a34 * (a41 * a55 - a45 * a51) + a35 * (a41 * a54 - a44 * a51)) + a24 * (a31 * (a43 * a55 - a45 * a53) - a33 * (a41 * a55 - a45 * a51) + a35 * (a41 * a53 - a43 * a51)) - a25 * (a31 * (a43 * a54 - a44 * a53) - a33 * (a41 * a54 - a44 * a51) + a34 * (a41 * a53 - a43 * a51)));
        float m13 = a21 * (a32 * (a44 * a55 - a45 * a54) - a34 * (a42 * a55 - a45 * a52) + a35 * (a42 * a54 - a44 * a52)) - a22 * (a31 * (a44 * a55 - a45 * a54) - a34 * (a41 * a55 - a45 * a51) + a35 * (a41 * a54 - a44 * a51)) + a24 * (a31 * (a42 * a55 - a45 * a52) - a32 * (a41 * a55 - a45 * a51) + a35 * (a41 * a52 - a42 * a51)) - a25 * (a31 * (a42 * a54 - a44 * a52) - a32 * (a41 * a54 - a44 * a51) + a34 * (a41 * a52 - a42 * a51));
        float m14 = -(a21 * (a32 * (a43 * a55 - a45 * a53) - a33 * (a42 * a55 - a45 * a52) + a35 * (a42 * a53 - a43 * a52)) - a22 * (a31 * (a43 * a55 - a45 * a53) - a33 * (a41 * a55 - a45 * a51) + a35 * (a41 * a53 - a43 * a51)) + a23 * (a31 * (a42 * a55 - a45 * a52) - a32 * (a41 * a55 - a45 * a51) + a35 * (a41 * a52 - a42 * a51)) - a25 * (a31 * (a42 * a53 - a43 * a52) - a32 * (a41 * a53 - a43 * a51) + a33 * (a41 * a52 - a42 * a51)));
        float m15 = a21 * (a32 * (a43 * a54 - a44 * a53) - a33 * (a42 * a54 - a44 * a52) + a34 * (a42 * a53 - a43 * a52)) - a22 * (a31 * (a43 * a54 - a44 * a53) - a33 * (a41 * a54 - a44 * a51) + a34 * (a41 * a53 - a43 * a51)) + a23 * (a31 * (a42 * a54 - a44 * a52) - a32 * (a41 * a54 - a44 * a51) + a34 * (a41 * a52 - a42 * a51)) - a24 * (a31 * (a42 * a53 - a43 * a52) - a32 * (a41 * a53 - a43 * a51) + a33 * (a41 * a52 - a42 * a51));
        float m21 = -(a12 * (a33 * (a44 * a55 - a45 * a54) - a34 * (a43 * a55 - a45 * a53) + a35 * (a43 * a54 - a44 * a53)) - a13 * (a32 * (a44 * a55 - a45 * a54) - a34 * (a42 * a55 - a45 * a52) + a35 * (a42 * a54 - a44 * a52)) + a14 * (a32 * (a43 * a55 - a45 * a53) - a33 * (a42 * a55 - a45 * a52) + a35 * (a42 * a53 - a43 * a52)) - a15 * (a32 * (a43 * a54 - a44 * a53) - a33 * (a42 * a54 - a44 * a52) + a34 * (a42 * a53 - a43 * a52)));
        float m22 = a11 * (a33 * (a44 * a55 - a45 * a54) - a34 * (a43 * a55 - a45 * a53) + a35 * (a43 * a54 - a44 * a53)) - a13 * (a31 * (a44 * a55 - a45 * a54) - a34 * (a41 * a55 - a45 * a51) + a35 * (a41 * a54 - a44 * a51)) + a14 * (a31 * (a43 * a55 - a45 * a53) - a33 * (a41 * a55 - a45 * a51) + a35 * (a41 * a53 - a43 * a51)) - a15 * (a31 * (a43 * a54 - a44 * a53) - a33 * (a41 * a54 - a44 * a51) + a34 * (a41 * a53 - a43 * a51));
        float m23 = -(a11 * (a32 * (a44 * a55 - a45 * a54) - a34 * (a42 * a55 - a45 * a52) + a35 * (a42 * a54 - a44 * a52)) - a12 * (a31 * (a44 * a55 - a45 * a54) - a34 * (a41 * a55 - a45 * a51) + a35 * (a41 * a54 - a44 * a51)) + a14 * (a31 * (a42 * a55 - a45 * a52) - a32 * (a41 * a55 - a45 * a51) + a35 * (a41 * a52 - a42 * a51)) - a15 * (a31 * (a42 * a54 - a44 * a52) - a32 * (a41 * a54 - a44 * a51) + a34 * (a41 * a52 - a42 * a51)));
        float m24 = a11 * (a32 * (a43 * a55 - a45 * a53) - a33 * (a42 * a55 - a45 * a52) + a35 * (a42 * a53 - a43 * a52)) - a12 * (a31 * (a43 * a55 - a45 * a53) - a33 * (a41 * a55 - a45 * a51) + a35 * (a41 * a53 - a43 * a51)) + a13 * (a31 * (a42 * a55 - a45 * a52) - a32 * (a41 * a55 - a45 * a51) + a35 * (a41 * a52 - a42 * a51)) - a15 * (a31 * (a42 * a53 - a43 * a52) - a32 * (a41 * a53 - a43 * a51) + a33 * (a41 * a52 - a42 * a51));
        float m25 = -(a11 * (a32 * (a43 * a54 - a44 * a53) - a33 * (a42 * a54 - a44 * a52) + a34 * (a42 * a53 - a43 * a52)) - a12 * (a31 * (a43 * a54 - a44 * a53) - a33 * (a41 * a54 - a44 * a51) + a34 * (a41 * a53 - a43 * a51)) + a13 * (a31 * (a42 * a54 - a44 * a52) - a32 * (a41 * a54 - a44 * a51) + a34 * (a41 * a52 - a42 * a51)) - a14 * (a31 * (a42 * a53 - a43 * a52) - a32 * (a41 * a53 - a43 * a51) + a33 * (a41 * a52 - a42 * a51)));
        float m31 = a12 * (a23 * (a44 * a55 - a45 * a54) - a24 * (a43 * a55 - a45 * a53) + a25 * (a43 * a54 - a44 * a53)) - a13 * (a22 * (a44 * a55 - a45 * a54) - a24 * (a42 * a55 - a45 * a52) + a25 * (a42 * a54 - a44 * a52)) + a14 * (a22 * (a43 * a55 - a45 * a53) - a23 * (a42 * a55 - a45 * a52) + a25 * (a42 * a53 - a43 * a52)) - a15 * (a22 * (a43 * a54 - a44 * a53) - a23 * (a42 * a54 - a44 * a52) + a24 * (a42 * a53 - a43 * a52));
        float m32 = -(a11 * (a23 * (a44 * a55 - a45 * a54) - a24 * (a43 * a55 - a45 * a53) + a25 * (a43 * a54 - a44 * a53)) - a13 * (a21 * (a44 * a55 - a45 * a54) - a24 * (a41 * a55 - a45 * a51) + a25 * (a41 * a54 - a44 * a51)) + a14 * (a21 * (a43 * a55 - a45 * a53) - a23 * (a41 * a55 - a45 * a51) + a25 * (a41 * a53 - a43 * a51)) - a15 * (a21 * (a43 * a54 - a44 * a53) - a23 * (a41 * a54 - a44 * a51) + a24 * (a41 * a53 - a43 * a51)));
        float m33 = a11 * (a22 * (a44 * a55 - a45 * a54) - a24 * (a42 * a55 - a45 * a52) + a25 * (a42 * a54 - a44 * a52)) - a12 * (a21 * (a44 * a55 - a45 * a54) - a24 * (a41 * a55 - a45 * a51) + a25 * (a41 * a54 - a44 * a51)) + a14 * (a21 * (a42 * a55 - a45 * a52) - a22 * (a41 * a55 - a45 * a51) + a25 * (a41 * a52 - a42 * a51)) - a15 * (a21 * (a42 * a54 - a44 * a52) - a22 * (a41 * a54 - a44 * a51) + a24 * (a41 * a52 - a42 * a51));
        float m34 = -(a11 * (a22 * (a43 * a55 - a45 * a53) - a23 * (a42 * a55 - a45 * a52) + a25 * (a42 * a53 - a43 * a52)) - a12 * (a21 * (a43 * a55 - a45 * a53) - a23 * (a41 * a55 - a45 * a51) + a25 * (a41 * a53 - a43 * a51)) + a13 * (a21 * (a42 * a55 - a45 * a52) - a22 * (a41 * a55 - a45 * a51) + a25 * (a41 * a52 - a42 * a51)) - a15 * (a21 * (a42 * a53 - a43 * a52) - a22 * (a41 * a53 - a43 * a51) + a23 * (a41 * a52 - a42 * a51)));
        float m35 = a11 * (a22 * (a43 * a54 - a44 * a53) - a23 * (a42 * a54 - a44 * a52) + a24 * (a42 * a53 - a43 * a52)) - a12 * (a21 * (a43 * a54 - a44 * a53) - a23 * (a41 * a54 - a44 * a51) + a24 * (a41 * a53 - a43 * a51)) + a13 * (a21 * (a42 * a54 - a44 * a52) - a22 * (a41 * a54 - a44 * a51) + a24 * (a41 * a52 - a42 * a51)) - a14 * (a21 * (a42 * a53 - a43 * a52) - a22 * (a41 * a53 - a43 * a51) + a23 * (a41 * a52 - a42 * a51));
        float m41 = -(a12 * (a23 * (a34 * a55 - a35 * a54) - a24 * (a33 * a55 - a35 * a53) + a25 * (a33 * a54 - a34 * a53)) - a13 * (a22 * (a34 * a55 - a35 * a54) - a24 * (a32 * a55 - a35 * a52) + a25 * (a32 * a54 - a34 * a52)) + a14 * (a22 * (a33 * a55 - a35 * a53) - a23 * (a32 * a55 - a35 * a52) + a25 * (a32 * a53 - a33 * a52)) - a15 * (a22 * (a33 * a54 - a34 * a53) - a23 * (a32 * a54 - a34 * a52) + a24 * (a32 * a53 - a33 * a52)));
        float m42 = a11 * (a23 * (a34 * a55 - a35 * a54) - a24 * (a33 * a55 - a35 * a53) + a25 * (a33 * a54 - a34 * a53)) - a13 * (a21 * (a34 * a55 - a35 * a54) - a24 * (a31 * a55 - a35 * a51) + a25 * (a31 * a54 - a34 * a51)) + a14 * (a21 * (a33 * a55 - a35 * a53) - a23 * (a31 * a55 - a35 * a51) + a25 * (a31 * a53 - a33 * a51)) - a15 * (a21 * (a33 * a54 - a34 * a53) - a23 * (a31 * a54 - a34 * a51) + a24 * (a31 * a53 - a33 * a51));
        float m43 = -(a11 * (a22 * (a34 * a55 - a35 * a54) - a24 * (a32 * a55 - a35 * a52) + a25 * (a32 * a54 - a34 * a52)) - a12 * (a21 * (a34 * a55 - a35 * a54) - a24 * (a31 * a55 - a35 * a51) + a25 * (a31 * a54 - a34 * a51)) + a14 * (a21 * (a32 * a55 - a35 * a52) - a22 * (a31 * a55 - a35 * a51) + a25 * (a31 * a52 - a32 * a51)) - a15 * (a21 * (a32 * a54 - a34 * a52) - a22 * (a31 * a54 - a34 * a51) + a24 * (a31 * a52 - a32 * a51)));
        float m44 = a11 * (a22 * (a33 * a55 - a35 * a53) - a23 * (a32 * a55 - a35 * a52) + a25 * (a32 * a53 - a33 * a52)) - a12 * (a21 * (a33 * a55 - a35 * a53) - a23 * (a31 * a55 - a35 * a51) + a25 * (a31 * a53 - a33 * a51)) + a13 * (a21 * (a32 * a55 - a35 * a52) - a22 * (a31 * a55 - a35 * a51) + a25 * (a31 * a52 - a32 * a51)) - a15 * (a21 * (a32 * a53 - a33 * a52) - a22 * (a31 * a53 - a33 * a51) + a23 * (a31 * a52 - a32 * a51));
        float m45 = -(a11 * (a22 * (a33 * a54 - a34 * a53) - a23 * (a32 * a54 - a34 * a52) + a24 * (a32 * a53 - a33 * a52)) - a12 * (a21 * (a33 * a54 - a34 * a53) - a23 * (a31 * a54 - a34 * a51) + a24 * (a31 * a53 - a33 * a51)) + a13 * (a21 * (a32 * a54 - a34 * a52) - a22 * (a31 * a54 - a34 * a51) + a24 * (a31 * a52 - a32 * a51)) - a14 * (a21 * (a32 * a53 - a33 * a52) - a22 * (a31 * a53 - a33 * a51) + a23 * (a31 * a52 - a32 * a51)));
        float m51 = a12 * (a23 * (a34 * a45 - a35 * a44) - a24 * (a33 * a45 - a35 * a43) + a25 * (a33 * a44 - a34 * a43)) - a13 * (a22 * (a34 * a45 - a35 * a44) - a24 * (a32 * a45 - a35 * a42) + a25 * (a32 * a44 - a34 * a42)) + a14 * (a22 * (a33 * a45 - a35 * a43) - a23 * (a32 * a45 - a35 * a42) + a25 * (a32 * a43 - a33 * a42)) - a15 * (a22 * (a33 * a44 - a34 * a43) - a23 * (a32 * a44 - a34 * a42) + a24 * (a32 * a43 - a33 * a42));
        float m52 = -(a11 * (a23 * (a34 * a45 - a35 * a44) - a24 * (a33 * a45 - a35 * a43) + a25 * (a33 * a44 - a34 * a43)) - a13 * (a21 * (a34 * a45 - a35 * a44) - a24 * (a31 * a45 - a35 * a41) + a25 * (a31 * a44 - a34 * a41)) + a14 * (a21 * (a33 * a45 - a35 * a43) - a23 * (a31 * a45 - a35 * a41) + a25 * (a31 * a43 - a33 * a41)) - a15 * (a21 * (a33 * a44 - a34 * a43) - a23 * (a31 * a44 - a34 * a41) + a24 * (a31 * a43 - a33 * a41)));
        float m53 = a11 * (a22 * (a34 * a45 - a35 * a44) - a24 * (a32 * a45 - a35 * a42) + a25 * (a32 * a44 - a34 * a42)) - a12 * (a21 * (a34 * a45 - a35 * a44) - a24 * (a31 * a45 - a35 * a41) + a25 * (a31 * a44 - a34 * a41)) + a14 * (a21 * (a32 * a45 - a35 * a42) - a22 * (a31 * a45 - a35 * a41) + a25 * (a31 * a42 - a32 * a41)) - a15 * (a21 * (a32 * a44 - a34 * a42) - a22 * (a31 * a44 - a34 * a41) + a24 * (a31 * a42 - a32 * a41));
        float m54 = -(a11 * (a22 * (a33 * a45 - a35 * a43) - a23 * (a32 * a45 - a35 * a42) + a25 * (a32 * a43 - a33 * a42)) - a12 * (a21 * (a33 * a45 - a35 * a43) - a23 * (a31 * a45 - a35 * a41) + a25 * (a31 * a43 - a33 * a41)) + a13 * (a21 * (a32 * a45 - a35 * a42) - a22 * (a31 * a45 - a35 * a41) + a25 * (a31 * a42 - a32 * a41)) - a15 * (a21 * (a32 * a43 - a33 * a42) - a22 * (a31 * a43 - a33 * a41) + a23 * (a31 * a42 - a32 * a41)));
        float m55 = a11 * (a22 * (a33 * a44 - a34 * a43) - a23 * (a32 * a44 - a34 * a42) + a24 * (a32 * a43 - a33 * a42)) - a12 * (a21 * (a33 * a44 - a34 * a43) - a23 * (a31 * a44 - a34 * a41) + a24 * (a31 * a43 - a33 * a41)) + a13 * (a21 * (a32 * a44 - a34 * a42) - a22 * (a31 * a44 - a34 * a41) + a24 * (a31 * a42 - a32 * a41)) - a14 * (a21 * (a32 * a43 - a33 * a42) - a22 * (a31 * a43 - a33 * a41) + a23 * (a31 * a42 - a32 * a41));
        float det = (a11 * m11 + a12 * m12 + a13 * m13 + a14 * m14 + a15 * m15) / scale;
        data = inv.data;
        data[0] = m11 / det;
        data[1] = m21 / det;
        data[2] = m31 / det;
        data[3] = m41 / det;
        data[4] = m51 / det;
        data[5] = m12 / det;
        data[6] = m22 / det;
        data[7] = m32 / det;
        data[8] = m42 / det;
        data[9] = m52 / det;
        data[10] = m13 / det;
        data[11] = m23 / det;
        data[12] = m33 / det;
        data[13] = m43 / det;
        data[14] = m53 / det;
        data[15] = m14 / det;
        data[16] = m24 / det;
        data[17] = m34 / det;
        data[18] = m44 / det;
        data[19] = m54 / det;
        data[20] = m15 / det;
        data[21] = m25 / det;
        data[22] = m35 / det;
        data[23] = m45 / det;
        data[24] = m55 / det;
    }
}

