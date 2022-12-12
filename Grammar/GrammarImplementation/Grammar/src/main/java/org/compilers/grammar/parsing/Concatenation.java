package org.compilers.grammar.parsing;

import org.compilers.grammar.model.Grammar;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public final class Concatenation {

    // Concatenation of length 1
    public static Set<String> concatenate(final List<Set<String>> symbols) {
        if (symbols.isEmpty()) {
            throw new IllegalArgumentException("Can't do it with empty list");
        }

        if (symbols.size() == 1) {
            return symbols.get(0);
        }

        final var baseSet = symbols.get(0);
        final var otherSet = concatenate(symbols.subList(1, symbols.size()));

        return concatenate(baseSet, otherSet);
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
