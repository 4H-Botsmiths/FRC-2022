/*
 * Decompiled with CFR 0.150.
 */
package edu.wpi.first.cscore;

import edu.wpi.first.cscore.CameraServerJNI;
import edu.wpi.first.cscore.VideoSource;

public class VideoCamera
extends VideoSource {
    protected VideoCamera(int handle) {
        super(handle);
    }

    public synchronized void setBrightness(int brightness) {
        CameraServerJNI.setCameraBrightness(this.m_handle, brightness);
    }

    public synchronized int getBrightness() {
        return CameraServerJNI.getCameraBrightness(this.m_handle);
    }

    public synchronized void setWhiteBalanceAuto() {
        CameraServerJNI.setCameraWhiteBalanceAuto(this.m_handle);
    }

    public synchronized void setWhiteBalanceHoldCurrent() {
        CameraServerJNI.setCameraWhiteBalanceHoldCurrent(this.m_handle);
    }

    public synchronized void setWhiteBalanceManual(int value) {
        CameraServerJNI.setCameraWhiteBalanceManual(this.m_handle, value);
    }

    public synchronized void setExposureAuto() {
        CameraServerJNI.setCameraExposureAuto(this.m_handle);
    }

    public synchronized void setExposureHoldCurrent() {
        CameraServerJNI.setCameraExposureHoldCurrent(this.m_handle);
    }

    public synchronized void setExposureManual(int value) {
        CameraServerJNI.setCameraExposureManual(this.m_handle, value);
    }

    public static class WhiteBalance {
        public static final int kFixedIndoor = 3000;
        public static final int kFixedOutdoor1 = 4000;
        public static final int kFixedOutdoor2 = 5000;
        public static final int kFixedFluorescent1 = 5100;
        public static final int kFixedFlourescent2 = 5200;
    }
}

