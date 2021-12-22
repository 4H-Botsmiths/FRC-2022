/*
 * Decompiled with CFR 0.150.
 */
package edu.wpi.first.math.numbers;

import edu.wpi.first.math.Nat;
import edu.wpi.first.math.Num;

public final class N18
extends Num
implements Nat<N18> {
    public static final N18 instance = new N18();

    private N18() {
    }

    @Override
    public int getNum() {
        return 18;
    }
}

