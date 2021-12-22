/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  org.jetbrains.annotations.Nullable
 */
package org.ejml.equation;

import org.ejml.equation.Function;
import org.ejml.equation.Symbol;
import org.ejml.equation.Variable;
import org.ejml.equation.VariableScalar;
import org.ejml.equation.VariableType;
import org.jetbrains.annotations.Nullable;

class TokenList {
    Token first;
    Token last;
    int size = 0;

    public TokenList() {
    }

    public TokenList(Token first, Token last) {
        this.first = first;
        this.last = last;
        Token t = first;
        while (t != null) {
            ++this.size;
            t = t.next;
        }
    }

    public Token add(Function function) {
        Token t = new Token(function);
        this.push(t);
        return t;
    }

    public Token add(Variable variable) {
        Token t = new Token(variable);
        this.push(t);
        return t;
    }

    public Token add(Symbol symbol) {
        Token t = new Token(symbol);
        this.push(t);
        return t;
    }

    public Token add(String word) {
        Token t = new Token(word);
        this.push(t);
        return t;
    }

    public void push(Token token) {
        ++this.size;
        if (this.first == null) {
            this.first = token;
            this.last = token;
            token.previous = null;
            token.next = null;
        } else {
            this.last.next = token;
            token.previous = this.last;
            token.next = null;
            this.last = token;
        }
    }

    public void insert(@Nullable Token where, Token token) {
        if (where == null) {
            if (this.size == 0) {
                this.push(token);
            } else {
                this.first.previous = token;
                token.previous = null;
                token.next = this.first;
                this.first = token;
                ++this.size;
            }
        } else if (where == this.last || null == this.last) {
            this.push(token);
        } else {
            token.next = where.next;
            token.previous = where;
            where.next.previous = token;
            where.next = token;
            ++this.size;
        }
    }

    public void remove(Token token) {
        if (token == this.first) {
            this.first = this.first.next;
        }
        if (token == this.last) {
            this.last = this.last.previous;
        }
        if (token.next != null) {
            token.next.previous = token.previous;
        }
        if (token.previous != null) {
            token.previous.next = token.next;
        }
        token.previous = null;
        token.next = null;
        --this.size;
    }

    public void replace(Token original, Token target) {
        if (this.first == original) {
            this.first = target;
        }
        if (this.last == original) {
            this.last = target;
        }
        target.next = original.next;
        target.previous = original.previous;
        if (original.next != null) {
            original.next.previous = target;
        }
        if (original.previous != null) {
            original.previous.next = target;
        }
        original.previous = null;
        original.next = null;
    }

    public TokenList extractSubList(Token begin, Token end) {
        if (begin == end) {
            this.remove(begin);
            return new TokenList(begin, begin);
        }
        if (this.first == begin) {
            this.first = end.next;
        }
        if (this.last == end) {
            this.last = begin.previous;
        }
        if (begin.previous != null) {
            begin.previous.next = end.next;
        }
        if (end.next != null) {
            end.next.previous = begin.previous;
        }
        begin.previous = null;
        end.next = null;
        TokenList ret = new TokenList(begin, end);
        this.size -= ret.size();
        return ret;
    }

    public void insertAfter(Token before, TokenList list) {
        Token after = before.next;
        before.next = list.first;
        list.first.previous = before;
        if (after == null) {
            this.last = list.last;
        } else {
            after.previous = list.last;
            list.last.next = after;
        }
        this.size += list.size;
    }

    public String toString() {
        StringBuilder ret = new StringBuilder();
        Token t = this.first;
        while (t != null) {
            ret.append(t).append(" ");
            t = t.next;
        }
        return ret.toString();
    }

    public Token getFirst() {
        return this.first;
    }

    public Token getLast() {
        return this.last;
    }

    public int size() {
        return this.size;
    }

    public void print() {
        Token t = this.first;
        while (t != null) {
            System.out.println(t);
            t = t.next;
        }
    }

    public static class Token {
        public Token next;
        public Token previous;
        public Function function;
        public Variable variable;
        public Symbol symbol;
        public String word;

        public Token(Function function) {
            this.function = function;
        }

        public Token(Variable variable) {
            this.variable = variable;
        }

        public Token(Symbol symbol) {
            this.symbol = symbol;
        }

        public Token(String word) {
            this.word = word;
        }

        public Token() {
        }

        public Type getType() {
            if (this.function != null) {
                return Type.FUNCTION;
            }
            if (this.variable != null) {
                return Type.VARIABLE;
            }
            if (this.word != null) {
                return Type.WORD;
            }
            return Type.SYMBOL;
        }

        public Variable getVariable() {
            return this.variable;
        }

        public Function getFunction() {
            return this.function;
        }

        public Symbol getSymbol() {
            return this.symbol;
        }

        public String getWord() {
            return this.word;
        }

        @Nullable
        public VariableScalar.Type getScalarType() {
            if (this.variable != null && this.variable.getType() == VariableType.SCALAR) {
                return ((VariableScalar)this.variable).getScalarType();
            }
            return null;
        }

        public String toString() {
            switch (this.getType()) {
                case FUNCTION: {
                    return "Func:" + this.function.getName();
                }
                case SYMBOL: {
                    return "" + (Object)((Object)this.symbol);
                }
                case VARIABLE: {
                    return this.variable.toString();
                }
                case WORD: {
                    return "Word:" + this.word;
                }
            }
            throw new RuntimeException("Unknown type");
        }

        public Token copy() {
            Token t = new Token();
            t.word = this.word;
            t.function = this.function;
            t.symbol = this.symbol;
            t.variable = this.variable;
            return t;
        }
    }

    public static final class Type
    extends Enum<Type> {
        public static final /* enum */ Type FUNCTION = new Type();
        public static final /* enum */ Type VARIABLE = new Type();
        public static final /* enum */ Type SYMBOL = new Type();
        public static final /* enum */ Type WORD = new Type();
        private static final /* synthetic */ Type[] $VALUES;

        public static Type[] values() {
            return (Type[])$VALUES.clone();
        }

        public static Type valueOf(String name) {
            return Enum.valueOf(Type.class, name);
        }

        private static /* synthetic */ Type[] $values() {
            return new Type[]{FUNCTION, VARIABLE, SYMBOL, WORD};
        }

        static {
            $VALUES = Type.$values();
        }
    }
}

