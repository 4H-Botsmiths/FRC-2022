/*
 * Decompiled with CFR 0.150.
 */
package org.ejml.equation;

import org.ejml.equation.VariableType;

public class Variable {
    public VariableType type;

    protected Variable(VariableType type) {
        this.type = type;
    }

    public VariableType getType() {
        return this.type;
    }

    public String toString() {
        return "VAR_" + (Object)((Object)this.type);
    }
}

