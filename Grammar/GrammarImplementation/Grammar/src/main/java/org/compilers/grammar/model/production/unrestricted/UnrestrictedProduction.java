package org.compilers.grammar.model.production.unrestricted;

import org.compilers.grammar.model.vocabulary.Symbol;

import java.util.List;

public final class UnrestrictedProduction extends AbstractUnrestrictedProduction {

    // constructor
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    public UnrestrictedProduction(final List<? extends Symbol> leftSide, final List<? extends Symbol> rightSide) {
        super(leftSide, rightSide);
    }
}
