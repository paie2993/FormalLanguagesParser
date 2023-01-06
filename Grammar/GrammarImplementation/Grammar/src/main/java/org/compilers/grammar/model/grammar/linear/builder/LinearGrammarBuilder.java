package org.compilers.grammar.model.grammar.linear.builder;

import org.compilers.grammar.model.grammar.builder.AbstractGrammarBuilder;
import org.compilers.grammar.model.grammar.builder.GrammarBuilder;
import org.compilers.grammar.model.grammar.linear.LinearGrammar;
import org.compilers.grammar.model.grammar.linear.LinearGrammarImpl;
import org.compilers.grammar.model.grammar.production.builder.ProductionBuilder;
import org.compilers.grammar.model.grammar.production.linear.LinearProduction;

import java.util.Set;

public class LinearGrammarBuilder extends AbstractGrammarBuilder<LinearProduction, LinearGrammar<? extends LinearProduction>> {
    public LinearGrammarBuilder() {
    }

    @Override
    public LinearGrammar<? extends LinearProduction> build() {
        this.partialBuild();

        final ProductionBuilder<? extends LinearProduction> productionBuilder = LinearProduction.builder().symbols(this.grammar.nonTerminals(), this.grammar.terminals());
        final Set<? extends LinearProduction> productions = GrammarBuilder.prepareProductions(this.grammar.productions(), productionBuilder);
        final LinearGrammar<? extends LinearProduction> linearGrammar = new LinearGrammarImpl(this.grammar.nonTerminals(), this.grammar.terminals(), productions, this.grammar.startSymbol());

        this.eraseAll();

        return linearGrammar;
    }
}
