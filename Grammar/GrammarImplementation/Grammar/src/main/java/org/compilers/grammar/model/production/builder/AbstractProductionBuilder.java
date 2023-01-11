package org.compilers.grammar.model.production.builder;

import org.compilers.grammar.model.production.Production;
import org.compilers.grammar.model.vocabulary.Symbol;
import org.compilers.grammar.model.vocabulary.nonterminal.NonTerminal;
import org.compilers.grammar.model.vocabulary.nonterminal.NonTerminalImpl;
import org.compilers.grammar.model.vocabulary.terminal.Terminal;
import org.compilers.grammar.model.vocabulary.terminal.TerminalImpl;

import java.util.*;
import java.util.stream.Collectors;

public abstract class AbstractProductionBuilder<T extends Production> implements ProductionBuilder<T> {

    private Context context;

    // abstract, delegating the actual creation of the production
    abstract protected T buildFromSides(
            final List<? extends Symbol> leftSide,
            final List<? extends Symbol> rightSide
    );

    // overrides
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    @Override
    public AbstractProductionBuilder<T> context(
            final Set<? extends NonTerminal> nonTerminals,
            final Set<? extends Terminal> terminals
    ) {
        Objects.requireNonNull(nonTerminals);
        Objects.requireNonNull(terminals);
        this.context = new Context(nonTerminals, terminals);
        return this;
    }

    @Override
    public T build(final String stringRepresentation) {
        final var sides = stringRepresentation.split(ProductionBuilder.SIDES_SEPARATOR);

        if (sides.length < 1 || sides.length > 2) {
            throw new IllegalArgumentException("Invalid production string: " + stringRepresentation);
        }

        if (sides.length == 2 && sides[0].isEmpty()) {
            throw new IllegalArgumentException("Invalid left side (empty) of production string: " + stringRepresentation);
        }

        List<? extends Symbol> leftSide;
        List<? extends Symbol> rightSide;
        try {
            leftSide = buildLeftSide(sides[0]);
            rightSide = sides.length == 1 ? List.of() : buildRightSide(sides[1]);
        } catch (final Exception e) {
            System.err.println("Exception here: " + stringRepresentation);
            System.err.println("Context of production builder: ");
            System.err.println("NonTerminals: " + context.nonTerminals);
            System.err.println("Terminals: " + context.terminals);
            throw e;
        }

        return buildFromSides(leftSide, rightSide);
    }

    // Builds the left side of the production
    private List<? extends Symbol> buildLeftSide(final String sideString) {
        return buildSide(sideString);
    }

    //Builds the right side of the production, with the special case of empty production
    private List<? extends Symbol> buildRightSide(final String sideString) {
        if (sideString.isEmpty()) {
            return List.of();
        }
        return buildSide(sideString);
    }

    private List<? extends Symbol> buildSide(final String sideString) {
        final String[] symbols = splitSideSymbols(sideString);
        return convertSymbols(symbols);
    }


    // parsing
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // Receives one side of the production, and splits the vocabs according to [SYMBOLS_SEPARATOR]
    private String[] splitSideSymbols(final String sideString) {
        return sideString.split(ProductionBuilder.SYMBOLS_SEPARATOR);
    }

    // conversion
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // Converts an array of symbol representations to their corresponding terminals and non-terminals
    private List<? extends Symbol> convertSymbols(final String[] symbols) {
        return Arrays.stream(symbols).map(context::convert).toList();
    }

    private static class Context {

        private final Set<String> nonTerminals;
        private final Set<String> terminals;

        public Context(final Set<? extends NonTerminal> nonTerminals, final Set<? extends Terminal> terminals) {
            Objects.requireNonNull(nonTerminals);
            Objects.requireNonNull(terminals);
            this.nonTerminals = strings(nonTerminals);
            this.terminals = strings(terminals);
        }

        private Set<String> strings(final Collection<? extends Symbol> symbols) {
            return symbols.stream().map(Symbol::value).collect(Collectors.toUnmodifiableSet());
        }

        public Symbol convert(final String stringRepresentation) {
            Objects.requireNonNull(stringRepresentation);
            if (nonTerminals.contains(stringRepresentation)) {
                return new NonTerminalImpl(stringRepresentation);
            } else if (terminals.contains(stringRepresentation)) {
                return new TerminalImpl(stringRepresentation);
            }
            throw new IllegalArgumentException(
                    "Provided string is neither non-terminal nor terminal in the current context: " + stringRepresentation
            );
        }
    }
}
