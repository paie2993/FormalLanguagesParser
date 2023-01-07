package org.compilers.grammar.model.grammar.production.linear.builder;

import org.compilers.grammar.model.grammar.production.builder.AbstractProductionBuilder;
import org.compilers.grammar.model.grammar.production.linear.LinearProduction;
import org.compilers.grammar.model.grammar.production.linear.LinearProductionImpl;

public class LinearProductionBuilder extends AbstractProductionBuilder<LinearProduction> {
    public LinearProductionBuilder() {
    }

    @Override
    public LinearProduction build() {
        this.partialBuild();
        LinearProduction unrestrictedProduction = new LinearProductionImpl(this.production.leftSide(), this.production.rightSide());
        this.eraseAll();
        return unrestrictedProduction;
    }
}
