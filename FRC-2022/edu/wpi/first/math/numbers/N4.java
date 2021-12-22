/*
 * Decompiled with CFR 0.150.
 */
package edu.wpi.first.math.numbers;

import edu.wpi.first.math.Nat;
import edu.wpi.first.math.Num;

public final class N4
extends Num
implements Nat<N4> {
    public static final N4 instance = new N4();

    private N4() {
    }

    @Override
    public int getNum() {
        return 4;
    }
}

