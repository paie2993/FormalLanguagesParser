package org.compilers.grammar.model.grammar.context_free.follow;

import org.compilers.grammar.model.grammar.context_free.result_sets.ResultSets;
import org.compilers.grammar.model.production.context_free.AbstractContextFreeProduction;
import org.compilers.grammar.model.vocabulary.Symbol;
import org.compilers.grammar.model.vocabulary.nonterminal.NonTerminal;

import java.util.Collection;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

// codify 'epsilon' as empty string ""
public final class Follow1 {

    // a complete execution of the 'follow1' algorithm
    public static <T extends AbstractContextFreeProduction> Map<? extends NonTerminal, ? extends Set<String>> follow(
            final Map<? extends Symbol, ? extends Set<String>> first,
            final Set<? extends NonTerminal> nonTerminals,
            final NonTerminal startNonTerminal,
            final Collection<? extends T> productions
    ) {
        var previousResultSets = initializeNonTerminalsResultSets(nonTerminals, startNonTerminal);

        while (true) {
            final var currentResultSets = iteration(first, nonTerminals, productions, previousResultSets);

            if (currentResultSets.equals(previousResultSets)) {
                break;
            }

            previousResultSets = currentResultSets;
        }

        return previousResultSets;
    } // tested, good

    // initializes the result sets of non-terminals for the 'follow' algorithm
    // all the non-terminals are mapped to empty sets, with the exception of the startNonTerminal
    // Note: made public for testing
    public static Map<? extends NonTerminal, ? extends Set<String>> initializeNonTerminalsResultSets(
            final Set<? extends NonTerminal> nonTerminals,
            final NonTerminal startNonTerminal
    ) {
        final Map<NonTerminal, Set<String>> resultSets = nonTerminals.stream().collect(Collectors.toMap(
                        nonTerminal -> nonTerminal,
                        nonTerminal -> Set.of()
                )
        );
        resultSets.put(startNonTerminal, Set.of(""));
        return resultSets;
    } // tested, good

    // one step of the 'follow1' algorithm
    // Note: made public for testing
    public static <T extends AbstractContextFreeProduction> Map<? extends NonTerminal, ? extends Set<String>> iteration(
            final Map<? extends Symbol, ? extends Set<String>> first,
            final Set<? extends NonTerminal> nonTerminals,
            final Collection<? extends T> productions,
            final Map<? extends NonTerminal, ? extends Set<String>> resultSets
    ) {
        final var currentResultSets = ResultSets.copyResultSets(resultSets);

        for (final NonTerminal nonTerminal : nonTerminals) {
            for (final T production : productionsWithSymbolInRightSide(productions, nonTerminal)) {

                final var rightSide = production.rightSide();
                final var currentNonTerminalResultSet = currentResultSets.get(nonTerminal);
                final var leftSideOfProductionResultSet = resultSets.get(production.leftSideNonTerminal());

                // if the current non-terminal appears as the last symbol on the right side of the current production,
                // then add the 'follow' of the non-terminal from the left side of the production to the 'follow' of
                // the current non-terminal
                if (rightSide.indexOf(nonTerminal) == rightSide.size() - 1) {
                    currentNonTerminalResultSet.addAll(leftSideOfProductionResultSet);
                    continue;
                }

                // otherwise, get the 'first' of the symbol immediately to the right of the current non-terminal, and
                // add it to the follow of the current non-terminal
                // *we will denote 'gamma' as the symbol immediately to the right of the current non-terminal
                final var rightSideOfCurrentNonTerminal = rightSide.subList(rightSide.indexOf(nonTerminal) + 1, rightSide.size());
                final var gamma = rightSideOfCurrentNonTerminal.get(0);
                final var firstOfGamma = first.get(gamma);

                // if 'epsilon' is in the 'first' of gamma, then also add the 'follow' of the left side of the current
                // production to the 'follow' of the current non-terminal
                if (firstOfGamma.contains("")) {
                    currentNonTerminalResultSet.addAll(leftSideOfProductionResultSet);
                }

                // 'first1' of gamma without 'epsilon'
                final var filteredFirstOfGamma = firstOfGamma.stream().
                        filter(result -> !result.isEmpty())
                        .collect(Collectors.toUnmodifiableSet());

                currentNonTerminalResultSet.addAll(filteredFirstOfGamma);
            }
        }

        return currentResultSets;
    } // tested, good

    // filters the productions, returning only those productions having the given symbol in the right side
    // Note: made public for testing
    public static <T extends AbstractContextFreeProduction> Collection<? extends T> productionsWithSymbolInRightSide(
            final Collection<? extends T> productions,
            final Symbol symbol
    ) {
        return productions.stream()
                .filter(production -> production.symbolInRightSide(symbol))
                .collect(Collectors.toUnmodifiableSet());
    } // tested, good
}
