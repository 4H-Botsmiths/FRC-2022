/*
 * Decompiled with CFR 0.150.
 */
package edu.wpi.first.cscore.raw;

import edu.wpi.first.cscore.CameraServerJNI;
import java.nio.ByteBuffer;

public class RawFrame
implements AutoCloseable {
    private final long m_framePtr = CameraServerJNI.allocateRawFrame();
    private ByteBuffer m_dataByteBuffer;
    private long m_dataPtr;
    private int m_totalData;
    private int m_width;
    private int m_height;
    private int m_pixelFormat;

    @Override
    public void close() {
        CameraServerJNI.freeRawFrame(this.m_framePtr);
    }

    public void setData(ByteBuffer dataByteBuffer, long dataPtr, int totalData, int width, int height, int pixelFormat) {
        this.m_dataByteBuffer = dataByteBuffer;
        this.m_dataPtr = dataPtr;
        this.m_totalData = totalData;
        this.m_width = width;
        this.m_height = height;
        this.m_pixelFormat = pixelFormat;
    }

    public long getFramePtr() {
        return this.m_framePtr;
    }

    public ByteBuffer getDataByteBuffer() {
        return this.m_dataByteBuffer;
    }

    public long getDataPtr() {
        return this.m_dataPtr;
    }

    public int getTotalData() {
        return this.m_totalData;
    }

    public int getWidth() {
        return this.m_width;
    }

    public void setWidth(int width) {
        this.m_width = width;
    }

    public int getHeight() {
        return this.m_height;
    }

    public void setHeight(int height) {
        this.m_height = height;
    }

    public int getPixelFormat() {
        return this.m_pixelFormat;
    }

    public void setPixelFormat(int pixelFormat) {
        this.m_pixelFormat = pixelFormat;
    }
}

