/*
 * Decompiled with CFR 0.150.
 */
package edu.wpi.first.util.net;

import edu.wpi.first.util.WPIUtilJNI;

public final class PortForwarder {
    private PortForwarder() {
        throw new UnsupportedOperationException("This is a utility class!");
    }

    public static void add(int port, String remoteHost, int remotePort) {
        WPIUtilJNI.addPortForwarder(port, remoteHost, remotePort);
    }

    public static void remove(int port) {
        WPIUtilJNI.removePortForwarder(port);
    }
}

