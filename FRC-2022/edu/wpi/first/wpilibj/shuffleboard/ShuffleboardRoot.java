/*
 * Decompiled with CFR 0.150.
 */
package edu.wpi.first.wpilibj.shuffleboard;

import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab;

interface ShuffleboardRoot {
    public ShuffleboardTab getTab(String var1);

    public void update();

    public void enableActuatorWidgets();

    public void disableActuatorWidgets();

    public void selectTab(int var1);

    public void selectTab(String var1);
}

