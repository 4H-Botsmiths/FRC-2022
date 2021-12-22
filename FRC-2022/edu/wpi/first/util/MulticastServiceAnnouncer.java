/*
 * Decompiled with CFR 0.150.
 */
package edu.wpi.first.util;

import edu.wpi.first.util.WPIUtilJNI;
import java.util.Map;

public class MulticastServiceAnnouncer
implements AutoCloseable {
    private final int m_handle;

    public MulticastServiceAnnouncer(String serviceName, String serviceType, int port, Map<String, String> txt) {
        String[] keys = (String[])txt.keySet().toArray(String[]::new);
        String[] values = (String[])txt.values().toArray(String[]::new);
        this.m_handle = WPIUtilJNI.createMulticastServiceAnnouncer(serviceName, serviceType, port, keys, values);
    }

    @Override
    public void close() {
        WPIUtilJNI.freeMulticastServiceAnnouncer(this.m_handle);
    }

    public void start() {
        WPIUtilJNI.startMulticastServiceAnnouncer(this.m_handle);
    }

    public void stop() {
        WPIUtilJNI.stopMulticastServiceAnnouncer(this.m_handle);
    }

    public boolean hasImplementation() {
        return WPIUtilJNI.getMulticastServiceAnnouncerHasImplementation(this.m_handle);
    }
}

