//package org.compilers.grammar.model.grammar.production;
//
//import org.compilers.grammar.model.grammar.GrammarImpl1;
//import org.compilers.grammar.model.nonterminal.vocabulary.NonTerminalImpl;
//import org.compilers.grammar.model.vocabulary.AbstractSymbol;
//import org.compilers.grammar.model.terminal.vocabulary.TerminalImpl;
//
//import java.util.Arrays;
//import java.util.List;
//import java.util.Optional;
//import java.util.Set;
//import java.util.function.Function;
//import java.util.stream.Collectors;
//
//public class ProductionBuilder {
//
//    private static final String SYMBOLS_SEPARATOR = "`";
//
//    private final Convention convention;
//
//
//    public ProductionBuilder(final Set<NonTerminalImpl> nonTerminals, final Set<TerminalImpl> terminals) {
//        this.convention = new Convention(nonTerminals, terminals);
//    }
//
//
//    public ProductionImpl buildProduction(final String productionString) {
//        final String[] sides = productionString.split(ProductionImpl.SIDES_SEPARATOR);
//
//        final List<AbstractSymbol> left = buildLeftSide(sides[0]);
//        final List<AbstractSymbol> right = buildRightSide(sides[1]);
//
//        return new ProductionImpl(left, right);
//    }
//
//
//    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//    // section dedicated to building each side of the production
//
//    /**
//     * Builds the left side of the production
//     */
//    private List<AbstractSymbol> buildLeftSide(final String sideString) {
//        return buildSide(sideString);
//    }
//
//    /**
//     * Builds the right side of the production, with the special case of empty production
//     */
//    private List<AbstractSymbol> buildRightSide(final String sideString) {
//        final var epsilonOptional = specialCase(sideString);
//
//        return epsilonOptional.<List<AbstractSymbol>>map(List::of)
//                .orElseGet(() -> buildSide(sideString));
//    }
//
//    private Optional<TerminalImpl> specialCase(final String rightSide) {
//        if (GrammarImpl1.EPSILON.equals(rightSide)) {
//            return Optional.of(new TerminalImpl(GrammarImpl1.EPSILON));
//        }
//        return Optional.empty();
//    }
//
//    private List<AbstractSymbol> buildSide(final String sideString) {
//        final String[] symbols = splitSideSymbols(sideString);
//        return convertSymbols(symbols);
//    }
//
//    /**
//     * Receives one side of the production, and splits the vocabs according to [SYMBOLS_SEPARATOR]
//     */
//    private String[] splitSideSymbols(final String sideString) {
//        return sideString.split(SYMBOLS_SEPARATOR);
//    }
//
//    /**
//     * Converts an array of vocab representations to their corresponding terminals and non-terminals
//     */
//    private List<AbstractSymbol> convertSymbols(final String[] symbols) {
//        return Arrays.stream(symbols)
//                .map(this::convertSymbol)
//                .collect(Collectors.toList());
//    }
//
//    private AbstractSymbol convertSymbol(final String symbol) {
//        return convention.getConverter(symbol).apply(symbol);
//    }
//
//    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//    private static class Convention {
//        private final Set<String> nonTerminalsValues;
//        private final Set<String> terminalsValues;
//
//        public Convention(final Set<NonTerminalImpl> nonTerminals, final Set<TerminalImpl> terminals) {
//            this.nonTerminalsValues = stringSet(nonTerminals);
//            this.terminalsValues = stringSet(terminals);
//        }
//
//        private static Set<String> stringSet(final Set<? extends AbstractSymbol> symbols) {
//            return symbols.stream()
//                    .map(AbstractSymbol::value)
//                    .collect(Collectors.toUnmodifiableSet());
//        }
//
//        private Function<String, AbstractSymbol> getConverter(final String string) {
//            if (terminalsValues.contains(string)) { // terminal
//                return TerminalImpl::new;
//            } else if (nonTerminalsValues.contains(string)) { // non-terminal
//                return NonTerminalImpl::new;
//            }
//            throw new IllegalArgumentException(String.format("The string %s does not follow any convention", string));
//        }
//    }
//}
