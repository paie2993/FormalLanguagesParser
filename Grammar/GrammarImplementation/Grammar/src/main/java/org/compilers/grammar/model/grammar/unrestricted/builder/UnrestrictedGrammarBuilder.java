package org.compilers.grammar.model.grammar.unrestricted.builder;

import org.compilers.grammar.model.grammar.builder.AbstractGrammarBuilder;
import org.compilers.grammar.model.grammar.production.Production;
import org.compilers.grammar.model.grammar.production.builder.ProductionBuilder;
import org.compilers.grammar.model.grammar.production.unrestricted.UnrestrictedProduction;
import org.compilers.grammar.model.grammar.unrestricted.UnrestrictedGrammar;
import org.compilers.grammar.model.grammar.unrestricted.UnrestrictedGrammarImpl;

import java.util.Set;
import java.util.stream.Collectors;

public class UnrestrictedGrammarBuilder extends AbstractGrammarBuilder<UnrestrictedProduction, UnrestrictedGrammar<? extends UnrestrictedProduction>> {
    public UnrestrictedGrammarBuilder() {
    }

    @Override
    public UnrestrictedGrammar<? extends UnrestrictedProduction> build() {
        this.partialBuild();
        final Set<? extends UnrestrictedProduction> productions = prepareProductions(this.grammar.productions());
        final UnrestrictedGrammar<? extends UnrestrictedProduction> unrestrictedGrammar = new UnrestrictedGrammarImpl(this.grammar.nonTerminals(), this.grammar.terminals(), productions, this.grammar.startSymbol());
        this.eraseAll();
        return unrestrictedGrammar;
    }

    private static Set<? extends UnrestrictedProduction> prepareProductions(final Set<? extends Production> productions) {
        return productions
                .stream()
                .map(UnrestrictedProduction.builder()::production)
                .map(ProductionBuilder::build)
                .collect(Collectors.toUnmodifiableSet());
    }
}
