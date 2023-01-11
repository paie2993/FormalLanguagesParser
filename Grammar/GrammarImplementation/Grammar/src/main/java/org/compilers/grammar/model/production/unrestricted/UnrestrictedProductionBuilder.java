package org.compilers.grammar.model.production.unrestricted;

import org.compilers.grammar.model.production.builder.AbstractProductionBuilder;
import org.compilers.grammar.model.vocabulary.Symbol;

import java.util.List;

public final class UnrestrictedProductionBuilder extends AbstractProductionBuilder<UnrestrictedProduction> {

    @Override
    protected UnrestrictedProduction buildFromSides(
            final List<? extends Symbol> leftSide,
            final List<? extends Symbol> rightSide
    ) {
        return new UnrestrictedProduction(leftSide, rightSide);
    }
}
