package org.compilers.grammar.model.grammar.builder;

import org.compilers.grammar.model.grammar.Grammar;
import org.compilers.grammar.model.grammar.production.Production;
import org.compilers.grammar.model.grammar.production.builder.ProductionBuilder;
import org.compilers.grammar.model.grammar.production.unrestricted.UnrestrictedProduction;
import org.compilers.grammar.model.grammar.unrestricted.UnrestrictedGrammarImpl;
import org.compilers.grammar.model.vocabulary.nonterminal.NonTerminal;
import org.compilers.grammar.model.vocabulary.nonterminal.NonTerminalImpl;
import org.compilers.grammar.model.vocabulary.terminal.Terminal;
import org.compilers.grammar.model.vocabulary.terminal.TerminalImpl;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.stream.Collectors;

public abstract class AbstractGrammarBuilder<T1 extends Production, T2 extends Grammar<? extends T1>> implements GrammarBuilder<T1, T2> {
    private String fileName = null;

    private String separator = null;

    protected Grammar<? extends Production> grammar = null;

    @Override
    public GrammarBuilder<? extends T1, ? extends T2> file(final String fileName) {
        Objects.requireNonNull(fileName);

        this.setFile(fileName);

        return this;
    }

    @Override
    public GrammarBuilder<? extends T1, ? extends T2> file(String fileName, String separator) {
        Objects.requireNonNull(fileName);
        Objects.requireNonNull(separator);

        this.setFile(fileName, separator);

        return this;
    }

    @Override
    public GrammarBuilder<? extends T1, ? extends T2> grammar(final Grammar<? extends Production> grammar) {
        Objects.requireNonNull(grammar);

        this.setGrammar(grammar);

        return this;
    }

    private void setFile(final String fileName) {
        this.eraseGrammar();
        this.fileName = fileName;
        this.separator = SEPARATOR;
    }

    private void setFile(final String fileName, final String separator) {
        this.eraseGrammar();
        this.fileName = fileName;
        this.separator = separator;
    }

    private void eraseFileName() {
        this.fileName = null;
    }

    private void setGrammar(final Grammar<? extends Production> grammar) {
        this.eraseFileName();
        this.grammar = grammar;
    }

    private void eraseGrammar() {
        this.grammar = null;
    }

    protected T2 partialBuild(final BiFunction<Grammar<? extends Production>, Set<? extends T1>, T2> create, final ProductionBuilder<? extends T1> builder) {
        if (Objects.isNull(this.grammar)) {
            if (Objects.isNull(this.fileName)) {
                throw new RuntimeException("The builder was not provided with eiter a file name or a grammar object");
            }
            this.readFromFileAndSaveGrammar();
        }

        final ProductionBuilder<? extends T1> productionBuilder = builder.symbols(this.grammar.nonTerminals(), this.grammar.terminals());
        final Set<? extends T1> productions = GrammarBuilder.prepareProductions(this.grammar.productions(), productionBuilder);
        final T2 grammar = create.apply(this.grammar, productions);

        this.eraseAll();

        return grammar;
    }

    private void readFromFileAndSaveGrammar() {
        this.grammar = this.readGrammarFromFile();
    }

    protected void eraseAll() {
        this.eraseFileName();
        this.eraseGrammar();
    }

    private Grammar<? extends Production> readGrammarFromFile() {
        try (final BufferedReader reader = new BufferedReader(new FileReader(this.fileName))) {

            final Set<? extends NonTerminal> nonTerminals = readNonTerminals(reader);
            final Set<? extends Terminal> terminals = readTerminals(reader);
            final NonTerminal startingNonTerminal = readStartingNonTerminal(reader);
            final Set<? extends UnrestrictedProduction> productions = readProductions(reader, UnrestrictedProduction.builder().symbols(nonTerminals, terminals));

            return new UnrestrictedGrammarImpl(nonTerminals, terminals, productions, startingNonTerminal);
        } catch (IOException ioException) {
            throw new RuntimeException(ioException);
        }
    }

    // reading and building of grammar elements
    public Set<? extends Terminal> readTerminals(final BufferedReader reader) throws IOException {
        return buildElements(readTokens(reader), TerminalImpl::new);
    }

    public Set<? extends NonTerminal> readNonTerminals(final BufferedReader reader) throws IOException {
        return buildElements(readTokens(reader), NonTerminalImpl::new);
    }

    public Set<? extends UnrestrictedProduction> readProductions(
            final BufferedReader reader,
            final ProductionBuilder<? extends UnrestrictedProduction> productionBuilder
    ) throws IOException {
        return buildElements(readTokensOnLines(reader), productionString -> productionBuilder.productionString(productionString).build());
    }

    public NonTerminal readStartingNonTerminal(final BufferedReader reader) throws IOException {
        return new NonTerminalImpl(readLine(reader));
    }

    /**
     * Applies the builder to each token from [tokens]
     */
    private <T> Set<? extends T> buildElements(final String[] tokens, final Function<String, T> builder) {
        return Arrays.stream(tokens).map(builder).collect(Collectors.toUnmodifiableSet());
    }

    /**
     * Reads a set of tokens from the buffer
     */
    private String[] readTokens(final BufferedReader reader) throws IOException {
        return readLine(reader).split(separator);
    }

    private String[] readTokensOnLines(final BufferedReader reader) {
        return reader.lines()
                .map(String::trim)
                .toList()
                .toArray(new String[]{});
    }

    /**
     * Reads a single line from the buffer, trimming it of whitespaces if necessary
     */
    private String readLine(final BufferedReader reader) throws IOException {
        return reader.readLine().trim();
    }
}
