/*
 * Decompiled with CFR 0.150.
 */
package edu.wpi.first.wpilibj.shuffleboard;

import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.util.sendable.Sendable;
import edu.wpi.first.util.sendable.SendableRegistry;
import edu.wpi.first.wpilibj.shuffleboard.ComplexWidget;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardComponent;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardContainer;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardLayout;
import edu.wpi.first.wpilibj.shuffleboard.SimpleWidget;
import edu.wpi.first.wpilibj.shuffleboard.SuppliedValueWidget;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.BooleanSupplier;
import java.util.function.DoubleSupplier;
import java.util.function.Supplier;

final class ContainerHelper {
    private final ShuffleboardContainer m_container;
    private final Set<String> m_usedTitles = new HashSet<String>();
    private final List<ShuffleboardComponent<?>> m_components = new ArrayList();
    private final Map<String, ShuffleboardLayout> m_layouts = new LinkedHashMap<String, ShuffleboardLayout>();

    ContainerHelper(ShuffleboardContainer container) {
        this.m_container = container;
    }

    List<ShuffleboardComponent<?>> getComponents() {
        return this.m_components;
    }

    ShuffleboardLayout getLayout(String title, String type) {
        if (!this.m_layouts.containsKey(title)) {
            ShuffleboardLayout layout = new ShuffleboardLayout(this.m_container, title, type);
            this.m_components.add(layout);
            this.m_layouts.put(title, layout);
        }
        return this.m_layouts.get(title);
    }

    ShuffleboardLayout getLayout(String title) {
        ShuffleboardLayout layout = this.m_layouts.get(title);
        if (layout == null) {
            throw new NoSuchElementException("No layout has been defined with the title '" + title + "'");
        }
        return layout;
    }

    ComplexWidget add(String title, Sendable sendable) {
        this.checkTitle(title);
        ComplexWidget widget = new ComplexWidget(this.m_container, title, sendable);
        this.m_components.add(widget);
        return widget;
    }

    ComplexWidget add(Sendable sendable) {
        String name = SendableRegistry.getName(sendable);
        if (name.isEmpty()) {
            throw new IllegalArgumentException("Sendable must have a name");
        }
        return this.add(name, sendable);
    }

    SimpleWidget add(String title, Object defaultValue) {
        Objects.requireNonNull(title, "Title cannot be null");
        Objects.requireNonNull(defaultValue, "Default value cannot be null");
        this.checkTitle(title);
        ContainerHelper.checkNtType(defaultValue);
        SimpleWidget widget = new SimpleWidget(this.m_container, title);
        this.m_components.add(widget);
        widget.getEntry().setDefaultValue(defaultValue);
        return widget;
    }

    SuppliedValueWidget<String> addString(String title, Supplier<String> valueSupplier) {
        this.precheck(title, valueSupplier);
        return this.addSupplied(title, valueSupplier, NetworkTableEntry::setString);
    }

    SuppliedValueWidget<Double> addNumber(String title, DoubleSupplier valueSupplier) {
        this.precheck(title, valueSupplier);
        return this.addSupplied(title, valueSupplier::getAsDouble, NetworkTableEntry::setDouble);
    }

    SuppliedValueWidget<Boolean> addBoolean(String title, BooleanSupplier valueSupplier) {
        this.precheck(title, valueSupplier);
        return this.addSupplied(title, valueSupplier::getAsBoolean, NetworkTableEntry::setBoolean);
    }

    SuppliedValueWidget<String[]> addStringArray(String title, Supplier<String[]> valueSupplier) {
        this.precheck(title, valueSupplier);
        return this.addSupplied(title, valueSupplier, NetworkTableEntry::setStringArray);
    }

    SuppliedValueWidget<double[]> addDoubleArray(String title, Supplier<double[]> valueSupplier) {
        this.precheck(title, valueSupplier);
        return this.addSupplied(title, valueSupplier, NetworkTableEntry::setDoubleArray);
    }

    SuppliedValueWidget<boolean[]> addBooleanArray(String title, Supplier<boolean[]> valueSupplier) {
        this.precheck(title, valueSupplier);
        return this.addSupplied(title, valueSupplier, NetworkTableEntry::setBooleanArray);
    }

    SuppliedValueWidget<byte[]> addRaw(String title, Supplier<byte[]> valueSupplier) {
        this.precheck(title, valueSupplier);
        return this.addSupplied(title, valueSupplier, NetworkTableEntry::setRaw);
    }

    private void precheck(String title, Object valueSupplier) {
        Objects.requireNonNull(title, "Title cannot be null");
        Objects.requireNonNull(valueSupplier, "Value supplier cannot be null");
        this.checkTitle(title);
    }

    private <T> SuppliedValueWidget<T> addSupplied(String title, Supplier<T> supplier, BiConsumer<NetworkTableEntry, T> setter) {
        SuppliedValueWidget<T> widget = new SuppliedValueWidget<T>(this.m_container, title, supplier, setter);
        this.m_components.add(widget);
        return widget;
    }

    private static void checkNtType(Object data) {
        if (!NetworkTableEntry.isValidDataType(data)) {
            throw new IllegalArgumentException("Cannot add data of type " + data.getClass().getName() + " to Shuffleboard");
        }
    }

    private void checkTitle(String title) {
        if (this.m_usedTitles.contains(title)) {
            throw new IllegalArgumentException("Title is already in use: " + title);
        }
        this.m_usedTitles.add(title);
    }
}

