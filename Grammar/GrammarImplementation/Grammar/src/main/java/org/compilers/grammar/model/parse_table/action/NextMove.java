package org.compilers.grammar.model.parse_table.action;

import org.compilers.grammar.model.vocabulary.Symbol;

import java.util.List;
import java.util.stream.Collectors;

public final class NextMove {
    private final List<? extends Symbol> rightSide;
    private final int productionNumber;

    public NextMove(List<? extends Symbol> rightSide, int productionNumber) {
        this.rightSide = rightSide;
        this.productionNumber = productionNumber;
    }

    public List<? extends Symbol> rightSide() {
        return rightSide;
    }

    public int productionNumber() {
        return productionNumber;
    }

    @Override
    public String toString() {
        return String.format("(%s, %d)", rightSide.stream().map(Symbol::value).collect(Collectors.joining()), productionNumber);
    }
}
