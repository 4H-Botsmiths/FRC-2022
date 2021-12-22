/*
 * Decompiled with CFR 0.150.
 */
package edu.wpi.first.math.estimator;

import edu.wpi.first.math.Matrix;
import edu.wpi.first.math.Num;
import edu.wpi.first.math.numbers.N1;

interface KalmanTypeFilter<States extends Num, Inputs extends Num, Outputs extends Num> {
    public Matrix<States, States> getP();

    public double getP(int var1, int var2);

    public void setP(Matrix<States, States> var1);

    public Matrix<States, N1> getXhat();

    public double getXhat(int var1);

    public void setXhat(Matrix<States, N1> var1);

    public void setXhat(int var1, double var2);

    public void reset();

    public void predict(Matrix<Inputs, N1> var1, double var2);

    public void correct(Matrix<Inputs, N1> var1, Matrix<Outputs, N1> var2);
}

