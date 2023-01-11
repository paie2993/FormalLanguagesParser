package org.compilers.grammar.parser.reader;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;

public interface PIFReader {
    String COLUMN_SEPARATOR = "#";

    static List<? extends String> readFromFile(final String fileName) throws IOException {
        return read(fileName, COLUMN_SEPARATOR);
    }

    static List<? extends String> readFromFile(final String fileName, final String columnSeparator) throws IOException {
        return read(fileName, columnSeparator);
    }

    private static List<? extends String> read(final String fileName, final String columnSeparator) throws IOException {
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(fileName))) {
            return bufferedReader.lines()
                    .map(line -> line.split(columnSeparator))
                    .filter(entry -> entry.length >= 1)
                    .map(entry -> entry[0].trim())
                    .toList();
        }
    }
}
