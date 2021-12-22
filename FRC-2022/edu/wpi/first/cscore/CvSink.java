/*
 * Decompiled with CFR 0.150.
 */
package edu.wpi.first.cscore;

import edu.wpi.first.cscore.CameraServerCvJNI;
import edu.wpi.first.cscore.ImageSink;
import org.opencv.core.Mat;

public class CvSink
extends ImageSink {
    public CvSink(String name) {
        super(CameraServerCvJNI.createCvSink(name));
    }

    public long grabFrame(Mat image) {
        return this.grabFrame(image, 0.225);
    }

    public long grabFrame(Mat image, double timeout) {
        return CameraServerCvJNI.grabSinkFrameTimeout(this.m_handle, image.nativeObj, timeout);
    }

    public long grabFrameNoTimeout(Mat image) {
        return CameraServerCvJNI.grabSinkFrame(this.m_handle, image.nativeObj);
    }
}

