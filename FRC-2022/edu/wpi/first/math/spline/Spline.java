/*
 * Decompiled with CFR 0.150.
 */
package edu.wpi.first.math.spline;

import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.spline.PoseWithCurvature;
import java.util.Arrays;
import org.ejml.simple.SimpleMatrix;

public abstract class Spline {
    private final int m_degree;

    Spline(int degree) {
        this.m_degree = degree;
    }

    protected abstract SimpleMatrix getCoefficients();

    public PoseWithCurvature getPoint(double t) {
        double ddy;
        double ddx;
        double dy;
        double dx;
        SimpleMatrix polynomialBases = new SimpleMatrix(this.m_degree + 1, 1);
        SimpleMatrix coefficients = this.getCoefficients();
        for (int i = 0; i <= this.m_degree; ++i) {
            polynomialBases.set(i, 0, Math.pow(t, this.m_degree - i));
        }
        SimpleMatrix combined = coefficients.mult(polynomialBases);
        double x = combined.get(0, 0);
        double y = combined.get(1, 0);
        if (t == 0.0) {
            dx = coefficients.get(2, this.m_degree - 1);
            dy = coefficients.get(3, this.m_degree - 1);
            ddx = coefficients.get(4, this.m_degree - 2);
            ddy = coefficients.get(5, this.m_degree - 2);
        } else {
            dx = combined.get(2, 0) / t;
            dy = combined.get(3, 0) / t;
            ddx = combined.get(4, 0) / t / t;
            ddy = combined.get(5, 0) / t / t;
        }
        double curvature = (dx * ddy - ddx * dy) / ((dx * dx + dy * dy) * Math.hypot(dx, dy));
        return new PoseWithCurvature(new Pose2d(x, y, new Rotation2d(dx, dy)), curvature);
    }

    public static class ControlVector {
        public double[] x;
        public double[] y;

        public ControlVector(double[] x, double[] y) {
            this.x = Arrays.copyOf(x, x.length);
            this.y = Arrays.copyOf(y, y.length);
        }
    }
}

