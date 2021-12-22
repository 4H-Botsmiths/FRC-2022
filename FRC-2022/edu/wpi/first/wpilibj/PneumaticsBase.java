/*
 * Decompiled with CFR 0.150.
 */
package edu.wpi.first.wpilibj;

import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.CompressorConfigType;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.PneumaticHub;
import edu.wpi.first.wpilibj.PneumaticsControlModule;
import edu.wpi.first.wpilibj.PneumaticsModuleType;
import edu.wpi.first.wpilibj.SensorUtil;
import edu.wpi.first.wpilibj.Solenoid;

public interface PneumaticsBase
extends AutoCloseable {
    public static PneumaticsBase getForType(int module, PneumaticsModuleType type) {
        if (type == PneumaticsModuleType.CTREPCM) {
            return new PneumaticsControlModule(module);
        }
        if (type == PneumaticsModuleType.REVPH) {
            return new PneumaticHub(module);
        }
        throw new IllegalArgumentException("Unknown module type");
    }

    public static int getDefaultForType(PneumaticsModuleType type) {
        if (type == PneumaticsModuleType.CTREPCM) {
            return SensorUtil.getDefaultCTREPCMModule();
        }
        if (type == PneumaticsModuleType.REVPH) {
            return SensorUtil.getDefaultREVPHModule();
        }
        throw new IllegalArgumentException("Unknown module type");
    }

    public void setSolenoids(int var1, int var2);

    public int getSolenoids();

    public int getModuleNumber();

    public int getSolenoidDisabledList();

    public void fireOneShot(int var1);

    public void setOneShotDuration(int var1, int var2);

    public boolean getCompressor();

    public boolean getPressureSwitch();

    public double getCompressorCurrent();

    public void disableCompressor();

    public void enableCompressorDigital();

    public void enableCompressorAnalog(double var1, double var3);

    public void enableCompressorHybrid(double var1, double var3);

    public double getAnalogVoltage(int var1);

    public CompressorConfigType getCompressorConfigType();

    public boolean checkSolenoidChannel(int var1);

    public int checkAndReserveSolenoids(int var1);

    public void unreserveSolenoids(int var1);

    public boolean reserveCompressor();

    public void unreserveCompressor();

    @Override
    public void close();

    public Solenoid makeSolenoid(int var1);

    public DoubleSolenoid makeDoubleSolenoid(int var1, int var2);

    public Compressor makeCompressor();
}

