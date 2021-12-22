/*
 * Decompiled with CFR 0.150.
 */
package org.ejml;

import java.text.DecimalFormat;
import org.ejml.UtilEjml;

public class FancyPrint {
    DecimalFormat format = new DecimalFormat("#");
    int length = 11;
    int significant = 4;

    public FancyPrint(DecimalFormat format, int length, int significant) {
        this.format = format;
        this.length = length;
        this.significant = significant;
    }

    public FancyPrint() {
    }

    public String sf(double value) {
        return UtilEjml.fancyStringF(value, this.format, this.length, this.significant);
    }

    public String s(double value) {
        return UtilEjml.fancyString(value, this.format, this.length, this.significant);
    }

    public String p(double value) {
        return UtilEjml.fancyString(value, this.format, false, this.length, this.significant);
    }
}

