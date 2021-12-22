/*
 * Decompiled with CFR 0.150.
 */
package org.ejml.data;

import org.ejml.data.Complex_F32;
import org.ejml.data.Matrix;

public interface CMatrix
extends Matrix {
    public void get(int var1, int var2, Complex_F32 var3);

    public void set(int var1, int var2, float var3, float var4);

    public float getReal(int var1, int var2);

    public void setReal(int var1, int var2, float var3);

    public float getImag(int var1, int var2);

    public void setImag(int var1, int var2, float var3);

    public int getDataLength();
}

