package org.compilers.grammar.model.grammar;

import org.compilers.grammar.model.production.Production;
import org.compilers.grammar.model.production.ProductionBuilder;
import org.compilers.grammar.model.vocabulary.NonTerminal;
import org.compilers.grammar.model.vocabulary.Terminal;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

public final class GrammarReader {
    private static final String SEPARATOR = ";";

    public static Grammar readGrammar(final String fileName) throws IOException {
        try (final BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            final Set<NonTerminal> nonTerminals = readNonTerminals(reader);
            final Set<Terminal> terminals = readTerminals(reader);

            final Set<Production> productions = readProductions(reader, new ProductionBuilder(nonTerminals, terminals));

            final var startingNonTerminal = readStartingNonTerminal(reader);

            return new Grammar(nonTerminals, terminals, productions, startingNonTerminal);
        }
    }

    // reading and building of grammar elements
    private static Set<Terminal> readTerminals(final BufferedReader reader) throws IOException {
        return buildElements(readTokens(reader), Terminal::new);
    }

    private static Set<NonTerminal> readNonTerminals(final BufferedReader reader) throws IOException {
        return buildElements(readTokens(reader), NonTerminal::new);
    }

    private static Set<Production> readProductions(
            final BufferedReader reader,
            final ProductionBuilder productionBuilder
    ) throws IOException {
        return buildElements(readTokens(reader), productionBuilder::buildProduction);
    }

    private static NonTerminal readStartingNonTerminal(final BufferedReader reader) throws IOException {
        return new NonTerminal(readLine(reader));
    }

    /**
     * Applies the builder to each token from [tokens]
     */
    private static <T> Set<T> buildElements(final String[] tokens, final Function<String, T> builder) {
        return Arrays.stream(tokens).map(builder).collect(Collectors.toUnmodifiableSet());
    }

    /**
     * Reads a set of tokens from the buffer
     */
    private static String[] readTokens(final BufferedReader reader) throws IOException {
        return readLine(reader).split(SEPARATOR);
    }

    /**
     * Reads a single line from the buffer, trimming it of whitespaces if necessary
     */
    private static String readLine(final BufferedReader reader) throws IOException {
        return reader.readLine().trim();
    }
}
