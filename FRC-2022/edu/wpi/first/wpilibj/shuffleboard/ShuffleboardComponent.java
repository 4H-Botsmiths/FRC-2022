/*
 * Decompiled with CFR 0.150.
 */
package edu.wpi.first.wpilibj.shuffleboard;

import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardContainer;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardValue;
import edu.wpi.first.wpilibj.util.ErrorMessages;
import java.util.Map;

public abstract class ShuffleboardComponent<C extends ShuffleboardComponent<C>>
implements ShuffleboardValue {
    private final ShuffleboardContainer m_parent;
    private final String m_title;
    private String m_type;
    private Map<String, Object> m_properties;
    private boolean m_metadataDirty = true;
    private int m_column = -1;
    private int m_row = -1;
    private int m_width = -1;
    private int m_height = -1;

    protected ShuffleboardComponent(ShuffleboardContainer parent, String title, String type) {
        this.m_parent = ErrorMessages.requireNonNullParam(parent, "parent", "ShuffleboardComponent");
        this.m_title = ErrorMessages.requireNonNullParam(title, "title", "ShuffleboardComponent");
        this.m_type = type;
    }

    protected ShuffleboardComponent(ShuffleboardContainer parent, String title) {
        this(parent, title, null);
    }

    public final ShuffleboardContainer getParent() {
        return this.m_parent;
    }

    protected final void setType(String type) {
        this.m_type = type;
        this.m_metadataDirty = true;
    }

    public final String getType() {
        return this.m_type;
    }

    @Override
    public final String getTitle() {
        return this.m_title;
    }

    final Map<String, Object> getProperties() {
        return this.m_properties;
    }

    public final C withProperties(Map<String, Object> properties) {
        this.m_properties = properties;
        this.m_metadataDirty = true;
        return (C)this;
    }

    public final C withPosition(int columnIndex, int rowIndex) {
        this.m_column = columnIndex;
        this.m_row = rowIndex;
        this.m_metadataDirty = true;
        return (C)this;
    }

    public final C withSize(int width, int height) {
        this.m_width = width;
        this.m_height = height;
        this.m_metadataDirty = true;
        return (C)this;
    }

    protected final void buildMetadata(NetworkTable metaTable) {
        if (!this.m_metadataDirty) {
            return;
        }
        if (this.getType() == null) {
            metaTable.getEntry("PreferredComponent").delete();
        } else {
            metaTable.getEntry("PreferredComponent").forceSetString(this.getType());
        }
        if (this.m_width <= 0 || this.m_height <= 0) {
            metaTable.getEntry("Size").delete();
        } else {
            metaTable.getEntry("Size").setDoubleArray(new double[]{this.m_width, this.m_height});
        }
        if (this.m_column < 0 || this.m_row < 0) {
            metaTable.getEntry("Position").delete();
        } else {
            metaTable.getEntry("Position").setDoubleArray(new double[]{this.m_column, this.m_row});
        }
        if (this.getProperties() != null) {
            NetworkTable propTable = metaTable.getSubTable("Properties");
            this.getProperties().forEach((name, value) -> propTable.getEntry((String)name).setValue(value));
        }
        this.m_metadataDirty = false;
    }
}

