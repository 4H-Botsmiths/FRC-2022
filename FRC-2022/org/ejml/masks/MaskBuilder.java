/*
 * Decompiled with CFR 0.150.
 */
package org.ejml.masks;

import org.ejml.masks.Mask;

public abstract class MaskBuilder<MASK extends Mask> {
    protected boolean negated = false;

    public MaskBuilder<MASK> withNegated(boolean negated) {
        this.negated = negated;
        return this;
    }

    public abstract MASK build();
}

