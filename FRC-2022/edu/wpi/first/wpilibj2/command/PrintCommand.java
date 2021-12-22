/*
 * Decompiled with CFR 0.150.
 */
package edu.wpi.first.wpilibj2.command;

import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.Subsystem;

public class PrintCommand
extends InstantCommand {
    public PrintCommand(String message) {
        super(() -> System.out.println(message), new Subsystem[0]);
    }

    @Override
    public boolean runsWhenDisabled() {
        return true;
    }
}

