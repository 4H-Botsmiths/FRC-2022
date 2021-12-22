/*
 * Decompiled with CFR 0.150.
 */
package edu.wpi.first.wpilibj;

import edu.wpi.first.hal.EncoderJNI;
import edu.wpi.first.hal.HAL;
import edu.wpi.first.hal.SimDevice;
import edu.wpi.first.hal.util.AllocationException;
import edu.wpi.first.util.sendable.Sendable;
import edu.wpi.first.util.sendable.SendableBuilder;
import edu.wpi.first.util.sendable.SendableRegistry;
import edu.wpi.first.wpilibj.CounterBase;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.DigitalSource;
import edu.wpi.first.wpilibj.util.ErrorMessages;

public class Encoder
implements CounterBase,
Sendable,
AutoCloseable {
    protected DigitalSource m_aSource;
    protected DigitalSource m_bSource;
    protected DigitalSource m_indexSource;
    private boolean m_allocatedA;
    private boolean m_allocatedB;
    private boolean m_allocatedI;
    private final CounterBase.EncodingType m_encodingType;
    int m_encoder;

    private void initEncoder(boolean reverseDirection, CounterBase.EncodingType type) {
        this.m_encoder = EncoderJNI.initializeEncoder(this.m_aSource.getPortHandleForRouting(), this.m_aSource.getAnalogTriggerTypeForRouting(), this.m_bSource.getPortHandleForRouting(), this.m_bSource.getAnalogTriggerTypeForRouting(), reverseDirection, type.value);
        int fpgaIndex = this.getFPGAIndex();
        HAL.report(18, fpgaIndex + 1, type.value + 1);
        SendableRegistry.addLW((Sendable)this, "Encoder", fpgaIndex);
    }

    public Encoder(int channelA, int channelB, boolean reverseDirection) {
        this(channelA, channelB, reverseDirection, CounterBase.EncodingType.k4X);
    }

    public Encoder(int channelA, int channelB) {
        this(channelA, channelB, false);
    }

    public Encoder(int channelA, int channelB, boolean reverseDirection, CounterBase.EncodingType encodingType) {
        ErrorMessages.requireNonNullParam(encodingType, "encodingType", "Encoder");
        this.m_allocatedA = true;
        this.m_allocatedB = true;
        this.m_allocatedI = false;
        this.m_aSource = new DigitalInput(channelA);
        this.m_bSource = new DigitalInput(channelB);
        this.m_encodingType = encodingType;
        SendableRegistry.addChild(this, this.m_aSource);
        SendableRegistry.addChild(this, this.m_bSource);
        this.initEncoder(reverseDirection, encodingType);
    }

    public Encoder(int channelA, int channelB, int indexChannel, boolean reverseDirection) {
        this(channelA, channelB, reverseDirection);
        this.m_allocatedI = true;
        this.m_indexSource = new DigitalInput(indexChannel);
        SendableRegistry.addChild(this, this.m_indexSource);
        this.setIndexSource(this.m_indexSource);
    }

    public Encoder(int channelA, int channelB, int indexChannel) {
        this(channelA, channelB, indexChannel, false);
    }

    public Encoder(DigitalSource sourceA, DigitalSource sourceB, boolean reverseDirection) {
        this(sourceA, sourceB, reverseDirection, CounterBase.EncodingType.k4X);
    }

    public Encoder(DigitalSource sourceA, DigitalSource sourceB) {
        this(sourceA, sourceB, false);
    }

    public Encoder(DigitalSource sourceA, DigitalSource sourceB, boolean reverseDirection, CounterBase.EncodingType encodingType) {
        ErrorMessages.requireNonNullParam(sourceA, "sourceA", "Encoder");
        ErrorMessages.requireNonNullParam(sourceB, "sourceB", "Encoder");
        ErrorMessages.requireNonNullParam(encodingType, "encodingType", "Encoder");
        this.m_allocatedA = false;
        this.m_allocatedB = false;
        this.m_allocatedI = false;
        this.m_encodingType = encodingType;
        this.m_aSource = sourceA;
        this.m_bSource = sourceB;
        this.initEncoder(reverseDirection, encodingType);
    }

    public Encoder(DigitalSource sourceA, DigitalSource sourceB, DigitalSource indexSource, boolean reverseDirection) {
        this(sourceA, sourceB, reverseDirection);
        this.m_allocatedI = false;
        this.m_indexSource = indexSource;
        this.setIndexSource(indexSource);
    }

    public Encoder(DigitalSource sourceA, DigitalSource sourceB, DigitalSource indexSource) {
        this(sourceA, sourceB, indexSource, false);
    }

    public int getFPGAIndex() {
        return EncoderJNI.getEncoderFPGAIndex(this.m_encoder);
    }

    public int getEncodingScale() {
        return EncoderJNI.getEncoderEncodingScale(this.m_encoder);
    }

    @Override
    public void close() {
        SendableRegistry.remove(this);
        if (this.m_aSource != null && this.m_allocatedA) {
            this.m_aSource.close();
            this.m_allocatedA = false;
        }
        if (this.m_bSource != null && this.m_allocatedB) {
            this.m_bSource.close();
            this.m_allocatedB = false;
        }
        if (this.m_indexSource != null && this.m_allocatedI) {
            this.m_indexSource.close();
            this.m_allocatedI = false;
        }
        this.m_aSource = null;
        this.m_bSource = null;
        this.m_indexSource = null;
        EncoderJNI.freeEncoder(this.m_encoder);
        this.m_encoder = 0;
    }

    public int getRaw() {
        return EncoderJNI.getEncoderRaw(this.m_encoder);
    }

    @Override
    public int get() {
        return EncoderJNI.getEncoder(this.m_encoder);
    }

    @Override
    public void reset() {
        EncoderJNI.resetEncoder(this.m_encoder);
    }

    @Override
    @Deprecated
    public double getPeriod() {
        return EncoderJNI.getEncoderPeriod(this.m_encoder);
    }

    @Override
    public void setMaxPeriod(double maxPeriod) {
        EncoderJNI.setEncoderMaxPeriod(this.m_encoder, maxPeriod);
    }

    @Override
    public boolean getStopped() {
        return EncoderJNI.getEncoderStopped(this.m_encoder);
    }

    @Override
    public boolean getDirection() {
        return EncoderJNI.getEncoderDirection(this.m_encoder);
    }

    public double getDistance() {
        return EncoderJNI.getEncoderDistance(this.m_encoder);
    }

    public double getRate() {
        return EncoderJNI.getEncoderRate(this.m_encoder);
    }

    public void setMinRate(double minRate) {
        EncoderJNI.setEncoderMinRate(this.m_encoder, minRate);
    }

    public void setDistancePerPulse(double distancePerPulse) {
        EncoderJNI.setEncoderDistancePerPulse(this.m_encoder, distancePerPulse);
    }

    public double getDistancePerPulse() {
        return EncoderJNI.getEncoderDistancePerPulse(this.m_encoder);
    }

    public void setReverseDirection(boolean reverseDirection) {
        EncoderJNI.setEncoderReverseDirection(this.m_encoder, reverseDirection);
    }

    public void setSamplesToAverage(int samplesToAverage) {
        EncoderJNI.setEncoderSamplesToAverage(this.m_encoder, samplesToAverage);
    }

    public int getSamplesToAverage() {
        return EncoderJNI.getEncoderSamplesToAverage(this.m_encoder);
    }

    public void setIndexSource(int channel) {
        this.setIndexSource(channel, IndexingType.kResetOnRisingEdge);
    }

    public void setIndexSource(DigitalSource source) {
        this.setIndexSource(source, IndexingType.kResetOnRisingEdge);
    }

    public void setIndexSource(int channel, IndexingType type) {
        if (this.m_allocatedI) {
            throw new AllocationException("Digital Input for Indexing already allocated");
        }
        this.m_indexSource = new DigitalInput(channel);
        this.m_allocatedI = true;
        SendableRegistry.addChild(this, this.m_indexSource);
        this.setIndexSource(this.m_indexSource, type);
    }

    public void setIndexSource(DigitalSource source, IndexingType type) {
        EncoderJNI.setEncoderIndexSource(this.m_encoder, source.getPortHandleForRouting(), source.getAnalogTriggerTypeForRouting(), type.value);
    }

    public void setSimDevice(SimDevice device) {
        EncoderJNI.setEncoderSimDevice(this.m_encoder, device.getNativeHandle());
    }

    public double getDecodingScaleFactor() {
        switch (this.m_encodingType) {
            case k1X: {
                return 1.0;
            }
            case k2X: {
                return 0.5;
            }
            case k4X: {
                return 0.25;
            }
        }
        return 0.0;
    }

    @Override
    public void initSendable(SendableBuilder builder) {
        if (EncoderJNI.getEncoderEncodingType(this.m_encoder) == CounterBase.EncodingType.k4X.value) {
            builder.setSmartDashboardType("Quadrature Encoder");
        } else {
            builder.setSmartDashboardType("Encoder");
        }
        builder.addDoubleProperty("Speed", this::getRate, null);
        builder.addDoubleProperty("Distance", this::getDistance, null);
        builder.addDoubleProperty("Distance per Tick", this::getDistancePerPulse, null);
    }

    public static enum IndexingType {
        kResetWhileHigh(0),
        kResetWhileLow(1),
        kResetOnFallingEdge(2),
        kResetOnRisingEdge(3);

        public final int value;

        private IndexingType(int value) {
            this.value = value;
        }
    }
}

