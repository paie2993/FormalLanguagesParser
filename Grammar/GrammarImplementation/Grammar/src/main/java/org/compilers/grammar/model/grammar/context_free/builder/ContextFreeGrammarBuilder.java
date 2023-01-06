package org.compilers.grammar.model.grammar.context_free.builder;

import org.compilers.grammar.model.grammar.builder.AbstractGrammarBuilder;
import org.compilers.grammar.model.grammar.context_free.ContextFreeGrammar;
import org.compilers.grammar.model.grammar.context_free.ContextFreeGrammarImpl;
import org.compilers.grammar.model.grammar.production.Production;
import org.compilers.grammar.model.grammar.production.builder.ProductionBuilder;
import org.compilers.grammar.model.grammar.production.context_free.ContextFreeProduction;

import java.util.Set;
import java.util.stream.Collectors;

public class ContextFreeGrammarBuilder extends AbstractGrammarBuilder<ContextFreeProduction, ContextFreeGrammar<? extends ContextFreeProduction>> {
    public ContextFreeGrammarBuilder() {
    }

    @Override
    public ContextFreeGrammar<? extends ContextFreeProduction> build() {
        this.partialBuild();

        final ProductionBuilder<? extends ContextFreeProduction> productionBuilder = ContextFreeProduction.builder().symbols(this.grammar.nonTerminals(), this.grammar.terminals());
        final Set<? extends ContextFreeProduction> productions = prepareProductions(this.grammar.productions(), productionBuilder);
        final ContextFreeGrammar<? extends ContextFreeProduction> contextFreeGrammar = new ContextFreeGrammarImpl(this.grammar.nonTerminals(), this.grammar.terminals(), productions, this.grammar.startSymbol());

        this.eraseAll();

        return contextFreeGrammar;
    }

    private static Set<? extends ContextFreeProduction> prepareProductions(final Set<? extends Production> productions, final ProductionBuilder<? extends ContextFreeProduction> builder) {
        return productions
                .stream()
                .map(builder::production)
                .map(ProductionBuilder::build)
                .collect(Collectors.toUnmodifiableSet());
    }
}
