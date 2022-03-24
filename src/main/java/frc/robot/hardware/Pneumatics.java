package frc.robot.hardware;

import edu.wpi.first.util.sendable.Sendable;
import edu.wpi.first.util.sendable.SendableBuilder;
import edu.wpi.first.wpilibj.*;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Pneumatics extends PneumaticsControlModule implements Sendable {
    // public PneumaticsControlModule pcm = new PneumaticsControlModule();
    public Compressor2 compressor = makeCompressor2();
    public DoubleSolenoid[] pistons = new DoubleSolenoid[7];
    /** true for out, false for in */
    public boolean[] pistonValues = new boolean[7];
    private int moduleIndex = -1;
    public int climber = 2;
    public int intake = 0;

    public Pneumatics() {
        super();
        int index = 0;
        for (int i = 0; i < pistons.length; i += 2) {
            pistons[index] = makeDoubleSolenoid(i, i + 1);
            pistonValues[index] = false;
            index++;
        }
        SmartDashboard.putData("Pneumatics", this);
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
        SmartDashboard.putData("Pneumatics", this);
    }

    public void pistonOut(int index) {
        /*
         * if (pistonValues[index] != true) {
         * pistons[index].set(Value.kForward);
         * pistonValues[index] = true;
         * } else {
         * pistons[index].set(Value.kOff);
         * }
         */
        pistons[index].set(Value.kForward);
    }

    public void pistonIn(int index) {
        /*
         * if (pistonValues[index] != false) {
         * pistons[index].set(Value.kReverse);
         * pistonValues[index] = false;
         * } else {
         * pistons[index].set(Value.kOff);
         * }
         */
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

        public void enable(boolean enable){
            if(enable){
                enable();
            }else{
                disable();
            }
        }

        public void enable(double min, double max) {
            enableAnalog(min, max);
        }
    }

    @Override
    public void initSendable(SendableBuilder builder) {
        builder.setSmartDashboardType("Pneumatics");
        builder.addBooleanProperty("Air Compressor", compressor::enabled, compressor::enable);
    }
}