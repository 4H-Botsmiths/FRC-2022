package frc.robot.hardware;

import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * this class contains all of the code and functions required for using a limelight
 */
public class Limelight {
    public NetworkTable table = NetworkTableInstance.getDefault().getTable("limelight");
    public NetworkTableEntry tv = table.getEntry("tv");
    public NetworkTableEntry tx = table.getEntry("tx");
    public NetworkTableEntry ty = table.getEntry("ty");
    public NetworkTableEntry ta = table.getEntry("ta");
    public NetworkTableEntry ts = table.getEntry("ts");
    public NetworkTableEntry tl = table.getEntry("tl");
    public NetworkTableEntry tshort = table.getEntry("tshort");
    public NetworkTableEntry tlong = table.getEntry("tlong");
    public NetworkTableEntry thor = table.getEntry("thor");
    public NetworkTableEntry tvert = table.getEntry("tvert");
    public NetworkTableEntry getpipe = table.getEntry("getpipe");
    public NetworkTableEntry camtran = table.getEntry("camtran");

    // =================================================================

    /** Whether the limelight has any valid targets (0 or 1) */
    public boolean v = tv.getDouble(0) == 1;
    /**
     * Horizontal Offset From Crosshair To Target (LL1: -27 degrees to 27 degrees |
     * LL2: -29.8 to 29.8 degrees)
     */
    public double x = tx.getDouble(0);
    /**
     * Vertical Offset From Crosshair To Target (LL1: -20.5 degrees to 20.5 degrees
     * | LL2: -24.85 to 24.85 degrees)
     */
    public double y = ty.getDouble(0);
    /**
     * Target Area (0% of image to 100% of image)
     */
    public double a = ta.getDouble(0);
    /**
     * Skew or rotation (-90 degrees to 0 degrees)
     * 
     */
    public double s = ts.getDouble(0);
    /**
     * The pipeline’s latency contribution (ms) Add at least 11ms for image capture
     * latency.
     */
    public double l = tl.getDouble(0);
    /**
     * Sidelength of shortest side of the fitted bounding box (pixels)
     */
    public double short_ = tshort.getDouble(0);
    /**
     * Sidelength of longest side of the fitted bounding box (pixels)
     */
    public double long_ = tlong.getDouble(0);
    /**
     * Horizontal sidelength of the rough bounding box (0 - 320 pixels)
     */
    public double hor = thor.getDouble(0);
    /**
     * Vertical sidelength of the rough bounding box (0 - 320 pixels)
     */
    public double vert = tvert.getDouble(0);
    /**
     * True active pipeline index of the camera (0 .. 9)
     */
    public double pipe = getpipe.getDouble(0);
    /**
     * Results of a 3D position solution, 6 numbers: Translation (x,y,y)
     * Rotation(pitch,yaw,roll)
     */
    public double tran = camtran.getDouble(0);

    // ----------------------------------------------------------------
    public void updateData() {
        v = tv.getDouble(0) == 1;
        x = tx.getDouble(0);
        y = ty.getDouble(0);
        a = ta.getDouble(0);
        s = ts.getDouble(0);
        l = tl.getDouble(0);
        short_ = tshort.getDouble(0);
        long_ = tlong.getDouble(0);
        hor = thor.getDouble(0);
        vert = tvert.getDouble(0);
        pipe = getpipe.getDouble(0);
        tran = camtran.getDouble(0);

        SmartDashboard.putBoolean("Limelight Targets", v);
        if (v) {
            SmartDashboard.putNumber("Limelight X", x);
            SmartDashboard.putNumber("Limelight Y", y);
            SmartDashboard.putNumber("Limlight Area", a);
            SmartDashboard.putNumber("Limelight Skew/Rotation", s);
            SmartDashboard.putNumber("Limelight Latency", l);
            SmartDashboard.putNumber("Limelight Shortest Sidelength", short_);
            SmartDashboard.putNumber("Limelight Longest Sidelength", long_);
            SmartDashboard.putNumber("Limelight Horizontal Sidelength", hor);
            SmartDashboard.putNumber("Limelight Vertical Sidelength", vert);
            SmartDashboard.putNumber("Limelight pipe index 0..9", pipe);
            SmartDashboard.putNumber("Limelight 3D position", tran);
        }
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
        table.getEntry("ledMode").setNumber(mode);
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
        table.getEntry("camMode").setNumber(mode);
    }

    /**
     * Sets Limelight's Current Pipeline
     * 
     * @param pipeline
     *                 <p>
     *                 Select Pipeline 0..9
     */
    public void pipeline(int pipeline) {
        table.getEntry("pipeline").setNumber(pipeline);
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
        table.getEntry("stream").setNumber(mode);
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
        table.getEntry("snapshot").setNumber(mode);
    }
}
