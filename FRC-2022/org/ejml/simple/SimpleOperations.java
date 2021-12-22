/*
 * Decompiled with CFR 0.150.
 */
package org.ejml.simple;

import java.io.PrintStream;
import java.io.Serializable;
import org.ejml.data.Complex_F64;
import org.ejml.data.Matrix;

public interface SimpleOperations<T extends Matrix>
extends Serializable {
    public void set(T var1, int var2, int var3, double var4);

    public void set(T var1, int var2, int var3, double var4, double var6);

    public double get(T var1, int var2, int var3);

    public void get(T var1, int var2, int var3, Complex_F64 var4);

    public void fill(T var1, double var2);

    public void transpose(T var1, T var2);

    public void mult(T var1, T var2, T var3);

    public void multTransA(T var1, T var2, T var3);

    public void kron(T var1, T var2, T var3);

    public void plus(T var1, T var2, T var3);

    public void minus(T var1, T var2, T var3);

    public void minus(T var1, double var2, T var4);

    public void plus(T var1, double var2, T var4);

    public void plus(T var1, double var2, T var4, T var5);

    public void plus(double var1, T var3, double var4, T var6, T var7);

    public double dot(T var1, T var2);

    public void scale(T var1, double var2, T var4);

    public void divide(T var1, double var2, T var4);

    public boolean invert(T var1, T var2);

    public void setIdentity(T var1);

    public void pseudoInverse(T var1, T var2);

    public boolean solve(T var1, T var2, T var3);

    public void zero(T var1);

    public double normF(T var1);

    public double conditionP2(T var1);

    public double determinant(T var1);

    public double trace(T var1);

    public void setRow(T var1, int var2, int var3, double ... var4);

    public void setColumn(T var1, int var2, int var3, double ... var4);

    public void extract(T var1, int var2, int var3, int var4, int var5, T var6, int var7, int var8);

    public T diag(T var1);

    public boolean hasUncountable(T var1);

    public void changeSign(T var1);

    public double elementMaxAbs(T var1);

    public double elementMinAbs(T var1);

    public double elementSum(T var1);

    public void elementMult(T var1, T var2, T var3);

    public void elementDiv(T var1, T var2, T var3);

    public void elementPower(T var1, T var2, T var3);

    public void elementPower(T var1, double var2, T var4);

    public void elementExp(T var1, T var2);

    public void elementLog(T var1, T var2);

    public boolean isIdentical(T var1, T var2, double var3);

    public void print(PrintStream var1, Matrix var2, String var3);
}

