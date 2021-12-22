/*
 * Decompiled with CFR 0.150.
 */
package org.ejml.ops;

import org.ejml.ops.FMonoid;
import org.ejml.ops.FMonoids;
import org.ejml.ops.FSemiRing;

public final class FSemiRings {
    public static final FSemiRing PLUS_TIMES = new FSemiRing(FMonoids.PLUS, FMonoids.TIMES);
    public static final FSemiRing MIN_PLUS = new FSemiRing(FMonoids.MIN, FMonoids.PLUS);
    public static final FSemiRing MAX_PLUS = new FSemiRing(FMonoids.MAX, FMonoids.PLUS);
    public static final FSemiRing MIN_TIMES = new FSemiRing(FMonoids.MIN, FMonoids.TIMES);
    public static final FSemiRing MIN_MAX = new FSemiRing(FMonoids.MIN, FMonoids.MAX);
    public static final FSemiRing MAX_MIN = new FSemiRing(FMonoids.MAX, FMonoids.MIN);
    public static final FSemiRing MAX_TIMES = new FSemiRing(FMonoids.MAX, FMonoids.TIMES);
    public static final FSemiRing PLUS_MIN = new FSemiRing(FMonoids.PLUS, FMonoids.MIN);
    public static final FSemiRing OR_AND = new FSemiRing(FMonoids.OR, FMonoids.AND);
    public static final FSemiRing AND_OR = new FSemiRing(FMonoids.AND, FMonoids.OR);
    public static final FSemiRing XOR_AND = new FSemiRing(FMonoids.XOR, FMonoids.AND);
    public static final FSemiRing XNOR_OR = new FSemiRing(FMonoids.XNOR, FMonoids.OR);
    private static final FMonoid FIRST = new FMonoid(Float.NaN, (x, y) -> x);
    private static final FMonoid SECOND = new FMonoid(Float.NaN, (x, y) -> y);
    public static final FSemiRing MIN_FIRST = new FSemiRing(FMonoids.MIN, FIRST);
    public static final FSemiRing MIN_SECOND = new FSemiRing(FMonoids.MIN, SECOND);
    public static final FSemiRing MAX_FIRST = new FSemiRing(FMonoids.MAX, FIRST);
    public static final FSemiRing MAX_SECOND = new FSemiRing(FMonoids.MAX, SECOND);
}

