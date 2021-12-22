/*
 * Decompiled with CFR 0.150.
 */
package org.ejml.ops;

import org.ejml.ops.DMonoid;
import org.ejml.ops.DMonoids;
import org.ejml.ops.DSemiRing;

public final class DSemiRings {
    public static final DSemiRing PLUS_TIMES = new DSemiRing(DMonoids.PLUS, DMonoids.TIMES);
    public static final DSemiRing MIN_PLUS = new DSemiRing(DMonoids.MIN, DMonoids.PLUS);
    public static final DSemiRing MAX_PLUS = new DSemiRing(DMonoids.MAX, DMonoids.PLUS);
    public static final DSemiRing MIN_TIMES = new DSemiRing(DMonoids.MIN, DMonoids.TIMES);
    public static final DSemiRing MIN_MAX = new DSemiRing(DMonoids.MIN, DMonoids.MAX);
    public static final DSemiRing MAX_MIN = new DSemiRing(DMonoids.MAX, DMonoids.MIN);
    public static final DSemiRing MAX_TIMES = new DSemiRing(DMonoids.MAX, DMonoids.TIMES);
    public static final DSemiRing PLUS_MIN = new DSemiRing(DMonoids.PLUS, DMonoids.MIN);
    public static final DSemiRing OR_AND = new DSemiRing(DMonoids.OR, DMonoids.AND);
    public static final DSemiRing AND_OR = new DSemiRing(DMonoids.AND, DMonoids.OR);
    public static final DSemiRing XOR_AND = new DSemiRing(DMonoids.XOR, DMonoids.AND);
    public static final DSemiRing XNOR_OR = new DSemiRing(DMonoids.XNOR, DMonoids.OR);
    private static final DMonoid FIRST = new DMonoid(Double.NaN, (x, y) -> x);
    private static final DMonoid SECOND = new DMonoid(Double.NaN, (x, y) -> y);
    public static final DSemiRing MIN_FIRST = new DSemiRing(DMonoids.MIN, FIRST);
    public static final DSemiRing MIN_SECOND = new DSemiRing(DMonoids.MIN, SECOND);
    public static final DSemiRing MAX_FIRST = new DSemiRing(DMonoids.MAX, FIRST);
    public static final DSemiRing MAX_SECOND = new DSemiRing(DMonoids.MAX, SECOND);
}

