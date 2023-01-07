package org.compilers.grammar.parser.ll1.configuration;

import org.compilers.grammar.model.parse_table.action.NextMove;
import org.compilers.grammar.model.vocabulary.Symbol;
import org.compilers.grammar.model.vocabulary.terminal.Terminal;

import java.util.List;
import java.util.Stack;

public interface Configuration {
    void push(final NextMove nextMove);

    void pop();

    Terminal inputStackPeek();

    Symbol workStackPeek();

    Stack<? extends Terminal> inputStack();

    Stack<? extends Symbol> workStack();

    List<? extends Integer> output();
}
