package org.compilers.grammar.model.grammar.reader;

import org.compilers.grammar.model.production.Production;
import org.compilers.grammar.model.production.builder.ProductionBuilder;
import org.compilers.grammar.model.vocabulary.nonterminal.NonTerminal;
import org.compilers.grammar.model.vocabulary.nonterminal.NonTerminalImpl;
import org.compilers.grammar.model.vocabulary.terminal.Terminal;
import org.compilers.grammar.model.vocabulary.terminal.TerminalImpl;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public final class GrammarReader {

    private final static String SEPARATOR = ";";

    // reading all the grammar elements from the file
    //
    public static <T extends Production> GrammarElements<T> readGrammarElements(
            final String fileName,
            final ProductionBuilder<T> productionBuilder
    ) throws IOException {
        try (final var reader = new BufferedReader(new FileReader(fileName))) {

            final var nonTerminals = readNonTerminals(reader);
            final var terminals = readTerminals(reader);

            // must set the context of non-terminals and terminals, otherwise productions can't be read and built
            productionBuilder.context(nonTerminals, terminals);
            final var productions = readProductions(reader, productionBuilder);
            final var startingNonTerminal = readStartingNonTerminal(reader);

            return new GrammarElements<>(nonTerminals, terminals, productions, startingNonTerminal);
        }
    }

    // the methods in this group are dependent, should be called in order:
    // 1. readNonTerminals
    // 2. readTerminals
    // 3. readProductions
    // 4. readStartingNonTerminal
    // failure to comply leads to undefined behaviour
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // reading the non-terminals
    private static Set<? extends NonTerminal> readNonTerminals(final BufferedReader reader) throws IOException {
        final var tokens = readTokens(reader);
        return Arrays.stream(tokens).map(NonTerminalImpl::new).collect(Collectors.toUnmodifiableSet());
    }

    // reading the terminals
    private static Set<? extends Terminal> readTerminals(final BufferedReader reader) throws IOException {
        final var tokens = readTokens(reader);
        return Arrays.stream(tokens).map(TerminalImpl::new).collect(Collectors.toUnmodifiableSet());
    }

    // reading (and building) the productions
    private static <T extends Production> List<T> readProductions(
            final BufferedReader reader,
            final ProductionBuilder<T> productionBuilder
    ) throws IOException {
        final var tokens = readTokens(reader);
        return Arrays.stream(tokens)
                .map(productionBuilder::build)
                .toList();
    }

    // reading the starting symbol
    private static NonTerminal readStartingNonTerminal(final BufferedReader reader) throws IOException {
        final var line = readLine(reader);
        return new NonTerminalImpl(line);
    }
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    // reading utility
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // Reads a set of tokens from the buffer
    private static String[] readTokens(final BufferedReader reader) throws IOException {
        return readLine(reader).split(SEPARATOR);
    }

    // Reads a single line from the buffer, trimming it of whitespaces if necessary
    private static String readLine(final BufferedReader reader) throws IOException {
        return reader.readLine().trim();
    }
}
