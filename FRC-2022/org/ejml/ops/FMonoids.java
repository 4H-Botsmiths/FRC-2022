/*
 * Decompiled with CFR 0.150.
 */
package org.ejml.ops;

import org.ejml.ops.FMonoid;

public final class FMonoids {
    public static final FMonoid AND = new FMonoid(1.0f, (a, b) -> a == 0.0f || b == 0.0f ? 0.0f : 1.0f);
    public static final FMonoid OR = new FMonoid(0.0f, (a, b) -> a != 0.0f || b != 0.0f ? 1.0f : 0.0f);
    public static final FMonoid XOR = new FMonoid(0.0f, (a, b) -> a == 0.0f && b == 0.0f || a != 0.0f && b != 0.0f ? 0.0f : 1.0f);
    public static final FMonoid XNOR = new FMonoid(0.0f, (a, b) -> a == 0.0f && b == 0.0f || a != 0.0f && b != 0.0f ? 1.0f : 0.0f);
    public static final FMonoid PLUS = new FMonoid(0.0f, Float::sum);
    public static final FMonoid TIMES = new FMonoid(1.0f, (a, b) -> a * b);
    public static final FMonoid MIN = new FMonoid(Float.MAX_VALUE, (a, b) -> a <= b ? a : b);
    public static final FMonoid MAX = new FMonoid(-3.4028235E38f, (a, b) -> a >= b ? a : b);
}

