/*
 * Decompiled with CFR 0.150.
 */
package edu.wpi.first.wpilibj.shuffleboard;

import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardContainer;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardWidget;
import java.util.function.BiConsumer;
import java.util.function.Supplier;

public final class SuppliedValueWidget<T>
extends ShuffleboardWidget<SuppliedValueWidget<T>> {
    private final Supplier<T> m_supplier;
    private final BiConsumer<NetworkTableEntry, T> m_setter;

    SuppliedValueWidget(ShuffleboardContainer parent, String title, Supplier<T> supplier, BiConsumer<NetworkTableEntry, T> setter) {
        super(parent, title);
        this.m_supplier = supplier;
        this.m_setter = setter;
    }

    @Override
    public void buildInto(NetworkTable parentTable, NetworkTable metaTable) {
        this.buildMetadata(metaTable);
        metaTable.getEntry("Controllable").setBoolean(false);
        NetworkTableEntry entry = parentTable.getEntry(this.getTitle());
        this.m_setter.accept(entry, (NetworkTableEntry)this.m_supplier.get());
    }
}

