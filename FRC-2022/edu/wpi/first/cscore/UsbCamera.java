/*
 * Decompiled with CFR 0.150.
 */
package edu.wpi.first.cscore;

import edu.wpi.first.cscore.CameraServerJNI;
import edu.wpi.first.cscore.UsbCameraInfo;
import edu.wpi.first.cscore.VideoCamera;

public class UsbCamera
extends VideoCamera {
    public UsbCamera(String name, int dev) {
        super(CameraServerJNI.createUsbCameraDev(name, dev));
    }

    public UsbCamera(String name, String path) {
        super(CameraServerJNI.createUsbCameraPath(name, path));
    }

    public static UsbCameraInfo[] enumerateUsbCameras() {
        return CameraServerJNI.enumerateUsbCameras();
    }

    void setPath(String path) {
        CameraServerJNI.setUsbCameraPath(this.m_handle, path);
    }

    public String getPath() {
        return CameraServerJNI.getUsbCameraPath(this.m_handle);
    }

    public UsbCameraInfo getInfo() {
        return CameraServerJNI.getUsbCameraInfo(this.m_handle);
    }

    public void setConnectVerbose(int level) {
        CameraServerJNI.setProperty(CameraServerJNI.getSourceProperty(this.m_handle, "connect_verbose"), level);
    }
}

