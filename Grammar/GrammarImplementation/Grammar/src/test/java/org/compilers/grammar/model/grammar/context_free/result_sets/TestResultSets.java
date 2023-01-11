package org.compilers.grammar.model.grammar.context_free.result_sets;

import org.compilers.grammar.model.vocabulary.Symbol;
import org.compilers.grammar.model.vocabulary.nonterminal.NonTerminalImpl;
import org.compilers.grammar.model.vocabulary.terminal.TerminalImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Map;
import java.util.Set;

class TestResultSets {

    // test copy result set
    @Test
    public void copyResultSet() {
        final var S = new NonTerminalImpl("S");
        final var A = new NonTerminalImpl("A");
        final var B = new NonTerminalImpl("B");
        final var a = new TerminalImpl("a");
        final var b = new TerminalImpl("b");
        final var c = new TerminalImpl("c");

        final Map<? extends Symbol, ? extends Set<String>> resultSets = Map.of(
                S, Set.of(),
                A, Set.of("a"),
                B, Set.of("b", "c"),
                a, Set.of("a"),
                b, Set.of("b"),
                c, Set.of("c")
        );

        final var actual = ResultSets.copyResultSets(resultSets);

        Assertions.assertEquals(resultSets, actual);
        Assertions.assertNotSame(resultSets, actual);
    }

}