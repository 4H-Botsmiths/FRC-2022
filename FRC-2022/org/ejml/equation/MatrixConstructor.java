/*
 * Decompiled with CFR 0.150.
 */
package org.ejml.equation;

import java.util.ArrayList;
import java.util.List;
import org.ejml.data.DMatrixRMaj;
import org.ejml.dense.row.CommonOps_DDRM;
import org.ejml.equation.IntegerSequence;
import org.ejml.equation.ManagerTempVariables;
import org.ejml.equation.ParseError;
import org.ejml.equation.Variable;
import org.ejml.equation.VariableIntegerSequence;
import org.ejml.equation.VariableMatrix;
import org.ejml.equation.VariableScalar;
import org.ejml.equation.VariableType;

public class MatrixConstructor {
    VariableMatrix output;
    List<Item> items = new ArrayList<Item>();

    public MatrixConstructor(ManagerTempVariables manager) {
        this.output = manager.createMatrix();
    }

    public void addToRow(Variable variable) {
        if (variable.getType() == VariableType.INTEGER_SEQUENCE && ((VariableIntegerSequence)variable).sequence.requiresMaxIndex()) {
            throw new ParseError("Trying to create a matrix with an unbounded integer range. Forgot a value after a colon?");
        }
        this.items.add(new Item(variable));
    }

    public void endRow() {
        this.items.add(new Item());
    }

    public void construct() {
        if (!this.items.get((int)(this.items.size() - 1)).endRow) {
            this.endRow();
        }
        for (int i = 0; i < this.items.size(); ++i) {
            this.items.get(i).initialize();
        }
        this.setToRequiredSize(this.output.matrix);
        int matrixRow = 0;
        ArrayList<Item> row = new ArrayList<Item>();
        for (int i = 0; i < this.items.size(); ++i) {
            Item item = this.items.get(i);
            if (item.endRow) {
                int expectedRows = 0;
                int numCols = 0;
                for (int j = 0; j < row.size(); ++j) {
                    Item v = (Item)row.get(j);
                    int numRows = v.getRows();
                    if (j == 0) {
                        expectedRows = numRows;
                    } else if (v.getRows() != expectedRows) {
                        throw new RuntimeException("Row miss-matched. " + numRows + " " + v.getRows());
                    }
                    if (v.matrix) {
                        CommonOps_DDRM.insert(v.getMatrix(), this.output.matrix, matrixRow, numCols);
                    } else if (v.variable.getType() == VariableType.SCALAR) {
                        this.output.matrix.set(matrixRow, numCols, v.getValue());
                    } else if (v.variable.getType() == VariableType.INTEGER_SEQUENCE) {
                        IntegerSequence sequence = ((VariableIntegerSequence)v.variable).sequence;
                        int col = numCols;
                        while (sequence.hasNext()) {
                            this.output.matrix.set(matrixRow, col++, sequence.next());
                        }
                    } else {
                        throw new ParseError("Can't insert a variable of type " + (Object)((Object)v.variable.getType()) + " inside a matrix!");
                    }
                    numCols += v.getColumns();
                }
                matrixRow += expectedRows;
                row.clear();
                continue;
            }
            row.add(item);
        }
    }

    public VariableMatrix getOutput() {
        return this.output;
    }

    protected void setToRequiredSize(DMatrixRMaj matrix) {
        int matrixRow = 0;
        int matrixCol = 0;
        ArrayList<Item> row = new ArrayList<Item>();
        for (int i = 0; i < this.items.size(); ++i) {
            Item item = this.items.get(i);
            if (item.endRow) {
                Item v = (Item)row.get(0);
                int numRows = v.getRows();
                int numCols = v.getColumns();
                for (int j = 1; j < row.size(); ++j) {
                    v = (Item)row.get(j);
                    if (v.getRows() != numRows) {
                        throw new RuntimeException("Row miss-matched. " + numRows + " " + v.getRows());
                    }
                    numCols += v.getColumns();
                }
                matrixRow += numRows;
                if (matrixCol == 0) {
                    matrixCol = numCols;
                } else if (matrixCol != numCols) {
                    throw new ParseError("Row " + matrixRow + " has an unexpected number of columns; expected = " + matrixCol + " found = " + numCols);
                }
                row.clear();
                continue;
            }
            row.add(item);
        }
        matrix.reshape(matrixRow, matrixCol);
    }

    private static class Item {
        Variable variable;
        boolean endRow;
        boolean matrix;

        private Item(Variable variable) {
            this.variable = variable;
            this.matrix = variable instanceof VariableMatrix;
        }

        private Item() {
            this.endRow = true;
        }

        public int getRows() {
            if (this.matrix) {
                return ((VariableMatrix)this.variable).matrix.numRows;
            }
            return 1;
        }

        public int getColumns() {
            if (this.matrix) {
                return ((VariableMatrix)this.variable).matrix.numCols;
            }
            if (this.variable.getType() == VariableType.SCALAR) {
                return 1;
            }
            if (this.variable.getType() == VariableType.INTEGER_SEQUENCE) {
                return ((VariableIntegerSequence)this.variable).sequence.length();
            }
            throw new RuntimeException("BUG! Should have been caught earlier");
        }

        public DMatrixRMaj getMatrix() {
            return ((VariableMatrix)this.variable).matrix;
        }

        public double getValue() {
            return ((VariableScalar)this.variable).getDouble();
        }

        public void initialize() {
            if (this.variable != null && !this.matrix && this.variable.getType() == VariableType.INTEGER_SEQUENCE) {
                ((VariableIntegerSequence)this.variable).sequence.initialize(-1);
            }
        }
    }
}

