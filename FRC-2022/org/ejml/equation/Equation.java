/*
 * Decompiled with CFR 0.150.
 */
package org.ejml.equation;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;
import org.ejml.data.DMatrix;
import org.ejml.data.DMatrixFixed;
import org.ejml.data.DMatrixRMaj;
import org.ejml.data.DMatrixSparseCSC;
import org.ejml.data.FMatrix;
import org.ejml.data.FMatrixFixed;
import org.ejml.data.FMatrixRMaj;
import org.ejml.equation.Function;
import org.ejml.equation.IntegerSequence;
import org.ejml.equation.Macro;
import org.ejml.equation.ManagerFunctions;
import org.ejml.equation.ManagerTempVariables;
import org.ejml.equation.MatrixConstructor;
import org.ejml.equation.Operation;
import org.ejml.equation.ParseError;
import org.ejml.equation.Sequence;
import org.ejml.equation.Symbol;
import org.ejml.equation.TokenList;
import org.ejml.equation.Variable;
import org.ejml.equation.VariableDouble;
import org.ejml.equation.VariableInteger;
import org.ejml.equation.VariableIntegerSequence;
import org.ejml.equation.VariableMatrix;
import org.ejml.equation.VariableScalar;
import org.ejml.equation.VariableType;
import org.ejml.ops.ConvertMatrixData;
import org.ejml.ops.DConvertMatrixStruct;
import org.ejml.ops.FConvertMatrixStruct;
import org.ejml.simple.SimpleMatrix;

public class Equation {
    HashMap<String, Variable> variables = new HashMap();
    HashMap<String, Macro> macros = new HashMap();
    char[] storage = new char[1024];
    ManagerFunctions functions = new ManagerFunctions();
    ManagerTempVariables managerTemp = new ManagerTempVariables();

    public Equation() {
        this.alias(Math.PI, "pi");
        this.alias(Math.E, "e");
    }

    public Equation(Object ... args) {
        this();
        this.alias(args);
    }

    public void setSeed(long seed) {
        this.functions.managerTemp.getRandom().setSeed(seed);
    }

    public void setSeed() {
        this.functions.managerTemp.rand = new Random();
    }

    public void alias(DMatrixRMaj variable, String name) {
        if (this.isReserved(name)) {
            throw new RuntimeException("Reserved word or contains a reserved character");
        }
        VariableMatrix old = (VariableMatrix)this.variables.get(name);
        if (old == null) {
            this.variables.put(name, new VariableMatrix(variable));
        } else {
            old.matrix = variable;
        }
    }

    public void alias(FMatrixRMaj variable, String name) {
        DMatrixRMaj f = new DMatrixRMaj(variable.numRows, variable.numCols);
        ConvertMatrixData.convert(variable, f);
        this.alias(f, name);
    }

    public void alias(DMatrixSparseCSC variable, String name) {
        DMatrixRMaj f = new DMatrixRMaj(variable.numRows, variable.numCols);
        DConvertMatrixStruct.convert(variable, f);
        this.alias(f, name);
    }

    public void alias(SimpleMatrix variable, String name) {
        this.alias(variable.getMatrix(), name);
    }

    public void alias(double value, String name) {
        if (this.isReserved(name)) {
            throw new RuntimeException("Reserved word or contains a reserved character. '" + name + "'");
        }
        VariableDouble old = (VariableDouble)this.variables.get(name);
        if (old == null) {
            this.variables.put(name, new VariableDouble(value));
        } else {
            old.value = value;
        }
    }

    public void alias(int value, String name) {
        if (this.isReserved(name)) {
            throw new RuntimeException("Reserved word or contains a reserved character");
        }
        VariableInteger old = (VariableInteger)this.variables.get(name);
        if (old == null) {
            this.variables.put(name, new VariableInteger(value));
        } else {
            old.value = value;
        }
    }

    private void alias(IntegerSequence sequence, String name) {
        if (this.isReserved(name)) {
            throw new RuntimeException("Reserved word or contains a reserved character");
        }
        VariableIntegerSequence old = (VariableIntegerSequence)this.variables.get(name);
        if (old == null) {
            this.variables.put(name, new VariableIntegerSequence(sequence));
        } else {
            old.sequence = sequence;
        }
    }

    public void alias(Object ... args) {
        if (args.length % 2 == 1) {
            throw new RuntimeException("Even number of arguments expected");
        }
        for (int i = 0; i < args.length; i += 2) {
            this.aliasGeneric(args[i], (String)args[i + 1]);
        }
    }

    protected void aliasGeneric(Object variable, String name) {
        if (variable.getClass() == Integer.class) {
            this.alias((Integer)variable, name);
        } else if (variable.getClass() == Double.class) {
            this.alias((Double)variable, name);
        } else if (variable.getClass() == DMatrixRMaj.class) {
            this.alias((DMatrixRMaj)variable, name);
        } else if (variable.getClass() == FMatrixRMaj.class) {
            this.alias((FMatrixRMaj)variable, name);
        } else if (variable.getClass() == DMatrixSparseCSC.class) {
            this.alias((DMatrixSparseCSC)variable, name);
        } else if (variable.getClass() == SimpleMatrix.class) {
            this.alias((SimpleMatrix)variable, name);
        } else if (variable instanceof DMatrixFixed) {
            DMatrixRMaj M = new DMatrixRMaj(1, 1);
            DConvertMatrixStruct.convert((DMatrixFixed)variable, (DMatrix)M);
            this.alias(M, name);
        } else if (variable instanceof FMatrixFixed) {
            FMatrixRMaj M = new FMatrixRMaj(1, 1);
            FConvertMatrixStruct.convert((FMatrixFixed)variable, (FMatrix)M);
            this.alias(M, name);
        } else {
            throw new RuntimeException("Unknown value type of " + variable.getClass().getSimpleName() + " for variable " + name);
        }
    }

