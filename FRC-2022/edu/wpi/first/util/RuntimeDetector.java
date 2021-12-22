/*
 * Decompiled with CFR 0.150.
 */
package edu.wpi.first.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public final class RuntimeDetector {
    private static String filePrefix;
    private static String fileExtension;
    private static String filePath;

    private static synchronized void computePlatform() {
        if (fileExtension != null && filePath != null && filePrefix != null) {
            return;
        }
        boolean intel32 = RuntimeDetector.is32BitIntel();
        boolean intel64 = RuntimeDetector.is64BitIntel();
        if (RuntimeDetector.isWindows()) {
            filePrefix = "";
            fileExtension = ".dll";
            filePath = intel32 ? "/windows/x86/" : "/windows/x86-64/";
        } else if (RuntimeDetector.isMac()) {
            filePrefix = "lib";
            fileExtension = ".dylib";
            filePath = intel32 ? "/osx/x86" : "/osx/x86-64/";
        } else if (RuntimeDetector.isLinux()) {
            filePrefix = "lib";
            fileExtension = ".so";
            filePath = intel32 ? "/linux/x86/" : (intel64 ? "/linux/x86-64/" : (RuntimeDetector.isAthena() ? "/linux/athena/" : (RuntimeDetector.isRaspbian() ? "/linux/raspbian/" : (RuntimeDetector.isAarch64() ? "/linux/aarch64bionic/" : "/linux/nativearm/"))));
        } else {
            throw new IllegalStateException("Failed to determine OS");
        }
    }

    public static synchronized String getFilePrefix() {
        RuntimeDetector.computePlatform();
        return filePrefix;
    }

    public static synchronized String getFileExtension() {
        RuntimeDetector.computePlatform();
        return fileExtension;
    }

    public static synchronized String getPlatformPath() {
        RuntimeDetector.computePlatform();
        return filePath;
    }

    public static synchronized String getLibraryResource(String libName) {
        RuntimeDetector.computePlatform();
        return filePath + filePrefix + libName + fileExtension;
    }

    public static synchronized String getHashLibraryResource(String libName) {
        RuntimeDetector.computePlatform();
        return filePath + libName + ".hash";
    }

    public static boolean isAthena() {
        File runRobotFile = new File("/usr/local/frc/bin/frcRunRobot.sh");
        return runRobotFile.exists();
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public static boolean isRaspbian() {
        try (BufferedReader reader = Files.newBufferedReader(Paths.get("/etc/os-release", new String[0]));){
            String value = reader.readLine();
            if (value == null) {
                boolean bl2 = false;
                return bl2;
            }
            boolean bl = value.contains("Raspbian");
            return bl;
        }
        catch (IOException ex) {
            return false;
        }
    }

    public static boolean isAarch64() {
        return "aarch64".equals(System.getProperty("os.arch"));
    }

    public static boolean isLinux() {
        return System.getProperty("os.name").startsWith("Linux");
    }

    public static boolean isWindows() {
        return System.getProperty("os.name").startsWith("Windows");
    }

    public static boolean isMac() {
        return System.getProperty("os.name").startsWith("Mac");
    }

    public static boolean is32BitIntel() {
        String arch = System.getProperty("os.arch");
        return "x86".equals(arch) || "i386".equals(arch);
    }

    public static boolean is64BitIntel() {
        String arch = System.getProperty("os.arch");
        return "amd64".equals(arch) || "x86_64".equals(arch);
    }

    private RuntimeDetector() {
    }
}

