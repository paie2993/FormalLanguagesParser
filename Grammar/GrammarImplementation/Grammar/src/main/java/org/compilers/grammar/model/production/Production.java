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
        Production.validateLeftSide(leftSide);
        Production.validateSide(rightSide);

        this.leftSide = leftSide;
        this.rightSide = rightSide;
    }

    @Override
    public String toString() {
        return prepareListForPrinting(leftSide) + SIDES_SEPARATOR + prepareListForPrinting(rightSide);
    }

    private static String prepareListForPrinting(final List<Symbol> list) {
        final var builder = new StringBuilder();
        list.forEach(builder::append);
        return builder.toString();
    }

    public List<Symbol> leftSide() {
        return leftSide;
    }

    public List<Symbol> rightSide() {
        return rightSide;
    }

    private static void validateSide(final List<Symbol> side) {
        Objects.requireNonNull(side);
        if (side.isEmpty()) {
            throw new IllegalArgumentException("Side of production can't be empty");
        }
    }

    private static void validateLeftSide(final List<Symbol> leftSide) {
        Production.validateSide(leftSide);
        Production.containsNonTerminal(leftSide);
    }

    private static void containsNonTerminal(final List<Symbol> side) {
        Objects.requireNonNull(side);
        if (side.stream().noneMatch(symbol -> NonTerminal.class.equals(symbol.getClass()))) {
            throw new IllegalArgumentException("Left side of production must contain at least one non-terminal symbol");
        }
    }
}