    public Sequence compile(String equation) {
        return this.compile(equation, true, false);
    }

    public Sequence compile(String equation, boolean assignment, boolean debug) {
        this.functions.setManagerTemp(this.managerTemp);
        Sequence sequence = new Sequence();
        TokenList tokens = this.extractTokens(equation, this.managerTemp);
        if (tokens.size() < 3) {
            throw new RuntimeException("Too few tokens");
        }
        TokenList.Token t0 = tokens.getFirst();
        if (t0.word != null && t0.word.compareToIgnoreCase("macro") == 0) {
            this.parseMacro(tokens, sequence);
        } else {
            this.insertFunctionsAndVariables(tokens);
            this.insertMacros(tokens);
            if (debug) {
                System.out.println("Parsed tokens:\n------------");
                tokens.print();
                System.out.println();
            }
            if (t0.getType() != TokenList.Type.VARIABLE && t0.getType() != TokenList.Type.WORD) {
                this.compileTokens(sequence, tokens);
                Variable variable = tokens.getFirst().getVariable();
                if (variable != null) {
                    if (assignment) {
                        throw new IllegalArgumentException("No assignment to an output variable could be found. Found " + t0);
                    }
                    sequence.output = variable;
                }
            } else {
                this.compileAssignment(sequence, tokens, t0);
            }
            if (debug) {
                System.out.println("Operations:\n------------");
                for (int i = 0; i < sequence.operations.size(); ++i) {
                    System.out.println(sequence.operations.get(i).name());
                }
            }
        }
        return sequence;
    }

    private void compileAssignment(Sequence sequence, TokenList tokens, TokenList.Token t0) {
        List<Variable> range = this.parseAssignRange(sequence, tokens, t0);
        TokenList.Token t1 = t0.next;
        if (t1.getType() != TokenList.Type.SYMBOL || t1.getSymbol() != Symbol.ASSIGN) {
            throw new ParseError("Expected assignment operator next");
        }
        TokenList tokensRight = tokens.extractSubList(t1.next, tokens.last);
        this.compileTokens(sequence, tokensRight);
        if (tokensRight.getLast().getType() != TokenList.Type.VARIABLE) {
            throw new RuntimeException("BUG the last token must be a variable");
        }
        Variable variableRight = tokensRight.getFirst().getVariable();
        if (range == null) {
            sequence.output = this.createVariableInferred(t0, variableRight);
            sequence.addOperation(Operation.copy(variableRight, sequence.output));
        } else {
            if (t0.getType() == TokenList.Type.WORD) {
                throw new ParseError("Can't do lazy variable initialization with submatrices. " + t0.getWord());
            }
            sequence.addOperation(Operation.copy(variableRight, t0.getVariable(), range));
        }
    }

    private void compileTokens(Sequence sequence, TokenList tokens) {
        this.checkForUnknownVariables(tokens);
        this.handleParentheses(tokens, sequence);
        if (tokens.size() > 1) {
            this.parseBlockNoParentheses(tokens, sequence, false);
        }
        if (tokens.size() != 1) {
            throw new RuntimeException("BUG");
        }
    }

    private void parseMacro(TokenList tokens, Sequence sequence) {
        Macro macro = new Macro();
        TokenList.Token t = tokens.getFirst().next;
        if (t.word == null) {
            throw new ParseError("Expected the macro's name after " + tokens.getFirst().word);
        }
        ArrayList<TokenList.Token> variableTokens = new ArrayList<TokenList.Token>();
        macro.name = t.word;
        t = t.next;
        t = this.parseMacroInput(variableTokens, t);
        for (TokenList.Token a : variableTokens) {
            if (a.word == null) {
                throw new ParseError("expected word in macro header");
            }
            macro.inputs.add(a.word);
        }
        t = t.next;
        if (t == null || t.getSymbol() != Symbol.ASSIGN) {
            throw new ParseError("Expected assignment");
        }
        t = t.next;
        macro.tokens = new TokenList(t, tokens.last);
        sequence.addOperation(macro.createOperation(this.macros));
    }

    private TokenList.Token parseMacroInput(List<TokenList.Token> variables, TokenList.Token t) {
        if (t.getSymbol() != Symbol.PAREN_LEFT) {
            throw new ParseError("Expected (");
        }
        t = t.next;
        boolean expectWord = true;
        while (t != null && t.getSymbol() != Symbol.PAREN_RIGHT) {
            if (expectWord) {
                variables.add(t);
                expectWord = false;
            } else {
                if (t.getSymbol() != Symbol.COMMA) {
                    throw new ParseError("Expected comma");
                }
                expectWord = true;
            }
            t = t.next;
        }
        if (t == null) {
            throw new ParseError("Token sequence ended unexpectedly");
        }
        return t;
    }

