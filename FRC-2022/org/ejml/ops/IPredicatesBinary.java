/*
 * Decompiled with CFR 0.150.
 */
package org.ejml.ops;

import org.ejml.ops.IPredicateBinary;

public class IPredicatesBinary {
    public static final IPredicateBinary lowerTriangle = (row, col) -> row >= col;
    public static final IPredicateBinary higherTriangle = (row, col) -> row <= col;
    public static final IPredicateBinary diagonal = (row, col) -> row == col;
    public static final IPredicateBinary nonDiagonal = (row, col) -> row != col;
}

