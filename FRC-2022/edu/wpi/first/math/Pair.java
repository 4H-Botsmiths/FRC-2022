/*
 * Decompiled with CFR 0.150.
 */
package edu.wpi.first.math;

public class Pair<A, B> {
    private final A m_first;
    private final B m_second;

    public Pair(A first, B second) {
        this.m_first = first;
        this.m_second = second;
    }

    public A getFirst() {
        return this.m_first;
    }

    public B getSecond() {
        return this.m_second;
    }

    public static <A, B> Pair<A, B> of(A a, B b) {
        return new Pair<A, B>(a, b);
    }
}

