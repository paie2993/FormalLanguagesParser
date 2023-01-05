package org.compilers.grammar.model.grammar.context_dependent.builder;

import org.compilers.grammar.model.grammar.builder.AbstractGrammarBuilder;
import org.compilers.grammar.model.grammar.context_dependent.ContextDependentGrammar;
import org.compilers.grammar.model.grammar.context_dependent.ContextDependentGrammarImpl;
import org.compilers.grammar.model.grammar.production.Production;
import org.compilers.grammar.model.grammar.production.builder.ProductionBuilder;
import org.compilers.grammar.model.grammar.production.context_dependent.ContextDependentProduction;

import java.util.Set;
import java.util.stream.Collectors;

public class ContextDependentGrammarBuilder extends AbstractGrammarBuilder<ContextDependentProduction, ContextDependentGrammar<? extends ContextDependentProduction>> {
    public ContextDependentGrammarBuilder() {
    }

    @Override
    public ContextDependentGrammar<? extends ContextDependentProduction> build() {
        this.partialBuild();
        final Set<? extends ContextDependentProduction> productions = prepareProductions(this.grammar.productions());
        final ContextDependentGrammar<? extends ContextDependentProduction> contextDependentGrammar = new ContextDependentGrammarImpl(this.grammar.nonTerminals(), this.grammar.terminals(), productions, this.grammar.startSymbol());
        this.eraseAll();
        return contextDependentGrammar;
    }

    private static Set<? extends ContextDependentProduction> prepareProductions(final Set<? extends Production> productions) {
        return productions
                .stream()
                .map(ContextDependentProduction.builder()::production)
                .map(ProductionBuilder::build)
                .collect(Collectors.toUnmodifiableSet());
    }
}
