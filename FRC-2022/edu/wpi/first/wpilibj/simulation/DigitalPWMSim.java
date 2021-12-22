/*
 * Decompiled with CFR 0.150.
 */
package edu.wpi.first.wpilibj.simulation;

import edu.wpi.first.hal.simulation.DigitalPWMDataJNI;
import edu.wpi.first.hal.simulation.NotifyCallback;
import edu.wpi.first.wpilibj.DigitalOutput;
import edu.wpi.first.wpilibj.simulation.CallbackStore;
import java.util.NoSuchElementException;

public class DigitalPWMSim {
    private final int m_index;

    public DigitalPWMSim(DigitalOutput digitalOutput) {
        this.m_index = digitalOutput.getChannel();
    }

    private DigitalPWMSim(int index) {
        this.m_index = index;
    }

    public static DigitalPWMSim createForChannel(int channel) {
        int index = DigitalPWMDataJNI.findForChannel(channel);
        if (index < 0) {
            throw new NoSuchElementException("no digital PWM found for channel " + channel);
        }
        return new DigitalPWMSim(index);
    }

    public static DigitalPWMSim createForIndex(int index) {
        return new DigitalPWMSim(index);
    }

    public CallbackStore registerInitializedCallback(NotifyCallback callback, boolean initialNotify) {
        int uid = DigitalPWMDataJNI.registerInitializedCallback(this.m_index, callback, initialNotify);
        return new CallbackStore(this.m_index, uid, DigitalPWMDataJNI::cancelInitializedCallback);
    }

    public boolean getInitialized() {
        return DigitalPWMDataJNI.getInitialized(this.m_index);
    }

    public void setInitialized(boolean initialized) {
        DigitalPWMDataJNI.setInitialized(this.m_index, initialized);
    }

    public CallbackStore registerDutyCycleCallback(NotifyCallback callback, boolean initialNotify) {
        int uid = DigitalPWMDataJNI.registerDutyCycleCallback(this.m_index, callback, initialNotify);
        return new CallbackStore(this.m_index, uid, DigitalPWMDataJNI::cancelDutyCycleCallback);
    }

    public double getDutyCycle() {
        return DigitalPWMDataJNI.getDutyCycle(this.m_index);
    }

    public void setDutyCycle(double dutyCycle) {
        DigitalPWMDataJNI.setDutyCycle(this.m_index, dutyCycle);
    }

    public CallbackStore registerPinCallback(NotifyCallback callback, boolean initialNotify) {
        int uid = DigitalPWMDataJNI.registerPinCallback(this.m_index, callback, initialNotify);
        return new CallbackStore(this.m_index, uid, DigitalPWMDataJNI::cancelPinCallback);
    }

    public int getPin() {
        return DigitalPWMDataJNI.getPin(this.m_index);
    }

    public void setPin(int pin) {
        DigitalPWMDataJNI.setPin(this.m_index, pin);
    }

    public void resetData() {
        DigitalPWMDataJNI.resetData(this.m_index);
    }
}

