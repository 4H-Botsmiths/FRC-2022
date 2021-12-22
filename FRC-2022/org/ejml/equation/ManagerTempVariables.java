/*
 * Decompiled with CFR 0.150.
 */
package org.ejml.equation;

import java.util.Random;
import org.ejml.equation.IntegerSequence;
import org.ejml.equation.VariableDouble;
import org.ejml.equation.VariableInteger;
import org.ejml.equation.VariableIntegerSequence;
import org.ejml.equation.VariableMatrix;

public class ManagerTempVariables {
    Random rand = new Random(-559038737L);

    public Random getRandom() {
        return this.rand;
    }

    public VariableMatrix createMatrix() {
        return VariableMatrix.createTemp();
    }

    public VariableDouble createDouble() {
        return new VariableDouble(0.0);
    }

    public VariableDouble createDouble(double value) {
        return new VariableDouble(value);
    }

    public VariableInteger createInteger() {
        return this.createInteger(0);
    }

    public VariableInteger createInteger(int value) {
        return new VariableInteger(value);
    }

    public VariableIntegerSequence createIntegerSequence(IntegerSequence sequence) {
        return new VariableIntegerSequence(sequence);
    }
}

