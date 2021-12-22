/*
 * Decompiled with CFR 0.150.
 */
package edu.wpi.first.math.system;

import edu.wpi.first.math.Matrix;
import edu.wpi.first.math.Num;
import edu.wpi.first.math.numbers.N1;
import java.util.function.BiFunction;
import java.util.function.DoubleFunction;
import java.util.function.Function;

public final class NumericalIntegration {
    private NumericalIntegration() {
    }

    public static double rk4(DoubleFunction<Double> f, double x, double dtSeconds) {
        double h = dtSeconds;
        Double k1 = f.apply(x);
        Double k2 = f.apply(x + h * k1 * 0.5);
        Double k3 = f.apply(x + h * k2 * 0.5);
        Double k4 = f.apply(x + h * k3);
        return x + h / 6.0 * (k1 + 2.0 * k2 + 2.0 * k3 + k4);
    }

    public static double rk4(BiFunction<Double, Double, Double> f, double x, Double u, double dtSeconds) {
        double h = dtSeconds;
        Double k1 = f.apply(x, u);
        Double k2 = f.apply(x + h * k1 * 0.5, u);
        Double k3 = f.apply(x + h * k2 * 0.5, u);
        Double k4 = f.apply(x + h * k3, u);
        return x + h / 6.0 * (k1 + 2.0 * k2 + 2.0 * k3 + k4);
    }

    public static <States extends Num, Inputs extends Num> Matrix<States, N1> rk4(BiFunction<Matrix<States, N1>, Matrix<Inputs, N1>, Matrix<States, N1>> f, Matrix<States, N1> x, Matrix<Inputs, N1> u, double dtSeconds) {
        double h = dtSeconds;
        Matrix<States, N1> k1 = f.apply(x, u);
        Matrix<States, N1> k2 = f.apply(x.plus(k1.times(h * 0.5)), u);
        Matrix<States, N1> k3 = f.apply(x.plus(k2.times(h * 0.5)), u);
        Matrix<States, N1> k4 = f.apply(x.plus(k3.times(h)), u);
        return x.plus(k1.plus(k2.times(2.0)).plus(k3.times(2.0)).plus(k4).times(h / 6.0));
    }

    public static <States extends Num> Matrix<States, N1> rk4(Function<Matrix<States, N1>, Matrix<States, N1>> f, Matrix<States, N1> x, double dtSeconds) {
        double h = dtSeconds;
        Matrix<States, N1> k1 = f.apply(x);
        Matrix<States, N1> k2 = f.apply(x.plus(k1.times(h * 0.5)));
        Matrix<States, N1> k3 = f.apply(x.plus(k2.times(h * 0.5)));
        Matrix<States, N1> k4 = f.apply(x.plus(k3.times(h)));
        return x.plus(k1.plus(k2.times(2.0)).plus(k3.times(2.0)).plus(k4).times(h / 6.0));
    }

    public static <States extends Num, Inputs extends Num> Matrix<States, N1> rkf45(BiFunction<Matrix<States, N1>, Matrix<Inputs, N1>, Matrix<States, N1>> f, Matrix<States, N1> x, Matrix<Inputs, N1> u, double dtSeconds) {
        return NumericalIntegration.rkf45(f, x, u, dtSeconds, 1.0E-6);
    }

    public static <States extends Num, Inputs extends Num> Matrix<States, N1> rkf45(BiFunction<Matrix<States, N1>, Matrix<Inputs, N1>, Matrix<States, N1>> f, Matrix<States, N1> x, Matrix<Inputs, N1> u, double dtSeconds, double maxError) {
        double[][] A = new double[][]{{0.25}, {0.09375, 0.28125}, {0.8793809740555303, -3.277196176604461, 3.3208921256258535}, {2.0324074074074074, -8.0, 7.173489278752436, -0.20589668615984405}, {-0.2962962962962963, 2.0, -1.3816764132553607, 0.4529727095516569, -0.275}};
        double[] b1 = new double[]{0.11851851851851852, 0.0, 0.5189863547758284, 0.5061314903420167, -0.18, 0.03636363636363636};
        double[] b2 = new double[]{0.11574074074074074, 0.0, 0.5489278752436647, 0.5353313840155945, -0.2, 0.0};
        double h = dtSeconds;
        for (double dtElapsed = 0.0; dtElapsed < dtSeconds; dtElapsed += h) {
            Matrix<States, N1> newX;
            double truncationError;
            do {
                h = Math.min(h, dtSeconds - dtElapsed);
                Matrix<States, N1> k1 = f.apply(x, u);
                Matrix<States, N1> k2 = f.apply(x.plus(k1.times(A[0][0]).times(h)), u);
                Matrix<States, N1> k3 = f.apply(x.plus(k1.times(A[1][0]).plus(k2.times(A[1][1])).times(h)), u);
                Matrix<States, N1> k4 = f.apply(x.plus(k1.times(A[2][0]).plus(k2.times(A[2][1])).plus(k3.times(A[2][2])).times(h)), u);
                Matrix<States, N1> k5 = f.apply(x.plus(k1.times(A[3][0]).plus(k2.times(A[3][1])).plus(k3.times(A[3][2])).plus(k4.times(A[3][3])).times(h)), u);
                Matrix<States, N1> k6 = f.apply(x.plus(k1.times(A[4][0]).plus(k2.times(A[4][1])).plus(k3.times(A[4][2])).plus(k4.times(A[4][3])).plus(k5.times(A[4][4])).times(h)), u);
                newX = x.plus(k1.times(b1[0]).plus(k2.times(b1[1])).plus(k3.times(b1[2])).plus(k4.times(b1[3])).plus(k5.times(b1[4])).plus(k6.times(b1[5])).times(h));
                truncationError = k1.times(b1[0] - b2[0]).plus(k2.times(b1[1] - b2[1])).plus(k3.times(b1[2] - b2[2])).plus(k4.times(b1[3] - b2[3])).plus(k5.times(b1[4] - b2[4])).plus(k6.times(b1[5] - b2[5])).times(h).normF();
                h *= 0.9 * Math.pow(maxError / truncationError, 0.2);
            } while (truncationError > maxError);
            x = newX;
        }
        return x;
    }

