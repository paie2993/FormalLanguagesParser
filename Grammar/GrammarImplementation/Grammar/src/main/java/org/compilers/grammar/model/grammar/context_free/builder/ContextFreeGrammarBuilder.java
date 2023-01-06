package org.compilers.grammar.model.grammar.context_free.builder;

import org.compilers.grammar.model.grammar.builder.AbstractGrammarBuilder;
import org.compilers.grammar.model.grammar.builder.GrammarBuilder;
import org.compilers.grammar.model.grammar.context_free.ContextFreeGrammar;
import org.compilers.grammar.model.grammar.context_free.ContextFreeGrammarImpl;
import org.compilers.grammar.model.grammar.production.builder.ProductionBuilder;
import org.compilers.grammar.model.grammar.production.context_free.ContextFreeProduction;

import java.util.Set;

public class ContextFreeGrammarBuilder extends AbstractGrammarBuilder<ContextFreeProduction, ContextFreeGrammar<? extends ContextFreeProduction>> {
    public ContextFreeGrammarBuilder() {
    }

    @Override
    public ContextFreeGrammar<? extends ContextFreeProduction> build() {
        this.partialBuild();

        final ProductionBuilder<? extends ContextFreeProduction> productionBuilder = ContextFreeProduction.builder().symbols(this.grammar.nonTerminals(), this.grammar.terminals());
        final Set<? extends ContextFreeProduction> productions = GrammarBuilder.prepareProductions(this.grammar.productions(), productionBuilder);
        final ContextFreeGrammar<? extends ContextFreeProduction> contextFreeGrammar = new ContextFreeGrammarImpl(this.grammar.nonTerminals(), this.grammar.terminals(), productions, this.grammar.startSymbol());

        this.eraseAll();

        return contextFreeGrammar;
    }
}
