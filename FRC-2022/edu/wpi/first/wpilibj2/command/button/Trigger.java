/*
 * Decompiled with CFR 0.150.
 */
package edu.wpi.first.wpilibj2.command.button;

import edu.wpi.first.wpilibj.Debouncer;
import edu.wpi.first.wpilibj.util.ErrorMessages;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.CommandScheduler;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.Subsystem;
import java.util.function.BooleanSupplier;

public class Trigger {
    private final BooleanSupplier m_isActive;

    public Trigger(BooleanSupplier isActive) {
        this.m_isActive = isActive;
    }

    public Trigger() {
        this.m_isActive = () -> false;
    }

    public boolean get() {
        return this.m_isActive.getAsBoolean();
    }

    public Trigger whenActive(final Command command, final boolean interruptible) {
        ErrorMessages.requireNonNullParam(command, "command", "whenActive");
        CommandScheduler.getInstance().addButton(new Runnable(){
            private boolean m_pressedLast;
            {
                this.m_pressedLast = Trigger.this.get();
            }

            @Override
            public void run() {
                boolean pressed = Trigger.this.get();
                if (!this.m_pressedLast && pressed) {
                    command.schedule(interruptible);
                }
                this.m_pressedLast = pressed;
            }
        });
        return this;
    }

    public Trigger whenActive(Command command) {
        return this.whenActive(command, true);
    }

    public Trigger whenActive(Runnable toRun, Subsystem ... requirements) {
        return this.whenActive(new InstantCommand(toRun, requirements));
    }

    public Trigger whileActiveContinuous(final Command command, final boolean interruptible) {
        ErrorMessages.requireNonNullParam(command, "command", "whileActiveContinuous");
        CommandScheduler.getInstance().addButton(new Runnable(){
            private boolean m_pressedLast;
            {
                this.m_pressedLast = Trigger.this.get();
            }

            @Override
            public void run() {
                boolean pressed = Trigger.this.get();
                if (pressed) {
                    command.schedule(interruptible);
                } else if (this.m_pressedLast) {
                    command.cancel();
                }
                this.m_pressedLast = pressed;
            }
        });
        return this;
    }

    public Trigger whileActiveContinuous(Command command) {
        return this.whileActiveContinuous(command, true);
    }

    public Trigger whileActiveContinuous(Runnable toRun, Subsystem ... requirements) {
        return this.whileActiveContinuous(new InstantCommand(toRun, requirements));
    }

    public Trigger whileActiveOnce(final Command command, final boolean interruptible) {
        ErrorMessages.requireNonNullParam(command, "command", "whileActiveOnce");
        CommandScheduler.getInstance().addButton(new Runnable(){
            private boolean m_pressedLast;
            {
                this.m_pressedLast = Trigger.this.get();
            }

            @Override
            public void run() {
                boolean pressed = Trigger.this.get();
                if (!this.m_pressedLast && pressed) {
                    command.schedule(interruptible);
                } else if (this.m_pressedLast && !pressed) {
                    command.cancel();
                }
                this.m_pressedLast = pressed;
            }
        });
        return this;
    }

    public Trigger whileActiveOnce(Command command) {
        return this.whileActiveOnce(command, true);
    }

    public Trigger whenInactive(final Command command, final boolean interruptible) {
        ErrorMessages.requireNonNullParam(command, "command", "whenInactive");
        CommandScheduler.getInstance().addButton(new Runnable(){
            private boolean m_pressedLast;
            {
                this.m_pressedLast = Trigger.this.get();
            }

            @Override
            public void run() {
                boolean pressed = Trigger.this.get();
                if (this.m_pressedLast && !pressed) {
                    command.schedule(interruptible);
                }
                this.m_pressedLast = pressed;
            }
        });
        return this;
    }

    public Trigger whenInactive(Command command) {
        return this.whenInactive(command, true);
    }

    public Trigger whenInactive(Runnable toRun, Subsystem ... requirements) {
        return this.whenInactive(new InstantCommand(toRun, requirements));
    }

    public Trigger toggleWhenActive(final Command command, final boolean interruptible) {
        ErrorMessages.requireNonNullParam(command, "command", "toggleWhenActive");
        CommandScheduler.getInstance().addButton(new Runnable(){
            private boolean m_pressedLast;
            {
                this.m_pressedLast = Trigger.this.get();
            }

            @Override
            public void run() {
                boolean pressed = Trigger.this.get();
                if (!this.m_pressedLast && pressed) {
                    if (command.isScheduled()) {
                        command.cancel();
                    } else {
                        command.schedule(interruptible);
                    }
                }
                this.m_pressedLast = pressed;
            }
        });
        return this;
    }

    public Trigger toggleWhenActive(Command command) {
        return this.toggleWhenActive(command, true);
    }

    public Trigger cancelWhenActive(final Command command) {
        ErrorMessages.requireNonNullParam(command, "command", "cancelWhenActive");
        CommandScheduler.getInstance().addButton(new Runnable(){
            private boolean m_pressedLast;
            {
                this.m_pressedLast = Trigger.this.get();
            }

            @Override
            public void run() {
                boolean pressed = Trigger.this.get();
                if (!this.m_pressedLast && pressed) {
                    command.cancel();
                }
                this.m_pressedLast = pressed;
            }
        });
        return this;
    }

    public Trigger and(Trigger trigger) {
        return new Trigger(() -> this.get() && trigger.get());
    }

    public Trigger or(Trigger trigger) {
        return new Trigger(() -> this.get() || trigger.get());
    }

    public Trigger negate() {
        return new Trigger(() -> !this.get());
    }

    public Trigger debounce(final double seconds) {
        return new Trigger(new BooleanSupplier(){
            Debouncer m_debouncer;
            {
                this.m_debouncer = new Debouncer(seconds);
            }

            @Override
            public boolean getAsBoolean() {
                return this.m_debouncer.calculate(Trigger.this.get());
            }
        });
    }
}

