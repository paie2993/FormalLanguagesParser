package org.compilers.grammar.model.production;

import org.compilers.grammar.model.vocabulary.Symbol;

import java.util.List;
import java.util.Objects;

public abstract class AbstractProduction implements Production {

    protected final List<? extends Symbol> leftSide;
    protected final List<? extends Symbol> rightSide;

    // constructor
    public AbstractProduction(final List<? extends Symbol> leftSide, final List<? extends Symbol> rightSide) {
        Objects.requireNonNull(leftSide);
        Objects.requireNonNull(rightSide);

        this.leftSide = leftSide;
        this.rightSide = rightSide;
    }

    // getters
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    @Override
    public List<? extends Symbol> leftSide() {
        return leftSide;
    }

    @Override
    public List<? extends Symbol> rightSide() {
        return rightSide;
    }

    // complex getter
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    @Override
    public boolean symbolInRightSide(final Symbol symbol) {
        return symbolInSide(symbol, rightSide);
    }

    private static boolean symbolInSide(final Symbol symbol, final List<? extends Symbol> side) {
        Objects.requireNonNull(symbol);
        Objects.requireNonNull(side);
        return side.contains(symbol);
    }

    // general utility
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final var other = (AbstractProduction) o;
        return Objects.equals(other.leftSide, this.leftSide) &&
                Objects.equals(other.rightSide, this.rightSide);
    }

    @Override
    public int hashCode() {
        return Objects.hash(leftSide, rightSide);
    }

    @Override
    public String toString() {
        return String.format(
                "%s %s %s",
                prepareSideForPrinting(leftSide),
                Production.SIDES_SEPARATOR,
                prepareSideForPrinting(rightSide)
        );
    }

    private static String prepareSideForPrinting(final List<? extends Symbol> side) {
        Objects.requireNonNull(side);

        final var builder = new StringBuilder();
        if (side.isEmpty()) {
            builder.append(EPSILON);
        } else {
            side.stream().map(Symbol::value).forEach(builder::append);
        }

        return builder.toString();
    }
}
