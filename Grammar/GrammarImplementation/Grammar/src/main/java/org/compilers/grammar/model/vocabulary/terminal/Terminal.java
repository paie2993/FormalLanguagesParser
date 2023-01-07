package org.compilers.grammar.model.vocabulary.terminal;

import org.compilers.grammar.model.vocabulary.Symbol;

public interface Terminal extends Symbol {
    static boolean isTerminal(final Symbol symbol) {
        return symbol instanceof Terminal;
    }
}
