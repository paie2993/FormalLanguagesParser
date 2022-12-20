package org.compilers.grammar.model.grammar;

import org.compilers.grammar.model.production.Production;
import org.compilers.grammar.model.vocabulary.NonTerminal;
import org.compilers.grammar.model.vocabulary.Symbol;
import org.compilers.grammar.model.vocabulary.Terminal;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public final class Grammar {

    public static final String EPSILON = "epsilon";

    private final Set<NonTerminal> nonTerminals;
    private final Set<Terminal> terminals;
    private final List<Production> productions;
    private final NonTerminal startingNonTerminal;


    public Grammar(
            final Set<NonTerminal> nonTerminals,
            final Set<Terminal> terminals,
            final Set<Production> productions,
            final NonTerminal startingNonTerminal
    ) {
        Objects.requireNonNull(nonTerminals);
        Objects.requireNonNull(terminals);
        Objects.requireNonNull(productions);
        Objects.requireNonNull(startingNonTerminal);

        this.nonTerminals = nonTerminals;
        this.terminals = terminals;
        this.productions = new ArrayList<>(productions);
        this.startingNonTerminal = startingNonTerminal;
    }

    public Set<NonTerminal> nonTerminals() {
        return nonTerminals;
    }

    public Set<Terminal> terminals() {
        return terminals;
    }

    public List<Production> productions() {
        return productions;
    }

    public NonTerminal startingNonTerminal() {
        return startingNonTerminal;
    }


    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    public Set<Production> productionsOf(final Symbol nonTerminal) {
        return productions.stream()
                .filter(production -> equalsNonTerminal(production.leftSide(), nonTerminal))
                .collect(Collectors.toUnmodifiableSet());
    }

    /**
     * Returns all the productions of the given non-terminal
     * If the non-terminal is not part of the grammar, returns empty set
     */
    public static Set<Production> productionsOf(final Symbol nonTerminal, final Collection<Production> productions) {
        return productions.stream()
                .filter(production -> equalsNonTerminal(production.leftSide(), nonTerminal))
                .collect(Collectors.toUnmodifiableSet());
    }

    // checks if a side is equals to a symbol (true if side has single symbol)
    private static boolean equalsNonTerminal(final List<Symbol> side, final Symbol nonTerminal) {
        if (!(side.size() == 1)) {
            return false;
        }
        final var singleSymbol = side.get(0);
        return nonTerminal.equals(singleSymbol);
    }

    // set of productions in which the given symbol appears in the right-side
    // if the symbol does not appear in any right side, returns empty side
    public Set<Production> inRightSide(final Symbol symbol) {
        return productions.stream()
                .filter(production -> production.rightSide().contains(symbol))
                .collect(Collectors.toUnmodifiableSet());
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    /**
     * Checks if the given non-terminal belongs to the set of non-terminals
     */
    public boolean containsNonTerminal(final Symbol nonTerminal) {
        return NonTerminal.class.equals(nonTerminal.getClass()) && nonTerminals.contains(nonTerminal);
    }

    /**
     * Checks if the given terminal belongs to the set of terminals
     */
    public boolean containsTerminal(final Symbol terminal) {
        return Terminal.class.equals(terminal.getClass()) && terminals.contains(terminal);
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    /**
     * Checks if the grammar is context free
     */
    public boolean isContextFree() {
        return productions.stream().allMatch(Grammar::isContextFreeProduction);
    }

    /**
     * Checks if the production can belong to a context free grammar (has only one terminal on the left side)
     */
    private static boolean isContextFreeProduction(final Production production) {
        final List<Symbol> leftSide = production.leftSide();

        if (leftSide.size() > 1) {
            return false;
        }

        return NonTerminal.class.equals(leftSide.get(0).getClass());
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // a recursive method which keeps concatenating the sets of symbols from the tail of the input list until the start <br>
    // in other words, it first concatenates the last two sets, then concatenates the 3rd set from the list with the <br>
    // concatenation of the last two sets, then ... and so on
    public static Set<String> concatenate1(final List<Set<String>> symbols) {
        if (symbols.isEmpty()) {
            throw new IllegalArgumentException("Can't do it with empty list");
        }

        if (symbols.size() == 1) {
            return symbols.get(0);
        }

        final var firstSet = symbols.get(0); // first set from the input sets of symbols
        final var otherSets = symbols.subList(1, symbols.size()); // slice with the rest of the sets of symbols

        final var otherSetsConcatenation = concatenate1(otherSets);
        return concatenate1(firstSet, otherSetsConcatenation);
    }

    /**
     * Concatenation of length 1 for a pair of sets
     * Is public for testing purposes
     */
    public static Set<String> concatenate1(final Set<String> first, final Set<String> second) {
        if (first.contains(Grammar.EPSILON)) {
            return Stream.concat(
                    first.stream().filter(symbol -> !Grammar.EPSILON.equals(symbol)),
                    second.stream()
            ).collect(Collectors.toUnmodifiableSet());
        }
        return first;
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    public static Set<String> first(
            final Set<? extends Symbol> terminals,
            final Set<? extends Symbol> nonTerminals,
            final Collection<Production> productions,
            final List<Symbol> rightSide
    ) {
        final var firstResultSets = first(terminals, nonTerminals, productions);
        final var resultSets = firstResultSets.values();

        return firstAux(firstResultSets, rightSide);
    }

    private static Set<String> firstAux(
            final Map<? extends Symbol, ? extends Set<String>> firstResultSets,
            final List<Symbol> rightSide
    ) {
        if (rightSide.size() == 1) {
            return firstResultSets.get(rightSide.get(0));
        }
        return concatenate1(
                List.of(
                        firstResultSets.get(rightSide.get(0)),
                        firstAux(firstResultSets, rightSide.subList(1, rightSide.size()))
                )
        );
    }

    public static Set<String> firstTwo(
            final Map<? extends Symbol, ? extends Set<String>> first,
            final Symbol symbol1,
            final Symbol symbol2
    ) {
        return concatenate1(List.of(first.get(symbol1), first.get(symbol2)));
    }

    // this section is dedicated to the implementation of the first1 method
    public static Map<? extends Symbol, ? extends Set<String>> first(
            final Set<? extends Symbol> terminals, final Set<? extends Symbol> nonTerminals, final Collection<Production> productions
    ) {

        var previousResultSets = initializeResultSets(terminals, nonTerminals, productions);
        enrichWithEpsilon(previousResultSets); // added the epsilon as a terminal for brevity and removed it at the end

        var done = false;
        while (!done) {
            final var currentResultSets = oneIterationOfFirst(nonTerminals, productions, previousResultSets);

            if (!currentResultSets.equals(previousResultSets)) { // otherwise, continue the algorithm, and go to the next iteration
                previousResultSets = currentResultSets;
            } else { // no change compared to the previous result sets, meaning that the algorithm is finished
                done = true;
            }
        }

        // previousResultSets contains at this point the complete result sets, because the last iteration <br>
        // of the loop does not update 'previousResultSets'
        return trimEpsilon(previousResultSets);
    }

    // enriches the result sets of each non-terminal
    private static Map<Symbol, Set<String>> oneIterationOfFirst(
            final Set<? extends Symbol> nonTerminals,
            final Collection<Production> productions,
            final Map<Symbol, Set<String>> previousResultSets
    ) {
        // initializes the current (working) result sets with the result sets from the previous iteration
        final var currentResultSets = copyResultSets(previousResultSets);

        for (final var nonTerminal : nonTerminals) { // for each non-terminal
            for (final var production : productionsOf(nonTerminal, productions)) { // for each production of the nonTerminal
                if (areNonEmptyResultSets(production, previousResultSets)) {
                    // result of applying the first1 function on the right side of the current production (where
                    // each production belongs to the currently iterated non-terminal)
                    final var previousResultSetsOfProductionSymbols =
                            previousResultSetsOfProductionSymbols(production, previousResultSets);

                    // apply concatenate1 to extend the result sets of the current non-terminal
                    final var currentProductionResultSetsConcatenate1 = concatenate1(previousResultSetsOfProductionSymbols);

                    // add newly obtained 'first1' results sets to the already existing ones
                    currentResultSets.get(nonTerminal).addAll(currentProductionResultSetsConcatenate1);
                } // else, there is no point in enriching the solution, because applying concatenate1 on a set of <br>
                // sets, one of which is empty, would result in an empty set anyway
            }
        }
        return currentResultSets;
    }

    // does what its name implies
    private static List<Set<String>> previousResultSetsOfProductionSymbols(
            final Production production, final Map<Symbol, Set<String>> previousResultSets
    ) {
        return production.rightSide().stream().map(previousResultSets::get).toList();
    }

    // eliminate epsilon from the result sets, if any exists
    private static Map<? extends Symbol, ? extends Set<String>> trimEpsilon(final Map<? extends Symbol, ? extends Set<String>> resultSets) {
        return resultSets.entrySet().stream()
                .filter(entry -> !Grammar.EPSILON.equals(entry.getKey().value()))
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        Map.Entry::getValue
                ));
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // follow1 method
    public Map<NonTerminal, Set<String>> follow() {
        Map<NonTerminal, Set<String>> previousFollow = initializeNonTerminalsFollow();

        var done = false;
        while (!done) {
            final Map<NonTerminal, Set<String>> currentFollow = previousFollow.entrySet().stream()
                    .collect(Collectors.toMap(
                            Map.Entry::getKey,
                            entry -> new HashSet<>(entry.getValue())
                    ));

            for (final NonTerminal nonTerminal : nonTerminals) {
                for (final Production production : inRightSide(nonTerminal)) {
                    List<Symbol> rightSide = production.rightSide();

                    // if the current non-terminal appears as the last symbol on the right side of the current production <br>
                    // then add the 'follow' of the non-terminal from the left side of the production ot the 'follow' of <br>
                    // the current non-terminal
                    if (rightSide.indexOf(nonTerminal) == rightSide.size() - 1) {
                        currentFollow.get(nonTerminal).addAll(previousFollow.get((NonTerminal) production.leftSide().get(0)));
                        continue;
                    }

                    // otherwise, get the 'first' of the symbol immediately to the right of the current non-terminal <br>
                    // and add it to the follow of the current non-terminal <br>
                    // if 'epsilon' is in the 'first' of the next symbol after the current non-terminal, then also add
                    // the 'follow' of the left side of the current production to the 'follow' of the current non-terminal
                    Symbol gamma = rightSide.get(rightSide.indexOf(nonTerminal) + 1);
                    if (first(terminals, nonTerminals, productions).get(gamma).contains(Grammar.EPSILON)) {
                        currentFollow.get(nonTerminal).addAll(previousFollow.get((NonTerminal) production.leftSide().get(0)));
                    }
                    currentFollow.get(nonTerminal).addAll(first(terminals, nonTerminals, productions).get(gamma).stream()
                            .filter(symbol -> !Grammar.EPSILON.equals(symbol))
                            .collect(Collectors.toUnmodifiableSet()));
                }
            }

            if (!currentFollow.equals(previousFollow)) {
                previousFollow = currentFollow;
            } else {
                done = true;
            }
        }

        return previousFollow;
    }

    private Map<NonTerminal, Set<String>> initializeNonTerminalsFollow() {
        return nonTerminals.stream()
                .collect(Collectors.toMap(
                        nonTerminal -> nonTerminal,
                        nonTerminal -> {
                            if (startingNonTerminal.equals(nonTerminal)) {
                                return new HashSet<>(Set.of(Grammar.EPSILON));
                            }
                            return new HashSet<>();
                        }
                ));
    }

    // return true if there isn't any symbol whose result set is empty
    // in other words, returns true if all the symbols have something in their result set
    // in other words, returns false if there is at least a symbol whose result set is empty
    private static boolean areNonEmptyResultSets(final Production production, final Map<Symbol, Set<String>> previousIterationResultSets) {
        return production.rightSide().stream().noneMatch(symbol -> resultSetOfSymbolIsEmpty(symbol, previousIterationResultSets));
    }

    // true if result set of given symbol is empty
    private static boolean resultSetOfSymbolIsEmpty(final Symbol symbol, final Map<Symbol, Set<String>> iterationResultSets) {
        return iterationResultSets.get(symbol).isEmpty();
    }

    private static Map<Symbol, Set<String>> copyResultSets(final Map<Symbol, Set<String>> resultSet) {
        return resultSet.entrySet().stream()
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        entry -> new HashSet<>(entry.getValue())
                ));
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // this section is dedicated to initializing result sets of the first and follow algorithm

    // initializes the result set of first
    private static Map<Symbol, Set<String>> initializeResultSets(
            final Set<? extends Symbol> terminals, final Set<? extends Symbol> nonTerminals, final Collection<Production> productions
    ) {
        return Stream.concat(
                initializeTerminalsFirst(terminals).entrySet().stream(),
                initializeNonTerminalsFirst(nonTerminals, productions).entrySet().stream()
        ).collect(Collectors.toMap(
                Map.Entry::getKey,
                Map.Entry::getValue
        ));
    }

    // initializes the terminals result set
    private static Map<? extends Symbol, ? extends Set<String>> initializeTerminalsFirst(final Set<? extends Symbol> terminals) {
        final var resultSet = buildResultSetDataStructure(terminals);

        for (final var terminal : terminals) {
            resultSet.get(terminal).add(terminal.value());
        }

        return resultSet;
    }

    // initializes the non-terminals result set
    private static Map<? extends Symbol, ? extends Set<String>> initializeNonTerminalsFirst(
            final Set<? extends Symbol> nonTerminals, final Collection<Production> productions
    ) {
        final var resultSet = buildResultSetDataStructure(nonTerminals);

        for (final var nonTerminal : nonTerminals) { // for each non-terminal ...
            for (final var production : productionsOf(nonTerminal, productions)) { //  productions of the nonTerminal
                final Symbol firstSymbolOfProduction = production.rightSide().get(0); // first symbol of the production

                if (isTerminal(firstSymbolOfProduction)) {
                    final var resultSetOfNonTerminal = resultSet.get(nonTerminal);
                    resultSetOfNonTerminal.add(firstSymbolOfProduction.value());
                }
            }
        }

        return resultSet;
    }

    // lays out the structure of the result set (one column from the first/follow algorithm, check seminar notes)
    private static Map<Symbol, ? extends Set<String>> buildResultSetDataStructure(final Set<? extends Symbol> symbols) {
        return symbols.stream().collect(Collectors.toMap(
                symbol -> symbol,
                terminal -> new HashSet<>(List.of()))
        );
    }

    private static void enrichWithEpsilon(final Map<? super Symbol, ? super Set<String>> resultSet) {
        final var epsilonTerminal = new Terminal(Grammar.EPSILON);
        final var epsilonSet = (Set<String>) Set.of(Grammar.EPSILON);
        resultSet.put(epsilonTerminal, epsilonSet);
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // distinguish between terminals and non-terminals
    private static boolean isTerminal(final Symbol symbol) {
        return Terminal.class.equals(symbol.getClass());
    }
}
