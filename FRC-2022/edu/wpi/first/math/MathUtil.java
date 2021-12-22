/*
 * Decompiled with CFR 0.150.
 */
package edu.wpi.first.math;

public final class MathUtil {
    private MathUtil() {
        throw new AssertionError((Object)"utility class");
    }

    public static int clamp(int value, int low, int high) {
        return Math.max(low, Math.min(value, high));
    }

    public static double clamp(double value, double low, double high) {
        return Math.max(low, Math.min(value, high));
    }

    public static double applyDeadband(double value, double deadband) {
        if (Math.abs(value) > deadband) {
            if (value > 0.0) {
                return (value - deadband) / (1.0 - deadband);
            }
            return (value + deadband) / (1.0 - deadband);
        }
        return 0.0;
    }

    public static double inputModulus(double input, double minimumInput, double maximumInput) {
        double modulus = maximumInput - minimumInput;
        int numMax = (int)((input - minimumInput) / modulus);
        int numMin = (int)(((input -= (double)numMax * modulus) - maximumInput) / modulus);
        return input -= (double)numMin * modulus;
    }

    public static double angleModulus(double angleRadians) {
        return MathUtil.inputModulus(angleRadians, -Math.PI, Math.PI);
    }
}

