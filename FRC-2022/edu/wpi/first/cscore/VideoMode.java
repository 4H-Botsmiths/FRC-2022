/*
 * Decompiled with CFR 0.150.
 */
package edu.wpi.first.cscore;

public class VideoMode {
    private static final PixelFormat[] m_pixelFormatValues = PixelFormat.values();
    public PixelFormat pixelFormat;
    public int width;
    public int height;
    public int fps;

    public static PixelFormat getPixelFormatFromInt(int pixelFormat) {
        return m_pixelFormatValues[pixelFormat];
    }

    public VideoMode(int pixelFormat, int width, int height, int fps) {
        this.pixelFormat = VideoMode.getPixelFormatFromInt(pixelFormat);
        this.width = width;
        this.height = height;
        this.fps = fps;
    }

    public VideoMode(PixelFormat pixelFormat, int width, int height, int fps) {
        this.pixelFormat = pixelFormat;
        this.width = width;
        this.height = height;
        this.fps = fps;
    }

    public static enum PixelFormat {
        kUnknown(0),
        kMJPEG(1),
        kYUYV(2),
        kRGB565(3),
        kBGR(4),
        kGray(5);

        private final int value;

        private PixelFormat(int value) {
            this.value = value;
        }

        public int getValue() {
            return this.value;
        }
    }
}

