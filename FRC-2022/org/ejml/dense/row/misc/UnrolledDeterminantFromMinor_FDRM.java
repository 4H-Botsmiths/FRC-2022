/*
 * Decompiled with CFR 0.150.
 */
package org.ejml.dense.row.misc;

import org.ejml.data.FMatrix1Row;

public class UnrolledDeterminantFromMinor_FDRM {
    public static final int MAX = 6;

    public static float det(FMatrix1Row mat) {
        switch (mat.numRows) {
            case 2: {
                return UnrolledDeterminantFromMinor_FDRM.det2(mat);
            }
            case 3: {
                return UnrolledDeterminantFromMinor_FDRM.det3(mat);
            }
            case 4: {
                return UnrolledDeterminantFromMinor_FDRM.det4(mat);
            }
            case 5: {
                return UnrolledDeterminantFromMinor_FDRM.det5(mat);
            }
            case 6: {
                return UnrolledDeterminantFromMinor_FDRM.det6(mat);
            }
        }
        throw new IllegalArgumentException("Not supported");
    }

    public static float det2(FMatrix1Row mat) {
        float[] m = mat.data;
        return m[0] * m[3] - m[1] * m[2];
    }

    public static float det3(FMatrix1Row mat) {
        float[] m = mat.data;
        float a11 = m[0];
        float a12 = m[1];
        float a13 = m[2];
        float a21 = m[3];
        float a22 = m[4];
        float a23 = m[5];
        float a31 = m[6];
        float a32 = m[7];
        float a33 = m[8];
        float a = a11 * (a22 * a33 - a23 * a32);
        float b = a12 * (a21 * a33 - a23 * a31);
        float c = a13 * (a21 * a32 - a31 * a22);
        return a - b + c;
    }

    public static float det4(FMatrix1Row mat) {
        float[] data = mat.data;
        float a11 = data[5];
        float a12 = data[6];
        float a13 = data[7];
        float a21 = data[9];
        float a22 = data[10];
        float a23 = data[11];
        float a31 = data[13];
        float a32 = data[14];
        float a33 = data[15];
        float ret = 0.0f;
        ret += data[0] * (a11 * (a22 * a33 - a23 * a32) - a12 * (a21 * a33 - a23 * a31) + a13 * (a21 * a32 - a22 * a31));
        a11 = data[4];
        a21 = data[8];
        a31 = data[12];
        ret -= data[1] * (a11 * (a22 * a33 - a23 * a32) - a12 * (a21 * a33 - a23 * a31) + a13 * (a21 * a32 - a22 * a31));
        a12 = data[5];
        a22 = data[9];
        a32 = data[13];
        ret += data[2] * (a11 * (a22 * a33 - a23 * a32) - a12 * (a21 * a33 - a23 * a31) + a13 * (a21 * a32 - a22 * a31));
        a13 = data[6];
        a23 = data[10];
        a33 = data[14];
        return ret -= data[3] * (a11 * (a22 * a33 - a23 * a32) - a12 * (a21 * a33 - a23 * a31) + a13 * (a21 * a32 - a22 * a31));
    }