    private void checkForUnknownVariables(TokenList tokens) {
        TokenList.Token t = tokens.getFirst();
        while (t != null) {
            if (t.getType() == TokenList.Type.WORD) {
                throw new ParseError("Unknown variable on right side. " + t.getWord());
            }
            t = t.next;
        }
    }

    private Variable createVariableInferred(TokenList.Token t0, Variable variableRight) {
        Variable result;
        if (t0.getType() == TokenList.Type.WORD) {
            switch (variableRight.getType()) {
                case MATRIX: {
                    this.alias(new DMatrixRMaj(1, 1), t0.getWord());
                    break;
                }
                case SCALAR: {
                    if (variableRight instanceof VariableInteger) {
                        this.alias(0, t0.getWord());
                        break;
                    }
                    this.alias(1.0, t0.getWord());
                    break;
                }
                case INTEGER_SEQUENCE: {
                    this.alias((IntegerSequence)null, t0.getWord());
                    break;
                }
                default: {
                    throw new RuntimeException("Type not supported for assignment: " + (Object)((Object)variableRight.getType()));
                }
            }
            result = this.variables.get(t0.getWord());
        } else {
            result = t0.getVariable();
        }
        return result;
    }

    private List<Variable> parseAssignRange(Sequence sequence, TokenList tokens, TokenList.Token t0) {
        TokenList.Token tokenAssign = t0.next;
        while (tokenAssign != null && tokenAssign.symbol != Symbol.ASSIGN) {
            tokenAssign = tokenAssign.next;
        }
        if (tokenAssign == null) {
            throw new ParseError("Can't find assignment operator");
        }
        if (tokenAssign.previous.symbol == Symbol.PAREN_RIGHT) {
            TokenList.Token start = t0.next;
            if (start.symbol != Symbol.PAREN_LEFT) {
                throw new ParseError("Expected left param for assignment");
            }
            TokenList.Token end = tokenAssign.previous;
            TokenList subTokens = tokens.extractSubList(start, end);
            subTokens.remove(subTokens.getFirst());
            subTokens.remove(subTokens.getLast());
            this.handleParentheses(subTokens, sequence);
            List<TokenList.Token> inputs = this.parseParameterCommaBlock(subTokens, sequence);
            if (inputs.isEmpty()) {
                throw new ParseError("Empty function input parameters");
            }
            ArrayList<Variable> range = new ArrayList<Variable>();
            this.addSubMatrixVariables(inputs, range);
            if (range.size() != 1 && range.size() != 2) {
                throw new ParseError("Unexpected number of range variables.  1 or 2 expected");
            }
            return range;
        }
        return null;
    }

    protected void handleParentheses(TokenList tokens, Sequence sequence) {
        ArrayList<TokenList.Token> left = new ArrayList<TokenList.Token>();
        TokenList.Token t = tokens.first;
        while (t != null) {
            TokenList.Token next = t.next;
            if (t.getType() == TokenList.Type.SYMBOL) {
                if (t.getSymbol() == Symbol.PAREN_LEFT) {
                    left.add(t);
                } else if (t.getSymbol() == Symbol.PAREN_RIGHT) {
                    if (left.isEmpty()) {
                        throw new ParseError(") found with no matching (");
                    }
                    TokenList.Token a = (TokenList.Token)left.remove(left.size() - 1);
                    TokenList.Token before = a.previous;
                    TokenList sublist = tokens.extractSubList(a, t);
                    sublist.remove(sublist.first);
                    sublist.remove(sublist.last);
                    if (before != null && before.getType() == TokenList.Type.FUNCTION) {
                        List<TokenList.Token> inputs = this.parseParameterCommaBlock(sublist, sequence);
                        if (inputs.isEmpty()) {
                            throw new ParseError("Empty function input parameters");
                        }
                        this.createFunction(before, inputs, tokens, sequence);
                    } else if (before != null && before.getType() == TokenList.Type.VARIABLE && before.getVariable().getType() == VariableType.MATRIX) {
                        TokenList.Token extract = this.parseSubmatrixToExtract(before, sublist, sequence);
                        tokens.insert(before, extract);
                        tokens.remove(before);
                    } else {
                        TokenList.Token output = this.parseBlockNoParentheses(sublist, sequence, false);
                        if (output != null) {
                            tokens.insert(before, output);
                        }
                    }
                }
            }
            t = next;
        }
        if (!left.isEmpty()) {
            throw new ParseError("Dangling ( parentheses");
        }
    }

