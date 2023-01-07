package org.compilers.grammar.model.grammar;

import org.compilers.grammar.model.production.Production;
import org.compilers.grammar.model.vocabulary.NonTerminal;
import org.compilers.grammar.model.vocabulary.Terminal;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Set;

public class TestGrammarSimple {

    private Grammar getGrammarOne() {
        // non-terminals
        final var S = new NonTerminal("S");
        final var A = new NonTerminal("A");
        final var B = new NonTerminal("B");
        final var C = new NonTerminal("C");
        final var D = new NonTerminal("D");
        final var nonTerminals = Set.of(S, A, B, C, D);

        // terminals
        final var a = new Terminal("a");
        final var b = new Terminal("b");
        final var c = new Terminal("c");
        final var terminals = Set.of(a, b, c);

        // productions
        final var prodOne = new Production(S, A);
        final var prodTwo = new Production(A, B);
        final var prodThree = new Production(A, C);
        final var prodFour = new Production(C, a);
        final var prodFive = new Production(C, D);
        final var prodSix = new Production(D, b);
        final var prodSeven = new Production(C, c);
        final var productions = Set.of(prodOne, prodTwo, prodThree, prodFour, prodFive, prodSix, prodSeven);

        // grammar
        return new Grammar(nonTerminals, terminals, productions, S);
    }

    // test for 'productionsOf(NonTerminal)', when there are productions of the given non-terminal in the grammar
    @Test
    public void productionsOfNonTerminal() {
        final var grammar = getGrammarOne();

        final var C = new NonTerminal("C");
        final var a = new Terminal("a");
        final var D = new NonTerminal("D");
        final var c = new Terminal("c");

        final var prodFour = new Production(C, a);
        final var prodFive = new Production(C, D);
        final var prodSeven = new Production(C, c);

        final var expectedProductions = Set.of(prodFour, prodFive, prodSeven);
        final var productionsOfC = grammar.productionsOf(C);

        Assertions.assertEquals(expectedProductions, productionsOfC);
    }

    // test for 'productionsOf(NonTerminal)', when there aren't productions of the given non-terminal in the grammar
    @Test
    public void productionsOfNonTerminalEmpty() {
        final var grammar = getGrammarOne();

        final var Z = new NonTerminal("Z");

        final var expectedProductions = Set.of();
        final var productionsOfZ = grammar.productionsOf(Z);

        Assertions.assertEquals(expectedProductions, productionsOfZ);
    }
}
