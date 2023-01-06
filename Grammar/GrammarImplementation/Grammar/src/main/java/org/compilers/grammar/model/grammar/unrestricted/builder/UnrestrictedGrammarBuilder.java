package org.compilers.grammar.model.grammar.unrestricted.builder;

import org.compilers.grammar.model.grammar.builder.AbstractGrammarBuilder;
import org.compilers.grammar.model.grammar.production.builder.ProductionBuilder;
import org.compilers.grammar.model.grammar.production.unrestricted.UnrestrictedProduction;
import org.compilers.grammar.model.grammar.unrestricted.UnrestrictedGrammar;
import org.compilers.grammar.model.grammar.unrestricted.UnrestrictedGrammarImpl;

public class UnrestrictedGrammarBuilder extends AbstractGrammarBuilder<UnrestrictedProduction, UnrestrictedGrammar<? extends UnrestrictedProduction>> {
    public UnrestrictedGrammarBuilder() {
    }

    @Override
    public UnrestrictedGrammar<? extends UnrestrictedProduction> build() {
        final ProductionBuilder<? extends UnrestrictedProduction> productionBuilder = UnrestrictedProduction.builder();
        return this.partialBuild((grammar, productions) -> new UnrestrictedGrammarImpl(grammar.nonTerminals(), grammar.terminals(), productions, grammar.startSymbol()), productionBuilder);
    }
}