    protected List<TokenList.Token> parseParameterCommaBlock(TokenList tokens, Sequence sequence) {
        ArrayList<TokenList.Token> commas = new ArrayList<TokenList.Token>();
        TokenList.Token token = tokens.first;
        int numBracket = 0;
        while (token != null) {
            if (token.getType() == TokenList.Type.SYMBOL) {
                switch (token.getSymbol()) {
                    case COMMA: {
                        if (numBracket != 0) break;
                        commas.add(token);
                        break;
                    }
                    case BRACKET_LEFT: {
                        ++numBracket;
                        break;
                    }
                    case BRACKET_RIGHT: {
                        --numBracket;
                        break;
                    }
                }
            }
            token = token.next;
        }
        ArrayList<TokenList.Token> output = new ArrayList<TokenList.Token>();
        if (commas.isEmpty()) {
            output.add(this.parseBlockNoParentheses(tokens, sequence, false));
        } else {
            TokenList.Token before = tokens.first;
            for (int i = 0; i < commas.size(); ++i) {
                TokenList.Token after = (TokenList.Token)commas.get(i);
                if (before == after) {
                    throw new ParseError("No empty function inputs allowed!");
                }
                TokenList.Token tmp = after.next;
                TokenList sublist = tokens.extractSubList(before, after);
                sublist.remove(after);
                output.add(this.parseBlockNoParentheses(sublist, sequence, false));
                before = tmp;
            }
            if (before == null) {
                throw new ParseError("No empty function inputs allowed!");
            }
            TokenList.Token after = tokens.last;
            TokenList sublist = tokens.extractSubList(before, after);
            output.add(this.parseBlockNoParentheses(sublist, sequence, false));
        }
        return output;
    }

    protected TokenList.Token parseSubmatrixToExtract(TokenList.Token variableTarget, TokenList tokens, Sequence sequence) {
        Operation.Info info;
        List<TokenList.Token> inputs = this.parseParameterCommaBlock(tokens, sequence);
        ArrayList<Variable> variables = new ArrayList<Variable>();
        variables.add(variableTarget.getVariable());
        this.addSubMatrixVariables(inputs, variables);
        if (variables.size() != 2 && variables.size() != 3) {
            throw new ParseError("Unexpected number of variables.  1 or 2 expected");
        }
        if (inputs.size() == 1) {
            Variable varA = (Variable)variables.get(1);
            info = varA.getType() == VariableType.SCALAR ? this.functions.create("extractScalar", variables) : this.functions.create("extract", variables);
        } else if (inputs.size() == 2) {
            Variable varA = (Variable)variables.get(1);
            Variable varB = (Variable)variables.get(2);
            info = varA.getType() == VariableType.SCALAR && varB.getType() == VariableType.SCALAR ? this.functions.create("extractScalar", variables) : this.functions.create("extract", variables);
        } else {
            throw new ParseError("Expected 2 inputs to sub-matrix");
        }
        sequence.addOperation(info.op);
        return new TokenList.Token(info.output);
    }

    private void addSubMatrixVariables(List<TokenList.Token> inputs, List<Variable> variables) {
        for (int i = 0; i < inputs.size(); ++i) {
            TokenList.Token t = inputs.get(i);
            if (t.getType() != TokenList.Type.VARIABLE) {
                throw new ParseError("Expected variables only in sub-matrix input, not " + (Object)((Object)t.getType()));
            }
            Variable v = t.getVariable();
            if (v.getType() != VariableType.INTEGER_SEQUENCE && !Equation.isVariableInteger(t)) {
                throw new ParseError("Expected an integer, integer sequence, or array range to define a submatrix");
            }
            variables.add(v);
        }
    }

    protected TokenList.Token parseBlockNoParentheses(TokenList tokens, Sequence sequence, boolean insideMatrixConstructor) {
        if (!insideMatrixConstructor) {
            this.parseBracketCreateMatrix(tokens, sequence);
        }
        this.parseSequencesWithColons(tokens, sequence);
        this.parseNegOp(tokens, sequence);
        this.parseOperationsL(tokens, sequence);
        this.parseOperationsLR(new Symbol[]{Symbol.POWER, Symbol.ELEMENT_POWER}, tokens, sequence);
        this.parseOperationsLR(new Symbol[]{Symbol.TIMES, Symbol.RDIVIDE, Symbol.LDIVIDE, Symbol.ELEMENT_TIMES, Symbol.ELEMENT_DIVIDE}, tokens, sequence);
        this.parseOperationsLR(new Symbol[]{Symbol.PLUS, Symbol.MINUS}, tokens, sequence);
        this.stripCommas(tokens);
        this.parseIntegerLists(tokens);
        this.parseCombineIntegerLists(tokens);
        if (!insideMatrixConstructor) {
            if (tokens.size() > 1) {
                System.err.println("Remaining tokens: " + tokens.size);
                TokenList.Token t = tokens.first;
                while (t != null) {
                    System.err.println("  " + t);
                    t = t.next;
                }
                throw new RuntimeException("BUG in parser.  There should only be a single token left");
            }
            return tokens.first;
        }
        return null;
    }

    private void stripCommas(TokenList tokens) {
        TokenList.Token t = tokens.getFirst();
        while (t != null) {
            TokenList.Token next = t.next;
            if (t.getSymbol() == Symbol.COMMA) {
                tokens.remove(t);
            }
            t = next;
        }
    }

