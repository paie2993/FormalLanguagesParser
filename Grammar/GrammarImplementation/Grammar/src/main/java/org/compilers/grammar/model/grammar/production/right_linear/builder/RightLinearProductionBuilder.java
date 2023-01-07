package org.compilers.grammar.model.grammar.production.right_linear.builder;

import org.compilers.grammar.model.grammar.production.builder.AbstractProductionBuilder;
import org.compilers.grammar.model.grammar.production.right_linear.RightLinearProduction;
import org.compilers.grammar.model.grammar.production.right_linear.RightLinearProductionImpl;

public class RightLinearProductionBuilder extends AbstractProductionBuilder<RightLinearProduction> {
    public RightLinearProductionBuilder() {
    }

    @Override
    public RightLinearProduction build() {
        this.partialBuild();
        RightLinearProduction unrestrictedProduction = new RightLinearProductionImpl(this.production.leftSide(), this.production.rightSide());
        this.eraseAll();
        return unrestrictedProduction;
    }
}
