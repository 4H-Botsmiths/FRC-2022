/*
 * Decompiled with CFR 0.150.
 */
package edu.wpi.first.cscore.raw;

import edu.wpi.first.cscore.CameraServerJNI;
import edu.wpi.first.cscore.ImageSink;
import edu.wpi.first.cscore.raw.RawFrame;

public class RawSink
extends ImageSink {
    public RawSink(String name) {
        super(CameraServerJNI.createRawSink(name));
    }

    protected long grabFrame(RawFrame frame) {
        return this.grabFrame(frame, 0.225);
    }

    protected long grabFrame(RawFrame frame, double timeout) {
        return CameraServerJNI.grabSinkFrameTimeout(this.m_handle, frame, timeout);
    }

    protected long grabFrameNoTimeout(RawFrame frame) {
        return CameraServerJNI.grabSinkFrame(this.m_handle, frame);
    }
}

