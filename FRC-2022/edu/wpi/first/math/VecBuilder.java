/*
 * Decompiled with CFR 0.150.
 */
package edu.wpi.first.math;

import edu.wpi.first.math.MatBuilder;
import edu.wpi.first.math.Nat;
import edu.wpi.first.math.Num;
import edu.wpi.first.math.Vector;
import edu.wpi.first.math.numbers.N1;
import edu.wpi.first.math.numbers.N10;
import edu.wpi.first.math.numbers.N2;
import edu.wpi.first.math.numbers.N3;
import edu.wpi.first.math.numbers.N4;
import edu.wpi.first.math.numbers.N5;
import edu.wpi.first.math.numbers.N6;
import edu.wpi.first.math.numbers.N7;
import edu.wpi.first.math.numbers.N8;
import edu.wpi.first.math.numbers.N9;

public class VecBuilder<N extends Num>
extends MatBuilder<N, N1> {
    public VecBuilder(Nat<N> rows) {
        super(rows, Nat.N1());
    }

    private Vector<N> fillVec(double ... data) {
        return new Vector(this.fill(data));
    }

    public static Vector<N1> fill(double n1) {
        return new VecBuilder<N1>(Nat.N1()).fillVec(n1);
    }

    public static Vector<N2> fill(double n1, double n2) {
        return new VecBuilder<N2>(Nat.N2()).fillVec(n1, n2);
    }

    public static Vector<N3> fill(double n1, double n2, double n3) {
        return new VecBuilder<N3>(Nat.N3()).fillVec(n1, n2, n3);
    }

    public static Vector<N4> fill(double n1, double n2, double n3, double n4) {
        return new VecBuilder<N4>(Nat.N4()).fillVec(n1, n2, n3, n4);
    }

    public static Vector<N5> fill(double n1, double n2, double n3, double n4, double n5) {
        return new VecBuilder<N5>(Nat.N5()).fillVec(n1, n2, n3, n4, n5);
    }

    public static Vector<N6> fill(double n1, double n2, double n3, double n4, double n5, double n6) {
        return new VecBuilder<N6>(Nat.N6()).fillVec(n1, n2, n3, n4, n5, n6);
    }

    public static Vector<N7> fill(double n1, double n2, double n3, double n4, double n5, double n6, double n7) {
        return new VecBuilder<N7>(Nat.N7()).fillVec(n1, n2, n3, n4, n5, n6, n7);
    }

    public static Vector<N8> fill(double n1, double n2, double n3, double n4, double n5, double n6, double n7, double n8) {
        return new VecBuilder<N8>(Nat.N8()).fillVec(n1, n2, n3, n4, n5, n6, n7, n8);
    }

    public static Vector<N9> fill(double n1, double n2, double n3, double n4, double n5, double n6, double n7, double n8, double n9) {
        return new VecBuilder<N9>(Nat.N9()).fillVec(n1, n2, n3, n4, n5, n6, n7, n8, n9);
    }

    public static Vector<N10> fill(double n1, double n2, double n3, double n4, double n5, double n6, double n7, double n8, double n9, double n10) {
        return new VecBuilder<N10>(Nat.N10()).fillVec(n1, n2, n3, n4, n5, n6, n7, n8, n9, n10);
    }
}

