/*
 * Decompiled with CFR 0.150.
 */
package edu.wpi.first.wpilibj;

import edu.wpi.first.hal.DigitalGlitchFilterJNI;
import edu.wpi.first.hal.HAL;
import edu.wpi.first.util.sendable.Sendable;
import edu.wpi.first.util.sendable.SendableBuilder;
import edu.wpi.first.util.sendable.SendableRegistry;
import edu.wpi.first.wpilibj.Counter;
import edu.wpi.first.wpilibj.DigitalSource;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.SensorUtil;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class DigitalGlitchFilter
implements Sendable,
AutoCloseable {
    private int m_channelIndex = -1;
    private static final Lock m_mutex = new ReentrantLock(true);
    private static final boolean[] m_filterAllocated = new boolean[3];

    public DigitalGlitchFilter() {
        m_mutex.lock();
        try {
            int index;
            for (index = 0; m_filterAllocated[index] && index < m_filterAllocated.length; ++index) {
            }
            if (index != m_filterAllocated.length) {
                this.m_channelIndex = index;
                DigitalGlitchFilter.m_filterAllocated[index] = true;
                HAL.report(57, this.m_channelIndex + 1, 0);
                SendableRegistry.addLW((Sendable)this, "DigitalGlitchFilter", index);
            }
        }
        finally {
            m_mutex.unlock();
        }
    }

    @Override
    public void close() {
        SendableRegistry.remove(this);
        if (this.m_channelIndex >= 0) {
            m_mutex.lock();
            try {
                DigitalGlitchFilter.m_filterAllocated[this.m_channelIndex] = false;
            }
            finally {
                m_mutex.unlock();
            }
            this.m_channelIndex = -1;
        }
    }

    private static void setFilter(DigitalSource input, int channelIndex) {
        if (input != null) {
            if (input.isAnalogTrigger()) {
                throw new IllegalStateException("Analog Triggers not supported for DigitalGlitchFilters");
            }
            DigitalGlitchFilterJNI.setFilterSelect(input.getPortHandleForRouting(), channelIndex);
            int selected = DigitalGlitchFilterJNI.getFilterSelect(input.getPortHandleForRouting());
            if (selected != channelIndex) {
                throw new IllegalStateException("DigitalGlitchFilterJNI.setFilterSelect(" + channelIndex + ") failed -> " + selected);
            }
        }
    }

    public void add(DigitalSource input) {
        DigitalGlitchFilter.setFilter(input, this.m_channelIndex + 1);
    }

    public void add(Encoder input) {
        this.add(input.m_aSource);
        this.add(input.m_bSource);
    }

    public void add(Counter input) {
        this.add(input.m_upSource);
        this.add(input.m_downSource);
    }

    public void remove(DigitalSource input) {
        DigitalGlitchFilter.setFilter(input, 0);
    }

    public void remove(Encoder input) {
        this.remove(input.m_aSource);
        this.remove(input.m_bSource);
    }

    public void remove(Counter input) {
        this.remove(input.m_upSource);
        this.remove(input.m_downSource);
    }

    public void setPeriodCycles(int fpgaCycles) {
        DigitalGlitchFilterJNI.setFilterPeriod(this.m_channelIndex, fpgaCycles);
    }

    public void setPeriodNanoSeconds(long nanoseconds) {
        int fpgaCycles = (int)(nanoseconds * (long)SensorUtil.kSystemClockTicksPerMicrosecond / 4L / 1000L);
        this.setPeriodCycles(fpgaCycles);
    }

    public int getPeriodCycles() {
        return DigitalGlitchFilterJNI.getFilterPeriod(this.m_channelIndex);
    }

    public long getPeriodNanoSeconds() {
        int fpgaCycles = this.getPeriodCycles();
        return (long)fpgaCycles * 1000L / (long)(SensorUtil.kSystemClockTicksPerMicrosecond / 4);
    }

    @Override
    public void initSendable(SendableBuilder builder) {
    }
}

