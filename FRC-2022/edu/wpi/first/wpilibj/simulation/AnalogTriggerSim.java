/*
 * Decompiled with CFR 0.150.
 */
package edu.wpi.first.wpilibj.simulation;

import edu.wpi.first.hal.simulation.AnalogTriggerDataJNI;
import edu.wpi.first.hal.simulation.NotifyCallback;
import edu.wpi.first.wpilibj.AnalogTrigger;
import edu.wpi.first.wpilibj.simulation.CallbackStore;
import java.util.NoSuchElementException;

public class AnalogTriggerSim {
    private final int m_index;

    public AnalogTriggerSim(AnalogTrigger analogTrigger) {
        this.m_index = analogTrigger.getIndex();
    }

    private AnalogTriggerSim(int index) {
        this.m_index = index;
    }

    public static AnalogTriggerSim createForChannel(int channel) {
        int index = AnalogTriggerDataJNI.findForChannel(channel);
        if (index < 0) {
            throw new NoSuchElementException("no analog trigger found for channel " + channel);
        }
        return new AnalogTriggerSim(index);
    }

    public static AnalogTriggerSim createForIndex(int index) {
        return new AnalogTriggerSim(index);
    }

    public CallbackStore registerInitializedCallback(NotifyCallback callback, boolean initialNotify) {
        int uid = AnalogTriggerDataJNI.registerInitializedCallback(this.m_index, callback, initialNotify);
        return new CallbackStore(this.m_index, uid, AnalogTriggerDataJNI::cancelInitializedCallback);
    }

    public boolean getInitialized() {
        return AnalogTriggerDataJNI.getInitialized(this.m_index);
    }

    public void setInitialized(boolean initialized) {
        AnalogTriggerDataJNI.setInitialized(this.m_index, initialized);
    }

    public CallbackStore registerTriggerLowerBoundCallback(NotifyCallback callback, boolean initialNotify) {
        int uid = AnalogTriggerDataJNI.registerTriggerLowerBoundCallback(this.m_index, callback, initialNotify);
        return new CallbackStore(this.m_index, uid, AnalogTriggerDataJNI::cancelTriggerLowerBoundCallback);
    }

    public double getTriggerLowerBound() {
        return AnalogTriggerDataJNI.getTriggerLowerBound(this.m_index);
    }

    public void setTriggerLowerBound(double triggerLowerBound) {
        AnalogTriggerDataJNI.setTriggerLowerBound(this.m_index, triggerLowerBound);
    }

    public CallbackStore registerTriggerUpperBoundCallback(NotifyCallback callback, boolean initialNotify) {
        int uid = AnalogTriggerDataJNI.registerTriggerUpperBoundCallback(this.m_index, callback, initialNotify);
        return new CallbackStore(this.m_index, uid, AnalogTriggerDataJNI::cancelTriggerUpperBoundCallback);
    }

    public double getTriggerUpperBound() {
        return AnalogTriggerDataJNI.getTriggerUpperBound(this.m_index);
    }

    public void setTriggerUpperBound(double triggerUpperBound) {
        AnalogTriggerDataJNI.setTriggerUpperBound(this.m_index, triggerUpperBound);
    }

    public void resetData() {
        AnalogTriggerDataJNI.resetData(this.m_index);
    }
}

