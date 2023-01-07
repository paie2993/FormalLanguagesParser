package org.compilers.grammar.model.grammar;

import org.compilers.grammar.model.production.Production;
import org.compilers.grammar.model.vocabulary.NonTerminal;
import org.compilers.grammar.model.vocabulary.Symbol;
import org.compilers.grammar.model.vocabulary.Terminal;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.List;
import java.util.Set;

public class TestGrammarComplex {

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

    // test for 'first1' method
    @Test
    public void first() {

        final var a = new Terminal("a");
        final var openParenthesis = new Terminal("(");
        final var closedParenthesis = new Terminal(")");
        final var plus = new Terminal("+");
        final var star = new Terminal("*");
        final var epsilon = new Terminal(Grammar.EPSILON);

        final var S = new NonTerminal("S");
        final var A = new NonTerminal("A");
        final var B = new NonTerminal("B");
        final var C = new NonTerminal("C");
        final var D = new NonTerminal("D");

        final var terminals = Set.of(a, plus, star, openParenthesis, closedParenthesis);
        final var nonTerminals = Set.of(S, A, B, C, D);
        final var productions = Set.of(
                new Production(List.of(S), List.of(B, A)),
                new Production(List.of(A), List.of(plus, B, A)),
                new Production(List.of(A), List.of(epsilon)),
                new Production(List.of(B), List.of(D, C)),
                new Production(List.of(B), List.of(D, C)),
                new Production(List.of(C), List.of(star, D, C)),
                new Production(List.of(C), List.of(epsilon)),
                new Production(List.of(D), List.of(openParenthesis, S, closedParenthesis)),
                new Production(List.of(D), List.of(a))
        );

        final var expectedResult = new HashMap<Symbol, Set<String>>();
        expectedResult.put(S, Set.of("(", "a"));
        expectedResult.put(A, Set.of("+", "epsilon"));
        expectedResult.put(B, Set.of("(", "a"));
        expectedResult.put(C, Set.of("*", "epsilon"));
        expectedResult.put(D, Set.of("(", "a"));
        expectedResult.put(a, Set.of("a"));
        expectedResult.put(openParenthesis, Set.of("("));
        expectedResult.put(closedParenthesis, Set.of(")"));
        expectedResult.put(plus, Set.of("+"));
        expectedResult.put(star, Set.of("*"));

        final var actualResult = Grammar.first(terminals, nonTerminals, productions);

        Assertions.assertEquals(expectedResult, actualResult);
    }
}


















