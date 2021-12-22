/*
 * Decompiled with CFR 0.150.
 */
package org.ejml.equation;

import org.ejml.equation.VariableScalar;

public class VariableDouble
extends VariableScalar {
    public double value;

    public VariableDouble(double value) {
        super(VariableScalar.Type.DOUBLE);
        this.value = value;
    }

    @Override
    public double getDouble() {
        return this.value;
    }
}

