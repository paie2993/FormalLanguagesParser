package org.compilers.grammar.model.production.context_dependent;

import org.compilers.grammar.model.vocabulary.Symbol;

import java.util.List;

public final class ContextDependentProduction extends AbstractContextDependentProduction {

    public ContextDependentProduction(
            final List<? extends Symbol> leftSide,
            final List<? extends Symbol> rightSide
    ) {
        super(leftSide, rightSide);
    }
}
