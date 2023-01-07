package org.compilers.grammar.model.grammar.context_free.follow;

import org.compilers.grammar.model.production.context_free.ContextFreeProduction;
import org.compilers.grammar.model.vocabulary.Symbol;
import org.compilers.grammar.model.vocabulary.nonterminal.NonTerminal;
import org.compilers.grammar.model.vocabulary.nonterminal.NonTerminalImpl;
import org.compilers.grammar.model.vocabulary.terminal.TerminalImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;
import java.util.Set;

class TestFollow1 {

    @Test
    public void initializeNonTerminalsResultSets() {
        final var S = new NonTerminalImpl("S");
        final var A = new NonTerminalImpl("A");
        final var B = new NonTerminalImpl("B");
        final var nonTerminals = Set.of(S, A, B);

        final var actual = Follow1.initializeNonTerminalsResultSets(nonTerminals, S);
        final var expected = Map.of(
                S, Set.of(""),
                A, Set.of(),
                B, Set.of()
        );

        Assertions.assertEquals(expected, actual);
    }

    // test for method returning only productions with given symbol in the right side
    @Test
    public void productionsWithSymbolInRightSide() {
        final var S = new NonTerminalImpl("S");
        final var A = new NonTerminalImpl("A");
        final var B = new NonTerminalImpl("B");

        final var a = new TerminalImpl("a");
        final var b = new TerminalImpl("b");
        final var c = new TerminalImpl("c");

        final var prod1 = new ContextFreeProduction(S, List.of(A, B));
        final var prod2 = new ContextFreeProduction(A, List.of(A, a, c));
        final var prod3 = new ContextFreeProduction(A, List.of(B, c));
        final var prod4 = new ContextFreeProduction(B, List.of(b, A));
        final var productions = Set.of(prod1, prod2, prod3, prod4);

        final var actual = Follow1.productionsWithSymbolInRightSide(productions, A);
        final var expected = Set.of(prod1, prod2, prod4);

        Assertions.assertEquals(expected, actual);
    }

    @Test
    public void iteration() {
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
        final var nonTerminals = Set.of(S, A, B, C, D);

        final var prod1 = new ContextFreeProduction(S, List.of(B, A));
        final var prod2 = new ContextFreeProduction(A, List.of(plus, B, A));
        final var prod3 = new ContextFreeProduction(A, List.of());
        final var prod4 = new ContextFreeProduction(B, List.of(D, C));
        final var prod5 = new ContextFreeProduction(C, List.of(star, D, C));
        final var prod6 = new ContextFreeProduction(C, List.of());
        final var prod7 = new ContextFreeProduction(D, List.of(openParenthesis, S, closedParenthesis));
        final var prod8 = new ContextFreeProduction(D, List.of(a));
        final var productions = Set.of(prod1, prod2, prod3, prod4, prod5, prod6, prod7, prod8);

        final Map<? extends Symbol, ? extends Set<String>> first = Map.of(
                S, Set.of("(", "a"),
                A, Set.of("+", ""),
                B, Set.of("(", "a"),
                C, Set.of("*", ""),
                D, Set.of("(", "a"),
                a, Set.of("a"),
                openParenthesis, Set.of("("),
                closedParenthesis, Set.of(")"),
                plus, Set.of("+"),
                star, Set.of("*")
        );

        final Map<? extends NonTerminal, ? extends Set<String>> resultSets = Map.of(
                S, Set.of("", ")"),
                A, Set.of("", ")"),
                B, Set.of("+", "", ")"),
                C, Set.of("+", ""),
                D, Set.of("*", "+", "")
        );


        final Map<? extends NonTerminal, ? extends Set<String>> actual = Follow1.iteration(
                first,
                nonTerminals,
                productions,
                resultSets
        );
        final Map<? extends NonTerminal, ? extends Set<String>> expected = Map.of(
                S, Set.of("", ")"),
                A, Set.of("", ")"),
                B, Set.of("+", "", ")"),
                C, Set.of("+", "", ")"),
                D, Set.of("*", "+", "", ")")
        );

        Assertions.assertEquals(expected, actual);
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
        final var nonTerminals = Set.of(S, A, B, C, D);

        final var prod1 = new ContextFreeProduction(S, List.of(B, A));
        final var prod2 = new ContextFreeProduction(A, List.of(plus, B, A));
        final var prod3 = new ContextFreeProduction(A, List.of());
        final var prod4 = new ContextFreeProduction(B, List.of(D, C));
        final var prod5 = new ContextFreeProduction(C, List.of(star, D, C));
        final var prod6 = new ContextFreeProduction(C, List.of());
        final var prod7 = new ContextFreeProduction(D, List.of(openParenthesis, S, closedParenthesis));
        final var prod8 = new ContextFreeProduction(D, List.of(a));
        final var productions = Set.of(prod1, prod2, prod3, prod4, prod5, prod6, prod7, prod8);

        final Map<? extends Symbol, ? extends Set<String>> first = Map.of(
                S, Set.of("(", "a"),
                A, Set.of("+", ""),
                B, Set.of("(", "a"),
                C, Set.of("*", ""),
                D, Set.of("(", "a"),
                a, Set.of("a"),
                openParenthesis, Set.of("("),
                closedParenthesis, Set.of(")"),
                plus, Set.of("+"),
                star, Set.of("*")
        );

        final var actual = Follow1.follow(first, nonTerminals, S, productions);
        final var expected = Map.of(
                S, Set.of("", ")"),
                A, Set.of("", ")"),
                B, Set.of("+", "", ")"),
                C, Set.of("+", "", ")"),
                D, Set.of("*", "+", "", ")")
        );

        Assertions.assertEquals(expected, actual);
    }
}