/*
 * Decompiled with CFR 0.150.
 */
package edu.wpi.first.wpilibj;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.RobotController;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

public class Tracer {
    private static final long kMinPrintPeriod = 1000000L;
    private long m_lastEpochsPrintTime;
    private long m_startTime;
    private final Map<String, Long> m_epochs = new HashMap<String, Long>();

    public Tracer() {
        this.resetTimer();
    }

    public void clearEpochs() {
        this.m_epochs.clear();
        this.resetTimer();
    }

    public void resetTimer() {
        this.m_startTime = RobotController.getFPGATime();
    }

    public void addEpoch(String epochName) {
        long currentTime = RobotController.getFPGATime();
        this.m_epochs.put(epochName, currentTime - this.m_startTime);
        this.m_startTime = currentTime;
    }

    public void printEpochs() {
        this.printEpochs(out -> DriverStation.reportWarning(out, false));
    }

    public void printEpochs(Consumer<String> output) {
        long now = RobotController.getFPGATime();
        if (now - this.m_lastEpochsPrintTime > 1000000L) {
            StringBuilder sb = new StringBuilder();
            this.m_lastEpochsPrintTime = now;
            this.m_epochs.forEach((key, value) -> sb.append(String.format("\t%s: %.6fs\n", key, (double)value.longValue() / 1000000.0)));
            if (sb.length() > 0) {
                output.accept(sb.toString());
            }
        }
    }
}

