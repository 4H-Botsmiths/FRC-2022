/*
 * Decompiled with CFR 0.150.
 */
package org.ejml.equation;

import org.ejml.equation.VariableScalar;

public class VariableInteger
extends VariableScalar {
    public int value;

    public VariableInteger(int value) {
        super(VariableScalar.Type.INTEGER);
        this.value = value;
    }

    @Override
    public double getDouble() {
        return this.value;
    }
}

