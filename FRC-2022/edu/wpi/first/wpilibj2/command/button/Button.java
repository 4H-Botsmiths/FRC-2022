/*
 * Decompiled with CFR 0.150.
 */
package edu.wpi.first.wpilibj2.command.button;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Subsystem;
import edu.wpi.first.wpilibj2.command.button.Trigger;
import java.util.function.BooleanSupplier;

public class Button
extends Trigger {
    public Button() {
    }

    public Button(BooleanSupplier isPressed) {
        super(isPressed);
    }

    public Button whenPressed(Command command, boolean interruptible) {
        this.whenActive(command, interruptible);
        return this;
    }

    public Button whenPressed(Command command) {
        this.whenActive(command);
        return this;
    }

    public Button whenPressed(Runnable toRun, Subsystem ... requirements) {
        this.whenActive(toRun, requirements);
        return this;
    }

    public Button whileHeld(Command command, boolean interruptible) {
        this.whileActiveContinuous(command, interruptible);
        return this;
    }

    public Button whileHeld(Command command) {
        this.whileActiveContinuous(command);
        return this;
    }

    public Button whileHeld(Runnable toRun, Subsystem ... requirements) {
        this.whileActiveContinuous(toRun, requirements);
        return this;
    }

    public Button whenHeld(Command command, boolean interruptible) {
        this.whileActiveOnce(command, interruptible);
        return this;
    }

    public Button whenHeld(Command command) {
        this.whileActiveOnce(command, true);
        return this;
    }

    public Button whenReleased(Command command, boolean interruptible) {
        this.whenInactive(command, interruptible);
        return this;
    }

    public Button whenReleased(Command command) {
        this.whenInactive(command);
        return this;
    }

    public Button whenReleased(Runnable toRun, Subsystem ... requirements) {
        this.whenInactive(toRun, requirements);
        return this;
    }

    public Button toggleWhenPressed(Command command, boolean interruptible) {
        this.toggleWhenActive(command, interruptible);
        return this;
    }

    public Button toggleWhenPressed(Command command) {
        this.toggleWhenActive(command);
        return this;
    }

    public Button cancelWhenPressed(Command command) {
        this.cancelWhenActive(command);
        return this;
    }
}

