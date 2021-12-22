/*
 * Decompiled with CFR 0.150.
 */
package edu.wpi.first.wpilibj2.command;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.util.ErrorMessages;
import edu.wpi.first.wpilibj2.command.CommandBase;
import java.util.function.BooleanSupplier;

public class WaitUntilCommand
extends CommandBase {
    private final BooleanSupplier m_condition;

    public WaitUntilCommand(BooleanSupplier condition) {
        this.m_condition = ErrorMessages.requireNonNullParam(condition, "condition", "WaitUntilCommand");
    }

    public WaitUntilCommand(double time) {
        this(() -> Timer.getMatchTime() - time > 0.0);
    }

    @Override
    public boolean isFinished() {
        return this.m_condition.getAsBoolean();
    }

    @Override
    public boolean runsWhenDisabled() {
        return true;
    }
}

