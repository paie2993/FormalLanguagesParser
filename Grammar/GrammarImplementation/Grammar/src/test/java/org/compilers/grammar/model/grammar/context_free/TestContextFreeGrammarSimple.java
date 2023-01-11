package org.compilers.grammar.model.grammar.context_free;

import org.compilers.grammar.model.production.context_free.AbstractContextFreeProduction;
import org.compilers.grammar.model.production.context_free.ContextFreeProduction;
import org.compilers.grammar.model.vocabulary.nonterminal.NonTerminalImpl;
import org.compilers.grammar.model.vocabulary.terminal.TerminalImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Set;

public class TestContextFreeGrammarSimple {

    private ContextFreeGrammar getContextFreeGrammarOne() {
        // non-terminals
        final var S = new NonTerminalImpl("S");
        final var A = new NonTerminalImpl("A");
        final var B = new NonTerminalImpl("B");
        final var C = new NonTerminalImpl("C");
        final var D = new NonTerminalImpl("D");
        final var nonTerminals = Set.of(S, A, B, C, D);

        // terminals
        final var a = new TerminalImpl("a");
        final var b = new TerminalImpl("b");
        final var c = new TerminalImpl("c");
        final var terminals = Set.of(a, b, c);

        // productions
        final var prodOne = new ContextFreeProduction(S, List.of(A));
        final var prodTwo = new ContextFreeProduction(A, List.of(B));
        final var prodThree = new ContextFreeProduction(A, List.of(C));
        final var prodFour = new ContextFreeProduction(C, List.of(a));
        final var prodFive = new ContextFreeProduction(C, List.of(D));
        final var prodSix = new ContextFreeProduction(D, List.of(b));
        final var prodSeven = new ContextFreeProduction(C, List.of(c));
        final List<AbstractContextFreeProduction> productions = List.of(prodOne, prodTwo, prodThree, prodFour, prodFive, prodSix, prodSeven);

        // grammar
        return new ContextFreeGrammar(nonTerminals, terminals, productions, S);
    }

    // test for 'productionsOf(NonTerminal)', when there are productions of the given non-terminal in the grammar
    @Test
    public void productionsOfNonTerminal() {
        final var grammar = getContextFreeGrammarOne();

        final var C = new NonTerminalImpl("C");
        final var a = new TerminalImpl("a");
        final var D = new NonTerminalImpl("D");
        final var c = new TerminalImpl("c");

        final var prodFour = new ContextFreeProduction(C, List.of(a));
        final var prodFive = new ContextFreeProduction(C, List.of(D));
        final var prodSeven = new ContextFreeProduction(C, List.of(c));

        final var expectedProductions = Set.of(prodFour, prodFive, prodSeven);
        final var productionsOfC = grammar.productionsOf(C);

        Assertions.assertEquals(expectedProductions, productionsOfC);
    }

    // test for 'productionsOf(NonTerminal)', when there aren't productions of the given non-terminal in the grammar
    @Test
    public void productionsOfNonTerminalEmpty() {
        final var grammar = getContextFreeGrammarOne();

        final var Z = new NonTerminalImpl("Z");

        final var expectedProductions = Set.of();
        final var productionsOfZ = grammar.productionsOf(Z);

        Assertions.assertEquals(expectedProductions, productionsOfZ);
    }
}
