/*
 * Decompiled with CFR 0.150.
 */
package edu.wpi.first.wpilibj.simulation;

import edu.wpi.first.math.Matrix;
import edu.wpi.first.math.Num;
import edu.wpi.first.math.StateSpaceUtil;
import edu.wpi.first.math.numbers.N1;
import edu.wpi.first.math.system.LinearSystem;
import edu.wpi.first.wpilibj.RobotController;
import org.ejml.MatrixDimensionException;
import org.ejml.simple.SimpleMatrix;

public class LinearSystemSim<States extends Num, Inputs extends Num, Outputs extends Num> {
    protected final LinearSystem<States, Inputs, Outputs> m_plant;
    protected Matrix<States, N1> m_x;
    protected Matrix<Outputs, N1> m_y;
    protected Matrix<Inputs, N1> m_u;
    protected final Matrix<Outputs, N1> m_measurementStdDevs;

    public LinearSystemSim(LinearSystem<States, Inputs, Outputs> system) {
        this(system, null);
    }

    public LinearSystemSim(LinearSystem<States, Inputs, Outputs> system, Matrix<Outputs, N1> measurementStdDevs) {
        this.m_plant = system;
        this.m_measurementStdDevs = measurementStdDevs;
        this.m_x = new Matrix(new SimpleMatrix(system.getA().getNumRows(), 1));
        this.m_u = new Matrix(new SimpleMatrix(system.getB().getNumCols(), 1));
        this.m_y = new Matrix(new SimpleMatrix(system.getC().getNumRows(), 1));
    }

    public void update(double dtSeconds) {
        this.m_x = this.updateX(this.m_x, this.m_u, dtSeconds);
        this.m_y = this.m_plant.calculateY(this.m_x, this.m_u);
        if (this.m_measurementStdDevs != null) {
            this.m_y = this.m_y.plus(StateSpaceUtil.makeWhiteNoiseVector(this.m_measurementStdDevs));
        }
    }

    public Matrix<Outputs, N1> getOutput() {
        return this.m_y;
    }

    public double getOutput(int row) {
        return this.m_y.get(row, 0);
    }

    public void setInput(Matrix<Inputs, N1> u) {
        this.m_u = this.clampInput(u);
    }

    public void setInput(int row, double value) {
        this.m_u.set(row, 0, value);
        this.m_u = this.clampInput(this.m_u);
    }

    public void setInput(double ... u) {
        if (u.length != this.m_u.getNumRows()) {
            throw new MatrixDimensionException("Malformed input! Got " + u.length + " elements instead of " + this.m_u.getNumRows());
        }
        this.m_u = new Matrix(new SimpleMatrix(this.m_u.getNumRows(), 1, true, u));
    }

    public void setState(Matrix<States, N1> state) {
        this.m_x = state;
    }

    public double getCurrentDrawAmps() {
        return 0.0;
    }

    protected Matrix<States, N1> updateX(Matrix<States, N1> currentXhat, Matrix<Inputs, N1> u, double dtSeconds) {
        return this.m_plant.calculateX(currentXhat, u, dtSeconds);
    }

    protected Matrix<Inputs, N1> clampInput(Matrix<Inputs, N1> u) {
        return StateSpaceUtil.normalizeInputVector(u, RobotController.getBatteryVoltage());
    }
}

