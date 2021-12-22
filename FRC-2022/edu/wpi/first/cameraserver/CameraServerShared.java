/*
 * Decompiled with CFR 0.150.
 */
package edu.wpi.first.cameraserver;

public interface CameraServerShared {
    public Long getRobotMainThreadId();

    public void reportDriverStationError(String var1);

    public void reportVideoServer(int var1);

    public void reportUsbCamera(int var1);

    public void reportAxisCamera(int var1);

    default public boolean isRoboRIO() {
        return false;
    }
}

