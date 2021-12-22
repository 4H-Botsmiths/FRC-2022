/*
 * Decompiled with CFR 0.150.
 */
package org.ejml.dense.row;

import java.awt.Component;
import javax.swing.JFrame;
import org.ejml.data.DMatrixD1;
import org.ejml.dense.row.DMatrixComponent;

public class DMatrixVisualization {
    public static void show(DMatrixD1 A, String title) {
        JFrame frame = new JFrame(title);
        int width = 300;
        int height = 300;
        if (A.numRows > A.numCols) {
            width = width * A.numCols / A.numRows;
        } else {
            height = height * A.numRows / A.numCols;
        }
        DMatrixComponent panel = new DMatrixComponent(width, height);
        panel.setMatrix(A);
        frame.add((Component)panel, "Center");
        frame.pack();
        frame.setVisible(true);
    }
}

