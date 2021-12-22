/*
 * Decompiled with CFR 0.150.
 */
package org.ejml.equation;

import org.ejml.equation.IntegerSequence;
import org.ejml.equation.Variable;
import org.ejml.equation.VariableType;

public class VariableIntegerSequence
extends Variable {
    IntegerSequence sequence;

    protected VariableIntegerSequence(IntegerSequence sequence) {
        super(VariableType.INTEGER_SEQUENCE);
        this.sequence = sequence;
    }
}

