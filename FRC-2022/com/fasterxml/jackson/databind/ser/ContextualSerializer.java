/*
 * Decompiled with CFR 0.150.
 */
package com.fasterxml.jackson.databind.ser;

import com.fasterxml.jackson.databind.BeanProperty;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

public interface ContextualSerializer {
    public JsonSerializer<?> createContextual(SerializerProvider var1, BeanProperty var2) throws JsonMappingException;
}

