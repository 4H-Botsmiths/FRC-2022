/*
 * Decompiled with CFR 0.150.
 */
package edu.wpi.first.cameraserver;

import edu.wpi.first.cameraserver.CameraServerSharedStore;
import edu.wpi.first.cscore.AxisCamera;
import edu.wpi.first.cscore.CameraServerJNI;
import edu.wpi.first.cscore.CvSink;
import edu.wpi.first.cscore.CvSource;
import edu.wpi.first.cscore.MjpegServer;
import edu.wpi.first.cscore.UsbCamera;
import edu.wpi.first.cscore.VideoEvent;
import edu.wpi.first.cscore.VideoException;
import edu.wpi.first.cscore.VideoListener;
import edu.wpi.first.cscore.VideoMode;
import edu.wpi.first.cscore.VideoProperty;
import edu.wpi.first.cscore.VideoSink;
import edu.wpi.first.cscore.VideoSource;
import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicInteger;

public final class CameraServer {
    public static final int kBasePort = 1181;
    @Deprecated
    public static final int kSize640x480 = 0;
    @Deprecated
    public static final int kSize320x240 = 1;
    @Deprecated
    public static final int kSize160x120 = 2;
    private static final String kPublishName = "/CameraPublisher";
    private static CameraServer server;
    private static final AtomicInteger m_defaultUsbDevice;
    private static String m_primarySourceName;
    private static final Map<String, VideoSource> m_sources;
    private static final Map<String, VideoSink> m_sinks;
    private static final Map<Integer, NetworkTable> m_tables;
    private static final Map<Integer, Integer> m_fixedSources;
    private static final NetworkTable m_publishTable;
    private static final VideoListener m_videoListener;
    private static final int m_tableListener;
    private static int m_nextPort;
    private static String[] m_addresses;

    @Deprecated
    public static synchronized CameraServer getInstance() {
        if (server == null) {
            server = new CameraServer();
        }
        return server;
    }

    private static String makeSourceValue(int source) {
        switch (VideoSource.getKindFromInt(CameraServerJNI.getSourceKind(source))) {
            case kUsb: {
                return "usb:" + CameraServerJNI.getUsbCameraPath(source);
            }
            case kHttp: {
                String[] urls = CameraServerJNI.getHttpCameraUrls(source);
                if (urls.length > 0) {
                    return "ip:" + urls[0];
                }
                return "ip:";
            }
            case kCv: {
                return "cv:";
            }
        }
        return "unknown:";
    }

    private static String makeStreamValue(String address, int port) {
        return "mjpg:http://" + address + ":" + port + "/?action=stream";
    }

    private static synchronized String[] getSinkStreamValues(int sink) {
        if (VideoSink.getKindFromInt(CameraServerJNI.getSinkKind(sink)) != VideoSink.Kind.kMjpeg) {
            return new String[0];
        }
        int port = CameraServerJNI.getMjpegServerPort(sink);
        ArrayList<String> values = new ArrayList<String>(m_addresses.length + 1);
        String listenAddress = CameraServerJNI.getMjpegServerListenAddress(sink);
        if (!listenAddress.isEmpty()) {
            values.add(CameraServer.makeStreamValue(listenAddress, port));
        } else {
            values.add(CameraServer.makeStreamValue(CameraServerJNI.getHostname() + ".local", port));
            for (String addr : m_addresses) {
                if ("127.0.0.1".equals(addr)) continue;
                values.add(CameraServer.makeStreamValue(addr, port));
            }
        }
        return values.toArray(new String[0]);
    }

    private static synchronized String[] getSourceStreamValues(int source) {
        if (VideoSource.getKindFromInt(CameraServerJNI.getSourceKind(source)) != VideoSource.Kind.kHttp) {
            return new String[0];
        }
        String[] values = CameraServerJNI.getHttpCameraUrls(source);
        for (int j = 0; j < values.length; ++j) {
            values[j] = "mjpg:" + values[j];
        }
        if (CameraServerSharedStore.getCameraServerShared().isRoboRIO()) {
            for (VideoSink i : m_sinks.values()) {
                int sink = i.getHandle();
                int sinkSource = CameraServerJNI.getSinkSource(sink);
                if (source != sinkSource || VideoSink.getKindFromInt(CameraServerJNI.getSinkKind(sink)) != VideoSink.Kind.kMjpeg) continue;
                String[] finalValues = Arrays.copyOf(values, values.length + 1);
                int port = CameraServerJNI.getMjpegServerPort(sink);
                finalValues[values.length] = CameraServer.makeStreamValue("172.22.11.2", port);
                return finalValues;
            }
        }
        return values;
    }

