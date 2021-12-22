/*
 * Decompiled with CFR 0.150.
 */
package edu.wpi.first.util.sendable;

import edu.wpi.first.util.function.BooleanConsumer;
import java.util.function.BooleanSupplier;
import java.util.function.Consumer;
import java.util.function.DoubleConsumer;
import java.util.function.DoubleSupplier;
import java.util.function.Supplier;

public interface SendableBuilder {
    public void setSmartDashboardType(String var1);

    public void setActuator(boolean var1);

    public void setSafeState(Runnable var1);

    public void addBooleanProperty(String var1, BooleanSupplier var2, BooleanConsumer var3);

    public void addDoubleProperty(String var1, DoubleSupplier var2, DoubleConsumer var3);

    public void addStringProperty(String var1, Supplier<String> var2, Consumer<String> var3);

    public void addBooleanArrayProperty(String var1, Supplier<boolean[]> var2, Consumer<boolean[]> var3);

    public void addDoubleArrayProperty(String var1, Supplier<double[]> var2, Consumer<double[]> var3);

    public void addStringArrayProperty(String var1, Supplier<String[]> var2, Consumer<String[]> var3);

    public void addRawProperty(String var1, Supplier<byte[]> var2, Consumer<byte[]> var3);

    public BackendKind getBackendKind();

    public boolean isPublished();

    public void update();

    public void clearProperties();

    public static enum BackendKind {
        kUnknown,
        kNetworkTables;

    }
}

