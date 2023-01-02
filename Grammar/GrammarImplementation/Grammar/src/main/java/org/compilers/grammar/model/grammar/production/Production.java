package org.compilers.grammar.model.grammar.production;

import org.compilers.grammar.model.grammar.Grammar;
import org.compilers.grammar.model.grammar.vocabulary.Symbol;

import java.util.List;

public interface Production {
    String SIDES_SEPARATOR = "->";

    List<Symbol> leftSide();

    List<Symbol> rightSide();

    static String prepareSideForPrinting(final List<Symbol> side) {
        final StringBuilder builder = new StringBuilder();
        if (side.isEmpty()) {
            builder.append(Grammar.EPSILON);
        } else {
            side.stream().map(Symbol::value).forEach(builder::append);
        }
        return builder.toString();
    }
}
