/*
 * Decompiled with CFR 0.150.
 */
package edu.wpi.first.cscore;

import edu.wpi.first.cscore.UsbCameraInfo;
import edu.wpi.first.cscore.VideoEvent;
import edu.wpi.first.cscore.VideoMode;
import edu.wpi.first.cscore.raw.RawFrame;
import edu.wpi.first.util.RuntimeLoader;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.Consumer;

public class CameraServerJNI {
    static boolean libraryLoaded = false;
    static RuntimeLoader<CameraServerJNI> loader = null;

    public static synchronized void forceLoad() throws IOException {
        if (libraryLoaded) {
            return;
        }
        loader = new RuntimeLoader<CameraServerJNI>("cscorejni", RuntimeLoader.getDefaultExtractionRoot(), CameraServerJNI.class);
        loader.loadLibrary();
        libraryLoaded = true;
    }

    public static native int getPropertyKind(int var0);

    public static native String getPropertyName(int var0);

    public static native int getProperty(int var0);

    public static native void setProperty(int var0, int var1);

    public static native int getPropertyMin(int var0);

    public static native int getPropertyMax(int var0);

    public static native int getPropertyStep(int var0);

    public static native int getPropertyDefault(int var0);

    public static native String getStringProperty(int var0);

    public static native void setStringProperty(int var0, String var1);

    public static native String[] getEnumPropertyChoices(int var0);

    public static native int createUsbCameraDev(String var0, int var1);

    public static native int createUsbCameraPath(String var0, String var1);

    public static native int createHttpCamera(String var0, String var1, int var2);

    public static native int createHttpCameraMulti(String var0, String[] var1, int var2);

    public static native int createRawSource(String var0, int var1, int var2, int var3, int var4);

    public static native int getSourceKind(int var0);

    public static native String getSourceName(int var0);

    public static native String getSourceDescription(int var0);

    public static native long getSourceLastFrameTime(int var0);

    public static native void setSourceConnectionStrategy(int var0, int var1);

    public static native boolean isSourceConnected(int var0);

    public static native boolean isSourceEnabled(int var0);

    public static native int getSourceProperty(int var0, String var1);

    public static native int[] enumerateSourceProperties(int var0);

    public static native VideoMode getSourceVideoMode(int var0);

    public static native boolean setSourceVideoMode(int var0, int var1, int var2, int var3, int var4);

    public static native boolean setSourcePixelFormat(int var0, int var1);

    public static native boolean setSourceResolution(int var0, int var1, int var2);

    public static native boolean setSourceFPS(int var0, int var1);

    public static native boolean setSourceConfigJson(int var0, String var1);

    public static native String getSourceConfigJson(int var0);

    public static native VideoMode[] enumerateSourceVideoModes(int var0);

    public static native int[] enumerateSourceSinks(int var0);

    public static native int copySource(int var0);

    public static native void releaseSource(int var0);

    public static native void setCameraBrightness(int var0, int var1);

    public static native int getCameraBrightness(int var0);

    public static native void setCameraWhiteBalanceAuto(int var0);

    public static native void setCameraWhiteBalanceHoldCurrent(int var0);

    public static native void setCameraWhiteBalanceManual(int var0, int var1);

    public static native void setCameraExposureAuto(int var0);

    public static native void setCameraExposureHoldCurrent(int var0);

    public static native void setCameraExposureManual(int var0, int var1);

    public static native void setUsbCameraPath(int var0, String var1);

    public static native String getUsbCameraPath(int var0);

    public static native UsbCameraInfo getUsbCameraInfo(int var0);

    public static native int getHttpCameraKind(int var0);

    public static native void setHttpCameraUrls(int var0, String[] var1);

    public static native String[] getHttpCameraUrls(int var0);

    public static native void putRawSourceFrameBB(int var0, ByteBuffer var1, int var2, int var3, int var4, int var5);

    public static native void putRawSourceFrame(int var0, long var1, int var3, int var4, int var5, int var6);

    public static void putRawSourceFrame(int source, RawFrame raw) {
        CameraServerJNI.putRawSourceFrame(source, raw.getDataPtr(), raw.getWidth(), raw.getHeight(), raw.getPixelFormat(), raw.getTotalData());
    }

    public static native void notifySourceError(int var0, String var1);

    public static native void setSourceConnected(int var0, boolean var1);

    public static native void setSourceDescription(int var0, String var1);

    public static native int createSourceProperty(int var0, String var1, int var2, int var3, int var4, int var5, int var6, int var7);

    public static native void setSourceEnumPropertyChoices(int var0, int var1, String[] var2);

