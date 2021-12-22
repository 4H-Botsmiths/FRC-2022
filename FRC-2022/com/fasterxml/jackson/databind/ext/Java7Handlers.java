/*
 * Decompiled with CFR 0.150.
 */
package com.fasterxml.jackson.databind.ext;

import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.util.ClassUtil;

public abstract class Java7Handlers {
    private static final Java7Handlers IMPL;

    public static Java7Handlers instance() {
        return IMPL;
    }

    public abstract Class<?> getClassJavaNioFilePath();

    public abstract JsonDeserializer<?> getDeserializerForJavaNioFilePath(Class<?> var1);

    public abstract JsonSerializer<?> getSerializerForJavaNioFilePath(Class<?> var1);

    static {
        Java7Handlers impl = null;
        try {
            Class<?> cls = Class.forName("com.fasterxml.jackson.databind.ext.Java7HandlersImpl");
            impl = (Java7Handlers)ClassUtil.createInstance(cls, false);
        }
        catch (Throwable throwable) {
            // empty catch block
        }
        IMPL = impl;
    }
}

