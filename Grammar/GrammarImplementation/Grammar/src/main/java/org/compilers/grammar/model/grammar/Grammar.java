package org.compilers.grammar.model.grammar;

import org.compilers.grammar.model.grammar.production.Production;
import org.compilers.grammar.model.grammar.vocabulary.nonterminal.NonTerminal;
import org.compilers.grammar.model.grammar.vocabulary.Symbol;
import org.compilers.grammar.model.grammar.vocabulary.terminal.Terminal;

import java.util.Set;

public interface Grammar {
    String EPSILON = "ε";

    Set<? extends NonTerminal> nonTerminals();

    Set<? extends Terminal> terminals();

    Set<? extends Production> productions();

    NonTerminal startSymbol();

    boolean containsNonTerminal(final Symbol nonTerminal);

    boolean containsTerminal(final Symbol terminal);

    boolean containsProduction(final Production production);

    int indexOf(final Production production);

    Set<? extends Production> haveSymbolInRightSide(final Symbol symbol);

    Set<? extends Production> haveSymbolInLeftSide(final Symbol symbol);
}
