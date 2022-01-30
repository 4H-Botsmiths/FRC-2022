package frc.robot.hardware;

import edu.wpi.first.util.sendable.Sendable;
import edu.wpi.first.wpilibj.*;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Pneumatics extends PneumaticsControlModule {
    // public PneumaticsControlModule pcm = new PneumaticsControlModule();
    public Compressor2 compressor = makeCompressor2();
    public DoubleSolenoid[] pistons = new DoubleSolenoid[7];
    private int moduleIndex = -1;

    public Pneumatics() {
        super();
        int index = 0;
        for (int i = 0; i < pistons.length; i += 2) {
            pistons[index] = makeDoubleSolenoid(i, i + 1);
            index++;
        }
    }

    public Pneumatics(int module){
        super(module);
        int index = 0;
        for (int i = 0; i < pistons.length; i += 2) {
            pistons[index] = makeDoubleSolenoid(i, i + 1);
            index++;
        }
        moduleIndex = module;
    }

    public Compressor2 makeCompressor2() {
        if(moduleIndex == -1) {
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