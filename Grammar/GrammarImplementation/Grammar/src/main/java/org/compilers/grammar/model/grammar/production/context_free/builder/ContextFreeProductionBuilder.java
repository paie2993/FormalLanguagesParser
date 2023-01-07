package org.compilers.grammar.model.grammar.production.context_free.builder;

import org.compilers.grammar.model.grammar.production.builder.AbstractProductionBuilder;
import org.compilers.grammar.model.grammar.production.context_free.ContextFreeProduction;
import org.compilers.grammar.model.grammar.production.context_free.ContextFreeProductionImpl;

public class ContextFreeProductionBuilder extends AbstractProductionBuilder<ContextFreeProduction> {
    public ContextFreeProductionBuilder() {
    }

    @Override
    public ContextFreeProduction build() {
        this.partialBuild();
        ContextFreeProduction unrestrictedProduction = new ContextFreeProductionImpl(this.production.leftSide(), this.production.rightSide());
        this.eraseAll();
        return unrestrictedProduction;
    }
}
