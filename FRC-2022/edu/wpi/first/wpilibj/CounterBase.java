/*
 * Decompiled with CFR 0.150.
 */
package edu.wpi.first.wpilibj;

public interface CounterBase {
    public int get();

    public void reset();

    public double getPeriod();

    public void setMaxPeriod(double var1);

    public boolean getStopped();

    public boolean getDirection();

    public static enum EncodingType {
        k1X(0),
        k2X(1),
        k4X(2);

        public final int value;

        private EncodingType(int value) {
            this.value = value;
        }
    }
}

