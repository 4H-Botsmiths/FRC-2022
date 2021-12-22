/*
 * Decompiled with CFR 0.150.
 */
package edu.wpi.first.math;

import edu.wpi.first.math.MathShared;
import edu.wpi.first.math.MathUsageId;

public final class MathSharedStore {
    private static MathShared mathShared;

    private MathSharedStore() {
    }

    public static synchronized MathShared getMathShared() {
        if (mathShared == null) {
            mathShared = new MathShared(){

                @Override
                public void reportError(String error, StackTraceElement[] stackTrace) {
                }

                @Override
                public void reportUsage(MathUsageId id, int count) {
                }
            };
        }
        return mathShared;
    }

    public static synchronized void setMathShared(MathShared shared) {
        mathShared = shared;
    }

    public static void reportError(String error, StackTraceElement[] stackTrace) {
        MathSharedStore.getMathShared().reportError(error, stackTrace);
    }

    public static void reportUsage(MathUsageId id, int count) {
        MathSharedStore.getMathShared().reportUsage(id, count);
    }
}

