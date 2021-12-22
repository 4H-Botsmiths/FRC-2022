/*
 * Decompiled with CFR 0.150.
 */
package org.ejml.equation;

import java.util.ArrayList;
import java.util.List;
import org.ejml.equation.Operation;
import org.ejml.equation.Variable;

public class Sequence {
    List<Operation> operations = new ArrayList<Operation>();
    Variable output;

    public void addOperation(Operation operation) {
        this.operations.add(operation);
    }

    public void perform() {
        for (int i = 0; i < this.operations.size(); ++i) {
            this.operations.get(i).process();
        }
    }
}

