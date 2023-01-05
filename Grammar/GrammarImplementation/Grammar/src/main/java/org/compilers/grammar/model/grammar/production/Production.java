package org.compilers.grammar.model.grammar.production;

import org.compilers.grammar.model.vocabulary.Symbol;

import java.util.List;
import java.util.Objects;

public interface Production {
    String EPSILON = "ε";
    String SIDES_SEPARATOR = "->";

    List<Symbol> leftSide();

    List<Symbol> rightSide();

    boolean hasSymbolInLeftSide(final Symbol symbol);

    boolean hasSymbolInRightSide(final Symbol symbol);

    static String prepareSideForPrinting(final List<Symbol> side) {
        Objects.requireNonNull(side);

        final StringBuilder builder = new StringBuilder();
        if (side.isEmpty()) {
            builder.append(EPSILON);
        } else {
            side.stream().map(Symbol::value).forEach(builder::append);
        }
        return builder.toString();
    }

    static boolean hasSymbolInSide(final Symbol symbol, final List<Symbol> side) {
        Objects.requireNonNull(symbol);
        Objects.requireNonNull(side);

        return side.contains(symbol);

    }
}
