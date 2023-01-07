package org.compilers.grammar.model.grammar.production.regular.builder;

import org.compilers.grammar.model.grammar.production.builder.AbstractProductionBuilder;
import org.compilers.grammar.model.grammar.production.regular.RegularProduction;
import org.compilers.grammar.model.grammar.production.regular.RegularProductionImpl;

public class RegularProductionBuilder extends AbstractProductionBuilder<RegularProduction> {
    public RegularProductionBuilder() {
    }

    @Override
    public RegularProduction build() {
        this.partialBuild();
        RegularProduction unrestrictedProduction = new RegularProductionImpl(this.production.leftSide(), this.production.rightSide());
        this.eraseAll();
        return unrestrictedProduction;
    }
}
