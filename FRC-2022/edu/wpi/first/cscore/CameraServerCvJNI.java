/*
 * Decompiled with CFR 0.150.
 */
package edu.wpi.first.cscore;

import edu.wpi.first.cscore.CameraServerJNI;
import edu.wpi.first.util.RuntimeLoader;
import java.io.IOException;
import java.util.concurrent.atomic.AtomicBoolean;
import org.opencv.core.Core;

public class CameraServerCvJNI {
    static boolean libraryLoaded = false;
    static RuntimeLoader<Core> loader = null;

    public static synchronized void forceLoad() throws IOException {
        if (libraryLoaded) {
            return;
        }
        CameraServerJNI.forceLoad();
        loader = new RuntimeLoader<Core>(Core.NATIVE_LIBRARY_NAME, RuntimeLoader.getDefaultExtractionRoot(), Core.class);
        loader.loadLibrary();
        libraryLoaded = true;
    }

    public static native int createCvSource(String var0, int var1, int var2, int var3, int var4);

    public static native void putSourceFrame(int var0, long var1);

    public static native int createCvSink(String var0);

    public static native long grabSinkFrame(int var0, long var1);

    public static native long grabSinkFrameTimeout(int var0, long var1, double var3);

    static {
        String opencvName = Core.NATIVE_LIBRARY_NAME;
        if (Helper.getExtractOnStaticLoad()) {
            try {
                CameraServerJNI.forceLoad();
                loader = new RuntimeLoader<Core>(opencvName, RuntimeLoader.getDefaultExtractionRoot(), Core.class);
                loader.loadLibraryHashed();
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

