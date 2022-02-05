package frc.robot.hardware;

import edu.wpi.first.networktables.*;
import edu.wpi.first.util.sendable.Sendable;
import edu.wpi.first.util.sendable.SendableBuilder;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * this class contains all of the code and functions required for using a
 * limelight
 */
public class Limelight implements Sendable {
    public Limelight(){
        SmartDashboard.putData("Limelight", this);
    }
    /** Whether the limelight has any valid targets */
    public boolean targetVisible() {
        return NetworkTableInstance.getDefault().getTable("limelight").getEntry("tv").getDouble(0) == 1;
    }

    /**
     * Horizontal Offset From Crosshair To Target (-29.8 to 29.8 degrees)
     */
    public double getX() {
        return NetworkTableInstance.getDefault().getTable("limelight").getEntry("tx").getDouble(0);
    }

    /**
     * Vertical Offset From Crosshair To Target (-24.85 to 24.85 degrees)
     */
    public double getY() {
        return NetworkTableInstance.getDefault().getTable("limelight").getEntry("ty").getDouble(0);
    }

    /** Target Area (0% of image to 100% of image) */
    public double getArea() {
        return NetworkTableInstance.getDefault().getTable("limelight").getEntry("ta").getDouble(0);
    }

    /** Skew or rotation (-90 degrees to 0 degrees) */
    public double getRotation() {
        return NetworkTableInstance.getDefault().getTable("limelight").getEntry("ts").getDouble(0);
    }

    /**
     * The pipeline’s latency contribution (ms).
     */
    public double latency() {
        return NetworkTableInstance.getDefault().getTable("limelight").getEntry("tl").getDouble(0);
    }

    /**
     * The image capture latency (ms).
     */
    public double imageLatency() {
        return NetworkTableInstance.getDefault().getTable("limelight").getEntry("tl").getDouble(-11) + 11;
    }

    /** Sidelength of shortest side of the fitted bounding box (pixels) */
    public double getShortestSidelength() {
        return NetworkTableInstance.getDefault().getTable("limelight").getEntry("tshort").getDouble(0);
    }

    /** Sidelength of longest side of the fitted bounding box (pixels) */
    public double getLongestSidelength() {
        return NetworkTableInstance.getDefault().getTable("limelight").getEntry("tlong").getDouble(0);
    }

    /** Horizontal sidelength of the rough bounding box (0 - 320 pixels) */
    public double getWidth() {
        return NetworkTableInstance.getDefault().getTable("limelight").getEntry("thor").getDouble(0);
    }

    /** Vertical sidelength of the rough bounding box (0 - 320 pixels) */
    public double getHeight() {
        return NetworkTableInstance.getDefault().getTable("limelight").getEntry("tvert").getDouble(0);
    }

    /** True active pipeline index of the camera (0 .. 9) */
    public double getPipe() {
        return NetworkTableInstance.getDefault().getTable("limelight").getEntry("getpipe").getDouble(0);
    }

    /**
     * Results of a 3D position solution, 6 numbers: Translation (x,y,y)
     * Rotation(pitch,yaw,roll)
     */
    public Number[] get3D() {
        Number[] position = new Number[6];
        position[0] = 0;
        position[1] = 0;
        position[2] = 0;
        position[3] = 0;
        position[4] = 0;
        position[5] = 0;
        return NetworkTableInstance.getDefault().getTable("limelight").getEntry("camtran").getNumberArray(position);
    }

    public double[] get3DDouble() {
        return numberToDouble(get3D());
    }

    /**
     * Get the average HSV color underneath the crosshair region as a NumberArray
     */
    public Number[] getColor() {
        Number[] color = new Number[3];
        color[0] = 0;
        color[1] = 0;
        color[2] = 0;
        return NetworkTableInstance.getDefault().getTable("limelight").getEntry("tc").getNumberArray(color);
    }

    public double[] getColorDouble() {
        return numberToDouble(getColor());
    }

    /** Get value from network table */
    public double getRaw(String variable) {
        return NetworkTableInstance.getDefault().getTable("limelight").getEntry(variable).getDouble(0);
    }

    /** Set value in network table */
    public void setRaw(String variable, double value) {
        NetworkTableInstance.getDefault().getTable("limelight").getEntry(variable).setNumber(value);
    }

    /** Converts number array into double array */
    public double[] numberToDouble(Number[] numbers) {
        double[] doubles = new double[numbers.length];
        int i = 0;
        for (Number number : numbers) {
            doubles[i] = number.doubleValue();
            i++;
        }
        return doubles;
    }

