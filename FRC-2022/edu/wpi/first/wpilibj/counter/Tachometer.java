/*
 * Decompiled with CFR 0.150.
 */
package edu.wpi.first.wpilibj.counter;

import edu.wpi.first.hal.CounterJNI;
import edu.wpi.first.hal.HAL;
import edu.wpi.first.util.sendable.Sendable;
import edu.wpi.first.util.sendable.SendableBuilder;
import edu.wpi.first.util.sendable.SendableRegistry;
import edu.wpi.first.wpilibj.DigitalSource;
import edu.wpi.first.wpilibj.util.ErrorMessages;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;

public class Tachometer
implements Sendable,
AutoCloseable {
    private final DigitalSource m_source;
    private final int m_handle;
    private int m_edgesPerRevolution = 1;

    public Tachometer(DigitalSource source) {
        this.m_source = ErrorMessages.requireNonNullParam(source, "source", "Tachometer");
        ByteBuffer index = ByteBuffer.allocateDirect(4);
        index.order(ByteOrder.LITTLE_ENDIAN);
        this.m_handle = CounterJNI.initializeCounter(0, index.asIntBuffer());
        CounterJNI.setCounterUpSource(this.m_handle, source.getPortHandleForRouting(), source.getAnalogTriggerTypeForRouting());
        CounterJNI.setCounterUpSourceEdge(this.m_handle, true, false);
        int intIndex = index.getInt();
        HAL.report(11, intIndex + 1);
        SendableRegistry.addLW((Sendable)this, "Tachometer", intIndex);
    }

    @Override
    public void close() throws Exception {
        SendableRegistry.remove(this);
        CounterJNI.freeCounter(this.m_handle);
        CounterJNI.suppressUnused(this.m_source);
    }

    public double getPeriod() {
        return CounterJNI.getCounterPeriod(this.m_handle);
    }

    public double getFrequency() {
        double period = this.getPeriod();
        if (period == 0.0) {
            return 0.0;
        }
        return period;
    }

    public int getEdgesPerRevolution() {
        return this.m_edgesPerRevolution;
    }

    public void setEdgesPerRevolution(int edgesPerRevolution) {
        this.m_edgesPerRevolution = edgesPerRevolution;
    }

    public double getRevolutionsPerMinute() {
        double period = this.getPeriod();
        if (period == 0.0) {
            return 0.0;
        }
        int edgesPerRevolution = this.getEdgesPerRevolution();
        if (edgesPerRevolution == 0) {
            return 0.0;
        }
        return 1.0 / (double)edgesPerRevolution / period * 60.0;
    }

    public boolean getStopped() {
        return CounterJNI.getCounterStopped(this.m_handle);
    }

    public int getSamplesToAverage() {
        return CounterJNI.getCounterSamplesToAverage(this.m_handle);
    }

    public void setSamplesToAverage(int samplesToAverage) {
        CounterJNI.setCounterSamplesToAverage(this.m_handle, samplesToAverage);
    }

    public void setMaxPeriod(double maxPeriod) {
        CounterJNI.setCounterMaxPeriod(this.m_handle, maxPeriod);
    }

    public void setUpdateWhenEmpty(boolean updateWhenEmpty) {
        CounterJNI.setCounterUpdateWhenEmpty(this.m_handle, updateWhenEmpty);
    }

    @Override
    public void initSendable(SendableBuilder builder) {
        builder.setSmartDashboardType("Tachometer");
        builder.addDoubleProperty("RPM", this::getRevolutionsPerMinute, null);
    }
}

