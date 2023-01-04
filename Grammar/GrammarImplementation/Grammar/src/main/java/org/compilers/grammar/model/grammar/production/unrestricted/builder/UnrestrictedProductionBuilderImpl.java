package org.compilers.grammar.model.grammar.production.unrestricted.builder;

import org.compilers.grammar.model.grammar.production.builder.AbstractProductionBuilder;
import org.compilers.grammar.model.grammar.production.unrestricted.UnrestrictedProduction;
import org.compilers.grammar.model.grammar.production.unrestricted.UnrestrictedProductionImpl;

public class UnrestrictedProductionBuilderImpl extends AbstractProductionBuilder<UnrestrictedProduction> {
    public UnrestrictedProductionBuilderImpl() {
    }

    @Override
    public UnrestrictedProduction build() {
        this.partialBuild();
        UnrestrictedProduction unrestrictedProduction = new UnrestrictedProductionImpl(this.production.leftSide(), this.production.rightSide());
        this.eraseAll();
        return unrestrictedProduction;
    }
}
