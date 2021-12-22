/*
 * Decompiled with CFR 0.150.
 */
package org.ejml.equation;

public final class VariableType
extends Enum<VariableType> {
    public static final /* enum */ VariableType MATRIX = new VariableType();
    public static final /* enum */ VariableType SCALAR = new VariableType();
    public static final /* enum */ VariableType INTEGER_SEQUENCE = new VariableType();
    private static final /* synthetic */ VariableType[] $VALUES;

    public static VariableType[] values() {
        return (VariableType[])$VALUES.clone();
    }

    public static VariableType valueOf(String name) {
        return Enum.valueOf(VariableType.class, name);
    }

    private static /* synthetic */ VariableType[] $values() {
        return new VariableType[]{MATRIX, SCALAR, INTEGER_SEQUENCE};
    }

    static {
        $VALUES = VariableType.$values();
    }
}