    public static float det5(FMatrix1Row mat) {
        float[] data = mat.data;
        float a11 = data[6];
        float a12 = data[7];
        float a13 = data[8];
        float a14 = data[9];
        float a21 = data[11];
        float a22 = data[12];
        float a23 = data[13];
        float a24 = data[14];
        float a31 = data[16];
        float a32 = data[17];
        float a33 = data[18];
        float a34 = data[19];
        float a41 = data[21];
        float a42 = data[22];
        float a43 = data[23];
        float a44 = data[24];
        float ret = 0.0f;
        ret += data[0] * (a11 * (a22 * (a33 * a44 - a34 * a43) - a23 * (a32 * a44 - a34 * a42) + a24 * (a32 * a43 - a33 * a42)) - a12 * (a21 * (a33 * a44 - a34 * a43) - a23 * (a31 * a44 - a34 * a41) + a24 * (a31 * a43 - a33 * a41)) + a13 * (a21 * (a32 * a44 - a34 * a42) - a22 * (a31 * a44 - a34 * a41) + a24 * (a31 * a42 - a32 * a41)) - a14 * (a21 * (a32 * a43 - a33 * a42) - a22 * (a31 * a43 - a33 * a41) + a23 * (a31 * a42 - a32 * a41)));
        a11 = data[5];
        a21 = data[10];
        a31 = data[15];
        a41 = data[20];
        ret -= data[1] * (a11 * (a22 * (a33 * a44 - a34 * a43) - a23 * (a32 * a44 - a34 * a42) + a24 * (a32 * a43 - a33 * a42)) - a12 * (a21 * (a33 * a44 - a34 * a43) - a23 * (a31 * a44 - a34 * a41) + a24 * (a31 * a43 - a33 * a41)) + a13 * (a21 * (a32 * a44 - a34 * a42) - a22 * (a31 * a44 - a34 * a41) + a24 * (a31 * a42 - a32 * a41)) - a14 * (a21 * (a32 * a43 - a33 * a42) - a22 * (a31 * a43 - a33 * a41) + a23 * (a31 * a42 - a32 * a41)));
        a12 = data[6];
        a22 = data[11];
        a32 = data[16];
        a42 = data[21];
        ret += data[2] * (a11 * (a22 * (a33 * a44 - a34 * a43) - a23 * (a32 * a44 - a34 * a42) + a24 * (a32 * a43 - a33 * a42)) - a12 * (a21 * (a33 * a44 - a34 * a43) - a23 * (a31 * a44 - a34 * a41) + a24 * (a31 * a43 - a33 * a41)) + a13 * (a21 * (a32 * a44 - a34 * a42) - a22 * (a31 * a44 - a34 * a41) + a24 * (a31 * a42 - a32 * a41)) - a14 * (a21 * (a32 * a43 - a33 * a42) - a22 * (a31 * a43 - a33 * a41) + a23 * (a31 * a42 - a32 * a41)));
        a13 = data[7];
        a23 = data[12];
        a33 = data[17];
        a43 = data[22];
        ret -= data[3] * (a11 * (a22 * (a33 * a44 - a34 * a43) - a23 * (a32 * a44 - a34 * a42) + a24 * (a32 * a43 - a33 * a42)) - a12 * (a21 * (a33 * a44 - a34 * a43) - a23 * (a31 * a44 - a34 * a41) + a24 * (a31 * a43 - a33 * a41)) + a13 * (a21 * (a32 * a44 - a34 * a42) - a22 * (a31 * a44 - a34 * a41) + a24 * (a31 * a42 - a32 * a41)) - a14 * (a21 * (a32 * a43 - a33 * a42) - a22 * (a31 * a43 - a33 * a41) + a23 * (a31 * a42 - a32 * a41)));
        a14 = data[8];
        a24 = data[13];
        a34 = data[18];
        a44 = data[23];
        return ret += data[4] * (a11 * (a22 * (a33 * a44 - a34 * a43) - a23 * (a32 * a44 - a34 * a42) + a24 * (a32 * a43 - a33 * a42)) - a12 * (a21 * (a33 * a44 - a34 * a43) - a23 * (a31 * a44 - a34 * a41) + a24 * (a31 * a43 - a33 * a41)) + a13 * (a21 * (a32 * a44 - a34 * a42) - a22 * (a31 * a44 - a34 * a41) + a24 * (a31 * a42 - a32 * a41)) - a14 * (a21 * (a32 * a43 - a33 * a42) - a22 * (a31 * a43 - a33 * a41) + a23 * (a31 * a42 - a32 * a41)));
    }

