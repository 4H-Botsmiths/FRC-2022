/*
 * Decompiled with CFR 0.150.
 */
package org.ejml.sparse;

public final class FillReducing
extends Enum<FillReducing> {
    public static final /* enum */ FillReducing NONE = new FillReducing();
    public static final /* enum */ FillReducing RANDOM = new FillReducing();
    public static final /* enum */ FillReducing IDENTITY = new FillReducing();
    private static final /* synthetic */ FillReducing[] $VALUES;

    public static FillReducing[] values() {
        return (FillReducing[])$VALUES.clone();
    }

    public static FillReducing valueOf(String name) {
        return Enum.valueOf(FillReducing.class, name);
    }

    private static /* synthetic */ FillReducing[] $values() {
        return new FillReducing[]{NONE, RANDOM, IDENTITY};
    }

    static {
        $VALUES = FillReducing.$values();
    }
}

