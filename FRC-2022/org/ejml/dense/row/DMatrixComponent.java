/*
 * Decompiled with CFR 0.150.
 */
package org.ejml.dense.row;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import javax.swing.JPanel;
import org.ejml.data.DMatrixD1;
import org.ejml.dense.row.CommonOps_DDRM;

public class DMatrixComponent
extends JPanel {
    BufferedImage image;

    public DMatrixComponent(int width, int height) {
        this.image = new BufferedImage(width, height, 1);
        this.setPreferredSize(new Dimension(width, height));
        this.setMinimumSize(new Dimension(width, height));
    }

    public synchronized void setMatrix(DMatrixD1 A) {
        double maxValue = CommonOps_DDRM.elementMaxAbs(A);
        DMatrixComponent.renderMatrix(A, this.image, maxValue);
        this.repaint();
    }

    public static void renderMatrix(DMatrixD1 M, BufferedImage image, double maxValue) {
        int w = image.getWidth();
        int h = image.getHeight();
        double widthStep = (double)M.numCols / (double)image.getWidth();
        double heightStep = (double)M.numRows / (double)image.getHeight();
        for (int i = 0; i < h; ++i) {
            for (int j = 0; j < w; ++j) {
                int rgb;
                int p;
                double value = M.get((int)((double)i * heightStep), (int)((double)j * widthStep));
                if (value == 0.0) {
                    image.setRGB(j, i, -16777216);
                    continue;
                }
                if (value > 0.0) {
                    p = 255 - (int)(255.0 * (value / maxValue));
                    rgb = 0xFFFF0000 | p << 8 | p;
                    image.setRGB(j, i, rgb);
                    continue;
                }
                p = 255 + (int)(255.0 * (value / maxValue));
                rgb = 0xFF000000 | p << 16 | p << 8 | 0xFF;
                image.setRGB(j, i, rgb);
            }
        }
    }

    @Override
    public synchronized void paint(Graphics g) {
        g.drawImage(this.image, 0, 0, this);
    }
}

