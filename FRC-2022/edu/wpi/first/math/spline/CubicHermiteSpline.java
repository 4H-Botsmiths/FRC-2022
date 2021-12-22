/*
 * Decompiled with CFR 0.150.
 */
package edu.wpi.first.math.spline;

import edu.wpi.first.math.spline.Spline;
import org.ejml.simple.SimpleMatrix;

public class CubicHermiteSpline
extends Spline {
    private static SimpleMatrix hermiteBasis;
    private final SimpleMatrix m_coefficients;

    public CubicHermiteSpline(double[] xInitialControlVector, double[] xFinalControlVector, double[] yInitialControlVector, double[] yFinalControlVector) {
        super(3);
        int i;
        SimpleMatrix hermite = this.makeHermiteBasis();
        SimpleMatrix x = this.getControlVectorFromArrays(xInitialControlVector, xFinalControlVector);
        SimpleMatrix y = this.getControlVectorFromArrays(yInitialControlVector, yFinalControlVector);
        SimpleMatrix xCoeffs = (SimpleMatrix)hermite.mult(x).transpose();
        SimpleMatrix yCoeffs = (SimpleMatrix)hermite.mult(y).transpose();
        this.m_coefficients = new SimpleMatrix(6, 4);
        for (i = 0; i < 4; ++i) {
            this.m_coefficients.set(0, i, xCoeffs.get(0, i));
            this.m_coefficients.set(1, i, yCoeffs.get(0, i));
            this.m_coefficients.set(2, i, this.m_coefficients.get(0, i) * (double)(3 - i));
            this.m_coefficients.set(3, i, this.m_coefficients.get(1, i) * (double)(3 - i));
        }
        for (i = 0; i < 3; ++i) {
            this.m_coefficients.set(4, i, this.m_coefficients.get(2, i) * (double)(2 - i));
            this.m_coefficients.set(5, i, this.m_coefficients.get(3, i) * (double)(2 - i));
        }
    }

    @Override
    protected SimpleMatrix getCoefficients() {
        return this.m_coefficients;
    }

    private SimpleMatrix makeHermiteBasis() {
        if (hermiteBasis == null) {
            hermiteBasis = new SimpleMatrix(4, 4, true, new double[]{2.0, 1.0, -2.0, 1.0, -3.0, -2.0, 3.0, -1.0, 0.0, 1.0, 0.0, 0.0, 1.0, 0.0, 0.0, 0.0});
        }
        return hermiteBasis;
    }

    private SimpleMatrix getControlVectorFromArrays(double[] initialVector, double[] finalVector) {
        if (initialVector.length != 2 || finalVector.length != 2) {
            throw new IllegalArgumentException("Size of vectors must be 2");
        }
        return new SimpleMatrix(4, 1, true, new double[]{initialVector[0], initialVector[1], finalVector[0], finalVector[1]});
    }
}

