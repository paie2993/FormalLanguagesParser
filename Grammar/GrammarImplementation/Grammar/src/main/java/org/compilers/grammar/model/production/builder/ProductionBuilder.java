package org.compilers.grammar.model.production.builder;

import org.compilers.grammar.model.production.Production;
import org.compilers.grammar.model.vocabulary.nonterminal.NonTerminal;
import org.compilers.grammar.model.vocabulary.terminal.Terminal;

import java.util.Set;

// Every type of production will be built (from a string) using a
// production builder specific for its type.
// The ProductionBuilder must have a context (specified terminals and non-terminals,
// so that it knows how to interpret a symbol from a string)
public interface ProductionBuilder<T extends Production> {

    String SYMBOLS_SEPARATOR = "`";
    String SIDES_SEPARATOR = "->";

    ProductionBuilder<T> context(final Set<? extends NonTerminal> nonTerminals, final Set<? extends Terminal> terminals);

    T build(final String productionString);
}
