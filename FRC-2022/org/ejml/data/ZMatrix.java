/*
 * Decompiled with CFR 0.150.
 */
package org.ejml.data;

import org.ejml.data.Complex_F64;
import org.ejml.data.Matrix;

public interface ZMatrix
extends Matrix {
    public void get(int var1, int var2, Complex_F64 var3);

    public void set(int var1, int var2, double var3, double var5);

    public double getReal(int var1, int var2);

    public void setReal(int var1, int var2, double var3);

    public double getImag(int var1, int var2);

    public void setImag(int var1, int var2, double var3);

    public int getDataLength();
}

