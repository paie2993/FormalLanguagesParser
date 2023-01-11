package org.compilers.grammar.parser.writer;

import org.compilers.grammar.parser.output.ParserOutput;
import org.compilers.grammar.parser.output.father_sibling_table.entry.TableEntry;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public interface ParserOutputWriter {

    static void writeToFile(final String fileName, final ParserOutput output) throws IOException {
        write(fileName, output);
    }

    private static void write(final String fileName, final ParserOutput output) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {
            writer.write("As production string:");
            writer.newLine();
            writeList(writer, output.asProductionString());

            writer.write("As derivation sequence:");
            writer.newLine();
            writeList(writer, output.asDerivationString());

            writer.write("As father-sibling table:");
            writer.newLine();
            writeTable(writer, output.asFatherSiblingTable());
        }
    }

    private static void writeList(final BufferedWriter writer, final List<?> list) {
        list.forEach(element -> {
            try {
                writer.write(element.toString());
                writer.newLine();
            } catch (final IOException e) {
                throw new RuntimeException(e);
            }
        });
    }

    private static void writeTable(final BufferedWriter writer, final List<? extends TableEntry> table) throws IOException {
        writer.write(String.format("%-20s | %-20s | %-20s | %-20s", "Index", "Info", "Parent", "Right sibling"));
        final var index = new AtomicInteger(0);
        table.forEach(entry -> {
            try {
                writer.write(String.format("%-20s | %s", index.getAndIncrement(), entry.toString()));
                writer.newLine();
            } catch (final IOException e) {
                throw new RuntimeException(e);
            }
        });
    }
}