    protected void parseSequencesWithColons(TokenList tokens, Sequence sequence) {
        TokenList.Token t = tokens.getFirst();
        if (t == null) {
            return;
        }
        int state = 0;
        TokenList.Token start = null;
        TokenList.Token middle = null;
        TokenList.Token prev = t;
        boolean last = false;
        while (true) {
            IntegerSequence numbers;
            VariableIntegerSequence varSequence;
            IntegerSequence.Range range;
            if (state == 0) {
                if (Equation.isVariableInteger(t) && t.next != null && t.next.getSymbol() == Symbol.COLON) {
                    start = t;
                    state = 1;
                    t = t.next;
                } else if (t != null && t.getSymbol() == Symbol.COLON) {
                    range = new IntegerSequence.Range(null, null);
                    varSequence = this.functions.getManagerTemp().createIntegerSequence(range);
                    TokenList.Token n = new TokenList.Token(varSequence);
                    tokens.insert(t.previous, n);
                    tokens.remove(t);
                    t = n;
                }
            } else if (state == 1) {
                if (Equation.isVariableInteger(t)) {
                    state = 2;
                } else {
                    range = new IntegerSequence.Range(start, null);
                    varSequence = this.functions.getManagerTemp().createIntegerSequence(range);
                    this.replaceSequence(tokens, varSequence, start, prev);
                    state = 0;
                }
            } else if (state == 2) {
                if (t != null && t.getSymbol() == Symbol.COLON) {
                    middle = prev;
                    state = 3;
                } else {
                    numbers = new IntegerSequence.For(start, null, prev);
                    varSequence = this.functions.getManagerTemp().createIntegerSequence(numbers);
                    this.replaceSequence(tokens, varSequence, start, prev);
                    if (t != null) {
                        t = t.previous;
                    }
                    state = 0;
                }
            } else if (state == 3) {
                if (Equation.isVariableInteger(t)) {
                    numbers = new IntegerSequence.For(start, middle, t);
                    varSequence = this.functions.getManagerTemp().createIntegerSequence(numbers);
                    t = this.replaceSequence(tokens, varSequence, start, t);
                } else {
                    numbers = new IntegerSequence.Range(start, middle);
                    varSequence = this.functions.getManagerTemp().createIntegerSequence(numbers);
                    this.replaceSequence(tokens, varSequence, start, prev);
                }
                state = 0;
            }
            if (last) break;
            if (t.next == null) {
                last = true;
            }
            prev = t;
            t = t.next;
        }
    }

    protected void parseIntegerLists(TokenList tokens) {
        TokenList.Token t = tokens.getFirst();
        if (t == null || t.next == null) {
            return;
        }
        int state = 0;
        TokenList.Token start = null;
        TokenList.Token prev = t;
        boolean last = false;
        while (true) {
            if (state == 0) {
                if (Equation.isVariableInteger(t)) {
                    start = t;
                    state = 1;
                }
            } else if (state == 1) {
                state = Equation.isVariableInteger(t) ? 2 : 0;
            } else if (state == 2 && !Equation.isVariableInteger(t)) {
                IntegerSequence.Explicit sequence = new IntegerSequence.Explicit(start, prev);
                VariableIntegerSequence varSequence = this.functions.getManagerTemp().createIntegerSequence(sequence);
                this.replaceSequence(tokens, varSequence, start, prev);
                state = 0;
            }
            if (last) break;
            if (t.next == null) {
                last = true;
            }
            prev = t;
            t = t.next;
        }
    }

    protected void parseCombineIntegerLists(TokenList tokens) {
        VariableIntegerSequence varSequence;
        IntegerSequence.Combined sequence;
        TokenList.Token t = tokens.getFirst();
        if (t == null || t.next == null) {
            return;
        }
        int numFound = 0;
        TokenList.Token start = null;
        TokenList.Token end = null;
        while (t != null) {
            if (t.getType() == TokenList.Type.VARIABLE && (Equation.isVariableInteger(t) || t.getVariable().getType() == VariableType.INTEGER_SEQUENCE)) {
                if (numFound == 0) {
                    numFound = 1;
                    start = end = t;
                } else {
                    ++numFound;
                    end = t;
                }
            } else if (numFound > 1) {
                sequence = new IntegerSequence.Combined(start, end);
                varSequence = this.functions.getManagerTemp().createIntegerSequence(sequence);
                this.replaceSequence(tokens, varSequence, start, end);
                numFound = 0;
            } else {
                numFound = 0;
            }
            t = t.next;
        }
        if (numFound > 1) {
            sequence = new IntegerSequence.Combined(start, end);
            varSequence = this.functions.getManagerTemp().createIntegerSequence(sequence);
            this.replaceSequence(tokens, varSequence, start, end);
        }
    }

    private TokenList.Token replaceSequence(TokenList tokens, Variable target, TokenList.Token start, TokenList.Token end) {
        TokenList.Token tmp = new TokenList.Token(target);
        tokens.insert(start.previous, tmp);
        tokens.extractSubList(start, end);
        return tmp;
    }

    private static boolean isVariableInteger(TokenList.Token t) {
        if (t == null) {
            return false;
        }
        return t.getScalarType() == VariableScalar.Type.INTEGER;
    }

