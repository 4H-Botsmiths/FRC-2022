/*
 * Decompiled with CFR 0.150.
 */
package edu.wpi.first.wpilibj;

import edu.wpi.first.hal.HAL;
import edu.wpi.first.hal.SimBoolean;
import edu.wpi.first.hal.SimDevice;
import edu.wpi.first.hal.SimDouble;
import edu.wpi.first.util.sendable.Sendable;
import edu.wpi.first.util.sendable.SendableBuilder;
import edu.wpi.first.util.sendable.SendableRegistry;
import edu.wpi.first.wpilibj.Counter;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.DigitalOutput;
import edu.wpi.first.wpilibj.Timer;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Ultrasonic
implements Sendable,
AutoCloseable {
    private static final double kPingTime = 9.999999999999999E-6;
    private static final double kSpeedOfSoundInchesPerSec = 13560.0;
    private static final List<Ultrasonic> m_sensors = new ArrayList<Ultrasonic>();
    private static volatile boolean m_automaticEnabled;
    private DigitalInput m_echoChannel;
    private DigitalOutput m_pingChannel;
    private final boolean m_allocatedChannels;
    private boolean m_enabled;
    private Counter m_counter;
    private static Thread m_task;
    private static int m_instances;
    private SimDevice m_simDevice;
    private SimBoolean m_simRangeValid;
    private SimDouble m_simRange;

    private synchronized void initialize() {
        this.m_simDevice = SimDevice.create("Ultrasonic", this.m_echoChannel.getChannel());
        if (this.m_simDevice != null) {
            this.m_simRangeValid = this.m_simDevice.createBoolean("Range Valid", SimDevice.Direction.kInput, true);
            this.m_simRange = this.m_simDevice.createDouble("Range (in)", SimDevice.Direction.kInput, 0.0);
            this.m_pingChannel.setSimDevice(this.m_simDevice);
            this.m_echoChannel.setSimDevice(this.m_simDevice);
        }
        if (m_task == null) {
            m_task = new UltrasonicChecker();
        }
        boolean originalMode = m_automaticEnabled;
        Ultrasonic.setAutomaticMode(false);
        m_sensors.add(this);
        this.m_counter = new Counter(this.m_echoChannel);
        SendableRegistry.addChild(this, this.m_counter);
        this.m_counter.setMaxPeriod(1.0);
        this.m_counter.setSemiPeriodMode(true);
        this.m_counter.reset();
        this.m_enabled = true;
        Ultrasonic.setAutomaticMode(originalMode);
        HAL.report(37, ++m_instances);
        SendableRegistry.addLW((Sendable)this, "Ultrasonic", this.m_echoChannel.getChannel());
    }

    public int getEchoChannel() {
        return this.m_echoChannel.getChannel();
    }

    public Ultrasonic(int pingChannel, int echoChannel) {
        this.m_pingChannel = new DigitalOutput(pingChannel);
        this.m_echoChannel = new DigitalInput(echoChannel);
        SendableRegistry.addChild(this, this.m_pingChannel);
        SendableRegistry.addChild(this, this.m_echoChannel);
        this.m_allocatedChannels = true;
        this.initialize();
    }

    public Ultrasonic(DigitalOutput pingChannel, DigitalInput echoChannel) {
        Objects.requireNonNull(pingChannel, "Provided ping channel was null");
        Objects.requireNonNull(echoChannel, "Provided echo channel was null");
        this.m_allocatedChannels = false;
        this.m_pingChannel = pingChannel;
        this.m_echoChannel = echoChannel;
        this.initialize();
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public synchronized void close() {
        SendableRegistry.remove(this);
        boolean wasAutomaticMode = m_automaticEnabled;
        Ultrasonic.setAutomaticMode(false);
        if (this.m_allocatedChannels) {
            if (this.m_pingChannel != null) {
                this.m_pingChannel.close();
            }
            if (this.m_echoChannel != null) {
                this.m_echoChannel.close();
            }
        }
        if (this.m_counter != null) {
            this.m_counter.close();
            this.m_counter = null;
        }
        this.m_pingChannel = null;
        this.m_echoChannel = null;
        List<Ultrasonic> list = m_sensors;
        synchronized (list) {
            m_sensors.remove(this);
        }
        if (!m_sensors.isEmpty() && wasAutomaticMode) {
            Ultrasonic.setAutomaticMode(true);
        }
        if (this.m_simDevice != null) {
            this.m_simDevice.close();
            this.m_simDevice = null;
        }
    }

    public static void setAutomaticMode(boolean enabling) {
        if (enabling == m_automaticEnabled) {
            return;
        }
        m_automaticEnabled = enabling;
        if (enabling) {
            for (Ultrasonic u : m_sensors) {
                u.m_counter.reset();
            }
            m_task.start();
        } else {
            try {
                m_task.join();
            }
            catch (InterruptedException ex) {
                Thread.currentThread().interrupt();
                ex.printStackTrace();
            }
            for (Ultrasonic u : m_sensors) {
                u.m_counter.reset();
            }
        }
    }

    public void ping() {
        Ultrasonic.setAutomaticMode(false);
        this.m_counter.reset();
        this.m_pingChannel.pulse(9.999999999999999E-6);
    }

    public boolean isRangeValid() {
        if (this.m_simRangeValid != null) {
            return this.m_simRangeValid.get();
        }
        return this.m_counter.get() > 1;
    }

    public double getRangeInches() {
        if (this.isRangeValid()) {
            if (this.m_simRange != null) {
                return this.m_simRange.get();
            }
            return this.m_counter.getPeriod() * 13560.0 / 2.0;
        }
        return 0.0;
    }

    public double getRangeMM() {
        return this.getRangeInches() * 25.4;
    }

    public boolean isEnabled() {
        return this.m_enabled;
    }

    public void setEnabled(boolean enable) {
        this.m_enabled = enable;
    }

    @Override
    public void initSendable(SendableBuilder builder) {
        builder.setSmartDashboardType("Ultrasonic");
        builder.addDoubleProperty("Value", this::getRangeInches, null);
    }

    private static class UltrasonicChecker
    extends Thread {
        private UltrasonicChecker() {
        }

        @Override
        public synchronized void run() {
            block0: while (m_automaticEnabled) {
                for (Ultrasonic sensor : m_sensors) {
                    if (!m_automaticEnabled) continue block0;
                    if (sensor.isEnabled()) {
                        sensor.m_pingChannel.pulse(9.999999999999999E-6);
                    }
                    Timer.delay(0.1);
                }
            }
        }
    }
}

