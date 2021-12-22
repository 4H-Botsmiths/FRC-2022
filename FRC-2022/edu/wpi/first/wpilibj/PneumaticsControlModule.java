/*
 * Decompiled with CFR 0.150.
 */
package edu.wpi.first.wpilibj;

import edu.wpi.first.hal.CTREPCMJNI;
import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.CompressorConfigType;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.PneumaticsBase;
import edu.wpi.first.wpilibj.PneumaticsModuleType;
import edu.wpi.first.wpilibj.SensorUtil;
import edu.wpi.first.wpilibj.Solenoid;
import java.util.HashMap;
import java.util.Map;

public class PneumaticsControlModule
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

    public PneumaticsControlModule() {
        this(SensorUtil.getDefaultCTREPCMModule());
    }

    public PneumaticsControlModule(int module) {
        this.m_dataStore = PneumaticsControlModule.getForModule(module);
        this.m_handle = this.m_dataStore.m_handle;
    }

    @Override
    public void close() {
        PneumaticsControlModule.freeModule(this.m_dataStore);
    }

    @Override
    public boolean getCompressor() {
        return CTREPCMJNI.getCompressor(this.m_handle);
    }

    @Override
    public boolean getPressureSwitch() {
        return CTREPCMJNI.getPressureSwitch(this.m_handle);
    }

    @Override
    public double getCompressorCurrent() {
        return CTREPCMJNI.getCompressorCurrent(this.m_handle);
    }

    public boolean getCompressorCurrentTooHighFault() {
        return CTREPCMJNI.getCompressorCurrentTooHighFault(this.m_handle);
    }

    public boolean getCompressorCurrentTooHighStickyFault() {
        return CTREPCMJNI.getCompressorCurrentTooHighStickyFault(this.m_handle);
    }

    public boolean getCompressorShortedFault() {
        return CTREPCMJNI.getCompressorShortedFault(this.m_handle);
    }

    public boolean getCompressorShortedStickyFault() {
        return CTREPCMJNI.getCompressorShortedStickyFault(this.m_handle);
    }

    public boolean getCompressorNotConnectedFault() {
        return CTREPCMJNI.getCompressorNotConnectedFault(this.m_handle);
    }

    public boolean getCompressorNotConnectedStickyFault() {
        return CTREPCMJNI.getCompressorNotConnectedStickyFault(this.m_handle);
    }

    @Override
    public void setSolenoids(int mask, int values) {
        CTREPCMJNI.setSolenoids(this.m_handle, mask, values);
    }

    @Override
    public int getSolenoids() {
        return CTREPCMJNI.getSolenoids(this.m_handle);
    }

    @Override
    public int getModuleNumber() {
        return this.m_dataStore.m_module;
    }

    @Override
    public int getSolenoidDisabledList() {
        return CTREPCMJNI.getSolenoidDisabledList(this.m_handle);
    }

    public boolean getSolenoidVoltageFault() {
        return CTREPCMJNI.getSolenoidVoltageFault(this.m_handle);
    }

    public boolean getSolenoidVoltageStickyFault() {
        return CTREPCMJNI.getSolenoidVoltageStickyFault(this.m_handle);
    }

    public void clearAllStickyFaults() {
        CTREPCMJNI.clearAllStickyFaults(this.m_handle);
    }

    @Override
    public void fireOneShot(int index) {
        CTREPCMJNI.fireOneShot(this.m_handle, index);
    }

    @Override
    public void setOneShotDuration(int index, int durMs) {
        CTREPCMJNI.setOneShotDuration(this.m_handle, index, durMs);
    }

    @Override
    public boolean checkSolenoidChannel(int channel) {
        return CTREPCMJNI.checkSolenoidChannel(channel);
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
        return new Solenoid(this.m_dataStore.m_module, PneumaticsModuleType.CTREPCM, channel);
    }

    @Override
    public DoubleSolenoid makeDoubleSolenoid(int forwardChannel, int reverseChannel) {
        return new DoubleSolenoid(this.m_dataStore.m_module, PneumaticsModuleType.CTREPCM, forwardChannel, reverseChannel);
    }

    @Override
    public Compressor makeCompressor() {
        return new Compressor(this.m_dataStore.m_module, PneumaticsModuleType.CTREPCM);
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
    public void disableCompressor() {
        CTREPCMJNI.setClosedLoopControl(this.m_handle, false);
    }

    @Override
    public void enableCompressorDigital() {
        CTREPCMJNI.setClosedLoopControl(this.m_handle, true);
    }

    @Override
    public void enableCompressorAnalog(double minAnalogVoltage, double maxAnalogVoltage) {
        CTREPCMJNI.setClosedLoopControl(this.m_handle, false);
    }

    @Override
    public void enableCompressorHybrid(double minAnalogVoltage, double maxAnalogVoltage) {
        CTREPCMJNI.setClosedLoopControl(this.m_handle, false);
    }

    @Override
    public CompressorConfigType getCompressorConfigType() {
        return CTREPCMJNI.getClosedLoopControl(this.m_handle) ? CompressorConfigType.Digital : CompressorConfigType.Disabled;
    }

    @Override
    public double getAnalogVoltage(int channel) {
        return 0.0;
    }

    private static class DataStore
    implements AutoCloseable {
        public final int m_module;
        public final int m_handle;
        private int m_refCount;
        private int m_reservedMask;
        private boolean m_compressorReserved;
        private final Object m_reserveLock = new Object();

        DataStore(int module) {
            this.m_handle = CTREPCMJNI.initialize(module);
            this.m_module = module;
            m_handleMap.put(module, this);
        }

        @Override
        public void close() {
            CTREPCMJNI.free(this.m_handle);
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

