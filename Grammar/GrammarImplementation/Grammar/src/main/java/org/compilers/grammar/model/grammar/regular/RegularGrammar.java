package org.compilers.grammar.model.grammar.regular;

import org.compilers.grammar.model.grammar.production.regular.RegularProduction;
import org.compilers.grammar.model.grammar.right_linear.RightLinearGrammar;
import org.compilers.grammar.model.vocabulary.nonterminal.NonTerminal;

import java.util.Set;

public interface RegularGrammar<T extends RegularProduction> extends RightLinearGrammar<T> {
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
