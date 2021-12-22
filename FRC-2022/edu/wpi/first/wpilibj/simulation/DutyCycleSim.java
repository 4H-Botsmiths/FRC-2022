/*
 * Decompiled with CFR 0.150.
 */
package edu.wpi.first.wpilibj.simulation;

import edu.wpi.first.hal.simulation.DutyCycleDataJNI;
import edu.wpi.first.hal.simulation.NotifyCallback;
import edu.wpi.first.wpilibj.DutyCycle;
import edu.wpi.first.wpilibj.simulation.CallbackStore;
import java.util.NoSuchElementException;

public class DutyCycleSim {
    private final int m_index;

    public DutyCycleSim(DutyCycle dutyCycle) {
        this.m_index = dutyCycle.getFPGAIndex();
    }

    private DutyCycleSim(int index) {
        this.m_index = index;
    }

    public static DutyCycleSim createForChannel(int channel) {
        int index = DutyCycleDataJNI.findForChannel(channel);
        if (index < 0) {
            throw new NoSuchElementException("no duty cycle found for channel " + channel);
        }
        return new DutyCycleSim(index);
    }

    public static DutyCycleSim createForIndex(int index) {
        return new DutyCycleSim(index);
    }

    public CallbackStore registerInitializedCallback(NotifyCallback callback, boolean initialNotify) {
        int uid = DutyCycleDataJNI.registerInitializedCallback(this.m_index, callback, initialNotify);
        return new CallbackStore(this.m_index, uid, DutyCycleDataJNI::cancelInitializedCallback);
    }

    public boolean getInitialized() {
        return DutyCycleDataJNI.getInitialized(this.m_index);
    }

    public void setInitialized(boolean initialized) {
        DutyCycleDataJNI.setInitialized(this.m_index, initialized);
    }

    public CallbackStore registerFrequencyCallback(NotifyCallback callback, boolean initialNotify) {
        int uid = DutyCycleDataJNI.registerFrequencyCallback(this.m_index, callback, initialNotify);
        return new CallbackStore(this.m_index, uid, DutyCycleDataJNI::cancelFrequencyCallback);
    }

    public int getFrequency() {
        return DutyCycleDataJNI.getFrequency(this.m_index);
    }

    public void setFrequency(int frequency) {
        DutyCycleDataJNI.setFrequency(this.m_index, frequency);
    }

    public CallbackStore registerOutputCallback(NotifyCallback callback, boolean initialNotify) {
        int uid = DutyCycleDataJNI.registerOutputCallback(this.m_index, callback, initialNotify);
        return new CallbackStore(this.m_index, uid, DutyCycleDataJNI::cancelOutputCallback);
    }

    public double getOutput() {
        return DutyCycleDataJNI.getOutput(this.m_index);
    }

    public void setOutput(double output) {
        DutyCycleDataJNI.setOutput(this.m_index, output);
    }

    public void resetData() {
        DutyCycleDataJNI.resetData(this.m_index);
    }
}

