package frc.robot.hardware;

import edu.wpi.first.wpilibj.XboxController;

public class Gamepad2 extends XboxController {
  public Gamepad2() {
    super(1);
  }

  /**
   * Get the inverted Y axis value of left side of the controller.
   *
   * @return The axis value.
   */
  @Override
  public double getLeftY() {
    return -getRawAxis(Axis.kLeftY.value);
  }

  /**
   * Get the inverted Y axis value of right side of the controller.
   *
   * @return The axis value.
   */
  @Override
  public double getRightY() {
    return -getRawAxis(Axis.kRightY.value);
  }
}
