package org.compilers.grammar.model.production;

import org.compilers.grammar.model.vocabulary.NonTerminal;
import org.compilers.grammar.model.vocabulary.Symbol;

import java.util.List;
import java.util.Objects;

public final class Production {

    public static final String SIDES_SEPARATOR = " -> ";

    private final List<Symbol> leftSide;
    private final List<Symbol> rightSide;

    public Production(final List<Symbol> leftSide, final List<Symbol> rightSide) {
        Objects.requireNonNull(leftSide);
        Objects.requireNonNull(rightSide);

        Production.validateLeftSide(leftSide);
        Production.validateSide(rightSide);

        this.leftSide = leftSide;
        this.rightSide = rightSide;
    }


    public List<Symbol> leftSide() {
        return leftSide;
    }

    public List<Symbol> rightSide() {
        return rightSide;
    }

    @Override
    public String toString() {
        return prepareSideForPrinting(leftSide) + SIDES_SEPARATOR + prepareSideForPrinting(rightSide);
    }

    private static String prepareSideForPrinting(final List<Symbol> list) {
        final var builder = new StringBuilder();
        list.forEach(builder::append);
        return builder.toString();
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    private static void validateLeftSide(final List<Symbol> leftSide) {
        validateSide(leftSide);
        containsNonTerminal(leftSide);
    }

    private static void validateSide(final List<Symbol> side) {
        if (side.isEmpty()) {
            throw new IllegalArgumentException("Side of production can't be empty");
        }
    }

    private static void containsNonTerminal(final List<Symbol> side) {
        final var hasNoNonTerminal = side.stream().map(Object::getClass).noneMatch(NonTerminal.class::equals);
        if (hasNoNonTerminal) {
            throw new IllegalArgumentException("Left side of production must contain at least one non-terminal symbol");
        }
    }
}
