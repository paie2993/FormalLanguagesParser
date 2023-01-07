package org.compilers.grammar.model.production.unrestricted;

import org.compilers.grammar.model.production.AbstractProduction;
import org.compilers.grammar.model.vocabulary.Symbol;

import java.util.List;
import java.util.Objects;

public abstract class AbstractUnrestrictedProduction extends AbstractProduction {

    public AbstractUnrestrictedProduction(
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
            throw new IllegalArgumentException("Left side of unrestricted production must not be empty");
        }
    } // good

    private static void validateRightSide(final List<? extends Symbol> rightSide) {
        Objects.requireNonNull(rightSide);
    } // good
}
