package org.compilers.grammar.model.grammar;

import org.compilers.grammar.model.grammar.production.Production;
import org.compilers.grammar.model.vocabulary.nonterminal.NonTerminal;
import org.compilers.grammar.model.vocabulary.Symbol;
import org.compilers.grammar.model.vocabulary.terminal.Terminal;

import java.util.Set;

public interface Grammar<T extends Production> {
    Set<? extends NonTerminal> nonTerminals();

    Set<? extends Terminal> terminals();

    Set<? extends T> productions();

    NonTerminal startSymbol();

    boolean containsSymbol(final Symbol symbol);

    boolean containsNonTerminal(final Symbol nonTerminal);

    boolean containsTerminal(final Symbol terminal);

    boolean containsProduction(final Production production);

    int indexOf(final Production production);

    T productionAt(final int index);

    // set of productions in which the given symbol appears in the right-side
    // if the symbol does not appear in any right side, returns empty side
    Set<? extends T> haveSymbolInRightSide(final Symbol symbol);

    // set of productions in which the given symbol appears in the left-side
    // if the symbol does not appear in any left side, returns empty side
    Set<? extends T> haveSymbolInLeftSide(final Symbol symbol);
}
