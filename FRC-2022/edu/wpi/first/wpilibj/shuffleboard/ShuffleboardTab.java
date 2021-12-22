/*
 * Decompiled with CFR 0.150.
 */
package edu.wpi.first.wpilibj.shuffleboard;

import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.util.sendable.Sendable;
import edu.wpi.first.wpilibj.shuffleboard.ComplexWidget;
import edu.wpi.first.wpilibj.shuffleboard.ContainerHelper;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardComponent;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardContainer;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardLayout;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardRoot;
import edu.wpi.first.wpilibj.shuffleboard.SimpleWidget;
import edu.wpi.first.wpilibj.shuffleboard.SuppliedValueWidget;
import java.util.List;
import java.util.function.BooleanSupplier;
import java.util.function.DoubleSupplier;
import java.util.function.Supplier;

public final class ShuffleboardTab
implements ShuffleboardContainer {
    private final ContainerHelper m_helper = new ContainerHelper(this);
    private final ShuffleboardRoot m_root;
    private final String m_title;

    ShuffleboardTab(ShuffleboardRoot root, String title) {
        this.m_root = root;
        this.m_title = title;
    }

    @Override
    public String getTitle() {
        return this.m_title;
    }

    ShuffleboardRoot getRoot() {
        return this.m_root;
    }

    @Override
    public List<ShuffleboardComponent<?>> getComponents() {
        return this.m_helper.getComponents();
    }

    @Override
    public ShuffleboardLayout getLayout(String title, String type) {
        return this.m_helper.getLayout(title, type);
    }

    @Override
    public ShuffleboardLayout getLayout(String title) {
        return this.m_helper.getLayout(title);
    }

    @Override
    public ComplexWidget add(String title, Sendable sendable) {
        return this.m_helper.add(title, sendable);
    }

    @Override
    public ComplexWidget add(Sendable sendable) {
        return this.m_helper.add(sendable);
    }

    @Override
    public SimpleWidget add(String title, Object defaultValue) {
        return this.m_helper.add(title, defaultValue);
    }

    @Override
    public SuppliedValueWidget<String> addString(String title, Supplier<String> valueSupplier) {
        return this.m_helper.addString(title, valueSupplier);
    }

    @Override
    public SuppliedValueWidget<Double> addNumber(String title, DoubleSupplier valueSupplier) {
        return this.m_helper.addNumber(title, valueSupplier);
    }

    @Override
    public SuppliedValueWidget<Boolean> addBoolean(String title, BooleanSupplier valueSupplier) {
        return this.m_helper.addBoolean(title, valueSupplier);
    }

    @Override
    public SuppliedValueWidget<String[]> addStringArray(String title, Supplier<String[]> valueSupplier) {
        return this.m_helper.addStringArray(title, valueSupplier);
    }

    @Override
    public SuppliedValueWidget<double[]> addDoubleArray(String title, Supplier<double[]> valueSupplier) {
        return this.m_helper.addDoubleArray(title, valueSupplier);
    }

    @Override
    public SuppliedValueWidget<boolean[]> addBooleanArray(String title, Supplier<boolean[]> valueSupplier) {
        return this.m_helper.addBooleanArray(title, valueSupplier);
    }

    @Override
    public SuppliedValueWidget<byte[]> addRaw(String title, Supplier<byte[]> valueSupplier) {
        return this.m_helper.addRaw(title, valueSupplier);
    }

    @Override
    public void buildInto(NetworkTable parentTable, NetworkTable metaTable) {
        NetworkTable tabTable = parentTable.getSubTable(this.m_title);
        tabTable.getEntry(".type").setString("ShuffleboardTab");
        for (ShuffleboardComponent<?> component : this.m_helper.getComponents()) {
            component.buildInto(tabTable, metaTable.getSubTable(component.getTitle()));
        }
    }
}

