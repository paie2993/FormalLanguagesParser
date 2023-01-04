package org.compilers.grammar.model.grammar.production.unrestricted.builder;

import org.compilers.grammar.model.grammar.production.builder.ProductionBuilder;
import org.compilers.grammar.model.grammar.production.unrestricted.UnrestrictedProduction;
import org.compilers.grammar.model.vocabulary.nonterminal.NonTerminal;
import org.compilers.grammar.model.vocabulary.terminal.Terminal;

import java.util.Set;

public interface UnrestrictedProductionBuilder extends ProductionBuilder {
    @Override
    UnrestrictedProductionBuilder symbols(final Set<NonTerminal> nonTerminals, final Set<Terminal> terminals);

    @Override
    UnrestrictedProduction build();

    UnrestrictedProductionBuilder productionString(final String productionString);

    UnrestrictedProductionBuilder productionString(final String productionString, final String sideSeparator, final String symbolSeparator);
}
