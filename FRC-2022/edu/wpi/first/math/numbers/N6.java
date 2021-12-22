/*
 * Decompiled with CFR 0.150.
 */
package edu.wpi.first.math.numbers;

import edu.wpi.first.math.Nat;
import edu.wpi.first.math.Num;

public final class N6
extends Num
implements Nat<N6> {
    public static final N6 instance = new N6();

    private N6() {
    }

    @Override
    public int getNum() {
        return 6;
    }
}

