package org.compilers.grammar.model.production;

import org.compilers.grammar.model.grammar.Grammar;
import org.compilers.grammar.model.vocabulary.NonTerminal;
import org.compilers.grammar.model.vocabulary.Symbol;
import org.compilers.grammar.model.vocabulary.Terminal;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

public class ProductionBuilder {
    private static final String SYMBOLS_SEPARATOR = "`";

    private final Convention convention;

    public ProductionBuilder(final Set<NonTerminal> nonTerminals, final Set<Terminal> terminals) {
        this.convention = new Convention(nonTerminals, terminals);
    }

    public ProductionBuilder(final Grammar grammar) {
        this.convention = new Convention(grammar.nonTerminals(), grammar.terminals());
    }

    public Production buildProduction(final String productionString) {
        final String[] sides = productionString.split(Production.SIDES_SEPARATOR);

        final List<Symbol> left = buildLeftSide(sides[0]);
        final List<Symbol> right = buildRightSide(sides[1]);

        return new Production(left, right);
    }

    /**
     * Builds the left side of the production
     */
    private List<Symbol> buildLeftSide(final String sideString) {
        return buildSide(sideString);
    }

    /**
     * Builds the right side of the production, with the special case of empty production
     */
    private List<Symbol> buildRightSide(final String sideString) {
        final var epsilonOptional = specialCase(sideString);
        return epsilonOptional.<List<Symbol>>map(List::of).orElseGet(() -> buildSide(sideString));
    }

    private Optional<Terminal> specialCase(final String rightSide) {
        if (Grammar.EPSILON.equals(rightSide)) {
            return Optional.of(new Terminal(Grammar.EPSILON));
        }
        return Optional.empty();
    }

    private List<Symbol> buildSide(final String sideString) {
        final String[] symbols = processSymbols(sideString);
        return convertSymbols(symbols);
    }

    /**
     * Receives one side of the production, and splits the vocabs according to [SYMBOLS_SEPARATOR]
     */
    private String[] processSymbols(final String sideString) {
        return sideString.split(SYMBOLS_SEPARATOR);
    }

    /**
     * Converts an array of vocab representations to their corresponding terminals and non-terminals
     */
    private List<Symbol> convertSymbols(final String[] symbols) {
        return Arrays.stream(symbols)
                .map(this::convertSymbol)
                .collect(Collectors.toList());
    }

    private Symbol convertSymbol(final String symbol) {
        return this.convention.getConverter(symbol).apply(symbol);
    }


    private static class Convention {
        private final Set<String> nonTerminalsValues;
        private final Set<String> terminalsValues;

        public Convention(final Set<NonTerminal> nonTerminals, final Set<Terminal> terminals) {
            this.nonTerminalsValues = nonTerminals.stream()
                    .map(Symbol::value)
                    .collect(Collectors.toUnmodifiableSet());
            this.terminalsValues = terminals.stream()
                    .map(Symbol::value)
                    .collect(Collectors.toUnmodifiableSet());
        }

        private Function<String, Symbol> getConverter(final String string) {
            if (terminalsValues.contains(string)) { // terminal
                return Terminal::new;
            } else if (nonTerminalsValues.contains(string)) { // non-terminal
                return NonTerminal::new;
            }
            throw new IllegalArgumentException(String.format("The string %s does not follow any convention", string));
        }
    }
}
