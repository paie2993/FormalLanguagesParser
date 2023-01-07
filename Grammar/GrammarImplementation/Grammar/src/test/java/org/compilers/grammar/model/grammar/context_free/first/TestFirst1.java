package org.compilers.grammar.model.grammar.context_free.first;

import org.compilers.grammar.model.production.context_free.ContextFreeProduction;
import org.compilers.grammar.model.vocabulary.Symbol;
import org.compilers.grammar.model.vocabulary.nonterminal.NonTerminalImpl;
import org.compilers.grammar.model.vocabulary.terminal.TerminalImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;
import java.util.Set;

public class TestFirst1 {

    // build empty result sets based on set of symbols
    @Test
    public void buildEmptyResultSets() {
        final var a = new TerminalImpl("a");
        final var b = new TerminalImpl("b");
        final var c = new TerminalImpl("c");
        final var set = Set.of(a, b, c);

        final var actual = First1.buildEmptyResultSets(set);
        final var expected = Map.of(
                a, Set.of(),
                b, Set.of(),
                c, Set.of()
        );

        Assertions.assertEquals(expected, actual);
    }

    // initialize terminals result sets
    @Test
    public void initializeTerminalsResultSets() {
        final var a = new TerminalImpl("a");
        final var b = new TerminalImpl("b");
        final var c = new TerminalImpl("c");
        final var set = Set.of(a, b, c);

        final var actual = First1.initializeTerminalsResultSets(set);
        final var expected = Map.of(
                a, Set.of("a"),
                b, Set.of("b"),
                c, Set.of("c")
        );

        Assertions.assertEquals(expected, actual);
    }

    // test filter empty productions
    @Test
    public void filterEmptyProductions() {
        final var S = new NonTerminalImpl("S");
        final var A = new NonTerminalImpl("A");
        final var B = new NonTerminalImpl("B");
        final var a = new TerminalImpl("a");

        final var prod1 = new ContextFreeProduction(S, List.of(A));
        final var prod2 = new ContextFreeProduction(A, List.of(B));
        final var prod3 = new ContextFreeProduction(A, List.of());
        final var prod4 = new ContextFreeProduction(B, List.of(a));
        final var prod5 = new ContextFreeProduction(B, List.of());
        final var set = Set.of(prod1, prod2, prod3, prod4, prod5);

        final var actual = First1.filterEmptyProductions(set);
        final var expected = Set.of(prod1, prod2, prod4);

        Assertions.assertEquals(expected, actual);
    }

    // initialize non-terminals result sets
    @Test
    public void initializeNonTerminalsResultSets() {
        final var S = new NonTerminalImpl("S");
        final var A = new NonTerminalImpl("A");
        final var B = new NonTerminalImpl("B");
        final var a = new TerminalImpl("a");
        final var b = new TerminalImpl("b");
        final var nonTerminalsSet = Set.of(S, A, B);

        final var prod1 = new ContextFreeProduction(S, List.of(A));
        final var prod2 = new ContextFreeProduction(A, List.of(b, B));
        final var prod3 = new ContextFreeProduction(A, List.of());
        final var prod4 = new ContextFreeProduction(B, List.of(a));
        final var prod5 = new ContextFreeProduction(B, List.of());
        final var prodSet = Set.of(prod1, prod2, prod3, prod4, prod5);

        final var actual = First1.initializeNonTerminalsResultSets(nonTerminalsSet, prodSet);
        final var expected = Map.of(
                S, Set.of(),
                A, Set.of("b"),
                B, Set.of("a")
        );

        Assertions.assertEquals(expected, actual);
    }

    // initialize result sets
    @Test
    public void initializeResultSets() {
        final var S = new NonTerminalImpl("S");
        final var A = new NonTerminalImpl("A");
        final var B = new NonTerminalImpl("B");
        final var nonTerminals = Set.of(S, A, B);

        final var a = new TerminalImpl("a");
        final var b = new TerminalImpl("b");
        final var c = new TerminalImpl("c");
        final var terminals = Set.of(a, b, c);

        final var prod1 = new ContextFreeProduction(S, List.of(A));
        final var prod2 = new ContextFreeProduction(A, List.of(b, B));
        final var prod3 = new ContextFreeProduction(A, List.of());
        final var prod4 = new ContextFreeProduction(B, List.of(a, c));
        final var productions = Set.of(prod1, prod2, prod3, prod4);

        final var actual = First1.initializeResultSets(terminals, nonTerminals, productions);
        final var expected = Map.of(
                S, Set.of(),
                A, Set.of("b"),
                B, Set.of("a"),
                a, Set.of("a"),
                b, Set.of("b"),
                c, Set.of("c")
        );

        Assertions.assertEquals(expected, actual);
    }

    // test result sets of production symbols
    @Test
    public void resultSetsOfProductionSymbols() {
        final var A = new NonTerminalImpl("A");
        final var B = new NonTerminalImpl("B");
        final var prod = new ContextFreeProduction(A, List.of(B));

        final Map<? extends Symbol, ? extends Set<String>> resultSets = Map.of(
                A, Set.of(),
                B, Set.of("a")
        );

        final var actual = First1.resultSetsOfProductionSymbols(prod, resultSets);
        final var expected = List.of(Set.of("a"));

        Assertions.assertEquals(expected, actual);
    }

    // test one simple iteration of 'first'
    @Test
    public void iteration() {
        final var A = new NonTerminalImpl("A");
        final var B = new NonTerminalImpl("B");
        final var nonTerminals = Set.of(A, B);

        final var prod = new ContextFreeProduction(A, List.of(B));
        final var productions = Set.of(prod);

        final Map<? extends Symbol, ? extends Set<String>> resultSets = Map.of(
                A, Set.of(),
                B, Set.of("a")
        );

        final var actual = First1.iteration(nonTerminals, productions, resultSets);
        final var expected = Map.of(
                A, Set.of("a"),
                B, Set.of("a")
        );

        Assertions.assertEquals(expected, actual);
    }

    // test one complete execution of 'first'
    @Test
    public void first() {
        final var a = new TerminalImpl("a");
        final var openParenthesis = new TerminalImpl("(");
        final var closedParenthesis = new TerminalImpl(")");
        final var plus = new TerminalImpl("+");
        final var star = new TerminalImpl("*");
        final var terminals = Set.of(a, plus, star, openParenthesis, closedParenthesis);

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

        final var actual = First1.first(terminals, nonTerminals, productions);
        final var expected = Map.of(
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

        Assertions.assertEquals(expected, actual);
    }
}
