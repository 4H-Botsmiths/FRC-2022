/*
 * Decompiled with CFR 0.150.
 */
package edu.wpi.first.cscore.raw;

import edu.wpi.first.cscore.CameraServerJNI;
import edu.wpi.first.cscore.ImageSource;
import edu.wpi.first.cscore.VideoMode;
import edu.wpi.first.cscore.raw.RawFrame;

public class RawSource
extends ImageSource {
    public RawSource(String name, VideoMode mode) {
        super(CameraServerJNI.createRawSource(name, mode.pixelFormat.getValue(), mode.width, mode.height, mode.fps));
    }

    public RawSource(String name, VideoMode.PixelFormat pixelFormat, int width, int height, int fps) {
        super(CameraServerJNI.createRawSource(name, pixelFormat.getValue(), width, height, fps));
    }

    protected void putFrame(RawFrame image) {
        CameraServerJNI.putRawSourceFrame(this.m_handle, image);
    }

    protected void putFrame(long data, int width, int height, int pixelFormat, int totalData) {
        CameraServerJNI.putRawSourceFrame(this.m_handle, data, width, height, pixelFormat, totalData);
    }

    protected void putFrame(long data, int width, int height, VideoMode.PixelFormat pixelFormat, int totalData) {
        CameraServerJNI.putRawSourceFrame(this.m_handle, data, width, height, pixelFormat.getValue(), totalData);
    }
}

