/*
 * Decompiled with CFR 0.150.
 */
package edu.wpi.first.wpilibj.smartdashboard;

import edu.wpi.first.networktables.NTSendable;
import edu.wpi.first.networktables.NTSendableBuilder;
import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.wpilibj.smartdashboard.MechanismRoot2d;
import edu.wpi.first.wpilibj.util.Color8Bit;
import java.util.HashMap;
import java.util.Map;

public final class Mechanism2d
implements NTSendable {
    private static final String kBackgroundColor = "backgroundColor";
    private NetworkTable m_table;
    private final Map<String, MechanismRoot2d> m_roots;
    private final double[] m_dims = new double[2];
    private String m_color;

    public Mechanism2d(double width, double height) {
        this(width, height, new Color8Bit(0, 0, 32));
    }

    public Mechanism2d(double width, double height, Color8Bit backgroundColor) {
        this.m_roots = new HashMap<String, MechanismRoot2d>();
        this.m_dims[0] = width;
        this.m_dims[1] = height;
        this.setBackgroundColor(backgroundColor);
    }

    public synchronized MechanismRoot2d getRoot(String name, double x, double y) {
        MechanismRoot2d existing = this.m_roots.get(name);
        if (existing != null) {
            return existing;
        }
        MechanismRoot2d root = new MechanismRoot2d(name, x, y);
        this.m_roots.put(name, root);
        if (this.m_table != null) {
            root.update(this.m_table.getSubTable(name));
        }
        return root;
    }

    public synchronized void setBackgroundColor(Color8Bit color) {
        this.m_color = String.format("#%02X%02X%02X", color.red, color.green, color.blue);
        if (this.m_table != null) {
            this.m_table.getEntry(kBackgroundColor).setString(this.m_color);
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public void initSendable(NTSendableBuilder builder) {
        builder.setSmartDashboardType("Mechanism2d");
        Mechanism2d mechanism2d = this;
        synchronized (mechanism2d) {
            this.m_table = builder.getTable();
            this.m_table.getEntry("dims").setDoubleArray(this.m_dims);
            this.m_table.getEntry(kBackgroundColor).setString(this.m_color);
            for (Map.Entry<String, MechanismRoot2d> entry : this.m_roots.entrySet()) {
                MechanismRoot2d root;
                String name = entry.getKey();
                MechanismRoot2d mechanismRoot2d = root = entry.getValue();
                synchronized (mechanismRoot2d) {
                    root.update(this.m_table.getSubTable(name));
                }
            }
        }
    }
}

