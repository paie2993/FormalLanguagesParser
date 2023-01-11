package org.compilers.grammar;

import org.compilers.grammar.model.grammar.context_free.ContextFreeGrammar;
import org.compilers.grammar.model.grammar.reader.GrammarElements;
import org.compilers.grammar.model.grammar.reader.GrammarReader;
import org.compilers.grammar.model.production.context_free.AbstractContextFreeProduction;
import org.compilers.grammar.model.production.context_free.ContextFreeProductionBuilder;
import org.compilers.grammar.parser.ll1.LL1ParserImpl;
import org.compilers.grammar.parser.output.father_sibling_table.entry.TableEntry;
import org.compilers.grammar.parser.reader.PIFReader;
import org.compilers.grammar.parser.reader.WordReader;

import java.io.*;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public final class Application {

    private static final String FIRST_GRAMMAR_FILE = "Grammar/GrammarImplementation/Grammar/src/main/java/org/compilers/grammar/parser_input/grammars/g1.txt";
    private static final String SEQUENCE_FILE = "Grammar/GrammarImplementation/Grammar/src/main/java/org/compilers/grammar/parser_input/sequences/seq.txt";
    private static final String FIRST_OUTPUT_FILE = "Grammar/GrammarImplementation/Grammar/src/main/java/org/compilers/grammar/parser_output/out1.txt";

    private static final String SECOND_GRAMMAR_FILE = "Grammar/GrammarImplementation/Grammar/src/main/java/org/compilers/grammar/parser_input/grammars/g3.txt";
    private static final String PIF_FILE = "Grammar/GrammarImplementation/Grammar/src/main/java/org/compilers/grammar/parser_input/pifs/p3-2_pif.out";
    private static final String SECOND_OUTPUT_FILE = "Grammar/GrammarImplementation/Grammar/src/main/java/org/compilers/grammar/parser_output/out2.txt";

    public static void main(final String[] args) throws IOException {
        firstGrammar();
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

    private static void printTable(final List<? extends TableEntry> list, final BufferedWriter writer) throws IOException {
        writer.write(String.format("%-25s | %-25s | %-25s | %-25s", "Index", "Info", "Parent", "Right Sibling"));
        writer.newLine();
        final var i = new AtomicInteger(0);
        list.forEach(entry -> {
            try {
                writer.write(String.format("%-25d | %s", i.getAndIncrement(), entry.toString()));
                writer.newLine();
            } catch (final IOException e) {
                throw new RuntimeException(e);
            }
        });
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

        final var tokens = WordReader.readFromFile(SEQUENCE_FILE);
        final var output = ll1Parser.parse(tokens);

        try (final var writer = new BufferedWriter(new FileWriter(FIRST_OUTPUT_FILE))) {

            writer.write("As production string");
            writer.newLine();
            printList(output.asProductionString(), writer);

            writer.write("As derivation string");
            writer.newLine();
            printList(output.asDerivationString(), writer);

            writer.write("As table");
            writer.newLine();
            printTable(output.asFatherSiblingTable(), writer);

        } catch (final IOException exception) {
            System.out.println(exception.getMessage());
        }
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

        final var list = PIFReader.readFromFile(PIF_FILE);
        final var output = ll1Parser.parse(list);

        try (final var writer = new BufferedWriter(new FileWriter(SECOND_OUTPUT_FILE))) {

            writer.write("As production string");
            writer.newLine();
            printList(output.asProductionString(), writer);

            writer.write("As derivation string");
            writer.newLine();
            printList(output.asDerivationString(), writer);

            writer.write("As table");
            writer.newLine();
            printTable(output.asFatherSiblingTable(), writer);

        } catch (final IOException exception) {
            System.out.println(exception.getMessage());
        }
    }
}
