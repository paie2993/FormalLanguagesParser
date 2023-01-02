package org.compilers.grammar.model.grammar.production;

import org.compilers.grammar.model.grammar.vocabulary.NonTerminal;
import org.compilers.grammar.model.grammar.vocabulary.Symbol;

import java.util.List;

public interface ContextDependentProduction extends UnrestrictedProduction {
    static void validateLeftSide(final List<Symbol> side) {
        UnrestrictedProduction.validateLeftSide(side);
        final boolean hasNoNonTerminal = side.stream().noneMatch(NonTerminal::isNonTerminal);
        if (hasNoNonTerminal) {
            throw new IllegalArgumentException("Left side of context dependent production must contain at least one non-terminal symbol");
        }
    }

    static void validateRightSide(final List<Symbol> side) {
        UnrestrictedProduction.validateRightSide(side);
    }
}