    private static synchronized void updateStreamValues() {
        for (VideoSink videoSink : m_sinks.values()) {
            String[] values;
            NetworkTable table;
            int sink = videoSink.getHandle();
            int source = (Integer)Objects.requireNonNullElseGet((Object)m_fixedSources.get(sink), () -> CameraServerJNI.getSinkSource(sink));
            if (source == 0 || (table = m_tables.get(source)) == null || VideoSource.getKindFromInt(CameraServerJNI.getSourceKind(source)) == VideoSource.Kind.kHttp || (values = CameraServer.getSinkStreamValues(sink)).length <= 0) continue;
            table.getEntry("streams").setStringArray(values);
        }
        for (VideoSource videoSource : m_sources.values()) {
            String[] values;
            int source = videoSource.getHandle();
            NetworkTable table = m_tables.get(source);
            if (table == null || (values = CameraServer.getSourceStreamValues(source)).length <= 0) continue;
            table.getEntry("streams").setStringArray(values);
        }
    }

    private static String pixelFormatToString(VideoMode.PixelFormat pixelFormat) {
        switch (pixelFormat) {
            case kMJPEG: {
                return "MJPEG";
            }
            case kYUYV: {
                return "YUYV";
            }
            case kRGB565: {
                return "RGB565";
            }
            case kBGR: {
                return "BGR";
            }
            case kGray: {
                return "Gray";
            }
        }
        return "Unknown";
    }

    private static String videoModeToString(VideoMode mode) {
        return mode.width + "x" + mode.height + " " + CameraServer.pixelFormatToString(mode.pixelFormat) + " " + mode.fps + " fps";
    }

    private static String[] getSourceModeValues(int sourceHandle) {
        VideoMode[] modes = CameraServerJNI.enumerateSourceVideoModes(sourceHandle);
        String[] modeStrings = new String[modes.length];
        for (int i = 0; i < modes.length; ++i) {
            modeStrings[i] = CameraServer.videoModeToString(modes[i]);
        }
        return modeStrings;
    }

    private static void putSourcePropertyValue(NetworkTable table, VideoEvent event, boolean isNew) {
        String infoName;
        String name;
        if (event.name.startsWith("raw_")) {
            name = "RawProperty/" + event.name;
            infoName = "RawPropertyInfo/" + event.name;
        } else {
            name = "Property/" + event.name;
            infoName = "PropertyInfo/" + event.name;
        }
        NetworkTableEntry entry = table.getEntry(name);
        try {
            switch (event.propertyKind) {
                case kBoolean: {
                    if (isNew) {
                        entry.setDefaultBoolean(event.value != 0);
                        break;
                    }
                    entry.setBoolean(event.value != 0);
                    break;
                }
                case kInteger: 
                case kEnum: {
                    if (isNew) {
                        entry.setDefaultDouble(event.value);
                        table.getEntry(infoName + "/min").setDouble(CameraServerJNI.getPropertyMin(event.propertyHandle));
                        table.getEntry(infoName + "/max").setDouble(CameraServerJNI.getPropertyMax(event.propertyHandle));
                        table.getEntry(infoName + "/step").setDouble(CameraServerJNI.getPropertyStep(event.propertyHandle));
                        table.getEntry(infoName + "/default").setDouble(CameraServerJNI.getPropertyDefault(event.propertyHandle));
                        break;
                    }
                    entry.setDouble(event.value);
                    break;
                }
                case kString: {
                    if (isNew) {
                        entry.setDefaultString(event.valueStr);
                        break;
                    }
                    entry.setString(event.valueStr);
                    break;
                }
            }
        }
        catch (VideoException videoException) {
            // empty catch block
        }
    }

