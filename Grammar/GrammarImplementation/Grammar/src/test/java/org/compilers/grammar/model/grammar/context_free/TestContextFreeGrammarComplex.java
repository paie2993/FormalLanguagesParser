package org.compilers.grammar.model.grammar.context_free;

import org.compilers.grammar.model.grammar.production.context_free.ContextFreeProductionImpl;
import org.compilers.grammar.model.vocabulary.Symbol;
import org.compilers.grammar.model.vocabulary.nonterminal.NonTerminal;
import org.compilers.grammar.model.vocabulary.nonterminal.NonTerminalImpl;
import org.compilers.grammar.model.vocabulary.terminal.TerminalImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.List;
import java.util.Set;


class TestContextFreeGrammarComplex {
    @Test
    public void concatenate1OnTwoSetsWithoutEpsilon() {

        final var firstSet = Set.of("11", "22", "33");
        final var secondSet = Set.of("aa", "bb", "cc");

        final var expectedResult = Set.of("11", "22", "33");
        final var actualResult = ContextFreeGrammar.concatenate1(firstSet, secondSet);

        Assertions.assertEquals(expectedResult, actualResult);
    }

    @Test
    public void concatenate1OnTwoListedSetsWithoutEpsilon() {

        final var firstSet = Set.of("11", "22", "33");
        final var secondSet = Set.of("aa", "bb", "cc");

        final var expectedResult = Set.of("11", "22", "33");
        final var actualResult = ContextFreeGrammar.concatenate1(List.of(firstSet, secondSet));

        Assertions.assertEquals(expectedResult, actualResult);
    }

    @Test
    public void concatenate1OnTwoSetsWithEpsilonInFirst() {

        final var firstSet = Set.of("11", "22", "33", "");
        final var secondSet = Set.of("aa", "bb", "cc");

        final var expectedResult = Set.of("11", "22", "33", "aa", "bb", "cc");
        final var actualResult = ContextFreeGrammar.concatenate1(firstSet, secondSet);

        Assertions.assertEquals(expectedResult, actualResult);
    }

    @Test
    public void concatenate1OnTwoSetsWithEpsilonInFirstAndSecond() {

        final var firstSet = Set.of("11", "22", "33", "");
        final var secondSet = Set.of("aa", "bb", "cc", "");

        final var expectedResult = Set.of("11", "22", "33", "aa", "bb", "cc", "");
        final var actualResult = ContextFreeGrammar.concatenate1(firstSet, secondSet);

        Assertions.assertEquals(expectedResult, actualResult);
    }

    // first two only contain epsilon, third also contains epsilon but contains other symbols as well
    @Test
    public void concatenate1OnThreeSets() {
        final var firstSet = Set.of("");
        final var secondSet = Set.of("");
        final var thirdSet = Set.of("1", "2", "3", "");

        final var expectedResult = Set.of("1", "2", "3", "");
        final var actualResult = ContextFreeGrammar.concatenate1(List.of(firstSet, secondSet, thirdSet));

        Assertions.assertEquals(expectedResult, actualResult);
    }


    @Test
    public void first() {

        final var a = new TerminalImpl("a");
        final var openParenthesis = new TerminalImpl("(");
        final var closedParenthesis = new TerminalImpl(")");
        final var plus = new TerminalImpl("+");
        final var star = new TerminalImpl("*");

        final var S = new NonTerminalImpl("S");
        final var A = new NonTerminalImpl("A");
        final var B = new NonTerminalImpl("B");
        final var C = new NonTerminalImpl("C");
        final var D = new NonTerminalImpl("D");

        final var terminals = Set.of(a, plus, star, openParenthesis, closedParenthesis);
        final var nonTerminals = Set.of(S, A, B, C, D);
        final var productions = Set.of(
                new ContextFreeProductionImpl(S, List.of(B, A)),
                new ContextFreeProductionImpl(A, List.of(plus, B, A)),
                new ContextFreeProductionImpl(A, List.of()),
                new ContextFreeProductionImpl(B, List.of(D, C)),
                new ContextFreeProductionImpl(C, List.of(star, D, C)),
                new ContextFreeProductionImpl(C, List.of()),
                new ContextFreeProductionImpl(D, List.of(openParenthesis, S, closedParenthesis)),
                new ContextFreeProductionImpl(D, List.of(a))
        );

        final var expectedResult = new HashMap<Symbol, Set<String>>();
        expectedResult.put(S, Set.of("(", "a"));
        expectedResult.put(A, Set.of("+", ""));
        expectedResult.put(B, Set.of("(", "a"));
        expectedResult.put(C, Set.of("*", ""));
        expectedResult.put(D, Set.of("(", "a"));
        expectedResult.put(a, Set.of("a"));
        expectedResult.put(openParenthesis, Set.of("("));
        expectedResult.put(closedParenthesis, Set.of(")"));
        expectedResult.put(plus, Set.of("+"));
        expectedResult.put(star, Set.of("*"));

        final var actualResult = ContextFreeGrammar.first(terminals, nonTerminals, productions);

        Assertions.assertEquals(expectedResult, actualResult);
    }

    @Test
    public void follow() {
        final var a = new TerminalImpl("a");
        final var openParenthesis = new TerminalImpl("(");
        final var closedParenthesis = new TerminalImpl(")");
        final var plus = new TerminalImpl("+");
        final var star = new TerminalImpl("*");

        final var S = new NonTerminalImpl("S");
        final var A = new NonTerminalImpl("A");
        final var B = new NonTerminalImpl("B");
        final var C = new NonTerminalImpl("C");
        final var D = new NonTerminalImpl("D");

        final var terminals = Set.of(a, plus, star, openParenthesis, closedParenthesis);
        final var nonTerminals = Set.of(S, A, B, C, D);
        final var productions = Set.of(
                new ContextFreeProductionImpl(S, List.of(B, A)),
                new ContextFreeProductionImpl(A, List.of(plus, B, A)),
                new ContextFreeProductionImpl(A, List.of()),
                new ContextFreeProductionImpl(B, List.of(D, C)),
                new ContextFreeProductionImpl(C, List.of(star, D, C)),
                new ContextFreeProductionImpl(C, List.of()),
                new ContextFreeProductionImpl(D, List.of(openParenthesis, S, closedParenthesis)),
                new ContextFreeProductionImpl(D, List.of(a))
        );

        final var expectedResult = new HashMap<NonTerminal, Set<String>>();
        expectedResult.put(S, Set.of("", ")"));
        expectedResult.put(A, Set.of("", ")"));
        expectedResult.put(B, Set.of("", "+", ")"));
        expectedResult.put(C, Set.of("", "+", ")"));
        expectedResult.put(D, Set.of("", "*", "+", ")"));

        final var actualResult = ContextFreeGrammar.follow(ContextFreeGrammar.first(terminals, nonTerminals, productions), nonTerminals, S, productions);

        Assertions.assertEquals(expectedResult, actualResult);
    }
}