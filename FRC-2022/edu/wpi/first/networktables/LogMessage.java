/*
 * Decompiled with CFR 0.150.
 */
package edu.wpi.first.networktables;

import edu.wpi.first.networktables.NetworkTableInstance;

public final class LogMessage {
    public static final int kCritical = 50;
    public static final int kError = 40;
    public static final int kWarning = 30;
    public static final int kInfo = 20;
    public static final int kDebug = 10;
    public static final int kDebug1 = 9;
    public static final int kDebug2 = 8;
    public static final int kDebug3 = 7;
    public static final int kDebug4 = 6;
    public final int logger;
    public final int level;
    public final String filename;
    public final int line;
    public final String message;
    private final NetworkTableInstance m_inst;

    public LogMessage(NetworkTableInstance inst, int logger, int level, String filename, int line, String message) {
        this.m_inst = inst;
        this.logger = logger;
        this.level = level;
        this.filename = filename;
        this.line = line;
        this.message = message;
    }

    NetworkTableInstance getInstance() {
        return this.m_inst;
    }
}

