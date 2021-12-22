/*
 * Decompiled with CFR 0.150.
 */
package edu.wpi.first.math.numbers;

import edu.wpi.first.math.Nat;
import edu.wpi.first.math.Num;

public final class N12
extends Num
implements Nat<N12> {
    public static final N12 instance = new N12();

    private N12() {
    }

    @Override
    public int getNum() {
        return 12;
    }
}

