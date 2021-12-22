/*
 * Decompiled with CFR 0.150.
 */
package edu.wpi.first.wpilibj.shuffleboard;

import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardComponent;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardContainer;
import edu.wpi.first.wpilibj.shuffleboard.WidgetType;

abstract class ShuffleboardWidget<W extends ShuffleboardWidget<W>>
extends ShuffleboardComponent<W> {
    ShuffleboardWidget(ShuffleboardContainer parent, String title) {
        super(parent, title);
    }

    public final W withWidget(WidgetType widgetType) {
        return this.withWidget(widgetType.getWidgetName());
    }

    public final W withWidget(String widgetType) {
        this.setType(widgetType);
        return (W)this;
    }
}

