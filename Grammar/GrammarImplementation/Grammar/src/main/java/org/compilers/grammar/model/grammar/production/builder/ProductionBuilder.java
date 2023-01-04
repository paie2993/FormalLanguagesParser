package org.compilers.grammar.model.grammar.production.builder;

import org.compilers.grammar.model.grammar.production.Production;
import org.compilers.grammar.model.vocabulary.nonterminal.NonTerminal;
import org.compilers.grammar.model.vocabulary.terminal.Terminal;

import java.util.Set;

public interface ProductionBuilder {
    String SYMBOLS_SEPARATOR = "`";
    String SIDES_SEPARATOR = "->";

    ProductionBuilder symbols(final Set<NonTerminal> nonTerminals, final Set<Terminal> terminals);

    Production build();
}
