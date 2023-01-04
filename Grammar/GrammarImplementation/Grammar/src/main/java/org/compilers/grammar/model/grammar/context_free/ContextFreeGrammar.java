package org.compilers.grammar.model.grammar.context_free;


import org.compilers.grammar.model.grammar.context_dependent.ContextDependentGrammar;
import org.compilers.grammar.model.grammar.production.context_free.ContextFreeProduction;
import org.compilers.grammar.model.vocabulary.Symbol;
import org.compilers.grammar.model.vocabulary.nonterminal.NonTerminal;
import org.compilers.grammar.model.vocabulary.terminal.Terminal;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public interface ContextFreeGrammar extends ContextDependentGrammar {
    @Override
    Set<? extends ContextFreeProduction> productions();

    @Override
    Set<? extends ContextFreeProduction> haveSymbolInRightSide(final Symbol symbol);

    @Override
    Set<? extends ContextFreeProduction> haveSymbolInLeftSide(final Symbol symbol);

    static Set<ContextFreeProduction> productionsOf(
            final Collection<? extends ContextFreeProduction> productions,
            final NonTerminal nonTerminal
    ) {
        Objects.requireNonNull(productions);
        Objects.requireNonNull(nonTerminal);

        return productions
                .stream()
                .filter(production -> nonTerminal.equals(production.leftSideNonTerminal()))
                .collect(Collectors.toUnmodifiableSet());
    }

    static Map<? extends Symbol, ? extends Set<String>> first(
            final Set<? extends Terminal> terminals,
            final Set<? extends NonTerminal> nonTerminals,
            final Collection<? extends ContextFreeProduction> productions
    ) {

        Map<? extends Symbol, ? extends Set<String>> previousResultSets = initializeResultSets(terminals, nonTerminals, productions);

        var done = false;
        while (!done) {
            final var currentResultSets = oneIterationOfFirst(nonTerminals, productions, previousResultSets);

            if (currentResultSets.equals(previousResultSets)) { // otherwise, continue the algorithm, and go to the next iteration
                done = true;
            } else { // no change compared to the previous result sets, meaning that the algorithm is finished
                previousResultSets = currentResultSets;
            }
        }

        // previousResultSets contains at this point the complete result sets, because the last iteration
        // of the loop does not update 'previousResultSets'
        return previousResultSets;
    }

    private static Map<? extends Symbol, ? extends Set<String>> oneIterationOfFirst(
            final Set<? extends NonTerminal> nonTerminals,
            final Collection<? extends ContextFreeProduction> productions,
            final Map<? extends Symbol, ? extends Set<String>> previousResultSets
    ) {
        // initializes the current (working) result sets with the result sets from the previous iteration
        final Map<? extends Symbol, ? extends Set<String>> currentResultSets = copyResultSets(previousResultSets);

        for (final NonTerminal nonTerminal : nonTerminals) { // for each non-terminal
            for (final ContextFreeProduction production : productionsOf(productions, nonTerminal)) { // for each production of the nonTerminal
                if (areNonEmptyResultSets(previousResultSets, production)) {

                    // result of applying the first1 function on the right side of the current production (where
                    // each production belongs to the currently iterated non-terminal)
                    final List<? extends Set<String>> previousResultSetsOfProductionSymbols =
                            previousResultSetsOfProductionSymbols(previousResultSets, production);

                    // apply concatenate1 to extend the result sets of the current non-terminal
                    final Set<String> currentProductionResultSetsConcatenate1 = concatenate1(previousResultSetsOfProductionSymbols);

                    // add newly obtained 'first1' results sets to the already existing ones
                    currentResultSets.get(nonTerminal).addAll(currentProductionResultSetsConcatenate1);
                } // else, there is no point in enriching the solution, because applying concatenate1 on a set of <br>
                // sets, one of which is empty, would result in an empty set anyway
            }
        }
        return currentResultSets;
    }

    private static Map<? extends Symbol, ? extends Set<String>> copyResultSets(
            final Map<? extends Symbol, ? extends Set<String>> resultSet
    ) {
        return resultSet
                .entrySet()
                .stream()
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        entry -> new HashSet<>(entry.getValue())
                ));
    }

    // does what its name implies
    private static List<? extends Set<String>> previousResultSetsOfProductionSymbols(
            final Map<? extends Symbol, ? extends Set<String>> previousResultSets,
            final ContextFreeProduction production
    ) {
        return production
                .rightSide()
                .stream()
                .map(previousResultSets::get)
                .toList();
    }

    private static Map<Symbol, Set<String>> initializeResultSets(
            final Set<? extends Terminal> terminals,
            final Set<? extends NonTerminal> nonTerminals,
            final Collection<? extends ContextFreeProduction> productions
    ) {
        return Stream.concat(
                initializeTerminalsFirst(terminals).entrySet().stream(),
                initializeNonTerminalsFirst(nonTerminals, productions).entrySet().stream()
        ).collect(Collectors.toMap(
                Map.Entry::getKey,
                Map.Entry::getValue
        ));
    }

    private static Map<? extends Symbol, ? extends Set<String>> initializeTerminalsFirst(
            final Set<? extends Terminal> terminals
    ) {
        final Map<? extends Symbol, ? extends Set<String>> resultSet = buildEmptyResultSet(terminals);

        for (final Terminal terminal : terminals) {
            resultSet.get(terminal).add(terminal.value());
        }

        return resultSet;
    }

    private static Map<? extends Symbol, ? extends Set<String>> initializeNonTerminalsFirst(
            final Set<? extends NonTerminal> nonTerminals,
            final Collection<? extends ContextFreeProduction> productions
    ) {
        final Map<? extends Symbol, ? extends Set<String>> resultSet = buildEmptyResultSet(nonTerminals);

        for (final NonTerminal nonTerminal : nonTerminals) { // for each non-terminal ...
            for (final var production : productionsOf(productions, nonTerminal)) { //  productions of the nonTerminal
                final Symbol firstSymbolOfProduction = production.rightSide().get(0); // first symbol of the production

                if (Terminal.isTerminal(firstSymbolOfProduction)) {
                    final Set<String> resultSetOfNonTerminal = resultSet.get(nonTerminal);
                    resultSetOfNonTerminal.add(firstSymbolOfProduction.value());
                }
            }
        }

        return resultSet;
    }


    private static Map<? extends Symbol, ? extends Set<String>> buildEmptyResultSet(
            final Set<? extends Symbol> symbols
    ) {
        return symbols
                .stream()
                .collect(Collectors.toMap(
                        symbol -> symbol,
                        terminal -> new HashSet<>()));
    }

    private static boolean areNonEmptyResultSets(
            final Map<? extends Symbol, ? extends Set<String>> previousIterationResultSets,
            final ContextFreeProduction production
    ) {
        return production
                .rightSide()
                .stream()
                .noneMatch(symbol -> resultSetOfSymbolIsEmpty(previousIterationResultSets, symbol));
    }

    private static boolean resultSetOfSymbolIsEmpty(
            final Map<? extends Symbol, ? extends Set<String>> iterationResultSets,
            final Symbol symbol
    ) {
        return iterationResultSets.containsKey(symbol) && iterationResultSets.get(symbol).isEmpty();
    }


    /////////////////////

    static Set<String> concatenate1(final List<? extends Set<String>> symbols) {
        if (symbols.isEmpty()) {
            return Set.of();
        }

        if (symbols.size() == 1) {
            return symbols.get(0);
        }

        final Set<String> firstSet = symbols.get(0);
        final List<? extends Set<String>> otherSets = symbols.subList(1, symbols.size());

        final Set<String> otherSetsConcatenation = concatenate1(otherSets);
        return concatenate1(firstSet, otherSetsConcatenation);
    }

    static Set<String> concatenate1(final Set<String> leftSet, final Set<String> rightSet) {
        return leftSet
                .stream()
                .flatMap(left -> rightSet
                        .stream()
                        .map(right -> concatenate1(left, right)))
                .collect(Collectors.toUnmodifiableSet());
    }

    static String concatenate1(final String left, final String right) {
        if (left.isEmpty()) {
            return right;
        }
        return left;
    }
}
