package org.compilers.grammar.model.grammar.production;


import org.compilers.grammar.model.vocabulary.Symbol;

import java.util.List;
import java.util.Objects;

public abstract class AbstractProduction implements Production {
    protected final List<? extends Symbol> leftSide;
    protected final List<? extends Symbol> rightSide;

    public AbstractProduction(final List<? extends Symbol> leftSide, final List<? extends Symbol> rightSide) {
        Objects.requireNonNull(leftSide);
        Objects.requireNonNull(rightSide);

        this.leftSide = leftSide;
        this.rightSide = rightSide;
    }

    @Override
    public List<? extends Symbol> leftSide() {
        return this.leftSide;
    }

    @Override
    public List<? extends Symbol> rightSide() {
        return this.rightSide;
    }

    @Override
    public boolean hasSymbolInLeftSide(Symbol symbol) {
        return Production.hasSymbolInSide(symbol, this.leftSide);
    }

    @Override
    public boolean hasSymbolInRightSide(Symbol symbol) {
        return Production.hasSymbolInSide(symbol, this.rightSide);
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
