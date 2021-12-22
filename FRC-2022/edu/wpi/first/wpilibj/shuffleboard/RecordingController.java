/*
 * Decompiled with CFR 0.150.
 */
package edu.wpi.first.wpilibj.shuffleboard;

import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.shuffleboard.EventImportance;

final class RecordingController {
    private static final String kRecordingTableName = "/Shuffleboard/.recording/";
    private static final String kRecordingControlKey = "/Shuffleboard/.recording/RecordData";
    private static final String kRecordingFileNameFormatKey = "/Shuffleboard/.recording/FileNameFormat";
    private static final String kEventMarkerTableName = "/Shuffleboard/.recording/events";
    private final NetworkTableEntry m_recordingControlEntry;
    private final NetworkTableEntry m_recordingFileNameFormatEntry;
    private final NetworkTable m_eventsTable;

    RecordingController(NetworkTableInstance ntInstance) {
        this.m_recordingControlEntry = ntInstance.getEntry(kRecordingControlKey);
        this.m_recordingFileNameFormatEntry = ntInstance.getEntry(kRecordingFileNameFormatKey);
        this.m_eventsTable = ntInstance.getTable(kEventMarkerTableName);
    }

    public void startRecording() {
        this.m_recordingControlEntry.setBoolean(true);
    }

    public void stopRecording() {
        this.m_recordingControlEntry.setBoolean(false);
    }

    public void setRecordingFileNameFormat(String format) {
        this.m_recordingFileNameFormatEntry.setString(format);
    }

    public void clearRecordingFileNameFormat() {
        this.m_recordingFileNameFormatEntry.delete();
    }

    public void addEventMarker(String name, String description, EventImportance importance) {
        if (name == null || name.isEmpty()) {
            DriverStation.reportError("Shuffleboard event name was not specified", true);
            return;
        }
        if (importance == null) {
            DriverStation.reportError("Shuffleboard event importance was null", true);
            return;
        }
        String eventDescription = description == null ? "" : description;
        this.m_eventsTable.getSubTable(name).getEntry("Info").setStringArray(new String[]{eventDescription, importance.getSimpleName()});
    }
}

