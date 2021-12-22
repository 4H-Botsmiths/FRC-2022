/*
 * Decompiled with CFR 0.150.
 */
package edu.wpi.first.hal.util;

public class BoundaryException
extends RuntimeException {
    public BoundaryException(String message) {
        super(message);
    }

    public static void assertWithinBounds(double value, double lower, double upper) {
        if (value < lower || value > upper) {
            throw new BoundaryException("Value must be between " + lower + " and " + upper + ", " + value + " given");
        }
    }

    public static String getMessage(double value, double lower, double upper) {
        return "Value must be between " + lower + " and " + upper + ", " + value + " given";
    }
}

