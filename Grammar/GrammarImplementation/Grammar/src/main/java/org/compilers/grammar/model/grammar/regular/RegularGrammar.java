package org.compilers.grammar.model.grammar.regular;

import org.compilers.grammar.model.grammar.production.regular.RegularProduction;
import org.compilers.grammar.model.grammar.right_linear.RightLinearGrammar;
import org.compilers.grammar.model.vocabulary.Symbol;
import org.compilers.grammar.model.vocabulary.nonterminal.NonTerminal;

import java.util.Set;

public interface RegularGrammar extends RightLinearGrammar {
    @Override
    Set<? extends RegularProduction> productions();

    @Override
    Set<? extends RegularProduction> haveSymbolInRightSide(final Symbol symbol);

    @Override
    Set<? extends RegularProduction> haveSymbolInLeftSide(final Symbol symbol);

    static void validateGrammar(
            final Set<? extends RegularProduction> productions,
            final NonTerminal startSymbol
    ) {
        final boolean startSymbolGoesInEpsilon = productions
                .stream()
                .filter(production -> startSymbol.equals(production.leftSideNonTerminal()))
                .anyMatch(production -> production.rightSide().isEmpty());
        if (startSymbolGoesInEpsilon) {
            final boolean startSymbolAppearsInRightSide = productions
                    .stream()
                    .anyMatch(production -> production.hasSymbolInRightSide(startSymbol));
            if (startSymbolAppearsInRightSide) {
                throw new IllegalArgumentException("The given grammar is not regular");
            }
        }
    }
}
