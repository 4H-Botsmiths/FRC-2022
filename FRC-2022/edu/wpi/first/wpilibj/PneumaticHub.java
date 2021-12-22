/*
 * Decompiled with CFR 0.150.
 */
package edu.wpi.first.wpilibj;

import edu.wpi.first.hal.PortsJNI;
import edu.wpi.first.hal.REVPHFaults;
import edu.wpi.first.hal.REVPHJNI;
import edu.wpi.first.hal.REVPHStickyFaults;
import edu.wpi.first.hal.REVPHVersion;
import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.CompressorConfigType;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.PneumaticsBase;
import edu.wpi.first.wpilibj.PneumaticsModuleType;
import edu.wpi.first.wpilibj.SensorUtil;
import edu.wpi.first.wpilibj.Solenoid;
import java.util.HashMap;
import java.util.Map;

public class PneumaticHub
implements PneumaticsBase {
    private static final Map<Integer, DataStore> m_handleMap = new HashMap<Integer, DataStore>();
    private static final Object m_handleLock = new Object();
    private final DataStore m_dataStore;
    private final int m_handle;

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private static DataStore getForModule(int module) {
        Object object = m_handleLock;
        synchronized (object) {
            Integer moduleBoxed = module;
            DataStore pcm = m_handleMap.get(moduleBoxed);
            if (pcm == null) {
                pcm = new DataStore(module);
            }
            pcm.addRef();
            return pcm;
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private static void freeModule(DataStore store) {
        Object object = m_handleLock;
        synchronized (object) {
            store.removeRef();
        }
    }

    public PneumaticHub() {
        this(SensorUtil.getDefaultREVPHModule());
    }

    public PneumaticHub(int module) {
        this.m_dataStore = PneumaticHub.getForModule(module);
        this.m_handle = this.m_dataStore.m_handle;
    }

    @Override
    public void close() {
        PneumaticHub.freeModule(this.m_dataStore);
    }

    @Override
    public boolean getCompressor() {
        return REVPHJNI.getCompressor(this.m_handle);
    }

    @Override
    public CompressorConfigType getCompressorConfigType() {
        return CompressorConfigType.fromValue(REVPHJNI.getCompressorConfig(this.m_handle));
    }

    @Override
    public boolean getPressureSwitch() {
        return REVPHJNI.getPressureSwitch(this.m_handle);
    }

    @Override
    public double getCompressorCurrent() {
        return REVPHJNI.getCompressorCurrent(this.m_handle);
    }

    @Override
    public void setSolenoids(int mask, int values) {
        REVPHJNI.setSolenoids(this.m_handle, mask, values);
    }

    @Override
    public int getSolenoids() {
        return REVPHJNI.getSolenoids(this.m_handle);
    }

    @Override
    public int getModuleNumber() {
        return this.m_dataStore.m_module;
    }

    @Override
    public void fireOneShot(int index) {
        REVPHJNI.fireOneShot(this.m_handle, index, this.m_dataStore.m_oneShotDurMs[index]);
    }

    @Override
    public void setOneShotDuration(int index, int durMs) {
        this.m_dataStore.m_oneShotDurMs[index] = durMs;
    }

    @Override
    public boolean checkSolenoidChannel(int channel) {
        return REVPHJNI.checkSolenoidChannel(channel);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public int checkAndReserveSolenoids(int mask) {
        Object object = this.m_dataStore.m_reserveLock;
        synchronized (object) {
            if ((this.m_dataStore.m_reservedMask & mask) != 0) {
                return this.m_dataStore.m_reservedMask & mask;
            }
            this.m_dataStore.m_reservedMask |= mask;
            return 0;
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public void unreserveSolenoids(int mask) {
        Object object = this.m_dataStore.m_reserveLock;
        synchronized (object) {
            this.m_dataStore.m_reservedMask &= ~mask;
        }
    }

    @Override
    public Solenoid makeSolenoid(int channel) {
        return new Solenoid(this.m_dataStore.m_module, PneumaticsModuleType.REVPH, channel);
    }

    @Override
    public DoubleSolenoid makeDoubleSolenoid(int forwardChannel, int reverseChannel) {
        return new DoubleSolenoid(this.m_dataStore.m_module, PneumaticsModuleType.REVPH, forwardChannel, reverseChannel);
    }

    @Override
    public Compressor makeCompressor() {
        return new Compressor(this.m_dataStore.m_module, PneumaticsModuleType.REVPH);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public boolean reserveCompressor() {
        Object object = this.m_dataStore.m_reserveLock;
        synchronized (object) {
            if (this.m_dataStore.m_compressorReserved) {
                return false;
            }
            this.m_dataStore.m_compressorReserved = true;
            return true;
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public void unreserveCompressor() {
        Object object = this.m_dataStore.m_reserveLock;
        synchronized (object) {
            this.m_dataStore.m_compressorReserved = false;
        }
    }

    @Override
    public int getSolenoidDisabledList() {
        int raw = REVPHJNI.getStickyFaultsNative(this.m_handle);
        return raw & 0xFFFF;
    }

    @Override
    public void disableCompressor() {
        REVPHJNI.setClosedLoopControlDisabled(this.m_handle);
    }

    @Override
    public void enableCompressorDigital() {
        REVPHJNI.setClosedLoopControlDigital(this.m_handle);
    }

    @Override
    public void enableCompressorAnalog(double minAnalogVoltage, double maxAnalogVoltage) {
        REVPHJNI.setClosedLoopControlAnalog(this.m_handle, minAnalogVoltage, maxAnalogVoltage);
    }

    @Override
    public void enableCompressorHybrid(double minAnalogVoltage, double maxAnalogVoltage) {
        REVPHJNI.setClosedLoopControlHybrid(this.m_handle, minAnalogVoltage, maxAnalogVoltage);
    }

    @Override
    public double getAnalogVoltage(int channel) {
        return REVPHJNI.getAnalogVoltage(this.m_handle, channel);
    }

    void clearStickyFaults() {
        REVPHJNI.clearStickyFaults(this.m_handle);
    }

    REVPHVersion getVersion() {
        return REVPHJNI.getVersion(this.m_handle);
    }

    REVPHFaults getFaults() {
        return REVPHJNI.getFaults(this.m_handle);
    }

    REVPHStickyFaults getStickyFaults() {
        return REVPHJNI.getStickyFaults(this.m_handle);
    }

    double getInputVoltage() {
        return REVPHJNI.getInputVoltage(this.m_handle);
    }

    double get5VRegulatedVoltage() {
        return REVPHJNI.get5VVoltage(this.m_handle);
    }

    double getSolenoidsTotalCurrent() {
        return REVPHJNI.getSolenoidCurrent(this.m_handle);
    }

    double getSolenoidsVoltage() {
        return REVPHJNI.getSolenoidVoltage(this.m_handle);
    }

    private static class DataStore
    implements AutoCloseable {
        public final int m_module;
        public final int m_handle;
        private int m_refCount;
        private int m_reservedMask;
        private boolean m_compressorReserved;
        public int[] m_oneShotDurMs = new int[PortsJNI.getNumREVPHChannels()];
        private final Object m_reserveLock = new Object();

        DataStore(int module) {
            this.m_handle = REVPHJNI.initialize(module);
            this.m_module = module;
            m_handleMap.put(module, this);
        }

        @Override
        public void close() {
            REVPHJNI.free(this.m_handle);
            m_handleMap.remove(this.m_module);
        }

        public void addRef() {
            ++this.m_refCount;
        }

        public void removeRef() {
            --this.m_refCount;
            if (this.m_refCount == 0) {
                this.close();
            }
        }
    }
}

