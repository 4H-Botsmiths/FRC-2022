/*
 * Decompiled with CFR 0.150.
 */
package edu.wpi.first.math.numbers;

import edu.wpi.first.math.Nat;
import edu.wpi.first.math.Num;

public final class N14
extends Num
implements Nat<N14> {
    public static final N14 instance = new N14();

    private N14() {
    }

    @Override
    public int getNum() {
        return 14;
    }
}

