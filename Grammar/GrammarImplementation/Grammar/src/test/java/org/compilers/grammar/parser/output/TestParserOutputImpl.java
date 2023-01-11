package org.compilers.grammar.parser.output;

import org.compilers.grammar.model.grammar.context_free.ContextFreeGrammar;
import org.compilers.grammar.model.production.context_free.AbstractContextFreeProduction;
import org.compilers.grammar.model.production.context_free.ContextFreeProduction;
import org.compilers.grammar.model.vocabulary.nonterminal.NonTerminalImpl;
import org.compilers.grammar.model.vocabulary.terminal.TerminalImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Set;

class TestParserOutputImpl {

    @Test
    void asDerivationString() {
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
        final var productionsSequence = List.of(0, 3, 7, 4, 6, 0, 3, 7, 5, 1, 3, 7, 5, 2, 5, 2);
        // initializations end here

        final var output = ParserOutputImpl.of(grammar, productionsSequence);
        final var actual = output.asDerivationString();
        final var expected = List.of(
                List.of(S),
                List.of(B, A),
                List.of(D, C, A),
                List.of(a, C, A),
                List.of(a, star, D, C, A),
                List.of(a, star, openParenthesis, S, closedParenthesis, C, A),
                List.of(a, star, openParenthesis, B, A, closedParenthesis, C, A),
                List.of(a, star, openParenthesis, D, C, A, closedParenthesis, C, A),
                List.of(a, star, openParenthesis, a, C, A, closedParenthesis, C, A),
                List.of(a, star, openParenthesis, a, A, closedParenthesis, C, A),
                List.of(a, star, openParenthesis, a, plus, B, A, closedParenthesis, C, A),
                List.of(a, star, openParenthesis, a, plus, D, C, A, closedParenthesis, C, A),
                List.of(a, star, openParenthesis, a, plus, a, C, A, closedParenthesis, C, A),
                List.of(a, star, openParenthesis, a, plus, a, A, closedParenthesis, C, A),
                List.of(a, star, openParenthesis, a, plus, a, closedParenthesis, C, A),
                List.of(a, star, openParenthesis, a, plus, a, closedParenthesis, A),
                List.of(a, star, openParenthesis, a, plus, a, closedParenthesis)
        );

        Assertions.assertEquals(expected, actual);
    }
}