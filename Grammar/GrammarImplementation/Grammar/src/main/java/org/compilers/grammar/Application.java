package org.compilers.grammar;

import org.compilers.grammar.model.grammar.context_free.ContextFreeGrammar;
import org.compilers.grammar.model.grammar.reader.GrammarElements;
import org.compilers.grammar.model.grammar.reader.GrammarReader;
import org.compilers.grammar.model.production.context_free.AbstractContextFreeProduction;
import org.compilers.grammar.model.production.context_free.ContextFreeProductionBuilder;
import org.compilers.grammar.parser.ll1.LL1ParserImpl;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public final class Application {

    private static final String FIRST_GRAMMAR_FILE = "Grammar/GrammarImplementation/Grammar/src/main/java/org/compilers/grammar/parser_input/grammars/g1.txt";
    private static final String SEQUENCE_FILE = "Grammar/GrammarImplementation/Grammar/src/main/java/org/compilers/grammar/parser_input/sequences/seq.txt";
    private static final String FIRST_OUTPUT_FILE = "Grammar/GrammarImplementation/Grammar/src/main/java/org/compilers/grammar/parser_output/out1.txt";

    private static final String SECOND_GRAMMAR_FILE = "Grammar/GrammarImplementation/Grammar/src/main/java/org/compilers/grammar/parser_input/grammars/g2.txt";
    private static final String PIF_FILE = "Grammar/GrammarImplementation/Grammar/src/main/java/org/compilers/grammar/parser_input/pifs/pif1.txt";
    private static final String SECOND_OUTPUT_FILE = "Grammar/GrammarImplementation/Grammar/src/main/java/org/compilers/grammar/parser_output/out2.txt";

    public static void main(final String[] args) {
//        firstGrammar();
        secondGrammar();
    }

    private static void printList(final List<?> list, final BufferedWriter writer) {
        list.forEach(element -> {
            try {
                writer.write(element.toString());
                writer.newLine();
            } catch (final IOException e) {
                System.out.println(e.getMessage());
            }
        });
    }

    private static void secondGrammar() {
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

        final List<String> list = new ArrayList<>();
        try (final var reader = new BufferedReader(new FileReader(PIF_FILE))) {
            var line = reader.readLine();
            while (line != null) {
                final var processedLine = line.trim();
                final var tokens = processedLine.split("\\|");
                final var pifLabel = tokens[0];
                list.add(pifLabel);
                line = reader.readLine();
            }
        } catch (final IOException e) {
            System.out.println(e.getMessage());
        }

        final var output = ll1Parser.parse(list);

        try (final var writer = new BufferedWriter(new FileWriter(FIRST_OUTPUT_FILE))) {

            writer.write("As production string");
            writer.newLine();
            printList(output.asProductionString(), writer);

            writer.write("As derivation string");
            writer.newLine();
            printList(output.asDerivationString(), writer);

        } catch (final IOException exception) {
            System.out.println(exception.getMessage());
        }
    }

    private static void firstGrammar() {
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

        var word = "";
        try (final var reader = new BufferedReader(new FileReader(SEQUENCE_FILE))) {
            word = reader.readLine().trim();
        } catch (final IOException e) {
            System.out.println(e.getMessage());
            return;
        }

        final var tokens = Arrays.stream(word.split("")).toList();
        final var output = ll1Parser.parse(tokens);

        try (final var writer = new BufferedWriter(new FileWriter(FIRST_OUTPUT_FILE))) {

            writer.write("As production string");
            writer.newLine();
            printList(output.asProductionString(), writer);

            writer.write("As derivation string");
            writer.newLine();
            printList(output.asDerivationString(), writer);

        } catch (final IOException exception) {
            System.out.println(exception.getMessage());
        }
    }


}
