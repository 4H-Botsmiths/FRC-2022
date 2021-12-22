/*
 * Decompiled with CFR 0.150.
 */
package edu.wpi.first.math.numbers;

import edu.wpi.first.math.Nat;
import edu.wpi.first.math.Num;

public final class N11
extends Num
implements Nat<N11> {
    public static final N11 instance = new N11();

    private N11() {
    }

    @Override
    public int getNum() {
        return 11;
    }
}

