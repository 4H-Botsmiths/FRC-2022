/*
 * Decompiled with CFR 0.150.
 */
package edu.wpi.first.math.numbers;

import edu.wpi.first.math.Nat;
import edu.wpi.first.math.Num;

public final class N19
extends Num
implements Nat<N19> {
    public static final N19 instance = new N19();

    private N19() {
    }

    @Override
    public int getNum() {
        return 19;
    }
}

