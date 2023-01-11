package org.compilers.grammar;

import org.compilers.grammar.model.grammar.context_free.ContextFreeGrammar;
import org.compilers.grammar.model.grammar.reader.GrammarElements;
import org.compilers.grammar.model.grammar.reader.GrammarReader;
import org.compilers.grammar.model.production.context_free.AbstractContextFreeProduction;
import org.compilers.grammar.model.production.context_free.ContextFreeProductionBuilder;
import org.compilers.grammar.parser.ll1.LL1ParserImpl;
import org.compilers.grammar.parser.reader.PIFReader;
import org.compilers.grammar.parser.reader.WordReader;
import org.compilers.grammar.parser.writer.ParserOutputWriter;

import java.io.*;

public final class Application {

    private static final String FIRST_GRAMMAR_FILE = "Grammar/GrammarImplementation/Grammar/src/main/java/org/compilers/grammar/files/parser_input/grammars/g1.txt";
    private static final String SEQUENCE_FILE = "Grammar/GrammarImplementation/Grammar/src/main/java/org/compilers/grammar/files/parser_input/sequences/seq.txt";
    private static final String FIRST_OUTPUT_FILE = "Grammar/GrammarImplementation/Grammar/src/main/java/org/compilers/grammar/files/parser_output/out1.txt";


    private static final String SECOND_GRAMMAR_FILE = "Grammar/GrammarImplementation/Grammar/src/main/java/org/compilers/grammar/files/parser_input/grammars/g2.txt";
    private static final String PIF_FILE = "Grammar/GrammarImplementation/Grammar/src/main/java/org/compilers/grammar/files/parser_input/pifs/p1_pif.out";
    private static final String SECOND_OUTPUT_FILE = "Grammar/GrammarImplementation/Grammar/src/main/java/org/compilers/grammar/files/parser_output/out2_1.txt";


    public static void main(final String[] args) throws IOException {
        firstGrammar();
        secondGrammar();
    }

    private static void firstGrammar() throws IOException {
        // initialize a production builder
        final var productionBuilder = new ContextFreeProductionBuilder();

        GrammarElements<AbstractContextFreeProduction> grammarElements;
        try {
            grammarElements = GrammarReader.readGrammarElements(FIRST_GRAMMAR_FILE, productionBuilder);
        } catch (final IOException e) {
            System.out.println("Could not read from file: " + e.getMessage());
            return;
        }

        final var grammar = new ContextFreeGrammar(
                grammarElements.nonTerminals(),
                grammarElements.terminals(),
                grammarElements.productions(),
                grammarElements.startingNonTerminal()
        );

        final var ll1Parser = new LL1ParserImpl(grammar);

        final var word = WordReader.readFromFile(SEQUENCE_FILE);
        final var output = ll1Parser.parse(word);

        ParserOutputWriter.writeToFile(FIRST_OUTPUT_FILE, output);
    }

    private static void secondGrammar() throws IOException {
        // initialize a production builder
        final var productionBuilder = new ContextFreeProductionBuilder();

        GrammarElements<AbstractContextFreeProduction> grammarElements;
        try {
            grammarElements = GrammarReader.readGrammarElements(SECOND_GRAMMAR_FILE, productionBuilder);
        } catch (final IOException e) {
            System.out.println("Could not read from file: " + e.getMessage());
            return;
        }

        final var grammar = new ContextFreeGrammar(
                grammarElements.nonTerminals(),
                grammarElements.terminals(),
                grammarElements.productions(),
                grammarElements.startingNonTerminal()
        );

        final var ll1Parser = new LL1ParserImpl(grammar);

        final var word = PIFReader.readFromFile(PIF_FILE);
        final var output = ll1Parser.parse(word);

        ParserOutputWriter.writeToFile(SECOND_OUTPUT_FILE, output);
    }
}
