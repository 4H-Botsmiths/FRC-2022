/*
 * Decompiled with CFR 0.150.
 */
package edu.wpi.first.hal;

import edu.wpi.first.util.RuntimeLoader;
import java.io.IOException;
import java.util.concurrent.atomic.AtomicBoolean;

public class JNIWrapper {
    static boolean libraryLoaded = false;
    static RuntimeLoader<JNIWrapper> loader = null;

    public static synchronized void forceLoad() throws IOException {
        if (libraryLoaded) {
            return;
        }
        loader = new RuntimeLoader<JNIWrapper>("wpiHaljni", RuntimeLoader.getDefaultExtractionRoot(), JNIWrapper.class);
        loader.loadLibrary();
        libraryLoaded = true;
    }

    public static void suppressUnused(Object object) {
    }

    static {
        if (Helper.getExtractOnStaticLoad()) {
            try {
                loader = new RuntimeLoader<JNIWrapper>("wpiHaljni", RuntimeLoader.getDefaultExtractionRoot(), JNIWrapper.class);
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

