/*
 * Decompiled with CFR 0.150.
 */
package edu.wpi.first.math.numbers;

import edu.wpi.first.math.Nat;
import edu.wpi.first.math.Num;

public final class N3
extends Num
implements Nat<N3> {
    public static final N3 instance = new N3();

    private N3() {
    }

    @Override
    public int getNum() {
        return 3;
    }
}

