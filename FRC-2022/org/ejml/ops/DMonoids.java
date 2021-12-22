/*
 * Decompiled with CFR 0.150.
 */
package org.ejml.ops;

import org.ejml.ops.DMonoid;

public final class DMonoids {
    public static final DMonoid AND = new DMonoid(1.0, (a, b) -> a == 0.0 || b == 0.0 ? 0.0 : 1.0);
    public static final DMonoid OR = new DMonoid(0.0, (a, b) -> a != 0.0 || b != 0.0 ? 1.0 : 0.0);
    public static final DMonoid XOR = new DMonoid(0.0, (a, b) -> a == 0.0 && b == 0.0 || a != 0.0 && b != 0.0 ? 0.0 : 1.0);
    public static final DMonoid XNOR = new DMonoid(0.0, (a, b) -> a == 0.0 && b == 0.0 || a != 0.0 && b != 0.0 ? 1.0 : 0.0);
    public static final DMonoid PLUS = new DMonoid(0.0, Double::sum);
    public static final DMonoid TIMES = new DMonoid(1.0, (a, b) -> a * b);
    public static final DMonoid MIN = new DMonoid(Double.MAX_VALUE, (a, b) -> a <= b ? a : b);
    public static final DMonoid MAX = new DMonoid(-1.7976931348623157E308, (a, b) -> a >= b ? a : b);
}

