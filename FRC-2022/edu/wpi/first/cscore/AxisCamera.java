/*
 * Decompiled with CFR 0.150.
 */
package edu.wpi.first.cscore;

import edu.wpi.first.cscore.HttpCamera;

public class AxisCamera
extends HttpCamera {
    private static String hostToUrl(String host) {
        return "http://" + host + "/mjpg/video.mjpg";
    }

    private static String[] hostToUrl(String[] hosts) {
        String[] urls = new String[hosts.length];
        for (int i = 0; i < hosts.length; ++i) {
            urls[i] = AxisCamera.hostToUrl(hosts[i]);
        }
        return urls;
    }

    public AxisCamera(String name, String host) {
        super(name, AxisCamera.hostToUrl(host), HttpCamera.HttpCameraKind.kAxis);
    }

    public AxisCamera(String name, String[] hosts) {
        super(name, AxisCamera.hostToUrl(hosts), HttpCamera.HttpCameraKind.kAxis);
    }
}

