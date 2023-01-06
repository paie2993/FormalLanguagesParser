package org.compilers.grammar.model.grammar.context_free.builder;

import org.compilers.grammar.model.grammar.Grammar;
import org.compilers.grammar.model.grammar.builder.AbstractGrammarBuilder;
import org.compilers.grammar.model.grammar.context_free.ContextFreeGrammar;
import org.compilers.grammar.model.grammar.context_free.ContextFreeGrammarImpl;
import org.compilers.grammar.model.grammar.production.Production;
import org.compilers.grammar.model.grammar.production.builder.ProductionBuilder;
import org.compilers.grammar.model.grammar.production.context_free.ContextFreeProduction;

import java.util.Set;
import java.util.function.BiFunction;

public class ContextFreeGrammarBuilder extends AbstractGrammarBuilder<ContextFreeProduction, ContextFreeGrammar<? extends ContextFreeProduction>> {
    public ContextFreeGrammarBuilder() {
    }

    @Override
    public ContextFreeGrammar<? extends ContextFreeProduction> build() {
        final BiFunction<Grammar<? extends Production>, Set<? extends ContextFreeProduction>, ContextFreeGrammar<? extends ContextFreeProduction>> create = (grammar, productions) -> new ContextFreeGrammarImpl(grammar.nonTerminals(), grammar.terminals(), productions, grammar.startSymbol());
        final ProductionBuilder<? extends ContextFreeProduction> productionBuilder = ContextFreeProduction.builder();
        return this.partialBuild(create, productionBuilder);
    }
}
