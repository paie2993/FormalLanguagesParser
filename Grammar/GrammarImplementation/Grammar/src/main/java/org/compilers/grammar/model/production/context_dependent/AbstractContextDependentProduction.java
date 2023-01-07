package org.compilers.grammar.model.production.context_dependent;

import org.compilers.grammar.model.production.unrestricted.AbstractUnrestrictedProduction;
import org.compilers.grammar.model.vocabulary.Symbol;
import org.compilers.grammar.model.vocabulary.nonterminal.NonTerminal;

import java.util.List;
import java.util.Objects;

public abstract class AbstractContextDependentProduction extends AbstractUnrestrictedProduction {

    public AbstractContextDependentProduction(
            final List<? extends Symbol> leftSide,
            final List<? extends Symbol> rightSide
    ) {
        super(leftSide, rightSide);
        validateLeftSide(leftSide);
        validateRightSide(rightSide);
    }

    // validators
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    private static void validateLeftSide(final List<? extends Symbol> leftSide) {
        Objects.requireNonNull(leftSide);
        if (leftSide.isEmpty()) {
            throw new IllegalArgumentException("Left side cannot be empty: " + leftSide);
        }
        final var noNonTerminal = leftSide.stream().noneMatch(NonTerminal::isInstance);
        if (noNonTerminal) {
            throw new IllegalArgumentException("Left side of context dependent production must contain at least one non-terminal symbol");
        }
    }

    private static void validateRightSide(final List<? extends Symbol> rightSide) {
        Objects.requireNonNull(rightSide);
    }
}
