/*
 * Decompiled with CFR 0.150.
 */
package edu.wpi.first.util;

import java.util.HashMap;
import java.util.Map;

public class ServiceData {
    private final Map<String, String> m_txt;
    private final long m_ipv4Address;
    private final int m_port;
    private final String m_serviceName;
    private final String m_hostName;

    public ServiceData(long ipv4Address, int port, String serviceName, String hostName, String[] keys, String[] values) {
        this.m_serviceName = serviceName;
        this.m_hostName = hostName;
        this.m_port = port;
        this.m_ipv4Address = ipv4Address;
        this.m_txt = new HashMap<String, String>();
        for (int i = 0; i < keys.length; ++i) {
            this.m_txt.put(keys[i], values[i]);
        }
    }

    public Map<String, String> getTxt() {
        return this.m_txt;
    }

    public String getHostName() {
        return this.m_hostName;
    }

    public String getServiceName() {
        return this.m_serviceName;
    }

    public int getPort() {
        return this.m_port;
    }

    public long getIpv4Address() {
        return this.m_ipv4Address;
    }
}