    public static native int createMjpegServer(String var0, String var1, int var2);

    public static native int createRawSink(String var0);

    public static native int getSinkKind(int var0);

    public static native String getSinkName(int var0);

    public static native String getSinkDescription(int var0);

    public static native int getSinkProperty(int var0, String var1);

    public static native int[] enumerateSinkProperties(int var0);

    public static native boolean setSinkConfigJson(int var0, String var1);

    public static native String getSinkConfigJson(int var0);

    public static native void setSinkSource(int var0, int var1);

    public static native int getSinkSourceProperty(int var0, String var1);

    public static native int getSinkSource(int var0);

    public static native int copySink(int var0);

    public static native void releaseSink(int var0);

    public static native String getMjpegServerListenAddress(int var0);

    public static native int getMjpegServerPort(int var0);

    public static native void setSinkDescription(int var0, String var1);

    private static native long grabRawSinkFrameImpl(int var0, RawFrame var1, long var2, ByteBuffer var4, int var5, int var6, int var7);

    private static native long grabRawSinkFrameTimeoutImpl(int var0, RawFrame var1, long var2, ByteBuffer var4, int var5, int var6, int var7, double var8);

    public static long grabSinkFrame(int sink, RawFrame rawFrame) {
        return CameraServerJNI.grabRawSinkFrameImpl(sink, rawFrame, rawFrame.getFramePtr(), rawFrame.getDataByteBuffer(), rawFrame.getWidth(), rawFrame.getHeight(), rawFrame.getPixelFormat());
    }

    public static long grabSinkFrameTimeout(int sink, RawFrame rawFrame, double timeout) {
        return CameraServerJNI.grabRawSinkFrameTimeoutImpl(sink, rawFrame, rawFrame.getFramePtr(), rawFrame.getDataByteBuffer(), rawFrame.getWidth(), rawFrame.getHeight(), rawFrame.getPixelFormat(), timeout);
    }

    public static native String getSinkError(int var0);

    public static native void setSinkEnabled(int var0, boolean var1);

    public static native int addListener(Consumer<VideoEvent> var0, int var1, boolean var2);

    public static native void removeListener(int var0);

    public static native int createListenerPoller();

    public static native void destroyListenerPoller(int var0);

    public static native int addPolledListener(int var0, int var1, boolean var2);

    public static native VideoEvent[] pollListener(int var0) throws InterruptedException;

    public static native VideoEvent[] pollListenerTimeout(int var0, double var1) throws InterruptedException;

    public static native void cancelPollListener(int var0);

    public static native void setTelemetryPeriod(double var0);

    public static native double getTelemetryElapsedTime();

    public static native long getTelemetryValue(int var0, int var1);

    public static long getTelemetryValue(int handle, TelemetryKind kind) {
        return CameraServerJNI.getTelemetryValue(handle, kind.getValue());
    }

    public static native double getTelemetryAverageValue(int var0, int var1);

    public static double getTelemetryAverageValue(int handle, TelemetryKind kind) {
        return CameraServerJNI.getTelemetryAverageValue(handle, kind.getValue());
    }

    public static native void setLogger(LoggerFunction var0, int var1);

    public static native UsbCameraInfo[] enumerateUsbCameras();

    public static native int[] enumerateSources();

    public static native int[] enumerateSinks();

    public static native String getHostname();

    public static native String[] getNetworkInterfaces();

    public static native long allocateRawFrame();

    public static native void freeRawFrame(long var0);

    static {
        if (Helper.getExtractOnStaticLoad()) {
            try {
                loader = new RuntimeLoader<CameraServerJNI>("cscorejni", RuntimeLoader.getDefaultExtractionRoot(), CameraServerJNI.class);
                loader.loadLibrary();
            }
            catch (IOException ex) {
                ex.printStackTrace();
                System.exit(1);
            }
            libraryLoaded = true;
        }
    }

    @FunctionalInterface
    public static interface LoggerFunction {
        public void apply(int var1, String var2, int var3, String var4);
    }

    public static enum TelemetryKind {
        kSourceBytesReceived(1),
        kSourceFramesReceived(2);

        private final int value;

        private TelemetryKind(int value) {
            this.value = value;
        }

        public int getValue() {
            return this.value;
        }
    }

    public static class Helper {
        private static AtomicBoolean extractOnStaticLoad = new AtomicBoolean(true);

        public static boolean getExtractOnStaticLoad() {
            return extractOnStaticLoad.get();
        }

        public static void setExtractOnStaticLoad(boolean load) {
            extractOnStaticLoad.set(load);
        }
    }
}