    /**
     * Set Limelight's LED State
     * 
     * @param mode
     *             <p>
     *             0: normal,
     *             <p>
     *             1: force off,
     *             <p>
     *             2: force blink,
     *             <p>
     *             3: force on
     */
    public void setLEDMode(double mode) {
        NetworkTableInstance.getDefault().getTable("limelight").getEntry("ledMode").setNumber(mode);
    }
    public double getLEDMode(){
        return NetworkTableInstance.getDefault().getTable("limelight").getEntry("ledMode").getNumber(0).intValue();
    }

    /**
     * Sets Limelight's Operation Mode
     * 
     * @param mode
     *             <p>
     *             0: Vision Proccesing,
     *             <p>
     *             1: Driver Camera
     */
    public void setCamMode(double mode) {
        NetworkTableInstance.getDefault().getTable("limelight").getEntry("camMode").setNumber(mode);
    }
    public double getCamMode(){
        return NetworkTableInstance.getDefault().getTable("limelight").getEntry("camMode").getNumber(0).intValue();
    }

    /**
     * Sets Limelight's Current Pipeline
     * 
     * @param pipeline
     *                 <p>
     *                 Select Pipeline 0..9
     */
    public void setPipeline(double pipeline) {
        NetworkTableInstance.getDefault().getTable("limelight").getEntry("pipeline").setNumber(pipeline);
    }

    /**
     * Sets Limelight's Streaming Mode
     * 
     * @param mode
     *             <p>
     *             0: Standard (Side By Side),
     *             <p>
     *             1: PiP Main (Secoundary Stream In Lower Right Corner),
     *             <p>
     *             2: PiP Secoundary (Primary Stream In Lower Right Corner)
     */
    public void setStream(double mode) {
        NetworkTableInstance.getDefault().getTable("limelight").getEntry("stream").setNumber(mode);
    }
    public double getStream(){
        return NetworkTableInstance.getDefault().getTable("limelight").getEntry("stream").getNumber(0).intValue();
    }

    /**
     * Allows Users To Take Snapshots During A Match
     * 
     * @param mode
     *             <p>
     *             0: Stop Taking Snapshots
     *             <p>
     *             1: Take Two Snapshots Per Second
     */
    public void setSnapshot(double mode) {
        NetworkTableInstance.getDefault().getTable("limelight").getEntry("snapshot").setNumber(mode);
    }
    public double getSnapshot(){
        return NetworkTableInstance.getDefault().getTable("limelight").getEntry("snapshot").getNumber(0).intValue();
    }

    @Override
    public void initSendable(SendableBuilder builder) {
        builder.setSmartDashboardType("Limelight");

        builder.addDoubleProperty("LED Mode (0:normal, 1:off, 2:blink, 3:on)", this::getLEDMode, this::setLEDMode);
        builder.addDoubleProperty("Pipeline (0 to 9)", this::getPipe, this::setPipeline);
        builder.addDoubleProperty("Limelight Operation Mode (0:vision proccesing, 1:driver camera)", this::getCamMode, this::setCamMode);
        builder.addDoubleProperty("Limelight Stream Mode (0:Standard, 1:PiP Main, 2:PiP Secoundary)", this::getStream, this::setStream);
        builder.addDoubleProperty("Limelight Snapshot Mode (0:off, 1:twice/second)", this::getSnapshot, this::setSnapshot);

        builder.addBooleanProperty("Target Visible", this::targetVisible, null);
        builder.addDoubleProperty("Target x (-29.8 to 29.8 degrees)", this::getX, null);
        builder.addDoubleProperty("Target y (-24.85 to 24.85 degrees)", this::getY, null);
        builder.addDoubleProperty("Target Area (Percent)", this::getArea, null);
        builder.addDoubleProperty("Target Skew/Rotation (-90 to 0 degrees)", this::getRotation, null);
        builder.addDoubleProperty("Pipeline Latency (ms)", this::latency, null);
        builder.addDoubleProperty("Image Latency (ms)", this::imageLatency, null);
        builder.addDoubleProperty("Shortest Sidelength (pixels)", this::getShortestSidelength, null);
        builder.addDoubleProperty("Longest Sidelength (pixels)", this::getLongestSidelength, null);
        builder.addDoubleProperty("Horizontal Sidelength (0 to 320 pixels)", this::getWidth, null);
        builder.addDoubleProperty("Vertical Sidelength (0 to 320 pixels)", this::getHeight, null);
        builder.addDoubleArrayProperty("Avergae HSV Color", this::getColorDouble, null);
        builder.addDoubleArrayProperty("3D Position (Translation: (x,y,y) Rotation: (pitch,yaw,roll))",
                this::get3DDouble, null);
    }
}
