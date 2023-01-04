package org.compilers.grammar.model.vocabulary.nonterminal;

import org.compilers.grammar.model.vocabulary.Symbol;

public interface NonTerminal extends Symbol {
    static boolean isNonTerminal(final Symbol symbol) {
        return symbol instanceof NonTerminal;
    }
}
