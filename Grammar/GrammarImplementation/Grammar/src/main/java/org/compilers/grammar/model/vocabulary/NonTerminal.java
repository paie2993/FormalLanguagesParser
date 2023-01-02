package org.compilers.grammar.model.vocabulary;

public interface NonTerminal extends Symbol {
    static boolean isNonTerminal(final Symbol symbol) {
        return symbol instanceof NonTerminal;
    }
}