    public static <States extends Num, Inputs extends Num> Matrix<States, N1> rkdp(BiFunction<Matrix<States, N1>, Matrix<Inputs, N1>, Matrix<States, N1>> f, Matrix<States, N1> x, Matrix<Inputs, N1> u, double dtSeconds) {
        return NumericalIntegration.rkdp(f, x, u, dtSeconds, 1.0E-6);
    }

    public static <States extends Num, Inputs extends Num> Matrix<States, N1> rkdp(BiFunction<Matrix<States, N1>, Matrix<Inputs, N1>, Matrix<States, N1>> f, Matrix<States, N1> x, Matrix<Inputs, N1> u, double dtSeconds, double maxError) {
        double[][] A = new double[][]{{0.2}, {0.075, 0.225}, {0.9777777777777777, -3.7333333333333334, 3.5555555555555554}, {2.9525986892242035, -11.595793324188385, 9.822892851699436, -0.2908093278463649}, {2.8462752525252526, -10.757575757575758, 8.906422717743473, 0.2784090909090909, -0.2735313036020583}, {0.09114583333333333, 0.0, 0.44923629829290207, 0.6510416666666666, -0.322376179245283, 0.13095238095238096}};
        double[] b1 = new double[]{0.09114583333333333, 0.0, 0.44923629829290207, 0.6510416666666666, -0.322376179245283, 0.13095238095238096, 0.0};
        double[] b2 = new double[]{0.08991319444444444, 0.0, 0.4534890685834082, 0.6140625, -0.2715123820754717, 0.08904761904761904, 0.025};
        double h = dtSeconds;
        for (double dtElapsed = 0.0; dtElapsed < dtSeconds; dtElapsed += h) {
            Matrix<States, N1> newX;
            double truncationError;
            do {
                h = Math.min(h, dtSeconds - dtElapsed);
                Matrix<States, N1> k1 = f.apply(x, u);
                Matrix<States, N1> k2 = f.apply(x.plus(k1.times(A[0][0]).times(h)), u);
                Matrix<States, N1> k3 = f.apply(x.plus(k1.times(A[1][0]).plus(k2.times(A[1][1])).times(h)), u);
                Matrix<States, N1> k4 = f.apply(x.plus(k1.times(A[2][0]).plus(k2.times(A[2][1])).plus(k3.times(A[2][2])).times(h)), u);
                Matrix<States, N1> k5 = f.apply(x.plus(k1.times(A[3][0]).plus(k2.times(A[3][1])).plus(k3.times(A[3][2])).plus(k4.times(A[3][3])).times(h)), u);
                Matrix<States, N1> k6 = f.apply(x.plus(k1.times(A[4][0]).plus(k2.times(A[4][1])).plus(k3.times(A[4][2])).plus(k4.times(A[4][3])).plus(k5.times(A[4][4])).times(h)), u);
                newX = x.plus(k1.times(A[5][0]).plus(k2.times(A[5][1])).plus(k3.times(A[5][2])).plus(k4.times(A[5][3])).plus(k5.times(A[5][4])).plus(k6.times(A[5][5])).times(h));
                Matrix<States, N1> k7 = f.apply(newX, u);
                truncationError = k1.times(b1[0] - b2[0]).plus(k2.times(b1[1] - b2[1])).plus(k3.times(b1[2] - b2[2])).plus(k4.times(b1[3] - b2[3])).plus(k5.times(b1[4] - b2[4])).plus(k6.times(b1[5] - b2[5])).plus(k7.times(b1[6] - b2[6])).times(h).normF();
                h *= 0.9 * Math.pow(maxError / truncationError, 0.2);
            } while (truncationError > maxError);
            x = newX;
        }
        return x;
    }
}

