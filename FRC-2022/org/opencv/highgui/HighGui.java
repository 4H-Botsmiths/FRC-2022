/*
 * Decompiled with CFR 0.150.
 */
package org.opencv.highgui;

import java.awt.Image;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import org.opencv.core.Mat;
import org.opencv.highgui.ImageWindow;

public final class HighGui {
    public static final int WINDOW_NORMAL = 0;
    public static final int WINDOW_AUTOSIZE = 1;
    public static int n_closed_windows = 0;
    public static int pressedKey = -1;
    public static CountDownLatch latch = new CountDownLatch(1);
    public static Map<String, ImageWindow> windows = new HashMap<String, ImageWindow>();

    public static void namedWindow(String winname) {
        HighGui.namedWindow(winname, 1);
    }

    public static void namedWindow(String winname, int flag) {
        ImageWindow newWin = new ImageWindow(winname, flag);
        if (windows.get(winname) == null) {
            windows.put(winname, newWin);
        }
    }

    public static void imshow(String winname, Mat img) {
        if (img.empty()) {
            System.err.println("Error: Empty image in imshow");
            System.exit(-1);
        } else {
            ImageWindow tmpWindow = windows.get(winname);
            if (tmpWindow == null) {
                ImageWindow newWin = new ImageWindow(winname, img);
                windows.put(winname, newWin);
            } else {
                tmpWindow.setMat(img);
            }
        }
    }

    public static Image toBufferedImage(Mat m) {
        int type = 10;
        if (m.channels() > 1) {
            type = 5;
        }
        int bufferSize = m.channels() * m.cols() * m.rows();
        byte[] b = new byte[bufferSize];
        m.get(0, 0, b);
        BufferedImage image = new BufferedImage(m.cols(), m.rows(), type);
        byte[] targetPixels = ((DataBufferByte)image.getRaster().getDataBuffer()).getData();
        System.arraycopy(b, 0, targetPixels, 0, b.length);
        return image;
    }

    public static JFrame createJFrame(String title, int flag) {
        JFrame frame = new JFrame(title);
        frame.addWindowListener(new WindowAdapter(){

            @Override
            public void windowClosing(WindowEvent windowEvent) {
                if (++n_closed_windows == windows.size()) {
                    latch.countDown();
                }
            }
        });
        frame.addKeyListener(new KeyListener(){

            @Override
            public void keyTyped(KeyEvent e) {
            }

            @Override
            public void keyReleased(KeyEvent e) {
            }

            @Override
            public void keyPressed(KeyEvent e) {
                pressedKey = e.getKeyCode();
                latch.countDown();
            }
        });
        if (flag == 1) {
            frame.setResizable(false);
        }
        return frame;
    }

    public static void waitKey() {
        HighGui.waitKey(0);
    }

    public static int waitKey(int delay) {
        latch = new CountDownLatch(1);
        n_closed_windows = 0;
        pressedKey = -1;
        if (windows.isEmpty()) {
            System.err.println("Error: waitKey must be used after an imshow");
            System.exit(-1);
        }
        Iterator<Map.Entry<String, ImageWindow>> iter = windows.entrySet().iterator();
        while (iter.hasNext()) {
            Map.Entry<String, ImageWindow> entry = iter.next();
            ImageWindow win = (ImageWindow)entry.getValue();
            if (!win.alreadyUsed.booleanValue()) continue;
            iter.remove();
            win.frame.dispose();
        }
        for (ImageWindow win : windows.values()) {
            if (win.img != null) {
                ImageIcon icon = new ImageIcon(HighGui.toBufferedImage(win.img));
                if (win.lbl == null) {
                    JFrame frame = HighGui.createJFrame(win.name, win.flag);
                    JLabel lbl = new JLabel(icon);
                    win.setFrameLabelVisible(frame, lbl);
                    continue;
                }
                win.lbl.setIcon(icon);
                continue;
            }
            System.err.println("Error: no imshow associated with namedWindow: \"" + win.name + "\"");
            System.exit(-1);
        }
        try {
            if (delay == 0) {
                latch.await();
            } else {
                latch.await(delay, TimeUnit.MILLISECONDS);
            }
        }
        catch (InterruptedException e) {
            e.printStackTrace();
        }
        for (ImageWindow win : windows.values()) {
            win.alreadyUsed = true;
        }
        return pressedKey;
    }

    public static void destroyWindow(String winname) {
        ImageWindow tmpWin = windows.get(winname);
        if (tmpWin != null) {
            windows.remove(winname);
        }
    }

    public static void destroyAllWindows() {
        windows.clear();
    }

    public static void resizeWindow(String winname, int width, int height) {
        ImageWindow tmpWin = windows.get(winname);
        if (tmpWin != null) {
            tmpWin.setNewDimension(width, height);
        }
    }

    public static void moveWindow(String winname, int x, int y) {
        ImageWindow tmpWin = windows.get(winname);
        if (tmpWin != null) {
            tmpWin.setNewPosition(x, y);
        }
    }
}

