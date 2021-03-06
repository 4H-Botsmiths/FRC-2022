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

  /**
   * Rumble the gamepad
   * @param left the amount to vibrate the left vibrater (0 to 1)
   * @param right the amount to vibrate the right vibrater (0 to 1)
   */
  public void setRumble(double left, double right) {
    setRumble(RumbleType.kLeftRumble, left < 0 ? 0 : left > 1 ? 1 : left);
    setRumble(RumbleType.kRightRumble, right < 0 ? 0 : right > 1 ? 1 : right);
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

    /**
   * Read the value of the guide button on the controller.
   *
   * @return The state of the button.
   */
  /*public boolean getGuideButton() {
    return getRawButton(0);
  }

  /**
   * Whether the guide button was pressed since the last check.
   *
   * @return Whether the button was pressed since the last check.
   */
  /*public boolean getGuideButtonPressed() {
    return getRawButtonPressed(0);
  }

  /**
   * Whether the guide button was released since the last check.
   *
   * @return Whether the button was released since the last check.
   */
  /*public boolean getGuideButtonReleased() {
    return getRawButtonReleased(0);
  }*/
}
