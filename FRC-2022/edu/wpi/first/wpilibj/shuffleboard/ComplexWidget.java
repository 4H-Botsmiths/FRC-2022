/*
 * Decompiled with CFR 0.150.
 */
package edu.wpi.first.wpilibj.shuffleboard;

import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.util.sendable.Sendable;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardContainer;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardWidget;
import edu.wpi.first.wpilibj.smartdashboard.SendableBuilderImpl;

public final class ComplexWidget
extends ShuffleboardWidget<ComplexWidget> {
    private final Sendable m_sendable;
    private SendableBuilderImpl m_builder;

    ComplexWidget(ShuffleboardContainer parent, String title, Sendable sendable) {
        super(parent, title);
        this.m_sendable = sendable;
    }

    @Override
    public void buildInto(NetworkTable parentTable, NetworkTable metaTable) {
        this.buildMetadata(metaTable);
        if (this.m_builder == null) {
            this.m_builder = new SendableBuilderImpl();
            this.m_builder.setTable(parentTable.getSubTable(this.getTitle()));
            this.m_sendable.initSendable(this.m_builder);
            this.m_builder.startListeners();
        }
        this.m_builder.update();
    }

    void enableIfActuator() {
        if (this.m_builder.isActuator()) {
            this.m_builder.startLiveWindowMode();
        }
    }

    void disableIfActuator() {
        if (this.m_builder.isActuator()) {
            this.m_builder.stopLiveWindowMode();
        }
    }
}

