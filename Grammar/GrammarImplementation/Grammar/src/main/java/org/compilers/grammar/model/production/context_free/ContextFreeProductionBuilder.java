package org.compilers.grammar.model.production.context_free;

import org.compilers.grammar.model.production.builder.AbstractProductionBuilder;
import org.compilers.grammar.model.vocabulary.Symbol;

import java.util.List;

public final class ContextFreeProductionBuilder extends AbstractProductionBuilder<AbstractContextFreeProduction> {

    @Override
    protected AbstractContextFreeProduction buildFromSides(
            final List<? extends Symbol> leftSide,
            final List<? extends Symbol> rightSide
    ) {
        return new ContextFreeProduction(leftSide, rightSide);
    }
}
