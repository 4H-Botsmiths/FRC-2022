/*
 * Decompiled with CFR 0.150.
 */
package edu.wpi.first.wpilibj;

import edu.wpi.first.hal.HAL;
import edu.wpi.first.hal.RelayJNI;
import edu.wpi.first.hal.util.HalHandleException;
import edu.wpi.first.hal.util.UncleanStatusException;
import edu.wpi.first.util.sendable.Sendable;
import edu.wpi.first.util.sendable.SendableBuilder;
import edu.wpi.first.util.sendable.SendableRegistry;
import edu.wpi.first.wpilibj.MotorSafety;
import edu.wpi.first.wpilibj.SensorUtil;
import edu.wpi.first.wpilibj.util.ErrorMessages;
import java.util.Arrays;
import java.util.Optional;

public class Relay
extends MotorSafety
implements Sendable,
AutoCloseable {
    private final int m_channel;
    private int m_forwardHandle;
    private int m_reverseHandle;
    private Direction m_direction;

    private void initRelay() {
        SensorUtil.checkRelayChannel(this.m_channel);
        int portHandle = HAL.getPort((byte)this.m_channel);
        if (this.m_direction == Direction.kBoth || this.m_direction == Direction.kForward) {
            this.m_forwardHandle = RelayJNI.initializeRelayPort(portHandle, true);
            HAL.report(30, this.m_channel + 1);
        }
        if (this.m_direction == Direction.kBoth || this.m_direction == Direction.kReverse) {
            this.m_reverseHandle = RelayJNI.initializeRelayPort(portHandle, false);
            HAL.report(30, this.m_channel + 128);
        }
        this.setSafetyEnabled(false);
        SendableRegistry.addLW((Sendable)this, "Relay", this.m_channel);
    }

    public Relay(int channel, Direction direction) {
        this.m_channel = channel;
        this.m_direction = ErrorMessages.requireNonNullParam(direction, "direction", "Relay");
        this.initRelay();
        this.set(Value.kOff);
    }

    public Relay(int channel) {
        this(channel, Direction.kBoth);
    }

    @Override
    public void close() {
        SendableRegistry.remove(this);
        this.freeRelay();
    }

    private void freeRelay() {
        try {
            RelayJNI.setRelay(this.m_forwardHandle, false);
        }
        catch (HalHandleException | UncleanStatusException runtimeException) {
            // empty catch block
        }
        try {
            RelayJNI.setRelay(this.m_reverseHandle, false);
        }
        catch (HalHandleException | UncleanStatusException runtimeException) {
            // empty catch block
        }
        RelayJNI.freeRelayPort(this.m_forwardHandle);
        RelayJNI.freeRelayPort(this.m_reverseHandle);
        this.m_forwardHandle = 0;
        this.m_reverseHandle = 0;
    }

    public void set(Value value) {
        switch (value) {
            case kOff: {
                if (this.m_direction == Direction.kBoth || this.m_direction == Direction.kForward) {
                    RelayJNI.setRelay(this.m_forwardHandle, false);
                }
                if (this.m_direction != Direction.kBoth && this.m_direction != Direction.kReverse) break;
                RelayJNI.setRelay(this.m_reverseHandle, false);
                break;
            }
            case kOn: {
                if (this.m_direction == Direction.kBoth || this.m_direction == Direction.kForward) {
                    RelayJNI.setRelay(this.m_forwardHandle, true);
                }
                if (this.m_direction != Direction.kBoth && this.m_direction != Direction.kReverse) break;
                RelayJNI.setRelay(this.m_reverseHandle, true);
                break;
            }
            case kForward: {
                if (this.m_direction == Direction.kReverse) {
                    throw new InvalidValueException("A relay configured for reverse cannot be set to forward");
                }
                if (this.m_direction == Direction.kBoth || this.m_direction == Direction.kForward) {
                    RelayJNI.setRelay(this.m_forwardHandle, true);
                }
                if (this.m_direction != Direction.kBoth) break;
                RelayJNI.setRelay(this.m_reverseHandle, false);
                break;
            }
            case kReverse: {
                if (this.m_direction == Direction.kForward) {
                    throw new InvalidValueException("A relay configured for forward cannot be set to reverse");
                }
                if (this.m_direction == Direction.kBoth) {
                    RelayJNI.setRelay(this.m_forwardHandle, false);
                }
                if (this.m_direction != Direction.kBoth && this.m_direction != Direction.kReverse) break;
                RelayJNI.setRelay(this.m_reverseHandle, true);
                break;
            }
        }
    }

    public Value get() {
        if (this.m_direction == Direction.kForward) {
            if (RelayJNI.getRelay(this.m_forwardHandle)) {
                return Value.kOn;
            }
            return Value.kOff;
        }
        if (this.m_direction == Direction.kReverse) {
            if (RelayJNI.getRelay(this.m_reverseHandle)) {
                return Value.kOn;
            }
            return Value.kOff;
        }
        if (RelayJNI.getRelay(this.m_forwardHandle)) {
            if (RelayJNI.getRelay(this.m_reverseHandle)) {
                return Value.kOn;
            }
            return Value.kForward;
        }
        if (RelayJNI.getRelay(this.m_reverseHandle)) {
            return Value.kReverse;
        }
        return Value.kOff;
    }

    public int getChannel() {
        return this.m_channel;
    }

    @Override
    public void stopMotor() {
        this.set(Value.kOff);
    }

    @Override
    public String getDescription() {
        return "Relay ID " + this.getChannel();
    }

    public void setDirection(Direction direction) {
        ErrorMessages.requireNonNullParam(direction, "direction", "setDirection");
        if (this.m_direction == direction) {
            return;
        }
        this.freeRelay();
        this.m_direction = direction;
        this.initRelay();
    }

    @Override
    public void initSendable(SendableBuilder builder) {
        builder.setSmartDashboardType("Relay");
        builder.setActuator(true);
        builder.setSafeState(() -> this.set(Value.kOff));
        builder.addStringProperty("Value", () -> this.get().getPrettyValue(), value -> this.set(Value.getValueOf(value).orElse(Value.kOff)));
    }

    public static enum Direction {
        kBoth,
        kForward,
        kReverse;

    }

    public static enum Value {
        kOff("Off"),
        kOn("On"),
        kForward("Forward"),
        kReverse("Reverse");

        private final String m_prettyValue;

        private Value(String prettyValue) {
            this.m_prettyValue = prettyValue;
        }

        public String getPrettyValue() {
            return this.m_prettyValue;
        }

        public static Optional<Value> getValueOf(String value) {
            return Arrays.stream(Value.values()).filter(v -> v.m_prettyValue.equals(value)).findFirst();
        }
    }

    public static class InvalidValueException
    extends RuntimeException {
        public InvalidValueException(String message) {
            super(message);
        }
    }
}

