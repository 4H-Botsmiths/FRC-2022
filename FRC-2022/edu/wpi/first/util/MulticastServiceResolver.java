/*
 * Decompiled with CFR 0.150.
 */
package edu.wpi.first.util;

import edu.wpi.first.util.ServiceData;
import edu.wpi.first.util.WPIUtilJNI;

public class MulticastServiceResolver
implements AutoCloseable {
    private final int m_handle;

    public MulticastServiceResolver(String serviceType) {
        this.m_handle = WPIUtilJNI.createMulticastServiceResolver(serviceType);
    }

    @Override
    public void close() {
        WPIUtilJNI.freeMulticastServiceResolver(this.m_handle);
    }

    public void start() {
        WPIUtilJNI.startMulticastServiceResolver(this.m_handle);
    }

    public void stop() {
        WPIUtilJNI.stopMulticastServiceResolver(this.m_handle);
    }

    public boolean hasImplementation() {
        return WPIUtilJNI.getMulticastServiceResolverHasImplementation(this.m_handle);
    }

    public int getEventHandle() {
        return WPIUtilJNI.getMulticastServiceResolverEventHandle(this.m_handle);
    }

    public ServiceData[] getData() {
        return WPIUtilJNI.getMulticastServiceResolverData(this.m_handle);
    }
}

