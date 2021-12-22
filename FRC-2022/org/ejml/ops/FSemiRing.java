/*
 * Decompiled with CFR 0.150.
 */
package org.ejml.ops;

import org.ejml.ops.FMonoid;

public class FSemiRing {
    public final FMonoid add;
    public final FMonoid mult;

    public FSemiRing(FMonoid add, FMonoid mult) {
        this.add = add;
        this.mult = mult;
    }
}

