package org.compilers.grammar.model.grammar.production.builder;

import org.compilers.grammar.model.grammar.production.Production;
import org.compilers.grammar.model.vocabulary.nonterminal.NonTerminal;
import org.compilers.grammar.model.vocabulary.terminal.Terminal;

import java.util.Set;

public interface ProductionBuilder<T extends Production> {
    String SYMBOLS_SEPARATOR = "`";
    String SIDES_SEPARATOR = "->";

    ProductionBuilder<T> symbols(final Set<NonTerminal> nonTerminals, final Set<Terminal> terminals);

    ProductionBuilder<T> productionString(final String productionString);

    ProductionBuilder<T> productionString(final String productionString, final String sideSeparator, final String symbolSeparator);

    ProductionBuilder<T> production(final Production production);

    T build();

}