    public static float det6(FMatrix1Row mat) {
        float[] data = mat.data;
        float a11 = data[7];
        float a12 = data[8];
        float a13 = data[9];
        float a14 = data[10];
        float a15 = data[11];
        float a21 = data[13];
        float a22 = data[14];
        float a23 = data[15];
        float a24 = data[16];
        float a25 = data[17];
        float a31 = data[19];
        float a32 = data[20];
        float a33 = data[21];
        float a34 = data[22];
        float a35 = data[23];
        float a41 = data[25];
        float a42 = data[26];
        float a43 = data[27];
        float a44 = data[28];
        float a45 = data[29];
        float a51 = data[31];
        float a52 = data[32];
        float a53 = data[33];
        float a54 = data[34];
        float a55 = data[35];
        float ret = 0.0f;
        ret += data[0] * (a11 * (a22 * (a33 * (a44 * a55 - a45 * a54) - a34 * (a43 * a55 - a45 * a53) + a35 * (a43 * a54 - a44 * a53)) - a23 * (a32 * (a44 * a55 - a45 * a54) - a34 * (a42 * a55 - a45 * a52) + a35 * (a42 * a54 - a44 * a52)) + a24 * (a32 * (a43 * a55 - a45 * a53) - a33 * (a42 * a55 - a45 * a52) + a35 * (a42 * a53 - a43 * a52)) - a25 * (a32 * (a43 * a54 - a44 * a53) - a33 * (a42 * a54 - a44 * a52) + a34 * (a42 * a53 - a43 * a52))) - a12 * (a21 * (a33 * (a44 * a55 - a45 * a54) - a34 * (a43 * a55 - a45 * a53) + a35 * (a43 * a54 - a44 * a53)) - a23 * (a31 * (a44 * a55 - a45 * a54) - a34 * (a41 * a55 - a45 * a51) + a35 * (a41 * a54 - a44 * a51)) + a24 * (a31 * (a43 * a55 - a45 * a53) - a33 * (a41 * a55 - a45 * a51) + a35 * (a41 * a53 - a43 * a51)) - a25 * (a31 * (a43 * a54 - a44 * a53) - a33 * (a41 * a54 - a44 * a51) + a34 * (a41 * a53 - a43 * a51))) + a13 * (a21 * (a32 * (a44 * a55 - a45 * a54) - a34 * (a42 * a55 - a45 * a52) + a35 * (a42 * a54 - a44 * a52)) - a22 * (a31 * (a44 * a55 - a45 * a54) - a34 * (a41 * a55 - a45 * a51) + a35 * (a41 * a54 - a44 * a51)) + a24 * (a31 * (a42 * a55 - a45 * a52) - a32 * (a41 * a55 - a45 * a51) + a35 * (a41 * a52 - a42 * a51)) - a25 * (a31 * (a42 * a54 - a44 * a52) - a32 * (a41 * a54 - a44 * a51) + a34 * (a41 * a52 - a42 * a51))) - a14 * (a21 * (a32 * (a43 * a55 - a45 * a53) - a33 * (a42 * a55 - a45 * a52) + a35 * (a42 * a53 - a43 * a52)) - a22 * (a31 * (a43 * a55 - a45 * a53) - a33 * (a41 * a55 - a45 * a51) + a35 * (a41 * a53 - a43 * a51)) + a23 * (a31 * (a42 * a55 - a45 * a52) - a32 * (a41 * a55 - a45 * a51) + a35 * (a41 * a52 - a42 * a51)) - a25 * (a31 * (a42 * a53 - a43 * a52) - a32 * (a41 * a53 - a43 * a51) + a33 * (a41 * a52 - a42 * a51))) + a15 * (a21 * (a32 * (a43 * a54 - a44 * a53) - a33 * (a42 * a54 - a44 * a52) + a34 * (a42 * a53 - a43 * a52)) - a22 * (a31 * (a43 * a54 - a44 * a53) - a33 * (a41 * a54 - a44 * a51) + a34 * (a41 * a53 - a43 * a51)) + a23 * (a31 * (a42 * a54 - a44 * a52) - a32 * (a41 * a54 - a44 * a51) + a34 * (a41 * a52 - a42 * a51)) - a24 * (a31 * (a42 * a53 - a43 * a52) - a32 * (a41 * a53 - a43 * a51) + a33 * (a41 * a52 - a42 * a51))));
        a11 = data[6];
        a21 = data[12];
        a31 = data[18];
        a41 = data[24];
        a51 = data[30];
        ret -= data[1] * (a11 * (a22 * (a33 * (a44 * a55 - a45 * a54) - a34 * (a43 * a55 - a45 * a53) + a35 * (a43 * a54 - a44 * a53)) - a23 * (a32 * (a44 * a55 - a45 * a54) - a34 * (a42 * a55 - a45 * a52) + a35 * (a42 * a54 - a44 * a52)) + a24 * (a32 * (a43 * a55 - a45 * a53) - a33 * (a42 * a55 - a45 * a52) + a35 * (a42 * a53 - a43 * a52)) - a25 * (a32 * (a43 * a54 - a44 * a53) - a33 * (a42 * a54 - a44 * a52) + a34 * (a42 * a53 - a43 * a52))) - a12 * (a21 * (a33 * (a44 * a55 - a45 * a54) - a34 * (a43 * a55 - a45 * a53) + a35 * (a43 * a54 - a44 * a53)) - a23 * (a31 * (a44 * a55 - a45 * a54) - a34 * (a41 * a55 - a45 * a51) + a35 * (a41 * a54 - a44 * a51)) + a24 * (a31 * (a43 * a55 - a45 * a53) - a33 * (a41 * a55 - a45 * a51) + a35 * (a41 * a53 - a43 * a51)) - a25 * (a31 * (a43 * a54 - a44 * a53) - a33 * (a41 * a54 - a44 * a51) + a34 * (a41 * a53 - a43 * a51))) + a13 * (a21 * (a32 * (a44 * a55 - a45 * a54) - a34 * (a42 * a55 - a45 * a52) + a35 * (a42 * a54 - a44 * a52)) - a22 * (a31 * (a44 * a55 - a45 * a54) - a34 * (a41 * a55 - a45 * a51) + a35 * (a41 * a54 - a44 * a51)) + a24 * (a31 * (a42 * a55 - a45 * a52) - a32 * (a41 * a55 - a45 * a51) + a35 * (a41 * a52 - a42 * a51)) - a25 * (a31 * (a42 * a54 - a44 * a52) - a32 * (a41 * a54 - a44 * a51) + a34 * (a41 * a52 - a42 * a51))) - a14 * (a21 * (a32 * (a43 * a55 - a45 * a53) - a33 * (a42 * a55 - a45 * a52) + a35 * (a42 * a53 - a43 * a52)) - a22 * (a31 * (a43 * a55 - a45 * a53) - a33 * (a41 * a55 - a45 * a51) + a35 * (a41 * a53 - a43 * a51)) + a23 * (a31 * (a42 * a55 - a45 * a52) - a32 * (a41 * a55 - a45 * a51) + a35 * (a41 * a52 - a42 * a51)) - a25 * (a31 * (a42 * a53 - a43 * a52) - a32 * (a41 * a53 - a43 * a51) + a33 * (a41 * a52 - a42 * a51))) + a15 * (a21 * (a32 * (a43 * a54 - a44 * a53) - a33 * (a42 * a54 - a44 * a52) + a34 * (a42 * a53 - a43 * a52)) - a22 * (a31 * (a43 * a54 - a44 * a53) - a33 * (a41 * a54 - a44 * a51) + a34 * (a41 * a53 - a43 * a51)) + a23 * (a31 * (a42 * a54 - a44 * a52) - a32 * (a41 * a54 - a44 * a51) + a34 * (a41 * a52 - a42 * a51)) - a24 * (a31 * (a42 * a53 - a43 * a52) - a32 * (a41 * a53 - a43 * a51) + a33 * (a41 * a52 - a42 * a51))));
        a12 = data[7];
        a22 = data[13];
        a32 = data[19];
        a42 = data[25];
        a52 = data[31];
        ret += data[2] * (a11 * (a22 * (a33 * (a44 * a55 - a45 * a54) - a34 * (a43 * a55 - a45 * a53) + a35 * (a43 * a54 - a44 * a53)) - a23 * (a32 * (a44 * a55 - a45 * a54) - a34 * (a42 * a55 - a45 * a52) + a35 * (a42 * a54 - a44 * a52)) + a24 * (a32 * (a43 * a55 - a45 * a53) - a33 * (a42 * a55 - a45 * a52) + a35 * (a42 * a53 - a43 * a52)) - a25 * (a32 * (a43 * a54 - a44 * a53) - a33 * (a42 * a54 - a44 * a52) + a34 * (a42 * a53 - a43 * a52))) - a12 * (a21 * (a33 * (a44 * a55 - a45 * a54) - a34 * (a43 * a55 - a45 * a53) + a35 * (a43 * a54 - a44 * a53)) - a23 * (a31 * (a44 * a55 - a45 * a54) - a34 * (a41 * a55 - a45 * a51) + a35 * (a41 * a54 - a44 * a51)) + a24 * (a31 * (a43 * a55 - a45 * a53) - a33 * (a41 * a55 - a45 * a51) + a35 * (a41 * a53 - a43 * a51)) - a25 * (a31 * (a43 * a54 - a44 * a53) - a33 * (a41 * a54 - a44 * a51) + a34 * (a41 * a53 - a43 * a51))) + a13 * (a21 * (a32 * (a44 * a55 - a45 * a54) - a34 * (a42 * a55 - a45 * a52) + a35 * (a42 * a54 - a44 * a52)) - a22 * (a31 * (a44 * a55 - a45 * a54) - a34 * (a41 * a55 - a45 * a51) + a35 * (a41 * a54 - a44 * a51)) + a24 * (a31 * (a42 * a55 - a45 * a52) - a32 * (a41 * a55 - a45 * a51) + a35 * (a41 * a52 - a42 * a51)) - a25 * (a31 * (a42 * a54 - a44 * a52) - a32 * (a41 * a54 - a44 * a51) + a34 * (a41 * a52 - a42 * a51))) - a14 * (a21 * (a32 * (a43 * a55 - a45 * a53) - a33 * (a42 * a55 - a45 * a52) + a35 * (a42 * a53 - a43 * a52)) - a22 * (a31 * (a43 * a55 - a45 * a53) - a33 * (a41 * a55 - a45 * a51) + a35 * (a41 * a53 - a43 * a51)) + a23 * (a31 * (a42 * a55 - a45 * a52) - a32 * (a41 * a55 - a45 * a51) + a35 * (a41 * a52 - a42 * a51)) - a25 * (a31 * (a42 * a53 - a43 * a52) - a32 * (a41 * a53 - a43 * a51) + a33 * (a41 * a52 - a42 * a51))) + a15 * (a21 * (a32 * (a43 * a54 - a44 * a53) - a33 * (a42 * a54 - a44 * a52) + a34 * (a42 * a53 - a43 * a52)) - a22 * (a31 * (a43 * a54 - a44 * a53) - a33 * (a41 * a54 - a44 * a51) + a34 * (a41 * a53 - a43 * a51)) + a23 * (a31 * (a42 * a54 - a44 * a52) - a32 * (a41 * a54 - a44 * a51) + a34 * (a41 * a52 - a42 * a51)) - a24 * (a31 * (a42 * a53 - a43 * a52) - a32 * (a41 * a53 - a43 * a51) + a33 * (a41 * a52 - a42 * a51))));
        a13 = data[8];
        a23 = data[14];
        a33 = data[20];
        a43 = data[26];
        a53 = data[32];
        ret -= data[3] * (a11 * (a22 * (a33 * (a44 * a55 - a45 * a54) - a34 * (a43 * a55 - a45 * a53) + a35 * (a43 * a54 - a44 * a53)) - a23 * (a32 * (a44 * a55 - a45 * a54) - a34 * (a42 * a55 - a45 * a52) + a35 * (a42 * a54 - a44 * a52)) + a24 * (a32 * (a43 * a55 - a45 * a53) - a33 * (a42 * a55 - a45 * a52) + a35 * (a42 * a53 - a43 * a52)) - a25 * (a32 * (a43 * a54 - a44 * a53) - a33 * (a42 * a54 - a44 * a52) + a34 * (a42 * a53 - a43 * a52))) - a12 * (a21 * (a33 * (a44 * a55 - a45 * a54) - a34 * (a43 * a55 - a45 * a53) + a35 * (a43 * a54 - a44 * a53)) - a23 * (a31 * (a44 * a55 - a45 * a54) - a34 * (a41 * a55 - a45 * a51) + a35 * (a41 * a54 - a44 * a51)) + a24 * (a31 * (a43 * a55 - a45 * a53) - a33 * (a41 * a55 - a45 * a51) + a35 * (a41 * a53 - a43 * a51)) - a25 * (a31 * (a43 * a54 - a44 * a53) - a33 * (a41 * a54 - a44 * a51) + a34 * (a41 * a53 - a43 * a51))) + a13 * (a21 * (a32 * (a44 * a55 - a45 * a54) - a34 * (a42 * a55 - a45 * a52) + a35 * (a42 * a54 - a44 * a52)) - a22 * (a31 * (a44 * a55 - a45 * a54) - a34 * (a41 * a55 - a45 * a51) + a35 * (a41 * a54 - a44 * a51)) + a24 * (a31 * (a42 * a55 - a45 * a52) - a32 * (a41 * a55 - a45 * a51) + a35 * (a41 * a52 - a42 * a51)) - a25 * (a31 * (a42 * a54 - a44 * a52) - a32 * (a41 * a54 - a44 * a51) + a34 * (a41 * a52 - a42 * a51))) - a14 * (a21 * (a32 * (a43 * a55 - a45 * a53) - a33 * (a42 * a55 - a45 * a52) + a35 * (a42 * a53 - a43 * a52)) - a22 * (a31 * (a43 * a55 - a45 * a53) - a33 * (a41 * a55 - a45 * a51) + a35 * (a41 * a53 - a43 * a51)) + a23 * (a31 * (a42 * a55 - a45 * a52) - a32 * (a41 * a55 - a45 * a51) + a35 * (a41 * a52 - a42 * a51)) - a25 * (a31 * (a42 * a53 - a43 * a52) - a32 * (a41 * a53 - a43 * a51) + a33 * (a41 * a52 - a42 * a51))) + a15 * (a21 * (a32 * (a43 * a54 - a44 * a53) - a33 * (a42 * a54 - a44 * a52) + a34 * (a42 * a53 - a43 * a52)) - a22 * (a31 * (a43 * a54 - a44 * a53) - a33 * (a41 * a54 - a44 * a51) + a34 * (a41 * a53 - a43 * a51)) + a23 * (a31 * (a42 * a54 - a44 * a52) - a32 * (a41 * a54 - a44 * a51) + a34 * (a41 * a52 - a42 * a51)) - a24 * (a31 * (a42 * a53 - a43 * a52) - a32 * (a41 * a53 - a43 * a51) + a33 * (a41 * a52 - a42 * a51))));
        a14 = data[9];
        a24 = data[15];
        a34 = data[21];
        a44 = data[27];
        a54 = data[33];
        ret += data[4] * (a11 * (a22 * (a33 * (a44 * a55 - a45 * a54) - a34 * (a43 * a55 - a45 * a53) + a35 * (a43 * a54 - a44 * a53)) - a23 * (a32 * (a44 * a55 - a45 * a54) - a34 * (a42 * a55 - a45 * a52) + a35 * (a42 * a54 - a44 * a52)) + a24 * (a32 * (a43 * a55 - a45 * a53) - a33 * (a42 * a55 - a45 * a52) + a35 * (a42 * a53 - a43 * a52)) - a25 * (a32 * (a43 * a54 - a44 * a53) - a33 * (a42 * a54 - a44 * a52) + a34 * (a42 * a53 - a43 * a52))) - a12 * (a21 * (a33 * (a44 * a55 - a45 * a54) - a34 * (a43 * a55 - a45 * a53) + a35 * (a43 * a54 - a44 * a53)) - a23 * (a31 * (a44 * a55 - a45 * a54) - a34 * (a41 * a55 - a45 * a51) + a35 * (a41 * a54 - a44 * a51)) + a24 * (a31 * (a43 * a55 - a45 * a53) - a33 * (a41 * a55 - a45 * a51) + a35 * (a41 * a53 - a43 * a51)) - a25 * (a31 * (a43 * a54 - a44 * a53) - a33 * (a41 * a54 - a44 * a51) + a34 * (a41 * a53 - a43 * a51))) + a13 * (a21 * (a32 * (a44 * a55 - a45 * a54) - a34 * (a42 * a55 - a45 * a52) + a35 * (a42 * a54 - a44 * a52)) - a22 * (a31 * (a44 * a55 - a45 * a54) - a34 * (a41 * a55 - a45 * a51) + a35 * (a41 * a54 - a44 * a51)) + a24 * (a31 * (a42 * a55 - a45 * a52) - a32 * (a41 * a55 - a45 * a51) + a35 * (a41 * a52 - a42 * a51)) - a25 * (a31 * (a42 * a54 - a44 * a52) - a32 * (a41 * a54 - a44 * a51) + a34 * (a41 * a52 - a42 * a51))) - a14 * (a21 * (a32 * (a43 * a55 - a45 * a53) - a33 * (a42 * a55 - a45 * a52) + a35 * (a42 * a53 - a43 * a52)) - a22 * (a31 * (a43 * a55 - a45 * a53) - a33 * (a41 * a55 - a45 * a51) + a35 * (a41 * a53 - a43 * a51)) + a23 * (a31 * (a42 * a55 - a45 * a52) - a32 * (a41 * a55 - a45 * a51) + a35 * (a41 * a52 - a42 * a51)) - a25 * (a31 * (a42 * a53 - a43 * a52) - a32 * (a41 * a53 - a43 * a51) + a33 * (a41 * a52 - a42 * a51))) + a15 * (a21 * (a32 * (a43 * a54 - a44 * a53) - a33 * (a42 * a54 - a44 * a52) + a34 * (a42 * a53 - a43 * a52)) - a22 * (a31 * (a43 * a54 - a44 * a53) - a33 * (a41 * a54 - a44 * a51) + a34 * (a41 * a53 - a43 * a51)) + a23 * (a31 * (a42 * a54 - a44 * a52) - a32 * (a41 * a54 - a44 * a51) + a34 * (a41 * a52 - a42 * a51)) - a24 * (a31 * (a42 * a53 - a43 * a52) - a32 * (a41 * a53 - a43 * a51) + a33 * (a41 * a52 - a42 * a51))));
        a15 = data[10];
        a25 = data[16];
        a35 = data[22];
        a45 = data[28];
        a55 = data[34];
        return ret -= data[5] * (a11 * (a22 * (a33 * (a44 * a55 - a45 * a54) - a34 * (a43 * a55 - a45 * a53) + a35 * (a43 * a54 - a44 * a53)) - a23 * (a32 * (a44 * a55 - a45 * a54) - a34 * (a42 * a55 - a45 * a52) + a35 * (a42 * a54 - a44 * a52)) + a24 * (a32 * (a43 * a55 - a45 * a53) - a33 * (a42 * a55 - a45 * a52) + a35 * (a42 * a53 - a43 * a52)) - a25 * (a32 * (a43 * a54 - a44 * a53) - a33 * (a42 * a54 - a44 * a52) + a34 * (a42 * a53 - a43 * a52))) - a12 * (a21 * (a33 * (a44 * a55 - a45 * a54) - a34 * (a43 * a55 - a45 * a53) + a35 * (a43 * a54 - a44 * a53)) - a23 * (a31 * (a44 * a55 - a45 * a54) - a34 * (a41 * a55 - a45 * a51) + a35 * (a41 * a54 - a44 * a51)) + a24 * (a31 * (a43 * a55 - a45 * a53) - a33 * (a41 * a55 - a45 * a51) + a35 * (a41 * a53 - a43 * a51)) - a25 * (a31 * (a43 * a54 - a44 * a53) - a33 * (a41 * a54 - a44 * a51) + a34 * (a41 * a53 - a43 * a51))) + a13 * (a21 * (a32 * (a44 * a55 - a45 * a54) - a34 * (a42 * a55 - a45 * a52) + a35 * (a42 * a54 - a44 * a52)) - a22 * (a31 * (a44 * a55 - a45 * a54) - a34 * (a41 * a55 - a45 * a51) + a35 * (a41 * a54 - a44 * a51)) + a24 * (a31 * (a42 * a55 - a45 * a52) - a32 * (a41 * a55 - a45 * a51) + a35 * (a41 * a52 - a42 * a51)) - a25 * (a31 * (a42 * a54 - a44 * a52) - a32 * (a41 * a54 - a44 * a51) + a34 * (a41 * a52 - a42 * a51))) - a14 * (a21 * (a32 * (a43 * a55 - a45 * a53) - a33 * (a42 * a55 - a45 * a52) + a35 * (a42 * a53 - a43 * a52)) - a22 * (a31 * (a43 * a55 - a45 * a53) - a33 * (a41 * a55 - a45 * a51) + a35 * (a41 * a53 - a43 * a51)) + a23 * (a31 * (a42 * a55 - a45 * a52) - a32 * (a41 * a55 - a45 * a51) + a35 * (a41 * a52 - a42 * a51)) - a25 * (a31 * (a42 * a53 - a43 * a52) - a32 * (a41 * a53 - a43 * a51) + a33 * (a41 * a52 - a42 * a51))) + a15 * (a21 * (a32 * (a43 * a54 - a44 * a53) - a33 * (a42 * a54 - a44 * a52) + a34 * (a42 * a53 - a43 * a52)) - a22 * (a31 * (a43 * a54 - a44 * a53) - a33 * (a41 * a54 - a44 * a51) + a34 * (a41 * a53 - a43 * a51)) + a23 * (a31 * (a42 * a54 - a44 * a52) - a32 * (a41 * a54 - a44 * a51) + a34 * (a41 * a52 - a42 * a51)) - a24 * (a31 * (a42 * a53 - a43 * a52) - a32 * (a41 * a53 - a43 * a51) + a33 * (a41 * a52 - a42 * a51))));
    }
}

