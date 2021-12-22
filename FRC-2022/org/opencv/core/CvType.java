/*
 * Decompiled with CFR 0.150.
 */
package org.opencv.core;

public final class CvType {
    public static final int CV_8U = 0;
    public static final int CV_8S = 1;
    public static final int CV_16U = 2;
    public static final int CV_16S = 3;
    public static final int CV_32S = 4;
    public static final int CV_32F = 5;
    public static final int CV_64F = 6;
    public static final int CV_16F = 7;
    @Deprecated
    public static final int CV_USRTYPE1 = 7;
    public static final int CV_8UC1 = CvType.CV_8UC(1);
    public static final int CV_8UC2 = CvType.CV_8UC(2);
    public static final int CV_8UC3 = CvType.CV_8UC(3);
    public static final int CV_8UC4 = CvType.CV_8UC(4);
    public static final int CV_8SC1 = CvType.CV_8SC(1);
    public static final int CV_8SC2 = CvType.CV_8SC(2);
    public static final int CV_8SC3 = CvType.CV_8SC(3);
    public static final int CV_8SC4 = CvType.CV_8SC(4);
    public static final int CV_16UC1 = CvType.CV_16UC(1);
    public static final int CV_16UC2 = CvType.CV_16UC(2);
    public static final int CV_16UC3 = CvType.CV_16UC(3);
    public static final int CV_16UC4 = CvType.CV_16UC(4);
    public static final int CV_16SC1 = CvType.CV_16SC(1);
    public static final int CV_16SC2 = CvType.CV_16SC(2);
    public static final int CV_16SC3 = CvType.CV_16SC(3);
    public static final int CV_16SC4 = CvType.CV_16SC(4);
    public static final int CV_32SC1 = CvType.CV_32SC(1);
    public static final int CV_32SC2 = CvType.CV_32SC(2);
    public static final int CV_32SC3 = CvType.CV_32SC(3);
    public static final int CV_32SC4 = CvType.CV_32SC(4);
    public static final int CV_32FC1 = CvType.CV_32FC(1);
    public static final int CV_32FC2 = CvType.CV_32FC(2);
    public static final int CV_32FC3 = CvType.CV_32FC(3);
    public static final int CV_32FC4 = CvType.CV_32FC(4);
    public static final int CV_64FC1 = CvType.CV_64FC(1);
    public static final int CV_64FC2 = CvType.CV_64FC(2);
    public static final int CV_64FC3 = CvType.CV_64FC(3);
    public static final int CV_64FC4 = CvType.CV_64FC(4);
    public static final int CV_16FC1 = CvType.CV_16FC(1);
    public static final int CV_16FC2 = CvType.CV_16FC(2);
    public static final int CV_16FC3 = CvType.CV_16FC(3);
    public static final int CV_16FC4 = CvType.CV_16FC(4);
    private static final int CV_CN_MAX = 512;
    private static final int CV_CN_SHIFT = 3;
    private static final int CV_DEPTH_MAX = 8;

    public static final int makeType(int depth, int channels) {
        if (channels <= 0 || channels >= 512) {
            throw new UnsupportedOperationException("Channels count should be 1..511");
        }
        if (depth < 0 || depth >= 8) {
            throw new UnsupportedOperationException("Data type depth should be 0..7");
        }
        return (depth & 7) + (channels - 1 << 3);
    }

    public static final int CV_8UC(int ch) {
        return CvType.makeType(0, ch);
    }

    public static final int CV_8SC(int ch) {
        return CvType.makeType(1, ch);
    }

    public static final int CV_16UC(int ch) {
        return CvType.makeType(2, ch);
    }

    public static final int CV_16SC(int ch) {
        return CvType.makeType(3, ch);
    }

    public static final int CV_32SC(int ch) {
        return CvType.makeType(4, ch);
    }

    public static final int CV_32FC(int ch) {
        return CvType.makeType(5, ch);
    }

    public static final int CV_64FC(int ch) {
        return CvType.makeType(6, ch);
    }

    public static final int CV_16FC(int ch) {
        return CvType.makeType(7, ch);
    }

    public static final int channels(int type) {
        return (type >> 3) + 1;
    }

    public static final int depth(int type) {
        return type & 7;
    }

    public static final boolean isInteger(int type) {
        return CvType.depth(type) < 5;
    }

    public static final int ELEM_SIZE(int type) {
        switch (CvType.depth(type)) {
            case 0: 
            case 1: {
                return CvType.channels(type);
            }
            case 2: 
            case 3: 
            case 7: {
                return 2 * CvType.channels(type);
            }
            case 4: 
            case 5: {
                return 4 * CvType.channels(type);
            }
            case 6: {
                return 8 * CvType.channels(type);
            }
        }
        throw new UnsupportedOperationException("Unsupported CvType value: " + type);
    }

    public static final String typeToString(int type) {
        String s;
        switch (CvType.depth(type)) {
            case 0: {
                s = "CV_8U";
                break;
            }
            case 1: {
                s = "CV_8S";
                break;
            }
            case 2: {
                s = "CV_16U";
                break;
            }
            case 3: {
                s = "CV_16S";
                break;
            }
            case 4: {
                s = "CV_32S";
                break;
            }
            case 5: {
                s = "CV_32F";
                break;
            }
            case 6: {
                s = "CV_64F";
                break;
            }
            case 7: {
                s = "CV_16F";
                break;
            }
            default: {
                throw new UnsupportedOperationException("Unsupported CvType value: " + type);
            }
        }
        int ch = CvType.channels(type);
        if (ch <= 4) {
            return s + "C" + ch;
        }
        return s + "C(" + ch + ")";
    }
}

