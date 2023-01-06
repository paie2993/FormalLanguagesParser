package org.compilers.grammar.model.grammar.context_dependent.builder;

import org.compilers.grammar.model.grammar.builder.AbstractGrammarBuilder;
import org.compilers.grammar.model.grammar.builder.GrammarBuilder;
import org.compilers.grammar.model.grammar.context_dependent.ContextDependentGrammar;
import org.compilers.grammar.model.grammar.context_dependent.ContextDependentGrammarImpl;
import org.compilers.grammar.model.grammar.production.builder.ProductionBuilder;
import org.compilers.grammar.model.grammar.production.context_dependent.ContextDependentProduction;

import java.util.Set;

public class ContextDependentGrammarBuilder extends AbstractGrammarBuilder<ContextDependentProduction, ContextDependentGrammar<? extends ContextDependentProduction>> {
    public ContextDependentGrammarBuilder() {
    }

    @Override
    public ContextDependentGrammar<? extends ContextDependentProduction> build() {
        this.partialBuild();

        final ProductionBuilder<? extends ContextDependentProduction> productionBuilder = ContextDependentProduction.builder().symbols(this.grammar.nonTerminals(), this.grammar.terminals());
        final Set<? extends ContextDependentProduction> productions = GrammarBuilder.prepareProductions(this.grammar.productions(), productionBuilder);
        final ContextDependentGrammar<? extends ContextDependentProduction> contextDependentGrammar = new ContextDependentGrammarImpl(this.grammar.nonTerminals(), this.grammar.terminals(), productions, this.grammar.startSymbol());

        this.eraseAll();

        return contextDependentGrammar;
    }
}
