/*
 * Decompiled with CFR 0.150.
 */
package com.fasterxml.jackson.databind.util;

public interface LookupCache<K, V> {
    public int size();

    public V get(Object var1);

    public V put(K var1, V var2);

    public V putIfAbsent(K var1, V var2);

    public void clear();
}

