package org.compilers.grammar.model.vocabulary;

public final class Terminal extends Symbol {

    public Terminal(String value) {
        super(value);
    }

    public static boolean isTerminal(Symbol symbol) {
        return symbol instanceof Terminal;
    }
}