    private CameraServer() {
    }

    public static UsbCamera startAutomaticCapture() {
        UsbCamera camera = CameraServer.startAutomaticCapture(m_defaultUsbDevice.getAndIncrement());
        CameraServerSharedStore.getCameraServerShared().reportUsbCamera(camera.getHandle());
        return camera;
    }

    public static UsbCamera startAutomaticCapture(int dev) {
        UsbCamera camera = new UsbCamera("USB Camera " + dev, dev);
        CameraServer.startAutomaticCapture(camera);
        CameraServerSharedStore.getCameraServerShared().reportUsbCamera(camera.getHandle());
        return camera;
    }

    public static UsbCamera startAutomaticCapture(String name, int dev) {
        UsbCamera camera = new UsbCamera(name, dev);
        CameraServer.startAutomaticCapture(camera);
        CameraServerSharedStore.getCameraServerShared().reportUsbCamera(camera.getHandle());
        return camera;
    }

    public static UsbCamera startAutomaticCapture(String name, String path) {
        UsbCamera camera = new UsbCamera(name, path);
        CameraServer.startAutomaticCapture(camera);
        CameraServerSharedStore.getCameraServerShared().reportUsbCamera(camera.getHandle());
        return camera;
    }

    public static MjpegServer startAutomaticCapture(VideoSource camera) {
        CameraServer.addCamera(camera);
        MjpegServer server = CameraServer.addServer("serve_" + camera.getName());
        server.setSource(camera);
        return server;
    }

    public static AxisCamera addAxisCamera(String host) {
        return CameraServer.addAxisCamera("Axis Camera", host);
    }

    public static AxisCamera addAxisCamera(String[] hosts) {
        return CameraServer.addAxisCamera("Axis Camera", hosts);
    }

    public static AxisCamera addAxisCamera(String name, String host) {
        AxisCamera camera = new AxisCamera(name, host);
        CameraServer.startAutomaticCapture(camera);
        CameraServerSharedStore.getCameraServerShared().reportAxisCamera(camera.getHandle());
        return camera;
    }

