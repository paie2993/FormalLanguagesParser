package org.compilers.grammar.model.grammar;

import org.compilers.grammar.model.production.Production;
import org.compilers.grammar.model.vocabulary.NonTerminal;
import org.compilers.grammar.model.vocabulary.Symbol;
import org.compilers.grammar.model.vocabulary.Terminal;

import java.util.List;
import java.util.Objects;
import java.util.Set;

public interface Grammar {
    String EPSILON = "epsilon";

    Set<? extends NonTerminal> nonTerminals();

    Set<? extends Terminal> terminals();

    Set<? extends Production> productions();

    NonTerminal startingNonTerminal();

    Set<Production> productionsOf(final Symbol nonTerminal);

    int indexOf(final Production production);

    Set<Production> hasSymbolInRightSide(final Symbol symbol);

    Set<Production> hasSymbolInLeftSide(final Symbol symbol);

    boolean containsNonTerminal(final Symbol nonTerminal);

    boolean containsTerminal(final Symbol terminal);

    boolean isContextFree();

    static boolean isTerminal(final Symbol symbol) {
        Objects.requireNonNull(symbol);

        return Terminal.class.equals(symbol.getClass());
    }

    static boolean isNonTerminal(final Symbol symbol) {
        Objects.requireNonNull(symbol);

        return NonTerminal.class.equals(symbol.getClass());
    }

    static boolean sideContainsOnlyNonTerminal(final List<Symbol> side, final Symbol nonTerminal) {
        Objects.requireNonNull(side);
        Objects.requireNonNull(nonTerminal);

        if (!isNonTerminal(nonTerminal)) {
            return false;
        }
        if (side.size() != 1) {
            return false;
        }
        final Symbol singleSymbol = side.get(0);
        return nonTerminal.equals(singleSymbol);
    }

    static boolean sideContainsSymbol(final List<Symbol> side, final Symbol symbol) {
        Objects.requireNonNull(side);
        Objects.requireNonNull(symbol);

        return !side.isEmpty() && side.contains(symbol);
    }

    static boolean leftSideContainsSymbol(final Production production, final Symbol symbol) {
        Objects.requireNonNull(production);
        Objects.requireNonNull(symbol);

        return sideContainsSymbol(production.leftSide(), symbol);
    }

    static boolean rightSideContainsSymbol(final Production production, final Symbol symbol) {
        Objects.requireNonNull(production);
        Objects.requireNonNull(symbol);

        return sideContainsSymbol(production.rightSide(), symbol);
    }

    static boolean isProductionContextFree(final Production production) {
        Objects.requireNonNull(production);

        final List<Symbol> leftSide = production.leftSide();
        return leftSide.size() == 1 && isNonTerminal(leftSide.get(0));
    }
}
