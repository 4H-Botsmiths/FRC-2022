/*
 * Decompiled with CFR 0.150.
 */
package org.opencv.highgui;

import java.awt.Dimension;
import javax.swing.JFrame;
import javax.swing.JLabel;
import org.opencv.core.Mat;
import org.opencv.core.Size;
import org.opencv.imgproc.Imgproc;

public final class ImageWindow {
    public static final int WINDOW_NORMAL = 0;
    public static final int WINDOW_AUTOSIZE = 1;
    public String name;
    public Mat img = null;
    public Boolean alreadyUsed = false;
    public Boolean imgToBeResized = false;
    public Boolean windowToBeResized = false;
    public Boolean positionToBeChanged = false;
    public JFrame frame = null;
    public JLabel lbl = null;
    public int flag;
    public int x = -1;
    public int y = -1;
    public int width = -1;
    public int height = -1;

    public ImageWindow(String name, Mat img) {
        this.name = name;
        this.img = img;
        this.flag = 0;
    }

    public ImageWindow(String name, int flag) {
        this.name = name;
        this.flag = flag;
    }

    public static Size keepAspectRatioSize(int original_width, int original_height, int bound_width, int bound_height) {
        int new_width = original_width;
        int new_height = original_height;
        if (original_width > bound_width) {
            new_width = bound_width;
            new_height = new_width * original_height / original_width;
        }
        if (new_height > bound_height) {
            new_height = bound_height;
            new_width = new_height * original_width / original_height;
        }
        return new Size(new_width, new_height);
    }

    public void setMat(Mat img) {
        this.img = img;
        this.alreadyUsed = false;
        if (this.imgToBeResized.booleanValue()) {
            this.resizeImage();
            this.imgToBeResized = false;
        }
    }

    public void setFrameLabelVisible(JFrame frame, JLabel lbl) {
        this.frame = frame;
        this.lbl = lbl;
        if (this.windowToBeResized.booleanValue()) {
            lbl.setPreferredSize(new Dimension(this.width, this.height));
            this.windowToBeResized = false;
        }
        if (this.positionToBeChanged.booleanValue()) {
            frame.setLocation(this.x, this.y);
            this.positionToBeChanged = false;
        }
        frame.add(lbl);
        frame.pack();
        frame.setVisible(true);
    }

    public void setNewDimension(int width, int height) {
        if (this.width != width || this.height != height) {
            this.width = width;
            this.height = height;
            if (this.img != null) {
                this.resizeImage();
            } else {
                this.imgToBeResized = true;
            }
            if (this.lbl != null) {
                this.lbl.setPreferredSize(new Dimension(width, height));
            } else {
                this.windowToBeResized = true;
            }
        }
    }

    public void setNewPosition(int x, int y) {
        if (this.x != x || this.y != y) {
            this.x = x;
            this.y = y;
            if (this.frame != null) {
                this.frame.setLocation(x, y);
            } else {
                this.positionToBeChanged = true;
            }
        }
    }

    private void resizeImage() {
        if (this.flag == 0) {
            Size tmpSize = ImageWindow.keepAspectRatioSize(this.img.width(), this.img.height(), this.width, this.height);
            Imgproc.resize(this.img, this.img, tmpSize, 0.0, 0.0, 5);
        }
    }
}

