/*
 * Decompiled with CFR 0.150.
 */
package edu.wpi.first.math.util;

public final class Units {
    private static final double kInchesPerFoot = 12.0;
    private static final double kMetersPerInch = 0.0254;
    private static final double kSecondsPerMinute = 60.0;
    private static final double kMillisecondsPerSecond = 1000.0;
    private static final double kKilogramsPerLb = 0.453592;

    private Units() {
        throw new UnsupportedOperationException("This is a utility class!");
    }

    public static double metersToFeet(double meters) {
        return Units.metersToInches(meters) / 12.0;
    }

    public static double feetToMeters(double feet) {
        return Units.inchesToMeters(feet * 12.0);
    }

    public static double metersToInches(double meters) {
        return meters / 0.0254;
    }

    public static double inchesToMeters(double inches) {
        return inches * 0.0254;
    }

    public static double degreesToRadians(double degrees) {
        return Math.toRadians(degrees);
    }

    public static double radiansToDegrees(double radians) {
        return Math.toDegrees(radians);
    }

    public static double rotationsPerMinuteToRadiansPerSecond(double rpm) {
        return rpm * Math.PI / 30.0;
    }

    public static double radiansPerSecondToRotationsPerMinute(double radiansPerSecond) {
        return radiansPerSecond * 30.0 / Math.PI;
    }

    public static double millisecondsToSeconds(double milliseconds) {
        return milliseconds / 1000.0;
    }

    public static double secondsToMilliseconds(double seconds) {
        return seconds * 1000.0;
    }

    public static double kilogramsToLbs(double kilograms) {
        return kilograms / 0.453592;
    }

    public static double lbsToKilograms(double lbs) {
        return lbs * 0.453592;
    }
}

