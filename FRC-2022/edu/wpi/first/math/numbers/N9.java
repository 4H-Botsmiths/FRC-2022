/*
 * Decompiled with CFR 0.150.
 */
package edu.wpi.first.math.numbers;

import edu.wpi.first.math.Nat;
import edu.wpi.first.math.Num;

public final class N9
extends Num
implements Nat<N9> {
    public static final N9 instance = new N9();

    private N9() {
    }

    @Override
    public int getNum() {
        return 9;
    }
}

