/*
 * Decompiled with CFR 0.150.
 */
package edu.wpi.first.wpilibj;

import edu.wpi.first.wpilibj.RobotBase;
import java.io.File;

public final class Filesystem {
    private Filesystem() {
    }

    public static File getLaunchDirectory() {
        return new File(System.getProperty("user.dir")).getAbsoluteFile();
    }

    public static File getOperatingDirectory() {
        if (RobotBase.isReal()) {
            return new File("/home/lvuser");
        }
        return Filesystem.getLaunchDirectory();
    }

    public static File getDeployDirectory() {
        if (RobotBase.isReal()) {
            return new File(Filesystem.getOperatingDirectory(), "deploy");
        }
        return new File(Filesystem.getOperatingDirectory(), "src" + File.separator + "main" + File.separator + "deploy");
    }
}

