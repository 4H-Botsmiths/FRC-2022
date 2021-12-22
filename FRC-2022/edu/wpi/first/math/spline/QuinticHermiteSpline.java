/*
 * Decompiled with CFR 0.150.
 */
package edu.wpi.first.math.spline;

import edu.wpi.first.math.spline.Spline;
import org.ejml.simple.SimpleMatrix;

public class QuinticHermiteSpline
extends Spline {
    private static SimpleMatrix hermiteBasis;
    private final SimpleMatrix m_coefficients;

    public QuinticHermiteSpline(double[] xInitialControlVector, double[] xFinalControlVector, double[] yInitialControlVector, double[] yFinalControlVector) {
        super(5);
        int i;
        SimpleMatrix hermite = this.makeHermiteBasis();
        SimpleMatrix x = this.getControlVectorFromArrays(xInitialControlVector, xFinalControlVector);
        SimpleMatrix y = this.getControlVectorFromArrays(yInitialControlVector, yFinalControlVector);
        SimpleMatrix xCoeffs = (SimpleMatrix)hermite.mult(x).transpose();
        SimpleMatrix yCoeffs = (SimpleMatrix)hermite.mult(y).transpose();
        this.m_coefficients = new SimpleMatrix(6, 6);
        for (i = 0; i < 6; ++i) {
            this.m_coefficients.set(0, i, xCoeffs.get(0, i));
            this.m_coefficients.set(1, i, yCoeffs.get(0, i));
        }
        for (i = 0; i < 6; ++i) {
            this.m_coefficients.set(2, i, this.m_coefficients.get(0, i) * (double)(5 - i));
            this.m_coefficients.set(3, i, this.m_coefficients.get(1, i) * (double)(5 - i));
        }
        for (i = 0; i < 5; ++i) {
            this.m_coefficients.set(4, i, this.m_coefficients.get(2, i) * (double)(4 - i));
            this.m_coefficients.set(5, i, this.m_coefficients.get(3, i) * (double)(4 - i));
        }
    }

    @Override
    protected SimpleMatrix getCoefficients() {
        return this.m_coefficients;
    }

    private SimpleMatrix makeHermiteBasis() {
        if (hermiteBasis == null) {
            hermiteBasis = new SimpleMatrix(6, 6, true, new double[]{-6.0, -3.0, -0.5, 6.0, -3.0, 0.5, 15.0, 8.0, 1.5, -15.0, 7.0, -1.0, -10.0, -6.0, -1.5, 10.0, -4.0, 0.5, 0.0, 0.0, 0.5, 0.0, 0.0, 0.0, 0.0, 1.0, 0.0, 0.0, 0.0, 0.0, 1.0, 0.0, 0.0, 0.0, 0.0, 0.0});
        }
        return hermiteBasis;
    }

    private SimpleMatrix getControlVectorFromArrays(double[] initialVector, double[] finalVector) {
        if (initialVector.length != 3 || finalVector.length != 3) {
            throw new IllegalArgumentException("Size of vectors must be 3");
        }
        return new SimpleMatrix(6, 1, true, new double[]{initialVector[0], initialVector[1], initialVector[2], finalVector[0], finalVector[1], finalVector[2]});
    }
}

