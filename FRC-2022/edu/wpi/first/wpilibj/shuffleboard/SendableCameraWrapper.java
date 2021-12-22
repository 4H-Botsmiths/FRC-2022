/*
 * Decompiled with CFR 0.150.
 */
package edu.wpi.first.wpilibj.shuffleboard;

import edu.wpi.first.cscore.VideoSource;
import edu.wpi.first.util.sendable.Sendable;
import edu.wpi.first.util.sendable.SendableBuilder;
import edu.wpi.first.util.sendable.SendableRegistry;
import java.util.Map;
import java.util.WeakHashMap;

public final class SendableCameraWrapper
implements Sendable,
AutoCloseable {
    private static final String kProtocol = "camera_server://";
    private static Map<VideoSource, SendableCameraWrapper> m_wrappers = new WeakHashMap<VideoSource, SendableCameraWrapper>();
    private final String m_uri;

    private SendableCameraWrapper(VideoSource source) {
        String name = source.getName();
        SendableRegistry.add(this, name);
        this.m_uri = kProtocol + name;
    }

    @Override
    public void close() {
        SendableRegistry.remove(this);
    }

    public static SendableCameraWrapper wrap(VideoSource source) {
        return m_wrappers.computeIfAbsent(source, SendableCameraWrapper::new);
    }

    @Override
    public void initSendable(SendableBuilder builder) {
        builder.addStringProperty(".ShuffleboardURI", () -> this.m_uri, null);
    }
}

