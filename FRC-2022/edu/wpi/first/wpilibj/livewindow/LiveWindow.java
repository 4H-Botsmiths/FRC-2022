/*
 * Decompiled with CFR 0.150.
 */
package edu.wpi.first.wpilibj.livewindow;

import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.util.sendable.Sendable;
import edu.wpi.first.util.sendable.SendableRegistry;
import edu.wpi.first.wpilibj.smartdashboard.SendableBuilderImpl;

public class LiveWindow {
    private static final int dataHandle = SendableRegistry.getDataHandle();
    private static final NetworkTable liveWindowTable = NetworkTableInstance.getDefault().getTable("LiveWindow");
    private static final NetworkTable statusTable = liveWindowTable.getSubTable(".status");
    private static final NetworkTableEntry enabledEntry = statusTable.getEntry("LW Enabled");
    private static boolean startLiveWindow;
    private static boolean liveWindowEnabled;
    private static boolean telemetryEnabled;
    private static Runnable enabledListener;
    private static Runnable disabledListener;

    private static Component getOrAdd(Sendable sendable) {
        Component data = (Component)SendableRegistry.getData(sendable, dataHandle);
        if (data == null) {
            data = new Component();
            SendableRegistry.setData(sendable, dataHandle, data);
        }
        return data;
    }

    private LiveWindow() {
        throw new UnsupportedOperationException("This is a utility class!");
    }

    public static synchronized void setEnabledListener(Runnable runnable) {
        enabledListener = runnable;
    }

    public static synchronized void setDisabledListener(Runnable runnable) {
        disabledListener = runnable;
    }

    public static synchronized boolean isEnabled() {
        return liveWindowEnabled;
    }

    public static synchronized void setEnabled(boolean enabled) {
        if (liveWindowEnabled != enabled) {
            startLiveWindow = enabled;
            liveWindowEnabled = enabled;
            LiveWindow.updateValues();
            if (enabled) {
                System.out.println("Starting live window mode.");
                if (enabledListener != null) {
                    enabledListener.run();
                }
            } else {
                System.out.println("stopping live window mode.");
                SendableRegistry.foreachLiveWindow(dataHandle, cbdata -> ((SendableBuilderImpl)cbdata.builder).stopLiveWindowMode());
                if (disabledListener != null) {
                    disabledListener.run();
                }
            }
            enabledEntry.setBoolean(enabled);
        }
    }

    public static synchronized void enableTelemetry(Sendable sendable) {
        telemetryEnabled = true;
        LiveWindow.getOrAdd((Sendable)sendable).m_telemetryEnabled = true;
    }

    public static synchronized void disableTelemetry(Sendable sendable) {
        LiveWindow.getOrAdd((Sendable)sendable).m_telemetryEnabled = false;
    }

    public static synchronized void disableAllTelemetry() {
        telemetryEnabled = false;
        SendableRegistry.foreachLiveWindow(dataHandle, cbdata -> {
            if (cbdata.data == null) {
                cbdata.data = new Component();
            }
            ((Component)cbdata.data).m_telemetryEnabled = false;
        });
    }

    public static synchronized void updateValues() {
        if (!liveWindowEnabled && !telemetryEnabled) {
            return;
        }
        SendableRegistry.foreachLiveWindow(dataHandle, cbdata -> {
            if (cbdata.sendable == null || cbdata.parent != null) {
                return;
            }
            if (cbdata.data == null) {
                cbdata.data = new Component();
            }
            Component component = (Component)cbdata.data;
            if (!liveWindowEnabled && !component.m_telemetryEnabled) {
                return;
            }
            if (component.m_firstTime) {
                if (cbdata.name.isEmpty()) {
                    return;
                }
                NetworkTable ssTable = liveWindowTable.getSubTable(cbdata.subsystem);
                NetworkTable table = cbdata.name.equals(cbdata.subsystem) ? ssTable : ssTable.getSubTable(cbdata.name);
                table.getEntry(".name").setString(cbdata.name);
                ((SendableBuilderImpl)cbdata.builder).setTable(table);
                cbdata.sendable.initSendable(cbdata.builder);
                ssTable.getEntry(".type").setString("LW Subsystem");
                component.m_firstTime = false;
            }
            if (startLiveWindow) {
                ((SendableBuilderImpl)cbdata.builder).startLiveWindowMode();
            }
            cbdata.builder.update();
        });
        startLiveWindow = false;
    }

    static {
        telemetryEnabled = true;
        SendableRegistry.setLiveWindowBuilderFactory(() -> new SendableBuilderImpl());
    }

    private static class Component {
        boolean m_firstTime = true;
        boolean m_telemetryEnabled = true;

        private Component() {
        }
    }
}

