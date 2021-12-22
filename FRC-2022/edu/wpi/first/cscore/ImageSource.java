/*
 * Decompiled with CFR 0.150.
 */
package edu.wpi.first.cscore;

import edu.wpi.first.cscore.CameraServerJNI;
import edu.wpi.first.cscore.VideoProperty;
import edu.wpi.first.cscore.VideoSource;

public abstract class ImageSource
extends VideoSource {
    protected ImageSource(int handle) {
        super(handle);
    }

    public void notifyError(String msg) {
        CameraServerJNI.notifySourceError(this.m_handle, msg);
    }

    public void setConnected(boolean connected) {
        CameraServerJNI.setSourceConnected(this.m_handle, connected);
    }

    public void setDescription(String description) {
        CameraServerJNI.setSourceDescription(this.m_handle, description);
    }

    public VideoProperty createProperty(String name, VideoProperty.Kind kind, int minimum, int maximum, int step, int defaultValue, int value) {
        return new VideoProperty(CameraServerJNI.createSourceProperty(this.m_handle, name, kind.getValue(), minimum, maximum, step, defaultValue, value));
    }

    public VideoProperty createIntegerProperty(String name, int minimum, int maximum, int step, int defaultValue, int value) {
        return new VideoProperty(CameraServerJNI.createSourceProperty(this.m_handle, name, VideoProperty.Kind.kInteger.getValue(), minimum, maximum, step, defaultValue, value));
    }

    public VideoProperty createBooleanProperty(String name, boolean defaultValue, boolean value) {
        return new VideoProperty(CameraServerJNI.createSourceProperty(this.m_handle, name, VideoProperty.Kind.kBoolean.getValue(), 0, 1, 1, defaultValue ? 1 : 0, value ? 1 : 0));
    }

    public VideoProperty createStringProperty(String name, String value) {
        VideoProperty prop = new VideoProperty(CameraServerJNI.createSourceProperty(this.m_handle, name, VideoProperty.Kind.kString.getValue(), 0, 0, 0, 0, 0));
        prop.setString(value);
        return prop;
    }

    public void setEnumPropertyChoices(VideoProperty property, String[] choices) {
        CameraServerJNI.setSourceEnumPropertyChoices(this.m_handle, property.m_handle, choices);
    }
}

