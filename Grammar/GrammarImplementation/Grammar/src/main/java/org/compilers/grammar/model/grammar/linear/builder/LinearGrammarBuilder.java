package org.compilers.grammar.model.grammar.linear.builder;

import org.compilers.grammar.model.grammar.Grammar;
import org.compilers.grammar.model.grammar.builder.AbstractGrammarBuilder;
import org.compilers.grammar.model.grammar.linear.LinearGrammar;
import org.compilers.grammar.model.grammar.linear.LinearGrammarImpl;
import org.compilers.grammar.model.grammar.production.Production;
import org.compilers.grammar.model.grammar.production.builder.ProductionBuilder;
import org.compilers.grammar.model.grammar.production.linear.LinearProduction;

import java.util.Set;
import java.util.function.BiFunction;

public class LinearGrammarBuilder extends AbstractGrammarBuilder<LinearProduction, LinearGrammar<? extends LinearProduction>> {
    public LinearGrammarBuilder() {
    }

    @Override
    public LinearGrammar<? extends LinearProduction> build() {
        final BiFunction<Grammar<? extends Production>, Set<? extends LinearProduction>, LinearGrammar<? extends LinearProduction>> create = (grammar, productions) -> new LinearGrammarImpl(grammar.nonTerminals(), grammar.terminals(), productions, grammar.startSymbol());
        final ProductionBuilder<? extends LinearProduction> productionBuilder = LinearProduction.builder();
        return this.partialBuild(create, productionBuilder);
    }
}
