/*
 * Decompiled with CFR 0.150.
 */
package edu.wpi.first.math.system;

import edu.wpi.first.math.Matrix;
import edu.wpi.first.math.Num;
import edu.wpi.first.math.StateSpaceUtil;
import edu.wpi.first.math.controller.LinearPlantInversionFeedforward;
import edu.wpi.first.math.controller.LinearQuadraticRegulator;
import edu.wpi.first.math.estimator.KalmanFilter;
import edu.wpi.first.math.numbers.N1;
import edu.wpi.first.math.system.LinearSystem;
import java.util.function.Function;
import org.ejml.MatrixDimensionException;
import org.ejml.simple.SimpleMatrix;

public class LinearSystemLoop<States extends Num, Inputs extends Num, Outputs extends Num> {
    private final LinearQuadraticRegulator<States, Inputs, Outputs> m_controller;
    private final LinearPlantInversionFeedforward<States, Inputs, Outputs> m_feedforward;
    private final KalmanFilter<States, Inputs, Outputs> m_observer;
    private Matrix<States, N1> m_nextR;
    private Function<Matrix<Inputs, N1>, Matrix<Inputs, N1>> m_clampFunction;

    public LinearSystemLoop(LinearSystem<States, Inputs, Outputs> plant, LinearQuadraticRegulator<States, Inputs, Outputs> controller, KalmanFilter<States, Inputs, Outputs> observer, double maxVoltageVolts, double dtSeconds) {
        this(controller, new LinearPlantInversionFeedforward<States, Inputs, Outputs>(plant, dtSeconds), observer, u -> StateSpaceUtil.normalizeInputVector(u, maxVoltageVolts));
    }

    public LinearSystemLoop(LinearSystem<States, Inputs, Outputs> plant, LinearQuadraticRegulator<States, Inputs, Outputs> controller, KalmanFilter<States, Inputs, Outputs> observer, Function<Matrix<Inputs, N1>, Matrix<Inputs, N1>> clampFunction, double dtSeconds) {
        this(controller, new LinearPlantInversionFeedforward<States, Inputs, Outputs>(plant, dtSeconds), observer, clampFunction);
    }

    public LinearSystemLoop(LinearQuadraticRegulator<States, Inputs, Outputs> controller, LinearPlantInversionFeedforward<States, Inputs, Outputs> feedforward, KalmanFilter<States, Inputs, Outputs> observer, double maxVoltageVolts) {
        this(controller, feedforward, observer, u -> StateSpaceUtil.normalizeInputVector(u, maxVoltageVolts));
    }

    public LinearSystemLoop(LinearQuadraticRegulator<States, Inputs, Outputs> controller, LinearPlantInversionFeedforward<States, Inputs, Outputs> feedforward, KalmanFilter<States, Inputs, Outputs> observer, Function<Matrix<Inputs, N1>, Matrix<Inputs, N1>> clampFunction) {
        this.m_controller = controller;
        this.m_feedforward = feedforward;
        this.m_observer = observer;
        this.m_clampFunction = clampFunction;
        this.m_nextR = new Matrix(new SimpleMatrix(controller.getK().getNumCols(), 1));
        this.reset(this.m_nextR);
    }

    public Matrix<States, N1> getXHat() {
        return this.getObserver().getXhat();
    }

    public double getXHat(int row) {
        return this.getObserver().getXhat(row);
    }

    public void setXHat(Matrix<States, N1> xhat) {
        this.getObserver().setXhat(xhat);
    }

    public void setXHat(int row, double value) {
        this.getObserver().setXhat(row, value);
    }

    public double getNextR(int row) {
        return this.getNextR().get(row, 0);
    }

    public Matrix<States, N1> getNextR() {
        return this.m_nextR;
    }

    public void setNextR(Matrix<States, N1> nextR) {
        this.m_nextR = nextR;
    }

    public void setNextR(double ... nextR) {
        if (nextR.length != this.m_nextR.getNumRows()) {
            throw new MatrixDimensionException(String.format("The next reference does not have the correct number of entries! Expected %s, but got %s.", this.m_nextR.getNumRows(), nextR.length));
        }
        this.m_nextR = new Matrix(new SimpleMatrix(this.m_nextR.getNumRows(), 1, true, nextR));
    }

    public Matrix<Inputs, N1> getU() {
        return this.clampInput(this.m_controller.getU().plus(this.m_feedforward.getUff()));
    }

    public double getU(int row) {
        return this.getU().get(row, 0);
    }

    public LinearQuadraticRegulator<States, Inputs, Outputs> getController() {
        return this.m_controller;
    }

    public LinearPlantInversionFeedforward<States, Inputs, Outputs> getFeedforward() {
        return this.m_feedforward;
    }

    public KalmanFilter<States, Inputs, Outputs> getObserver() {
        return this.m_observer;
    }

    public void reset(Matrix<States, N1> initialState) {
        this.m_nextR.fill(0.0);
        this.m_controller.reset();
        this.m_feedforward.reset(initialState);
        this.m_observer.setXhat(initialState);
    }

    public Matrix<States, N1> getError() {
        return this.getController().getR().minus(this.m_observer.getXhat());
    }

    public double getError(int index) {
        return this.getController().getR().minus(this.m_observer.getXhat()).get(index, 0);
    }

    public Function<Matrix<Inputs, N1>, Matrix<Inputs, N1>> getClampFunction() {
        return this.m_clampFunction;
    }

    public void setClampFunction(Function<Matrix<Inputs, N1>, Matrix<Inputs, N1>> clampFunction) {
        this.m_clampFunction = clampFunction;
    }

    public void correct(Matrix<Outputs, N1> y) {
        this.getObserver().correct(this.getU(), y);
    }

    public void predict(double dtSeconds) {
        Matrix<Inputs, N1> u = this.clampInput(this.m_controller.calculate(this.getObserver().getXhat(), this.m_nextR).plus(this.m_feedforward.calculate(this.m_nextR)));
        this.getObserver().predict(u, dtSeconds);
    }

    public Matrix<Inputs, N1> clampInput(Matrix<Inputs, N1> unclampedU) {
        return this.m_clampFunction.apply(unclampedU);
    }
}

