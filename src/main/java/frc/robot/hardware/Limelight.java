package frc.robot.hardware;

import edu.wpi.first.networktables.*;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * this class contains all of the code and functions required for using a
 * limelight
 */
public class Limelight {
    /** Whether the limelight has any valid targets */
    public boolean tv() {
        return NetworkTableInstance.getDefault().getTable("limelight").getEntry("tv").getDouble(0) == 1;
    }

    /**
     * Horizontal Offset From Crosshair To Target (-29.8 to 29.8 degrees)
     */
    public double tx() {
        return NetworkTableInstance.getDefault().getTable("limelight").getEntry("tx").getDouble(0);
    }

    /**
     * Vertical Offset From Crosshair To Target (-24.85 to 24.85 degrees)
     */
    public double ty() {
        return NetworkTableInstance.getDefault().getTable("limelight").getEntry("ty").getDouble(0);
    }

    /** Target Area (0% of image to 100% of image) */
    public double ta() {
        return NetworkTableInstance.getDefault().getTable("limelight").getEntry("ta").getDouble(0);
    }

    /** Skew or rotation (-90 degrees to 0 degrees) */
    public double ts() {
        return NetworkTableInstance.getDefault().getTable("limelight").getEntry("ts").getDouble(0);
    }

    /**
     * The pipeline’s latency contribution (ms).
     */
    public double tl() {
        return NetworkTableInstance.getDefault().getTable("limelight").getEntry("tl").getDouble(0);
    }

    /**
     * The image capture latency (ms).
     */
    public double til() {
        return NetworkTableInstance.getDefault().getTable("limelight").getEntry("tl").getDouble(-11) + 11;
    }

    /** Sidelength of shortest side of the fitted bounding box (pixels) */
    public double tshort() {
        return NetworkTableInstance.getDefault().getTable("limelight").getEntry("tshort").getDouble(0);
    }

    /** Sidelength of longest side of the fitted bounding box (pixels) */
    public double tlong() {
        return NetworkTableInstance.getDefault().getTable("limelight").getEntry("tlong").getDouble(0);
    }

    /** Horizontal sidelength of the rough bounding box (0 - 320 pixels) */
    public double thor() {
        return NetworkTableInstance.getDefault().getTable("limelight").getEntry("thor").getDouble(0);
    }

    /** Vertical sidelength of the rough bounding box (0 - 320 pixels) */
    public double tvert() {
        return NetworkTableInstance.getDefault().getTable("limelight").getEntry("tvert").getDouble(0);
    }

    /** True active pipeline index of the camera (0 .. 9) */
    public double getpipe() {
        return NetworkTableInstance.getDefault().getTable("limelight").getEntry("getpipe").getDouble(0);
    }

    /**
     * Results of a 3D position solution, 6 numbers: Translation (x,y,y)
     * Rotation(pitch,yaw,roll)
     */
    public Number[] camtran() {
        Number[] position = new Number[6];
        return NetworkTableInstance.getDefault().getTable("limelight").getEntry("camtran").getNumberArray(position);
    }

    /**
     * Get the average HSV color underneath the crosshair region as a NumberArray
     */
    public Number[] tc() {
        Number[] defaultNum = new Number[3];
        return NetworkTableInstance.getDefault().getTable("limelight").getEntry("tc").getNumberArray(defaultNum);
    }

    /** Get value from network table */
    public double getRaw(String variable) {
        return NetworkTableInstance.getDefault().getTable("limelight").getEntry(variable).getDouble(0);
    }

    /** Set value in network table */
    public void setRaw(String variable, double value) {
        NetworkTableInstance.getDefault().getTable("limelight").getEntry(variable).setNumber(value);
    }

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
    public void ledMode(int mode) {
        NetworkTableInstance.getDefault().getTable("limelight").getEntry("ledMode").setNumber(mode);
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
    public void camMode(int mode) {
        NetworkTableInstance.getDefault().getTable("limelight").getEntry("camMode").setNumber(mode);
    }

    /**
     * Sets Limelight's Current Pipeline
     * 
     * @param pipeline
     *                 <p>
     *                 Select Pipeline 0..9
     */
    public void pipeline(int pipeline) {
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
    public void stream(int mode) {
        NetworkTableInstance.getDefault().getTable("limelight").getEntry("stream").setNumber(mode);
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
    public void snapshot(int mode) {
        NetworkTableInstance.getDefault().getTable("limelight").getEntry("snapshot").setNumber(mode);
    }

    /** Updates SmartDashboard */
    public void updateSmartDashboard() {
        SmartDashboard.putBoolean("Target Visible", tv());
        if (tv()) {
            SmartDashboard.putNumber("Target x (-29.8 to 29.8 degrees)", tx());
            SmartDashboard.putNumber("Target y (-24.85 to 24.85 degrees)", ty());
            SmartDashboard.putNumber("Target Area (percent)", ta());
            SmartDashboard.putNumber("Target Skew/Rotation (-90 to 0 degrees)", ts());
            SmartDashboard.putNumber("Pipeline Latency (ms)", tl());
            SmartDashboard.putNumber("Image Latency (ms)", til());
            SmartDashboard.putNumber("Shortest Sidelength (pixels)", tshort());
            SmartDashboard.putNumber("Longest Sidelength (pixels)", tlong());
            SmartDashboard.putNumber("Horizontal Sidelength (0 to 320 pixels)", thor());
            SmartDashboard.putNumber("Vertical Sidelength (0 to 320 pixels)", tvert());
            SmartDashboard.putNumber("Pipeline (0 to 9)", getpipe());
            /*SmartDashboard.putNumberArray("3D Position (Translation: (x,y,y) Rotation: (pitch,yaw,roll))",
                    numberToDouble(camtran()));
                    */
            //SmartDashboard.putNumberArray("Avergae HSV Color (h, s, v)", numberToDouble(tc()));
        }
    }
}