    public static AxisCamera addAxisCamera(String name, String[] hosts) {
        AxisCamera camera = new AxisCamera(name, hosts);
        CameraServer.startAutomaticCapture(camera);
        CameraServerSharedStore.getCameraServerShared().reportAxisCamera(camera.getHandle());
        return camera;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public static MjpegServer addSwitchedCamera(String name) {
        CvSource source = new CvSource(name, VideoMode.PixelFormat.kMJPEG, 160, 120, 30);
        MjpegServer server = CameraServer.startAutomaticCapture(source);
        Class<CameraServer> class_ = CameraServer.class;
        synchronized (CameraServer.class) {
            m_fixedSources.put(server.getHandle(), source.getHandle());
            // ** MonitorExit[var3_3] (shouldn't be in output)
            return server;
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public static CvSink getVideo() {
        Class<CameraServer> class_ = CameraServer.class;
        synchronized (CameraServer.class) {
            if (m_primarySourceName == null) {
                throw new VideoException("no camera available");
            }
            VideoSource source = m_sources.get(m_primarySourceName);
            // ** MonitorExit[var1] (shouldn't be in output)
            if (source == null) {
                throw new VideoException("no camera available");
            }
            return CameraServer.getVideo(source);
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public static CvSink getVideo(VideoSource camera) {
        String name = "opencv_" + camera.getName();
        Class<CameraServer> class_ = CameraServer.class;
        synchronized (CameraServer.class) {
            VideoSink sink = m_sinks.get(name);
            if (sink != null) {
                VideoSink.Kind kind = sink.getKind();
                if (kind != VideoSink.Kind.kCv) {
                    throw new VideoException("expected OpenCV sink, but got " + kind);
                }
                // ** MonitorExit[var2_2] (shouldn't be in output)
                return (CvSink)sink;
            }
            // ** MonitorExit[var2_2] (shouldn't be in output)
            CvSink newsink = new CvSink(name);
            newsink.setSource(camera);
            CameraServer.addServer(newsink);
            return newsink;
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public static CvSink getVideo(String name) {
        Class<CameraServer> class_ = CameraServer.class;
        synchronized (CameraServer.class) {
            VideoSource source = m_sources.get(name);
            if (source == null) {
                throw new VideoException("could not find camera " + name);
            }
            // ** MonitorExit[var2_1] (shouldn't be in output)
            return CameraServer.getVideo(source);
        }
    }

    public static CvSource putVideo(String name, int width, int height) {
        CvSource source = new CvSource(name, VideoMode.PixelFormat.kMJPEG, width, height, 30);
        CameraServer.startAutomaticCapture(source);
        return source;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public static MjpegServer addServer(String name) {
        Class<CameraServer> class_ = CameraServer.class;
        synchronized (CameraServer.class) {
            int port = m_nextPort++;
            // ** MonitorExit[var2_1] (shouldn't be in output)
            return CameraServer.addServer(name, port);
        }
    }

    public static MjpegServer addServer(String name, int port) {
        MjpegServer server = new MjpegServer(name, port);
        CameraServer.addServer(server);
        return server;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public static void addServer(VideoSink server) {
        Class<CameraServer> class_ = CameraServer.class;
        synchronized (CameraServer.class) {
            m_sinks.put(server.getName(), server);
            // ** MonitorExit[var1_1] (shouldn't be in output)
            return;
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public static void removeServer(String name) {
        Class<CameraServer> class_ = CameraServer.class;
        synchronized (CameraServer.class) {
            m_sinks.remove(name);
            // ** MonitorExit[var1_1] (shouldn't be in output)
            return;
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public static VideoSink getServer() {
        Class<CameraServer> class_ = CameraServer.class;
        synchronized (CameraServer.class) {
            if (m_primarySourceName == null) {
                throw new VideoException("no camera available");
            }
            // ** MonitorExit[var0] (shouldn't be in output)
            return CameraServer.getServer("serve_" + m_primarySourceName);
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public static VideoSink getServer(String name) {
        Class<CameraServer> class_ = CameraServer.class;
        synchronized (CameraServer.class) {
            // ** MonitorExit[var1_1] (shouldn't be in output)
            return m_sinks.get(name);
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public static void addCamera(VideoSource camera) {
        String name = camera.getName();
        Class<CameraServer> class_ = CameraServer.class;
        synchronized (CameraServer.class) {
            if (m_primarySourceName == null) {
                m_primarySourceName = name;
            }
            m_sources.put(name, camera);
            // ** MonitorExit[var2_2] (shouldn't be in output)
            return;
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public static void removeCamera(String name) {
        Class<CameraServer> class_ = CameraServer.class;
        synchronized (CameraServer.class) {
            m_sources.remove(name);
            // ** MonitorExit[var1_1] (shouldn't be in output)
            return;
        }
    }

    static {
        m_defaultUsbDevice = new AtomicInteger();
        m_sources = new HashMap<String, VideoSource>();
        m_sinks = new HashMap<String, VideoSink>();
        m_tables = new HashMap<Integer, NetworkTable>();
        m_fixedSources = new HashMap<Integer, Integer>();
        m_publishTable = NetworkTableInstance.getDefault().getTable(kPublishName);
        m_videoListener = new VideoListener(event -> {
            switch (event.kind) {
                case kSourceCreated: {
                    NetworkTable table = m_publishTable.getSubTable(event.name);
                    m_tables.put(event.sourceHandle, table);
                    table.getEntry("source").setString(CameraServer.makeSourceValue(event.sourceHandle));
                    table.getEntry("description").setString(CameraServerJNI.getSourceDescription(event.sourceHandle));
                    table.getEntry("connected").setBoolean(CameraServerJNI.isSourceConnected(event.sourceHandle));
                    table.getEntry("streams").setStringArray(CameraServer.getSourceStreamValues(event.sourceHandle));
                    try {
                        VideoMode mode = CameraServerJNI.getSourceVideoMode(event.sourceHandle);
                        table.getEntry("mode").setDefaultString(CameraServer.videoModeToString(mode));
                        table.getEntry("modes").setStringArray(CameraServer.getSourceModeValues(event.sourceHandle));
                    }
                    catch (VideoException mode) {}
                    break;
                }
                case kSourceDestroyed: {
                    NetworkTable table = m_tables.get(event.sourceHandle);
                    if (table == null) break;
                    table.getEntry("source").setString("");
                    table.getEntry("streams").setStringArray(new String[0]);
                    table.getEntry("modes").setStringArray(new String[0]);
                    break;
                }
                case kSourceConnected: {
                    NetworkTable table = m_tables.get(event.sourceHandle);
                    if (table == null) break;
                    table.getEntry("description").setString(CameraServerJNI.getSourceDescription(event.sourceHandle));
                    table.getEntry("connected").setBoolean(true);
                    break;
                }
                case kSourceDisconnected: {
                    NetworkTable table = m_tables.get(event.sourceHandle);
                    if (table == null) break;
                    table.getEntry("connected").setBoolean(false);
                    break;
                }
                case kSourceVideoModesUpdated: {
                    NetworkTable table = m_tables.get(event.sourceHandle);
                    if (table == null) break;
                    table.getEntry("modes").setStringArray(CameraServer.getSourceModeValues(event.sourceHandle));
                    break;
                }
                case kSourceVideoModeChanged: {
                    NetworkTable table = m_tables.get(event.sourceHandle);
                    if (table == null) break;
                    table.getEntry("mode").setString(CameraServer.videoModeToString(event.mode));
                    break;
                }
                case kSourcePropertyCreated: {
                    NetworkTable table = m_tables.get(event.sourceHandle);
                    if (table == null) break;
                    CameraServer.putSourcePropertyValue(table, event, true);
                    break;
                }
                case kSourcePropertyValueUpdated: {
                    NetworkTable table = m_tables.get(event.sourceHandle);
                    if (table == null) break;
                    CameraServer.putSourcePropertyValue(table, event, false);
                    break;
                }
                case kSourcePropertyChoicesUpdated: {
                    NetworkTable table = m_tables.get(event.sourceHandle);
                    if (table == null) break;
                    try {
                        String[] choices = CameraServerJNI.getEnumPropertyChoices(event.propertyHandle);
                        table.getEntry("PropertyInfo/" + event.name + "/choices").setStringArray(choices);
                    }
                    catch (VideoException videoException) {}
                    break;
                }
                case kSinkSourceChanged: 
                case kSinkCreated: 
                case kSinkDestroyed: 
                case kNetworkInterfacesChanged: {
                    m_addresses = CameraServerJNI.getNetworkInterfaces();
                    CameraServer.updateStreamValues();
                    break;
                }
            }
        }, 20479, true);
        m_tableListener = NetworkTableInstance.getDefault().addEntryListener("/CameraPublisher/", event -> {
            String propName;
            String relativeKey = event.name.substring(kPublishName.length() + 1);
            int subKeyIndex = relativeKey.indexOf(47);
            if (subKeyIndex == -1) {
                return;
            }
            String sourceName = relativeKey.substring(0, subKeyIndex);
            VideoSource source = m_sources.get(sourceName);
            if (source == null) {
                return;
            }
            if ("mode".equals(relativeKey = relativeKey.substring(subKeyIndex + 1))) {
                event.getEntry().setString(CameraServer.videoModeToString(source.getVideoMode()));
                return;
            }
            if (relativeKey.startsWith("Property/")) {
                propName = relativeKey.substring(9);
            } else if (relativeKey.startsWith("RawProperty/")) {
                propName = relativeKey.substring(12);
            } else {
                return;
            }
            VideoProperty property = source.getProperty(propName);
            switch (property.getKind()) {
                case kNone: {
                    return;
                }
                case kBoolean: {
                    event.getEntry().setBoolean(property.get() != 0);
                    return;
                }
                case kInteger: 
                case kEnum: {
                    event.getEntry().setDouble(property.get());
                    return;
                }
                case kString: {
                    event.getEntry().setString(property.getString());
                    return;
                }
            }
        }, 17);
        m_nextPort = 1181;
        m_addresses = new String[0];
    }
}

