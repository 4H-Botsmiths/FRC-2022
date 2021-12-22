/*
 * Decompiled with CFR 0.150.
 */
package edu.wpi.first.cameraserver;

import edu.wpi.first.cameraserver.CameraServerShared;

public final class CameraServerSharedStore {
    private static CameraServerShared cameraServerShared;

    private CameraServerSharedStore() {
    }

    public static synchronized CameraServerShared getCameraServerShared() {
        if (cameraServerShared == null) {
            cameraServerShared = new CameraServerShared(){

                @Override
                public void reportVideoServer(int id) {
                }

                @Override
                public void reportUsbCamera(int id) {
                }

                @Override
                public void reportDriverStationError(String error) {
                }

                @Override
                public void reportAxisCamera(int id) {
                }

                @Override
                public Long getRobotMainThreadId() {
                    return null;
                }
            };
        }
        return cameraServerShared;
    }

    public static synchronized void setCameraServerShared(CameraServerShared shared) {
        cameraServerShared = shared;
    }
}

