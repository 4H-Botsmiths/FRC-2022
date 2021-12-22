/*
 * Decompiled with CFR 0.150.
 */
package org.ejml.equation;

import org.ejml.equation.Variable;
import org.ejml.equation.VariableType;

public abstract class VariableScalar
extends Variable {
    Type type;

    protected VariableScalar(Type type) {
        super(VariableType.SCALAR);
        this.type = type;
    }

    public abstract double getDouble();

    @Override
    public String toString() {
        switch (this.type) {
            case INTEGER: {
                return "ScalarI";
            }
            case DOUBLE: {
                return "ScalarD";
            }
            case COMPLEX: {
                return "ScalarC";
            }
        }
        return "ScalarUnknown";
    }

    public Type getScalarType() {
        return this.type;
    }

    public static final class Type
    extends Enum<Type> {
        public static final /* enum */ Type INTEGER = new Type();
        public static final /* enum */ Type DOUBLE = new Type();
        public static final /* enum */ Type COMPLEX = new Type();
        private static final /* synthetic */ Type[] $VALUES;

        public static Type[] values() {
            return (Type[])$VALUES.clone();
        }

        public static Type valueOf(String name) {
            return Enum.valueOf(Type.class, name);
        }

        private static /* synthetic */ Type[] $values() {
            return new Type[]{INTEGER, DOUBLE, COMPLEX};
        }

        static {
            $VALUES = Type.$values();
        }
    }
}

