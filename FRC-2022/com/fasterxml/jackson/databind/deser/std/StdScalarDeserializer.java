/*
 * Decompiled with CFR 0.150.
 */
package com.fasterxml.jackson.databind.deser.std;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationConfig;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import com.fasterxml.jackson.databind.jsontype.TypeDeserializer;
import com.fasterxml.jackson.databind.type.LogicalType;
import com.fasterxml.jackson.databind.util.AccessPattern;
import java.io.IOException;

public abstract class StdScalarDeserializer<T>
extends StdDeserializer<T> {
    private static final long serialVersionUID = 1L;

    protected StdScalarDeserializer(Class<?> vc) {
        super(vc);
    }

    protected StdScalarDeserializer(JavaType valueType) {
        super(valueType);
    }

    protected StdScalarDeserializer(StdScalarDeserializer<?> src) {
        super(src);
    }

    @Override
    public LogicalType logicalType() {
        return LogicalType.OtherScalar;
    }

    @Override
    public Boolean supportsUpdate(DeserializationConfig config) {
        return Boolean.FALSE;
    }

    @Override
    public AccessPattern getNullAccessPattern() {
        return AccessPattern.ALWAYS_NULL;
    }

    @Override
    public AccessPattern getEmptyAccessPattern() {
        return AccessPattern.CONSTANT;
    }

    @Override
    public Object deserializeWithType(JsonParser p, DeserializationContext ctxt, TypeDeserializer typeDeserializer) throws IOException {
        return typeDeserializer.deserializeTypedFromScalar(p, ctxt);
    }

    @Override
    public T deserialize(JsonParser p, DeserializationContext ctxt, T intoValue) throws IOException {
        ctxt.handleBadMerge(this);
        return this.deserialize(p, ctxt);
    }
}

