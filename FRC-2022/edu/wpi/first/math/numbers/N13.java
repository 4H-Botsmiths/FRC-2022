/*
 * Decompiled with CFR 0.150.
 */
package edu.wpi.first.math.numbers;

import edu.wpi.first.math.Nat;
import edu.wpi.first.math.Num;

public final class N13
extends Num
implements Nat<N13> {
    public static final N13 instance = new N13();

    private N13() {
    }

    @Override
    public int getNum() {
        return 13;
    }
}

