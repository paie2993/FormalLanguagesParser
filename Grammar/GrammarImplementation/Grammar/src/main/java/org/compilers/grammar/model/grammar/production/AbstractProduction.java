package org.compilers.grammar.model.grammar.production;


import org.compilers.grammar.model.grammar.vocabulary.Symbol;

import java.util.List;
import java.util.Objects;

public abstract class AbstractProduction implements Production {
    private final List<Symbol> leftSide;
    private final List<Symbol> rightSide;

    public AbstractProduction(final List<Symbol> leftSide, final List<Symbol> rightSide) {
        Objects.requireNonNull(leftSide);
        Objects.requireNonNull(rightSide);

        this.leftSide = leftSide;
        this.rightSide = rightSide;
    }

    @Override
    public List<Symbol> leftSide() {
        return this.leftSide;
    }

    @Override
    public List<Symbol> rightSide() {
        return this.rightSide;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        AbstractProduction other = (AbstractProduction) o;
        return Objects.equals(leftSide, other.leftSide) && Objects.equals(rightSide, other.rightSide);
    }

    @Override
    public int hashCode() {
        return Objects.hash(leftSide, rightSide);
    }

    @Override
    public String toString() {
        return String.format("%s %s %s", Production.prepareSideForPrinting(leftSide), Production.SIDES_SEPARATOR, Production.prepareSideForPrinting(rightSide));
    }
}
