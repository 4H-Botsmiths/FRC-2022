/*
 * Decompiled with CFR 0.150.
 */
package org.ejml.ops;

import org.ejml.ops.FOperatorBinary;

public class FMonoid {
    public final float id;
    public final FOperatorBinary func;

    public FMonoid(float id, FOperatorBinary func) {
        this.id = id;
        this.func = func;
    }

    FMonoid(FOperatorBinary func) {
        this(0.0f, func);
    }
}

