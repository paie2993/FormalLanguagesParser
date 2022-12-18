package org.compilers.grammar.model.grammar;

import org.compilers.grammar.model.production.Production;
import org.compilers.grammar.model.vocabulary.NonTerminal;
import org.compilers.grammar.model.vocabulary.Symbol;
import org.compilers.grammar.model.vocabulary.Terminal;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Grammar {
    public static final String EPSILON = "epsilon";

    private final Set<NonTerminal> nonTerminals;

    private final Set<Terminal> terminals;

    private final Set<Production> productions;

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
        this.productions = productions;
        this.startingNonTerminal = startingNonTerminal;
    }

    public Set<NonTerminal> nonTerminals() {
        return nonTerminals;
    }

    public Set<Terminal> terminals() {
        return terminals;
    }

    public Set<Production> productions() {
        return productions;
    }

    public NonTerminal startingNonTerminal() {
        return startingNonTerminal;
    }


    /**
     * Returns all the productions of the given non-terminal
     * If the non-terminal is not part of the grammar, returns empty set
     */
    public Set<Production> productions(final NonTerminal nonTerminal) {
        return this.productions.stream()
                .filter(it -> it.leftSide().size() == 1 && nonTerminal.equals(it.leftSide().get(0)))
                .collect(Collectors.toUnmodifiableSet());
    }

    public Set<Production> rightSideProductions(final NonTerminal nonTerminal) {
        return this.productions.stream()
                .filter(production -> production.rightSide().contains(nonTerminal))
                .collect(Collectors.toUnmodifiableSet());
    }


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

    /**
     * Checks if the grammar is context free
     */
    public boolean isContextFree() {
        return this.productions.stream().allMatch(Grammar::isContextFreeProduction);
    }

    public static Set<String> concatenate1(final List<Set<String>> symbols) {
        if (symbols.isEmpty()) {
            throw new IllegalArgumentException("Can't do it with empty list");
        }

        if (symbols.size() == 1) {
            return symbols.get(0);
        }

        return concatenate1(symbols.get(0), concatenate1(symbols.subList(1, symbols.size())));
    }

    /**
     * Concatenation of length 1 for a pair of sets
     */
    private static Set<String> concatenate1(final Set<String> first, final Set<String> second) {
        if (first.contains(Grammar.EPSILON)) {
            return Stream.concat(
                    first.stream()
                            .filter(symbol -> !Grammar.EPSILON.equals(symbol)),
                    second.stream()
            ).collect(Collectors.toUnmodifiableSet());
        }
        return first;
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


    // First_1
    public Map<Symbol, Set<String>> first() {
        Map<Symbol, Set<String>> previousFirst = Stream.concat(
                initializeTerminalsFirst().entrySet().stream(),
                initializeNonTerminalsFirst().entrySet().stream()
        ).collect(Collectors.toMap(
                Map.Entry::getKey,
                Map.Entry::getValue
        ));
//        added the epsilon as a terminal for brevity and removed it at the end
        previousFirst.put(new Terminal(Grammar.EPSILON), Set.of(Grammar.EPSILON));

        while (true) {
            final Map<Symbol, Set<String>> currentFirst = previousFirst.entrySet().stream()
                    .collect(Collectors.toMap(
                            Map.Entry::getKey,
                            entry -> new HashSet<>(entry.getValue())
                    )); // deep-copy of the previous solution

            for (final NonTerminal nonTerminal : nonTerminals) { // for each non-terminal

                for (final Production production : productions(nonTerminal)) { // contains productions of the nonTerminal
                    if (checkNonEmptyFirst(production.rightSide(), previousFirst)) {
//                        extract previous first for each symbol of the right side
                        final List<Set<String>> rightSidePreviousFirst = production
                                .rightSide()
                                .stream()
                                .map(previousFirst::get)
                                .toList();
                        currentFirst.get(nonTerminal).addAll(concatenate1(rightSidePreviousFirst));
                    }
                }

            }
            if (currentFirst.equals(previousFirst)) {
                break;
            }
            previousFirst = currentFirst;
        }


        return previousFirst.entrySet().stream()
                .filter(entry -> !Grammar.EPSILON.equals(entry.getKey().value()))
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        Map.Entry::getValue
                ));
    }

    public Map<NonTerminal, Set<String>> follow() {
        Map<NonTerminal, Set<String>> previousFollow = initializeNonTerminalsFollow();
        while (true) {
            final Map<NonTerminal, Set<String>> currentFollow = previousFollow.entrySet().stream()
                    .collect(Collectors.toMap(
                            Map.Entry::getKey,
                            entry -> new HashSet<>(entry.getValue())
                    ));

            for (final NonTerminal nonTerminal : nonTerminals) {
                for (final Production production : rightSideProductions(nonTerminal)) {
                    List<Symbol> rightSide = production.rightSide();
                    if (rightSide.indexOf(nonTerminal) == rightSide.size() - 1) {
                        currentFollow.get(nonTerminal).addAll(previousFollow.get((NonTerminal) production.leftSide().get(0)));
                        continue;
                    }
                    Symbol gamma = rightSide.get(rightSide.indexOf(nonTerminal) + 1);
                    if (this.first().get(gamma).contains(Grammar.EPSILON)) {
                        currentFollow.get(nonTerminal).addAll(previousFollow.get((NonTerminal) production.leftSide().get(0)));
                    }
                    currentFollow.get(nonTerminal).addAll(this.first().get(gamma).stream()
                            .filter(symbol -> !Grammar.EPSILON.equals(symbol))
                            .collect(Collectors.toUnmodifiableSet()));
                }
            }

            if (currentFollow.equals(previousFollow)) {
                break;
            }
            previousFollow = currentFollow;
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


    private static boolean checkNonEmptyFirst(final List<Symbol> rightSide, final Map<Symbol, Set<String>> previousIteration) {
        return rightSide.stream().noneMatch(it -> previousIteration.get(it).isEmpty());
    }

    private Map<Symbol, Set<String>> initializeNonTerminalsFirst() {
        final Map<Symbol, Set<String>> resultSet = nonTerminals.stream()
                .collect(Collectors.toMap(
                        nonTerminal -> nonTerminal,
                        nonTerminal -> new HashSet<>())
                );

        for (final NonTerminal nonTerminal : nonTerminals) { // for each non-terminal
            for (final Production production : productions(nonTerminal)) { // contains productions of the nonTerminal
                final Symbol firstSymbolOfProduction = production.rightSide().get(0); // first symbol of the production

                if (Terminal.class.equals(firstSymbolOfProduction.getClass())) {
                    resultSet.get(nonTerminal).add(firstSymbolOfProduction.value());
                }
            }
        }

        return resultSet;
    }

    private Map<Symbol, Set<String>> initializeTerminalsFirst() {
        return terminals.stream()
                .collect(Collectors.toMap(
                        terminal -> terminal,
                        terminal -> Set.of(terminal.value()))
                );
    }
}
