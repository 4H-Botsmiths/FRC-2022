/*
 * Decompiled with CFR 0.150.
 */
package edu.wpi.first.wpilibj.shuffleboard;

import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.shuffleboard.EventImportance;
import edu.wpi.first.wpilibj.shuffleboard.RecordingController;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardInstance;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardRoot;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab;

public final class Shuffleboard {
    public static final String kBaseTableName = "/Shuffleboard";
    private static final ShuffleboardRoot root = new ShuffleboardInstance(NetworkTableInstance.getDefault());
    private static final RecordingController recordingController = new RecordingController(NetworkTableInstance.getDefault());

    private Shuffleboard() {
        throw new UnsupportedOperationException("This is a utility class and cannot be instantiated");
    }

    public static void update() {
        root.update();
    }

    public static ShuffleboardTab getTab(String title) {
        return root.getTab(title);
    }

    public static void selectTab(int index) {
        root.selectTab(index);
    }

    public static void selectTab(String title) {
        root.selectTab(title);
    }

    public static void enableActuatorWidgets() {
        root.enableActuatorWidgets();
    }

    public static void disableActuatorWidgets() {
        Shuffleboard.update();
        root.disableActuatorWidgets();
    }

    public static void startRecording() {
        recordingController.startRecording();
    }

    public static void stopRecording() {
        recordingController.stopRecording();
    }

    public static void setRecordingFileNameFormat(String format) {
        recordingController.setRecordingFileNameFormat(format);
    }

    public static void clearRecordingFileNameFormat() {
        recordingController.clearRecordingFileNameFormat();
    }

    public static void addEventMarker(String name, String description, EventImportance importance) {
        recordingController.addEventMarker(name, description, importance);
    }

    public static void addEventMarker(String name, EventImportance importance) {
        Shuffleboard.addEventMarker(name, "", importance);
    }
}

