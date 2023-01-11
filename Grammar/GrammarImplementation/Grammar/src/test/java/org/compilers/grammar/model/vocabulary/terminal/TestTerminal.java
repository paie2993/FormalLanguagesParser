package org.compilers.grammar.model.vocabulary.terminal;

import org.compilers.grammar.model.vocabulary.nonterminal.NonTerminalImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class TestTerminal {

    // test for symbol that is instance of Terminal
    @Test
    void isInstance() {
        final var terminal = new TerminalImpl("a");
        Assertions.assertTrue(Terminal.isInstance(terminal));
    }

    // test for symbol that is not instance of Terminal
    @Test
    void isInstanceNot() {
        final var nonTerminal = new NonTerminalImpl("A");
        Assertions.assertFalse(Terminal.isInstance(nonTerminal));
    }
}