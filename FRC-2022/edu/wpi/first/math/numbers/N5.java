/*
 * Decompiled with CFR 0.150.
 */
package edu.wpi.first.math.numbers;

import edu.wpi.first.math.Nat;
import edu.wpi.first.math.Num;

public final class N5
extends Num
implements Nat<N5> {
    public static final N5 instance = new N5();

    private N5() {
    }

    @Override
    public int getNum() {
        return 5;
    }
}

