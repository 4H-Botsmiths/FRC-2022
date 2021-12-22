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
import edu.wpi.first.wpilibj.counter.EdgeConfiguration;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;

public class UpDownCounter
implements Sendable,
AutoCloseable {
    private DigitalSource m_upSource;
    private DigitalSource m_downSource;
    private final int m_handle;

    public UpDownCounter(DigitalSource upSource, DigitalSource downSource) {
        ByteBuffer index = ByteBuffer.allocateDirect(4);
        index.order(ByteOrder.LITTLE_ENDIAN);
        this.m_handle = CounterJNI.initializeCounter(0, index.asIntBuffer());
        if (upSource != null) {
            this.m_upSource = upSource;
            CounterJNI.setCounterUpSource(this.m_handle, upSource.getPortHandleForRouting(), upSource.getAnalogTriggerTypeForRouting());
            CounterJNI.setCounterUpSourceEdge(this.m_handle, true, false);
        }
        if (downSource != null) {
            this.m_downSource = downSource;
            CounterJNI.setCounterDownSource(this.m_handle, downSource.getPortHandleForRouting(), downSource.getAnalogTriggerTypeForRouting());
            CounterJNI.setCounterDownSourceEdge(this.m_handle, true, false);
        }
        this.reset();
        int intIndex = index.getInt();
        HAL.report(11, intIndex + 1);
        SendableRegistry.addLW((Sendable)this, "UpDown Counter", intIndex);
    }

    @Override
    public void close() throws Exception {
        SendableRegistry.remove(this);
        CounterJNI.freeCounter(this.m_handle);
        CounterJNI.suppressUnused(this.m_upSource);
        CounterJNI.suppressUnused(this.m_downSource);
    }

    public void setUpEdgeConfiguration(EdgeConfiguration configuration) {
        CounterJNI.setCounterUpSourceEdge(this.m_handle, configuration.rising, configuration.falling);
    }

    public void setDownEdgeConfiguration(EdgeConfiguration configuration) {
        CounterJNI.setCounterDownSourceEdge(this.m_handle, configuration.rising, configuration.falling);
    }

    public void reset() {
        CounterJNI.resetCounter(this.m_handle);
    }

    public void setReverseDirection(boolean reverseDirection) {
        CounterJNI.setCounterReverseDirection(this.m_handle, reverseDirection);
    }

    public int getCount() {
        return CounterJNI.getCounter(this.m_handle);
    }

    @Override
    public void initSendable(SendableBuilder builder) {
        builder.setSmartDashboardType("UpDown Counter");
        builder.addDoubleProperty("Count", this::getCount, null);
    }
}

