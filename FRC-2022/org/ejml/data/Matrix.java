/*
 * Decompiled with CFR 0.150.
 */
package org.ejml.data;

import java.io.Serializable;
import org.ejml.data.MatrixType;

public interface Matrix
extends Serializable {
    public int getNumRows();

    public int getNumCols();

    public void zero();

    public <T extends Matrix> T copy();

    public <T extends Matrix> T createLike();

    public <T extends Matrix> T create(int var1, int var2);

    public void setTo(Matrix var1);

    public void print();

    public void print(String var1);

    public MatrixType getType();
}

