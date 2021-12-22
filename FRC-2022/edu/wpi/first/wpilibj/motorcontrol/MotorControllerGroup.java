/*
 * Decompiled with CFR 0.150.
 */
package edu.wpi.first.wpilibj.motorcontrol;

import edu.wpi.first.util.sendable.Sendable;
import edu.wpi.first.util.sendable.SendableBuilder;
import edu.wpi.first.util.sendable.SendableRegistry;
import edu.wpi.first.wpilibj.motorcontrol.MotorController;
import java.util.Arrays;

public class MotorControllerGroup
implements MotorController,
Sendable,
AutoCloseable {
    private boolean m_isInverted;
    private final MotorController[] m_motorControllers;
    private static int instances;

    public MotorControllerGroup(MotorController motorController, MotorController ... motorControllers) {
        this.m_motorControllers = new MotorController[motorControllers.length + 1];
        this.m_motorControllers[0] = motorController;
        System.arraycopy(motorControllers, 0, this.m_motorControllers, 1, motorControllers.length);
        this.init();
    }

    public MotorControllerGroup(MotorController[] motorControllers) {
        this.m_motorControllers = Arrays.copyOf(motorControllers, motorControllers.length);
        this.init();
    }

    private void init() {
        for (MotorController controller : this.m_motorControllers) {
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
        for (MotorController motorController : this.m_motorControllers) {
            motorController.set(this.m_isInverted ? -speed : speed);
        }
    }

    @Override
    public double get() {
        if (this.m_motorControllers.length > 0) {
            return this.m_motorControllers[0].get() * (double)(this.m_isInverted ? -1 : 1);
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
        for (MotorController motorController : this.m_motorControllers) {
            motorController.disable();
        }
    }

    @Override
    public void stopMotor() {
        for (MotorController motorController : this.m_motorControllers) {
            motorController.stopMotor();
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

