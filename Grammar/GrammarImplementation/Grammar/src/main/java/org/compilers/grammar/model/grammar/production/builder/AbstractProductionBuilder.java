package org.compilers.grammar.model.grammar.production.builder;

import org.compilers.grammar.model.grammar.production.Production;
import org.compilers.grammar.model.grammar.production.unrestricted.UnrestrictedProductionImpl;
import org.compilers.grammar.model.vocabulary.Symbol;
import org.compilers.grammar.model.vocabulary.nonterminal.NonTerminal;
import org.compilers.grammar.model.vocabulary.nonterminal.NonTerminalImpl;
import org.compilers.grammar.model.vocabulary.terminal.Terminal;
import org.compilers.grammar.model.vocabulary.terminal.TerminalImpl;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

public abstract class AbstractProductionBuilder<T extends Production> implements ProductionBuilder<T> {
    private Convention convention = null;

    protected String sideSeparator = null;

    private String symbolSeparator = null;
    private String productionString = null;

    protected Production production = null;

    @Override
    public AbstractProductionBuilder<T> symbols(
            final Set<NonTerminal> nonTerminals,
            final Set<Terminal> terminals) {
        Objects.requireNonNull(nonTerminals);
        Objects.requireNonNull(terminals);

        this.setConvention(nonTerminals, terminals);

        return this;
    }

    @Override
    public AbstractProductionBuilder<T> productionString(String productionString) {
        Objects.requireNonNull(productionString);

        this.setProductionString(productionString);

        return this;
    }

    @Override
    public AbstractProductionBuilder<T> productionString(String productionString, String sideSeparator, String symbolSeparator) {
        Objects.requireNonNull(productionString);
        Objects.requireNonNull(sideSeparator);
        Objects.requireNonNull(symbolSeparator);

        this.setProductionString(productionString, sideSeparator, symbolSeparator);

        return this;
    }

    @Override
    public AbstractProductionBuilder<T> production(Production production) {
        Objects.requireNonNull(production);

        this.setProduction(production);

        return this;
    }

    private void setConvention(
            final Set<NonTerminal> nonTerminals,
            final Set<Terminal> terminals
    ) {
        this.convention = new Convention(nonTerminals, terminals);
    }

    private void setProductionString(final String productionString) {
        this.eraseProduction();
        this.productionString = productionString;
        this.sideSeparator = SIDES_SEPARATOR;
        this.symbolSeparator = SYMBOLS_SEPARATOR;
    }

    private void setProductionString(final String productionString, final String sideSeparator, final String symbolSeparator) {
        this.eraseProduction();
        this.productionString = productionString;
        this.sideSeparator = sideSeparator;
        this.symbolSeparator = symbolSeparator;
    }

    private void eraseProductionString() {
        this.productionString = null;
        this.sideSeparator = null;
        this.symbolSeparator = null;
    }

    private void setProduction(final Production production) {
        this.eraseProductionString();
        this.production = production;
    }

    private void eraseProduction() {
        this.production = null;
    }

    private void parseProductionStringAndSaveProduction() {
        this.production = this.parseProductionString();
    }

    protected void partialBuild() {
        if (Objects.isNull(this.production)) {
            if (Objects.isNull(this.productionString)) {
                throw new RuntimeException("The builder was not provided with eiter a production string or a production object");
            }
            this.parseProductionStringAndSaveProduction();
        }
    }


    protected void eraseAll() {
        this.eraseProductionString();
        this.eraseProduction();
    }


    private Production parseProductionString() {
        final String[] sides = this.productionString.split(this.sideSeparator);

        final List<Symbol> left = buildLeftSide(sides[0]);
        final List<Symbol> right = buildRightSide(sides[1]);

        return new UnrestrictedProductionImpl(left, right);
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
        if (sideString.isEmpty()) {
            return List.of();
        }
        return buildSide(sideString);
    }

    private List<Symbol> buildSide(final String sideString) {
        final String[] symbols = splitSideSymbols(sideString);
        return convertSymbols(symbols);
    }

    /**
     * Receives one side of the production, and splits the vocabs according to [SYMBOLS_SEPARATOR]
     */
    private String[] splitSideSymbols(final String sideString) {
        return sideString.split(this.symbolSeparator);
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
            this.nonTerminalsValues = stringSet(nonTerminals);
            this.terminalsValues = stringSet(terminals);
        }

        public static Set<String> stringSet(final Set<? extends Symbol> symbols) {
            return symbols.stream()
                    .map(Symbol::value)
                    .collect(Collectors.toUnmodifiableSet());
        }

        public Function<String, Symbol> getConverter(final String string) {
            if (terminalsValues.contains(string)) { // terminal
                return TerminalImpl::new;
            } else if (nonTerminalsValues.contains(string)) { // non-terminal
                return NonTerminalImpl::new;
            }
            throw new IllegalArgumentException(String.format("The string %s does not follow any convention", string));
        }
    }
}
