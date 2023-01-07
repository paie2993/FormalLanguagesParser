package org.compilers.grammar.model.grammar.regular.builder;

import org.compilers.grammar.model.grammar.Grammar;
import org.compilers.grammar.model.grammar.builder.AbstractGrammarBuilder;
import org.compilers.grammar.model.grammar.production.Production;
import org.compilers.grammar.model.grammar.production.builder.ProductionBuilder;
import org.compilers.grammar.model.grammar.production.regular.RegularProduction;
import org.compilers.grammar.model.grammar.regular.RegularGrammar;
import org.compilers.grammar.model.grammar.regular.RegularGrammarImpl;

import java.util.Set;
import java.util.function.BiFunction;

public class RegularGrammarBuilder extends AbstractGrammarBuilder<RegularProduction, RegularGrammar<? extends RegularProduction>> {
    public RegularGrammarBuilder() {
    }

    @Override
    public RegularGrammar<? extends RegularProduction> build() {
        final BiFunction<Grammar<? extends Production>, Set<? extends RegularProduction>, RegularGrammar<? extends RegularProduction>> create = (grammar, productions) -> new RegularGrammarImpl(grammar.nonTerminals(), grammar.terminals(), productions, grammar.startSymbol());
        final ProductionBuilder<? extends RegularProduction> productionBuilder = RegularProduction.builder();
        return this.partialBuild(create, productionBuilder);
    }
}
