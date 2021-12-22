/*
 * Decompiled with CFR 0.150.
 */
package edu.wpi.first.cscore;

import edu.wpi.first.cscore.CameraServerJNI;
import edu.wpi.first.cscore.VideoSink;

public class MjpegServer
extends VideoSink {
    public MjpegServer(String name, String listenAddress, int port) {
        super(CameraServerJNI.createMjpegServer(name, listenAddress, port));
    }

    public MjpegServer(String name, int port) {
        this(name, "", port);
    }

    public String getListenAddress() {
        return CameraServerJNI.getMjpegServerListenAddress(this.m_handle);
    }

    public int getPort() {
        return CameraServerJNI.getMjpegServerPort(this.m_handle);
    }

    public void setResolution(int width, int height) {
        CameraServerJNI.setProperty(CameraServerJNI.getSinkProperty(this.m_handle, "width"), width);
        CameraServerJNI.setProperty(CameraServerJNI.getSinkProperty(this.m_handle, "height"), height);
    }

    public void setFPS(int fps) {
        CameraServerJNI.setProperty(CameraServerJNI.getSinkProperty(this.m_handle, "fps"), fps);
    }

    public void setCompression(int quality) {
        CameraServerJNI.setProperty(CameraServerJNI.getSinkProperty(this.m_handle, "compression"), quality);
    }

    public void setDefaultCompression(int quality) {
        CameraServerJNI.setProperty(CameraServerJNI.getSinkProperty(this.m_handle, "default_compression"), quality);
    }
}

