/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  org.jetbrains.annotations.Nullable
 */
package org.ejml.equation;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.ejml.equation.ManagerTempVariables;
import org.ejml.equation.Operation;
import org.ejml.equation.Symbol;
import org.ejml.equation.Variable;
import org.jetbrains.annotations.Nullable;

public class ManagerFunctions {
    Map<String, Input1> input1 = new HashMap<String, Input1>();
    Map<String, InputN> inputN = new HashMap<String, InputN>();
    protected ManagerTempVariables managerTemp;

    public ManagerFunctions() {
        this.addBuiltIn();
    }

    public boolean isFunctionName(String s) {
        if (this.input1.containsKey(s)) {
            return true;
        }
        return this.inputN.containsKey(s);
    }

    @Nullable
    public Operation.Info create(String name, Variable var0) {
        Input1 func = this.input1.get(name);
        if (func == null) {
            return null;
        }
        return func.create(var0, this.managerTemp);
    }

    @Nullable
    public Operation.Info create(String name, List<Variable> vars) {
        InputN func = this.inputN.get(name);
        if (func == null) {
            return null;
        }
        return func.create(vars, this.managerTemp);
    }

    public Operation.Info create(char op, Variable input) {
        switch (op) {
            case '\'': {
                return Operation.transpose(input, this.managerTemp);
            }
        }
        throw new RuntimeException("Unknown operation " + op);
    }

    public Operation.Info create(Symbol op, Variable left, Variable right) {
        switch (op) {
            case PLUS: {
                return Operation.add(left, right, this.managerTemp);
            }
            case MINUS: {
                return Operation.subtract(left, right, this.managerTemp);
            }
            case TIMES: {
                return Operation.multiply(left, right, this.managerTemp);
            }
            case RDIVIDE: {
                return Operation.divide(left, right, this.managerTemp);
            }
            case LDIVIDE: {
                return Operation.divide(right, left, this.managerTemp);
            }
            case POWER: {
                return Operation.pow(left, right, this.managerTemp);
            }
            case ELEMENT_DIVIDE: {
                return Operation.elementDivision(left, right, this.managerTemp);
            }
            case ELEMENT_TIMES: {
                return Operation.elementMult(left, right, this.managerTemp);
            }
            case ELEMENT_POWER: {
                return Operation.elementPow(left, right, this.managerTemp);
            }
        }
        throw new RuntimeException("Unknown operation " + (Object)((Object)op));
    }

    public void setManagerTemp(ManagerTempVariables managerTemp) {
        this.managerTemp = managerTemp;
    }

    public void add1(String name, Input1 function) {
        this.input1.put(name, function);
    }

    public void addN(String name, InputN function) {
        this.inputN.put(name, function);
    }

    private void addBuiltIn() {
        this.input1.put("inv", Operation::inv);
        this.input1.put("pinv", Operation::pinv);
        this.input1.put("rref", Operation::rref);
        this.input1.put("eye", Operation::eye);
        this.input1.put("det", Operation::det);
        this.input1.put("normF", Operation::normF);
        this.input1.put("sum", Operation::sum_one);
        this.input1.put("trace", Operation::trace);
        this.input1.put("diag", Operation::diag);
        this.input1.put("min", Operation::min);
        this.input1.put("max", Operation::max);
        this.input1.put("abs", Operation::abs);
        this.input1.put("sin", Operation::sin);
        this.input1.put("cos", Operation::cos);
        this.input1.put("atan", Operation::atan);
        this.input1.put("exp", Operation::exp);
        this.input1.put("log", Operation::log);
        this.input1.put("sqrt", Operation::sqrt);
        this.input1.put("rng", Operation::rng);
        this.inputN.put("normP", (inputs, manager) -> {
            if (inputs.size() != 2) {
                throw new RuntimeException("Two inputs expected");
            }
            return Operation.normP((Variable)inputs.get(0), (Variable)inputs.get(1), manager);
        });
        this.inputN.put("max", (inputs, manager) -> {
            if (inputs.size() != 2) {
                throw new RuntimeException("One or two inputs expected");
            }
            return Operation.max_two((Variable)inputs.get(0), (Variable)inputs.get(1), manager);
        });
        this.inputN.put("min", (inputs, manager) -> {
            if (inputs.size() != 2) {
                throw new RuntimeException("One or two inputs expected");
            }
            return Operation.min_two((Variable)inputs.get(0), (Variable)inputs.get(1), manager);
        });
        this.inputN.put("sum", (inputs, manager) -> {
            if (inputs.size() != 2) {
                throw new RuntimeException("One or two inputs expected");
            }
            return Operation.sum_two((Variable)inputs.get(0), (Variable)inputs.get(1), manager);
        });
        this.inputN.put("zeros", (inputs, manager) -> {
            if (inputs.size() != 2) {
                throw new RuntimeException("Two inputs expected");
            }
            return Operation.zeros((Variable)inputs.get(0), (Variable)inputs.get(1), manager);
        });
        this.inputN.put("ones", (inputs, manager) -> {
            if (inputs.size() != 2) {
                throw new RuntimeException("Two inputs expected");
            }
            return Operation.ones((Variable)inputs.get(0), (Variable)inputs.get(1), manager);
        });
        this.inputN.put("rand", (inputs, manager) -> {
            if (inputs.size() != 2) {
                throw new RuntimeException("Two inputs expected");
            }
            return Operation.rand((Variable)inputs.get(0), (Variable)inputs.get(1), manager);
        });
        this.inputN.put("randn", (inputs, manager) -> {
            if (inputs.size() != 2) {
                throw new RuntimeException("Two inputs expected");
            }
            return Operation.randn((Variable)inputs.get(0), (Variable)inputs.get(1), manager);
        });
        this.inputN.put("kron", (inputs, manager) -> {
            if (inputs.size() != 2) {
                throw new RuntimeException("Two inputs expected");
            }
            return Operation.kron((Variable)inputs.get(0), (Variable)inputs.get(1), manager);
        });
        this.inputN.put("dot", (inputs, manager) -> {
            if (inputs.size() != 2) {
                throw new RuntimeException("Two inputs expected");
            }
            return Operation.dot((Variable)inputs.get(0), (Variable)inputs.get(1), manager);
        });
        this.inputN.put("pow", (inputs, manager) -> {
            if (inputs.size() != 2) {
                throw new RuntimeException("Two inputs expected");
            }
            return Operation.pow((Variable)inputs.get(0), (Variable)inputs.get(1), manager);
        });
        this.inputN.put("atan2", (inputs, manager) -> {
            if (inputs.size() != 2) {
                throw new RuntimeException("Two inputs expected");
            }
            return Operation.atan2((Variable)inputs.get(0), (Variable)inputs.get(1), manager);
        });
        this.inputN.put("solve", (inputs, manager) -> {
            if (inputs.size() != 2) {
                throw new RuntimeException("Two inputs expected");
            }
            return Operation.solve((Variable)inputs.get(0), (Variable)inputs.get(1), manager);
        });
        this.inputN.put("extract", Operation::extract);
        this.inputN.put("extractScalar", (inputs, manager) -> {
            if (inputs.size() != 2 && inputs.size() != 3) {
                throw new RuntimeException("Two or three inputs expected");
            }
            return Operation.extractScalar(inputs, manager);
        });
    }

    public ManagerTempVariables getManagerTemp() {
        return this.managerTemp;
    }

    public static interface Input1 {
        public Operation.Info create(Variable var1, ManagerTempVariables var2);
    }

    public static interface InputN {
        public Operation.Info create(List<Variable> var1, ManagerTempVariables var2);
    }
}

