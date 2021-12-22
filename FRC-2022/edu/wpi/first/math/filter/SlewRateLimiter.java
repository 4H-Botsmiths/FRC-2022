/*
 * Decompiled with CFR 0.150.
 */
package edu.wpi.first.math.filter;

import edu.wpi.first.math.MathUtil;
import edu.wpi.first.util.WPIUtilJNI;

public class SlewRateLimiter {
    private final double m_rateLimit;
    private double m_prevVal;
    private double m_prevTime;

    public SlewRateLimiter(double rateLimit, double initialValue) {
        this.m_rateLimit = rateLimit;
        this.m_prevVal = initialValue;
        this.m_prevTime = (double)WPIUtilJNI.now() * 1.0E-6;
    }

    public SlewRateLimiter(double rateLimit) {
        this(rateLimit, 0.0);
    }

    public double calculate(double input) {
        double currentTime = (double)WPIUtilJNI.now() * 1.0E-6;
        double elapsedTime = currentTime - this.m_prevTime;
        this.m_prevVal += MathUtil.clamp(input - this.m_prevVal, -this.m_rateLimit * elapsedTime, this.m_rateLimit * elapsedTime);
        this.m_prevTime = currentTime;
        return this.m_prevVal;
    }

    public void reset(double value) {
        this.m_prevVal = value;
        this.m_prevTime = (double)WPIUtilJNI.now() * 1.0E-6;
    }
}

