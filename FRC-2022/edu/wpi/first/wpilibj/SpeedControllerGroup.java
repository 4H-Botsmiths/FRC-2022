/*
 * Decompiled with CFR 0.150.
 */
package edu.wpi.first.wpilibj;

import edu.wpi.first.util.sendable.Sendable;
import edu.wpi.first.util.sendable.SendableBuilder;
import edu.wpi.first.util.sendable.SendableRegistry;
import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.motorcontrol.MotorController;
import java.util.Arrays;

@Deprecated(since="2022", forRemoval=true)
public class SpeedControllerGroup
implements MotorController,
Sendable,
AutoCloseable {
    private boolean m_isInverted;
    private final SpeedController[] m_speedControllers;
    private static int instances;

    public SpeedControllerGroup(SpeedController speedController, SpeedController ... speedControllers) {
        this.m_speedControllers = new SpeedController[speedControllers.length + 1];
        this.m_speedControllers[0] = speedController;
        System.arraycopy(speedControllers, 0, this.m_speedControllers, 1, speedControllers.length);
        this.init();
    }

    public SpeedControllerGroup(SpeedController[] speedControllers) {
        this.m_speedControllers = Arrays.copyOf(speedControllers, speedControllers.length);
        this.init();
    }

    private void init() {
        for (SpeedController controller : this.m_speedControllers) {
            SendableRegistry.addChild(this, controller);
        }
        SendableRegistry.addLW((Sendable)this, "MotorControllerGroup", ++instances);
    }

    @Override
    public void close() {
        SendableRegistry.remove(this);
    }

    @Override
    public void set(double speed) {
        for (SpeedController speedController : this.m_speedControllers) {
            speedController.set(this.m_isInverted ? -speed : speed);
        }
    }

    @Override
    public double get() {
        if (this.m_speedControllers.length > 0) {
            return this.m_speedControllers[0].get() * (double)(this.m_isInverted ? -1 : 1);
        }
        return 0.0;
    }

    @Override
    public void setInverted(boolean isInverted) {
        this.m_isInverted = isInverted;
    }

    @Override
    public boolean getInverted() {
        return this.m_isInverted;
    }

    @Override
    public void disable() {
        for (SpeedController speedController : this.m_speedControllers) {
            speedController.disable();
        }
    }

    @Override
    public void stopMotor() {
        for (SpeedController speedController : this.m_speedControllers) {
            speedController.stopMotor();
        }
    }

    @Override
    public void initSendable(SendableBuilder builder) {
        builder.setSmartDashboardType("Motor Controller");
        builder.setActuator(true);
        builder.setSafeState(this::stopMotor);
        builder.addDoubleProperty("Value", this::get, this::set);
    }
}

