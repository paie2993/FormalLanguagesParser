package org.compilers.grammar.parser.parse_table;

import org.compilers.grammar.model.grammar.context_free.ContextFreeGrammar;
import org.compilers.grammar.parser.parse_table.action.Action;
import org.compilers.grammar.parser.parse_table.action.NextMove;
import org.compilers.grammar.model.production.context_free.AbstractContextFreeProduction;
import org.compilers.grammar.model.production.context_free.ContextFreeProduction;
import org.compilers.grammar.model.vocabulary.nonterminal.NonTerminalImpl;
import org.compilers.grammar.model.vocabulary.terminal.TerminalImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.*;

class TestParseTableImpl {

    @Test
    public void initializeTerminalTable() {
        final var a = new TerminalImpl("a");
        final var b = new TerminalImpl("b");
        final var terminals = Set.of(a, b);

        final var A = new NonTerminalImpl("A");
        final var B = new NonTerminalImpl("B");
        final var nonTerminals = Set.of(A, B);

        final List<AbstractContextFreeProduction> productions = List.of();

        final var grammar = new ContextFreeGrammar(nonTerminals, terminals, productions, A);

        final var actual = ParseTableImpl.initializeTerminalTable(grammar);
        final var expected = Map.of(
                Indices.of(a, a), Action.POP,
                Indices.of(b, b), Action.POP,
                Indices.of(ParseTable.DOLLAR_TERMINAL, ParseTable.DOLLAR_TERMINAL), Action.ACCEPT
        );

        Assertions.assertEquals(expected, actual);
    }

    @Test
    public void initializeNonTerminalTable() {
        final var a = new TerminalImpl("a");
        final var openParenthesis = new TerminalImpl("(");
        final var closedParenthesis = new TerminalImpl(")");
        final var plus = new TerminalImpl("+");
        final var star = new TerminalImpl("*");
        final var terminals = Set.of(a, openParenthesis, closedParenthesis, plus, star);

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
        final List<AbstractContextFreeProduction> productions = List.of(prod1, prod2, prod3, prod4, prod5, prod6, prod7, prod8);

        final var grammar = new ContextFreeGrammar(nonTerminals, terminals, productions, S);

        final var actual = ParseTableImpl.initializeNonTerminalTable(grammar);
        final Map<Indices, NextMove> expected = new HashMap<>();
        expected.put(Indices.of(S, a), NextMove.of(List.of(B, A), 0));
        expected.put(Indices.of(S, openParenthesis), NextMove.of(List.of(B, A), 0));
        expected.put(Indices.of(A, plus), NextMove.of(List.of(plus, B, A), 1));
        expected.put(Indices.of(A, closedParenthesis), NextMove.of(List.of(), 2));
        expected.put(Indices.of(A, ParseTable.DOLLAR_TERMINAL), NextMove.of(List.of(), 2));
        expected.put(Indices.of(B, a), NextMove.of(List.of(D, C), 3));
        expected.put(Indices.of(B, openParenthesis), NextMove.of(List.of(D, C), 3));
        expected.put(Indices.of(C, plus), NextMove.of(List.of(), 5));
        expected.put(Indices.of(C, star), NextMove.of(List.of(star, D, C), 4));
        expected.put(Indices.of(C, closedParenthesis), NextMove.of(List.of(), 5));
        expected.put(Indices.of(C, ParseTable.DOLLAR_TERMINAL), NextMove.of(List.of(), 5));
        expected.put(Indices.of(D, a), NextMove.of(List.of(a), 7));
        expected.put(Indices.of(D, openParenthesis), NextMove.of(List.of(openParenthesis, S, closedParenthesis), 6));

        Assertions.assertEquals(expected, actual);
    }
}