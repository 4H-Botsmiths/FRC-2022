/*
 * Decompiled with CFR 0.150.
 */
package edu.wpi.first.cscore;

import edu.wpi.first.cscore.CameraServerCvJNI;
import edu.wpi.first.cscore.ImageSource;
import edu.wpi.first.cscore.VideoMode;
import org.opencv.core.Mat;

public class CvSource
extends ImageSource {
    public CvSource(String name, VideoMode mode) {
        super(CameraServerCvJNI.createCvSource(name, mode.pixelFormat.getValue(), mode.width, mode.height, mode.fps));
    }

    public CvSource(String name, VideoMode.PixelFormat pixelFormat, int width, int height, int fps) {
        super(CameraServerCvJNI.createCvSource(name, pixelFormat.getValue(), width, height, fps));
    }

    public void putFrame(Mat image) {
        CameraServerCvJNI.putSourceFrame(this.m_handle, image.nativeObj);
    }
}

