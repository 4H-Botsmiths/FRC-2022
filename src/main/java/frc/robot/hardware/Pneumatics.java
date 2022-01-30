package frc.robot.hardware;

import edu.wpi.first.wpilibj.*;

public class Pneumatics extends PneumaticsControlModule {
    // public PneumaticsControlModule pcm = new PneumaticsControlModule();
    public Compressor2 compressor = (Compressor2) makeCompressor();
    public DoubleSolenoid[] pistons = new DoubleSolenoid[7];

    public Pneumatics() {
        super();
        int index = 0;
        for (int i = 0; i < pistons.length; i += 2) {
            pistons[index] = makeDoubleSolenoid(i, i + 1);
            index++;
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