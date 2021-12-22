/*
 * Decompiled with CFR 0.150.
 */
package edu.wpi.first.math.numbers;

import edu.wpi.first.math.Nat;
import edu.wpi.first.math.Num;

public final class N2
extends Num
implements Nat<N2> {
    public static final N2 instance = new N2();

    private N2() {
    }

    @Override
    public int getNum() {
        return 2;
    }
}

