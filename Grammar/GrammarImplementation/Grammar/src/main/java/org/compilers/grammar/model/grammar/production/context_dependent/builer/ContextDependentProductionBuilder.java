package org.compilers.grammar.model.grammar.production.context_dependent.builer;

import org.compilers.grammar.model.grammar.production.builder.AbstractProductionBuilder;
import org.compilers.grammar.model.grammar.production.context_dependent.ContextDependentProduction;
import org.compilers.grammar.model.grammar.production.context_dependent.ContextDependentProductionImpl;

public class ContextDependentProductionBuilder extends AbstractProductionBuilder<ContextDependentProduction> {
    public ContextDependentProductionBuilder() {
    }

    @Override
    public ContextDependentProduction build() {
        this.partialBuild();
        ContextDependentProduction unrestrictedProduction = new ContextDependentProductionImpl(this.production.leftSide(), this.production.rightSide());
        this.eraseAll();
        return unrestrictedProduction;
    }
}
