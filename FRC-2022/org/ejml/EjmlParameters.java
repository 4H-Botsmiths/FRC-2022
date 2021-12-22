/*
 * Decompiled with CFR 0.150.
 */
package org.ejml;

public class EjmlParameters {
    public static final float TOL32 = 1.0E-4f;
    public static final double TOL64 = 1.0E-8;
    public static MemoryUsage MEMORY = MemoryUsage.FASTER;
    public static int BLOCK_WIDTH = 60;
    public static int BLOCK_WIDTH_CHOL = 20;
    public static int TRANSPOSE_SWITCH = 375;
    public static int MULT_COLUMN_SWITCH = 15;
    public static int MULT_TRANAB_COLUMN_SWITCH = 40;
    public static int MULT_INNER_SWITCH = 100;
    public static int CMULT_COLUMN_SWITCH = 7;
    public static int CMULT_TRANAB_COLUMN_SWITCH = 20;
    public static int SWITCH_BLOCK64_CHOLESKY = 1000;
    public static int SWITCH_BLOCK64_QR = 1500;

    public static final class MemoryUsage
    extends Enum<MemoryUsage> {
        public static final /* enum */ MemoryUsage LOW_MEMORY = new MemoryUsage();
        public static final /* enum */ MemoryUsage FASTER = new MemoryUsage();
        private static final /* synthetic */ MemoryUsage[] $VALUES;

        public static MemoryUsage[] values() {
            return (MemoryUsage[])$VALUES.clone();
        }

        public static MemoryUsage valueOf(String name) {
            return Enum.valueOf(MemoryUsage.class, name);
        }

        private static /* synthetic */ MemoryUsage[] $values() {
            return new MemoryUsage[]{LOW_MEMORY, FASTER};
        }

        static {
            $VALUES = MemoryUsage.$values();
        }
    }
}

