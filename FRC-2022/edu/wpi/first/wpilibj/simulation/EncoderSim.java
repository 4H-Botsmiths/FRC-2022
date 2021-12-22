/*
 * Decompiled with CFR 0.150.
 */
package edu.wpi.first.wpilibj.simulation;

import edu.wpi.first.hal.simulation.EncoderDataJNI;
import edu.wpi.first.hal.simulation.NotifyCallback;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.simulation.CallbackStore;
import java.util.NoSuchElementException;

public class EncoderSim {
    private final int m_index;

    public EncoderSim(Encoder encoder) {
        this.m_index = encoder.getFPGAIndex();
    }

    private EncoderSim(int index) {
        this.m_index = index;
    }

    public static EncoderSim createForChannel(int channel) {
        int index = EncoderDataJNI.findForChannel(channel);
        if (index < 0) {
            throw new NoSuchElementException("no encoder found for channel " + channel);
        }
        return new EncoderSim(index);
    }

    public static EncoderSim createForIndex(int index) {
        return new EncoderSim(index);
    }

    public CallbackStore registerInitializedCallback(NotifyCallback callback, boolean initialNotify) {
        int uid = EncoderDataJNI.registerInitializedCallback(this.m_index, callback, initialNotify);
        return new CallbackStore(this.m_index, uid, EncoderDataJNI::cancelInitializedCallback);
    }

    public boolean getInitialized() {
        return EncoderDataJNI.getInitialized(this.m_index);
    }

    public void setInitialized(boolean initialized) {
        EncoderDataJNI.setInitialized(this.m_index, initialized);
    }

    public CallbackStore registerCountCallback(NotifyCallback callback, boolean initialNotify) {
        int uid = EncoderDataJNI.registerCountCallback(this.m_index, callback, initialNotify);
        return new CallbackStore(this.m_index, uid, EncoderDataJNI::cancelCountCallback);
    }

    public int getCount() {
        return EncoderDataJNI.getCount(this.m_index);
    }

    public void setCount(int count) {
        EncoderDataJNI.setCount(this.m_index, count);
    }

    public CallbackStore registerPeriodCallback(NotifyCallback callback, boolean initialNotify) {
        int uid = EncoderDataJNI.registerPeriodCallback(this.m_index, callback, initialNotify);
        return new CallbackStore(this.m_index, uid, EncoderDataJNI::cancelPeriodCallback);
    }

    public double getPeriod() {
        return EncoderDataJNI.getPeriod(this.m_index);
    }

    public void setPeriod(double period) {
        EncoderDataJNI.setPeriod(this.m_index, period);
    }

    public CallbackStore registerResetCallback(NotifyCallback callback, boolean initialNotify) {
        int uid = EncoderDataJNI.registerResetCallback(this.m_index, callback, initialNotify);
        return new CallbackStore(this.m_index, uid, EncoderDataJNI::cancelResetCallback);
    }

    public boolean getReset() {
        return EncoderDataJNI.getReset(this.m_index);
    }

    public void setReset(boolean reset) {
        EncoderDataJNI.setReset(this.m_index, reset);
    }

    public CallbackStore registerMaxPeriodCallback(NotifyCallback callback, boolean initialNotify) {
        int uid = EncoderDataJNI.registerMaxPeriodCallback(this.m_index, callback, initialNotify);
        return new CallbackStore(this.m_index, uid, EncoderDataJNI::cancelMaxPeriodCallback);
    }

    public double getMaxPeriod() {
        return EncoderDataJNI.getMaxPeriod(this.m_index);
    }

    public void setMaxPeriod(double maxPeriod) {
        EncoderDataJNI.setMaxPeriod(this.m_index, maxPeriod);
    }

    public CallbackStore registerDirectionCallback(NotifyCallback callback, boolean initialNotify) {
        int uid = EncoderDataJNI.registerDirectionCallback(this.m_index, callback, initialNotify);
        return new CallbackStore(this.m_index, uid, EncoderDataJNI::cancelDirectionCallback);
    }

    public boolean getDirection() {
        return EncoderDataJNI.getDirection(this.m_index);
    }

    public void setDirection(boolean direction) {
        EncoderDataJNI.setDirection(this.m_index, direction);
    }

    public CallbackStore registerReverseDirectionCallback(NotifyCallback callback, boolean initialNotify) {
        int uid = EncoderDataJNI.registerReverseDirectionCallback(this.m_index, callback, initialNotify);
        return new CallbackStore(this.m_index, uid, EncoderDataJNI::cancelReverseDirectionCallback);
    }

    public boolean getReverseDirection() {
        return EncoderDataJNI.getReverseDirection(this.m_index);
    }

    public void setReverseDirection(boolean reverseDirection) {
        EncoderDataJNI.setReverseDirection(this.m_index, reverseDirection);
    }

    public CallbackStore registerSamplesToAverageCallback(NotifyCallback callback, boolean initialNotify) {
        int uid = EncoderDataJNI.registerSamplesToAverageCallback(this.m_index, callback, initialNotify);
        return new CallbackStore(this.m_index, uid, EncoderDataJNI::cancelSamplesToAverageCallback);
    }

    public int getSamplesToAverage() {
        return EncoderDataJNI.getSamplesToAverage(this.m_index);
    }

    public void setSamplesToAverage(int samplesToAverage) {
        EncoderDataJNI.setSamplesToAverage(this.m_index, samplesToAverage);
    }

    public void setDistance(double distance) {
        EncoderDataJNI.setDistance(this.m_index, distance);
    }

    public double getDistance() {
        return EncoderDataJNI.getDistance(this.m_index);
    }

    public void setRate(double rate) {
        EncoderDataJNI.setRate(this.m_index, rate);
    }

    public double getRate() {
        return EncoderDataJNI.getRate(this.m_index);
    }

    public void resetData() {
        EncoderDataJNI.resetData(this.m_index);
    }
}

