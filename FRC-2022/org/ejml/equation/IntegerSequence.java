/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  org.jetbrains.annotations.Nullable
 */
package org.ejml.equation;

import java.util.ArrayList;
import java.util.List;
import org.ejml.equation.TokenList;
import org.ejml.equation.VariableInteger;
import org.ejml.equation.VariableIntegerSequence;
import org.ejml.equation.VariableType;
import org.jetbrains.annotations.Nullable;

public interface IntegerSequence {
    public int length();

    public void initialize(int var1);

    public int next();

    public boolean hasNext();

    public Type getType();

    public boolean requiresMaxIndex();

    public static class Range
    implements IntegerSequence {
        @Nullable
        VariableInteger start;
        @Nullable
        VariableInteger step;
        int valStart;
        int valStep;
        int valEnd;
        int where;
        int length;

        public Range(@Nullable TokenList.Token start, @Nullable TokenList.Token step) {
            this.start = start == null ? null : (VariableInteger)start.getVariable();
            this.step = step == null ? null : (VariableInteger)step.getVariable();
        }

        @Override
        public int length() {
            return this.length;
        }

        @Override
        public void initialize(int maxIndex) {
            if (maxIndex < 0) {
                throw new IllegalArgumentException("Range sequence being used inside an object without a known upper limit");
            }
            this.valEnd = maxIndex;
            this.valStart = this.start != null ? this.start.value : 0;
            this.valStep = this.step == null ? 1 : this.step.value;
            if (this.valStep <= 0) {
                throw new IllegalArgumentException("step size must be a positive integer");
            }
            this.where = 0;
            this.length = (this.valEnd - this.valStart) / this.valStep + 1;
        }

        @Override
        public int next() {
            return this.valStart + this.valStep * this.where++;
        }

        @Override
        public boolean hasNext() {
            return this.where < this.length;
        }

        public int getStart() {
            return this.valStart;
        }

        public int getStep() {
            return this.valStep;
        }

        public int getEnd() {
            return this.valEnd;
        }

        @Override
        public Type getType() {
            return Type.RANGE;
        }

        @Override
        public boolean requiresMaxIndex() {
            return true;
        }
    }

    public static class Combined
    implements IntegerSequence {
        List<IntegerSequence> sequences = new ArrayList<IntegerSequence>();
        int which;

        public Combined(TokenList.Token start, TokenList.Token end) {
            TokenList.Token t = start;
            do {
                if (t.getVariable().getType() == VariableType.SCALAR) {
                    this.sequences.add(new Explicit(t));
                    continue;
                }
                if (t.getVariable().getType() == VariableType.INTEGER_SEQUENCE) {
                    this.sequences.add(((VariableIntegerSequence)t.getVariable()).sequence);
                    continue;
                }
                throw new RuntimeException("Unexpected token type");
            } while ((t = t.next) != null && t.previous != end);
        }

        @Override
        public int length() {
            int total = 0;
            for (int i = 0; i < this.sequences.size(); ++i) {
                total += this.sequences.get(i).length();
            }
            return total;
        }

        @Override
        public void initialize(int maxIndex) {
            this.which = 0;
            for (int i = 0; i < this.sequences.size(); ++i) {
                this.sequences.get(i).initialize(maxIndex);
            }
        }

        @Override
        public int next() {
            int output = this.sequences.get(this.which).next();
            if (!this.sequences.get(this.which).hasNext()) {
                ++this.which;
            }
            return output;
        }

        @Override
        public boolean hasNext() {
            return this.which < this.sequences.size();
        }

        @Override
        public Type getType() {
            return Type.COMBINED;
        }

        @Override
        public boolean requiresMaxIndex() {
            for (int i = 0; i < this.sequences.size(); ++i) {
                if (!this.sequences.get(i).requiresMaxIndex()) continue;
                return true;
            }
            return false;
        }
    }

    public static class For
    implements IntegerSequence {
        VariableInteger start;
        @Nullable
        VariableInteger step;
        VariableInteger end;
        int valStart;
        int valStep;
        int valEnd;
        int where;
        int length;

        public For(TokenList.Token start, @Nullable TokenList.Token step, TokenList.Token end) {
            this.start = (VariableInteger)start.getVariable();
            this.step = step == null ? null : (VariableInteger)step.getVariable();
            this.end = (VariableInteger)end.getVariable();
        }

        @Override
        public int length() {
            return this.length;
        }

        @Override
        public void initialize(int maxIndex) {
            this.valStart = this.start.value;
            this.valEnd = this.end.value;
            this.valStep = this.step == null ? 1 : this.step.value;
            if (this.valStep <= 0) {
                throw new IllegalArgumentException("step size must be a positive integer");
            }
            if (this.valEnd < this.valStart) {
                throw new IllegalArgumentException("end value must be >= the start value");
            }
            this.where = 0;
            this.length = (this.valEnd - this.valStart) / this.valStep + 1;
        }

        @Override
        public int next() {
            return this.valStart + this.valStep * this.where++;
        }

        @Override
        public boolean hasNext() {
            return this.where < this.length;
        }

        public int getStart() {
            return this.valStart;
        }

        public int getStep() {
            return this.valStep;
        }

        public int getEnd() {
            return this.valEnd;
        }

        @Override
        public Type getType() {
            return Type.FOR;
        }

        @Override
        public boolean requiresMaxIndex() {
            return false;
        }
    }

    public static class Explicit
    implements IntegerSequence {
        List<VariableInteger> sequence = new ArrayList<VariableInteger>();
        int where;

        public Explicit(TokenList.Token start, TokenList.Token end) {
            TokenList.Token t = start;
            while (true) {
                this.sequence.add((VariableInteger)t.getVariable());
                if (t == end) break;
                t = t.next;
            }
        }

        public Explicit(TokenList.Token single) {
            this.sequence.add((VariableInteger)single.getVariable());
        }

        @Override
        public int length() {
            return this.sequence.size();
        }

        @Override
        public void initialize(int maxIndex) {
            this.where = 0;
        }

        @Override
        public int next() {
            return this.sequence.get((int)this.where++).value;
        }

        @Override
        public boolean hasNext() {
            return this.where < this.sequence.size();
        }

        @Override
        public Type getType() {
            return Type.EXPLICIT;
        }

        @Override
        public boolean requiresMaxIndex() {
            return false;
        }

        public List<VariableInteger> getSequence() {
            return this.sequence;
        }
    }

    public static final class Type
    extends Enum<Type> {
        public static final /* enum */ Type EXPLICIT = new Type();
        public static final /* enum */ Type FOR = new Type();
        public static final /* enum */ Type COMBINED = new Type();
        public static final /* enum */ Type RANGE = new Type();
        private static final /* synthetic */ Type[] $VALUES;

        public static Type[] values() {
            return (Type[])$VALUES.clone();
        }

        public static Type valueOf(String name) {
            return Enum.valueOf(Type.class, name);
        }

        private static /* synthetic */ Type[] $values() {
            return new Type[]{EXPLICIT, FOR, COMBINED, RANGE};
        }

        static {
            $VALUES = Type.$values();
        }
    }
}

