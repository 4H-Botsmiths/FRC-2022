package frc.robot.hardware;

import edu.wpi.first.wpilibj.*;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;

public class Pneumatics extends PneumaticsControlModule {
    // public PneumaticsControlModule pcm = new PneumaticsControlModule();
    public Compressor2 compressor = makeCompressor2();
    public DoubleSolenoid[] pistons = new DoubleSolenoid[7];
    /** true for out, false for in */
    public boolean[] pistonValues = new boolean[7];
    private int moduleIndex = -1;
    public int climber = 0;
    public int intake = 2;

    public Pneumatics() {
        super();
        int index = 0;
        for (int i = 0; i < pistons.length; i += 2) {
            pistons[index] = makeDoubleSolenoid(i, i + 1);
            pistonValues[index] = false;
            index++;
        }
    }

    public Pneumatics(int module) {
        super(module);
        int index = 0;
        for (int i = 0; i < pistons.length; i += 2) {
            pistons[index] = makeDoubleSolenoid(i, i + 1);
            pistonValues[index] = false;
            index++;
        }
        moduleIndex = module;
    }

    public void pistonOut(int index) {
        /*if (pistonValues[index] != true) {
            pistons[index].set(Value.kForward);
            pistonValues[index] = true;
        } else {
            pistons[index].set(Value.kOff);
        }*/
        pistons[index].set(Value.kForward);
    }

    public void pistonIn(int index) {
        /*if (pistonValues[index] != false) {
            pistons[index].set(Value.kReverse);
            pistonValues[index] = false;
        } else {
            pistons[index].set(Value.kOff);
        }*/
        pistons[index].set(Value.kReverse);
    }

    public void pistonOff(int index) {
        pistons[index].set(Value.kOff);
    }

    public Value togglePiston(int index) {
        pistons[index].toggle();
        switch (pistons[index].get()) {
            case kForward:
                return Value.kReverse;
            case kReverse:
                return Value.kForward;
            default:
                return Value.kOff;
        }
    }

    public void clearStickyFaults() {
        try {
            clearAllStickyFaults();
        } catch (edu.wpi.first.hal.util.HalHandleException error) {
            System.out.println("Failed to clear sticky faults: " + error);
        }
    }

    public Compressor2 makeCompressor2() {
        if (moduleIndex == -1) {
            return new Compressor2(PneumaticsModuleType.CTREPCM);
        } else {
            return new Compressor2(moduleIndex, PneumaticsModuleType.CTREPCM);
        }
    }

    public class Compressor2 extends Compressor {
        public Compressor2(int module, PneumaticsModuleType moduleType) {
            super(module, moduleType);
        }

        public Compressor2(PneumaticsModuleType moduleType) {
            super(PneumaticsBase.getDefaultForType(moduleType), moduleType);
        }

        public void enable() {
            enableDigital();
        }

        public void enable(double min, double max) {
            enableAnalog(min, max);
        }
    }
}