/*
 * Decompiled with CFR 0.150.
 */
package edu.wpi.first.wpilibj;

import edu.wpi.first.hal.HAL;
import edu.wpi.first.hal.util.AllocationException;
import edu.wpi.first.util.sendable.Sendable;
import edu.wpi.first.util.sendable.SendableBuilder;
import edu.wpi.first.util.sendable.SendableRegistry;
import edu.wpi.first.wpilibj.PneumaticsBase;
import edu.wpi.first.wpilibj.PneumaticsModuleType;

public class DoubleSolenoid
implements Sendable,
AutoCloseable {
    private final int m_forwardMask;
    private final int m_reverseMask;
    private final int m_mask;
    private PneumaticsBase m_module;
    private final int m_forwardChannel;
    private final int m_reverseChannel;

    public DoubleSolenoid(PneumaticsModuleType moduleType, int forwardChannel, int reverseChannel) {
        this(PneumaticsBase.getDefaultForType(moduleType), moduleType, forwardChannel, reverseChannel);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public DoubleSolenoid(int module, PneumaticsModuleType moduleType, int forwardChannel, int reverseChannel) {
        this.m_module = PneumaticsBase.getForType(module, moduleType);
        boolean allocatedSolenoids = false;
        boolean successfulCompletion = false;
        this.m_forwardChannel = forwardChannel;
        this.m_reverseChannel = reverseChannel;
        this.m_forwardMask = 1 << forwardChannel;
        this.m_reverseMask = 1 << reverseChannel;
        this.m_mask = this.m_forwardMask | this.m_reverseMask;
        try {
            if (!this.m_module.checkSolenoidChannel(forwardChannel)) {
                throw new IllegalArgumentException("Channel " + forwardChannel + " out of range");
            }
            if (!this.m_module.checkSolenoidChannel(reverseChannel)) {
                throw new IllegalArgumentException("Channel " + reverseChannel + " out of range");
            }
            int allocMask = this.m_module.checkAndReserveSolenoids(this.m_mask);
            if (allocMask != 0) {
                if (allocMask == this.m_mask) {
                    throw new AllocationException("Channels " + forwardChannel + " and " + reverseChannel + " already allocated");
                }
                if (allocMask == this.m_forwardMask) {
                    throw new AllocationException("Channel " + forwardChannel + " already allocated");
                }
                throw new AllocationException("Channel " + reverseChannel + " already allocated");
            }
            allocatedSolenoids = true;
            HAL.report(34, forwardChannel + 1, this.m_module.getModuleNumber() + 1);
            HAL.report(34, reverseChannel + 1, this.m_module.getModuleNumber() + 1);
            SendableRegistry.addLW(this, "DoubleSolenoid", this.m_module.getModuleNumber(), forwardChannel);
            successfulCompletion = true;
        }
        finally {
            if (!successfulCompletion) {
                if (allocatedSolenoids) {
                    this.m_module.unreserveSolenoids(this.m_mask);
                }
                this.m_module.close();
            }
        }
    }

    @Override
    public synchronized void close() {
        SendableRegistry.remove(this);
        this.m_module.unreserveSolenoids(this.m_mask);
        this.m_module.close();
        this.m_module = null;
    }

    public void set(Value value) {
        int setValue;
        switch (value) {
            case kOff: {
                setValue = 0;
                break;
            }
            case kForward: {
                setValue = this.m_forwardMask;
                break;
            }
            case kReverse: {
                setValue = this.m_reverseMask;
                break;
            }
            default: {
                throw new AssertionError((Object)("Illegal value: " + value));
            }
        }
        this.m_module.setSolenoids(this.m_mask, setValue);
    }

    public Value get() {
        int values = this.m_module.getSolenoids();
        if ((values & this.m_forwardMask) != 0) {
            return Value.kForward;
        }
        if ((values & this.m_reverseMask) != 0) {
            return Value.kReverse;
        }
        return Value.kOff;
    }

    public void toggle() {
        Value value = this.get();
        if (value == Value.kForward) {
            this.set(Value.kReverse);
        } else if (value == Value.kReverse) {
            this.set(Value.kForward);
        }
    }

    public int getFwdChannel() {
        return this.m_forwardChannel;
    }

    public int getRevChannel() {
        return this.m_reverseChannel;
    }

    public boolean isFwdSolenoidDisabled() {
        return (this.m_module.getSolenoidDisabledList() & this.m_forwardMask) != 0;
    }

    public boolean isRevSolenoidDisabled() {
        return (this.m_module.getSolenoidDisabledList() & this.m_reverseMask) != 0;
    }

    @Override
    public void initSendable(SendableBuilder builder) {
        builder.setSmartDashboardType("Double Solenoid");
        builder.setActuator(true);
        builder.setSafeState(() -> this.set(Value.kOff));
        builder.addStringProperty("Value", () -> this.get().name().substring(1), value -> {
            if ("Forward".equals(value)) {
                this.set(Value.kForward);
            } else if ("Reverse".equals(value)) {
                this.set(Value.kReverse);
            } else {
                this.set(Value.kOff);
            }
        });
    }

    public static enum Value {
        kOff,
        kForward,
        kReverse;

    }
}

