/*
 * Decompiled with CFR 0.150.
 */
package org.ejml.ops;

import org.ejml.ops.DMonoid;

public class DSemiRing {
    public final DMonoid add;
    public final DMonoid mult;

    public DSemiRing(DMonoid add, DMonoid mult) {
        this.add = add;
        this.mult = mult;
    }
}

