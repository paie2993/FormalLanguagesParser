package org.compilers.grammar.model.vocabulary.nonterminal;

import org.compilers.grammar.model.vocabulary.terminal.Terminal;
import org.compilers.grammar.model.vocabulary.terminal.TerminalImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class TestNonTerminal {

    // test for symbol that is instance of non-terminal
    @Test
    void isInstance() {
        final var nonTerminal = new NonTerminalImpl("A");

        Assertions.assertTrue(NonTerminal.isInstance(nonTerminal));
    }

    // test for symbol that is not instance of non-terminal
    @Test
    void isInstanceNot() {
        final var terminal = new TerminalImpl("a");

        Assertions.assertTrue(Terminal.isInstance(terminal));
    }
}