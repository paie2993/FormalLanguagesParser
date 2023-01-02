package org.compilers.grammar.model.vocabulary;

public interface Terminal extends Symbol {
    static boolean isTerminal(final Symbol symbol) {
        return symbol instanceof Terminal;
    }
}
