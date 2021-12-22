/*
 * Decompiled with CFR 0.150.
 */
package org.ejml.equation;

public final class Symbol
extends Enum<Symbol> {
    public static final /* enum */ Symbol PLUS = new Symbol();
    public static final /* enum */ Symbol MINUS = new Symbol();
    public static final /* enum */ Symbol TIMES = new Symbol();
    public static final /* enum */ Symbol LDIVIDE = new Symbol();
    public static final /* enum */ Symbol RDIVIDE = new Symbol();
    public static final /* enum */ Symbol POWER = new Symbol();
    public static final /* enum */ Symbol PERIOD = new Symbol();
    public static final /* enum */ Symbol ELEMENT_TIMES = new Symbol();
    public static final /* enum */ Symbol ELEMENT_DIVIDE = new Symbol();
    public static final /* enum */ Symbol ELEMENT_POWER = new Symbol();
    public static final /* enum */ Symbol ASSIGN = new Symbol();
    public static final /* enum */ Symbol PAREN_LEFT = new Symbol();
    public static final /* enum */ Symbol PAREN_RIGHT = new Symbol();
    public static final /* enum */ Symbol BRACKET_LEFT = new Symbol();
    public static final /* enum */ Symbol BRACKET_RIGHT = new Symbol();
    public static final /* enum */ Symbol GREATER_THAN = new Symbol();
    public static final /* enum */ Symbol LESS_THAN = new Symbol();
    public static final /* enum */ Symbol GREATER_THAN_EQ = new Symbol();
    public static final /* enum */ Symbol LESS_THAN_EQ = new Symbol();
    public static final /* enum */ Symbol COMMA = new Symbol();
    public static final /* enum */ Symbol TRANSPOSE = new Symbol();
    public static final /* enum */ Symbol COLON = new Symbol();
    public static final /* enum */ Symbol SEMICOLON = new Symbol();
    private static final /* synthetic */ Symbol[] $VALUES;

    public static Symbol[] values() {
        return (Symbol[])$VALUES.clone();
    }

    public static Symbol valueOf(String name) {
        return Enum.valueOf(Symbol.class, name);
    }

    public static Symbol lookup(char c) {
        switch (c) {
            case '.': {
                return PERIOD;
            }
            case ',': {
                return COMMA;
            }
            case '\'': {
                return TRANSPOSE;
            }
            case '+': {
                return PLUS;
            }
            case '-': {
                return MINUS;
            }
            case '*': {
                return TIMES;
            }
            case '\\': {
                return LDIVIDE;
            }
            case '/': {
                return RDIVIDE;
            }
            case '^': {
                return POWER;
            }
            case '=': {
                return ASSIGN;
            }
            case '(': {
                return PAREN_LEFT;
            }
            case ')': {
                return PAREN_RIGHT;
            }
            case '[': {
                return BRACKET_LEFT;
            }
            case ']': {
                return BRACKET_RIGHT;
            }
            case '>': {
                return GREATER_THAN;
            }
            case '<': {
                return LESS_THAN;
            }
            case ':': {
                return COLON;
            }
            case ';': {
                return SEMICOLON;
            }
        }
        throw new RuntimeException("Unknown type " + c);
    }

    public static Symbol lookupElementWise(char c) {
        switch (c) {
            case '*': {
                return ELEMENT_TIMES;
            }
            case '/': {
                return ELEMENT_DIVIDE;
            }
            case '^': {
                return ELEMENT_POWER;
            }
        }
        throw new RuntimeException("Unknown element-wise type " + c);
    }

    private static /* synthetic */ Symbol[] $values() {
        return new Symbol[]{PLUS, MINUS, TIMES, LDIVIDE, RDIVIDE, POWER, PERIOD, ELEMENT_TIMES, ELEMENT_DIVIDE, ELEMENT_POWER, ASSIGN, PAREN_LEFT, PAREN_RIGHT, BRACKET_LEFT, BRACKET_RIGHT, GREATER_THAN, LESS_THAN, GREATER_THAN_EQ, LESS_THAN_EQ, COMMA, TRANSPOSE, COLON, SEMICOLON};
    }

    static {
        $VALUES = Symbol.$values();
    }
}