    protected void parseBracketCreateMatrix(TokenList tokens, Sequence sequence) {
        ArrayList<TokenList.Token> left = new ArrayList<TokenList.Token>();
        TokenList.Token t = tokens.getFirst();
        while (t != null) {
            TokenList.Token next = t.next;
            if (t.getSymbol() == Symbol.BRACKET_LEFT) {
                left.add(t);
            } else if (t.getSymbol() == Symbol.BRACKET_RIGHT) {
                if (left.isEmpty()) {
                    throw new RuntimeException("No matching left bracket for right");
                }
                TokenList.Token start = (TokenList.Token)left.remove(left.size() - 1);
                TokenList bracketLet = tokens.extractSubList(start.next, t.previous);
                this.parseBlockNoParentheses(bracketLet, sequence, true);
                MatrixConstructor constructor = this.constructMatrix(bracketLet);
                Operation.Info info = Operation.matrixConstructor(constructor);
                sequence.addOperation(info.op);
                tokens.insert(start.previous, new TokenList.Token(info.output));
                tokens.remove(start);
                tokens.remove(t);
            }
            t = next;
        }
        if (!left.isEmpty()) {
            throw new RuntimeException("Dangling [");
        }
    }

    private MatrixConstructor constructMatrix(TokenList bracketLet) {
        MatrixConstructor constructor = new MatrixConstructor(this.functions.getManagerTemp());
        TokenList.Token n = bracketLet.first;
        while (n != null) {
            if (n.getType() == TokenList.Type.VARIABLE) {
                constructor.addToRow(n.getVariable());
            } else if (n.getType() == TokenList.Type.SYMBOL) {
                if (n.getSymbol() == Symbol.SEMICOLON) {
                    constructor.endRow();
                }
            } else {
                throw new ParseError("Expected variable or symbol only");
            }
            n = n.next;
        }
        constructor.endRow();
        return constructor;
    }

    protected void parseNegOp(TokenList tokens, Sequence sequence) {
        if (tokens.size == 0) {
            return;
        }
        TokenList.Token token = tokens.first;
        while (token != null) {
            TokenList.Token next = token.next;
            if (!(token.getSymbol() != Symbol.MINUS || token.previous != null && token.previous.getType() != TokenList.Type.SYMBOL || token.previous != null && token.previous.getType() == TokenList.Type.SYMBOL && token.previous.symbol == Symbol.TRANSPOSE || token.next == null || token.next.getType() == TokenList.Type.SYMBOL)) {
                if (token.next.getType() != TokenList.Type.VARIABLE) {
                    throw new RuntimeException("Crap bug rethink this function");
                }
                Operation.Info info = Operation.neg(token.next.getVariable(), this.functions.getManagerTemp());
                sequence.addOperation(info.op);
                TokenList.Token t = new TokenList.Token(info.output);
                tokens.insert(token.next, t);
                tokens.remove(token.next);
                tokens.remove(token);
                next = t;
            }
            token = next;
        }
    }

    protected void parseOperationsL(TokenList tokens, Sequence sequence) {
        if (tokens.size == 0) {
            return;
        }
        TokenList.Token token = tokens.first;
        if (token.getType() != TokenList.Type.VARIABLE) {
            throw new ParseError("The first token in an equation needs to be a variable and not " + token);
        }
        while (token != null) {
            if (token.getType() == TokenList.Type.FUNCTION) {
                throw new ParseError("Function encountered with no parentheses");
            }
            if (token.getType() == TokenList.Type.SYMBOL && token.getSymbol() == Symbol.TRANSPOSE) {
                if (token.previous.getType() == TokenList.Type.VARIABLE) {
                    token = this.insertTranspose(token.previous, tokens, sequence);
                } else {
                    throw new ParseError("Expected variable before transpose");
                }
            }
            token = token.next;
        }
    }

    protected void parseOperationsLR(Symbol[] ops, TokenList tokens, Sequence sequence) {
        if (tokens.size == 0) {
            return;
        }
        TokenList.Token token = tokens.first;
        if (token.getType() != TokenList.Type.VARIABLE) {
            throw new ParseError("The first token in an equation needs to be a variable and not " + token);
        }
        boolean hasLeft = false;
        while (token != null) {
            if (token.getType() == TokenList.Type.FUNCTION) {
                throw new ParseError("Function encountered with no parentheses");
            }
            if (token.getType() == TokenList.Type.VARIABLE) {
                if (hasLeft) {
                    if (Equation.isTargetOp(token.previous, ops)) {
                        token = this.createOp(token.previous.previous, token.previous, token, tokens, sequence);
                    }
                } else {
                    hasLeft = true;
                }
            } else if (token.previous.getType() == TokenList.Type.SYMBOL) {
                throw new ParseError("Two symbols next to each other. " + token.previous + " and " + token);
            }
            token = token.next;
        }
    }

    protected TokenList.Token insertTranspose(TokenList.Token variable, TokenList tokens, Sequence sequence) {
        Operation.Info info = this.functions.create('\'', variable.getVariable());
        sequence.addOperation(info.op);
        TokenList.Token t = new TokenList.Token(info.output);
        tokens.remove(variable.next);
        tokens.replace(variable, t);
        return t;
    }

    protected TokenList.Token createOp(TokenList.Token left, TokenList.Token op, TokenList.Token right, TokenList tokens, Sequence sequence) {
        Operation.Info info = this.functions.create(op.symbol, left.getVariable(), right.getVariable());
        sequence.addOperation(info.op);
        TokenList.Token t = new TokenList.Token(info.output);
        tokens.remove(left);
        tokens.remove(right);
        tokens.replace(op, t);
        return t;
    }

