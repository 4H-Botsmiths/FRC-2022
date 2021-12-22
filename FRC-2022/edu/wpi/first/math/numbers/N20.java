/*
 * Decompiled with CFR 0.150.
 */
package edu.wpi.first.math.numbers;

import edu.wpi.first.math.Nat;
import edu.wpi.first.math.Num;

public final class N20
extends Num
implements Nat<N20> {
    public static final N20 instance = new N20();

    private N20() {
    }

    @Override
    public int getNum() {
        return 20;
    }
}

