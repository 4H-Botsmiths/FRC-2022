/*
 * Decompiled with CFR 0.150.
 */
package edu.wpi.first.cscore;

public class UsbCameraInfo {
    public int dev;
    public String path;
    public String name;
    public String[] otherPaths;
    public int vendorId;
    public int productId;

    public UsbCameraInfo(int dev, String path, String name, String[] otherPaths, int vendorId, int productId) {
        this.dev = dev;
        this.path = path;
        this.name = name;
        this.otherPaths = otherPaths;
        this.vendorId = vendorId;
        this.productId = productId;
    }
}

