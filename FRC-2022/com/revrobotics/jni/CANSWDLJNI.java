/*
 * Decompiled with CFR 0.150.
 */
package com.revrobotics.jni;

import com.revrobotics.jni.RevJNIWrapper;

public class CANSWDLJNI
extends RevJNIWrapper {
    public static native void AddDevice(int var0, int ... var1);

    public static native int RunSWDL(String var0);
}

