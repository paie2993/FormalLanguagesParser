package org.compilers.grammar.model.grammar.production.unrestricted.builder;


import org.compilers.grammar.model.grammar.production.builder.AbstractProductionBuilder;
import org.compilers.grammar.model.grammar.production.unrestricted.UnrestrictedProduction;
import org.compilers.grammar.model.grammar.production.unrestricted.UnrestrictedProductionImpl;
import org.compilers.grammar.model.vocabulary.Symbol;
import org.compilers.grammar.model.vocabulary.nonterminal.NonTerminal;
import org.compilers.grammar.model.vocabulary.terminal.Terminal;

import java.util.*;
import java.util.stream.Collectors;

public class UnrestrictedProductionBuilderImpl extends AbstractProductionBuilder implements UnrestrictedProductionBuilder {
    private String sideSeparator = null;

    private String symbolSeparator = null;
    private String productionString = null;


    public UnrestrictedProductionBuilderImpl() {
    }

    @Override
    public UnrestrictedProductionBuilder symbols(
            final Set<NonTerminal> nonTerminals,
            final Set<Terminal> terminals) {
        super.setConvention(nonTerminals, terminals);

        return this;
    }

    @Override
    public UnrestrictedProduction build() {
        Objects.requireNonNull(this.convention);
        Objects.requireNonNull(this.productionString);

        UnrestrictedProduction production = this.parseProductionString();
        this.productionString = null;

        return production;
    }

    @Override
    public UnrestrictedProductionBuilder productionString(String productionString) {
        Objects.requireNonNull(productionString);

        this.productionString = productionString;
        this.sideSeparator = SIDES_SEPARATOR;
        this.symbolSeparator = SYMBOLS_SEPARATOR;

        return this;
    }

    @Override
    public UnrestrictedProductionBuilder productionString(String productionString, String sideSeparator, String symbolSeparator) {
        Objects.requireNonNull(productionString);
        Objects.requireNonNull(sideSeparator);
        Objects.requireNonNull(symbolSeparator);

        this.productionString = productionString;
        this.sideSeparator = sideSeparator;
        this.symbolSeparator = symbolSeparator;

        return this;
    }


    private UnrestrictedProduction parseProductionString() {
        final String[] sides = this.productionString.split(this.sideSeparator);

        final List<Symbol> left = buildLeftSide(sides[0]);
        final List<Symbol> right = buildRightSide(sides[1]);

        return new UnrestrictedProductionImpl(left, right);
    }


    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // section dedicated to building each side of the production

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

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

}
