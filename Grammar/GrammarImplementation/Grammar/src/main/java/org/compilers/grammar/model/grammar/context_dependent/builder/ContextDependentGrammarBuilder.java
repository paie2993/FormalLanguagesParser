package org.compilers.grammar.model.grammar.context_dependent.builder;

import org.compilers.grammar.model.grammar.Grammar;
import org.compilers.grammar.model.grammar.builder.AbstractGrammarBuilder;
import org.compilers.grammar.model.grammar.context_dependent.ContextDependentGrammar;
import org.compilers.grammar.model.grammar.context_dependent.ContextDependentGrammarImpl;
import org.compilers.grammar.model.grammar.production.Production;
import org.compilers.grammar.model.grammar.production.builder.ProductionBuilder;
import org.compilers.grammar.model.grammar.production.context_dependent.ContextDependentProduction;

import java.util.Set;
import java.util.function.BiFunction;

public class ContextDependentGrammarBuilder extends AbstractGrammarBuilder<ContextDependentProduction, ContextDependentGrammar<? extends ContextDependentProduction>> {
    public ContextDependentGrammarBuilder() {
    }

    @Override
    public ContextDependentGrammar<? extends ContextDependentProduction> build() {
        final BiFunction<Grammar<? extends Production>, Set<? extends ContextDependentProduction>, ContextDependentGrammar<? extends ContextDependentProduction>> create = (grammar, productions) -> new ContextDependentGrammarImpl(grammar.nonTerminals(), grammar.terminals(), productions, grammar.startSymbol());
        final ProductionBuilder<? extends ContextDependentProduction> productionBuilder = ContextDependentProduction.builder();
        return this.partialBuild(create, productionBuilder);
    }
}
