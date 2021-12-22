/*
 * Decompiled with CFR 0.150.
 */
package edu.wpi.first.math.numbers;

import edu.wpi.first.math.Nat;
import edu.wpi.first.math.Num;

public final class N15
extends Num
implements Nat<N15> {
    public static final N15 instance = new N15();

    private N15() {
    }

    @Override
    public int getNum() {
        return 15;
    }
}

