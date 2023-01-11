package org.compilers.grammar.model.grammar.context_free.concatenate;

import org.compilers.grammar.model.grammar.context_free.concatenate.Concatenate1;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Set;

public class TestConcatenate1 {

    // concatenate1 test when the left symbol (as string) is empty
    // expected to get only the right symbol (as string)
    @Test
    void concatenate1TwoStringLeftEmpty() {
        final var left = "";
        final var right = "b";

        final var actual = Concatenate1.concatenate1(left, right);
        final var expected = "b";

        Assertions.assertEquals(expected, actual);
    }

    // concatenate1 test when the left symbol (as string) is non-empty
    // expected to get only the left symbol (as string)
    @Test
    void concatenate1TwoStringLeftNonEmpty() {
        final var left = "a";
        final var right = "b";

        final var actual = Concatenate1.concatenate1(left, right);
        final var expected = "a";

        Assertions.assertEquals(expected, actual);
    }

    // concatenate1(Set<String>, Set<String>) test
    @Test
    void concatenate1TwoSetsOfStrings() {
        final var leftSet = Set.of("a", "b", "", "e", "f");
        final var rightSet = Set.of("g", "h", "i");

        final var actual = Concatenate1.concatenate1(leftSet, rightSet);
        final var expected = Set.of("a", "b", "e", "f", "g", "h", "i");

        Assertions.assertEquals(expected, actual);
    }

    // concatenate1 test on a list of sets of symbols (as strings), when the list is empty
    // expected to get a set with empty string
    @Test
    void concatenate1ListIsEmpty() {
        final List<? extends Set<String>> list = List.of();

        final var actual = Concatenate1.concatenate1(list);
        final var expected = Set.of("");

        Assertions.assertEquals(expected, actual);
    }

    // concatenate1 test on a list of sets of symbols (as strings), when the list is has only one set
    // expected to get the only set present in the list
    @Test
    void concatenate1ListWithSingleSet() {
        final var list = List.of(Set.of("a", "b", "c"));

        final var actual = Concatenate1.concatenate1(list);
        final var expected = Set.of("a", "b", "c");

        Assertions.assertEquals(expected, actual);
    }

    // concatenate1 test on a list of sets of symbols (as strings), when the list is has two sets
    // expected to get the set of concatenation1 between the elements of the two sets
    @Test
    void concatenate1ListWithTwoSets() {
        final var set1 = Set.of("a", "", "b");
        final var set2 = Set.of("c", "d");
        final var list = List.of(set1, set2);

        final var actual = Concatenate1.concatenate1(list);
        final var expected = Set.of("a", "b", "c", "d");

        Assertions.assertEquals(expected, actual);
    }

    // concatenate1 test on a list of sets of symbols (as strings), when the list is has three sets
    // and the first two sets both have an empty symbol (meaning epsilon)
    // expected to get a set with all the symbols in the list, except the empty symbol
    @Test
    void concatenate1ListWithThreeSetsWithoutEpsilonInThird() {
        final var set1 = Set.of("a", "", "b");
        final var set2 = Set.of("c", "", "d");
        final var set3 = Set.of("e", "f");
        final var list = List.of(set1, set2, set3);

        final var actual = Concatenate1.concatenate1(list);
        final var expected = Set.of("a", "b", "c", "d", "e", "f");

        Assertions.assertEquals(expected, actual);
    }

    // concatenate1 test on a list of sets of symbols (as strings), when the list is has three sets
    // and all three sets have an empty symbol (meaning epsilon)
    // expected to get a set with all the symbols in the list, including the empty symbol
    @Test
    void concatenate1ListWithThreeSetsAndEpsilonInThird() {
        final var set1 = Set.of("a", "", "b");
        final var set2 = Set.of("c", "", "d");
        final var set3 = Set.of("e", "", "f");
        final var list = List.of(set1, set2, set3);

        final var actual = Concatenate1.concatenate1(list);
        final var expected = Set.of("a", "b", "c", "d", "e", "f", "");

        Assertions.assertEquals(expected, actual);
    }
}
