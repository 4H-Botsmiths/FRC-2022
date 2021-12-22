/*
 * Decompiled with CFR 0.150.
 */
package org.opencv.osgi;

import java.util.logging.Level;
import java.util.logging.Logger;
import org.opencv.osgi.OpenCVInterface;

public class OpenCVNativeLoader
implements OpenCVInterface {
    public void init() {
        System.loadLibrary("opencv_java452");
        Logger.getLogger("org.opencv.osgi").log(Level.INFO, "Successfully loaded OpenCV native library.");
    }
}

