/*
 * Decompiled with CFR 0.150.
 */
package com.fasterxml.jackson.databind.deser.std;

import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.deser.std.AtomicBooleanDeserializer;
import com.fasterxml.jackson.databind.deser.std.AtomicIntegerDeserializer;
import com.fasterxml.jackson.databind.deser.std.AtomicLongDeserializer;
import com.fasterxml.jackson.databind.deser.std.ByteBufferDeserializer;
import com.fasterxml.jackson.databind.deser.std.FromStringDeserializer;
import com.fasterxml.jackson.databind.deser.std.NullifyingDeserializer;
import com.fasterxml.jackson.databind.deser.std.StackTraceElementDeserializer;
import com.fasterxml.jackson.databind.deser.std.UUIDDeserializer;
import java.nio.ByteBuffer;
import java.util.HashSet;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

public class JdkDeserializers {
    private static final HashSet<String> _classNames;

    public static JsonDeserializer<?> find(Class<?> rawType, String clsName) {
        if (_classNames.contains(clsName)) {
            FromStringDeserializer<?> d = FromStringDeserializer.findDeserializer(rawType);
            if (d != null) {
                return d;
            }
            if (rawType == UUID.class) {
                return new UUIDDeserializer();
            }
            if (rawType == StackTraceElement.class) {
                return new StackTraceElementDeserializer();
            }
            if (rawType == AtomicBoolean.class) {
                return new AtomicBooleanDeserializer();
            }
            if (rawType == AtomicInteger.class) {
                return new AtomicIntegerDeserializer();
            }
            if (rawType == AtomicLong.class) {
                return new AtomicLongDeserializer();
            }
            if (rawType == ByteBuffer.class) {
                return new ByteBufferDeserializer();
            }
            if (rawType == Void.class) {
                return NullifyingDeserializer.instance;
            }
        }
        return null;
    }

    public static boolean hasDeserializerFor(Class<?> rawType) {
        return _classNames.contains(rawType.getName());
    }

    static {
        Class[] types;
        _classNames = new HashSet();
        for (Class class_ : types = new Class[]{UUID.class, AtomicBoolean.class, AtomicInteger.class, AtomicLong.class, StackTraceElement.class, ByteBuffer.class, Void.class}) {
            _classNames.add(class_.getName());
        }
        for (Class class_ : FromStringDeserializer.types()) {
            _classNames.add(class_.getName());
        }
    }
}

