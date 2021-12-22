/*
 * Decompiled with CFR 0.150.
 */
package edu.wpi.first.util;

import edu.wpi.first.util.RuntimeLoader;
import edu.wpi.first.util.ServiceData;
import java.io.IOException;
import java.util.concurrent.atomic.AtomicBoolean;

public final class WPIUtilJNI {
    static boolean libraryLoaded = false;
    static RuntimeLoader<WPIUtilJNI> loader = null;

    public static synchronized void forceLoad() throws IOException {
        if (libraryLoaded) {
            return;
        }
        loader = new RuntimeLoader<WPIUtilJNI>("wpiutiljni", RuntimeLoader.getDefaultExtractionRoot(), WPIUtilJNI.class);
        loader.loadLibrary();
        libraryLoaded = true;
    }

    public static native void enableMockTime();

    public static native void setMockTime(long var0);

    public static native long now();

    public static native void addPortForwarder(int var0, String var1, int var2);

    public static native void removePortForwarder(int var0);

    public static native int createEvent(boolean var0, boolean var1);

    public static native void destroyEvent(int var0);

    public static native void setEvent(int var0);

    public static native void resetEvent(int var0);

    public static native int createSemaphore(int var0, int var1);

    public static native void destroySemaphore(int var0);

    public static native boolean releaseSemaphore(int var0, int var1);

    public static native void waitForObject(int var0) throws InterruptedException;

    public static native boolean waitForObjectTimeout(int var0, double var1) throws InterruptedException;

    public static native int[] waitForObjects(int[] var0) throws InterruptedException;

    public static native int[] waitForObjectsTimeout(int[] var0, double var1) throws InterruptedException;

    public static native int createMulticastServiceAnnouncer(String var0, String var1, int var2, String[] var3, String[] var4);

    public static native void freeMulticastServiceAnnouncer(int var0);

    public static native void startMulticastServiceAnnouncer(int var0);

    public static native void stopMulticastServiceAnnouncer(int var0);

    public static native boolean getMulticastServiceAnnouncerHasImplementation(int var0);

    public static native int createMulticastServiceResolver(String var0);

    public static native void freeMulticastServiceResolver(int var0);

    public static native void startMulticastServiceResolver(int var0);

    public static native void stopMulticastServiceResolver(int var0);

    public static native boolean getMulticastServiceResolverHasImplementation(int var0);

    public static native int getMulticastServiceResolverEventHandle(int var0);

    public static native ServiceData[] getMulticastServiceResolverData(int var0);

    static {
        if (Helper.getExtractOnStaticLoad()) {
            try {
                loader = new RuntimeLoader<WPIUtilJNI>("wpiutiljni", RuntimeLoader.getDefaultExtractionRoot(), WPIUtilJNI.class);
                loader.loadLibrary();
            }
            catch (IOException ex) {
                ex.printStackTrace();
                System.exit(1);
            }
            libraryLoaded = true;
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

