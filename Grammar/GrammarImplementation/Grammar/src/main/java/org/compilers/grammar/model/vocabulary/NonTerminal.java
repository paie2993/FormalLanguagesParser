package org.compilers.grammar.model.vocabulary;

public final class NonTerminal extends Symbol {

    public NonTerminal(String value) {
        super(value);
    }

    public static boolean isNonTerminal(Symbol symbol) {
        return symbol instanceof NonTerminal;
    }
}
