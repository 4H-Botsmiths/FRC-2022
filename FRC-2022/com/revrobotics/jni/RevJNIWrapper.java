/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  edu.wpi.first.wpiutil.RuntimeLoader
 */
package com.revrobotics.jni;

import edu.wpi.first.wpiutil.RuntimeLoader;
import java.io.IOException;

public class RevJNIWrapper {
    static boolean libraryLoaded = false;
    static RuntimeLoader<RevJNIWrapper> loader = null;

    static {
        if (!libraryLoaded) {
            try {
                loader = new RuntimeLoader("SparkMaxDriver", RuntimeLoader.getDefaultExtractionRoot(), RevJNIWrapper.class);
                loader.loadLibrary();
            }
            catch (IOException ex) {
                ex.printStackTrace();
                System.exit(1);
            }
            libraryLoaded = true;
        }
    }
}

