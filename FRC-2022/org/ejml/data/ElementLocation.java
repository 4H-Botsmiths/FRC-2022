/*
 * Decompiled with CFR 0.150.
 */
package org.ejml.data;

public class ElementLocation {
    public int row;
    public int col;

    public ElementLocation() {
    }

    public ElementLocation(int row, int col) {
        this.row = row;
        this.col = col;
    }

    public void setTo(ElementLocation src) {
        this.row = src.row;
        this.col = src.col;
    }

    public void setTo(int row, int col) {
        this.row = row;
        this.col = col;
    }

    public int getRow() {
        return this.row;
    }

    public int getCol() {
        return this.col;
    }

    public void setRow(int row) {
        this.row = row;
    }

    public void setCol(int col) {
        this.col = col;
    }

    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof ElementLocation)) {
            return false;
        }
        ElementLocation other = (ElementLocation)o;
        if (!other.canEqual(this)) {
            return false;
        }
        if (this.getRow() != other.getRow()) {
            return false;
        }
        return this.getCol() == other.getCol();
    }

    protected boolean canEqual(Object other) {
        return other instanceof ElementLocation;
    }

    public int hashCode() {
        int PRIME = 59;
        int result = 1;
        result = result * 59 + this.getRow();
        result = result * 59 + this.getCol();
        return result;
    }

    public String toString() {
        return "ElementLocation(row=" + this.getRow() + ", col=" + this.getCol() + ")";
    }
}

