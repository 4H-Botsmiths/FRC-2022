/*
 * Decompiled with CFR 0.150.
 */
package edu.wpi.first.math.numbers;

import edu.wpi.first.math.Nat;
import edu.wpi.first.math.Num;

public final class N8
extends Num
implements Nat<N8> {
    public static final N8 instance = new N8();

    private N8() {
    }

    @Override
    public int getNum() {
        return 8;
    }
}

