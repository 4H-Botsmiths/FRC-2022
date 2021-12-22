/*
 * Decompiled with CFR 0.150.
 */
package edu.wpi.first.cscore;

import edu.wpi.first.cscore.CameraServerJNI;
import edu.wpi.first.cscore.VideoSink;

public abstract class ImageSink
extends VideoSink {
    protected ImageSink(int handle) {
        super(handle);
    }

    public void setDescription(String description) {
        CameraServerJNI.setSinkDescription(this.m_handle, description);
    }

    public String getError() {
        return CameraServerJNI.getSinkError(this.m_handle);
    }

    public void setEnabled(boolean enabled) {
        CameraServerJNI.setSinkEnabled(this.m_handle, enabled);
    }
}

