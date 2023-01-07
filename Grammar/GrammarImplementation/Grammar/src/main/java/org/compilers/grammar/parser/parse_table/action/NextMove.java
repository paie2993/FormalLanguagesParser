package org.compilers.grammar.parser.parse_table.action;

import org.compilers.grammar.model.vocabulary.Symbol;

import java.util.List;
import java.util.stream.Collectors;

public final class NextMove {

    private final List<? extends Symbol> rightSide;

    private final int productionNumber;

    // constructor
    public NextMove(final List<? extends Symbol> rightSide, final int productionNumber) {
        this.rightSide = rightSide;
        this.productionNumber = productionNumber;
    }

    public static NextMove of(final List<? extends Symbol> rightSide, final int productionNumber) {
        return new NextMove(rightSide, productionNumber);
    }

    // getters
    public List<? extends Symbol> rightSide() {
        return rightSide;
    }

    public int productionNumber() {
        return productionNumber;
    }

    // general utility
    @Override
    public boolean equals(final Object o) {
        if (!(o instanceof NextMove other)) {
            return false;
        }
        return other.rightSide.equals(this.rightSide) &&
                other.productionNumber == this.productionNumber;
    }

    @Override
    public int hashCode() {
        var hash = 7;
        hash = 31 * hash + rightSide.hashCode();
        hash = 31 * hash + productionNumber;
        return hash;
    }

    @Override
    public String toString() {
        final var rightSideString = rightSide.stream().map(Symbol::value).collect(Collectors.joining(" "));
        return String.format("(%s, %d)", rightSideString, productionNumber);
    }
}
