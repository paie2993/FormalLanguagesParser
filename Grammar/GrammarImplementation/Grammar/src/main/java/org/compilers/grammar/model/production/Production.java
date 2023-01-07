package org.compilers.grammar.model.production;

import org.compilers.grammar.model.vocabulary.Symbol;

import java.util.List;

public interface Production {

    String EPSILON = "ε";
    String SIDES_SEPARATOR = "->";

    // getters
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    List<? extends Symbol> leftSide();

    List<? extends Symbol> rightSide();

    // complex getter
    boolean symbolInRightSide(final Symbol symbol);
}
