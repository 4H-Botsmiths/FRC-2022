/*
 * Decompiled with CFR 0.150.
 */
package edu.wpi.first.math.filter;

import edu.wpi.first.math.MathSharedStore;
import edu.wpi.first.math.MathUsageId;
import edu.wpi.first.util.CircularBuffer;
import java.util.Arrays;
import org.ejml.simple.SimpleMatrix;

public class LinearFilter {
    private final CircularBuffer m_inputs;
    private final CircularBuffer m_outputs;
    private final double[] m_inputGains;
    private final double[] m_outputGains;
    private static int instances;

    public LinearFilter(double[] ffGains, double[] fbGains) {
        this.m_inputs = new CircularBuffer(ffGains.length);
        this.m_outputs = new CircularBuffer(fbGains.length);
        this.m_inputGains = Arrays.copyOf(ffGains, ffGains.length);
        this.m_outputGains = Arrays.copyOf(fbGains, fbGains.length);
        MathSharedStore.reportUsage(MathUsageId.kFilter_Linear, ++instances);
    }

    public static LinearFilter singlePoleIIR(double timeConstant, double period) {
        double gain = Math.exp(-period / timeConstant);
        double[] ffGains = new double[]{1.0 - gain};
        double[] fbGains = new double[]{-gain};
        return new LinearFilter(ffGains, fbGains);
    }

    public static LinearFilter highPass(double timeConstant, double period) {
        double gain = Math.exp(-period / timeConstant);
        double[] ffGains = new double[]{gain, -gain};
        double[] fbGains = new double[]{-gain};
        return new LinearFilter(ffGains, fbGains);
    }

    public static LinearFilter movingAverage(int taps) {
        if (taps <= 0) {
            throw new IllegalArgumentException("Number of taps was not at least 1");
        }
        double[] ffGains = new double[taps];
        for (int i = 0; i < ffGains.length; ++i) {
            ffGains[i] = 1.0 / (double)taps;
        }
        double[] fbGains = new double[]{};
        return new LinearFilter(ffGains, fbGains);
    }

    public static LinearFilter backwardFiniteDifference(int derivative, int samples, double period) {
        if (derivative < 1) {
            throw new IllegalArgumentException("Order of derivative must be greater than or equal to one.");
        }
        if (samples <= 0) {
            throw new IllegalArgumentException("Number of samples must be greater than zero.");
        }
        if (derivative >= samples) {
            throw new IllegalArgumentException("Order of derivative must be less than number of samples.");
        }
        SimpleMatrix S = new SimpleMatrix(samples, samples);
        for (int row = 0; row < samples; ++row) {
            for (int col = 0; col < samples; ++col) {
                double s = 1 - samples + col;
                S.set(row, col, Math.pow(s, row));
            }
        }
        SimpleMatrix d = new SimpleMatrix(samples, 1);
        for (int i = 0; i < samples; ++i) {
            d.set(i, 0, i == derivative ? (double)LinearFilter.factorial(derivative) : 0.0);
        }
        SimpleMatrix a = (SimpleMatrix)S.solve(d).divide(Math.pow(period, derivative));
        double[] ffGains = new double[samples];
        for (int i = 0; i < samples; ++i) {
            ffGains[i] = a.get(samples - i - 1, 0);
        }
        double[] fbGains = new double[]{};
        return new LinearFilter(ffGains, fbGains);
    }

    public void reset() {
        this.m_inputs.clear();
        this.m_outputs.clear();
    }

    public double calculate(double input) {
        int i;
        double retVal = 0.0;
        if (this.m_inputGains.length > 0) {
            this.m_inputs.addFirst(input);
        }
        for (i = 0; i < this.m_inputGains.length; ++i) {
            retVal += this.m_inputs.get(i) * this.m_inputGains[i];
        }
        for (i = 0; i < this.m_outputGains.length; ++i) {
            retVal -= this.m_outputs.get(i) * this.m_outputGains[i];
        }
        if (this.m_outputGains.length > 0) {
            this.m_outputs.addFirst(retVal);
        }
        return retVal;
    }

    private static int factorial(int n) {
        if (n < 2) {
            return 1;
        }
        return n * LinearFilter.factorial(n - 1);
    }
}