    protected TokenList.Token createFunction(TokenList.Token name, List<TokenList.Token> inputs, TokenList tokens, Sequence sequence) {
        Operation.Info info;
        if (inputs.size() == 1) {
            info = this.functions.create(name.getFunction().getName(), inputs.get(0).getVariable());
        } else {
            ArrayList<Variable> vars = new ArrayList<Variable>();
            for (int i = 0; i < inputs.size(); ++i) {
                vars.add(inputs.get(i).getVariable());
            }
            info = this.functions.create(name.getFunction().getName(), vars);
        }
        sequence.addOperation(info.op);
        TokenList.Token t = new TokenList.Token(info.output);
        tokens.replace(name, t);
        return t;
    }

    public <T extends Variable> T lookupVariable(String token) {
        Variable result = this.variables.get(token);
        return (T)result;
    }

    public Macro lookupMacro(String token) {
        return this.macros.get(token);
    }

    public DMatrixRMaj lookupDDRM(String token) {
        return ((VariableMatrix)this.variables.get((Object)token)).matrix;
    }

    public FMatrixRMaj lookupFDRM(String token) {
        DMatrixRMaj d = ((VariableMatrix)this.variables.get((Object)token)).matrix;
        FMatrixRMaj f = new FMatrixRMaj(d.numRows, d.numCols);
        ConvertMatrixData.convert(d, f);
        return f;
    }

    public int lookupInteger(String token) {
        return ((VariableInteger)this.variables.get((Object)token)).value;
    }

