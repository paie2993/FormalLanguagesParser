package org.compilers.grammar.model.production.context_free;

import org.compilers.grammar.model.production.context_dependent.AbstractContextDependentProduction;
import org.compilers.grammar.model.vocabulary.Symbol;
import org.compilers.grammar.model.vocabulary.nonterminal.NonTerminal;

import java.util.List;
import java.util.Objects;

public abstract class AbstractContextFreeProduction extends AbstractContextDependentProduction {

    public AbstractContextFreeProduction(
            final List<? extends Symbol> leftSide,
            final List<? extends Symbol> rightSide
    ) {
        super(leftSide, rightSide);
        validateLeftSide(leftSide);
        validateRightSide(rightSide);
    }

    abstract public NonTerminal leftSideNonTerminal();

    // validators
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    private static void validateLeftSide(final List<? extends Symbol> leftSide) {
        Objects.requireNonNull(leftSide);
        if (leftSide.isEmpty()) {
            throw new IllegalArgumentException("Left side of context free production must not be empty");
        }
        final var noNonTerminal = leftSide.stream().noneMatch(NonTerminal::isInstance);
        if (noNonTerminal) {
            throw new IllegalArgumentException("Left side of context free production must contain at least one non-terminal symbol");
        }
        if (leftSide.size() != 1) {
            throw new IllegalArgumentException("Left side of context free production must contain exactly one non-terminal");
        }
    }

    private static void validateRightSide(final List<? extends Symbol> rightSide) {
        Objects.requireNonNull(rightSide);
    }
}
