package org.compilers.grammar.model.production.context_dependent;

import org.compilers.grammar.model.production.builder.AbstractProductionBuilder;
import org.compilers.grammar.model.vocabulary.Symbol;

import java.util.List;

public final class ContextDependentProductionBuilder extends AbstractProductionBuilder<AbstractContextDependentProduction> {

    @Override
    protected AbstractContextDependentProduction buildFromSides(
            final List<? extends Symbol> leftSide,
            final List<? extends Symbol> rightSide
    ) {
        return new ContextDependentProduction(leftSide, rightSide);
    }
}
