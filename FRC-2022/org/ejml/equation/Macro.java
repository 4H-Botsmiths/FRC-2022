/*
 * Decompiled with CFR 0.150.
 */
package org.ejml.equation;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import org.ejml.equation.Operation;
import org.ejml.equation.TokenList;

public class Macro {
    String name;
    List<String> inputs = new ArrayList<String>();
    TokenList tokens;

    public TokenList execute(List<TokenList.Token> replacements) {
        TokenList output = new TokenList();
        TokenList.Token t = this.tokens.getFirst();
        while (t != null) {
            if (t.word != null) {
                boolean matched = false;
                for (int i = 0; i < this.inputs.size(); ++i) {
                    if (!this.inputs.get(i).equals(t.word)) continue;
                    output.insert(output.last, replacements.get(i).copy());
                    matched = true;
                    break;
                }
                if (!matched) {
                    output.insert(output.last, t.copy());
                }
            } else {
                output.insert(output.last, t.copy());
            }
            t = t.next;
        }
        return output;
    }

    public Operation createOperation(HashMap<String, Macro> macros) {
        return new Assign(macros);
    }

    public class Assign
    extends Operation {
        HashMap<String, Macro> macros;

        protected Assign(HashMap<String, Macro> macros) {
            super("Macro:" + Macro.this.name);
            this.macros = macros;
        }

        @Override
        public void process() {
            this.macros.put(Macro.this.name, Macro.this);
        }
    }
}

