/*
 * Decompiled with CFR 0.150.
 */
package edu.wpi.first.cscore;

import edu.wpi.first.cscore.CameraServerJNI;
import edu.wpi.first.cscore.VideoCamera;

public class HttpCamera
extends VideoCamera {
    public static HttpCameraKind getHttpCameraKindFromInt(int kind) {
        switch (kind) {
            case 1: {
                return HttpCameraKind.kMJPGStreamer;
            }
            case 2: {
                return HttpCameraKind.kCSCore;
            }
            case 3: {
                return HttpCameraKind.kAxis;
            }
        }
        return HttpCameraKind.kUnknown;
    }

    public HttpCamera(String name, String url) {
        super(CameraServerJNI.createHttpCamera(name, url, HttpCameraKind.kUnknown.getValue()));
    }

    public HttpCamera(String name, String url, HttpCameraKind kind) {
        super(CameraServerJNI.createHttpCamera(name, url, kind.getValue()));
    }

    public HttpCamera(String name, String[] urls) {
        super(CameraServerJNI.createHttpCameraMulti(name, urls, HttpCameraKind.kUnknown.getValue()));
    }

    public HttpCamera(String name, String[] urls, HttpCameraKind kind) {
        super(CameraServerJNI.createHttpCameraMulti(name, urls, kind.getValue()));
    }

    public HttpCameraKind getHttpCameraKind() {
        return HttpCamera.getHttpCameraKindFromInt(CameraServerJNI.getHttpCameraKind(this.m_handle));
    }

    public void setUrls(String[] urls) {
        CameraServerJNI.setHttpCameraUrls(this.m_handle, urls);
    }

    public String[] getUrls() {
        return CameraServerJNI.getHttpCameraUrls(this.m_handle);
    }

    public static enum HttpCameraKind {
        kUnknown(0),
        kMJPGStreamer(1),
        kCSCore(2),
        kAxis(3);

        private final int value;

        private HttpCameraKind(int value) {
            this.value = value;
        }

        public int getValue() {
            return this.value;
        }
    }
}

