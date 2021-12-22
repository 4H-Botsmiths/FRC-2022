/*
 * Decompiled with CFR 0.150.
 */
package org.ejml.dense.row;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import javax.swing.JPanel;
import org.ejml.data.FMatrixD1;
import org.ejml.dense.row.CommonOps_FDRM;

public class FMatrixComponent
extends JPanel {
    BufferedImage image;

    public FMatrixComponent(int width, int height) {
        this.image = new BufferedImage(width, height, 1);
        this.setPreferredSize(new Dimension(width, height));
        this.setMinimumSize(new Dimension(width, height));
    }

    public synchronized void setMatrix(FMatrixD1 A) {
        float maxValue = CommonOps_FDRM.elementMaxAbs(A);
        FMatrixComponent.renderMatrix(A, this.image, maxValue);
        this.repaint();
    }

    public static void renderMatrix(FMatrixD1 M, BufferedImage image, float maxValue) {
        int w = image.getWidth();
        int h = image.getHeight();
        float widthStep = (float)M.numCols / (float)image.getWidth();
        float heightStep = (float)M.numRows / (float)image.getHeight();
        for (int i = 0; i < h; ++i) {
            for (int j = 0; j < w; ++j) {
                int rgb;
                int p;
                float value = M.get((int)((float)i * heightStep), (int)((float)j * widthStep));
                if (value == 0.0f) {
                    image.setRGB(j, i, -16777216);
                    continue;
                }
                if (value > 0.0f) {
                    p = 255 - (int)(255.0f * (value / maxValue));
                    rgb = 0xFFFF0000 | p << 8 | p;
                    image.setRGB(j, i, rgb);
                    continue;
                }
                p = 255 + (int)(255.0f * (value / maxValue));
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

