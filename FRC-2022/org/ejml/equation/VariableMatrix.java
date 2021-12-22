/*
 * Decompiled with CFR 0.150.
 */
package org.ejml.equation;

import org.ejml.data.DMatrixRMaj;
import org.ejml.equation.Variable;
import org.ejml.equation.VariableType;

public class VariableMatrix
extends Variable {
    public DMatrixRMaj matrix;
    public boolean temp;

    public VariableMatrix(DMatrixRMaj matrix) {
        super(VariableType.MATRIX);
        this.matrix = matrix;
    }

    public static VariableMatrix createTemp() {
        VariableMatrix ret = new VariableMatrix(new DMatrixRMaj(1, 1));
        ret.setTemp(true);
        return ret;
    }

    public boolean isTemp() {
        return this.temp;
    }

    public void setTemp(boolean temp) {
        this.temp = temp;
    }
}

