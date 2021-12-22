/*
 * Decompiled with CFR 0.150.
 */
package edu.wpi.first.wpilibj.simulation;

import edu.wpi.first.hal.simulation.NotifyCallback;
import edu.wpi.first.hal.simulation.RelayDataJNI;
import edu.wpi.first.wpilibj.Relay;
import edu.wpi.first.wpilibj.simulation.CallbackStore;

public class RelaySim {
    private final int m_index;

    public RelaySim(Relay relay) {
        this.m_index = relay.getChannel();
    }

    public RelaySim(int channel) {
        this.m_index = channel;
    }

    public CallbackStore registerInitializedForwardCallback(NotifyCallback callback, boolean initialNotify) {
        int uid = RelayDataJNI.registerInitializedForwardCallback(this.m_index, callback, initialNotify);
        return new CallbackStore(this.m_index, uid, RelayDataJNI::cancelInitializedForwardCallback);
    }

    public boolean getInitializedForward() {
        return RelayDataJNI.getInitializedForward(this.m_index);
    }

    public void setInitializedForward(boolean initializedForward) {
        RelayDataJNI.setInitializedForward(this.m_index, initializedForward);
    }

    public CallbackStore registerInitializedReverseCallback(NotifyCallback callback, boolean initialNotify) {
        int uid = RelayDataJNI.registerInitializedReverseCallback(this.m_index, callback, initialNotify);
        return new CallbackStore(this.m_index, uid, RelayDataJNI::cancelInitializedReverseCallback);
    }

    public boolean getInitializedReverse() {
        return RelayDataJNI.getInitializedReverse(this.m_index);
    }

    public void setInitializedReverse(boolean initializedReverse) {
        RelayDataJNI.setInitializedReverse(this.m_index, initializedReverse);
    }

    public CallbackStore registerForwardCallback(NotifyCallback callback, boolean initialNotify) {
        int uid = RelayDataJNI.registerForwardCallback(this.m_index, callback, initialNotify);
        return new CallbackStore(this.m_index, uid, RelayDataJNI::cancelForwardCallback);
    }

    public boolean getForward() {
        return RelayDataJNI.getForward(this.m_index);
    }

    public void setForward(boolean forward) {
        RelayDataJNI.setForward(this.m_index, forward);
    }

    public CallbackStore registerReverseCallback(NotifyCallback callback, boolean initialNotify) {
        int uid = RelayDataJNI.registerReverseCallback(this.m_index, callback, initialNotify);
        return new CallbackStore(this.m_index, uid, RelayDataJNI::cancelReverseCallback);
    }

    public boolean getReverse() {
        return RelayDataJNI.getReverse(this.m_index);
    }

    public void setReverse(boolean reverse) {
        RelayDataJNI.setReverse(this.m_index, reverse);
    }

    public void resetData() {
        RelayDataJNI.resetData(this.m_index);
    }
}

