package org.compilers.grammar.model.grammar.unrestricted.builder;

import org.compilers.grammar.model.grammar.Grammar;
import org.compilers.grammar.model.grammar.builder.AbstractGrammarBuilder;
import org.compilers.grammar.model.grammar.production.Production;
import org.compilers.grammar.model.grammar.production.builder.ProductionBuilder;
import org.compilers.grammar.model.grammar.production.unrestricted.UnrestrictedProduction;
import org.compilers.grammar.model.grammar.unrestricted.UnrestrictedGrammar;
import org.compilers.grammar.model.grammar.unrestricted.UnrestrictedGrammarImpl;

import java.util.Set;
import java.util.function.BiFunction;

public class UnrestrictedGrammarBuilder extends AbstractGrammarBuilder<UnrestrictedProduction, UnrestrictedGrammar<? extends UnrestrictedProduction>> {
    public UnrestrictedGrammarBuilder() {
    }

    @Override
    public UnrestrictedGrammar<? extends UnrestrictedProduction> build() {
        final BiFunction<Grammar<? extends Production>, Set<? extends UnrestrictedProduction>, ? extends UnrestrictedGrammar<? extends UnrestrictedProduction>> create = (grammar, productions) -> new UnrestrictedGrammarImpl(grammar.nonTerminals(), grammar.terminals(), productions, grammar.startSymbol());
        final ProductionBuilder<? extends UnrestrictedProduction> productionBuilder = UnrestrictedProduction.builder();
        return this.partialBuild(create, productionBuilder);
    }
}
