package org.compilers.grammar.model.grammar.vocabulary.nonterminal;

import org.compilers.grammar.model.grammar.vocabulary.Symbol;

public interface NonTerminal extends Symbol {
    static boolean isNonTerminal(final Symbol symbol) {
        return symbol instanceof NonTerminal;
    }
}
