package org.compilers.grammar.model.grammar;

import org.compilers.grammar.model.production.Production;
import org.compilers.grammar.model.vocabulary.nonterminal.NonTerminal;
import org.compilers.grammar.model.vocabulary.terminal.Terminal;

import java.util.List;
import java.util.Set;

public interface Grammar<T extends Production> {

    // getters
    Set<? extends NonTerminal> nonTerminals();

    Set<? extends Terminal> terminals();

    List<T> productions();

    NonTerminal startNonTerminal();

    // complex getters
    boolean containsNonTerminal(final NonTerminal nonTerminal);

    int indexOf(final T production);

    T at(final int productionIndex);
}
