package frc.robot.hardware;

import edu.wpi.first.wpilibj.XboxController;

/**
 * Use this for reading inputs from Xbox controller 0
 */
public class Gamepad1 extends XboxController {
  /**
   * Use this for reading inputs from Xbox controller 0
   */
  public Gamepad1() {
    super(0);
  }

  public RumbleType LeftRumble = RumbleType.kLeftRumble;
  public RumbleType RightRumble = RumbleType.kRightRumble;

  public void setRumble(double left, double right) {
    setRumble(LeftRumble, left < 0 ? 0 : left > 1 ? 1 : left);
    setRumble(RightRumble, right < 0 ? 0 : right > 1 ? 1 : right);
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
