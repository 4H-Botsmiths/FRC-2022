/*
 * Decompiled with CFR 0.150.
 */
package edu.wpi.first.wpilibj.util;

import edu.wpi.first.math.MathUtil;
import edu.wpi.first.wpilibj.util.Color;
import java.util.Objects;

public class Color8Bit {
    public final int red;
    public final int green;
    public final int blue;

    public Color8Bit(int red, int green, int blue) {
        this.red = MathUtil.clamp(red, 0, 255);
        this.green = MathUtil.clamp(green, 0, 255);
        this.blue = MathUtil.clamp(blue, 0, 255);
    }

    public Color8Bit(Color color) {
        this((int)(color.red * 255.0), (int)(color.green * 255.0), (int)(color.blue * 255.0));
    }

    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (other == null || this.getClass() != other.getClass()) {
            return false;
        }
        Color8Bit color8Bit = (Color8Bit)other;
        return this.red == color8Bit.red && this.green == color8Bit.green && this.blue == color8Bit.blue;
    }

    public int hashCode() {
        return Objects.hash(this.red, this.green, this.blue);
    }
}

