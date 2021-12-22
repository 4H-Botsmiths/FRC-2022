/*
 * Decompiled with CFR 0.150.
 */
package edu.wpi.first.wpilibj.shuffleboard;

import edu.wpi.first.cscore.VideoSource;
import edu.wpi.first.util.sendable.Sendable;
import edu.wpi.first.wpilibj.shuffleboard.ComplexWidget;
import edu.wpi.first.wpilibj.shuffleboard.LayoutType;
import edu.wpi.first.wpilibj.shuffleboard.SendableCameraWrapper;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardComponent;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardLayout;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardValue;
import edu.wpi.first.wpilibj.shuffleboard.SimpleWidget;
import edu.wpi.first.wpilibj.shuffleboard.SuppliedValueWidget;
import java.util.List;
import java.util.function.BooleanSupplier;
import java.util.function.DoubleSupplier;
import java.util.function.Supplier;

public interface ShuffleboardContainer
extends ShuffleboardValue {
    public List<ShuffleboardComponent<?>> getComponents();

    public ShuffleboardLayout getLayout(String var1, String var2);

    default public ShuffleboardLayout getLayout(String title, LayoutType layoutType) {
        return this.getLayout(title, layoutType.getLayoutName());
    }

    public ShuffleboardLayout getLayout(String var1);

    public ComplexWidget add(String var1, Sendable var2);

    default public ComplexWidget add(String title, VideoSource video) {
        return this.add(title, SendableCameraWrapper.wrap(video));
    }

    public ComplexWidget add(Sendable var1);

    default public ComplexWidget add(VideoSource video) {
        return this.add(SendableCameraWrapper.wrap(video));
    }

    public SimpleWidget add(String var1, Object var2);

    public SuppliedValueWidget<String> addString(String var1, Supplier<String> var2);

    public SuppliedValueWidget<Double> addNumber(String var1, DoubleSupplier var2);

    public SuppliedValueWidget<Boolean> addBoolean(String var1, BooleanSupplier var2);

    public SuppliedValueWidget<String[]> addStringArray(String var1, Supplier<String[]> var2);

    public SuppliedValueWidget<double[]> addDoubleArray(String var1, Supplier<double[]> var2);

    public SuppliedValueWidget<boolean[]> addBooleanArray(String var1, Supplier<boolean[]> var2);

    public SuppliedValueWidget<byte[]> addRaw(String var1, Supplier<byte[]> var2);

    default public SimpleWidget addPersistent(String title, Object defaultValue) {
        SimpleWidget widget = this.add(title, defaultValue);
        widget.getEntry().setPersistent();
        return widget;
    }
}

