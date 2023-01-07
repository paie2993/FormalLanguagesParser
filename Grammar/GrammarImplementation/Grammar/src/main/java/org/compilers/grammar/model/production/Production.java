package org.compilers.grammar.model.production;

import org.compilers.grammar.model.vocabulary.NonTerminal;
import org.compilers.grammar.model.vocabulary.Symbol;

import java.util.List;
import java.util.Objects;

public final class Production {

    public static final String SIDES_SEPARATOR = " -> ";

    private final List<? extends Symbol> leftSide;
    private final List<? extends Symbol> rightSide;

    // constructors
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    public Production(final List<? extends Symbol> leftSide, final List<? extends Symbol> rightSide) {
        Objects.requireNonNull(leftSide);
        Objects.requireNonNull(rightSide);

        Production.validateLeftSide(leftSide);
        Production.validateSide(rightSide);

        this.leftSide = leftSide;
        this.rightSide = rightSide;
    }

    public Production(final NonTerminal leftSide, final Symbol rightSide) {
        Objects.requireNonNull(leftSide);
        Objects.requireNonNull(rightSide);

        final var leftSideList = List.of(leftSide);
        final var rightSideList = List.of(rightSide);

        Production.validateLeftSide(leftSideList);
        Production.validateSide(rightSideList);

        this.leftSide = leftSideList;
        this.rightSide = rightSideList;
    }


    // getters
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    public List<? extends Symbol> leftSide() {
        return leftSide;
    }

    public List<? extends Symbol> rightSide() {
        return rightSide;
    }

    // general util
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    @Override
    public String toString() {
        return prepareSideForPrinting(leftSide) + SIDES_SEPARATOR + prepareSideForPrinting(rightSide);
    }

    private static String prepareSideForPrinting(final List<? extends Symbol> list) {
        final var builder = new StringBuilder();
        list.forEach(builder::append);
        return builder.toString();
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Production other)) { // used 'instanceof' because we can cast automatically while checking the class
            return false;
        }
        return other.leftSide.equals(this.leftSide) && other.rightSide.equals(this.rightSide);
    }

    @Override
    public int hashCode() {
        var hash = 7;
        hash = 31 * hash + leftSide.hashCode();
        hash = 31 * hash + rightSide.hashCode();
        return hash;
    }

    // validators (checking if productions have a correct structure)
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    // the left side must not be empty and must contain at least one non-terminal
    private static void validateLeftSide(final List<? extends Symbol> leftSide) {
        validateSide(leftSide);
        containsNonTerminal(leftSide);
    }

    // checks if the given side is not empty
    private static void validateSide(final List<? extends Symbol> side) {
        if (side.isEmpty()) {
            throw new IllegalArgumentException("Side of production can't be empty");
        }
    }

    // checks if the given side has at least a non-terminal
    // it's useful for validating the left side of a production, because a left side must contain at least one non-terminal
    private static void containsNonTerminal(final List<? extends Symbol> side) {
        final var hasNoNonTerminal = side.stream().map(Object::getClass).noneMatch(NonTerminal.class::equals);
        if (hasNoNonTerminal) {
            throw new IllegalArgumentException("Left side of production must contain at least one non-terminal symbol");
        }
    }
}
