/*
 * Decompiled with CFR 0.150.
 */
package edu.wpi.first.math.numbers;

import edu.wpi.first.math.Nat;
import edu.wpi.first.math.Num;

public final class N1
extends Num
implements Nat<N1> {
    public static final N1 instance = new N1();

    private N1() {
    }

    @Override
    public int getNum() {
        return 1;
    }
}

