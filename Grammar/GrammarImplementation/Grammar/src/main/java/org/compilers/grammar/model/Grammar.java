package org.compilers.grammar.model;

import org.compilers.grammar.model.vocab.non_terminal.NonTerminal;
import org.compilers.grammar.model.production.Production;
import org.compilers.grammar.model.vocab.terminal.Terminal;
import org.compilers.grammar.model.vocab.Vocab;

import java.util.*;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.stream.Collectors;

public final class Grammar {


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
        final var cfgProductions =
                productions.stream().filter(it -> it.left().size() == 1).collect(Collectors.toUnmodifiableSet());

        return cfgProductions.stream().filter(it -> nonTerminal.equals(it.left().get(0))).collect(Collectors.toUnmodifiableSet());
    }


    /**
     * Checks if the given non-terminal belongs to the set of non-terminals
     */
    public boolean containsNonTerminal(final NonTerminal nonTerminal) {
        return nonTerminals.contains(nonTerminal);
    }


    /**
     * Checks if the grammar is context free
     */
    public boolean isContextFree() {
        for (final Production production : productions) {
            if (!isContextFreeProduction(production)) {
                return false;
            }
        }
        return true;
    }

    /**
     * Checks if the production can belong to a context free grammar (has only one terminal on in the left side)
     */
    private static boolean isContextFreeProduction(final Production production) {
        final List<Vocab> left = production.left();

        if (left.size() > 1) {
            return false;
        }

        final Vocab vocab = left.get(0);
        return vocab.isNonTerminal();
    }

    // First_1
    public Map<NonTerminal, HashSet<String>> first() {
        final var terminalsFirst = initializeTerminalsFirst();
        var previousFirst = initializeNonTerminalsFirst();

        var changed = true;
        while (changed) {
            changed = false;

            var currentFirst = previousFirst
                    .entrySet()
                    .stream()
                    .collect(Collectors.toMap(
                            Map.Entry::getKey,
                            entry -> new HashSet<>(entry.getValue())
                    )); // deep-copy of the previous solution

            for (final var nonTerminal : nonTerminals) { // for each non-terminal

                for (final var production : productions(nonTerminal)) { // contains productions of the nonTerminal
                    checkNonEmptyFirst(production.right(), previousFirst);

                }
            }
        }

        return null;
    }


    private static boolean checkNonEmptyFirst(final List<Vocab> rightSide, final Map<NonTerminal, Set<String>> previousIteration) {
        return rightSide.stream().filter(Vocab::isNonTerminal).noneMatch(it -> previousIteration.get(it).isEmpty());
    }

    private Map<NonTerminal, HashSet<String>> initializeNonTerminalsFirst() {
        final var resultSet = nonTerminals.stream().collect(Collectors.toMap(
                nonTerminal -> nonTerminal,
                nonTerminal -> new HashSet<String>()
        ));

        for (final var nonTerminal : nonTerminals) { // for each non-terminal

            for (final var production : productions(nonTerminal)) { // contains productions of the nonTerminal
                final var firstSymbolOfProduction = production.right().get(0); // first symbol of production

                if (firstSymbolOfProduction.isTerminal()) {
                    resultSet.get(nonTerminal).add(firstSymbolOfProduction.value());
                }
            }
        }

        return resultSet;
    }

    private Map<Terminal, Set<String>> initializeTerminalsFirst() {
        return terminals.stream().collect(Collectors.toMap(
                terminal -> terminal,
                terminal -> Set.of(terminal.value())
        ));
    }
}
