/*
 * Decompiled with CFR 0.150.
 */
package edu.wpi.first.math;

import edu.wpi.first.util.RuntimeLoader;
import java.io.IOException;
import java.util.concurrent.atomic.AtomicBoolean;

public final class WPIMathJNI {
    static boolean libraryLoaded = false;
    static RuntimeLoader<WPIMathJNI> loader = null;

    public static synchronized void forceLoad() throws IOException {
        if (libraryLoaded) {
            return;
        }
        loader = new RuntimeLoader<WPIMathJNI>("wpimathjni", RuntimeLoader.getDefaultExtractionRoot(), WPIMathJNI.class);
        loader.loadLibrary();
        libraryLoaded = true;
    }

    public static native void discreteAlgebraicRiccatiEquation(double[] var0, double[] var1, double[] var2, double[] var3, int var4, int var5, double[] var6);

    public static native void exp(double[] var0, int var1, double[] var2);

    public static native void pow(double[] var0, int var1, double var2, double[] var4);

    public static native boolean isStabilizable(int var0, int var1, double[] var2, double[] var3);

    public static native double[] fromPathweaverJson(String var0) throws IOException;

    public static native void toPathweaverJson(double[] var0, String var1) throws IOException;

    public static native double[] deserializeTrajectory(String var0);

    public static native String serializeTrajectory(double[] var0);

    static {
        if (Helper.getExtractOnStaticLoad()) {
            try {
                loader = new RuntimeLoader<WPIMathJNI>("wpimathjni", RuntimeLoader.getDefaultExtractionRoot(), WPIMathJNI.class);
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

