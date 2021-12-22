/*
 * Decompiled with CFR 0.150.
 */
package org.ejml.ops;

import org.ejml.ops.DOperatorBinary;

public class DMonoid {
    public final double id;
    public final DOperatorBinary func;

    public DMonoid(double id, DOperatorBinary func) {
        this.id = id;
        this.func = func;
    }

    DMonoid(DOperatorBinary func) {
        this(0.0, func);
    }
}

