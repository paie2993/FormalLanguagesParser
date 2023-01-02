package org.compilers.grammar.model.grammar.production.linear;

import org.compilers.grammar.model.grammar.production.context_free.ContextFreeProduction;
import org.compilers.grammar.model.grammar.vocabulary.NonTerminal;
import org.compilers.grammar.model.grammar.vocabulary.Symbol;

import java.util.List;

public interface LinearProduction extends ContextFreeProduction {
    static void validateLeftSide(final List<Symbol> side) {
        ContextFreeProduction.validateLeftSide(side);
    }

    static void validateRightSide(final List<Symbol> side) {
        ContextFreeProduction.validateRightSide(side);
        final long noNonterminals = side.stream().filter(NonTerminal::isNonTerminal).count();
        if (noNonterminals >= 2) {
            throw new IllegalArgumentException("Right side of linear production must contain at most one non-terminal symbol");
        }
    }
}
