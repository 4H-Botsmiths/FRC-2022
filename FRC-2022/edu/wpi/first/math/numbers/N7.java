/*
 * Decompiled with CFR 0.150.
 */
package edu.wpi.first.math.numbers;

import edu.wpi.first.math.Nat;
import edu.wpi.first.math.Num;

public final class N7
extends Num
implements Nat<N7> {
    public static final N7 instance = new N7();

    private N7() {
    }

    @Override
    public int getNum() {
        return 7;
    }
}

