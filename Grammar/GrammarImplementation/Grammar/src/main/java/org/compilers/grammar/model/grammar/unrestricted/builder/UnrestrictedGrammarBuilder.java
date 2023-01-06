package org.compilers.grammar.model.grammar.unrestricted.builder;

import org.compilers.grammar.model.grammar.builder.AbstractGrammarBuilder;
import org.compilers.grammar.model.grammar.builder.GrammarBuilder;
import org.compilers.grammar.model.grammar.production.builder.ProductionBuilder;
import org.compilers.grammar.model.grammar.production.unrestricted.UnrestrictedProduction;
import org.compilers.grammar.model.grammar.unrestricted.UnrestrictedGrammar;
import org.compilers.grammar.model.grammar.unrestricted.UnrestrictedGrammarImpl;

import java.util.Set;

public class UnrestrictedGrammarBuilder extends AbstractGrammarBuilder<UnrestrictedProduction, UnrestrictedGrammar<? extends UnrestrictedProduction>> {
    public UnrestrictedGrammarBuilder() {
    }

    @Override
    public UnrestrictedGrammar<? extends UnrestrictedProduction> build() {
        this.partialBuild();

        final ProductionBuilder<? extends UnrestrictedProduction> productionBuilder = UnrestrictedProduction.builder().symbols(this.grammar.nonTerminals(), this.grammar.terminals());
        final Set<? extends UnrestrictedProduction> productions = GrammarBuilder.prepareProductions(this.grammar.productions(), productionBuilder);
        final UnrestrictedGrammar<? extends UnrestrictedProduction> unrestrictedGrammar = new UnrestrictedGrammarImpl(this.grammar.nonTerminals(), this.grammar.terminals(), productions, this.grammar.startSymbol());

        this.eraseAll();

        return unrestrictedGrammar;
    }
}
