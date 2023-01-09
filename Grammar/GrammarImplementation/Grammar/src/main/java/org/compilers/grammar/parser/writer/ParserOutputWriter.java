package org.compilers.grammar.parser.writer;

import org.compilers.grammar.parser.output.father_sibling_table.entry.TableEntry;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public interface ParserOutputWriter {
    String COLUMN_SEPARATOR = " ";

    static void writeToFile(final String fileName, final List<? extends TableEntry> output) throws IOException {
        write(fileName, COLUMN_SEPARATOR, output);
    }

    static void writeToFile(final String fileName, final String columnSeparator, final List<? extends TableEntry> output) throws IOException {
        write(fileName, columnSeparator, output);
    }

    private static void write(final String fileName, final String columnSeparator, final List<? extends TableEntry> output) throws IOException {
        try (BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(fileName))) {
            output.stream()
                    .map(entry -> String.format("%s%s%d%s%d", entry.symbol(), columnSeparator, entry.parentIndex(), columnSeparator, entry.rightSiblingIndex()))
                    .forEach(entry -> {
                        try {
                            bufferedWriter.append(entry);
                            bufferedWriter.newLine();
                        } catch (IOException ioException) {
                            throw new RuntimeException(ioException);
                        }
                    });
        }
    }
}
