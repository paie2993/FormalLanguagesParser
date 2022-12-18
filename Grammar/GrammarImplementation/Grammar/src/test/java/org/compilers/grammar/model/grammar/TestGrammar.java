package org.compilers.grammar.model.grammar;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Set;

public class TestGrammar {

    @Test
    public void concatenate1OnTwoSetsWithoutEpsilon() {

        final var firstSet = Set.of("11", "22", "33");
        final var secondSet = Set.of("aa", "bb", "cc");

        final var expectedResult = Set.of("11", "22", "33");
        final var actualResult = Grammar.concatenate1(firstSet, secondSet);

        Assertions.assertEquals(expectedResult, actualResult);
    }

    @Test
    public void concatenate1OnTwoListedSetsWithoutEpsilon() {

        final var firstSet = Set.of("11", "22", "33");
        final var secondSet = Set.of("aa", "bb", "cc");

        final var expectedResult = Set.of("11", "22", "33");
        final var actualResult = Grammar.concatenate1(List.of(firstSet, secondSet));

        Assertions.assertEquals(expectedResult, actualResult);
    }

    @Test
    public void concatenate1OnTwoSetsWithEpsilonInFirst() {

        final var firstSet = Set.of("11", "22", "33", Grammar.EPSILON);
        final var secondSet = Set.of("aa", "bb", "cc");

        final var expectedResult = Set.of("11", "22", "33", "aa", "bb", "cc");
        final var actualResult = Grammar.concatenate1(firstSet, secondSet);

        Assertions.assertEquals(expectedResult, actualResult);
    }

    @Test
    public void concatenate1OnTwoSetsWithEpsilonInFirstAndSecond() {

        final var firstSet = Set.of("11", "22", "33", Grammar.EPSILON);
        final var secondSet = Set.of("aa", "bb", "cc", Grammar.EPSILON);

        final var expectedResult = Set.of("11", "22", "33", "aa", "bb", "cc", Grammar.EPSILON);
        final var actualResult = Grammar.concatenate1(firstSet, secondSet);

        Assertions.assertEquals(expectedResult, actualResult);
    }

    // first two only contain epsilon, third also contains epsilon but contains other symbols as well
    @Test
    public void concatenate1OnThreeSets() {
        final var firstSet = Set.of(Grammar.EPSILON);
        final var secondSet = Set.of(Grammar.EPSILON);
        final var thirdSet = Set.of("1", "2", "3", Grammar.EPSILON);

        final var expectedResult = Set.of("1", "2", "3", Grammar.EPSILON);
        final var actualResult = Grammar.concatenate1(List.of(firstSet, secondSet, thirdSet));

        Assertions.assertEquals(expectedResult, actualResult);
    }
}