    public double lookupDouble(String token) {
        Variable v = this.variables.get(token);
        if (v instanceof VariableMatrix) {
            DMatrixRMaj m = ((VariableMatrix)v).matrix;
            if (m.getNumCols() == 1 && m.getNumRows() == 1) {
                return m.get(0, 0);
            }
            throw new RuntimeException("Can only return 1x1 real matrices as doubles");
        }
        return ((VariableScalar)this.variables.get(token)).getDouble();
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    protected TokenList extractTokens(String equation, ManagerTempVariables managerTemp) {
        equation = equation + " ";
        TokenList tokens = new TokenList();
        int length = 0;
        TokenType type = TokenType.UNKNOWN;
        for (int i = 0; i < equation.length(); ++i) {
            boolean again = false;
            char c = equation.charAt(i);
            if (type == TokenType.WORD) {
                if (Equation.isLetter(c)) {
                    this.storage[length++] = c;
                } else {
                    String name = new String(this.storage, 0, length);
                    tokens.add(name);
                    type = TokenType.UNKNOWN;
                    again = true;
                }
            } else if (type == TokenType.INTEGER) {
                if (c == '.') {
                    type = TokenType.FLOAT;
                    this.storage[length++] = c;
                } else if (c == 'e' || c == 'E') {
                    type = TokenType.FLOAT_EXP;
                    this.storage[length++] = c;
                } else if (Character.isDigit(c)) {
                    this.storage[length++] = c;
                } else {
                    if (!Equation.isSymbol(c) && !Character.isWhitespace(c)) throw new ParseError("Unexpected character at the end of an integer " + c);
                    int value = Integer.parseInt(new String(this.storage, 0, length));
                    tokens.add(managerTemp.createInteger(value));
                    type = TokenType.UNKNOWN;
                    again = true;
                }
            } else if (type == TokenType.FLOAT) {
                if (c == '.') {
                    throw new ParseError("Unexpected '.' in a float");
                }
                if (c == 'e' || c == 'E') {
                    this.storage[length++] = c;
                    type = TokenType.FLOAT_EXP;
                } else if (Character.isDigit(c)) {
                    this.storage[length++] = c;
                } else {
                    if (!Equation.isSymbol(c) && !Character.isWhitespace(c)) throw new ParseError("Unexpected character at the end of an float " + c);
                    double value = Double.parseDouble(new String(this.storage, 0, length));
                    tokens.add(managerTemp.createDouble(value));
                    type = TokenType.UNKNOWN;
                    again = true;
                }
            } else if (type == TokenType.FLOAT_EXP) {
                boolean end = false;
                if (c == '-') {
                    char p = this.storage[length - 1];
                    if (p == 'e' || p == 'E') {
                        this.storage[length++] = c;
                    } else {
                        end = true;
                    }
                } else if (Character.isDigit(c)) {
                    this.storage[length++] = c;
                } else {
                    if (!Equation.isSymbol(c) && !Character.isWhitespace(c)) throw new ParseError("Unexpected character at the end of an float " + c);
                    end = true;
                }
                if (end) {
                    double value = Double.parseDouble(new String(this.storage, 0, length));
                    tokens.add(managerTemp.createDouble(value));
                    type = TokenType.UNKNOWN;
                    again = true;
                }
            } else if (Equation.isSymbol(c)) {
                boolean special = false;
                if (c == '-' && i + 1 < equation.length() && Character.isDigit(equation.charAt(i + 1)) && (tokens.last == null || Equation.isOperatorLR(tokens.last.getSymbol()))) {
                    type = TokenType.INTEGER;
                    this.storage[0] = c;
                    length = 1;
                    special = true;
                }
                if (!special) {
                    TokenList.Token t = tokens.add(Symbol.lookup(c));
                    if (t.previous != null && t.previous.getType() == TokenList.Type.SYMBOL && t.previous.getSymbol() == Symbol.PERIOD) {
                        tokens.remove(t.previous);
                        tokens.remove(t);
                        tokens.add(Symbol.lookupElementWise(c));
                    }
                }
            } else {
                if (Character.isWhitespace(c)) continue;
                type = Character.isDigit(c) ? TokenType.INTEGER : TokenType.WORD;
                this.storage[0] = c;
                length = 1;
            }
            if (!again) continue;
            --i;
        }
        return tokens;
    }

    void insertFunctionsAndVariables(TokenList tokens) {
        TokenList.Token t = tokens.getFirst();
        while (t != null) {
            if (t.getType() == TokenList.Type.WORD) {
                Object v = this.lookupVariable(t.word);
                if (v != null) {
                    t.variable = v;
                    t.word = null;
                } else if (this.functions.isFunctionName(t.word)) {
                    t.function = new Function(t.word);
                    t.word = null;
                }
            }
            t = t.next;
        }
    }

    void insertMacros(TokenList tokens) {
        TokenList.Token t = tokens.getFirst();
        while (t != null) {
            Macro v;
            if (t.getType() == TokenList.Type.WORD && (v = this.lookupMacro(t.word)) != null) {
                TokenList.Token before = t.previous;
                ArrayList<TokenList.Token> inputs = new ArrayList<TokenList.Token>();
                t = this.parseMacroInput(inputs, t.next);
                TokenList sniplet = v.execute(inputs);
                tokens.extractSubList(before.next, t);
                tokens.insertAfter(before, sniplet);
                t = sniplet.last;
            }
            t = t.next;
        }
    }

    public SimpleMatrix lookupSimple(String token) {
        return SimpleMatrix.wrap(this.lookupDDRM(token));
    }

    protected static boolean isTargetOp(TokenList.Token token, Symbol[] ops) {
        Symbol c = token.symbol;
        for (int i = 0; i < ops.length; ++i) {
            if (c != ops[i]) continue;
            return true;
        }
        return false;
    }

    protected static boolean isSymbol(char c) {
        return c == '*' || c == '/' || c == '+' || c == '-' || c == '(' || c == ')' || c == '[' || c == ']' || c == '=' || c == '\'' || c == '.' || c == ',' || c == ':' || c == ';' || c == '\\' || c == '^';
    }

    protected static boolean isOperatorLR(Symbol s) {
        if (s == null) {
            return false;
        }
        switch (s) {
            case ELEMENT_DIVIDE: 
            case ELEMENT_TIMES: 
            case ELEMENT_POWER: 
            case RDIVIDE: 
            case LDIVIDE: 
            case TIMES: 
            case POWER: 
            case PLUS: 
            case MINUS: 
            case ASSIGN: {
                return true;
            }
        }
        return false;
    }

    protected static boolean isLetter(char c) {
        return !Equation.isSymbol(c) && !Character.isWhitespace(c);
    }

    protected boolean isReserved(String name) {
        if (this.functions.isFunctionName(name)) {
            return true;
        }
        for (int i = 0; i < name.length(); ++i) {
            if (Equation.isLetter(name.charAt(i))) continue;
            return true;
        }
        return false;
    }

    public Equation process(String equation) {
        this.compile(equation).perform();
        return this;
    }

    public Equation process(String equation, boolean debug) {
        this.compile(equation, true, debug).perform();
        return this;
    }

    public void print(String equation) {
        Object v = this.lookupVariable(equation);
        if (v == null) {
            Sequence sequence = this.compile(equation, false, false);
            sequence.perform();
            v = sequence.output;
        }
        if (v instanceof VariableMatrix) {
            ((VariableMatrix)v).matrix.print();
        } else if (v instanceof VariableScalar) {
            System.out.println("Scalar = " + ((VariableScalar)v).getDouble());
        } else {
            System.out.println("Add support for " + v.getClass().getSimpleName());
        }
    }

    public ManagerFunctions getFunctions() {
        return this.functions;
    }

    protected static final class TokenType
    extends Enum<TokenType> {
        public static final /* enum */ TokenType WORD = new TokenType();
        public static final /* enum */ TokenType INTEGER = new TokenType();
        public static final /* enum */ TokenType FLOAT = new TokenType();
        public static final /* enum */ TokenType FLOAT_EXP = new TokenType();
        public static final /* enum */ TokenType UNKNOWN = new TokenType();
        private static final /* synthetic */ TokenType[] $VALUES;

        public static TokenType[] values() {
            return (TokenType[])$VALUES.clone();
        }

        public static TokenType valueOf(String name) {
            return Enum.valueOf(TokenType.class, name);
        }

        private static /* synthetic */ TokenType[] $values() {
            return new TokenType[]{WORD, INTEGER, FLOAT, FLOAT_EXP, UNKNOWN};
        }

        static {
            $VALUES = TokenType.$values();
        }
    }
}

