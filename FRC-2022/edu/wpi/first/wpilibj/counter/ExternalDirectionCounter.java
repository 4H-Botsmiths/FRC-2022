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
import edu.wpi.first.wpilibj.util.ErrorMessages;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;

public class ExternalDirectionCounter
implements Sendable,
AutoCloseable {
    private final DigitalSource m_countSource;
    private final DigitalSource m_directionSource;
    private final int m_handle;

    public ExternalDirectionCounter(DigitalSource countSource, DigitalSource directionSource) {
        this.m_countSource = ErrorMessages.requireNonNullParam(countSource, "countSource", "ExternalDirectionCounter");
        this.m_directionSource = ErrorMessages.requireNonNullParam(directionSource, "directionSource", "ExternalDirectionCounter");
        ByteBuffer index = ByteBuffer.allocateDirect(4);
        index.order(ByteOrder.LITTLE_ENDIAN);
        this.m_handle = CounterJNI.initializeCounter(3, index.asIntBuffer());
        CounterJNI.setCounterUpSource(this.m_handle, countSource.getPortHandleForRouting(), countSource.getAnalogTriggerTypeForRouting());
        CounterJNI.setCounterUpSourceEdge(this.m_handle, true, false);
        CounterJNI.setCounterDownSource(this.m_handle, directionSource.getPortHandleForRouting(), directionSource.getAnalogTriggerTypeForRouting());
        CounterJNI.setCounterDownSourceEdge(this.m_handle, false, true);
        CounterJNI.resetCounter(this.m_handle);
        int intIndex = index.getInt();
        HAL.report(11, intIndex + 1);
        SendableRegistry.addLW((Sendable)this, "External Direction Counter", intIndex);
    }

    public int getCount() {
        return CounterJNI.getCounter(this.m_handle);
    }

    public void setReverseDirection(boolean reverseDirection) {
        CounterJNI.setCounterReverseDirection(this.m_handle, reverseDirection);
    }

    public void reset() {
        CounterJNI.resetCounter(this.m_handle);
    }

    public void setEdgeConfiguration(EdgeConfiguration configuration) {
        CounterJNI.setCounterUpSourceEdge(this.m_handle, configuration.rising, configuration.falling);
    }

    @Override
    public void close() throws Exception {
        SendableRegistry.remove(this);
        CounterJNI.freeCounter(this.m_handle);
        CounterJNI.suppressUnused(this.m_countSource);
        CounterJNI.suppressUnused(this.m_directionSource);
    }

    @Override
    public void initSendable(SendableBuilder builder) {
        builder.setSmartDashboardType("External Direction Counter");
        builder.addDoubleProperty("Count", this::getCount, null);
    }
}

