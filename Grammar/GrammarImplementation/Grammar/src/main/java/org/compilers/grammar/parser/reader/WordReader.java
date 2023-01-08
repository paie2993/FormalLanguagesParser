package org.compilers.grammar.parser.reader;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;

public final class WordReader {
    public static List<? extends String> readFromFile(final String fileName) throws IOException {
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(fileName))) {
            final String word = bufferedReader.readLine();
            return word.chars()
                    .mapToObj(letter -> (char) letter)
                    .map(String::valueOf)
                    .toList();
        }
    }
}
