package org.compilers.grammar.model.grammar.context_free.concatenate;

import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

public final class Concatenate1 {

    // a recursive method which keeps concatenating the sets of symbols from the tail of the input list until the start <br>
    // in other words, it first concatenates the last two sets, then concatenates the 3rd set from the tail of the list with <br>
    // the concatenation of the last two sets, then ... and so on
    // Note: made public for testing
    public static Set<String> concatenate1(
            final List<? extends Set<String>> symbols
    ) {
        Objects.requireNonNull(symbols);

        if (symbols.isEmpty()) {
            return Set.of("");
        }

        if (symbols.size() == 1) {
            return symbols.get(0);
        }

        final var firstSet = symbols.get(0);
        final var otherSets = symbols.subList(1, symbols.size());

        final var otherSetsConcatenation = concatenate1(otherSets);
        return concatenate1(firstSet, otherSetsConcatenation);
    } // tested, good

    // applies concatenate(1) on two sets of symbols (as strings) (represents the terminal operation of the concatenate1
    // recursion)
    // Note: made public for testing
    public static Set<String> concatenate1(
            final Set<String> leftSet,
            final Set<String> rightSet
    ) {
        Objects.requireNonNull(leftSet);
        Objects.requireNonNull(rightSet);
        return leftSet.stream()
                .flatMap(leftSymbol -> rightSet.stream().map(rightSymbol -> concatenate1(leftSymbol, rightSymbol)))
                .collect(Collectors.toUnmodifiableSet());
    } // tested, good

    // applies concatenate(1) on two symbols (as strings)
    // Note: made public for testing
    // TODO: Discuss if this case should be exceptional: left == epsilon and right == epsilon
    public static String concatenate1(
            final String left,
            final String right
    ) {
        Objects.requireNonNull(left);
        Objects.requireNonNull(right);
        return left.isEmpty() ? right : left;
    } // tested, good

}
