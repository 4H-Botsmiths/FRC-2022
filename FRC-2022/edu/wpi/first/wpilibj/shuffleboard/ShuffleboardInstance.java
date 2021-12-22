/*
 * Decompiled with CFR 0.150.
 */
package edu.wpi.first.wpilibj.shuffleboard;

import edu.wpi.first.hal.HAL;
import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.shuffleboard.ComplexWidget;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardComponent;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardContainer;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardRoot;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab;
import edu.wpi.first.wpilibj.util.ErrorMessages;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.function.Consumer;

final class ShuffleboardInstance
implements ShuffleboardRoot {
    private final Map<String, ShuffleboardTab> m_tabs = new LinkedHashMap<String, ShuffleboardTab>();
    private boolean m_tabsChanged = false;
    private final NetworkTable m_rootTable;
    private final NetworkTable m_rootMetaTable;
    private final NetworkTableEntry m_selectedTabEntry;

    ShuffleboardInstance(NetworkTableInstance ntInstance) {
        ErrorMessages.requireNonNullParam(ntInstance, "ntInstance", "ShuffleboardInstance");
        this.m_rootTable = ntInstance.getTable("/Shuffleboard");
        this.m_rootMetaTable = this.m_rootTable.getSubTable(".metadata");
        this.m_selectedTabEntry = this.m_rootMetaTable.getEntry("Selected");
        HAL.report(78, 0);
    }

    @Override
    public ShuffleboardTab getTab(String title) {
        ErrorMessages.requireNonNullParam(title, "title", "getTab");
        if (!this.m_tabs.containsKey(title)) {
            this.m_tabs.put(title, new ShuffleboardTab(this, title));
            this.m_tabsChanged = true;
        }
        return this.m_tabs.get(title);
    }

    @Override
    public void update() {
        if (this.m_tabsChanged) {
            String[] tabTitles = (String[])this.m_tabs.values().stream().map(ShuffleboardTab::getTitle).toArray(String[]::new);
            this.m_rootMetaTable.getEntry("Tabs").forceSetStringArray(tabTitles);
            this.m_tabsChanged = false;
        }
        for (ShuffleboardTab tab : this.m_tabs.values()) {
            String title = tab.getTitle();
            tab.buildInto(this.m_rootTable, this.m_rootMetaTable.getSubTable(title));
        }
    }

    @Override
    public void enableActuatorWidgets() {
        this.applyToAllComplexWidgets(ComplexWidget::enableIfActuator);
    }

    @Override
    public void disableActuatorWidgets() {
        this.applyToAllComplexWidgets(ComplexWidget::disableIfActuator);
    }

    @Override
    public void selectTab(int index) {
        this.m_selectedTabEntry.forceSetDouble(index);
    }

    @Override
    public void selectTab(String title) {
        this.m_selectedTabEntry.forceSetString(title);
    }

    private void applyToAllComplexWidgets(Consumer<ComplexWidget> func) {
        for (ShuffleboardTab tab : this.m_tabs.values()) {
            this.apply(tab, func);
        }
    }

    private void apply(ShuffleboardContainer container, Consumer<ComplexWidget> func) {
        for (ShuffleboardComponent<?> component : container.getComponents()) {
            if (component instanceof ComplexWidget) {
                func.accept((ComplexWidget)component);
            }
            if (!(component instanceof ShuffleboardContainer)) continue;
            this.apply((ShuffleboardContainer)((Object)component), func);
        }
    }
}

