/*
 * Decompiled with CFR 0.150.
 */
package edu.wpi.first.math.numbers;

import edu.wpi.first.math.Nat;
import edu.wpi.first.math.Num;

public final class N10
extends Num
implements Nat<N10> {
    public static final N10 instance = new N10();

    private N10() {
    }

    @Override
    public int getNum() {
        return 10;
    }
}

