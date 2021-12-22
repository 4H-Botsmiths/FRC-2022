/*
 * Decompiled with CFR 0.150.
 */
package edu.wpi.first.math.numbers;

import edu.wpi.first.math.Nat;
import edu.wpi.first.math.Num;

public final class N16
extends Num
implements Nat<N16> {
    public static final N16 instance = new N16();

    private N16() {
    }

    @Override
    public int getNum() {
        return 16;
    }
}

