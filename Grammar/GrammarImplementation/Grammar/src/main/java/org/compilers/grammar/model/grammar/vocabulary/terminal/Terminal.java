package org.compilers.grammar.model.grammar.vocabulary.terminal;

import org.compilers.grammar.model.grammar.vocabulary.Symbol;

public interface Terminal extends Symbol {
    static boolean isTerminal(final Symbol symbol) {
        return symbol instanceof Terminal;
    }
}
