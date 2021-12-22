/*
 * Decompiled with CFR 0.150.
 */
package edu.wpi.first.math.numbers;

import edu.wpi.first.math.Nat;
import edu.wpi.first.math.Num;

public final class N17
extends Num
implements Nat<N17> {
    public static final N17 instance = new N17();

    private N17() {
    }

    @Override
    public int getNum() {
        return 17;
    }
}

