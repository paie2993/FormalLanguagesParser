package org.compilers.grammar.model.grammar.context_free.first;

import org.compilers.grammar.model.grammar.context_free.AbstractContextFreeGrammar;
import org.compilers.grammar.model.grammar.context_free.concatenate.Concatenate1;
import org.compilers.grammar.model.grammar.context_free.result_sets.ResultSets;
import org.compilers.grammar.model.production.context_free.AbstractContextFreeProduction;
import org.compilers.grammar.model.vocabulary.Symbol;
import org.compilers.grammar.model.vocabulary.nonterminal.NonTerminal;
import org.compilers.grammar.model.vocabulary.terminal.Terminal;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public final class First1 {

    public static <T extends AbstractContextFreeProduction> Map<? extends Symbol, ? extends Set<String>> first(
            final Set<? extends Terminal> terminals,
            final Set<? extends NonTerminal> nonTerminals,
            final Collection<? extends T> productions
    ) {
        var previousResultSets = initializeResultSets(terminals, nonTerminals, productions);

        while (true) {
            final var currentResultSets = iteration(nonTerminals, productions, previousResultSets);

            if (currentResultSets.equals(previousResultSets)) {
                // no change compared to the previous result sets, meaning that the algorithm is finished
                break;
            }

            // no change compared to the previous result sets, meaning that the algorithm is finished
            previousResultSets = currentResultSets;
        }

        // previousResultSets contains at this point the complete result sets, because the last iteration
        // of the loop does not update 'previousResultSets'
        return previousResultSets;
    } // tested, good

    // obtains the first of an arbitrary sequence of symbols, given computed result sets
    public static <T extends AbstractContextFreeProduction> Set<String> first(
            final List<? extends Symbol> sequence,
            final Map<? extends Symbol, ? extends Set<String>> resultSets
    ) {
        final var sets = sequence.stream().map(resultSets::get).toList();
        return Concatenate1.concatenate1(sets);
    } // pending tests

    // Note: made public for testing
    public static <T extends AbstractContextFreeProduction> Map<? extends Symbol, ? extends Set<String>> iteration(
            final Set<? extends NonTerminal> nonTerminals,
            final Collection<? extends T> productions,
            final Map<? extends Symbol, ? extends Set<String>> previousResultSets
    ) {
        // initializes the current (working) result sets with the result sets from the previous iteration
        final var currentResultSets = ResultSets.copyResultSets(previousResultSets);

        for (final NonTerminal nonTerminal : nonTerminals) { // for each non-terminal
            for (final var production : AbstractContextFreeGrammar.productionsOf(nonTerminal, productions)) { // for each production of the nonTerminal
                // result of applying the first1 function on the right side of the current production
                final var currentResultSetsOfProductionSymbols = resultSetsOfProductionSymbols(production, previousResultSets);

                // apply concatenate1 to extend the result sets of the current non-terminal
                final var updatedResultSets = Concatenate1.concatenate1(currentResultSetsOfProductionSymbols);

                // add newly obtained 'first1' results sets to the already existing ones
                currentResultSets.get(nonTerminal).addAll(updatedResultSets);
            }
        }
        return currentResultSets;
    } // tested, good

    // Note: made public for testing
    public static <T extends AbstractContextFreeProduction> Map<? extends Symbol, ? extends Set<String>> initializeResultSets(
            final Set<? extends Terminal> terminals,
            final Set<? extends NonTerminal> nonTerminals,
            final Collection<? extends T> productions
    ) {
        final var terminalsResultSets = initializeTerminalsResultSets(terminals);
        final var nonTerminalsResultSets = initializeNonTerminalsResultSets(nonTerminals, productions);

        return Stream.concat(
                terminalsResultSets.entrySet().stream(),
                nonTerminalsResultSets.entrySet().stream()
        ).collect(Collectors.toMap(
                Map.Entry::getKey,
                Map.Entry::getValue
        ));
    } // tested, good

    // Note: made public for testing
    public static Map<? extends Symbol, ? extends Set<String>> initializeTerminalsResultSets(
            final Set<? extends Terminal> terminals
    ) {
        final var resultSet = buildEmptyResultSets(terminals);
        terminals.forEach(terminal -> resultSet.get(terminal).add(terminal.value()));
        return resultSet;
    } // tested, good

    // Note: made public for testing
    public static <T extends AbstractContextFreeProduction> Map<? extends Symbol, ? extends Set<String>> initializeNonTerminalsResultSets(
            final Set<? extends NonTerminal> nonTerminals,
            final Collection<? extends T> productions
    ) {
        // initialize empty result sets for the non-terminals
        final var resultSet = buildEmptyResultSets(nonTerminals);

        // productions without epsilon on the right side
        final var filteredProductions = filterEmptyProductions(productions);

        for (final NonTerminal nonTerminal : nonTerminals) { // for each non-terminal ...
            for (final var production : AbstractContextFreeGrammar.productionsOf(nonTerminal, filteredProductions)) { //  productions of the nonTerminal

                // first symbol of the production
                final var firstSymbolOfProduction = production.rightSide().get(0);

                // add first1 of the current non-terminal, only if the symbol is a terminal
                if (Terminal.isInstance(firstSymbolOfProduction)) {
                    final var resultSetOfNonTerminal = resultSet.get(nonTerminal);
                    resultSetOfNonTerminal.add(firstSymbolOfProduction.value());
                }
            }
        }
        return resultSet;
    } // tested, good

    // Note: made public for testing
    public static Map<? extends Symbol, ? extends Set<String>> buildEmptyResultSets(
            final Set<? extends Symbol> symbols
    ) {
        return symbols.stream().collect(Collectors.toMap(
                        symbol -> symbol,
                        terminal -> new HashSet<>()
                )
        );
    } // tested, good

    // filters those productions that have an empty right side (i.e., result in epsilon)
    // Note: made public for testing
    public static <T extends AbstractContextFreeProduction> Collection<? extends T> filterEmptyProductions(final Collection<? extends T> productions) {
        return productions.stream()
                .filter(production -> !production.rightSide().isEmpty())
                .collect(Collectors.toUnmodifiableSet());
    } // tested, good

    // retrieves the result sets of all the symbols on the right side of the production
    // Note: made public for testing
    public static <T extends AbstractContextFreeProduction> List<? extends Set<String>> resultSetsOfProductionSymbols(
            final T production,
            final Map<? extends Symbol, ? extends Set<String>> resultSets
    ) {
        return production.rightSide().stream()
                .map(resultSets::get)
                .toList();
    } // tested, good
}
