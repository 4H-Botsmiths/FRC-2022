/*
 * Decompiled with CFR 0.150.
 */
package edu.wpi.first.wpilibj.simulation;

import edu.wpi.first.hal.simulation.AddressableLEDDataJNI;
import edu.wpi.first.hal.simulation.ConstBufferCallback;
import edu.wpi.first.hal.simulation.NotifyCallback;
import edu.wpi.first.wpilibj.AddressableLED;
import edu.wpi.first.wpilibj.simulation.CallbackStore;
import java.util.NoSuchElementException;

public class AddressableLEDSim {
    private final int m_index;

    public AddressableLEDSim() {
        this.m_index = 0;
    }

    public AddressableLEDSim(AddressableLED addressableLED) {
        this.m_index = 0;
    }

    private AddressableLEDSim(int index) {
        this.m_index = index;
    }

    public static AddressableLEDSim createForChannel(int pwmChannel) {
        int index = AddressableLEDDataJNI.findForChannel(pwmChannel);
        if (index < 0) {
            throw new NoSuchElementException("no addressable LED found for PWM channel " + pwmChannel);
        }
        return new AddressableLEDSim(index);
    }

    public static AddressableLEDSim createForIndex(int index) {
        return new AddressableLEDSim(index);
    }

    public CallbackStore registerInitializedCallback(NotifyCallback callback, boolean initialNotify) {
        int uid = AddressableLEDDataJNI.registerInitializedCallback(this.m_index, callback, initialNotify);
        return new CallbackStore(this.m_index, uid, AddressableLEDDataJNI::cancelInitializedCallback);
    }

    public boolean getInitialized() {
        return AddressableLEDDataJNI.getInitialized(this.m_index);
    }

    public void setInitialized(boolean initialized) {
        AddressableLEDDataJNI.setInitialized(this.m_index, initialized);
    }

    public CallbackStore registerOutputPortCallback(NotifyCallback callback, boolean initialNotify) {
        int uid = AddressableLEDDataJNI.registerOutputPortCallback(this.m_index, callback, initialNotify);
        return new CallbackStore(this.m_index, uid, AddressableLEDDataJNI::cancelOutputPortCallback);
    }

    public int getOutputPort() {
        return AddressableLEDDataJNI.getOutputPort(this.m_index);
    }

    public void setOutputPort(int outputPort) {
        AddressableLEDDataJNI.setOutputPort(this.m_index, outputPort);
    }

    public CallbackStore registerLengthCallback(NotifyCallback callback, boolean initialNotify) {
        int uid = AddressableLEDDataJNI.registerLengthCallback(this.m_index, callback, initialNotify);
        return new CallbackStore(this.m_index, uid, AddressableLEDDataJNI::cancelLengthCallback);
    }

    public int getLength() {
        return AddressableLEDDataJNI.getLength(this.m_index);
    }

    public void setLength(int length) {
        AddressableLEDDataJNI.setLength(this.m_index, length);
    }

    public CallbackStore registerRunningCallback(NotifyCallback callback, boolean initialNotify) {
        int uid = AddressableLEDDataJNI.registerRunningCallback(this.m_index, callback, initialNotify);
        return new CallbackStore(this.m_index, uid, AddressableLEDDataJNI::cancelRunningCallback);
    }

    public boolean getRunning() {
        return AddressableLEDDataJNI.getRunning(this.m_index);
    }

    public void setRunning(boolean running) {
        AddressableLEDDataJNI.setRunning(this.m_index, running);
    }

    public CallbackStore registerDataCallback(ConstBufferCallback callback) {
        int uid = AddressableLEDDataJNI.registerDataCallback(this.m_index, callback);
        return new CallbackStore(this.m_index, uid, AddressableLEDDataJNI::cancelDataCallback);
    }

    public byte[] getData() {
        return AddressableLEDDataJNI.getData(this.m_index);
    }

    public void setData(byte[] data) {
        AddressableLEDDataJNI.setData(this.m_index, data);
    }

    public void resetData() {
        AddressableLEDDataJNI.resetData(this.m_index);
    }
}

