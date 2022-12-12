package org.compilers.grammar.parsing;

import org.compilers.grammar.model.Grammar;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public final class Concatenation {

    // Concatenation of length 1
    public static Set<String> concatenate(final Set<String>... symbols) {
        if (symbols.length == 0) {
            throw new IllegalArgumentException("Can't do it with empty array");
        }

        if (symbols.length == 1) {
            return symbols[0];
        }

        final var baseSet = symbols[0];
        final var otherSet = concatenate(slice(1, symbols));

        return concatenate(baseSet, otherSet);
    }

    private static Set<String>[] slice(final int index, final Set<String>[] array) {
        return (Set<String>[]) Arrays.stream(array).skip(index).toArray();
    }

    // Concatenation of length 1
    private static Set<String> concatenate(final Set<String> first, final Set<String> second) {
        final var firstContainsEpsilon = first.contains(Grammar.EPSILON);

        if (firstContainsEpsilon) {
            final var firstWithoutEpsilon = first.stream().filter(obj -> !obj.equals(Grammar.EPSILON));
            return Stream.concat(firstWithoutEpsilon, second.stream()).collect(Collectors.toSet());
        }

        return first;
    }
}
