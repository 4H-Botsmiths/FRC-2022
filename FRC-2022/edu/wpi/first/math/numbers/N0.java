/*
 * Decompiled with CFR 0.150.
 */
package edu.wpi.first.math.numbers;

import edu.wpi.first.math.Nat;
import edu.wpi.first.math.Num;

public final class N0
extends Num
implements Nat<N0> {
    public static final N0 instance = new N0();

    private N0() {
    }

    @Override
    public int getNum() {
        return 0;
    }
}

